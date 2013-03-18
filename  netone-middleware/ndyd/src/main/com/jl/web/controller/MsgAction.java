package com.jl.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jl.common.dyform.DyFormBuildHtml;
import com.jl.common.dyform.DyFormBuildHtmlExt;
import com.jl.common.msg.MsgIfc;
import com.jl.entity.User;

public class MsgAction extends AbstractAction {

	// ��Ϣ�б�
	public ActionForward msgListView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MsgIfc ins = (MsgIfc) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("msgIfc");
		User user = getOnlineUser(request);
		String usercode = user.getUserCode();
		String type = request.getParameter("type");
		if (StringUtils.isEmpty(type)) {
			type = "01";
		}
		// type ��Ϣ����Type=01 ��ʾȫ����Ϣ��02 ��������Ϣ03 ������Ϣ��04 �ᵽ�ҵ�05 ���� 06 ת�� 00ԭ�Ĵ������б�
		List list = ins.msgList(usercode, type, "", "", "");
		request.setAttribute("msglist", list);
		List memeberlist = ins.myGroupAndMember(usercode);
		request.setAttribute("memeberlist", memeberlist);
		String forward = "/msg/msglist.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true��ʹ��ת��,Ĭ����false����ת��
		return af;
	}

	// ��Ϣ�б�
	public ActionForward msgdata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MsgIfc ins = (MsgIfc) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("msgIfc");
		User user = getOnlineUser(request);
		String usercode = user.getUserCode();
		String type = request.getParameter("type");
		if (StringUtils.isEmpty(type)) {
			type = "01";
		}
		// type ��Ϣ����Type=01 ��ʾȫ����Ϣ��02 ��������Ϣ03 ������Ϣ��04 �ᵽ�ҵ�05 ���� 06 ת�� 00ԭ�Ĵ������б�
		List list = ins.msgList(usercode, type, "", "", "");
		request.setAttribute("msglist", list);
		String forward = "/msg/msgdata.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true��ʹ��ת��,Ĭ����false����ת��
		return af;
	}

	// ����
	public void newMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MsgIfc ins = (MsgIfc) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("msgIfc");
		User user = getOnlineUser(request);
		String msgtos = request.getParameter("msgto");
		String fileids = request.getParameter("fileids");
		String[] filestrs = null;
		if (StringUtils.isNotEmpty(fileids)) {
			filestrs = fileids.split(",");
		}
		JSONObject json = new JSONObject();
		if (StringUtils.isNotEmpty(msgtos)) {
			String[] msgto = msgtos.split(",");
			String lsh = "";
			for (int i = 0; i < msgto.length; i++) {
				String msgbody = request.getParameter("msgbody");
				String comment = request.getParameter("comment");
				String usercode = user.getUserCode();

				lsh = ins.newMsg(usercode, msgto[i], msgbody, "0"
						.equals(comment) ? false : true);

				if (filestrs!=null){
				for (int j = 0; j < filestrs.length; j++) {
					com.jl.entity.File file = (com.jl.entity.File) getCommonDAO()
							.findForObject("File.selectFileById", filestrs[j]);
					if (file != null) {
						file.setD_unid(lsh);
						file = (com.jl.entity.File) getCommonDAO().insert(
								"File.insert", file);
					}
				}
				}
			}

			if (StringUtils.isNotEmpty(lsh)) {
				json.put("lsh", lsh);
			} else {
				json.put("error", "yes");
				json.put("tip", "����ʧ��");
			}
		} else {
			json.put("error", "yes");
			json.put("tip", "����ʧ��,�����߲���Ϊ��");
		}

		super.writeJsonStr(response, json.toString());
	}

	// ת��
	public void forwardMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MsgIfc ins = (MsgIfc) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("msgIfc");
		User user = getOnlineUser(request);
		String msgto = request.getParameter("msgto");
		String msgbody = request.getParameter("msgbody");
		String source = request.getParameter("source");
		String usercode = user.getUserCode();
		String lsh = ins.forwardMsg(usercode, msgto, msgbody, false, source);
		JSONObject json = new JSONObject();
		if (StringUtils.isNotEmpty(lsh)) {
			json.put("lsh", lsh);
		} else {
			json.put("error", "yes");
			json.put("tip", "����ʧ��");
		}
		super.writeJsonStr(response, json.toString());
	}

	// ɾ����Ϣ
	public void delMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MsgIfc ins = (MsgIfc) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("msgIfc");
		String lsh = request.getParameter("lsh");
		boolean f = ins.delMsg(lsh);
		JSONObject json = new JSONObject();
		if (f) {
			json.put("lsh", lsh);
		} else {
			json.put("error", "yes");
			json.put("tip", "ɾ��ʧ��");
		}
		super.writeJsonStr(response, json.toString());
	}

	// ɾ������
	public void delComment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MsgIfc ins = (MsgIfc) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("msgIfc");
		String lsh = request.getParameter("lsh");
		boolean f = ins.delComment(lsh);
		JSONObject json = new JSONObject();
		if (f) {
			json.put("lsh", lsh);
		} else {
			json.put("error", "yes");
			json.put("tip", "ɾ��ʧ��");
		}
		super.writeJsonStr(response, json.toString());
	}

	// ����
	public void commentMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		MsgIfc ins = (MsgIfc) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("msgIfc");
		User user = getOnlineUser(request);
		String msgbody = request.getParameter("msgbody");
		String source = request.getParameter("source");
		String usercode = user.getUserCode();
		String lsh = ins.commentMsg(usercode, source, msgbody);
		JSONObject json = new JSONObject();
		if (StringUtils.isNotEmpty(lsh)) {
			json.put("lsh", lsh);
		} else {
			json.put("error", "yes");
			json.put("tip", "����ʧ��");
		}
		super.writeJsonStr(response, json.toString());
	}

	// ��Ϣ�б�
	public ActionForward myGroupAndMemberView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		MsgIfc ins = (MsgIfc) WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext())
				.getBean("msgIfc");
		User user = getOnlineUser(request);
		String usercode = user.getUserCode();
		List list = ins.myGroupAndMember(usercode);
		request.setAttribute("msglist", list);
		String forward = "/msg/groupAndMemberlist.jsp";
		ActionForward af = new ActionForward(forward);
		af.setRedirect(false);
		// true��ʹ��ת��,Ĭ����false����ת��
		return af;
	}

}
