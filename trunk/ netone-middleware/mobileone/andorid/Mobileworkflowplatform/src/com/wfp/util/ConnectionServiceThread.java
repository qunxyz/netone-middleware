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
public class ConnectionServiceThread extends Thread {
	
	private int key;
	private static int LOAD_DONE = 10;
	private static int LOAD_FAIL = 11;
	private static String SHARED_USERINFO = "userinfo";		//账户信息key
    private static String SERVICE_INFO = "SERVICEINFO";		//服务端信息key
	private HelpHandler helpHandler;
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
	public ConnectionServiceThread( Activity context, int key, HelpHandler helpHandler, String threadFlag){
		this.key = key;
		this.helpHandler = helpHandler;
		this.threadFlag = threadFlag;
		this.context = context;
		
		SharedPreferences serviceInfo = context.getSharedPreferences(SERVICE_INFO,0);
		SharedPreferences userInfo = context.getSharedPreferences(SHARED_USERINFO,0);
		serviceip = serviceInfo.getString("serviceip", "http://112.5.5.114:84");
		userid = userInfo.getString("userId", "adminx");

	}
	
	public ConnectionServiceThread( Activity context, int key, HelpHandler helpHandler,  String threadFlag, File file){
		this.key = key;
		this.helpHandler = helpHandler;
		this.threadFlag = threadFlag;
		this.file = file;
		this.context = context;
		
		SharedPreferences serviceInfo = context.getSharedPreferences(SERVICE_INFO,0);
		SharedPreferences userInfo = context.getSharedPreferences(SHARED_USERINFO,0);
		serviceip = serviceInfo.getString("serviceip", "http://112.5.5.114:84");
		userid = userInfo.getString("userId", "adminx");
	}
	
