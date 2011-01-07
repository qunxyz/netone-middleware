package oe.web.tag.bi;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
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
		BiAnalysis analysis = null;
		try {
			analysis = (BiAnalysis) RmiEntry.iv("biAnalysis");
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
		String[] targetNaturalname = viewmodel.getTargetid();
		// 维度名称
		String[] dimName = viewmodel.getDimensionname();
		String[] dimNatualname = viewmodel.getDimensionid();

		Map mapOtherdim = viewmodel.getOtherDim();
		int otherdimnum = 0;
		if (mapOtherdim != null && mapOtherdim.size() > 0) {
			for (Iterator iterator = mapOtherdim.keySet().iterator(); iterator
					.hasNext();) {
				String keyname = (String) iterator.next();
				List list = (List) mapOtherdim.get(keyname);
				otherdimnum = list.size();
			}
		}
		// 其他维度名
		List otherdimKey = new ArrayList();
		for (Iterator iterator = mapOtherdim.keySet().iterator(); iterator
				.hasNext();) {
			String key = (String) iterator.next();
			otherdimKey.add(key);
		}

		if (targetValue != null) {
			int beginindex = this.begin;
			int endindex = this.end;
			if (this.end > targetValue.length) {
				endindex = targetValue.length;
			}

			double[][] reTargetValue = new double[endindex - beginindex][targetName.length];
			String[][] reDimValue = new String[endindex - beginindex][dimName.length];
			String[][] reDimValueOther = new String[endindex - beginindex][otherdimnum];
			if (beginindex >= 0) {
				// 指标值
				System.arraycopy(targetValue, beginindex, reTargetValue, 0,
						endindex - beginindex);
				// 维度值
				System.arraycopy(dimValue, beginindex, reDimValue, 0, endindex
						- beginindex);
				// 其他维度值
				int rowi = 0;
				for (Iterator iterator = mapOtherdim.keySet().iterator(); iterator
						.hasNext();) {
					String keyname = (String) iterator.next();
					List list = (List) mapOtherdim.get(keyname);
					int colj = 0;
					for (Iterator iterator2 = list.iterator(); iterator2
							.hasNext();) {
						String value = (String) iterator2.next();
						reDimValueOther[colj++][rowi] = value;
					}
					rowi++;
				}
				// 处理并返回指标数据结果
				List list = asList(dimNatualname, dimName, targetNaturalname,
						targetName, otherdimKey, reDimValue, reDimValueOther,
						reTargetValue);

//				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
//					Map object = (Map) iterator.next();
//					for (Iterator iterator2 = object.keySet().iterator(); iterator2
//							.hasNext();) {
//						String key = (String) iterator2.next();
//						Object name = object.get(key);
//						System.out.print(key + "," + name + " ");
//					}
//					System.out.println();
//				}

				getJspContext().setAttribute(this.dataname, list);
			}
		}

	}

	/**
	 * 处理最终的指标数据，以类似DB查询结果的模式返回
	 * 
	 * @param dimname
	 *            维度名字
	 * @param targetname
	 *            指标名字
	 * @param otherdimkey
	 *            其他维度名字
	 * @param dimvalue
	 *            维度值
	 * @param otherdimvalue
	 *            其他维度值
	 * @param targetValue
	 *            指标值
	 * @return
	 */
	public List asList(String[] dimid, String[] dimname, String[] targetid,
			String[] targetname, List otherdimkey, String[][] dimvalue,
			String[][] otherdimvalue, double[][] targetValue) {
		List list = new ArrayList();// 存储最终的报表展现数据
		// 添加的是相关字段的 中文名
		Map firstname = new HashMap();

		for (int i = 0; i < dimid.length; i++) {
			firstname.put(dimid[i], dimname[i]);
		}

		for (int i = 0; i < targetid.length; i++) {
			firstname.put(targetid[i], targetname[i]);
		}
		String[] otherdimkeyArr = (String[]) otherdimkey.toArray(new String[0]);
		for (int i = 0; i < otherdimkeyArr.length; i++) {
			System.out.println(otherdimkeyArr[i]);
			String key = StringUtils.substringBefore(otherdimkeyArr[i], "[");
			String name = StringUtils.substringBetween(otherdimkeyArr[i], "[",
					"]");
			otherdimkeyArr[i] = key;
			firstname.put(key, name);
			
			
		}
		list.add(firstname);
		// /////完成添加//////////////////

		for (int i = 0; i < targetValue.length; i++) {
			Map map = new HashMap();// 将报表数据以类似DB的查询结果模式来存储返回，以方便前台展现享用
			for (int j = 0; j < dimid.length; j++) {// 添加一行维度数据
				map.put(dimid[j], dimvalue[i][j]);
			}
			for (int j = 0; j < targetid.length; j++) {// 添加一行指标数据

				map.put(targetid[j], targetValue[i][j]);
			}
			for (int j = 0; j < otherdimkey.size(); j++) {// 添加一行的其他维度数据
				map.put(otherdimkeyArr[j], otherdimvalue[i][j]);
				System.out.println(otherdimkeyArr[j]+","+otherdimvalue[i][j]);
			}
			System.out.println();
			list.add(map);// 完成构造一行的维度数据
		}

		return list;
	}
}
