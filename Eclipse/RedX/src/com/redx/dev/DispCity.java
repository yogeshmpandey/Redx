package com.redx.dev;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DispCity extends ListActivity {
	AlertDialog.Builder alertDialog , alertDialog1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		alertDialog  = new AlertDialog.Builder(DispCity.this);
        alertDialog1  = new AlertDialog.Builder(DispCity.this);
		View header = getLayoutInflater().inflate(R.layout.actionbar_layout, null);
		View footer = getLayoutInflater().inflate(R.layout.footer_layout, null);
		Search val = new Search(this);
		val.open();
		String data = val.getData();
		String[] inf = data.split(",");	
		val.deleteAll();
		val.close();
		ListView lv = getListView();
		lv.addHeaderView(header);
		lv.addFooterView(footer);
		lv.setTextFilterEnabled(true);
		  setListAdapter(new ArrayAdapter<String>(this, R.layout.dispres,R.id.disp_info, inf));
		  alertDialog.setTitle("Sign Out?");
	      alertDialog.setMessage("Are you sure you want sign out?");
	      alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog,int which) {
	        	  Intent i = new Intent(DispCity.this,Login.class);
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