//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.1.1/xslt/JavaClass.xsl

package oe.cms.web.infomodel;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfomodel;
import oe.cms.dao.blog.LayoutX;
import oe.cms.dao.infomodel.ModelDao;
import oe.frame.orm.OrmerEntry;
import oe.frame.web.util.WebStr;
import oe.frame.web.util.WebTip;
import oe.security3a.sso.Security;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Wang ting-jie 2006-08-09
 */
public class InfoModelEditAction extends Action {
	
	static ResourceBundle messages = null;
	static {
		try {
			messages = ResourceBundle.getBundle("extcss", Locale.CHINESE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 编辑资讯模型
	public ActionForward execute(ActionMapping mapping, ActionForm actionform,
			HttpServletRequest request, HttpServletResponse response) {
		InfoModelForm form = (InfoModelForm) actionform;

		String tomodel = request.getParameter("tomodel");
		if ("ok".equals(tomodel)) {
			String modelid = request.getParameter("id");
			LayoutX layoutX = (LayoutX) CmsEntry.fetchDao("layoutX");
			TCmsInfomodel inf = (TCmsInfomodel) OrmerEntry.fetchOrmer()
					.fetchQuerister().loadObject(TCmsInfomodel.class,
							new Long(modelid));
			layoutX.copyToTemplate(inf);
			try {
				response.setContentType("text/html; charset=GBK");
				response.getWriter().print(
						"<script>alert('制作成功');window.close();</script>");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		Security ser = new Security(request);

		String editFlag = request.getParameter("editFlag");// 编辑标志位

		String seeFlag = request.getParameter("seeFlag"); // 是否是"查看"标志位
		request.setAttribute("seeFlag", seeFlag);

		ModelDao modelDao = (ModelDao) CmsEntry.fetchDao("modelDao");
		if ("update".equals(editFlag)) { // 更新
			try {
				TCmsInfomodel cmsinfomodel = new TCmsInfomodel();
				try {
					BeanUtils.copyProperties(cmsinfomodel, form);
					// 扩展属性加入extcss
					String extcss = request.getParameter("extcss");
					String extendattribute = cmsinfomodel.getExtendattribute();
					
					System.out.println("extcss===="+extcss);
					// 有换extcss
					if( !"".equals(extcss) && extcss != null)
					{
						// 原先有值
						if(StringUtils.contains(extendattribute, "theme:"))
						{
							String[] extendx = StringUtils.split(extendattribute, ","); //所有的值对
							for (int i = 0; i < extendx.length; i++) 
							{
								if(StringUtils.contains(extendx[i], "theme:"))
								{
									String newtheme = "theme:" + extcss;
									extendx[i] = newtheme;
								}
							}
							String newExtendx = StringUtils.join(extendx,",");
							System.out.println("newExtendx"+newExtendx);
							cmsinfomodel.setExtendattribute(newExtendx);
						}
						else
						{
							// 加入新的
							extendattribute = extendattribute + ",theme:" + extcss;
							System.out.println("extendattribute"+extendattribute);
							cmsinfomodel.setExtendattribute(extendattribute);
						}
					}
					cmsinfomodel.setModelname(WebStr.encode(request, cmsinfomodel
							.getModelname()));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				cmsinfomodel.setAccessmode("1");
				cmsinfomodel.setUserid(WebStr.encode(request,form.getUserid()));
				cmsinfomodel.setParticipant(ser.getUserLoginName());

				boolean todo = modelDao.update(cmsinfomodel);
				if (todo) {
					WebTip
							.htmlInfoOri(
									"<script>alert('修改成功');opener.location.reload();window.close()</script>",
									response);
					// opener.location.reload()会出现BUG：父窗口会新一列空的页组，width值为0.2%
				} else {
					WebTip.htmlInfo("失败", true, response);
				}
				return null;
			} catch (Exception e) {
				request.setAttribute("updateFlag", "false");
				e.printStackTrace();
			}

		} else { // 初始化

			// 初始化模型存取模式
			// String[][] accessmode=InfomodelExtend._ACCESS;//模型存取模式
			// List accessmodeList=new ArrayList();
			// for(int i=0;i<accessmode.length;i++){
			// LabelValueBean lv=new
			// LabelValueBean(accessmode[i][1],accessmode[i][0]);
			// accessmodeList.add(lv);
			// }
			// request.setAttribute("accessmodeList",accessmodeList);

			String id = request.getParameter("id");

			TCmsInfomodel cmsinfomodel = modelDao.view(id);
			try {
				// 构造Ext主题选项
				String themes = messages.getString("themes");
				List extcss = Arrays.asList(themes.split(","));
				request.setAttribute("extcss", extcss);
				
				// 从扩展属性取原来的css
				String extend = cmsinfomodel.getExtendattribute();
				// String[] extendx = StringUtils.split(extend, ","); 
				// 取值对 key:value,
				String theme = StringUtils.substringAfter(extend, "theme:");
				// 去掉可能存在的逗号，和之后的字串
				if(StringUtils.contains(theme, ","))
				{
					theme = StringUtils.substringBefore(theme, ",");
				}
				
				System.out.println("theme="+theme);
				
				request.setAttribute("theme", theme);
				
				BeanUtils.copyProperties(form, cmsinfomodel);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("done", "");

		}

		return mapping.getInputForward();
	}
}
