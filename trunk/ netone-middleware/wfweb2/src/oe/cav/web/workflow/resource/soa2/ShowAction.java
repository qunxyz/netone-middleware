package oe.cav.web.workflow.resource.soa2;

import java.io.PrintWriter;
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
import oe.midware.workflow.client.ScriptObject;
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
 * 流程创建和执行修改2
 */
public class ShowAction extends Action {

	// 显示0
	public void show0(HttpServletRequest request, ResourceRmi resourceRmi, ShowForm sf) {
		try {
			String formcode = sf.getFormcode();
			UmsProtectedobject txtupo = new UmsProtectedobject();
			txtupo.setExtendattribute(formcode);
			List txtlist = resourceRmi.fetchResource(txtupo, null, null);
			if (txtlist != null && txtlist.size() == 1) {
				String name = ((UmsProtectedobject) txtlist.get(0)).getName();
				String naturalname = ((UmsProtectedobject) txtlist.get(0)).getNaturalname();
				String txt = name + "[" + naturalname + "]";
				sf.setTxt(txt);
				request.setAttribute("sf", sf);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// 显示1
	public DataField[] show1(HttpServletRequest request, ShowForm sf) {
		String processid = sf.getProcessid();
		// 显示1
		WorkflowView wfview = null;

		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
		} catch (Exception e) {
			e.printStackTrace();
		}
		WorkflowProcess wplist = null;
		try {
			wplist = wfview.fetchWorkflowProcess(processid);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		DataField[] df = wplist.getDataField();
		request.setAttribute("df", df);
		return df;
	}

	// 显示2
	public void show2(HttpServletRequest request, ShowForm sf) {
		WorkflowView wfview = null;
		try {
			wfview = (WorkflowView) RmiEntry.iv("wfview");
		} catch (Exception e) {
			e.printStackTrace();
		}

		String formcode = sf.getFormcode();
		String checks = sf.getChecks();
		try {
			if ("dy".equals(checks)) {
				DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
				List list = dy.fetchColumnList(formcode);
				request.setAttribute("list", list);
			} else if ("wf".equals(checks)) {
				WorkflowProcess wplist = wfview.fetchWorkflowProcess(formcode);
				DataField[] df = wplist.getDataField();
				request.setAttribute("dfX", df);
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

	public void show(HttpServletRequest request, ResourceRmi resourceRmi, ShowForm sf) {
		show0(request, resourceRmi, sf);
		show1(request, sf);
		show2(request, sf);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		ShowForm sf = (ShowForm) form;
		request.setAttribute("sf", sf);

		String task = request.getParameter("task");
		// 若是流程新建,则进入第一个页面,否则进入修改页面
		if ("first".equals(task)) {
			String chkid = request.getParameter("chkid");
			if (StringUtils.isNotEmpty(chkid)) {
				sf.setChkid(chkid);
			} else {
				chkid = sf.getChkid();
			}
			try {
				ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
				UmsProtectedobject upo = resourceRmi.loadResourceById(chkid);
				String extendattribute = upo.getExtendattribute();
				upo = resourceRmi.loadResourceById(upo.getParentdir());
				String processid = upo.getExtendattribute();
				sf.setProcessid(processid);
				ActiveBindDao xmldao = new ActiveBindDaoImpl();
				SoaObj soa = xmldao.fromXml(extendattribute);
				if (soa != null) {
					ActiveBind ab = soa.getActivity();
					ScriptObject scriptobj = soa.getScript();
					String checks = "";
					String formcode = "";
					if (ab != null) {
						String source = ab.getBindsource();
						String[] str = StringUtils.split(source, ":");
						checks = str[0];
						formcode = str[1];
						sf.setChecks(checks);
						sf.setFormcode(formcode);
						sf.setSyn(String.valueOf(ab.isSync()));
						List<Paramobj> params = ab.getParams();
						String outtext = "";
						String intext = "";
						for (Iterator iter = params.iterator(); iter.hasNext();) {
							Paramobj po = (Paramobj) iter.next();
							if ("out".equals(po.getDatamode())) {
								outtext = outtext + po.getValue() + ",";
							}
							if ("in".equals(po.getDatamode())) {
								intext = intext + po.getValue() + ",";
							}
						}
						if ("dy".equals(checks)) {
							sf.setOutdytext(outtext);
						} else if ("wf".equals(checks)) {
							sf.setOutwftext(outtext);
						} else if ("be".equals(checks)) {
							sf.setOutbetext(outtext);
						}
						sf.setInnewtext(intext);
						show(request, resourceRmi, sf);
					}
					if (scriptobj != null) {
						sf.setCdata(scriptobj.getCdata());
					}
				}
				request.setAttribute("sf", sf);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
			return mapping.findForward("first");
			// 流程第二个页面
		} else if ("step".equals(task)) {
			try {
				String chkid = sf.getChkid();
				String txt = sf.getTxt();
				String naturalname = StringUtils.substringBetween(txt, "[", "]");
				ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
				UmsProtectedobject upotmp = resourceRmi.loadResourceByNatural(naturalname);
				sf.setFormcode(upotmp.getExtendattribute());
				UmsProtectedobject upo = resourceRmi.loadResourceById(chkid);
				upo = resourceRmi.loadResourceById(upo.getParentdir());
				String processid = upo.getExtendattribute();
				sf.setProcessid(processid);

				show(request, resourceRmi, sf);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
			return mapping.findForward("step");
			// 流程第三个页面
		} else if ("next".equals(task)) {
			show1(request, sf);
			return mapping.findForward("next");
			// 流程第完成页面
		} else if ("done".equals(task)) {
			show1(request, sf);
			show2(request, sf);
			return mapping.findForward("done");
		} else if ("final".equals(task)) {
			try {
				String formcode = sf.getFormcode();
				String processid = sf.getProcessid();
				String checks = sf.getChecks();
				String chkid = sf.getChkid();
				String sync = sf.getSyn();
				ActiveBind ab = new ActiveBind();
				ActiveBindDao xmldao = new ActiveBindDaoImpl();
				ab.setName(processid);
				ab.setBindsource(checks + ":" + formcode);
				ab.setSync(Boolean.parseBoolean(sync));
				List<Paramobj> params = new ArrayList<Paramobj>();
				if ("dy".equals(checks)) {
					String scripts = sf.getOutdytext();
					String[] script = scripts.split(",");
					DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
					List list = dy.fetchColumnList(formcode);
					if (list != null && list.size() > 0) {
						int i = 0;
						for (Iterator iter = list.iterator(); iter.hasNext();) {
							TCsColumn element = (TCsColumn) iter.next();
							Paramobj po = new Paramobj();
							po.setDatamode("out");
							po.setName("");
							po.setSource(ab.getBindsource());
							po.setNode(element.getColumnid());
							if (script.length != 0 && i < script.length) {
								po.setValue(script[i]);
							} else {
								po.setValue("");
							}
							params.add(po);
							i++;
						}
					}
				} else if ("wf".equals(checks)) {
					String scripts = sf.getOutwftext();
					String[] script = scripts.split(",");
					WorkflowView wfview = null;
					try {
						wfview = (WorkflowView) RmiEntry.iv("wfview");
					} catch (Exception e) {
						e.printStackTrace();
					}
					WorkflowProcess wplist = wfview.fetchWorkflowProcess(formcode);
					DataField[] df = wplist.getDataField();
					for (int i = 0; i < df.length; i++) {
						Paramobj po = new Paramobj();
						po.setDatamode("out");
						po.setName("");
						po.setSource(ab.getBindsource());
						po.setNode(df[i].getId());
						if (script.length != 0 && i < script.length) {
							po.setValue(script[i]);
						} else {
							po.setValue("");
						}
						params.add(po);
					}
				} else if ("be".equals(checks)) {
					String scripts = sf.getOutbetext();
					String[] script = scripts.split(",");
					BeanService beansv = (BeanService) RmiEntry.iv("beanhandle");
					SoaBean beaOut = beansv.outParamDescription(formcode);
					int i = 0;
					for (Iterator iter = beaOut.getClassproperty().keySet().iterator(); iter.hasNext();) {
						String columnid = (String) iter.next();
						Paramobj po = new Paramobj();
						po.setDatamode("out");
						po.setName("");
						po.setSource(ab.getBindsource());
						po.setNode(columnid);
						if (script.length != 0 && i < script.length) {
							po.setValue(script[i]);
						} else {
							po.setValue("");
						}
						params.add(po);
						i++;
					}
				}
				DataField[] df = show1(request, sf);
				String[] script = sf.getInnewtext().split(",");
				for (int i = 0; i < df.length; i++) {
					Paramobj po = new Paramobj();
					po.setDatamode("in");
					po.setName("");
					po.setSource(ab.getBindsource());
					po.setNode(df[i].getId());
					if (script.length != 0 && i < script.length) {
						po.setValue(script[i]);
					} else {
						po.setValue("");
					}
					params.add(po);
				}
				ab.setParams(params);

				ScriptObject scriptobj = new ScriptObject();
				scriptobj.setActivityname(ab.getName());
				scriptobj.setName("");
				scriptobj.setCdata(sf.getCdata());

				xmldao.create(chkid, ab, null, scriptobj);
				request.setAttribute("result", "y");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mapping.findForward("done");
		} else {
			response.setContentType("text/xml;charset=GBK");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Expires", "0");
			PrintWriter out = null;
			StringBuffer sb = new StringBuffer("<select onclick='document.all.tmptxt.value=this.value'>");
			String tmpcheck = request.getParameter("tmpcheck");
			String extend = request.getParameter("extend");
			try {
				ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
				UmsProtectedobject upo = resourceRmi.loadResourceByNatural(extend);
				extend = upo.getExtendattribute();
				if ("dy".equals(tmpcheck)) {
					DyFormService dy = (DyFormService) RmiEntry.iv("dyhandle");
					List list = dy.fetchColumnList(extend);
					if (list != null && list.size() > 0) {
						for (Iterator iter = list.iterator(); iter.hasNext();) {
							TCsColumn element = (TCsColumn) iter.next();
							sb = sb.append("<option value=" + element.getColumnid() + ">");
							sb = sb.append(element.getColumname() + "{" + element.getHtmltype() + "}");
							sb = sb.append("</option>");
						}
						sb.append("</select><input type='text' name='tmptxt'>");
					}
					out = response.getWriter();
					out.write(sb.toString());
					out.close();
				} else if ("wf".equals(tmpcheck)) {
					WorkflowView wfview = null;
					wfview = (WorkflowView) RmiEntry.iv("wfview");
					WorkflowProcess wplist = wfview.fetchWorkflowProcess(extend);
					DataField[] df = wplist.getDataField();
					for (int i = 0; i < df.length; i++) {
						sb = sb.append("<option value=" + df[i].getId() + ">");
						sb = sb.append(df[i].getName() + "{" + df[i].getDataType().getType() + "}");
						sb = sb.append("</option>");
					}
					sb.append("</select><input type='text' name='tmptxt'>");
					out = response.getWriter();
					out.write(sb.toString());
					out.close();
				} else if ("be".equals(tmpcheck)) {
					BeanService beansv = (BeanService) RmiEntry.iv("beanhandle");
					SoaBean beaIn = beansv.inParamDescription(extend);
					SoaBean beaOut = beansv.outParamDescription(extend);
					for (Iterator iter = beaIn.getClassproperty().keySet().iterator(); iter.hasNext();) {
						String columnid = (String) iter.next();
						ClassProperty objpro = beaIn.getClassproperty().get(columnid);
						sb = sb.append("<option value=" + columnid + ">");
						sb = sb.append(objpro.getName() + "{" + objpro.getType() + "}");
						sb = sb.append("</option>");
					}
					sb
							.append("</select><input type='text' name='tmptxt'><select onclick='document.all.tmptxt2.value=this.value'>");
					for (Iterator iter = beaOut.getClassproperty().keySet().iterator(); iter.hasNext();) {
						String columnid = (String) iter.next();
						ClassProperty objpro = beaOut.getClassproperty().get(columnid);
						sb = sb.append("<option value=" + columnid + ">");
						sb = sb.append(objpro.getName() + "{" + objpro.getType() + "}");
						sb = sb.append("</option>");
					}
					sb.append("</select><input type='text' name='tmptxt2'>");
					out = response.getWriter();
					out.write(sb.toString());
					out.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
}
