package com.example.sqlliteandloginform;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	DatabaseConnector dConnector = new DatabaseConnector(this);
	Form form = new Form();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		final EditText firstName = (EditText) findViewById(R.id.firstName);
		final EditText lastName = (EditText) findViewById(R.id.lastName);
		final EditText email = (EditText) findViewById(R.id.email);
		final EditText phoneNumber = (EditText) findViewById(R.id.phone);
		final EditText username = (EditText) findViewById(R.id.userName);
		final EditText password = (EditText) findViewById(R.id.password);
		
		final Button register = (Button)findViewById(R.id.register);
		final Button login = (Button) findViewById(R.id.login);
		final Button submit = (Button) findViewById(R.id.submit);
		
		firstName.setVisibility(View.GONE);
		lastName.setVisibility(View.GONE);
		email.setVisibility(View.GONE);
		phoneNumber.setVisibility(View.GONE);
		submit.setVisibility(View.GONE);
		register.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				firstName.setVisibility(View.VISIBLE);
				lastName.setVisibility(View.VISIBLE);
				email.setVisibility(View.VISIBLE);
				phoneNumber.setVisibility(View.VISIBLE);
				login.setVisibility(View.GONE);
				register.setVisibility(View.GONE);
				submit.setVisibility(View.VISIBLE);
			}
			
		});
		login.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				try {
				List<String> userRecord = dConnector.getRecord(username.getEditableText().toString(), password.getEditableText().toString());
				Log.d("Login onClick()", String.valueOf(userRecord.size()));
				Dialog dialog = new Dialog(view.getContext());
				TextView tv = new TextView(getApplicationContext());
				tv.setText("First Name : " + userRecord.get(0) +" \n" + "Last Name : " + userRecord.get(1) + "\n" + "Email : " + userRecord.get(2)
						+ "\n" + "Phone : " + userRecord.get(3));
				dialog.setContentView(tv);
				dialog.setTitle("User Details");
				dialog.show();
				dialog.setCanceledOnTouchOutside(true);
				}
				
				catch (Exception e) {
				Toast.makeText(getApplicationContext(), "Login Failed, Record Not Found", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
				}
				
			}
			
		});
		submit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				form.setFirstName(firstName.getEditableText().toString());
				form.setLastName(lastName.getEditableText().toString());
				form.setEmail(email.getEditableText().toString());
				form.setPhoneNumber(phoneNumber.getEditableText().toString());
				form.setUsername(username.getEditableText().toString());
				form.setPassword(password.getEditableText().toString());
				dConnector.addRecord(form.getFirstName(), form.getLastName(), form.getEmail(), form.getPhoneNumber(), form.getUsername(), form.getPassword());
				Toast.makeText(getApplicationContext(), "Record Added!", Toast.LENGTH_SHORT).show();
				firstName.setVisibility(View.GONE);
				lastName.setVisibility(View.GONE);
				email.setVisibility(View.GONE);
				phoneNumber.setVisibility(View.GONE);
				submit.setVisibility(View.GONE);
				login.setVisibility(View.VISIBLE);
				register.setVisibility(View.VISIBLE);
			}
			
		});
	
	}

}
