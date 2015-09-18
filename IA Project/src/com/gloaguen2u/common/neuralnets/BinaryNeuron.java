package com.gloaguen2u.common.neuralnets;

import java.util.Map;

import com.gloaguen2u.common.Logger;

public final class BinaryNeuron extends AbstractNeuron<Boolean> {
	@Override
	public void calculate() {
		float result = 0f;
		for (Map.Entry<Input<?>, Float> entry : inputs.entrySet()) {
			Object val = entry.getKey().getValue();
			if (val instanceof Integer)
				result += ((float) val) * entry.getValue();
			else if (val instanceof Boolean)
				result += ((boolean) val) ? entry.getValue() : 0;
			else if (val instanceof Float)
				result += ((float) val) * entry.getValue();
			else
				Logger.warn("Unknown type for calculus : " + val.getClass().getName());
		}
		this.result = result >= threshold;
	}
}
