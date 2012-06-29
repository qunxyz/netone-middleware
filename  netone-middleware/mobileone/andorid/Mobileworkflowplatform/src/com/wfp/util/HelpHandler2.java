package com.wfp.util;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.wfp.activity.R;
import com.wfp.config.PathConfig;

public class HelpHandler2 extends Handler {

	//传入的界面元素
	private ProgressDialog pDialog;
	private Activity activity;
	private ListView marketListView;
	private AlertDialog alertDialog;
	
	private TextView nowByte;
	private ProgressBar upload_pb;
		
	//参数
	private static int LOAD_DONE = 10;
	private String activityName;
	ArrayList formDataAndMuskList;			//表单数据 

	private static String SHARED_USERINFO = "userinfo";		//账户信息key
    private static String SERVICE_INFO = "SERVICEINFO";		//服务端信息key
    private static String serviceip;	//服务端IP 
	
	public HelpHandler2(ProgressDialog pDialog, Activity activity){
		
		Log.i("handlerpp!!", "handler");
		this.pDialog = pDialog;
		this.activity = activity;
		activityName = activity.getClass().getName();

		//服务端IP
		SharedPreferences serviceInfo = activity.getSharedPreferences(SERVICE_INFO,0);
		serviceip = serviceInfo.getString("serviceip", "http://42.120.40.204:84");

		if(activityName.equals("com.wfp.activity.ChooseMarketActivity")) {
			marketListView = (ListView) activity.findViewById(R.id.marketListView);
		}
	}
	
	public HelpHandler2(AlertDialog alertDialog, Activity activity){
		Log.i("handlerpp22!!", "handler2");
		this.alertDialog = alertDialog;
		this.activity = activity;
		
		//页面初始化
		
	}
	
	public void handleMessage(Message msg) {
		
		Bundle bundle = msg.getData();
		int key = msg.arg1;
		Log.i("handler!!", String.valueOf(key));
		switch (key) {
			
			//获取指定半径内商超信息
			case 1:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					Log.i("withinaradiusmarkethandler", "withinaradiusmarkethandler");
					final String longitude = bundle.getString("longitude");
					final String latitude = bundle.getString("latitude");
					final String addFormUrl = bundle.getString("addFormUrl");
					ArrayList radiusMarketList = bundle.getStringArrayList("radiusMarketList");
					if(radiusMarketList.size() == 1){
						HashMap map = (HashMap) radiusMarketList.get(0);
						String marketName = (String) map.get("column5");
						String marketlsh = (String) map.get("lsh");
						StringBuffer uploadInfo = new StringBuffer();	//新增表单数据
						Log.i("addSubFormUrl", addFormUrl);
						uploadInfo.append(addFormUrl);
						uploadInfo.append("&");
						uploadInfo.append(longitude);
						uploadInfo.append("&");
						uploadInfo.append(latitude);
						uploadInfo.append("&");
						uploadInfo.append(" ");
						uploadInfo.append("&");
						uploadInfo.append("form");
						uploadInfo.append("&");
						uploadInfo.append("add");
						uploadInfo.append("&");
						uploadInfo.append(marketName+ ";" + marketlsh);
						//显示等待dialog
				        ProgressDialog pDialog = new ProgressDialog(activity);
				        pDialog.setMessage(activity.getResources().getString(R.string.searchRangeMarket));
				        pDialog.setOnCancelListener(new OnCancelListener() {
							
							@Override
							public void onCancel(DialogInterface dialog) {
								// TODO Auto-generated method stub
								activity.finish();
							}
						});
				        pDialog.show();
						//获取到服务端流程名称数据，加载下拉列表
						HelpHandler helpHandler = new HelpHandler(null,
								activity);
						//获取流程名称，服务端线程连接
						ConnectionServiceThread connServiceThread = new ConnectionServiceThread(activity, 13, helpHandler, uploadInfo.toString());
						connServiceThread.start();
					}else if(radiusMarketList.size() == 0){
						Toast.makeText(activity, R.string.rangehasnosupermarket, Toast.LENGTH_SHORT).show();
						activity.finish();
					}else {
						//初始化范围超市对话框列表
						LayoutInflater inflater = activity.getLayoutInflater();
				        View marketListLayout = inflater.inflate(R.layout.marketlist, null);
				        AlertDialog.Builder marketDialogBuilder = new AlertDialog.Builder(activity);
				        marketDialogBuilder.setTitle(R.string.searchedMarket);
				        marketDialogBuilder.setView(marketListLayout);
				        final AlertDialog marketDialog = marketDialogBuilder.create();
				        marketDialog.show();
				        marketDialog.setOnCancelListener(new OnCancelListener() {
							
							@Override
							public void onCancel(DialogInterface dialog) {
								// TODO Auto-generated method stub
								activity.finish();
							}
						});
				        
				        
				        ListView marketListView = (ListView) marketListLayout.findViewById(R.id.marketListView);
						
						//实例化一个适配器
						SimpleAdapter listAdapter = new SimpleAdapter(activity,
								radiusMarketList, android.R.layout.simple_list_item_1,
								new String[] { "column5" },
								new int[] { android.R.id.text1});
				        //将Spinner和数据适配器关联
						marketListView.setAdapter(listAdapter);
						marketListView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> adapterView,
									View arg1, int position, long arg3) {
								// TODO Auto-generated method stub
								marketDialog.dismiss();
								HashMap map = (HashMap) adapterView.getItemAtPosition(position);
								String marketName = (String) map.get("column5");
								String marketlsh = (String) map.get("lsh");
								
								StringBuffer uploadInfo = new StringBuffer();	//新增表单数据
								Log.i("addSubFormUrl", addFormUrl);
								uploadInfo.append(addFormUrl);
								uploadInfo.append("&");
								uploadInfo.append(longitude);
								uploadInfo.append("&");
								uploadInfo.append(latitude);
								uploadInfo.append("&");
								uploadInfo.append(" ");
								uploadInfo.append("&");
								uploadInfo.append("form");
								uploadInfo.append("&");
								uploadInfo.append("add");
								uploadInfo.append("&");
								uploadInfo.append(marketName+ ";" + marketlsh);
								//显示等待dialog
						        ProgressDialog pDialog = new ProgressDialog(activity);
						        pDialog.setMessage(activity.getResources().getString(R.string.openCamera));
						        pDialog.setOnCancelListener(new OnCancelListener() {
									
									@Override
									public void onCancel(DialogInterface dialog) {
										// TODO Auto-generated method stub
										activity.finish();
									}
								});
						        pDialog.show();
								//获取到服务端流程名称数据，加载下拉列表
								HelpHandler helpHandler = new HelpHandler(pDialog,
										activity);
								//获取流程名称，服务端线程连接
								ConnectionServiceThread connServiceThread = new ConnectionServiceThread(activity, 13, helpHandler, uploadInfo.toString());
								connServiceThread.start();
							}
						});
					}

				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
		}
		
		super.handleMessage(msg);
	}
	
	private void setFormData(ArrayList list){
		this.formDataAndMuskList = list;
	}
	
	public ArrayList getFormData(){
		return formDataAndMuskList;
	}

}
