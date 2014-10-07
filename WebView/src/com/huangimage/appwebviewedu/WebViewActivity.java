//
//  WebViewActivity.java
//  WebView
//
//  Created by Jingshu Huang on 5/28/14.
//  Copyright (c) 2014 HuangImage. All rights reserved.
//
//Reference:
//http://javatechig.com/android/android-webview-example
//http://developer.android.com/guide/webapps/webview.html
//
//TODO:
// Http Basic authentication (unsafe since it just encode with Base64 and embedded in http header)
// http://blog.leocad.io/basic-http-authentication-on-android/
//

package com.huangimage.appwebviewedu;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewActivity extends Activity {
	private final String testHtmlText = "<html><body>test.</body></html>";
	private final String htmlText = "<body><p><strong>Authorization</strong> thumbnail: " +
            "&nbsp;<img src=\"pet2.png\">" +
            "<blockquote>From <a href=\"www.huangimage.com\">huangimage.com<a></blockquote></p></body>";
	private final String jsHtmlText = ""
			+ "<input type=\"button\" value=\"Say hello\" onClick=\"showAndroidToast('Hello Android!')\" />"
			+ "<script type=\"text/javascript\">"
			+ "  function showAndroidToast(toast) {"
            + "    AndroidJS.showToast(toast);"
            + "  }"
            + "</script>";

	private WebView webView;
	private EditText urlEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);

		urlEditText = (EditText) findViewById(R.id.urlField);

		webView = (WebView) findViewById(R.id.webView);
		webView.setWebViewClient(new MyWebViewClient()); // open in embedded view.
		webView.addJavascriptInterface(new WebAppInterface(this), "AndroidJS");  // For javascript.

		Button openUrlBtn = (Button) findViewById(R.id.goButton);
		openUrlBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				String url = urlEditText.getText().toString();
				if (!url.startsWith("http")) {
					url = "http://" + url;
				}
				if (validateUrl(url)) {
					WebSettings settings = webView.getSettings();
					settings.setJavaScriptEnabled(true);
					settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
					settings.setBuiltInZoomControls(true);
					webView.loadUrl(url);   // Load from url.
					//webView.loadData(testHtmlText, "text/html", "UTF-8");  // Load static data.
					//webView.loadData(jsHtmlText, "text/html", "UTF-8");  // Load static data that has js.
				}

				// Open in an external web browser.
				// Uri uri = Uri.parse(url);
				// Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				// startActivity(intent);
			}

			private boolean validateUrl(String url) {
				// TODO
				return true;
			}
		});

		TextView htmlTextView = (TextView) findViewById(R.id.html_text);
		htmlTextView.setText(Html.fromHtml(htmlText, new ImageGetter(), null));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// Check if the key event was the Back button and if there's history
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
			webView.goBack();
			return true;
		}
		// If it wasn't the Back key or there's no web page history, bubble up
		// to the default
		// system behavior (probably exit the activity)
		return super.onKeyDown(keyCode, event);
	}


	// WebViewClient helps to monitor events in a WebView. So override
	// shouldOverrideUrlLoading
	// to load pages in embedded webview instead of android default behavior of
	// opening pages in the
	// default web browser.
	private class MyWebViewClient extends WebViewClient {
		private ProgressDialog progressDialog;

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

		@Override
		public void onLoadResource(WebView view, String url) {
			if (progressDialog == null) {
				progressDialog = new ProgressDialog(WebViewActivity.this);
				progressDialog.setMessage("Loading...");
				progressDialog.show();
			}
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			try {
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
					progressDialog = null;
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}


	private class ImageGetter implements Html.ImageGetter {

		public Drawable getDrawable(String source) {
			if (!source.equals("pet2.png")) {
				return null;
			}

			int id = R.drawable.pet2;
			Drawable d = getResources().getDrawable(id);
			d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
			return d;
		}
	}

	private class WebAppInterface {
	    Context mContext;

	    WebAppInterface(Context c) {
	        mContext = c;
	    }

	    @JavascriptInterface
	    public void showToast(String toast) {
	        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
	    }
	}
}


//webView = new WebView(this);
//setContentView(webview);
//webview.setWebViewClient(new WebViewClient());

//getWindow().requestFeature(Window.FEATURE_PROGRESS);
//webview.getSettings().setJavaScriptEnabled(true);
//
//final Activity activity = this;
//webview.setWebChromeClient(new WebChromeClient() {
//public void onProgressChanged(WebView view, int progress) {
//// Activities and WebViews measure progress with different scales.
//// The progress meter will automatically disappear when we reach 100%
//activity.setProgress(progress * 1000);
//}
//});
//webview.setWebViewClient(new WebViewClient() {
//public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
//}
//});
//
