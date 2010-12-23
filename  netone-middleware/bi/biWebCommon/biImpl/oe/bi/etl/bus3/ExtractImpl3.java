package oe.bi.etl.bus3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.bi.BiEntry;
import oe.bi.dataModel.dao.exception.NullDataSetException;
import oe.bi.dataModel.dao.exception.UnableLoadDataModel;
import oe.bi.dataModel.obj.DataModel;
import oe.bi.dataModel.obj.TargetColumn;
import oe.bi.dataModel.util.TargetNameAdapter;
import oe.bi.datasource.ConditionAdapter;
import oe.bi.datasource.DataModelDao;
import oe.bi.etl.bus.Extract;
import oe.bi.etl.bus.TargetAdapter;
import oe.bi.etl.bus.extractcore.FetchDimensionLevel;
import oe.bi.etl.bus.extractcore.FetchDimensionTypes;
import oe.bi.etl.bus.extractcore.FetchTargetId;
import oe.bi.etl.bus.extractcore.FetchTargetName;
import oe.bi.etl.bus.extractcore.TargetColumnList;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.obj.DimensionElement;
import oe.bi.etl.obj.MiddleCode;


/**
 * ExtractImpl中需要从DataModel中抽取出相关的信息，生成MiddleExpression对象 <br>
 * 算法步骤： <br>
 * 1 创建统一视图，连接模型中的表生成形如 from t1,t2....tn where t1.c1=t2.c1 and ...... 的信息 <br>
 * 2 创建维度条件，根据选择的N维度信息创建N维的步长分量条件 <br>
 * 3 创建指标信息,形如: target1,target2......targetn 的信息 4 创建维度值
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class ExtractImpl3 implements Extract {

	public MiddleCode performExtract(ChoiceInfo choice) throws UnableLoadDataModel, NullDataSetException {
		if (choice == null) {
			return null;
		}
		// 需要构建和返回的中间对象
		MiddleCode middleExpression = new MiddleCode();

		String dataModelid = choice.getDataModelid();

		DataModelDao dataModelDao = (DataModelDao) BiEntry.fetchBi("dataModelDao");
		// 获得模型对象
		DataModel dataModel = dataModelDao.fetchDataModel(dataModelid);
		// // 步骤一 创建统一视图
		// String uniformView = UniformView.makeUniformView(dataModel);
		// middleExpression.setUniformview(uniformView);

		// 需要传递模型ID，这里的模型ID对应于指标组ID
		middleExpression.setDatamodelid(choice.getDataModelid());

		List dimensionElement = null;
		if ("sample".equals(dataModelid)) {
			// 用于Sample
			dimensionElement = choice.getDimensionElement();
		} else {
			dimensionElement = new ArrayList();
		}
		// 创建维度字段 和 指标过滤条件
		TargetAdapter targetAdapter = (TargetAdapter) BiEntry.fetchBi("targetAdapter");
		String targetFileCondtion = targetAdapter.adapt(choice, dimensionElement);

		// 步骤二 创建指标字段
		List targetElement = choice.getTargetElement();
		String targetElemetns = TargetColumnList.makeTargetColumn(targetElement);
		middleExpression.setTargetColumnLink(targetElemetns);

		String[] targetColumnName = FetchTargetName.fetchTargetNameInfo(targetElement, dataModel);

		String[] targetColumnId = FetchTargetId.fetchTargetIdInfo(targetElement, dataModel);

		TargetNameAdapter.adapte(choice, targetColumnId, targetColumnName);

		// 将真实纬度信息也加入指标组中,目的是为了后期可以查询使用
		targetColumnName = rebulideTarget(targetColumnName);
		// 将真实纬度信息也加入指标组中,目的是为了后期可以查询使用
		targetColumnId = rebulideTarget(targetColumnId);

		middleExpression.setTargetColumnNames(targetColumnName);
		middleExpression.setTargetColumnIds(targetColumnId);

		String[] types = FetchDimensionTypes.fetchDimensionTypes(dimensionElement, dataModel);
		middleExpression.setDimensionTypes(types);

		// String[] dimColumnId = FetchDimensionId
		// .fetchDimensionIdInfo(dimensionElement);
		// String[] dimColumnName = FetchDimensionName.fetchDimensionNameInfo(
		// dimColumnId, dataModel);
		String[] levelinfo = FetchDimensionLevel.fetchDimensionLevel(dimensionElement);

		middleExpression.setDimensionColumnIds(new String[] { "name_en", "start_time" });
		// -- 让用户可以修订 类表中两个维度的名字 -------
		List list = choice.getDimensionElement();
		DimensionElement dim1 = (DimensionElement) list.get(0);
		DimensionElement dim2 = (DimensionElement) list.get(1);
		String dim1name = dim1.getName();
		String dim2name = dim2.getName();
		String dimname = dim1name == null || "".equals(dim1name) ? "类型" : dim1.getName();
		String timename = dim2name == null || "".equals(dim2name) ? "时间" : dim2.getName();
		// -- 让用户可以修订 类表中两个维度的名字 -------

		middleExpression.setDimensionColumnNames(new String[] { dimname, timename });
		middleExpression.setDimensionlevel(levelinfo);

		// 步骤五 创建维度条件（结合优化措施）
		ConditionAdapter conditionAdapter = (ConditionAdapter) BiEntry.fetchBi("conditionAdapter");
		String dimension = conditionAdapter.makeDimensionCondition(dimensionElement, dataModel, choice);

		String[] dimensionx = reBuildCondtion(dimension, targetFileCondtion);

		// 重新构建条件
		middleExpression.setDimensionConditions(dimensionx);

		return middleExpression;
	}

	/**
	 * 由于真实的纬度信息,在最终的查询结果中才会出现(先前是查询前就有了) 所以这里也将纬度的真实内容邦定到指标中,去执行查询
	 * 这个纬度信息,只占用指标数组的第一个元素,如果没有真实的纬度信息,那么该元素为空
	 * 
	 * @param target
	 * @param dimTarget
	 * @return
	 */
	public String[] rebulideTarget(String[] target) {

		String[] newStr = new String[target.length + 2];
		for (int i = 0; i < target.length; i++) {
			newStr[i + 2] = target[i];
		}
		newStr[0] = _DEFAULT_DIM;
		newStr[1] = _DEFAULT_TIME;
		return newStr;
	}

	/**
	 * 重新构建条件,将所有的条件结合过滤条件,整合成一个条件,为了适应先前的接口 将这个条件构建成Stirng[]返回
	 * 
	 * @param condition
	 * @param targetid
	 * @param targefileCon
	 * @return
	 */
	private String[] reBuildCondtion(String condition, String targefileCon) {
		// 将所有的条件整合成一个条件，其中倒数第二个条件是查询出的个数个数限制
		// 倒一条件是排序条件，注意如果没有查询个数限制（不过滤得情况下，程序会自动过滤前30000，排序倒序）
		StringBuffer but = new StringBuffer();

		but.append(condition);

		if (targefileCon != null && !targefileCon.equals("")) {
			but.append(targefileCon);
		} else {
			// but.append(" and rownum<10000");
		}
		return new String[] { but.toString() };
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
