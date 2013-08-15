package com.sociam.android.report;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.Button;
import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.sociam.android.R;

@SuppressLint("NewApi")
public class ReportActivity extends FragmentActivity {

	ViewPager pager;
	Button btn1, btn2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_main);
		setUpView();
		setFooter();
	}
	

	// implement pageChangeLitener
	private void setUpView(){
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new MyFragmentStatePagerAdapter(getSupportFragmentManager()));
		pager.setCurrentItem(0);
		initButton();
	}

	private void setFooter() {
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				switch(position){
				case 0: setBtn(btn1,"1",60,60,true);
						setBtn(btn2, "", 20, 20,false);
						break;
				case 1:	setBtn(btn1,"",20,20,false);
						setBtn(btn2, "2", 60, 60,true);
						break;	
				}
				
			}
			

	

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		

		
	}

	@SuppressLint("NewApi")
	private void  setBtn(Button btn, String text, int h, int w,boolean select){
		Resources res = getResources();
		Drawable main = res.getDrawable(R.drawable.rounded_cell);
		Drawable mini = res.getDrawable(R.drawable.rounded_mini);
		if(select) btn.setBackground(main);
		else btn.setBackground(mini);
		btn.setText(text);
		btn.setHeight(h);
		btn.setWidth(w);
	}
	
	private void initButton() {
		btn1 = (Button) findViewById(R.id.btn1);
		btn2 = (Button) findViewById(R.id.btn2);
		setBtn(btn1, "1", 60, 60,true);
		setBtn(btn2,"",20,20,false);
		
	}
	
	
	
}
