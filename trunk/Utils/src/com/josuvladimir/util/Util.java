package com.josuvladimir.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;

public class Util {

	private static final int CHAR = 128;

	public static final String TAG	= "UTIL";
	public static final String ID	= "id";
	public static final String TYPE	= "type";
	public static final String NAME	= "name";
	public static final String LATITUDE	= "latitude";
	public static final String LONGITUDE= "longitude";
	
	private static Random mRandom = new Random();
	public static int convertCoord(double d){
		return (int) (d * 1E6);
	}

	public static double convertCoord(int i){
		return (double) (i / 1E6);
	}
	
	public static String getStringUpperFirst(String string) {
		string = string.substring(0, 1).toUpperCase() + string.substring(1).toLowerCase();
		return string;
	}
	
	public static Bundle getExtras(GeoPoint point) {
		Bundle bundle = new Bundle();
		bundle.putInt(LATITUDE, point.getLatitudeE6());
		bundle.putInt(LONGITUDE, point.getLongitudeE6());
		return bundle;
	}

	public static GeoPoint getGeoPoint(Bundle bundle) {
		GeoPoint point = null;
		if (bundle != null && bundle.containsKey(LATITUDE) && bundle.containsKey(LONGITUDE)) {
			point = new GeoPoint(bundle.getInt(LATITUDE), bundle.getInt(LONGITUDE));
		}
		return point;
	}
	
	public static Uri addQuery(Uri uri, String name, String value){
		String prefix = "";
		if (uri != null && name != null && value!= null) {
			String query = uri.getQuery();
			if (query == null) {
				prefix = "?";
			} else {
				prefix = "&";
			}
			if(name.length() > 0 && value.length() > 0)
				uri = Uri.parse(uri + prefix + name + "=" + value);
		}
		return uri;
	}
	public static Uri addQuery(Uri uri, String name, long value) {
		return addQuery(uri, name, String.valueOf(value));
	}
	public static Uri addQuery(Uri uri, String name, float value) {
		return addQuery(uri, name, String.valueOf(value));
	}
	public static Uri addQuery(Uri uri, String name, double value) {
		return addQuery(uri, name, String.valueOf(value));
	}

	public static String roundCoord(String coord, int d) {
		d++;
		int i = coord.indexOf(".");
		i += d;
		if(i > d && coord.length() >= i ) {
			coord = coord.substring(0, i );
		}
		return coord;
	}
	public static ContentValues getContentValues(Uri uri) {
		ContentValues values = new ContentValues();
		try {
//			String encodeString = URLEncoder.encode(uri.toString());
			URI convertUri = new URI(uri.toString());
			List<NameValuePair> parse = URLEncodedUtils.parse(convertUri, HTTP.UTF_8);
			for (NameValuePair nameValuePair : parse) {
				values.put(nameValuePair.getName(), nameValuePair.getValue());
			}
		} catch (URISyntaxException e) {
			Log.e(TAG, "Error then trying to get query from uri:" + uri.toString());
			e.printStackTrace();
		}
		return values;
	}
	public static String encryptString(String string) {
		String result = "";
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(string.getBytes());
			byte [] bs = messageDigest.digest();
			
			char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
		    char[] hexChars = new char[bs.length * 2];
		    int v;
		    for ( int j = 0; j < bs.length; j++ ) {
		        v = bs[j] & 0xFF;
		        hexChars[j*2] = hexArray[v/16];
		        hexChars[j*2 + 1] = hexArray[v%16];
		    }
		    
			result = new String(hexChars);
		} catch (NoSuchAlgorithmException e) {
			Log.e(TAG, "Error when trying to encrypt password");
			e.printStackTrace();
		}
		return result;
	}

	public static String getDate(String string){
		String dateString;
		long l = Long.decode(string) * 1000;
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(l);
		dateString = "" + calendar.get(GregorianCalendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" +calendar.get(Calendar.YEAR); 
		return dateString;
	}
	public static String getStringCutPre(String string) {
		int i = string.indexOf(":");
		String newString = string;
		if (i >= 0) {
			newString = string.substring(i + 1);			
		}
		return newString;
	}
	public static String getString(String[]strings,String idxString) {
		String string = strings[0];
		int idx = 0;
		if (idxString == null) {
			idxString = "0";
		}
		try {
			idx = Integer.decode(idxString);//(idxString, 0);			
			string = strings[idx];
		} catch (NumberFormatException e) {
			Log.e(TAG, "Error decode number");
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			Log.e(TAG, "Error array out of bounds");
			e.printStackTrace();
		}
		return string; 
	}
	public static String toString(InputStream inputStream) {
		BufferedReader reader;
		StringBuilder builder = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(inputStream,HTTP.UTF_8));
			builder = new StringBuilder();
			for(String line = null; (line = reader.readLine()) != null;){
				builder.append(line).append("\n");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	@SuppressWarnings({ "rawtypes" })
	public static String toString(JSONObject object) {
		Iterator keys = object.keys();
		String string;
		String contentString = "";
		for (Iterator iterator = keys; iterator.hasNext();) {
			string = (String) iterator.next();
			try {
				contentString += string + "=" + object.getString(string);
				if (iterator.hasNext()) {
					contentString += "&";
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return contentString;
	}
	public static long getCurrentDate() {
		long currentTimeMillis = System.currentTimeMillis();
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(currentTimeMillis);
//		calendar.get(GregorianCalendar.)
		return currentTimeMillis;
	}
	public static boolean setTextOrHide(TextView textView,String string) {
		if (string == null || string.contains("null")) {
			textView.setVisibility(View.INVISIBLE);
			return false;
		} else {
			textView.setText(string);
			textView.setVisibility(View.VISIBLE);
			return true;
		}
	}
	public static ContentValues getContentValues(NamedNodeMap map){
		ContentValues values = new ContentValues();
		for (int i = 0; i < map.getLength(); i++) {
			Node node = map.item(i);
			values.put(node.getNodeName(), node.getNodeValue());
		}
		return values;
	}
	public static GeoPoint getGeoPoint(Location location) {
		GeoPoint point = null;
		if (location != null) {
			point = new GeoPoint(convertCoord(location.getLatitude()), convertCoord(location.getLongitude()));
		}
		return point;
	}
	public static void startActivityNavigation(Context context, Location location) {
		GeoPoint point = Util.getGeoPoint(location);
		Uri uri;
		uri = Uri.parse("http://maps.google.com/maps?daddr=" + Util.convertCoord(point.getLatitudeE6()) + "," + Util.convertCoord(point.getLongitudeE6()));
//		uri = Uri.parse("google.direction:q=" + Util.convertCoord(point.getLatitudeE6()) + "," + Util.convertCoord(point.getLongitudeE6()));
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		context.startActivity(intent);
	}

	public static String getRandomString(int lenght) {
		String string = new String();
		while (string.length() < lenght) {
			string += mRandom.nextInt(CHAR);
		}
		return string;
	}
}
