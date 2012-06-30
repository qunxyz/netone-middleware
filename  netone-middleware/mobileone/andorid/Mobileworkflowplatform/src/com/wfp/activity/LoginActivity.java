package com.wfp.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.wfp.util.ConnectionServiceThread;
import com.wfp.util.ConnectionServiceThread2;
import com.wfp.util.FunctionUtil;
import com.wfp.util.HelpHandler;
import com.wfp.util.HelpHandler2;

public class LoginActivity extends Activity {
	
	//界面元素
	private AutoCompleteTextView userNameEdit;
	private EditText passWordEdit;
	private ImageButton loginBt;
	//private ImageButton registerBt;	//注册按钮
	private RadioButton userInfoLogin;
	private RadioButton phoneIdLogin;

	
	//参数
	private int loginType;
	private static String SERVICE_INFO = "SERVICEINFO";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.RGBA_8888);	//防止PNG图片失真
        setContentView(R.layout.login);
        //初始化
        initData();
        
        //界面元素初始化
        userNameEdit = (AutoCompleteTextView) findViewById(R.id.userNameEdit);
        passWordEdit = (EditText) findViewById(R.id.passwordEdit);
        loginBt = (ImageButton) findViewById(R.id.loginBt);
        //registerBt = (ImageButton) findViewById(R.id.registerBt);		注册按钮
        userInfoLogin = (RadioButton) findViewById(R.id.userInfoLogin);
        phoneIdLogin = (RadioButton) findViewById(R.id.phoneIdLogin);
        //账户限制字符过滤
        userNameEdit.setFilters(new InputFilter[] { new InputFilter() {
		    @Override
		    public CharSequence filter(CharSequence source, int start,
		           int end, Spanned dest, int dstart, int dend) {
		    	//Toast.makeText(LoginActivity.this, source, Toast.LENGTH_SHORT).show();
		    	String limitStr = "qwertyuiopasdfghjklzxcvbnm1234567890";	//允许输入的字符
		    	String subStr = String.valueOf(source);	//截取当前输入字符
		    	//判断输入字符是否为允许输入字符
		    	if(limitStr.indexOf(subStr) < 0){
		    		source = "";
		    	}
		        return source;
		    }
		}});


        //用户名列表集
        final ArrayList userNameList = new ArrayList();
        
        loginBt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(loginType == 1) {	//用户信息登录
					String userName = userNameEdit.getText().toString().trim();
					String passWord = passWordEdit.getText().toString().trim();
					
					//输入的用户名加入提示
					
					userNameList.add(userName);
					ArrayAdapter adapter = new ArrayAdapter(LoginActivity.this, //定义匹配源的adapter
		                android.R.layout.simple_dropdown_item_1line, userNameList);
					userNameEdit.setAdapter(adapter);     //设置 匹配源的adapter 到 AutoCompleteTextView控件
					
					//判断用户名是否为空
					if(userName.equals("")){
						Toast.makeText(LoginActivity.this, R.string.emputUsername, Toast.LENGTH_LONG).show();
						return;
					}
					//判断密码是否为空
					if(passWord.equals("")){
						Toast.makeText(LoginActivity.this, R.string.emputPsd, Toast.LENGTH_LONG).show();
						return;
					}
					String userInfo = userName+ ";" + passWord;
					
					//显示等待dialog
		            ProgressDialog pDialog = new ProgressDialog(LoginActivity.this);
		            pDialog.setMessage(getResources().getString(R.string.nowLogin));
		            pDialog.show();
		    		//获取到服务端流程名称数据，并装载数据
		    		HelpHandler2 helpHandler2 = new HelpHandler2(pDialog,
		    				LoginActivity.this);
		    		//启动请求服务端线程，封装数据给handler
		    		ConnectionServiceThread2 connServiceThread2 = new ConnectionServiceThread2(LoginActivity.this, 3, helpHandler2, userInfo);
		    		connServiceThread2.start();
				}else{	//IMEI码登录
					
					//显示等待dialog
		            ProgressDialog pDialog = new ProgressDialog(LoginActivity.this);
		            pDialog.setMessage(getResources().getString(R.string.nowLogin));
		            pDialog.show();
		    		//获取到服务端流程名称数据，并装载数据
		    		HelpHandler2 helpHandler2 = new HelpHandler2(pDialog,
		    				LoginActivity.this);
		    		//启动请求服务端线程，封装数据给handler
		    		ConnectionServiceThread2 connServiceThread2 = new ConnectionServiceThread2(LoginActivity.this, 3, helpHandler2, "IMEI");
		    		connServiceThread2.start();
				}
			}
		});
        
        //注册界面跳转点击事件
//        registerBt.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent  = new Intent(LoginActivity.this, RegisterActivity.class);
//				startActivity(intent);
//			}
//		});
        //用户信息登录选择事件
        userInfoLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loginType = 1;
				userNameEdit.setVisibility(View.VISIBLE);
				passWordEdit.setVisibility(View.VISIBLE);
			}
		});
        
        //手机IMEI码登录选择事件
        phoneIdLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loginType = 2;
				userNameEdit.setVisibility(View.GONE);
				passWordEdit.setVisibility(View.GONE);
				
			}
		});
    }
    
    /**
     * 初始化服务端图片以及检测sd卡与网络
     */
    private void initData(){
    	
    	//获取当前网络连接状态
		ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //3G网络与wifi状态
		String mobile3GState = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState().toString();
		String wifiState = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState().toString();
		//检测3G网络或WIFI是否开启
		if(!mobile3GState.equals("CONNECTED") && !wifiState.equals("CONNECTED")){

			new AlertDialog.Builder(this)
			.setMessage(getResources().getString(R.string.noNetWork))
			.setPositiveButton(R.string.networkSetting,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							
							startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));//进入无线网络配置界面
							finish();
						}
					})
			.setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog,
						int which) {
					// TODO Auto-generated method stub
					finish();
				}}).show();
			return;
		}
		
		//SD卡检测
		if(FunctionUtil.getSDPath() == null){
    		Toast.makeText(this, R.string.noSDcardExists, Toast.LENGTH_SHORT).show();
    		finish();
    		return;
    	}
       
        loginType = 1;	 //登录类型初始化

		//服务端IP初始化
        TelephonyManager telephonyManager;
        //使用SharedPreferences存储
        SharedPreferences serviceInfo = getSharedPreferences(SERVICE_INFO,0);
        Editor editor = serviceInfo.edit();
        editor.putString("serviceip", "http://112.5.5.114:84");
        editor.commit();
    
    }
}
