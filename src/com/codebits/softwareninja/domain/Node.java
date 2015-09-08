package com.codebits.softwareninja.domain;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.codebits.softwareninja.R;
import com.codebits.softwareninja.domain.Edge.Direction;
import com.codebits.softwareninja.interpretation.NodeVisitor;

public abstract class Node extends ImageButton {

	private final Set<Edge> incoming;
	private final Set<Edge> outgoing;
	private int posX, posY, nodeWidth, nodeHeight, oldPosX, oldPosY;
	private Bitmap wrongStateImg, rightStateImg;
	private final Bitmap baloon;
	private final Bitmap liveBaloon;
	private boolean isStateRight; // if state is right or wrong
	private boolean isTouched;
	private boolean isMoving;
	private long timestampIntervalTouch;
	protected final ProgramView programView;
	private boolean showInfo;
	private boolean liveExecution;

	public boolean isLiveExecution() {
		return liveExecution;
	}

	public void setLiveExecution(boolean liveExecution) {
		this.liveExecution = liveExecution;
	}

	public void setShowInfo(boolean showInfo) {
		this.showInfo = showInfo;
	}

	public boolean isShowInfo() {
		return showInfo;
	}

	public Node(Context context, int posX, int posY, ProgramView programView) {
		super(context);
		setPosX(posX);
		setPosY(posY);
		setTouched(false);
		setMoving(false);
		setTimestampIntervalTouch(0L);
		incoming = new HashSet<Edge>();
		outgoing = new HashSet<Edge>();
		this.programView = programView;
		baloon = BitmapFactory.decodeResource(getResources(), R.drawable.greyballon);
		liveBaloon = BitmapFactory.decodeResource(getResources(), R.drawable.greenballon);
	}

	protected Bitmap getBaloonBitmap() {
		return baloon;
	}

	public static enum Corner {
		LeftUp, LeftDown, RightUp, RightDown
	}

	public abstract void handleNodeCollision(Node n);

	public abstract void evaluateState();

	public abstract String getInfoForDraw();

	/* Note that it is overriden in the begin node! */
	public void drawInfo(Canvas canvas) {
		String text = getInfoForDraw();
		if (text == null)
			return;
		Paint paint = new Paint();
		paint.setColor(Color.rgb(50, 50, 50));
		// random text size values
		int textSize = 18;
		paint.setTextSize(textSize);

		Bitmap baloonToUse = null;
		if (liveExecution) {
			baloonToUse = getLiveBaloon();
		} else {
			baloonToUse = getBaloonBitmap();
		}

		canvas.drawBitmap(baloonToUse, null, new Rect(getPosX() + 3 * getNodeWidth() / 4 - 30, getPosY() - textSize - 10, getPosX() + 3 * getNodeWidth() / 4
				+ text.length() * textSize - 25, getPosY() + 20), null);

		canvas.drawText(text, getPosX() + 3 * getNodeWidth() / 4, getPosY(), paint);
	}

	@Override
	public void draw(Canvas canvas) {
		if (isTouched()) {
			Paint paint = new Paint();
			paint.setAlpha(50);
			canvas.drawBitmap(getImageToDraw(), null, new Rect(getOldPosX() - getNodeWidth() / 2, getOldPosY() - getNodeHeight() / 2, getOldPosX()
					+ getNodeWidth() / 2, getOldPosY() + getNodeHeight() / 2), paint);
		}
		if (isShowInfo()) {
			drawInfo(canvas);
		}

		canvas.drawBitmap(getImageToDraw(), null, new Rect(getPosX() - getNodeWidth() / 2, getPosY() - getNodeHeight() / 2, getPosX() + getNodeWidth() / 2,
				getPosY() + getNodeHeight() / 2), null);
	}

	// task to be done when node is removed (e.g. removing arcs)
	public abstract void remove();

	public void addIncomingArc(Edge arc) {
		incoming.add(arc);
	}

	public void addOutgoingArc(Edge arc) {
		outgoing.add(arc);
	}

	public void removeIncomingArc(Edge arc) {
		incoming.remove(arc);
	}

