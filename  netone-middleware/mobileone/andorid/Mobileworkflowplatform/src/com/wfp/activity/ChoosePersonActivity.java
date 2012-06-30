package com.wfp.activity;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.wfp.util.ConnectionServiceThread2;
import com.wfp.util.HelpHandler2;

public class ChoosePersonActivity extends Activity {
	
	//界面元素
	private Button upStep;
	private Button complete;
	private Button cancel;
	private Spinner deadlineSpinner;
	private ListView commiterListView;
	private EditText commiterText;
	private ImageButton resetCommiter;
	
	//参数
	private static String SHARED_USERINFO = "userinfo";		//账户信息key
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.chooseperson);
        
        //界面元素初始化
        upStep = (Button) findViewById(R.id.upStep3);
        complete = (Button) findViewById(R.id.complete);
    	cancel = (Button) findViewById(R.id.cancel3);
    	deadlineSpinner = (Spinner) findViewById(R.id.deadlineSpinner);
    	commiterListView = (ListView) findViewById(R.id.commiterListView);
    	commiterText = (EditText) findViewById(R.id.commiterText);
    	resetCommiter = (ImageButton) findViewById(R.id.resetCommiter);
    	
    	//界面数据初始化
    	Intent intent = getIntent();
    	String suggestion = intent.getStringExtra("suggestion");
    	String commiterUrl = intent.getStringExtra("commiterUrl");
    	final String submitProcessUrl = intent.getStringExtra("submitProcessUrl");
    	final String actid = intent.getStringExtra("actid");

    	
    	
    	//加载人员列表
    	// 显示等待dialog
		ProgressDialog pDialog = ProgressDialog.show(this, getResources()
				.getString(R.string.nowloading), getResources().getString(
				R.string.pleasewait), true, true);

		// 获取到服务端流程名称数据，并装载数据
		HelpHandler2 helpHandler2 = new HelpHandler2(pDialog, this);
		// 启动请求服务端线程，封装数据给handler
		ConnectionServiceThread2 ConnServiceThread2 = new ConnectionServiceThread2(
				this, 31, helpHandler2, commiterUrl+";"+actid);
		ConnServiceThread2.start();
        
		//意见列表加载
		ArrayAdapter deadlineAdapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.deadlinearray));
		deadlineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将Spinner和数据适配器关联
		deadlineSpinner.setAdapter(deadlineAdapter);
		
        //上一步按钮点击事件
		upStep.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setResult(RESULT_OK);
				finish();
			}
		});
		
		//完成按钮点击事件
		complete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String commiterName = commiterText.getText().toString();
				if(commiterName == null || commiterName.equals("")){
					Toast.makeText(ChoosePersonActivity.this, R.string.emptycommiter, Toast.LENGTH_LONG).show();
					return;
				}
				//shared获取用户名
				SharedPreferences userInfo = getSharedPreferences(SHARED_USERINFO,0);
				String clientId = userInfo.getString("userId", "adminx");
				//提交流程
				// 显示等待dialog
				ProgressDialog pDialog = ProgressDialog.show(ChoosePersonActivity.this, getResources()
						.getString(R.string.nowloading), getResources().getString(
						R.string.pleasewait), true, true);
		
				// 获取到服务端流程名称数据，并装载数据
				HelpHandler2 helpHandler2 = new HelpHandler2(pDialog, ChoosePersonActivity.this);
				// 启动请求服务端线程，封装数据给handler
				ConnectionServiceThread2 ConnServiceThread2 = new ConnectionServiceThread2(
						ChoosePersonActivity.this, 2, helpHandler2, submitProcessUrl+";"+actid+";"+clientId);
				ConnServiceThread2.start();
				
				
			}
		});
		
		//取消按钮点击事件
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setResult(RESULT_CANCELED);
				finish();
			}
		});
		//重置处理人按钮点击事件
		resetCommiter.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				commiterText.setText("");
			}
		});
		
		//人员列表选择事件
		commiterListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				HashMap map = (HashMap) adapterView.getItemAtPosition(position);
				String commiter = (String) map.get("participant");
				commiterText.setText(commiter);
			}
		});
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	if(keyCode == event.KEYCODE_BACK){

    		setResult(RESULT_OK);
			finish();
    	}
    	return true;
    }
}
