package com.codebits.softwareninja.interpretation;

public class BoolValue extends Value {

	private final Boolean value;

	public static final String BOOL_TRUE = "T";
	public static final String BOOL_FALSE = "F";

	public BoolValue(Boolean value) {
		this.value = value;
	}

	@Override
	public Boolean getBool() {
		return value;
	}

}
