//
//  MainActivity.java
//  FileBrowser
//
//  Created by Jingshu Huang on 4/28/14.
//  Copyright (c) 2014 HuangImage. All rights reserved.
//
// Reference:
// http://custom-android-dn.blogspot.com/2013/01/create-simple-file-explore-in-android.html
//

package com.huangimage.appfilebroweredu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	private static final int REQUEST_PATH = 1;

	private String curFileName;
	private EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		editText = (EditText) findViewById(R.id.editText);
	}

	public void getfile(View view) {
		Intent intent = new Intent(this, FileListActivity.class);
		startActivityForResult(intent, REQUEST_PATH);
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// See which child activity is calling us back.
		if (requestCode == REQUEST_PATH) {
			if (resultCode == RESULT_OK) {
				curFileName = data.getStringExtra("GetFileName");
				editText.setText(curFileName);
			}
		}
	}
}
