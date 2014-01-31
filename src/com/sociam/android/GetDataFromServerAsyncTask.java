package com.sociam.android;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.sociam.android.MainActivity.GetDateFromCallback;

import android.os.AsyncTask;
import android.util.Log;
/**
 * This async task class is to obtain crime/message data as a csv file from the server.
 * @author yukki
 *@version 1
 */
public class GetDataFromServerAsyncTask extends AsyncTask<String, String, String>{

	
	private GetDateFromCallback callback;
	String response;
	public GetDataFromServerAsyncTask(GetDateFromCallback callback) {
		this.callback = callback;
		
	}
	
	@Override
	protected String doInBackground(String... params) {
		
		response = null;
	  	HttpClient httpClient = new DefaultHttpClient();
	  	HttpPost httpPost = new HttpPost("http://sociamvm-yi1g09.ecs.soton.ac.uk/androidcsv.php");
	  	
	    ResponseHandler<String> responseHandler =new BasicResponseHandler();
	    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	    	
	    	//setLatestLatLon();
	    	
	    try{
	    	multipartEntity.addPart("type",new StringBody(params[0]));
	    	
	    	multipartEntity.addPart("lat",new StringBody(params[1]));
	    	multipartEntity.addPart("lon",new StringBody(params[2]));

		    httpPost.setEntity(multipartEntity);
		    response=httpClient.execute(httpPost, responseHandler);
		    
	    }catch(Exception e){
	    	Log.e("sociam",e.getMessage());
	    }  
	 // Log.e("sociam", type+" "+response);
	  
	    return null;

	}
	
	
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		callback.onTaskDone(response);
	}
}
