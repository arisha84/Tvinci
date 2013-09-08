package com.tvinci.modules;

import java.io.Serializable;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Media implements Serializable  {
	
	 //JSON Node names for Media
    private static final String TAG_MEDIA_NAME = "MediaName";
    private static final String TAG_LIKES_COUNTER = "like_counter";
    private static final String TAG_PIC_URL = "PicURL";
    private static final String TAG_FILE_URL = "URL";
    private static final String TAG_MEDIA_WEB_LINK = "MediaWebLink";
    private static final String TAG_DESCRIPTION = "Description";
    private static final String TAG_RATING = "Rating";
    private static final String TAG_KEY = "Key";
    private static final String TAG_VALUE = "Value";
    private static final String TAG_TAGS = "Tags";
    private String MediaName, picUrl, likeCounter, fileUrl, description, weblink;
	private HashMap<String, String> tagsMap;
	private float rating;	
	
	
	public Media (String JsonMediaString) throws JSONException
	{
		this(new JSONObject(JsonMediaString));
	}
	
	
	public Media(JSONObject jsonMediaObject) throws JSONException {
		
		
		this.MediaName =        jsonMediaObject.getString(TAG_MEDIA_NAME); 
        this.picUrl    =		jsonMediaObject.getString(TAG_PIC_URL);
		this.likeCounter =						jsonMediaObject.getString(TAG_LIKES_COUNTER);
		this.fileUrl =						jsonMediaObject.getString(TAG_FILE_URL);
		this.weblink =						jsonMediaObject.getString(TAG_MEDIA_WEB_LINK);
		this.description =						jsonMediaObject.getString(TAG_DESCRIPTION);
        this.rating =								Float.valueOf(jsonMediaObject.getString(TAG_RATING));
				
        tagsMap = new HashMap<String, String>();		
		JSONArray tagsArray = jsonMediaObject.getJSONArray(TAG_TAGS);
		
		for (int j = 0; j < tagsArray.length(); j++)
			tagsMap.put(tagsArray.getJSONObject(j).getString(TAG_KEY), tagsArray.getJSONObject(j).getString(TAG_VALUE));	
	}
	

	
	public HashMap<String, String> getTags() {
		return tagsMap;
	}

	public void setTags(HashMap<String, String> tags) {
		this.tagsMap = tags;
	}

	public String getWeblink() {
		return weblink;
	}

	public void setWeblink(String weblink) {
		this.weblink = weblink;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getFileUrl(){
		return fileUrl;
	}

	public String getMediaName() {
		return MediaName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public String getLikeCounter() {
		return likeCounter;
	}
	
	@Override
	public String toString() {
		return MediaName;
	}
	


}
