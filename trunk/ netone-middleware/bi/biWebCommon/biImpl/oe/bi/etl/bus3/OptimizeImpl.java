package oe.bi.etl.bus3;

import java.util.Map;

import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.OptimizeTable;
import oe.bi.etl.bus.Optimize;
import oe.bi.etl.obj.MiddleCode;

import org.apache.commons.lang.StringUtils;


public class OptimizeImpl implements Optimize {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void performOptimize(MiddleCode middlecode, DataModel dataModel,
			OptimizeTable opt) {
		// 修改统一视图
		middlecode.setUniformview(modifyUniformview(opt));
		// 修改指标
		modifyTarget(middlecode, opt);

	}

	private String modifyUniformview(OptimizeTable opt) {
		return " from " + opt.getId() + " where 1=1 and ";
	}

	private void modifyTarget(MiddleCode middlecode, OptimizeTable opt) {

		// 修改指标
		String[] targetid = middlecode.getTargetColumnIds();
		String targetLink = middlecode.getTargetColumnLink();

		Map mapTarget = opt.fetchTargetmap();
		for (int i = 0; i < targetid.length; i++) {
			String thisTarget = (String) mapTarget.get(targetid[i]);
			// 修改指标链表
			targetLink = StringUtils.replaceOnce(targetLink, targetid[i],
					thisTarget);
		}
		middlecode.setTargetColumnLink(targetLink);

		Map mapTarKeyName = opt.fetchTargetmapKeyName();
		String[] targetNames = middlecode.getTargetColumnNames();
		for (int i = 0; i < targetid.length; i++) {
			String thisTarget = (String) mapTarget.get(targetid[i]);
			Object targetName = mapTarKeyName.get(targetid[i]);
			if (targetName != null) {
				targetNames[i] = targetName.toString();
			}
			targetid[i] = thisTarget;

		}
		middlecode.setTargetColumnIds(targetid);
		middlecode.setTargetColumnNames(targetNames);
	}

}
