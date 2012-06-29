package com.wfp.activity;

import java.io.File;
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
import android.graphics.PixelFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.wfp.config.ModulesConfig;
import com.wfp.config.PathConfig;
import com.wfp.customcontrols.WebImageListAdapter;
import com.wfp.util.ConnectionServiceThread;
import com.wfp.util.FunctionUtil;
import com.wfp.util.HelpHandler;
public class MobileworkflowplatformActivity extends Activity {
	
	//界面视图元素
	private GridView mainFuncGrid;
	private ImageButton searchBt;
	private RadioButton myagencybt;
	private RadioButton querybt;
	private EditText rearchText;
	private AlertDialog menuDialog; // menu菜单Dialog
	private View menuView;
	private GridView menuGrid;
	private ProgressDialog pDialog;
	private ConnectionServiceThread connServiceThread;
	
	//参数
    private static String SHARED_USERINFO = "userinfo";		//账户信息key
    private static String SERVICE_INFO = "SERVICEINFO";		//服务端信息key
    private static String openFuncid = "00";		//打开功能编号
    private static String photographFuncid = "01";	//拍照功能编号
    private static String uploadFileFuncid = "02";	//附件管理功能编号
    private static String addFormFuncid = "25";		//新建表单数据功能编号
    private static String loadFormFuncid = "29";	//装载表单结构功能编号
    private static String radiusMarketFuncid = "30";	//搜索半径内网点超市
    
    private static String queryPersonPoint = "22";	//查询人的经纬度
    private static String queryMarketPoint = "23";	//查询商超的经纬度
    private static String UploadPicPersonInfo = "24";	//查询上传照片人员
    
    private static String ordersAppid = "5";		//工单管理应用编号
    private static String formAppcid = "16";		//表单应用编号
    private double latitude=0.0;  	//经度
    private double longitude =0.0;  //维度
    private static int MAP = 7;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);	//防止PNG图片失真
        getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.main);
        Log.i("loadMainonCreate", "loadMainonCreate");

        
        //初始化界面元素
        searchBt = (ImageButton)findViewById(R.id.searchbt);
        mainFuncGrid = (GridView)findViewById(R.id.main_function_gridview);
        myagencybt = (RadioButton)findViewById(R.id.myagencybt);
        querybt = (RadioButton) findViewById(R.id.querybt);
        rearchText = (EditText) findViewById(R.id.rearchText);
        
        //界面数据
        final String naturalName = "PHONE.PHONE.SHOUJI";		//资源根目录naturalname
        Intent intent = getIntent();
        String test = intent.getStringExtra("test");

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
		//监听menu选项 
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
					
					//显示等待dialog
					pDialog = ProgressDialog.show(
							MobileworkflowplatformActivity.this, 
							getResources().getString(R.string.nowloading), 
							getResources().getString(R.string.pleasewait), 
							true,true);	

					//获取到服务端流程名称数据，并装载数据
					HelpHandler helpHandler = new HelpHandler(pDialog,
							MobileworkflowplatformActivity.this);
					//启动请求服务端线程，封装数据给handler
					connServiceThread = new ConnectionServiceThread(MobileworkflowplatformActivity.this, 7, helpHandler, naturalName);
					connServiceThread.start();
				}else if(menuItemName.equals(getResources().getString(R.string.systemsetting))){
					Log.i("menu_syssetting", "menu_syssetting");
					menuDialog.dismiss();
			    	Intent intent = new Intent(MobileworkflowplatformActivity.this, SystemSettingActivity.class);
			    	startActivity(intent);
				}else if(menuItemName.equals(getResources().getString(R.string.exit))){
					Log.i("menu_exit", "menu_exit");
					finish();
				}
			}
		});
        //待办添加待办数图标
