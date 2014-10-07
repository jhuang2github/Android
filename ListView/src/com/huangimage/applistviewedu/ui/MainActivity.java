//
//  MainActivity.java
//  ContactListView
//
//  Created by Jingshu Huang on 6/28/14.
//  Copyright (c) 2014 HuangImage. All rights reserved.
//

package com.huangimage.applistviewedu.ui;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.huangimage.applistviewedu.R;
import com.huangimage.applistviewedu.model.contacts.Contact;
import com.huangimage.applistviewedu.model.contacts.ContactManager;

public class MainActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// ListView 1: xml data.
		// Bind resource array to list adapter.
		// String[] products =
		// this.getResources().getStringArray(R.array.products);
		// ListAdapter adapter = new ArrayAdapter<String>(this,
		// R.layout.listview1_item, R.id.label, products);

		// ListView 2: json data.
		ListAdapter adapter = new SimpleAdapter(this, ContactManager.instance()
				.getContactList(ContactManager.kContactList1),
				R.layout.listview2_item, new String[] { Contact.TAG_NAME,
						Contact.TAG_EMAIL, Contact.TAG_PHONE_MOBILE },
				new int[] { R.id.name, R.id.email, R.id.mobile });

		setListAdapter(adapter);

		// Handle when item is clicked.
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				TextView txtView = (TextView) ((LinearLayout) view)
						.findViewById(R.id.name);
				String item = txtView.getText().toString();
				Intent i = new Intent(MainActivity.this,
						ListItemDetailActivity.class);
				i.putExtra("item", item);
				startActivity(i);
			}
		});
	}
}
