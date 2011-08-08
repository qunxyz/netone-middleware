package oe.cav.web.workflow.monitor.process;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;
import oe.midware.workflow.XMLException;
import oe.midware.workflow.runtime.ormobj.TWfRuntime;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;
import oe.midware.workflow.service.WorkflowConsole;
import oe.midware.workflow.service.WorkflowDesign;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.rmi.client.RmiEntry;
import oe.security3a.sso.Security;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * �򿪻�ĶԻ�����
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 * 
 */
public class RUseAccessAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
  
		String runtimeid = request.getParameter("runtimeid"); // ��־λ
		String activity = request.getParameter("activityid"); // ��־λ

		WorkflowView wfview = null;

		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String processsid = "";
		if (runtimeid != null) {
			runtimeid = runtimeid.split(",")[0];

			List wList = null;
			
			try {
				wList = wfview.fetchRunningWorklist(runtimeid, activity);
				processsid = wfview.loadRuntime(runtimeid).getProcessid();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(wList==null||wList.size()==0){//����ڵ��Ѿ��ύ���ˣ���ô�������ٴ��ύ����������Ȩ��������������С��Ȩ������ʾʲô��������
				request.setAttribute("permission", "0");
				request.setAttribute("runtimeid", runtimeid);//û�д��У�����ֽڵ��Ѿ��ύ����ô����򿪳��ִ��� wusz
				return mapping.getInputForward();
			}

			String action = processsid + "." + activity;
			Security ser = new Security(request);
			int actvalue = ser.getUserAction(action);
			if (actvalue == 0) {
				WebTip.htmlInfo("��û�з��ʸýڵ��Ȩ��", true, response);
				return null;
			}
			// ��ҳ���ϻ���ʿ���Ȩ�� 
			request.setAttribute("permission", actvalue);

			if (wList != null && wList.size() > 0) {
				TWfWorklist worklist = (TWfWorklist) wList
						.get(wList.size() - 1);
				Activity act = null;
				try {
					act = wfview.fetchActivity(processsid, worklist
							.getActivityid());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					String forminfo = act.getExtendedAttributes().get(
							"formcode");

					if (forminfo == null) {
						forminfo = "/cavserweb/data/loseform.html";
					} else {
						String fatherlsh = act.getExtendedAttributes().get(
								"fatherlsh");
						String url = request.getContextPath().substring(0,
								request.getContextPath().lastIndexOf("/"));

						forminfo = url
								+ "/dyForm/data/showdata/createOrModifyview.do?lsh="
								+ worklist.getRuntimeid() + "&formcode="
								+ forminfo + "&fatherlsh=" + fatherlsh;
					}

					request.setAttribute("forminfo", forminfo);
				} catch (XMLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Activity[] actlist = null;
			try {
				actlist = wfview.fetchWorkflowProcess(processsid).getActivity();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List listact = Arrays.asList(actlist);
			request.setAttribute("act", listact);

		}

		request.setAttribute("runtimeid", runtimeid);
		request.setAttribute("actid", activity);
		request.setAttribute("processid", processsid);

		return mapping.getInputForward();
	}

}
