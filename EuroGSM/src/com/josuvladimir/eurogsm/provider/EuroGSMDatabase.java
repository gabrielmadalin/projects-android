package com.josuvladimir.eurogsm.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class EuroGSMDatabase extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 0;
	private static final String DATABASE_NAME = "eurogsm.db";

	public EuroGSMDatabase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	interface Tables {
		String SHOP = "shop";
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	
		db.execSQL("CREATE TABLE " + Tables.SHOP + " (" + 
				BaseColumns._ID + "");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
