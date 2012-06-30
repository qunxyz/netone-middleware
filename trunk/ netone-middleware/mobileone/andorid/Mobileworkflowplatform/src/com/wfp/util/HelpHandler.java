package com.wfp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.wfp.activity.CameraActivity;
import com.wfp.activity.MyMapActivity;
import com.wfp.activity.R;
import com.wfp.customcontrols.ExpandableAdapter;
import com.wfp.customcontrols.ResourceListAdapter;
import com.wfp.customcontrols.WebImageListAdapter;
import com.wfp.overlay.MyOverlay;


public class HelpHandler extends Handler {

	//传入的界面元素
	private ProgressDialog pDialog;
	private Activity activity;
	private GridView mainFuncGrid;
	private LinearLayout annount_layout;
	private LinearLayout channelTree_layout;
	private LinearLayout resource_layout;
	private LinearLayout myagency_layout;
	private ListView myAgencyListView;
	private GridView resourceGridView;
	private ListView resourceListView;
	private ListView annountcementListView;
	private GridView channelGridView;
	private ExpandableListView channelTree;
	private Spinner mainFuncSpinner;
	private TextView currentWorkOrder;
	private TextView haveBeenOrganizedWorkOrder;
	private ListView ordersListView;
	private ListView historyListView;
	private GridView formDirGridView;
	private AlertDialog alertDialog;
	private GridView unuploadGridView;
	private ListView commentListView;
	
	private TextView nowByte;
	private ProgressBar upload_pb;
		
	//参数
	private static int LOAD_DONE = 10;
	private static String SHARED_USERINFO = "userinfo";		//账户信息key
    private static String SERVICE_INFO = "SERVICEINFO";		//服务端信息key
    private static String SYS_PARAMS = "SystemParams";		//系统设置key
	private String activityName;
	
	private ArrayList formDataAndMuskList;			//表单数据 
	private static int CHOOSE_MARKET = 12;
	private static int PERSON_POINT = 1;	//地图人员指针标
	
	
	public HelpHandler(ProgressDialog pDialog, Activity activity){
		
		Log.i("handlerpp!!", "handler");
		this.pDialog = pDialog;
		this.activity = activity;
		activityName = activity.getClass().getName();
		//页面元素初始化
		if(activityName.equals("com.wfp.activity.MobileworkflowplatformActivity"))
			mainFuncGrid = (GridView)activity.findViewById(R.id.main_function_gridview);
		else if(activityName.equals("com.wfp.activity.FuncGroupActivity")) {
			mainFuncSpinner = (Spinner)activity.findViewById(R.id.mainfuncSpinner);
	        annountcementListView = (ListView)activity.findViewById(R.id.annountList);
	        channelGridView = (GridView)activity.findViewById(R.id.channelGridView);
	        channelTree = (ExpandableListView) activity.findViewById(R.id.channelTree);
	        resourceGridView = (GridView)activity.findViewById(R.id.resourceGridView);
	        resourceListView = (ListView)activity.findViewById(R.id.resourceListView);
	        myAgencyListView = (ListView)activity.findViewById(R.id.myAgencyListview);
	        currentWorkOrder = (TextView) activity.findViewById(R.id.currentWorkOrder);
	        haveBeenOrganizedWorkOrder = (TextView) activity.findViewById(R.id.haveBeenOrganizedWorkOrder);
	        annount_layout = (LinearLayout)activity.findViewById(R.id.annount_layout);
	        resource_layout = (LinearLayout)activity.findViewById(R.id.resource_layout);
	        channelTree_layout = (LinearLayout)activity.findViewById(R.id.channelTree_layout);
	        myagency_layout = (LinearLayout)activity.findViewById(R.id.myagency_layout);
	        formDirGridView = (GridView)activity.findViewById(R.id.formDirGridView);
	        commentListView = (ListView) activity.findViewById(R.id.commentListView);
		}else if(activityName.equals("com.wfp.activity.WorkOrdersActivity")) {
			ordersListView = (ListView)activity.findViewById(R.id.ordersListView);
		}else if(activityName.equals("com.wfp.activity.UploadHistoryActivity")) {
			historyListView = (ListView)activity.findViewById(R.id.historyListView);
		}
	}
	
