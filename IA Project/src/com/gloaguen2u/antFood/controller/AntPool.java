package com.gloaguen2u.antFood.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.gloaguen2u.antFood.model.AntWorld;
import com.gloaguen2u.common.Data;
import com.gloaguen2u.common.genalgs.Pool;

public class AntPool extends Pool<Ant> {
	public static float minDirPickup = 0.99f;
	public final int foodMax;
	private final int steps;
	private final float sX, sY;
	private ArrayList<Position> food = new ArrayList<Position>();
	private final int generation;

	// Pool functions
	public AntPool(Collection<Ant> ents, int steps, int foodMax, float sX, float sY, int generation) {
		super(ents);
		this.foodMax = foodMax;
		this.steps = steps;
		this.sX = sX;
		this.sY = sY;
		this.generation = generation;
	}

	@Override
	public void evaluate() {
		Ant[] antArray = new Ant[entities.size()];
		Position[] foodArray = new Position[foodMax];
		for (int i = 0; i < foodMax; i++)
			food.add(new Position(Data.rng.nextFloat() * sX, Data.rng.nextFloat() * sY));
		for (Ant a : entities) {
			entEvals.put(a, 0f);
		}
		for (int i = 0; i < steps; i++) {
			for (Ant a : entities) {
				Position closest = getClosestFood(a);
				float drX = closest.x - a.getX();
				if (drX > sX / 2)
					drX -= sX;
				if (drX < -sX / 2)
					drX += sX;
				float drY = closest.y - a.getY();
				if (drY > sY / 2)
					drY -= sY;
				if (drY < -sY / 2)
					drY += sY;
				float norm = (float) Math.sqrt(Math.pow(drX, 2) + Math.pow(drY, 2));
				if (norm == 0)
					norm = 1;
				a.setDirFood(drX / norm, drY / norm);
				a.update(sX, sY);
				float result = entEvals.get(a) + getFood(a);
				entEvals.put(a, result);
			}
			new AntWorld(entities.toArray(antArray), food.toArray(foodArray), generation).display();
		}
	}

	@Override
	public AntPool nextGen() {
		Collection<Ant> ents = new ArrayList<Ant>();
		evalSum = 0f;
		for (Float f : entEvals.values())
			evalSum += f;
		for (int i = 0; i < evalSum / (Math.sqrt(entities.size()) + 1); i++) {
			Ant a = makeBaby();
			if(a != null) {
				a.createBrain();
				ents.add(a);
			}
		}
		return new AntPool(ents, steps, foodMax, sX, sY, generation + 1);
	}

	// Ant functions
	private float getFood(Ant a) {
		int res = 0;
		for (Position f : food)
			if (Math.pow(f.x - a.getX(), 2) + Math.pow(f.y - a.getY(), 2) < minDirPickup) {
				res++;
				f.x = Data.rng.nextFloat() * sX;
				f.y = Data.rng.nextFloat() * sY;
			}

		return res;
	}

	private Position getClosestFood(Ant a) {
		Position res = null;
		float maxDist = sX * sX + sY * sY;
		for (Position f : food) {
			float drX = a.getX() - f.x;
			if (drX > sX / 2)
				drX -= sX;
			if (drX < -sX / 2)
				drX += sX;
			float drY = a.getY() - f.y;
			if (drY > sY / 2)
				drY -= sY;
			if (drY < -sY / 2)
				drY += sY;
			if (Math.pow(drX, 2) + Math.pow(drY, 2) < maxDist) {
				res = f;
				maxDist = (float) (Math.pow(drX, 2) + Math.pow(drY, 2));
			}
		}
		return res;
	}

	// Result analyzer
	public String getResult() {
		float average = 0;
		float max = 0;
		float med = 0;
		int nb = entities.size();
		for (float f : entEvals.values()) {
			average += f;
			max = f > max ? f : max;
		}
		float min = max;
		for (float f : entEvals.values())
			min = f < min ? f : min;
		List<Float> medCalculator = new ArrayList<Float>(entEvals.values());
		Collections.sort(medCalculator);
		if(!medCalculator.isEmpty())
			med = medCalculator.get(medCalculator.size() / 2);
		if(!entEvals.isEmpty())
			average /= entEvals.size();
		return "{\"avg\":" + average + ",\"med\":" + med + ",\"min\":" + min + ",\"max\":" + max + ",\"nb\":" + nb + "}";
	}

}
