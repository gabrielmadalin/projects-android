package com.josuvladimir.katzenshaker;

import java.io.IOException;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.josuvladimir.util.ShakeEventListener;
import com.josuvladimir.util.ShareObject;
import com.josuvladimir.util.ShakeEventListener.OnShakeListener;

public class Home extends Activity implements OnClickListener, OnShakeListener, OnCompletionListener {
	private MediaPlayer mPlayer;
	private SensorManager mSensorManager;
	private ShakeEventListener mShakeEventListener;
	private Vibrator	mVibrator;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.home_button).setOnClickListener(this);
        mPlayer = MediaPlayer.create(this, R.raw.i_love_u_baby);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mVibrator	= (Vibrator) getSystemService(VIBRATOR_SERVICE);
        mShakeEventListener = new ShakeEventListener();
        mShakeEventListener.setOnShakeListener(this);
        mPlayer.setOnCompletionListener(this);
    }
	@Override
	public void onClick(View v) {
		new ShareObject(this, "test subject", "test text").share();
//		play();
	}
	private void play() {
		try {
			if (!mPlayer.isPlaying()) {
				Toast.makeText(this, "Start playing", Toast.LENGTH_LONG).show();
				mPlayer.start();
			} else {
				Toast.makeText(this, "Already playing", Toast.LENGTH_LONG).show();
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
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
		mVibrator.vibrate(30);
		play();
	}
	@Override
	public void onCompletion(MediaPlayer mp) {
		try {
			mPlayer.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}