	public void removeOutgoingArc(Edge arc) {
		outgoing.remove(arc);
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public abstract void populateContextToolbox(Activity activity, LinearLayout editLayout);

	public boolean isStateRight() {
		return isStateRight;
	}

	public void setStateRight(boolean isStateRight) {
		this.isStateRight = isStateRight;
	}

	public Bitmap getImageToDraw() {
		return isStateRight() ? getRightStateImg() : getWrongStateImg();
	}

	public Bitmap getWrongStateImg() {
		return wrongStateImg;
	}

	public void setWrongStateImg(Bitmap wrongState) {
		this.wrongStateImg = wrongState;
	}

	public Bitmap getRightStateImg() {
		return rightStateImg;
	}

	public void setRightStateImg(Bitmap rightState) {
		this.rightStateImg = rightState;
	}

	public Point getSlotPoint(Direction edgeDirection) {
		switch (edgeDirection) {
		case EAST:
			return new Point(posX + nodeWidth / 2, posY);
		case NORTH:
			return new Point(posX, posY - nodeHeight / 2);
		case SOUTH:
			return new Point(posX, posY + nodeHeight / 2);
		case WEST:
			return new Point(posX - nodeWidth / 2, posY);
		default:
			return null;
		}
	}

	public Point[] getSlotPoints() {
		// NORTH,SOUTH,WEST,EAST
		return new Point[] { new Point(posX, posY - nodeHeight / 2), new Point(posX, posY + nodeHeight / 2), new Point(posX - nodeWidth / 2, posY),
				new Point(posX + nodeWidth / 2, posY) };
	}

	public int getNodeWidth() {
		return nodeWidth;
	}

	public void setNodeWidth(int nodeWidth) {
		this.nodeWidth = nodeWidth;
	}

	public int getNodeHeight() {
		return nodeHeight;
	}

	public void setNodeHeight(int nodeHeight) {
		this.nodeHeight = nodeHeight;
	}

	public void setTouched(boolean touched) {
		this.isTouched = touched;
	}

	public boolean isTouched() {
		return this.isTouched;
	}

	protected Point getCorner(Corner corner) {
		switch (corner) {
		case LeftUp:
			return new Point(posX - nodeWidth / 2, posY - nodeHeight / 2);
		case LeftDown:
			return new Point(posX - nodeWidth / 2, posY - nodeHeight / 2);
		case RightUp:
			return new Point(posX - nodeWidth / 2, posY + nodeHeight / 2);
		case RightDown:
			return new Point(posX + nodeWidth / 2, posY + nodeHeight / 2);
		default:
			return new Point(posX, posY);
		}
	}

	protected boolean checkCollision(Node n) {
		// works for 2 circles with the same radius, good enough for now?
		if (checkBoxCollision(n)) {
			boolean result = Math.sqrt(Math.pow((getPosX() - n.getPosX()), 2) + Math.pow((getPosY() - n.getPosY()), 2)) <= getNodeHeight();
			Log.d(this.getClass().getName(), "x1 " + getPosX() + " y1 " + getPosY() + " x2 " + n.getPosX() + " y2 " + n.getPosY() + " h" + getNodeHeight());
			Log.d(this.getClass().getName(), "real collision result: " + result);
			return result;
		} else
			return false;
	}

	protected boolean checkBoxCollision(Node n) {
		if (checkBoxCollision(n.getCorner(Corner.LeftUp)) || checkBoxCollision(n.getCorner(Corner.LeftDown)) || checkBoxCollision(n.getCorner(Corner.RightUp))
				|| checkBoxCollision(n.getCorner(Corner.RightDown))) {

			return true;
		}
		return false;
	}

	protected boolean checkBoxCollision(Point p) {
		return checkBoxCollision(p.x, p.y);
	}

	protected boolean checkBoxCollision(int x, int y) {
		return x >= getPosX() - getNodeWidth() / 2 && x <= getPosX() + getNodeWidth() / 2 && y >= getPosY() - getNodeHeight() / 2
				&& y <= getPosY() + getNodeHeight() / 2;
	}

	// updates position being the given coordinates the coordinates of the
	// center
	// this update only takes place if the change is "significant"
	protected boolean updateCenterCoords(int x, int y) {
		int previousX = getPosX();
		int previousY = getPosY();
		int newX = x - getWidth() / 2;
		int newY = y - getHeight() / 2;
		int diffX = Math.abs(newX - previousX);
		int diffY = Math.abs(newY - previousY);
		if ((diffX + diffY) > 20) {
			setPosX(newX);
			setPosY(newY);
			return true;
		}
		return false;
	}

	synchronized private void updateEdges() {
		for (Edge e : incoming)
			e.refreshPath();
		for (Edge e : outgoing) {
			e.refreshPath();
		}
	}

	// returns true if it was the one who had to handle the event
	public boolean handleTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		if (this.isTouched()) {
			if (event.getAction() == MotionEvent.ACTION_MOVE && updateCenterCoords(x, y)) {
				// Log.d(this.getClass().getName(), "moving");
				this.setMoving(true);
				updateEdges();

			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				if (!this.isMoving()) {
					// Did not move, so assume it is to open the toolbox
					populateToolbox();
				} else {
					Node collisionNode = programView.checkCollisions(this);
					if (collisionNode != null) {
						// source node is the one who knows how to handle node
						// insertion
						collisionNode.handleNodeCollision(this);
						setPosX(getOldPosX());
						setPosY(getOldPosY());
						updateEdges();
					}

				}
				this.setTouched(false);
				this.setMoving(false);
				// Log.d(this.getClass().getName(), "untouched");
			}
			return true;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN && checkBoxCollision(x, y)) {
			// Log.d(this.getClass().getName(), "touched " + event.getAction());
			// Record the time of this press for opening the toolbox
			this.setTimestampIntervalTouch(System.currentTimeMillis());
			this.setTouched(true);
			setOldPosX(getPosX());
			setOldPosY(getPosY());
			return true;
		}
		return false;
	}

