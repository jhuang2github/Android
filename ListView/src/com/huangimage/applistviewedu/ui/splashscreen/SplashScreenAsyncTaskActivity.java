//
//  SplashScreenAsyncTaskActivity.java
//  ContactListView
//
//  Created by Jingshu Huang on 6/28/14.
//  Copyright (c) 2014 HuangImage. All rights reserved.
//
//
// Reference:
// http://www.41post.com/4588/programming/android-coding-a-loading-screen-part-1
//

package com.huangimage.applistviewedu.ui.splashscreen;

import android.os.AsyncTask;
import android.os.Bundle;

import com.huangimage.applistviewedu.R;

public class SplashScreenAsyncTaskActivity extends SplashScreenActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		new DataFetchTask().execute();
	}

	private class DataFetchTask extends AsyncTask<Void, Integer, Boolean> {

		public DataFetchTask() {
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			SplashScreenAsyncTaskActivity.this
					.createProgressDialog(SplashScreenAsyncTaskActivity.this);
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			SplashScreenAsyncTaskActivity.this.loadData();
			// publishProgress(mFileCounter);
			return true;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			SplashScreenAsyncTaskActivity.this
					.onUpdateProgressDialog(values[0]);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			SplashScreenAsyncTaskActivity.this.dismissProgressDialog();
			SplashScreenAsyncTaskActivity.this
					.startMainActivity(SplashScreenAsyncTaskActivity.this);
			finish(); // Close this activity.
		}
	}

}
