package com.sociam.android.report;

import com.sociam.android.R;
import com.sociam.android.model.Crime;

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
		setListAdapter(((ReportActivity) getActivity()).getArrayAdapter());

	}

	

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Log.w("sociam","list clickkkkkk  "+ getListAdapter().getItem(position));
		
		switch (position) {
		case 0:
			if(getListAdapter().getItem(position).equals("Picture : No")){
				
			}else{
				PictureAlertDialogFragment padf = new PictureAlertDialogFragment();
				padf.show(getActivity().getSupportFragmentManager(), "sociam");
			}
			break;
			
		default:
			break;
		}
		
	}


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


