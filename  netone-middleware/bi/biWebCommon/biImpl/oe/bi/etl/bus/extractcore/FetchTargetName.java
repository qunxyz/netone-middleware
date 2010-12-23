package oe.bi.etl.bus.extractcore;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.TargetColumn;
import oe.bi.etl.obj.TargetElement;


public class FetchTargetName {

	public static String[] fetchTargetNameInfo(List targetElement, DataModel datamodel) {
		Map map = datamodel.getTargetColumns();
		String[] targetNames = new String[targetElement.size()];
		int i=0;
		for (Iterator itr= targetElement.iterator();itr.hasNext(); ) {
			TargetElement targetElementPre=(TargetElement)itr.next();
			if (map.containsKey(targetElementPre.getId())) {
				TargetColumn targetColumn = (TargetColumn) map.get(targetElementPre.getId());
				targetNames[i++] = targetColumn.getName();
			}
		}
		return targetNames;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
