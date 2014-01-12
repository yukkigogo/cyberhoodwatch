package com.sociam.android;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
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
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.OpenableColumns;
import android.util.Log;

public class UpdateTagAsyncTask extends AsyncTask<String, Integer, String>{

	int currentver;
	SharedPreferences sp;
	Context context;
	DataApplication dapp;
	int resnum=1;
	
	public UpdateTagAsyncTask(Context con) {
		sp = PreferenceManager.getDefaultSharedPreferences(con);
		currentver = sp.getInt("tagver", 1);
		this.context=con;
		dapp = (DataApplication) context.getApplicationContext();
	}
	
	@Override
	protected String doInBackground(String... params) {
		// check the latest id and if it old update
			//Log.e("sociam", "UpdateTagAsyncTask execute on!");

		if(checkNeedUpdateTags(currentver)){
			Log.e("sociam", "UpdateTagAsyncTask execute on! - reading from the server");

			
			HttpClient client = new DefaultHttpClient();
		    HttpPost httpPost = new HttpPost("http://sociamvm-yi1g09.ecs.soton.ac.uk/tagmanager.php");
			
		    ResponseHandler<String> responseHandler =new BasicResponseHandler();
		    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
		    try {
				multipartEntity.addPart("type",new StringBody("0"));
				httpPost.setEntity(multipartEntity);

				String response1 = client.execute(httpPost, responseHandler);
				
				BufferedReader br = new BufferedReader(new InputStreamReader(
						new ByteArrayInputStream(response1.getBytes())));
				String currentLine;
				
				String FILENAME = "tag.csv";
				FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
				
				while((currentLine=br.readLine())!=null){
					String str = currentLine+"\n";
					fos.write(str.getBytes());
					
				}
				fos.close();
			
			} catch (Exception e) {
				Log.e("sociam", "problem with UpdateTagAsyncTask line 76");
			}
		    
		    //call method to update DataApplication's tags
		   if(dapp.setInitTags()){
		    	
			   // update the cvs file version
		       Editor e = sp.edit();
			   e.putInt("tagver", resnum);
			   e.commit();
			  }
		    
		    
		}
		
		return null;
	}

	private boolean checkNeedUpdateTags(int current){
		
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
		
	   Log.v("sociam", current +" and  " +resnum);
	   if(current>=resnum) return false; 
	   else return true;
		   
	}
	
}
