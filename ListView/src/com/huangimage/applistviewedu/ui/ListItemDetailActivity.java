//
//  ListItemDetailActivity.java
//  ContactListView
//
//  Created by Jingshu Huang on 6/28/14.
//  Copyright (c) 2014 HuangImage. All rights reserved.
//

package com.huangimage.applistviewedu.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.huangimage.applistviewedu.R;

public class ListItemDetailActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.listview2_item_detail);

		Intent i = getIntent();
		String item = i.getStringExtra("item");
		TextView txtProduct = (TextView) findViewById(R.id.label);
		txtProduct.setText(item);
	}
}
