package com.sociam.android.user;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.sociam.android.user.TagRegisterFragment.TagRegisterFragmentCallBack;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SubmitUserRegisterAsyncTask extends AsyncTask<String, Integer, Integer>{

	Context context;
    ProgressDialog dialog;
    TagRegisterFragmentCallBack fc;
    boolean submitok;
    
	public SubmitUserRegisterAsyncTask(Context con, TagRegisterFragmentCallBack fc) {
		context=con;
		this.fc=fc;
	}
	
	
	@Override
	protected Integer doInBackground(String... params) {

		try {
			
				String username = params[0];
				String password = params[1];
				String tags = params[2];
				String email =params[3];
		
			
		      HttpClient httpClient = new DefaultHttpClient();
		      HttpPost httpPost = new HttpPost("http://sociamvm-yi1g09.ecs.soton.ac.uk/personels.php");
		      
		      ResponseHandler<String> responseHandler =new BasicResponseHandler();
		      MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
 
		      multipartEntity.addPart("type", new StringBody("0"));
		      multipartEntity.addPart("username", new StringBody(username));
		      multipartEntity.addPart("password", new StringBody(password));
		      multipartEntity.addPart("tags", new StringBody(tags));
		      multipartEntity.addPart("email", new StringBody(email));
		      
		      httpPost.setEntity(multipartEntity);
		      
		      String response = httpClient.execute(httpPost, responseHandler);

		      if(response!=null){
		    	  Log.v("sociam",response);
		    	  if(response.equals("ok")){
		    		submitok=true;
		    	  } else{
		    		 submitok=false; 
		    	  }
		      }else{
		    	  submitok=false;
		      }
		      
			
		} catch (Exception e) {
			Log.e("sociam","email asynctask has problem SubmitUserAvaialbeAsyncTask line 55");
		}
		
		return null;
	}
	
    @Override
    protected void onPreExecute() {
      dialog = new ProgressDialog(context);
      dialog.setTitle("Please wait");
      dialog.setMessage("Uploading to the server...");
      dialog.show();
    }  
	
	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		// if sucsess store ps  and dismiss the dialog
		if(submitok){
			fc.onTaskDone(true);
			
		}else{
		//else show dialog it fail then stay Register page encourage submit
			fc.onTaskDone(false);
			
		}
	}
	
		
}
