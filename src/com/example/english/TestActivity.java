package com.example.english;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.util.Pair;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class TestActivity extends Activity{
	Button btn1,btn2,btn3,btn4;
	ImageView heart1,heart2,heart3, picture, star1, star2, star3, star4, star5;
	int rightAns;
	final int SECOND=1000;
	MediaPlayer mp;
	TextToSpeech t1;


	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_test); 

		t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				if(status != TextToSpeech.ERROR) {
					t1.setLanguage(Locale.US);
				}
			}
		});
		Button speechBtn= (Button)findViewById(R.id.speechBtn);		
		if(MainActivity.autospeak%2==0){
			String toSpeak= MainActivity.word;
			t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
			speechBtn.setBackground(getResources().getDrawable(R.drawable.orange_speaker_icon));
		}else{

			speechBtn.setBackground(getResources().getDrawable(R.drawable.blue_speaker_icon));
		}

		mp= MediaPlayer.create(this,R.raw.glass_ping);

		heart1 = (ImageView)findViewById(R.id.heart1);
		heart2 = (ImageView)findViewById(R.id.heart2);
		heart3 = (ImageView)findViewById(R.id.heart3);
		picture= (ImageView)findViewById(R.id.picture);
		picture.setVisibility(View.INVISIBLE);
		if(MainActivity.database_type.equals(Database_type.usual))
			picture.setVisibility(View.INVISIBLE);
		else{
			picture.setVisibility(View.INVISIBLE);
			/*new DownloadImageTask((ImageView) findViewById(R.id.picture))
	        .execute("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRao7k6amoAgWh4cbTRT3-zEZ8c1_Uluex6osMyAVPIsn9KQ2dkW5t7Ww");
			picture.setVisibility(View.VISIBLE);*/
		}


		if(MainActivity.num_of_hearts==2)
			heart3.setVisibility(View.INVISIBLE);
		if(MainActivity.num_of_hearts==1){
			heart2.setVisibility(View.INVISIBLE);
			heart3.setVisibility(View.INVISIBLE);
		}
		
		
		star1 = (ImageView)findViewById(R.id.star1);
		star2 = (ImageView)findViewById(R.id.star2);
		star3 = (ImageView)findViewById(R.id.star3);
		star4 = (ImageView)findViewById(R.id.star4);
		star5 = (ImageView)findViewById(R.id.star5);
		
		


		rightAns= MainActivity.rightAns +1;
		Button screenBtn= (Button)findViewById(R.id.screenBtn);
		screenBtn.setVisibility(View.INVISIBLE);
		Button btn1= (Button)findViewById(R.id.swtichBtn);
		Button addTodbBtn= (Button)findViewById(R.id.addToMyDb);
		Button btn2= (Button)findViewById(R.id.button2);
		Button btn3= (Button)findViewById(R.id.button3);
		Button btn4= (Button)findViewById(R.id.button4);


		btn1.setText(MainActivity.answers[0]);
		btn2.setText(MainActivity.answers[1]);
		btn3.setText(MainActivity.answers[2]);
		btn4.setText(MainActivity.answers[3]);

		TextView display= (TextView)findViewById(R.id.textView);
		display.setText(MainActivity.word);

		TextView coins_display= (TextView)findViewById(R.id.linear_backgroung);
		coins_display.setText(" "+MainActivity.coins+" ");

		speechBtn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				if(MainActivity.switch_flag==0){
					String toSpeak= MainActivity.word;
					t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
				}
				else
				{Toast.makeText(getApplicationContext(), "The speech avialable only for English",Toast.LENGTH_SHORT).show();
				}
			}
		});

		speechBtn.setOnLongClickListener(new OnLongClickListener() { 
			@Override
			public boolean onLongClick(View v) {
				MainActivity.autospeak++;
				Button speechBtn= (Button)findViewById(R.id.speechBtn);
				Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				vib.vibrate(100);
				if(MainActivity.autospeak%2==0){
					Toast.makeText(getApplicationContext(), "Autospeak is ON",Toast.LENGTH_SHORT).show();
					speechBtn.setBackground(getResources().getDrawable(R.drawable.orange_speaker_icon));
				}else{

					Toast.makeText(getApplicationContext(), "Autospeak is OFF",Toast.LENGTH_SHORT).show();
					speechBtn.setBackground(getResources().getDrawable(R.drawable.blue_speaker_icon));
				}

				return true;
			}
		});

		screenBtn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){

				Button screenBtn= (Button)findViewById(R.id.screenBtn);
				screenBtn.setVisibility(View.INVISIBLE);
				next_step(v);
			}
		});

		addTodbBtn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				addTodb();
				Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
				vib.vibrate(150);

			}
		});


		btn1.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				if(rightAns==1)
				{
					/*Button btn1= (Button)findViewById(R.id.swtichBtn);
					btn1.setBackground(getResources().getDrawable(R.drawable.green_btn));
					 */
					rightAnswer(v, 1);
				}
				else{
					/*Button btn1= (Button)findViewById(R.id.swtichBtn);
					btn1.setBackground(getResources().getDrawable(R.drawable.red_btn));*/
					wrongAnswer(v,1);

				}

			}
		});

		btn2.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				if(rightAns==2)
				{
					/*Button btn1= (Button)findViewById(R.id.button2);
					btn1.setBackground(getResources().getDrawable(R.drawable.green_btn));
					 */
					rightAnswer(v, 2);
				}
				else{
					/*Button btn2= (Button)findViewById(R.id.button2);
					btn2.setBackground(getResources().getDrawable(R.drawable.red_btn));*/

					wrongAnswer(v,2);

				}			
			}
		});
		btn3.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				if(rightAns==3)
				{
					/*Button btn1= (Button)findViewById(R.id.button3);
					btn1.setBackground(getResources().getDrawable(R.drawable.green_btn));*/
					rightAnswer(v,3);
				}
				else{
					/*Button btn3= (Button)findViewById(R.id.button3);
					btn3.setBackground(getResources().getDrawable(R.drawable.red_btn));*/

					wrongAnswer(v,3);
				}			
			}
		});
		btn4.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v){
				if(rightAns==4)
				{
					/*Button btn1= (Button)findViewById(R.id.button4);
					btn1.setBackground(getResources().getDrawable(R.drawable.green_btn));*/
					rightAnswer(v, 4);

				}
				else{
					/*Button btn4= (Button)findViewById(R.id.button4);
					btn4.setBackground(getResources().getDrawable(R.drawable.red_btn));*/

					wrongAnswer(v,4);
				}			
			}
		});
		//Toast.makeText(TestActivity.this," " + MainActivity.num_of_hearts, 4*SECOND).show();
	}// on creat

	void wrongAnswer(View v,int btn){
		addTodb();

		if(MainActivity.num_of_hearts > 1){

			MainActivity.num_of_hearts--;

			Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vib.vibrate(150);

			/*
			Button screenBtn= (Button)findViewById(R.id.screenBtn);
			screenBtn.bringToFront();
			screenBtn.setVisibility(View.VISIBLE);
			 */	
			next_step(v);

		}
		else{

			Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			vib.vibrate(400);
			MainActivity.last_score= MainActivity.coins;

			if(MainActivity.coins>MainActivity.record){
				int old_record=MainActivity.record;
				MainActivity.record= MainActivity.coins;
				recordWrite(MainActivity.record+"");

				// notification block
				mp.start();
				NotificationManager nm= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				Notification noti= new Notification(R.drawable.ic_launcher,
						"Congratulations!", System.currentTimeMillis());
				Context context= TestActivity.this;
				//CharSequence title= "Congratulations! You broke the record!";
				//CharSequence details= "Your old record was:"+ old_record + "\nand you new record is: " + MainActivity.record;
				CharSequence title= "Congratulations!";
				CharSequence details= "You broke the record! which was: "+ old_record;
				Intent intent = new Intent(context,MainActivity.class);
				PendingIntent pending = PendingIntent.getActivity(context, 0, intent, 0);
				noti.setLatestEventInfo(context, title, details, pending);
				nm.notify(0, noti);
				// end of notification block
			}

			MainActivity.coins=0;
			Intent i= new Intent(v.getContext(),MainActivity.class);
			startActivityForResult(i,0);
		}
	}

	void rightAnswer(View v, int btn){


		if((MainActivity.level)%5==0)
			MainActivity.coins++;
		if((MainActivity.level)%5==1)
			MainActivity.coins= MainActivity.coins +2;
		if((MainActivity.level)%5==2)
			MainActivity.coins= MainActivity.coins +3;
		if((MainActivity.level)%5==3)
			MainActivity.coins= MainActivity.coins +4;
		if((MainActivity.level)%5==4)
			MainActivity.coins= MainActivity.coins +5;


		Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		vib.vibrate(50);

		next_step(v);

	}

	void next_step(View v){
		if(MainActivity.database.size()>8){

			random();
			
			if(MainActivity.autospeak%2==0){
				String toSpeak= MainActivity.word;
				//t1.playSilence(50000, 1, null);
				t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
			}

			onCreate(null);
			
		}else{
			Toast.makeText(TestActivity.this,"Congratulations! The database is empty! ", 4*SECOND).show();
			Intent i= new Intent(v.getContext(),MainActivity.class);
			startActivityForResult(i,0);
		}
	}

	void init(){

	}

	static void random(){
		Random rnd_word= new Random();
		int num_word= rnd_word.nextInt(MainActivity.database.size());

		Random rnd_ans= new Random();
		int num_ans= rnd_ans.nextInt(4);
		MainActivity.rightAns=num_ans;

		Random rnd_offset= new Random();
		int offset= rnd_offset.nextInt(1000);

		if(MainActivity.switch_flag==0){

			MainActivity.word= MainActivity.database.get(num_word).first ; 
			MainActivity.answers[num_ans]=MainActivity.database.get(num_word).second;
			String hebWord=MainActivity.database.get(num_word).second;

			while(num_word == (num_word +101 +offset)%MainActivity.database.size() || num_word == (num_word +201 +offset)%MainActivity.database.size() || num_word == (num_word +301 +offset)%MainActivity.database.size() // two numbers are the same
					|| hebWord.equals(MainActivity.database.get((num_word +101 +offset)%MainActivity.database.size()).second) || hebWord.equals(MainActivity.database.get((num_word +201 +offset)%MainActivity.database.size()).second) || hebWord.equals(MainActivity.database.get((num_word +301 +offset)%MainActivity.database.size()).second) // one of the options are equal
					|| (MainActivity.database.get((num_word +101 +offset)%MainActivity.database.size()).second).equals(MainActivity.database.get((num_word +201 +offset)%MainActivity.database.size()).second) || (MainActivity.database.get((num_word +101 +offset)%MainActivity.database.size()).second).equals(MainActivity.database.get((num_word +301 +offset)%MainActivity.database.size()).second) || (MainActivity.database.get((num_word +201 +offset)%MainActivity.database.size()).second).equals(MainActivity.database.get((num_word +301 +offset)%MainActivity.database.size()).second))
				offset= offset + 11;

			MainActivity.answers[(num_ans+1)%4]=MainActivity.database.get((num_word +101 +offset)%MainActivity.database.size()).second;
			MainActivity.answers[(num_ans+2)%4]=MainActivity.database.get((num_word +201 +offset)%MainActivity.database.size()).second;
			MainActivity.answers[(num_ans+3)%4]=MainActivity.database.get((num_word +301 +offset)%MainActivity.database.size()).second;
		}
		else{
			MainActivity.word=MainActivity.database.get(num_word).second;
			MainActivity.answers[num_ans]=MainActivity.database.get(num_word).first;


			while(num_word == (num_word +101 +offset)%MainActivity.database.size() || num_word == (num_word +201 +offset)%MainActivity.database.size() || num_word == (num_word +301 +offset)%MainActivity.database.size())
				offset= offset + 11;

			MainActivity.answers[(num_ans+1)%4]=MainActivity.database.get((num_word +101 +offset)%MainActivity.database.size()).first;
			MainActivity.answers[(num_ans+2)%4]=MainActivity.database.get((num_word +201 +offset)%MainActivity.database.size()).first;
			MainActivity.answers[(num_ans+3)%4]=MainActivity.database.get((num_word +301 +offset)%MainActivity.database.size()).first;

		}
		MainActivity.database.remove(num_word);



	}

	private void recordWrite(String status){ 
		try {
			FileOutputStream fos= openFileOutput("app.txt",Context.MODE_PRIVATE);
			OutputStreamWriter osw= new OutputStreamWriter(fos);
			try {
				osw.write(status);
				osw.flush();
				osw.close();
			} catch (IOException e) {e.printStackTrace();}
		} catch (FileNotFoundException e) {	e.printStackTrace();}
	}


	private void myDBWrite(String status){ 
		try {
			FileOutputStream fos= openFileOutput("mydb.txt",Context.MODE_PRIVATE);
			OutputStreamWriter osw= new OutputStreamWriter(fos);
			try {
				osw.write(status);
				osw.flush();
				osw.close();
			} catch (IOException e) {e.printStackTrace();}
		} catch (FileNotFoundException e) {	e.printStackTrace();}
	}


	public class MyPictureTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			String title ="";
			return title;   
		} 


		@Override
		protected void onPostExecute(String result) {        
			//if you had a ui element, you could display the title
			/*Button btn1= (Button)findViewById(R.id.swtichBtn);
			btn1.setBackground(getResources().getDrawable(R.drawable.green_btn));
			 */
		}
	}
	/*	
	public Drawable LoadImageFromWebOperations(String url) {
	    try {
	        InputStream is = (InputStream) new URL(url).getContent();
	        Drawable d = Drawable.createFromStream(is, "src name");
	        return d;
	    } catch (Exception e) {
	    	Toast.makeText(getApplicationContext(), "Failed",Toast.LENGTH_SHORT).show();
	        return null;
	    }
	}
	 */
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				//         Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}
	}
	
	public void addTodb(){
		String en_word="", heb_word="";
		if(MainActivity.switch_flag==0){
			en_word= MainActivity.word;
			heb_word= MainActivity.answers[MainActivity.rightAns];
		}
		else{
			heb_word= MainActivity.word;
			en_word= MainActivity.answers[MainActivity.rightAns];
		}
		/*MainActivity.mydb+= "{\"heb_word\":\""+ heb_word+ "\", \"en_word\""+en_word+"\"\"},";*/
		MainActivity.mydb+= "("+ en_word + " : " +  heb_word + ")" + "\n";
		myDBWrite(MainActivity.mydb);
		Pair<String,String> p= new Pair<String,String>(en_word,heb_word);
		MainActivity.mydb_arraylist.add(p);
		// TODO 1 its totally random
		//wrongAnswer(v,1); 

	}
}