package oe.bi.analysis;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import oe.bi.BiEntry;
import oe.bi.analysis.BiAnalysis;
import oe.bi.dataModel.dao.exception.NullDataSetException;
import oe.bi.dataModel.dao.exception.UnableLoadDataModel;
import oe.bi.etl.bus.Extract;
import oe.bi.etl.bus.Load;
import oe.bi.etl.bus.Transform;
import oe.bi.etl.obj.AimCode;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.obj.MiddleCode;
import oe.bi.exceptions.ErrorDataModelException;
import oe.bi.view.obj.ViewModel;


public class BiAnalysisImpl2 extends UnicastRemoteObject implements BiAnalysis {
	public BiAnalysisImpl2() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public ViewModel performBiAnaysis(ChoiceInfo choiceInfo)
			throws ErrorDataModelException, RemoteException {
		Extract extract = (Extract) BiEntry.fetchBi("extract");
		Transform transform = (Transform) BiEntry.fetchBi("transform");
		Load load = (Load) BiEntry.fetchBi("load");
		try {
			// 抽取操作 Extract;
			MiddleCode midcode = extract.performExtract(choiceInfo);
			// 转换操作
			AimCode aimcode = transform.performTransform(midcode);
			// 数据装载
			return load.performLoad(aimcode,choiceInfo);

		} catch (UnableLoadDataModel e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullDataSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
