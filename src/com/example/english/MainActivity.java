package com.example.english;

import org.json.*;
import org.jsoup.Jsoup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.nodes.Document;


public class MainActivity extends Activity {

	Button startButton;
	MediaPlayer mp;
	String status="";
	static ArrayList<Pair<String,String>> database;
	static ArrayList<Pair<String,String>> mydb_arraylist= new ArrayList<Pair<String,String>>();
	static String word;
	static String [] answers;
	static int rightAns=-1;
	static int coins=0;
	static int record=0;
	static int last_score;
	static int level= 0;
	static int num_of_hearts= 3;
	static AtomicInteger atomicFlag= new AtomicInteger (0); 
	TextView options, levels_text;
	static int switch_flag= 0;
	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;
	static String mydb=""; //???
	static Database_type database_type;
	static String db="";
	static int autospeak=0;

	final int level1=50;
	final int level2=20;	
	final int level4=1;
	final int level5=1;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		
		try{
			FileInputStream fis= openFileInput("rec.txt");
			InputStreamReader isr= new InputStreamReader(fis);
			char[] buf=new char[100];
			String  ans="";
			int size;
			try {
				while((size=isr.read(buf))>0){
					String read_data= 	String.copyValueOf(buf, 0, size);
					ans+= read_data;
					buf= new char[100];
				}
				if(ans=="")
					record=0;
				else
					record = Integer.parseInt(ans);

			} catch (IOException e) {e.printStackTrace();}
		} catch (FileNotFoundException e) {e.printStackTrace();}
		
		
		
		
		readMyRecord();
		
		
		readMyDB();
		//ReadArrayListFromSD(getBaseContext(),"mydb");
		//onResume();
		initDatabase();
		initMyDatabase();
		TextView display_record= (TextView)findViewById(R.id.record);
		TextView display_last_score= (TextView)findViewById(R.id.last_score);
		TextView display= (TextView)findViewById(R.id.coins_text);
		display.setText(" "+coins+" ");
		display_last_score.setText("Last score: "+last_score);
		display_record.setText("Your record: "+record);
		answers= new String [4];
		database_type= Database_type.usual;


		num_of_hearts= 3;

		levels_text= (TextView)findViewById(R.id.levelText);

		switch(level % 5){
		case 0:
			levels_text.setText("Beginner");
			initDatabase();
			break;
		case 1:
			levels_text.setText("Easy");
			initDatabase();
			break;

		case 2:
			levels_text.setText("Medium");
			initDatabase();
			break;
		case 3:
			levels_text.setText("Hard");
			initDatabase();
			break;
		case 4:
			levels_text.setText("Expert");
			initDatabase();
			break;

		}

