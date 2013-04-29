package com.redx.dev;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

public class DataState {
	JSONObject json;
	JSONArray jarr;
	public String getState()
	{
		String state="";
		String response = null;
		int i=0;
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("state", "demo"));
			try {
				response = CustomHttpClient.executeHttpPost(
						"http://192.168.243.1:8084/Redx/getstate.do",postParameters);
				String res = response.toString();
				res = res.replaceAll("\\s+", "");
				res = res.replaceAll("\\s+", "");
				if (res.equals("0")) {
					return "state not found";
				} else {
					json = new JSONObject(res);
					jarr = new JSONArray(json.getString("array"));
					while (!jarr.isNull(i)) {
						state = state+jarr.getJSONObject(i).getString(
								"state")+",";
						i++;
					}
					return state;
				}
			} catch (Exception e) {
				return "error";
			}
	}
	public String getCampState()
	{
		String state="";
		String response = null;
		int i=0;
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("state", "demo"));
			try {
				response = CustomHttpClient.executeHttpPost(
						"http://192.168.243.1:8084/Redx/getstatecamp.do",postParameters);
				String res = response.toString();
				res = res.replaceAll("\\s+", "");
				res = res.replaceAll("\\s+", "");
				if (res.equals("0")) {
					return "state not found";
				} else {
					json = new JSONObject(res);
					jarr = new JSONArray(json.getString("array"));
					while (!jarr.isNull(i)) {
						state = state+jarr.getJSONObject(i).getString(
								"state")+",";
						i++;
					}
					return state;
				}
			} catch (Exception e) {
				return "error";
			}
	}
}
