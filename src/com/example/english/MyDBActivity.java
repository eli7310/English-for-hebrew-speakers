package com.example.english;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyDBActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mydb);
		/*String s="";
		if(MainActivity.mydb_arraylist.size()>0)
			s= MainActivity.mydb_arraylist.get(0).first + " , " + MainActivity.mydb_arraylist.get(0).second;
		*/	
		TextView display= (TextView)findViewById(R.id.mydbtext);
		display.setText(" "+MainActivity.mydb+" ");
		//display.setText(s);
		
	}



}
