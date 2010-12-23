package oe.bi.view.bus.filt;

import oe.bi.exceptions.MoreThenOneDimensionViewModel;
import oe.bi.view.obj.GraphModel;
import oe.bi.view.obj.ViewModel;


public interface DimensionFilt {

	/**
	 * ����ά�Ȳ���������ͼ����
	 * 
	 * @param viewModel:
	 *            ����ģ��
	 * @param cur����ǰҳ
	 * @param request:
	 *            web��Ϣ
	 * @return ����webҳ��չʾ�Ķ���
	 * 
	 * �㷨�ı����Ƕ�viewModel.getDimensionvalue()�е�ά��ֵ������
	 * graphModel.getOhterDimension()�е�ά��ֵ�����й����޳�����graphModel.getOhterDimension()
	 * ����û�еļ�¼�������¹���һ��viewModel��ֻ��һ��ά����raphModel.getXOffsetDimension()�е�ά��
	 * 
	 */
	ViewModel filtvalue(ViewModel viewModel, GraphModel graphModel)throws MoreThenOneDimensionViewModel;

	/**
	 * �������ά�ȵ�ֵ
	 * 
	 * @param viewModel
	 * @return [i][0] dimensionId [i][1]dimensionName
	 *         [i][2]dimensionValueLink(split by symbol",")
	 */
	String[][] allDimensionValueList(ViewModel viewModel);


}
