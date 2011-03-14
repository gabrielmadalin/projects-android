package com.josuvladimir.neuropsychologie;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import android.util.Log;

public class Application extends android.app.Application {
	
	public static final String LIST_ID	= "list_id";
	public static final String SUBLIST	= "sublist";	
	public static final String TAG = "Neyropsyhologie";
	
	public static Map<String, Integer> dataMap = new HashMap<String, Integer>();

	@Override
	public void onCreate() {
		super.onCreate();
		mapData();
	}



	@SuppressWarnings("rawtypes")
	public void mapData(){
		R.array name = new R.array();
		Class c = R.array.class;
		Field fields[] = c.getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String string = "R.array." + field.getName();
				int id = field.getInt(name);
				dataMap.put(string, id);
				Log.d(TAG, "Adding "+ string + " with value " + id);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkData(String string){
		return dataMap.containsKey(string);
	}
	public static int	getData(String string){
		return dataMap.get(string);
	}
}
