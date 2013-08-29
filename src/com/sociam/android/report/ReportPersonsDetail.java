package com.sociam.android.report;

import java.lang.reflect.Array;

import com.google.android.gms.internal.bu;
import com.google.android.gms.internal.cu;
import com.sociam.android.Crime;
import com.sociam.android.Persons;
import com.sociam.android.R;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.style.BulletSpan;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.view.View.OnClickListener;


@SuppressLint("ValidFragment")
public class ReportPersonsDetail extends Fragment {
	
	
	
	ViewPager pager;
	boolean sbtn5, sbtn2, sbtn3, sbtn4;
	Button btn1, btnS, btnD;
	ToggleButton btn2,btn3,btn4, btn5;
	Crime currentCrime;
	Persons persons;
	String cat1;
	int selectedItem=0;
	int colnum=0;
	int agenum=0;
	AlertDialog alertDialog;
	boolean suspects;
	
	public ReportPersonsDetail(boolean type) {
		// true - suspects
		this.suspects=type;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
												Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.report_people2, container, false);
		TextView tx = (TextView) view.findViewById(R.id.textview_people2); 
		tx.setText("Detail of Suspect(s)");
		setBtns(view);
		return view;
	}

	public void onStart() {
		super.onStart();
		currentCrime =  ((ReportActivity) getActivity()).getCrime();
		if(suspects) persons = currentCrime.getSuspects();
		else persons= currentCrime.getVictim();
	}


	


	private void setBtns(View v) {
	  btn1 = (Button) v.findViewById(R.id.people2_midBtn);
	  btnS = (Button) v.findViewById(R.id.people2_goSummary);
	  btnD = (Button) v.findViewById(R.id.people2_description);
	  
	  btn2 = (ToggleButton) v.findViewById(R.id.people2_RightBtmBtn);
	  btn3 = (ToggleButton) v.findViewById(R.id.people2_LeftBtmBtn);
	  btn4 = (ToggleButton) v.findViewById(R.id.people2_LeftTopBtn);
	  btn5 = (ToggleButton) v.findViewById(R.id.people2_RightTopBtn);
	
	  btn2.setTextOff("Number of People");
	  btn2.setTextOn("Number of People");
	  btn2.setText("Number of People");
	  
	  btn3.setTextOn("Dress Colour");
	  btn3.setTextOff("Dress Colour");
	  btn3.setText("Dress Colour");
	  
	  btn4.setTextOn("Ethics and Gender");
	  btn4.setTextOff("Ethics and Gender");
	  btn4.setText("Ethics and Gender");
	  
	  btn5.setTextOff("Age");
	  btn5.setTextOn("Age");
	  btn5.setText("Age");

	  
	  setListeners(btn1, 0);
	  setListeners(btnS, 99);
	  setListeners(btnD, 999);
	  
	  setToggleListeners(btn2,2);
	  setToggleListeners(btn3,3);
	  setToggleListeners(btn4,4);
	  setToggleListeners(btn5,5);
	  
	}
	
	
	private void setListeners(final Button btn, final int type){
		
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 0 = mid, 99=summary,  999=description
				switch (type) {
				case 0:
					Log.e("sociam","push the button");
					pager =(ViewPager) getActivity().findViewById(R.id.pager);
					pager.setCurrentItem(pager.getCurrentItem()+1);
					break;
				case 99:
					pager =(ViewPager) getActivity().findViewById(R.id.pager);
					pager.setCurrentItem(ReportActivity.SUMMARY_FRAG_NUM);
					break;
				
				case 999:
					addText();
					break;
			
			
				default:
					break;
				} 
			}
		});
	}
	
	private void setToggleListeners(final ToggleButton btn,final int num){
		
		btn.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, 
							boolean isChecked) {
						// 2:Top 3:Right 4:Bottom 5:Left
						if(isChecked){
							switch (num) {
							case 2:									
								//open dialong input gender and number
								spinnerNumOpen();
								break;
							case 3:								
								// open dress colour
								spinnerColOpen();								
								break;
							
							case 4:
								//spinnerEthics();
								FragmentManager fm = getActivity().getSupportFragmentManager();
								AlertFragment af = new AlertFragment();
								af.show(fm, "sociam");
								break;
							case 5:
								spinnerAgeOpen();
								break;

							default:
								break;
							}
							
							
						}else if(!isChecked){
							switch (num) {
							case 2:									
								//open dialong input gender and number
								
								break;
							case 3:								
								// open dress colour
								break;
							
							case 4:

								break;
							case 5:
								
								break;
							default:
								break;
							}
							
						}
						
					}

					

				});
	}
	
	
	private void spinnerEthics(){		
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		builder.setView(inflater.inflate(R.layout.ethics_gender_dialog, null))
			.setTitle("Ethics and Gender")
			.create();
		
		ArrayAdapter<String> gender = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_single_choice);
		gender.add("Unknown");
		gender.add("Male");
		gender.add("Female");
		gender.add("Male and Female");
		Spinner spinner = (Spinner) alertDialog.findViewById(R.id.spinner1);
		spinner.setAdapter(gender);

		ArrayAdapter<String> ethics = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_single_choice);
		ethics.add("Unknown");
		ethics.add("White");
		ethics.add("Black");
		ethics.add("Asian");
		//Spinner spinner2 = (Spinner) getActivity().findViewById(R.id.spinner2);
		//pinner2.setAdapter(ethics);
		
		
		Button eth_btn = (Button) getActivity().findViewById(R.id.ethics_button1);
		eth_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				alertDialog.dismiss();
				
			}
		});		
		
		alertDialog.show();
		
	}

	
	private class AlertFragment extends DialogFragment {
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
		
			View view = inflater.inflate(R.layout.ethics_gender_dialog, container);
			getDialog().setTitle("Ethics and Gender");
			
			final ArrayAdapter<String> gender = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_list_item_single_choice);
			gender.add("Unknown");
			gender.add("Male");
			gender.add("Female");
			gender.add("Male and Female");
			Spinner spinner = (Spinner) view.findViewById(R.id.spinner1);
			spinner.setAdapter(gender);
			spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
					Log.w("sociam","gender : " + gender.getItem(position));
					persons.setGender(gender.getItem(position));
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
			});
			
			final ArrayAdapter<String> ethics = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_list_item_single_choice);
			ethics.add("Unknown");
			ethics.add("White");
			ethics.add("Black");
			ethics.add("Asian");
			Spinner spinner2 = (Spinner) view.findViewById(R.id.spinner2);
			spinner2.setAdapter(ethics);
			spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int position, long arg3) {
						Log.w("sociam","ethics : "+ ethics.getItem(position));
						persons.setEthics(ethics.getItem(position));
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
				}
				
			});
			
			Button btn = (Button) view.findViewById(R.id.ethics_button1);
			btn.setOnClickListener(new OnClickListener() {				
				@Override
				public void onClick(View v) {
					dismiss();					
				}
			});
			
			
			return view;
		}	
			
			
		
	}
	
	private void spinnerAgeOpen() {
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_single_choice);
		
			adapter.add("Under 16");
			adapter.add("15 - 20");
			adapter.add("20 - 25");
			adapter.add("25 - 30");
			adapter.add("30 - 40");
			adapter.add("40 - 50");
			adapter.add("50 - 60");
			adapter.add("60 - 70");
			adapter.add("Over 70");
		Builder dialog = new AlertDialog.Builder(getActivity());
					
					dialog.setIcon(android.R.drawable.ic_dialog_info);
					dialog.setTitle("Age Range?");
					dialog.setSingleChoiceItems(adapter, colnum, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
				            colnum=which;
							persons.setAge(adapter.getItem(colnum));
							Log.w("sociam","age  : "+ persons.getAge());
				            alertDialog.dismiss();
							
						}
					});
			alertDialog = dialog.create();
			alertDialog.show();	

	
	}
	
	
	private void spinnerNumOpen() {
		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(
				getActivity(), android.R.layout.simple_list_item_single_choice);
		
		for(int i=1;i<=15;i++) adapter.add(i);
		
		Builder dialog = new AlertDialog.Builder(getActivity());
		
				dialog.setIcon(android.R.drawable.ic_dialog_info);
				dialog.setTitle("How many People?");
				dialog.setSingleChoiceItems(adapter, selectedItem, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
			            selectedItem = which;
			            persons.setNum(selectedItem+1);
			            Log.w("sociam","number  : "+ persons.getNum());
			            alertDialog.dismiss();
						
					}
				});
		alertDialog = dialog.create();
		alertDialog.show();
	}
	
	private void spinnerColOpen(){
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_single_choice);

			adapter.add("Unkown");
			adapter.add("Black");
			adapter.add("Gray");
			adapter.add("Blue");
			adapter.add("Green");
			adapter.add("Red");
			adapter.add("Yellow");
			adapter.add("Purple");
			adapter.add("Orange");
			adapter.add("Pink");
			adapter.add("Other");
			
			Builder dialog = new AlertDialog.Builder(getActivity());
			
			dialog.setIcon(android.R.drawable.ic_dialog_info);
			dialog.setTitle("Cloths Colour?");
			dialog.setSingleChoiceItems(adapter, colnum, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
		            colnum=which;
					persons.setDressCol(adapter.getItem(colnum));
		            Log.w("sociam","col : "+ persons.getDressCol());
		            alertDialog.dismiss();
					
				}
			});
	alertDialog = dialog.create();
	alertDialog.show();	
			
			
	}


	
	
	
	private void addText(){		
		final EditText eText = new EditText(getActivity());
		
		if(persons.getisText()){
			String str = persons.getText();
			eText.setText(str, TextView.BufferType.EDITABLE);
		}
		
		new AlertDialog.Builder(getActivity())
		.setIcon(android.R.drawable.ic_dialog_info)
		.setTitle("Input Description")
		.setView(eText)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(eText.getText().toString().length()>0){
					persons.setisText(true);
					persons.setText(eText.getText().toString());
				}else{
					persons.setisText(false);
					persons.setText("");
				}
				
				// out the input to toast at the moment
				Toast.makeText(getActivity(), eText.getText().toString()
						, Toast.LENGTH_LONG).show();
			}
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {						
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// Do nothing
				}
		}).show();
	}
	
}
