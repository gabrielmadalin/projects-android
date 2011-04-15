package com.josuvladimir.katzenshaker;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class Home extends Activity implements OnClickListener {
	MediaPlayer mPlayer;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.home_button).setOnClickListener(this);
        mPlayer = MediaPlayer.create(this, R.raw.i_love_u_baby);
    }
	@Override
	public void onClick(View v) {
		play();
	}
	private void play() {
		mPlayer.start();
	}
}