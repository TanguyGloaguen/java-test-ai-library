package com.gloaguen2u.common.neuralnets;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractNeuron<T> implements Neuron<T> {
	protected Map<Input<?>, Float> inputs;
	protected float threshold;
	protected T result;

	public AbstractNeuron() {
		inputs = new HashMap<Input<?>, Float>();
	}

	@Override
	public void Connect(Input<?> in, float weight) {
		inputs.put(in, weight);
	}

	@Override
	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}

	@Override
	public abstract void calculate();

	@Override
	public T getValue() {
		return result;
	}

}
