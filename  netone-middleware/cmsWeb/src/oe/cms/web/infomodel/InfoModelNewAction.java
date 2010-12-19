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

	// 新增资讯模型
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
				// 原始代码：cmsinfomodel.setUserid(form.getUserid());2009-2-6
				// 改后代码:如下
				String userid = new String(form.getUserid().getBytes(
						"ISO-8859-1"), "GBK");
				if(StringUtils.indexOf(userid, "[")==0){
					//以[开头，表示不传递中文名称，需要获取中文名,外部传递进来的参数如[CSSFILE.CSSFILE.DEFAULT]
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

				// 如果有使用模板的情况处理
				String template = request.getParameter("template");
				if (template != null && !template.equals("")) {
					// 获得参考模板的对象信息
					String tempalteid = StringUtils.substringBetween(template,
							"[", "]");
					TCmsInfomodel ii = (TCmsInfomodel) OrmerEntry.fetchOrmer()
							.fetchQuerister().loadObject(TCmsInfomodel.class,
									new Long(tempalteid));
					// 保存模板对象中,所有可模板化的页元素(有模板化的页元素,通常会执行拷贝创建,其他的将执行引用创建)
					List list = new ArrayList();
					String descrition = ii.getDescription();
					String pretemplate = StringUtils.substringBetween(
							descrition, "template:", ";");
					// 找出所有非引用的,需要独立拷贝的模块
					while (pretemplate != null && !pretemplate.equals("")) {
						list.add(pretemplate);
						descrition = StringUtils.substringAfter(descrition,
								"template:" + pretemplate + ";");
						pretemplate = StringUtils.substringBetween(descrition,
								"template:", ";");
					}

					// 所有新创建的cell资源的子资源的根目录
					String belongto = cmsinfomodel.getNaturalname()
							.toUpperCase();
					ResourceRmi rmi = (ResourceRmi) RmiEntry.iv("resource");
					// 依次处理所有的模板化的页元素,将其对应的cell对象单独拷贝出来
					String infoxml = ii.getInfoxml();

					/*-
					 * don add 2009-6-15 修正基于模版创建歧义问题<br>
					 * 制作模板可加入:$[template:naturalname;] 其中naturalname参考WEB布局内部格式中的<br>
					 * extendattribute中的路径名xxx.xxx.xxx<br>
					 * $[template:naturalname;]表示创建模版,且这个模版是共享,若模版改动,则基于模版创建的portal都会相应变化<br>
					 * 而没有定义为模版的页进行"重新创建"
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

					// 处理新的页组,针对所有需要模板化的页进行拷贝创建,整个过程包括3个步骤
					// 1:创建新的页,2:注册新的页到资源中,3,修改当页组中的页信息
					for (Iterator iterator = cellInfoMap.keySet().iterator(); iterator
							.hasNext();) {
						String oldnaturalname = (String) iterator.next();

						UmsProtectedobject upo = rmi
								.loadResourceByNatural(oldnaturalname);

						String oldcellid = upo.getExtendattribute();

						// 1 创建新页
						TCmsInfocell cell = (TCmsInfocell) OrmerEntry
								.fetchOrmer().fetchQuerister().loadObject(
										TCmsInfocell.class,
										upo.getExtendattribute());
						String newcellid = IdServer.uuid();
						cell.setCellid(newcellid);
						cell.setBelongto(belongto);

						OrmerEntry.fetchOrmer().fetchSerializer().create(cell);
						// 2 注册页所对应的资源
						UmsProtectedobject upox = new UmsProtectedobject();
						upox.setName(upo.getName());
						upox.setNaturalname(StringUtils.substringAfterLast(upo
								.getNaturalname(), "."));
						upox.setExtendattribute(newcellid);
						upox.setId(newcellid);
						upox.setActionurl(upo.getActionurl());
						upox.setAggregation(upo.getAggregation());

						rmi.addResource(upox, belongto);
						// 3 修改新的页组中的页引用
						String newNaturalname = belongto
								+ "."
								+ StringUtils.substringAfterLast(upo
										.getNaturalname(), ".");

						infoxml = infoxml.replace(oldnaturalname,
								newNaturalname);
						infoxml = infoxml.replace(oldcellid, newcellid);
					}

					// 处理新的页组,针对所有需要模板化的页进行拷贝创建,整个过程包括3个步骤
					// 1:创建新的页,2:注册新的页到资源中,3,修改当页组中的页信息
					// for (Iterator iterator = list.iterator(); iterator
					// .hasNext();) {
					// String oldnaturalname = (String) iterator.next();
					//
					// UmsProtectedobject upo = rmi
					// .loadResourceByNatural(oldnaturalname);
					//
					// String oldcellid = upo.getExtendattribute();
					//
					// // 1 创建新页
					// TCmsInfocell cell = (TCmsInfocell) OrmerEntry
					// .fetchOrmer().fetchQuerister().loadObject(
					// TCmsInfocell.class,
					// upo.getExtendattribute());
					// String newcellid = IdServer.uuid();
					// cell.setCellid(newcellid);
					// cell.setBelongto(belongto);
					//
					// OrmerEntry.fetchOrmer().fetchSerializer().create(cell);
					// // 2 注册页所对应的资源
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
					// // 3 修改新的页组中的页引用
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
					// 将处理过模板化的信息更新到当前页组中去
					cmsinfomodel.setExtendattribute(ii.getExtendattribute());
					cmsinfomodel.setInfoxml(infoxml);
					modelDao.update(cmsinfomodel);
				}

				// request.setAttribute("createFlag", "true");
				// request.setAttribute("modelid", cmsinfomodel.getModelid());
				String info = "<script type='text/javascript'>alert('创建成功'); window.close(); window.open('/cmsWeb/ds.do?model=ok&id="
						+ cmsinfomodel.getModelid() + "');</script>";

				WebTip.htmlInfoOri(info, response);
				return null;
			} catch (Exception e) {
				WebTip.htmlInfo("创建失败:" + e.getMessage(), true, response);
				e.printStackTrace();
			}
		}

		// // 初始化模型
		// String[][] accessmode = InfomodelExtend._ACCESS;// 模型存取模式
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
