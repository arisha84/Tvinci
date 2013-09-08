package com.tvinci.modules;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;



public  class CategoryParser {
	
	private CategoryNode  m_MainNode;
	
	private ArrayList<CategoryNode> m_ChannelsArray; 
	
	
	public CategoryParser(JSONObject mainNode) {
		m_MainNode = new CategoryNode(mainNode);
	}



	public ArrayList<String> getChannelsArray() {
		 
		ArrayList<String> stubList = new ArrayList<String>();
		stubList.add("a");
		stubList.add("b");
		stubList.add("c");
		
		
		
		
		return stubList;
	}
	
	
	
	

}
