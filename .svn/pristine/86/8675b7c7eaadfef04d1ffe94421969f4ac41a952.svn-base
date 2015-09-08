package com.codebits.softwareninja.domain.type;


public class Parameter extends Variable {

	private boolean inputParameter; // false if it is an outputParameter (not the normal case)
	
	public Parameter(Identifier name, LanguageType type, boolean inputParameter) {
		super(name, type);
		this.setInputParameter(inputParameter);
	}

	public boolean isInputParameter() {
		return inputParameter;
	}

	public void setInputParameter(boolean inputParameter) {
		this.inputParameter = inputParameter;
	}

}
