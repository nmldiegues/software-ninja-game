package com.codebits.softwareninja.interpretation.operations;

import com.codebits.softwareninja.interpretation.Value;

public class LessThanOperation extends ConditionOperation {

	public LessThanOperation(String originalText, Value leftValue, Value rightValue) {
		super(originalText, leftValue, rightValue);
	}

	@Override
	public Boolean computeResult() {
		return getLeftValue().getInt() < getRightValue().getInt();
	}

}
