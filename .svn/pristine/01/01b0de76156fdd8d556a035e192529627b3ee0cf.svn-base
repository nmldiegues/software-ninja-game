package com.codebits.softwareninja;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codebits.softwareninja.domain.GameCanvas;
import com.codebits.softwareninja.domain.LevelCreator;

public class SandBoxActivity extends Activity {

	private GameCanvas gameCanvas;
	public static int level = 1;
	private ImageButton closeBtn;
	private TextView assignment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		level = getIntent().getIntExtra("level", 1);
		setContentView(R.layout.assignment);
		closeBtn = (ImageButton) findViewById(R.id.closebtn);
		assignment = (TextView) findViewById(R.id.assignment);
		if (level == 1)
			assignment.setText(" " + LevelCreator.getLevel1Description());
		else
			assignment.setText(" " + LevelCreator.getLevel2Description());
		closeBtn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				startSandBox();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_sand_box, menu);
		return true;
	}

	private void startSandBox() {
		setContentView(R.layout.activity_sand_box);
		gameCanvas = (GameCanvas) findViewById(R.id.SurfaceView01);
		gameCanvas.setReturnActivity(this);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		int action = event.getAction();
		int keyCode = event.getKeyCode();
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			if (action == KeyEvent.ACTION_UP) {
				// TODO
				gameCanvas.handleHardwareButtonEvent(event);
			}
			return true;
		default:
			return super.dispatchKeyEvent(event);
		}
	}

}
