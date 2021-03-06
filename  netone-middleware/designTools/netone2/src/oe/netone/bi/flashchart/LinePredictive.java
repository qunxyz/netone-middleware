package oe.netone.bi.flashchart;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.cav.bean.logic.column.TCsColumn;
import oe.midware.dyform.service.DyFormDesignService;
import oe.netone.bi.ExpendTime;
import oe.netone.bi.ManageBI;
import oe.netone.bi.PAnalysis;
import oe.netone.bi.xyData;
import oe.rmi.client.RmiEntry;


import com.report.chart.entity.LineChartObj;

import flex.messaging.io.ArrayCollection;
/**
 *  线图的  预测方法
 *   xuwei（2012-2-15）
 *   
 *   
 *   
 * */
public class LinePredictive {

	public static List predictiveList(String predictionbegan,
			String predictionend, String Predictiontype, String xdata,
			String[] zbList, String limitcolumn, String limit,
			String tablename, int j, String Formcode) {

		List Linelist = new ArrayList();
		List<PAnalysis> fsdata = null;
		List<PAnalysis> fsdata1 = null;
		List<PAnalysis> fsdata2 = null;
		List<PAnalysis> fsdata3 = null;
		List<PAnalysis> fsdata4 = null;
		List<PAnalysis> fsdata5 = null;
		List<PAnalysis> fsdata6 = null;
		List<PAnalysis> fsdata7 = null;

		String[] predictionarr = null;
		List<xyData> predictionData = null;
		List<xyData> predictionData1 = null;
		List<xyData> predictionData2 = null;
		List<xyData> predictionData3 = null;
		List<xyData> predictionData4 = null;
		List<xyData> predictionData5 = null;
		List<xyData> predictionData6 = null;
		List<xyData> predictionData7 = null;

		TCsColumn busForm1 = new TCsColumn();
		busForm1.setFormcode(Formcode);
		// long indexname=dys.queryObjectsNumber(busForm);
		List listmame1 = null;
		DyFormDesignService dys = null;

		try {
			dys = (DyFormDesignService) RmiEntry.iv("dydesign");
			listmame1 = dys.queryObjects(busForm1);
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 获取到row的键值
		String yString = "";
		String y1String = "";
		String y2String = "";
		String y3String = "";
		String y4String = "";
		String y5String = "";
		String y6String = "";
		String y7String = "";

		for (Iterator iterator1 = listmame1.iterator(); iterator1.hasNext();) {
			TCsColumn columnname = (TCsColumn) iterator1.next();

			for (int i = 0; i < zbList.length; i++) {
				if (columnname.getColumncode().equals(zbList[i])) {
					if (i == 0) {
						yString = columnname.getColumname();
					}
					if (i == 1) {
						y1String = columnname.getColumname();
					}
					if (i == 2) {
						y2String = columnname.getColumname();
					}
					if (i == 3) {
						y3String = columnname.getColumname();
					}
					if (i == 4) {
						y4String = columnname.getColumname();
					}
					if (i == 5) {
						y5String = columnname.getColumname();
					}
					if (i == 6) {
						y6String = columnname.getColumname();
					}
					if (i == 7) {
						y7String = columnname.getColumname();
					}
				}
			}
		}

		if (predictionbegan != "" || predictionbegan != null
				|| predictionend != "" || predictionend != null) {
			predictionarr = ExpendTime.getExpendTime(predictionbegan,
					predictionend);
			ArrayCollection farr = new ArrayCollection();
			for (int i = 0; i < predictionarr.length; i++) {
				farr.add(predictionarr[i]);
			}
			switch (j) {
			case 2:
				try {
					fsdata = ManageBI.ExpendXydata(xdata, zbList[0],
							limitcolumn, limit, tablename);
					predictionData = ForecastMethod.ExpendValue(fsdata, farr,
							Predictiontype);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3:
				try {
					fsdata = ManageBI.ExpendXydata(xdata, zbList[0],
							limitcolumn, limit, tablename);
					predictionData = ForecastMethod.ExpendValue(fsdata, farr,
							Predictiontype);

					fsdata1 = ManageBI.ExpendXydata(xdata, zbList[1],
							limitcolumn, limit, tablename);
					predictionData1 = ForecastMethod.ExpendValue(fsdata1, farr,
							Predictiontype);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 4:
				try {
					fsdata = ManageBI.ExpendXydata(xdata, zbList[0],
							limitcolumn, limit, tablename);
					predictionData = ForecastMethod.ExpendValue(fsdata, farr,
							Predictiontype);

					fsdata1 = ManageBI.ExpendXydata(xdata, zbList[1],
							limitcolumn, limit, tablename);
					predictionData1 = ForecastMethod.ExpendValue(fsdata1, farr,
							Predictiontype);

					fsdata2 = ManageBI.ExpendXydata(xdata, zbList[2],
							limitcolumn, limit, tablename);
					predictionData2 = ForecastMethod.ExpendValue(fsdata2, farr,
							Predictiontype);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 5:
				try {
					fsdata = ManageBI.ExpendXydata(xdata, zbList[0],
							limitcolumn, limit, tablename);
					predictionData = ForecastMethod.ExpendValue(fsdata, farr,
							Predictiontype);

					fsdata1 = ManageBI.ExpendXydata(xdata, zbList[1],
							limitcolumn, limit, tablename);
					predictionData1 = ForecastMethod.ExpendValue(fsdata1, farr,
							Predictiontype);

					fsdata2 = ManageBI.ExpendXydata(xdata, zbList[2],
							limitcolumn, limit, tablename);
					predictionData2 = ForecastMethod.ExpendValue(fsdata2, farr,
							Predictiontype);

					fsdata3 = ManageBI.ExpendXydata(xdata, zbList[3],
							limitcolumn, limit, tablename);
					predictionData3 = ForecastMethod.ExpendValue(fsdata3, farr,
							Predictiontype);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 6:
				try {
					fsdata = ManageBI.ExpendXydata(xdata, zbList[0],
							limitcolumn, limit, tablename);
					predictionData = ForecastMethod.ExpendValue(fsdata, farr,
							Predictiontype);

					fsdata1 = ManageBI.ExpendXydata(xdata, zbList[1],
							limitcolumn, limit, tablename);
					predictionData1 = ForecastMethod.ExpendValue(fsdata1, farr,
							Predictiontype);

					fsdata2 = ManageBI.ExpendXydata(xdata, zbList[2],
							limitcolumn, limit, tablename);
					predictionData2 = ForecastMethod.ExpendValue(fsdata2, farr,
							Predictiontype);

					fsdata3 = ManageBI.ExpendXydata(xdata, zbList[3],
							limitcolumn, limit, tablename);
					predictionData3 = ForecastMethod.ExpendValue(fsdata3, farr,
							Predictiontype);

					fsdata4 = ManageBI.ExpendXydata(xdata, zbList[4],
							limitcolumn, limit, tablename);
					predictionData4 = ForecastMethod.ExpendValue(fsdata4, farr,
							Predictiontype);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 7:
				try {
					fsdata = ManageBI.ExpendXydata(xdata, zbList[0],
							limitcolumn, limit, tablename);
					predictionData = ForecastMethod.ExpendValue(fsdata, farr,
							Predictiontype);

					fsdata1 = ManageBI.ExpendXydata(xdata, zbList[1],
							limitcolumn, limit, tablename);
					predictionData1 = ForecastMethod.ExpendValue(fsdata1, farr,
							Predictiontype);

					fsdata2 = ManageBI.ExpendXydata(xdata, zbList[2],
							limitcolumn, limit, tablename);
					predictionData2 = ForecastMethod.ExpendValue(fsdata2, farr,
							Predictiontype);

					fsdata3 = ManageBI.ExpendXydata(xdata, zbList[3],
							limitcolumn, limit, tablename);
					predictionData3 = ForecastMethod.ExpendValue(fsdata3, farr,
							Predictiontype);

					fsdata4 = ManageBI.ExpendXydata(xdata, zbList[4],
							limitcolumn, limit, tablename);
					predictionData4 = ForecastMethod.ExpendValue(fsdata4, farr,
							Predictiontype);

					fsdata5 = ManageBI.ExpendXydata(xdata, zbList[5],
							limitcolumn, limit, tablename);
					predictionData5 = ForecastMethod.ExpendValue(fsdata5, farr,
							Predictiontype);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 8:
				try {
					fsdata = ManageBI.ExpendXydata(xdata, zbList[0],
							limitcolumn, limit, tablename);
					predictionData = ForecastMethod.ExpendValue(fsdata, farr,
							Predictiontype);

					fsdata1 = ManageBI.ExpendXydata(xdata, zbList[1],
							limitcolumn, limit, tablename);
					predictionData1 = ForecastMethod.ExpendValue(fsdata1, farr,
							Predictiontype);

					fsdata2 = ManageBI.ExpendXydata(xdata, zbList[2],
							limitcolumn, limit, tablename);
					predictionData2 = ForecastMethod.ExpendValue(fsdata2, farr,
							Predictiontype);

					fsdata3 = ManageBI.ExpendXydata(xdata, zbList[3],
							limitcolumn, limit, tablename);
					predictionData3 = ForecastMethod.ExpendValue(fsdata3, farr,
							Predictiontype);

					fsdata4 = ManageBI.ExpendXydata(xdata, zbList[4],
							limitcolumn, limit, tablename);
					predictionData4 = ForecastMethod.ExpendValue(fsdata4, farr,
							Predictiontype);

					fsdata5 = ManageBI.ExpendXydata(xdata, zbList[5],
							limitcolumn, limit, tablename);
					predictionData5 = ForecastMethod.ExpendValue(fsdata5, farr,
							Predictiontype);

					fsdata6 = ManageBI.ExpendXydata(xdata, zbList[6],
							limitcolumn, limit, tablename);
					predictionData6 = ForecastMethod.ExpendValue(fsdata6, farr,
							Predictiontype);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 9:
				try {
					fsdata = ManageBI.ExpendXydata(xdata, zbList[0],
							limitcolumn, limit, tablename);
					predictionData = ForecastMethod.ExpendValue(fsdata, farr,
							Predictiontype);

					fsdata1 = ManageBI.ExpendXydata(xdata, zbList[1],
							limitcolumn, limit, tablename);
					predictionData1 = ForecastMethod.ExpendValue(fsdata1, farr,
							Predictiontype);

					fsdata2 = ManageBI.ExpendXydata(xdata, zbList[2],
							limitcolumn, limit, tablename);
					predictionData2 = ForecastMethod.ExpendValue(fsdata2, farr,
							Predictiontype);

					fsdata3 = ManageBI.ExpendXydata(xdata, zbList[3],
							limitcolumn, limit, tablename);
					predictionData3 = ForecastMethod.ExpendValue(fsdata3, farr,
							Predictiontype);

					fsdata4 = ManageBI.ExpendXydata(xdata, zbList[4],
							limitcolumn, limit, tablename);
					predictionData4 = ForecastMethod.ExpendValue(fsdata4, farr,
							Predictiontype);

					fsdata5 = ManageBI.ExpendXydata(xdata, zbList[5],
							limitcolumn, limit, tablename);
					predictionData5 = ForecastMethod.ExpendValue(fsdata5, farr,
							Predictiontype);

					fsdata6 = ManageBI.ExpendXydata(xdata, zbList[6],
							limitcolumn, limit, tablename);
					predictionData6 = ForecastMethod.ExpendValue(fsdata6, farr,
							Predictiontype);

					fsdata7 = ManageBI.ExpendXydata(xdata, zbList[7],
							limitcolumn, limit, tablename);
					predictionData7 = ForecastMethod.ExpendValue(fsdata7, farr,
							Predictiontype);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				break;
			}

			if (predictionData != null) {
				for (int i = 0; i < predictionData.size(); i++) {
					if (j == 2) {
						double shuju = Double.valueOf(
								predictionData.get(i).getYdata()).doubleValue();
						LineChartObj Lineoyu = new LineChartObj();
						Lineoyu.setValue(shuju);
						Lineoyu.setRowKey(yString);
						Lineoyu.setColumnKey(predictionData.get(i).xdata);
						Linelist.add(Lineoyu);
					}
					if (j == 3) {
						double shuju = Double.valueOf(
								predictionData.get(i).getYdata()).doubleValue();
						LineChartObj Lineoyu = new LineChartObj();
						Lineoyu.setValue(shuju);
						Lineoyu.setRowKey(yString);
						Lineoyu.setColumnKey(predictionData.get(i).xdata);
						Linelist.add(Lineoyu);

						double shuju1 = Double.valueOf(
								predictionData1.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu1 = new LineChartObj();
						Lineoyu1.setValue(shuju1);
						Lineoyu1.setRowKey(y1String);
						Lineoyu1.setColumnKey(predictionData1.get(i).xdata);
						Linelist.add(Lineoyu1);

					}
					if (j == 4) {
						double shuju = Double.valueOf(
								predictionData.get(i).getYdata()).doubleValue();
						LineChartObj Lineoyu = new LineChartObj();
						Lineoyu.setValue(shuju);
						Lineoyu.setRowKey(yString);
						Lineoyu.setColumnKey(predictionData.get(i).xdata);
						Linelist.add(Lineoyu);

						double shuju1 = Double.valueOf(
								predictionData1.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu1 = new LineChartObj();
						Lineoyu1.setValue(shuju1);
						Lineoyu1.setRowKey(y1String);
						Lineoyu1.setColumnKey(predictionData1.get(i).xdata);
						Linelist.add(Lineoyu1);

						double shuju2 = Double.valueOf(
								predictionData2.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu2 = new LineChartObj();
						Lineoyu2.setValue(shuju2);
						Lineoyu2.setRowKey(y2String);
						Lineoyu2.setColumnKey(predictionData2.get(i).xdata);
						Linelist.add(Lineoyu2);
					}
					if (j == 5) {
						double shuju = Double.valueOf(
								predictionData.get(i).getYdata()).doubleValue();
						LineChartObj Lineoyu = new LineChartObj();
						Lineoyu.setValue(shuju);
						Lineoyu.setRowKey(yString);
						Lineoyu.setColumnKey(predictionData.get(i).xdata);
						Linelist.add(Lineoyu);

						double shuju1 = Double.valueOf(
								predictionData1.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu1 = new LineChartObj();
						Lineoyu1.setValue(shuju1);
						Lineoyu1.setRowKey(y1String);
						Lineoyu1.setColumnKey(predictionData1.get(i).xdata);
						Linelist.add(Lineoyu1);

						double shuju2 = Double.valueOf(
								predictionData2.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu2 = new LineChartObj();
						Lineoyu2.setValue(shuju2);
						Lineoyu2.setRowKey(y2String);
						Lineoyu2.setColumnKey(predictionData2.get(i).xdata);
						Linelist.add(Lineoyu2);

						double shuju3 = Double.valueOf(
								predictionData3.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu3 = new LineChartObj();
						Lineoyu3.setValue(shuju3);
						Lineoyu3.setRowKey(y3String);
						Lineoyu3.setColumnKey(predictionData3.get(i).xdata);
						Linelist.add(Lineoyu3);
					}
					if (j == 6) {
						double shuju = Double.valueOf(
								predictionData.get(i).getYdata()).doubleValue();
						LineChartObj Lineoyu = new LineChartObj();
						Lineoyu.setValue(shuju);
						Lineoyu.setRowKey(yString);
						Lineoyu.setColumnKey(predictionData.get(i).xdata);
						Linelist.add(Lineoyu);

						double shuju1 = Double.valueOf(
								predictionData1.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu1 = new LineChartObj();
						Lineoyu1.setValue(shuju1);
						Lineoyu1.setRowKey(y1String);
						Lineoyu1.setColumnKey(predictionData1.get(i).xdata);
						Linelist.add(Lineoyu1);

						double shuju2 = Double.valueOf(
								predictionData2.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu2 = new LineChartObj();
						Lineoyu2.setValue(shuju2);
						Lineoyu2.setRowKey(y2String);
						Lineoyu2.setColumnKey(predictionData2.get(i).xdata);
						Linelist.add(Lineoyu2);

						double shuju3 = Double.valueOf(
								predictionData3.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu3 = new LineChartObj();
						Lineoyu3.setValue(shuju3);
						Lineoyu3.setRowKey(y3String);
						Lineoyu3.setColumnKey(predictionData3.get(i).xdata);
						Linelist.add(Lineoyu3);

						double shuju4 = Double.valueOf(
								predictionData4.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu4 = new LineChartObj();
						Lineoyu4.setValue(shuju4);
						Lineoyu4.setRowKey(y4String);
						Lineoyu4.setColumnKey(predictionData4.get(i).xdata);
						Linelist.add(Lineoyu4);
					}
					if (j == 7) {
						double shuju = Double.valueOf(
								predictionData.get(i).getYdata()).doubleValue();
						LineChartObj Lineoyu = new LineChartObj();
						Lineoyu.setValue(shuju);
						Lineoyu.setRowKey(yString);
						Lineoyu.setColumnKey(predictionData.get(i).xdata);
						Linelist.add(Lineoyu);

						double shuju1 = Double.valueOf(
								predictionData1.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu1 = new LineChartObj();
						Lineoyu1.setValue(shuju1);
						Lineoyu1.setRowKey(y1String);
						Lineoyu1.setColumnKey(predictionData1.get(i).xdata);
						Linelist.add(Lineoyu1);

						double shuju2 = Double.valueOf(
								predictionData2.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu2 = new LineChartObj();
						Lineoyu2.setValue(shuju2);
						Lineoyu2.setRowKey(y2String);
						Lineoyu2.setColumnKey(predictionData2.get(i).xdata);
						Linelist.add(Lineoyu2);

						double shuju3 = Double.valueOf(
								predictionData3.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu3 = new LineChartObj();
						Lineoyu3.setValue(shuju3);
						Lineoyu3.setRowKey(y3String);
						Lineoyu3.setColumnKey(predictionData3.get(i).xdata);
						Linelist.add(Lineoyu3);

						double shuju4 = Double.valueOf(
								predictionData4.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu4 = new LineChartObj();
						Lineoyu4.setValue(shuju4);
						Lineoyu4.setRowKey(y4String);
						Lineoyu4.setColumnKey(predictionData4.get(i).xdata);
						Linelist.add(Lineoyu4);

						double shuju5 = Double.valueOf(
								predictionData5.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu5 = new LineChartObj();
						Lineoyu5.setValue(shuju5);
						Lineoyu5.setRowKey(y5String);
						Lineoyu5.setColumnKey(predictionData5.get(i).xdata);
						Linelist.add(Lineoyu5);
					}
					if (j == 8) {
						double shuju = Double.valueOf(
								predictionData.get(i).getYdata()).doubleValue();
						LineChartObj Lineoyu = new LineChartObj();
						Lineoyu.setValue(shuju);
						Lineoyu.setRowKey(yString);
						Lineoyu.setColumnKey(predictionData.get(i).xdata);
						Linelist.add(Lineoyu);

						double shuju1 = Double.valueOf(
								predictionData1.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu1 = new LineChartObj();
						Lineoyu1.setValue(shuju1);
						Lineoyu1.setRowKey(y1String);
						Lineoyu1.setColumnKey(predictionData1.get(i).xdata);
						Linelist.add(Lineoyu1);

						double shuju2 = Double.valueOf(
								predictionData2.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu2 = new LineChartObj();
						Lineoyu2.setValue(shuju2);
						Lineoyu2.setRowKey(y2String);
						Lineoyu2.setColumnKey(predictionData2.get(i).xdata);
						Linelist.add(Lineoyu2);

						double shuju3 = Double.valueOf(
								predictionData3.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu3 = new LineChartObj();
						Lineoyu3.setValue(shuju3);
						Lineoyu3.setRowKey(y3String);
						Lineoyu3.setColumnKey(predictionData3.get(i).xdata);
						Linelist.add(Lineoyu3);

						double shuju4 = Double.valueOf(
								predictionData4.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu4 = new LineChartObj();
						Lineoyu4.setValue(shuju4);
						Lineoyu4.setRowKey(y4String);
						Lineoyu4.setColumnKey(predictionData4.get(i).xdata);
						Linelist.add(Lineoyu4);

						double shuju5 = Double.valueOf(
								predictionData5.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu5 = new LineChartObj();
						Lineoyu5.setValue(shuju5);
						Lineoyu5.setRowKey(y5String);
						Lineoyu5.setColumnKey(predictionData5.get(i).xdata);
						Linelist.add(Lineoyu5);

						double shuju6 = Double.valueOf(
								predictionData6.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu6 = new LineChartObj();
						Lineoyu6.setValue(shuju6);
						Lineoyu6.setRowKey(y6String);
						Lineoyu6.setColumnKey(predictionData6.get(i).xdata);
						Linelist.add(Lineoyu6);
					}
					if (j == 9) {
						double shuju = Double.valueOf(
								predictionData.get(i).getYdata()).doubleValue();
						LineChartObj Lineoyu = new LineChartObj();
						Lineoyu.setValue(shuju);
						Lineoyu.setRowKey(yString);
						Lineoyu.setColumnKey(predictionData.get(i).xdata);
						Linelist.add(Lineoyu);

						double shuju1 = Double.valueOf(
								predictionData1.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu1 = new LineChartObj();
						Lineoyu1.setValue(shuju1);
						Lineoyu1.setRowKey(y1String);
						Lineoyu1.setColumnKey(predictionData1.get(i).xdata);
						Linelist.add(Lineoyu1);

						double shuju2 = Double.valueOf(
								predictionData2.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu2 = new LineChartObj();
						Lineoyu2.setValue(shuju2);
						Lineoyu2.setRowKey(y2String);
						Lineoyu2.setColumnKey(predictionData2.get(i).xdata);
						Linelist.add(Lineoyu2);

						double shuju3 = Double.valueOf(
								predictionData3.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu3 = new LineChartObj();
						Lineoyu3.setValue(shuju3);
						Lineoyu3.setRowKey(y3String);
						Lineoyu3.setColumnKey(predictionData3.get(i).xdata);
						Linelist.add(Lineoyu3);

						double shuju4 = Double.valueOf(
								predictionData4.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu4 = new LineChartObj();
						Lineoyu4.setValue(shuju4);
						Lineoyu4.setRowKey(y4String);
						Lineoyu4.setColumnKey(predictionData4.get(i).xdata);
						Linelist.add(Lineoyu4);

						double shuju5 = Double.valueOf(
								predictionData5.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu5 = new LineChartObj();
						Lineoyu5.setValue(shuju5);
						Lineoyu5.setRowKey(y5String);
						Lineoyu5.setColumnKey(predictionData5.get(i).xdata);
						Linelist.add(Lineoyu5);

						double shuju6 = Double.valueOf(
								predictionData6.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu6 = new LineChartObj();
						Lineoyu6.setValue(shuju6);
						Lineoyu6.setRowKey(y6String);
						Lineoyu6.setColumnKey(predictionData6.get(i).xdata);
						Linelist.add(Lineoyu6);

						double shuju7 = Double.valueOf(
								predictionData7.get(i).getYdata())
								.doubleValue();
						LineChartObj Lineoyu7 = new LineChartObj();
						Lineoyu7.setValue(shuju7);
						Lineoyu7.setRowKey(y7String);
						Lineoyu7.setColumnKey(predictionData7.get(i).xdata);
						Linelist.add(Lineoyu7);
					}

				}

			}
		}
		return Linelist;
	}
}