package com.tvinci.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

public class JsonQueryTask extends AsyncTask<String, Integer, String> {

	private Activity mActivity;
	private TaskListener mTvinciMain;
	ProgressDialog dialog;	
	
	
	public JsonQueryTask (Activity mActivity, TaskListener tvinciMain){
		this.mActivity = mActivity;
		mTvinciMain = tvinciMain;
		dialog = new ProgressDialog(mActivity);
	}
	
	@Override
	protected void onPreExecute() {
		dialog.setMessage("Loading...");	    
	    dialog.show();
	}
	
	
	@Override
	protected String doInBackground(String... Queries) {
		
		return DoQuery(Queries[0]);
	}
	
	@Override
	protected void onPostExecute(String result) {		
			if (result == null)
				return;
			
			//mActivity.
			mTvinciMain.onTaskFinished(result);
			//JSONObject responseObj = new JSONObject(result);
			//JSONArray FirstInnerCategories = responseObj.getJSONArray("InnerCategories");
			//ArrayList<String> items = new ArrayList<String>();
			
			dialog.hide();
		
	}
	
	private String DoQuery(String Query)
	{
		String response = sendSearchRequest(Query);		
	    return response;		
	}
	
	private String sendSearchRequest(String searchQuery) {
		BufferedReader input = null;
		HttpURLConnection httpCon = null;
		StringBuilder response = new StringBuilder();
		
		try {
		
			URL url = new URL(searchQuery);
			httpCon = (HttpURLConnection) url.openConnection();
			
			if(httpCon.getResponseCode()!=HttpURLConnection.HTTP_OK)
			{
				Log.e("myApp","Error Connecting!");
				return null;
			}
			
			input = new BufferedReader(new InputStreamReader(
					httpCon.getInputStream()));
			
			String line;
			
			while ((line = input.readLine()) !=null)
			{
				response.append(line+"\n");
			}			
		}
		
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		catch (MalformedURLException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e){
		     e.printStackTrace();
		}
		finally
		{
			try {
				if(input!=null)
					input.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			
			if(httpCon!=null)
				httpCon.disconnect();
		}
		
		return response.toString();
	}
	

}
