package com.jl.web.controller;

import java.util.List;

import com.jl.common.workflow.DbTools;

public class ReportBaseData {

	/**
	 * 分销商信息
	 * 
	 * @return
	 */
	public static List getFClientInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from dyform.DY_61336130537483  where column19='1'");
		List list = DbTools.queryData(sb.toString());
		return list;
	}

	/**
	 * 产品大类信息
	 * 
	 * @return
	 */
	public static List getChangPingDaLeiInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from dyform.DY_521339922112143  where column5='1'");
		List list = DbTools.queryData(sb.toString());
		return list;
	}

	/**
	 * 供应商信息
	 * 
	 * @return
	 */
	public static List getGClientInfo() {
		StringBuffer sb = new StringBuffer();
		sb
				.append("select * from dyform.DY_941335942247762 where column19='1'");
		List list = DbTools.queryData(sb.toString());
		return list;
	}

	/**
	 * 成色信息
	 * 
	 * @return
	 */
	public static List getChengSeXingXiInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from dyform.DY_61336130537507 where column6='1'");
		List list = DbTools.queryData(sb.toString());
		return list;
	}

	/**
	 * 宝石名称信息
	 * 
	 * @return
	 */
	public static List getBaoShiMingChengInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from dyform.DY_61336130537504 where column6='1'");
		List list = DbTools.queryData(sb.toString());
		return list;
	}

	/**
	 * 品名信息
	 * 
	 * @return
	 */
	public static List getChangPingMingChengInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from dyform.DY_61336130537512 where column7='1'");
		List list = DbTools.queryData(sb.toString());
		return list;
	}

	/**
	 * 分销柜组信息
	 * 
	 * @return
	 */
	public static List getFenXiaoGuiZuInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from dyform.DY_61336130537510");
		List list = DbTools.queryData(sb.toString());
		return list;
	}

	/**
	 * 自定大类信息
	 * 
	 * @return
	 */
	public static List getZiDingDaLeiInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from dyform.DY_381336140843575 where column5 = '1'");
		List list = DbTools.queryData(sb.toString());
		return list;
	}

	/**
	 * 经营品牌信息
	 * 
	 * @return
	 */
	public static List getJingYingPingPaiInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from dyform.DY_381336140843568 where column5 = '1'");
		List list = DbTools.queryData(sb.toString());
		return list;
	}

	/**
	 * 店面经理
	 * 
	 * @return
	 */
	public static List getDianMianJingLiInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from dyform.DY_381336140843571 where column21 = '1' and column5='h001'");
		List list = DbTools.queryData(sb.toString());
		return list;
	}

	/**
	 * 售货员
	 * 
	 * @return
	 */
	public static List getShouHuoYuanInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from dyform.DY_381336140843571 where column21 = '1' and column5='h002'");
		List list = DbTools.queryData(sb.toString());
		return list;
	}

	/**
	 * 收银班组
	 * 
	 * @return
	 */
	public static List getShouYinBanZuInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from dyform.DY_381336140843590");
		List list = DbTools.queryData(sb.toString());
		return list;
	}

	/**
	 * 销售属性
	 * 
	 * @return
	 */
	public static List getXiaoShouShuXingInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from dyform.DY_381336140843567");
		List list = DbTools.queryData(sb.toString());
		return list;
	}
}
