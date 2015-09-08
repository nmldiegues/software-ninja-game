package com.codebits.softwareninja.domain;

import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.codebits.softwareninja.R;
import com.codebits.softwareninja.domain.Edge.EdgeType;
import com.codebits.softwareninja.interpretation.NodeVisitor;
import com.codebits.softwareninja.interpretation.operations.Parser;

public class IfNode extends Node {

	private static final int DIALOG_ALERT = 10;

	private String condition;
	private Boolean executionValue;

	public IfNode(Context context, int posX, int posY, ProgramView programView) {
		super(context, posX, posY, programView);
		// TODO Auto-generated constructor stub
		setRightStateImg(BitmapFactory.decodeResource(getResources(), R.drawable.botao_if_green));
		setWrongStateImg(BitmapFactory.decodeResource(getResources(), R.drawable.botao_if_red));
		setStateRight(false);
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void populateContextToolbox(Activity activity, LinearLayout editLayout) {
		TextView toolboxTitle = (TextView) activity.findViewById(R.id.nodeName);
		toolboxTitle.setText("Condition");

		// Load the XML for the layout of this toolbox
		activity.getLayoutInflater().inflate(R.layout.toolbox_if_node, editLayout);

		// Filter characters on the expression field and set the keyboard
		// correctly
		final EditText inputExpr = (EditText) editLayout.findViewById(R.id.inputCondition);
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
		// !isConditionSymbol(source.charAt(i))) {
		// return "";
		// }
		// }
		// return null;
		// }
		// };
		// inputExpr.setFilters(new InputFilter[] { filter });

		// Save condition to the model
		inputExpr.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				IfNode.this.condition = inputExpr.getText().toString();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
		});

		// If a condition already exists in the model, restore it in the UI
		if (this.condition != null) {
			inputExpr.setText(this.condition);
		}

		// Populate the existing variables to be used in the expression
		final Spinner existingVariablesChoice = (Spinner) editLayout.findViewById(R.id.chooseVariables);
		final CheckExistingVarsVisitor checker = new CheckExistingVarsVisitor();
		checker.visit(this);
		ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(activity, android.R.layout.simple_spinner_item, checker.getVarNames()
				.toArray(new String[] {}));
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		existingVariablesChoice.setAdapter(spinnerAdapter);

		// When a variable is picked, add it to the expression
		final Button addExistingVariableBtn = (Button) editLayout.findViewById(R.id.addExistingVariableToCond);
		addExistingVariableBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String parameterName = ((CharSequence) existingVariablesChoice.getSelectedItem()).toString();
				inputExpr.setText(inputExpr.getText().toString() + " " + parameterName);
				inputExpr.setSelection(inputExpr.getText().length());
			}
		});
	}

	private boolean isConditionSymbol(char c) {
		return Parser.conditionOperations.contains("" + c);
	}

	@Override
	public void handleNodeCollision(final Node n) {
		// TODO Auto-generated method stub

		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setCancelable(false);
		builder.setMessage("Which arc do you want to choose?");
		builder.setPositiveButton("TRUE", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				programView.addEdge(new Edge(IfNode.this, n, Edge.EdgeType.IF_TRUE));
			}
		});
		builder.setNegativeButton("FALSE", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				programView.addEdge(new Edge(IfNode.this, n, Edge.EdgeType.IF_FALSE));

			}
		});
		/*
		 * builder.setNeutralButton("Intru��o ap�s IF", new
		 * DialogInterface.OnClickListener() {
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) {
		 * programView.addEdge(new Edge(IfNode.this, n, Edge.EdgeType.REGULAR));
		 * 
		 * } });
		 */
		builder.show();

	}

	@Override
	public void accept(NodeVisitor visitor) {
		Set<Edge> outgoing = getOutgoing();
		if (outgoing.size() < 1 || outgoing.size() > 2) {
			throw new RuntimeException("Inconsistent number of flows for If node: " + outgoing.size());
		}
		Iterator<Edge> iterator = outgoing.iterator();
		Edge tmp = iterator.next();
		Edge trueJump;
		Edge falseJump;
		if (tmp.getEdgeType().equals(EdgeType.IF_TRUE)) {
			trueJump = tmp;
			falseJump = iterator.next();
		} else {
			falseJump = tmp;
			trueJump = iterator.next();
		}
		boolean condition = visitor.visit(this);
		if (condition) {
			trueJump.getSinkNode().accept(visitor);
		} else {
			falseJump.getSinkNode().accept(visitor);
		}
	}

	@Override
	public void evaluateState() {
		boolean trueEdge = false;
		boolean falseEdge = false;
		if (getIncoming().size() > 0 && getOutgoing().size() == 2) {
			synchronized (programView) {
				for (Edge e : getOutgoing()) {
					if (e.getEdgeType().equals(EdgeType.IF_TRUE))
						trueEdge = true;
					else if (e.getEdgeType().equals(EdgeType.IF_FALSE))
						falseEdge = true;
				}
			}
			setStateRight(trueEdge && falseEdge);
		} else {
			setStateRight(false);
		}
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public String getInfoForDraw() {
		Object val = this.executionValue;
		if (isLiveExecution() && val != null) {
			if (getCondition() == null)
				return null;
			return getCondition() + " : " + val;
		} else {
			if (getCondition() == null)
				return null;
			return getCondition() + " : ?";
		}
	}

	public Boolean getExecutionValue() {
		return executionValue;
	}

	public void setExecutionValue(Boolean executionValue) {
		this.executionValue = executionValue;
	}

}
