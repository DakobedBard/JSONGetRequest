package com.example.jsongetrequest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
public class DataManager {

	private final String[] months = {"January", "Febuary" , "March", "April" , "May" , "June" , "July" , "August" , "Septermber" , " October" , "November" , "December"};
	
	
	DataPoint[] mainArray;
	SQLite database;
	SQLiteDatabase sqLiteDatabase;
	DataAdapter dataAdapter;
	
	DataPoint[] January;
	public DataManager(Context context){
		dataAdapter = new DataAdapter(context);
	}


	public DataPoint[] loadData(String tableName){
		
		DataPoint[] results = dataAdapter.getData(tableName);
		
	
		if(results == null){
			
		}else{
			
		}
	
		
		return results;
	}
	
	
	
	public void saveInternalCache(File folder, DataPoint[] data, String fileName){

	
		StringBuilder a = new StringBuilder();
		a.append(fileName);
		a.append(".txt");
		
		String b = " this is a file";
		FileOutputStream fileOutputStream = null;
		File myFile = new File(folder,a.toString());
		
		String line;
		try {
			fileOutputStream = new FileOutputStream(myFile);
			fileOutputStream.write(b.getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
	
	
	}
	
	

	
	
	
	
	
	
	

	public void createTable(String tableName){
		dataAdapter.drop(tableName);
		dataAdapter.createNewTable(tableName);
	}
	
	public void insert(String tableName, DataPoint[] month){
		
		long id;
		double max;
		double min;
		String t;
		
		int good =0;
		int bad =0;
		
		for(int i=0; i < 20; i ++){
			max = month[i].diffuseMax;
			min = month[i].diffuseMin;
			t = month[i].ut;
			id = dataAdapter.insertData(tableName, max, min, t);
			
			if(id < 0){
				bad ++;
			}else{
				good++;
			}
		}
		
	}

	
	
	
	
	
	public void insertMonthExternal(DataPoint[] month){
		
	}
	public void insertDayExternal(DataPoint[] day){
		
	}

	public double[] getDifMax(DataPoint[] a){
		double[] dub = new  double[a.length];
		
		for(int i = 0 ; i < dub.length -1 ; i ++){
			dub[i] = a[i].diffuseMax;
	}
		return dub;
		
	}
	
	public double[][] getYearMax(){
		
		double[][] dubs = new double[12][];
  		
		

		// could either return an array of double arrays.  
		
		double[] monthofDub; 
		for(int k = 0; k <12 ; k++){
			monthofDub = dataAdapter.getDifMax(months[k]);
			
			
			if(monthofDub == null){
			
			}
			
			
			
			dubs[k] = monthofDub;
		}
		
		
		
		return dubs;
		
	}
	
	public double[] getYearMin(){
		
		
		
		
		return null;
		
	}
	
	
	public double[] getDifMin(DataPoint[] a){
		double[] dub = new  double[a.length];
		
		for(int i = 0 ; i < dub.length -1 ; i ++){
			dub[i] = a[i].diffuseMin;
	}
		return dub;
		
	}

	
	
	public void update(){
		//database = new SQLite(this);
	}
	@SuppressLint("NewApi")
	private DataPoint[] getMonth(int i ){
		
		int c= 1440  * (i -1);
		DataPoint[] a = this.mainArray.clone();
	
		DataPoint[] mon = java.util.Arrays.copyOfRange(a, c, c+1440);
			
		return mon;
	}
}
