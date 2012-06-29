package com.wfp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

public class QueryContentActivity extends Activity {
	
	//界面视图元素
	private Spinner searchTypeListView;
	private ListView searchResultListView;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.RGBA_8888);	//防止PNG图片失真
        setContentView(R.layout.querymain);
    
        //准备要添加的数据条目
        String[] type = {"业务流程","频道文章","移动采集"};
        
        //实例化一个适配器
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, type);
        //设置下拉列表样式
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //获得Spinner实例
        searchTypeListView = (Spinner)findViewById(R.id.queryTypeSpinner);
        //将Spinner和数据适配器关联
        searchTypeListView.setAdapter(arrayAdapter);
        
      
        //准备要添加的数据条目
        List<Map<String, Object>> listItems = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < 10; i++) {
        	Map<String, Object> item2 = new HashMap<String, Object>();
            item2.put("atitle", i+"中国移动福建省宁德市分公司");
            item2.put("adatetime", "2012-12-12");
            listItems.add(item2);
		}
        
        //实例化一个适配器
        SimpleAdapter listAdapter = new SimpleAdapter(this, listItems, R.layout.annount_listitem, new String[]{"atitle", "adatetime"}, new int[]{R.id.annountTitle, R.id.annountDatetime});
        //获得Spinner实例
        searchResultListView = (ListView)findViewById(R.id.searchResultListView);

        //将Spinner和数据适配器关联
        searchResultListView.setAdapter(listAdapter);
    }
}
