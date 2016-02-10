package com.example.jsongetrequest;

import fragments.FragmentA;
import fragments.FragmentB;
import fragments.FragmentC;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class NewActivity extends FragmentActivity{

	Context context;
	 ViewPager viewPager;
	DataManager dataManager;
	public void onCreate(Bundle savedInstanceState)
	{
		
		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_layout);
		
		
		context = getApplicationContext();
		dataManager = new DataManager(context);
		viewPager = (ViewPager) findViewById(R.id.pager);
		FragmentManager fragmentManager = getSupportFragmentManager();
		
		viewPager.setAdapter(new MyAdapter(fragmentManager));
	}
	
	
	
	 class MyAdapter extends FragmentPagerAdapter
		//Fragment fragment = null;
	 {

		public MyAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public Fragment getItem(int i){
			Fragment fragment = null;
			if(i==0){
				
				
				fragment = new FragmentA();
			}
			
			if(i==1){
				
				fragment = new FragmentB();
				
			}
			
			if(i ==2){
				fragment = new FragmentC();
			}
			
			return fragment;
		}
		
		@Override
		public int getCount(){
			return 3;
		}
		
		
		
		
	}
	
	
}