		Button LevelButton = (Button)findViewById(R.id.levelBtn);
		LevelButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){

				level++;
				switch(level % 5){
				case 0:
					levels_text.setText("Beginner");
					initDatabase();
					break;
				case 1:
					levels_text.setText("Easy");
					initDatabase();
					break;

				case 2:
					levels_text.setText("Medium");
					initDatabase();
					break;
				case 3:
					levels_text.setText("Hard");
					initDatabase();
					break;
				case 4:
					levels_text.setText("Expert");
					initDatabase();
					break;

				}

			}
		});

		Button myDbBtn = (Button)findViewById(R.id.myDbBtn);
		myDbBtn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				Intent i= new Intent(v.getContext(),MyDBActivity.class);
				startActivityForResult(i,0);
			}
		});

		Button VideoBtn = (Button)findViewById(R.id.videoBtn);
		VideoBtn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				Intent i= new Intent(v.getContext(),SonglistActivity.class);
				startActivityForResult(i,0);
			}
		});

		Button switchButton = (Button)findViewById(R.id.swtichBtn);
		if(switch_flag==1){
			switchButton.setBackground(getResources().getDrawable(R.drawable.green_btn));
		}
		else
			switchButton.setBackground(getResources().getDrawable(R.drawable.blue_btn));
		switchButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				switch_flag++;
				switch_flag= switch_flag%2;
				Button switchButton = (Button)findViewById(R.id.swtichBtn);

				if(switch_flag==1)
					switchButton.setBackground(getResources().getDrawable(R.drawable.green_btn));
				else
					switchButton.setBackground(getResources().getDrawable(R.drawable.blue_btn));

				random();
			}
		});

		Button specialModulsButton = (Button)findViewById(R.id.specialModulsBtn);
		specialModulsButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){

				Intent i= new Intent(v.getContext(),SpecialModulsActivity.class);
				startActivityForResult(i,0);

			}
		});



		random();

		mp= MediaPlayer.create(this,R.raw.click);
		Button startButton= (Button)findViewById(R.id.start_btn);

		startButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){

				Intent i= new Intent(v.getContext(),TestActivity.class);
				startActivityForResult(i,0);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	 @Override
	    protected void onStop(){
	       super.onStop();
	       try {
				FileOutputStream fos= openFileOutput("rec.txt",Context.MODE_PRIVATE);
				OutputStreamWriter osw= new OutputStreamWriter(fos);
				try {
					osw.write(status);
					osw.flush();
					osw.close();
				} catch (IOException e) {e.printStackTrace();}
			} catch (FileNotFoundException e) {	e.printStackTrace();}
	 }

/*	
	public void onPause(){
		super.onPause();
		//SaveArrayListToSD(getBaseContext(), "mydb",  mydb_arraylist);
		
		
	}
	
	
	public void onResume(){
		super.onResume();
		ReadArrayListFromSD(getBaseContext(),"mydb");
	}
*/


	void readMyRecord(){
		try{
			FileInputStream fis= openFileInput("app.txt");
			InputStreamReader isr= new InputStreamReader(fis);
			char[] buf=new char[100];
			String  ans="";
			int size;
			try {
				while((size=isr.read(buf))>0){
					String read_data= 	String.copyValueOf(buf, 0, size);
					ans+= read_data;
					buf= new char[100];
				}
				if(ans=="")
					record=0;
				else
					record = Integer.parseInt(ans);

			} catch (IOException e) {e.printStackTrace();}
		} catch (FileNotFoundException e) {e.printStackTrace();}
	}

	void readMyDB(){
		try{
			FileInputStream fis= openFileInput("mydb.txt");
			InputStreamReader isr= new InputStreamReader(fis);
			char[] buf=new char[100];
			String  ans="";
			int size;
			try {
				while((size=isr.read(buf))>0){
					String read_data= 	String.copyValueOf(buf, 0, size);
					ans+= read_data;
					buf= new char[100];
				}
				if(ans=="")
					mydb="";
				else
					mydb = ans;

			} catch (IOException e) {e.printStackTrace();}
		} catch (FileNotFoundException e) {e.printStackTrace();}
	}

	static void initMyDatabase(){
		//mydb_arraylist= new ArrayList<Pair<String,String>>();
		//mydb_arraylist.clear();
	}
	static void initDatabase(){

		database= new ArrayList<Pair<String,String>>();
		database.clear();
		/*
		if((level % 3)==2){
			Toast.makeText(MainActivity.this," " + level % 3, 1000).show();

			Pair<String,String> obj= new Pair<String,String>("Petroleum","נפט");
			database.add(obj);
			obj= new Pair<String,String>("Emphasize","להדגיש");  
			database.add(obj);
			obj= new Pair<String,String>("Conduct","התנהגות");  
			database.add(obj);
			obj= new Pair<String,String>("Emit","לפלוט");  
			database.add(obj);
			obj= new Pair<String,String>("Exert","להפעיל");  
			database.add(obj);
			obj= new Pair<String,String>("Contemporary","עכשווי");  
			database.add(obj);
			obj= new Pair<String,String>("Concur","להסכים");  
			database.add(obj);
		}
		else{
			if((level % 3)==1){
				Toast.makeText(MainActivity.this," " + level % 3, 1000).show();
		 */
		Pair<String,String> obj= new Pair<String,String>("Village","כפר");
		database.add(obj);
		obj= new Pair<String,String>("State","מדינה");  
		database.add(obj);
		obj= new Pair<String,String>("Region","איזור");  
		database.add(obj);
		obj= new Pair<String,String>("Coast","חוף");  
		database.add(obj);
		obj= new Pair<String,String>("Timber","עץ");  
		database.add(obj);
		obj= new Pair<String,String>("Badge","תג");  
		database.add(obj);
		obj= new Pair<String,String>("Band","להקה");  
		database.add(obj);
		/*
			}
			else{
				Toast.makeText(MainActivity.this," " + level % 3, 1000).show();
				Pair<String,String> obj= new Pair<String,String>("Toys","צעצועים");
				database.add(obj);
				obj= new Pair<String,String>("Computer","מחשב");  
				database.add(obj);
				obj= new Pair<String,String>("Police","משטרה");  
				database.add(obj);
				obj= new Pair<String,String>("Country","מדינה");  
				database.add(obj);
				obj= new Pair<String,String>("Exit","יציאה");  
				database.add(obj);
				obj= new Pair<String,String>("Room","חדר");  
				database.add(obj);
				obj= new Pair<String,String>("Moon","ירח");  
				database.add(obj);

			}
		}
		 */
		new MainActivity().new MyTask().execute();
		
	}

	static void random(){
		Random rnd_word= new Random();
		int num_word= rnd_word.nextInt(database.size());

		Random rnd_ans= new Random();
		int num_ans= rnd_ans.nextInt(4);
		rightAns=num_ans;

		Random rnd_offset= new Random();
		int offset= rnd_offset.nextInt(1000);

		if(switch_flag==0){

			word=database.get(num_word).first;

			answers[num_ans]=database.get(num_word).second;
			while(num_word == (num_word +101 +offset)%database.size() || num_word == (num_word +201 +offset)%database.size() || num_word == (num_word +301 +offset)%database.size())
				offset= offset + 2;
			answers[(num_ans+1)%4]=database.get((num_word +101 +offset)%database.size()).second;
			answers[(num_ans+2)%4]=database.get((num_word +201 +offset)%database.size()).second;
			answers[(num_ans+3)%4]=database.get((num_word +301 +offset)%database.size()).second;
		}
		else{
			word=database.get(num_word).second;

			answers[num_ans]=database.get(num_word).first;
			while(num_word == (num_word +101 +offset)%database.size() || num_word == (num_word +201 +offset)%database.size() || num_word == (num_word +301 +offset)%database.size())
				offset= offset + 2;
			answers[(num_ans+1)%4]=database.get((num_word +101 +offset)%database.size()).first;
			answers[(num_ans+2)%4]=database.get((num_word +201 +offset)%database.size()).first;
			answers[(num_ans+3)%4]=database.get((num_word +301 +offset)%database.size()).first;

		}
	}

	public class MyTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			String title ="";
			Document doc;
			try {

				switch(database_type){
				case countries:
					doc = Jsoup.connect("https://www.cs.bgu.ac.il/~elii/countries").get();
					database.clear();
					break;
				case animals:
					doc = Jsoup.connect("https://www.cs.bgu.ac.il/~elii/animals").get();
					database.clear();
					break;
				case food:
					doc = Jsoup.connect("https://www.cs.bgu.ac.il/~elii/food").get();
					database.clear();
					break;
				case sport:
					doc = Jsoup.connect("https://www.cs.bgu.ac.il/~elii/sport").get();
					database.clear();
					break;
				default:
					doc = Jsoup.connect("https://www.cs.bgu.ac.il/~elii/index").get();
					break;
				}

				title = doc.body().text();


			} catch (IOException e) {
				e.printStackTrace();
			}
			return title;   
		} 


		@Override
		protected void onPostExecute(String result) {        
			//if you had a ui element, you could display the title

			int my_flag= atomicFlag.incrementAndGet();

			Pair<String,String> obj= new Pair<String,String>("Petroleum","נפט");


			try {

				JSONObject object = new JSONObject(result);
				JSONArray arr = object.getJSONArray("pairs");


				for(int k=0; k < arr.length() && (atomicFlag.get()== my_flag) ;k++){
					JSONObject triple = arr.getJSONObject(k);
					if(database_type.equals(Database_type.usual) && (level % 5)==4 && ((triple.getInt("counter") > 1) || (triple.getString("en_word").length() < 8))) // expert
						continue;
					else{
						if(database_type.equals(Database_type.usual) && ((level % 5)==4 || (level % 5)==3) && (triple.getInt("counter") > 1)) // hard
							break;
						else{
							if(database_type.equals(Database_type.usual) && (level % 5)==1 && (triple.getInt("counter") <= 20)) // Easy
								continue;
							if(database_type.equals(Database_type.usual) && (level % 5)==0 && (triple.getInt("counter") <= 50)) // beginner
								continue;
							else{
								String heb_word= triple.getString("heb_word");
								String en_word= triple.getString("en_word");
								en_word = en_word.toLowerCase();
								en_word = Character.toString(en_word.charAt(0)).toUpperCase()+en_word.substring(1) /*+ " " + triple.getInt("counter")*/;

								obj= new Pair<String,String>(en_word,heb_word);
								database.add(obj);
							}
						}
					}
				}
				
			//	writeDB(result);
				//TODO

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		
		
	}
	
	public static <E> void SaveArrayListToSD(Context mContext, String filename, ArrayList<E> list){
        try {

            FileOutputStream fos = mContext.openFileOutput(filename + ".dat", mContext.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static Object ReadArrayListFromSD(Context mContext,String filename){
        try {
            FileInputStream fis = mContext.openFileInput(filename + ".dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object obj= (Object) ois.readObject();
            fis.close();
            return obj;

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<Object>();
        }
    }
	
	private void writeDB(String status){ 
		try {
			FileOutputStream fos= openFileOutput("db.txt",Context.MODE_PRIVATE);
			OutputStreamWriter osw= new OutputStreamWriter(fos);
			try {
				osw.write(status);
				osw.flush();
				osw.close();
			} catch (IOException e) {e.printStackTrace();}
		} catch (FileNotFoundException e) {	e.printStackTrace();}
	}
	
	
	
}// class
