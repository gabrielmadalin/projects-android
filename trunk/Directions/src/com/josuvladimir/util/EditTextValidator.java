package com.josuvladimir.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class EditTextValidator implements TextWatcher{
	public static final int EMPTY = 0;
	public static final int VALID = 1;
	public static final int INVALID = 2;
	EditText mEditText;
	Type	mType;
	
	public EditTextValidator(EditText editText, Type type) {
		mType = type;
		mEditText = editText;
		mEditText.addTextChangedListener(this);
	}

	public int check() {
		if (mEditText.getText().length() == 0) {
			return EMPTY;
		}
		if (checkString(mEditText.getText().toString(), mType)) {
			return VALID;
		}
		return INVALID;
	}

	private boolean checkString(String string, Type type) {
		if (string.length() == 0) {
			return false;
		}
		switch (type) {
		case MAIL:
			Pattern pattern = Pattern.compile("^\\D.+@.+\\.[a-z]+");
			Matcher matcher = pattern.matcher(string);
			if (matcher.matches()) {
				return true;
			} else {
				return false;
			}
		default:
			break;
		}
		return true;
	}

	public enum Type {
		MAIL
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		if (checkString(mEditText.getText().toString(), mType)) {
			mEditText.setTextColor(Color.BLACK);
		} else {
			mEditText.setTextColor(Color.RED);
		}
	}
}
