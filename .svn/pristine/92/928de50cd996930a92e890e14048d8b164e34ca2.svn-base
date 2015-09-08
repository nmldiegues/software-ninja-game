package com.codebits.softwareninja.interpretation.operations;

import com.codebits.softwareninja.interpretation.Value;

public abstract class Operation {

	private final String originalText;
	private final Value leftValue;
	private final Value rightValue;

	public Operation(String originalText, Value leftValue, Value rightValue) {
		this.originalText = originalText;
		this.leftValue = leftValue;
		this.rightValue = rightValue;
	}

	public Value getLeftValue() {
		return leftValue;
	}

	public Value getRightValue() {
		return rightValue;
	}

	public String getOriginalText() {
		return originalText;
	}

	public abstract Object computeResult();

}
