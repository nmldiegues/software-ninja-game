package com.codebits.softwareninja.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.codebits.softwareninja.domain.type.Numeric;

public class LevelCreator {

	public static Level createLevel(int level) {
		switch (level) {
		case 1:
			return createLevel1();
		case 2:
			return createLevel2();
		default:
			return createLevel1();
		}
	}

	private static Level createLevel1() {
		return new Level(new ArrayList<Object>(), null, null, null);
	}

	// Factorial
	public static Level createLevel2() {
		List<Object> params = new ArrayList<Object>();
		Object ret = new Integer(120);
		params.add(new Integer(5));
		Map<Class<?>, Integer> counts = new HashMap<Class<?>, Integer>();
		counts.put(BeginNode.class, 1);
		counts.put(ExpNode.class, 4);
		counts.put(IfNode.class, 1);
		counts.put(CycleNode.class, 1);
		counts.put(PrintNode.class, 5);
		counts.put(EndNode.class, 2);
		return new Level(params, ret, new Numeric(), counts);
	}

	public static String getLevel2Description() {
		return "Implement the factorial function\n This function returns n*(n-1)*(n-2)*... until n==1\n\n Parameters: Numeric(n)\n Return Type: Numeric\n\n Ninja, Vanish!!";
	}

	public static String getLevel1Description() {
		return "Create a correct flux of instructions\n\n Example: Print Hello World\n\n Parameters: None\n Return Type: None\n\n Ninja, Vanish!!";
	}

}
