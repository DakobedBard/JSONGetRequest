package com.example.jsongetrequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.util.Log;

public class DataPoint {
	
	int uid;
	String ut;

	double diffuseMean;
	double diffuseMin;

	double diffuseMax;
	double totalMax;
	
	public DataPoint(){
		
	}
	
	public DataPoint(String ut, double difMax, double difMin){
		this.ut =ut;
		this.diffuseMax = difMax;
		this.diffuseMin = difMin;
			
	}
	
	public void setString(String ut){
		this.ut = ut;
	}
	
	public void setDiffuseMax(double max){
		this.diffuseMax= max;
	}
	
	public void setMax(double totalMax){
		this.totalMax = totalMax;
	}
	
	
	public void setDiffuseMin(double min){
		this.diffuseMin = min;
	}
	
	public void setDiffuseMeanK(double mean){
		this.diffuseMean = mean;
	}

	
	public String toString(){
		
		return   this.diffuseMax + " " + this.diffuseMean+ " " + this.ut + "\n";
		
	}

}