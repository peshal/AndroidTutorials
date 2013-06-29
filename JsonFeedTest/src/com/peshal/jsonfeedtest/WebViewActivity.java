package com.peshal.jsonfeedtest;


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.app.Activity;

public class WebViewActivity extends Activity {
	WebView webView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_view);
    	webView = (WebView)findViewById(R.id.webview);
		webView.setWebViewClient(new WebViewClient());
		String url=getIntent().getExtras().getString("url");
		webView.canGoBack();
		webView.loadUrl(url);
		webView.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {    
				 		webView.goBack();
				 		return true; 	
				}
				else {
					return false;
				}
				
			}		
		});
	
	}
}
