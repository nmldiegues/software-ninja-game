package com.codebits.softwareninja.domain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.codebits.softwareninja.R;
import com.codebits.softwareninja.SandBoxActivity;

public class GameCanvas extends SurfaceView implements SurfaceHolder.Callback {
	public class CanvasThread extends Thread {
		private boolean running;
		private SurfaceHolder holder;

		public CanvasThread(SurfaceHolder holder) {
			this.holder = holder;
			running = false;
		}

		@Override
		public void run() {

			Canvas canvas = null;
			Bitmap bck = BitmapFactory.decodeResource(getResources(), R.drawable.bck);
			while (running) {
				try {
					canvas = holder.lockCanvas(null);
					if (canvas != null) {
						synchronized (holder) {
							// canvas.drawColor(Color.DKGRAY);
							canvas.drawBitmap(bck, 0, 0, null);
							// TODO
							programView.draw(canvas);
						}
					}
				} finally {
					// do this in a finally so that if an exception is thrown
					// during the above, we don't leave the Surface in an
					// inconsistent state
					if (canvas != null) {
						holder.unlockCanvasAndPost(canvas);
					}
				}

			}
		}

		public boolean isRunning() {
			return running;
		}

		public void setRunning(boolean running) {
			this.running = running;
		}

		public SurfaceHolder getHolder() {
			return holder;
		}

		public void setHolder(SurfaceHolder holder) {
			this.holder = holder;
		}
	}

	private CanvasThread canvasThread;
	private SandBoxActivity sandBoxActivity;
	private final ProgramView programView;
	Context context;
	boolean firstRun = true;

	private ImageButton beginBtn, endBtn, cycleBtn, ifBtn, printBtn, expBtn;

	public GameCanvas(Context context, AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
		setFocusable(true);

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		programView = new ProgramView(context, display.getHeight(), display.getWidth(), SandBoxActivity.level);
		this.context = context;
	}

	public void createBtnListeners() {
		View v = getRootView();
		beginBtn = (ImageButton) v.findViewById(R.id.startbtn);
		endBtn = (ImageButton) v.findViewById(R.id.endbtn);
		cycleBtn = (ImageButton) v.findViewById(R.id.cyclobtn);
		ifBtn = (ImageButton) v.findViewById(R.id.ifbtn);
		printBtn = (ImageButton) v.findViewById(R.id.printbtn);
		expBtn = (ImageButton) v.findViewById(R.id.expbtn);
		beginBtn.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					programView.addNode(BeginNode.class);
				}
				return false;
			}
		});
		endBtn.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP)
					programView.addNode(EndNode.class);
				return false;
			}
		});
		cycleBtn.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP)
					programView.addNode(CycleNode.class);
				return false;
			}
		});
		ifBtn.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP)
					programView.addNode(IfNode.class);
				return false;
			}
		});
		printBtn.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP)
					programView.addNode(PrintNode.class);
				return false;
			}
		});
		expBtn.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP)
					programView.addNode(ExpNode.class);
				return false;
			}
		});

	}

	public void stop() {
		if (canvasThread != null)
			canvasThread.setRunning(false);
	}

	public void setReturnActivity(SandBoxActivity gamePlayActivity) {
		this.sandBoxActivity = gamePlayActivity;
	}

	public CanvasThread getThread() {
		return canvasThread;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		canvasThread = new CanvasThread(holder);
		canvasThread.setRunning(true);
		canvasThread.start();
		createBtnListeners();

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		canvasThread.setRunning(false);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// Log.d(this.getClass().getName(), "touched in " + event.getX() + " " +
		// event.getY());
		programView.handleTouchEvent(event);

		return true;
	}

	public void handleHardwareButtonEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		programView.handleHardwareButtonEvent(event);
	}

}