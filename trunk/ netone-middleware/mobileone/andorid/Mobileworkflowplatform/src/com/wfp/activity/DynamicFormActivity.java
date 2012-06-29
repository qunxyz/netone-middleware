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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wfp.config.PathConfig;
import com.wfp.util.ConnectionServiceThread;
import com.wfp.util.FunctionUtil;
import com.wfp.util.HelpHandler;

public class DynamicFormActivity extends Activity {

	// 界面元素
	private Button createFormBt;
	private Button editFormBt;
	private Button exitFormBt;
	private TextView formTitle;
	private LinearLayout formFunc_layout;
	private CheckBox isapproveBox;
	private LinearLayout isapprove_layout;
	
	//参数
	private double latitude=0.0;  	//经度
    private double longitude =0.0;  //维度
    private static String openFuncid = "00";		//打开功能编号
    private static String photographFuncid = "01";	//拍照功能编号
    private static String uploadFileFuncid = "02";	//附件管理功能编号
    private static String addFormFuncid = "25";		//新建表单数据功能编号
    private static String updateFormFuncid = "26";	//修改表单数据功能编号
    private static String queryFormFuncid = "27";	//查询表单数据功能编号
    private static String deleteFormFuncid = "28";	//删除表单数据功能编号
    private static String loadFormFuncid = "29";	//装载表单结构功能编号
    private static String SHARED_USERINFO = "userinfo";
    private static String SERVICE_INFO = "SERVICEINFO";
    private String formListUrl;
    private String editFormUrl;
    private String queryFormUrl;
    private String deleteFormUrl;
    private String photographUrl;
    private String uploadFileUrl;
    private String loadFormUrl;
    private String addFormUrl;
    private ConnectionServiceThread formConnServiceThread;		//表单请求数据线程
    private HelpHandler helpHandler;							//表单操作handler
    private String userid;	//账号
    private String lsh;		//表单流水号
    private boolean isapprove = false;	//是否审核通过
    private boolean examine;	//是否为审核状态
    private static int YDFORM_TO_NODE = 11;	//动态表单界面与节点选择界面跳转标示符
    private String funcJsonStr;		//功能json字符串
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		setContentView(R.layout.dynamicform);
		Log.i("DynamicFormactivity", "DynamicFormactivity");

		// 界面视图初始化
		createFormBt = (Button) findViewById(R.id.createFormBt);
		editFormBt = (Button) findViewById(R.id.editFormBt);
		exitFormBt = (Button) findViewById(R.id.exitFormBt);
		formTitle = (TextView) findViewById(R.id.formTitle);
		formFunc_layout = (LinearLayout) findViewById(R.id.formFunc_layout);
		isapproveBox = (CheckBox) findViewById(R.id.isapprove); 
		isapprove_layout = (LinearLayout) findViewById(R.id.isapprove_layout);

		// 界面数据初始化
		Intent intent = getIntent();
		final String formflag = intent.getStringExtra("formflag");
		funcJsonStr = intent.getStringExtra("funcJsonStr");
		lsh = intent.getStringExtra("lsh");
		examine = intent.getBooleanExtra("examineflag", false);
		SharedPreferences userInfo = getSharedPreferences(SHARED_USERINFO,0);
		userid = userInfo.getString("userId", "adminx");
		// 获取功能url
		formListUrl = "";
		editFormUrl = "";
		queryFormUrl = "";
		deleteFormUrl = "";
		photographUrl = "";
		uploadFileUrl = "";
		loadFormUrl = "";
		addFormUrl = "";
		
