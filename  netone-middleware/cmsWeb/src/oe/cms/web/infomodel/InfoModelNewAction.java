package oe.cms.web.infomodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.cfg.TCmsInfomodel;
import oe.cms.dao.infomodel.ModelDao;
import oe.cms.datasource.XMLParseImpl;
import oe.cms.datasource.XMLParser;
import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.IdServer;
import oe.frame.web.util.WebTip;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Wang ting-jie 2006-08-09
 */
public class InfoModelNewAction extends Action {

	// ������Ѷģ��
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) {
		InfoModelForm form = (InfoModelForm) actionform;
//		Security ser = new Security(request);

		String newFlag = request.getParameter("newFlag");
		ModelDao modelDao = (ModelDao) CmsEntry.fetchDao("modelDao");
		XMLParser xmlParser = new XMLParseImpl();
		System.out.println(newFlag);
		if ("new".equals(newFlag)) {
			try {
				TCmsInfomodel cmsinfomodel = new TCmsInfomodel();

				cmsinfomodel.setAccessmode("1");
				cmsinfomodel.setDescription(form.getDescription());
				cmsinfomodel.setExtendattribute("100.0%");
				cmsinfomodel.setInfoxml(XMLParser._XML_HEAD
						+ XMLParser._DEFAULT_BODY);
				cmsinfomodel.setModelname(form.getModelname());
				// ԭʼ���룺cmsinfomodel.setUserid(form.getUserid());2009-2-6
				// �ĺ����:����
				String userid = new String(form.getUserid().getBytes(
						"ISO-8859-1"), "GBK");
				if(StringUtils.indexOf(userid, "[")==0){
					//��[��ͷ����ʾ�������������ƣ���Ҫ��ȡ������,�ⲿ���ݽ����Ĳ�����[CSSFILE.CSSFILE.DEFAULT]
					String cssName  = StringUtils.substringBetween(userid, "[", "]");
					ResourceRmi rmi2 = (ResourceRmi) RmiEntry.iv("resource");
					UmsProtectedobject cssUpo = rmi2.loadResourceByNatural(cssName);
					String userid2 = cssUpo.getName()+ userid;
					cmsinfomodel.setUserid(userid2);
				}else{
					cmsinfomodel.setUserid(userid);
				}
				
		        if(StringUtils.isEmpty(request.getParameter("loginName")))
		        {
		            Security ser = new Security(request);
		            cmsinfomodel.setParticipant(ser.getUserLoginName());
		        } else
		        {
		            cmsinfomodel.setParticipant(request.getParameter("loginName").toString());
		        }

//				cmsinfomodel.setParticipant(ser.getUserLoginName());
				cmsinfomodel.setModelid(new Long(System.currentTimeMillis()));
				cmsinfomodel.setNaturalname(form.getNaturalname());
				String parentdir = request.getParameter("path");
				modelDao.create(cmsinfomodel, parentdir);

				// �����ʹ��ģ����������
				String template = request.getParameter("template");
				if (template != null && !template.equals("")) {
					// ��òο�ģ��Ķ�����Ϣ
					String tempalteid = StringUtils.substringBetween(template,
							"[", "]");
					TCmsInfomodel ii = (TCmsInfomodel) OrmerEntry.fetchOrmer()
							.fetchQuerister().loadObject(TCmsInfomodel.class,
									new Long(tempalteid));
					// ����ģ�������,���п�ģ�廯��ҳԪ��(��ģ�廯��ҳԪ��,ͨ����ִ�п�������,�����Ľ�ִ�����ô���)
					List list = new ArrayList();
					String descrition = ii.getDescription();
					String pretemplate = StringUtils.substringBetween(
							descrition, "template:", ";");
					// �ҳ����з����õ�,��Ҫ����������ģ��
					while (pretemplate != null && !pretemplate.equals("")) {
						list.add(pretemplate);
						descrition = StringUtils.substringAfter(descrition,
								"template:" + pretemplate + ";");
						pretemplate = StringUtils.substringBetween(descrition,
								"template:", ";");
					}

					// �����´�����cell��Դ������Դ�ĸ�Ŀ¼
					String belongto = cmsinfomodel.getNaturalname()
							.toUpperCase();
					ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
					// ���δ������е�ģ�廯��ҳԪ��,�����Ӧ��cell���󵥶���������
					String infoxml = ii.getInfoxml();

					/*-
					 * don add 2009-6-15 ��������ģ�洴����������<br>
					 * ����ģ��ɼ���:$[template:naturalname;] ����naturalname�ο�WEB�����ڲ���ʽ�е�<br>
					 * extendattribute�е�·����xxx.xxx.xxx<br>
					 * $[template:naturalname;]��ʾ����ģ��,�����ģ���ǹ���,��ģ��Ķ�,�����ģ�洴����portal������Ӧ�仯<br>
					 * ��û�ж���Ϊģ���ҳ����"���´���"
					 */
					List infoxmlList = xmlParser.toOBj(infoxml);
					Map cellInfoMap = new HashMap();
					for (Iterator iterator = infoxmlList.iterator(); iterator
							.hasNext();) {
						CellInfo object = (CellInfo) iterator.next();
						cellInfoMap.put(StringUtils.substringBetween(object
								.getExtendattribute(), "[", "]"), object
								.getInfoCellid());
					}

					for (Iterator iterator = list.iterator(); iterator
							.hasNext();) {
						String oldnaturalname = (String) iterator.next();
						if (cellInfoMap.containsKey(oldnaturalname)) {
							cellInfoMap.remove(oldnaturalname);
						}
					}

					// �����µ�ҳ��,���������Ҫģ�廯��ҳ���п�������,�������̰���3������
					// 1:�����µ�ҳ,2:ע���µ�ҳ����Դ��,3,�޸ĵ�ҳ���е�ҳ��Ϣ
					for (Iterator iterator = cellInfoMap.keySet().iterator(); iterator
							.hasNext();) {
						String oldnaturalname = (String) iterator.next();

						UmsProtectedobject upo = rmi
								.loadResourceByNatural(oldnaturalname);

						String oldcellid = upo.getExtendattribute();

						// 1 ������ҳ
						TCmsInfocell cell = (TCmsInfocell) OrmerEntry
								.fetchOrmer().fetchQuerister().loadObject(
										TCmsInfocell.class,
										upo.getExtendattribute());
						String newcellid = IdServer.uuid();
						cell.setCellid(newcellid);
						cell.setBelongto(belongto);

						OrmerEntry.fetchOrmer().fetchSerializer().create(cell);
						// 2 ע��ҳ����Ӧ����Դ
						UmsProtectedobject upox = new UmsProtectedobject();
						upox.setName(upo.getName());
						upox.setNaturalname(StringUtils.substringAfterLast(upo
								.getNaturalname(), "."));
						upox.setExtendattribute(newcellid);
						upox.setId(newcellid);
						upox.setActionurl(upo.getActionurl());
						upox.setAggregation(upo.getAggregation());

						rmi.addResource(upox, belongto);
						// 3 �޸��µ�ҳ���е�ҳ����
						String newNaturalname = belongto
								+ "."
								+ StringUtils.substringAfterLast(upo
										.getNaturalname(), ".");

						infoxml = infoxml.replace(oldnaturalname,
								newNaturalname);
						infoxml = infoxml.replace(oldcellid, newcellid);
					}

					// �����µ�ҳ��,���������Ҫģ�廯��ҳ���п�������,�������̰���3������
					// 1:�����µ�ҳ,2:ע���µ�ҳ����Դ��,3,�޸ĵ�ҳ���е�ҳ��Ϣ
					// for (Iterator iterator = list.iterator(); iterator
					// .hasNext();) {
					// String oldnaturalname = (String) iterator.next();
					//
					// UmsProtectedobject upo = rmi
					// .loadResourceByNatural(oldnaturalname);
					//
					// String oldcellid = upo.getExtendattribute();
					//
					// // 1 ������ҳ
					// TCmsInfocell cell = (TCmsInfocell) OrmerEntry
					// .fetchOrmer().fetchQuerister().loadObject(
					// TCmsInfocell.class,
					// upo.getExtendattribute());
					// String newcellid = IdServer.uuid();
					// cell.setCellid(newcellid);
					// cell.setBelongto(belongto);
					//
					// OrmerEntry.fetchOrmer().fetchSerializer().create(cell);
					// // 2 ע��ҳ����Ӧ����Դ
					// UmsProtectedobject upox = new UmsProtectedobject();
					// upox.setName(upo.getName());
					// upox.setNaturalname(StringUtils.substringAfterLast(upo
					// .getNaturalname(), "."));
					// upox.setExtendattribute(newcellid);
					// upox.setId(newcellid);
					// upox.setActionurl(upo.getActionurl());
					// upox.setAggregation(upo.getAggregation());
					//
					// rmi.addResource(upox, belongto);
					// // 3 �޸��µ�ҳ���е�ҳ����
					// String newNaturalname = belongto
					// + "."
					// + StringUtils.substringAfterLast(upo
					// .getNaturalname(), ".");
					//
					// infoxml = infoxml.replace(oldnaturalname,
					// newNaturalname);
					// infoxml = infoxml.replace(oldcellid, newcellid);
					//
					// }
					// �������ģ�廯����Ϣ���µ���ǰҳ����ȥ
					cmsinfomodel.setExtendattribute(ii.getExtendattribute());
					cmsinfomodel.setInfoxml(infoxml);
					modelDao.update(cmsinfomodel);
				}

				// request.setAttribute("createFlag", "true");
				// request.setAttribute("modelid", cmsinfomodel.getModelid());
				String info = "<script type='text/javascript'>alert('�����ɹ�'); window.close(); window.open('/cmsWeb/ds.do?model=ok&id="
						+ cmsinfomodel.getModelid() + "');</script>";

				WebTip.htmlInfoOri(info, response);
				return null;
			} catch (Exception e) {
				WebTip.htmlInfo("����ʧ��:" + e.getMessage(), true, response);
				e.printStackTrace();
			}
		}

		// // ��ʼ��ģ��
		// String[][] accessmode = InfomodelExtend._ACCESS;// ģ�ʹ�ȡģʽ
		// List accessmodeList = new ArrayList();
		// for (int i = 0; i < accessmode.length; i++) {
		// LabelValueBean lv = new LabelValueBean(accessmode[i][1],
		// accessmode[i][0]);
		// accessmodeList.add(lv);
		// }
		// request.setAttribute("accessmodeList", accessmodeList);

		return mapping.getInputForward();
	}
}
