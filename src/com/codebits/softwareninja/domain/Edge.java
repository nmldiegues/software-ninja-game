package com.codebits.softwareninja.domain;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.Log;
import android.view.MotionEvent;

public class Edge {

	public static enum EdgeType {
		IF_TRUE, IF_FALSE, CYCLE, CYCLE_FALSE, CYCLE_TRUE, REGULAR
	}

	public static enum Direction {
		NORTH, SOUTH, EAST, WEST
	}

	public static int ARROW_WIDTH = 12;

	private Node source, sink;
	private EdgeType edgeType;
	private final Path path;
	private Region region;
	private Direction edgeDirection;
	private boolean touched;
	private long timestampIntervalTouch;

	public Edge(Node source, Node sink, EdgeType type) {
		setSourceNode(source);
		setSinkNode(sink);
		// TODO check this stuff
		path = new Path();
		edgeType = type;
		source.addOutgoingArc(this);
		sink.addIncomingArc(this);
		refreshPath();
	}

	public void spliceEdge(Node node) {
		sink.removeIncomingArc(this);
		sink = node;
		node.addIncomingArc(this);
		new Edge(node, sink, EdgeType.REGULAR);
	}

	public Node getSourceNode() {
		return source;
	}

	public void setSourceNode(Node source) {
		this.source = source;
	}

	public Node getSinkNode() {
		return sink;
	}

	public void setSinkNode(Node sink) {
		this.sink = sink;
	}

	public void draw(Canvas canvas) {
		canvas.drawPath(path, getEdgeTypePaint());
		canvas.drawPath(getArrow(sink.getSlotPoint(edgeDirection), ARROW_WIDTH, edgeDirection), getEdgeTypeArrowPaint());
	}

	public void refreshPath() {
		Object[] ret = calulateEdgeDirections();
		List<Direction> edgeDirections = (List<Direction>) ret[0];
		Boolean specialCase = (Boolean) ret[1];
		Point sourcePoint = source.getSlotPoint(edgeDirections.get(0));
		Point sinkPoint = sink.getSlotPoint(edgeDirections.get(1));

		path.reset();
		// The pain
		if (!specialCase) {
			// |-
			if (edgeDirections.get(0) == Direction.WEST && edgeDirections.get(1) == Direction.NORTH) {
				path.moveTo(sinkPoint.x, sinkPoint.y);
				path.arcTo(new RectF(sinkPoint.x, sourcePoint.y, sourcePoint.x, sinkPoint.y), 180, 90);
				path.lineTo(sourcePoint.x, sourcePoint.y);

			} else if (edgeDirections.get(1) == Direction.WEST && edgeDirections.get(0) == Direction.NORTH) {
				path.moveTo(sourcePoint.x, sourcePoint.y);
				path.arcTo(new RectF(sourcePoint.x, sinkPoint.y, sinkPoint.x, sourcePoint.y), 180, 90);
				path.lineTo(sinkPoint.x, sinkPoint.y);

			}// |_
			else if (edgeDirections.get(0) == Direction.WEST && edgeDirections.get(1) == Direction.SOUTH) {
				path.moveTo(sourcePoint.x, sourcePoint.y);
				path.arcTo(new RectF(sinkPoint.x, sinkPoint.y, sourcePoint.x, sourcePoint.y), 90, 90);
				path.lineTo(sinkPoint.x, sinkPoint.y);

			} else if (edgeDirections.get(1) == Direction.WEST && edgeDirections.get(0) == Direction.SOUTH) {
				path.moveTo(sinkPoint.x, sinkPoint.y);
				path.arcTo(new RectF(sourcePoint.x, sourcePoint.y, sinkPoint.x, sinkPoint.y), 90, 90);
				path.lineTo(sourcePoint.x, sourcePoint.y);

			}// -|
			else if (edgeDirections.get(0) == Direction.EAST && edgeDirections.get(1) == Direction.NORTH) {
				path.moveTo(sourcePoint.x, sourcePoint.y);
				path.arcTo(new RectF(sourcePoint.x, sourcePoint.y, sinkPoint.x, sinkPoint.y), 270, 90);
				path.lineTo(sinkPoint.x, sinkPoint.y);

			} else if (edgeDirections.get(1) == Direction.EAST && edgeDirections.get(0) == Direction.NORTH) {
				path.moveTo(sinkPoint.x, sinkPoint.y);
				path.arcTo(new RectF(sinkPoint.x, sinkPoint.y, sourcePoint.x, sourcePoint.y), 270, 90);
				path.lineTo(sourcePoint.x, sourcePoint.y);

			}// _|
			else if (edgeDirections.get(0) == Direction.EAST && edgeDirections.get(1) == Direction.SOUTH) {
				path.moveTo(sinkPoint.x, sinkPoint.y);
				path.arcTo(new RectF(sourcePoint.x, sinkPoint.y, sinkPoint.x, sourcePoint.y), 0, 90);
				path.lineTo(sourcePoint.x, sourcePoint.y);

			} else if (edgeDirections.get(1) == Direction.EAST && edgeDirections.get(0) == Direction.SOUTH) {
				path.moveTo(sourcePoint.x, sourcePoint.y);
				path.arcTo(new RectF(sinkPoint.x, sourcePoint.y, sourcePoint.x, sinkPoint.y), 0, 90);
				path.lineTo(sinkPoint.x, sinkPoint.y);

			}
		} else {
			path.moveTo(sourcePoint.x, sourcePoint.y);
			path.lineTo(sinkPoint.x, sinkPoint.y);
		}

		RectF bounds = new RectF();
		path.computeBounds(bounds, true);
		region = new Region();
		region.setPath(path, new Region((int) bounds.left, (int) bounds.top, (int) bounds.right, (int) bounds.bottom));
		edgeDirection = edgeDirections.get(1);
	}

