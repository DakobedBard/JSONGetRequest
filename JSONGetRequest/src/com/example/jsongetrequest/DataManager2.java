package com.example.jsongetrequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import android.content.Context;
import android.util.Log;

/**
	The DataManager2 class has methods that involve handling and saving the data.  The Datamanager2 class saves the data in the internal cache of the Andriod device.  
*/

public class DataManager2 {
	// method takes an array of data points and writes a text file where the first entry is the number of data points and then followed by rows representing each datapoint

	public void saveInternalCache(File folder, DataPoint[] data, String fileName){
		
		String dataString ;
		String null1 = "data is null ";
		FileOutputStream fileOutputStream = null;
		File myFile = new File(folder,fileName);
		
		try {
			
			fileOutputStream = new FileOutputStream(myFile);
	
			if( data == null){
				
				Log.d("bug","the data is null");
				fileOutputStream.write(null1.getBytes());
				fileOutputStream.close();	
				return;
				
			}
		
			String datalength = data.length + "\n";
	
			fileOutputStream.write(datalength.getBytes());
			
			for(int k =0 ; k <data.length; k++){
				
				dataString=data[k].toString();
				fileOutputStream.write(dataString.getBytes());
			}
			fileOutputStream.close();
			

		} catch (FileNotFoundException e) {
			Log.d("error", e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			Log.d("error" , e.toString());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	// The method loadInternalchache method finds the file specified by fileName in the cache
	
	public DataPoint[] loadInternalCache2(File folder, String fileName) {
	
		File a = new File(folder.getPath()+ "/" +fileName);
	
		// Test if the file given by fileName.txt exits in the cache directory. 
		if(!a.exists()){
			Log.d("bug", "There is no file: " + fileName + " in the internal cache");
			return null;
		}
		String line;		
		DataPoint [] outArray ;
	
		Integer n;
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(a));
			
			// Read the first line which tells us the number of datapoints 
			n = Integer.parseInt(in.readLine());
			outArray = new DataPoint[n];
		
			int k=0;
			// Loop through all rows of the parsing the string with the method getDataFromLine.
			
			while((line = in.readLine()) != null ){
				outArray[k] = getDataFromLine(line);
				Log.d("data" , line);
				k++;
			}
			return outArray;
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return null;		
	}
	
	// method that returns a data point from the input string.
	public DataPoint getDataFromLine(String b){
		String [] a = b.split(" ");
		
		DataPoint newPoint = new DataPoint(a[2],Double.parseDouble(a[0]), Double.parseDouble(a[1]));
		return newPoint;	
	}
	
	
	private static double[] getMaxArray(DataPoint[] a){
		double[] dubs = new double[a.length ];
		
		for (int k=0 ; k<a.length;k++){
			dubs[k]= a[k].diffuseMax;
		}
		return dubs;
	}
	
	
	// This method takes an array of doubles and returns the average.  This method is to be used while preprocessing data to be plotted.  
	private static double  getAv(double[] dubs){
		
		double sum =0;
		
		for(int k =0 ; k< dubs.length; k++){
			sum += dubs[k];
			
		}
		
		return sum/dubs.length;
	}
	
	
	public static double[] getZPeaks(double[] d){
		
		int n = d.length/1429;
		int count = 0;
		int index =0;
		
		double[] peaks = new double[n];
		for(int k=4; k<d.length;k++){
			
			if(count == n){
				return peaks; 
			}
			
			if(d[k-1] < d[k] && d[k] > d[k+1]){
				peaks[count] = d[k];
				count++;	
			}
		}
		return d;
	}
	
	
	public static double[] dub(DataPoint[] data){
		
		double[] d = new double[data.length];
		for(int k =0; k < data.length;k++){
			d[k]=data[k].diffuseMax;
		}
		return d;
	}
	

	public static Number[] getnumb (double[] data){
		
		Number[] n = new Number[data.length];
		for(int k =0 ; k < data.length;k++){
			n[k] = data[k];
		}
	
		return n;
	}
	
	public double[] reduce(DataPoint[] data){
		
		int length = data.length;
		int d =length/200;
		int g = length/d;
	
		double[] dubs = new double[g+1];
		
		DataPoint [] temp ;
		double[] outDub ;
		
		int j = length % d;
		int off  =  length - d*g;
		
		// loop through the array averaging out the doubles every 20 points 
		
		for(int i =0;i<dubs.length-1;i++){
			
			temp = Arrays.copyOfRange(data, d*i, d*(i+1)-1);
			dubs[i] = getAv(getMaxArray(temp));
		}
		
		//dubs[dubs.length-1] = getAv(getMaxArray(Arrays.copyOfRange(data, g*20,off-1 )));
		//int length = data.length;
		
		return dubs;		
	}
}
