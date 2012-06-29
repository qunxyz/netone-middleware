package com.wfp.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout.LayoutParams;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.wfp.activity.CameraActivity;
import com.wfp.activity.DynamicFormActivity;
import com.wfp.activity.MobileworkflowplatformActivity;
import com.wfp.activity.MyMapActivity;
import com.wfp.activity.NodeSelectionActivity;
import com.wfp.activity.OrdersUploadImgActivity;
import com.wfp.activity.R;
import com.wfp.activity.WebActivity;
import com.wfp.config.PathConfig;
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
	private ListView formsListView;
	private GridView formDirGridView;
	private AlertDialog alertDialog;
	private GridView unuploadGridView;
	private ListView uploadListView;
	private ListView commentListView;
	private LinearLayout dyform_layout;
	private Button createFormBt;
	private Button editFormBt;
	private Spinner nodeSpinner;
	private EditText suggestionText;
	private ListView commiterListView;
	private EditText commiterText;
	private ListView marketListView;
	
	private TextView nowByte;
	private ProgressBar upload_pb;
		
	//参数
	private static int LOAD_DONE = 10;
	private static String SHARED_USERINFO = "userinfo";		//账户信息key
    private static String SERVICE_INFO = "SERVICEINFO";		//服务端信息key
    private static String SYS_PARAMS = "SystemParams";		//系统设置key
	private String activityName;
	private String setDateTime;		//表单日期与时间控件结果值
	ArrayList formDataAndMuskList;			//表单数据 
	private static int PERSON_POINT = 1;	//地图人员指针标
	private static int MARKET_POINT = 2;	//地图商超指针标
	private static int YDFORM_TO_NODE = 11;	//动态表单界面与节点选择界面跳转标示符
	private static int CHOOSE_MARKET = 12;
	
	
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
		}else if(activityName.equals("com.wfp.activity.OrdersUploadImgActivity")) {
			unuploadGridView = (GridView) activity.findViewById(R.id.unuploadGridView);
			uploadListView = (ListView) activity.findViewById(R.id.uploadListView);
		}else if(activityName.equals("com.wfp.activity.FormListActivity")) {
			formsListView = (ListView) activity.findViewById(R.id.formsListView);
		}else if(activityName.equals("com.wfp.activity.DynamicFormActivity")) {
			dyform_layout = (LinearLayout) activity.findViewById(R.id.dyform_layout);
			createFormBt = (Button) activity.findViewById(R.id.createFormBt);
			editFormBt = (Button) activity.findViewById(R.id.editFormBt);
		}else if(activityName.equals("com.wfp.activity.NodeSelectionActivity")) {
			nodeSpinner = (Spinner) activity.findViewById(R.id.nodeSpinner);
			suggestionText = (EditText) activity.findViewById(R.id.suggestionText);
		}else if(activityName.equals("com.wfp.activity.ChoosePersonActivity")) {
			commiterListView = (ListView) activity.findViewById(R.id.commiterListView);
			commiterText = (EditText) activity.findViewById(R.id.commiterText);
		}else if(activityName.equals("com.wfp.activity.ChooseMarketActivity")) {
			marketListView = (ListView) activity.findViewById(R.id.marketListView);
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
			//读取表单目录数据
			case 16:
				// 关闭dialog
//				pDialog.dismiss();
//				if (msg.what == LOAD_DONE) {
//					
//					
//					Log.i("formDirListhandler", "formDirListhandler");
//					ArrayList formDirList = bundle.getStringArrayList("formDirList");
//					String dirKey = bundle.getString("key");
//					if(dirKey.equals("1")){
//						//实例化一个适配器
//						WebImageListAdapter formDirGridViewadapter = new WebImageListAdapter(activity, formDirList, R.layout.process_griditem, new String[]{"processImgUrl", "name"}, new int[]{R.id.processImg, R.id.processText});
//
//				        //将GridView和数据适配器关联
//						formDirGridView.setAdapter(formDirGridViewadapter);
//						//界面切换
//				        myagency_layout.setVisibility(View.GONE);
//				        annount_layout.setVisibility(View.GONE);
//				        channelGridView.setVisibility(View.GONE);
//				        channelTree_layout.setVisibility(View.GONE);
//				        processGridView.setVisibility(View.GONE);
//				        processDetail_layout.setVisibility(View.GONE);
//				        formDirGridView.setVisibility(View.VISIBLE);
//					}else{
//						//实例化一个适配器
//						SimpleAdapter formListViewadapter = new SimpleAdapter(activity, formDirList, R.layout.form_listitem, new String[]{ "name"}, new int[]{R.id.formName});
//
//				        //将ListView和数据适配器关联
//						formsListView.setAdapter(formListViewadapter);
//
//					}
//
//				}else{
//					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
//				}
				break;
			//图片上传
			case 17:
					Log.i("uploadimagehandler", "uploadimagehandler");
					
					//元素初始化
					nowByte = (TextView) alertDialog.findViewById(R.id.nowByte);
					upload_pb = (ProgressBar) alertDialog.findViewById(R.id.upload_pb);
					
					//线程数据获取
					Bundle bundle17 = msg.getData();

					if (!Thread.currentThread().isInterrupted()) {
						switch (msg.what) {
						//设置总进度
						case 0:
							
							long fileSize = bundle17.getLong("fileSize");
							Log.i("fileSize", String.valueOf(fileSize));
							upload_pb.setMax((int) fileSize);
						//设置当前进度
						case 1:
							
							long uploadFileSize =  bundle17.getLong("uploadFileSize");
							Log.i("uploadFileSize", String.valueOf(uploadFileSize));
							upload_pb.setProgress((int) uploadFileSize);
							nowByte.setText(String.valueOf(uploadFileSize));
							break;
						//上传完成
						case 2:
							// 关闭dialog
							alertDialog.dismiss();
							Toast.makeText(activity, R.string.uploadfilesuccess,
									1).show();
							OrdersUploadImgActivity ordersUploadImgActivity = (OrdersUploadImgActivity) activity;
							//获取到服务端流程名称数据，并装载数据
				    		HelpHandler helpHandler = new HelpHandler(null,
				    				activity);
				    		//启动请求服务端线程，封装数据给handler
				    		ConnectionServiceThread connServiceThread = new ConnectionServiceThread(activity, 18, helpHandler, ordersUploadImgActivity.imgRootPath);
				    		connServiceThread.start();
							break;
						//取消上传
						case 3:
							// 关闭dialog
							alertDialog.dismiss();
							Toast.makeText(activity, R.string.cancelUpload,
									1).show();
							break;
						//上传失败
						case -1:
							// 关闭dialog
							alertDialog.dismiss();
							String error = msg.getData().getString("error");
							Toast.makeText(activity, error, 1)
									.show();
							break;
						}
					}
				break;
			//工单照片读取
			case 18:
				Log.i("formDirListhandler", "formDirListhandler");
				
				//线程数据获取
				Bundle bundle18 = msg.getData();
				if (msg.what == LOAD_DONE) {
									
					ArrayList ordersImgList = bundle18.getStringArrayList("ordersImgList");
					WebImageListAdapter uploadImgAdapter = new WebImageListAdapter(activity, ordersImgList, R.layout.process_griditem, new String[] {"filepath","filename"}, new int[] {R.id.processImg, R.id.processText});
					unuploadGridView.setAdapter(uploadImgAdapter);
					// 关闭dialog
					if(pDialog != null)
						pDialog.dismiss();
					Log.i("pictureloadComplete", "pictureloadComplete");
				}else{
					String error = bundle18.getString("exception");
					Toast.makeText(activity, activity.getResources().getString(R.string.errorcode) + error, Toast.LENGTH_SHORT).show();
				}
				break;
			//服务端单点登录验证
			case 19:
				Log.i("loginhandler", "loginhandler");
				// 关闭dialog
				pDialog.dismiss();
				Bundle bundle19 = msg.getData();
				if (msg.what == LOAD_DONE) {
					String userId = bundle19.getString("userId");
					String userName = bundle19.getString("userName");
					String passWord = bundle19.getString("passWord");
					//将用户名密码存储在SharedPreferences中
					SharedPreferences userInfoShared = activity.getSharedPreferences(SHARED_USERINFO,0);
					Editor editor = userInfoShared.edit();
					editor.putString("userId", userId);
					editor.putString("userName", userName);
					editor.putString("passWord", passWord);
					editor.commit();
					SharedPreferences sysParamsShared = activity.getSharedPreferences(SYS_PARAMS,0);
					String radiusValue = sysParamsShared.getString("radiusValue", null);
					if(radiusValue == null || radiusValue.equals("")) {
						Editor editor2 = sysParamsShared.edit();
						editor2.putString("radiusValue", "200");
						editor2.commit();
					}
					//跳转到主界面
					Intent intent = new Intent(activity, MobileworkflowplatformActivity.class);
					activity.startActivity(intent);
					activity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
				}else{
					String loginFail = bundle.getString("loginfail");
					if(loginFail != null)
						Toast.makeText(activity, R.string.loginfail, Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//装载表单结构
			case 20:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					Log.i("formStructurehandler", "formStructurehandler");
					ArrayList formStructureList = bundle.getStringArrayList("formStructureList");
					ArrayList formDataList = bundle.getStringArrayList("formDataList");
					if(formDataList == null)
						System.out.println("formDataListnull");
					formDataAndMuskList = new ArrayList();
					String opFlag20 = bundle.getString("opFlag20");
					final HashMap formFieldMap = new HashMap();	//表单数据map
					final HashMap muskMap = new HashMap();		//必须填写的地段数据map
					
					//装载表单，将表单结构数据解析为界面视图
					for (int i = 0; i < formStructureList.size(); i++) {
						HashMap map = (HashMap) formStructureList.get(i);
						LinearLayout linearLayout = new LinearLayout(activity);
						LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
						layoutParams.setMargins(0,5,0,0);
						linearLayout.setLayoutParams(layoutParams);
						//添加字段名称textview
						TextView fieldName = new TextView(activity);
						fieldName.setLayoutParams(new LinearLayout.LayoutParams( 0, 50, 5.0f));
						String columnName = (String)map.get("columname");
						fieldName.setText(columnName);
						fieldName.setTextSize(14f);
						fieldName.setTextColor(R.drawable.darkgray);
						linearLayout.addView(fieldName);
						
						String musk = (String) map.get("musk");
						String opemode = (String) map.get("opemode");
						final String columnid = (String) map.get("columnid");
						String editViewType = (String) map.get("viewtype");
						String valuelist = (String) map.get("valuelist").toString();
						//查看与编辑表单时的表单数据
						HashMap map1 = null;
						if(formDataList.size() > 0)
							map1 = (HashMap) formDataList.get(0);
						if(opFlag20.equals("brow")){
							TextView fieldData = new TextView(activity);
							fieldData.setLayoutParams(new LinearLayout.LayoutParams( 0, 50, 10.0f));
							//获取表单字段相对应的值
							String fieldValue = "";
							
							if(editViewType.equals("24")){
								//从SharedPreferences中获取用户名
								SharedPreferences userInfo = activity.getSharedPreferences(SHARED_USERINFO,0);
								fieldValue = (String) map1.get("participant").toString();
								
							}else{
								try{
									fieldValue = (String) map1.get(columnid).toString();
								}catch (NullPointerException nex){
									fieldValue = "";
								}
							}
							fieldData.setText(fieldValue);
							fieldData.setTextColor(R.drawable.gray);
							fieldData.setGravity(Gravity.LEFT);
							linearLayout.addView(fieldData);
							
						}else{
							//普通文本框
							if(editViewType.equals("00")){
								EditText fieldEditView = new EditText(activity);
								fieldEditView.setLayoutParams(new LinearLayout.LayoutParams( 0, 50, 10.0f));
								fieldEditView.setTag(columnid);
								fieldEditView.setSingleLine(true);	//单行显示数据
								//焦点事件，获取当前视图数据
								fieldEditView.setOnFocusChangeListener(new OnFocusChangeListener() {
									
									@Override
									public void onFocusChange(View v, boolean hasFocus) {
										// TODO Auto-generated method stub
										EditText editText = (EditText) v;
										putFieldValue( editText, formFieldMap, muskMap, columnid);

									}
								});
								//是否只读
								if(opemode.equals("1")){
									fieldEditView.setEnabled(false);
								}
								//默认值
								if(valuelist != null && !valuelist.toString().equals("null")){
									fieldEditView.setText(valuelist);
								}
								String fieldValue = "";
								//编辑时的表单数据
								if(opFlag20.equals("edit")){
									
									try{
										fieldValue = (String) map1.get(columnid).toString();
									}catch (NullPointerException nex){
										fieldValue = "";
									}
									fieldEditView.setText(fieldValue);
								}
								linearLayout.addView(fieldEditView);
								//是否不必须
								if(musk.equals("1")){
									addRequiredImgView( linearLayout, muskMap, columnid, columnName, fieldValue);

								}
							//数字文本框
							}else if(editViewType.equals("01")){
								EditText fieldEditView = new EditText(activity);
								fieldEditView.setLayoutParams(new LinearLayout.LayoutParams( 0, 50, 10.0f));
								fieldEditView.setTag(columnid);
								fieldEditView.setSingleLine(true);	//单行显示数据
								//默认值
								if(valuelist != null && !valuelist.toString().equals("null")){
									fieldEditView.setText(valuelist);
								}
								//编辑时的表单数据
								String fieldValue = "";
								if(opFlag20.equals("edit")){
									
									try{
										fieldValue = (String) map1.get(columnid).toString();
									}catch (NullPointerException nex){
										fieldValue = "";
									}
									fieldEditView.setText(fieldValue);
								}
								//字符输入限制
								fieldEditView.setFilters(new InputFilter[] { new InputFilter() {
								    @Override
								    public CharSequence filter(CharSequence source, int start,
								           int end, Spanned dest, int dstart, int dend) {
								    	String limitStr = "1234567890.";	//允许输入的字符
								    	String subStr = String.valueOf(source);	//截取当前输入字符
								    	//判断输入字符是否为允许输入字符
								    	if(limitStr.indexOf(subStr) < 0){
								    		source = "";
								    	}
								        return source;
								    }
								}});
								//焦点事件，获取当前视图数据
								fieldEditView.setOnFocusChangeListener(new OnFocusChangeListener() {
									
									@Override
									public void onFocusChange(View v, boolean hasFocus) {
										// TODO Auto-generated method stub
										EditText editText = (EditText) v;
										putFieldValue( editText, formFieldMap, muskMap, columnid);
									}
								});
								//是否只读
								if(opemode.equals("1")){
									fieldEditView.setEnabled(false);
								}
								
								linearLayout.addView(fieldEditView);
								//是否不必须
								if(musk.equals("1")){
									addRequiredImgView( linearLayout, muskMap, columnid, columnName, fieldValue);
								}
							//时间文本框
							}else if(editViewType.equals("02")){
								final EditText fieldEditView = new EditText(activity);
								fieldEditView.setLayoutParams(new LinearLayout.LayoutParams( 0, 50, 10.0f));
								fieldEditView.setTag(columnid);
								fieldEditView.setSingleLine(true);	//单行显示数据
								fieldEditView.setInputType(InputType.TYPE_NULL);	//禁用软键盘
								//焦点事件，获取当前视图数据
								fieldEditView.setOnFocusChangeListener(new OnFocusChangeListener() {
									
									@Override
									public void onFocusChange(View v, boolean hasFocus) {
										// TODO Auto-generated method stub
										EditText editText = (EditText) v;
										putFieldValue( editText, formFieldMap, muskMap, columnid);
									}
								});
			
								final Calendar cal = Calendar.getInstance(); 
								final TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
	
									@Override
									public void onTimeSet(TimePicker view,
											int hourOfDay, int minute) {
										// TODO Auto-generated method stub
										cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
										cal.set(Calendar.MINUTE, minute);
										cal.set(Calendar.SECOND, 00);
										SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");         
										String setDate = simpleDateFormat.format(cal.getTime());
	//									Toast.makeText(activity, setDate, Toast.LENGTH_LONG).show();
										fieldEditView.setText(setDate);
									}
								}; 
								fieldEditView.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										new TimePickerDialog(activity, listener,
												cal.get(Calendar.HOUR_OF_DAY),
												cal.get(Calendar.MINUTE),
												true
												).show(); 
									}
								});
								//是否只读
								if(opemode.equals("1")){
									fieldEditView.setEnabled(false);
								}
								//默认值
								if(valuelist != null && !valuelist.toString().equals("null")){
									fieldEditView.setText(valuelist);
								}
								//编辑时的表单数据
								String fieldValue = "";
								if(opFlag20.equals("edit")){
									
									try{
										fieldValue = (String) map1.get(columnid).toString();
									}catch (NullPointerException nex){
										fieldValue = "";
									}
									fieldEditView.setText(fieldValue);
								}
								linearLayout.addView(fieldEditView);
								//是否不必须
								if(musk.equals("1")){
									addRequiredImgView( linearLayout, muskMap, columnid, columnName, fieldValue);
								}
	
							//日期文本框
							}else if(editViewType.equals("03")){
								final EditText fieldEditView = new EditText(activity);
								fieldEditView.setLayoutParams(new LinearLayout.LayoutParams( 0, 50, 10.0f));
								fieldEditView.setTag(columnid);
								fieldEditView.setSingleLine(true);	//单行显示数据
								fieldEditView.setInputType(InputType.TYPE_NULL);	//禁用软键盘
								//焦点事件，获取当前视图数据
								fieldEditView.setOnFocusChangeListener(new OnFocusChangeListener() {
									
									@Override
									public void onFocusChange(View v, boolean hasFocus) {
										// TODO Auto-generated method stub
										EditText editText = (EditText) v;
										putFieldValue( editText, formFieldMap, muskMap, columnid);
									}
								});
	
								final Calendar cal = Calendar.getInstance(); 
								final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
									@Override        
									public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
										cal.set(Calendar.YEAR, year);
										cal.set(Calendar.MONTH, monthOfYear);
										cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
										SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");         
										String setDate = simpleDateFormat.format(cal.getTime());
										fieldEditView.setText(setDate);
										}
								}; 
								fieldEditView.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										new DatePickerDialog(activity,listener,
												cal.get(Calendar.YEAR),
												cal.get(Calendar.MONTH),
												cal.get(Calendar.DAY_OF_MONTH)
												).show(); 
									}
								});
								//是否只读
								if(opemode.equals("1")){
									fieldEditView.setEnabled(false);
								}
								//默认值
								if(valuelist != null && !valuelist.toString().equals("null")){
									fieldEditView.setText(valuelist);
								}
								//编辑时的表单数据
								String fieldValue = "";
								if(opFlag20.equals("edit")){
									try{
										fieldValue = (String) map1.get(columnid).toString();
									}catch (NullPointerException nex){
										fieldValue = "";
									}
									fieldEditView.setText(fieldValue);
								}
								linearLayout.addView(fieldEditView);
								//是否不必须
								if(musk.equals("1")){
									addRequiredImgView( linearLayout, muskMap, columnid, columnName, fieldValue);
								}
							//日期和时间文本框
							}else if(editViewType.equals("04")){
								final EditText fieldEditView = new EditText(activity);
								fieldEditView.setLayoutParams(new LinearLayout.LayoutParams( 0, 50, 10.0f));
								fieldEditView.setTag(columnid);
								fieldEditView.setSingleLine(true);	//单行显示数据
								fieldEditView.setInputType(InputType.TYPE_NULL);	//禁用软键盘
								//焦点事件，获取当前视图数据
								fieldEditView.setOnFocusChangeListener(new OnFocusChangeListener() {
									
									@Override
									public void onFocusChange(View v, boolean hasFocus) {
										// TODO Auto-generated method stub
										EditText editText = (EditText) v;
										putFieldValue( editText, formFieldMap, muskMap, columnid);
									}
								});
	
								final Calendar cal = Calendar.getInstance(); 
								final TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
	
									@Override
									public void onTimeSet(TimePicker view,
											int hourOfDay, int minute) {
										// TODO Auto-generated method stub
										cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
										cal.set(Calendar.MINUTE, minute);
										cal.set(Calendar.SECOND, 00);
										SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
										setDateTime += " " + simpleDateFormat.format(cal.getTime());
										fieldEditView.setText(setDateTime);
									}
								}; 
								final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
									@Override        
									public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
										cal.set(Calendar.YEAR, year);
										cal.set(Calendar.MONTH, monthOfYear);
										cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
										SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");         
										setDateTime = simpleDateFormat.format(cal.getTime());
										new TimePickerDialog(activity, timeListener,
												cal.get(Calendar.HOUR_OF_DAY),
												cal.get(Calendar.MINUTE),
												true
												).show(); 
										}
								}; 
								fieldEditView.setOnClickListener(new OnClickListener() {
									
									@Override
									public void onClick(View v) {
										// TODO Auto-generated method stub
										new DatePickerDialog(activity,dateListener,
												cal.get(Calendar.YEAR),
												cal.get(Calendar.MONTH),
												cal.get(Calendar.DAY_OF_MONTH)
												).show(); 
									}
								});
								//是否只读
								if(opemode.equals("1")){
									fieldEditView.setEnabled(false);
								}
								//默认值
								if(valuelist != null && !valuelist.toString().equals("null")){
									fieldEditView.setText(valuelist);
								}
								//编辑时的表单数据
								String fieldValue = "";
								if(opFlag20.equals("edit")){
									try{
										fieldValue = (String) map1.get(columnid).toString();
									}catch (NullPointerException nex){
										fieldValue = "";
									}
									fieldEditView.setText(fieldValue);
								}
								linearLayout.addView(fieldEditView);
								//是否不必须
								if(musk.equals("1")){
									addRequiredImgView( linearLayout, muskMap, columnid, columnName, fieldValue);
								}
	
							//单选按钮
							}else if(editViewType.equals("05")){
								final CheckBox fieldEditView = new CheckBox(activity);
								fieldEditView.setLayoutParams(new LinearLayout.LayoutParams( 0, 50, 10f));
								//radio选择改变事件，获取当前视图数据
								fieldEditView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
									
									@Override
									public void onCheckedChanged(
											CompoundButton buttonView,
											boolean isChecked) {
										Log.i("CheckBox fieldEditView", "CheckBox "+isChecked);
										if (isChecked) {
											formFieldMap.put(columnid, "1");
										} else {
											formFieldMap.put(columnid, "0");
										}
								        
								    }

								});
								


								//是否只读
								if(opemode.equals("1")){
									fieldEditView.setEnabled(false);
								}
								linearLayout.setGravity(Gravity.LEFT);
								linearLayout.addView(fieldEditView);
							//邮件文本框
							}else if(editViewType.equals("06")){
								EditText fieldEditView = new EditText(activity);
								fieldEditView.setLayoutParams(new LinearLayout.LayoutParams( 0, 50, 10.0f));
								fieldEditView.setTag(columnid);
								fieldEditView.setSingleLine(true);	//单行显示数据
								fieldEditView.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS); 
								//焦点事件，获取当前视图数据
								fieldEditView.setOnFocusChangeListener(new OnFocusChangeListener() {
									
									@Override
									public void onFocusChange(View v, boolean hasFocus) {
										// TODO Auto-generated method stub
										EditText editText = (EditText) v;
										putFieldValue( editText, formFieldMap, muskMap, columnid);
									}
								});
								//是否只读
								if(opemode.equals("1")){
									fieldEditView.setEnabled(false);
								}
								//默认值
								if(valuelist != null && !valuelist.toString().equals("null")){
									fieldEditView.setText(valuelist);
								}
								//编辑时的表单数据
								String fieldValue = "";
								if(opFlag20.equals("edit")){
									
									try{
										fieldValue = (String) map1.get(columnid).toString();
									}catch (NullPointerException nex){
										fieldValue = "";
									}
									fieldEditView.setText(fieldValue);
								}
								linearLayout.addView(fieldEditView);
								//是否不必须
								if(musk.equals("1")){
									addRequiredImgView( linearLayout, muskMap, columnid, columnName, fieldValue);
								}
							//列表视图	
							}else if(editViewType.equals("10")){
								Spinner fieldEditView = new Spinner(activity);
								fieldEditView.setLayoutParams(new LinearLayout.LayoutParams( 0, LayoutParams.WRAP_CONTENT, 10.0f));
								fieldEditView.setTag(columnid);
								//加载默认数据
								String[] viewValueArray = valuelist.split(",");
								ArrayList defaultValList = new ArrayList();
								for (int j = 0; j < viewValueArray.length; j++) {
									String[] values =  viewValueArray[j].split("-");
									if(values.length > 1)
										defaultValList.add(values[1]);
									else
										defaultValList.add(activity.getResources().getString(R.string.notvalue));
								}
								ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, defaultValList); 
								spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
								fieldEditView.setAdapter(spinnerAdapter); 
								linearLayout.addView(fieldEditView);
								formFieldMap.put(columnid, fieldEditView.getSelectedItem().toString());
								//列表选中事件，获取选择数据
								fieldEditView.setOnItemClickListener(new OnItemClickListener() {
	
									@Override
									public void onItemClick(AdapterView<?> adapterView,
											View arg1, int arg2, long arg3) {
										// TODO Auto-generated method stub
										formFieldMap.put(columnid, adapterView.getSelectedItem().toString());
									}
								});
								//是否只读
								if(opemode.equals("1")){
									fieldEditView.setEnabled(false);
								}
	
							//K-V下拉列表（暂时同上）
							}else if(editViewType.equals("11")){
								Spinner fieldEditView = new Spinner(activity);
								fieldEditView.setLayoutParams(new LinearLayout.LayoutParams( 0, LayoutParams.WRAP_CONTENT, 10.0f));
								fieldEditView.setTag(columnid);
								//加载默认数据
								String[] viewValueArray = valuelist.split(",");
								ArrayList defaultValList = new ArrayList();
								for (int j = 0; j < viewValueArray.length; j++) {
									String[] values =  viewValueArray[j].split("-");
									if(values.length > 1)
										defaultValList.add(values[1]);
									else
										defaultValList.add("无");
								}
								ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, defaultValList); 							
								spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
								fieldEditView.setAdapter(spinnerAdapter); 
								linearLayout.addView(fieldEditView);
								formFieldMap.put(columnid, fieldEditView.getSelectedItem().toString());
								//列表选中事件，获取选择数据
								fieldEditView.setOnItemSelectedListener(new OnItemSelectedListener() {
	
									@Override
									public void onItemSelected(AdapterView<?> adapterView,
											View arg1, int position, long arg3) {
										// TODO Auto-generated method stub
										formFieldMap.put(columnid, adapterView.getItemAtPosition(position).toString());
	
									}
	
									@Override
									public void onNothingSelected(
											AdapterView<?> arg0) {
										// TODO Auto-generated method stub
										
									}
								});
								//是否只读
								if(opemode.equals("1")){
									fieldEditView.setEnabled(false);
								}
	
							//IP地址文本框
							}else if(editViewType.equals("12")){
								EditText fieldEditView = new EditText(activity);
								fieldEditView.setLayoutParams(new LinearLayout.LayoutParams( 0, 50, 10.0f));
								fieldEditView.setTag(columnid);
								fieldEditView.setSingleLine(true);	//单行显示数据
								//默认值
								if(valuelist != null && !valuelist.toString().equals("null")){
									fieldEditView.setText(valuelist);
								}
								//编辑时的表单数据
								String fieldValue = "";
								if(opFlag20.equals("edit")){
									
									try{
										fieldValue = (String) map1.get("formDataList").toString();
									}catch (NullPointerException nex){
										fieldValue = "";
									}
									fieldEditView.setText(fieldValue);
								}
								//字符输入限制
								fieldEditView.setFilters(new InputFilter[] { new InputFilter() {
								    @Override
								    public CharSequence filter(CharSequence source, int start,
								           int end, Spanned dest, int dstart, int dend) {
								    	String limitStr = "1234567890.";	//允许输入的字符
								    	String subStr = String.valueOf(source);	//截取当前输入字符
								    	//判断输入字符是否为允许输入字符
								    	if(limitStr.indexOf(subStr) < 0){
								    		source = "";
								    	}
								        return source;
								    }
								}});
								//焦点事件，获取当前视图数据
								fieldEditView.setOnFocusChangeListener(new OnFocusChangeListener() {
									
									@Override
									public void onFocusChange(View v, boolean hasFocus) {
										// TODO Auto-generated method stub
										EditText editText = (EditText) v;
										putFieldValue( editText, formFieldMap, muskMap, columnid);
									}
								});
								//是否只读
								if(opemode.equals("1")){
									fieldEditView.setEnabled(false);
								}
								linearLayout.addView(fieldEditView);
								//是否不必须
								if(musk.equals("1")){
									addRequiredImgView( linearLayout, muskMap, columnid, columnName, fieldValue);
								}
							//大文本框
							}else if(editViewType.equals("13")){
								EditText fieldEditView = new EditText(activity);
								fieldEditView.setLayoutParams(new LinearLayout.LayoutParams( 0, 150, 10.0f));
								fieldEditView.setTag(columnid);
								fieldEditView.setSingleLine(true);	//单行显示数据
								//焦点事件，获取当前视图数据
								fieldEditView.setOnFocusChangeListener(new OnFocusChangeListener() {
									
									@Override
									public void onFocusChange(View v, boolean hasFocus) {
										// TODO Auto-generated method stub
										EditText editText = (EditText) v;
										putFieldValue( editText, formFieldMap, muskMap, columnid);
									}
								});
								//是否只读
								if(opemode.equals("1")){
									fieldEditView.setEnabled(false);
								}
								//默认值
								if(valuelist != null && !valuelist.toString().equals("null")){
									fieldEditView.setText(valuelist);
								}
								//编辑时的表单数据
								String fieldValue = "";
								if(opFlag20.equals("edit")){
									
									try{
										fieldValue = (String) map1.get(columnid).toString();
									}catch (NullPointerException nex){
										fieldValue = "";
									}
									fieldEditView.setText(fieldValue);
								}
								linearLayout.addView(fieldEditView);
								//是否不必须
								if(musk.equals("1")){
									addRequiredImgView( linearLayout, muskMap, columnid, columnName, fieldValue);
								}
							//当前用户文本框
							}else if(editViewType.equals("24")){
								EditText fieldEditView = new EditText(activity);
								fieldEditView.setLayoutParams(new LinearLayout.LayoutParams( 0, 50, 10.0f));
								fieldEditView.setTag(columnid);
								fieldEditView.setSingleLine(true);	//单行显示数据
								//从SharedPreferences中获取用户名
								SharedPreferences userInfo = activity.getSharedPreferences(SHARED_USERINFO,0);
								String userid = userInfo.getString("userName", "");
								fieldEditView.setText(userid);
								fieldEditView.setEnabled(false);
								//焦点事件，获取当前视图数据
								fieldEditView.setOnFocusChangeListener(new OnFocusChangeListener() {
									
									@Override
									public void onFocusChange(View v, boolean hasFocus) {
										// TODO Auto-generated method stub
										EditText editText = (EditText) v;
										putFieldValue( editText, formFieldMap, muskMap, columnid);
									}
								});

								//编辑时的表单数据
								String fieldValue = "";
								if(opFlag20.equals("edit")){
									
									try{
										fieldValue = (String) map1.get(columnid).toString();
									}catch (NullPointerException nex){
										fieldValue = "";
									}
									fieldEditView.setText(fieldValue);
								}
								linearLayout.addView(fieldEditView);
								//是否不必须
								if(musk.equals("1")){
									addRequiredImgView( linearLayout, muskMap, columnid, columnName, fieldValue);
								}
							//所属部门
							}else if(editViewType.equals("25")){
								EditText fieldEditView = new EditText(activity);
								fieldEditView.setLayoutParams(new LinearLayout.LayoutParams( 0, 50, 10.0f));
								fieldEditView.setTag(columnid);
								fieldEditView.setSingleLine(true);	//单行显示数据
								//焦点事件，获取当前视图数据
								fieldEditView.setOnFocusChangeListener(new OnFocusChangeListener() {
									
									@Override
									public void onFocusChange(View v, boolean hasFocus) {
										// TODO Auto-generated method stub
										EditText editText = (EditText) v;
										putFieldValue( editText, formFieldMap, muskMap, columnid);
									}
								});
								//是否只读
								if(opemode.equals("1")){
									fieldEditView.setEnabled(false);
								}
								//默认值
								if(valuelist != null && !valuelist.toString().equals("null")){
									fieldEditView.setText(valuelist);
								}
								//编辑时的表单数据
								String fieldValue = "";
								if(opFlag20.equals("edit")){
									
									try{
										fieldValue = (String) map1.get(columnid).toString();
									}catch (NullPointerException nex){
										fieldValue = "";
									}
									fieldEditView.setText(fieldValue);
								}
								linearLayout.addView(fieldEditView);
								//是否不必须
								if(musk.equals("1")){
									addRequiredImgView( linearLayout, muskMap, columnid, columnName, fieldValue);
								}
							}else{
								EditText fieldEditView = new EditText(activity);
								fieldEditView.setLayoutParams(new LinearLayout.LayoutParams( 0, 50, 10.0f));
								fieldEditView.setTag(columnid);
								fieldEditView.setSingleLine(true);	//单行显示数据
								//焦点事件，获取当前视图数据
								fieldEditView.setOnFocusChangeListener(new OnFocusChangeListener() {
									
									@Override
									public void onFocusChange(View v, boolean hasFocus) {
										// TODO Auto-generated method stub
										EditText editText = (EditText) v;
										putFieldValue( editText, formFieldMap, muskMap, columnid);
									}
								});
								//是否只读
								if(opemode.equals("1")){
									fieldEditView.setEnabled(false);
								}
								//默认值
								if(valuelist != null && !valuelist.toString().equals("null")){
									fieldEditView.setText(valuelist);
								}
								//编辑时的表单数据
								String fieldValue = "";
								if(opFlag20.equals("edit")){
									
									try{
										fieldValue = (String) map1.get(columnid).toString();
									}catch (NullPointerException nex){
										fieldValue = "";
									}
									fieldEditView.setText(fieldValue);
								}
								linearLayout.addView(fieldEditView);
								//是否不必须
								if(musk.equals("1")){
									addRequiredImgView( linearLayout, muskMap, columnid, columnName, fieldValue);
								}
							}
						}
						dyform_layout.addView(linearLayout);

					}
					//存储表单数据和必须填写字段名称，便于界面调用此数据
					formDataAndMuskList.add(formFieldMap);
					formDataAndMuskList.add(muskMap);
					setFormData(formDataAndMuskList);
					
				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//删除表单数据
			case 21:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					
					Log.i("deleteFormchandler", "deleteFormchandler");
					Toast.makeText(activity, R.string.alreadyDeleteFormData, Toast.LENGTH_SHORT).show();

				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
				//Toast.makeText(context, R.string.createFormFail, Toast.LENGTH_SHORT).show();
			//新增表单
			case 22:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {

					Log.i("addformhandler", "addformhandler");
					String funcJsonStr = bundle.getString("funcJsonStr");
					String lsh = bundle.getString("lsh");
					String loadFormUrl = bundle.getString("loadFormUrl");
					Intent intent = new Intent(activity, DynamicFormActivity.class);
					intent.putExtra("formflag", "add");
					intent.putExtra("funcJsonStr", funcJsonStr);
					intent.putExtra("lsh", lsh);
					intent.putExtra("loadFormUrl", loadFormUrl);
					activity.startActivity(intent);
				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//修改表单
			case 23:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					String opFlag = bundle.getString("opFlag");
					String isapprove = bundle.getString("isapprove");
					String funcJsonStr = bundle.getString("funcJsonStr");
					
					Log.i("editformhandler", "editformhandler  "+opFlag);
					if(opFlag.equals("add")){
						Toast.makeText(activity, 
								R.string.createformsuccess, 
								Toast.LENGTH_SHORT).show();
						activity.finish();
					}else if(opFlag.equals("edit")){
						if(isapprove.equals("noexamine")){
							Toast.makeText(activity, 
									R.string.editformsuccess, 
									Toast.LENGTH_SHORT).show();
							activity.finish();
						}else{
							Intent intent = new Intent(activity, NodeSelectionActivity.class );
							intent.putExtra("isapprove", isapprove);
							intent.putExtra("funcJsonStr", funcJsonStr);
							activity.startActivityForResult(intent, YDFORM_TO_NODE);
						}
					}else{
						Toast.makeText(activity, 
								activity.getResources().getString(R.string.operatefail), 
								Toast.LENGTH_SHORT).show();
						activity.finish();
					}
				}else{
					Toast.makeText(activity, 
							activity.getResources().getString(R.string.webtimeoutorfail), 
							Toast.LENGTH_SHORT).show();
				}
				break;
			//已上传附件数据
			case 24:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					Log.i("uploadFileDatahandler", "uploadFileDatahandler");
					ArrayList uploadFileList = bundle.getStringArrayList("uploadFileList");
					//实例化一个适配器
					SimpleAdapter listAdapter = new SimpleAdapter(activity,
							uploadFileList, R.layout.upload_file_listitem,
							new String[] { "filename", "note", "updatetime" },
							new int[] { R.id.fileName, R.id.createPersion,
									R.id.uploadTime });

			        //将Spinner和数据适配器关联
					uploadListView.setAdapter(listAdapter);
					

				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//获取已上传附件地址
			case 25:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					Log.i("uploadFileAddresshandler", "uploadFileAddresshandler");
					String fileAddress = bundle.getString("fileAddress");
					Intent intent = new Intent(activity, WebActivity.class);
					intent.putExtra("webAddress", fileAddress);
					activity.startActivity(intent);
				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//删除已上传附件
			case 26:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					Log.i("deleteFilehandler", "deleteFilehandler");
					String fileAddress = bundle.getString("fileAddress");
					Toast.makeText(activity, R.string.deleteFilesuccess, Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//获取商超位置和上传过照片的人员信息
			case 27:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					Log.i("marketPointhandler", "marketPointhandler");
					ArrayList marketPointList = bundle.getStringArrayList("marketPointList");
					MyMapActivity myMapActivity = (MyMapActivity) activity;
					MapView mapView = (MapView) myMapActivity.findViewById(R.id.mapview);
					List<Overlay> overlays = mapView.getOverlays();
					for (int i = 0; i < marketPointList.size(); i++) {
						HashMap map = (HashMap) marketPointList.get(i);
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
							name = "无名商超";
						GeoPoint currentGeoPoint = new GeoPoint((int)(geoLat * 1E6), (int)(geoLong * 1E6));
						MyOverlay myOverlay = new MyOverlay(activity, currentGeoPoint, name+" ",
								Color.RED, myMapActivity.getResources(), MARKET_POINT);
						overlays.add(myOverlay);
						mapView.invalidate();
						}
					}
				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//加载配置图片
			case 28:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					Log.i("allServiceImghandler", "allServiceImghandler");
					
				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//流程节点获取
			case 29:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					Log.i("nodehandler", "nodehandler");
					ArrayList nodeList = bundle.getStringArrayList("nodeList");
					//实例化一个适配器
					SimpleAdapter listAdapter = new SimpleAdapter(activity,
							nodeList, android.R.layout.simple_spinner_item,
							new String[] { "name" },
							new int[] { android.R.id.text1});
					listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			        //将Spinner和数据适配器关联
					nodeSpinner.setAdapter(listAdapter);
					

				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//保存填写意见
			case 30:
				// 关闭dialog
				//pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					Log.i("suggestionhandler", "suggestionhandler");
					Toast.makeText(activity, R.string.saveSuccessSugg, Toast.LENGTH_SHORT).show();
					

				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//获取节点处理人员信息
			case 31:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					Log.i("nodehandler", "nodehandler");
					ArrayList commiterList = bundle.getStringArrayList("commiterList");
					//实例化一个适配器
					SimpleAdapter listAdapter = new SimpleAdapter(activity,
							commiterList, R.layout.commiterlist_item,
							new String[] { "participant" },
							new int[] { R.id.commiterName});
			        //将Spinner和数据适配器关联
					commiterListView.setAdapter(listAdapter);

				}else{
					Toast.makeText(activity, R.string.webtimeoutorfail, Toast.LENGTH_SHORT).show();
				}
				break;
			//提交当前节点审核
			case 32:
				// 关闭dialog
				pDialog.dismiss();
				if (msg.what == LOAD_DONE) {
					
					Log.i("submitprocesshandler", "submitprocesshandler");
					Toast.makeText(activity, R.string.submitAudit, Toast.LENGTH_SHORT).show();
					//退出审核界面
					activity.setResult(activity.RESULT_CANCELED);
					activity.finish();

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
	private void putFieldValue(EditText editText, HashMap formFieldMap, HashMap muskMap, String columnid){

		String fieldValue = editText.getText().toString();
		formFieldMap.put(columnid, fieldValue);
		if(fieldValue.equals(""))
			muskMap.put(columnid+"&exsitsValue", 0);
		else
			muskMap.put(columnid+"&exsitsValue", 1);
	}
}
