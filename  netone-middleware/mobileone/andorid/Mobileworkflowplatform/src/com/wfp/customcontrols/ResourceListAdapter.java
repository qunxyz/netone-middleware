package com.wfp.customcontrols;


import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wfp.activity.OrdersUploadImgActivity;
import com.wfp.activity.R;
import com.wfp.activity.UploadHistoryActivity;
import com.wfp.activity.WorkOrdersActivity;
import com.wfp.config.PathConfig;
import com.wfp.util.ConnectionServiceThread;
import com.wfp.util.ConnectionServiceThread2;
import com.wfp.util.FunctionUtil;
import com.wfp.util.HelpHandler;
import com.wfp.util.HelpHandler2;

public class ResourceListAdapter extends BaseAdapter {

	// 数据源
	private List<HashMap<String, String>> list;
	private Context context;
	private int layout;
	
	//参数
    private double latitude=0.0;  	//经度
    private double longitude =0.0;  //维度
    private static String SHARED_USERINFO = "userinfo";		//账户信息key
    private static String SERVICE_INFO = "SERVICEINFO";		//服务端信息key
    private static String openFuncid = "00";		//打开功能编号
    private static String photographFuncid = "01";	//拍照功能编号
    private static String uploadFileFuncid = "02";	//附件管理功能编号
    private static String addFormFuncid = "25";		//新建表单数据功能编号
    private static String updateFormFuncid = "26";	//修改表单数据功能编号
    private static String queryFormFuncid = "27";	//查询表单数据功能编号
    private static String deleteFormFuncid = "28";	//删除表单数据功能编号
    private static String loadFormFuncid = "29";	//装载表单结构功能编号
    
    private static String ordersAppid = "5";		//工单管理应用编号
    private static String formAppcid = "16";		//表单应用编号
    
    private String formListUrl;
    private String editFormUrl;
    private String queryFormUrl;
    private String deleteFormUrl;
    private String photographUrl;
    private String uploadFileUrl;
    private String loadFormUrl;
    private String addFormUrl;

