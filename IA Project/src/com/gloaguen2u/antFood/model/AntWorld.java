package com.gloaguen2u.antFood.model;

import com.gloaguen2u.antFood.view.AntOutput;

public class AntWorld {
	public static AntOutput output = null;
	private AntEntity[] ants;
	private AntFood[] food;

	public AntWorld(AntEntity[] ants, AntFood[] food, int generation) {
		this.ants = ants;
		this.food = food;
		if (output != null)
			output.setGen(generation);
	}

	public AntWorld display() {
		if (output != null) {
			String s = "[";
			for (AntFood f : food) {
				s += "{\"type\":\"food\",\"x\":" + f.getX() + ",\"y\":" + f.getY() + "},";
			}
			for (AntEntity t : ants) {
				s += "{\"type\":\"ant\",\"x\":" + t.getX() + ",\"y\":" + t.getY() + ",\"dX\":" + t.getDirX()
						+ ",\"dY\":" + t.getDirY() + "},";
			}
			s += "{}]";
			output.logState(s);
		}
		return this;
	}
}
