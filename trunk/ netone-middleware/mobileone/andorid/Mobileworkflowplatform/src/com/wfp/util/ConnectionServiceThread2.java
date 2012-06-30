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
		serviceip = serviceInfo.getString("serviceip", "http://112.5.5.114:84");
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
		serviceip = serviceInfo.getString("serviceip", "http://112.5.5.114:84");
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
				//提交当前节点审核
				case 2:
					Log.i("nodeserviceUrl", threadFlag);
					String urlStr32 = threadFlag.split(";")[0];
					String actid32 = threadFlag.split(";")[1];
					String clientId = threadFlag.split(";")[2];
					String paramsStr32 = "&actid="+actid32+"&clientId="+clientId;
					//得到请求服务端结果
					String connResultStr32 = null;
					connResultStr32 = getConnectionResultToService(urlStr32+paramsStr32);

			     // 发送操作指令
					Message ms32 = new Message();
						
					if(connResultStr32.equals("") ){
						// 数据加载成功，发送成功指令
					
						ms32.what = LOAD_DONE;
						ms32.arg1 = 2;
						
					}else{
						ms32.what = LOAD_FAIL;
						ms32.arg1 = 2;
					}
					helpHandler.sendMessage(ms32);
					break;
				//服务端单点登录验证
				case 3:
					String connResultStr19 = null;
					String paramsStr19 = "";
					String userName = "";
					String passWord = "";
					if(!threadFlag.equals("IMEI")){
						String[] userInfo = threadFlag.split(";");
						userName = userInfo[0];
						passWord = userInfo[1];
						paramsStr19 = "name=" + userName + "&password=" + passWord;
						Log.i("urlStr19", serviceip+PathConfig.loginUrlStr+paramsStr19);
						
						//得到请求服务端结果
						connResultStr19 = getConnectionResultToService(serviceip+PathConfig.loginUrlStr+paramsStr19);
						//connResultStr19 = "y";
					}else{
						//获取IMEI码
						TelephonyManager telephonyMgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE); 
						String IMEI = telephonyMgr.getDeviceId();
						Log.i("IMEIcode", IMEI);
						System.out.println(IMEI);
						//得到请求服务端结果
						connResultStr19 = getConnectionResultToService(serviceip+PathConfig.IMEILoginUrlStr+IMEI);
						//connResultStr19 = "y";
					}
					userName = "adminx";
					// 发送操作指令
					Message ms19 = new Message();
					Bundle bundle = new Bundle();
					if(connResultStr19 != null && connResultStr19.equals("false")){
				
						bundle.putString("loginfail", "loginfail");
						ms19.what = LOAD_FAIL;
						ms19.arg1 = 3;
					}else if(connResultStr19 != null && !connResultStr19.equals("")){
						
						JSONArray userInfoArray = new JSONArray(connResultStr19);
						JSONObject userInfoObj = (JSONObject) userInfoArray.get(0);
						// 数据加载成功，发送成功指令						
						ms19.what = LOAD_DONE;
						ms19.arg1 = 3;
						bundle.putString("userId", userInfoObj.getString("usercode"));
						bundle.putString("passWord", passWord);
						bundle.putString("userName", userInfoObj.getString("name"));
					}else{
						ms19.what = LOAD_FAIL;
						ms19.arg1 = 3;
					}
						
					ms19.setData(bundle);
					helpHandler.sendMessage(ms19);
					break;	
				//读取表单目录数据
				case 16:
					
					String[] flag16 = threadFlag.split("&");
					String naturalName16 = flag16[0];
					String key = flag16[1];
					
					String paramsStr16= "&naturalname=" + naturalName16;
					Log.i("urlStr16", serviceip+PathConfig.formUrlStr + paramsStr16);
					//得到请求服务端结果
					String connResultStr16 = null;
					connResultStr16 = getConnectionResultToService(serviceip+PathConfig.formUrlStr + paramsStr16);

					//从服务端获取表单目录图片存至SD卡
