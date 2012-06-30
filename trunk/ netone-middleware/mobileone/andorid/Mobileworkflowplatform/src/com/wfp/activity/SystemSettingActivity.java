package com.wfp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.wfp.util.ConnectionServiceThread2;
import com.wfp.util.HelpHandler2;

public class SystemSettingActivity extends Activity {

	//界面元素
	private ListView settingListView;
	
	//参数
	private static String SYS_PARAMS = "SystemParams";		//系统设置key
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.systemsetting);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.systemconfig);
        
        //初始化界面元素
        settingListView = (ListView) findViewById(R.id.settingListView);
        ArrayList<Map<String,Object>> settingList = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("settingImg", R.drawable.imgflush);
        map.put("settingText", getResources().getString(R.string.settingImgFlush));
        settingList.add(map);
        map = new HashMap<String,Object>();
        map.put("settingImg", R.drawable.range);
        map.put("settingText", getResources().getString(R.string.settingRangeMarket));
        settingList.add(map);
		SimpleAdapter settingAdapter = new SimpleAdapter(this, settingList,
				R.layout.setting_listitem, new String[] { "settingImg",
						"settingText" }, new int[] { R.id.settingImg,
						R.id.settingText });
		settingListView.setAdapter(settingAdapter);
		//系统配置列表项点击事件
		settingListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				if(position == 0){
					//下载服务端图片
			    	//正在下载提示
					Toast.makeText(SystemSettingActivity.this, R.string.initSyatemData, Toast.LENGTH_LONG).show();

					//获取到服务端流程名称数据，并装载数据
					HelpHandler2 helpHandler2 = new HelpHandler2(null,
							SystemSettingActivity.this);
					//启动请求服务端线程，封装数据给handler
					ConnectionServiceThread2 connServiceThread2 = new ConnectionServiceThread2(SystemSettingActivity.this, 28, helpHandler2, "");
					connServiceThread2.start();
				}else if(position == 1){
					//初始化网点范围设置对话框列表
					LayoutInflater inflater = getLayoutInflater();
			        final View rangeDialogLayout = inflater.inflate(R.layout.settingrange_dialog, null);
			        AlertDialog.Builder rangeDialogBuilder = new AlertDialog.Builder(SystemSettingActivity.this);
			        rangeDialogBuilder.setTitle(R.string.searchedMarket);
			        rangeDialogBuilder.setView(rangeDialogLayout);
			        
			        //范围设置文本框字符过滤
			        final EditText rangeEditText = (EditText) rangeDialogLayout.findViewById(R.id.rangeEditText);
			        //shared获取搜索半径
					SharedPreferences sysParamsShared = getSharedPreferences(SYS_PARAMS,0);
					String radiusValue = sysParamsShared.getString("radiusValue", "200");
			        rangeEditText.setText(radiusValue);
					//字符输入限制
					rangeEditText.setFilters(new InputFilter[] { new InputFilter() {
					    @Override
					    public CharSequence filter(CharSequence source, int start,
					           int end, Spanned dest, int dstart, int dend) {
					    	String limitStr = "1234567890";	//允许输入的字符
					    	String subStr = String.valueOf(source);	//截取当前输入字符
					    	//判断输入字符是否为允许输入字符
					    	if(limitStr.indexOf(subStr) < 0){
					    		source = "";
					    	}
					        return source;
					    }
					}});
			        //设置半径范围按钮点击事件
			        rangeDialogBuilder.setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							String rangeNumberValue = rangeEditText.getText().toString();
							if(!rangeNumberValue.equals("")) {
								SharedPreferences sysParamsShared = getSharedPreferences(SYS_PARAMS,0);
								Editor editor2 = sysParamsShared.edit();
								editor2.putString("radiusValue", rangeNumberValue);
								editor2.commit();
							}
						}
					});
			        //取消按钮点击事件
			        rangeDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
			        final AlertDialog rangeDialog = rangeDialogBuilder.create();
			        rangeDialog.show();
				}
			}
		});
		
    }
}
