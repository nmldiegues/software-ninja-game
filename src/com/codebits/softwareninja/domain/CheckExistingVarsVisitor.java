package com.codebits.softwareninja.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.util.Log;

import com.codebits.softwareninja.domain.type.Parameter;
import com.codebits.softwareninja.domain.type.Variable;

public class CheckExistingVarsVisitor {

	Set<Variable> list = new HashSet<Variable>();
	Set<Node> alreadyVisited = new HashSet<Node>();

	public void visit(Node node) {
		if (alreadyVisited.contains(node)) {
			return;
		} else {
			alreadyVisited.add(node);
		}
		Log.d("test", "class " + node.getClass().getName());
		for (Edge e : node.getIncoming()) {
			e.getSourceNode().accept(this);
		}
	}

	public void visit(ExpNode node) {
		Variable v = node.getVar();
		if (v != null)
			list.add(v);
		visit((Node) node);

	}

	public void visit(BeginNode node) {

		for (Parameter p : node.getParamsModelList())
			list.add(p);
		visit((Node) node);

	}

	public List<String> getVarNames() {
		List<String> names = new ArrayList<String>();

		for (Variable v : list)
			names.add(v.getName().getId());

		return names;
	}

	public Variable getVariable(String parameterName) {
		for (Variable v : list)
			if (v.getName().getId().equals(parameterName))
				return v;
		return null;
	}
}
