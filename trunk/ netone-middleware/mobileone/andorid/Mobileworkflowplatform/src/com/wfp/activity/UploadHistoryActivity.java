package com.wfp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.wfp.util.ConnectionServiceThread;
import com.wfp.util.ConnectionServiceThread2;
import com.wfp.util.FunctionUtil;
import com.wfp.util.HelpHandler;
import com.wfp.util.HelpHandler2;

public class UploadHistoryActivity extends Activity {
	
	//界面元素
	private ListView historyListView;
	
	//参数
	private String lsh;
	private ConnectionServiceThread connServiceThread;
    private static String openFuncid = "00";		//打开功能编号
    private static String photographFuncid = "01";	//拍照功能编号
    private static String uploadFileFuncid = "02";	//附件管理功能编号
    private static String addFormFuncid = "25";		//新建表单数据功能编号
    private static String updateFormFuncid = "26";	//修改表单数据功能编号
    private static String queryFormFuncid = "27";	//查询表单数据功能编号
    private static String deleteFormFuncid = "28";	//删除表单数据功能编号
    private static String loadFormFuncid = "29";	//装载表单结构功能编号
    private String formListUrl;
    private String editFormUrl;
    private String queryFormUrl;
    private String deleteFormUrl;
    private String photographUrl;
    private String uploadFileUrl;
    private String loadFormUrl;
    private String addFormUrl;
    private String opFlags;		//表单操作标签
    private static int EDIT_FORM = 14;
    private String formtype;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.upload_history);
        
        //初始化界面元素
        historyListView = (ListView) findViewById(R.id.historyListView);
        
        //初始化数据
        Intent intent = getIntent();
        final String funcJsonStr = intent.getStringExtra("funcJsonStr");
        formtype = intent.getStringExtra("formtype");
		
        // 获取功能url
		formListUrl = "";
		editFormUrl = "";
		queryFormUrl = "";
		deleteFormUrl = "";
		photographUrl = "";
		uploadFileUrl = "";
		loadFormUrl = "";
		addFormUrl = "";
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(funcJsonStr);
			for (Iterator iterator = jsonObj.keys(); iterator.hasNext();) {
				final String fieldName = (String) iterator.next();
				Log.i("funckey", fieldName);
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


//		for (Iterator iterator = jsonObj.keys(); iterator.hasNext();) {
//			final String fieldName = (String) iterator.next();
//			Log.i("key", fieldName);
//        
		String params = formListUrl + ";" + formtype;
        //显示等待dialog
        ProgressDialog pDialog = ProgressDialog.show(
				this, 
				getResources().getString(R.string.nowloading), 
				getResources().getString(R.string.pleasewait), 
				true, true);	

		//获取到服务端流程名称数据，并装载数据
		HelpHandler helpHandler = new HelpHandler(pDialog,
				this);
		//启动请求服务端线程，封装数据给handler
		connServiceThread = new ConnectionServiceThread(this, 15, helpHandler, params);
		connServiceThread.start();
		
		historyListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, final int position,
					long arg3) {
				// TODO Auto-generated method stub
				HashMap map = (HashMap) adapterView.getItemAtPosition(position);
				final String url = (String) map.get("url");
				String[] params = url.split("&");
				//从url中获取lsh
				for (int i = 0; i < params.length; i++) {
					if(params[i].indexOf("lsh") >=0){
						String[] param = params[i].split("=");
						lsh = param[1];
					}
				}
				//表单操作过滤
				String opText = "";
				String opFlags = "";
				if(!queryFormUrl.equals(""))
					opText += getResources().getString(R.string.query)+",";

				if(!editFormUrl.equals(""))
					opText += getResources().getString(R.string.edit)+",";
				
				if(!deleteFormUrl.equals(""))
					opText += getResources().getString(R.string.delete)+",";
				
				final String[] opTextArray = opText.split(",");
				Log.i("opTextArraylength", String.valueOf(opTextArray.length)+opTextArray[0]);
				if(!opText.equals("")) {
					AlertDialog.Builder operateDialogbuilder = new AlertDialog.Builder(UploadHistoryActivity.this);
					operateDialogbuilder.setTitle(R.string.formoperate);
					operateDialogbuilder.setItems(opTextArray, new OnClickListener() {
					
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
							String opText = opTextArray[which];
							//查看表单
							if(opText.equals(getResources().getString(R.string.query))){
								AlertDialog.Builder browDialogbuilder = new AlertDialog.Builder(UploadHistoryActivity.this);
								browDialogbuilder.setTitle(R.string.browoperate);
								browDialogbuilder.setItems(R.array.formbrowarray, new OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										if(which == 0){
											Intent intent = new Intent(UploadHistoryActivity.this, DynamicFormActivity.class);
											intent.putExtra("funcJsonStr", funcJsonStr);
											intent.putExtra("formflag", "brow");
											intent.putExtra("lsh", lsh);
											startActivity(intent);
										}else if(which == 1){

											Intent intent = new Intent( Intent.ACTION_VIEW );
											intent.setData( Uri.parse( url)); //这里面是需要调转的url
											intent = Intent.createChooser( intent, null);
										    startActivity( intent);
//											Intent intent = new Intent(UploadHistoryActivity.this, WebActivity.class);
//											intent.putExtra("webAddress", url);
//											startActivity(intent);
											
										}
									}
									
								});
								browDialogbuilder.create().show();
							//编辑表单
							}else if(opText.equals(getResources().getString(R.string.edit))){
								Intent intent = new Intent(UploadHistoryActivity.this, DynamicFormActivity.class);
								intent.putExtra("editFormUrl", editFormUrl);
								intent.putExtra("funcJsonStr", funcJsonStr);
								intent.putExtra("formflag", "edit");
								intent.putExtra("lsh", lsh);
								startActivityForResult(intent, EDIT_FORM);
							//删除表单
							}else if(opText.equals(getResources().getString(R.string.delete))){
								AlertDialog.Builder deleteFormbuilder = new AlertDialog.Builder(UploadHistoryActivity.this);
								deleteFormbuilder.setTitle(R.string.prompt);
								deleteFormbuilder.setMessage(R.string.isDeleteFormData);
								//删除表单数据按钮点击事件
								deleteFormbuilder.setPositiveButton(R.string.delete, new OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										String url = deleteFormUrl + "&lsh=" + lsh;
										//刷新删除后列表
										ArrayList historyDataList = connServiceThread.getResultList();
										historyDataList.remove(position);
										SimpleAdapter listAdapter = null;
										if(formtype.equals("Collection"))
											listAdapter = new SimpleAdapter(UploadHistoryActivity.this, historyDataList, R.layout.history_listitem, new String[]{ "time", "userid"}, new int[]{R.id.historyDate, R.id.opPerson});
										else if(formtype.equals("market"))
											listAdapter = new SimpleAdapter(UploadHistoryActivity.this, historyDataList, R.layout.market_listitem, new String[]{ "name", "time"}, new int[]{R.id.marketName, R.id.createTime});
										
								        //将ListView和数据适配器关联
								        historyListView.setAdapter(listAdapter);
										//显示等待dialog
							            ProgressDialog pDialog = new ProgressDialog(UploadHistoryActivity.this);
							            pDialog.setMessage(getResources().getString(R.string.isDeleteFormData));
							            pDialog.show();
							    		//获取到服务端流程名称数据，并装载数据
							    		HelpHandler2 helpHandler2 = new HelpHandler2(pDialog,
							    				UploadHistoryActivity.this);
							    		//启动请求服务端线程，封装数据给handler
							    		ConnectionServiceThread2 connServiceThread2 = new ConnectionServiceThread2(UploadHistoryActivity.this, 21, helpHandler2, url);
							    		connServiceThread2.start();
									}
								});
								//取消删除按钮点击事件
								deleteFormbuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
	
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
									}
								});
								deleteFormbuilder.create().show();
							}
						}
					});
					operateDialogbuilder.create().show();
				}
			}
		});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if(requestCode == EDIT_FORM){
    		if(resultCode == RESULT_OK){
    			String params = formListUrl + ";" + formtype;
    	        //显示等待dialog
    	        ProgressDialog pDialog = ProgressDialog.show(
    					this, 
    					getResources().getString(R.string.nowloading), 
    					getResources().getString(R.string.pleasewait), 
    					true, true);	

    			//获取到服务端流程名称数据，并装载数据
    			HelpHandler helpHandler = new HelpHandler(pDialog,
    					this);
    			//启动请求服务端线程，封装数据给handler
    			connServiceThread = new ConnectionServiceThread(this, 15, helpHandler, params);
    			connServiceThread.start();
    		}
    	}
    }
}
