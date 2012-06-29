package com.wfp.util;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.wfp.config.PathConfig;

/**
 * 
 * @author dingqiang
 * @category 连接服务端线程
 * 
 */
public class ConnectionServiceThread2 extends Thread {
	
	private int key;
	private static int LOAD_DONE = 10;
	private static int LOAD_FAIL = 11;
	private static String SHARED_USERINFO = "userinfo";		//账户信息key
    private static String SERVICE_INFO = "SERVICEINFO";		//服务端信息key
	private HelpHandler2 helpHandler;
	private ArrayList agemcytaskList;
	private String serviceRsStr;
	private File file;
	private SocketHttpRequester socketHttpRequester;
	private String serviceip;
	private String userid;	//账号

	private String threadFlag;
	private Activity context;
    private String allAgencyUrl;
    private String allAgencyNumUrl;
    private String haveBeenNumUrl;
    private static String allAgencyFuncid = "11";		//所有待办功能编号
    private static String allAgencyNumFuncid = "12";	//待办数功能编号
    private static String haveBeenNumFuncid = "14";		//已办树功能编号
	/**
	 * 
	 * @param context
	 * @param key
	 * @param helpHandler
	 * @param threadFlag
	 */
	public ConnectionServiceThread2( Activity context, int key, HelpHandler2 helpHandler, String threadFlag){
		this.key = key;
		this.helpHandler = helpHandler;
		this.threadFlag = threadFlag;
		this.context = context;
		
		SharedPreferences serviceInfo = context.getSharedPreferences(SERVICE_INFO,0);
		SharedPreferences userInfo = context.getSharedPreferences(SHARED_USERINFO,0);
		serviceip = serviceInfo.getString("serviceip", "http://42.120.40.204:84");
		userid = userInfo.getString("userId", "adminx");

	}
	
	public ConnectionServiceThread2( Activity context, int key, HelpHandler2 helpHandler,  String threadFlag, File file){
		this.key = key;
		this.helpHandler = helpHandler;
		this.threadFlag = threadFlag;
		this.file = file;
		this.context = context;
		
		SharedPreferences serviceInfo = context.getSharedPreferences(SERVICE_INFO,0);
		SharedPreferences userInfo = context.getSharedPreferences(SHARED_USERINFO,0);
		serviceip = serviceInfo.getString("serviceip", "http://42.120.40.204:84");
		userid = userInfo.getString("userId", "adminx");
	}
	
