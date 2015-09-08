package com.codebits.softwareninja.domain.type;

import com.codebits.softwareninja.interpretation.Value;

public abstract class LanguageType {

	private static final CharSequence[] typesStr = initTypesStr();

	private final String desc;

	public LanguageType(String desc) {
		this.desc = desc;
	}

	public abstract Class<?> getJavaClass();

	public String getDesc() {
		return desc;
	}

	public abstract Value createValue(Object value);

	public abstract Object getValue(Value value);

	public static CharSequence[] getTypesStr() {
		return typesStr;
	}

	public static LanguageType fromString(String desc) {
		if (desc.equals("num")) {
			return new Numeric();
		} else if (desc.equals("{num}")) {
			return new ArrayNum();
		} else if (desc.equals("{bool}")) {
			return new ArrayBool();
		} else {
			return new Bool();
		}
	}

	private static CharSequence[] initTypesStr() {
		CharSequence[] arr = new CharSequence[4];
		arr[0] = "num";
		arr[1] = "bool";
		arr[2] = "{num}";
		arr[3] = "{bool}";
		return arr;
	}

}
