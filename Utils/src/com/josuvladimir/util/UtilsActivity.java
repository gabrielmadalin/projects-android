package com.josuvladimir.util;

import com.josuvladimir.util.EditTextValidator.Type;
import com.josuvladimir.util.ShakeEventListener.OnShakeListener;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class UtilsActivity extends Activity implements OnClickListener, OnShakeListener {
    private MediaPlayer mPlayer;
	private ShakeEventListener mShakeEventListener;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.main_btn_carousel).setOnClickListener(this);
        new EditTextValidator((EditText) findViewById(R.id.main_edit_text), Type.MAIL);
        mShakeEventListener = new ShakeEventListener(this,this);
		mPlayer = MediaPlayer.create(this, R.raw.i_love_u_baby);
//		mPlayer.prepareAsync();
    }

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mShakeEventListener.unregister();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.main_btn_carousel:
			loadCarousel();
			break;

		default:
			break;
		}
	}

	@Override
	public void onShake() {
		if (!mPlayer.isPlaying()) {
//			mPlayer.start();
		}
		loadCarousel();
	}

	private void loadCarousel() {
		Intent intent = new Intent(this, CarouselActivity.class);
		startActivity(intent);
	}
}