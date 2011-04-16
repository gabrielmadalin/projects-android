package com.josuvladimir.katzenshaker;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.josuvladimir.util.ShakeEventListener;
import com.josuvladimir.util.ShakeEventListener.OnShakeListener;

public class Home extends Activity implements OnClickListener, OnShakeListener {
	private MediaPlayer mPlayer;
	private SensorManager mSensorManager;
	private ShakeEventListener mShakeEventListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.home_button).setOnClickListener(this);
        mPlayer = MediaPlayer.create(this, R.raw.i_love_u_baby);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mShakeEventListener = new ShakeEventListener();
        mShakeEventListener.setOnShakeListener(this);
    }
	@Override
	public void onClick(View v) {
		play();
	}
	private void play() {
		try {
			if (!mPlayer.isPlaying()) {
				Toast.makeText(this, "Start playing", Toast.LENGTH_LONG).show();
				mPlayer.start();
//				mPlayer.prepareAsync();
			} else {
				Toast.makeText(this, "Already playing", Toast.LENGTH_LONG).show();
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(mShakeEventListener);
		super.onPause();
	}
	@Override
	protected void onResume() {
        mSensorManager.registerListener(mShakeEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
		super.onResume();
	}
	@Override
	public void onShake() {
		play();
	}
}