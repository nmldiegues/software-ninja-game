package com.codebits.softwareninja.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.codebits.softwareninja.R;
import com.codebits.softwareninja.domain.type.Identifier;
import com.codebits.softwareninja.domain.type.LanguageType;
import com.codebits.softwareninja.domain.type.Parameter;
import com.codebits.softwareninja.interpretation.NodeVisitor;

public class BeginNode extends Node {

	private final List<Parameter> paramsModelList;
	private List<Object> executionValues;

	public BeginNode(Context context, int posX, int posY, ProgramView programView) {
		super(context, posX, posY, programView);
		setRightStateImg(BitmapFactory.decodeResource(getResources(), R.drawable.botao_start_green));
		setWrongStateImg(BitmapFactory.decodeResource(getResources(), R.drawable.botao_start_red));
		setStateRight(false);
		this.paramsModelList = new ArrayList<Parameter>();
	}

	public List<Parameter> getParamsModelList() {
		return paramsModelList;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub
	}

	@Override
	public void populateContextToolbox(final Activity activity, LinearLayout editLayout) {
		TextView toolboxTitle = (TextView) activity.findViewById(R.id.nodeName);
		toolboxTitle.setText("Begin");

		// Load the XML for the layout of this toolbox
		activity.getLayoutInflater().inflate(R.layout.toolbox_begin_node, editLayout);

		// Add the stuff related to inserting new parameters
		final LinearLayout addLayout = (LinearLayout) editLayout.findViewById(R.id.editNodeLayout);
		final Spinner newParameterChoice = (Spinner) editLayout.findViewById(R.id.newParameterChoice);

		CharSequence array[] = LanguageType.getTypesStr();
		ArrayAdapter<CharSequence> spinnerAdapter = new ArrayAdapter<CharSequence>(activity, android.R.layout.simple_spinner_item, array);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		newParameterChoice.setAdapter(spinnerAdapter);

		final EditText parameterName = (EditText) editLayout.findViewById(R.id.parameterName);
		final Button addBtn = (Button) editLayout.findViewById(R.id.addButton);

		// Add the stuff
		final ListView parametersList = (ListView) editLayout.findViewById(R.id.parametersList);

		final ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
		ParametersListAdapter adapter = new ParametersListAdapter(activity, listItem);

		for (Parameter param : paramsModelList) {
			HashMap<String, String> newParameter = new HashMap<String, String>();
			newParameter.put("type", param.getType().getDesc());
			newParameter.put("identifier", param.getName().getId());
			listItem.add(newParameter);
		}

		parametersList.setAdapter(adapter);

		addBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// save the new parameter in the model
				final String typeStr = (String) newParameterChoice.getSelectedItem();
				final String paramStr = parameterName.getText().toString();
				// TODO validate the typeStr to make sure it is a type and the
				// paramStr (using the Identifier method)
				// also make sure the param name is unique in this scope
				final Parameter newParamModel = new Parameter(new Identifier(paramStr), LanguageType.fromString(typeStr), true);
				/* for now assume always input param */
				BeginNode.this.paramsModelList.add(newParamModel);

				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// update the interface to have the new parameter in the
						// list
						HashMap<String, String> newParameter = new HashMap<String, String>();
						newParameter.put("type", typeStr);
						newParameter.put("identifier", paramStr);
						listItem.add(newParameter);
						ParametersListAdapter adapter = new ParametersListAdapter(activity, listItem);
						parametersList.setAdapter(adapter);

						parameterName.setText("");
					}
				});
			}
		});

	}

	private class ParametersListAdapter extends SimpleAdapter {

		private final List<? extends Map<String, ?>> data;

		ParametersListAdapter(Context context, List<? extends Map<String, ?>> data) {
			super(context, data, R.layout.parameter_item_list, new String[] { "type", "identifier" }, new int[] { R.id.type, R.id.identifier });
			this.data = data;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View row = super.getView(position, convertView, parent);
			Button deleteButton = (Button) row.findViewById(R.id.removeParameterBtn);

			deleteButton.setOnClickListener(new Button.OnClickListener() {
				@Override
				public void onClick(View v) {
					// Remove parameter from UI
					String removedParamName = ((Map<String, String>) data.remove(position)).get("identifier");
					ParametersListAdapter.this.notifyDataSetInvalidated();

					// Remove parameter from model
					List<Parameter> params = BeginNode.this.paramsModelList;
					for (Parameter param : params) {
						if (param.getName().equals(removedParamName)) {
							params.remove(param);
							break;
						}
					}
				}
			});

			return row;
		}
	}

	@Override
	public void accept(NodeVisitor visitor) {
		// Visit the begin node
		visitor.visit(this);
		Set<Edge> outgoing = getOutgoing();
		if (outgoing.size() != 1) {
			throw new RuntimeException("Begin node must have only one next node");
		}
		// Visit the rest of the program
		outgoing.iterator().next().getSinkNode().accept(visitor);
	}

	@Override
	public void accept(CheckExistingVarsVisitor visitor) {
		visitor.visit(this);
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
	public void evaluateState() {
		setStateRight(getIncoming().size() == 0 && getOutgoing().size() == 1);
	}

	@Override
	public void drawInfo(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.rgb(50, 50, 50));
		// random text size values
		int textSize = 18;
		paint.setTextSize(textSize);

		List<String> strings = new ArrayList<String>();
		int relPos = 0;
		int maxSize = Integer.MAX_VALUE;
		int iterValues = 0;
		for (Parameter p : paramsModelList) {
			String text = null;
			List<Object> values = this.executionValues;
			if (isLiveExecution() && values != null) {
				text = p.getType().getDesc() + " " + p.getName().getId() + " : " + values.get(iterValues);
				iterValues++;
			} else {
				text = p.getType().getDesc() + " " + p.getName().getId();
			}
			maxSize = text.length() * textSize;
			strings.add(text);
		}
		if (strings.size() == 0)
			return;

		Bitmap baloonToUse = null;
		if (this.isLiveExecution()) {
			baloonToUse = getLiveBaloon();
		} else {
			baloonToUse = getBaloonBitmap();
		}
		canvas.drawBitmap(baloonToUse, null, new Rect(getPosX() + 3 * getNodeWidth() / 4 - 20, getPosY() - (getNodeWidth() / 2) - 10, getPosX() + 3
				* getNodeWidth() / 4 + maxSize + 10, getPosY() - (getNodeWidth() / 2) + strings.size() * textSize + 20), null);

		for (String s : strings) {
			canvas.drawText(s, getPosX() + 3 * getNodeWidth() / 4, getPosY() - (getNodeWidth() / 2) + textSize + relPos, paint);
			relPos += textSize + 4;
		}

	}

	@Override
	public String getInfoForDraw() {
		return null;
	}

	public List<Object> getExecutionValues() {
		return executionValues;
	}

	public void setExecutionValues(List<Object> executionValues) {
		this.executionValues = executionValues;
	}
}
