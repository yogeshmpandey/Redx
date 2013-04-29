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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class DonorState extends Activity {
	Button se;
	AutoCompleteTextView st;
	TextView error;
	Spinner bg;
	JSONObject json;
	JSONArray jarr;
	int i;
	String data;
	 AlertDialog.Builder alertDialog , alertDialog1;
	public void getState()
	{
		DataState city = new DataState();
		data = city.getState();
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sstate);
		se = (Button) findViewById(R.id.st_se);
		st = (AutoCompleteTextView) findViewById(R.id.st_state);
		error = (TextView) findViewById(R.id.st_error);
		bg = (Spinner) findViewById(R.id.st_bg);
		alertDialog  = new AlertDialog.Builder(DonorState.this);
        alertDialog1  = new AlertDialog.Builder(DonorState.this);
		getState();
		String[] CITIES=data.split(",");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item,CITIES);
		st.setAdapter(adapter);
		se.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair("state", st.getText()
						.toString()));
				postParameters.add(new BasicNameValuePair("bdonor", bg
						.getSelectedItem().toString()));
				String response = null;
				Intent open = getIntent();
				String user = open.getStringExtra("user");
				try {
					response = CustomHttpClient.executeHttpPost(
							"http://192.168.243.1:8084/Redx/sstate.do",
							postParameters);
					String res = response.toString();
					res = res.replaceAll("\\s+", "");
					res = res.replaceAll("\\s+", "");
					if (res.equals("0")) {
						Toast.makeText(getApplicationContext(), "Blood Donor not found..", Toast.LENGTH_SHORT).show();
					} else {
						json = new JSONObject(res);
						jarr = new JSONArray(json.getString("array"));
						i = 0;
						SearchS entry = new SearchS(DonorState.this);
						entry.open();
						while (!jarr.isNull(i)) {
							String user1 = jarr.getJSONObject(i).getString(
									"user");
							String name = jarr.getJSONObject(i).getString(
									"name");
							String phone = jarr.getJSONObject(i).getString(
									"phone");
							String email = jarr.getJSONObject(i).getString(
									"email");
							String city = jarr.getJSONObject(i).getString(
									"city");
							i++;
							if (!user1.equals(user))
								entry.createEntry(name, city, phone, email);
						}
						entry.close();
						Intent disp = new Intent(DonorState.this,
								DispState.class);
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
	        	  Intent i = new Intent(DonorState.this,Login.class);
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