	// 构造函数
	public ResourceListAdapter(Context context,
			List<HashMap<String, String>> list, int layout) {
		this.context = context;
		this.list = list;
		this.layout = layout;
		
	    formListUrl = "";
	    editFormUrl = "";
	    queryFormUrl = "";
	    deleteFormUrl = "";
	    loadFormUrl = "";
	    addFormUrl = "";
	    photographUrl = "";
	    uploadFileUrl = "";
	    
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater mInflater = LayoutInflater.from(context);
		// 产生一个View
		View view  = mInflater.inflate(layout, null);
		ImageView imageView = null;
		TextView testView = null;

		String processName = list.get(position).get("resourcename");
		String distinguish = list.get(position).get("distinguish");
		String imagename = list.get(position).get("imagename");
		if(distinguish.equals("0")){
			//应用文件图标
			imageView = (ImageView) view.findViewById(R.id.pDetailImg);

			Bitmap bitmap = BitmapFactory.decodeFile(imagename);
			imageView.setImageBitmap(bitmap);
			//应用文件名称
			testView = (TextView) view.findViewById(R.id.pDetailText);
			testView.setText(processName);
			final String types = list.get(position).get("types");
			final String extendattribute=list.get(position).get("extendattribute");

			final String funcJsonStr = extendattribute.split("\\$")[1];
	
			try {

				LinearLayout fileFuncitonBt_layout = (LinearLayout) view.findViewById(R.id.fileFuncitonBt_layout);
				fileFuncitonBt_layout.removeAllViews();
				//JSONArray jsonArray = new JSONArray(funcJsonStr);
				final JSONObject jsonObj = new JSONObject(funcJsonStr);

					for (Iterator iterator = jsonObj.keys(); iterator.hasNext();) {
						final String fieldName = (String) iterator.next();
						Log.i("key", fieldName);
						if(fieldName.equals(openFuncid)){
							
							//列表项点击事件
							view.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									String[] extendattributeArray = extendattribute.split(";");
									//工单应用
									if(types.equals(ordersAppid)){

												Intent intent = new Intent(context, WorkOrdersActivity.class);
												intent.putExtra("funcJsonStr", funcJsonStr);
												context.startActivity(intent);
//											}else{
//												if(i == extendattributeArray.length-1)
//													Toast.makeText(context, R.string.noProcessNaturalName, Toast.LENGTH_LONG).show();
//											}

									//表单应用
									}else if(types.equals(formAppcid)){
										String formListUrl = "";
										try {
											formListUrl = FunctionUtil.getFuncUrl(jsonObj, openFuncid);
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										Intent intent = new Intent(context, UploadHistoryActivity.class);
										intent.putExtra("formListUrl", formListUrl);
										intent.putExtra("funcJsonStr", funcJsonStr);
										if(formListUrl.indexOf("SHANGCHAOXINXI") >= 0)
											intent.putExtra("formtype", "market");
										else if(formListUrl.indexOf("MBJC") >= 0)
											intent.putExtra("formtype", "Collection");
										context.startActivity(intent);
									}
								}
							});
						}else if(fieldName.equals(photographFuncid)){
							ImageView photographBt = new ImageButton(view.getContext());
							LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(35, 35);
							layoutParams.setMargins(0, 0, 12, 0);
							photographBt.setLayoutParams(layoutParams);
							photographBt.setBackgroundResource(R.drawable.uploadphonebt);
							fileFuncitonBt_layout.addView(photographBt);
							//拍照按钮点击事件
							photographBt.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Toast.makeText(context, R.string.openCamera, Toast.LENGTH_SHORT).show();
									//获取当前经纬度
							        double[] LonlatArray = FunctionUtil.getLonlatValue(context);
									Log.i("latitude  longitude ", LonlatArray[1] +"  "+LonlatArray[0]);

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
									uploadInfo.append(LonlatArray[0]);
									uploadInfo.append("&");
									uploadInfo.append(LonlatArray[1]);
									uploadInfo.append("&");
									uploadInfo.append("");
									uploadInfo.append("&");
									uploadInfo.append("form");
									uploadInfo.append("&");
									uploadInfo.append("add");
									//获取到服务端流程名称数据，加载下拉列表
									HelpHandler helpHandler = new HelpHandler(null,
											(Activity)context);
									//获取流程名称，服务端线程连接
									ConnectionServiceThread connServiceThread = new ConnectionServiceThread((Activity)context, 13, helpHandler, uploadInfo.toString());
									connServiceThread.start();
								}
							});
						//附件管理入口
						}else if(fieldName.equals(uploadFileFuncid)){
							
							ImageView uploadFileBt = new ImageButton(view.getContext());
							LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(35, 35);
							layoutParams.setMargins(0, 0, 12, 0);
							uploadFileBt.setLayoutParams(layoutParams);
							uploadFileBt.setBackgroundResource(R.drawable.uploadimagebt);
							fileFuncitonBt_layout.addView(uploadFileBt);
							
							uploadFileBt.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									Toast.makeText(context, "附件管理", Toast.LENGTH_SHORT).show();
									try {

										String uploadFileUrl = FunctionUtil.getFuncUrl(jsonObj, fieldName);
	
										File file = null;
										Intent intent = new Intent(context, OrdersUploadImgActivity.class);
										//表单应用
										if(types.equals(formAppcid)){
											//
											String url = FunctionUtil.getFuncUrl(jsonObj, openFuncid);
											String appName = url.split("=")[url.split("=").length-1];
											String imgRootPath = FunctionUtil.getSDPath()+PathConfig.appSDCardPath+PathConfig.uploadImgPath + PathConfig.formImgPath + "/" + appName;
											Log.i("formfilepath", imgRootPath);
											file = new File(imgRootPath);
											intent.putExtra("formAppName", appName);
											intent.putExtra("uploadFileUrl", uploadFileUrl);
											intent.putExtra("imgRootPath", imgRootPath);
										//工单应用
										}else if(types.equals(ordersAppid)){
											
										}

										if(file.isDirectory()) {

											context.startActivity(intent);
										}else {
											Toast.makeText(context, 
													R.string.notFindOrderImg, 
													Toast.LENGTH_LONG).show();
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
	
								}
							});
						//新增功能
						}else if(fieldName.equals(addFormFuncid)){
							ImageView addFormBt = new ImageButton(view.getContext());
							LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(35, 35);
							layoutParams.setMargins(0, 0, 12, 0);
							addFormBt.setLayoutParams(layoutParams);
							addFormBt.setBackgroundResource(R.drawable.addbt);
							fileFuncitonBt_layout.addView(addFormBt);
							//新增表单按钮点击事件
							addFormBt.setOnClickListener(new OnClickListener() {
								
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
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
									ProgressDialog pDialog = ProgressDialog.show(context, context.getResources()
											.getString(R.string.nowloading), context.getResources().getString(
											R.string.pleasewait), true, true);

									// 获取到服务端流程名称数据，并装载数据
									HelpHandler2 helpHandler2 = new HelpHandler2(pDialog, (Activity) context);
									// 启动请求服务端线程，封装数据给handler
									ConnectionServiceThread2 connServiceThread2 = new ConnectionServiceThread2(
											(Activity) context, 22, helpHandler2, uploadInfo.toString());
									connServiceThread2.start();
									

								}
							});
						}
	
					}
	
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(distinguish.equals("1")){
			//资源文件夹图标
			imageView = (ImageView) view.findViewById(R.id.processImg);
			Bitmap bitmap = BitmapFactory.decodeFile(FunctionUtil.getSDPath() + PathConfig.appSDCardPath + "/processes.png");
			imageView.setImageBitmap(bitmap);
			//应用文件名称
			testView = (TextView) view.findViewById(R.id.processText);
			testView.setText(processName);
			

		}else
			return view;

		return view;
	}
	


}