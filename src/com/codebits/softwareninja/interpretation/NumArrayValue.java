package com.codebits.softwareninja.interpretation;

import java.util.ArrayList;

public class NumArrayValue extends Value {

	private final ArrayList<Integer> value;

	public NumArrayValue(ArrayList<Integer> value) {
		this.value = value;
	}

	@Override
	public ArrayList<Integer> getIntArray() {
		return value;
	}

}
