package oe.security4a.web.human;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.bus.res.doc.ExcelHandler;
import oe.frame.orm.util.IdServer;
import oe.frame.web.form.RequestParamMap;
import oe.frame.web.form.RequestUtil;
import oe.midware.doc.excel.ExcelHandlerImp;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.auditingser.OperationLog;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.MultiDimData;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.seupublic.authentication.PDP;
import oe.security3a.seupublic.authentication.PDPClerkInput;
import oe.security3a.seupublic.authentication.PDPClerkOutput;
import oe.security3a.seupublic.authentication.obj.RequestCtx;
import oe.security3a.seupublic.authentication.obj.ResponseCtx;
import oe.security3a.seupublic.authentication.util.ClerkResponseToClerk;
import oe.security3a.sso.onlineuser.DefaultOnlineUserMgr;
import oe.security3a.sso.onlineuser.OnlineUser;
import oe.security3a.sso.onlineuser.OnlineUserMgr;
import oe.security3a.sso.util.SyncUser;
import oe.security3a.sso.util.SyncUser;
import oe.security4a.web.system.XmlObjListAndMDD;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


public class HumanFileAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		RequestParamMap reqmap = RequestUtil.setParamMapToRequest(request);
		File tmpf = null;
		
		OnlineUserMgr olmgr = new DefaultOnlineUserMgr();
		OnlineUser oluser = olmgr.getOnlineUser(request);
		String loginName = oluser.getLoginname();
		String code = oluser.getBelongto();
		
		if (FileUpload.isMultipartContent(request)) {
			DiskFileUpload upload = new DiskFileUpload();
			List items = null;
			try {
				items = upload.parseRequest(request);
				for (Object obj : items) {
					FileItem fileitem = (FileItem) obj;
					if (fileitem.getContentType() == null) {
						reqmap.put(fileitem.getFieldName(), fileitem.getString());
					}
					if (fileitem.getFieldName().equals("upfile")) {
						String name = UUID.randomUUID().toString();
						tmpf = File.createTempFile(name, "xls");
						fileitem.write(tmpf);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				OperationLog.info(request, "�����û�", e.getMessage(),true);
			}
		}

		if ("input".equals(reqmap.getParameter("task"))) {
			
			//���ŵĸ��ڵ�id
			String parentdir = "";
			
			RequestCtx requestctx = new RequestCtx();
			requestctx.newSubject();
			requestctx.newResource();
			requestctx.newAction();
			requestctx.getSubject().newAttribute("ope", "addClerk");
			requestctx.getSubject().newAttribute("participant", loginName);
			try {
				ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
				ExcelHandler excelHandler = new ExcelHandlerImp();
				Map meta = excelHandler.metaData(new FileInputStream(tmpf));
				List column = (List) meta.get("human");
				if (column == null) {
					throw new RuntimeException("δ����excel����Ϊhuman��sheet");
				}
				List valueNew = excelHandler.readExcel(new FileInputStream(tmpf), column, "human");
				MultiDimData newDmm = XmlObjListAndMDD.toMDD(column, valueNew);
				List list = newDmm.getDatavalue();
				if (list == null || list.size() < 1) {
					throw new RuntimeException("δ��������,���������ļ��Ƿ���ȷ!");
				}
				String value = "";
				String err = "";
				boolean b = true;
				int i = 0;
				if (StringUtils.isNotEmpty(reqmap.getParameter("id"))) {
					// ��������ѡ�еĲ��ŵ�����Ա
					for (Iterator iter = list.iterator(); iter.hasNext();) {
						Map map = (Map) iter.next();
						String naturalname = (String) map.get("����ƴ��");
						String name = (String) map.get("����");
						String widename = (String) map.get("����");
						if (StringUtils.isNotEmpty(naturalname) && StringUtils.isNotEmpty(name)) {
							if (StringUtils.isNotEmpty(widename)) {
								i = 1;
								break;
							}
						}
					}
				} else {
					// ���ĵ���Ĳ��ŵ�����Ա
					for (Iterator iter = list.iterator(); iter.hasNext();) {
						Map map = (Map) iter.next();
						String naturalname = (String) map.get("����ƴ��");
						String name = (String) map.get("����");
						String widename = (String) map.get("����");
						if (StringUtils.isNotEmpty(naturalname) && StringUtils.isNotEmpty(name)) {
							if (StringUtils.isEmpty(widename)) {
								i = 2;
								break;
							} else {
								UmsProtectedobject upo = rsrmi.loadResourceById(widename);
								if (upo == null) {
									i = 3;
									break;
								}else{
									parentdir  = upo.getParentdir();
								}
							}
						}
					}
				}
				if (i == 1) {
					reqmap.setAlertMsg("�ļ����Ѿ�ָ����Ա�Ĳ���,�޷��ڵ�ǰ�����е���!");
					request.setAttribute("ImportSuccess", "n");
					OperationLog.info(request, "�����û�", "�ļ����Ѿ�ָ����Ա�Ĳ���,�޷��ڵ�ǰ�����е���!",true);
				} else if (i == 2) {
					reqmap.setAlertMsg("�ļ���û��ָ����Ա�Ĳ�����Ϣ,�޷�����!");
					request.setAttribute("ImportSuccess", "n");
					OperationLog.info(request, "�����û�", "�ļ���û��ָ����Ա�Ĳ�����Ϣ,�޷�����!",true);
				} else if (i == 3) {
					reqmap.setAlertMsg("�ļ��еĲ�����Ϣ����ȷ!");
					request.setAttribute("ImportSuccess", "n");
					OperationLog.info(request, "�����û�", "�ļ��еĲ�����Ϣ����ȷ!",true);
				} else {
					for (Iterator iter = list.iterator(); iter.hasNext();) {
						Map map = (Map) iter.next();
						String naturalname = (String) map.get("����ƴ��");
						String name = (String) map.get("����");
						String province = (String) map.get("��Ա����");
						String widename = (String) map.get("����");
						if (StringUtils.isNotEmpty(naturalname) && StringUtils.isNotEmpty(name)) {
							if (StringUtils.isEmpty(widename)) {
								widename = reqmap.getParameter("id");
							}
							UmsProtectedobject upo = rsrmi.loadResourceById(widename);
							String description = "";
							if (StringUtils.isEmpty(StringUtils.trim(upo.getActionurl()))
									|| "null".equals(upo.getActionurl()) || "Ա��".equals(StringUtils.trim(province))) {
								description = naturalname.trim();
							} else {
								description = naturalname.trim();
							}
							String company = (String) map.get("ְ��");
							String faxNO = "";
							String phoneNO = (String) map.get("�ƶ��绰");
							if (StringUtils.isNotEmpty(phoneNO)) {
								if (StringUtils.indexOf(phoneNO, ".") > 0) {
									if (StringUtils.indexOf(phoneNO, "E10") > 0) {
										phoneNO = StringUtils.substringBefore(phoneNO, "E10");
										phoneNO = StringUtils.replace(phoneNO, ".", "");
									} else {
										throw new RuntimeException("�ƶ��绰����ȷ!");
									}
								}
							}
							String email = (String) map.get("�ʼ�");
							String remark = (String) map.get("��ע");
							//�Ȳ�ѯҪ�������Ա�治����
							Clerk clerk = new Clerk();
							clerk.setNaturalname(description);
							clerk.setDescription(description);
							clerk.setName(description);
							long exist = rsrmi.queryObjectsNumberClerk(code, clerk, null);
							if(exist != 0){
								err = err + name + " ";
								b = false;
							}
							String extendattribute = "";
							if (b) {
								if (upo != null) {
									extendattribute = upo.getNaturalname();
								} else {
									b = false;
								}
							}
							value = "name=" + name.trim() + ",naturalname=" + naturalname + ",description="
									+ description + ",company=" + company + ",province=" + province + ",faxNO=" + faxNO
									+ ",phoneNO=" + phoneNO + ",email=" + email + ",remark=" + remark
									+ ",extendattribute=" + extendattribute;
							requestctx.getResource().newAttribute(widename, value);
						} else {
							throw new RuntimeException("�ֶ�����������,�����ļ���д�Ƿ���ȷ����ڿ���!");
						}
					}
					if (!b) {
						reqmap.setAlertMsg(err + "�Ѵ���,��������!");
						request.setAttribute("ImportSuccess", "n");
						OperationLog.info(request, "�����û�", err + "�Ѵ���,��������!",true);
					} else {
						PDP pdp = new PDPClerkInput();
						if (pdp.evaluate(code, requestctx) != null) {
							request.setAttribute("ImportSuccess", "y");
							OperationLog.info(request, "�����û�", "�����û��ɹ�",true);
						} else {
							request.setAttribute("ImportSuccess", "n");
							OperationLog.info(request, "�����û�", "�����û�ʧ��",false);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				reqmap.setAlertMsg(e.getMessage());
				request.setAttribute("ImportSuccess", "n");
				OperationLog.info(request, "�����û�", e.getMessage(),false);
			}
		}
		if ("output".equals(reqmap.getParameter("task"))) {
			response.setContentType("text/html; charset=GBK");
			response.setContentType("application/x-msdownload");
			try {
				String cn = "";
				String str[] = reqmap.getParameterValues("chkid");
				for (int i = 0; i < str.length; i++) {
					if (i == 0) {
						cn = str[i];
					} else {
						cn = cn + "," + str[i];
					}
				}
				RequestCtx requestctx = new RequestCtx();
				requestctx.newSubject();
				requestctx.newResource();
				requestctx.newAction();
				requestctx.getSubject().newAttribute("ope", "getClerk");
				requestctx.getSubject().newAttribute("participant", loginName);
				requestctx.getSubject().newAttribute("condition", cn);
				PDP pdp = new PDPClerkOutput();
				response.setHeader("Content-Disposition", "attachment; filename=" + IdServer.uuid() + ".xls");
				ResponseCtx responsectx = pdp.evaluate(code, requestctx);
				MultiDimData view = new MultiDimData();
				Map<String, String> map1 = new LinkedHashMap<String, String>();
				map1.put("name", "����");
				map1.put("naturalname", "����ƴ��");
				map1.put("description", "�ʺ�");
				map1.put("phoneNO", "�ƶ��绰");
				map1.put("email", "�ʼ�");
				map1.put("province", "��Ա����");
				map1.put("deptname", "����");
				map1.put("company", "ְ��");
				map1.put("remark", "��ע");
				view.setDataColumnName(map1);
				Map<String, String> map2 = new LinkedHashMap<String, String>();
				map2.put("name", "string");
				map2.put("naturalname", "string");
				map2.put("description", "string");
				map2.put("phoneNO", "string");
				map2.put("email", "string");
				map2.put("province", "string");
				map2.put("deptname", "string");
				map2.put("company", "string");
				map2.put("remark", "string");
				view.setDataColumnType(map2);
				List clerklist = ClerkResponseToClerk.evaluate(responsectx);
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				for (Iterator iter = clerklist.iterator(); iter.hasNext();) {
					Clerk clerk = (Clerk) iter.next();
					Map<String, String> mapelement = new LinkedHashMap<String, String>();
					for (Iterator iterx = map1.keySet().iterator(); iterx.hasNext();) {
						String elementName = (String) iterx.next();
						if ("deptname".equals(elementName)) {
							mapelement.put("deptname", clerk.getDeptment());
						} else {
							mapelement.put(elementName, (String) BeanUtils.getProperty(clerk, elementName));
						}
					}
					list.add(mapelement);
				}
				view.setDatavalue(list);
				List xmlobjlist = XmlObjListAndMDD.toXmlObjList(view);
				List value = view.getDatavalue();
				ExcelHandler excelHandler = new ExcelHandlerImp();
				excelHandler.writeExcel(value, xmlobjlist, null, "human", response.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
				OperationLog.info(request, "�����û�", e.getMessage(),true);
			}
			return null;
		}
		return mapping.findForward("humanfile");
	}
}
