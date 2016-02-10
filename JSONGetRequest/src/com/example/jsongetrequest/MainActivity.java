package com.example.jsongetrequest;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.net.ssl.SSLHandshakeException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;







import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends Activity {
	private final String[] months = {"January", "Febuary" , "March", "April" , "May" , "June" , "July" , "August" , "Septermber" , " October" , "November" , "December"};
	int count =0;
	Context context;
	TextView output;
	List<MyTask> tasks;
	DataManager dataManager;
	DataManager2 dataManager2;
	File folder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		context = getApplicationContext();
		//dataManager = new DataManager(context);
		dataManager2 = new DataManager2();
		folder = getCacheDir();
		
		//Initialize the TextView for vertical scrolling 
		output = (TextView)findViewById(R.id.text);
		output.setMovementMethod(new ScrollingMovementMethod());
		
		tasks = new ArrayList<>();
		final EditText a = (EditText) findViewById(com.example.jsongetrequest.R.id.editText);
		final Button button = (Button) findViewById(R.id.LoadButton);
		final Button RequestButton =(Button) findViewById(R.id.dataButton);
		final Button button2= (Button) findViewById(com.example.jsongetrequest.R.id.Button2);
		/**
		 * 
		 */
		button.setText("delete");	
		button.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			click4();
			button.setText("Got Pressed:" + ++count);
			}
		});
	        
		RequestButton.setText("Request Data");
		RequestButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				click();
				
				}
			});
		        
	    button2.setText("plot");
	        
	    button2.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			plot();
			
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
			int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	public void delete(File Folder){
		
		File[] files = folder.listFiles();
		
		for(int i =0 ; i < files.length;i++){
			
			if(files[i].delete()){
				
			}
		}
	}
	
	public void plot(){
		startNewActivity();
	}
	
	// method to be called from oncreate that checks the cache dir to determine which files are present and which to request.  The file to be passed to the function   
	
	private List<String> checkData(File Folder){
		// Get the current date
		Calendar c = Calendar.getInstance();		
		String[] b = c.getTime().toString().split(" ");
		String uri;

		Log.d("bug","good");
		
	//	int year = Integer.parseInt(b[5]);
	//	int day =  Integer.parseInt(b[2]);
	//	int mon =  Integer.parseInt(b[1]);
		ArrayList <File> fileList;
		
		int year=2015;
		
		fileList = getList(folder.listFiles());
		File file = new File(folder.getPath()+"/July.txt");
	
		for(int i =0; i < 1 ; i ++){
			file = new File(folder.getPath() + "/"+months[9-i]+".txt");
			
			if(file.exists()){
				Log.d("bug","The file exists in cache");
			}
			
			if(!file.exists()){
				
				uri = "https://sunshine.nwra.com/pyranometer/json/201501110000/2015012100000/";
				// uri =  "https://sunshine.nwra.com/pyranometer/json/201502010000/201502020010";
				// uri = "https://sunshine.nwra.com/pyranometer/json/"+year+"0" + i+"010000/"+year+"0" +Integer.toString(i+1)+"0000";
				Log.d("bug" , "Requesting " + months[i]);
				requestData(uri,months[0]);	
			}	
		}
		return null;
	}
	
	private ArrayList<File> getList(File[] fileArray){
		
		ArrayList<File> list = new ArrayList<File>();
				for(int k =0 ; k < fileArray.length; k++){
					list.add(fileArray[k]);
				}
		return null;
	}

	public void click (){
		checkData(getCacheDir());
	}
		
	public void click3(){
		
		checkData(getCacheDir());
		String a = months[6]+ ".txt";
		DataPoint[]	array = dataManager2.loadInternalCache2(getCacheDir(), a);
	
		for(int k=0; k < array.length;k++){
		
			Log.d("data", array[k].toString());
			
		}
	}
	
	public void click4()
	{
		delete(getCacheDir());	
	
	}
	
	public void click2(){
		startNewActivity();
	}	
		
	public void startNewActivity(){
		
		int a =2;
		Intent intent = new Intent(this, SimpleXYPlotActivity.class);
		MainActivity.this.startActivity(intent);
	}
	
	//private void requestData(String uri,int i){
	//	MyTask task = new MyTask();		
	//	task.execute(uri, months[i]);
	//}

	
	// The method requestData is called when the request data button is pressed in the main activity.  This method executes the asynchronous task defined by MyTask, which 
	// makes a request to the server 
	
	private void requestData(String uri, String fileName){
		MyTask task = new MyTask();
		task.execute(uri, fileName);
	}
	
	private class MyTask extends AsyncTask<String,String,String>{
	
		DataPoint[] ar;		
		
		@Override
		protected String doInBackground(String... params) {
			String table  =params[1];
			try{
				
				this.ar=HttpManager.getData(params[0]);
				return  table;
				
			}catch(Exception e){
				Log.d("error",e.toString());
			}	
			return  table;	
			}
		
		// This method is called after the AsyncTask has completed.  The save
		protected void onPostExecute(String table){
			
				dataManager2.saveInternalCache(folder, this.ar,table);
				Log.d("bug","done: " + table);
			//	dataManager.createTable(tableName, File folder);	
				tasks.remove(this);				
		}
		protected void onPreExecute(){

		}
	}
	
	public class CustomFile extends File{
	
		public CustomFile(File dir, String name) {
			super(dir, name);
		}		
		
		public boolean equals(Object o){
		CustomFile 	x = (CustomFile) o;
		return false;
	
		}
	}
}
