package com.tvinci.modules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;

public  class CategoryNode implements Serializable {
	
	private static String INNER_CATEGORIES = "InnerCategories";
	private static String CHANNEL_ID = "ChannelID";
	private static String TITLE = "Title";
	
	String channelId;
	String Title;
	JSONArray InnerCategories = null;
	ArrayList<CategoryNode> ChildNodes = null;
	

	// build Node from Json Object
	public CategoryNode(JSONObject jsonObject)
	{
		try {
			channelId = jsonObject.getString(CHANNEL_ID);
			Title     = jsonObject.getString(TITLE);
			InnerCategories = jsonObject.getJSONArray(INNER_CATEGORIES);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CategoryNode(String jsonString) throws JSONException {
		this(new JSONObject(jsonString));
	}
	
	public CategoryNode(String channelId, String title, JSONArray innerCategories) {
		super();
		this.channelId = channelId;
		Title = title;
		InnerCategories = innerCategories;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public JSONArray getInnerCategories() {
		return InnerCategories;
	}

	public void setInnerCategories(JSONArray innerCategories) {
		InnerCategories = innerCategories;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	public ArrayList<CategoryNode> getChildrenNodes()
	{
		if (ChildNodes == null)
		{
            if (InnerCategories == null)
            {
            	return null;
            }
			ChildNodes = new ArrayList<CategoryNode>(InnerCategories.length());
			for (int i = 0; i < InnerCategories.length(); i++) {
				try {
					ChildNodes.add(new CategoryNode(InnerCategories.getJSONObject(i)));
				} catch (JSONException e) {					
					e.printStackTrace();
				}
				
			}
		}
		
		return ChildNodes;		
	}
	
	@Override
	public String toString() {
		return Title;
	}
	
	public ArrayList<Media> getMedias()
	{
        		return null;
	}
	
}

	
	/*JSONObject responseObj = new JSONObject(response);
	JSONArray results = responseObj.getJSONArray("results");
	
	if(results.length()==0)
	{
		this.setListAdapter(null);
		Toast.makeText(this, "No results!", Toast.LENGTH_LONG).show();
		return;						
	}
	              
	ArrayList<String> items = new ArrayList<String>();
	
	for (int i = 0; i < results.length(); i++) {
		JSONObject tweet = results.getJSONObject(i);
		
		String userName = tweet.getString("from_user_name");*/

