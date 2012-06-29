package com.wfp.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;

import com.wfp.config.PathConfig;
import com.wfp.customcontrols.WebImageListAdapter;
import com.wfp.util.ConnectionServiceThread;
import com.wfp.util.FunctionUtil;
import com.wfp.util.HelpHandler;

public class OrdersUploadImgActivity extends TabActivity {

	//界面视图元素
	private GridView unuploadGridView;
	private ListView uploadListView;
	private LinearLayout unuploadFile_layout;
	private LinearLayout uploadFile_layout;
	private AlertDialog imgOperateDialog;
	private AlertDialog uploadpbDialog;
	private AlertDialog menuDialog; // menu菜单Dialog
	private View menuView;
	private GridView menuGrid;
	

	//参数
	private File file;
	private static int CHECK_FILE = 1;		//与sdcard目录界面的切换表标记
	private int fileSize;
	private int downLoadFileSize;
	private ConnectionServiceThread connServiceThread;
	public String imgRootPath;	//文件夹SD卡路径
	private String tabid = "unupload";	//	tab标签id
	private String uploadFileUrl;	//上传附件服务端url
	private String lsh;
	private String formlsh;
	private ArrayList formDirStackList;		//表单附件目录栈
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);	//防止PNG图片失真
        setTitle(R.string.orderUploadImg);
        

        
        //初始化数据
        Intent intent = getIntent();
//        lsh = intent.getStringExtra("lsh");
//        formlsh = intent.getStringExtra("formlsh");
//        if(formlsh == null)
//        	formlsh = "null"; 	
//        String formAppName = intent.getStringExtra("formAppName");
//        final String formFlag = intent.getStringExtra("formflag");
        uploadFileUrl = intent.getStringExtra("uploadFileUrl");
        imgRootPath = intent.getStringExtra("imgRootPath");
        formDirStackList = new ArrayList();		//初始化表单附件目录栈
        
        
		TabHost uploadTypeTab = this.getTabHost();
		LayoutInflater.from(this).inflate(R.layout.ordersuploadimg,
				uploadTypeTab.getTabContentView(), true);
		uploadTypeTab.setBackgroundResource(R.drawable.darkgray);
		uploadTypeTab.setScrollContainer(true);
		uploadTypeTab.addTab(uploadTypeTab.newTabSpec("unupload")// 未上传附件标签
				.setIndicator(getResources().getString(R.string.unUploadFile),
						getResources().getDrawable(R.drawable.uploadfiletab)).setContent(R.id.unuploadFile_layout));
