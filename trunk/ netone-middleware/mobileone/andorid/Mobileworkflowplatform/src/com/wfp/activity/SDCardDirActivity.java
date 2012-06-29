package com.wfp.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.wfp.util.FunctionUtil;

public class SDCardDirActivity extends Activity {
	
	//界面元素初始化
	private ListView sdcardDirListView;
	
	//参数
	private ArrayList dirStackList;		//目录栈
	private String formPath;	//表单附件目录
	private String formlsh;		//表单lsh
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.sdcarddir);
        
        //界面元素初始化
        sdcardDirListView = (ListView) findViewById(R.id.sdcardDirListView);
        
        //界面数据初始化
        Intent intent = getIntent();
        formPath = intent.getStringExtra("formpath");
        formlsh = intent.getStringExtra("formlsh");
        String sdcardRootDir = FunctionUtil.getSDPath();
        dirStackList = new ArrayList();
        dirStackList.add(sdcardRootDir);	//添加目录栈
        //刷新目录列表
        ArrayList sdcardDirList =  FunctionUtil.findFilesList(sdcardRootDir,2);
        SimpleAdapter sdcarDirAdapter = new SimpleAdapter(this, sdcardDirList, R.layout.sdcarddir_listitem, new String[] {"fileimg","filename"}, new int[] {R.id.sdcardFileImg, R.id.sdcardFileName});
        sdcardDirListView.setAdapter(sdcarDirAdapter);
        //目录列表项点击事件
        sdcardDirListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				HashMap map = (HashMap) adapterView.getItemAtPosition(position);
				final String filePath = (String) map.get("filepath");
				final File file = new File(filePath);
				if(file.isFile()){

					AlertDialog.Builder checkFileDialogBuilder = new AlertDialog.Builder(SDCardDirActivity.this);
					checkFileDialogBuilder.setTitle(R.string.prompt);
					checkFileDialogBuilder.setMessage(R.string.isCheckFile);
					checkFileDialogBuilder.setPositiveButton(R.string.select,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
									//显示等待dialog
						            final ProgressDialog pDialog = new ProgressDialog(SDCardDirActivity.this);
						            pDialog.setMessage(getResources().getString(R.string.copyFileToFormDir));
						            pDialog.show();
									Thread thread = new Thread(){
									
										public void run(){
											try {
											FileInputStream inputStream = new FileInputStream(file);	//获取文件流
											String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); 		//当前时间
							            	String[] names = file.getName().split("\\.");
							            	String formFilename = "IMG&" + formlsh + "&" + timeStamp + "." + names[1];
											File copyFile = new File(formPath + "/" + formFilename);
											if(!copyFile.exists())
												copyFile.createNewFile();
											FileOutputStream output = new FileOutputStream(copyFile);
											byte buffer [] = new byte[2];
											while((inputStream.read(buffer)) != -1){
												output.write(buffer);
											}
											output.flush();
											pDialog.dismiss();
											Intent intent = new Intent();
											intent.putExtra("filename", filePath);
											setResult(RESULT_OK);
											finish();
											} catch (FileNotFoundException e) {
												// TODO Auto-generated catch block
												Toast.makeText(SDCardDirActivity.this, R.string.notFoundFile, Toast.LENGTH_SHORT).show();
												e.printStackTrace();
											} catch (IOException e) {
												// TODO Auto-generated catch block
												Toast.makeText(SDCardDirActivity.this, getResources().getString(R.string.errorcode)+getResources().getString(R.string.fileStraficError), Toast.LENGTH_SHORT).show();
												e.printStackTrace();
											}
										}
									};
									thread.start();
								}
							});
					checkFileDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					AlertDialog checkFileDialog = checkFileDialogBuilder.create();
					checkFileDialog.show();
				}else if(file.isDirectory()){
					dirStackList.add(filePath);		//添加目录栈
					//刷新目录列表
					sdcardDirListView.setVisibility(View.GONE);
			        ArrayList sdcardDirList =  FunctionUtil.findFilesList(filePath,2);
			        
			        SimpleAdapter sdcarDirAdapter = new SimpleAdapter(SDCardDirActivity.this, sdcardDirList, R.layout.sdcarddir_listitem, new String[] {"fileimg","filename"}, new int[] {R.id.sdcardFileImg, R.id.sdcardFileName});
			        sdcardDirListView.setAdapter(sdcarDirAdapter);
			        sdcardDirListView.setAnimation(AnimationUtils.loadAnimation(SDCardDirActivity.this, R.anim.fade_in));
			        sdcardDirListView.setVisibility(View.VISIBLE);
				}
			}
		});
    }
    
    @SuppressWarnings("static-access")
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	if(keyCode == event.KEYCODE_BACK){
    		if(dirStackList.size() != 1){
    			dirStackList.remove(dirStackList.size()-1);
    			//刷新目录列表
				sdcardDirListView.setVisibility(View.GONE);
		        ArrayList sdcardDirList =  FunctionUtil.findFilesList((String)dirStackList.get(dirStackList.size()-1),2);
		        
		        SimpleAdapter sdcarDirAdapter = new SimpleAdapter(SDCardDirActivity.this, sdcardDirList, R.layout.sdcarddir_listitem, new String[] {"fileimg","filename"}, new int[] {R.id.sdcardFileImg, R.id.sdcardFileName});
		        sdcardDirListView.setAdapter(sdcarDirAdapter);
		        sdcardDirListView.setAnimation(AnimationUtils.loadAnimation(SDCardDirActivity.this, R.anim.fade_in));
		        sdcardDirListView.setVisibility(View.VISIBLE);
    		}else{
    			finish();
    		}
    		
    	}
    	return true;
    }
}
