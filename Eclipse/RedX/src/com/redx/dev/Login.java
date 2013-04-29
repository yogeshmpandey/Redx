package com.redx.dev;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {
	EditText un, pw;
	Button log;
	TextView error, rS;
	String usn;
	CheckBox saveLoginCheckBox;
	private SharedPreferences loginPreferences;
	private SharedPreferences.Editor loginPrefsEditor;
	private Boolean saveLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		un = (EditText) findViewById(R.id.log_un);
		pw = (EditText) findViewById(R.id.log_pw);
		log = (Button) findViewById(R.id.btnLogin);
		error = (TextView) findViewById(R.id.log_error);
		rS = (TextView) findViewById(R.id.link_to_register);
		saveLoginCheckBox = (CheckBox) findViewById(R.id.saveLoginCheckBox);
		loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
		loginPrefsEditor = loginPreferences.edit();
		saveLogin = loginPreferences.getBoolean("saveLogin", false);

		if (saveLogin == true) {
			un.setText(loginPreferences.getString("username", ""));
			pw.setText(loginPreferences.getString("password", ""));
			saveLoginCheckBox.setChecked(true);
		}
		rS.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(Login.this, Register.class);
				startActivity(i);
			}
		});
		log.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {

				usn = un.getText().toString();
				String pwd = pw.getText().toString();
				if (saveLoginCheckBox.isChecked()) {
					loginPrefsEditor.putBoolean("saveLogin", true);
					loginPrefsEditor.putString("username", usn);
					loginPrefsEditor.putString("password", pwd);
					loginPrefsEditor.commit();
				} else {
					loginPrefsEditor.clear();
					loginPrefsEditor.commit();
				}
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair("username", usn));
				postParameters.add(new BasicNameValuePair("password", pwd));
				String response = null;
				try {
					response = CustomHttpClient.executeHttpPost(
							"http://192.168.243.1:8084//Redx/check.do",
							postParameters);
					String res = response.toString();
					res = res.replaceAll("\\s+", "");
					if (res.equals("1")) {
						getLoc();
						Intent open = new Intent(Login.this, Bntwrk.class);
						open.putExtra("user", usn);
						Login.this.startActivity(open);
						finish();
					} else
						Toast.makeText(getApplicationContext(),
								"Incorrect Username or Password..",
								Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"Error Connecting to Server..", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});
	}

	public void getLoc() {
		Geocoder geocoder;
		String bestProvider;
		List<Address> user = null;
		double lat;
		double lng;
		LocationManager lm = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		bestProvider = lm.getBestProvider(criteria, false);
		Location location = lm.getLastKnownLocation(bestProvider);

		if (location == null) {
			Toast.makeText(this, "Location Not found", Toast.LENGTH_LONG)
					.show();
		} else {
			geocoder = new Geocoder(this);
			try {
				user = geocoder.getFromLocation(location.getLatitude(),
						location.getLongitude(), 1);
				lat = (double) user.get(0).getLatitude();
				lng = (double) user.get(0).getLongitude();
				String latitude = lat + "";
				String longitude = lng + "";
				Toast.makeText(this, lat + ":" + lng, Toast.LENGTH_SHORT)
						.show();
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters
						.add(new BasicNameValuePair("latitude", latitude));
				postParameters.add(new BasicNameValuePair("longitude",
						longitude));
				postParameters.add(new BasicNameValuePair("user", usn));
				String response = null;
				try {
					response = CustomHttpClient.executeHttpPost(
							"http://192.168.243.1:8084/Redx/position.do",
							postParameters);
					String res = response.toString();
					res = res.replaceAll("\\s+", "");
					if (res.equals("1")) {
						Toast.makeText(getApplicationContext(),
								"Location Received..", Toast.LENGTH_SHORT)
								.show();
					} else
						Toast.makeText(getApplicationContext(), "Error...",
								Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"Error Connecting to Server..", Toast.LENGTH_SHORT)
							.show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
