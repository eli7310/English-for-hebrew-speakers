package com.example.english;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridLayout.Spec;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class SonglistActivity extends Activity {
	Button button;
	final int num_of_buttons=100;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_songlist);

		GridLayout gridView = (GridLayout) findViewById(R.id.gridview);



		// Instance of ImageAdapter Class
		//  gridView.setAdapter(new ImageAdapter(this));


		for (int i = 0; i < num_of_buttons; i++) {
			Spec rowSpec = GridLayout.spec(i/4);
			Spec colSpec = GridLayout.spec(i%4);
			GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec, colSpec);

			params.width = 250;
			params.height= 250;
			params.rightMargin= 10;
			params.leftMargin= 2;
			Button btn = new Button(this);
			btn.setId(i);
			final int id_ = btn.getId();
			//btn.setText("button " + id_);
			btn.setBackgroundResource(R.drawable.lock);
			if(i==0){
				btn.setBackgroundResource(R.drawable.black_btn2);
				
				btn.setTextColor(Color.WHITE);
				btn.setText("Let her go");

			}
			gridView.addView(btn, params);
			btn = ((Button) findViewById(id_));
			if(i==0)
				btn.setOnClickListener(new View.OnClickListener() {
					public void onClick(View view) {
						Intent i= new Intent(view.getContext(),VideoActivity.class);
						startActivityForResult(i,0);
					}
				});
		}	
	}
}
