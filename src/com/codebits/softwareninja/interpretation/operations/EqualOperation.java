package com.codebits.softwareninja.interpretation.operations;

import com.codebits.softwareninja.interpretation.Value;

public class EqualOperation extends ConditionOperation {

	public EqualOperation(String originalText, Value leftValue, Value rightValue) {
		super(originalText, leftValue, rightValue);
	}

	@Override
	public Boolean computeResult() {
		return getLeftValue().getInt().equals(getRightValue().getInt());
	}

	
}
