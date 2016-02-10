package com.example.jsongetrequest;

import java.sql.SQLException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/** 
 * 
 * The DataAdapter class is used in conjunction with the DataManager class to save the data in an SQLite database.  The DataAdapter 
 * class contains methods that format the strings that will be used SQL queries, and methods that execute the various SQL commands that create
 * and delete tables of data.  
 *  
 *
 */

public class DataAdapter {

	DataHelper helper;
	
	public DataAdapter(Context context)
	{
		helper = new DataHelper(context);
	}
	
	public void createNewTable(String tableName){
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(createTableStatement(tableName));
		
		db.close();
	}
	
	public void table(String tableName) {
		Cursor cursor;
		SQLiteDatabase db = helper.getWritableDatabase();
		
		// execSQL performs any SQL that does not return data
		db.execSQL(createTableStatement(tableName));
		cursor = db.rawQuery("select * from January", null);
		for(int i =0; i < cursor.getColumnNames().length; i ++ ){
			
		}
		db.close();
	}

	public String createTableStatement(String tableName){
		StringBuilder a = new StringBuilder();
		a.append("CREATE TABLE ");
		a.append(tableName);
		a.append("( " + helper.UID + "  INTEGER PRIMARY KEY AUTOINCREMENT, UT VARVCHAR(30), " + helper.DIFFUSE_MAX + " float, " + helper.DIFFUSE_MIN + " float );" ) ;
		
		return a.toString();
	}
	
	public void drop(String tableName){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL(createDropStatement(tableName));
		db.close();
	}
	
	// The createDropStatement formats the string that will be used in the SQL query responsible for deleting the table given by the input String tableName.
	public String createDropStatement(String tableName){
		
		StringBuilder a = new StringBuilder();
		a.append("DROP TABLE IF EXISTS "); 
		a.append(tableName);
		
		return a.toString();
	}

	public long insertData(String tableName, double difMax, double difMin, String ut)
	{
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DataHelper.DIFFUSE_MAX, difMax);
		contentValues.put(DataHelper.DIFFUSE_MIN, difMin);
		contentValues.put("ut",  ut);
		long id = db.insert(tableName,null, contentValues);
	
		return id;
	}

	// The method getDifmax returns an array of doubles by accessing the SQLite database.  
	
	public double[] getDifMax(String tableName){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		
		//Cursor cursor = db.rawQuery(returnSQL(tableName), null);
		
		try{
	
			Cursor cursor = db.rawQuery(returnSQL(tableName), null);
		
			int count = cursor.getCount();
			double[] a = new double[cursor.getCount()];

			int utc = 1;
			int dif = cursor.getColumnIndex("diffuseMax");
			
			cursor.moveToFirst();
		
			for(int k = 0 ; k < count  -1 ; k ++ ){
			
				a[k] = cursor.getDouble(dif);
				cursor.moveToNext();
			}
		
		cursor.close();
		
		return a;
		} catch(SQLiteException e){
			return null;
		}finally{
			
		}
	}
	
	public DataPoint[] getData(String tableName){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		
		String [] columns = { helper.DIFFUSE_MAX, helper.DIFFUSE_MIN,helper.UT};
		
		Cursor cursor = db.rawQuery(returnSQL(tableName), null);
		int count = cursor.getCount();
		DataPoint[] a = new DataPoint[cursor.getCount()];
	
		int utc = 1;
		int dif = cursor.getColumnIndex("diffuseMax");
		int difm = cursor.getColumnIndex("diffuseMin");
		
		Log.d("SQLite" , "the ut of the cursor is "  + Integer.toString(utc)); 
		Log.d("SQLite" , "the difMax of the cursor is "  + Integer.toString(dif)); 
		Log.d("SQLite" , "the difMin of the cursor is "  + Integer.toString(difm)); 
		
		cursor.moveToFirst();
		Log.d("SQLite" , "the string is equal to " + cursor.getString(utc));
		
		Log.d("SQLite" , "the position of the cursor is "  + Integer.toString(cursor.getPosition())); 
	
		for(int k = 0 ; k < count  -1 ; k ++ ){
			
			a[k] = new DataPoint(cursor.getString(utc), cursor.getDouble(dif), cursor.getDouble(difm));
		}
	
		cursor.close();
		db.close();
		
		return a;
	}
	
	private String returnSQL(String tableName){
		StringBuilder a = new StringBuilder();
		a.append("SELECT * FROM ");
		a.append(tableName);
		return a.toString();
	}
	
	
	public double[] forYear(){
		return null;
		
	}
	
		static class DataHelper extends SQLiteOpenHelper{
		
			private static final String UID = "_id";
			private static final String DATABASE_NAME ="sundata";
			private static final String TABLE_NAME = "January";
				
			private static final int DATABASE_VERSION = 1;
			private static final String DIFFUSE_MAX ="diffuseMax";
			private static final String DIFFUSE_MIN ="diffuseMin";
			private static final String UT = "ut";
	
			private static final String LOAD_TABLE =" SELECT * FROM ";
		
			private static final String CREATE_TABLE = "CREATE TABLE " +" March2 ( " + UID + "  INTEGER PRIMARY KEY AUTOINCREMENT, UT VARVCHAR(30), " + DIFFUSE_MAX + " float, " + DIFFUSE_MIN + " float );";
			private static final String DROP_TABLE = "DROP_TABLE IF EXISTS" + TABLE_NAME ;
		//private static final String INSERT = 
	
			private Context context;
	 
			public DataHelper(Context context){
				super(context,DATABASE_NAME, null, 2);
				this.context=context;
				Message.message(this.context, "constructor called");
			}

			@Override
			public void onCreate(SQLiteDatabase db) {
		
				Message.message(context, "onCreate called");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		try{
			Message.message(context, "onUpgrade called");
			db.execSQL(DROP_TABLE);
			onCreate(db);
		}catch(Exception e){
			Message.message(context, "" +e);
			
		}
	}
}

}