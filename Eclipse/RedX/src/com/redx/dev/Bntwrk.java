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
public class Bntwrk extends Activity{
	Button up,sd,bc,sc,uc,yc;
	 AlertDialog.Builder alertDialog , alertDialog1;
	 String user;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 Intent open = getIntent();
			user = open.getStringExtra("user");	
			
	        setContentView(R.layout.dashboard_layout);
	        alertDialog  = new AlertDialog.Builder(Bntwrk.this);
	        alertDialog1  = new AlertDialog.Builder(Bntwrk.this);
	       up=(Button)findViewById(R.id.ntw_up);
	       sd=(Button)findViewById(R.id.ntw_ds);
	       bc=(Button)findViewById(R.id.ntw_bc);
	       sc=(Button)findViewById(R.id.ntw_sc);
	       uc=(Button)findViewById(R.id.ntw_uc);
	       yc=(Button)findViewById(R.id.ntw_yc);	        
	      up.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	                Intent i = new Intent(getApplicationContext(), UpdatePro.class);
	                i.putExtra("user",user);
	                startActivity(i);
	            }
	        });
	      sd.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	                Intent i = new Intent(getApplicationContext(), SearchDonor.class);
	                i.putExtra("user",user);
	                startActivity(i);
	            }
	        });
	      bc.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	                Intent i = new Intent(getApplicationContext(), CampCreate.class);
	                i.putExtra("user",user);
	                startActivity(i);
	            }
	        });
	      sc.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	                Intent i = new Intent(getApplicationContext(), SearchCamp.class);
	                i.putExtra("user",user);
	                startActivity(i);
	            }
	        });
	      uc.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	                Intent i = new Intent(getApplicationContext(), UpdateCamp.class);
	                i.putExtra("user",user);
	                startActivity(i);
	            }
	        });
	      yc.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View view) {
	                Intent i = new Intent(getApplicationContext(), DelUser.class);
	                i.putExtra("user",user);
	                startActivity(i);
	            }
	        });
	     
	      alertDialog.setTitle("Sign Out?");
	      alertDialog.setMessage("Are you sure you want sign out?");
	      alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog,int which) {
	        	  Intent i = new Intent(Bntwrk.this,Login.class);
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
