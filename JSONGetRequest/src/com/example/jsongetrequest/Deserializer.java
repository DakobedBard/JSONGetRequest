package com.example.jsongetrequest;

import java.lang.reflect.Type;
import java.util.Iterator;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;



public class Deserializer implements JsonDeserializer<DataPoint> {

	@Override
	public DataPoint deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		
		DataPoint data = new DataPoint();
		JsonObject ob = (JsonObject) arg0;
		
		double dm = ob.get("diffuse_max").getAsDouble();
		double tm = ob.get("total_max").getAsDouble();
		String ut = ob.get("ut").getAsString();
		double dmin = ob.get("diffuse_min").getAsDouble();
		double dmean = ob.get("diffuse_mean").getAsDouble();

		//double q = ob.getAsDouble("diffuse_max");
		
		data.setDiffuseMax(dm);
		data.setMax(tm);
		data.setString(ut);
		data.setDiffuseMeanK(dmean);
		data.setDiffuseMin(dmin);
		
		return data;
	}

	public Deserializer(){
		
	}
}
