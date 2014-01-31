package com.sociam.android;


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

import com.sociam.android.MainMsgDetailFragmentReplyDialog.ReplyMessageFragmentCallback;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
/**
 * This class is used for creating async task to update a reply message to the server.
 * @author yukki
 *
 */
public class ReplyMessageAsyncTask extends AsyncTask<String, Integer, Integer> {

	ProgressDialog dialog;
	ReplyMessageFragmentCallback callback;
	Context context;
	SharedPreferences sp;
	
	/**
	 * Constructor 
	 * @param context MainActivity
	 * @param frag used for callback function in MainActivity
	 */
	public ReplyMessageAsyncTask(Context context,  ReplyMessageFragmentCallback frag) {
		this.context=context;
		sp = PreferenceManager.getDefaultSharedPreferences(context);
		this.callback=frag;
		
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		

		String parent_id = params[0];
		String user_id_code=params[1];
		String user_id=params[2];		
		String lat=params[3];
		String lon=params[4];
		String date_time=params[5];
		String message=params[6].replaceAll("\n", "");
		
		
		//Log.e("sociam", parent_id + " " + user_id+ " " +user_id_code+ " " +lat+ " " +lon+ " " +date_time+ " " +message );

		HttpClient client = new DefaultHttpClient();
		HttpPost hpost = new HttpPost("http://sociamvm-yi1g09.ecs.soton.ac.uk/replyupandroid.php");
		
	    ResponseHandler<String> responseHandler =new BasicResponseHandler();
	    MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	    
	    try {
	    	multipartEntity.addPart("parent_id", new StringBody(parent_id));
			multipartEntity.addPart("id_code", new StringBody(user_id_code));
			multipartEntity.addPart("user_id", new StringBody(user_id));
			multipartEntity.addPart("lat",new StringBody(lat));
			multipartEntity.addPart("lon",new StringBody(lon));
			multipartEntity.addPart("date", new StringBody(date_time));
			multipartEntity.addPart("message", new StringBody(message));
			
			hpost.setEntity(multipartEntity);
			String response = client.execute(hpost, responseHandler);
			//Log.e("sociam", response);
			
			String[] str = response.split("\n");
			Log.e("sociam","reply response" + response);
			String match = "rep_message_id";
		     Pattern p = Pattern.compile(match);
		     for(int i=0;i<str.length;i++){
		    	 
		    	 Matcher m = p.matcher(str[i]);
		    	 if(m.find()){
		    		 String[] str2 = str[i].split(",");
		    		
		    		 String past_msg = sp.getString("rep_message_id", "");
		    		 //Log.v("sociam","current msg ids : " + past_msg);
		    		 Editor e = sp.edit();
		    		 e.putString("rep_message_id", past_msg+","+str2[1]);
		    		 e.commit();
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
