package com.josuvladimir;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Util {
//	private static final String DIACRITICS = "";

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
		getFirstLine(contentString);
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
		.replace("\u0103", "a")
		.replace("\u00e2", "a")
		.replace("\u00ee", "i")
		.replace("\u015f", "s")
		.replace("\u0163", "t");
		/*/
		contentString.replace("&#226;", "a");
		contentString.replace("&#238;", "i");
		contentString.replace("&#259;", "a");
		contentString.replace("&#350;", "s");
		contentString.replace("&#355;", "t");
		//*/
		/*
		 "A", //"�",
		 "&#226;" => "a", //"�",
		 "&#238;" => "i", //"�",
		 "&#206;" => "I", //"�",
		 "&#258;" => "A", //"A",
		 "&#259;" => "a", //"a",
		 "&#350;" => "S", //"S",
		 "&#351;" => "s", //"s",
		 "&#354;" => "T", //"T",
		 "&#355;" => "t", //"t",
		 "�" => "I", "�" => "i", "�" => "a", "�" => "A", "�" => "s", "a" => "a"
		 */		
		return contentString;
	}
}
