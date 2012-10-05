package com.twitterapp2.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class TwitterApp2Activity extends Activity {

	// private static final String LOG_TAG = "TwitterApp1Activity";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);

		Intent myIntent = new Intent(/*getApplicationContext()*/this,
				sociallibactivity.class);
		startActivityForResult(myIntent, 0);
    }

}