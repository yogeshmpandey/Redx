package com.redx.dev;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends Activity implements Cities {
	EditText un, pw, cp, fn, tn, em, dob, ld;
	AutoCompleteTextView ci;
	Button reg;
	TextView error, lS;
	Spinner bg;
	private int mYear, mMonth, mDay;
	static final int DATE_DIALOG_ID = 0, DATE_DIALOG = 1;
	StringBuilder s, cd;
	String usn;

	public void toLogin() {
		Intent i = new Intent(Register.this, Login.class);
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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		un = (EditText) findViewById(R.id.reg_un);
		pw = (EditText) findViewById(R.id.reg_pw);
		cp = (EditText) findViewById(R.id.reg_cpw);
		fn = (EditText) findViewById(R.id.reg_fn);
		tn = (EditText) findViewById(R.id.reg_tn);
		em = (EditText) findViewById(R.id.reg_em);
		dob = (EditText) findViewById(R.id.reg_dob);
		((EditText) findViewById(R.id.reg_dob))
				.setInputType(InputType.TYPE_NULL);
		ld = (EditText) findViewById(R.id.reg_ld);
		ci = (AutoCompleteTextView) findViewById(R.id.reg_city);
		reg = (Button) findViewById(R.id.btnRegister);
		error = (TextView) findViewById(R.id.reg_error);
		lS = (TextView) findViewById(R.id.link_to_login);
		bg = (Spinner) findViewById(R.id.reg_bg);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.list_item, CITIES);
		ci.setAdapter(adapter);
		setCurrentDate();
		updateDisplay();
		lS.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				toLogin();
			}
		});
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
				usn = un.getText().toString();
				String pass = pw.getText().toString();
				String cpass = cp.getText().toString();
				String full = fn.getText().toString();
				String tel = tn.getText().toString();
				String email = em.getText().toString();
				String dobirth = dob.getText().toString();
				String ldate = ld.getText().toString();
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters.add(new BasicNameValuePair("username", usn));
				postParameters.add(new BasicNameValuePair("password", pass));
				postParameters.add(new BasicNameValuePair("cpassword", cpass));
				postParameters.add(new BasicNameValuePair("fullname", full));
				postParameters.add(new BasicNameValuePair("city", c));
				postParameters.add(new BasicNameValuePair("state", state));
				postParameters.add(new BasicNameValuePair("phone", tel));
				postParameters.add(new BasicNameValuePair("email", email));
				postParameters.add(new BasicNameValuePair("dob", dobirth));
				postParameters.add(new BasicNameValuePair("ldate", ldate));
				postParameters.add(new BasicNameValuePair("bgroup", bg
						.getSelectedItem().toString()));
				postParameters.add(new BasicNameValuePair("cdate", cd
						.toString()));
				String response = null;
				try {
					response = CustomHttpClient.executeHttpPost(
							"http://192.168.243.1:8084/Redx/register.do",
							postParameters);
					String res = response.toString();
					res = res.replaceAll("\\s+", "");
					if (res.equals("0"))
						Toast.makeText(getApplicationContext(),
								"Username exists..", Toast.LENGTH_SHORT).show();
					else if (res.equals("1"))
						Toast.makeText(getApplicationContext(),
								"Passwords do not match..", Toast.LENGTH_SHORT)
								.show();
					else if (res.equals("2"))
						Toast.makeText(getApplicationContext(),
								"Invalid Age limits(17yr<age<60yr)..",
								Toast.LENGTH_SHORT).show();
					else if (res.equals("3"))
						Toast.makeText(getApplicationContext(),
								"Invalid last date of blood donation..",
								Toast.LENGTH_SHORT).show();
					else if (res.equals("4")) {
						getLoc();
						Toast.makeText(getApplicationContext(),
								"Registered!! You can now Login..",
								Toast.LENGTH_SHORT).show();
					} else if (res.equals("6"))
						Toast.makeText(getApplicationContext(),
								"Wrong Phone No..", Toast.LENGTH_SHORT).show();
					else if (res.equals("5"))
						Toast.makeText(getApplicationContext(),
								"Wrong Input..", Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(getApplicationContext(), "Try Again..",
								Toast.LENGTH_SHORT).show();

				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"Error connecting to server..", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

	}

	public void getLoc() {
		Geocoder geocoder;
		String bestProvider;
		List<Address> user = null;
		double lat;
		double lng;
		LocationManager lm = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		bestProvider = lm.getBestProvider(criteria, false);
		Location location = lm.getLastKnownLocation(bestProvider);

		if (location == null) {
			Toast.makeText(this, "Location Not found", Toast.LENGTH_LONG)
					.show();
		} else {
			geocoder = new Geocoder(this);
			try {
				user = geocoder.getFromLocation(location.getLatitude(),
						location.getLongitude(), 1);
				lat = (double) user.get(0).getLatitude();
				lng = (double) user.get(0).getLongitude();
				String latitude = lat + "";
				String longitude = lng + "";
				Toast.makeText(this, lat + ":" + lng, Toast.LENGTH_SHORT)
						.show();
				ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
				postParameters
						.add(new BasicNameValuePair("latitude", latitude));
				postParameters.add(new BasicNameValuePair("longitude",
						longitude));
				postParameters.add(new BasicNameValuePair("user", usn));
				String response = null;
				try {
					response = CustomHttpClient.executeHttpPost(
							"http://192.168.243.1:8084/Redx/position.do",
							postParameters);
					String res = response.toString();
					res = res.replaceAll("\\s+", "");
					if (res.equals("1")) {
						Toast.makeText(getApplicationContext(),
								"Location Received..", Toast.LENGTH_SHORT)
								.show();
					} else
						Toast.makeText(getApplicationContext(), "Error...",
								Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(),
							"Error Connecting to Server..", Toast.LENGTH_SHORT)
							.show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
