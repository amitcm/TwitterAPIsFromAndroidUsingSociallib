package com.twitterapp2.example;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import com.expertiseandroid.lib.sociallib.exceptions.NotAuthentifiedException;
import com.expertiseandroid.lib.sociallib.model.twitter.TwitterStatus;
import com.expertiseandroid.lib.sociallib.model.twitter.TwitterUser;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class optionsActivity extends Activity{
	//	public static TwitterConnector mConn1;
	private static final String LOG_TAG = "SocialLibActivity";
	private Button postTweetButton;
	private Button getTimelineButton;
	private Button getFollowerbutton;
	private EditText editTextTweet;
	private Button buttonPostTweet;


	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.options);

		postTweetButton = (Button) findViewById(R.id.postTweet);
		MyOnClickListener myClickListener = new MyOnClickListener();
		postTweetButton.setOnClickListener(myClickListener);

		getTimelineButton = (Button)findViewById(R.id.getTimeLine);
		getTimelineButton.setOnClickListener(myClickListener);

		getFollowerbutton =(Button)findViewById(R.id.getFollowers);
		getFollowerbutton.setOnClickListener(myClickListener);


	}	
	
	public void DisplayListView(String[] values)
	{
		ListView listViewTw = (ListView) findViewById(R.id.mylist);
		 
		// First paramenter - Context
		// Second parameter - Layout for the row
		// Third parameter - ID of the View to which the data is written
		// Forth - the Array of data
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, values);

		// Assign adapter to ListView
		listViewTw.setAdapter(adapter);
		
	}

	
	protected class MyOnClickListener implements OnClickListener
	{
		public void onClick(View arg0) 
		{
			//editTextTweet.setCursorVisible(false);
			// TODO Auto-generated method stub
			switch(arg0.getId())
			{
			case R.id.postTweet:
				postTweetfun();
				break;

			case R.id.getFollowers:
				editTextTweet.setVisibility(View.INVISIBLE);
				buttonPostTweet.setVisibility(View.INVISIBLE);
				getFollowersFun();
				break;

			case R.id.getTimeLine:
				editTextTweet.setVisibility(View.INVISIBLE);
				buttonPostTweet.setVisibility(View.INVISIBLE);
				getTimeLinefun();
				break;
			}

		}

	}


	void postTweetfun()
	{
		//Post a tweet
		editTextTweet = (EditText)findViewById(R.id.editText1);
		editTextTweet.setVisibility(View.VISIBLE);
		//editTextTweet.setCursorVisible(true);
		//editTextTweet.setFocusable(true);
		buttonPostTweet = (Button)findViewById(R.id.button1);
		buttonPostTweet.setVisibility(View.VISIBLE);
		buttonPostTweet.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String tweet_txt = editTextTweet.getText().toString();
				try 
				{
					if (sociallibactivity.mConn.tweet(tweet_txt))
					{
						editTextTweet.setText("");
						editTextTweet.setVisibility(View.INVISIBLE);
						buttonPostTweet.setVisibility(View.INVISIBLE);
						//editTextTweet.setCursorVisible(false);
						//editTextTweet.setFocusable(false);
						Log.i(LOG_TAG,"Tweet Successful");
					}
				} catch (OAuthMessageSignerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OAuthExpectationFailedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OAuthCommunicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotAuthentifiedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			}
		});
		
		
	}	

	void getFollowersFun()
	{
		//Get Followers 
		List<TwitterUser> listofFollowers;
		try {
			listofFollowers =sociallibactivity.mConn.getFollowers();
			String[] values = new String[20];
			int i = 0;
			for(TwitterUser temp : listofFollowers)
			{
				values[i] = temp.name.toString();
				i++;
				if(i == 19)
					break;
				Log.i(LOG_TAG,temp.name);
			}
			
			DisplayListView(values);

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotAuthentifiedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	void getTimeLinefun()
	{
		// Get Timeline
		List<TwitterStatus> list;
		
		String[] values = new String[20];
		int i = 0;

		try {
			list = sociallibactivity.mConn.getWallPosts(); // We create a list containing the
			// user's wall posts
			for (TwitterStatus st : list) {

				values[i] = st.getContents().toString();
				i++;
				if(i == 19)
					break;
				
				Log.i(LOG_TAG, st.getContents());
			}
			DisplayListView(values);
		} catch (NotAuthentifiedException e) {
			Log.e(LOG_TAG, "The user is not logged in");
		} catch (Exception e) {
			Log.e(LOG_TAG, e.getMessage());
		}

	}
}

