package com.wfp.activity;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ExpandableListView.OnChildClickListener;

import com.wfp.config.ModulesConfig;
import com.wfp.config.PathConfig;
import com.wfp.customcontrols.WebImageListAdapter;
import com.wfp.util.ConnectionServiceThread;
import com.wfp.util.FunctionUtil;
import com.wfp.util.HelpHandler;

public class FuncGroupActivity extends Activity {
	
	//界面视图元素
	private Spinner mainFuncSpinner;
	private RadioButton fmyagencybt;
	private RadioButton fquerybt;
	private RadioButton fmainHomebt;
	private ListView annountcementListView;
	private GridView channelGridView;
	private ExpandableListView channelTree;
	private ImageButton dBackdepart;
	private GridView resourceGridView;
	private ListView resourceListView;
	private ImageButton pBackProcess;
	private LinearLayout annount_layout;
	private LinearLayout channelTree_layout;
	private TextView departmentName;
	private LinearLayout resource_layout;
	private TextView processName;
	private ListView myAgencyListView;
	private TextView currentWorkOrder;
	private TextView haveBeenOrganizedWorkOrder;
	private AlertDialog menuDialog; // menu菜单Dialog
	private View menuView;
	private GridView menuGrid;
	private ImageView departmentImg;
	private ImageView processImg;
	private ProgressDialog pDialog;
	private GridView formDirGridView;
	private ListView commentListView;
	private LinearLayout fileFuncitonBt_layout;

	//界面参数
	private boolean isInit = true;
	private ArrayList funcList;
	private int activityKey;
	private ConnectionServiceThread connServiceThread;
	private ArrayList treeStackList;	//栏目树堆栈
	//private String naturalname;		//资源naturalname
	private ArrayList resourceStackList;	//资源目录堆栈
    private static String SHARED_USERINFO = "userinfo";		//账户信息key
    private static String SERVICE_INFO = "SERVICEINFO";		//服务端信息key
    private static String openFuncid = "00";		//打开功能编号
    private static String photographFuncid = "01";	//拍照功能编号
    private static String uploadFileFuncid = "02";	//附件管理功能编号
    private static String addFormFuncid = "25";		//新建表单数据功能编号
    private static String updateFormFuncid = "26";	//修改表单数据功能编号
    private static String queryFormFuncid = "27";	//查询表单数据功能编号
    private static String loadFormFuncid = "29";	//装载表单结构功能编号
    
