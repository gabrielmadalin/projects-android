package com.josuvladimir.autosettings;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;

public class AutoSettingsActivity extends PreferenceActivity implements OnPreferenceChangeListener {
    private static final String BOX_KEY = "box_vibrate";

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
//        toggleButton.setOnCheckedChangeListener(this);
//        mTextView = (TextView) findViewById(R.id.textView);
        setPreferenceScreen(createPreferenceHierarchy());
    }

	private PreferenceScreen createPreferenceHierarchy() {
		PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
		PreferenceCategory category = new PreferenceCategory(this);
		category.setTitle("Title");
		root.addPreference(category);
		CheckBoxPreference boxPreference = new CheckBoxPreference(this);
		boxPreference.setKey(BOX_KEY);
		boxPreference.setTitle("Vibrate");
		boxPreference.setSummary("Summary");
		boxPreference.setOnPreferenceChangeListener(this);
		root.addPreference(boxPreference);
		return root;
	}

	public void onCheckedChanged(boolean isChecked) {
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
			break;
		case AudioManager.VIBRATE_SETTING_OFF:
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		String key = preference.getKey();
		if (key.equalsIgnoreCase(BOX_KEY)) {
			boolean b = (Boolean) newValue;
			onCheckedChanged(b);
			return true;
		}
		return false;
	}
}