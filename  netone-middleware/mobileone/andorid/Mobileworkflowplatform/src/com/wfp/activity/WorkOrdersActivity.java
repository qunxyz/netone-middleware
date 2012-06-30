package com.wfp.activity;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.PixelFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.wfp.config.PathConfig;
import com.wfp.util.ConnectionServiceThread;
import com.wfp.util.FunctionUtil;
import com.wfp.util.HelpHandler;

public class WorkOrdersActivity extends Activity {
	
	//界面视图元素
	private ListView ordersListView;
	private RadioButton agencyOrdersBt;
	private RadioButton haveBeenOrdersBt;
	private RadioButton hasGoneThroughOrdersBt;
	private RadioButton allOrders;
	
	private ProgressDialog pDialog;
	
	//参数
	private ConnectionServiceThread connServiceThread;
	private File file;
    private static final String TAG="OrdersActivity";
    private String funcJsonStr;		//功能json字符串
    private double latitude=0.0;  	//经度
    private double longitude =0.0;  //维度
    private static String agencyFuncid = "18";	//待办列表数据功能编号
    private static String haveBeenFuncid = "19";	//已办列表数据功能编号
    private static String unfiledFuncid = "20";	//待办未归档数据功能编号
    private static String filedFuncid = "21";	//已办且归档功能编号
    private static String updateFormFuncid = "26";	//修改表单数据功能编号
    private static String queryFormFuncid = "27";	//查询表单数据功能编号
    private static String loadFormFuncid = "29";	//装载表单结构功能编号
    private String agencyUrl;		//待办服务url
    private String haveBeenUrl;		//已办服务url
    private String unfiledUrl;		//已办未归档服务url
    private String filedUrl;		//已办且归档服务url
    private static String SHARED_USERINFO = "userinfo";		//账户信息key
    private static String SERVICE_INFO = "SERVICEINFO";		//服务端信息key
    private static int ORDER_TYPE = 9;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.workorders);
        
        //初始化界面元素
        agencyOrdersBt = (RadioButton) findViewById(R.id.agencyOrdersBt);
        haveBeenOrdersBt = (RadioButton) findViewById(R.id.haveBeenOrdersBt);
        hasGoneThroughOrdersBt = (RadioButton) findViewById(R.id.hasGoneThroughOrdersBt);
        allOrders = (RadioButton) findViewById(R.id.allOrdersBt);
        ordersListView = (ListView) findViewById(R.id.ordersListView);
        
        //初始化数据
        Intent intent = getIntent();
        funcJsonStr = intent.getStringExtra("funcJsonStr");
        
        agencyUrl = "";		
        haveBeenUrl = "";		
        unfiledUrl = "";		
        filedUrl = "";
		try {
			final JSONObject jsonObj = new JSONObject(funcJsonStr);
			for (Iterator iterator = jsonObj.keys(); iterator.hasNext();) {
				final String fieldName = (String) iterator.next();
				Log.i("key", fieldName);
				if(fieldName.equals(agencyFuncid)){
					agencyUrl = FunctionUtil.getFuncUrl(jsonObj, agencyFuncid);
				}else if(fieldName.equals(haveBeenFuncid)){
					haveBeenUrl = FunctionUtil.getFuncUrl(jsonObj, haveBeenFuncid);
				}else if(fieldName.equals(unfiledFuncid)){
					unfiledUrl = FunctionUtil.getFuncUrl(jsonObj, unfiledFuncid);
				}else if(fieldName.equals(filedFuncid)){
					filedUrl = FunctionUtil.getFuncUrl(jsonObj, filedFuncid);
				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        //显示等待dialog
		pDialog = ProgressDialog.show(
				WorkOrdersActivity.this, 
				getResources().getString(R.string.nowloading), 
				getResources().getString(R.string.pleasewait), 
				true,true);		

		//获取到服务端流程名称数据，并装载数据
		HelpHandler helpHandler = new HelpHandler(pDialog,
				WorkOrdersActivity.this);
		//启动请求服务端线程，封装数据给handler
		connServiceThread = new ConnectionServiceThread(this, 9, helpHandler, agencyUrl);
		connServiceThread.start();
        
        
        //待办工单按钮点击事件
        agencyOrdersBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("agencyOrdersBt", "agencyOrdersBt");
				ORDER_TYPE = 9;
				//显示等待dialog
				pDialog = ProgressDialog.show(
						WorkOrdersActivity.this, 
						getResources().getString(R.string.nowloading), 
						getResources().getString(R.string.pleasewait), 
						true, true);		

				//获取到服务端流程名称数据，并装载数据
				HelpHandler helpHandler = new HelpHandler(pDialog,
						WorkOrdersActivity.this);
				//启动请求服务端线程，封装数据给handler
				connServiceThread = new ConnectionServiceThread(WorkOrdersActivity.this, 9, helpHandler, agencyUrl);
				connServiceThread.start();
			}
		});
        
        //已办工单按钮点击事件
        haveBeenOrdersBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("haveBeenOrdersBt", "haveBeenOrdersBt");
				ORDER_TYPE = 10;
				//显示等待dialog
				pDialog = ProgressDialog.show(
						WorkOrdersActivity.this, 
						getResources().getString(R.string.nowloading), 
						getResources().getString(R.string.pleasewait), 
						true, true);		

				//获取到服务端流程名称数据，并装载数据
				HelpHandler helpHandler = new HelpHandler(pDialog,
						WorkOrdersActivity.this);
				//启动请求服务端线程，封装数据给handler
				connServiceThread = new ConnectionServiceThread(WorkOrdersActivity.this, 10, helpHandler, haveBeenUrl);
				connServiceThread.start();
			}
		});
        
        //已办结工单按钮点击事件
        hasGoneThroughOrdersBt.setOnClickListener(new OnClickListener() {
        	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("hasGoneThroughOrdersBt", "hasGoneThroughOrdersBt");
				ORDER_TYPE = 11;
				//显示等待dialog
				pDialog = ProgressDialog.show(
						WorkOrdersActivity.this, 
						getResources().getString(R.string.nowloading), 
						getResources().getString(R.string.pleasewait), 
						true, true);

				//获取到服务端流程名称数据，并装载数据
				HelpHandler helpHandler = new HelpHandler(pDialog,
						WorkOrdersActivity.this);
				//启动请求服务端线程，封装数据给handler
				connServiceThread = new ConnectionServiceThread(WorkOrdersActivity.this, 11, helpHandler, unfiledUrl);
				connServiceThread.start();
			}
		});

        //所有工单按钮点击事件
        allOrders.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("allOrders", "allOrders");
				ORDER_TYPE = 12;
				//显示等待dialog
				pDialog = ProgressDialog.show(
						WorkOrdersActivity.this, 
						getResources().getString(R.string.nowloading), 
						getResources().getString(R.string.pleasewait), 
						true, true);

				//获取到服务端流程名称数据，并装载数据
				HelpHandler helpHandler = new HelpHandler(pDialog,
						WorkOrdersActivity.this);
				//启动请求服务端线程，封装数据给handler
				connServiceThread = new ConnectionServiceThread(WorkOrdersActivity.this, 12, helpHandler, filedUrl);
				connServiceThread.start();
			}
		});
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.orders_listitem, null);

		
        
        //工单列表项点击事件
        ordersListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Log.i("ordersListViewItemClick", "ordersListViewItemClick");
				final HashMap map = (HashMap) adapterView.getItemAtPosition(position);
				AlertDialog.Builder browDialogbuilder = new AlertDialog.Builder(WorkOrdersActivity.this);
				browDialogbuilder.setTitle(R.string.browoperate);
				browDialogbuilder.setItems(R.array.formbrowarray, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub
						if(which == 0){
							String serve = (String) map.get("serve");
							String funcJsonStr = serve.split("\\$")[1];
							Intent intent = new Intent(WorkOrdersActivity.this, DynamicFormActivity.class);
							String editFormUrl = null;
							String queryFormUrl = null;
							try {
								JSONObject jsonObj = new JSONObject(funcJsonStr);
								editFormUrl = FunctionUtil.getFuncUrl(jsonObj, updateFormFuncid);
								queryFormUrl = FunctionUtil.getFuncUrl(jsonObj, queryFormFuncid);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							String[] urlArray = queryFormUrl.split("&");
							String lsh = "";
							for (int i = 0; i < urlArray.length; i++) {
								if(urlArray[i].indexOf("lsh") >= 0){
									lsh = urlArray[i].split("=")[1];
									break;
								}
							}
							if(ORDER_TYPE == 9)
								intent.putExtra("isagency", true);
							else
								intent.putExtra("isagency", false);
							intent.putExtra("editFormUrl", editFormUrl);
							intent.putExtra("funcJsonStr", funcJsonStr);
							intent.putExtra("formflag", "edit");
							intent.putExtra("examineflag", true);
							intent.putExtra("lsh", lsh);
							startActivity(intent);
						}else if(which == 1){
							//得到当前用户名
							SharedPreferences userInfo = getSharedPreferences(SHARED_USERINFO,0);
							String userid = userInfo.getString("userId", "adminx");
							String passWord = userInfo.getString("passWord", "");
							String url = (String) map.get("url");
							url = PathConfig.freeLoginPrefix+userid+"&password="+passWord+"&gotourl="+URLEncoder.encode(url);
							Log.i("orderurl", url);
							Intent intent = new Intent( Intent.ACTION_VIEW );
							intent.setData( Uri.parse( url)); //这里面是需要调转的url
							intent = Intent.createChooser( intent, null);
						    startActivity( intent);
							
						}
					}


					
				});
				browDialogbuilder.create().show();
				


			}
		});
        
        ordersListView.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				Log.i("ordersListViewItemSelected", "ordersListViewItemSelected");
				Toast.makeText(WorkOrdersActivity.this, "ordersListViewItemSelected", Toast.LENGTH_LONG).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        
        //加载对话框取消事件
        pDialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				//connServiceThread.interrupt();
				//Toast.makeText(WorkOrdersActivity.this, R.string.cancelDataLoad, Toast.LENGTH_LONG).show();
			}
		});
    }
    
	@Override
	/**
	 * 创建菜单
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(getResources().getString(R.string.createorder));// 必须创建一项
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		String itemTitle = item.getTitle().toString();
		Toast.makeText(this, itemTitle, Toast.LENGTH_SHORT).show();
		return super.onMenuItemSelected(featureId, item);
	}
	
}
