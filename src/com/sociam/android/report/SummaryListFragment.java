package com.sociam.android.report;



import com.sociam.android.Crime;
import com.sociam.android.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SummaryListFragment extends ListFragment {
	
	Crime currentCrime;
	//ArrayAdapter<String> adapter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		currentCrime = ((ReportActivity) getActivity()).getCrime();
		
		//setAdapter();
		
		setListAdapter(((ReportActivity) getActivity()).getArrayAdapter());
		//getActivity().getSupportFragmentManager().beginTransaction().add(this, "SummaryFrag").commit();

	}


	
//	private void setAdapter(){
//		
//		ArrayList<String> details = new ArrayList<String>();
//		
//		
//		details.add("Picture : "+ (currentCrime.getFilepath() !=null ? "Yes" : "No"));
//		details.add("Category : "+currentCrime.getCategory());
//		if(currentCrime.getisCategoryText()) 
//			details.add(currentCrime.getCategoryText());			
//		
//		
//		
//		if(currentCrime.getLocationLatLng()){
//			details.add("Location : Here");
//		}else if(currentCrime.getIsAddress()){
//			details.add("Location : "+currentCrime.getAddress());
//		}
//
//		
//		
//		if(currentCrime.getIsNow() == true)
//			details.add("Time and Date : " +  "Now"); 
//		else { 
//			Time t = currentCrime.getDate();
//			String str =t.format("%d-%m-%Y %H:%M");
//			details.add("Time and Date : "+ str );
//		}
//		
//		if(currentCrime.getIsDateText())
//			details.add(currentCrime.getDateText());
//		
//		
//		String severity="Not Serious";
//		switch (currentCrime.getSeverity()){
//		case 88 :
//			break;
//		case 1 :
//			break;
//		case 2:
//			severity = "Serious";
//			break;
//		case 3 :
//			severity = "Very Serious";
//			break;
//		case 4:
//			severity = "Extremely Serious";
//			break;
//			
//		}		
//		details.add("How Serious? : " + severity);
//		
//				
//		adapter = new ArrayAdapter<String>(
//			        getActivity(),R.layout.list_row,R.id.list1,details);
//		
//		setListAdapter(adapter);
//		
//	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Log.w("sociam","list clickkkkkk  "+position);
		switch (position) {
		case 0:
			PictureAlertDialogFragment padf = new PictureAlertDialogFragment();
			padf.show(getActivity().getSupportFragmentManager(), "sociam");
			break;
			
		default:
			break;
		}
		
	}

	
	
	
	// testindesu 
	

	@SuppressLint("ValidFragment")
	private class PictureAlertDialogFragment extends DialogFragment{
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View view = inflater.inflate(R.layout.picture_dialog, null);
			ImageView image = (ImageView) view.findViewById(R.id.picture_image_indialog);
			Bitmap img = Bitmap.createScaledBitmap(currentCrime.getBitmap(),
					459,612,false);
					
			image.setImageBitmap(img);

			builder.setView(view);
			builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					PictureAlertDialogFragment.this.getDialog().dismiss();
					
				}
			});

			return builder.create();
			
		}
	}
	
}


