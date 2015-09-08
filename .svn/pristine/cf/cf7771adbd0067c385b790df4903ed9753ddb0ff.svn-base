package com.codebits.softwareninja;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultEvaluationActivity extends Activity {

	TextView errorView;
	TextView printView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		boolean res = getIntent().getBooleanExtra("res", false);
		String err = getIntent().getStringExtra("err");
		String prints = getIntent().getStringExtra("prints");
		if (err != null) {
			badNinjaDisplay("Your function produced an error during its execution: \n\t" + err, prints);
		} else if (!res) {
			badNinjaDisplay("The function did not produce the expected result", prints);
		} else
			awesomeNinjaDisplay(prints);
	}

	private void awesomeNinjaDisplay(String prints) {
		setContentView(R.layout.result_success);
		printView = (TextView) findViewById(R.id.prints1);
		printView.setText(prints);
	}

	private void badNinjaDisplay(String ret, String prints) {
		setContentView(R.layout.result_fail);
		errorView = (TextView) findViewById(R.id.errorview);
		errorView.setText(ret);
		printView = (TextView) findViewById(R.id.prints);
		printView.setText(prints);

	}
}
