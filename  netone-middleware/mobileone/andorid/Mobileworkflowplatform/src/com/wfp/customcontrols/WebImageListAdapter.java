package com.wfp.customcontrols;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

public class WebImageListAdapter extends SimpleAdapter {

	
	public WebImageListAdapter(Context context,
			List<? extends Map<String, ?>> data, int resource, String[] from,
			int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}

	public void setViewImage(ImageView v, String value) {
		BitmapFactory.Options option = new BitmapFactory.Options();
		
		if(value.indexOf("uploadimg") > 0)
			option.inSampleSize = 32; //将图片设为原来宽高的1/32，防止内存溢出
    	Bitmap bitmap = BitmapFactory.decodeFile(value, option);
    	((ImageView) v).setImageBitmap(bitmap);
    } 

}
