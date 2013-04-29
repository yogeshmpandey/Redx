package com.redx.dev;

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

public class SearchCamp extends Activity {
	Button sc, ss,su;
	AlertDialog.Builder alertDialog , alertDialog1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camp_layout);
		sc = (Button) findViewById(R.id.cam_ci);
		ss = (Button) findViewById(R.id.cam_st);
		su = (Button) findViewById(R.id.cam_us);
		alertDialog  = new AlertDialog.Builder(SearchCamp.this);
        alertDialog1  = new AlertDialog.Builder(SearchCamp.this);
		sc.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				Intent open = getIntent();
				Intent i = new Intent(SearchCamp.this, CampCity.class);
				i.putExtra("user",open.getStringExtra("user"));
				startActivity(i);
			}
		});
		ss.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				Intent open = getIntent();
				Intent i = new Intent(SearchCamp.this, CampState.class);
				i.putExtra("user",open.getStringExtra("user"));
				startActivity(i);
			}
		});
		su.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				Intent open = getIntent();
				Intent i = new Intent(SearchCamp.this, CampUser.class);
				i.putExtra("user",open.getStringExtra("user"));
				startActivity(i);
			}
		});
		 alertDialog.setTitle("Sign Out?");
	      alertDialog.setMessage("Are you sure you want sign out?");
	      alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog,int which) {
	        	  Intent i = new Intent(SearchCamp.this,Login.class);
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
