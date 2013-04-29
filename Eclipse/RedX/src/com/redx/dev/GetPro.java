package com.redx.dev;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.Toast;

public class GetPro extends Activity{
	JSONObject json;
	JSONArray jarr;
	String un, pw, ci, st, fn, pn, em, dob, ld, bg;
	public void getCamp(String user)
	{		
		String response = null;
		int i=0;
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("user", user));
			try {
				response = CustomHttpClient.executeHttpPost(
						"http://192.168.243.1:8084/Redx/getpro.do",postParameters);
				String res = response.toString();
				res = res.replaceAll("\\s+", "");
				res = res.replaceAll("\\s+", "");
					json = new JSONObject(res);
					jarr = new JSONArray(json.getString("array"));
					while (!jarr.isNull(i)) {
						un=jarr.getJSONObject(i).getString(
								"name");
						pw=jarr.getJSONObject(i).getString(
								"pass");
						fn=jarr.getJSONObject(i).getString(
								"fn");
						ci=jarr.getJSONObject(i).getString(
								"city");
						st=jarr.getJSONObject(i).getString(
								"state");
						pn=jarr.getJSONObject(i).getString(
								"phone");
						dob=jarr.getJSONObject(i).getString(
								"dob");
						ld=jarr.getJSONObject(i).getString(
								"ld");
						bg=jarr.getJSONObject(i).getString(
								"bg");
						em=jarr.getJSONObject(i).getString(
								"em");
						
						i++;
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public String getUser(String user)
	{
		getCamp(user);
		return un;		
	}
	public String getPass(String user)
	{
		getCamp(user);
		return pw;		
	}
	public String getFullName(String user)
	{
		getCamp(user);
		return fn;		
	}
	public String getCity(String user)
	{
		getCamp(user);
		return ci+"("+st+")";		
	}
	public String getPhone(String user)
	{
		getCamp(user);
		return pn;		
	}
	public String getDob(String user)
	{
		getCamp(user);
		dob = dob.replace('_','/');
		return dob;		
	}
	public String getld(String user)
	{
		getCamp(user);
		ld = ld.replace('_','/');
		return ld;		
	}
	public String getBg(String user)
	{
		getCamp(user);
		return bg;		
	}
	public String getEm(String user)
	{
		getCamp(user);
		return em;		
	}
}
