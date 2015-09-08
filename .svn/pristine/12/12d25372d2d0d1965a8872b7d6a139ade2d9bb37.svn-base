package com.codebits.softwareninja.interpretation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codebits.softwareninja.domain.type.LanguageType;
import com.codebits.softwareninja.domain.type.Variable;

/*
 * This class holds the memory corresponding to an evaluation 
 */
public class EvaluationState {

	private String systemOut;
	private Value returnValue;
	private final Map<String, Object> memory;
	private final List<Object> paramsValues;

	public final static Object NOT_INIT = new Object();

	public EvaluationState(List<Object> paramsValues) {
		this.systemOut = "";
		this.memory = new HashMap<String, Object>();
		this.paramsValues = paramsValues;
	}

	public void assignValue(Variable var, Object value) {
		if (!checkType(var.getType(), value)) {
			throw new RuntimeException("Variable " + var.getName() + " of type " + var.getType() + " was assigned value of type " + value.getClass());
		}
		memory.put(var.getName().getId(), value);
	}

	public Object retrieveValue(Variable var) {
		Object value = memory.get(var.getName().getId());
		if (value == NOT_INIT) {
			throw new RuntimeException("Retrieving variable not initialized");
		}
		return value;
	}

	public boolean checkType(LanguageType type, Object value) {
		return type.getJavaClass().equals(value.getClass());
	}

	public Value getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Value returnValue) {
		this.returnValue = returnValue;
	}

	public String getSystemOut() {
		return systemOut;
	}

	public void addToSystemOut(String moreOutput) {
		this.systemOut += moreOutput + "\n";
	}

	public List<Object> getParamsValues() {
		return paramsValues;
	}

}
