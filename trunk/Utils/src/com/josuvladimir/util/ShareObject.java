package com.josuvladimir.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;


public class ShareObject {
	public static final String TYPE_IMAGE = "image/*";
	public static final String TYPE_TEXT  = "text/*";
	private Context mContext;
	private String mSubject;
	private String mText;
	@SuppressWarnings("unused")
	private Bitmap mBitmap;
	public ShareObject(Context context, String subject, String text) {
		mContext = context;
		mSubject = subject;
		mText = text;
		test();
	}
	public void share(){
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType(TYPE_IMAGE);
		intent.putExtra(Intent.EXTRA_SUBJECT, mSubject);
		intent.putExtra(Intent.EXTRA_TEXT, mText);
//		intent.putExtra(Intent.EXTRA_STREAM, mBitmap);
//		String imageUrlString = "http://www.osmoz.com/var/osmoz/storage/images/parfums/marques-de-designers/michalsky/michalsky-for-men/939810-1-eng-GB/Michalsky-for-Men_vignette_parfum_grande.jpg";
//		myWebView.loadDataWithBaseURL("file:///android_asset/", myHTML, "text/html", "UTF-8", "");
//		Uri uri = Uri.parse("file:///android_asset/" + myHTML);
//		intent.putExtra(Intent.EXTRA_STREAM, imageUrlString);
//		intent.putExtra(Intent.EXTRA_STREAM, Uri.parse("http://www.osmoz.com/var/osmoz/storage/images/parfums/marques-de-designers/michalsky/michalsky-for-men/939810-1-eng-GB/Michalsky-for-Men_vignette_parfum_grande.jpg"));
		mContext.startActivity(Intent.createChooser(intent, "Share"));
	}
	private void test() {
//		Uri uri = Uri.parse("http://m.facebook.com/sharer.php?u=" +
//	            "http://www.osmoz.com" + "&t=" + "this is test".replaceAll(" ","+"));
//	    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//	    mContext.startActivity(intent);
//		mText = "http://www.osmoz.com/var/osmoz/storage/images/parfums/marques-de-designers/michalsky/michalsky-for-men/939810-1-eng-GB/Michalsky-for-Men_vignette_parfum_grande.jpg";
//		Drawable drawable = mContext.getResources().getDrawable(R.drawable.icon);
//		mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.icon);
	}
}
