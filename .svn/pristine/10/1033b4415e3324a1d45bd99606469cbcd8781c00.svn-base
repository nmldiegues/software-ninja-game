package com.codebits.softwareninja.domain.type;

import com.codebits.softwareninja.interpretation.BoolValue;
import com.codebits.softwareninja.interpretation.Value;

public class Bool extends LanguageType {

	public Bool() {
		super("bool");
	}

	@Override
	public Class<?> getJavaClass() {
		return Boolean.class;
	}

	@Override
	public BoolValue createValue(Object value) {
		return new BoolValue((Boolean) value);
	}

	@Override
	public Object getValue(Value value) {
		return value.getBool();
	}

}
