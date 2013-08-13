package com.sociam.android;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class Downloader {
	
	final static String sociamurl = "http://sociamvm-yi1g09.ecs.soton.ac.uk";
	
	public static Bitmap getImageFromURL(String path){
		Bitmap bmp=null;		
		try {
			String url = sociamurl+path.substring(1);
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet hg = new HttpGet(url);
			HttpResponse httpResponse = client.execute(hg);
			if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				bmp = BitmapFactory.decodeStream(httpResponse.getEntity().getContent());
				hg.abort();
			}else{
				Log.e("sociam","fail - obtain image from server");
			}
			
		} catch (Exception e) {
		}
		
		return bmp;
	}
	
	
	
}
