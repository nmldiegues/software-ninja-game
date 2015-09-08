package com.codebits.softwareninja.domain.type;

import com.codebits.softwareninja.interpretation.NumValue;
import com.codebits.softwareninja.interpretation.Value;

public class Numeric extends LanguageType {

	public Numeric() {
		super("num");
	}

	@Override
	public Class<?> getJavaClass() {
		return Integer.class;
	}

	@Override
	public NumValue createValue(Object value) {
		return new NumValue((Integer) value);
	}

	@Override
	public Object getValue(Value value) {
		return value.getInt();
	}

}
