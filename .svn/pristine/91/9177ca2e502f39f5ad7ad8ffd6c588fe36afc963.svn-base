package com.codebits.softwareninja.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class ProgramView {
	List<Node> nodes;
	List<Edge> edges;
	private BeginNode beginning;
	private EndNode ending;
	final static int SCREEN_NODE_RATIO = 6;
	private final Map<Class<?>, Integer> nodesLimit;
	private boolean showInfo;

	public void setLimit(Class clazz, int limit) {
		nodesLimit.put(clazz, limit);
	}

	public Map<Class<?>, Integer> getNodesLimit() {
		return nodesLimit;
	}

	public boolean checkLimit(Class clazz) {
		Integer i = nodesLimit.get(clazz);
		if (i != null) {
			return i > 0;
		}
		return true;
	}

	public void produceNodeType(Class<?> clazz) {
		Integer i = nodesLimit.get(clazz);
		if (i != null) {
			nodesLimit.put(clazz, i + 1);
		}
	}

	public void consumeNodeType(Class<?> clazz) {
		Integer i = nodesLimit.get(clazz);
		if (i != null) {
			nodesLimit.put(clazz, i - 1);
		}
	}

	public static int getScreenNodeRatio() {
		return SCREEN_NODE_RATIO;
	}

	private final int screenHeight, screenWidth;

	public int getScreenHeight() {
		return screenHeight;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	private final Context context;

	public Context getContext() {
		return context;
	}

	public Level level;

	public ProgramView(Context context, int screenHeight, int screenWidth, int levelN) {
		this.context = context;
		this.screenHeight = screenHeight;
		this.screenWidth = screenWidth;
		this.nodes = new ArrayList<Node>();
		int node_width = (screenWidth / SCREEN_NODE_RATIO);
		Node garbage = new GarbageNode(context, node_width / 2, node_width / 2, this);

		garbage.setNodeWidth(node_width);
		garbage.setNodeHeight(node_width);

		Node runNode = new RunNode(context, this.screenWidth - node_width / 2, node_width / 2, this);

		runNode.setNodeWidth(node_width);
		runNode.setNodeHeight(node_width);

		CountBarNode countBox = new CountBarNode(context, 0, this.screenHeight - node_width / 2, this);
		runNode.setNodeWidth(node_width);
		runNode.setNodeHeight(node_width);

		this.nodes.add(garbage);
		this.nodes.add(runNode);
		this.nodes.add(countBox);

		this.edges = new ArrayList<Edge>();

		level = LevelCreator.createLevel(levelN);

		nodesLimit = level.getNodeTypeLimits();
	}

	synchronized public void draw(Canvas canvas) {
		// draw bitmaps
		// draw Paths
		for (Node n : nodes) {
			n.draw(canvas);
		}
		for (Edge e : edges) {
			e.draw(canvas);
		}
	}

	synchronized public boolean handleTouchEvent(MotionEvent event) {
		// TODO check algorithm -> what happens when coordinates overlap 2
		// nodes?
		// it only makes sense for 1 node to be updated or be in touched state.
		// Check this
		Node n;
		for (int i = 0; i < nodes.size(); i++) {
			n = nodes.get(i);
			if (n.handleTouchEvent(event))
				break;
			// Log.d(this.getClass().getName(), n.toString() + "was touched");
		}
		return true;
	}

	public BeginNode getBeginningNode() {
		return beginning;
	}

	public void setBeginningNode(BeginNode beginning) {
		this.beginning = beginning;
	}

	public EndNode getEndingNode() {
		return ending;
	}

	public void setEndingNode(EndNode ending) {
		this.ending = ending;
	}

	public Node checkCollisions(Node holdedNode) {
		for (Node n : nodes) {
			if (n != holdedNode && holdedNode.checkCollision(n))
				return n;
		}
		return null;
	}

	public synchronized void addEdge(Edge edge) {
		edges.add(edge);
		edge.getSourceNode().evaluateState();
		edge.getSinkNode().evaluateState();
	}

	synchronized public void removeNode(Node n) {
		nodes.remove(n);
		produceNodeType(n.getClass());
		n.cleanEdges();
		if (n.equals(beginning))
			beginning = null;
		if (n.equals(ending))
			ending = null;
	}

	synchronized public void removeEdge(Edge e) {
		edges.remove(e);
		e.getSourceNode().evaluateState();
		e.getSinkNode().evaluateState();
	}

	public void addNode(Class<?> clazz) {
		// dont add node if it can not
		if (!checkLimit(clazz))
			return;
		consumeNodeType(clazz);

		Node n = null;
		if (clazz.equals(BeginNode.class)) {
			n = new BeginNode(context, screenHeight, screenWidth, this);
			beginning = (BeginNode) n;
		} else if (clazz.equals(EndNode.class)) {
			n = new EndNode(context, screenHeight, screenWidth, this);
			ending = (EndNode) n;
		} else if (clazz.equals(CycleNode.class)) {
			n = new CycleNode(context, screenHeight, screenWidth, this);
		} else if (clazz.equals(IfNode.class)) {
			n = new IfNode(context, screenHeight, screenWidth, this);
		} else if (clazz.equals(ExpNode.class)) {
			n = new ExpNode(context, screenHeight, screenWidth, this);
		} else if (clazz.equals(PrintNode.class)) {
			n = new PrintNode(context, screenHeight, screenWidth, this);
		}
		n.setPosX(screenWidth / 2);
		n.setPosY(screenHeight - screenHeight / 4);
		n.setNodeWidth(screenWidth / SCREEN_NODE_RATIO);
		n.setNodeHeight(screenWidth / SCREEN_NODE_RATIO);
		n.setShowInfo(showInfo);
		synchronized (this) {
			this.nodes.add(n);
		}
	}

	synchronized public void handleHardwareButtonEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP)
			showInfo = true;
		else if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)
			showInfo = false;
		for (Node n : nodes) {
			n.handleHardwareButtonEvent(event);
		}
	}
}
