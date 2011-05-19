package com.josuvladimir;

import java.lang.reflect.Method;



public class Among {
	public int mStringSize;
	public String mSearchString;
	public int subStringIndex;
	public int mResult;
	public Method mMethod;
	public MyProgram mOject;

	public Among (String string, int subString_idx, int result,	String methodname, MyProgram methodobject) {
		mStringSize = string.length();
		mSearchString = string;
		subStringIndex = subString_idx;
		mResult = result;
		mOject = methodobject;
		
		if (methodname.length() == 0) {
		    mMethod = null;
		} else {
		    try {
		    	mMethod = methodobject.getClass().
		    	getDeclaredMethod(methodname, new Class[0]);
		    } catch (NoSuchMethodException e) {
		    	mMethod = null;
		    }
		}
	}
};
