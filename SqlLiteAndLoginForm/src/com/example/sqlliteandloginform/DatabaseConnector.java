package com.example.sqlliteandloginform;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseConnector extends SQLiteOpenHelper {
	
private static final int Version =1;
private static final String firstName="FIRST_NAME";
private static final String lastName="LAST_NAME";
private static final String email="EMAIL";
private static final String phoneNumber="PHONE_NUMBER";
private static final String username="USERNAME";
private static final String password="PASSWORD";
private static final String databaseName="FORM";
private static final String tableName="USER_RECORDS";
private static final String id = "ID";
	
	public DatabaseConnector(Context context) {
		super(context, databaseName, null, Version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		String createTableSQL = "CREATE TABLE " + tableName + " (" + id +" INTEGER NOT NULL PRIMARY KEY, " + firstName +" TEXT, " + lastName
				+ " TEXT, " +email + " TEXT, " + phoneNumber + " TEXT, " + username + " TEXT, " + password + " TEXT)";
		Log.d("onCreate()", createTableSQL);
		database.execSQL(createTableSQL);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int arg1, int arg2) {
		
	}
	
	public void addRecord(String firstname, String lastname, String emailAddress, String phone, String uname, String pword ) {
		String insertSQL = "INSERT INTO " + tableName + " (" + firstName + ", " + lastName + " ," + email + " ," + phoneNumber + " ," + username + " ," + password + ") "
				+ "VALUES" + " ('" + firstname + "', '" + lastname + "', '" + emailAddress + "', '" + phone + "', '" + uname + "', '" + pword + "')" ;
		Log.d("addRecord()", insertSQL);
		SQLiteDatabase dataBase = this.getWritableDatabase();
		dataBase.execSQL(insertSQL);
		dataBase.close();
	}
	
	public List<String> getRecord(String uname, String pword) {
		List<String> recordList = new ArrayList<String>(); 
		SQLiteDatabase dataBase = this.getReadableDatabase();
		String getSQL = "SELECT * FROM " + tableName + " WHERE " + username + " = '" + uname + "' AND " + password + " = '" + pword + "'";
		 Cursor cursor = dataBase.rawQuery(getSQL , null);
		 Log.d("getRecord()", getSQL + "##Count = " + cursor.getCount());
		 cursor.moveToFirst();
		 String fName = cursor.getString(1);
		 String lName = cursor.getString(2);
		 String eMail = cursor.getString(3);
		 String ph = cursor.getString(4);
		 Log.d("getRecord()", "FirstName: " + fName + "LastName: " + lName + "Email: " + eMail + "Phone" + ph);
		 recordList.add(fName);
		 recordList.add(lName);
		 recordList.add(eMail);
		 recordList.add(ph);
		 dataBase.close();
		 return recordList;
	
	}
	

}
