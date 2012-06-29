package com.wfp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;

public class WebActivity extends Activity {
	
	private WebView webView;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.webview);
        Log.i("webactivity", "webactivity");
        
        //初始化界面元素
        webView = (WebView) findViewById(R.id.webView);
        //初始化界面数据
        Intent intent = getIntent();
        String fileAddress = intent.getStringExtra("webAddress");
        //wenview初始化设置
        webView.setInitialScale(25);//为25%，最小缩放等级 
        //设置双击缩放网页
       
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);//支持js

      //加载网页
        webView.loadUrl(fileAddress);
        

    }
}
