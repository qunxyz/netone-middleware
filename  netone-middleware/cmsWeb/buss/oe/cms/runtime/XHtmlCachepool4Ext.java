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
	 * ��ȡHTML��֧�ֻ���
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
		
		// ���ڻ���
		if (WebCache.containCache(cachekey)) 
		{
			TCmsInfocell cellobj = (TCmsInfocell) WebCache.getCache(cachekey);
			String pageinfo = cellobj.getBody();
			
			// ���Ŀǰ��ҳ��û������Ч��
			String checkAvailable = checkAvailable(cellobj);
			if (checkAvailable != null) 
			{
				// ˵��ҳ��ǰ������Ч�ڲ��ܷ��ʣ�ֱ�ӷ��� body��Ϣ, body��Ϣ
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
		
		// û�� cache�����
		InfoCellDao cellDao = (InfoCellDao) CmsEntry.fetchDao("infocellDao");
		
		TCmsInfocell cellObj = cellDao.view4ext(cellid,request);
		// ����Ƿ���Ч��
		String checkAvailable = checkAvailable(cellObj);
		String pageinfo = cellObj.getBody();
		if (checkAvailable != null) 
		{
			// ˵��ҳ��ǰ������Ч�ڲ��ܷ��ʣ�ֱ�ӷ��� body��Ϣ, body��Ϣ
//			String itemsinfo = StringUtils.substringBetween(pageinfo,
//					Blog._PORTALET_BODY_START_MARK,
//					Blog._PORTALET_BODY_END_MARK);
//			pageinfo = StringUtils.replaceOnce(pageinfo,
//					Blog._PORTALET_BODY_START_MARK + itemsinfo
//							+ Blog._PORTALET_BODY_END_MARK, checkAvailable);
			cellObj.setBody(pageinfo);
			return cellObj;
		}
		
		// �ɷ�����ҳ����ô��������
		Date dateCycle = null;
		if (cellObj.getCachecycle() != null) 
		{
			// �������ǰ����Ļ�����������,�ӵ�ǰʱ��㿪ʼ���϶����Cachecycle�е�Сʱ
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
		// �����Ч��
		Date dateNow = new Date(System.currentTimeMillis());
		String checktime = null;
		if (cell.getAvailablefrom() != null && dateNow != null
				&& dateNow.compareTo(cell.getAvailablefrom()) < 0) {
			checktime = "[δ����Ч����]<br>��Ч����Ϊ:" + cell.getAvailablefrom();
		}
		if (cell.getAvailableto() != null && dateNow != null
				&& dateNow.compareTo(cell.getAvailableto()) > 0) {
			checktime = "[�ѹ���Ч��]<br>��Ч����Ϊ:" + cell.getAvailableto();
		}
		return checktime;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
