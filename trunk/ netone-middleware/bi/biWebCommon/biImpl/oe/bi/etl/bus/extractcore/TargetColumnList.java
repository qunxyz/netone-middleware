package oe.bi.etl.bus.extractcore;

import java.util.Iterator;
import java.util.List;

import oe.bi.etl.obj.TargetElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TargetColumnList {

	private static Log _log = LogFactory.getLog(TargetColumnList.class);

	/**
	 * 创建指标字段
	 * 
	 * @param targetElement
	 * @return
	 */
	public static String makeTargetColumn(List targetElement) {
		if (targetElement == null || targetElement.size() == 0) {
			_log.warn("没有选择指标");
			return "count(*)";// 如果没有指标,那么构建一个默认的指标
		}
		StringBuffer buf = new StringBuffer();
		for (Iterator itr = targetElement.iterator(); itr.hasNext();) {
			TargetElement target = (TargetElement) itr.next();
			String targetId = target.getId();
			String targetExpress = targetId;
			buf.append("," + targetExpress);
		}
		return buf.substring(1).toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
