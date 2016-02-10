package com.example.jsongetrequest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper{

	private static String NAME = "data";
	private static int version=1;
	private Context context;
	public SQLHelper(Context context){
		super(context,NAME, null, version);
		this.context = context;
	}
	
	
	public void onCreate(SQLite db){
		
	
		
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
