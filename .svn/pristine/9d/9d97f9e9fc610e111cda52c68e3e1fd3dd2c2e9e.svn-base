package com.codebits.softwareninja.domain;

import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.codebits.softwareninja.R;
import com.codebits.softwareninja.domain.type.Identifier;
import com.codebits.softwareninja.domain.type.LanguageType;
import com.codebits.softwareninja.domain.type.Variable;
import com.codebits.softwareninja.interpretation.NodeVisitor;
import com.codebits.softwareninja.interpretation.operations.Parser;

public class ExpNode extends Node {

	// Variable that gets assigned. May be null if we decide not all expressions
	// are assignments
	private Variable var;
	private String expr;
	private Object executionValue;

	public ExpNode(Context context, int posX, int posY, ProgramView programView) {
		super(context, posX, posY, programView);
		// TODO Auto-generated constructor stub
		setRightStateImg(BitmapFactory.decodeResource(getResources(), R.drawable.botao_exp_green));
		setWrongStateImg(BitmapFactory.decodeResource(getResources(), R.drawable.botao_exp_red));
		setStateRight(false);
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	public Variable getVar() {
		if (this.var != null) {
			if (this.var.getName().getId().equals("")) {
				this.var = null;
				return null;
			}
		}
		return this.var;
	}

	public void setVar(Variable var) {
		this.var = var;
	}

	public String getExpr() {
		return expr;
	}

	public void setExpr(String expr) {
		this.expr = expr;
	}

	@Override
	public void populateContextToolbox(Activity activity, LinearLayout editLayout) {
		TextView toolboxTitle = (TextView) activity.findViewById(R.id.nodeName);
		toolboxTitle.setText("Expression");

		// Load the XML for the layout of this toolbox
		activity.getLayoutInflater().inflate(R.layout.toolbox_expr_node, editLayout);

		// Filter characters on the expression field and set the keyboard
		// correctly
		final EditText inputExpr = (EditText) editLayout.findViewById(R.id.inputExpr);
		// inputExpr.setRawInputType(InputType.TYPE_CLASS_NUMBER);
		// InputFilter filter = new InputFilter() {
		// @Override
		// public CharSequence filter(CharSequence source, int start, int end,
		// Spanned dest, int dstart, int dend) {
		// if (source.charAt(start) == ' ') {
		// return source.toString().replaceAll("\\s", "");
		// }
		// for (int i = start; i < end; i++) {
		// if (!Character.isDigit(source.charAt(i)) &&
		// !isMathSymbol(source.charAt(i))) {
		// return "";
		// }
		// }
		// return null;
		// }
		// };
		// inputExpr.setFilters(new InputFilter[] { filter });

		// Save the expression into the model
		inputExpr.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				ExpNode.this.expr = inputExpr.getText().toString();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});

		// If an expression already exists in the model, restore it in the UI
		if (this.expr != null) {
			inputExpr.setText(this.expr);
		}

		// Populate the existing variables to be used in the expression
		final Spinner existingVariablesChoice = (Spinner) editLayout.findViewById(R.id.chooseVariables);
		final CheckExistingVarsVisitor checker = new CheckExistingVarsVisitor();
		checker.visit((Node) this); // we want to skip this node on purpose to
		// avoid its variable of the assignment to
		// show up here
		final ArrayAdapter<CharSequence> existingVarsSpinnerAdapter = new ArrayAdapter<CharSequence>(activity, android.R.layout.simple_spinner_item, checker
				.getVarNames().toArray(new String[] {}));
		existingVarsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		existingVariablesChoice.setAdapter(existingVarsSpinnerAdapter);

		// When a variable is picked, add it to the expression
		final Button addExistingVariableBtn = (Button) editLayout.findViewById(R.id.addExistingVariableToExpr);
		addExistingVariableBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String parameterName = ((CharSequence) existingVariablesChoice.getSelectedItem()).toString();
				inputExpr.setText(inputExpr.getText().toString() + " " + parameterName);
				inputExpr.setSelection(inputExpr.getText().length());
			}
		});

		// Load the types into the type spinner
		final Spinner typeSpinner = (Spinner) editLayout.findViewById(R.id.assignedVariableType);
		CharSequence array[] = LanguageType.getTypesStr();
		ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(activity, android.R.layout.simple_spinner_item, array);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(spinnerAdapter);

		// Add the variable written (to be assigned) into the model
		final EditText newVar = (EditText) editLayout.findViewById(R.id.variableAssignedName);
		typeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String variableName = newVar.getText().toString();
				ExpNode.this.var = new Variable(new Identifier(variableName), LanguageType.fromString((String) typeSpinner.getSelectedItem()));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		newVar.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				String variableName = newVar.getText().toString();
				Variable var = checker.getVariable(variableName);
				if (var != null) {
					ExpNode.this.var = var;
				} else {
					ExpNode.this.var = new Variable(new Identifier(variableName), LanguageType.fromString((String) typeSpinner.getSelectedItem()));
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});

		// If a variable already exists in the model, set the spinner type to
		// its type
		// plus, add its name to the text box
		if (this.var != null) {
			String typeDesc = this.var.getType().getDesc();
			int position = 0;
			for (int i = 0; i < array.length; i++) {
				if (typeDesc.equals(array[i])) {
					position = i;
					break;
				}
			}
			typeSpinner.setSelection(position);
			newVar.setText(this.var.getName().getId());
		}
	}

	private boolean isMathSymbol(char c) {
		return Parser.arithmeticOperations.contains("" + c) || Parser.conditionOperations.contains("" + c);
	}

	@Override
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
		Set<Edge> out = getOutgoing();
		if (out.size() != 1) {
			throw new RuntimeException("Exp node must have only one next node");
		}
		out.iterator().next().getSinkNode().accept(visitor);
	}

	@Override
	public void handleNodeCollision(Node n) {
		if (n instanceof CycleNode && n.findForwardNode(this)) {
			programView.addEdge(new Edge(this, n, Edge.EdgeType.CYCLE));
		} else {
			programView.addEdge(new Edge(this, n, Edge.EdgeType.REGULAR));
		}
	}

	@Override
	public void accept(CheckExistingVarsVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public void evaluateState() {
		setStateRight(getIncoming().size() > 0 && getOutgoing().size() == 1);
	}

	@Override
	public String getInfoForDraw() {
		Object val = this.executionValue;
		if (isLiveExecution() && val != null) {
			if (getExpr() == null)
				return null;
			return getVar().getName().getId() + " = " + getExpr() + " : " + val;
		} else {
			if (getExpr() == null)
				return null;
			return getVar().getName().getId() + " = " + getExpr();
		}
	}

	public Object getExecutionValue() {
		return executionValue;
	}

	public void setExecutionValue(Object executionValue) {
		this.executionValue = executionValue;
	}

}
