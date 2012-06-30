package com.wfp.activity;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.wfp.util.ConnectionServiceThread2;
import com.wfp.util.FunctionUtil;
import com.wfp.util.HelpHandler2;

public class NodeSelectionActivity extends Activity {
	
	//界面元素
	private Button upStep;
	private Button downStep;
	private Button cancel;
	private Spinner nodeSpinner;
	private Spinner suggestionSpinner;
	private EditText suggestionText;
	
	//参数
	private static int NODE_TO_CHOOSE = 12;		//节点选择界面到人员选择界面标示符
	private static String downStepNodeFuncid = "30";	//通过审批后选择的节点获取
	private static String upStepNodeFuncid = "31";	//未通过审批后选择的节点获取
	private static String commiterFuncid = "32";		//节点处理人获取
	private static String saveSuggestionFuncid = "33";	//审核意见提交
	private static String submitProcessFuncid = "34";	//当前节点审核提交
	private String downStepNodeUrl;
    private String upStepNodeUrl;
    private String commiterUrl;
    private String saveSuggestionUrl;
    private String submitProcessUrl;
    private String actid;	//选择的节点
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        setContentView(R.layout.nodeselection);
        
        //界面元素初始化
        upStep = (Button) findViewById(R.id.upStep2);
    	downStep = (Button) findViewById(R.id.downStep2);
    	cancel = (Button) findViewById(R.id.cancel2);
    	nodeSpinner = (Spinner) findViewById(R.id.nodeSpinner);
    	suggestionSpinner = (Spinner) findViewById(R.id.suggestionSpinner);
    	suggestionText = (EditText) findViewById(R.id.suggestionText);
        
    	//界面数据初始化
    	Intent intent = getIntent();
    	String isapprove = intent.getStringExtra("isapprove");
    	String funcJsonStr = intent.getStringExtra("funcJsonStr");
    	Log.i("isapprove", isapprove);
    	// 获取功能url
    	downStepNodeUrl = "";
    	upStepNodeUrl = "";
    	commiterUrl = "";
    	saveSuggestionUrl = "";
    	submitProcessUrl = "";
		
		try {
			final JSONObject jsonObj = new JSONObject(funcJsonStr);
			for (Iterator iterator = jsonObj.keys(); iterator.hasNext();) {
				final String fieldName = (String) iterator.next();
				Log.i("key", fieldName);
				if(fieldName.equals(downStepNodeFuncid)){
					downStepNodeUrl = FunctionUtil.getFuncUrl(jsonObj, downStepNodeFuncid);
				}else if(fieldName.equals(upStepNodeFuncid)){
					upStepNodeUrl = FunctionUtil.getFuncUrl(jsonObj, upStepNodeFuncid);
				}else if(fieldName.equals(commiterFuncid)){
					commiterUrl = FunctionUtil.getFuncUrl(jsonObj, commiterFuncid);
				}else if(fieldName.equals(saveSuggestionFuncid)){
					saveSuggestionUrl = FunctionUtil.getFuncUrl(jsonObj, saveSuggestionFuncid);
				}else if(fieldName.equals(submitProcessFuncid)){
					submitProcessUrl = FunctionUtil.getFuncUrl(jsonObj, submitProcessFuncid);
				}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//显示节点服务
		String nodeUrl = "";
		if(isapprove.equals("true")){
			nodeUrl = downStepNodeUrl;
		}else{
			nodeUrl = upStepNodeUrl;
		}
        //显示节点下拉列表
        // 显示等待dialog
		ProgressDialog pDialog = ProgressDialog.show(this, getResources()
				.getString(R.string.nowloading), getResources().getString(
				R.string.pleasewait), true, true);

		// 获取到服务端流程名称数据，并装载数据
		HelpHandler2 helpHandler2 = new HelpHandler2(pDialog, this);
		// 启动请求服务端线程，封装数据给handler
		ConnectionServiceThread2 ConnServiceThread2 = new ConnectionServiceThread2(
				this, 29, helpHandler2, nodeUrl);
		ConnServiceThread2.start();
		
		//意见列表加载
		ArrayAdapter suggestionAdapter = new ArrayAdapter(this,
				android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.suggestionarray));
		suggestionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将Spinner和数据适配器关联
		suggestionSpinner.setAdapter(suggestionAdapter);
		
		
		//上一步按钮点击事件
		upStep.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setResult(RESULT_OK);
				finish();
			}
		});
		
		//下一步按钮点击事件
		downStep.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//保存意见
				String suggestion = suggestionText.getText().toString();
				


				// 获取到服务端流程名称数据，并装载数据
				HelpHandler2 helpHandler2 = new HelpHandler2(null, NodeSelectionActivity.this);
				// 启动请求服务端线程，封装数据给handler
				ConnectionServiceThread2 ConnServiceThread2 = new ConnectionServiceThread2(
						NodeSelectionActivity.this, 30, helpHandler2, saveSuggestionUrl+";"+suggestion);
				ConnServiceThread2.start();
				
				Intent intent = new Intent(NodeSelectionActivity.this, ChoosePersonActivity.class);
				intent.putExtra("suggestion", suggestion);
				intent.putExtra("commiterUrl", commiterUrl);
				intent.putExtra("submitProcessUrl", submitProcessUrl);
				intent.putExtra("actid", actid);
				startActivityForResult(intent, NODE_TO_CHOOSE);
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
		
		nodeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapterView, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				HashMap map = (HashMap) adapterView.getItemAtPosition(position);
				actid = (String) map.get("id");
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		suggestionSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> adapterView, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub

				String suggestionVal = (String) adapterView.getItemAtPosition(position).toString();
				suggestionText.setText(suggestionVal);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	super.onActivityResult(requestCode, resultCode, data);
    	if(requestCode == NODE_TO_CHOOSE){
    		if(resultCode == RESULT_CANCELED){
    			setResult(RESULT_CANCELED);
    			finish();
    		}
    	}
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
