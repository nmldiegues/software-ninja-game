package com.codebits.softwareninja.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codebits.softwareninja.domain.type.LanguageType;
import com.codebits.softwareninja.domain.type.Parameter;
import com.codebits.softwareninja.interpretation.Value;

public class Level {

	private final List<Object> params;
	private final Object ret;
	private final LanguageType returnType;
	private final Map<Class<?>, Integer> limits;

	public int getParamsCount() {
		return params.size();
	}

	public Level(List<Object> params, Object ret, LanguageType returnType, Map<Class<?>, Integer> limits) {
		this.params = params;
		this.ret = ret;
		this.returnType = returnType;
		this.limits = limits;
	}

	public boolean checkTypes(BeginNode node) {
		List<Parameter> paramsProg = node.getParamsModelList();
		for (int i = 0; i < params.size(); i++) {
			if (!paramsProg.get(i).getType().getJavaClass().equals(params.get(i).getClass())) {
				return false;
			}
		}
		return true;
	}

	public boolean checkTypes(EndNode node) {
		if (returnType != null) {
			LanguageType retType = node.getReturnLanguageType();
			return retType.getJavaClass().equals(ret.getClass());
		}
		return true;
	}

	public List<Object> getParams() {
		return params;
	}

	public Boolean evaluateResponse(Value returnValue) {
		if (this.ret == null || this.returnType == null) {
			return true;
		}
		return ret.equals(this.returnType.getValue(returnValue));
	}

	public Map<Class<?>, Integer> getNodeTypeLimits() {
		return limits != null ? limits : new HashMap<Class<?>, Integer>();
	}

}
