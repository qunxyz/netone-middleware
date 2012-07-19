package oe.midware.doc.txt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.bus.res.doc.TxtHandler;
import oe.frame.bus.res.doc.common.XmlObj;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Txt格式的文档处理
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public class TxtHandlerImpl implements TxtHandler {
	// 列的分割符号
	private String colSplit;

	// 行的分割符号
	private String lineSplit;

	private Log _log = LogFactory.getLog(TxtHandlerImpl.class);

	public void writeTxt(List value, List column, OutputStream outstream) {
		if (column == null || column.size() == 0) {
			return;
		}
		StringBuffer but = new StringBuffer();
		for (Iterator itrx = column.iterator(); itrx.hasNext();) {
			XmlObj preColumn = (XmlObj) itrx.next();
			String name = preColumn.getName() == null ? preColumn.getId()
					: preColumn.getName();
			but.append(name + colSplit);
		}
		but.append(lineSplit);

		for (Iterator itr = value.iterator(); itr.hasNext();) {
			Map obj = (Map) itr.next();

			for (Iterator itrx = column.iterator(); itrx.hasNext();) {
				XmlObj preColumn = (XmlObj) itrx.next();
				String valuePre = (String) obj.get(preColumn.getId());
				but.append(valuePre + colSplit);
			}
			but.append(lineSplit);
		}
		_log.debug("txtInfo:" + but);
		try {
			outstream.write(but.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				outstream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List readTxt(InputStream in) {
		byte[] buffer = new byte[8912];
		int size;
		StringBuffer but = new StringBuffer();
		try {
			while ((size = in.read(buffer)) != -1) {
				String valuePre = new String(buffer, 0, size);
				but.append(valuePre);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String text = but.toString();
		_log.debug("readText:" + text);
		String[] pragfi = text.split(lineSplit);

		if (pragfi == null || pragfi.length < 1) {
			return new ArrayList();
		}
		String[] column = pragfi[0].split(colSplit);
		List listValue = new ArrayList();
		for (int i = 1; i < pragfi.length; i++) {
			Map map = new HashMap();
			String[] value = pragfi[i].split(colSplit);
			int bestFitLen = value.length > column.length ? column.length
					: value.length;
			for (int j = 0; j < bestFitLen; j++) {
				_log.debug("preValue:" + column[j] + "," + value[j]);
				map.put(column[j], value[j]);
			}
			listValue.add(map);
		}
		return listValue;
	}

	public List metaData(List value) {
		if (value == null || value.size() == 0) {
			return new ArrayList();
		}
		Map map = (Map) value.get(0);
		List listValue = new ArrayList();
		for (Iterator itr = map.keySet().iterator(); itr.hasNext();) {
			String id = (String) itr.next();
			XmlObj xmlobj = new XmlObj();
			xmlobj.setId(id);

			String valueTmp = (String) map.get(id);
			try {
				Double.parseDouble(valueTmp);
				xmlobj.setHtmltype("number");
			} catch (Exception e) {
				xmlobj.setHtmltype("string");
			}
			listValue.add(xmlobj);
		}
		return listValue;
	}

	public String getColSplit() {
		return colSplit;
	}

	public void setColSplit(String colSplit) {
		this.colSplit = colSplit;
	}

	public String getLineSplit() {
		return lineSplit;
	}

	public void setLineSplit(String lineSplit) {
		this.lineSplit = lineSplit;
	}
}
