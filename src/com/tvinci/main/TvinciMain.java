package com.tvinci.main;

import java.util.ArrayList;
import java.util.Stack;

import org.json.JSONException;
import org.json.JSONObject;


import com.tvinci.modules.CategoryNode;
import com.tvinci.modules.CategoryParser;
import com.tvinci.modules.Channel;



import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.support.v4.app.NavUtils;

public class TvinciMain extends Activity implements OnItemClickListener{
	
	public static final String LOG_TAG = "TvinciMain";
	public static final String QUERY = "http://test.tvinci.com/tvpapi/gateways/data.aspx";
	public static final String GET_CHANNEL_URL = "http://test.tvinci.com/tvpapi/gateways/getchannel.aspx?ChannelID=";
	protected static final int CHANNEL_LIST_ACTIVITY = 1;
	
	
	
	private JsonQueryTask   m_JsonQueryTask;
	private CategoryParser      m_CategoryParser;
	private ListView mListView;
	private ArrayAdapter<CategoryNode> m_ListAdapter;
	private CategoryNode CurrentNode;	
	private CategoryNode StartNode;
	private ArrayList<CategoryNode> CurrentCategories;
	private Channel currentChannel;
	private Stack <CategoryNode> CurrentNodeStack = new Stack<CategoryNode>(); 
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvinci_main);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        if(m_JsonQueryTask !=null && m_JsonQueryTask.getStatus() != AsyncTask.Status.FINISHED )
    	{
    		Log.d(LOG_TAG,"already running...");
    		return;
    	}
        
        mListView = (ListView) findViewById(R.id.list);    	
        mListView.setOnItemClickListener(this);
    	
        m_JsonQueryTask = new JsonQueryTask(this,new TaskListener() {

			public void onTaskFinished(String response) {
				processJsonResponse(response);
			}
		   });       
        
        m_JsonQueryTask.execute(QUERY);        
		
    }
    

	
    
    public void processJsonResponse (String jsonStringMainNode)
    {
    	try {
    		
    		//m_CategoryParser = new CategoryParser(new JSONObject(jsonStringMainNode));
    		//ArrayList<String> Channels = m_CategoryParser.getChannelsArray();
    		
    		CurrentNode = new CategoryNode(jsonStringMainNode); 
    		StartNode = CurrentNode; 
    		CurrentNodeStack.push(CurrentNode);
    		setCurrentCatagoriesForView();    		    			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   	
    	
    }
    
    private void setCurrentCatagoriesForView ()
    {  
    	
    	CurrentCategories = CurrentNode.getChildrenNodes();
    	
    	// check if this is a channel
    	if (CurrentCategories == null)
    	{
    		
    		if (CurrentNode.getChannelId() != null)
    		{
    			//display channel in new activity
    			JsonQueryTask xJsonQueryTask = new JsonQueryTask(this,new TaskListener() {

    				public void onTaskFinished(String response) {
    					processJsonChannelResponse(response, CurrentNode.getTitle());
    				}
    			   });       
    	        
    			// we pop the last node because a new activity will be started.
    			CurrentNodeStack.pop();
    			
    			xJsonQueryTask.execute(GET_CHANNEL_URL + CurrentNode.getChannelId());     	
    		
    			
    		}
    		else
    		{
    			//Something is wrong - must be channel or have list of categories
    			Log.w(LOG_TAG, "Something is wrong - must be channel or have list of categories");
    			
    		}
    		
    	}
    	
    	// we have inner categories, display them
    	else
    	{
    		m_ListAdapter = new ArrayAdapter<CategoryNode>(this,
    				android.R.layout.simple_list_item_1,CurrentCategories);    		
    		
    		mListView.setAdapter(m_ListAdapter);    		
    	}    	
    	    	
    }   
    
    public void processJsonChannelResponse (String jsonChannelString, String selectedChannelName)
    {
    	try {
			currentChannel = new Channel(jsonChannelString);
			
			Intent intent = new Intent(this, ChannelListActivity.class);
			intent.putExtra("channel", currentChannel);
			intent.putExtra("title", selectedChannelName);
			startActivityForResult(intent, CHANNEL_LIST_ACTIVITY);
			
			
		} catch (JSONException e) {
			Log.e(LOG_TAG, "Exception thrown in processJsonChannelResponse");
			e.printStackTrace();
		}
    }
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tvinci_main, menu);        
        
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
		    int position, long id) {
		CurrentNodeStack.push(CurrentNode);
		
		CurrentNode = CurrentCategories.get(position);		
		
		setCurrentCatagoriesForView();
		
		/*TextView tv = (TextView) v;
		String colorName = (String) tv.getText();
       Log.d(LOG_TAG, "colorName= " + colorName);*/
		
	}
	

	@Override
	public void onBackPressed() {
		
		if (CurrentNodeStack.isEmpty())
		{
			Log.e(LOG_TAG, "Node Stack is empty - it's a bug!");
			return;
		}
		CategoryNode TempNode = CurrentNodeStack.peek();
		
		if (TempNode == StartNode)
		{
			CurrentNode = StartNode;
			setCurrentCatagoriesForView();
		}		
		
		else {
			 CurrentNode = CurrentNodeStack.pop();		
         	  setCurrentCatagoriesForView();
		}	
	
	}


}
