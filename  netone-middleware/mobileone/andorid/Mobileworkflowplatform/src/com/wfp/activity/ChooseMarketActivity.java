package com.wfp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import com.wfp.config.PathConfig;
import com.wfp.util.ConnectionServiceThread2;
import com.wfp.util.FunctionUtil;
import com.wfp.util.HelpHandler2;

public class ChooseMarketActivity extends Activity {
	
	//参数
	private double latitude=0.0;  	//经度
    private double longitude =0.0;  //维度
    private static String SYS_PARAMS = "SystemParams";
    private static int CHOOSE_MARKET = 12;
    private static String addFormFuncid = "25";		//新建表单数据功能编号
    private static String radiusMarketFuncid = "30";	//搜索半径内网点超市
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.choosemarket);
        
        //界面数据初始化
        Intent intent = getIntent();
        String radiusMarketUrl = intent.getStringExtra("radiusMarketUrl");
        String addFormUrl = intent.getStringExtra("addFormUrl");
        
        //获取当前经纬度
        double[] LonlatArray = FunctionUtil.getLonlatValue(this);
		//shared获取搜索半径
		SharedPreferences sysParamsShared = getSharedPreferences(SYS_PARAMS,0);
		String radiusValue = sysParamsShared.getString("radiusValue", "200");
        String flag = radiusMarketUrl + "&" + LonlatArray[0] + "&" + LonlatArray[1] + "&" + radiusValue + "&" + addFormUrl;
		
        //显示等待dialog
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getString(R.string.searchRangeMarket));
        pDialog.setOnCancelListener(new OnCancelListener() {
			
			@Override
			public void onCancel(DialogInterface dialog) {
				// TODO Auto-generated method stub
				finish();
			}
		});
        pDialog.show();
		//获取到服务端流程名称数据，并装载数据
		HelpHandler2 helpHandler2 = new HelpHandler2(pDialog,
				this);
		//启动请求服务端线程，封装数据给handler
		ConnectionServiceThread2 connServiceThread = new ConnectionServiceThread2(this, 1, helpHandler2, flag);
		connServiceThread.start();
        
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	if(keyCode == event.KEYCODE_BACK){

			finish();
			return true;
    	}else
    		return false;
    	
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if(requestCode == CHOOSE_MARKET){
    		if(resultCode == RESULT_CANCELED)
    			finish();
    	}
    		
    }
}
