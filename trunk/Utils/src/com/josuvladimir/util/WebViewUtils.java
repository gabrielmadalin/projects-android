package com.josuvladimir.util;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewUtils {
	
	private OnWebViewEventListener mLoadingEventListener;
	class JavaScriptInterface {
		@SuppressWarnings("unused")
		private final String TAG = WebViewUtils.class.getName();

		public void showHTML(String html) {
			mLoadingEventListener.onSourceCodeReceived(html);
//			Log.i(TAG, html);
		}
	}
	
	public interface OnWebViewEventListener {
		void onSourceCodeReceived(String htmlString);
	}

	private static final String HTML_OUT = "HTMLOUT";

	public static String getSourceCode(Context context, String urlString, OnWebViewEventListener listener) {
		WebViewUtils utils = new WebViewUtils();
		utils.mLoadingEventListener = listener;
		final WebView webView = new WebView(context);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.addJavascriptInterface(utils.new JavaScriptInterface(), HTML_OUT);
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				webView.loadUrl("javascript:window.HTMLOUT.showHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
			}
			
		});
		webView.loadUrl(urlString);
		return urlString;
	}
}
