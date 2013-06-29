package com.peshal.jsonfeedtest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {
ListView lv;
JSONArray items;
JSONObject jsonObject;
List<String> titleCollection = new ArrayList<String>();
List<String> urlCollection = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	lv = (ListView) findViewById(R.id.listView);
	new NetworkOperation().execute();
	lv.setOnItemClickListener(new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			String photoUrl = urlCollection.get(arg2);
			Intent webViewIntent = new Intent(MainActivity.this, WebViewActivity.class);
			webViewIntent.putExtra("url", photoUrl);
			startActivity(webViewIntent);
		}
		
	});
	}
	
	public void loadContents() {
		ArrayAdapter<String> adapter =new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,titleCollection); 
		lv.setAdapter(adapter);
	}
	
	public class NetworkOperation extends AsyncTask<Void, Void, Void> {
		
		@Override
		protected Void doInBackground(Void... arg0) {
			JsonParser parser = new JsonParser();
			
			try {
				String result = parser.getHttpConnection();
				String removedString = result.trim().substring(15, result.trim().length()-1);
				JSONObject jsonObject = new JSONObject(removedString);
				items = jsonObject.getJSONArray("items");
				for (int i = 0; i<items.length(); i++) {
					jsonObject = items.getJSONObject(i);
					titleCollection.add(jsonObject.getString("title"));
					urlCollection.add(jsonObject.getString("link"));
				}
				
				
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}


		@Override
		protected void onPostExecute(Void result) {
	loadContents();
	
			super.onPostExecute(result);
		}
		
	}

}
