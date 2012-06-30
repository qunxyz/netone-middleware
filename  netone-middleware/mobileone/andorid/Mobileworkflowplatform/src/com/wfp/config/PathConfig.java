package com.wfp.config;

public class PathConfig {
	
	public static String appSDCardPath = "/mobileworkflow";		//app文件根目录
	public static String uploadImgPath = "/uploadimg";			//照片文件目录
	public static String formImgPath = "/form_upload_img";		//表单照片目录
	public static String orderImgPath = "/order_upload_img";		//工单照片目录

	//代办任务数据
	public static String agencyStr1 = "/netoneWebSerivce/myframeSvl?userid=adminx&model=0";
	//代办工单数
	public static String agencyStr2 = "/netoneWebSerivce/myframeSvl?userid=adminx&model=1";
	//已办工单数
	public static String agencyStr3 = "/netoneWebSerivce/myframeSvl?userid=adminx&model=3";
	
	//公告列表
	public static String annountcementUrlStr = "";
	
	//查询信息
	public static String queryUrlStr = "";
	
	//一级频道数据
	public static String channelMainUrlStr = "/netoneWebSerivce/modulearticleSvl?model=0";
	
	//栏目数据
	public static String channelTreeUrlStr = "/netoneWebSerivce/modulearticleSvl?model=1&parentid=";
	
	//门户文章数据
	public static String channelContentUrlStr = "/netoneWebSerivce/modulearticleSvl?model=2&catid=";
	
	//一级流程数据
	public static String processMainUrlStr = "/netoneWebSerivce/workflowSvl?naturalname=FRAMEPG.FRAMEPG&model=0";

	//分类流程数据
	public static String processDetailUrlStr = "/netoneWebSerivce/workflowSvl?model=1&parentdir=";
	
	//当前流程工单
	public static String orderUrl = "/netoneWebSerivce/worklistSvl?mode=1";
	
	//上传经维度信息
	public static String uploadPositionUrl = "/netoneWebSerivce/dyformtestSvl?";

	//获取人员经纬度数据
	public static String latlongInfoUrlStr = "/netoneWebSerivce/querytime?appname=APPFRAME.APPFRAME.HGMY.MBJC";
	
	//获取历史拍照工单数据
	public static String historyOrderUrlStr = "/netoneWebSerivce/dyformquery?";

	//获取表单数据
	public static String formUrlStr = "/netoneWebSerivce/workflowSvl?model=0";
	
	//上传图片
	public static String uploadImageUrlStr = "/netoneWebSerivce/uploadfileAction";
	
	//登录验证
	public static String loginUrlStr = "/netoneWebSerivce/loginmvl?";
	
	//IMEI登录验证
	public static String IMEILoginUrlStr = "/netoneWebSerivce/soleregister?identifying=";
	
	//获取上传附件信息
	public static String uploadFilesInfoUrlStr = "/netoneWebSerivce/numberfileaction?";
	
	//下载附件url
	public static String downLoadFileAddressUrlStr = "/netoneWebSerivce/downloadfileaction?";

	//删除附件
	public static String deleteFileUrlStr = "/netoneWebSerivce/deletefileaction?";
	
	//装载表单
	public static String loadFormUrlStr = "/netoneWebSerivce/Queryform?appname=";
	
	//保存表单数据
	public static String addFormDataUrlStr = "/netoneWebSerivce/AddSvl?appname=";
	
	//上传图片的人员信息
	public static String mapQueryPersonUrl = "/netoneWebSerivce/personnel";
	
	//商超经纬度信息
	public static String marketPointUrl = "/netoneWebSerivce/querybranch?appname=APPFRAME.APPFRAME.HGMY.SHANGCHAOXINXI";
	
	//所有配置图片地址
	public static String allAppServiceImg = "/netoneWebSerivce/allimage?appname=PHONE.PHONE.SHOUJI";
	
	//资源目录
	public static String sourcesDir = "/netoneWebSerivce/phconfigservice?naturalname=";
	
	//网页免登录前缀地址
	public static String freeLoginPrefix = "http://112.5.5.114:8080/Security3A/login?username=";
	
	
	//一级频道图片服务端地址
	public static String channelImgWebUrl = "/netoneWebSerivce/pic/part.png";
	
	//一级流程图片服务端地址
	public static String processMainImgWebUrl = "/netoneWebSerivce/pic/processes.png";
	
	//分类流程图片服务端地址
	public static String processDetailImgWebUrl = "/netoneWebSerivce/pic/process_detail.png";


	
}
