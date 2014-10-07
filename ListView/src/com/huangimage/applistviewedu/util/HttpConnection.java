package com.huangimage.applistviewedu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

public class HttpConnection {
	public static final int GET = 1;
	public static final int POST = 2;

	private String mUrl = "";
	private int mMethod = GET;
	private HttpClient mClient = null;

	public HttpConnection(String url, int method) {
		mUrl = url;
		mMethod = method;
		mClient = new DefaultHttpClient();
	}

    public String execute() {
        StringBuilder builder = new StringBuilder();

        try {
        	HttpResponse response = null;
        	if (mMethod == GET) {
        		response = mClient.execute(new HttpGet(mUrl));
        	} else {
        		response = mClient.execute(new HttpPost(mUrl));
        	}

            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));

                String line;
                while ((line = reader.readLine()) != null) {
          	      builder.append(line);
                }
            } else {
                Log.e("==>", "Failed to download file");
            }
        } catch (MalformedURLException e) {
      	    e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
  	}
}
