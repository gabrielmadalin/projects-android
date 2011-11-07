package com.josuvladimir.autosettings;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class AutoSettingsActivity extends Activity implements OnCheckedChangeListener {
    private TextView mTextView;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setOnCheckedChangeListener(this);
        mTextView = (TextView) findViewById(R.id.textView);
    }

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		int setting;
		int ringerMode;
		if (isChecked) {
			setting = AudioManager.VIBRATE_SETTING_ON;
			ringerMode = AudioManager.RINGER_MODE_NORMAL;
		} else {
			setting = AudioManager.VIBRATE_SETTING_OFF;
			ringerMode = AudioManager.RINGER_MODE_SILENT;
		}
		audioManager.setVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER, setting);
		audioManager.setRingerMode(ringerMode);

		int vibrateSetting = audioManager.getVibrateSetting(AudioManager.VIBRATE_TYPE_RINGER);
		switch (vibrateSetting) {
		case AudioManager.VIBRATE_SETTING_ON:
			mTextView.setText("Vibrate settings is on");
			break;
		case AudioManager.VIBRATE_SETTING_OFF:
			mTextView.setText("Vibrate settings is off");
			break;
		default:
			break;
		}
	}
}