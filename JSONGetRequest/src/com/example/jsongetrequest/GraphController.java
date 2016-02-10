package com.example.jsongetrequest;

public class GraphController {	
	
	
	public Graph createGraph(String name, DataPoint[] data){

		double[] dubs;
		Graph graph = new Graph(name , getMaxArray(data));
		
		return null;
	}
	
	private static double[] getMaxArray(DataPoint[] a){
		double[] dubs = new double[a.length ];
		
		for (int k=0 ; k<a.length;k++){
			dubs[k]= a[k].diffuseMax;
		}
		return dubs;
	}
	
	private static double[] getMinArray(DataPoint[] a){
		double[] dubs = new double[a.length ];
		
		for (int k=0 ; k<a.length;k++){
			dubs[k]= a[k].diffuseMax;
		}
		return dubs;
	}
	
	
	public class Graph{
		
			String graphName;
			double [] data;
			
			public Graph(String name, double[] data){
				this.graphName= name;
				this.data = data;
			}		
	}	
}
