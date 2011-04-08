package oe.cms.runtime;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import oe.cms.CmsEntry;
import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.dao.infocell.InfoCellDao;
import oe.frame.web.WebCache;

public class XHtmlCachepool4Ext 
{

	public static void removeInfoCache(String cellid) {
		WebCache.removeCache(WebCache._CACHE_ITEM_WEBCELL + cellid);
	}

	public static void removeInfoCacheAll() {
		WebCache.initCache();
	}

	public static TCmsInfocell fetchPageForEdit(String cellid,
			HttpServletRequest request) {
		if (cellid == null) {
			return null;
		}

		InfoCellDao cellDao = (InfoCellDao) CmsEntry.fetchDao("infocellDao");
		TCmsInfocell cellObj = cellDao.viewPreOperation(cellid, request);

		return cellObj;

	}
	
	/**
	 * 读取HTML，支持缓存
	 * @param cellid
	 * @param request
	 * @return
	 */
	public static TCmsInfocell fetchPage
	(
			String cellid,
			HttpServletRequest request
	) 
	{
		if (cellid == null) 
		{
			return null;
		}
		
		String cachekey = WebCache._CACHE_ITEM_WEBCELL + "EXT" + cellid;
		
		// 存在缓存
		if (WebCache.containCache(cachekey)) 
		{
			TCmsInfocell cellobj = (TCmsInfocell) WebCache.getCache(cachekey);
			String pageinfo = cellobj.getBody();
			
			// 检查目前的页又没超出有效期
			String checkAvailable = checkAvailable(cellobj);
			if (checkAvailable != null) 
			{
				// 说明页当前不再有效期不能访问，直接访问 body信息, body信息
//				String itemsinfo = StringUtils.substringBetween(pageinfo,
//						Blog._PORTALET_BODY_START_MARK,
//						Blog._PORTALET_BODY_END_MARK);
//				pageinfo = StringUtils.replaceOnce(pageinfo,
//						Blog._PORTALET_BODY_START_MARK + itemsinfo
//								+ Blog._PORTALET_BODY_END_MARK, checkAvailable);
				cellobj.setBody(pageinfo);
			}
			return cellobj;
		}
		
		// 没有 cache的情况
		InfoCellDao cellDao = (InfoCellDao) CmsEntry.fetchDao("infocellDao");
		
		TCmsInfocell cellObj = cellDao.view4ext(cellid,request);
		// 检查是否到有效期
		String checkAvailable = checkAvailable(cellObj);
		String pageinfo = cellObj.getBody();
		if (checkAvailable != null) 
		{
			// 说明页当前不再有效期不能访问，直接访问 body信息, body信息
//			String itemsinfo = StringUtils.substringBetween(pageinfo,
//					Blog._PORTALET_BODY_START_MARK,
//					Blog._PORTALET_BODY_END_MARK);
//			pageinfo = StringUtils.replaceOnce(pageinfo,
//					Blog._PORTALET_BODY_START_MARK + itemsinfo
//							+ Blog._PORTALET_BODY_END_MARK, checkAvailable);
			cellObj.setBody(pageinfo);
			return cellObj;
		}
		
		// 可发布的页，那么创建缓存
		Date dateCycle = null;
		if (cellObj.getCachecycle() != null) 
		{
			// 计算出当前缓存的缓存生命周期,从单前时间点开始加上定义的Cachecycle中的小时
			long currentime = System.currentTimeMillis();
			long cachecycle = currentime
					+ (long) (cellObj.getCachecycle().doubleValue() * 1000 * 60 * 60);
			dateCycle = new Date(cachecycle);
		}
		
		if(!CellInfo._IN_TIME.equals(cellObj.getIntime())){
			WebCache.setCache(WebCache._CACHE_ITEM_WEBCELL + "EXT" + cellid, cellObj,
					dateCycle);
		}
		
		return cellObj;

	}

	private static String checkAvailable(TCmsInfocell cell) 
	{
		// 检查有效期
		Date dateNow = new Date(System.currentTimeMillis());
		String checktime = null;
		if (cell.getAvailablefrom() != null && dateNow != null
				&& dateNow.compareTo(cell.getAvailablefrom()) < 0) {
			checktime = "[未到生效日期]<br>生效日期为:" + cell.getAvailablefrom();
		}
		if (cell.getAvailableto() != null && dateNow != null
				&& dateNow.compareTo(cell.getAvailableto()) > 0) {
			checktime = "[已过有效期]<br>有效日期为:" + cell.getAvailableto();
		}
		return checktime;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
