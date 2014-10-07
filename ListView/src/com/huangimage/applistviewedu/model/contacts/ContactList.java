//
//  ContactList.java
//  ContactListView
//
//  Created by Jingshu Huang on 6/28/14.
//  Copyright (c) 2014 HuangImage. All rights reserved.
//

package com.huangimage.applistviewedu.model.contacts;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ContactList extends ArrayList<Map<String, ?>> {
	private static final long serialVersionUID = 100000L;
	// JSON node names.
    private static final String TAG_CONTACTS = "contacts";

	public ContactList(JSONObject jsonObj) {
		try {
			JSONArray contacts = jsonObj.getJSONArray(TAG_CONTACTS);
			for (int i = 0; i < contacts.length(); ++i) {
				this.add(new Contact(contacts.getJSONObject(i)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
