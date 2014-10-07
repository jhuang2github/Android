//
//  SplashScreenThreadHandlerActivity.java
//  ContactListView
//
//  Created by Jingshu Huang on 6/28/14.
//  Copyright (c) 2014 HuangImage. All rights reserved.
//
// Reference:
// http://www.41post.com/4619/programming/android-coding-a-loading-screen-part-2
//

package com.huangimage.applistviewedu.ui.splashscreen;

import android.os.Bundle;
import android.os.Handler;

public class SplashScreenThreadHandlerActivity extends SplashScreenActivity implements Runnable {
	private Thread mThread;
	private Handler mHandler; // To update the UI from a background thread.

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_splash_screen);

		onPreExecution();

		mHandler = new Handler(); // attached to the current thread.
		mThread = new Thread(this, "ProgressDialogThread");
		mThread.start();
	}

	@Override
	public void run() {
		doInForeground();
		onPostExecution();
	}

	private void onPreExecution() {
		createProgressDialog(this);
	}

	private void doInForeground() {
		loadData();
		publishProgress();
	}

	private void publishProgress() {
		// while (mFileCounter <= totalNeeded) {
		// ++mFileCounter;
		// mHandler.post(new Runnable() {
		// @Override
		// public void run() {
		// onUpdateProgressDialog(mFileCounter);
		// }
		// });
		// }
	}

	private void onPostExecution() {
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				dismissProgressDialog();
				startMainActivity(SplashScreenThreadHandlerActivity.this);
				finish(); // Close the current (splash screen) activity.
			}
		});

		// Try to kill the thread by interrupting its execution.
		synchronized (mThread) {
			mThread.interrupt();
		}
	}
}
