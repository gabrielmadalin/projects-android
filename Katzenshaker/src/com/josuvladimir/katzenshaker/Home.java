package com.josuvladimir.katzenshaker;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Home extends Activity implements OnClickListener, SensorEventListener {
	private MediaPlayer mPlayer;
	private SensorManager mSensorManager;
	private float mLastAccel;
	private float mCurrentAccel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.home_button).setOnClickListener(this);
        mPlayer = MediaPlayer.create(this, R.raw.i_love_u_baby);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mCurrentAccel = SensorManager.GRAVITY_EARTH;
    }
	@Override
	public void onClick(View v) {
		play();
	}
	private void play() {
		try {
			if (!mPlayer.isPlaying()) {
				mPlayer.start();
//				mPlayer.prepareAsync();
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		mLastAccel = mCurrentAccel;
		mCurrentAccel = (float) Math.sqrt((double)(x*x + y*y + z*z));
		float delta = mCurrentAccel - mLastAccel;
		if (delta > 15) {
			play();
		}
	}
	@Override
	protected void onPause() {
		mSensorManager.unregisterListener(this);
		super.onPause();
	}
	@Override
	protected void onResume() {
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
		super.onResume();
	}
}