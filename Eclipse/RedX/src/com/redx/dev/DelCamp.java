package com.redx.dev;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

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

public class DelCamp extends Activity {
	Button se;
	AutoCompleteTextView ci;
	TextView error,city;
	String user;
	String data;
	 AlertDialog.Builder alertDialog , alertDialog1,alertDialog2;
	public void getCamp()
	{
		DataCamp city = new DataCamp();
		data = city.getCamp(user);
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ccity);
		alertDialog  = new AlertDialog.Builder(DelCamp.this);
        alertDialog1  = new AlertDialog.Builder(DelCamp.this);
        alertDialog2  = new AlertDialog.Builder(DelCamp.this);
        se = (Button) findViewById(R.id.ca_se);
		se.setText("Delete Camp");
		ci = (AutoCompleteTextView) findViewById(R.id.ca_city);
		error = (TextView) findViewById(R.id.ca_error);
		city = (TextView) findViewById(R.id.tv_city);
		city.setText("Camp Name");
		Intent open = getIntent();
		user = open.getStringExtra("user");
		getCamp();
		String[] CITIES=data.split(",");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item,CITIES);
		ci.setAdapter(adapter);
		se.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				alertDialog2.show();
				

			}
		});
		alertDialog2.setTitle("Delete?");
	      alertDialog2.setMessage("Are you sure you want delete ?");
	      alertDialog2.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog,int which) {
	        	  ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("camp", ci.getText()
							.toString()));
					postParameters.add(new BasicNameValuePair("user",user));
					
					String response = null;
					try {
						response = CustomHttpClient.executeHttpPost(
								"http://192.168.243.1:8084/Redx/delcamp.do",
								postParameters);
						String res = response.toString();
						res = res.replaceAll("\\s+", "");
						if (res.equals("1")) {
							Toast.makeText(getApplicationContext(), "Blood Donation Camp Deleted..", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(getApplicationContext(), "Blood Donation Camp Not Found..", Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						Toast.makeText(getApplicationContext(), "Error Connecting To Server..", Toast.LENGTH_SHORT).show();
					}
	          }
	      });
	      alertDialog2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog, int which) {
	          Toast.makeText(getApplicationContext(), "Continue..", Toast.LENGTH_SHORT).show();
	          dialog.cancel();
	          }
	      });
		alertDialog.setTitle("Sign Out?");
	      alertDialog.setMessage("Are you sure you want sign out?");
	      alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog,int which) {
	        	  Intent i = new Intent(DelCamp.this,Login.class);
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