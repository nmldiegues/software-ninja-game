package com.codebits.softwareninja.interpretation.operations;

import com.codebits.softwareninja.interpretation.Value;

public abstract class ConditionOperation extends Operation {

	public ConditionOperation(String originalText, Value leftValue, Value rightValue) {
		super(originalText, leftValue, rightValue);
	}

	@Override
	public abstract Boolean computeResult();
}
