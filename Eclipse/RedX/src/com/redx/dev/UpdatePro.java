package com.redx.dev;

import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UpdatePro extends Activity implements Cities {
	EditText pw, cp, fn, tn, em, dob, ld;
	AutoCompleteTextView ci;
	Button reg;
	TextView un, error, lS;
	Spinner bg;
	private int mYear, mMonth, mDay;
	static final int DATE_DIALOG_ID = 0, DATE_DIALOG = 1;
	StringBuilder s, cd;
	String user;
	AlertDialog.Builder alertDialog, alertDialog1, alertDialog2;

	public void toLogin() {
		Intent i = new Intent(UpdatePro.this, Bntwrk.class);
		startActivity(i);
		finish();
	}

	public void setCurrentDate() {
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		cd = new StringBuilder().append(mDay).append("/").append(mMonth + 1)
				.append("/").append(mYear).append(" ");
	}

	public void updateDisplay() {
		s = new StringBuilder().append(mDay).append("/").append(mMonth + 1)
				.append("/").append(mYear).append(" ");
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
			dob.setText(s);
		}
	};
	private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
			ld.setText(s);
		}
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		case DATE_DIALOG:
			return new DatePickerDialog(this, dateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		GetPro gp = new GetPro();
		super.onCreate(savedInstanceState);
		alertDialog = new AlertDialog.Builder(UpdatePro.this);
		alertDialog1 = new AlertDialog.Builder(UpdatePro.this);
		alertDialog2 = new AlertDialog.Builder(UpdatePro.this);
		setContentView(R.layout.updatepro);
		un = (TextView) findViewById(R.id.ud_un);
		pw = (EditText) findViewById(R.id.reg_pw);
		cp = (EditText) findViewById(R.id.reg_cpw);
		fn = (EditText) findViewById(R.id.reg_fn);
		tn = (EditText) findViewById(R.id.reg_tn);
		em = (EditText) findViewById(R.id.reg_em);
		dob = (EditText) findViewById(R.id.reg_dob);
		ld = (EditText) findViewById(R.id.reg_ld);
		ci = (AutoCompleteTextView) findViewById(R.id.reg_city);
		reg = (Button) findViewById(R.id.btnRegister);
		error = (TextView) findViewById(R.id.reg_error);
		lS = (TextView) findViewById(R.id.link_to_login);
		bg = (Spinner) findViewById(R.id.reg_bg);
		reg.setText("Update");
		lS.setText("");
		Intent open = getIntent();
		user = open.getStringExtra("user");
		un.setText(user);
		setCurrentDate();
		updateDisplay();
		pw.setText(gp.getPass(user));
		cp.setText(gp.getPass(user));
		fn.setText(gp.getFullName(user));
		fn.setText(gp.getFullName(user));
		tn.setText(gp.getPhone(user));
		em.setText(gp.getEm(user));
		dob.setText(gp.getDob(user));
		ld.setText(gp.getld(user));
		ci.setText(gp.getCity(user));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, CITIES);
		ci.setAdapter(adapter);

		dob.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				showDialog(DATE_DIALOG_ID);
			}
		});
		ld.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				showDialog(DATE_DIALOG);
			}
		});
		reg.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				alertDialog.show();
			}
		});
		alertDialog.setTitle("Update?");
		alertDialog.setMessage("Are you sure you want to update?");
		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String city = ci.getText().toString();
						String state = "";
						String c = "";
						int x = 0;
						for (int i = 0; i < city.length() - 1; i++) {
							if (city.charAt(i) == '(')
								x++;
							if (x == 1 && !(city.charAt(i) == '('))
								state = state + city.charAt(i);
							else if (x == 0)
								c = c + city.charAt(i);
						}
						ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
						postParameters.add(new BasicNameValuePair("username",
								user));
						postParameters.add(new BasicNameValuePair("password",
								pw.getText().toString()));
						postParameters.add(new BasicNameValuePair("cpassword",
								cp.getText().toString()));
						postParameters.add(new BasicNameValuePair("fullname",
								fn.getText().toString()));
						postParameters.add(new BasicNameValuePair("city", c));
						postParameters.add(new BasicNameValuePair("state",
								state));
						postParameters.add(new BasicNameValuePair("phone", tn
								.getText().toString()));
						postParameters.add(new BasicNameValuePair("email", em
								.getText().toString()));
						postParameters.add(new BasicNameValuePair("dob", dob
								.getText().toString()));
						postParameters.add(new BasicNameValuePair("ldate", ld
								.getText().toString()));
						postParameters.add(new BasicNameValuePair("bgroup", bg
								.getSelectedItem().toString()));
						postParameters.add(new BasicNameValuePair("cdate", cd
								.toString()));
						String response = null;
						try {
							response = CustomHttpClient
									.executeHttpPost(
											"http://192.168.243.1:8084/Redx/updatepro.do",
											postParameters);
							String res = response.toString();
							res = res.replaceAll("\\s+", "");
							if (res.equals("0"))
								Toast.makeText(getApplicationContext(),
										"Update Failed..", Toast.LENGTH_SHORT)
										.show();
							else if (res.equals("1"))
								Toast.makeText(getApplicationContext(),
										"Passwords do not match..",
										Toast.LENGTH_SHORT).show();
							else if (res.equals("2"))
								Toast.makeText(getApplicationContext(),
										"Invalid Age Limit(<18yr or >60yr)..",
										Toast.LENGTH_SHORT).show();
							else if (res.equals("3"))
								Toast.makeText(
										getApplicationContext(),
										"Last date of Blood donation invalid..",
										Toast.LENGTH_SHORT).show();
							else if (res.equals("4"))
								Toast.makeText(getApplicationContext(),
										"Profile Updated..", Toast.LENGTH_SHORT)
										.show();
							else if (res.equals("5"))
								Toast.makeText(getApplicationContext(),
										"Invalid Entries..", Toast.LENGTH_SHORT)
										.show();
							else if (res.equals("6"))
								Toast.makeText(getApplicationContext(),
										"Wrong Phone No..", Toast.LENGTH_SHORT)
										.show();
							else
								Toast.makeText(getApplicationContext(),
										"Try Again..", Toast.LENGTH_SHORT)
										.show();

						} catch (Exception e) {
							Toast.makeText(getApplicationContext(),
									"Error Connecting to Server..",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
		alertDialog.setNegativeButton("NO",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getApplicationContext(),
								"Update Canceled", Toast.LENGTH_SHORT).show();
						dialog.cancel();
					}
				});
		alertDialog2.setTitle("Sign Out?");
		alertDialog2.setMessage("Are you sure you want sign out?");
		alertDialog2.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent i = new Intent(UpdatePro.this, Login.class);
						startActivity(i);
						finish();
					}
				});
		alertDialog2.setNegativeButton("NO",
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
			alertDialog2.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
