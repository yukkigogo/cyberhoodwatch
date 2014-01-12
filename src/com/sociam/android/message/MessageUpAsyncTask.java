package com.sociam.android.message;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import com.sociam.android.message.MessageActivity.MessageFragmentCallBack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

public class MessageUpAsyncTask extends AsyncTask<String, Integer, Integer> {

	ProgressDialog dialog;
	MessageFragmentCallBack callback;
	Context context;
	SharedPreferences sp;
	boolean postSuccess;
	
	public MessageUpAsyncTask(Context context,  MessageFragmentCallBack frag) {
		this.context=context;
		sp = PreferenceManager.getDefaultSharedPreferences(context);
		this.callback=frag;
		
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		

		String user_id=params[0];
		String user_id_code=params[1];
		String lat=params[2];
		String lon=params[3];
		String date_time=params[4];
		//Log.e("sociam", "result??" + date_time);
		String message=params[5];
		String tags=params[6];

		HttpClient client = new DefaultHttpClient();
		HttpPost hpost = new HttpPost("http://sociamvm-yi1g09.ecs.soton.ac.uk/messageupandroid.php");
		
	    ResponseHandler<String> responseHandler =new BasicResponseHandler();
	    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	    
	    try {
	    	multipartEntity.addPart("user_id", new StringBody(user_id));
			multipartEntity.addPart("id_code", new StringBody(user_id_code));
			multipartEntity.addPart("lat",new StringBody(lat));
			multipartEntity.addPart("lon",new StringBody(lon));
			multipartEntity.addPart("date", new StringBody(date_time));
			multipartEntity.addPart("message", new StringBody(message));
			if(tags!="") multipartEntity.addPart("tags",new StringBody(tags));
			
			hpost.setEntity(multipartEntity);
			String response = client.execute(hpost, responseHandler);
			Log.e("sociam", response);
			
			String[] str = response.split("\n");
			
			String match = "message_id";
		    
			Pattern p = Pattern.compile(match);
		     for(int i=0;i<str.length;i++){
		    	 //Log.e("sociam",str[i]);
		    	 Matcher m = p.matcher(str[i]);
		    	 if(m.find()){
		    		 String[] str2 = str[i].split(",");
		    		
		    		 if(str2[1].equals("false")){
		    			 postSuccess=false;

		    		 }else{
		    		 
		    		 String past_msg = sp.getString("message_id", "");
		    		 Editor e = sp.edit();
		    		 e.putString("message_id", past_msg+","+str2[1]);
		    		 e.commit();
		    		 }
		    	 }
		     }

			
		} catch (Exception e) {
			Log.e("sociam",e.getMessage().toString());
		}
		
		
		return 0;
		
	}

	
	  @Override
	  protected void onPostExecute(Integer result) {
	    if(dialog != null){
	      dialog.dismiss();
	      if(!postSuccess)
	    	  Toast.makeText(context, "You cannot post multiple post anonymously in 2 mins", Toast.LENGTH_LONG).show();     
	      callback.onTaskDone();
	    }
	  }
	  
	  @Override
	  protected void onPreExecute() {
	    dialog = new ProgressDialog(context);
	    dialog.setTitle("Please wait");
	    dialog.setMessage("Uploading to the server...");
	    dialog.show();
	  }  
}
