package com.codebits.softwareninja.domain;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.codebits.softwareninja.R;
import com.codebits.softwareninja.interpretation.Interpreter;
import com.codebits.softwareninja.interpretation.NodeVisitor;

public class RunNode extends Node {

	public RunNode(Context context, int posX, int posY, ProgramView programView) {
		super(context, posX, posY, programView);
		// TODO Auto-generated constructor stub
		setRightStateImg(BitmapFactory.decodeResource(getResources(), R.drawable.run_button));
		setWrongStateImg(BitmapFactory.decodeResource(getResources(), R.drawable.run_button));
		setStateRight(true);
	}

	@Override
	public void handleNodeCollision(Node n) {
		// do nothing
		return;

	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawBitmap(getImageToDraw(), null, new Rect(getPosX() - getNodeWidth() / 2, getPosY() - getNodeHeight() / 2, getPosX() + getNodeWidth() / 2,
				getPosY() + getNodeHeight() / 2), null);
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
		throw new RuntimeException("We should never be visiting a Run Node");
	}

	// just accept touch events
	@Override
	public boolean handleTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		if (this.isTouched()) {
			if (event.getAction() == MotionEvent.ACTION_UP && checkBoxCollision(x, y)) {
				if (System.currentTimeMillis() - getTimestampIntervalTouch() > 500) {
					// Launch interpreter in an asynch thread
					new Interpreter(programView).start();
				}
				this.setTouched(false);
			}
			return true;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN && checkBoxCollision(x, y)) {
			setTimestampIntervalTouch(System.currentTimeMillis());
			this.setTouched(true);
			return true;
		}
		return false;

	}

	@Override
	public void evaluateState() {
	}

	@Override
	public String getInfoForDraw() {
		return null;
	}

}
