package com.josuvladimir.eurogsm.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class EuroGSMContract {
	
	public static final String CONTENT_AUTHORITY = "com.josuvladimir.eurogsm";
	private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
	
	
	public interface SyncColumns {
		String UPDATED = "updated";
	}
	
	interface ShopColumns {
		String NAME = "nume";
		String ADDRESS = "adresa";
		String CITY = "oras";
		String COUNTY = "judet";
		String PHONE = "telefon";
		String EMAIL = "email";
		String PROGRAMM = "orar" ;
		String IMAGE_URL = "url";
	}
	
	public static class Shop implements ShopColumns, BaseColumns {
		private static final String PATH_SHOP = "shop";
		public static final Uri CONTENT_URI = 
				BASE_CONTENT_URI.buildUpon().appendPath(PATH_SHOP).build();
		public static final String CONTENT_TYPE = 
				"vnd.android.cursor.dir/vnd.eurogsm.shop";
		public static final String CONTENT_ITEM_TYPE = 
				"vnd.android.cursor.item/vnd.eurogsm.shop";
	}
}
