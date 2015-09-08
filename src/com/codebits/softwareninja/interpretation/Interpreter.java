package com.codebits.softwareninja.interpretation;

import java.util.List;

import android.app.Activity;
import android.content.Intent;

import com.codebits.softwareninja.ResultEvaluationActivity;
import com.codebits.softwareninja.domain.BeginNode;
import com.codebits.softwareninja.domain.CycleNode;
import com.codebits.softwareninja.domain.EndNode;
import com.codebits.softwareninja.domain.ExpNode;
import com.codebits.softwareninja.domain.IfNode;
import com.codebits.softwareninja.domain.Node;
import com.codebits.softwareninja.domain.PrintNode;
import com.codebits.softwareninja.domain.ProgramView;
import com.codebits.softwareninja.domain.exceptions.NinjaException;
import com.codebits.softwareninja.domain.type.Parameter;
import com.codebits.softwareninja.domain.type.Variable;
import com.codebits.softwareninja.interpretation.operations.Operation;
import com.codebits.softwareninja.interpretation.operations.Parser;

public class Interpreter extends Thread implements NodeVisitor {

	private final EvaluationState state;
	private final ProgramView program;
	private Boolean resultMatches;
	private NinjaException error;

	public Interpreter(ProgramView program) {
		this.program = program;
		this.state = new EvaluationState(program.level.getParams());
		this.resultMatches = false;
		this.error = null;
	}

	@Override
	public void run() {
		try {
			checkErrors(program);
			this.resultMatches = program.level.evaluateResponse(run(program, program.level.getParams()));
		} catch (NinjaException e) {
			this.error = e;
		}
		final boolean ret = this.resultMatches;
		final NinjaException exception = this.error;
		final Activity activity = (Activity) program.getContext();
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (exception != null) {
					Intent intent = new Intent(activity, ResultEvaluationActivity.class);
					intent.putExtra("res", ret);
					intent.putExtra("err", exception.getMessage());
					intent.putExtra("prints", Interpreter.this.state.getSystemOut());
					activity.startActivity(intent);
				} else {
					Intent intent = new Intent(activity, ResultEvaluationActivity.class);
					intent.putExtra("res", ret);
					intent.putExtra("prints", Interpreter.this.state.getSystemOut());
					activity.startActivity(intent);
				}
			}
		});
	}

	private void checkErrors(ProgramView program) throws NinjaException {
		if (program.getBeginningNode() == null)
			throw new NinjaException("No begin node in control flux");

		if (program.getEndingNode() == null)
			throw new NinjaException("No end node in control flux");

		if (program.getBeginningNode().getParamsModelList().size() != program.level.getParamsCount())
			throw new NinjaException("Wrong number of parameters");

		if (!program.level.checkTypes(program.getBeginningNode()))
			throw new NinjaException("Wrong Types in the parameters");

		if (!program.level.checkTypes(program.getEndingNode()))
			throw new NinjaException("Wrong Type of return");
	}

	private Value run(ProgramView program, List<Object> params) throws NinjaException {
		checkErrors(program);
		program.getBeginningNode().accept(this);
		return this.state.getReturnValue();
	}

	private void sleepBetweenVisits() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
	}

	private void activateNodeLive(Node node) {
		node.setLiveExecution(true);
		node.setShowInfo(true);
		sleepBetweenVisits();
	}

	private void disableNodeLive(Node node) {
		sleepBetweenVisits();

		node.setLiveExecution(false);
		node.setShowInfo(false);
	}

	@Override
	public void visit(BeginNode node) {
		activateNodeLive(node);

		int consumedParamValue = 1;
		List<Object> paramValues = this.state.getParamsValues();
		for (Parameter param : node.getParamsModelList()) {
			Object value = consumedParamValue > paramValues.size() ? EvaluationState.NOT_INIT : paramValues.get(consumedParamValue - 1);
			this.state.assignValue(param, value);
			consumedParamValue++;
		}

		node.setExecutionValues(paramValues);
		disableNodeLive(node);
		node.setExecutionValues(null);
	}

	@Override
	public boolean visit(CycleNode node) {
		activateNodeLive(node);
		boolean result = Parser.parseConditionOperation(this.state, node.getCondition()).computeResult();
		node.setExecutionValue(result);
		disableNodeLive(node);
		node.setExecutionValue(null);
		return result;
	}

	@Override
	public void visit(EndNode node) {
		activateNodeLive(node);

		Variable var = node.getReturnVar();
		if (var != null) {
			Object val = this.state.retrieveValue(var);
			this.state.setReturnValue(var.getType().createValue(val));
			node.setExecutionValue(val);
		}

		disableNodeLive(node);
		node.setExecutionValue(null);
	}

	@Override
	public void visit(ExpNode node) {
		activateNodeLive(node);

		Variable var = node.getVar();
		Operation op = null;
		if (var.getType().getDesc().equals("num")) {
			op = Parser.parseArithmeticOperation(this.state, node.getExpr());
		} else {
			// assume bool
			op = Parser.parseConditionOperation(this.state, node.getExpr());
		}
		Object value = op.computeResult();
		this.state.assignValue(var, value);
		node.setExecutionValue(value);
		disableNodeLive(node);
		node.setExecutionValue(null);
	}

	@Override
	public boolean visit(IfNode node) {
		activateNodeLive(node);
		boolean result = Parser.parseConditionOperation(this.state, node.getCondition()).computeResult();
		node.setExecutionValue(result);
		disableNodeLive(node);
		node.setExecutionValue(null);
		return result;
	}

	@Override
	public void visit(PrintNode node) {
		activateNodeLive(node);

		Variable var = node.getVarToPrint();
		String tmp = "";
		String text = node.getText();
		if (text != null) {
			tmp += text;
		}
		if (var != null) {
			Object value = this.state.retrieveValue(var);
			tmp += value;
			node.setExecutionValue(value);
		}
		this.state.addToSystemOut(tmp);

		disableNodeLive(node);
		node.setExecutionValue(null);
	}

}
