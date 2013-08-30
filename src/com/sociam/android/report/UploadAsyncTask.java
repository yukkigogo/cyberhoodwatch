package com.sociam.android.report;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class UploadAsyncTask extends AsyncTask<String, Integer, Integer>{

	ProgressDialog dialog;
	Context context;
	String lat,lon;
	
	
	  public UploadAsyncTask(Context context){
		    this.context = context;
		}
	  
	@Override
	protected Integer doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
		      String fileName = params[0];
		      String lat = params[1];
		      String lon = params[2];
		      
		      Log.v("odebaki",lat);
		      
		      HttpClient httpClient = new DefaultHttpClient();
		      HttpPost httpPost = new HttpPost("http://sociamvm-yi1g09.ecs.soton.ac.uk/upandroid.php");
		      
		      ResponseHandler<String> responseHandler =new BasicResponseHandler();
		      MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
		      
		      // image post
		      File file = new File(fileName);
		      FileBody fileBody = new FileBody(file, "image/jpeg");
		      multipartEntity.addPart("f1", fileBody);
		      
		      // other post
		      
		      multipartEntity.addPart("lat", new StringBody(lat));
		      multipartEntity.addPart("lon", new StringBody(lon));
		     
		      
		      httpPost.setEntity(multipartEntity);
		      String response = httpClient.execute(httpPost, responseHandler);
		      Log.e("odebaki", response);
		      
		    
		      
		    } catch (ClientProtocolException e) {
		      e.printStackTrace();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		    
		    return 0;
	}

	  @Override
	  protected void onPostExecute(Integer result) {
	    if(dialog != null){
	      dialog.dismiss();
	    }
	  }
	  
	  @Override
	  protected void onPreExecute() {
	    dialog = new ProgressDialog(context);
	    dialog.setTitle("Please wait");
	    dialog.setMessage("Uploading...");
	    dialog.show();
	  }  
	
	
}
