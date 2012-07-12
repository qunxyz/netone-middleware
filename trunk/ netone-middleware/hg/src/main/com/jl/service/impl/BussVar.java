/**
 * 
 */
package com.jl.service.impl;

/**
 * 应用系统变量
 * 
 * @author Don(cai.you.dun)
 * @version 1.0.0 2011-1-10 下午05:15:13
 * @history
 */
public class BussVar {

	/** 组织机构变量 */
	/** 根 */
	public final static String BUSSTYPE_0 = "";
	/** 省公司 */
	public final static String BUSSTYPE_1 = "1";
	/** 大营销部 */
	public final static String BUSSTYPE_2 = "2";
	/** 营销部 */
	public final static String BUSSTYPE_3 = "3";
	/** 经销商 */
	public final static String BUSSTYPE_4 = "4";
	/** 分销商 */
	public final static String BUSSTYPE_5 = "5";
	/** 业务主任 */
	public final static String BUSSTYPE_X = "x";
	/** 仓库 */
	public final static String BUSSTYPE_S = "s";
	public final static String BUSSTYPE_S1 = "s1";// 省公司仓库类型
	public final static String BUSSTYPE_S2 = "s2";// 经销商仓库类型
	/** 目录 */
	public final static String BUSSTYPE_D = "-1";

	/** 报表汇总依据变量 */
	/** 省公司 */
	public final static String GROUPBY_1 = "1";
	/** 大营销部 */
	public final static String GROUPBY_2 = "2";
	/** 营销部 */
	public final static String GROUPBY_3 = "3";
	/** 经销商 */
	public final static String GROUPBY_4 = "4";
	/** 分销商 */
	public final static String GROUPBY_5 = "5";
	/** 业务主任 */
	public final static String GROUPBY_X = "x";
	/** 业务类型 */
	public final static String GROUPBY_T = "type";
	/** 组 */
	public final static String GROUPBY_P_1 = "p1";
	/** 产品分类 */
	public final static String GROUPBY_P_2 = "p2";
	/** 产品 */
	public final static String GROUPBY_P_3 = "p3";

	/** 订单类型 */
	/** 正常订单 */
	public final static String INDENT_TYPE_1 = "1";
	/** 广告用酒 */
	public final static String INDENT_TYPE_2 = "2";
	/** 退货订单 */
	public final static String INDENT_TYPE_3 = "3";
	/** 广告用酒退货单 */
	public final static String INDENT_TYPE_4 = "4";

	/** 计划类型 */
	/** 正常计划 */
	public final static String PLAN_TYPE_1 = "1";
	/** 追加计划 */
	public final static String PLAN_TYPE_2 = "2";
	/** 取消计划 */
	public final static String PLAN_TYPE_3 = "3";
	/** 计划调帐 */
	public final static String PLAN_TYPE_4 = "4";

	public final static String SESSION_USER = "_SESSION_USER";

	/**
	 * 返回业务类型
	 * 
	 * @param type
	 * @return
	 */
	public static String returnBussType(String type) {
		String typeName = "";
		if (BUSSTYPE_1.equals(type)) {
			typeName = "省公司";
		} else if (BUSSTYPE_2.equals(type)) {
			typeName = "大营销部";
		} else if (BUSSTYPE_3.equals(type)) {
			typeName = "营销部";
		} else if (BUSSTYPE_4.equals(type)) {
			typeName = "经销商";
		} else if (BUSSTYPE_5.equals(type)) {
			typeName = "分销商";
		} else if (BUSSTYPE_S.equals(type)) {
			typeName = "仓库";
		} else if (BUSSTYPE_D.equals(type)) {
			typeName = "区域";
		} else {
			typeName = "未知";
		}
		return typeName;
	}

	/**
	 * 返回订单类型
	 * 
	 * @param type
	 * @return
	 */
	public static String returnIndentType(String type) {
		String typeName = "";
		if (INDENT_TYPE_1.equals(type)) {
			typeName = "正常订单";
		} else if (INDENT_TYPE_2.equals(type)) {
			typeName = "广告用酒";
		} else if (INDENT_TYPE_3.equals(type)) {
			typeName = "退货订单";
		} else if (INDENT_TYPE_4.equals(type)) {
			typeName = "广告用酒退货单";
		} else {
			typeName = "未知";
		}
		return typeName;
	}

	/**
	 * 返回计划类型
	 * 
	 * @param type
	 * @return
	 */
	public static String returnPlanType(String type) {
		String typeName = "";
		if (PLAN_TYPE_1.equals(type)) {
			typeName = "正常计划";
		} else if (PLAN_TYPE_2.equals(type)) {
			typeName = "追加计划";
		} else if (PLAN_TYPE_3.equals(type)) {
			typeName = "取消计划";
		} else if (PLAN_TYPE_4.equals(type)) {
			typeName = "计划调帐";
		} else {
			typeName = "未知";
		}
		return typeName;
	}

	/**
	 * 取得账务方向
	 * 
	 * @param x
	 * @return
	 */
	public static String getFinanceDirection(Double value) {
		if (value > 0) {
			return "贷";
		} else if (value == 0) {
			return "平";
		} else if (value < 0) {
			return "借";
		}
		return "";
	}

	/**
	 * 转成正数
	 * 
	 * @param value
	 * @return
	 */
	public static double convertPlus(Double value) {
		if (value < 0) {
			return -1 * value;
		}
		return value;
	}

}
