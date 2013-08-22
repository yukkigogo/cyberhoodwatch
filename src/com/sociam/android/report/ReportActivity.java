package com.sociam.android.report;

import java.util.ArrayList;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.ToggleButton;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.sociam.android.Crime;
import com.sociam.android.R;


/*
 * when you add another fragment
 * 0. set footer_xml
 * 
 * 1. this java
 * add variable, this.setFooter() , this.initbutton();
 * 
 * 2.MyFragmentStatePagerAdapter
 * getItem, pagecount
 * 
 */
@SuppressLint("NewApi")
public class ReportActivity extends FragmentActivity {

	public static final int SUMMARY_FRAG_NUM = 11; 
	
	// store crime data
	private Crime crime;
	private MyFragmentStatePagerAdapter myAdapter;
	ViewPager pager;
	Button[] btns = new Button[8];
	
	// ToggleButton for Category2 and cat
	ToggleButton frag3btn2,frag3btn3,frag3btn4,frag3btn5;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_main);
		
		crime = new Crime();
		
		setUpView();
		setFooter();
	}
	

	// implement pageChangeLitener
	private void setUpView(){
		pager = (ViewPager) findViewById(R.id.pager);
		myAdapter = new MyFragmentStatePagerAdapter(getSupportFragmentManager());
		
		pager.setAdapter(myAdapter);
		pager.setCurrentItem(0);
		initButton();
	}

	private void setFooter() {
		pager.setOnPageChangeListener(new OnPageChangeListener() {			
			@Override
			public void onPageSelected(int position) {
				switch(position){
				case 0: setBtnInFooter(0);
						break;
				case 1:	setBtnInFooter(1);
						break;	
				case 2:	setBtnInFooter(1);
						initFrag3Button();
						setTexts();
						if(crime.getCat2Def()){
							allFrag3Off();
							crime.setCat2Def(false);
						}						
						break;						
				case 3:	setBtnInFooter(2);
						break;
				case 4:	setBtnInFooter(2);
						break;		
				case 5:	setBtnInFooter(3);
						break;		
				case 6:	setBtnInFooter(3);
						break;		
				case 7:	setBtnInFooter(4);
						break;		
				case 8:	setBtnInFooter(5);
						break;		
				case 9:	setBtnInFooter(6);
						break;		
				case 10:setBtnInFooter(7);
						break;		

				}
				
			}
			
			// get currentItem de page no zokusei wo set suru
			//http://stackoverflow.com/questions/8117523/how-can-i-get-page-number-in-view-pager-for-android

	

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		

		
	}

	private void setBtnInFooter(int i){
		for(int j=0;j<btns.length;j++){
			if(i==j){
				String num = Integer.toString(i+1);
				setBtn(btns[j], num, 60, 60, true);
				
			}else{
				setBtn(btns[j], "", 20, 20, false);
				
			}
				
		}
		
	}
	
	@SuppressLint("NewApi")
	private void  setBtn(Button btn, String text, int h, int w,boolean select){
		Resources res = getResources();
		Drawable main = res.getDrawable(R.drawable.rounded_cell);
		Drawable mini = res.getDrawable(R.drawable.rounded_mini);
		if(select) btn.setBackground(main);
		else btn.setBackground(mini);
		btn.setText(text);
		btn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
		btn.setHeight(h);
		btn.setWidth(w);
	}
	
	private void initButton() {
		// for footer
		btns[0] = (Button) findViewById(R.id.btn0);
		btns[1] = (Button) findViewById(R.id.btn1);
		btns[2] = (Button) findViewById(R.id.btn2);
		btns[3] = (Button) findViewById(R.id.btn3);
		btns[4] = (Button) findViewById(R.id.btn4);
		btns[5] = (Button) findViewById(R.id.btn5);
		btns[6] = (Button) findViewById(R.id.btn6);
		btns[7] = (Button) findViewById(R.id.btn7);		
		setBtnInFooter(0);
		

	}
	
	private void initFrag3Button(){
		// for category2
		frag3btn2 = (ToggleButton) findViewById(R.id.frag3TopBtn);
		frag3btn3 = (ToggleButton) findViewById(R.id.frag3RightBtn);
		frag3btn4 = (ToggleButton) findViewById(R.id.frag3bottomBtn);
		frag3btn5 = (ToggleButton) findViewById(R.id.frag3LeftBtn);	
	}
	
	public Crime getCrime(){
		return this.crime;
	}
	
	public MyFragmentStatePagerAdapter getMyfm(){
		return this.myAdapter;
	}
	
	private void setTexts(){
		
		  // the text of button set dynamically
		  if(crime.getCategoryCode()==1){
			  frag3btn2.setText("Damage");
			  frag3btn2.setTextOn("Damage");
			  frag3btn2.setTextOff("Damage");
			  frag3btn3.setText("Attack");
			  frag3btn3.setTextOn("Attack");
			  frag3btn3.setTextOff("Attack");
			  frag3btn4.setText("Other");
			  frag3btn4.setTextOn("Other");
			  frag3btn4.setTextOff("Other");
			  frag3btn5.setText("Rape");
			  frag3btn5.setTextOn("Rape");
			  frag3btn5.setTextOff("Rape");
		
			  
			}else if(crime.getCategoryCode()==2){
			  frag3btn2.setText("Robbery");
			  frag3btn2.setTextOn("Robbery");
			  frag3btn2.setTextOff("Robbery");
			  frag3btn3.setText("Bike");
			  frag3btn3.setTextOn("Bike");
			  frag3btn3.setTextOff("Bike");
			  frag3btn4.setText("Other");
			  frag3btn4.setTextOn("Other");
			  frag3btn4.setTextOff("Other");
			  frag3btn5.setText("Shop");
			  frag3btn5.setTextOn("Shop");
			  frag3btn5.setTextOff("Shop");
			  
		  }else if(crime.getCategoryCode()==3){
			  frag3btn2.setText("Noise");
			  frag3btn2.setTextOn("Noise");
			  frag3btn2.setTextOff("Noise");
			  frag3btn3.setText("Street Drinking");
			  frag3btn3.setTextOn("Street Drinking");
			  frag3btn3.setTextOff("Street Drinking");
			  frag3btn4.setText("Other");
			  frag3btn4.setTextOn("Other");
			  frag3btn4.setTextOff("Other");
			  frag3btn5.setText("Drugs");
			  frag3btn5.setTextOn("Drugs");
			  frag3btn5.setTextOff("Drugs");

		  }else{
			  frag3btn2.setText("");
			  frag3btn2.setTextOn("");
			  frag3btn2.setTextOff("");
			  frag3btn3.setText("");
			  frag3btn3.setTextOn("");
			  frag3btn3.setTextOff("");
			  frag3btn4.setText("");
			  frag3btn4.setTextOn("");
			  frag3btn4.setTextOff("");
			  frag3btn5.setText("");
			  frag3btn5.setTextOn("");
			  frag3btn5.setTextOff("");

		  }
	}
	
	private void allFrag3Off(){
		frag3btn2.setChecked(false);
		frag3btn3.setChecked(false);
		frag3btn4.setChecked(false);
		frag3btn5.setChecked(false);
	}
	
}
