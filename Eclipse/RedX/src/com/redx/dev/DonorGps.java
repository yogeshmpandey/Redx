package com.redx.dev;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DonorGps extends Activity {
	Button se;
	EditText ci;
	Spinner bg;
	JSONObject json;
	JSONArray jarr;
	int i;
	float meter;
	String user;
	Intent open;
	AlertDialog.Builder alertDialog, alertDialog1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		alertDialog = new AlertDialog.Builder(DonorGps.this);
		alertDialog1 = new AlertDialog.Builder(DonorGps.this);
		setContentView(R.layout.gps);
		se = (Button) findViewById(R.id.gps_se);
		ci = (EditText) findViewById(R.id.gps_m);
		bg = (Spinner) findViewById(R.id.gps_bg);
		open = getIntent();
		user = open.getStringExtra("user");
		se.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair("user", user));
				postParameters.add(new BasicNameValuePair("bdonor", bg
						.getSelectedItem().toString()));
				String response = null;
				
				meter = Float.parseFloat(ci.getText().toString());
				try {
					response = CustomHttpClient.executeHttpPost(
							"http://192.168.243.1:8084/Redx/gpsinfo.do",
							postParameters);
					String res = response.toString();
					res = res.replaceAll("\\s+", "");
					res = res.replaceAll("\\s+", "");
					if (res.equals("0")) {
						Toast.makeText(getApplicationContext(),
								"Blood Donor not found..", Toast.LENGTH_SHORT)
								.show();
					} else {
						json = new JSONObject(res);
						jarr = new JSONArray(json.getString("array"));
						i = 0;
						Search entry = new Search(DonorGps.this);
						entry.open();
						while (!jarr.isNull(i)) {
							String ulat = jarr.getJSONObject(i).getString(
									"ulat");
							String ulon = jarr.getJSONObject(i).getString(
									"ulon");
							String lat = jarr.getJSONObject(i).getString("lat");
							String lon = jarr.getJSONObject(i).getString("lon");
							float dist = dis(ulat, ulon, lat, lon);
							if (dist < meter) {
								String venue = jarr.getJSONObject(i).getString(
										"venue");
								String name = jarr.getJSONObject(i).getString(
										"name");
								String phone = jarr.getJSONObject(i).getString(
										"phone");
								String email = jarr.getJSONObject(i).getString(
										"email");
								entry.createEntry(name, phone + "(" + email
										+ ")", venue);
							}
							i++;
						}
						entry.close();
						Intent i = new Intent(DonorGps.this,DispCity.class);
						startActivity(i);
						
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"Error Connecting to Server..", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		alertDialog.setTitle("Sign Out?");
		alertDialog.setMessage("Are you sure you want sign out?");
		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent i = new Intent(DonorGps.this, Login.class);
						startActivity(i);
						finish();
					}
				});
		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(), "Continue..",
								Toast.LENGTH_SHORT).show();
						dialog.cancel();
					}
				});

		alertDialog1.setTitle("Exit?");
		alertDialog1.setMessage("Are you sure you want to exit?");
		alertDialog1.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});
		alertDialog1.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(), "Continue..",
								Toast.LENGTH_SHORT).show();
						dialog.cancel();
					}
				});
	}

	protected float dis(String ulat, String ulon, String lat, String lon) {
		double ult = Double.parseDouble(ulat);
		double uln = Double.parseDouble(ulon);
		double lt = Double.parseDouble(lat);
		double ln = Double.parseDouble(lon);
		float results[] = new float[1];
		Location.distanceBetween(ult, uln, lt, ln, results);
		return results[0];
	}

	// Initiating Menu XML file (menu.xml)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_exit:
			alertDialog1.show();
			return true;

		case R.id.menu_log:
			alertDialog.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
