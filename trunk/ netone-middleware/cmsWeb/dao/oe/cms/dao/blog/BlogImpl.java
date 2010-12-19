package oe.cms.dao.blog;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.cms.CmsBean;
import oe.cms.CmsEntry;
import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.cfg.TCmsInfomodel;
import oe.cms.dao.blog.util.ArticleDeal;
import oe.cms.dao.infocell.InfoCellDao;
import oe.cms.runtime.core.WiParser;
import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.IdServer;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;

public class BlogImpl implements Blog {

	public Long regeidtBlog(String userid) {
		// 检查是否已经开通过
		TCmsInfomodel modelCheck = new TCmsInfomodel();
		modelCheck.setParticipant(userid);
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
				modelCheck, null);
		if (list != null && list.size() > 0) {// 已经开通过
			return null;
		}
		Long id = new Long(System.currentTimeMillis());
		// // 创建个人文件架
		// InfoGroupDao infogroupDao = (InfoGroupDao) CmsEntry
		// .fetchDao("infogroupDao");
		// TCmsGroup group = new TCmsGroup();
		// String username = UserDao.getUserName(userid);
		// group.setDescription(username + "的个人文件夹");
		// group.setGroupname(username);
		// infogroupDao.create(group, userid);
		// // // 创建个人资源库
		// // IisFileuploadDirs dir = new IisFileuploadDirs();
		// // dir.setDirdesc(username + "的个人资源库");
		// // dir.setDirid(id);
		// // dir.setDirname(username);
		// // dir.setDirparentid(new Long(0));
		// // dir.setParticipant(userid);
		// // dir.setTypes(CellInfo._TYPE_PUBLIC);
		// // OrmerEntry.fetchOrmer().fetchSerializer().create(dir);
		//
//		 TCmsInfomodel model = new TCmsInfomodel();
//		 model.setModelid(id);
//		 model.setParticipant(userid);
//		 model.setModelname(username);
//		 model.setDescription(username + "个人畅想空间");
//		 model.setUserid(userid);
//		
//		 ModelDao modelDao = (ModelDao) CmsEntry.fetchDao("modelDao");
//		 modelDao.create(model);
		return id;
	}

	public void addPic(HttpServletRequest req, HttpServletResponse rep,
			String participant) {

		String cellid = req.getParameter("cellid");
		String picid = req.getParameter("picid");
		String name = req.getParameter("cname");
		String naturalname = req.getParameter("naturalname");

		String width = req.getParameter("width");
		String height = req.getParameter("height");
		String linkType = req.getParameter("linkType");
		String linkvalue = req.getParameter("linkvalue");
		String linkvaluepage = req.getParameter("linkvaluepage");

		String linkInfo = "";
		if ("oecpage".equals(linkType)) {
			linkInfo = linkvaluepage;
		} else {
			linkInfo = linkvalue;
		}
		String script = "$:TEMPLATE.TEMPLATE.PIC{id:=" + picid + "&+url:=" + linkInfo
				+ "&+style:=width:" + width + ";height:" + height + "}";

		// addInnerElement(cellid, innerElement, CellInfo._PIC_AHEAD, name,
		// participant);

		regeditToResource(name, naturalname, script,
				ProtectedObjectReference._OBJ_TYPE_PIC, cellid, null);

		makeEffectToWeb(rep, cellid, CellInfo._PIC_AHEAD);
	}

	public void addBlankScript(HttpServletRequest req, HttpServletResponse rep,
			String participant) {
		String cellid = req.getParameter("id");
		String naturalname = req.getParameter("naturalname");
		String name = req.getParameter("name");

		String styleinfo = req.getParameter("bodyinfo");

		// addInnerElement(cellid, innerElement, CellInfo._PIC_AHEAD, name,
		// participant);
		regeditToResource(name, naturalname, styleinfo,
				ProtectedObjectReference._OBJ_TYPE_PIC, cellid, null);

		makeEffectToWeb(rep, cellid, CellInfo._PIC_AHEAD);
	}

	public void addFile(HttpServletRequest req, HttpServletResponse rep,
			String participant) {
		String name = req.getParameter("name");
		String cname = req.getParameter("cname");
		String naturalname = req.getParameter("naturalname");
		String cellid = req.getParameter("cellid");
		String fileid = req.getParameter("fileid");

		String innerElement = "$:TEMPLATE.TEMPLATE.FILE{id:=" + fileid + "&+name:=" + name + "}";

		regeditToResource(cname, naturalname, innerElement,
				ProtectedObjectReference._OBJ_TYPE_FILE, cellid, null);
		makeEffectToWeb(rep, cellid, CellInfo._FILE_AHEAD);

	}

	public void addZip(HttpServletRequest req, HttpServletResponse rep,
			String participant) {
		String name = req.getParameter("name");
		String naturalname = req.getParameter("naturalname");
		String url = ProtectedObjectReference._RS_URI_SER
				+ req.getParameter("valueinfo");
		String cellid = req.getParameter("id");

		String innerElement = "$:fileView{url:=" + url + "&+name:=" + name
				+ "}";

		regeditToResource(name, naturalname, innerElement,
				ProtectedObjectReference._OBJ_TYPE_ZIP, cellid, null);
		makeEffectToWeb(rep, cellid, CellInfo._FILE_AHEAD);

	}

	public void addCutFish(HttpServletRequest req, HttpServletResponse rep,
			String participant) {

		String cellid = req.getParameter("id");

		String name = req.getParameter("cname");
		String naturalname = req.getParameter("naturalname");

		String args = req.getParameter("args");

		WiParser parser = (WiParser) CmsEntry
				.fetchBean(CmsBean._WEB_INFO_PARSER);
		String body = parser.performWiParser(args);
		// addInnerElement(cellid, body, CellInfo._CUT_HEAD, name, participant);
		regeditToResource(name, naturalname, body,
				ProtectedObjectReference._OBJ_TYPE_CUT, cellid, null);
		makeEffectToWeb(rep, cellid, CellInfo._CUT_HEAD);

	}

	public void addArticle(HttpServletRequest req, HttpServletResponse rep,
			String participant) {
		String cellid = req.getParameter("id");
		String cname = req.getParameter("cname");
		String naturalname = req.getParameter("naturalname");

		String body = ArticleDeal.makeBody(req);
		regeditToResource(cname, naturalname, body,
				ProtectedObjectReference._OBJ_TYPE_ARTICLE, cellid, null);

		makeEffectToWeb(rep, cellid, CellInfo._ARTICLE_AHEAD);
	}


	public void addPage(HttpServletRequest request, HttpServletResponse rep,
			String participant) {

		String modelid = request.getParameter("pagepath");
		String name = request.getParameter("name");
		String naturalname = request.getParameter("naturalname");

		String width = request.getParameter("wid");
		String height = request.getParameter("len");
		String cache = request.getParameter("cache");
		String types = request.getParameter("types");
		
		String linkx= request.getParameter("linkx");

		String cachecycle = request.getParameter("cachecycle");
		Double cachecyclevalue = cachecycle != null && !cachecycle.equals("") ? new Double(
				cachecycle)
				: null;
		String availablefrom = request.getParameter("availablefrom");
		Timestamp availablefromValue = availablefrom != null
				&& !availablefrom.equals("") ? Timestamp.valueOf(availablefrom)
				: null;
		String availableto = request.getParameter("availableto");
		Timestamp availabletoValue = availableto != null
				&& !availableto.equals("") ? Timestamp.valueOf(availableto)
				: null;

		boolean usecache = false;
		if (CellInfo._IN_TIME.equals(cache)) {
			usecache = true;
		}

		TCmsInfocell cell = new TCmsInfocell();
		cell.setBelongto(modelid);
		cell.setCellname(name);
		cell.setNaturalname(naturalname);
		cell.setParticipant(participant);
		cell.setWidth(new Long(width));
		cell.setHeight(new Long(height));
		cell.setTypes(types);
		cell.setCreated(new Timestamp(System.currentTimeMillis()));
		cell.setCachecycle(cachecyclevalue);
		cell.setAvailablefrom(availablefromValue);
		cell.setAvailableto(availabletoValue);
		cell.setExtendattribute(linkx);
		if (usecache) {
			cell.setIntime(CellInfo._IN_TIME);
		}
		InfoCellDao infocellDao = (InfoCellDao) CmsEntry
				.fetchDao("infocellDao");

		infocellDao.create(cell, CellInfo._JPP_HEAD, "");

		makeEffectToWeb(rep, cell.getCellid(), CellInfo._JPP_HEAD);
	}

	public void updatePage(HttpServletRequest request, HttpServletResponse rep,
			String participant) {
		String name = request.getParameter("name");
		String naturalname = request.getParameter("naturalname");
		String types = request.getParameter("types");
		String width = request.getParameter("wid");
		String height = request.getParameter("len");
		String cache = request.getParameter("cache");
		String cellid = request.getParameter("cellid");
		String linkx= request.getParameter("linkx");

		String cachecycle = request.getParameter("cachecycle");
		Double cachecyclevalue = cachecycle != null && !cachecycle.equals("") ? new Double(
				cachecycle)
				: null;
		String availablefrom = request.getParameter("availablefrom");
		Timestamp availablefromValue = availablefrom != null
				&& !availablefrom.equals("") ? Timestamp.valueOf(availablefrom)
				: null;
		String availableto = request.getParameter("availableto");
		Timestamp availabletoValue = availableto != null
				&& !availableto.equals("") ? Timestamp.valueOf(availableto)
				: null;

		boolean usecache = false;
		if (CellInfo._IN_TIME.equals(cache)) {
			usecache = true;
		}
		InfoCellDao infocellDao = (InfoCellDao) CmsEntry
				.fetchDao("infocellDao");
		TCmsInfocell cell = infocellDao.view(cellid);

		cell.setCellname(name);
		cell.setNaturalname(naturalname);
		cell.setTypes(types);
		cell.setWidth(new Long(width));
		cell.setHeight(new Long(height));
		cell.setCachecycle(cachecyclevalue);
		cell.setAvailablefrom(availablefromValue);
		cell.setAvailableto(availabletoValue);
		if (usecache) {
			cell.setIntime(CellInfo._IN_TIME);
		} else {
			cell.setIntime(null);
		}
		cell.setExtendattribute(linkx);

		infocellDao.update(cell, cell.getBody());
		makeEffectToWeb(rep, cell.getCellid(), "");
	}

	public void addUrl(HttpServletRequest req, HttpServletResponse rep,
			String participant) {
		String cellid = req.getParameter("id");
		String mode = req.getParameter("mode");
		String name = req.getParameter("cname");
		if(name==null){// 这个方法是 项中的URL和应用中的程序 复用的，而应用程序中没有cname
			name=req.getParameter("name");
		}
		String naturalname = req.getParameter("naturalname");
		String url = req.getParameter("url");
		String body = "";
		if ("link".equals(mode)) {
			String description = req.getParameter("description");

			body = "<a href='" + url
					+ "' target='_blank'><font class='Headinglink1'>" + name
					+ "</font></a><br><font class='Text1'>" + description
					+ "</font>";
		} else {
			String appbar = req.getParameter("appbar");
			String width = req.getParameter("width");
			String height = req.getParameter("height");

			if (width == null || width.equals("")) {
				width = "200";
			}
			if (height == null || height.equals("")) {
				height = "300";
			}

			String uuid = "app_" + IdServer.uuid();
			if ("yes".equals(appbar)) {
				body = StringUtils.replace(_PORTAL_APP_WITH_BAR,
						_PORTAL_APP_TITLE, name);
				body = StringUtils.replace(body, _PORTAL_APP_WIDTH, width);
				body = StringUtils.replace(body, _PORTAL_APP_HEIGHT, height);
				body = StringUtils.replace(body, _PORTAL_APP_URL, url);
				body = StringUtils.replace(body, _PORTAL_APP_BODY, uuid);

			} else {

				body = StringUtils.replace(_PORTAL_APP_, _PORTAL_APP_WIDTH,
						width);
				body = StringUtils.replace(body, _PORTAL_APP_HEIGHT, height);
				body = StringUtils.replace(body, _PORTAL_APP_URL, url);
			}

			String basePath = req.getScheme() + "://" + req.getServerName()
					+ ":" + req.getServerPort() + req.getContextPath() + "/";
			body = StringUtils.replace(body, _PORTAL_APP_CONTEXTPATH, basePath);
			// body = "<div id='" + cellid + "'>" + body + "</div>";

		}
		// 原先的做法是直接在cell中增加信息
		// addInnerElement(cellid, body, CellInfo._URL_HEAD, name, participant);
		// 新的做法是将信息注册到资源树中去
		regeditToResource(name, naturalname, body,
				ProtectedObjectReference._OBJ_TYPE_URL, cellid, null);
		makeEffectToWeb(rep, cellid, CellInfo._URL_HEAD);

	}

	/**
	 * 自动注册到资源管理中去
	 * 
	 * @param name
	 * @param naturalname
	 * @param body
	 * @param type
	 * @param cellNaturalname
	 */
	private void regeditToResource(String name, String naturalname,
			String body, String type, String cellid, String cache) {
		TCmsInfocell cell = (TCmsInfocell) OrmerEntry.fetchOrmer()
				.fetchQuerister().loadObject(TCmsInfocell.class, cellid);

		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setName(name);
			upo.setNaturalname(naturalname);
			upo.setObjecttype(type);
			upo.setInclusion(ProtectedObjectReference._OBJ_INCLUSTION_NO);
			upo.setExtendattribute(body);

			upo.setDescription(cache);// 在 description中 定义cache

			rsrmi.addResource(upo, cell.getBelongto() + "."
					+ cell.getNaturalname());

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 生成的脚本适合2个地方使用 <br>
	 * 1：在版面设计中使用，这时可以自动刷新版面 <br>
	 * 2：在JPP设计界面中使用，这时只刷新资源列表 <br>
	 * 这两种应用的区别就在于1：中存在valueid这个元素
	 * 
	 * @param cellid
	 * @return
	 */
	private String makeScript(String cellid) {
		return "<script>alert('成功');self.opener.addgroupcoreUseId('" + cellid
				+ "'); window.close();</script>";
	}

	/**
	 * portalet内部刷新使用
	 * 
	 * @param cellid
	 * @return
	 */
	private String makeScriptCore(String cellid) {
		return "<script>alert('成功');;self.opener.refreshdiv('" + cellid
				+ "'); window.close();</script>";
	}

	/**
	 * 将结果生效到页面
	 * 
	 * @param rep
	 * @param cellid
	 */
	private void makeEffectToWeb(HttpServletResponse rep, String cellid,
			String type) {
		PrintWriter out = null;
		try {
			rep.setContentType("text/html; charset=GBK");
			out = rep.getWriter();
			String script = makeScriptCore(cellid);
			// if (CellInfo._CUT_HEAD.equals(type)) {
			// script = makeScriptForFish(cellid);
			// }
			if (CellInfo._JPP_HEAD.equals(type)) {
				script = makeScript(cellid);
			}
			out.print(script);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			out.flush();
			out.close();
		}
	}

	public void addOecScript(HttpServletRequest req, HttpServletResponse rep,
			String participant) {

		String paraminfo = req.getParameter("#paraminfo");
		String jppnaturalname = req.getParameter("#naturalname");
		String extvalue = req.getParameter("#extvalue");
		String naturalname = null;
		String name = null;
		String cachecycle = null;
		if (extvalue != null) {
			String[] valuesx = extvalue.split(",");
			naturalname = valuesx[0];
			if (naturalname == null || naturalname.equals("")) {
				naturalname = IdServer.uuid();
			}
			if (valuesx.length >= 2) {
				name = valuesx[1];
			}
			if (valuesx.length == 3) {
				cachecycle = valuesx[2];
			}
		}

		ResourceRmi resourceRmi;
		UmsProtectedobject upo = null;
		try {
			resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			upo = resourceRmi.loadResourceByNatural(jppnaturalname);
			if (name == null || name.equals("")) {
				name = upo.getName(); // 如果外部没有传递名字过来，那么直接使用函数的名字
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		String cellid = req.getParameter("#belongtocellid");

		paraminfo = paraminfo == null ? "" : paraminfo;
		String[] paraminfoArr = paraminfo.split(",");
		StringBuffer but = new StringBuffer();
		for (int i = 0; i < paraminfoArr.length; i++) {
			String value = req.getParameter(paraminfoArr[i]);
			but.append(paraminfoArr[i] + ":=" + value + "&+");
		}
		String innerElement = "$:" + jppnaturalname + "{" + but + "}";

		regeditToResource(name, naturalname, innerElement,
				ProtectedObjectReference._OBJ_TYPE_APP_JPP, cellid, cachecycle);
		makeEffectToWeb(rep, cellid, CellInfo._TEMPLATE_HEAD);

	}
	
	public void addOutItem(HttpServletRequest req, HttpServletResponse rep,
			String participant) {
		String cname = req.getParameter("cname");
		String naturalname = req.getParameter("naturalname");
		String naturalnameitem = req.getParameter("naturalnameitem");
		String cellid = req.getParameter("id");

		String innerElement = "$:TEMPLATE.TEMPLATE.OUTERITEM{itemname:=" + naturalnameitem+"}";

		regeditToResource(cname, naturalname, innerElement,
				ProtectedObjectReference._OBJ_TYPE_FILE, cellid, null);
		makeEffectToWeb(rep, cellid, CellInfo._FILE_AHEAD);

	}

}