    private static String ordersAppid = "5";		//工单管理应用编号
    private static String formAppcid = "16";		//表单应用编号
    private double latitude = 0.0;  	//经度
    private double longitude = 0.0;  //维度
    private String funcJsonStr;		//功能json字符串
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.func_group);
        Log.i("funcgroupactivity", "funcgroupactivity");
        
        //界面元素初始化
        mainFuncSpinner = (Spinner)findViewById(R.id.mainfuncSpinner);
        fmyagencybt = (RadioButton) findViewById(R.id.fmyagencybt);
        fquerybt = (RadioButton) findViewById(R.id.fquerybt);
        fmainHomebt = (RadioButton) findViewById(R.id.fmainHomebt);
        annountcementListView = (ListView)findViewById(R.id.annountList);
        channelGridView = (GridView)findViewById(R.id.channelGridView);
        channelTree = (ExpandableListView) findViewById(R.id.channelTree);
        departmentName = (TextView) findViewById(R.id.departmentName);
        dBackdepart = (ImageButton) findViewById(R.id.dBackdepart);
        resourceGridView = (GridView)findViewById(R.id.resourceGridView);
        resourceListView = (ListView)findViewById(R.id.resourceListView);
        processName = (TextView) findViewById(R.id.processName);
        pBackProcess = (ImageButton) findViewById(R.id.pBackProcess);
        currentWorkOrder = (TextView) findViewById(R.id.currentWorkOrder);
        haveBeenOrganizedWorkOrder = (TextView) findViewById(R.id.haveBeenOrganizedWorkOrder);
        myAgencyListView = (ListView)findViewById(R.id.myAgencyListview);
        annount_layout = (LinearLayout)findViewById(R.id.annount_layout);
        channelTree_layout = (LinearLayout)findViewById(R.id.channelTree_layout);
        resource_layout = (LinearLayout)findViewById(R.id.resource_layout);
        //processImg = (ImageView) findViewById(R.id.processImg);
        departmentImg = (ImageView) findViewById(R.id.departmentImg);
        formDirGridView = (GridView)findViewById(R.id.formDirGridView);
        commentListView = (ListView) findViewById(R.id.commentListView);
        fileFuncitonBt_layout = (LinearLayout)findViewById(R.id.fileFuncitonBt_layout);
        
        // 设置自定义menu菜单布局
		menuView = View.inflate(this, R.layout.gridview_menu, null);
		menuGrid = (GridView) menuView.findViewById(R.id.menu_gridview);
        //菜单图片 
    	int[] menu_image_array = { R.drawable.menu_refresh,
    			R.drawable.menu_syssettings, R.drawable.menu_return };
    	//菜单文字
    	String[] menu_name_array = { getResources().getString(R.string.reflush), 
    			getResources().getString(R.string.systemsetting), 
    			getResources().getString(R.string.exit) };
        //构建菜单
		menuDialog = FunctionUtil.initMenu(this, menuView, menuGrid, menu_name_array, menu_image_array);
		
		/** 监听menu选项 **/
		menuGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				HashMap map = (HashMap) adapterView.getItemAtPosition(position);
				String menuItemName =  map.get("menuItemText").toString();
				if(menuItemName.equals(getResources().getString(R.string.reflush))){
					Log.i("menu_funcreflush", "menu_funcreflush");
					menuDialog.dismiss();
					String flag = "";
					if(activityKey == 16)	//表单
						flag = "APPFRAME.APPFRAME.HGMY.MBJC&1";
					else if(activityKey == 7){	//资源列表
						HashMap map1 = (HashMap) resourceStackList.get(resourceStackList.size()-1);
						String naturalname = (String) map1.get("naturalname");
						String types = (String) map1.get("types");
						flag = naturalname + "&" + types;
					}else if(activityKey == 1)	//待办
						flag = funcJsonStr;
					else
						flag = "root";
					//显示等待dialog
					pDialog = ProgressDialog.show(
							FuncGroupActivity.this, 
							getResources().getString(R.string.nowloading), 
							getResources().getString(R.string.pleasewait), 
							true, true);	

					//获取到服务端流程名称数据，并装载数据
					HelpHandler helpHandler = new HelpHandler(pDialog,
							FuncGroupActivity.this);
					//启动请求服务端线程，封装数据给handler
					connServiceThread = new ConnectionServiceThread(FuncGroupActivity.this, activityKey, helpHandler, flag);
					connServiceThread.start();
				}else if(menuItemName.equals(getResources().getString(R.string.systemsetting))){
					Log.i("menu_syssetting", "menu_syssetting");
					menuDialog.dismiss();
					Intent intent = new Intent(FuncGroupActivity.this, SystemSettingActivity.class);
			    	startActivity(intent);
				}else if(menuItemName.equals(getResources().getString(R.string.exit))){
					Log.i("menu_exit", "menu_exit");
					finish();
				}
			}
		});
        
        //界面数据初始化
        Intent intent = getIntent();
        activityKey = intent.getIntExtra("key", 1);
        ArrayList funcMainList = intent.getStringArrayListExtra("funcMainList");
        funcJsonStr = intent.getStringExtra("funcJsonStr");
        //spinner默认选中项
        int spinnerPosition = intent.getIntExtra("position", 0);
		try {
			Log.i("activitykey", String.valueOf(activityKey));

			String flag = "";
			if(activityKey == 16)	//表单
				flag = "APPFRAME.APPFRAME.HGMY.MBJC&1";
			else if(activityKey == 7){	//资源列表
				String naturalname = intent.getStringExtra("naturalname");
				String resourcename = intent.getStringExtra("resourcename");
				String types = intent.getStringExtra("types");
				processName.setText(resourcename);
				flag = naturalname + "&" + types;

				resource_layout.setVisibility(View.VISIBLE);
				resourceStackList = new ArrayList();
				HashMap map = new HashMap();
				map.put("naturalname", naturalname);
				map.put("types", types);
				map.put("resourcename", resourcename);
				resourceStackList.add(map);
			}else if(activityKey == 1)	//待办
				flag = funcJsonStr;
			else
				flag = "root";
			
	        //显示等待dialog
			pDialog = ProgressDialog.show(
					FuncGroupActivity.this, 
					getResources().getString(R.string.nowloading), 
					getResources().getString(R.string.pleasewait), 
					true, true);		

			//获取到服务端流程名称数据，并装载数据
			HelpHandler helpHandler = new HelpHandler(pDialog,
					this);
			//启动请求服务端线程，封装数据给handler
			connServiceThread = new ConnectionServiceThread( this, activityKey, helpHandler, flag);
			connServiceThread.start();

		} catch(NullPointerException ex) {
			Log.i("NullPointerException", ex.getMessage());
			Toast.makeText(this, getResources().getString(R.string.errorcode)+ex.getMessage(), Toast.LENGTH_LONG).show();
			finish();
		} catch(Exception ex) {
			Log.i("Exception", ex.getMessage());
			Toast.makeText(this, getResources().getString(R.string.errorcode)+ex.getMessage(), Toast.LENGTH_LONG).show();
			finish();
		}
        


        //实例化一个适配器
        SimpleAdapter spinnerAdapter = new SimpleAdapter(this, funcMainList, R.layout.main_fuc_spinneritem, new String[]{"resourceImgUrl", "resourcename"}, new int[]{R.id.mainfuncImg2, R.id.mainfuncText2});
        //将Spinner和数据适配器关联
        mainFuncSpinner.setAdapter(spinnerAdapter);
        //加载spinner选中项
        mainFuncSpinner.setSelection(spinnerPosition);
        
        mainFuncSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> aview, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				if(!isInit) {
					try{

						HashMap map = (HashMap) aview.getItemAtPosition(position);
						String distinguish = (String) map.get("distinguish");
						String types = (String) map.get("types");
						String naturalname = (String) map.get("naturalname");
						String resourcename = (String) map.get("resourcename");
						String flag = "";
						if(types == null) {
							Log.i("modulesnotfound", "modulesnotfound");
							Toast.makeText(FuncGroupActivity.this, 
									getResources().getString(R.string.serviceerror), 
									Toast.LENGTH_LONG).show();
							return;
						} else if(distinguish.equals("1")){
							activityKey = 7;
							processName.setText(resourcename);
							flag = naturalname + "&" + types;
							resource_layout.setVisibility(View.VISIBLE);
							resourceStackList = new ArrayList();
							HashMap map1 = new HashMap();
							map1.put("naturalname", naturalname);
							map1.put("types", types);
							map1.put("resourcename", resourcename);
							resourceStackList.add(map1);
							
						} else if(distinguish.equals("0")){
							int key = 0;
							String[][] modulesArray = ModulesConfig.modulesArray;
							for (int i = 0; i < modulesArray.length; i++) {
								if(modulesArray[i][1].equals(types)) {
									key = Integer.valueOf(modulesArray[i][1]);
									break;
								}
								if(i == modulesArray.length-1){
									Toast.makeText(FuncGroupActivity.this, 
											getResources().getString(R.string.serviceerror), 
											Toast.LENGTH_LONG).show();
									return;
								}
							}
							Intent intent = null;
							if(key == 7){
									intent = new Intent(FuncGroupActivity.this, CameraActivity.class);
									startActivity(intent);
									return;
							} else if(key == 8){
									intent = new Intent(FuncGroupActivity.this, MyMapActivity.class);
									startActivity(intent);
									return;
							}else if(key == 1){
								String extendattribute = (String) map.get("extendattribute");
								String funcJsonStr = extendattribute.split("\\$")[1];
								flag = funcJsonStr;
							} else {
								activityKey = key;
								flag = "root";
							}
						}

						//显示等待dialog
						pDialog = ProgressDialog.show(
								FuncGroupActivity.this, 
								getResources().getString(R.string.nowloading), 
								getResources().getString(R.string.pleasewait), 
								true, true);		

						//获取到服务端流程名称数据，并装载数据
						HelpHandler helpHandler = new HelpHandler(pDialog,
								FuncGroupActivity.this);
						//启动请求服务端线程，封装数据给handler
						connServiceThread = new ConnectionServiceThread(FuncGroupActivity.this, activityKey, helpHandler, flag);
						connServiceThread.start();
					} catch(Exception ex) {
						Log.i("Exception", "Exception");
						Toast.makeText(FuncGroupActivity.this, getResources().getString(R.string.errorcode)+"fuc_Exception:"+ex.getMessage(), Toast.LENGTH_LONG).show();
					}

				}
				isInit = false;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				Log.i("nothingselected", "nothingselected");
			}
		});
        
        //拦截功能下拉列表首次事件
        isInit = true;
        //“我的代办”tab按钮点击事件
        fmyagencybt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("fmyagencyclick", "fmyagencyclick");

                mainFuncSpinner.setSelection(0);
			}
		});
        
        //“查询”tab按钮点击事件
        fquerybt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//界面跳转设置
				Intent intent = new Intent(FuncGroupActivity.this,QueryContentActivity.class);

				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
			}
		});
        
        //“返回主界面”tab按钮点击事件
        fmainHomebt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
			}
		});
        
        /*--start我的代办模块初始化--*/
        
        myAgencyListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				final HashMap map = (HashMap) adapterView.getItemAtPosition(position);
				AlertDialog.Builder browDialogbuilder = new AlertDialog.Builder(FuncGroupActivity.this);
				browDialogbuilder.setTitle(R.string.browoperate);
				browDialogbuilder.setItems(R.array.formbrowarray, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						// TODO Auto-generated method stub
						if(which == 0){
							String serve = (String) map.get("serve");
							String funcJsonStr = serve.split("\\$")[1];
							Intent intent = new Intent(FuncGroupActivity.this, DynamicFormActivity.class);
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
							intent.putExtra("editFormUrl", editFormUrl);
							intent.putExtra("funcJsonStr", funcJsonStr);
							intent.putExtra("formflag", "edit");
							intent.putExtra("examineflag", true);
							intent.putExtra("lsh", lsh);
							startActivity(intent);
						}else if(which == 1){
							SharedPreferences userInfo = getSharedPreferences(SHARED_USERINFO,0);
							String userid = userInfo.getString("userId", "adminx");
							String passWord = userInfo.getString("passWord", "");
							String url = (String) map.get("url");
							//得到服务端IP
							SharedPreferences serviceInfo = getSharedPreferences(SERVICE_INFO,0);
							String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); 
							String serviceip = serviceInfo.getString("serviceip", "http://42.120.40.204:84");
							url = serviceip+PathConfig.freeLoginPrefix+userid+"&password="+passWord+"&gotourl="+URLEncoder.encode(url);
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
        /*--end我的代办模块初始化--*/
        
        
        /*--start公告模块初始化--*/
  
        /*--end公告模块初始化--*/
        
        
        /*--start频道首页模块初始化--*/
        
        channelGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Log.i("channelgridclick", "channelgridclick");

				channelGridView.setVisibility(View.GONE);
				channelGridView.setAnimation(AnimationUtils.loadAnimation(FuncGroupActivity.this, R.anim.push_left_out));
				channelTree_layout.setAnimation(AnimationUtils.loadAnimation(FuncGroupActivity.this, R.anim.push_right_in));
				channelTree_layout.setVisibility(View.VISIBLE);
				activityKey = 4;
				
				HashMap map = (HashMap) adapterView.getItemAtPosition(position); 
				String catid = map.get("catid").toString();
				//栏目数层级栈添加
				treeStackList = new ArrayList();
				treeStackList.add(catid);
		        //显示等待dialog
				pDialog = ProgressDialog.show(
						FuncGroupActivity.this, 
						getResources().getString(R.string.nowloading), 
						getResources().getString(R.string.pleasewait), 
						true, true);	
				
				//获取到服务端流程名称数据，并装载数据
				HelpHandler helpHandler = new HelpHandler(pDialog,
						FuncGroupActivity.this);
				//启动请求服务端线程，封装数据给handler
				connServiceThread = new ConnectionServiceThread(FuncGroupActivity.this, 4, helpHandler, catid);
				connServiceThread.start();

				//栏目数界面中设置一级栏目名称
				departmentName.setText(map.get("catname").toString());
				//栏目树界面图片初始化
				departmentImg.setImageBitmap(BitmapFactory.decodeFile(FunctionUtil.getSDPath() + PathConfig.appSDCardPath + "/part.png"));
			}
        	
		});
        /*--end频道首页模块初始化--*/
        
        
        /*--start频道栏目树模块初始化--*/
        
        //栏目数子栏目单击事件
        channelTree.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				ArrayList childAllList = connServiceThread.getResultList();
				ArrayList childList = (ArrayList) childAllList.get(groupPosition);
				Log.i("childList", String.valueOf(childList.size()));
				HashMap map = (HashMap) childList.get(childPosition);
				if(map.get("catname") == null) {
					String url = (String) map.get("url");
					Intent intent = new Intent( Intent.ACTION_VIEW );
					intent.setData( Uri.parse( url)); //这里面是需要调转的url
					intent = Intent.createChooser( intent, null);
				    startActivity( intent);


				} else {
					String parentid = String.valueOf(map.get("parentid"));
					//栏目数层级栈添加
					treeStackList.add(parentid);
					
			        //显示等待dialog
					pDialog = ProgressDialog.show(
							FuncGroupActivity.this, 
							getResources().getString(R.string.nowloading), 
							getResources().getString(R.string.pleasewait), 
							true, true);	
					
					//获取到服务端流程名称数据，并装载数据
					HelpHandler helpHandler = new HelpHandler(pDialog,
							FuncGroupActivity.this);
					//启动请求服务端线程，封装数据给handler
					connServiceThread = new ConnectionServiceThread(FuncGroupActivity.this, 4, helpHandler, parentid);
					connServiceThread.start();
				}
				return true;
			}
		});

		//回退键单击事件，回退到以及业务流程界面
		dBackdepart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("channelgridclick", "channelgridclick");
				if(treeStackList.size() == 1) {
					channelTree_layout.setVisibility(View.GONE);
					channelTree_layout.setAnimation(AnimationUtils.loadAnimation(FuncGroupActivity.this, R.anim.push_right_out));
					channelGridView.setAnimation(AnimationUtils.loadAnimation(FuncGroupActivity.this, R.anim.push_left_in));
					channelGridView.setVisibility(View.VISIBLE);
				} else{
					treeStackList.remove(treeStackList.size()-1);
					String parentid = (String) treeStackList.get(treeStackList.size()-1);
			        //显示等待dialog
					pDialog = ProgressDialog.show(
							FuncGroupActivity.this, 
							getResources().getString(R.string.nowloading), 
							getResources().getString(R.string.pleasewait), 
							true, true);	
					
					//获取到服务端流程名称数据，并装载数据
					HelpHandler helpHandler = new HelpHandler(pDialog,
							FuncGroupActivity.this);
					//启动请求服务端线程，封装数据给handler
					connServiceThread = new ConnectionServiceThread(FuncGroupActivity.this, 4, helpHandler, parentid);
					connServiceThread.start();
				}
			}
		});
		/*--end频道栏目树模块初始化--*/
		
		
		/*--start资源目录初始化--*/
		//网格布局
        resourceGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub				
				Log.i("processgridclick", "processgridclick");

				activityKey = 7;	//设置当前刷新布局对象key
				
				HashMap map = (HashMap) adapterView.getItemAtPosition(position); 
				String naturalName = (String)map.get("naturalname");		//资源naturalname
				String resourcename = (String)map.get("resourcename");		//资源名称
				String types = (String)map.get("types");					//下一次资源展示类型
				String distinguish = (String)map.get("distinguish");		//文件类型
				//文件夹目录
				if(distinguish.equals("1")){
					//隐藏当前布局
					resourceGridView.setVisibility(View.GONE);
					resourceGridView.setAnimation(AnimationUtils.loadAnimation(FuncGroupActivity.this, R.anim.fade_in));
					//当前资源目录名称
					processName.setText(resourcename);
					HashMap map1 = new HashMap();
					map1.put("naturalname", naturalName);
					map1.put("types", types);
					map1.put("resourcename", resourcename);
					resourceStackList.add(map1);
					String flag = naturalName + "&" + types;
					
			        //显示等待dialog
					pDialog = ProgressDialog.show(
							FuncGroupActivity.this, 
							getResources().getString(R.string.nowloading), 
							getResources().getString(R.string.pleasewait), 
							true, true);		
	
					//获取到服务端流程名称数据，并装载数据
					HelpHandler helpHandler = new HelpHandler(pDialog,
							FuncGroupActivity.this);
					//启动请求服务端线程，封装数据给handler
					connServiceThread = new ConnectionServiceThread(FuncGroupActivity.this, activityKey, helpHandler, flag);
					connServiceThread.start();
					//详细流程界面图标初始化
					//processImg.setImageBitmap(BitmapFactory.decodeFile(FunctionUtil.getSDPath() + PathConfig.appSDCardPath + "/processes.png"));
				//文件（应用）
				}else if(distinguish.equals("0")){
					String extendattribute = (String)map.get("extendattribute");
					String[] extendattributeArray = extendattribute.split(";");
					if(types.equals("5")){
						for (int i = 0; i < extendattributeArray.length; i++) {
							if(extendattributeArray[i].indexOf("NATURALNAME") > 0){
								String[] appNames = extendattributeArray[i].split("\\$");
								String appName = appNames[1];
								Intent intent = new Intent(FuncGroupActivity.this, WorkOrdersActivity.class);
								String appname = (String) map.get("appname");
								intent.putExtra("appname", appName);
								startActivity(intent);
							}else{
								if(i == extendattributeArray.length-1)
									Toast.makeText(FuncGroupActivity.this, R.string.noProcessNaturalName, Toast.LENGTH_LONG).show();
							}
						}
						
						
					}else if(types.equals("16")){
						
					}
				}
			}
		});
        
        //网格布局项长按事件
        resourceGridView.setLongClickable(true);
        resourceGridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapterView, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Log.i("mainFuncGridlongclick","mainFuncGridlongclick");
				
				HashMap map = (HashMap) adapterView.getItemAtPosition(position); 
				String naturalName = (String)map.get("naturalname");		//资源naturalname
				String resourcename = (String)map.get("resourcename");		//资源名称
				final String types = (String)map.get("types");					//下一次资源展示类型
				String distinguish = (String)map.get("distinguish");		//文件类型
				final String extendattribute = (String)map.get("extendattribute");	//扩展属性
				//文件夹目录
				if(distinguish.equals("1")){
					return false;
				}else if(distinguish.equals("0")){
					if(types.equals("5")){
						
					}else if(types.equals("16")){
						final String funcJsonStr = extendattribute.split("\\$")[1];

						AlertDialog.Builder funcOperatebuilder = new AlertDialog.Builder(FuncGroupActivity.this);
						funcOperatebuilder.setTitle(R.string.functionOperate);
						//获取功能对话框布局视图
						LayoutInflater inflater = getLayoutInflater();
				        View view = inflater.inflate(R.layout.funcoperate_dialog, null);
						funcOperatebuilder.setView(view);
						final AlertDialog funcOpDialog = funcOperatebuilder.create();
						funcOpDialog.show();
						ListView funcopListView = (ListView) view.findViewById(R.id.funcopListView);
						ArrayList funcOpList = new ArrayList();		//功能列表数据
						try {
							
							JSONObject jsonObj = new JSONObject(funcJsonStr);
							for (Iterator iterator = jsonObj.keys(); iterator.hasNext();) {
								final String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								HashMap opMap = new HashMap();
								String funStr = jsonObj.getString(fieldName);
								final JSONObject jsonObj2 = new JSONObject(funStr);
								String imageName = "";
								for (Iterator iterator2 = jsonObj2.keys(); iterator2.hasNext();) {
									String fieldName2 = (String) iterator2.next();
									if(fieldName.equals("imagename")){
										imageName = jsonObj2.getString(fieldName);
									}
								}
								if(fieldName.equals(openFuncid)){
									
									opMap.put("key",openFuncid);
									opMap.put("funcName",getResources().getString(R.string.open));
									opMap.put("imagePath",R.drawable.open);
									funcOpList.add(opMap);

								}else if(fieldName.equals(photographFuncid)){
									opMap.put("key",photographFuncid);
									opMap.put("funcName",getResources().getString(R.string.photograph));
									opMap.put("imagePath",R.drawable.uploadphone);
									funcOpList.add(opMap);
								}else if(fieldName.equals(addFormFuncid)){
									opMap.put("key",addFormFuncid);
									opMap.put("funcName",getResources().getString(R.string.addForm));
									opMap.put("imagePath",R.drawable.create);
									funcOpList.add(opMap);
								}

							}
							
									
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						WebImageListAdapter funcOpAdapter = new WebImageListAdapter(FuncGroupActivity.this , 
								funcOpList, R.layout.funcop_dialog_listitem, 
								new String[]{ "imagePath", "funcName"}, 
								new int[]{ R.id.funcOpImg, R.id.funcOpText});
						funcopListView.setAdapter(funcOpAdapter);
						
						funcopListView.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> adapterView,
									View arg1, int position, long arg3) {
								// TODO Auto-generated method stub
								try {
									funcOpDialog.dismiss();		//关闭功能dialog
									final JSONObject jsonObj = new JSONObject(funcJsonStr);
									for (Iterator iterator = jsonObj.keys(); iterator.hasNext();) {
										final String fieldName = (String) iterator.next();
										Log.i("key", fieldName);
										//打开应用
										if(fieldName.equals(openFuncid)){
											String[] extendattributeArray = extendattribute.split(";");
											//工单应用
											if(types.equals(ordersAppid)){
												for (int i = 0; i < extendattributeArray.length; i++) {
													Log.i("extendattributeArray", extendattributeArray[i]);
													String[] appNames = extendattributeArray[i].split("\\$");
													String appNameKey = appNames[0];
													Log.i("appNameKey", appNameKey);
													if(appNameKey.equals("NATURALNAME")){
														
														String appname = appNames[1];
														Log.i("appName", appname);
														Intent intent = new Intent(FuncGroupActivity.this, WorkOrdersActivity.class);
														intent.putExtra("appname", appname);
														intent.putExtra("funcJsonStr", funcJsonStr);
														startActivity(intent);
													}else{
														if(i == extendattributeArray.length-1)
															Toast.makeText(FuncGroupActivity.this, R.string.noProcessNaturalName, Toast.LENGTH_LONG).show();
													}
												}
											//表单应用
											}else if(types.equals(formAppcid)){
												String formListUrl = "";
												try {
													formListUrl = FunctionUtil.getFuncUrl(jsonObj, openFuncid);
												} catch (JSONException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												Intent intent = new Intent(FuncGroupActivity.this, UploadHistoryActivity.class);
												intent.putExtra("formListUrl", formListUrl);
												intent.putExtra("funcJsonStr", funcJsonStr);
												if(formListUrl.indexOf("SHANGCHAOXINXI") >= 0)
													intent.putExtra("formtype", "market");
												else if(formListUrl.indexOf("MBJC") >= 0)
													intent.putExtra("formtype", "Collection");
												startActivity(intent);
											}

										}else if(fieldName.equals(photographFuncid)){

											Toast.makeText(FuncGroupActivity.this, "启动相机", Toast.LENGTH_SHORT).show();
											LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
											if (locationManager
													.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
												Location location = locationManager
														.getLastKnownLocation(LocationManager.GPS_PROVIDER);
												if (location != null) {
													latitude = location.getLatitude();
													longitude = location.getLongitude();
													//Toast.makeText(FormListActivity.this, " 经度："+location.getLatitude()+"维度："+location.getLongitude(), Toast.LENGTH_LONG).show();

												}
											} else {
												LocationListener locationListener = new LocationListener() {
							
													// Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
													@Override
													public void onStatusChanged(String provider,
															int status, Bundle extras) {
							
													}
							
													// Provider被enable时触发此函数，比如GPS被打开
													@Override
													public void onProviderEnabled(String provider) {
							
													}
							
													// Provider被disable时触发此函数，比如GPS被关闭
													@Override
													public void onProviderDisabled(String provider) {
							
													}
							
													// 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
													@Override
													public void onLocationChanged(Location location) {
														if (location != null) {
															Log.e("Map", "Location changed : Lat: "
																	+ location.getLatitude() + " Lng: "
																	+ location.getLongitude());
															//Toast.makeText(FormListActivity.this, "改变的： 经度："+location.getLatitude()+"维度："+location.getLongitude(), Toast.LENGTH_LONG).show();
														}
													}
												};
												locationManager.requestLocationUpdates(
														LocationManager.NETWORK_PROVIDER, 1000, 0,
														locationListener);
												Location location = locationManager
														.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
												if (location != null) {
													latitude = location.getLatitude(); 		// 经度
													longitude = location.getLongitude(); 	// 纬度
													//Toast.makeText(FormListActivity.this, " 经度："+location.getLatitude()+"维度："+location.getLongitude(), Toast.LENGTH_LONG).show();
												}

											}
			
											SharedPreferences serviceInfo = getSharedPreferences(SERVICE_INFO,0);
											String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); 
											String serviceip = serviceInfo.getString("serviceip", "http://42.120.40.204:84");
											StringBuffer uploadInfo = new StringBuffer();	//新增表单数据
											String addFormUrl = null;
											try {
												addFormUrl = FunctionUtil.getFuncUrl(jsonObj, fieldName);

											} catch (JSONException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											uploadInfo.append(addFormUrl);
											uploadInfo.append("&");
											uploadInfo.append(longitude);
											uploadInfo.append("&");
											uploadInfo.append(latitude);
											uploadInfo.append("&");
											uploadInfo.append(nowDate);
											uploadInfo.append("&");
											uploadInfo.append("form");
											uploadInfo.append("&");
											uploadInfo.append("add");
											//获取到服务端流程名称数据，加载下拉列表
											HelpHandler helpHandler = new HelpHandler(null,
													FuncGroupActivity.this);
											//获取流程名称，服务端线程连接
											ConnectionServiceThread connServiceThread = new ConnectionServiceThread(FuncGroupActivity.this, 13, helpHandler, uploadInfo.toString());
											connServiceThread.start();

										//新增功能
										}else if(fieldName.equals(addFormFuncid)){

											StringBuffer uploadInfo = new StringBuffer();	//新增表单数据
											String addFormUrl = null;
											String loadFormUrl = null;
											try {
												addFormUrl = FunctionUtil.getFuncUrl(jsonObj, fieldName);
												loadFormUrl = FunctionUtil.getFuncUrl(jsonObj, loadFormFuncid);

											} catch (JSONException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}

											uploadInfo.append(addFormUrl);
											uploadInfo.append(";");
											uploadInfo.append(loadFormUrl);
											uploadInfo.append(";");
											uploadInfo.append(funcJsonStr);
											// 显示等待dialog
											ProgressDialog pDialog = ProgressDialog.show(FuncGroupActivity.this, getResources()
													.getString(R.string.nowloading), getResources().getString(
													R.string.pleasewait), true, true);

											// 获取到服务端流程名称数据，并装载数据
											HelpHandler helpHandler = new HelpHandler(pDialog, FuncGroupActivity.this);
											// 启动请求服务端线程，封装数据给handler
											ConnectionServiceThread connServiceThread = new ConnectionServiceThread(
													FuncGroupActivity.this, 22, helpHandler, uploadInfo.toString());
											connServiceThread.start();
													
										}
					
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							
						});


					}
					return true;
				}
				return true;
				
			}
		});
        
        //列表布局
        resourceListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Log.i("processDetailListclick", "processDetailListclick");
				

				
				activityKey = 7;	//设置当前刷新布局对象key
				
				HashMap map = (HashMap) adapterView.getItemAtPosition(position); 
				String naturalName = (String)map.get("naturalname");		//资源naturalname
				String resourcename = (String)map.get("resourcename");		//资源名称
				String types = (String)map.get("types");					//下一次资源展示类型
				String distinguish = (String)map.get("distinguish");		//文件类型
				//FuncGroupActivity.this.naturalname = naturalName;
				if(distinguish.equals("1")){
					//隐藏当前布局
					resourceListView.setVisibility(View.GONE);
					resourceListView.setAnimation(AnimationUtils.loadAnimation(FuncGroupActivity.this, R.anim.fade_in));
					//当前资源目录名称
					processName.setText(resourcename);
					HashMap map1 = new HashMap();
					map1.put("naturalname", naturalName);
					map1.put("types", types);
					map1.put("resourcename", resourcename);
					resourceStackList.add(map1);
					String flag = naturalName + "&" + types;
					
			        //显示等待dialog
					pDialog = ProgressDialog.show(
							FuncGroupActivity.this, 
							getResources().getString(R.string.nowloading), 
							getResources().getString(R.string.pleasewait), 
							true, true);		
	
					//获取到服务端流程名称数据，并装载数据
					HelpHandler helpHandler = new HelpHandler(pDialog,
							FuncGroupActivity.this);
					//启动请求服务端线程，封装数据给handler
					connServiceThread = new ConnectionServiceThread(FuncGroupActivity.this, activityKey, helpHandler, flag);
					connServiceThread.start();
					//详细流程界面图标初始化
					//processImg.setImageBitmap(BitmapFactory.decodeFile(FunctionUtil.getSDPath() + PathConfig.appSDCardPath + "/processes.png"));
				}else if(distinguish.equals("0")){
					String extendattribute = (String)map.get("extendattribute");
					String funcJsonStr = extendattribute.split("\\$")[1];
					String[] extendattributeArray = extendattribute.split(";");
					if(types.equals("5")){

						Log.i("funcJsonStr", funcJsonStr);
						Intent intent = new Intent(FuncGroupActivity.this, WorkOrdersActivity.class);
						intent.putExtra("funcJsonStr", funcJsonStr);
						startActivity(intent);
//							}else{
//								if(i == extendattributeArray.length-1)
//									Toast.makeText(FuncGroupActivity.this, R.string.noProcessNaturalName, Toast.LENGTH_LONG).show();
//							}

						
						
					}else if(types.equals("16")){
						JSONObject jsonObj;
						try {
							jsonObj = new JSONObject(funcJsonStr);
							for (Iterator iterator = jsonObj.keys(); iterator.hasNext();) {
								String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								if(fieldName.equals("00")){
									//Intent intent = new Intent(FuncGroupActivity.this, UploadHistoryActivity.class);
									//intent.putExtra("id", id);
									//startActivity(intent);
									Toast.makeText(FuncGroupActivity.this, "formlist", Toast.LENGTH_SHORT).show();
								}

							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			}
		});

        //回退键单击事件，回退到以及业务流程界面
        pBackProcess.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("pbackprocessclick", "pbackprocessclick");
				channelTree_layout.setVisibility(View.GONE);
				if(resourceStackList.size() != 1){
					resourceGridView.setVisibility(View.GONE);
					resourceListView.setVisibility(View.GONE);
					resourceStackList.remove(resourceStackList.size()-1);
					HashMap map = (HashMap) resourceStackList.get(resourceStackList.size()-1);
					String naturalName = (String) map.get("naturalname");
					String types = (String) map.get("types");
					String resourcename = (String) map.get("resourcename");
					String flag = naturalName + "&" + types;
					//显示等待dialog
					pDialog = ProgressDialog.show(
							FuncGroupActivity.this, 
							getResources().getString(R.string.nowloading), 
							getResources().getString(R.string.pleasewait), 
							true, true);		
	
					//获取到服务端流程名称数据，并装载数据
					HelpHandler helpHandler = new HelpHandler(pDialog,
							FuncGroupActivity.this);
					//启动请求服务端线程，封装数据给handler
					connServiceThread = new ConnectionServiceThread(FuncGroupActivity.this, 7, helpHandler, flag);
					connServiceThread.start();
					//当前资源目录名称
					processName.setText(resourcename);
				}else{
					finish();
				}

			}
		});
            
        /*--end详细业务流程模块初始化--*/
        
        /*--start表单列表模块初始化--*/
        
