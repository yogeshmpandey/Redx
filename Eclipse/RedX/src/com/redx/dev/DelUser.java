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
import android.widget.Button;
import android.widget.Toast;

public class DelUser extends Activity {
	Button sc, ss,dac;
	String user;
	AlertDialog.Builder alertDialog , alertDialog1,alertDialog2,alertDialog3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		alertDialog  = new AlertDialog.Builder(DelUser.this);
        alertDialog1  = new AlertDialog.Builder(DelUser.this);
        alertDialog2  = new AlertDialog.Builder(DelUser.this);
        alertDialog3  = new AlertDialog.Builder(DelUser.this);
		setContentView(R.layout.delete);
		sc = (Button) findViewById(R.id.del_user);
		ss = (Button) findViewById(R.id.del_camp);
		dac = (Button) findViewById(R.id.del_allcamp);
		Intent open = getIntent();
		user = open.getStringExtra("user");
		sc.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				 alertDialog2.show();
				
			}
		});
		dac.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				alertDialog3.show();
							}
		});
		ss.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(DelUser.this, DelCamp.class);
				i.putExtra("user", user);
				startActivity(i);
			}
		});
		alertDialog2.setTitle("Delete?");
	      alertDialog2.setMessage("Are you sure you want delete your profile?");
	      alertDialog2.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog,int which) {
	        	  ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("username", user));
					String response = null;
					try {
						response = CustomHttpClient.executeHttpPost(
								"http://192.168.243.1:8084/Redx/deluser.do",
								postParameters);
						String res = response.toString();
						res = res.replaceAll("\\s+", "");
						if (res.equals("0"))
							 Toast.makeText(getApplicationContext(), "Error Occured..", Toast.LENGTH_SHORT).show();
						else {

							Intent i = new Intent(DelUser.this, Login.class);
							startActivity(i);
							finish();
						}

					} catch (Exception e) {
						 Toast.makeText(getApplicationContext(), "Error Connecting To Server..", Toast.LENGTH_SHORT).show();
					}
	          }
	      });
	      alertDialog2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog, int which) {
	          Toast.makeText(getApplicationContext(), "Deletion Canceled..", Toast.LENGTH_SHORT).show();
	          dialog.cancel();
	          }
	      });
	      alertDialog3.setTitle("Delete?");
	      alertDialog3.setMessage("Are you sure you want delete all your camps?");
	      alertDialog3.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog,int which) {
	        	  ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("username", user));
					String response = null;
					try {
						response = CustomHttpClient.executeHttpPost(
								"http://192.168.243.1:8084/Redx/delallcamp.do",
								postParameters);
						String res = response.toString();
						res = res.replaceAll("\\s+", "");
						if (res.equals("0"))
							 Toast.makeText(getApplicationContext(), "No Blood Donation Camps Created By User..", Toast.LENGTH_SHORT).show();
						else {
							 Toast.makeText(getApplicationContext(), "All Blood Donation Camps created by user deleted", Toast.LENGTH_SHORT).show();
						}

					} catch (Exception e) {
						 Toast.makeText(getApplicationContext(), "Error Connecting To Server..", Toast.LENGTH_SHORT).show();
					}

	          }
	      });
	      alertDialog3.setNegativeButton("NO", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog, int which) {
	          Toast.makeText(getApplicationContext(), "Deletion Canceled..", Toast.LENGTH_SHORT).show();
	          dialog.cancel();
	          }
	      });
		alertDialog.setTitle("Sign Out?");
	      alertDialog.setMessage("Are you sure you want sign out?");
	      alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog,int which) {
	        	  Intent i = new Intent(DelUser.this,Login.class);
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
