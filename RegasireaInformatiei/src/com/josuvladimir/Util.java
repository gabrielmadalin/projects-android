package com.josuvladimir;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Util {

	public static void log(String string) {
		System.out.println(string);
	}
	public static String getContent(String filePath) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(filePath);
		DataInputStream dataInputStream = new DataInputStream(fileInputStream);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
		String contentString = "";
		String lineString;
		while ((lineString  = bufferedReader.readLine()) != null) {
			contentString += lineString + " ";
		}
//		Util.log(contentString);
		fileInputStream.close();
		contentString = replaceDiacritics(contentString);
		return contentString;
	}
	public static String getFirstLine(String filePath) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(filePath);
		DataInputStream dataInputStream = new DataInputStream(fileInputStream);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
		String lineString;
		lineString  = bufferedReader.readLine();
		fileInputStream.close();
		lineString = replaceDiacritics(lineString);
		return lineString;
	}
	
	public static String replaceDiacritics(String contentString) {
		//*/
		contentString = contentString
		.replace("Ä‚", "A")
		.replace("Ä�", "a")
		.replace("Ă‚", "A")
		.replace("Ă˘", "a")
		.replace("ĂŽ", "A")
		.replace("Ă®", "a")
		.replace("Čš", "I")
		.replace("Č›", "i")
		.replace("Č�", "T")
		.replace("Č™", "t")
		.replace("»ö", "T")
		.replace("»õ", "t")
		.replace("≈û", "S")
		.replace("»ò", "S")
		////////////////
		.replace("ƒÇ", "A")
		.replace("ƒÉ", "a")
		.replace("√Ç", "A")
		.replace("√¢", "a")
		.replace("√é", "I")
		.replace("√Æ", "i")
		.replace("»ö", "T")
		.replace("≈£", "t")
		.replace("»ö", "T")
		.replace("»õ", "t")
		.replace("≈û", "S")
		.replace("»ò", "S")
		.replace("≈ü", "mSearchString");
		/*/
		contentString.replace("&#226;", "a");
		contentString.replace("&#238;", "i");
		contentString.replace("&#259;", "a");
		contentString.replace("&#350;", "mSearchString");
		contentString.replace("&#355;", "t");
		//*/
		return contentString;
	}
	public static String getData(long lastModified) {
		String dateString;
		long l = Long.decode(String.valueOf(lastModified));
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeInMillis(l);
		dateString = "" + calendar.get(GregorianCalendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.MONTH) + "/" +calendar.get(Calendar.YEAR); 
		return dateString;
	}
}
