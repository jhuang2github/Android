//
//  FileItem.java
//  FileBrowser
//
//  Created by Jingshu Huang on 4/28/14.
//  Copyright (c) 2014 HuangImage. All rights reserved.
//

package com.huangimage.appfilebroweredu;

public class FileItem implements Comparable<FileItem> {
	private String name;
	private String data;
	private String date;
	private String path;
	private String image;

	public FileItem(String n, String d, String dt, String p, String img) {
		name = n;
		data = d;
		date = dt;
		path = p;
		image = img;
	}

	public String getName() {
		return name;
	}

	public String getData() {
		return data;
	}

	public String getDate() {
		return date;
	}

	public String getPath() {
		return path;
	}

	public String getImage() {
		return image;
	}

	@Override
	public int compareTo(FileItem o) {
		if (this.name != null) {
			return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
		} else {
			throw new IllegalArgumentException();
		}
	}
}