		try {
			final JSONObject jsonObj = new JSONObject(funcJsonStr);
			for (Iterator iterator = jsonObj.keys(); iterator.hasNext();) {
				final String fieldName = (String) iterator.next();
				Log.i("key", fieldName);
				if(fieldName.equals(photographFuncid)){
					photographUrl = FunctionUtil.getFuncUrl(jsonObj, photographFuncid);
				}else if(fieldName.equals(uploadFileFuncid)){
					uploadFileUrl = FunctionUtil.getFuncUrl(jsonObj, uploadFileFuncid);
				}else if(fieldName.equals(addFormFuncid)){
					addFormUrl = FunctionUtil.getFuncUrl(jsonObj, addFormFuncid);
				}else if(fieldName.equals(queryFormFuncid)){
					queryFormUrl = FunctionUtil.getFuncUrl(jsonObj, queryFormFuncid);
				}else if(fieldName.equals(updateFormFuncid)){
					editFormUrl = FunctionUtil.getFuncUrl(jsonObj, updateFormFuncid);
				}else if(fieldName.equals(deleteFormFuncid)){
					deleteFormUrl = FunctionUtil.getFuncUrl(jsonObj, deleteFormFuncid);
				}else if(fieldName.equals(loadFormFuncid)){
					loadFormUrl = FunctionUtil.getFuncUrl(jsonObj, loadFormFuncid);
				}else if(fieldName.equals(openFuncid)){
					formListUrl = FunctionUtil.getFuncUrl(jsonObj, openFuncid);
				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		//传入线程的重要参数
		StringBuffer paramsBuffer = null;
		if (formflag.equals("add")) {
			//设置返回按钮显示文字
			exitFormBt.setText(R.string.invalid);
			String loadFormUrl = intent.getStringExtra("loadFormUrl");
			formTitle.setText(R.string.addForm);
			paramsBuffer = new StringBuffer();
			paramsBuffer.append(loadFormUrl);
			paramsBuffer.append(";");
			paramsBuffer.append("null");
			paramsBuffer.append(";");
			paramsBuffer.append("add");
			paramsBuffer.append(";");
			paramsBuffer.append(lsh);

			createFormBt.setVisibility(View.VISIBLE);
			//新建表单按钮
			createFormBt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					submitDynamicFormData("add");	//提交表单
				}
			});
		} else if (formflag.equals("edit")) {
			//设置返回按钮显示文字
			exitFormBt.setText(R.string.back);
			//标题设置和审核按钮显示
			if(examine){
				formTitle.setText(R.string.examine);
				isapprove_layout.setVisibility(View.VISIBLE);
				editFormBt.setText(R.string.downStep);
			}else
				formTitle.setText(R.string.editForm);
			//显示编辑按钮
			editFormBt.setVisibility(View.VISIBLE);
			//传入线程参数设置
			paramsBuffer = new StringBuffer();
			paramsBuffer.append(loadFormUrl);
			paramsBuffer.append(";");
			paramsBuffer.append(queryFormUrl);
			paramsBuffer.append(";");
			paramsBuffer.append("edit");
			paramsBuffer.append(";");
			paramsBuffer.append(lsh);

			//表单编辑按钮点击事件
			editFormBt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					submitDynamicFormData("edit");	//提交表单
				}
			});
		} else if (formflag.equals("brow")){
			//设置返回按钮显示文字
			exitFormBt.setText(R.string.back);
			//标题设置
			formTitle.setText(R.string.queryForm);
			//传入线程参数设置
			paramsBuffer = new StringBuffer();
			paramsBuffer.append(loadFormUrl);
			paramsBuffer.append(";");
			paramsBuffer.append(queryFormUrl);
			paramsBuffer.append(";");
			paramsBuffer.append("brow");
			paramsBuffer.append(";");
			paramsBuffer.append(lsh);

		}
		
		// 显示等待dialog
		ProgressDialog pDialog = ProgressDialog.show(this, getResources()
				.getString(R.string.nowloading), getResources().getString(
				R.string.pleasewait), true, true);

		// 获取到服务端流程名称数据，并装载数据
		helpHandler = new HelpHandler(pDialog, this);
		// 启动请求服务端线程，封装数据给handler
		formConnServiceThread = new ConnectionServiceThread(
				this, 20, helpHandler, paramsBuffer.toString());
		formConnServiceThread.start();
		
		//配置表单功能
		//formFunc_layout.removeAllViews();

		try {
			final JSONObject jsonObj2 = new JSONObject(funcJsonStr);
			for (Iterator iterator = jsonObj2.keys(); iterator.hasNext();) {
				final String fieldName = (String) iterator.next();
				Log.i("key", fieldName);
				
				if(fieldName.equals(photographFuncid)){
					//非查看表单时配置拍照功能
					if(!formflag.equals("brow")){
						ImageView photographBt = new ImageButton(this);
						LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(40, 40);
						layoutParams.setMargins(0, 0, 15, 0);
						photographBt.setLayoutParams(layoutParams);
						photographBt.setBackgroundResource(R.drawable.uploadphonebt);
						formFunc_layout.addView(photographBt);
						//拍照按钮点击事件
						photographBt.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								Toast.makeText(DynamicFormActivity.this, R.string.openCamera, Toast.LENGTH_SHORT).show();

								//获取当前经纬度
						        double[] LonlatArray = FunctionUtil.getLonlatValue(DynamicFormActivity.this);
								Log.i("latitude  longitude ", LonlatArray[1]+"  "+LonlatArray[0]);
								String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); 
								StringBuffer uploadInfo = new StringBuffer();	//新增表单数据
								//传入线程参数
								uploadInfo.append(editFormUrl);
								uploadInfo.append("&");
								uploadInfo.append(LonlatArray[0]);
								uploadInfo.append("&");
								uploadInfo.append(LonlatArray[1]);
								uploadInfo.append("&");
								uploadInfo.append(lsh);
								uploadInfo.append("&");
								uploadInfo.append("form");
								uploadInfo.append("&");
								uploadInfo.append("edit");
								//获取到服务端流程名称数据，加载下拉列表
								HelpHandler helpHandler = new HelpHandler(null,
										DynamicFormActivity.this);
								//获取流程名称，服务端线程连接
								ConnectionServiceThread connServiceThread = new ConnectionServiceThread(DynamicFormActivity.this, 13, helpHandler, uploadInfo.toString());
								connServiceThread.start();

							}
						
						});
					}
				//附件管理入口
				}else if(fieldName.equals(uploadFileFuncid)){
					
					ImageView uploadFileBt = new ImageButton(this);
					LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(40, 40);
					layoutParams.setMargins(0, 0, 15, 0);
					uploadFileBt.setLayoutParams(layoutParams);
					uploadFileBt.setBackgroundResource(R.drawable.uploadimagebt);
					formFunc_layout.addView(uploadFileBt);
					
					uploadFileBt.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Toast.makeText( DynamicFormActivity.this, "附件管理", Toast.LENGTH_SHORT).show();

							File file = null;
							Intent intent = new Intent(
									DynamicFormActivity.this,
									OrdersUploadImgActivity.class);
							String appName = formListUrl.split("=")[formListUrl
									.split("=").length - 1];
							String imgRootPath = FunctionUtil.getSDPath()
							+ PathConfig.appSDCardPath
							+ PathConfig.uploadImgPath
							+ PathConfig.formImgPath + "/" + appName
							+ "/" + lsh;
							Log.i("formfilepath", imgRootPath);
							file = new File(imgRootPath);
							intent.putExtra("formAppName", appName);
							intent.putExtra("formlsh", lsh);
							intent.putExtra("uploadFileUrl", uploadFileUrl);
							intent.putExtra("formflag", formflag);
							intent.putExtra("imgRootPath", imgRootPath);

							if (file.isDirectory()) {

								startActivity(intent);
							} else {
								Toast.makeText(DynamicFormActivity.this,
										R.string.notFindOrderImg,
										Toast.LENGTH_LONG).show();
							}

						}
					});
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(this, getResources().getString(R.string.errorcode)+e.getMessage(), Toast.LENGTH_SHORT).show();
		}
		//返回按钮点击事件
		exitFormBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(formflag.equals("add")){		//若界面为新建表单状态，按钮功能为删除此表单记录
					Button bt = (Button)v;
					String url = deleteFormUrl + "&lsh=" + lsh;

					//显示等待dialog
		            ProgressDialog pDialog = new ProgressDialog(DynamicFormActivity.this);
		            pDialog.setMessage(getResources().getString(R.string.isDeleteFormData));
		            pDialog.show();
		    		//获取到服务端流程名称数据，并装载数据
		    		HelpHandler helpHandler = new HelpHandler(pDialog,
		    				DynamicFormActivity.this);
		    		//启动请求服务端线程，封装数据给handler
		    		ConnectionServiceThread connServiceThread = new ConnectionServiceThread(DynamicFormActivity.this, 21, helpHandler, url);
		    		connServiceThread.start();
				}
				finish();
			}
		});
		//审核勾选事件
		isapproveBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				isapprove = isChecked;
			}
		});
	}
	

	
	/**
	 * 动态表单提交数据
	 */
	private void submitDynamicFormData(String formOp){
		ArrayList formDataList = helpHandler.getFormData();
		HashMap formFieldMap = (HashMap) formDataList.get(0);
		HashMap muskMap = (HashMap) formDataList.get(1);
		for (Iterator iterator = muskMap.keySet().iterator(); iterator
				.hasNext();) {
			String key = (String) iterator.next();
			if(key.indexOf("exsitsValue") >= 0){
				int exsitsValue = (Integer) muskMap.get(key);
				//必须填写字段为空值，提示重新填写
				if(exsitsValue == 0){
					String colulmnId = key.split("&")[0];
					Toast.makeText(DynamicFormActivity.this, 
							muskMap.get(colulmnId).toString()+getResources().getString(R.string.notnull), 
							Toast.LENGTH_SHORT).show();
					return;
				}
			}
			
		}
		//表单填写无误，创建表单数据
		StringBuffer urlBuffer = new StringBuffer(editFormUrl);
		urlBuffer.append(";");
		urlBuffer.append("userid:");
		urlBuffer.append(userid);
		urlBuffer.append("$");
		urlBuffer.append("lsh:");
		urlBuffer.append(lsh);
		urlBuffer.append("$");
		for (Iterator iterator = formFieldMap.keySet().iterator(); iterator
				.hasNext();) {
			String key = (String) iterator.next();		
			urlBuffer.append(key);
			urlBuffer.append(":");
			String fieldValue = formFieldMap.get(key).toString().trim();
			if(fieldValue.equals("") || fieldValue == null)
				urlBuffer.append("empty");
			else
				urlBuffer.append(fieldValue);
			urlBuffer.append("$");
		}
		urlBuffer.append(";");
		urlBuffer.append(formOp);
		urlBuffer.append(";");
		if(examine){
			urlBuffer.append(isapprove);
			urlBuffer.append(";");
			urlBuffer.append(funcJsonStr);
		
		}else
			urlBuffer.append("noexamine");
		Log.i("editFormurl", urlBuffer.toString());
		// 显示等待dialog
		ProgressDialog pDialog = ProgressDialog.show(DynamicFormActivity.this, getResources()
				.getString(R.string.nowloading), getResources().getString(
				R.string.pleasewait), true, true);

		// 获取到服务端流程名称数据，并装载数据
		HelpHandler helpHandler = new HelpHandler(pDialog, DynamicFormActivity.this);
		// 启动请求服务端线程，封装数据给handler
		ConnectionServiceThread ConnServiceThread = new ConnectionServiceThread(
				DynamicFormActivity.this, 23, helpHandler, urlBuffer.toString());
		ConnServiceThread.start();
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if(requestCode == YDFORM_TO_NODE){
    		if(resultCode == RESULT_CANCELED){
    			setResult(RESULT_CANCELED);
    			finish();
    		}
    	}
    }
	
}