//		uploadTypeTab.addTab(uploadTypeTab.newTabSpec("upload")// 已上传附件标签
//				.setIndicator(getResources().getString(R.string.uploadFile),
//						getResources().getDrawable(R.drawable.uploadfiletab)).setContent(R.id.uploadFile_layout));
		uploadTypeTab.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				if(tabId.equals("unupload")){
					tabid = "unupload";
				}else if(tabId.equals("upload")){
					tabid = "upload";
					//显示等待dialog
		            ProgressDialog pDialog = new ProgressDialog(OrdersUploadImgActivity.this);
		            pDialog.setMessage(getResources().getString(R.string.getPicture));
		            pDialog.show();
		    		//获取到服务端流程名称数据，并装载数据
		    		HelpHandler helpHandler = new HelpHandler(pDialog,
		    				OrdersUploadImgActivity.this);
		    		//启动请求服务端线程，封装数据给handler
		    		connServiceThread = new ConnectionServiceThread(OrdersUploadImgActivity.this, 24, helpHandler, PathConfig.uploadFilesInfoUrlStr+";"+formlsh);
		    		connServiceThread.start();
				}
			}
		});
        Log.i("imageGridView", "imageGridView");
        //初始化元素界面
        unuploadFile_layout = (LinearLayout) findViewById(R.id.unuploadFile_layout);
        uploadFile_layout = (LinearLayout) findViewById(R.id.uploadFile_layout);
        unuploadGridView = (GridView) findViewById(R.id.unuploadGridView);
        uploadListView = (ListView) findViewById(R.id.uploadListView);
        
        
        Log.i("imgRootPath", imgRootPath);
        formDirStackList.add(imgRootPath);	//添加目录栈
        
        // 设置自定义menu菜单布局
		menuView = View.inflate(this, R.layout.gridview_menu, null);
		menuGrid = (GridView) menuView.findViewById(R.id.menu_gridview);
        //菜单图片 
		//添加手机附件 	R.drawable.menu_zoommode,
    	int[] menu_image_array = { 
    			R.drawable.menu_bookmark_sync_sync, R.drawable.menu_delete_file,
    			R.drawable.menu_syssettings, R.drawable.menu_return };
    	//菜单文字
    	//添加手机附件 getResources().getString(R.string.insertFile),
    	String[] menu_name_array = { 
    			getResources().getString(R.string.uploadAllFile),
    			getResources().getString(R.string.deleteAllFile), 
    			getResources().getString(R.string.systemsetting), 
    			getResources().getString(R.string.exit) };
    	//构建菜单
		menuDialog = FunctionUtil.initMenu(this, menuView, menuGrid, menu_name_array, menu_image_array);
		//菜单项点击事件
		menuGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				menuDialog.dismiss();
				HashMap map = (HashMap) adapterView.getItemAtPosition(position);
				String menuItemName =  map.get("menuItemText").toString();
				if(menuItemName.equals(getResources().getString(R.string.insertFile))){				//添加手机附件
//					Intent intent = new Intent(OrdersUploadImgActivity.this, SDCardDirActivity.class);
//					intent.putExtra("formpath", imgRootPath);
//					intent.putExtra("formlsh", formlsh);
//					startActivityForResult(intent, CHECK_FILE);
				}else if(menuItemName.equals(getResources().getString(R.string.uploadAllFile))){	//上传所有附件
					uploadAllFiles(imgRootPath);
				}else if(menuItemName.equals(getResources().getString(R.string.deleteAllFile))){	//删除所有附件
					AlertDialog.Builder deleteAllFilesDialogBuilder = new AlertDialog.Builder(OrdersUploadImgActivity.this);
					deleteAllFilesDialogBuilder.setTitle(R.string.prompt);
					deleteAllFilesDialogBuilder.setMessage(R.string.isDeleteAllFiles);
					deleteAllFilesDialogBuilder.setPositiveButton(R.string.delete,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
									File formDir = new File(imgRootPath);
									File[] files = formDir.listFiles();
									if(	files.length < 1){
										Toast.makeText(OrdersUploadImgActivity.this, R.string.nofiledelete, Toast.LENGTH_SHORT).show();
										return;
									}
									for (int i = 0; i < files.length; i++) {
										files[i].delete();
									}
								}
							});
					deleteAllFilesDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					AlertDialog deleteAllFilesDialog = deleteAllFilesDialogBuilder.create();
					deleteAllFilesDialog.show();

		    		//获取到服务端流程名称数据，并装载数据
		    		HelpHandler helpHandler = new HelpHandler(null,
		    				OrdersUploadImgActivity.this);
		    		//启动请求服务端线程，封装数据给handler
		    		connServiceThread = new ConnectionServiceThread(OrdersUploadImgActivity.this, 18, helpHandler, imgRootPath);
		    		connServiceThread.start();
				}else if(menuItemName.equals(getResources().getString(R.string.systemsetting))){
					Log.i("menu_syssetting", "menu_syssetting");
					menuDialog.dismiss();
			    	//下载服务端图片
			    	//显示等待dialog
			        ProgressDialog pDialog = new ProgressDialog(OrdersUploadImgActivity.this);
			        pDialog.setMessage(getResources().getString(R.string.initSyatemData));
			        pDialog.show();
					//获取到服务端流程名称数据，并装载数据
					HelpHandler helpHandler = new HelpHandler(pDialog,
							OrdersUploadImgActivity.this);
					//启动请求服务端线程，封装数据给handler
					ConnectionServiceThread connServiceThread = new ConnectionServiceThread(OrdersUploadImgActivity.this, 28, helpHandler, "");
					connServiceThread.start();
				}else if(menuItemName.equals(getResources().getString(R.string.exit))){
					Log.i("menu_exit", "menu_exit");
					finish();
				}
			}
		});

        try {
        	//显示等待dialog
            ProgressDialog pDialog = new ProgressDialog(this);
            pDialog.setMessage(getResources().getString(R.string.getPicture));
            pDialog.show();
    		//获取到服务端流程名称数据，并装载数据
    		HelpHandler helpHandler = new HelpHandler(pDialog,
    				this);
    		//启动请求服务端线程，封装数据给handler
    		connServiceThread = new ConnectionServiceThread(this, 18, helpHandler, imgRootPath);
    		connServiceThread.start();

    		unuploadGridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> adapterView, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					final HashMap map = (HashMap) adapterView.getItemAtPosition(position);
					
					String dirpath = (String) map.get("dirpath");
					if(dirpath != null) {
						formDirStackList.add(dirpath);
						unuploadGridView.setVisibility(View.GONE);
						ArrayList ordersImgList =  FunctionUtil.findFilesList(dirpath,1);
						WebImageListAdapter uploadImgAdapter = new WebImageListAdapter(OrdersUploadImgActivity.this, ordersImgList, R.layout.process_griditem, new String[] {"filepath","filename"}, new int[] {R.id.processImg, R.id.processText});
						unuploadGridView.setAdapter(uploadImgAdapter);
						unuploadGridView.setVisibility(View.VISIBLE);
					} else {
//						if(!formFlag.equals("brow")){
							String[] items = {getResources().getString(R.string.open), getResources().getString(R.string.upload), getResources().getString(R.string.delete)};
							AlertDialog.Builder builder = new AlertDialog.Builder(
									OrdersUploadImgActivity.this);
							builder.setTitle(R.string.operate);
							builder.setItems(items,
									new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog,
												int item) {
											String filepath = (String) map.get("filepath");
											//预览附件
											if(item == 0){
												Intent intent = new Intent(Intent.ACTION_VIEW);    
												intent.setDataAndType(Uri.parse("file://"+filepath), "image/*");    
												startActivity(intent);
											//上传附件
											}else if(item == 1){
												imgOperateDialog.dismiss();
												file = new File(filepath);
												//file = new File(FunctionUtil.getSDPath() + PathConfig.appSDCardPath + "/processes.png");
										        Log.i("tag", "照片文件是否存在："+file);
										        
										        //显示等待dialog
										        AlertDialog.Builder uploadpbDialogBuilder = new AlertDialog.Builder(
														OrdersUploadImgActivity.this);
										        uploadpbDialogBuilder.setTitle(R.string.uploadProcess);
										        uploadpbDialogBuilder.setCancelable(true);
	
										        View pbVIew = View.inflate(OrdersUploadImgActivity.this, R.layout.upload_process_dialog, null);
										        uploadpbDialogBuilder.setView(pbVIew);
										        uploadpbDialog = uploadpbDialogBuilder.create();
										        //显示进度条对话框
										        uploadpbDialog.show();
										        uploadpbDialog.setContentView(R.layout.upload_process_dialog);
										        TextView imageName = (TextView) uploadpbDialog.findViewById(R.id.imageName);
										        TextView titalByte = (TextView) uploadpbDialog.findViewById(R.id.titalByte);
										        //设置上传图片名称和总大小
										        imageName.setText(getResources().getString(R.string.imageName) + file.getName());
										        titalByte.setText("/" + file.length() + getResources().getString(R.string.bytes));
										        
												//获取到服务端流程名称数据，并装载数据
												HelpHandler helpHandler = new HelpHandler(uploadpbDialog,
														OrdersUploadImgActivity.this);
	//											//启动请求服务端线程，封装数据给handler
										        connServiceThread = new ConnectionServiceThread(OrdersUploadImgActivity.this, 17, helpHandler, uploadFileUrl, file);
												connServiceThread.start();
												uploadpbDialog.setOnDismissListener(new OnDismissListener() {
													
													@Override
													public void onDismiss(DialogInterface dialog) {
														// TODO Auto-generated method stub
														connServiceThread.cancelUploadFile();
													}
												});
												
												
											//删除附件
											}else if(item == 2){
												file = new File(filepath);
												if(file.exists())
													file.delete();
												Toast.makeText(OrdersUploadImgActivity.this, R.string.deleteFilesuccess, Toast.LENGTH_SHORT).show();
												//显示等待dialog
//									            ProgressDialog pDialog = new ProgressDialog(OrdersUploadImgActivity.this);
//									            pDialog.setMessage(getResources().getString(R.string.getPicture));
//									            pDialog.show();
									    		//获取到服务端流程名称数据，并装载数据
									    		HelpHandler helpHandler = new HelpHandler(null,
									    				OrdersUploadImgActivity.this);
									    		//启动请求服务端线程，封装数据给handler
									    		connServiceThread = new ConnectionServiceThread(OrdersUploadImgActivity.this, 18, helpHandler, imgRootPath);
									    		connServiceThread.start();
											}
	
										}
									});
	
							imgOperateDialog = builder.create();
							imgOperateDialog.show();
