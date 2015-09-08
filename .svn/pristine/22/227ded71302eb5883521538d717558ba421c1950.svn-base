package com.codebits.softwareninja.domain;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.codebits.softwareninja.R;
import com.codebits.softwareninja.interpretation.NodeVisitor;

public class CountBarNode extends Node {

	public CountBarNode(Context context, int posX, int posY, ProgramView programView) {
		super(context, posX, posY, programView);
		// TODO Auto-generated constructor stub
		setRightStateImg(BitmapFactory.decodeResource(getResources(), R.drawable.lixo_fechado));
		setWrongStateImg(BitmapFactory.decodeResource(getResources(), R.drawable.lixo_fechado));
		setStateRight(true);

	}

	@Override
	public void handleNodeCollision(Node n) {

	}

	@Override
	public void draw(Canvas canvas) {
		int w = programView.getScreenWidth();
		int h = programView.getScreenHeight();

		Map<Class<?>, Integer> counts = programView.getNodesLimit();

		if (!counts.isEmpty()) {
			Paint paint = new Paint();
			paint.setTextSize(20);
			paint.setColor(Color.rgb(190, 190, 190));
			canvas.drawText(counts.get(BeginNode.class) + "", w / 12 - (w / 54), h - 130, paint);
			canvas.drawText(counts.get(ExpNode.class) + "", w / 12 + w / 6 - (w / 52), h - 130, paint);
			canvas.drawText(counts.get(IfNode.class) + "", w / 12 + 2 * w / 6 - (w / 96), h - 130, paint);
			canvas.drawText(counts.get(CycleNode.class) + "", w / 12 + 3 * w / 6 - (w / 96), h - 130, paint);
			canvas.drawText(counts.get(PrintNode.class) + "", w / 12 + 4 * w / 6 - (w / 96), h - 130, paint);
			canvas.drawText(counts.get(EndNode.class) + "", w / 12 + 5 * w / 6 - (w / 96), h - 130, paint);

		}

	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

	@Override
	public void populateContextToolbox(Activity activity, LinearLayout editLayout) {
		// TODO Auto-generated method stub

	}

	@Override
	public void accept(NodeVisitor visitor) {
		throw new RuntimeException("We should never be visiting a Garbage Node");
	}

	// never drag the garbage
	@Override
	public boolean handleTouchEvent(MotionEvent event) {
		return false;
	}

	@Override
	public void evaluateState() {
	}

	@Override
	public void drawInfo(Canvas canvas) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getInfoForDraw() {
		// TODO Auto-generated method stub
		return null;
	}

}
