package oe.cms.dao.infocell;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.dao.blog.Blog;
import oe.cms.dao.blog.PersonRes;
import oe.cms.runtime.ScriptExe;
import oe.cms.runtime.XHtmlCachepool;
import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.IdServer;
import oe.frame.web.WebCache;
import oe.frame.web.util.Cacheobj;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

public class InfoCellDaoImpl implements InfoCellDao {

	public TCmsInfocell viewPreOperation(String cellid, HttpServletRequest req) {
		Ormer ormer = OrmerEntry.fetchOrmer();
		TCmsInfocell cell = (TCmsInfocell) ormer.fetchQuerister().loadObject(
				TCmsInfocell.class, cellid);

		try {
			StringBuffer butBody = new StringBuffer();

			// 特别标示下该字段
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			String naturalname = cell.getBelongto() + "."
					+ cell.getNaturalname();

			UmsProtectedobject upo = null;
			try {
				upo = rsrmi.loadResourceByNatural(naturalname);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if (upo == null) {
				return null;
			}
			// 处理 Portal标题中的链接，该链接信息定义在页cell 的 extendattribute中
			String linkvalue = cell.getExtendattribute();
			String linkInfo = "";
			if (linkvalue != null) {
				String[] linkvaluex = StringUtils.split(linkvalue, "#");
				if (linkvaluex.length == 2) {
					linkInfo = "<a href='" + linkvaluex[1]
							+ "' target='_blank'><font class='HeaderLink'>" + linkvaluex[0] + "</font></a>";
				}
			}

			List list = rsrmi.subResource(upo.getId());

			for (Iterator iter = list.iterator(); iter.hasNext();) {
				UmsProtectedobject element = (UmsProtectedobject) iter.next();
				String cacheCycle = element.getDescription();// description用于保存缓存信息
				String bodyinfo = element.getExtendattribute();
				String itemid = element.getNaturalname();
				String exers = null;
				if (cacheCycle != null && !cacheCycle.equals("")) {
					// 存在缓存的情况下

					if (!WebCache.containCache(WebCache._CACHE_ITEM_WEBITEM
							+ itemid)) {
						String[] rsinfo = ScriptExe.executeScriptCore(cellid,
								bodyinfo, req);
						exers = rsinfo[1];
					} else {
						exers = (String) WebCache
								.getCache(WebCache._CACHE_ITEM_WEBITEM + itemid);
						long currentime = System.currentTimeMillis();
						long cachecycle = currentime
								+ (long) (Double.parseDouble(cacheCycle) * 1000 * 60 * 60);
						WebCache.setCache(
								WebCache._CACHE_ITEM_WEBITEM + itemid, exers,
								new Date(cachecycle));

					}
				} else {
					// 不存在缓存的情况下
					String[] rsinfo = ScriptExe.executeScriptCore(cellid,
							bodyinfo, req);
					exers = rsinfo[1];
				}
				butBody.append(Blog._PORTALET_ELEMENT_START_MARK + exers
						+ Blog._PORTALET_ELEMENT_END_MARK);
			}

			String head = null;

			if (CellInfo._TYPE_PORTAL_WITHOUT_BORDER.equals(cell.getTypes())) {
				head = Blog._PORTALET_HEAD_MARK2.replace(
						Blog._PORTALET_KEY_NAME, cell.getCellname()).replace(
						Blog._PORTALET_KEY_LINK, linkInfo);

			} else if (CellInfo._TYPE_PORTAL_WITHOUT_TITLE.equals(cell
					.getTypes())) {
				head = Blog._PORTALET_HEAD_MARK3.replace(
						Blog._PORTALET_KEY_NAME, cell.getCellname()).replace(
						Blog._PORTALET_KEY_LINK, linkInfo);

			} else if (CellInfo._TYPE_PORTAL_WITHOUT_TITLE_AND_BORDER
					.equals(cell.getTypes())) {
				head = Blog._PORTALET_HEAD_MARK4.replace(
						Blog._PORTALET_KEY_NAME, cell.getCellname()).replace(
						Blog._PORTALET_KEY_LINK, linkInfo);

			} else {
				head = Blog._PORTALET_HEAD_MARK1.replace(
						Blog._PORTALET_KEY_NAME, cell.getCellname()).replace(
						Blog._PORTALET_KEY_LINK, linkInfo);

			}
			String bodyStart = Blog._PORTALET_START_MARK.replace(
					Blog._PORTALET_KEY_HEIGHT, cell.getHeight().toString())
					.replace(Blog._PORTALET_KEY_WIDTH,
							cell.getWidth().toString());

			String bodyStart_pre = Blog._PORTALET_BODY_START_MARK_PRE.replace(
					Blog._PORTALET_KEY_HEIGHT, cell.getHeight().toString())
					.replace(Blog._PORTALET_KEY_WIDTH,
							cell.getWidth().toString());
			String newbody = head + bodyStart + bodyStart_pre
					+ Blog._PORTALET_BODY_START_MARK + butBody
					+ Blog._PORTALET_BODY_END_MARK
					+ Blog._PORTALET_BODY_END_MARK_PRE
					+ Blog._PORTALET_END_MARK;

			cell.setBody(newbody);
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
		return cell;
	}
	
	public TCmsInfocell view4ext(String cellid, HttpServletRequest req) 
	{
		Ormer ormer = OrmerEntry.fetchOrmer();
		TCmsInfocell cell = (TCmsInfocell) ormer.fetchQuerister().loadObject(
				TCmsInfocell.class, cellid);
		
		try {
			StringBuffer butBody = new StringBuffer();

			// 特别标示下该字段
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			String naturalname = cell.getBelongto() + "."
					+ cell.getNaturalname();

			UmsProtectedobject upo = null;
			try {
				upo = rsrmi.loadResourceByNatural(naturalname);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			if (upo == null) {
				return null;
			}
//			// 处理 Portal标题中的链接，该链接信息定义在页cell 的 extendattribute中
//			String linkvalue = cell.getExtendattribute();
//			String linkInfo = "";
//			if (linkvalue != null) {
//				String[] linkvaluex = StringUtils.split(linkvalue, "#");
//				if (linkvaluex.length == 2) {
//					linkInfo = "<a href=\"" + linkvaluex[1]
//							+ "\" target=\"_blank\"><font class=\"HeaderLink\">" + linkvaluex[0] + "</font></a>";
//					// 这边更换扩展属性为 标题link
//					cell.setExtendattribute(linkvalue);
//				}
//			}
			
			

			List list = rsrmi.subResource(upo.getId());// 页下面的项

			for (Iterator iter = list.iterator(); iter.hasNext();) 
			{
				UmsProtectedobject element = (UmsProtectedobject) iter.next(); //资源表ums_protectedobject的记录
				String cacheCycle = element.getDescription();// description用于保存缓存信息
				String bodyinfo = element.getExtendattribute();
				String itemid = element.getNaturalname();
				String exers = null;
				if (cacheCycle != null && !cacheCycle.equals("")) {
					// 存在缓存的情况下

					if (!WebCache.containCache(WebCache._CACHE_ITEM_WEBITEM + "EXT"
							+ itemid)) {
						String[] rsinfo = ScriptExe.executeScriptCore(cellid,
								bodyinfo, req);
						exers = rsinfo[1];
					} else {
						exers = (String) WebCache
								.getCache(WebCache._CACHE_ITEM_WEBITEM + "EXT" + itemid);
						long currentime = System.currentTimeMillis();
						long cachecycle = currentime
								+ (long) (Double.parseDouble(cacheCycle) * 1000 * 60 * 60);
						WebCache.setCache(
								WebCache._CACHE_ITEM_WEBITEM + "EXT" + itemid, exers,
								new Date(cachecycle));

					}
				} else {
					// 不存在缓存的情况下
					String[] rsinfo = ScriptExe.executeScriptCore(cellid,
							bodyinfo, req);
					exers = rsinfo[1];
				}
				butBody.append(Blog._PORTALET_ELEMENT_START_MARK + exers
						+ Blog._PORTALET_ELEMENT_END_MARK);
			}
			
			// 保留Table,为了显示页下面的每一项
			cell.setBody(
							Blog._PORTALET_BODY_START_MARK 
							+ butBody.toString()
							+ Blog._PORTALET_END_MARK
						);
			
			System.out.println(
								"bodyx="+Blog._PORTALET_BODY_START_MARK 
								+ butBody.toString()
								+ Blog._PORTALET_END_MARK );
		} 
		catch (MalformedURLException e) 
		{
			e.printStackTrace();
		} 
		catch (RemoteException e) 
		{
			e.printStackTrace();
		} 
		catch (NotBoundException e) 
		{
			e.printStackTrace();
		}
		return cell;
	}

	/**
	 * 根据查询的资讯元SQL取得 资讯元列表
	 * 
	 * @return
	 */
	public List searchCellList(String sql, String paritcipant, int from, int to) {

		List list = new ArrayList();

		if (sql == null || sql.trim().equals("")) {
			list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
					new TCmsInfocell(), null, from, to,
					PersonRes.fetchCondition(paritcipant));
		} else {
			list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
					new TCmsInfocell(), null, from, to,
					sql + PersonRes.fetchCondition(paritcipant));
		}

		return list;
	}

	/**
	 * 根据查询的资讯元SQL查询 取得 资讯元列表的总数
	 * 
	 * @param name
	 * @return
	 */
	public int searchCellNum(String sql, String paritcipant) {

		int i = 0;
		if (sql == null || sql.trim().equals("")) {
			i = (int) OrmerEntry.fetchOrmer().fetchQuerister()
					.queryObjectsNumber(new TCmsInfocell(), null,
							PersonRes.fetchCondition(paritcipant));
		} else {
			i = (int) OrmerEntry.fetchOrmer().fetchQuerister()
					.queryObjectsNumber(new TCmsInfocell(), null,
							sql + PersonRes.fetchCondition(paritcipant));
		}
		return i;
	}

	/**
	 * 获取资讯元列表
	 * 
	 * @return
	 */
	public List getCellToolList(String belongto, String participant) {

		List list = new ArrayList();
		if (belongto != null) {
			TCmsInfocell cmsinfocell = new TCmsInfocell();
			List ormCellList = OrmerEntry.fetchOrmer().fetchQuerister()
					.queryObjects(
							cmsinfocell,
							null,
							" and belongto='" + belongto + "'"
									+ PersonRes.fetchCondition(participant));
			for (int i = 0; i < ormCellList.size(); i++) {
				cmsinfocell = (TCmsInfocell) ormCellList.get(i);
				Cacheobj lv = new Cacheobj(cmsinfocell.getCellname(), String
						.valueOf(cmsinfocell.getCellid()));
				list.add(lv);
			}
		}
		return list;
	}

	/**
	 * 获取图形列表
	 * 
	 * @return
	 */
	public List getChartList(String[][] chartStr) {
		List list = new ArrayList();

		// String [][]chartStr=ChartCreater.CHAR_TIP;
		for (int i = 0; i < chartStr.length; i++) {
			Cacheobj lv = new Cacheobj(chartStr[i][0], chartStr[i][1]);
			list.add(lv);
		}
		return list;
	}

	public boolean create(TCmsInfocell cell, String celltype, String bodyinfo) {
		Ormer orm = OrmerEntry.fetchOrmer();
		cell.setBody(bodyinfo);
		cell.setCellid(celltype + IdServer.uuid());
		if (cell.getTypes() == null) {
			cell.setTypes(CellInfo._TYPE_PRIVATE);
		}
		orm.fetchSerializer().create(cell);

		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setName(cell.getCellname());
			upo.setNaturalname(cell.getNaturalname());
			upo.setObjecttype(ProtectedObjectReference._OBJ_TYPE_PAGE);
			upo.setInclusion(ProtectedObjectReference._OBJ_INCLUSTION_YES);
			upo.setActionurl(cell.getCellid());
			upo.setExtendattribute(cell.getCellid());

			if (cell.getBelongto() == null) {
				// 页是单独创建的,不是页组中
				rsrmi.addResource(upo, Blog._PAGEGROUP);
				cell.setBelongto(Blog._PAGEGROUP);
			} else {
				// // 页是在页组中创建的
				// TCmsInfomodel infomodel = (TCmsInfomodel)
				// orm.fetchQuerister()
				// .loadObject(TCmsInfomodel.class,
				// new Long(cell.getBelongto()));
				// rsrmi.addResource(upo, ReferenceTmp._PAGEGROUP + "."
				// + infomodel.getNaturalname());
				// cell.setBelongto(ReferenceTmp._PAGEGROUP + "."
				// + infomodel.getNaturalname());

				rsrmi.addResource(upo, cell.getBelongto());
				// cell.setNaturalname((cell.getBelongto() + "." + cell
				// .getNaturalname()).toUpperCase());
			}

			// 将资源中的路径写入到cell本身中去,这样方便二者的关联
			// orm.fetchSerializer().update(cell);

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

		return true;
	}

	public boolean update(TCmsInfocell cell, String bodyinfo) {
		Ormer orm = OrmerEntry.fetchOrmer();
		cell.setBody(bodyinfo);
		boolean canupdate = orm.fetchSerializer().update(cell);
		XHtmlCachepool.removeInfoCache(cell.getCellid());

		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			String uponame = cell.getBelongto() + "." + cell.getNaturalname();
			UmsProtectedobject upo = rsrmi.loadResourceByNatural(uponame
					.toUpperCase());
			upo.setName(cell.getCellname());
			rsrmi.updateResource(upo);
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
		UmsProtectedobject upo = new UmsProtectedobject();
		upo.setName(cell.getCellname());
		upo.setNaturalname(cell.getNaturalname());
		upo.setObjecttype(ProtectedObjectReference._OBJ_TYPE_PAGE);
		upo.setInclusion(ProtectedObjectReference._OBJ_INCLUSTION_YES);
		upo.setActionurl(cell.getCellid());
		upo.setExtendattribute(cell.getBody());

		return canupdate;
	}

	public boolean delete(String id, String participant) {
		XHtmlCachepool.removeInfoCache(id);
		Ormer orm = OrmerEntry.fetchOrmer();
		TCmsInfocell tcmsinfocell = (TCmsInfocell) orm.fetchQuerister()
				.loadObject(TCmsInfocell.class, id);
		tcmsinfocell.setParticipant(participant);
		boolean done = orm.fetchSerializer().drop(tcmsinfocell); // 删除

		XHtmlCachepool.removeInfoCache(id);
		return done;

	}

	public TCmsInfocell view(String cellid) {
		Ormer orm = OrmerEntry.fetchOrmer();
		TCmsInfocell cmsinfocell = (TCmsInfocell) orm.fetchQuerister()
				.loadObject(TCmsInfocell.class, cellid);

		return cmsinfocell;
	}

	public List queryByType(String userid, String cellTypes) {
		String condition = PersonRes.fetchCondition(userid);
		TCmsInfocell cell = new TCmsInfocell();
		if (CellInfo._ARTICLE_AHEAD.equals(cellTypes)
				|| CellInfo._CUT_HEAD.equals(cellTypes)
				|| CellInfo._FILE_AHEAD.equals(cellTypes)
				|| CellInfo._JPP_HEAD.equals(cellTypes)
				|| CellInfo._PIC_AHEAD.equals(cellTypes)
				|| CellInfo._BLK_HEAD.equals(cellTypes)) {

			condition += " and cellid like '" + cellTypes + "%'";
		}
		Ormer orm = OrmerEntry.fetchOrmer();
		return orm.fetchQuerister().queryObjects(cell, null, condition);
	}

	public UmsProtectedobject viewRS(String cellid) {
		String id = view(cellid).getExtendattribute();
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject obj = new UmsProtectedobject();
			obj.setExtendattribute(cellid);
			obj.setObjecttype(ProtectedObjectReference._OBJ_TYPE_PAGE);
			List list = rsrmi.fetchResource(obj, null);
			if (list == null || list.size() == 0) {
				return null;
			} else {
				if (list.size() != 1) {
					throw new RuntimeException("存储异常,页的命名重复, 页ID:" + cellid);
				}
				return (UmsProtectedobject) list.get(0);
			}
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
		return null;
	}

	public UmsProtectedobject viewRS2(String cellid) {

		TCmsInfocell cell = this.view(cellid);

		try {

			String naturename = cell.getBelongto() + "."
					+ cell.getNaturalname();
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");

			return rsrmi.loadResourceByNatural(naturename);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
