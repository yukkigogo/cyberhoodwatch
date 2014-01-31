package com.sociam.android.user;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.sociam.android.user.UserRegisterFragment.UserSetupFragmentCallBack;

import android.os.AsyncTask;
/**
 * This class (Async Task) is to check whether the username is available or not.
 * If someone already registered the username, the return the false.  
 * @author yukki
 * @version 1
 *
 */
public class UserAvaiableAsyncTask extends AsyncTask<String, Integer, Integer>{

	private UserSetupFragmentCallBack fuscbk;
	private int avaiable=2;
	
	public UserAvaiableAsyncTask(UserSetupFragmentCallBack fc) {
		fuscbk = fc;
	}
	
	@Override
	protected Integer doInBackground(String... params) {

		try {
			
			String username = params[0];
			
		      HttpClient httpClient = new DefaultHttpClient();
		      HttpPost httpPost = new HttpPost("http://sociamvm-yi1g09.ecs.soton.ac.uk/personels.php");
		      
		      ResponseHandler<String> responseHandler =new BasicResponseHandler();
		      MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
 
		      multipartEntity.addPart("type", new StringBody("3"));
		      multipartEntity.addPart("username", new StringBody(username));
		      
		      httpPost.setEntity(multipartEntity);
		      
		      String response = httpClient.execute(httpPost, responseHandler);

		      if(response!=null){
		    	  if(response.equals("okay")) avaiable=1;
		    	  else if(response.equals("no")) avaiable=0;
		    	  else avaiable=2;
		      }else{
		    	  avaiable=2;
		      }
		      
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		fuscbk.onTaskDone(avaiable);
		
	}
	
}
