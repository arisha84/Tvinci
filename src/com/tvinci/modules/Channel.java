package com.tvinci.modules;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Channel implements Serializable{

	private ArrayList<Media> m_Medias;
	
	public Channel(String jsonString) throws JSONException {	
		
		this(new JSONArray(jsonString));
		
	}
	
	public Channel (JSONArray  xJSONArrayMedias) throws JSONException
	{
		m_Medias = new ArrayList<Media>();
		
		for ( int i = 0; i < xJSONArrayMedias.length(); i++)
		{
			
			m_Medias.add(new Media(xJSONArrayMedias.getJSONObject(i)));
			
	    }
		
	}

	public ArrayList<Media> getMedias() {
		return m_Medias;
	}

}

