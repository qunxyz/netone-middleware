package com.jl.web.controller;

import java.util.List;

import com.jl.common.workflow.DbTools;

public class ReportBaseData {

	/**
	 * ��������Ϣ
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
	 * ��Ʒ������Ϣ
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
	 * ��Ӧ����Ϣ
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
	 * ��ɫ��Ϣ
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
	 * ��ʯ������Ϣ
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
	 * Ʒ����Ϣ
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
	 * ����������Ϣ
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
	 * �Զ�������Ϣ
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
	 * ��ӪƷ����Ϣ
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
	 * ���澭��
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
	 * �ۻ�Ա
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
	 * ��������
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
	 * ��������
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
