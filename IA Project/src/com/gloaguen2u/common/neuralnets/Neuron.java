package com.gloaguen2u.common.neuralnets;

public interface Neuron<T> extends Input<T> {
	public void Connect(Input<?> in, float weight);

	public void setThreshold(float threshold);

	public void calculate();
}
