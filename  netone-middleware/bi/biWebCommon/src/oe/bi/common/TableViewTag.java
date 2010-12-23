package oe.bi.common;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import oe.bi.analysis.BiAnalysis;
import oe.bi.analysis.util.BiAnalysisBind;
import oe.bi.etl.obj.ChoiceInfo;
import oe.bi.etl.obj.DimensionElement;
import oe.bi.exceptions.ErrorDataModelException;
import oe.bi.view.obj.ViewModel;
import oe.bi.wizard.WizardDao;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;


public class TableViewTag extends SimpleTagSupport {
	private String ananame;

	private int begin;

	private int end;

	private String dataname;

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getAnaname() {
		return ananame;
	}

	public void setAnaname(String ananame) {
		this.ananame = ananame;
	}

	public String getDataname() {
		return dataname;
	}

	public void setDataname(String dataname) {
		this.dataname = dataname;
	}

	public void doTag() throws JspException {
		ResourceRmi rsrmi = null;
		String lsh = "";
		try {
			rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			String naturalname = ananame;
			UmsProtectedobject upo = rsrmi.loadResourceByNatural(naturalname);
			lsh = upo.getExtendattribute();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		WizardDao wd = null;
		ChoiceInfo choiceinfo = null;
		try {
			wd = (WizardDao) RmiEntry.iv("bihandle");
			choiceinfo = wd.fromXml(lsh);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List oldlist = choiceinfo.getDimensionElement();
		List<DimensionElement> newlist = new ArrayList<DimensionElement>();
		for (Iterator iterator = oldlist.iterator(); iterator.hasNext();) {
			DimensionElement de = (DimensionElement) iterator.next();
			String id = de.getId();
			if (StringUtils.contains(id, ":")) {
				id = StringUtils.substringBefore(id, ":");
				de.setId(id);
			}
			newlist.add(de);
		}
		choiceinfo.setDimensionElement(newlist);
		List<ViewModel> viewList = new ArrayList<ViewModel>();
		BiAnalysis analysis=null;
		try {
			analysis = (BiAnalysis)RmiEntry.iv("biAnalysis");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ViewModel vm = analysis.performBiAnaysis(choiceinfo);
			viewList.add(vm);
		} catch (ErrorDataModelException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ViewModel viewmodel = BiAnalysisBind.bindViewModel(viewList, null);

		Map<String, Object> map = new LinkedHashMap<String, Object>();

		// 指标值
		double[][] targetValue = viewmodel.getTargetvalue();

		// 维度值
		String[][] dimValue = viewmodel.getDimensionvalue();

		// 指标名称
		String[] targetName = viewmodel.getTargetname();

		// 维度名称
		String[] dimName = viewmodel.getDimensionname();

		if (targetValue != null) {
			int beginindex = this.begin;
			int endindex = this.end;
			if (this.end > targetValue.length) {
				endindex = targetValue.length;
			}

			double[][] reTargetValue = new double[endindex - beginindex][targetName.length];
			String[][] reDimValue = new String[endindex - beginindex][dimName.length];
			if (beginindex >= 0) {
				System.arraycopy(targetValue, beginindex, reTargetValue, 0,
						endindex - beginindex);
				System.arraycopy(dimValue, beginindex, reDimValue, 0, endindex
						- beginindex);
				map.put("targetValue", reTargetValue);
				map.put("dimValue", reDimValue);
			}
		}
		map.put("targetName", targetName);
		map.put("dimName", dimName);

		getJspContext().setAttribute(this.dataname, map);

	}

}
