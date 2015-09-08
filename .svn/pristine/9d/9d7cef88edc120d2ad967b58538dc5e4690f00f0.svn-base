package com.codebits.softwareninja.interpretation.operations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.codebits.softwareninja.domain.type.Identifier;
import com.codebits.softwareninja.domain.type.Variable;
import com.codebits.softwareninja.interpretation.BoolValue;
import com.codebits.softwareninja.interpretation.EvaluationState;
import com.codebits.softwareninja.interpretation.NumValue;

public class Parser {

	public static final List<String> arithmeticOperations = new ArrayList<String>();
	public static final List<String> conditionOperations = new ArrayList<String>();

	static {
		arithmeticOperations.addAll(Arrays.asList(new String[] { "+", "-", "*", "/", "%" }));
		// the order of the condition operations is relevant
		conditionOperations.addAll(Arrays.asList(new String[] { "<=", "<", ">=", ">", "=" }));
	}

	public static ArithmeticOperation parseArithmeticOperation(EvaluationState state, String text) {
		String usedOp = null;
		for (String op : arithmeticOperations) {
			if (text.contains(op)) {
				usedOp = op;
				break;
			}
		}

		// Consider unary operation
		if (usedOp == null) {
			String str = text.trim();
			Integer operand = null;
			try {
				operand = Integer.parseInt(str);
			} catch (Exception e) {
			}
			try {
				if (operand == null) {
					operand = (Integer) state.retrieveValue(new Variable(new Identifier(str), null));
				}
			} catch (ClassCastException cce) {
				throw new RuntimeException("Refered to variable with wrong type of operation: " + str + " " + operand);
			}
			return new UnaryArithmeticOperation(text, new NumValue(operand));
		}

		Integer leftOperand = extractLeftIntOperand(state, text, usedOp);
		Integer rightOperand = extractRightIntOperand(state, text, usedOp, leftOperand);

		if (usedOp.equals("+")) {
			return new SumOperation(text, new NumValue(leftOperand), new NumValue(rightOperand));
		} else if (usedOp.equals("-")) {
			return new DiffOperation(text, new NumValue(leftOperand), new NumValue(rightOperand));
		} else if (usedOp.equals("*")) {
			return new MultOperation(text, new NumValue(leftOperand), new NumValue(rightOperand));
		} else if (usedOp.equals("/")) {
			return new DivideOperation(text, new NumValue(leftOperand), new NumValue(rightOperand));
		} else if (usedOp.equals("%")) {
			return new ModuloOperation(text, new NumValue(leftOperand), new NumValue(rightOperand));
		}

		throw new RuntimeException("Arithmetic Operation contains invalid operation");
	}

	private static Integer extractRightIntOperand(EvaluationState state, String text, String usedOp, Integer leftOperand) {
		String rightOperandStr = text.substring(text.indexOf(usedOp) + usedOp.length(), text.length()).trim();
		Integer rightOperand = null;
		try {
			rightOperand = Integer.parseInt(rightOperandStr);
		} catch (Exception e) {
		}
		try {
			if (rightOperand == null) {
				rightOperand = (Integer) state.retrieveValue(new Variable(new Identifier(rightOperandStr), null));
			}
		} catch (ClassCastException cce) {
			throw new RuntimeException("Refered to variable with wrong type of operation: " + leftOperand + " " + rightOperand);
		}
		return rightOperand;
	}

	private static Integer extractLeftIntOperand(EvaluationState state, String text, String usedOp) {
		String leftOperandStr = text.substring(0, text.indexOf(usedOp)).trim();
		Integer leftOperand = null;
		try {
			leftOperand = Integer.parseInt(leftOperandStr);
		} catch (Exception e) {
		}
		try {
			if (leftOperand == null) {
				leftOperand = (Integer) state.retrieveValue(new Variable(new Identifier(leftOperandStr), null));
			}
		} catch (ClassCastException cce) {
			throw new RuntimeException("Refered to variable with wrong type of operation: " + leftOperand);
		}
		return leftOperand;
	}

	public static ConditionOperation parseConditionOperation(EvaluationState state, String text) {
		String usedOp = null;
		for (String op : conditionOperations) {
			if (text.contains(op)) {
				usedOp = op;
				break;
			}
		}

		// Consider unary condition (a simple boolean)
		if (usedOp == null) {
			String str = text.trim();
			Boolean operand = null;
			if (str.equals(BoolValue.BOOL_TRUE)) {
				return new UnaryConditionOperation(text, new BoolValue(true));
			} else if (str.equals(BoolValue.BOOL_FALSE)) {
				return new UnaryConditionOperation(text, new BoolValue(false));
			}
			try {
				if (operand == null) {
					operand = (Boolean) state.retrieveValue(new Variable(new Identifier(str), null));
				}
			} catch (ClassCastException cce) {
				throw new RuntimeException("Refered to variable with wrong type of operation: " + str + " " + operand);
			}
			return new UnaryConditionOperation(text, new BoolValue(operand));
		}

		Integer leftOperand = extractLeftIntOperand(state, text, usedOp);
		Integer rightOperand = extractRightIntOperand(state, text, usedOp, leftOperand);

		if (usedOp.equals("<")) {
			return new LessThanOperation(text, new NumValue(leftOperand), new NumValue(rightOperand));
		} else if (usedOp.equals("<=")) {
			return new LessEqualThanOperation(text, new NumValue(leftOperand), new NumValue(rightOperand));
		} else if (usedOp.equals("=")) {
			return new EqualOperation(text, new NumValue(leftOperand), new NumValue(rightOperand));
		} else if (usedOp.equals(">")) {
			return new LargerThanOperation(text, new NumValue(leftOperand), new NumValue(rightOperand));
		} else if (usedOp.equals(">=")) {
			return new LargerEqualThanOperation(text, new NumValue(leftOperand), new NumValue(rightOperand));
		}

		throw new RuntimeException("Condition Operation contains invalid operation");
	}
}
