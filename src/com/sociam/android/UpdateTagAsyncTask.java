package com.sociam.android;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

public class UpdateTagAsyncTask extends AsyncTask<String, Integer, String>{

	int currentver;
	SharedPreferences sp;
	
	public UpdateTagAsyncTask(Context context) {
		sp = PreferenceManager.getDefaultSharedPreferences(context);
		currentver = sp.getInt("tagver", 1);
	}
	
	@Override
	protected String doInBackground(String... params) {
		// check the latest id and if it old update
		
		if(checkNeedUpdateTags(currentver)){
			//update the local csv file
			
			
		}
		
		return null;
	}

	private boolean checkNeedUpdateTags(int current){
		
		int resnum=1;
		
		HttpClient client = new DefaultHttpClient();
	    HttpPost httpPost = new HttpPost("http://sociamvm-yi1g09.ecs.soton.ac.uk/tagmanager.php");
		
	    ResponseHandler<String> responseHandler =new BasicResponseHandler();
	    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	    
	   try {
		multipartEntity.addPart("type",new StringBody("1"));
	 
	   
	    httpPost.setEntity(multipartEntity);
	    
		String responce = client.execute(httpPost, responseHandler);
		resnum = Integer.parseInt(responce);
		
	   } catch (Exception e) {
			e.printStackTrace();
	   }	
		
	   
	   if(current>=resnum) return false;
	   else return true;
	}
	
}
