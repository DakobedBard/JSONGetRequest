package com.example.jsongetrequest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLite extends SQLiteOpenHelper{

	
	private static final String DATABASE_NAME ="darrs.db";
	private static final String TABLE_NAME = "DataTable";
	
	
	
	
	private static final String UID = "_id";
	private static final String UT = "ut";
	private static final String DF = "diffuseMax";
//	private static final String 
	
	
	
	private static final String Name = "name";
	private static final int DATABASE_VERSION =1;
	
	private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+UID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+Name +" VARCHAR(255))";
	private static final String DROP_TABLE = "DROP TABLE IF EXISTS " +TABLE_NAME;
	
	private Context context;
	
	
	
	public SQLite(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context = context;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		Message.message(context, "onCreate called");
		try{
			db.execSQL(CREATE_TABLE);
		
		onCreate(db);
				
		}catch(Exception e){
			
		}
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d("queer", "onUpgrade called");
		
		try{
		db.execSQL(DROP_TABLE);
		onCreate(db);
		}catch(Exception e){
			
		}
		
		
	}

}
