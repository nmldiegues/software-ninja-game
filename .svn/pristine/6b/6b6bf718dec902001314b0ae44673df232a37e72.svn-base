package com.codebits.softwareninja.domain.type;

import java.util.ArrayList;

import com.codebits.softwareninja.interpretation.NumArrayValue;
import com.codebits.softwareninja.interpretation.Value;

public class ArrayNum extends LanguageType {

	public ArrayNum() {
		super("{num}");
	}

	@Override
	public Class<?> getJavaClass() {
		return ArrayList.class;
	}

	@Override
	public NumArrayValue createValue(Object value) {
		return new NumArrayValue((ArrayList<Integer>) value);
	}

	@Override
	public Object getValue(Value value) {
		return value.getIntArray();
	}

}
