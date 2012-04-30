package com.josuvladimir.eurogsm.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.josuvladimir.eurogsm.provider.EuroGSMContract.Shop;

public class EuroGSMDatabase extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 0;
	private static final String DATABASE_NAME = "eurogsm.db";
	private static final String TAG = "EuroGSMDatabase";

	public EuroGSMDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	interface Tables {
		String SHOP = Shop.TABLE_NAME;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	
		db.execSQL("CREATE TABLE " + Shop.TABLE_NAME + " ("
				+ Shop._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ Shop.NAME + " TEXT NOT NULL," 
				+ Shop.ADDRESS + " TEXT,"
				+ Shop.CITY + " TEXT,"
				+ Shop.COUNTY + " TEXT NOT NULL,"
				+ Shop.PHONE + " TEXT," 
				+ Shop.PROGRAMM + " TEXT," 
				+ Shop.IMAGE_URL + " TEXT," 
				+ Shop.EMAIL + " TEXT,"
				+ "UNIQUE (" + Shop._ID + ") ON CONFLICT REPLACE"
				+ ")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion != DATABASE_VERSION) {
			Log.w(TAG, "Destroying old data during update");
			db.execSQL("DROP TABLE IF EXIST " + Tables.SHOP);
			onCreate(db);
		}
	}
}
