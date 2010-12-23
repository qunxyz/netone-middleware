package oe.bi.analysis;

import java.rmi.Remote;
import java.rmi.RemoteException;

import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.exceptions.ErrorDataModelException;
import oe.bi.view.obj.ViewModel;


/**
 * ����
 * 
 * @author chen.jia.xun (Robanco)
 * 
 */
public interface BiAnalysis extends Remote{

	/**
	 * ����Nά��ҵ��ģ��
	 * 
	 * @param choiceInfo
	 * @return
	 * @throws ErrorDataModelException
	 */
	public ViewModel performBiAnaysis(ChoiceInfo choiceInfo)
			throws ErrorDataModelException,RemoteException;

}
