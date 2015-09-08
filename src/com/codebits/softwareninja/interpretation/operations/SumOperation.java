package com.codebits.softwareninja.interpretation.operations;

import com.codebits.softwareninja.interpretation.Value;


public class SumOperation extends ArithmeticOperation {

	public SumOperation(String originalText, Value leftValue, Value rightValue) {
		super(originalText, leftValue, rightValue);
	}

	@Override
	public Integer computeResult() {
		return getLeftValue().getInt() + getRightValue().getInt();
	}

	
}
