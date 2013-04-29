package com.redx.dev;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DispCampCity extends ListActivity implements OnItemClickListener {
	AlertDialog.Builder alertDialog, alertDialog1;

	int itemId;
	String[] inf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		alertDialog = new AlertDialog.Builder(DispCampCity.this);
		alertDialog1 = new AlertDialog.Builder(DispCampCity.this);
		View header = getLayoutInflater().inflate(R.layout.actionbar_layout,
				null);
		View footer = getLayoutInflater().inflate(R.layout.footer_layout, null);
		SearchCi val = new SearchCi(this);
		val.open();
		String data = val.getData();
		inf = data.split(",");
		val.deleteAll();
		val.close();
		ListView lv = getListView();
		lv.addHeaderView(header);
		lv.addFooterView(footer);
		lv.setTextFilterEnabled(true);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.dispresc, R.id.dispc_info, inf);
		setListAdapter(adapter);
		lv.setOnItemClickListener(this);
		registerForContextMenu(lv);
		alertDialog.setTitle("Sign Out?");
		alertDialog.setMessage("Are you sure you want sign out?");
		alertDialog.setPositiveButton("YES",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent i = new Intent(DispCampCity.this, Login.class);
						startActivity(i);
						finish();
					}
				});
		alertDialog.setNegativeButton("NO",
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
			alertDialog.show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getTitle() == "Add Event to Calendar") {
			function1(itemId);
		} else if (item.getTitle() == "Cancel") {
			function2(itemId);
		}
		return true;
	}

	private void function1(int Id) {
		// TODO Auto-generated method stub

		String title, dateStr, time;
		String addInfo = null;
		String place;
		int status = 1;
		long sTime = 0, eTime = 0;
		long sdmil;
		long edmil;

		String[] event = inf[Id - 1].split("\n");
		String[] dates = event[1].split("-");
		Toast.makeText(this, "Event Added to Calendar", Toast.LENGTH_SHORT)
				.show();

		// addInfo = "Shared by - " + event[0];

		title = event[0];

		dateStr = dates[0].substring(0, 9);
		SimpleDateFormat curFormater = new SimpleDateFormat("dd/MM/yyyy");
		Date sDate = null;
		try {
			sDate = curFormater.parse(dateStr);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sdmil = sDate.getTime();
		time = dates[0].substring(10, 15);

		int hour = Integer.parseInt(time.substring(0, 2));
		int min = Integer.parseInt(time.substring(3, 5));
		sTime = hour * 60 * 60 * 1000 + min * 60 * 1000;
		sTime = sTime + sdmil;

		dateStr = dates[1].substring(0, 9);
		curFormater = new SimpleDateFormat("dd/MM/yyyy");
		Date eDate = null;
		try {
			eDate = curFormater.parse(dateStr);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		edmil = sDate.getTime();
		time = dates[1].substring(10, 15);
		hour = Integer.parseInt(time.substring(0, 2));
		min = Integer.parseInt(time.substring(3, 5));
		eTime = hour * 60 * 60 * 1000 + min * 60 * 1000;
		eTime = eTime + edmil;

		int diffInDays = (int) ((eDate.getTime() - sDate.getTime()) / (1000 * 60 * 60 * 24));

		place = event[2];

		String eventUriString = "content://com.android.calendar/events";
		ContentValues eventValues = new ContentValues();
		eventValues.put("calendar_id", 1);
		eventValues.put("title", title);
		eventValues.put("description", addInfo);
		eventValues.put("eventLocation", place);
		eventValues.put("dtstart", sTime);
		eventValues.put("dtend", eTime);
		eventValues.put("eventStatus", status);

		eventValues.put("visibility", 3);
		eventValues.put("transparency", 0);
		eventValues.put("hasAlarm", 1); // 0 for false, 1 for true

		for (int i = 0; i <= diffInDays; i++) {
			eventValues.put("dtstart", sTime);
			eventValues.put("dtend", eTime);
			Uri eventsUri = Uri.parse(eventUriString);
			Uri url = getContentResolver().insert(eventsUri, eventValues);
			sTime = sTime + 86400000;
			eTime = eTime + 86400000;
		}
	}

	private void function2(int Id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Select an option");
		menu.add(0, v.getId(), 0, "Add Event to Calendar");
		menu.add(0, v.getId(), 0, "Cancel");
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		itemId = info.position;
	}
	
}