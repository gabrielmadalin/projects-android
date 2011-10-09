package com.josuvladimir.util;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeEventListener implements SensorEventListener{
	private static final int MIN_FORCE = 10;
	private static final int MIN_DIRECTION_CHANGE = 3;
	@SuppressWarnings("unused")
	private static final int MIN_PAUSE_BETWEEN_DIRECTION_CHANGE = 3;
	private static final int MAX_TOTAL_DURATION_OF_SHAKE = 400;
	private long mFirstDirectionChangeTime = 0;
	private long mLastDirectionChangeTime;
	private long mDirectionChangeCount = 0;
	private float lastX = 0;
	private float lastY = 0;
	private float lastZ = 0;
	
	public ShakeEventListener(Context context, OnShakeListener listener) {
		SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
		setOnShakeListener(listener);
	}
	
	private OnShakeListener mShakeListener;
	public interface OnShakeListener {
		void onShake();
	}
	public void setOnShakeListener(OnShakeListener listener) {
		mShakeListener = listener;
	}
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		float x = event.values[SensorManager.DATA_X];
		float y = event.values[SensorManager.DATA_Y];
		float z = event.values[SensorManager.DATA_Z];
		float totalMovement = Math.abs(x + y + z - lastX - lastY - lastZ);
		if (totalMovement > MIN_FORCE) {
			long now = System.currentTimeMillis();
			if (mFirstDirectionChangeTime == 0) {
				mFirstDirectionChangeTime = now;
				mLastDirectionChangeTime = now;
			}
			long lastChangeWasAgo = now - mLastDirectionChangeTime;
			if (lastChangeWasAgo < MAX_TOTAL_DURATION_OF_SHAKE) {
				mLastDirectionChangeTime = now;
				mDirectionChangeCount++;
				lastX = x;
				lastY = y;
				lastZ = z;
				if (mDirectionChangeCount > MIN_DIRECTION_CHANGE) {
					long totalDuration = now - mFirstDirectionChangeTime;
					if (totalDuration < MAX_TOTAL_DURATION_OF_SHAKE) {
						mShakeListener.onShake();
						resetShakeParameters();
					}
				}
			} else {
				resetShakeParameters();
			}
		}
	}
	private void resetShakeParameters() {
		mFirstDirectionChangeTime = 0;
		mDirectionChangeCount = 0;
		mLastDirectionChangeTime = 0;
		lastX = 0;
		lastY = 0;
		lastZ = 0;
	}
}
