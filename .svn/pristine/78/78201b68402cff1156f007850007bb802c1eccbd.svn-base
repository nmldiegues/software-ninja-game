package com.codebits.softwareninja.interpretation.operations;

import com.codebits.softwareninja.interpretation.BoolValue;

public class UnaryConditionOperation extends ConditionOperation {

	private final BoolValue unaryVal;

	public UnaryConditionOperation(String originalText, BoolValue unaryValue) {
		super(originalText, null, null);
		this.unaryVal = unaryValue;
	}

	@Override
	public Boolean computeResult() {
		return unaryVal.getBool();
	}

}
