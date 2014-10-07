//
//  ContactManager.java
//  ContactListView
//
//  Created by Jingshu Huang on 6/28/14.
//  Copyright (c) 2014 HuangImage. All rights reserved.
//

package com.huangimage.applistviewedu.model.contacts;

import java.util.HashMap;

import org.json.JSONObject;

public class ContactManager {
	public static final String kContactList1 = "ContactList1";

	private static final ContactManager sInstance = new ContactManager();
	private final HashMap<String, ContactList> mContactLists = new HashMap<String, ContactList>();

	public static ContactManager instance() {
		return sInstance;
	}

	private ContactManager() {
	}

	public void setContactList(String name, JSONObject jsonObj) {
		mContactLists.put(name, new ContactList(jsonObj));
	}

	public ContactList getContactList(String name) {
		return mContactLists.get(name);
	}
}