//					boolean isSaveformDirImg = FunctionUtil.saveWebFileToSDCard( context, serviceip+PathConfig.processMainImgWebUrl);
					
					// 发送操作指令
					Message ms16 = new Message();
					//if(connResultStr16 != null && isSaveformDirImg){
					if(connResultStr16 != null){
						// 数据加载成功，发送成功指令
						
						JSONArray jsonArray16 = new JSONArray(connResultStr16);
				        //准备要添加的数据条目
				        ArrayList formDirList = new ArrayList<Map<String,Object>>();
				        
				        //json数据解析
						for (int i = 0; i < jsonArray16.length(); i++) {
							JSONObject jsonObj = (JSONObject) jsonArray16.get(i);
							Map map = new HashMap();
							Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
							for (Iterator iterator = jsonObj.keys(); iterator
									.hasNext();) {
								String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								map.put(fieldName, jsonObj.get(fieldName));
								
							}
							map.put("processImgUrl", FunctionUtil.getSDPath() + PathConfig.appSDCardPath + "/processes.png");
							formDirList.add(map);
							
						}
						
						ms16.what = LOAD_DONE;
						ms16.arg1 = 16;
						Bundle bundle16 = new Bundle();
						bundle16.putStringArrayList("formDirList", formDirList);
						bundle16.putString("key", key);
						ms16.setData(bundle16);
						
					}else{
						ms16.what = LOAD_FAIL;
						ms16.arg1 = 16;
					}
					helpHandler.sendMessage(ms16);
					break;	
				//上传图片
				case 17:

					System.out.println(threadFlag);
			        Log.i("loadstart", "upload start");
			        try {
			        	//服务端地址 112.5.5.114:84

			            //请求普通信息
			            Map<String, String> postparams = new HashMap<String, String>();
			            //postparams.put("userid", userid);
			            //上传文件
			            String name = file.getName();

			            Log.i("uploadfilename", userid + "&"+name);
			            FormFile formfile = new FormFile(userid + "&"+name, file, "image", "application/octet-stream");

			            socketHttpRequester = new SocketHttpRequester();
			            socketHttpRequester.post(threadFlag, postparams, formfile, helpHandler);

			            Log.i("usuccess", "upload success");
			        } catch (Exception e) {
			            Log.i("uerror", "upload error");
						Message ms17 = new Message();
						ms17.what = -1;
						ms17.arg1 = 17;
						Bundle bundle17 = new Bundle();
						bundle17.putString("error", "上传失败");
						ms17.setData(bundle17);

						helpHandler.sendMessage(ms17);
			            e.printStackTrace();
			        }
			        Log.i("uend", "upload end");

					break;
				//工单照片读取
				case 18:
					Log.i("getpicture", "getpicture");
					// 发送操作指令
					Message ms18 = new Message();
					ms18.arg1 = 18;
					Bundle bundle18 = new Bundle();
					try{
						ms18.what = LOAD_DONE;
						ArrayList ordersImgList =  FunctionUtil.findFilesList(threadFlag,1);
						bundle18.putStringArrayList("ordersImgList", ordersImgList);

					}catch(Exception ex){
						ex.printStackTrace();
						ms18.what = LOAD_FAIL;
						bundle18.putString("exception", ex.getMessage());
					}
					ms18.setData(bundle18);
					helpHandler.sendMessage(ms18);
					break;	
				
				//装载表单结构
				case 20:
					
					//得到请求服务端结果
					String connResultStr20_loadForm = null;
					String connResultStr20_queryForm = null;
					Log.i("loadFormUrlStr", threadFlag);
					String loadFormUrl20 = threadFlag.split(";")[0];
					String queryFormUrl20 = threadFlag.split(";")[1];
					String opFlag20 = threadFlag.split(";")[2];
					String lsh20 = threadFlag.split(";")[3];
					connResultStr20_loadForm = getConnectionResultToService(loadFormUrl20);
					// 发送操作指令
					Message ms20 = new Message();
					ArrayList formDataList = null;
					if(opFlag20.equals("brow") || opFlag20.equals("edit")){
						connResultStr20_queryForm = getConnectionResultToService(queryFormUrl20+"&lsh="+lsh20);
						Log.i("connResultStr20_queryForm", connResultStr20_queryForm);
						if(connResultStr20_queryForm != null){
							// 数据加载成功，发送成功指令
							
							JSONArray jsonArray20 = new JSONArray(connResultStr20_queryForm);
					        //准备要添加的数据条目
					        formDataList = new ArrayList<Map<String,Object>>();
					        
					        //json数据解析
							for (int i = 0; i < jsonArray20.length(); i++) {
								JSONObject jsonObj = (JSONObject) jsonArray20.get(i);
								Map map = new HashMap();
								Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
								for (Iterator iterator = jsonObj.keys(); iterator
										.hasNext();) {
									String fieldName = (String) iterator.next();
									Log.i("key", fieldName);
									map.put(fieldName, jsonObj.get(fieldName));
								}
								formDataList.add(map);
								
							}
							
							
						}else{
							ms20.what = LOAD_FAIL;
							ms20.arg1 = 20;
							helpHandler.sendMessage(ms20);
						}
						
					}
					
					if(connResultStr20_loadForm != null){
						// 数据加载成功，发送成功指令
						
						JSONArray jsonArray20 = new JSONArray(connResultStr20_loadForm);
				        //准备要添加的数据条目
				        ArrayList formStructureList = new ArrayList<Map<String,Object>>();
				        
				        //json数据解析
						for (int i = 0; i < jsonArray20.length(); i++) {
							JSONObject jsonObj = (JSONObject) jsonArray20.get(i);
							Map map = new HashMap();
							Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
							for (Iterator iterator = jsonObj.keys(); iterator
									.hasNext();) {
								String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								map.put(fieldName, jsonObj.get(fieldName));
							}
							formStructureList.add(map);
							
						}
						
						ms20.what = LOAD_DONE;
						ms20.arg1 = 20;
						Bundle bundle20 = new Bundle();
						bundle20.putStringArrayList("formStructureList", formStructureList);
						bundle20.putStringArrayList("formDataList", formDataList);
						bundle20.putString("opFlag20", opFlag20);
						
						ms20.setData(bundle20);
						
					}else{
						ms20.what = LOAD_FAIL;
						ms20.arg1 = 20;
					}
					helpHandler.sendMessage(ms20);
					break;	
				//删除表单数据
				case 21:
					
					//得到请求服务端结果
					String connResultStr21 = null;
					Log.i("deleteFormUrlStr", threadFlag);
					connResultStr21 = getConnectionResultToService(threadFlag);

			     // 发送操作指令
					Message ms21 = new Message();
					
					if(connResultStr21 != null){
						// 数据加载成功，发送成功指令
						if(connResultStr21.indexOf("true") >= 0){
							ms21.what = LOAD_DONE;
							ms21.arg1 = 21;

						}else if(connResultStr21.equals("false")){
							ms21.what = LOAD_FAIL;
							ms21.arg1 = 21;
						}
					}else{
						ms21.what = LOAD_FAIL;
						ms21.arg1 = 21;
					}
					helpHandler.sendMessage(ms21);
					break;	
				//新增表单
				case 22:
					Log.i("naturalname", threadFlag);
					String[] flags = threadFlag.split(";");
					String addFormUrl22 = flags[0];
					String loadFormUrl = flags[1];
					String funcJsonStr = flags[2];
					String paramsStr22 = "&userid=" + userid;
					Log.i("addFormUrl", addFormUrl22+paramsStr22);
					//得到请求服务端结果
					String connResultStr22 = null;
					connResultStr22 = getConnectionResultToService(addFormUrl22+paramsStr22);
					//connResultStr22 = getConnectionResultToService(URLEncoder.encode(""));
					

			     // 发送操作指令
					Message ms22 = new Message();
					
					if(connResultStr22 != null && !connResultStr22.equals("")){

						ms22.what = LOAD_DONE;
						ms22.arg1 = 22;
						Bundle bundle22 = new Bundle();
						bundle22.putString("funcJsonStr", funcJsonStr);
						bundle22.putString("lsh", connResultStr22);
						bundle22.putString("loadFormUrl", loadFormUrl);
						
						ms22.setData(bundle22);
						
					}else{
						ms22.what = LOAD_FAIL;
						ms22.arg1 = 22;
					}
					helpHandler.sendMessage(ms22);
					break;
				//修改表单
				case 23:
					Log.i("editurl", threadFlag);
					String  editUrl= threadFlag.split(";")[0];
					String params23 = threadFlag.split(";")[1];
					String opFlag23 = threadFlag.split(";")[2];
					String isapprove = threadFlag.split(";")[3];
					String funcJsonStr23 = "";
					if(!isapprove.equals("noexamine"))
						funcJsonStr23 = threadFlag.split(";")[4];
					Log.i("editFormurl", editUrl);
					//得到请求服务端结果
					String connResultStr23 = null;
					connResultStr23 = httpPostConnectionService(editUrl, params23);

			     // 发送操作指令
					Message ms23 = new Message();
					
					if(connResultStr23 != null && !connResultStr23.equals("")){

						ms23.what = LOAD_DONE;
						ms23.arg1 = 23;
						Bundle bundle23 = new Bundle();
						bundle23.putString("opFlag", opFlag23);
						bundle23.putString("isapprove", isapprove);
						bundle23.putString("funcJsonStr", funcJsonStr23);
						
						ms23.setData(bundle23);
						
					}else{
						ms23.what = LOAD_FAIL;
						ms23.arg1 = 23;
					}
					helpHandler.sendMessage(ms23);
					break;
				//已上传附件数据
				case 24:
					Log.i("editurl", threadFlag);
					String  uploadFileInfoUrl= threadFlag.split(";")[0];
					String formlsh = threadFlag.split(";")[1];
					Log.i("uploadFileInfoUrl", uploadFileInfoUrl + "lsh=" + formlsh);
					//得到请求服务端结果
					String connResultStr24 = null;
					connResultStr24 = getConnectionResultToService(serviceip + uploadFileInfoUrl + "&lsh=" + formlsh);

					JSONArray jsonArray24 = new JSONArray(connResultStr24);
			        //准备要添加的数据条目
			        ArrayList uploadFileList = new ArrayList<Map<String,Object>>();
			        
			        //json数据解析
					for (int i = 0; i < jsonArray24.length(); i++) {
						JSONObject jsonObj = (JSONObject) jsonArray24.get(i);
						Map map = new HashMap();
						Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
						for (Iterator iterator = jsonObj.keys(); iterator
								.hasNext();) {
							String fieldName = (String) iterator.next();
							Log.i("key", fieldName);
//							if(fieldName.equals("note")){
//								map.put(fieldName, jsonObj.get(fieldName).toString().split("\\")[1]);
//							}else
								map.put(fieldName, jsonObj.get(fieldName));
							
						}
						uploadFileList.add(map);
						
					}
					
			     // 发送操作指令
					Message ms24 = new Message();
					
					if(connResultStr24 != null && !connResultStr24.equals("")){

						ms24.what = LOAD_DONE;
						ms24.arg1 = 24;
						Bundle bundle24 = new Bundle();
						bundle24.putStringArrayList("uploadFileList", uploadFileList);
						ms24.setData(bundle24);
						
					}else{
						ms24.what = LOAD_FAIL;
						ms24.arg1 = 24;
					}
					helpHandler.sendMessage(ms24);
					break;
				//获取已上传附件地址
				case 25:
					Log.i("editurl", threadFlag);
					String  fileAddressUrl= threadFlag.split(";")[0];
					String fileid25 = threadFlag.split(";")[1];
					
					Log.i("fileAddressUrl", fileAddressUrl);
					//得到请求服务端结果
					String connResultStr25 = null;
					connResultStr25 = getConnectionResultToService(serviceip+fileAddressUrl + "unid=" + fileid25);

			     // 发送操作指令
					Message ms25 = new Message();
					
					if(connResultStr25 != null && !connResultStr25.equals("")){
						
						JSONArray fileUrlArray = new JSONArray(connResultStr25);
						JSONObject fileUrlObj = (JSONObject) fileUrlArray.get(0);
						String fileUrl = fileUrlObj.getString("address");
						ms25.what = LOAD_DONE;
						ms25.arg1 = 25;
						Bundle bundle25 = new Bundle();
						bundle25.putString("fileAddress", serviceip+fileUrl);
						ms25.setData(bundle25);
						
					}else{
						ms25.what = LOAD_FAIL;
						ms25.arg1 = 25;
					}
					helpHandler.sendMessage(ms25);
					break;
				//删除已上传附件
				case 26:
					String  deleteUpLoadFileUrl = threadFlag.split(";")[0];
					String fileid26 = threadFlag.split(";")[1];
					String paramsStr26 = "unid=" + fileid26 + "&userid=" + userid;
					Log.i("deleteUpLoadFileUrl", serviceip+deleteUpLoadFileUrl + paramsStr26);
					//得到请求服务端结果
					String connResultStr26 = null;
					connResultStr26 = getConnectionResultToService(serviceip+deleteUpLoadFileUrl + paramsStr26);

			        // 发送操作指令
					Message ms26 = new Message();
					
					if(connResultStr26.equals("true")){

						ms26.what = LOAD_DONE;
						ms26.arg1 = 26;
						Bundle bundle26 = new Bundle();
						bundle26.putString("fileAddress", serviceip+connResultStr26);
						ms26.setData(bundle26);
						
					}else{
						ms26.what = LOAD_FAIL;
						ms26.arg1 = 26;
					}
					helpHandler.sendMessage(ms26);
					break;
				//获取商超位置和上传过照片的人员信息
				case 27:
					Log.i("mapQueryPersonUrl", threadFlag);

					//得到请求服务端结果
					String connResultStr27_1 = null;
					String connResultStr27_2 = null;
					connResultStr27_1 = getConnectionResultToService(serviceip + PathConfig.mapQueryPersonUrl);
					connResultStr27_2 = getConnectionResultToService(serviceip + PathConfig.marketPointUrl);

					JSONArray jsonArray27_1 = new JSONArray(connResultStr27_1);
					JSONArray jsonArray27_2 = new JSONArray(connResultStr27_2);
			        //准备要添加的数据条目
			        ArrayList personList = new ArrayList<Map<String,Object>>();
			        ArrayList marketPointList = new ArrayList<Map<String,Object>>();
			        
			        //人员json数据解析
					for (int i = 0; i < jsonArray27_1.length(); i++) {
						JSONObject jsonObj = (JSONObject) jsonArray27_1.get(i);
						Map map = new HashMap();
						Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
						for (Iterator iterator = jsonObj.keys(); iterator
								.hasNext();) {
							String fieldName = (String) iterator.next();
							Log.i("key", fieldName);
							map.put(fieldName, jsonObj.get(fieldName));
							
						}
						personList.add(map);
						
					}
					
					//商超位置json数据解析
					for (int i = 0; i < jsonArray27_2.length(); i++) {
						JSONObject jsonObj = (JSONObject) jsonArray27_2.get(i);
						Map map = new HashMap();
						Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
						for (Iterator iterator = jsonObj.keys(); iterator
								.hasNext();) {
							String fieldName = (String) iterator.next();
							Log.i("key", fieldName);
							map.put(fieldName, jsonObj.get(fieldName));
							
						}
						marketPointList.add(map);
						
					}
					
			     // 发送操作指令
					Message ms27 = new Message();
					
					if(connResultStr27_1 != null && !connResultStr27_1.equals("") && connResultStr27_2 != null && !connResultStr27_2.equals("")){

						ms27.what = LOAD_DONE;
						ms27.arg1 = 27;
						Bundle bundle27 = new Bundle();
						bundle27.putStringArrayList("marketPointList", marketPointList);
						ms27.setData(bundle27);
						//存储人员信息
						setResultList(personList);
					}else{
						ms27.what = LOAD_FAIL;
						ms27.arg1 = 27;
					}
					helpHandler.sendMessage(ms27);
					break;
				//加载配置图片
				case 28:
					Log.i("loadServiceImg", threadFlag);
					
					
					//得到请求服务端结果
					String connResultStr28 = null;
					connResultStr28 = getConnectionResultToService(serviceip+PathConfig.allAppServiceImg);

			     // 发送操作指令
					Message ms28 = new Message();
						
					if(connResultStr28 != null){
						// 数据加载成功，发送成功指令
					
						JSONArray jsonArray28 = new JSONArray(connResultStr28);
				        //准备要添加的数据条目
				        ArrayList allServiceImgList = new ArrayList<Map<String,Object>>();
				        
				        //json数据解析并下载服务端图片
						for (int i = 0; i < jsonArray28.length(); i++) {
							JSONObject jsonObj = (JSONObject) jsonArray28.get(i);
							Map map = new HashMap();
							Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
							for (Iterator iterator = jsonObj.keys(); iterator
									.hasNext();) {
								String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								String imgUrl = (String) jsonObj.get(fieldName);
								FunctionUtil.saveWebFileToSDCard(context, imgUrl);
							}
							
						}

						ms28.what = LOAD_DONE;
						ms28.arg1 = 28;
						
					}else{
						ms28.what = LOAD_FAIL;
						ms28.arg1 = 28;
					}
					helpHandler.sendMessage(ms28);
					break;
				//获取节点信息
				case 29:
					Log.i("nodeserviceUrl", threadFlag);
					
					
					//得到请求服务端结果
					String connResultStr29 = null;
					connResultStr29 = getConnectionResultToService(threadFlag);

			     // 发送操作指令
					Message ms29 = new Message();
						
					if(connResultStr29 != null || connResultStr29.equals("")){
						// 数据加载成功，发送成功指令
					
						JSONArray jsonArray29 = new JSONArray(connResultStr29);
				        //准备要添加的数据条目
				        ArrayList nodeList = new ArrayList<Map<String,Object>>();
				        
				        //json数据解析
						for (int i = 0; i < jsonArray29.length(); i++) {
							JSONObject jsonObj = (JSONObject) jsonArray29.get(i);
							Map map = new HashMap();
							Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
							for (Iterator iterator = jsonObj.keys(); iterator
									.hasNext();) {
								String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								map.put(fieldName, jsonObj.get(fieldName));
							}
							nodeList.add(map);
							
						}
						Bundle bundle29 = new Bundle();
						bundle29.putStringArrayList("nodeList", nodeList);

						ms29.setData(bundle29);
						ms29.what = LOAD_DONE;
						ms29.arg1 = 29;
						
					}else{
						ms29.what = LOAD_FAIL;
						ms29.arg1 = 29;
					}
					helpHandler.sendMessage(ms29);
					break;
				//保存填写的意见
				case 30:
					Log.i("nodeserviceUrl", threadFlag);
					String urlStr30 = threadFlag.split(";")[0];
					String suggestion = threadFlag.split(";")[1];
					urlStr30 = urlStr30.split("?")[0];
					String workcodes = urlStr30.split("?")[1];
					String workcodeTitle = workcodes.split("=")[0];
					String workcodeText = workcodes.split("=")[1];
					String paramsStr = workcodeTitle+":"+workcodeText+"$participant:"+userid+"$note:"+suggestion;
					//得到请求服务端结果
					String connResultStr30 = null;
					connResultStr30 = httpPostConnectionService(urlStr30,paramsStr);

			     // 发送操作指令
					Message ms30 = new Message();
						
					if(connResultStr30 != null && connResultStr30.equals("true")){
						// 数据加载成功，发送成功指令
					
						ms30.what = LOAD_DONE;
						ms30.arg1 = 30;
						
					}else{
						ms30.what = LOAD_FAIL;
						ms30.arg1 = 30;
					}
					helpHandler.sendMessage(ms30);
					break;
				//获取节点处理人员信息
				case 31:
					Log.i("personserviceUrl", threadFlag);
					
					String urlStr31 = threadFlag.split(";")[0];
					String actid31 = threadFlag.split(";")[1];
					//得到请求服务端结果
					String connResultStr31 = null;
					connResultStr31 = getConnectionResultToService(urlStr31+"&actid="+actid31);

			     // 发送操作指令
					Message ms31 = new Message();
						
					if(connResultStr31 != null || connResultStr31.equals("")){
						// 数据加载成功，发送成功指令
					
						JSONArray jsonArray31 = new JSONArray(connResultStr31);
				        //准备要添加的数据条目
				        ArrayList commiterList = new ArrayList<Map<String,Object>>();
				        
				        //json数据解析
						for (int i = 0; i < jsonArray31.length(); i++) {
							JSONObject jsonObj = (JSONObject) jsonArray31.get(i);
							Map map = new HashMap();
							Log.i("jsonkeys", String.valueOf(jsonObj.keys()));
							for (Iterator iterator = jsonObj.keys(); iterator
									.hasNext();) {
								String fieldName = (String) iterator.next();
								Log.i("key", fieldName);
								map.put(fieldName, jsonObj.get(fieldName));
							}
							commiterList.add(map);
							
						}
						Bundle bundle31 = new Bundle();
						bundle31.putStringArrayList("commiterList", commiterList);

						ms31.setData(bundle31);
						ms31.what = LOAD_DONE;
						ms31.arg1 = 31;
						
					}else{
						ms31.what = LOAD_FAIL;
						ms31.arg1 = 31;
					}
					helpHandler.sendMessage(ms31);
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
