//
//  SplashScreenThreadHandlerActivity.java
//  ContactListView
//
//  Created by Jingshu Huang on 6/28/14.
//  Copyright (c) 2014 HuangImage. All rights reserved.
//
// Reference
// http://developer.android.com/guide/topics/ui/layout/listview.html
//

package com.huangimage.applistviewedu.ui.splashscreen;

import android.app.ActionBar.LayoutParams;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.huangimage.applistviewedu.ui.ListItemDetailActivity;

public class SplashScreenViewGroupCursorActivity extends ListActivity implements
		LoaderManager.LoaderCallbacks<Cursor> {

	// Contacts rows that we will retrieve.
	private static final String[] PROJECTION = new String[] {
			ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME };
	// Select criteria.
	private static final String SELECTION = "(("
			+ ContactsContract.Data.DISPLAY_NAME + " NOTNULL) AND ("
			+ ContactsContract.Data.DISPLAY_NAME + " != '' ))";

	private SimpleCursorAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		showProgressBar();
		setupCursorAdapter();

		// Prepare the loader. Either re-connect with an existing one, or start
		// a new one.
		getLoaderManager().initLoader(0, null, this);
	}

	private void showProgressBar() {
		ProgressBar progressBar = new ProgressBar(this);
		progressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, Gravity.CENTER));
		progressBar.setIndeterminate(true);
		getListView().setEmptyView(progressBar);

		ViewGroup root = (ViewGroup) findViewById(android.R.id.content);
		root.addView(progressBar);
	}

	private void setupCursorAdapter() {
		// Specify which columns go into which views
		String[] fromColumns = { ContactsContract.Data.DISPLAY_NAME };
		int[] toViews = { android.R.id.text1 }; // The TextView in
												// simple_list_item_1

		// Create an empty adapter we will use to display the loaded data.
		// We pass null for the cursor, then update it in onLoadFinished()
		mAdapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_1, null, fromColumns,
				toViews, 0);
		setListAdapter(mAdapter);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// Create a CursorLoader that will take care of creating a Cursor for
		// the data being displayed.
		return new CursorLoader(this, ContactsContract.Data.CONTENT_URI,
				PROJECTION, SELECTION, null, null);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		mAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		// This is called when the last Cursor provided to onLoadFinished()
		// above is about to be
		// closed. We need to make sure we are no longer using it.
		mAdapter.swapCursor(null);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String item = ((TextView)v).getText().toString();
		Intent i = new Intent(SplashScreenViewGroupCursorActivity.this,
				ListItemDetailActivity.class);
		i.putExtra("item", item);
		startActivity(i);
	}
}
