package oe.cav.web.workflow.resource.soa;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cav.bean.logic.column.TCsColumn;
import oe.mid.soa.bean.BeanService;
import oe.mid.soa.bean.ClassProperty;
import oe.mid.soa.bean.SoaBean;
import oe.midware.dyform.service.DyFormService;
import oe.midware.workflow.client.ActiveBind;
import oe.midware.workflow.client.Paramobj;
import oe.midware.workflow.client.SoaObj;
import oe.midware.workflow.service.WorkflowView;
import oe.midware.workflow.service.soatools.ActiveBindDao;
import oe.midware.workflow.service.soatools.ActiveBindDaoImpl;
import oe.midware.workflow.xpdl.model.data.DataField;
import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 * 流程创建和执行修改
 */
public class ShowAction extends Action {

	public void show(HttpServletRequest request, ResourceRmi resourceRmi, String processid, String checks,
			String formcode) {
		request.setAttribute("checks", checks);
		request.setAttribute("formcode", formcode);
		request.setAttribute("processid", processid);
		try {
			// 显示0
			UmsProtectedobject txtupo = new UmsProtectedobject();
			txtupo.setExtendattribute(formcode);
			List txtlist = resourceRmi.fetchResource(txtupo, null, null);
			if (txtlist != null && txtlist.size() == 1) {
				String name = ((UmsProtectedobject) txtlist.get(0)).getName();
				request.setAttribute("txt", name + "[" + formcode + "]");
			}
			// 显示1
			WorkflowView wfview = null;

			try {
				wfview = (WorkflowView) RmiEntry.iv("wfview");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			WorkflowProcess wplist = wfview.fetchWorkflowProcess(processid);
			DataField[] df = wplist.getDataField();
			for (int i = 0; i < df.length; i++) {
				System.out.println(df[i].getName());
				System.out.println(df[i].getId());
			}
			request.setAttribute("df", df);
			// 显示2
			if ("dy".equals(checks)) {
				DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
				List list = dy.fetchColumnList(formcode);
				for (Iterator iter = list.iterator(); iter.hasNext();) {
					TCsColumn element = (TCsColumn) iter.next();
					System.out.println(element.getColumname());
					System.out.println(element.getColumnid());
				}
				request.setAttribute("list", list);
			} else if ("wf".equals(checks)) {
				DataField[] dfX = wplist.getDataField();
				for (int i = 0; i < dfX.length; i++) {
					System.out.println(dfX[i].getName());
					System.out.println(dfX[i].getId());
				}
				request.setAttribute("dfX", dfX);
			} else if ("be".equals(checks)) {
				BeanService beansv = (BeanService) RmiEntry.iv("beanhandle");
				SoaBean beaIn = beansv.inParamDescription(formcode);
				SoaBean beaOut = beansv.outParamDescription(formcode);
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				for (Iterator iter = beaIn.getClassproperty().keySet().iterator(); iter.hasNext();) {
					String columnid = (String) iter.next();
					ClassProperty objpro = beaIn.getClassproperty().get(columnid);
					Map<String, String> map = new LinkedHashMap<String, String>();
					map.put("id", columnid);
					map.put("name", objpro.getName());
					list.add(map);
				}
				request.setAttribute("list", list);
				List<Map<String, String>> list2 = new ArrayList<Map<String, String>>();
				for (Iterator iter = beaOut.getClassproperty().keySet().iterator(); iter.hasNext();) {
					String columnid = (String) iter.next();
					ClassProperty objpro = beaOut.getClassproperty().get(columnid);
					Map<String, String> map = new LinkedHashMap<String, String>();
					map.put("id", columnid);
					map.put("name", objpro.getName());
					list2.add(map);
				}
				request.setAttribute("list2", list2);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String task = request.getParameter("task");
		// 若是流程新建,则进入第一个页面,否则进入修改页面
		if ("beforeshow".equals(task)) {
			String id = request.getParameter("chkid");
			request.setAttribute("chkid", id);
			try {
				ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
				UmsProtectedobject upo = resourceRmi.loadResourceById(id);
				String extendattribute = upo.getExtendattribute();
				upo = resourceRmi.loadResourceById(upo.getParentdir());
				String processid = upo.getExtendattribute();
				ActiveBindDao xmldao = new ActiveBindDaoImpl();
				SoaObj soa = xmldao.fromXml(extendattribute);
				if (soa != null) {
					ActiveBind ab = soa.getActivity();
					String checks = "";
					String formcode = "";
					if (ab != null) {
						String source = ab.getBindsource();
						String[] str = StringUtils.split(source, ":");
						checks = str[0];
						formcode = str[1];
						request.setAttribute("params", ab.getParams());
						request.setAttribute("syn", ab.isSync());

						show(request, resourceRmi, processid, checks, formcode);
					} else {
						request.setAttribute("params", "");
						return mapping.findForward("beforeshow");
					}
				} else {
					request.setAttribute("params", "");
					return mapping.findForward("beforeshow");
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
			return mapping.findForward("modifyshow");
			// 流程新建,进入第二个页面
		} else if ("wfB".equals(task)) {
			try {
				String id = request.getParameter("chkid");
				request.setAttribute("chkid", id);
				String txt = request.getParameter("txt");
				String formcode = StringUtils.substringBetween(txt, "[", "]");
				String checks = request.getParameter("checks");
				ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
				UmsProtectedobject upo = resourceRmi.loadResourceById(id);
				upo = resourceRmi.loadResourceById(upo.getParentdir());
				String processid = upo.getExtendattribute();

				show(request, resourceRmi, processid, checks, formcode);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
			// 流程第二个页面新建或修改执行完成
		} else if ("show".equals(task)) {
			try {
				String results = request.getParameter("totalresult");
				String formcode = request.getParameter("formcode");
				String processid = request.getParameter("processid");
				String checks = request.getParameter("checks");
				String id = request.getParameter("chkid");
				String sync = request.getParameter("syn");
				ActiveBind ab = new ActiveBind();
				ActiveBindDao xmldao = new ActiveBindDaoImpl();
				ab.setName(processid);
				ab.setBindsource(checks + ":" + formcode);
				ab.setSync(Boolean.parseBoolean(sync));
				List<Paramobj> params = new ArrayList<Paramobj>();
				if (StringUtils.isNotEmpty(results)) {
					String[] result = StringUtils.split(results, "@");
					if (result != null && result.length > 0) {
						for (int i = 0; i < result.length; i++) {
							String[] value = StringUtils.split(result[i], ".");
							Paramobj po = new Paramobj();
							if (value.length == 4) {
								po.setDatamode(value[3]);
							} else {
								po.setDatamode(value[2]);
							}
							po.setName(value[0]);
							po.setSource(ab.getBindsource());
							po.setNode(value[1]);
							if (value.length == 4) {
								po.setScript(value[2]);
							} else {
								po.setScript("");
							}
							params.add(po);
						}
					}
				}
				ab.setParams(params);
				xmldao.create(id, ab, null, null);
				request.setAttribute("result", "y");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mapping.findForward("show");

	}
}
