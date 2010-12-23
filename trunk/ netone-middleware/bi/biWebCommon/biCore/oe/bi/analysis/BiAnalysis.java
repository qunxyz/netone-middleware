package oe.bi.analysis;

import java.rmi.Remote;
import java.rmi.RemoteException;

import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.exceptions.ErrorDataModelException;
import oe.bi.view.obj.ViewModel;


/**
 * 分析
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface BiAnalysis extends Remote{

	/**
	 * 构建N维度业务模型
	 * 
	 * @param choiceInfo
	 * @return
	 * @throws ErrorDataModelException
	 */
	public ViewModel performBiAnaysis(ChoiceInfo choiceInfo)
			throws ErrorDataModelException,RemoteException;

}
