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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.codebits.softwareninja.R;
import com.codebits.softwareninja.domain.type.Variable;
import com.codebits.softwareninja.interpretation.NodeVisitor;

public class PrintNode extends Node {

	private String text;
	private Variable varToPrint;
	private Object executionValue;

	public PrintNode(Context context, int posX, int posY, ProgramView programView) {
		super(context, posX, posY, programView);
		// TODO Auto-generated constructor stub
		setRightStateImg(BitmapFactory.decodeResource(getResources(), R.drawable.botao_print_green));
		setWrongStateImg(BitmapFactory.decodeResource(getResources(), R.drawable.botao_print_red));
		setStateRight(false);
		setText("");
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void populateContextToolbox(Activity activity, LinearLayout editLayout) {
		TextView toolboxTitle = (TextView) activity.findViewById(R.id.nodeName);
		toolboxTitle.setText("Print");

		// Load the XML for the layout of this toolbox
		activity.getLayoutInflater().inflate(R.layout.toolbox_print_node, editLayout);

		final Spinner chooseVariableToPrint = (Spinner) editLayout.findViewById(R.id.chooseVariableToPrint);
		final CheckExistingVarsVisitor checker = new CheckExistingVarsVisitor();
		checker.visit(this);
		// List<String> varList = checker.getVarNames();
		// String[] arr = new String[varList.size() + 1];
		// arr[0] = "Nothing";
		// arr = varList.toArray(arr);
		ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(activity, android.R.layout.simple_spinner_item, checker.getVarNames()
				.toArray(new String[] {}));
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		chooseVariableToPrint.setAdapter(spinnerAdapter);

		final EditText stringToPrint = (EditText) editLayout.findViewById(R.id.stringToPrint);
		stringToPrint.setText(getText());

		chooseVariableToPrint.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				final String parameterName = arg0.getItemAtPosition(arg2).toString();
				if (parameterName != "Nothing") {
					PrintNode.this.setVarToPrint(checker.getVariable(parameterName));
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});
		stringToPrint.addTextChangedListener(new TextWatcher() {
			@Override
			public void afterTextChanged(Editable arg0) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				PrintNode.this.setText(arg0.toString());
			}
		});
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
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
		Set<Edge> outgoing = this.getOutgoing();
		if (outgoing.size() != 1) {
			throw new RuntimeException("Print node must have only one next node");
		}
		outgoing.iterator().next().getSinkNode().accept(visitor);
	}

	@Override
	public void evaluateState() {
		setStateRight(getIncoming().size() > 0 && getOutgoing().size() == 1);
	}

	public void setVarToPrint(Variable varToPrint) {
		this.varToPrint = varToPrint;
	}

	public Variable getVarToPrint() {
		if (varToPrint == null || varToPrint.getName().getId().equals("")) {
			this.varToPrint = null;
			return null;
		}
		return varToPrint;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getInfoForDraw() {
		Object val = this.executionValue;
		if (isLiveExecution() && val != null) {
			String text = new String();
			if (!getText().equals(""))
				text += getText();
			if (getVarToPrint() != null)
				text += getVarToPrint().getName().getId() + " : " + val;

			return text;
		} else {
			String text = new String();
			if (!getText().equals(""))
				text += getText();
			if (getVarToPrint() != null)
				text += getVarToPrint().getName().getId();

			return text;
		}
	}

	public Object getExecutionValue() {
		return executionValue;
	}

	public void setExecutionValue(Object executionValue) {
		this.executionValue = executionValue;
	}

}
