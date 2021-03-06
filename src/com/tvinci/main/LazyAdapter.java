package com.tvinci.main;

import java.util.ArrayList;
import java.util.HashMap;

import com.tvinci.modules.Channel;
import com.tvinci.modules.Media;
 
import ImageLoader.ImageLoader;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class LazyAdapter extends BaseAdapter {
 
    private Activity activity;
    private Channel data;     
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
 
    public LazyAdapter(Activity a, Channel d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
 
    public int getCount() {
        return data.getMedias().size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);
 
        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView likes = (TextView)vi.findViewById(R.id.likes); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image       
   
        Media media = data.getMedias().get(position);
 
        // Setting all values in listview
        title.setText(media.getMediaName());
        likes.setText(media.getLikeCounter());
        imageLoader.DisplayImage(media.getPicUrl(), thumb_image);
        return vi;
    }
}