	public HelpHandler(AlertDialog alertDialog, Activity activity){
		Log.i("handlerpp22!!", "handler2");
		this.alertDialog = alertDialog;
		this.activity = activity;
		
		
	}
	
	public void handleMessage(Message msg) {
		
		Bundle bundle = msg.getData();
		int key = msg.arg1;
		Log.i("handler!!", String.valueOf(key));
		switch (key) {
			//我的代办数据展示
			case 1:
				
			     // 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					Log.i("agencyhandler", "agencyhandler");
					ArrayList listItems = bundle.getStringArrayList("agencylist");
					String agencyCount = bundle.getString("agencyCount");
					String haveBeenOrganizedCount = bundle.getString("haveBeenOrganizedCount");
					//实例化一个适配器
			        SimpleAdapter listAdapter = new SimpleAdapter(activity, listItems, R.layout.myagency_listitem, new String[]{"actname", "commitername", "starttime"}, new int[]{R.id.agencyTitle, R.id.agencyPersonName, R.id.agencyDateTime});

//			        SimpleAdapter listAdapter = new SimpleAdapter(activity, listItems, R.layout.myagency_listitem, new String[]{"mtitle", "mpname", "mdatetime"}, new int[]{R.id.agencyTitle, R.id.agencyPersonName, R.id.agencyDateTime});

			        //将Spinner和数据适配器关联
			        myAgencyListView.setAdapter(listAdapter);
			        
			        currentWorkOrder.setText(activity.getResources().getString(R.string.currentWorkOrder)+agencyCount+activity.getResources().getString(R.string.article));
			        haveBeenOrganizedWorkOrder.setText(activity.getResources().getString(R.string.haveBeenOrganizedWorkOrder)+haveBeenOrganizedCount+activity.getResources().getString(R.string.article));
			        //界面切换
			        myagency_layout.setVisibility(View.VISIBLE);
			        annount_layout.setVisibility(View.GONE);
			        channelGridView.setVisibility(View.GONE);
			        channelTree_layout.setVisibility(View.GONE);
			        resourceGridView.setVisibility(View.GONE);
			        resourceListView.setVisibility(View.GONE);
			        resource_layout.setVisibility(View.GONE);
				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				
				break;
			//公告
			case 2:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					

					Log.i("annountcementhandler", "annountcementhandler");
					ArrayList annountcementLis = bundle.getStringArrayList("annountcementList");
			        //实例化一个适配器
			        SimpleAdapter annountcementListAdapter = new SimpleAdapter(activity, annountcementLis, R.layout.annount_listitem, new String[]{"atitle", "adatetime"}, new int[]{R.id.annountTitle, R.id.annountDatetime});

			        //将Spinner和数据适配器关联
			        annountcementListView.setAdapter(annountcementListAdapter);
			        //界面切换
			        myagency_layout.setVisibility(View.GONE);
			        annount_layout.setVisibility(View.VISIBLE);
			        channelGridView.setVisibility(View.GONE);
			        channelTree_layout.setVisibility(View.GONE);
			        resourceGridView.setVisibility(View.GONE);
			        resourceListView.setVisibility(View.GONE);
			        resource_layout.setVisibility(View.GONE);
			        formDirGridView.setVisibility(View.GONE);
				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//频道主页
			case 3:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					
					Log.i("channelmainhandler", "channelmainhandler");
			        
					ArrayList channelMainList = bundle.getStringArrayList("channelMainList");
//					//实例化一个适配器
					WebImageListAdapter adapter = new WebImageListAdapter(activity, channelMainList, R.layout.channel_griditem, new String[]{"channelImgUrl", "catname"}, new int[]{R.id.channelImg, R.id.channelText});

			        //将GridView和数据适配器关联
			        channelGridView.setAdapter(adapter);
			        //界面切换
			        myagency_layout.setVisibility(View.GONE);
			        annount_layout.setVisibility(View.GONE);
			        channelGridView.setVisibility(View.VISIBLE);
			        channelTree_layout.setVisibility(View.GONE);
			        resourceGridView.setVisibility(View.GONE);
			        resourceListView.setVisibility(View.GONE);
			        resource_layout.setVisibility(View.GONE);
			        formDirGridView.setVisibility(View.GONE);
			        
				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//频道栏目树
			case 4:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					
					Log.i("channelTreehandler", "channelTreehandler");

					ArrayList groupArray = bundle.getStringArrayList("treeGroupArray");
					ArrayList childArray = bundle.getStringArrayList("treeChildArray");
//					//适配器数据加载
					ExpandableAdapter channelTreeAdapter = new ExpandableAdapter(activity, groupArray, childArray); 
					//tree适配器配置
					channelTree.setAdapter(channelTreeAdapter);
					channelTree.setChildDivider(activity.getResources().getDrawable(R.drawable.channel_tree_line));        
					channelTree.setDivider(activity.getResources().getDrawable(R.drawable.white));
					channelTree.setDividerHeight(1);

				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//业务流程
			case 5:
				// 关闭dialog
//				pDialog.dismiss();
//				if (msg.what == LOAD_DONE) {
//					
//					
//					Log.i("processMainhandler", "processMainhandler");
//			        
//					ArrayList processMainList = bundle.getStringArrayList("processMainList");
//					//实例化一个适配器
//			        WebImageListAdapter pGridViewadapter = new WebImageListAdapter(activity, processMainList, R.layout.process_griditem, new String[]{"processImgUrl", "name"}, new int[]{R.id.processImg, R.id.processText});
//
//			        //将GridView和数据适配器关联
//			        resourceGridView.setAdapter(pGridViewadapter);
//			        //界面切换
//			        myagency_layout.setVisibility(View.GONE);
//			        annount_layout.setVisibility(View.GONE);
//			        channelGridView.setVisibility(View.GONE);
//			        channelTree_layout.setVisibility(View.GONE);
//			        processGridView.setVisibility(View.VISIBLE);
//			        processDetail_layout.setVisibility(View.GONE);
//			        formDirGridView.setVisibility(View.GONE);
//				}else{
//					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
//				}
				break;
			//详细业务流程
			case 6:
				// 关闭dialog
//				pDialog.dismiss();
//				if (msg.what == LOAD_DONE) {
//					
//					
//					Log.i("processDetailhandler", "processDetailhandler");
//					ArrayList processDetailList = bundle.getStringArrayList("processDetailList");
//			        //实例化一个适配器
//			        WebImageListAdapter processDetailListAdapter = new WebImageListAdapter(activity, processDetailList, R.layout.process_listitem, new String[]{"processDetailImgUrl", "name"}, new int[]{R.id.pDetailImg, R.id.pDetailText});
//			        
//			        //将Spinner和数据适配器关联
//			        processDetailListView.setAdapter(processDetailListAdapter);
//					
//					processGridView.setVisibility(View.GONE);
//					processGridView.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.push_left_out));
//					channelTree_layout.setAnimation(AnimationUtils.loadAnimation(activity, R.anim.push_right_in));
//					channelTree_layout.setVisibility(View.VISIBLE);
//
//				}else{
//					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
//				}

				break;
			//主页功能
			case 7:

				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					Log.i("mainFunchandler", "mainFunchandler");
					ArrayList mainFuncList = bundle.getStringArrayList("mainFuncList");
					if(activityName.equals("com.wfp.activity.MobileworkflowplatformActivity")){
				        //实例化一个适配器
						WebImageListAdapter adapter = new WebImageListAdapter(activity, mainFuncList, R.layout.main_func_griditem, new String[]{"imagename", "resourcename"}, new int[]{R.id.mainfuncImg, R.id.mainfuncText});
				        //ResourceListAdapter adapter = new ResourceListAdapter(activity, mainFuncList, R.layout.process_griditem);
				        //将GridView和数据适配器关联
				        mainFuncGrid.setAdapter(adapter);
					}else if(activityName.equals("com.wfp.activity.FuncGroupActivity")){
						//展示类型
						String types = bundle.getString("types");
						
						if(types.equals("1")){		//网格展示
							//实例化一个适配器
							WebImageListAdapter adapter = new WebImageListAdapter(activity, mainFuncList, R.layout.process_griditem, new String[]{"imagename", "resourcename"}, new int[]{R.id.processImg, R.id.processText});
							//ResourceListAdapter adapter = new ResourceListAdapter(activity, mainFuncList, R.layout.process_griditem);
					        //将GridView和数据适配器关联
					        resourceGridView.setAdapter(adapter);
					        //界面切换
					        resourceGridView.setVisibility(View.VISIBLE);
					        //resourceListView.setVisibility(View.GONE);

						}else if(types.equals("0")){	//列表展示
							//实例化一个适配器
							ResourceListAdapter adapter = new ResourceListAdapter(activity, mainFuncList, R.layout.process_listitem);
							//SimpleAdapter adapter = new SimpleAdapter(activity, mainFuncList, R.layout.process_listitem, new String[]{"resourceImgUrl", "resourcename"}, new int[]{R.id.pDetailImg, R.id.pDetailText});
					        //将ListView和数据适配器关联
					        resourceListView.setAdapter(adapter);
					        //界面切换
					        //resourceGridView.setVisibility(View.GONE);
					        resourceListView.setVisibility(View.VISIBLE);
						}
						//隐藏其他应用布局
				        myagency_layout.setVisibility(View.GONE);
				        annount_layout.setVisibility(View.GONE);
				        channelGridView.setVisibility(View.GONE);
				        channelTree_layout.setVisibility(View.GONE);
				        resource_layout.setVisibility(View.VISIBLE);
				        formDirGridView.setVisibility(View.GONE);
						
					}

				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}

				break;
			//查询
			case 8:
				
				break;
			//代办工单
			case 9:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					
					Log.i("agencyOrdershandler", "agencyOrdershandler");
					ArrayList agencyOrdersList = bundle.getStringArrayList("agencyOrdersList");
					//实例化一个适配器
			        SimpleAdapter listAdapter = new SimpleAdapter(activity, agencyOrdersList, R.layout.orders_listitem, new String[]{"actname", "commitername", "starttime"}, new int[]{R.id.orderName, R.id.submittedBy, R.id.submitted});

			        //将Spinner和数据适配器关联
			        ordersListView.setAdapter(listAdapter);
					

				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//已办工单
			case 10:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					
					Log.i("haveBeenOrderchandler", "haveBeenOrderchandler");
					ArrayList haveBeenOrderList = bundle.getStringArrayList("haveBeenOrderList");
					//实例化一个适配器
			        SimpleAdapter listAdapter = new SimpleAdapter(activity, haveBeenOrderList, R.layout.orders_listitem, new String[]{"actname", "commitername", "starttime"}, new int[]{R.id.orderName, R.id.submittedBy, R.id.submitted});

			        //将Spinner和数据适配器关联
			        ordersListView.setAdapter(listAdapter);
					

				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//已办结工单
			case 11:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					
					Log.i("hasGoneThroughOrdersListhandler", "hasGoneThroughOrdersListhandler");
					ArrayList hasGoneThroughOrdersList = bundle.getStringArrayList("hasGoneThroughOrdersList");
					//实例化一个适配器
			        SimpleAdapter listAdapter = new SimpleAdapter(activity, hasGoneThroughOrdersList, R.layout.orders_listitem, new String[]{"actname", "commitername", "starttime"}, new int[]{R.id.orderName, R.id.submittedBy, R.id.submitted});

			        //将Spinner和数据适配器关联
			        ordersListView.setAdapter(listAdapter);
					

				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//所有工单
			case 12:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					
					Log.i("allOrdersListhandler", "allOrdersListhandler");
					ArrayList allOrdersList = bundle.getStringArrayList("allOrdersList");
					//实例化一个适配器
			        SimpleAdapter listAdapter = new SimpleAdapter(activity, allOrdersList, R.layout.orders_listitem, new String[]{"actname", "commitername", "starttime"}, new int[]{R.id.orderName, R.id.submittedBy, R.id.submitted});

			        //将ListView和数据适配器关联
			        ordersListView.setAdapter(listAdapter);
					

				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//上传经纬度值
			case 13:
				if (msg.what == LOAD_DONE) {
					
					
					Log.i("longitude", "longitude");
					String rsFlag = bundle.getString("rsFlag");
					String flag = bundle.getString("flag");
					String appName = bundle.getString("appName");
					Log.i("rsFlag", rsFlag);
					Intent intent = new Intent(activity, CameraActivity.class);
					if(flag.equals("order"))
						intent.putExtra("lsh", appName);
					else if(flag.equals("form")){
						String marketInfo = bundle.getString("marketInfo");
						if(marketInfo != null && pDialog != null)	//关闭dialog （商超范围选择后）
							pDialog.dismiss();
						intent.putExtra("id", appName);
						intent.putExtra("marketInfo", marketInfo);
					}
					intent.putExtra("serialNO", rsFlag);
					activity.startActivityForResult(intent, CHOOSE_MARKET);
				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//读取指定用户图片上传位置经纬度值
			case 14:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					Log.i("latlonginfoList", "latlonginfoList");
					ArrayList latlonginfoList = bundle.getStringArrayList("latlonginfoList");
					MyMapActivity myMapActivity = (MyMapActivity) activity;
					MapView mapView = (MapView) myMapActivity.findViewById(R.id.mapview);
					List<Overlay> overlays = mapView.getOverlays();
					for (int i = 0; i < latlonginfoList.size(); i++) {
						HashMap map = (HashMap) latlonginfoList.get(i);
						String geoLatStr = (String) map.get("latitude");
						String geoLongStr = (String) map.get("longitude");
						Double geoLat = 0.0;
						Double geoLong = 0.0;
						//过滤为空的经纬度信息
						if(!geoLatStr.equals("") && !geoLongStr.equals("")){
							geoLat = Double.valueOf(geoLatStr);
							geoLong = Double.valueOf(geoLongStr);
						
						String name = (String) map.get("name");
						Log.i("commitername", name+"  ");
						if(name == null)
							name = "无名";
						GeoPoint currentGeoPoint = new GeoPoint((int)(geoLat * 1E6), (int)(geoLong * 1E6));
						MyOverlay myOverlay = new MyOverlay(activity, currentGeoPoint, name+" ",
								Color.BLUE, myMapActivity.getResources(), PERSON_POINT);
						overlays.add(myOverlay);
						mapView.invalidate();
						}
					}
					
				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//读取历史上传记录
			case 15:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					
					Log.i("historyListhandler", "historyListhandler");
					ArrayList allOrdersList = bundle.getStringArrayList("historyList");
					String formType = bundle.getString("formType");
					//实例化一个适配器
					SimpleAdapter listAdapter = null;
					if(formType.equals("Collection"))
						listAdapter = new SimpleAdapter(activity, allOrdersList, R.layout.history_listitem, new String[]{ "time", "userid"}, new int[]{R.id.historyDate, R.id.opPerson});
					else if(formType.equals("market"))
						listAdapter = new SimpleAdapter(activity, allOrdersList, R.layout.market_listitem, new String[]{ "name", "time"}, new int[]{R.id.marketName, R.id.createTime});
			        //将ListView和数据适配器关联
			        historyListView.setAdapter(listAdapter);
					

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
	
	/**
	 * 添加“必须填写”提示图片控件
	 * @param linearLayout 	父布局
	 * @param muskMap		必填字段标识集
	 * @param columnid		字段id
	 * @param columnName	字段名称
	 * @param fieldValue	字段值
	 */
	private void addRequiredImgView(LinearLayout linearLayout, HashMap muskMap, String columnid, String columnName, String fieldValue){
		ImageView muskImg = new ImageView(activity);
		muskImg.setLayoutParams(new LinearLayout.LayoutParams( 0, 50, 1.5f));
		muskImg.setImageResource(R.drawable.warning);
		linearLayout.addView(muskImg);
		muskMap.put(columnid, columnName);
		if(fieldValue.equals(""))
			muskMap.put(columnid+"&exsitsValue", 0);
		else
			muskMap.put(columnid+"&exsitsValue", 1);

	}
	
	/**
	 * 获取填写表单字段数据并判断是否必填
	 * @param editText
	 * @param formFieldMap
	 * @param muskMap
	 * @param columnid
	 */
	private void putFieldValue(HashMap formFieldMap, HashMap muskMap, String columnid ,String columnName, String musk, String fieldValue){
		Log.i("musk", musk);
		formFieldMap.put(columnid, fieldValue);
		if(musk.equals("1")){
			muskMap.put(columnid, columnName);
			if(fieldValue.equals(""))
				muskMap.put(columnid+"&exsitsValue", 0);
			else
				muskMap.put(columnid+"&exsitsValue", 1);
			Log.i("formDataAndMuskListsize", String.valueOf(formDataAndMuskList.size()));
			if(formDataAndMuskList.size() > 0) {
				formDataAndMuskList.add( 1, muskMap);
				setFormData(formDataAndMuskList);
				Log.i("muskMapchange", "muskMapchange");
			}
		}
	}
	
}


