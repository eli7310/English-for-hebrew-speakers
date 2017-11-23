package com.example.english;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

public class VideoActivity extends Activity {

	String youtube_url ="https://www.youtube.com/embed/";
	String html = "<iframe class=\"youtube-player\" style=\"border: 0; width: 100%; height: 95%; padding:0px; margin:0px\" id=\"ytplayer\" type=\"text/html\" src=\"https://www.youtube.com/embed/S3fTw_D3l10"
			+ "?fs=0\" frameborder=\"0\">\n"
			+ "</iframe>";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video);


		WebView myWebView = (WebView) findViewById(R.id.video);
		myWebView.getSettings().setJavaScriptEnabled(true);
		//myWebView.loadDataWithBaseURL("", html , "text/html",  "UTF-8", "");
		myWebView.loadUrl("https://www.youtube.com/embed/RBumgq5yVrA");
		TextView title= (TextView) findViewById(R.id.title); 
		title.setText("  Passenger - Let Her Go");
		TextView lyrics= (TextView) findViewById(R.id.lyrics); 
		lyrics.setText("Well you only need the light when it's burning low, Only miss the sun when it starts to snow, Only know you love her when you let her go. \nOnly know you've been high when you're feeling low, Only hate the road when you're missing home, Only know you love her when you let her go, And you let her go. \nStaring at the bottom of your glass, Hoping one day you'll make a dream last, But dreams come slow and they go so fast \nYou see her when you close your eyes, Maybe one day you'll understand why, Everything you touch surely dies.");

		/*

		PIh2xe4jnpk // MAGIC! - Rude

		Button button1= (Button)findViewById(R.id.button1);
		button1.setText("Passenger - Let Her Go");
		button1.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				WebView myWebView = (WebView) findViewById(R.id.video);
				myWebView.getSettings().setJavaScriptEnabled(true);
				myWebView.loadUrl(youtube_url+"RBumgq5yVrA");
			}
		});

		Button button2= (Button)findViewById(R.id.button2);
		button2.setText("Bruno Mars - Just The Way You Are");
		button2.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				WebView myWebView = (WebView) findViewById(R.id.video);
				myWebView.getSettings().setJavaScriptEnabled(true);
				myWebView.loadUrl(youtube_url+"rExJ6j5OeCo");
			}
		});

		Button button3= (Button)findViewById(R.id.button3);
		button3.setText("Coldplay - Fix You");
		button3.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				WebView myWebView = (WebView) findViewById(R.id.video);
				myWebView.getSettings().setJavaScriptEnabled(true);
				myWebView.loadUrl(youtube_url+"0WMCZvsPq-g");
			}
		});

		Button button4= (Button)findViewById(R.id.button4);
		button4.setText("MAGIC! - Rude");
		button4.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				WebView myWebView = (WebView) findViewById(R.id.video);
				myWebView.getSettings().setJavaScriptEnabled(true);
				myWebView.loadUrl(youtube_url+"dcge47yeCgo");
			}
		});

		Button button5= (Button)findViewById(R.id.button5);
		button5.setText("MAGIC! - Rude");
		button5.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				WebView myWebView = (WebView) findViewById(R.id.video);
				myWebView.getSettings().setJavaScriptEnabled(true);
				myWebView.loadUrl(youtube_url+"dcge47yeCgo");
			}
		});
		 */

	}


}
