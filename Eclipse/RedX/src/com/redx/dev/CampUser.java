package com.redx.dev;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
public class CampUser extends Activity {
	JSONObject json;
	JSONArray jarr;
	int i;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent open = getIntent();
		String user = open.getStringExtra("user");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("user", user));
		String response = null;
		try {
			response = CustomHttpClient.executeHttpPost(
					"http://192.168.243.1:8084/Redx/cuser.do", postParameters);
			String res = response.toString();
			res = res.replaceAll("\\s+", "");
			if (res.equals("0")) {
				Toast.makeText(this, "Camp not found...", Toast.LENGTH_LONG)
				.show();
				finish();
			} else {
				json = new JSONObject(res);
				jarr = new JSONArray(json.getString("array"));
				i = 0;
				SearchCi entry = new SearchCi(CampUser.this);
				entry.open();
				while (!jarr.isNull(i)) {
					String name = jarr.getJSONObject(i).getString("name");
					String time = jarr.getJSONObject(i).getString("time");
					String venue = jarr.getJSONObject(i).getString("venue");
					i++;
					entry.createEntry(name, time, venue);
				}
				entry.close();
				Intent disp = new Intent(CampUser.this, DispCampCity.class);
				startActivity(disp);
				finish();
			}
		} catch (Exception e) {
			Intent back = new Intent(CampUser.this, SearchCamp.class);
			startActivity(back);
			finish();
		}
	}
}
