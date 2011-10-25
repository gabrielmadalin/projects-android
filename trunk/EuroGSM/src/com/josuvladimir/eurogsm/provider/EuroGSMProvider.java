package com.josuvladimir.eurogsm.provider;

import java.util.Arrays;

import com.josuvladimir.eurogsm.provider.EuroGSMContract.Shop;
import com.josuvladimir.eurogsm.provider.EuroGSMDatabase.Tables;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class EuroGSMProvider extends ContentProvider {
	
	private static final UriMatcher MATCHER = buildUriMatcher();
	private static final int SHOP = 100;
	private static final int SHOP_ID = 101;
	private static final String TAG = "EuroGSMProvider";
	private EuroGSMDatabase mOpenHelper;

	private static UriMatcher buildUriMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		String authority = EuroGSMContract.CONTENT_AUTHORITY;

		matcher.addURI(authority, Tables.SHOP, SHOP);
		matcher.addURI(authority, Tables.SHOP + "/*", SHOP_ID);
		
		return matcher;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		int match = MATCHER.match(uri);
		switch (match) {
		case SHOP:
			return Shop.CONTENT_TYPE;
		case SHOP_ID:
			return Shop.CONTENT_ITEM_TYPE;
		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		Context context = getContext();
		mOpenHelper = new EuroGSMDatabase(context);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		Log.v(TAG, "query(uri=" + uri + ", proj=" + Arrays.toString(projection) + ")");
		@SuppressWarnings("unused")
		final SQLiteDatabase database = mOpenHelper.getReadableDatabase();
		int match = MATCHER.match(uri);
		switch (match) {
		case SHOP:
		default:
			break;
		}
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
