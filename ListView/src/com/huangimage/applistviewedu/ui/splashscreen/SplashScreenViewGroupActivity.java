//
//  SplashScreenThreadHandlerActivity.java
//  ContactListView
//
//  Created by Jingshu Huang on 6/28/14.
//  Copyright (c) 2014 HuangImage. All rights reserved.
//
// Reference:
// http://www.41post.com/4650/programming/android-coding-a-loading-screen-part-3
//
// TODO(jhuang):
// 1) With ImageView:
// http://custom-android-dn.blogspot.com/2013/01/listviewis-view-group-that-displays.html
// 2) ExpandableListView 
// http://custom-android-dn.blogspot.com/2013/01/how-to-create-custom-expandablelistview.html
//

package com.huangimage.applistviewedu.ui.splashscreen;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.huangimage.applistviewedu.R;
import com.huangimage.applistviewedu.model.Config;
import com.huangimage.applistviewedu.model.contacts.Contact;
import com.huangimage.applistviewedu.model.contacts.ContactManager;
import com.huangimage.applistviewedu.ui.ListItemDetailActivity;
import com.huangimage.applistviewedu.util.HttpConnection;
import com.huangimage.applistviewedu.util.JsonParser;

// Merge two activities are into one.
public class SplashScreenViewGroupActivity extends SplashScreenActivity {
	private ViewSwitcher mViewSwitcher;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new DataFetchTask().execute();
	}

	@Override
	public void onBackPressed() {
		if (mViewSwitcher.getDisplayedChild() == 0) {
			return;
		}

		super.onBackPressed();
	}

	protected void showListView() {
		// setContentView(R.layout.listview2);
		ListView lv = (ListView) ViewSwitcher.inflate(this, R.layout.listview2,
				null);
		mViewSwitcher.addView(lv);
		mViewSwitcher.showNext();

		ArrayList<Map<String, ?>> contacts = ContactManager.instance()
				.getContactList(ContactManager.kContactList1);
		String[] tags = new String[] { Contact.TAG_NAME, Contact.TAG_EMAIL,
				Contact.TAG_PHONE_MOBILE };
		int[] items = new int[] { R.id.name, R.id.email, R.id.mobile };
		ListAdapter adapter = new SimpleAdapter(this, contacts,
				R.layout.listview2_item, tags, items);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView txtView = (TextView) ((LinearLayout) view)
						.findViewById(R.id.name);
				String item = txtView.getText().toString();
				Intent i = new Intent(SplashScreenViewGroupActivity.this,
						ListItemDetailActivity.class);
				i.putExtra("item", item);
				startActivity(i);
			}
		});
	}

	private class DataFetchTask extends AsyncTask<Void, Integer, Boolean> {
		private TextView mTextViewProgress;
		private ProgressBar mProgressBar;

		public DataFetchTask() {
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mViewSwitcher = new ViewSwitcher(SplashScreenViewGroupActivity.this);
			View loadingView = ViewSwitcher.inflate(
					SplashScreenViewGroupActivity.this,
					R.layout.activity_splash_screen_loading_bar, null);
			mViewSwitcher.addView(loadingView);

			// Note: we find view using view switcher since it is the root.
			mTextViewProgress = (TextView) mViewSwitcher
					.findViewById(R.id.tv_progress);
			mProgressBar = (ProgressBar) mViewSwitcher
					.findViewById(R.id.pb_progressbar);
			mProgressBar.setMax(100);

			setContentView(mViewSwitcher); // Set view switcher as the current
											// view.
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			loadData();
			// publishProgress(mFileCounter);
			return true;
		}

		protected void loadData() {
			JsonParser parser = new JsonParser();
			JSONObject jsonObj = parser.getJsonObjFromUrl(Config.kUrl2,
					HttpConnection.POST);
			ContactManager.instance().setContactList(
					ContactManager.kContactList1, jsonObj);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			int progress = values[0];
			mProgressBar.setProgress(progress);
			mTextViewProgress
					.setText("Progress: " + Integer.toString(progress));
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			SplashScreenViewGroupActivity.this.showListView();
		}
	}

}

