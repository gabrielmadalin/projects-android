package com.josuvladimir.directions;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.josuvladimir.util.EditTextValidator;
import com.josuvladimir.util.EditTextValidator.Type;

public class DirectionsActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        EditText editText = (EditText) findViewById(R.id.mail_email);
        new EditTextValidator(editText, Type.MAIL);
    }
}