//        	formDirGridView.setOnItemClickListener(new OnItemClickListener() {
//
//				@Override
//				public void onItemClick(AdapterView<?> adapterView, View arg1,
//						int position, long arg3) {
//					// TODO Auto-generated method stub
//					HashMap map = (HashMap) adapterView.getItemAtPosition(position);
//					//String key = (String) map.get("key");
//					String key = "0";
//					Log.i("isdirkey", key);
//					String naturalName = (String) map.get("naturalname");
//					//String flag = naturalName + "&" + key;
//					String flag = naturalName + "&0";
//					if(key.equals("1")){
//						//显示等待dialog
//						pDialog = ProgressDialog.show(
//								FuncGroupActivity.this, 
//								getResources().getString(R.string.nowloading), 
//								getResources().getString(R.string.pleasewait), 
//								true, true);
//
//						//获取到服务端流程名称数据，并装载数据
//						HelpHandler helpHandler = new HelpHandler(pDialog,
//								FuncGroupActivity.this);
//						//启动请求服务端线程，封装数据给handler
//						connServiceThread = new ConnectionServiceThread(FuncGroupActivity.this, 16, helpHandler, flag);
//						connServiceThread.start();
//					}else{
//						Intent intent = new Intent(FuncGroupActivity.this, FormListActivity.class);
//						intent.putExtra("naturalName", naturalName);
//						startActivity(intent);
//					}
//				}
//			});
        /*--end表单列表模块初始化--*/
        
      //加载对话框取消事件
        pDialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				//connServiceThread.interrupt();
				//Toast.makeText(FuncGroupActivity.this, R.string.cancelDataLoad, Toast.LENGTH_LONG).show();
			}
		});
        

    }
    
    @Override
    public void onStart(){
    	super.onStart();
    	Log.i("funconstart", "funconstart");
    }
    
    @Override
    public void onPause(){
    	super.onStart();
    	Log.i("funconPause", "funconPause");
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	Log.i("funconResume", "funconResume");
    }
    
    @Override
    public void onStop(){
    	super.onStop();
    	Log.i("funconStop", "funconStop");
    }
    
    
    @Override
    public void onDestroy(){
    	super.onDestroy();
    	Log.i("funconDestroy", "funconDestroy");
    }
    
	@Override
	/**
	 * 创建菜单
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add("menu");// 必须创建一项
		return super.onCreateOptionsMenu(menu);
	}
    
	@Override
	/**
	 * 拦截MENU键
	 */
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (menuDialog == null) {
			menuDialog = new AlertDialog.Builder(this).setView(menuView).show();
		} else {
			menuDialog.show();
		}
		return false;// 返回为true 则显示系统menu
	}
}
