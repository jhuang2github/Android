//
//  FileListActivity.java
//  FileBrowser
//
//  Created by Jingshu Huang on 4/28/14.
//  Copyright (c) 2014 HuangImage. All rights reserved.
//

package com.huangimage.appfilebroweredu;

import java.io.File;
import java.sql.Date;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class FileListActivity extends ListActivity {
	private static final String kDefaultPath = Environment.getExternalStorageDirectory().getPath();
	private static final String kDirUpIcon = "directory_up";
	private static final String kDirIcon = "directory_icon";
	private static final String kFileIcon = "file_icon";

	private File currentDir;
	private FileArrayAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		currentDir = new File(kDefaultPath);
		fill(currentDir);
	}

	private void fill(File f) {
		File[] dirs = f.listFiles();
		this.setTitle("Current Dir: " + f.getName());

		List<FileItem> dir = new ArrayList<FileItem>();
		List<FileItem> fls = new ArrayList<FileItem>();
		try {
			for (File ff : dirs) {
				Date lastModDate = new Date(ff.lastModified());
				String dateModify = DateFormat.getDateTimeInstance().format(lastModDate);

				if (ff.isDirectory()) {
					File[] fbuf = ff.listFiles();
					int buf = (fbuf == null) ? 0 : fbuf.length;
					String numItem = (buf == 0) ? String.valueOf(buf) + " item" : String.valueOf(buf) + " items";
					dir.add(new FileItem(ff.getName(), numItem, dateModify, ff.getAbsolutePath(), kDirIcon));
				} else {
					String length = ff.length() + " Bytes";
					fls.add(new FileItem(ff.getName(), length, dateModify, ff.getAbsolutePath(), kFileIcon));
				}
			}
		} catch (Exception e) {
			Log.d("HuangImageDebug", "Error in loading files.");
			return;
		}

		Collections.sort(dir);
		Collections.sort(fls);
		dir.addAll(fls);
		if (!f.getName().equalsIgnoreCase(kDefaultPath)) {
			dir.add(0, new FileItem("..", "Parent Directory", "", f.getParent(), kDirUpIcon));
		}

		adapter = new FileArrayAdapter(FileListActivity.this, R.layout.listview_item, dir);
		this.setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		FileItem o = adapter.getItem(position);
		if (o.getImage().equalsIgnoreCase(kDirIcon) || o.getImage().equalsIgnoreCase(kDirUpIcon)) {
			onDirClicked(o);
		} else {
			onFileClicked(o);
		}
	}

	private void onDirClicked(FileItem o) {
		currentDir = new File(o.getPath());
		fill(currentDir);
	}

    private void onFileClicked(FileItem o) {
        //Toast.makeText(this, "Folder Clicked: "+ currentDir, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("GetPath", currentDir.toString());
        intent.putExtra("GetFileName", o.getName());
        setResult(RESULT_OK, intent);
        finish();
    }
}
