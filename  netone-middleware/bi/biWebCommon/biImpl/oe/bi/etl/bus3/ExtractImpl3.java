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
 * ExtractImpl����Ҫ��DataModel�г�ȡ����ص���Ϣ������MiddleExpression���� <br>
 * �㷨���裺 <br>
 * 1 ����ͳһ��ͼ������ģ���еı��������� from t1,t2....tn where t1.c1=t2.c1 and ...... ����Ϣ <br>
 * 2 ����ά������������ѡ���Nά����Ϣ����Nά�Ĳ����������� <br>
 * 3 ����ָ����Ϣ,����: target1,target2......targetn ����Ϣ 4 ����ά��ֵ
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public class ExtractImpl3 implements Extract {

	public MiddleCode performExtract(ChoiceInfo choice) throws UnableLoadDataModel, NullDataSetException {
		if (choice == null) {
			return null;
		}
		// ��Ҫ�����ͷ��ص��м����
		MiddleCode middleExpression = new MiddleCode();

		String dataModelid = choice.getDataModelid();

		DataModelDao dataModelDao = (DataModelDao) BiEntry.fetchBi("dataModelDao");
		// ���ģ�Ͷ���
		DataModel dataModel = dataModelDao.fetchDataModel(dataModelid);
		// // ����һ ����ͳһ��ͼ
		// String uniformView = UniformView.makeUniformView(dataModel);
		// middleExpression.setUniformview(uniformView);

		// ��Ҫ����ģ��ID�������ģ��ID��Ӧ��ָ����ID
		middleExpression.setDatamodelid(choice.getDataModelid());

		List dimensionElement = null;
		if ("sample".equals(dataModelid)) {
			// ����Sample
			dimensionElement = choice.getDimensionElement();
		} else {
			dimensionElement = new ArrayList();
		}
		// ����ά���ֶ� �� ָ���������
		TargetAdapter targetAdapter = (TargetAdapter) BiEntry.fetchBi("targetAdapter");
		String targetFileCondtion = targetAdapter.adapt(choice, dimensionElement);

		// ����� ����ָ���ֶ�
		List targetElement = choice.getTargetElement();
		String targetElemetns = TargetColumnList.makeTargetColumn(targetElement);
		middleExpression.setTargetColumnLink(targetElemetns);

		String[] targetColumnName = FetchTargetName.fetchTargetNameInfo(targetElement, dataModel);

		String[] targetColumnId = FetchTargetId.fetchTargetIdInfo(targetElement, dataModel);

		TargetNameAdapter.adapte(choice, targetColumnId, targetColumnName);

		// ����ʵγ����ϢҲ����ָ������,Ŀ����Ϊ�˺��ڿ��Բ�ѯʹ��
		targetColumnName = rebulideTarget(targetColumnName);
		// ����ʵγ����ϢҲ����ָ������,Ŀ����Ϊ�˺��ڿ��Բ�ѯʹ��
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
		// -- ���û������޶� ���������ά�ȵ����� -------
		List list = choice.getDimensionElement();
		DimensionElement dim1 = (DimensionElement) list.get(0);
		DimensionElement dim2 = (DimensionElement) list.get(1);
		String dim1name = dim1.getName();
		String dim2name = dim2.getName();
		String dimname = dim1name == null || "".equals(dim1name) ? "����" : dim1.getName();
		String timename = dim2name == null || "".equals(dim2name) ? "ʱ��" : dim2.getName();
		// -- ���û������޶� ���������ά�ȵ����� -------

		middleExpression.setDimensionColumnNames(new String[] { dimname, timename });
		middleExpression.setDimensionlevel(levelinfo);

		// ������ ����ά������������Ż���ʩ��
		ConditionAdapter conditionAdapter = (ConditionAdapter) BiEntry.fetchBi("conditionAdapter");
		String dimension = conditionAdapter.makeDimensionCondition(dimensionElement, dataModel, choice);

		String[] dimensionx = reBuildCondtion(dimension, targetFileCondtion);

		// ���¹�������
		middleExpression.setDimensionConditions(dimensionx);

		return middleExpression;
	}

	/**
	 * ������ʵ��γ����Ϣ,�����յĲ�ѯ����вŻ����(��ǰ�ǲ�ѯǰ������) ��������Ҳ��γ�ȵ���ʵ���ݰ��ָ����,ȥִ�в�ѯ
	 * ���γ����Ϣ,ֻռ��ָ������ĵ�һ��Ԫ��,���û����ʵ��γ����Ϣ,��ô��Ԫ��Ϊ��
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
	 * ���¹�������,�����е�������Ϲ�������,���ϳ�һ������,Ϊ����Ӧ��ǰ�Ľӿ� ���������������Stirng[]����
	 * 
	 * @param condition
	 * @param targetid
	 * @param targefileCon
	 * @return
	 */
	private String[] reBuildCondtion(String condition, String targefileCon) {
		// �����е��������ϳ�һ�����������е����ڶ��������ǲ�ѯ���ĸ�����������
		// ��һ����������������ע�����û�в�ѯ�������ƣ������˵�����£�������Զ�����ǰ30000��������
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