	public void run(){
		try {

			switch (key) {
				//获取指定半径内商超信息
				case 1:
					Log.i("withinaradiusmarket", threadFlag);
					String radiusMarketUrl = threadFlag.split("&")[0];
					String longitude1 = threadFlag.split("&")[1];
					String latitude1 = threadFlag.split("&")[2];
					String radius = threadFlag.split("&")[3];
					String addFormUrl = threadFlag.split("&")[4];
					String params1 = "&yoffset=" + longitude1 + "&xoffset=" + latitude1 + "&roffset=" + radius;
					//得到请求服务端结果
					String connResultStr1 = null;
					Log.i("withinaradiusmarketUrl", serviceip+radiusMarketUrl+params1);
					connResultStr1 = getConnectionResultToService(radiusMarketUrl+params1);

			     // 发送操作指令
					Message ms1 = new Message();
						
					if(connResultStr1 != null || connResultStr1.equals("")){
						// 数据加载成功，发送成功指令
					
						JSONArray jsonArray1 = new JSONArray(connResultStr1);
				        //准备要添加的数据条目
				        ArrayList radiusMarketList = new ArrayList<Map<String,Object>>();
				        
				        //json数据解析
						for (int i = 0; i < jsonArray1.length(); i++) {
							JSONObject jsonObj = (JSONObject) jsonArray1.get(i);
							Map map = new HashMap();
							Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
							for (Iterator iterator = jsonObj.keys(); iterator
									.hasNext();) {
								String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								map.put(fieldName, jsonObj.get(fieldName));
							}
							radiusMarketList.add(map);
							
						}
						Bundle bundle1 = new Bundle();
						bundle1.putStringArrayList("radiusMarketList", radiusMarketList);
						bundle1.putString("longitude", longitude1);
						bundle1.putString("latitude", latitude1);
						bundle1.putString("addFormUrl", addFormUrl);
						ms1.setData(bundle1);
						ms1.what = LOAD_DONE;
						ms1.arg1 = 1;
						
					}else{
						ms1.what = LOAD_FAIL;
						ms1.arg1 = 1;
					}
					helpHandler.sendMessage(ms1);
					break;
				default:
					break;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			//System.out.println(ex.getMessage());

		}
	}
	
	public void setResultList(ArrayList agemcytaskList) {
		this.agemcytaskList = agemcytaskList;
	}
	
	public ArrayList getResultList() {
		return agemcytaskList;
	}
	
	public String getServiceRsStr() {
		return serviceRsStr;
	}

	public void setServiceRsStr(String serviceRsStr) {
		this.serviceRsStr = serviceRsStr;
	}
	
	/**
	 * 
	 * @param urlStr get方式请求服务端的url
	 * @return 	返回json结果数据
	 */
	private String getConnectionResultToService(String urlStr){
		
		try{
			// 主机地址不可以设置为localhost或127.0.0.1，必须是本机或其他机器所在Internet网或局域网地址。
	
			String path = urlStr;
			URL url = new URL(path);
					
			HttpURLConnection conn = (HttpURLConnection)
			url.openConnection();
										
			Log.i("ResponseCode",String.valueOf(conn.getResponseCode()));
			// 请求成功
			if (conn.getResponseCode() == 200) {
			
				// 获取服务器返回的数据
			    byte[] redata = StringUtil.readStream(conn.getInputStream());

												
				String returnStr = new String(redata,"utf-8");
				Log.i("serviceresult", returnStr);
				return returnStr; 
			}else{
				 Log.i("ResponseCode",
				 String.valueOf(conn.getResponseCode()));
				 return null;
												
			}
		} catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * HttpPost请求服务端
	 * @param urlStr 请求服务端地址
	 * @param paramsStr 参数
	 * @return 	返回 流数
	 */
	private String httpPostConnectionService(String urlStr, String paramsStr){
		
		try{
			// 主机地址不可以设置为localhost或127.0.0.1，必须是本机或其他机器所在Internet网或局域网地址。
	
			String path = urlStr;
			URL url = new URL(path);
			
			HttpPost post = new HttpPost(urlStr);
			Log.i("postparamsStr", "postparamsStr");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			String[] paramsArray = paramsStr.split("\\$");
			for (int i = 0; i < paramsArray.length; i++) {
				String key = paramsArray[i].split(":")[0];
				String value = paramsArray[i].split(":")[1];
				if(value.equals("empty"))
					params.add(new BasicNameValuePair(key, ""));
				else
					params.add(new BasicNameValuePair(key, value));
			}


			post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));

			HttpResponse response = new DefaultHttpClient().execute(post);


										
			Log.i("ResponseCode",String.valueOf( response.getStatusLine().getStatusCode()));
			// 请求成功
			if (response.getStatusLine().getStatusCode() == 200) {

				String resultStr = EntityUtils.toString(response.getEntity(),"utf-8");

//				// 获取服务器返回的数据
//			    byte[] redata = StringUtil.readStream(conn.getInputStream());
//
//				String returnStr = new String(redata,"utf-8");
				Log.i("serviceresult", resultStr);
				return resultStr; 
			}else{
				 Log.i("ResponseCode",
				 String.valueOf(response.getStatusLine().getStatusCode()));
				 return null;
												
			}
		} catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public void cancelUploadFile(){
		socketHttpRequester.cancelUpload();
	}
}
