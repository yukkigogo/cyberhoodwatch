package com.sociam.android.user;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.sociam.android.user.UserRegisterFragment.UserSetupFragmentCallBackEmail;

import android.os.AsyncTask;
import android.util.Log;

public class EmailAvaiableAsyncTask extends AsyncTask<String, Integer, Integer>{

	private UserSetupFragmentCallBackEmail fuscbk;
	private int avaiable=2;
	
	public EmailAvaiableAsyncTask(UserSetupFragmentCallBackEmail fc) {
		fuscbk = fc;
	}
	
	@Override
	protected Integer doInBackground(String... params) {

		try {
			
			String email = params[0];
			
		      HttpClient httpClient = new DefaultHttpClient();
		      HttpPost httpPost = new HttpPost("http://sociamvm-yi1g09.ecs.soton.ac.uk/personels.php");
		      
		      ResponseHandler<String> responseHandler =new BasicResponseHandler();
		      MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
 
		      multipartEntity.addPart("type", new StringBody("4"));
		      multipartEntity.addPart("email", new StringBody(email));
		      
		      httpPost.setEntity(multipartEntity);
		      
		      String response = httpClient.execute(httpPost, responseHandler);

		      if(response!=null){
		    	  Log.v("sociam",response);
		    	  if(response.equals("okay")) avaiable=1;
		    	  else if(response.equals("no")) avaiable=0;
		    	  else avaiable=2;
		      }else{
		    	  avaiable=2;
		      }
		      
			
		} catch (Exception e) {
			Log.e("sociam","email asynctask has problem EmailAvaialbeAsyncTask line 55");
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		fuscbk.onTaskDone(avaiable);
		
	}
	
}
