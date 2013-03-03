package com.example.locationtest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;


public class MainActivity extends Activity implements LocationListener{
	private TextView textView =null;
	LocationManager locationManager=null;
	Location location=null;
	protected String url;
	protected JSONObject jsonData=null;
	private EditText urlText=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if(gpsEnabled!=true) {
			Toast.makeText(getApplicationContext(), "GPS Disbled! Please Enable to Proceed!", Toast.LENGTH_SHORT).show();
        	startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
		} 
		
		textView = (TextView)findViewById(R.id.textView);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1, this);
		urlText = (EditText)findViewById(R.id.urlTextbox);
		Button submitButton = (Button)findViewById(R.id.submitUrl);
		
		submitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (urlText.getText()!=null) {
				url=urlText.getText().toString();
				System.out.println(url);//for testing only, not required
				Toast.makeText(getApplicationContext(), "Url Submitted, Sending data to Web Service at url: " + url, Toast.LENGTH_SHORT).show();
			}
			}
		});
		
	

		
	}

	@Override
	public void onLocationChanged(final Location location) {
		this.location=location;
		 final Handler handler = new Handler();
		  Timer ourtimer = new Timer();
		  TimerTask timerTask = new TimerTask() {
			  int cnt=1;    
			  public void run() {
		                  handler.post(new Runnable() {
		                          public void run() {
		                        	 
		                        	Double latitude = location.getLatitude();
		                      		Double longitude = location.getLongitude();
		                      		Double altitude = location.getAltitude();
		                      		Float accuracy = location.getAccuracy();
		                      		textView.setText("Latitude: " + latitude + "\n" + "Longitude: " + longitude+ "\n" + "Altitude: " + altitude + "\n" + "Accuracy: " + accuracy + "meters"+"\n" + "Location Counter: " + cnt);
		                      	try {
		                      		jsonData = new JSONObject();
									jsonData.put("Latitude", latitude);
									jsonData.put("Longitude", longitude);
									jsonData.put("Altitude", altitude);
									jsonData.put("Accuracy", accuracy);
									
									System.out.println(jsonData.toString());//not required, for testing only
									if(url!=null) {
									new HttpPostHandler().execute();
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		                      	
		                      		cnt++;
		                          }
		                   
		                 });
		          }};
		      ourtimer.schedule(timerTask, 0, 120000);
		
	}

	@Override
	public void onProviderDisabled(String provider) {

		
	}

	@Override
	public void onProviderEnabled(String provider) {

		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

		
	}
	




public class HttpPostHandler extends AsyncTask<Void,Void,Void> {

	@Override
	protected Void doInBackground(Void... arg0) {
		
		 HttpClient httpClient = new DefaultHttpClient();
		 HttpPost httpPost = new HttpPost(url);
		 StringEntity dataEntity = null;
		try {
			dataEntity = new StringEntity(jsonData.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 httpPost.setEntity(dataEntity);
		 httpPost.setHeader("Content-Type", "application/json");
		try {
			httpClient.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		return null;
		
	
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		try {
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

}
