//
//  Contact.java
//  ContactListView
//
//  Created by Jingshu Huang on 6/28/14.
//  Copyright (c) 2014 HuangImage. All rights reserved.
//

package com.huangimage.applistviewedu.model.contacts;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class Contact extends HashMap<String, String> {	
	private static final long serialVersionUID = 100001L;

	// JSON node names
	public static final String TAG_NAME = "name";
	public static final String TAG_EMAIL = "email";
	public static final String TAG_PHONE = "phone";
	public static final String TAG_PHONE_MOBILE = "mobile";

	public Contact(JSONObject o) {
		try {
			this.put(TAG_NAME, o.getString(TAG_NAME));
			this.put(TAG_EMAIL, o.getString(TAG_EMAIL));
            JSONObject phone = o.getJSONObject(TAG_PHONE);
			this.put(TAG_PHONE_MOBILE, phone.getString(TAG_PHONE_MOBILE));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
