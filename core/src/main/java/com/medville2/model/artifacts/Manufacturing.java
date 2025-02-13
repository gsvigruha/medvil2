package com.medville2.model.artifacts;

import java.io.Serializable;
import java.util.Map;

import com.medville2.model.society.Market;

public class Manufacturing implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String name;
	private final int timeDays;
	private final Map<String, Integer> inputs;
	private final Map<String, Integer> outputs;

	public Manufacturing(String name, int timeDays, Map<String, Integer> inputs, Map<String, Integer> outputs) {
		this.name = name;
		this.timeDays = timeDays;
		this.inputs = inputs;
		this.outputs = outputs;
	}

	public int getProfit(Market market) {
		return (market.getPrice(outputs) - market.getPrice(inputs)) * 100 / timeDays;
	}

	public boolean isProfitable(Market market) {
		return getProfit(market) > 0;
	}

	public String getName() {
		return name;
	}

	public Map<String, Integer> getInputs() {
		return inputs;
	}

	public Map<String, Integer> getOutputs() {
		return outputs;
	}
}
