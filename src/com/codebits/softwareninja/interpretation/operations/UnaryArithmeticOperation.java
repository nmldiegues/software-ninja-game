package com.codebits.softwareninja.interpretation.operations;

import com.codebits.softwareninja.interpretation.NumValue;

public class UnaryArithmeticOperation extends ArithmeticOperation {

	private final NumValue unaryVal;

	public UnaryArithmeticOperation(String originalText, NumValue numVal) {
		super(originalText, null, null);
		this.unaryVal = numVal;
	}

	@Override
	public Integer computeResult() {
		return unaryVal.getInt();
	}

}
