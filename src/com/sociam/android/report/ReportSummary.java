package com.sociam.android.report;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.google.android.gms.internal.cu;
import com.sociam.android.Crime;
import com.sociam.android.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Time;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ReportSummary extends Fragment {
	
	ListView lv;
	Crime currentCrime;
	Button btnSubmit;
	String user_id;
	@SuppressLint("NewApi")
	@Override
	public View onCreateView(
		LayoutInflater inflater, 
		ViewGroup container, 
		Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.report_summary, container, false);

		
		currentCrime = ((ReportActivity) getActivity()).getCrime();
		//set up background				
		if(currentCrime.getPicON()==1){
			LinearLayout layout = (LinearLayout) view.findViewById(R.id.smy);		  
			  layout.setBackground(currentCrime.getBitmapdrawable());
		}	
		setBtn(view);
		getID();

		return view;

	}

	


	private void getID() {
		// setup today's ID
		Time t = ((ReportActivity) getActivity()).getNow();
		user_id = ((ReportActivity) getActivity()).getSP().getString("uuid", "false")
				+"-"+Integer.toString(t.monthDay)+"-"+Integer.toString(t.month)+"-"
				+Integer.toString(t.year);
	
	
		Log.w("sociam", "id before hash "+ user_id);
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(user_id.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(Integer.toHexString((int) (b & 0xff)));
			}
			user_id = sb.toString();
			Log.w("sociam", "id after hash "+ user_id);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}




	private void setBtn(View view){
		
		btnSubmit = (Button) view.findViewById(R.id.smybtn);
		

		
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// show dialog for comfimation and upload
				new AlertDialog.Builder(getActivity())
				.setIcon(android.R.drawable.ic_dialog_info)
				.setTitle("Do you want to upload this incidence?")
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// upload to the server 
						
						UploadAsyncTask upload = (UploadAsyncTask) new UploadAsyncTask(
								getActivity(),new FragmentCallBack() {
									
									@Override
									public void onTaskDone() {
										getActivity().finish();
										
									}
								});
						
						upload.execute(
										currentCrime.getFilepath()==null ? "" : currentCrime.getFilepath(),
										user_id,
										currentCrime.getIdCode() ==true ? "1" : "0",
										currentCrime.getFilepath() !=null ? "1" : "0",
										currentCrime.getCategory(),
										currentCrime.getisCategoryText()==true ? "1" : "0",
										currentCrime.getisCategoryText()==true ? currentCrime.getCategoryText() : "",
										currentCrime.getLocationLatLng()==true ? "1" : "0",
										currentCrime.getIsAddress()==true ? "1":"0",
										Double.toString(currentCrime.getLat()),
										Double.toString(currentCrime.getLon()),
										currentCrime.getIsAddress()==true ? currentCrime.getAddress():"",
										currentCrime.getDate().format2445(),		
										currentCrime.getIsDateText()==true ? "1" : "0",
										currentCrime.getIsDateText()==true ? currentCrime.getDateText() : "",
										currentCrime.getSeverity()==88 ? "1": Integer.toString(currentCrime.getSeverity())							
										);
					
						
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Do nothing						
					}
				}).show();
				
			}
		});
	}
	
	public interface FragmentCallBack{
		public void onTaskDone();
	}
	
	
}
