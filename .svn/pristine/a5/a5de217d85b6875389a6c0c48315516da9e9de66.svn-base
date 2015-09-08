package com.codebits.softwareninja;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class ProgramNinjaActivity extends Activity {

	private ImageButton puzzle1Btn, puzzle2Btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		puzzle1Btn = (ImageButton) findViewById(R.id.puzzleonebtn);
		puzzle2Btn = (ImageButton) findViewById(R.id.puzzletwobtn);

		puzzle1Btn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ProgramNinjaActivity.this, SandBoxActivity.class);
				intent.putExtra("level", 1);
				startActivity(intent);
			}
		});

		puzzle2Btn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ProgramNinjaActivity.this, SandBoxActivity.class);
				intent.putExtra("level", 2);
				startActivity(intent);
			}
		});
	}
}
