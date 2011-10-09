package com.josuvladimir.util;

import java.util.Random;

public class Util {

	private static final int CHAR = 128;
	private static Random mRandom = new Random();

	public static String getRandomString(int lenght) {
		String string = new String();
		while (string.length() < lenght) {
			string += mRandom.nextInt(CHAR);
		}
		return string;
	}
}
