package com.tvinci.main;




import com.tvinci.modules.CategoryNode;
import com.tvinci.modules.Channel;
import com.tvinci.modules.Media;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.NavUtils;

public class ChannelListActivity extends Activity {

    private Channel m_channel;
    private ListView mListView;
    private ArrayAdapter<Media> m_ListAdapter;
    private LazyAdapter   m_ChannelLazyAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
               WindowManager.LayoutParams.FLAG_FULLSCREEN);
            
        setContentView(R.layout.activity_channel_list_view);
       // getActionBar().setDisplayHomeAsUpEnabled(true);        
        
        TextView tvChannelTitle = (TextView) findViewById(R.id.tvChannelName);
        tvChannelTitle.setText(getIntent().getStringExtra("title"));
        
        Intent intent = getIntent();
        m_channel = (Channel) intent.getExtras().getSerializable("channel"); 
        
        mListView = (ListView) findViewById(R.id.ChannlsList);
        
        m_ChannelLazyAdapter = new LazyAdapter(this, m_channel);
        
        //m_ListAdapter = new ArrayAdapter<Media>(this,
			//	android.R.layout.simple_list_item_1,m_channel.getMedias());    		
		
		mListView.setAdapter(m_ChannelLazyAdapter);    
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_channel_list, menu);
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

}
