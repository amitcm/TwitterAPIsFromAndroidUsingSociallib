package com.twitterapp2.example;


import java.util.List;


import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import android.util.Log;

import com.expertiseandroid.lib.sociallib.connectors.SocialNetworkHelper;
import com.expertiseandroid.lib.sociallib.connectors.TwitterConnector;
import com.expertiseandroid.lib.sociallib.exceptions.NotAuthentifiedException;
import com.expertiseandroid.lib.sociallib.model.twitter.TwitterStatus;


public class sociallibactivity extends Activity implements Parcelable{
	private static final String CONS_KEY = "FsnXBsk6GdgY9MY7f9DZw";
	private static final String CONS_SEC = "kUPldgqQZVFZyuKT7K2pnOisaREjlgs3DQWqKMouUOQ";
	private static final String CALLBACK = "proapp://sociallib-app1";


	public static TwitterConnector mConn;

	private static final String LOG_TAG = "SocialLibActivity";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);

		Uri uri = this.getIntent().getData();
		if (uri != null && uri.toString().startsWith(CALLBACK)) {
			returningFromWebPage();
		} else {
			firstTimeHere();
		}
	}

	private void firstTimeHere() {
		mConn = SocialNetworkHelper.createTwitterConnector(CONS_KEY, CONS_SEC,
				CALLBACK);
		try 
		{
			mConn.requestAuthorization(this);
		} catch (OAuthMessageSignerException e) {
			Log.e(LOG_TAG, "", e);
		} catch (OAuthNotAuthorizedException e) {
			Log.e(LOG_TAG, "", e);
		} catch (OAuthExpectationFailedException e) {
			Log.e(LOG_TAG, "", e);
		} catch (OAuthCommunicationException e) {
			Log.e(LOG_TAG, "", e);
		}
		// After this line of code, the user will be redirected to a web page to
		// enter his credentials
	}

	private void returningFromWebPage() {
		// We are returning to the activity, the user has enterd his credentials
		// already, so we can authorize the application
		try {
			mConn.authorize(this);
		} catch (OAuthMessageSignerException e1) {
			Log.e(LOG_TAG, "", e1);
		} catch (OAuthNotAuthorizedException e1) {
			Log.e(LOG_TAG, "", e1);
		} catch (OAuthExpectationFailedException e1) {
			Log.e(LOG_TAG, "", e1);
		} catch (OAuthCommunicationException e1) {
			Log.e(LOG_TAG, "", e1);
		}

		
		
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		finish();
		Intent int_options = new Intent(sociallibactivity.this,optionsActivity.class);
		startActivity(int_options);
	}

	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

}
