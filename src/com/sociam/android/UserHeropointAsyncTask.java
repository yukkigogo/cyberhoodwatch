package com.sociam.android;

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
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

/**
 * This class (extending AsyncTask) is used for obtaining hero point of the user from the server. 
 * 
 * @author yukki
 * @version 1
 */
public class UserHeropointAsyncTask extends AsyncTask<String, Integer, Integer>{

	int heropoint;
	SharedPreferences sp;
	
	public UserHeropointAsyncTask(Context con) {
		sp = PreferenceManager.getDefaultSharedPreferences(con);
	}
	
	
	@Override
	protected Integer doInBackground(String... params) {

		try {
			
			String username = params[0];
			
		      HttpClient httpClient = new DefaultHttpClient();
		      HttpPost httpPost = new HttpPost("http://sociamvm-yi1g09.ecs.soton.ac.uk/personels.php");
		      
		      ResponseHandler<String> responseHandler =new BasicResponseHandler();
		      MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
 
		      multipartEntity.addPart("type", new StringBody("5"));
		      multipartEntity.addPart("username", new StringBody(username));
		      
		      httpPost.setEntity(multipartEntity);
		      
		      String response = httpClient.execute(httpPost, responseHandler);

		      if(response!=null){
		    	  Editor e = sp.edit();		    	  
		    	  e.putInt("hero_point", Integer.parseInt(response));
		    	  e.commit();
		      }
		      
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	
}
