package com.sociam.android.user;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.sociam.android.DataApplication;
import com.sociam.android.R;
import com.sociam.android.Tag;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import android.widget.LinearLayout.LayoutParams;


public class TagRegisterFragment extends Fragment {
	
	Typeface robothin;
	View view;
	DataApplication dapp;
	HashMap<String, String> tagMap;
    HashMap<Tag,ToggleButton> btns;
	private ViewPager pager;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		dapp = (DataApplication) ((UserRegisterActivity) getActivity()).getApplication();
		btns = ((UserRegisterActivity) getActivity()).getBtns();
		view = inflater.inflate(R.layout.user_reg_tags_frag, null);		
		robothin = dapp.getTypefaceRobothin();		
		
		setUpButton(getTags(getTagsFromLocal()));

		pager = ((UserRegisterActivity) getActivity()).getPager();

		TextView title = (TextView) view.findViewById(R.id.reg_tag_title);
		title.setTypeface(robothin);
		TextView title2 = (TextView) view.findViewById(R.id.reg_tag_general_title);
		title2.setTypeface(robothin);
		TextView title3 =(TextView) view.findViewById(R.id.reg_tag_southampton_title);
		title3.setTypeface(robothin);
		
		setBackBtn();
		
		
		return view;
	}
	
	
	
	
	private void setBackBtn() {
		TextView backtitle = (TextView) view.findViewById(R.id.reg_tag_back_tex);
		setBackClick(backtitle);
		ImageView backimg = (ImageView) view.findViewById(R.id.reg_tag_back);
		setBackClick(backimg);
	}

	private void setBackClick(Object obj){
		((View) obj).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				pager.setCurrentItem(pager.getCurrentItem()-1);
			}
		});
	}


	private ArrayList<Tag> getTags(HashMap<String, String> tagmap){
		ArrayList<Tag> tags = new ArrayList<Tag>();
		
		for(Entry<String, String> entry : tagmap.entrySet()) {			
			tags.add(new Tag(entry.getKey(),entry.getValue()));
		}		
				
				
		return tags;
	}
	

    private void setUpButton(ArrayList<Tag> tags){
        Log.e("sociam", "Start reading!");
        
        LinearLayout l_gene = (LinearLayout) view.findViewById(R.id.reg_tag_general_layout);
        LinearLayout l_soton = (LinearLayout) view.findViewById(R.id.reg_tag_southampton_layout);
        
        LinearLayout row_g = new LinearLayout(getActivity());
        row_g.setOrientation(0);
        row_g.setGravity(Gravity.CENTER_HORIZONTAL);
        
        LinearLayout row_s = new LinearLayout(getActivity());
        row_s.setOrientation(0);
        row_s.setGravity(Gravity.CENTER_HORIZONTAL);

        
        
        int count_g=0;
        int count_row_g=0;
        int count_s=0;
        int count_row_s=0;
        
        for(Tag tag : tags){
                String text = tag.getName();
                
                if(tag.getCategory().equals("general")){        
                        
                        count_g = count_g + text.length();
                        
                        if(count_g>20 || count_row_g>2){
                                
                        //        Log.e("sociam", Integer.toString(count_g));
                                l_gene.addView(row_g);
                                
                                row_g = new LinearLayout(getActivity());
                                row_g.setOrientation(0);
                                row_g.setGravity(Gravity.CENTER_HORIZONTAL);
                                
                        
                                ToggleButton tb = setToggleButton(tag);
                                
                                row_g.addView(tb);
                                count_g=text.length();
                                count_row_g=0;
                                
                                
                        
                        }else{        
                        //        Log.v("sociam", Integer.toString(count_g));

                                ToggleButton tb = setToggleButton(tag);
                                row_g.addView(tb);
                                ++count_row_g; 
                                
                        }
                        
                        
                }else{
                        
                        
                        count_s = count_s + text.length();
                        

                        if(count_s>25 || count_row_s>2){

                                l_soton.addView(row_s);
                                
                                row_s = new LinearLayout(getActivity());
                                row_s.setOrientation(0);
                                row_s.setGravity(Gravity.CENTER_HORIZONTAL);

                                
                        
                                ToggleButton tb = setToggleButton(tag);
                                row_s.addView(tb);
                                count_s=text.length();
                                count_row_s=0;
                                
                                
                        
                        }else{        
                        //        Log.e("sociam", "soton row " + text);

                                ToggleButton tb = setToggleButton(tag);
                                row_s.addView(tb);
                                ++count_row_s; 
                                
                        }
                                
                        
                }                
        }
        
        l_gene.addView(row_g);
        l_soton.addView(row_s);

}


private ToggleButton setToggleButton(final Tag tag){
        
        String text = tag.getName();
        LinearLayout.LayoutParams pane = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        pane.leftMargin=10;
        pane.topMargin=10;
        final ToggleButton tb = new ToggleButton(getActivity());
        tb.setTypeface(robothin);
        tb.setBackgroundResource(R.drawable.tag_btn);
        tb.setTextOff(text);
        tb.setTextOn(text);
        tb.setText(text);
        tb.setLayoutParams(pane);
        
        
        tb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, 
                                                boolean isChecked) {
                                        
                                        if(isChecked){
                                                tb.setTextColor(Color.WHITE);
                                                tag.setUserSetting(true);
                                        }else{
                                                tb.setTextColor(Color.BLACK);
                                                tag.setUserSetting(false);
                                        }
                                }
                        });
        
        btns.put(tag,tb);
        return tb;
}
	
	

private HashMap<String,String> getTagsFromLocal(){
    HashMap<String, String> tags = new HashMap<String, String>();
    String FILENAME = "tag.csv";
    
    try {
            FileInputStream in = getActivity().openFileInput(FILENAME);
            InputStreamReader ins = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(ins);
            String currentLine;
            
            while((currentLine=br.readLine())!=null){
                    String str[] = currentLine.split(",");
                    String cate;
                    if(str[1]!=null) cate=str[1];
                    else cate="";
                    tags.put(str[0], cate);
            }

            br.close();
    } catch (Exception e) {
            Log.e("sociam", e.getMessage());

    }
    
    
    return tags;
}
	
	
}