	private Object[] calulateEdgeDirections() {
		List<Direction> dir = new ArrayList<Direction>();
		Boolean specialCase = false;
		Point src = new Point(source.getPosX(), source.getPosY());
		Point sk = new Point(sink.getPosX(), sink.getPosY());
		Point[] srcSlotPoints = source.getSlotPoints(); // NORTH,SOUTH,WEST,EAST
		Point[] skSlotPoints = sink.getSlotPoints(); // NORTH,SOUTH,WEST,EAST

		if (src.y < skSlotPoints[0].y) {
			if (skSlotPoints[2].x > srcSlotPoints[3].x) {
				dir.add(Direction.EAST);
				dir.add(Direction.NORTH);
			} else if (skSlotPoints[3].x < srcSlotPoints[2].x) {
				dir.add(Direction.WEST);
				dir.add(Direction.NORTH);
			} else {
				specialCase = true;
				dir.add(Direction.SOUTH);
				dir.add(Direction.NORTH);
			}
		} else if (src.y > skSlotPoints[1].y) {
			if (skSlotPoints[2].x > srcSlotPoints[3].x) {
				dir.add(Direction.EAST);
				dir.add(Direction.SOUTH);
			} else if (skSlotPoints[3].x < srcSlotPoints[2].x) {
				dir.add(Direction.WEST);
				dir.add(Direction.SOUTH);
			} else {
				specialCase = true;
				dir.add(Direction.NORTH);
				dir.add(Direction.SOUTH);
			}
		} else {
			specialCase = true;
			if (src.x > sk.x) {
				dir.add(Direction.WEST);
				dir.add(Direction.EAST);
			} else {
				dir.add(Direction.EAST);
				dir.add(Direction.WEST);
			}
		}

		return new Object[] { dir, specialCase };

	}

	// p1 is the tip of the arrow
	private Path getArrow(Point p1, int width, Direction direction) {
		Point p2 = null, p3 = null;

		if (direction == Direction.NORTH) {
			p2 = new Point(p1.x - (width / 2), p1.y - width);
			p3 = new Point(p1.x + (width / 2), p1.y - width);
		} else if (direction == Direction.SOUTH) {
			p2 = new Point(p1.x - (width / 2), p1.y + width);
			p3 = new Point(p1.x + (width / 2), p1.y + width);
		} else if (direction == Direction.EAST) {
			p2 = new Point(p1.x + width, p1.y - (width / 2));
			p3 = new Point(p1.x + width, p1.y + (width / 2));
		} else if (direction == Direction.WEST) {
			p2 = new Point(p1.x - width, p1.y + (width / 2));
			p3 = new Point(p1.x - width, p1.y - (width / 2));
		}

		Path path = new Path();
		path.moveTo(p1.x, p1.y);
		path.lineTo(p2.x, p2.y);
		path.lineTo(p3.x, p3.y);

		return path;
	}

	private Paint getEdgeTypePaint() {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(2);
		switch (edgeType) {
		case IF_FALSE:
			paint.setColor(Color.rgb(178, 34, 34));
			return paint;
		case IF_TRUE:
			paint.setColor(Color.rgb(0, 201, 87));
			return paint;
		case REGULAR:
			paint.setColor(Color.WHITE);
			return paint;
		case CYCLE:
			paint.setColor(Color.rgb(100, 149, 237));
			return paint;
		case CYCLE_TRUE:
			paint.setColor(Color.rgb(0, 201, 87));
			return paint;
		case CYCLE_FALSE:
			paint.setColor(Color.rgb(178, 34, 34));
			return paint;
		default:
			paint.setColor(Color.WHITE);
			return paint;
		}
	}

	private Paint getEdgeTypeArrowPaint() {
		Paint paint = getEdgeTypePaint();
		paint.setStyle(Style.FILL);
		return paint;
	}

	public EdgeType getEdgeType() {
		return edgeType;
	}

	public void setEdgeType(EdgeType edgeType) {
		this.edgeType = edgeType;
	}

	public Direction getEdgeDirection() {
		return edgeDirection;
	}

	public void setEdgeDirection(Direction edgeDirection) {
		this.edgeDirection = edgeDirection;
	}

	public boolean handleTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		for (int i = 0; i <= 30; i++) {
			if (region.contains(x + i, y) || region.contains(x - i, y) || region.contains(x, y + i) || region.contains(x, y - i)) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					if (touched && System.currentTimeMillis() - timestampIntervalTouch >= 2000) {
						touched = false;
						deleteEdge();
					} else {
						touched = true;
						timestampIntervalTouch = System.currentTimeMillis();
					}
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					touched = false;
				}
				return true;
			}
		}
		return false;
	}

	public void deleteEdge() {
		Log.d("Test", "tocou no edge");
		source.removeOutgoingArc(this);
		sink.removeIncomingArc(this);
	}

}
