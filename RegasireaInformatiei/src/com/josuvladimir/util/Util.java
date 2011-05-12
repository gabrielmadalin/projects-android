package com.josuvladimir.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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
	public static String replaceDiacritics(String contentString) {
		
		contentString.replace("\u015f", "s");
		contentString.replace("\u0163", "t");
		contentString.replace("\u0103", "a");
		contentString.replace("\u00e2", "a");
		contentString.replace("\u00ee", "i");
		return contentString;
	}
}