	public synchronized boolean findForwardNode(Node n) {
		boolean ret = false;
		for (Edge e : outgoing) {
			if (e.getSinkNode().equals(n)) {
				return true;
			}
			ret = e.getSinkNode().findForwardNode(n);
			if (ret)
				return true;
		}
		return false;
	}

	public int getOldPosX() {
		return oldPosX;
	}

	public void setOldPosX(int oldPosX) {
		this.oldPosX = oldPosX;
	}

	public int getOldPosY() {
		return oldPosY;
	}

	public void setOldPosY(int oldPosY) {
		this.oldPosY = oldPosY;
	}

	public long getTimestampIntervalTouch() {
		return timestampIntervalTouch;
	}

	public void setTimestampIntervalTouch(long timestampIntervalTouch) {
		this.timestampIntervalTouch = timestampIntervalTouch;
	}

	/*
	 * Changing the visibility of a layout on top of a surface is problematic:
	 * http
	 * ://stackoverflow.com/questions/8891262/error-when-refreshing-view-on-top
	 * -of-surfaceview
	 */
	private void populateToolbox() {
		final Activity activityContext = ((Activity) getContext());
		final SurfaceView surfaceView = (SurfaceView) activityContext.findViewById(R.id.SurfaceView01);
		final LinearLayout editLayout = (LinearLayout) activityContext.findViewById(R.id.editNodeLayout);
		final LinearLayout toolboxLayout = (LinearLayout) activityContext.findViewById(R.id.ContextToolboxView);

		toolboxLayout.setBackgroundColor(Color.argb(245, 50, 50, 50));
		/*
		 * toolboxLayout.setPadding(programView.getScreenWidth() /
		 * ProgramView.getScreenNodeRatio(), programView.getScreenWidth() /
		 * ProgramView.getScreenNodeRatio(), programView.getScreenWidth() -
		 * programView.getScreenWidth() / ProgramView.getScreenNodeRatio(),
		 * programView.getScreenHeight() - programView.getScreenWidth() /
		 * ProgramView.getScreenNodeRatio());
		 */

		// make the toolbox visible and set the needed contents
		activityContext.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				toolboxLayout.setVisibility(View.VISIBLE);
				populateContextToolbox(activityContext, editLayout);
				toolboxLayout.getParent().requestTransparentRegion(surfaceView);
			}
		});
		final ImageButton closeButton = (ImageButton) activityContext.findViewById(R.id.closeToolboxBtn);
		closeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editLayout.removeAllViews();
				toolboxLayout.setVisibility(View.INVISIBLE);
			}
		});
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public Set<Edge> getIncoming() {
		return incoming;
	}

	public Set<Edge> getOutgoing() {
		return outgoing;
	}

	public abstract void accept(NodeVisitor visitor);

	public void accept(CheckExistingVarsVisitor visitor) {
		visitor.visit(this);
	}

	synchronized public void cleanEdges() {
		// TODO Auto-generated method stub
		Set<Edge> tempIncoming = new HashSet<Edge>();
		tempIncoming.addAll(incoming);
		Set<Edge> tempOutgoing = new HashSet<Edge>();
		tempIncoming.addAll(outgoing);

		for (Edge e : tempIncoming) {
			e.deleteEdge();
			programView.removeEdge(e);
		}
		for (Edge e : tempOutgoing) {
			e.deleteEdge();
			programView.removeEdge(e);
		}

	}

	public void handleHardwareButtonEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_UP)
			setShowInfo(true);
		else if (event.getKeyCode() == KeyEvent.KEYCODE_VOLUME_DOWN)
			setShowInfo(false);

	}

	public Bitmap getLiveBaloon() {
		return baloon/* liveBaloon */;
	}
}