	public void run(){
		try {

			switch (key) {
				//我的代办
				case 1:
					Log.i("agencyfuncjsonstr", threadFlag);
					String connResultStr1_1 = null;
					String connResultStr1_2 = null;
					String connResultStr1_3 = null;
					allAgencyUrl = "";		
					allAgencyNumUrl = "";		
					haveBeenNumUrl = "";
					//解析服务url
					try {
						final JSONObject jsonObj = new JSONObject(threadFlag);
						for (Iterator iterator = jsonObj.keys(); iterator.hasNext();) {
							final String fieldName = (String) iterator.next();
							Log.i("key", fieldName);
							if(fieldName.equals(allAgencyFuncid)){
								allAgencyUrl = FunctionUtil.getFuncUrl(jsonObj, allAgencyFuncid);
							}else if(fieldName.equals(allAgencyNumFuncid)){
								allAgencyNumUrl = FunctionUtil.getFuncUrl(jsonObj, allAgencyNumFuncid);
							}else if(fieldName.equals(haveBeenNumFuncid)){
								haveBeenNumUrl = FunctionUtil.getFuncUrl(jsonObj, haveBeenNumFuncid);
							}
						}
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String params1 = "&userid=" + userid;
					//得到请求服务端结果
					connResultStr1_1 = getConnectionResultToService(allAgencyUrl + params1);
					connResultStr1_2 = getConnectionResultToService(allAgencyNumUrl + params1);
					connResultStr1_3 = getConnectionResultToService(haveBeenNumUrl + params1);
					//connResultStr1 = "[{\"lsh\":\"190bc48ba87d4b4c9372eece68b3d9fd\",\"starttime\":\"2012-02-24 13:21:58.987\",\"workcode\":\"8ab3308535adc66f0135adcf00cb000b\",\"actname\":\"http://10.51.176.5/cms/index.php?m=member&c=content&a=edit4netone&catid=96&id=62\",\"commitername\":\"adminx\",\"naturalname\":\"APPFRAME.APPFRAME.CMSAPP.NRSP\",\"commiter\":\"adminx\"}]";


					// 发送操作指令
					Message ms1 = new Message();
					
					if(connResultStr1_1 != null && connResultStr1_2 != null && connResultStr1_3 != null){
						// 数据加载成功，发送成功指令
						
						JSONArray agencyJson1_1= new JSONArray(connResultStr1_1);
						JSONArray agencyJson1_2= new JSONArray(connResultStr1_2);
						JSONArray agencyJson1_3= new JSONArray(connResultStr1_3);
						//准备要添加的数据条目
						ArrayList agencyList = new ArrayList();

						//json数据解析
						for (int i = 0; i < agencyJson1_1.length(); i++) {
							Log.i("agency!!", "agency");
							JSONObject jsonObj = (JSONObject) agencyJson1_1.get(i);
							Map map = new HashMap();
							Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
							for (Iterator iterator = jsonObj.keys(); iterator
									.hasNext();) {
								String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								if(fieldName.equals("starttime"))
									map.put(fieldName, jsonObj.get(fieldName).toString().split(" ")[0]);
								else
									map.put(fieldName, jsonObj.get(fieldName));
								
							}
							agencyList.add(map);
							
						}
						
						connResultStr1_2 = ((JSONObject)agencyJson1_2.get(0)).getString("countnum");
						connResultStr1_3 = ((JSONObject)agencyJson1_3.get(0)).getString("countnum");
						
						Log.i("agencyList", String.valueOf(agencyList));
						Log.i("agencyCount", String.valueOf(connResultStr1_2));
						Log.i("haveBeenOrganizedCount", String.valueOf(connResultStr1_3));
						
						ms1.what = LOAD_DONE;
						ms1.arg1 = 1;
						Bundle bundle1 = new Bundle();
						bundle1.putStringArrayList("agencylist", agencyList);
						bundle1.putString("agencyCount", connResultStr1_2);
						bundle1.putString("haveBeenOrganizedCount", connResultStr1_3);
						ms1.setData(bundle1);
					}else{
						ms1.what = LOAD_FAIL;
						ms1.arg1 = 1;
					}
					helpHandler.sendMessage(ms1);

					break;
				//公告
				case 2:
					String annountcementUrlStr = "";
					//得到请求服务端结果
					String connResultStr2 = null;
//					connResultStr2 = getConnectionResultToService(serviceip+PathConfig.annountcementUrlStr);
//					JSONArray jsonArray2 = new JSONArray(connResultStr2);
			        ArrayList listItems1 = new ArrayList();
			        for (int i = 0; i < 10; i++) {
			        	Map<String, Object> item2 = new HashMap<String, Object>();
			            item2.put("atitle", i+"中国移动福建省宁德市分公司");
			            item2.put("adatetime", "2012-12-12");
			            listItems1.add(item2);
					}
				        
	
					// 发送操作指令
					Message ms2 = new Message();
						
//					if(connResultStr2 != null){
						// 数据加载成功，发送成功指令
						ms2.what = LOAD_DONE;
						ms2.arg1 = 2;
						Bundle bundle2 = new Bundle();
						bundle2.putStringArrayList("annountcementList", listItems1);
						ms2.setData(bundle2);
						helpHandler.sendMessage(ms2);
//					}else{
//						ms2.what = LOAD_FAIL;
//						ms2.arg1 = 2;
//					}
					
					break;
				//频道主页
				case 3:
					
					//得到请求服务端结果
					String connResultStr3 = null;
					connResultStr3 = getConnectionResultToService(serviceip+PathConfig.channelMainUrlStr);
					//从服务端获取一级栏目图片存至SD卡
					//boolean isSavePartFile = FunctionUtil.saveWebFileToSDCard( context, serviceip+PathConfig.channelImgWebUrl);
	
					// 发送操作指令
					Message ms3 = new Message();
						
					//if(connResultStr3 != null && isSavePartFile){
					if(connResultStr3 != null){
						// 数据加载成功，发送成功指令
						
						JSONArray jsonArray3 = new JSONArray(connResultStr3);
						//json数据解析
						ArrayList channelitems = new ArrayList();

						for (int i = 0; i < jsonArray3.length(); i++) {
							JSONObject jsonObj = (JSONObject) jsonArray3.get(i);
							Map map = new HashMap();
							Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
							for (Iterator iterator = jsonObj.keys(); iterator
									.hasNext();) {
								String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								map.put(fieldName, jsonObj.get(fieldName));
								
							}
							map.put("channelImgUrl", FunctionUtil.getSDPath() + PathConfig.appSDCardPath +"/part.png");
							channelitems.add(map);
							
						}
						
						ms3.what = LOAD_DONE;
						ms3.arg1 = 3;
						Bundle bundle3 = new Bundle();
						bundle3.putStringArrayList("channelMainList", channelitems);
						ms3.setData(bundle3);
						
					}else{
						ms3.what = LOAD_FAIL;
						ms3.arg1 = 3;
					}
					helpHandler.sendMessage(ms3);
					break;
				//频道栏目树
				case 4:
						
					//得到请求服务端结果
					String connResultStr4 = null;
					connResultStr4 = getConnectionResultToService(serviceip+PathConfig.channelTreeUrlStr+threadFlag);

					// 发送操作指令
					Message ms4 = new Message();
						
					if(connResultStr4 != null){
						// 数据加载成功，发送成功指令
						
						JSONArray jsonArray4 = new JSONArray(connResultStr4);
						//结果list
						ArrayList treeRsList = new ArrayList(); 
						//标题 
						final ArrayList groupArray = new ArrayList<String>(); 
						//子标题
						final ArrayList childArray = new ArrayList<List<String>>();
						//json数据解析
						for (int i = 0; i < jsonArray4.length(); i++) {
							JSONObject jsonObj = (JSONObject) jsonArray4.get(i);
							Map map = new HashMap();
							Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
							for (Iterator iterator = jsonObj.keys(); iterator
									.hasNext();) {
								String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								map.put(fieldName, jsonObj.get(fieldName));
								
							}

							treeRsList.add(map);	
						}
						//标题数据导入
						for (int j = 0; j < treeRsList.size(); j++) {
							HashMap map = (HashMap) treeRsList.get(j);
							groupArray.add(map.get("catname").toString());
						}
						
						
						//子标题数据导入
						ArrayList subtreeAllRsList = new ArrayList<List<HashMap<String, String>>>();
						for (int n = 0; n < treeRsList.size(); n++) {
							HashMap map = (HashMap) treeRsList.get(n);
							String isChild = map.get("child").toString();
							String catid = map.get("catid").toString();
							String urlStr4_2 = "";
							String connResultStr4_2 = null;
							
							if(isChild.equals("true")){
								urlStr4_2 = ""+ catid +"";
								connResultStr4_2 = getConnectionResultToService(serviceip+PathConfig.channelTreeUrlStr+catid);

								// 发送操作指令
								Message ms4_2 = new Message();
									
								if(connResultStr4_2 != null){
									// 数据加载成功，发送成功指令
									JSONArray jsonArray4_2 = new JSONArray(connResultStr4_2);
									ArrayList subtreeRsList = new ArrayList<HashMap<String, String>>();
									//json数据解析
									for (int i = 0; i < jsonArray4_2.length(); i++) {
										JSONObject jsonObj = (JSONObject) jsonArray4_2.get(i);
										Map catmap = new HashMap();
										Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
										for (Iterator iterator = jsonObj.keys(); iterator
												.hasNext();) {
											String fieldName = (String) iterator.next();
											Log.i("key", fieldName);
											catmap.put(fieldName, jsonObj.get(fieldName));
										}

										subtreeRsList.add(catmap);	
									}
									subtreeAllRsList.add(subtreeRsList);
									ArrayList tempList = new ArrayList();
									for (int j = 0; j < subtreeRsList.size(); j++) {
										HashMap catmap = (HashMap) subtreeRsList.get(j);
										tempList.add(catmap.get("catname").toString());
									}
									childArray.add(tempList);
								}else{
									ms4_2.what = LOAD_FAIL;
									ms4_2.arg1 = 4;
									helpHandler.sendMessage(ms4_2);
									break;
								}
							}else{
								
								connResultStr4_2 = getConnectionResultToService(serviceip+PathConfig.channelContentUrlStr+catid);
								Log.i("channelContentUrlStr", serviceip+PathConfig.channelContentUrlStr+catid);
								// 发送操作指令
								Message ms4_2 = new Message();
									
								if(connResultStr4_2 != null){
									// 数据加载成功，发送成功指令
									JSONArray jsonArray4_2 = new JSONArray(connResultStr4_2);
									ArrayList subtreeRsList = new ArrayList<HashMap<String, String>>();
									//json数据解析
									for (int i = 0; i < jsonArray4_2.length(); i++) {
										JSONObject jsonObj = (JSONObject) jsonArray4_2.get(i);
										Map contentMap = new HashMap();
										Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
										for (Iterator iterator = jsonObj.keys(); iterator
												.hasNext();) {
											String fieldName = (String) iterator.next();
											Log.i("key", fieldName);
											contentMap.put(fieldName, jsonObj.get(fieldName));
											
										}

										subtreeRsList.add(contentMap);	
									}
									subtreeAllRsList.add(subtreeRsList);
									ArrayList tempList = new ArrayList();
									for (int j = 0; j < subtreeRsList.size(); j++) {
										HashMap catmap = (HashMap) subtreeRsList.get(j);
										tempList.add(catmap.get("title").toString());
									}
									childArray.add(tempList);
								}else{
									ms4_2.what = LOAD_FAIL;
									ms4_2.arg1 = 4;
									helpHandler.sendMessage(ms4_2);
									break;
								}
							}
							
							
						}
		
						setResultList(subtreeAllRsList);
						
						ms4.what = LOAD_DONE;
						ms4.arg1 = 4;
						Bundle bundle4 = new Bundle();
						bundle4.putStringArrayList("treeGroupArray", groupArray);
						bundle4.putCharSequenceArrayList("treeChildArray", childArray);
						ms4.setData(bundle4);
					}else{
						ms4.what = LOAD_FAIL;
						ms4.arg1 = 4;
					}
					helpHandler.sendMessage(ms4);
					break;
				//业务流程
				case 5:
					
					//得到请求服务端结果
					String connResultStr5 = null;
					connResultStr5 = getConnectionResultToService(serviceip+PathConfig.processMainUrlStr);
					
					//从服务端获取一级流程图片存至SD卡
					//boolean isSaveProcessFile = FunctionUtil.saveWebFileToSDCard( context, serviceip+PathConfig.processMainImgWebUrl);

			            
			         // 发送操作指令
						Message ms5 = new Message();
							
						//if(connResultStr5 != null && isSaveProcessFile){
						if(connResultStr5 != null){
						
							JSONArray jsonArray5 = new JSONArray(connResultStr5);
							//准备要添加的数据条目
					        ArrayList processMainList = new ArrayList<Map<String,Object>>();
					      //json数据解析
							for (int i = 0; i < jsonArray5.length(); i++) {
								JSONObject jsonObj = (JSONObject) jsonArray5.get(i);
								Map map = new HashMap();
								Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
								for (Iterator iterator = jsonObj.keys(); iterator
										.hasNext();) {
									String fieldName = (String) iterator.next();
									Log.i("key", fieldName);
									map.put(fieldName, jsonObj.get(fieldName));
									
								}
								map.put("processImgUrl", FunctionUtil.getSDPath() + PathConfig.appSDCardPath + "/processes.png");
								processMainList.add(map);
								
							}
							setResultList(processMainList);
							
							// 数据加载成功，发送成功指令
							ms5.what = LOAD_DONE;
							ms5.arg1 = 5;
							Bundle bundle5 = new Bundle();
							bundle5.putStringArrayList("processMainList", processMainList);
							ms5.setData(bundle5);
							helpHandler.sendMessage(ms5);
						}else{
							ms5.what = LOAD_FAIL;
							ms5.arg1 = 5;
						}
					break;
				//详细业务流程
				case 6:
					Log.i("urlStr6", serviceip+PathConfig.processDetailUrlStr + threadFlag);
					//得到请求服务端结果
					String connResultStr6 = null;
					connResultStr6 = getConnectionResultToService(serviceip+PathConfig.processDetailUrlStr + threadFlag);

					//从服务端获取详细流程图片存至SD卡
//					boolean isSaveProcessDetailFile = FunctionUtil.saveWebFileToSDCard( context, serviceip+PathConfig.processDetailImgWebUrl);
			        
			         // 发送操作指令
					Message ms6 = new Message();
						
					//if(connResultStr6 != null && isSaveProcessDetailFile){
					if(connResultStr6 != null){
						// 数据加载成功，发送成功指令
						
						JSONArray jsonArray6 = new JSONArray(connResultStr6);
						
						
				        //准备要添加的数据条目
				        ArrayList processDetailList = new ArrayList<Map<String,Object>>();
				        
					    //json数据解析
						for (int i = 0; i < jsonArray6.length(); i++) {
							JSONObject jsonObj = (JSONObject) jsonArray6.get(i);
							Map map = new HashMap();
							Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
							for (Iterator iterator = jsonObj.keys(); iterator
									.hasNext();) {
								String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								map.put(fieldName, jsonObj.get(fieldName));
								
							}
							map.put("processDetailImgUrl", FunctionUtil.getSDPath() + PathConfig.appSDCardPath + "/process_detail.png");
							processDetailList.add(map);
							
						}
						
						ms6.what = LOAD_DONE;
						ms6.arg1 = 6;
						Bundle bundle6 = new Bundle();
						bundle6.putStringArrayList("processDetailList", processDetailList);
						ms6.setData(bundle6);
						
					}else{
						ms6.what = LOAD_FAIL;
						ms6.arg1 = 6;
					}
					helpHandler.sendMessage(ms6);
					break;
				//资源数据
				case 7:
					Log.i("username", userid);
					String[] inputData = threadFlag.split("&");
					String naturalName = inputData[0];
					String urlStr7 = serviceip+PathConfig.sourcesDir;
					Log.i("urlStr7", urlStr7+naturalName);
					//得到请求服务端结果
					String connResultStr7 = null;
					connResultStr7 = getConnectionResultToService(urlStr7+naturalName);
					JSONArray jsonArray7 = new JSONArray(connResultStr7);
					//准备要添加的数据条目
			        ArrayList mainFuncList = new ArrayList<Map<String,Object>>();
			        
				    //json数据解析
					for (int i = 0; i < jsonArray7.length(); i++) {
						JSONObject jsonObj = (JSONObject) jsonArray7.get(i);
						Map map = new HashMap();
						Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
						String resourceImgName = "";
						for (Iterator iterator = jsonObj.keys(); iterator
								.hasNext();) {
							String fieldName = (String) iterator.next();
							Log.i("key", fieldName);
							if(fieldName.equals("imagename")){
								String resourceImgUrl = (String) jsonObj.get(fieldName);
								//从服务端获取资源图片存至SD卡
								//boolean isSaveResourceFile = FunctionUtil.saveWebFileToSDCard( context, serviceip+resourceImgUrl);
								//获取文件名
								map.put(fieldName, FunctionUtil.getSDPath()
										+ PathConfig.appSDCardPath + "/" + resourceImgUrl);
								Log.i("imagename", FunctionUtil.getSDPath()
										+ PathConfig.appSDCardPath + "/" + resourceImgUrl);
							}else{
								map.put(fieldName, jsonObj.get(fieldName));
							}
							
							
						}
						
						mainFuncList.add(map);
						
					}
			        
			            
				        // 发送操作指令
						Message ms7 = new Message();
							
						if(connResultStr7 != null){
							// 数据加载成功，发送成功指令
							ms7.what = LOAD_DONE;
							ms7.arg1 = 7;
							Bundle bundle7 = new Bundle();
							bundle7.putStringArrayList("mainFuncList", mainFuncList);
							if(inputData.length == 2)
								bundle7.putString("types", inputData[1]);
							ms7.setData(bundle7);
							helpHandler.sendMessage(ms7);
							setResultList(mainFuncList);
						}else{
							ms7.what = LOAD_FAIL;
							ms7.arg1 = 7;
						}
					break;
				//查询
				case 8:
					String queryUrlStr = "";
					//得到请求服务端结果
					String connResultStr8 = null;
//					connResultStr8 = getConnectionResultToService(serviceip+PathConfig.queryUrlStr);
//					JSONArray jsonArray8 = new JSONArray(connResultStr8);
			        ArrayList queryRsList = new ArrayList();
//			        for (int i = 0; i < 10; i++) {
//			        	Map<String, Object> item2 = new HashMap<String, Object>();
//			            item2.put("atitle", i+"中国移动福建省宁德市分公司");
//			            item2.put("adatetime", "2012-12-12");
//			            listItems1.add(item2);
//					}
				        
	
					// 发送操作指令
					Message ms8 = new Message();
						
//					if(connResultStr8 != null){
						// 数据加载成功，发送成功指令
						ms8.what = LOAD_DONE;
						ms8.arg1 = 8;
						Bundle bundle8 = new Bundle();
						bundle8.putStringArrayList("queryRsList", queryRsList);
						ms8.setData(bundle8);
						helpHandler.sendMessage(ms8);
//					}else{
//						ms8.what = LOAD_FAIL;
//						ms8.arg1 = 8;
//					}
					break;
				//代办工单
				case 9:
					Log.i("agencyUrl", threadFlag);
					String paramsStr9 = "&userid=" + userid;
					//得到请求服务端结果
					String connResultStr9 = null;
					connResultStr9 = getConnectionResultToService(threadFlag+paramsStr9);

			     // 发送操作指令
					Message ms9 = new Message();
						
					if(connResultStr9 != null){
						// 数据加载成功，发送成功指令
					
						JSONArray jsonArray9 = new JSONArray(connResultStr9);
				        //准备要添加的数据条目
				        ArrayList agencyOrdersList = new ArrayList<Map<String,Object>>();
				        
				        //json数据解析
						for (int i = 0; i < jsonArray9.length(); i++) {
							JSONObject jsonObj = (JSONObject) jsonArray9.get(i);
							Map map = new HashMap();
							Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
							for (Iterator iterator = jsonObj.keys(); iterator
									.hasNext();) {
								String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								map.put(fieldName, jsonObj.get(fieldName));
								
							}
							agencyOrdersList.add(map);
							
						}

						
						ms9.what = LOAD_DONE;
						ms9.arg1 = 9;
						Bundle bundle9 = new Bundle();
						bundle9.putStringArrayList("agencyOrdersList", agencyOrdersList);
						ms9.setData(bundle9);
						
					}else{
						ms9.what = LOAD_FAIL;
						ms9.arg1 = 9;
					}
					helpHandler.sendMessage(ms9);
					break;
				//已办工单
				case 10:
					Log.i("haveBeenUrl", threadFlag);
					String paramsStr10 = "&userid=" + userid;
					//得到请求服务端结果
					String connResultStr10 = null;
					connResultStr10 = getConnectionResultToService(threadFlag+paramsStr10);

			     // 发送操作指令
					Message ms10 = new Message();
					
					if(connResultStr10 != null){
						// 数据加载成功，发送成功指令
						
						JSONArray jsonArray10 = new JSONArray(connResultStr10);
				        //准备要添加的数据条目
				        ArrayList haveBeenOrderList = new ArrayList<Map<String,Object>>();
				        
				        //json数据解析
						for (int i = 0; i < jsonArray10.length(); i++) {
							JSONObject jsonObj = (JSONObject) jsonArray10.get(i);
							Map map = new HashMap();
							Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
							for (Iterator iterator = jsonObj.keys(); iterator
									.hasNext();) {
								String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								map.put(fieldName, jsonObj.get(fieldName));
								
							}
							haveBeenOrderList.add(map);
							
						}
						
						ms10.what = LOAD_DONE;
						ms10.arg1 = 10;
						Bundle bundle10 = new Bundle();
						bundle10.putStringArrayList("haveBeenOrderList", haveBeenOrderList);
						ms10.setData(bundle10);
						
					}else{
						ms10.what = LOAD_FAIL;
						ms10.arg1 = 10;
					}
					helpHandler.sendMessage(ms10);
					break;
				//已办结工单
				case 11:
					Log.i("unfiledUrl", threadFlag);
					String paramsStr11 = "&userid=" + userid;
					//得到请求服务端结果
					String connResultStr11 = null;
					connResultStr11 = getConnectionResultToService(threadFlag+paramsStr11);

			     // 发送操作指令
					Message ms11 = new Message();
						
					if(connResultStr11 != null){
						// 数据加载成功，发送成功指令
						
						JSONArray jsonArray11 = new JSONArray(connResultStr11);
						//准备要添加的数据条目
				        ArrayList hasGoneThroughOrdersList = new ArrayList<Map<String,Object>>();		        
				        //json数据解析
						for (int i = 0; i < jsonArray11.length(); i++) {
							JSONObject jsonObj = (JSONObject) jsonArray11.get(i);
							Map map = new HashMap();
							Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
							for (Iterator iterator = jsonObj.keys(); iterator
									.hasNext();) {
								String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								map.put(fieldName, jsonObj.get(fieldName));
								
							}
							hasGoneThroughOrdersList.add(map);
							
						}

						
						ms11.what = LOAD_DONE;
						ms11.arg1 = 11;
						Bundle bundle11 = new Bundle();
						bundle11.putStringArrayList("hasGoneThroughOrdersList", hasGoneThroughOrdersList);
						ms11.setData(bundle11);
						
					}else{
						ms11.what = LOAD_FAIL;
						ms11.arg1 = 11;
					}
					helpHandler.sendMessage(ms11);
					break;
				//所有工单
				case 12:
					Log.i("filedUrl", threadFlag);
					String paramsStr12 = "&userid=" + userid;
					//得到请求服务端结果
					String connResultStr12 = null;
					connResultStr12 = getConnectionResultToService(threadFlag+paramsStr12);

			     // 发送操作指令
					Message ms12 = new Message();
						
					if(connResultStr12 != null){
						// 数据加载成功，发送成功指令
						
						JSONArray jsonArray12 = new JSONArray(connResultStr12);
				        //准备要添加的数据条目
				        ArrayList allOrdersList = new ArrayList<Map<String,Object>>();        
				        //json数据解析
						for (int i = 0; i < jsonArray12.length(); i++) {
							JSONObject jsonObj = (JSONObject) jsonArray12.get(i);
							Map map = new HashMap();
							Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
							for (Iterator iterator = jsonObj.keys(); iterator
									.hasNext();) {
								String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								map.put(fieldName, jsonObj.get(fieldName));
								
							}
							allOrdersList.add(map);
							
						}

						
						ms12.what = LOAD_DONE;
						ms12.arg1 = 12;
						Bundle bundle12 = new Bundle();
						bundle12.putStringArrayList("allOrdersList", allOrdersList);
						ms12.setData(bundle12);
						
					}else{
						ms12.what = LOAD_FAIL;
						ms12.arg1 = 12;
					}
					helpHandler.sendMessage(ms12);
					break;
				//上传经维度信息
				case 13:
					Log.i("processName", threadFlag);
					String[] params = threadFlag.split("&");
					String url = params[0];
					String longitude = params[1];
					String latitude = params[2];
					String lsh = params[3];
					String flag13 = params[4];
					String formOperate = params[5];
					
					String paramsStr13= "";
					Log.i("paramslength", String.valueOf(params.length));
					if(formOperate.equals("add"))
						if(params.length == 7){	//添加子表单
							String parentlsh = params[6].split(";")[1];
							
							paramsStr13 = "&userid="+ userid +"&column4="+ latitude +"&column3=" + longitude + "&parentId=" + parentlsh;
						}else
							paramsStr13 = "&userid="+ userid +"&column4="+ latitude +"&column3=" + longitude;
					else if(formOperate.equals("edit"))
						paramsStr13 = "&userid="+ userid +"&column4="+ latitude +"&column3=" + longitude + "&lsh=" + lsh;
					String appName = url.split("=")[url.split("=").length-1];
					Log.i("urlStr13", url+paramsStr13);
					
					//得到请求服务端结果
					String connResultStr13 = null;
					connResultStr13 = getConnectionResultToService(url+paramsStr13);

					// 发送操作指令
					Message ms13 = new Message();
					if(connResultStr13 != null){
						// 数据加载成功，发送成功指令
						Log.i("urlStr13", url+paramsStr13);
						ms13.what = LOAD_DONE;
						ms13.arg1 = 13;
						Bundle bundle13 = new Bundle();
						if(formOperate.equals("add")){
							bundle13.putString("rsFlag", connResultStr13);
							if(params.length == 7)
								bundle13.putString("marketInfo", params[6]);		//新增记录时为商超信息
							Log.i("marketNameandmarketlsh", lsh);
							
						}else if(formOperate.equals("edit"))
							bundle13.putString("rsFlag", lsh);
						bundle13.putString("appName", appName);
						bundle13.putString("flag", flag13);
						ms13.setData(bundle13);
						setServiceRsStr(connResultStr13);
					}else{
						ms13.what = LOAD_FAIL;
						ms13.arg1 = 13;
					}
					helpHandler.sendMessage(ms13);
					break;
				//读取指定用户图片上传位置经纬度值
				case 14:
					Log.i("threadFlag14", threadFlag);
					//是否搜索所有人员
					String time = "";
					String userid14 = "";
					if(threadFlag.split(";").length == 1){
						time = threadFlag.split(";")[0];
					}else {
						time = threadFlag.split(";")[0];
						userid14 = threadFlag.split(";")[1];
					}
					String paramsStr14= "&time=" + time + "&userid=" + userid14;
					//得到请求服务端结果
					String connResultStr14 = null;
					connResultStr14 = getConnectionResultToService(serviceip+PathConfig.latlongInfoUrlStr + paramsStr14);

					// 发送操作指令
					Message ms14 = new Message();
					if(connResultStr14 != null){
						// 数据加载成功，发送成功指令
						
						JSONArray jsonArray14 = new JSONArray(connResultStr14);
				        //准备要添加的数据条目
				        ArrayList latlonginfoList = new ArrayList<Map<String,Object>>();
				        
				        //json数据解析
						for (int i = 0; i < jsonArray14.length(); i++) {
							JSONObject jsonObj = (JSONObject) jsonArray14.get(i);
							Map map = new HashMap();
							Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
							for (Iterator iterator = jsonObj.keys(); iterator
									.hasNext();) {
								String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								map.put(fieldName, jsonObj.get(fieldName));
								
							}
							latlonginfoList.add(map);
							
						}
						
						ms14.what = LOAD_DONE;
						ms14.arg1 = 14;
						Bundle bundle14 = new Bundle();
						bundle14.putStringArrayList("latlonginfoList", latlonginfoList);
						ms14.setData(bundle14);
						
					}else{
						ms14.what = LOAD_FAIL;
						ms14.arg1 = 14;
					}
					helpHandler.sendMessage(ms14);
					break;
					
				//读取历史上传记录
				case 15:

					System.out.println("urlStr15: "+threadFlag);
					String[] params15 = threadFlag.split(";");
					String urlStr15 = params15[0];
					String formType = params15[1];
					//得到请求服务端结果
					String connResultStr15 = null;
					connResultStr15 = getConnectionResultToService(urlStr15);

					// 发送操作指令
					Message ms15 = new Message();
					if(connResultStr15 != null){
						// 数据加载成功，发送成功指令
						
						JSONArray jsonArray15 = new JSONArray(connResultStr15);
				        //准备要添加的数据条目
				        ArrayList historyList = new ArrayList<Map<String,Object>>();
				        
				        //json数据解析
						for (int i = 0; i < jsonArray15.length(); i++) {
							JSONObject jsonObj = (JSONObject) jsonArray15.get(i);
							Map map = new HashMap();
							Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
							for (Iterator iterator = jsonObj.keys(); iterator
									.hasNext();) {
								String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								map.put(fieldName, jsonObj.get(fieldName));
								
							}
							historyList.add(map);
							
						}
						setResultList(historyList);
						ms15.what = LOAD_DONE;
						ms15.arg1 = 15;
						Bundle bundle15 = new Bundle();
						bundle15.putStringArrayList("historyList", historyList);
						bundle15.putString("formType", formType);
						ms15.setData(bundle15);
						
					}else{
						ms15.what = LOAD_FAIL;
						ms15.arg1 = 15;
					}
					helpHandler.sendMessage(ms15);
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
