//
//  SplashScreenActivity.java
//  ContactListView
//
//  Created by Jingshu Huang on 6/28/14.
//  Copyright (c) 2014 HuangImage. All rights reserved.
//
// References:
// Parsing json/xml and show list view; Loading screen;
// Launching new activity on selected single list item.
//
// http://www.androidhive.info/2012/01/android-json-parsing-tutorial/
// http://mrbool.com/how-to-use-json-to-parse-data-into-android-application/28944
// http://www.androidhive.info/2011/10/android-listview-tutorial/
// 

package com.huangimage.applistviewedu.ui.splashscreen;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.huangimage.applistviewedu.model.Config;
import com.huangimage.applistviewedu.model.contacts.ContactManager;
import com.huangimage.applistviewedu.ui.MainActivity;
import com.huangimage.applistviewedu.util.HttpConnection;
import com.huangimage.applistviewedu.util.JsonParser;


abstract public class SplashScreenActivity extends Activity {
	private String mUrl = "";
	private ProgressDialog mProgressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mUrl = Config.kUrl2;
	}

	protected void createProgressDialog(Context context) {
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setTitle("Loading");
		mProgressDialog.setMessage("Please wait...");
		mProgressDialog.setCancelable(false);
		// If we know the loading amount, set it to determinate. Otherwise, set
		// it to indeterminate.
		// mProgressDialog.setIndeterminate(false);
		// mProgressDialog.setMax(100); // maximum number of items to load is
		// 100
		// mProgressDialog.setProgress(0); // current progress.

		mProgressDialog.show();
	}

	protected void onUpdateProgressDialog(Integer progress) {
		// mProgressDialog.setProgress(progress);
	}

	protected void dismissProgressDialog() {
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
	}

	protected void loadData() {
		JsonParser parser = new JsonParser();
		JSONObject jsonObj = parser
				.getJsonObjFromUrl(mUrl, HttpConnection.POST);
		ContactManager.instance().setContactList(ContactManager.kContactList1,
				jsonObj);
	}

	protected void startMainActivity(Context context) {
		Intent i = new Intent(context, MainActivity.class);
		startActivity(i);
		// setContentView(R.layout.listview2_item); // or just reuse the current
		// activity
	}
}
