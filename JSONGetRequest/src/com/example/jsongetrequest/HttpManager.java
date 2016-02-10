package com.example.jsongetrequest;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.scheme.SchemeRegistry;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import android.util.Log;


// This class handles the HTTP requests that are made by the application.  


public class HttpManager {
	
	// The method getData takes as input a string, which is a the URL of the request that we are making.  
	
	public static  DataPoint[] getData(String uri){
	
		DataPoint[] data;
	
		try{
			
			URL url = new URL(uri);
			SSLSocketFactory sslSocketFactory = createSslSocketFactory();
			URLConnection urlConnection = url.openConnection();
			HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlConnection;
			httpsURLConnection.setSSLSocketFactory(sslSocketFactory);
			httpsURLConnection.connect();
			
			try{
	
				GsonBuilder gsonBuilder = new GsonBuilder();
				InputStream inputStream = httpsURLConnection.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	
				/// The GSON library requires 
				gsonBuilder.registerTypeAdapter(DataPoint.class, new Deserializer());
				// creates a new GSON instance with the the current configuration
				Gson gson = gsonBuilder.create();
				JsonArray jsonArray = gson.fromJson(inputStreamReader, JsonArray.class);
			
			
			if(jsonArray == null){
				Log.d("bug" , "problem w/ jsonArray");
				return null;
			}
			
			data = new DataPoint[jsonArray.size()];
			JsonObject i;			
			DataPoint f;

			if(jsonArray != null){
				Log.d("bug", "jsonArray  " + jsonArray.size());
			}
	
			for(int k=0; k < jsonArray.size();k++){
				i = (JsonObject) jsonArray.get(k);
				f = gson.fromJson(i.get("fields").toString(), DataPoint.class);
				data[k] = f;
			}
			
			inputStream.close();
			inputStreamReader.close();
			
			return data;
			}catch(Exception e){
				Log.d("error",e.toString());
			
			}
			
		}catch(Exception e){
		
			e.printStackTrace();
			return null;
		}
		return null;
		}
		
	// This method is necessary in order to give permission to the Android application to make requests to the HTTPS server.  By leaving the methods blank,  
	
	 private static SSLSocketFactory createSslSocketFactory() throws Exception {
		 TrustManager[] byPassTrustManagers = new TrustManager[] {new X509TrustManager(){
			 public X509Certificate[] getAcceptedIssuers1(){
				 return new X509Certificate[0];
			 }
			 
			 public void checkClientTrusted1(X509Certificate[] chain, String authType ){
			 }
			 
			 public void CheckServerTrust(X509Certificate[] chain, String authType){
				 
			 }

			@Override
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
				// TODO Auto-generated method stub
				}

			@Override
			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
				// TODO Auto-generated method stub
				}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				// TODO Auto-generated method stub
				return null;
			} 
		 }
	 };
		 
		 SSLContext sslContext = SSLContext.getInstance("TLS");
		 sslContext.init(null, byPassTrustManagers, new SecureRandom());
		 return sslContext.getSocketFactory();
	 }
}
