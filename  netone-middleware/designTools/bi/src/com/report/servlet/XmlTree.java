package com.report.servlet;

import com.jl.common.netone.NetoneXmlTree;

public class XmlTree {

	/**
	 * @param args
	 */
 
	public String dataxml(String path) {
		String cname=null;
		if (path.equals("REPORTDS")) {
			cname="报表管理";
		}
		if (path.equals("BUSSFORM")) {
			cname="业务表单";
		}
		if (path.equals("DATASOURCE")) {
			cname="驱动选择";
		}
		if (path.equals("FRAMEPG")) {
			cname="页框选择";
		}
		if (path.equals("APPFRAME")) {
			cname="框架选择";
		}
		if (path.equals("DATASOURCE")) {
			cname="数据源选择";
		}if (path.equals("DYVIEW")) {
			cname="视图表单";
		}if(path.equals("SOASCRIPT")){
			cname="SOA选择";
		}if(path.equals("DEPT")){
			cname="组织机构";
		}if(path.equals("BUSSENV")){
			cname="业务属性";
		}if(path.equals("DATASOURCE")){
			cname="数据源";
		}if(path.equals("JSPAPP")){
			cname="页程序PORTLET";
		}if(path.equals("CSSFILE")){
			cname="样式资源";
		}if(path.equals("BUSSWF")){
			cname="业务流程";
		}if(path.equals("TEMPLATE")){
			cname="业务模块";
		}if(path.equals("BUSSBEAN")){
			cname="业务BEAN";
		}if(path.equals("PLAN")){
			cname="计划任务";
		}if(path.equals("ETL")){
			cname="BI分析";
		}if(path.equals("FCK")){
			cname="多彩文档";
		}if(path.equals("FILE")){
			cname="附件库";
		}if(path.equals("PIC")){
			cname="图片库";
		}if(path.equals("DICT")){
			cname="字典管理";
		}if(path.equals("GSSCHOOL")){
			cname="工商学校";
		}if(path.equals("GSXXP")){
			cname="学校权限";
		}if(path.equals("GSXXTOHTML")){
			cname="工商学校HTML";
		}if(path.equals("PORTALPG")){
			cname="门户页";
		}if(path.equals("AREA")){
			cname="区域";
		}if(path.equals("SYSROLE")){
			cname="角色分类";
		}if(path.equals("SYSROLE")){
			cname="ZYERP";
		}if(path.equals("ERPDICT")){
			cname="ERPDICT";
		}if(path.equals("CHART")){
			cname="图表";
		}if(path.equals("8B2152F001DA11E0BBC8CF6CB5625BD5_")){
			cname="产品管理";
		}if(path.equals("FD")){
			cname="fdsf";
		}if(path.equals("BANK")){
			cname="银行";
		}if(path.equals("NDCM")){
			cname="宁德移动";
		}if(path.equals("APPFRAME")){
			cname="应用开发框架";
		}if(path.equals("SYSTEMTEAM")){
			cname="群组";
		}if(path.equals("SYSTEAM")){
			cname="SYSTEAM";
		}if(path.equals("APPENTRY")){
			cname="APPENTRY";
		}if(path.equals("APPFRAMEENTRY")){
			cname="appframeEntry";
		}if(path.equals("TRANS")){
			cname="TRANS";
		}if(path.equals("REPORTDS")){
			cname="REPORTDS";
		}if(path.equals("BISUBJECT")){
			cname="BISUBJECT";
		}if(path.equals("REPORTQ")){
			cname="REPORTQ";
		}if(path.equals("ANADIG")){
			cname="ANADIG";
		}if(path.equals("DYVIEW")){
			cname="视图表单";
		}if(path.equals("PHONE")){
			cname="手机配置";
		}if(path.equals("SSOCINFIG")){
			cname="单点登录集成";
		}if(path.equals("SOASCRIPT")){
			cname="功能扩展";
		}if(path.equals("EXTENDATTRIBUTE")){
			cname="扩展功能";
		}
		NetoneXmlTree dxml=new NetoneXmlTree();
		return dxml.dataxml(path, cname);
	}
	//返回名字和formcode
	public String formdataxml(String path) {
		String cname=null; 
		if (path.equals("DYVIEW")) {
			cname="视图表单";
		}
		if (path.equals("BUSSFORM")) {
			cname="业务表单";
		}
		NetoneXmlTree dxml=new NetoneXmlTree();
		return dxml.dyfromdataxml(path, cname);
	}
	//类型区分
	public String Reportds(String path,String type) {
		String cname=null; 
		if (type.equals("chart")) {
			cname="图表选择";
		}
		if (type.equals("Report")) {
			cname="报表选择";
		}
		NetoneXmlTree dxml=new NetoneXmlTree();
		return dxml.Reportdsxml(path, cname, type);
	}
}
