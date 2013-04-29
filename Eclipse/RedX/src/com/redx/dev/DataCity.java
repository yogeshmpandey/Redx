package com.redx.dev;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

public class DataCity {
	JSONObject json;
	JSONArray jarr;
	public String getCity()
	{
		String city="";
		String response = null;
		int i=0;
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("city", "demo"));
			try {
				response = CustomHttpClient.executeHttpPost(
						"http://192.168.243.1:8084/Redx/getcity.do",postParameters);
				String res = response.toString();
				res = res.replaceAll("\\s+", "");
				res = res.replaceAll("\\s+", "");
				if (res.equals("0")) {
					return "city not found";
				} else {
					json = new JSONObject(res);
					jarr = new JSONArray(json.getString("array"));
					while (!jarr.isNull(i)) {
						city = city+jarr.getJSONObject(i).getString(
								"city")+",";
						i++;
					}
					return city;
				}
			} catch (Exception e) {
				return "error";
			}
	}
	public String getCampCity()
	{
		String city="";
		String response = null;
		int i=0;
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("city", "demo"));
			try {
				response = CustomHttpClient.executeHttpPost(
						"http://192.168.243.1:8084/Redx/getcampcity.do",postParameters);
				String res = response.toString();
				res = res.replaceAll("\\s+", "");
				res = res.replaceAll("\\s+", "");
				if (res.equals("0")) {
					return "city not found";
				} else {
					json = new JSONObject(res);
					jarr = new JSONArray(json.getString("array"));
					while (!jarr.isNull(i)) {
						city = city+jarr.getJSONObject(i).getString(
								"city")+",";
						i++;
					}
					return city;
				}
			} catch (Exception e) {
				return "error";
			}
	}
}
