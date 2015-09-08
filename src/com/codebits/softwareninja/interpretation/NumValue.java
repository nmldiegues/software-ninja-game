package com.codebits.softwareninja.interpretation;

public class NumValue extends Value {

	private final Integer value;
	
	public NumValue(Integer value) {
		this.value = value;
	}
	
	@Override
	public Integer getInt() {
		return value;
	}
	
}
