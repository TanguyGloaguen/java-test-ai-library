package com.gloaguen2u.antFood.controller;

import com.gloaguen2u.antFood.model.AntFood;

public class Position implements AntFood {
	public float x;
	public float y;

	public Position(float f, float g) {
		this.x = f;
		this.y = g;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}
}
