package com.codebits.softwareninja.interpretation;

import java.util.ArrayList;

public class BoolArrayValue extends Value {

	private final ArrayList<Boolean> value;

	public BoolArrayValue(ArrayList<Boolean> value) {
		this.value = value;
	}

	@Override
	public ArrayList<Boolean> getBoolArray() {
		return value;
	}
	
}
