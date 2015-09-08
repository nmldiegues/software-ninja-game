package com.codebits.softwareninja.domain;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.codebits.softwareninja.R;
import com.codebits.softwareninja.domain.type.LanguageType;
import com.codebits.softwareninja.domain.type.Variable;
import com.codebits.softwareninja.interpretation.NodeVisitor;

public class EndNode extends Node {

	private Variable returnVar;
	private Object executionValue;

	public EndNode(Context context, int posX, int posY, ProgramView programView) {
		super(context, posX, posY, programView);
		// TODO Auto-generated constructor stub
		setRightStateImg(BitmapFactory.decodeResource(getResources(), R.drawable.botao_cancel_green));
		setWrongStateImg(BitmapFactory.decodeResource(getResources(), R.drawable.botao_cancel_red));
		setStateRight(false);
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

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
	}

	public LanguageType getReturnLanguageType() {
		return this.returnVar.getType();
	}

	@Override
	public void populateContextToolbox(final Activity activity, LinearLayout editLayout) {

		// TODO warn user if no var declared
		TextView toolboxTitle = (TextView) activity.findViewById(R.id.nodeName);
		toolboxTitle.setText("End");

		// Add the stuff related to inserting new parameters
		LinearLayout addLayout = new LinearLayout(activity);
		editLayout.addView(addLayout);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 75);
		params.setMargins(5, 10, 5, 10);
		addLayout.setLayoutParams(params);
		addLayout.setGravity(Gravity.CENTER);

		final Spinner newParameterChoice = new Spinner(activity);
		addLayout.addView(newParameterChoice);

		final CheckExistingVarsVisitor checker = new CheckExistingVarsVisitor();
		checker.visit(this);
		ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(activity, android.R.layout.simple_spinner_item, checker.getVarNames()
				.toArray(new String[] {}));
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		newParameterChoice.setAdapter(spinnerAdapter);
		int position = 0;
		if (returnVar != null)
			position = spinnerAdapter.getPosition(returnVar.getName().getId().toString());
		newParameterChoice.setSelection(position);

		// Add the stuff
		final ListView parametersList = new ListView(activity);
		editLayout.addView(parametersList);
		LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		parametersList.setLayoutParams(linearParams);

		final ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		SimpleAdapter adapter = new SimpleAdapter(activity, listItem, R.layout.parameter_item_list, new String[] { "type", "identifier" }, new int[] {
				R.id.type, R.id.identifier });
		parametersList.setAdapter(adapter);

		newParameterChoice.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				final String parameterName = arg0.getItemAtPosition(arg2).toString();
				EndNode.this.setReturnVar(checker.getVariable(parameterName));

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	public Variable getReturnVar() {
		return returnVar;
	}

	public void setReturnVar(Variable returnVar) {
		this.returnVar = returnVar;
	}

	@Override
	public void evaluateState() {
		setStateRight(getIncoming().size() > 0 && getOutgoing().size() == 0);
	}

	@Override
	public String getInfoForDraw() {
		Object val = this.executionValue;
		if (this.isLiveExecution() && val != null) {
			if (getReturnVar() == null)
				return null;
			return getReturnVar().getType().getDesc() + " " + getReturnVar().getName().getId() + " : " + val;
		} else {
			if (getReturnVar() == null)
				return null;
			return getReturnVar().getType().getDesc() + " " + getReturnVar().getName().getId();
		}
	}

	public Object getExecutionValue() {
		return executionValue;
	}

	public void setExecutionValue(Object executionValue) {
		this.executionValue = executionValue;
	}

}
