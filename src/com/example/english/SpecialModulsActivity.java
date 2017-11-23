package com.example.english;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SpecialModulsActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_specialmoduls);
		
		
		
		Button animalButton = (Button)findViewById(R.id.linear_layout);
		animalButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				MainActivity.database_type= Database_type.animals;
				MainActivity.initDatabase();
				Intent i= new Intent(v.getContext(),TestActivity.class);
				startActivityForResult(i,0);
			}
		});
		
		Button sportButton = (Button)findViewById(R.id.sportModulBtn);
		sportButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				MainActivity.database_type= Database_type.sport;
				MainActivity.initDatabase();
				Intent i= new Intent(v.getContext(),TestActivity.class);
				startActivityForResult(i,0);
			}
		});
		
		Button foodButton = (Button)findViewById(R.id.foodModulBtn);
		foodButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				MainActivity.database_type= Database_type.food;
				MainActivity.initDatabase();
				Intent i= new Intent(v.getContext(),TestActivity.class);
				startActivityForResult(i,0);
			}
		});
		
		Button countriesButton = (Button)findViewById(R.id.countriesModulBtn);
		countriesButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				MainActivity.database_type= Database_type.countries;
				MainActivity.initDatabase();
				Intent i= new Intent(v.getContext(),TestActivity.class);
				startActivityForResult(i,0);
			}
		});
		
		
	}
	
	
	
}
