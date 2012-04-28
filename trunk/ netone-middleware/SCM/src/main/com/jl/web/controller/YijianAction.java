package com.jl.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.entity.User;
import com.jl.service.YijianService;

public class YijianAction extends AbstractAction {

	/** ��־ */
	private final Logger log = Logger.getLogger(YijianAction.class);

	// ����
	// ҳ����ش���:
	// var userid = '';//Ϊ�� ��ʾ�Զ��Ե�ǰ��¼��ID
	// var vUrl = '<c:url
	// value="/common/yijian.do?method=load"/>'+'&userid='+userid;
	// Ext.Ajax.request({
	// url: vUrl,
	// success: function(response, options){
	// var responseArray = Ext.util.JSON.decode(response.responseText);
	// var yijianSelect = document.getElementById('XXXX');//ѡ����ID
	// for(var i=0; i< responseArray.length; i++){
	// var option = document.createElement('option');
	// option.text = responseArray[i].yijian;
	// option.value = responseArray[i].unid;
	// yijianSelect.options.add(option);
	// }
	// },
	// failure: function (response, options) {
	// checkAjaxStatus(response);
	// }
	// });
	public void load(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		YijianService ser = (YijianService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("yijianService");
		String userid = request.getParameter("userid");
		if (StringUtils.isEmpty(userid)) {
			User user = getOnlineUser(request);
			userid = user.getUserId();
		}
		String jsonx = ser.load(userid);
		super.writeJsonStr(response, jsonx.toString());
	}

	// ����
	// /common/yijian.do?method=insert
	public void insert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		YijianService ser = (YijianService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("yijianService");
		String userid = request.getParameter("userid");
		String yijian = request.getParameter("yijian");
		JSONObject json = new JSONObject();
		try {
			if (StringUtils.isEmpty(userid)) {
				User user = getOnlineUser(request);
				userid = user.getUserId();
			}
			if (StringUtils.isNotEmpty(yijian)) {
				yijian = yijian.trim();
			}
			String jsonx = ser.insert(userid, yijian);
			if (jsonx == null) {
				json.put("error", "yes");
				json.put("tip", "�Ѿ����ڴ����");
			} else {
				json = json.fromObject(jsonx);
			}

		} catch (Exception e) {
			json.put("error", "yes");
			json.put("tip", "����ʧ��");
			log.error("������", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

	// ɾ��
	// /common/yijian.do?method=delete
	public void delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		YijianService ser = (YijianService) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("yijianService");
		String unid = request.getParameter("unid");
		JSONObject json = new JSONObject();
		try {
			String jsonx = ser.delete(unid);
			json = json.fromObject(jsonx);
		} catch (Exception e) {
			json.put("error", "yes");
			json.put("tip", "ɾ��ʧ��");
			log.error("������", e);
		} finally {
			super.writeJsonStr(response, json.toString());
		}
	}

}
