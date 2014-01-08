package com.sociam.android.report;

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
import android.widget.Toast;

public class UploadAsyncTask extends AsyncTask<String, Integer, Integer>{

	ProgressDialog dialog;
	Context context;
	String lat,lon;
	SharedPreferences sp;
	private FragmentCallBack fragmentcallback;
	boolean postSuccess = true;
	
	  public UploadAsyncTask(Context context, FragmentCallBack frag){
		    this.context = context;
		    sp = PreferenceManager.getDefaultSharedPreferences(context);
		    this.fragmentcallback=frag;
		}
	  


	@Override
	protected Integer doInBackground(String... params) {
		// TODO Auto-generated method stub
		try {
			
		      String fileName = params[0];	      
		      String user_id = params[1];
		      String id_code = params[2];
		      String pic_on = params[3];
		      String category = params[4];
		      String is_cat_text = params[5];
		      String cat_text = params[6];
		      String is_loc_latlon = params[7];
		      String is_address = params[8];
		      String lat = params[9];
		      String lon = params[10];
		      String address = params[11];
		      String date_time = params[12];
		      String is_date_text = params[13];
		      String date_text  = params[14];
		      String severity = params[15];
 
		
		      
		      HttpClient httpClient = new DefaultHttpClient();
		      HttpPost httpPost = new HttpPost("http://sociamvm-yi1g09.ecs.soton.ac.uk/upandroid.php");
		      
		      ResponseHandler<String> responseHandler =new BasicResponseHandler();
		      MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
		      
		      // image post
		      if(fileName!=""){
		    	 // File file = new File(fileName);
		      	File file = new File(Environment.getExternalStorageDirectory().getPath()+"/CrimeTips/"+fileName);  
		      	FileBody fileBody = new FileBody(file, "image/jpeg");
		    	  multipartEntity.addPart("f1", fileBody);
		    	 // Log.e("sociam", fileBody.toString());
		      }
		      // other post		      
		      multipartEntity.addPart("user_id", new StringBody(user_id));
		      multipartEntity.addPart("id_code", new StringBody(id_code));
		      multipartEntity.addPart("pic_on", new StringBody(pic_on));
		      multipartEntity.addPart("category", new StringBody(category));
		      multipartEntity.addPart("is_cat_text", new StringBody(is_cat_text));
		      multipartEntity.addPart("cat_text", new StringBody(cat_text));
		      multipartEntity.addPart("is_loc_latlon", new StringBody(is_loc_latlon));
		      multipartEntity.addPart("is_address", new StringBody(is_address));
		      multipartEntity.addPart("lat", new StringBody(lat));
		      multipartEntity.addPart("lon", new StringBody(lon));		     
		      multipartEntity.addPart("address", new StringBody(address));
		      multipartEntity.addPart("date", new StringBody(date_time));
		      multipartEntity.addPart("is_date_text", new StringBody(is_date_text));
		      multipartEntity.addPart("date_text", new StringBody(date_text));
		      multipartEntity.addPart("severity", new StringBody(severity));

		      
		      
		      httpPost.setEntity(multipartEntity);
		      //
		      String response = httpClient.execute(httpPost, responseHandler);
		      //Log.e("odebaki", response);
		      String[] str = response.split("\n");
		    
		     String match = "crimeid";
		     Pattern p = Pattern.compile(match);
		     for(int i=0;i<str.length;i++){
		    	 Log.e("sociam",str[i]);
		    	 Matcher m = p.matcher(str[i]);
		    	 if(m.find()){
		    		 
		    		 String[] str2 = str[i].split(",");
		    		 Log.e("sociam", "crime num "+ str2[1]);
		    		 
		    		 if(str2[1].equals("false")){
		    			 postSuccess=false;
		    		 }else{
		    			 String crime_ids = sp.getString("crime_id", "");
		    			 Editor e = sp.edit();
		    			 e.putString("crime_id", crime_ids+","+str2[1]);
		    			 e.commit();
		    	 }
		    	 }
		     }
		     
		     Log.e("sociam","post crimes : ");
		      
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
	      if(!postSuccess){
	    	  Toast.makeText(context, "You cannot post multiple post anonymously in 2 mins", Toast.LENGTH_LONG).show();
	      }
	      fragmentcallback.onTaskDone();
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
