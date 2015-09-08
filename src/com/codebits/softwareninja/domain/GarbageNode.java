package com.codebits.softwareninja.domain;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.codebits.softwareninja.R;
import com.codebits.softwareninja.interpretation.NodeVisitor;

public class GarbageNode extends Node {

	public GarbageNode(Context context, int posX, int posY, ProgramView programView) {
		super(context, posX, posY, programView);
		// TODO Auto-generated constructor stub
		setRightStateImg(BitmapFactory.decodeResource(getResources(), R.drawable.lixo_fechado));
		setWrongStateImg(BitmapFactory.decodeResource(getResources(), R.drawable.lixo_fechado));
		setStateRight(true);
	}

	@Override
	public void handleNodeCollision(Node n) {
		// TODO removeNode
		programView.removeNode(n);

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
	public String getInfoForDraw() {
		// TODO Auto-generated method stub
		return null;
	}

}
