package com.redx.dev;

import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CampCreate extends Activity implements Cities {
	EditText cn,sd,st,ed,et;
	AlertDialog.Builder alertDialog , alertDialog1,alertDialog2;
	AutoCompleteTextView ci;
	Button cr;
	TextView error,un;
	private int mYear, mMonth, mDay;
	private int mHour,mMinute;
	static final int TIME_DIALOG_ID=2,TIME_DIALOG=3;
	static final int DATE_DIALOG_ID = 0,DATE_DIALOG=1;
	StringBuilder s, cd,t;
	
	public void toLogin() {
		Intent i = new Intent(CampCreate.this, Bntwrk.class);
		startActivity(i);
	}
	public void setCurrent()
	{
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		cd=new StringBuilder().append(mDay).append("/").append(mMonth + 1)
				.append("/").append(mYear).append(" ");
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
	}
	public void updateDisplay() {
		s = new StringBuilder().append(mDay).append("/").append(mMonth + 1)
				.append("/").append(mYear).append(" ");
	}
	public void updateDisp()
	{
		t = new StringBuilder().append(pad(mHour)).append(":")
				.append(pad(mMinute));
	}
	private static String pad(int c){
		if(c>=10)
			return String.valueOf(c);
		else
			return "0"+String.valueOf(c);
	}
	private TimePickerDialog.OnTimeSetListener mTime = 
			new TimePickerDialog.OnTimeSetListener() {
				
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
					mHour = hourOfDay;
					mMinute = minute;
					updateDisp();
					st.setText(t);
				}
			};
			private TimePickerDialog.OnTimeSetListener mTime1 = 
					new TimePickerDialog.OnTimeSetListener() {
						
						public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
							mHour = hourOfDay;
							mMinute = minute;
							updateDisp();
							et.setText(t);
						}
					};
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
			sd.setText(s);
		}
	};
	private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
			ed.setText(s);
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		case DATE_DIALOG:
			return new DatePickerDialog(this,dateSetListener,mYear,mMonth,mDay);
		case TIME_DIALOG_ID:
			return new TimePickerDialog(this,mTime,mHour,mMinute,false);
		case TIME_DIALOG:
			return new TimePickerDialog(this,mTime1,mHour,mMinute,false);	
		}
		return null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createcamp);
		alertDialog  = new AlertDialog.Builder(CampCreate.this);
        alertDialog1  = new AlertDialog.Builder(CampCreate.this);
        alertDialog2  = new AlertDialog.Builder(CampCreate.this);
		cn = (EditText) findViewById(R.id.c_na);
		sd = (EditText) findViewById(R.id.c_sd);
		st = (EditText) findViewById(R.id.c_st);
		ed = (EditText) findViewById(R.id.c_ed);
		et = (EditText) findViewById(R.id.c_et);
		ci = (AutoCompleteTextView) findViewById(R.id.c_city);
		cr = (Button) findViewById(R.id.btnCreate);
		error = (TextView) findViewById(R.id.c_error);
		un = (TextView) findViewById(R.id.c_un);
		Intent i = getIntent();
		un.setText(i.getStringExtra("user"));
		setCurrent();
		updateDisplay();
		updateDisp();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item,CITIES);
		ci.setAdapter(adapter);
		sd.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				showDialog(DATE_DIALOG_ID);
			}
		});	
		ed.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				showDialog(DATE_DIALOG);
			}
		});	
		st.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				showDialog(TIME_DIALOG_ID);
			}
		});
		et.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				showDialog(TIME_DIALOG);
			}
		});
		
		cr.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {	
				alertDialog2.show();				
			}
		});
		alertDialog2.setTitle("Create?");
	      alertDialog2.setMessage("Are you sure you want create Blood Donation Camp?");
	      alertDialog2.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog,int which) {
	        	  String city = ci.getText().toString();
					String state="";
					String c="";
	                int x=0;
	                for(int i=0;i<city.length()-1;i++)
	                {
	                    if(city.charAt(i)=='(')
	                    	x++;
	                    if(x==1 && !(city.charAt(i)=='('))
	                    state=state+city.charAt(i);
	                        else
	                        	if(x==0)
	                            c=c+city.charAt(i);
	                }
					ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
					postParameters.add(new BasicNameValuePair("username", un
							.getText().toString()));
					postParameters.add(new BasicNameValuePair("campname", cn
							.getText().toString()));
					postParameters.add(new BasicNameValuePair("startday", sd
							.getText().toString()));
					postParameters.add(new BasicNameValuePair("starttime", st
							.getText().toString()));
					postParameters.add(new BasicNameValuePair("city", c));
					postParameters.add(new BasicNameValuePair("state", state));
					postParameters.add(new BasicNameValuePair("endday", ed.getText()
							.toString()));
					postParameters.add(new BasicNameValuePair("etime", et.getText()
							.toString()));
					String response = null;
					try {
						response = CustomHttpClient.executeHttpPost(
								"http://192.168.243.1:8084/Redx/bdcamp.do",
								postParameters);
						String res = response.toString();
						res = res.replaceAll("\\s+", "");
						if (res.equals("0"))
							Toast.makeText(getApplicationContext(), "Camp Name already exists..", Toast.LENGTH_SHORT).show();
						else if (res.equals("1"))
							Toast.makeText(getApplicationContext(), "Reenter event's timing..", Toast.LENGTH_SHORT).show();
						else if (res.equals("2"))
							Toast.makeText(getApplicationContext(), "Blood Donation Camp Created..", Toast.LENGTH_SHORT).show();
						else if (res.equals("4"))
							Toast.makeText(getApplicationContext(), "Invalid Enteries..", Toast.LENGTH_SHORT).show();
						else 
							Toast.makeText(getApplicationContext(), "Try Again..", Toast.LENGTH_SHORT).show();
						
					} catch (Exception e) {
							Toast.makeText(getApplicationContext(), "Error Connecting To Server..", Toast.LENGTH_SHORT).show();
					}
	          }
	      });
	      alertDialog2.setNegativeButton("NO", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog, int which) {
	          Toast.makeText(getApplicationContext(), "Blood Donation Camp Canceled..", Toast.LENGTH_SHORT).show();
	          dialog.cancel();
	          }
	      });
		alertDialog.setTitle("Sign Out?");
	      alertDialog.setMessage("Are you sure you want sign out?");
	      alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog,int which) {
	        	  Intent i = new Intent(CampCreate.this,Login.class);
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
