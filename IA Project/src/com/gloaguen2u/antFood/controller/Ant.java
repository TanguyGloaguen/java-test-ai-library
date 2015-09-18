package com.gloaguen2u.antFood.controller;

import java.util.ArrayList;

import com.gloaguen2u.common.neuralnets.FloatNeuron;
import com.gloaguen2u.common.neuralnets.Input;
import com.gloaguen2u.common.neuralnets.Neuron;

import com.gloaguen2u.antFood.model.AntEntity;
import com.gloaguen2u.common.Data;
import com.gloaguen2u.common.genalgs.Entity;

public class Ant extends Entity implements AntEntity {
	// Data of the ant.
	public static float stepSize = 1f;
	// Position
	private float x, y;
	// Direction & speed
	private float dir, speed;
	// Connectors
	private final Value dX = new Value();
	private final Value dY = new Value();
	private final Value fX = new Value();
	private final Value fY = new Value();
	private FloatNeuron outX, outY;
	// Brain layers.
	private final ArrayList<ArrayList<Neuron<?>>> layers = new ArrayList<ArrayList<Neuron<?>>>();

	// Inputs for the neural network.
	public class Value implements Input<Float> {
		public float val;

		@Override
		public Float getValue() {
			return val;
		}
	}

	// UtilsObject for Utils neuron creation
	public UtilsConnector connector = new UtilsConnector(this);

	public class UtilsConnector {
		public final Ant parent;

		public UtilsConnector(Ant parent) {
			this.parent = parent;
		}

		public Value getDX() {
			return parent.dX;
		}

		public Value getDY() {
			return parent.dY;
		}

		public Value getFX() {
			return parent.fX;
		}

		public Value getFY() {
			return parent.fY;
		}

		public void setOX(FloatNeuron outX) {
			parent.outX = outX;
		}

		public void setOY(FloatNeuron outY) {
			parent.outY = outY;
		}

		public void addNeuron(Neuron<?> n, int level) {
			ArrayList<Neuron<?>> layer;
			try {
				layer = parent.layers.get(level);
			} catch (IndexOutOfBoundsException e) {
				parent.layers.add(level, (new ArrayList<Neuron<?>>()));
				layer = parent.layers.get(level);
			}
			layer.add(n);
		}
	}

	// Getters & setters
	@Override
	public float getX() {
		return this.x;
	}

	@Override
	public float getY() {
		return this.y;
	}

	@Override
	public float getDirX() {
		return this.dX.val;
	}

	@Override
	public float getDirY() {
		return this.dY.val;
	}

	public void setDirFood(float dX, float dY) {
		this.fX.val = dX;
		this.fY.val = dY;
	}

	// Updater
	public void update(float sX, float sY) {
		// Resolve the ant brain.
		for (ArrayList<Neuron<?>> layer : layers) // For each layer, in order
													// from closest to furthest.
			for (Neuron<?> n : layer) // For each neuron
				n.calculate(); // Resolve the neuron.
		// Now, we update the ant.
		float kappaL = outX.getValue(); // Retrieve applied force on left &
										// right of the ant.
		float kappaR = outY.getValue();
		// Apply force
		float rotDir = kappaL - kappaR;
		dir += rotDir;
		speed = kappaL + kappaR;
		if (speed < -1)
			speed = -1f;
		if (speed > 1)
			speed = 1f;
		// Calculate dir vectors
		this.dX.val = (float) Math.sin(dir);
		this.dY.val = (float) Math.cos(dir);
		// the ant moves.
		x += this.dX.val * speed * stepSize;
		y += this.dY.val * speed * stepSize;
		// Don't leave the map.
		if (x < 0)
			x += sX;
		if (x > sX)
			x -= sX;
		if (y < 0)
			y += sY;
		if (y > sY)
			y -= sY;
	}

	// Constructor
	public Ant(float posX, float posY) {
		super();
		x = posX;
		y = posY;
		dir = Data.rng.nextFloat() * (float) Math.PI;
		speed = Data.rng.nextFloat();
		this.dX.val = speed * (float) Math.sin(dir);
		this.dY.val = speed * (float) Math.cos(dir);
	}

	public void createBrain() {
		for (int i = 0; i < identity.size(); i++)
			Utils.createBrain(identity.get(i), connector, i);
	}

	// Entity functions

	@Override
	public Ant combine(Entity e) {
		Ant res = new Ant(Data.rng.nextFloat() * x, Data.rng.nextFloat() * y);
		for (int i = 0; i < identity.size(); i++)
			res.addChromosome(identity.get(i).combine(e.identity.get(i)));
		return res;
	}
}