//		Bitmap bitmap = FunctionUtil.getResIcon(getResources(), R.drawable.myagencytab);
//		Bitmap rsBitmap = FunctionUtil.generatorContactCountIcon(this, bitmap);
//		Drawable dr = new BitmapDrawable(rsBitmap);
		//myagencybt.setCompoundDrawablePadding(2);
		//myagencybt.setCompoundDrawablesWithIntrinsicBounds(null, dr, null, null);
		
		//显示等待dialog
		pDialog = ProgressDialog.show(
				this, 
				getResources().getString(R.string.nowloading), 
				getResources().getString(R.string.pleasewait), 
				true,true);

		//获取到服务端流程名称数据，并装载数据
		HelpHandler helpHandler = new HelpHandler(pDialog,
				this);
		//启动请求服务端线程，封装数据给handler
		connServiceThread = new ConnectionServiceThread( this, 7, helpHandler, naturalName);
		connServiceThread.start();

        mainFuncGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = null;
				try {
					HashMap hashMap = (HashMap) adapterView.getItemAtPosition(position);
					String distinguish = (String) hashMap.get("distinguish");
					String extendattribute = (String)hashMap.get("extendattribute");	//扩展属性
					String types = (String) hashMap.get("types");
					
					if(types == null) {
						Log.i("modulesnotfound", "modulesnotfound");
						Toast.makeText(MobileworkflowplatformActivity.this, 
								getResources().getString(R.string.serviceerror), 
								Toast.LENGTH_LONG).show();
						return;
					} else if(distinguish.equals("1")){
						intent = new Intent(MobileworkflowplatformActivity.this,FuncGroupActivity.class);
						String naturalname = (String) hashMap.get("naturalname");
						String resourcename = (String) hashMap.get("resourcename");
						intent.putExtra("distinguish", distinguish);
						intent.putExtra("naturalname", naturalname);
						intent.putExtra("resourcename", resourcename);
						intent.putExtra("types", types);
						intent.putExtra("key", 7);
						intent.putExtra("position", position);
						intent.putStringArrayListExtra("funcMainList", connServiceThread.getResultList());
						startActivity(intent);
						overridePendingTransition(R.anim.zoom_enter, R.anim.fade_out);
					} else if(distinguish.equals("0")){
						int key = 0;
						String[][] modulesArray = ModulesConfig.modulesArray;
						for (int i = 0; i < modulesArray.length; i++) {
							if(modulesArray[i][1].equals(types)) {
								key = Integer.valueOf(modulesArray[i][1]);
								break;
							}
							if(i == modulesArray.length-1){
								Toast.makeText(MobileworkflowplatformActivity.this, 
										getResources().getString(R.string.serviceerror), 
										Toast.LENGTH_LONG).show();
								return;
							}
						}
						String funcJsonStr = extendattribute.split("\\$")[1];	//功能json字符串
						JSONObject jsonObj = new JSONObject(funcJsonStr);
						if(key == 7){	//启动拍照功能
							String radiusMarketUrl = FunctionUtil.getFuncUrl(jsonObj, radiusMarketFuncid);
							String addFormUrl = FunctionUtil.getFuncUrl(jsonObj, addFormFuncid);
							intent = new Intent(MobileworkflowplatformActivity.this, ChooseMarketActivity.class);
							intent.putExtra("radiusMarketUrl", radiusMarketUrl);
							intent.putExtra("addFormUrl", addFormUrl);
							startActivity(intent);
						} else if(key == 8){	//启动google地图

							Log.i("mapfuncJsonStr", funcJsonStr);
							String marketPointUrl = FunctionUtil.getFuncUrl(jsonObj, queryMarketPoint);
							String personPointUrl = FunctionUtil.getFuncUrl(jsonObj, queryPersonPoint);
							//String uploadPicPersonUrl = FunctionUtil.getFuncUrl(jsonObj, UploadPicPersonInfo);
							intent = new Intent(MobileworkflowplatformActivity.this, MyMapActivity.class);
							
							startActivityForResult(intent, MAP);
						} else if(key == 16){	//启动表单应用

							String formListUrl = FunctionUtil.getFuncUrl(jsonObj, openFuncid);
							intent = new Intent(MobileworkflowplatformActivity.this, UploadHistoryActivity.class);
							intent.putExtra("formListUrl", formListUrl);
							intent.putExtra("funcJsonStr", funcJsonStr);
							if(formListUrl.indexOf("SHANGCHAOXINXI") >= 0)
								intent.putExtra("formtype", "market");
							else if(formListUrl.indexOf("MBJC") >= 0)
								intent.putExtra("formtype", "Collection");
							startActivity(intent);
						} else if(key == 9){

							String uploadFileUrl = FunctionUtil.getFuncUrl(jsonObj, uploadFileFuncid);
							File file = null;
							Intent intent9 = new Intent(
									MobileworkflowplatformActivity.this,
									OrdersUploadImgActivity.class);
							String imgRootPath = FunctionUtil.getSDPath()
							+ PathConfig.appSDCardPath
							+ PathConfig.uploadImgPath
							+ PathConfig.formImgPath + "/common";
							
							Log.i("formfilepath", imgRootPath );
							file = new File(imgRootPath );

							intent9.putExtra("uploadFileUrl", uploadFileUrl);
							intent9.putExtra("formflag", "edit");
							intent9.putExtra("imgRootPath", imgRootPath);
							if (!file.isDirectory()) 
								file.createNewFile();
							startActivity(intent9);
//							} else {
								
//								Toast.makeText(MobileworkflowplatformActivity.this,
//										R.string.notFindOrderImg,
//										Toast.LENGTH_LONG).show();
//							}
							
						} else {
						//界面跳转设置
							intent = new Intent(MobileworkflowplatformActivity.this,FuncGroupActivity.class);
							intent.putExtra("key", key);
							intent.putExtra("position", position);
							intent.putStringArrayListExtra("funcMainList", connServiceThread.getResultList());
							if(key == 1)
								intent.putExtra("funcJsonStr", funcJsonStr);
							startActivity(intent);
							overridePendingTransition(R.anim.zoom_enter, R.anim.fade_out);
						}
					}


				} catch(Exception ex) {
					Log.i("Exception", "Exception");
					Toast.makeText(MobileworkflowplatformActivity.this, 
							getResources().getString(R.string.errorcode)+"main_Exception:"+ex.getMessage(), 
							Toast.LENGTH_LONG).show();
				}


			}
        	
		});
        
        //网格布局项长按事件
        mainFuncGrid.setLongClickable(true);
        mainFuncGrid.setOnItemLongClickListener(new OnItemLongClickListener() {

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

						AlertDialog.Builder funcOperatebuilder = new AlertDialog.Builder(MobileworkflowplatformActivity.this);
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
						WebImageListAdapter funcOpAdapter = new WebImageListAdapter(MobileworkflowplatformActivity.this , 
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

										HashMap map = (HashMap) adapterView.getItemAtPosition(position);
										String fucKey = (String) map.get("key");
										Log.i("fucKey", fucKey);
										//打开应用
										if(fucKey.equals(openFuncid)){

												String formListUrl = "";
												try {
													formListUrl = FunctionUtil.getFuncUrl(jsonObj, openFuncid);
												} catch (JSONException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
												Intent intent = new Intent(MobileworkflowplatformActivity.this, UploadHistoryActivity.class);
												intent.putExtra("formListUrl", formListUrl);
												intent.putExtra("funcJsonStr", funcJsonStr);
												if(formListUrl.indexOf("SHANGCHAOXINXI") >= 0)
													intent.putExtra("formtype", "market");
												else if(formListUrl.indexOf("MBJC") >= 0)
													intent.putExtra("formtype", "Collection");
												startActivity(intent);


										}else if(fucKey.equals(photographFuncid)){

											Toast.makeText(MobileworkflowplatformActivity.this, "启动相机", Toast.LENGTH_SHORT).show();
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
												addFormUrl = FunctionUtil.getFuncUrl(jsonObj, "addFormFuncid");

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
													MobileworkflowplatformActivity.this);
											//获取流程名称，服务端线程连接
											ConnectionServiceThread connServiceThread = new ConnectionServiceThread(MobileworkflowplatformActivity.this, 13, helpHandler, uploadInfo.toString());
											connServiceThread.start();

										//新增功能
										}else if(fucKey.equals(addFormFuncid)){

											StringBuffer uploadInfo = new StringBuffer();	//新增表单数据
											String addFormUrl = null;
											String loadFormUrl = null;
											try {
												addFormUrl = FunctionUtil.getFuncUrl(jsonObj, addFormFuncid);
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
											ProgressDialog pDialog = ProgressDialog.show(MobileworkflowplatformActivity.this, getResources()
													.getString(R.string.nowloading), getResources().getString(
													R.string.pleasewait), true, true);

											// 获取到服务端流程名称数据，并装载数据
											HelpHandler helpHandler = new HelpHandler(pDialog, MobileworkflowplatformActivity.this);
											// 启动请求服务端线程，封装数据给handler
											ConnectionServiceThread connServiceThread = new ConnectionServiceThread(
													MobileworkflowplatformActivity.this, 22, helpHandler, uploadInfo.toString());
											connServiceThread.start();
													
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
        
        //查询内容按钮单击事件
        searchBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MobileworkflowplatformActivity.this, QueryContentActivity.class);
				startActivity(intent);
			}
		});
        //“我的代办”tab按钮点击事件
        myagencybt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//界面跳转设置
				Intent intent = new Intent(MobileworkflowplatformActivity.this,FuncGroupActivity.class);
				intent.putExtra("key", 1);
				intent.putStringArrayListExtra("funcMainList", connServiceThread.getResultList());
				intent.putExtra("tabposition", 1);
				startActivity(intent);
				overridePendingTransition(R.anim.zoom_enter, R.anim.fade_out);
			}
		});
        //“查询”tab按钮点击事件
        querybt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//界面跳转设置
				Intent intent = new Intent(MobileworkflowplatformActivity.this,QueryContentActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
			}
		});
        
        //加载对话框取消事件
        pDialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				//connServiceThread.interrupt();
				//Toast.makeText(MobileworkflowplatformActivity.this, R.string.cancelDataLoad, Toast.LENGTH_LONG).show();
			}
		});
    }
    
    @Override
    public void onStart(){
    	super.onStart();
    	Log.i("mainonstart", "mainonstart");
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