//						}else{	//查看表单时图片只有浏览功能
//							Intent intent = new Intent(Intent.ACTION_VIEW);    
//							intent.setDataAndType(Uri.parse("file://"+filepath), "image/*");    
//							startActivity(intent);
//						}
					}

				}
			});
    		
    		//已上传附件列表项点击事件
    		uploadListView.setOnItemClickListener(new OnItemClickListener() {
    			
				@Override
				public void onItemClick(AdapterView<?> adapterView, View arg1,
						int position, long arg3) {
					// TODO Auto-generated method stub
					HashMap map = (HashMap) adapterView.getItemAtPosition(position);
					final String unid = (String) map.get("unid");
					
//					if(!formFlag.equals("brow")){
						String[] items = {getResources().getString(R.string.open), getResources().getString(R.string.delete)};
						AlertDialog.Builder builder = new AlertDialog.Builder(
								OrdersUploadImgActivity.this);
						builder.setTitle(R.string.operate);
						builder.setItems(items,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int item) {
										//预览附件
										if(item == 0){
											//显示等待dialog
								            ProgressDialog pDialog = new ProgressDialog(OrdersUploadImgActivity.this);
								            pDialog.setMessage(getResources().getString(R.string.loadfile));
								            pDialog.show();
								    		//获取到服务端流程名称数据，并装载数据
								    		HelpHandler helpHandler = new HelpHandler(pDialog,
								    				OrdersUploadImgActivity.this);
								    		//启动请求服务端线程，封装数据给handler
								    		connServiceThread = new ConnectionServiceThread(OrdersUploadImgActivity.this, 25, helpHandler, PathConfig.downLoadFileAddressUrlStr+";"+unid);
								    		connServiceThread.start();
										//删除附件
										}else if(item == 1){
											//显示等待dialog
								            ProgressDialog pDialog = new ProgressDialog(OrdersUploadImgActivity.this);
								            pDialog.setMessage(getResources().getString(R.string.loadfile));
								            pDialog.show();
								    		//获取到服务端流程名称数据，并装载数据
								    		HelpHandler helpHandler = new HelpHandler(pDialog,
								    				OrdersUploadImgActivity.this);
								    		//启动请求服务端线程，封装数据给handler
								    		connServiceThread = new ConnectionServiceThread(OrdersUploadImgActivity.this, 26, helpHandler, PathConfig.deleteFileUrlStr+";"+unid);
								    		connServiceThread.start();
								    		
								    		//显示等待dialog
								            ProgressDialog pDialog2 = new ProgressDialog(OrdersUploadImgActivity.this);
								            pDialog.setMessage(getResources().getString(R.string.getPicture));
								            pDialog.show();
								    		//获取到服务端流程名称数据，并装载数据
								    		HelpHandler helpHandler2 = new HelpHandler(pDialog2,
								    				OrdersUploadImgActivity.this);
								    		//启动请求服务端线程，封装数据给handler
								    		connServiceThread = new ConnectionServiceThread(OrdersUploadImgActivity.this, 24, helpHandler2, PathConfig.uploadFilesInfoUrlStr+";"+formlsh);
								    		connServiceThread.start();
										}
									}
						});
						builder.create().show();
//					}else{
//						//显示等待dialog
//			            ProgressDialog pDialog = new ProgressDialog(OrdersUploadImgActivity.this);
//			            pDialog.setMessage(getResources().getString(R.string.loadfile));
//			            pDialog.show();
//			    		//获取到服务端流程名称数据，并装载数据
//			    		HelpHandler helpHandler = new HelpHandler(pDialog,
//			    				OrdersUploadImgActivity.this);
//			    		//启动请求服务端线程，封装数据给handler
//			    		connServiceThread = new ConnectionServiceThread(OrdersUploadImgActivity.this, 25, helpHandler, PathConfig.downLoadFileAddressUrlStr+";"+unid);
//			    		connServiceThread.start();
//					}
				}
			});
        }  catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, 
					getResources().getString(R.string.errorcode)+e.getMessage(), 
					Toast.LENGTH_LONG).show();
		}
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	if(keyCode == event.KEYCODE_BACK){
			if(formDirStackList.size() != 1){
				formDirStackList.remove(formDirStackList.size()-1);
				//刷新目录列表
				unuploadGridView.setVisibility(View.GONE);
		        ArrayList sdcardDirList =  FunctionUtil.findFilesList((String)formDirStackList.get(formDirStackList.size()-1),1);
		        
		        WebImageListAdapter uploadImgAdapter = new WebImageListAdapter(OrdersUploadImgActivity.this, sdcardDirList, R.layout.process_griditem, new String[] {"filepath","filename"}, new int[] {R.id.processImg, R.id.processText});
		        unuploadGridView.setAdapter(uploadImgAdapter);
		        unuploadGridView.setAnimation(AnimationUtils.loadAnimation(OrdersUploadImgActivity.this, R.anim.fade_in));
		        unuploadGridView.setVisibility(View.VISIBLE);
			}else{
				finish();
			}
			return true;
    	}else
    		return false;
    	
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
		menuDialog.show();
		return false;// 返回为true 则显示系统menu
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == CHECK_FILE){
			Log.i("checkfile", "checkfile");
			if(resultCode == RESULT_OK){
				Log.i("checkfileok", "checkfileok");
				//显示等待dialog
	            ProgressDialog pDialog = new ProgressDialog(this);
	            pDialog.setMessage(getResources().getString(R.string.getPicture));
	            pDialog.show();
	    		//获取到服务端流程名称数据，并装载数据
	    		HelpHandler helpHandler = new HelpHandler(pDialog,
	    				this);
	    		//启动请求服务端线程，封装数据给handler
	    		connServiceThread = new ConnectionServiceThread(this, 18, helpHandler, imgRootPath);
	    		connServiceThread.start();
			}
		}
	}
	
	/**
	 * 上传当前表单附件目录中所有文件
	 * 
	 * @param formPath 当前表单目录
	 */
	private void uploadAllFiles(String formPath){
		File formDir = new File(formPath);
		File[] files = formDir.listFiles();
		if(files.length > 0){
			for (int i = 0; i < files.length; i++) {
				if(files[i].isDirectory()){
					uploadAllFiles(files[i].getAbsolutePath());
				}else{
					file = new File(files[i].getAbsolutePath());
					//file = new File(FunctionUtil.getSDPath() + PathConfig.appSDCardPath + "/processes.png");
			        Log.i("tag", "照片文件是否存在："+file);
			        
			        //显示等待dialog
			        AlertDialog.Builder uploadpbDialogBuilder = new AlertDialog.Builder(
							OrdersUploadImgActivity.this);
			        uploadpbDialogBuilder.setTitle(R.string.uploadProcess);
			        uploadpbDialogBuilder.setCancelable(true);
	
			        View pbVIew = View.inflate(OrdersUploadImgActivity.this, R.layout.upload_process_dialog, null);
			        uploadpbDialogBuilder.setView(pbVIew);
			        uploadpbDialog = uploadpbDialogBuilder.create();
			        //显示进度条对话框
			        uploadpbDialog.show();
			        uploadpbDialog.setContentView(R.layout.upload_process_dialog);
			        TextView imageName = (TextView) uploadpbDialog.findViewById(R.id.imageName);
			        TextView titalByte = (TextView) uploadpbDialog.findViewById(R.id.titalByte);
			        //设置上传图片名称和总大小
			        imageName.setText(getResources().getString(R.string.imageName) + files[i].getName());
			        titalByte.setText("/" + files[i].length() + getResources().getString(R.string.bytes));
			        
					//获取到服务端流程名称数据，并装载数据
					HelpHandler helpHandler = new HelpHandler(uploadpbDialog,
							OrdersUploadImgActivity.this);
	//				//启动请求服务端线程，封装数据给handler
			        connServiceThread = new ConnectionServiceThread(OrdersUploadImgActivity.this, 17, helpHandler, uploadFileUrl, files[i]);
					connServiceThread.start();
					uploadpbDialog.setOnDismissListener(new OnDismissListener() {
						
						@Override
						public void onDismiss(DialogInterface dialog) {
							// TODO Auto-generated method stub
							connServiceThread.cancelUploadFile();
						}
					});
				}
			}
		}else{
			Toast.makeText(this, R.string.nofileupload, Toast.LENGTH_LONG).show();
		}
	}

}
