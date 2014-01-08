package com.sociam.android;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.sociam.android.report.ReportSummary.FragmentCallBack;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class EvaluateAsyncTask extends AsyncTask<String, Integer, Integer>{

	ProgressDialog dialog;
	Context context;
	SharedPreferences sp;
	
	  public EvaluateAsyncTask(Context context){
		    this.context = context;
		    sp = PreferenceManager.getDefaultSharedPreferences(context);
		}
	  


	@Override
	protected Integer doInBackground(String... params) {
		try {
			
		      String crime_id = params[0];	      
		      String up_thumb = params[1];
		      String down_thumb = params[2];
		      String crimeORmsg = params[3]; 
		      
		      
		     for(int i=0;i<params.length;i++)		
		    	  Log.e("sociam",i+" "+params[i]); 
		      
		      HttpClient httpClient = new DefaultHttpClient();
		      HttpPost httpPost = new HttpPost("http://sociamvm-yi1g09.ecs.soton.ac.uk/evalandroid.php");
		      
		      ResponseHandler<String> responseHandler =new BasicResponseHandler();
		      MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
		      

		      // other post		      
		      if(crimeORmsg.equals("crime")){  
		    	  
		    	  multipartEntity.addPart("type", new StringBody("0"));
			      multipartEntity.addPart("crime_id", new StringBody(crime_id));
			      multipartEntity.addPart("up_thumb", new StringBody(up_thumb));
			      multipartEntity.addPart("down_thumb", new StringBody(down_thumb));
			      
			      httpPost.setEntity(multipartEntity);
			      String response = httpClient.execute(httpPost, responseHandler);
			     
			      // store the evaluated data
	    		 String past_eval_crime = sp.getString("eval_crime", "");
	    		 Editor e = sp.edit();
	    		 e.putString("eval_crime", past_eval_crime+","+crime_id);
	    		 e.commit();
		      
		      }else{	 
		    	  
		    	  multipartEntity.addPart("type", new StringBody("1")); 

			      multipartEntity.addPart("crime_id", new StringBody(crime_id));
			      multipartEntity.addPart("up_thumb", new StringBody(up_thumb));
			      multipartEntity.addPart("down_thumb", new StringBody(down_thumb));
			      
			      httpPost.setEntity(multipartEntity);
			      String response = httpClient.execute(httpPost, responseHandler);
			     
			      // store the evaluated data
	    		 String past_eval_crime = sp.getString("eval_msg", "");
	    		 Editor e = sp.edit();
	    		 e.putString("eval_msg", past_eval_crime+","+crime_id);
	    		 e.commit();

		    	  
		      
		      }
		    		  

		      
		      
		      
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
	    dialog.setMessage("Uploading to the server...");
	    dialog.show();
	  }  
	
	
}
