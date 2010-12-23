package oe.bi.analysis;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

import oe.bi.BiEntry;
import oe.bi.analysis.BiForcast;
import oe.bi.analysis.forcastcore.ForcastCore2;
import oe.bi.dataModel.obj.ext.SqlTypes;
import oe.bi.exceptions.MoreThenOneDimensionViewModel;
import oe.bi.exceptions.NeedMoreThenForcastOneValueException;
import oe.bi.view.bus.filt.DimensionFilt;
import oe.bi.view.obj.GraphModel;
import oe.bi.view.obj.ViewModel;


/**
 * 
 * @author chen.jia.xun
 * 
 */
public class BiForcastImpl extends UnicastRemoteObject implements BiForcast {
	
	public BiForcastImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public ViewModel performBiForcast(ViewModel arg0, GraphModel graph,
			Map mapmodifyvalue) throws MoreThenOneDimensionViewModel,
			NeedMoreThenForcastOneValueException, RemoteException {

		DimensionFilt dimensionFilt = (DimensionFilt) BiEntry
				.fetchBi("dimensionFilt");
		ViewModel filtViewModel = dimensionFilt.filtvalue(arg0, graph);
		ForcastCore2.forcastCore2(filtViewModel, graph, mapmodifyvalue);
		return filtViewModel;
	}

	public String fetchTimeDimensionLink(ViewModel viewModel)
			throws RemoteException {
		String[] typs = viewModel.getDimensionType();
		String[] dimid = viewModel.getDimensionid();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < typs.length; i++) {

			if (SqlTypes._DIM_TYPE_DATE[0].equals(typs[i])) {
				buf.append(dimid[i] + ",");
			}
		}
		return buf.toString();
	}

}
