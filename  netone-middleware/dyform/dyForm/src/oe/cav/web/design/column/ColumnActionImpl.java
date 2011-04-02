package oe.cav.web.design.column;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.tools.FormColumnCache;
import oe.cav.bean.logic.tools.FormColumnTitleCache;
import oe.cav.web.util.SearchObj;
import oe.frame.web.util.WebTip;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.sso.Security;

import org.apache.commons.beanutils.BeanUtils;
import org.omg.CORBA.Request;

import com.rongji.webframework.struts.ActionEvent;
import com.rongji.webframework.struts.BaseAction;
import com.sun.mail.iap.Response;

public class ColumnActionImpl extends BaseAction {

	DyFormDesignService dys = null;

	DyFormService dysc = null;

	public ColumnActionImpl() {
		if (dys == null) {
			try {
				dys = (DyFormDesignService) RmiEntry.iv("dydesign");
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
		if (dysc == null) {
			try {
				dysc = (DyFormService) RmiEntry.iv("dyhandle");
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
	}

	protected boolean validateLogon() {
		return false;
	}

	public String onList(ActionEvent ae) throws Exception {
		SearchObj search = setListInfo(ae);
		ae.setAttribute("en", search);
		this.bindEvent(ae, "enList", ColumnTurnpage.class);
		return "list";
	}

	private SearchObj setListInfo(ActionEvent ae) {
		ColumnForm form = (ColumnForm) ae.getForm();
		String formcode = form.getFormcode();
		if (formcode == null || formcode.equals("")) {
			return null;
		}
		TCsColumn busForm = new TCsColumn();
		busForm.setFormcode(formcode);
		busForm.setStatusinfo(ColumnExtendInfo._STATUS_NORMAL);
		// 查询对象设置
		SearchObj search = new SearchObj();
		search.setSearchobj(busForm);
		search.setSql(" order by indexvalue");

		return search;
	}

	public String onCreateview(ActionEvent ae) throws Exception {
		ae.setAttribute("typeinfo", ColumnExtendInfo._TYPE_INFO_LIST);
		ae.setAttribute("booleaninfo", ColumnExtendInfo._BOOLEAN_INFO_LIST);
		// 创建的对象初始化
		ColumnForm form = (ColumnForm) ae.getForm();
		TCsColumn busForm = new TCsColumn();
		busForm.setStatusinfo(ColumnExtendInfo._STATUS_NORMAL);
		busForm.setFormcode(form.getFormcode());
		BeanUtils.copyProperties(form, busForm);
		return "createview";
	}

	public String onCreateope(ActionEvent ae) throws Exception {
		ColumnForm form = (ColumnForm) ae.getForm();
		TCsColumn busForm = new TCsColumn();
		BeanUtils.copyProperties(busForm, form);
		String formcode = form.getFormcode();
		long indexvalue = dys.getNextIndexValue(formcode);
		String newColumnid = dys.getNextColumn(formcode);
		busForm.setColumnid(newColumnid);
		busForm.setIndexvalue(new Long(indexvalue));
		String[] checkAndHtml = dys.parseViewType(form.getViewtype());

		busForm.setChecktype(checkAndHtml[0]);
		busForm.setHtmltype(checkAndHtml[1]);
		busForm.setStatusinfo(ColumnExtendInfo._STATUS_NORMAL);

		Security ser = new Security(ae.getRequest());
		busForm.setParticipant(ser.getUserLoginName());

		String info = dys.addColumn(busForm);

		// 清除缓存
		FormColumnCache.removeCache(formcode);
		FormColumnTitleCache.removeCache(formcode);

		WebTip.htmlInfo(info, true,true, ae.getResponse());

		return null;
	}

	public String onModifyview(ActionEvent ae) throws Exception {
	
		ae.setAttribute("typeinfo", ColumnExtendInfo._TYPE_INFO_LIST);
		ae.setAttribute("booleaninfo", ColumnExtendInfo._BOOLEAN_INFO_LIST);
		ColumnForm form = (ColumnForm) ae.getForm();
		// 装载对象
		String columncode = form.getColumncode();
		TCsColumn busForm = dys.loadColumn(form.getFormcode(), columncode);
		BeanUtils.copyProperties(form, busForm);

		return "modifyview";
	}

	public String onDropope(ActionEvent ae) throws Exception {
		ColumnForm form = (ColumnForm) ae.getForm();
		String columncode = form.getColumncode();
		TCsColumn bufForm = dys.loadColumn(form.getFormcode(), columncode);
		Security ser = new Security(ae.getRequest());
		bufForm.setParticipant(ser.getUserLoginName());
		String dors = dys.dropColumn(bufForm);

		String formcode = bufForm.getFormcode();
		// 清除缓存
		FormColumnCache.removeCache(formcode);
		FormColumnTitleCache.removeCache(formcode);

		WebTip.htmlInfo(dors, true, true, ae.getResponse());

		return null;
	}

	public String onModifyope(ActionEvent ae) throws Exception {

		ColumnForm form = (ColumnForm) ae.getForm();
		TCsColumn busForm = new TCsColumn();
		BeanUtils.copyProperties(busForm, form);

		String[] checkAndHtml = dys.parseViewType(form.getViewtype());
		busForm.setChecktype(checkAndHtml[0]);
		busForm.setHtmltype(checkAndHtml[1]);
		Security ser = new Security(ae.getRequest());
		busForm.setParticipant(ser.getUserLoginName());

		String dors = dys.updateColumn(busForm);
		String tip = WebTip.tipSpi(dors);
		ae.setAttribute("tip", tip);
		String formcode = busForm.getFormcode();
		// 清除缓存
		FormColumnCache.removeCache(formcode);
		FormColumnTitleCache.removeCache(formcode);

		WebTip.htmlInfo(dors, true, true, ae.getResponse());

		return null;
	}

	public String onMoveup(ActionEvent ae) throws Exception {
		ColumnForm form = (ColumnForm) ae.getForm();
		String formcode = form.getFormcode();
		String columncode = form.getColumncode();
		Security ser = new Security(ae.getRequest());

		dys.moveupColumn(formcode, columncode, ser.getUserLoginName());

		// 清除缓存
		FormColumnCache.removeCache(formcode);
		FormColumnTitleCache.removeCache(formcode);

		return onList(ae);
	}

	public String onMovedown(ActionEvent ae) throws Exception {
		ColumnForm form = (ColumnForm) ae.getForm();
		String formcode = form.getFormcode();
		String columncode = form.getColumncode();
		Security ser = new Security(ae.getRequest());
		dys.movedownColumn(formcode, columncode, ser.getUserLoginName());

		// 清除缓存
		FormColumnCache.removeCache(formcode);
		FormColumnTitleCache.removeCache(formcode);

		return onList(ae);
	}

	public String onUpload(ActionEvent ae) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String onInitpoint(ActionEvent ae) throws Exception {
		ColumnForm form = (ColumnForm) ae.getForm();
		String formcode = form.getFormcode();
		Security ser = new Security(ae.getRequest());
		dys.resizeColumnIndexValue(formcode, ser.getUserLoginName());
		// 清除缓存
		FormColumnCache.removeCache(formcode);
		FormColumnTitleCache.removeCache(formcode);

		return onList(ae);
	}

}
