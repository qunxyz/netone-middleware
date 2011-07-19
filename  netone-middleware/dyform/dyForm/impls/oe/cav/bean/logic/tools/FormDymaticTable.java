package oe.cav.bean.logic.tools;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.web.data.dyform.utils.DymaticFormCheck;
import oe.midware.dyform.service.DyFormDesignService;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;

import com.rongji.webframework.ui.html.HtmlUI;
import com.rongji.webframework.ui.html.Label;
import com.rongji.webframework.ui.html.Table;
import com.rongji.webframework.ui.html.TableColumn;
import com.rongji.webframework.ui.html.TableRow;

/**
 * 动态表单构造器，支持表单平面布局展示
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public class FormDymaticTable {

	/**
	 * 计算最大列数
	 * 
	 * @param formcode
	 * @return
	 */
	public static int maxCol(String formcode) {

		List list = new ArrayList();
		try {
			DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
			list = dysc.fetchColumnList(formcode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int maxrow = 1;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
			String ext = object.getExtendattribute();
			String offsetinfo = StringUtils.substringBetween(ext,
					DymaticFormCheck._CHECK_OFFSET,
					DymaticFormCheck._FINAL_CHECK);
			if (offsetinfo == null || offsetinfo.equals("")) {
				continue;
			}
			String[] offsetValue = offsetinfo.split("-");
			if (offsetValue.length != 2) {
				continue;
			}
			int yoffset = Integer.parseInt(offsetValue[1]);
			if (yoffset > maxrow) {
				maxrow = yoffset;
			}
		}
		return maxrow;

	}

	/**
	 * 解析表单设计中的字段平面布局参数
	 * 
	 * @param formcode
	 * @param columnid
	 * @return
	 */
	public static int[] getRowCol(String formcode, String columnid) {
		
		List list = new ArrayList();
		try {
			DyFormService dysc = (DyFormService) RmiEntry.iv("dyhandle");
			list = dysc.fetchColumnList(formcode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			TCsColumn object = (TCsColumn) iterator.next();
			if (columnid.equals(object.getColumnid())) {
				String ext = object.getExtendattribute();
				String offsetinfo = StringUtils.substringBetween(ext,
						DymaticFormCheck._CHECK_OFFSET,
						DymaticFormCheck._FINAL_CHECK);
				if (offsetinfo == null || offsetinfo.equals("")) {
					continue;
				}
				String[] offsetValue = offsetinfo.split("-");
				if (offsetValue.length != 2) {
					continue;
				}
				int yoffset = Integer.parseInt(offsetValue[1]);
				int xoffset = Integer.parseInt(offsetValue[0]);
				return new int[] { xoffset, yoffset };
			}
		}
		return new int[] { 0, 0 };
	}

	/**
	 * 构造缓存动态表单中字段平面布局
	 * 
	 * @param map
	 *            字段布局缓存
	 * @param title
	 * @param ui
	 * @param columnid
	 * @param inrow
	 * @param incol
	 */
	public static void addToTable(Map map, String title, HtmlUI ui,
			String columnid, int inrow, int incol) {
		if (map == null) {
			return;
		}
		if (inrow == 0 || incol == 0) {// 如果没有设计布局管理，那么直接用默认布局
			String index = StringUtils.substringAfter(columnid, "column");
			if (index.equals("")) {
				if (!map.containsKey("other")) {
					map.put("other", new HashMap());
				}
				Map mapx = (Map) map.get("other");

				Map other = new HashMap();
				other.put("title", title);
				other.put("ui", ui);
				other.put("inrow", inrow);
				other.put("columnid", columnid);

				mapx.put(columnid, other);
				return;

			}
			inrow = Integer.parseInt(index);
			incol = 1;
		}
		// 先检查是否存在行，没有创建行
		if (!map.containsKey(inrow)) {
			map.put(inrow, new HashMap());
		}
		Map rowmap = (Map) map.get(inrow);
		// 在行中加入列，注意如果列号雷同系统将覆盖
		Map col = new HashMap();
		col.put("title", title);
		col.put("ui", ui);
		col.put("inrow", inrow);
		col.put("columnid", columnid);
		rowmap.put(incol, col);

		// map.put(inrow, rowmap);
	}

	/**
	 * 创建最终的表单布局
	 * 
	 * @param map
	 *            先前的字段缓存
	 * @param table
	 *            布局table对象
	 * @param maxcol
	 */
	public static void makeTable(Map map, Table table, int maxcol) {

		for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
			Object key = iterator.next();
			Map rowmap = (Map) map.get(key);
			TableRow row = table.insertRow();// 创建新的一行
			for (int j = 1; j < maxcol + 1; j++) {
				if (rowmap.containsKey(j)) {
					Map colmap = (Map) rowmap.get(j);
					String title = (String) colmap.get("title");
					HtmlUI ui = (HtmlUI) colmap.get("ui");
					String columnid = (String) colmap.get("columnid");
					if (!rowmap.containsKey(j + 1)) {
						addColWithColSpace(row, title, table, ui, columnid,
								maxcol + 1 - j);
					} else {

						addCol(row, title, table, ui, columnid);
					}
				}
			}
		}

		Map other = (Map) map.get("other");
		if (other != null) {
			TableRow row = table.insertRow();// 创建新的一行
			row.setStyle("display:none");
			TableColumn colx = row.insertCol();
			for (Iterator iterator = other.keySet().iterator(); iterator
					.hasNext();) {
				String columnid = (String) iterator.next();
				Map eachOther = (Map) other.get(columnid);
				colx.insertUI((HtmlUI) eachOther.get("ui"));
			}

		}
	}

	private static void addCol(TableRow row, String title, Table table,
			HtmlUI ui, String columnid) {

		TableColumn colTitle = row.insertCol();// 创建新的一列
		colTitle.setAttribute("class", "td");
		Label label = new Label();
		label.setValue(title);
		colTitle.setUI(label);

		TableColumn colValue = row.insertCol();// 创建新的一列
		// colValue.setAttribute("class", "td");
		if (ui == null) {
			Label labelx = new Label();
			label.setValue("");
			colTitle.setUI(label);
		} else {
			colValue.setUI(ui);
		}

	}

	private static void addColWithColSpace(TableRow row, String title,
			Table table, HtmlUI ui, String columnid, int colspace) {

		TableColumn colTitle = row.insertCol();// 创建新的一列
		colTitle.setAttribute("class", "td");
		Label label = new Label();
		label.setValue(title);
		colTitle.setUI(label);

		TableColumn colValue = row.insertCol();// 创建新的一列
		colValue.setColspan(String.valueOf(colspace * 2 - 1));
		// colValue.setAttribute("class", "td");
		if (ui == null) {
			Label labelx = new Label();
			label.setValue("");
			colTitle.setUI(label);
		} else {
			colValue.setUI(ui);
		}
	}
}
