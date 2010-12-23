package oe.bi.datasource.impl;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

import oe.bi.datasource.DataChannel;
import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.form.TCsForm;

import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;

public class DataChannelsImpl implements DataChannel {

	final String _BI_TIME_COLUMN = "start_time";

	final String _BI_DIM_COLUMN = "sys_int_id";

	final String _DY_TIME_COLUMN_1H = "left(timex,13)";

	final String _DY_TIME_COLUMN_1D = "left(timex,10)";

	final String _DY_DIM_COLUMN = "belongx";

	private String preoperatoinCondition(String condition, String level) {
		if (level.equals("1d")) {
			condition = StringUtils.replace(condition, _BI_TIME_COLUMN,
					_DY_TIME_COLUMN_1D);
		} else {
			condition = StringUtils.replace(condition, _BI_TIME_COLUMN,
					_DY_TIME_COLUMN_1H);
		}

		return StringUtils.replace(condition, _BI_DIM_COLUMN, _DY_DIM_COLUMN);
	}

	public List[] executeQuery(String formcode, String[] condition,
			String[] targetColumn) {
		List listData = new ArrayList();
		String timelevel = "1h";
		try {
			DyFormService dfd = (DyFormService) RmiEntry.iv("dyhandle");

			TCsForm form = dfd.loadForm(formcode);
			timelevel = form.getTimelevel();

			TCsBus bus = new TCsBus();
			bus.setFormcode(formcode);
			String conditionX = preoperatoinCondition(condition[0], timelevel);
			System.out.println(conditionX);
			listData = dfd.queryData(bus, -1, -1, conditionX);

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
		if (listData == null) {
			listData = new ArrayList();
		}
		// 预处理数据,针对Start_time 和 sys_int_id 字段做个预处理
		for (Iterator iter = listData.iterator(); iter.hasNext();) {
			TCsBus element = (TCsBus) iter.next();

			element.setSys_int_id(element.getBelongx());
			String timestr = element.getTimex();
			if ("1d".equals(timelevel) || "1m".equals(timelevel)
					|| "1y".equals(timelevel)) {
				timestr = timestr.substring(0, 10);
			}
			element.setStart_time(timestr);
		}

		int returnNum = targetColumn.length;
		List[] listRs = new ArrayList[returnNum];
		for (int i = 0; i < listRs.length; i++) {
			listRs[i] = new ArrayList();
		}

		for (Iterator iter = listData.iterator(); iter.hasNext();) {
			TCsBus element = (TCsBus) iter.next();
			for (int i = 0; i < returnNum; i++) {
				String strValue = "";
				try {
					Object value = BeanUtils.getProperty(element,
							targetColumn[i].toLowerCase());
					if (value == null) {
						strValue = "-1";
					} else {
						strValue = value.toString();
					}
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				listRs[i].add(strValue);
			}
		}

		return listRs;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
