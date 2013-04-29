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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CampCity extends Activity {
	Button se;
	AutoCompleteTextView ci;
	TextView error;
	JSONObject json;
	JSONArray jarr;
	int i;
	String data;
	 AlertDialog.Builder alertDialog , alertDialog1;
	public void getCity()
	{
		DataCity city = new DataCity();
		data = city.getCampCity();
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		alertDialog  = new AlertDialog.Builder(CampCity.this);
        alertDialog1  = new AlertDialog.Builder(CampCity.this);
		setContentView(R.layout.ccity);
		se = (Button) findViewById(R.id.ca_se);
		ci = (AutoCompleteTextView) findViewById(R.id.ca_city);
		error = (TextView) findViewById(R.id.ca_error);
		getCity();
		String[] CITIES=data.split(",");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item,CITIES);
		ci.setAdapter(adapter);
		se.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair("city", ci.getText()
						.toString()));
				String response = null;
				try {
					response = CustomHttpClient.executeHttpPost(
							"http://192.168.243.1:8084/Redx/ccity.do",
							postParameters);
					String res = response.toString();
					res = res.replaceAll("\\s+", "");
					if (res.equals("0")) {
						Toast.makeText(getApplicationContext(), "Blood Donoation Camp not found..", Toast.LENGTH_SHORT).show();
					} else {
						json = new JSONObject(res);
						jarr = new JSONArray(json.getString("array"));
						i = 0;
						SearchCi entry = new SearchCi(CampCity.this);
						entry.open();
						while (!jarr.isNull(i)) {
							String name = jarr.getJSONObject(i).getString(
									"name");
							String time = jarr.getJSONObject(i).getString(
									"time");
							String venue = jarr.getJSONObject(i).getString(
									"venue");
							i++;
							entry.createEntry(name, time, venue);
						}
						entry.close();
						Intent disp = new Intent(CampCity.this,
								DispCampCity.class);
						startActivity(disp);
					}
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "Error Connecting to Server..", Toast.LENGTH_SHORT).show();
				}
				
				
			}
		});
		alertDialog.setTitle("Sign Out?");
	      alertDialog.setMessage("Are you sure you want sign out?");
	      alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog,int which) {
	        	  Intent i = new Intent(CampCity.this,Login.class);
	        	  startActivity(i);
	        	  finish();
	          }
	      });
	      alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog, int which) {
	          Toast.makeText(getApplicationContext(), "Continue..", Toast.LENGTH_SHORT).show();
	          dialog.cancel();
	          }
	      });

	      alertDialog1.setTitle("Exit?");
	      alertDialog1.setMessage("Are you sure you want to exit?");
	      alertDialog1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog,int which) {
	        	  finish();
	          }
	      });
	      alertDialog1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog, int which) {
	          Toast.makeText(getApplicationContext(), "Continue..", Toast.LENGTH_SHORT).show();
	          dialog.cancel();
	          }
	      });
	}
	// Initiating Menu XML file (menu.xml)
  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
      MenuInflater menuInflater = getMenuInflater();
      menuInflater.inflate(R.layout.menu, menu);
      return true;
  }
  @Override
  public boolean onOptionsItemSelected(MenuItem item)
  {

      switch (item.getItemId())
      {
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
