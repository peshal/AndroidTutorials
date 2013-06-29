package com.peshal.jsonfeedtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;


public class JsonParser {
	BufferedReader in = null;
	JSONObject jsonObject=null;
	String result="";



	public String getHttpConnection() throws ClientProtocolException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet("http://www.flickr.com/services/feeds/photos_public.gne?tags=punctuation,atsign&format=json");
		HttpResponse response = httpClient.execute(request);
		in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));	
		
		StringBuffer sb = new StringBuffer("");
		String line="";
		String lineSeparator=System.getProperty("line.separator");
		while ((line=in.readLine())!=null) {
			sb.append(line + lineSeparator);
		}
		in.close();
		result = sb.toString();
		try{
		jsonObject = new JSONObject(result);
		}
		catch(Exception e) {
		}
		return result;
			
	}
	
		
	
}
