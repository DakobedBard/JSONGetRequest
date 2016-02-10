package com.example.jsongetrequest;


import java.util.Arrays;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 *   The SimpleXYPlotActivity is launched from the main activity when the plot button is pressed.  
 *   
 *
 */

public class SimpleXYPlotActivity extends Activity {
	
	private XYPlot plot;
	DataManager2 dataManager2;
	GraphController graphController;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_xy_plot_example);
		plot =(XYPlot) findViewById(R.id.mySimpleXYPlot);
		
		DataPoint [] array ;
		double[] reducedData;
		double[] peaks;
		
		dataManager2 = new DataManager2();
		array =dataManager2.loadInternalCache2(getCacheDir(), "January");
	
		reducedData = dataManager2.reduce(array);
		peaks = DataManager2.getZPeaks(reducedData);
		
		if(peaks != null){
			
		}
		
		Number[] series1Numbers =   DataManager2.getnumb(reducedData);
		
		// Create a couple arrays of y-values to plot:
		// Number[] series1Numbers = {100, 110.7, 118, 123, 127,129,128,125, 120, 109, 100, 90,83,81,80, 80, 82, 86, 92,97,105,108, 120, 126,129,134};
    
        // Turn the above arrays into XYSeries':
        XYSeries series1 = new SimpleXYSeries(Arrays.asList(series1Numbers),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,"Series1"); 
        
        Log.d("bug","the size o the array is " + array.length);
        // Create a formatter to use for drawing a series using LineAndPointRenderer
        // and configure it from xml:
        LineAndPointFormatter series1Format = new LineAndPointFormatter();
        series1Format.setPointLabelFormatter(new PointLabelFormatter());
        series1Format.configure(getApplicationContext(), R.xml.line_point_formatter_with_plf1);
        plot.addSeries(series1,series1Format);
        
        LineAndPointFormatter series2Format = new LineAndPointFormatter();
         
        series2Format.setPointLabelFormatter(new PointLabelFormatter());
        series2Format.configure(getApplicationContext(),
                R.xml.line_point_formatter_with_plf2);
        //  plot.addSeries(series2, series2Format);
        // reduce the number of range labels
        plot.setTicksPerRangeLabel(3);
        plot.getGraphWidget().setDomainLabelOrientation(90);
        
	}
}
