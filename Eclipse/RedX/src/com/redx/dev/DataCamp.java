package com.redx.dev;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

public class DataCamp {
	JSONObject json;
	JSONArray jarr;
	public String getCamp(String user)
	{
		String camp="";
		String response = null;
		int i=0;
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("user", user));
			try {
				response = CustomHttpClient.executeHttpPost(
						"http://192.168.243.1:8084/Redx/getcamp.do",postParameters);
				String res = response.toString();
				res = res.replaceAll("\\s+", "");
				res = res.replaceAll("\\s+", "");
				if (res.equals("0")) {
					return "camp not found";
				} else {
					json = new JSONObject(res);
					jarr = new JSONArray(json.getString("array"));
					while (!jarr.isNull(i)) {
						camp = camp+jarr.getJSONObject(i).getString(
								"camp")+",";
						i++;
					}
					return camp;
				}
			} catch (Exception e) {
				return "error";
			}
	}
}
