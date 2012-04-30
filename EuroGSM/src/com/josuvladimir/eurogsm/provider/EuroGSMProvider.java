package com.josuvladimir.eurogsm.provider;

import java.util.Arrays;

import com.josuvladimir.eurogsm.provider.EuroGSMContract.Shop;
import com.josuvladimir.eurogsm.provider.EuroGSMDatabase.Tables;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
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
		Log.v(TAG, "delete(uri=" + uri + ")");
		SQLiteDatabase db = mOpenHelper.getWritableDatabase();
		buildSimpleSelection(uri);
		getContext().getContentResolver().notifyChange(uri, null);
		return 0;
	}

	private void buildSimpleSelection(Uri uri) {
//		builder = new SelectionBuilder();
		int match = MATCHER.match(uri);
		switch (match) {
		case SHOP:
//			return builder.table(Tables.SHOP);
			break;
		case SHOP_ID:
			
			break;

		default:
			break;
		}
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
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		int match = MATCHER.match(uri);
		switch (match) {
		case SHOP:
			builder.setTables(Shop.TABLE_NAME);
			break;
		case SHOP_ID:
			long id = ContentUris.parseId(uri);
			selectionArgs = insertSelectionArg(selectionArgs, String.valueOf(id));
			builder.appendWhere(Shop._ID + "=?");
			builder.setTables(Shop.TABLE_NAME);
			break;
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
	private String[] insertSelectionArg(String[] selectionArgs, String arg){
		if(selectionArgs == null){
			return new String[]{arg};
		} else {
			int newLength = selectionArgs.length + 1;
			String[] newSelectionArgs = new String[newLength];
			newSelectionArgs[0] = arg;
			System.arraycopy(selectionArgs, 0, newSelectionArgs, 1, selectionArgs.length);
			return newSelectionArgs;
		}
	}
}
