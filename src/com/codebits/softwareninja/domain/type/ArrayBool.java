package com.codebits.softwareninja.domain.type;

import java.util.ArrayList;

import com.codebits.softwareninja.interpretation.BoolArrayValue;
import com.codebits.softwareninja.interpretation.Value;

public class ArrayBool extends LanguageType {

	public ArrayBool() {
		super("{bool}");
	}

	@Override
	public Class<?> getJavaClass() {
		return ArrayList.class;
	}

	@Override
	public BoolArrayValue createValue(Object value) {
		return new BoolArrayValue((ArrayList<Boolean>) value);
	}

	@Override
	public Object getValue(Value value) {
		return value.getBoolArray();
	}

}
