package com.jl.common.report.servlet;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jl.common.report.obj.core.Getdatalist;
import com.jl.common.report.obj.core.Read_clr;
import com.jl.common.report.obj.core.Read_columns;
import com.jl.common.report.obj.core.Read_dataset;
import com.jl.common.report.obj.core.Read_label;
import com.jl.common.report.obj.core.Read_record;
import com.jl.common.report.obj.core.Read_report;
import com.jl.common.report.obj.core.Read_table;
import com.jl.common.report.obj.core.RecordPools;
import com.jl.common.report.obj.core.table.Read_tcol;
import com.jl.common.report.obj.core.table.Read_td;
import com.jl.common.report.obj.core.table.Read_tr;
import com.jl.common.report.parse.AddRs;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.lucaslee.report.ReportManager;
import com.lucaslee.report.grouparithmetic.AverageArithmetic;
import com.lucaslee.report.grouparithmetic.SumArithmetic;
import com.lucaslee.report.model.HeaderTable;
import com.lucaslee.report.model.Table;
import com.lucaslee.report.model.TableCell;
import com.lucaslee.report.model.TableRow;

public class DataRep {

	private Read_report reportobj;
	private RecordPools reportPools;

	public void fetchData(String name, String condition) throws Exception {

		String xmlinfo = AddRs.readxmlByName(name);
		InputStream input = new ByteArrayInputStream(xmlinfo.getBytes("utf-8"));

		SAXReader reader = new SAXReader();
		Document document = reader.read(input);

		reportobj = readXMLElementBySAX(document, condition);
		reportPools = XmlDataread.readXMLSql(reportobj, condition);
	}

	public Table getRecord() throws Exception {

		// TODO Auto-generated method stub
		// readXMLElementBySAX();

		Getdatalist listx = Readdata.getreportdata(reportobj, reportPools);
		List list = listx.getColumnslist();
		Table t = new Table();
		// TODO start

		List list2 = (List) listx.getRecorddatalist().get(list.get(0));

		Integer[] totalsum;
		Integer[] totalavg;

		List<Read_td> collist = reportobj.getRecordlist().get(0).getTdlist();

		List<Integer> sum1 = new ArrayList<Integer>();
		List<Integer> avg1 = new ArrayList<Integer>();

		for (int i = 0; i < collist.size(); i++) {

			String sum = "ToSum";
			String avg = "Toavg";
			Read_columns rc = (Read_columns) collist.get(i).getTdclr();
			if (sum.equals(rc.getStatistyp())) {
				sum1.add(collist.get(i).getCol());
			}
			if (avg.equals(((Read_columns) collist.get(i).getTdclr())
					.getStatistyp())) {
				avg1.add(collist.get(i).getCol());
			}
		}
		totalsum = sum1.toArray(new Integer[sum1.size()]);
		totalavg = avg1.toArray(new Integer[avg1.size()]);

		for (int i = 0; i < list2.size(); i++) {
			TableRow tr = new TableRow();

			for (int j = 0; j < list.size(); j++) {
				List datalist = (List) listx.getRecorddatalist().get(
						list.get(j));
				String data = datalist.get(i) + "";
				TableCell cell = new TableCell(data);
				tr.addCell(cell);
			}
			t.addRow(tr);

		}

		int[] totalsum_ = ArrayUtils.toPrimitive(totalsum);
		int[] totalavg_ = ArrayUtils.toPrimitive(totalavg);

		// for (int i = 0; i < 0; i++) {
		// t.addCol(t.getCol(3).cloneAll());
		// }
		// TODO end
		ReportManager rm = new ReportManager();
		if(totalsum_.length>0)
		rm.generateRowTotal(t, totalsum_, false, new SumArithmetic());
		
		if(totalavg_.length>0)
		rm.generateRowTotal(t, totalavg_, false, new AverageArithmetic());

		return t;

	}

	public HeaderTable getColHeader() throws Exception {
		HeaderTable th = new HeaderTable();
		Getdatalist listx = Readdata.gettabledata(reportobj, reportPools);
		for (int i = 0; i < reportobj.getTablelist().size(); i++) {
			String ssa = "tou";
			if (ssa.equals(reportobj.getTablelist().get(i).getIshead())) {
				List<Read_td> hlist = reportobj.getTablelist().get(i)
						.getTdlist();
				for (int m = 0; m < reportobj.getTablelist().get(i).getRows(); m++) {
					TableRow thr2 = new TableRow(reportobj.getTablelist()
							.get(i).getCols());
					for (int j = 0; j < hlist.size(); j++) {
						if (hlist.get(j).getRow() == m) {
							for (int k = 0; k < reportobj.getTablelist().get(i)
									.getCols(); k++) {
								if (hlist.get(j).getCol() == k) {
									TableCell tc = null;
									tc = thr2.getCell(k);
									tc.setAlign(tc.ALIGN_CENTER);
									if (listx.getRecorddatalist().containsKey(hlist.get(j).getTdclr()
											.getId())) {
										tc.setColSpan(hlist.get(j).getColspan());
										List list2=(List) listx.getRecorddatalist().get(hlist.get(j).getTdclr()
												.getId());
										if(list2.size()!=0){
											tc.setContent(""+list2.get(0));
										 }
									}else{
									tc.setColSpan(hlist.get(j).getColspan());
									tc.setContent(hlist.get(j).getTdclr()
											.getText());
									}
									for (int k2 = k + 1; k2 < k
											+ hlist.get(j).getColspan(); k2++) {
										thr2.getCell(k2).setIsHidden(true);
									}

								}
							}
						}
					}
					th.addRow(thr2);
				}

			}
		}

		return th;
	}

//	public Table getHeader() {
//		Table t = new Table();
//		t.setBorder(0);
//		t.setAlign(t.ALIGN_CENTER);
//
//		TableCell tc = null;
//		TableRow tr = null;
//
//		tr = new TableRow(reportobj.getRecordlist().get(0).getCols());
//		t.addRow(tr);
//		tc = tr.getCell(0);
//		tc.setColSpan(reportobj.getRecordlist().get(0).getCols());
//		tc.setAlign(tc.ALIGN_CENTER);
//		tc.setContent(" ");
//
//		for (int i = reportobj.getTablelist().get(0).getCols(); i < 0; i--) {
//			tr.getCell(i).setIsHidden(false);
//		}
//
//		return t;
//
//	}

	public Table getFooterTable(Table th) throws Exception {
		Getdatalist listx = Readdata.gettablewiedata(reportobj, reportPools);
		for (int i = 0; i < reportobj.getTablelist().size(); i++) {
			String ssa = "wei";
			if (ssa.equals(reportobj.getTablelist().get(i).getIshead())) {
				List<Read_td> hlist = reportobj.getTablelist().get(i)
						.getTdlist();
				for (int m = 0; m < reportobj.getTablelist().get(i).getRows(); m++) {
					TableRow thr2 = new TableRow(reportobj.getTablelist()
							.get(i).getCols());
					for (int j = 0; j < hlist.size(); j++) {
						if (hlist.get(j).getRow() == m) {
							for (int k = 0; k < reportobj.getTablelist().get(i)
									.getCols(); k++) {
								if (hlist.get(j).getCol() == k) {
									TableCell tc = null;
									tc = thr2.getCell(k);
									tc.setAlign(tc.ALIGN_CENTER);
									if (listx.getRecorddatalist().containsKey(hlist.get(j).getTdclr()
											.getId())) {
										tc.setColSpan(hlist.get(j).getColspan());
										List list2=(List) listx.getRecorddatalist().get(hlist.get(j).getTdclr()
												.getId());
										if(list2.size()!=0){
										tc.setContent(""+list2.get(0));
										}
									}else{
									tc.setColSpan(hlist.get(j).getColspan());
									tc.setContent(hlist.get(j).getTdclr()
											.getText());
									}
									for (int k2 = k + 1; k2 < k
											+ hlist.get(j).getColspan(); k2++) {
										thr2.getCell(k2).setIsHidden(true);
									}

								}
							}
						}
					}
					th.addRow(thr2);
				}

			}
		}

		return th;
	}

	public static Read_report readXMLElementBySAX(Document document,
			String condition) throws IllegalAccessException,
			InvocationTargetException {

		Read_report report_obj = new Read_report();

		Element root = document.getRootElement();

		List<Read_label> labellist = new ArrayList<Read_label>();
		for (Iterator i = root.elementIterator("Label"); i.hasNext();) {
			Element element = (Element) i.next();
			Read_label r_label = new Read_label();
			for (Iterator k = element.attributeIterator(); k.hasNext();) {
				Attribute attribute = (Attribute) k.next();
				String name = attribute.getName();
				String value = attribute.getValue();
				BeanUtils.setProperty(r_label, name, changeint(name, value));
			}
			labellist.add(r_label);
		}

		List<Read_columns> columnslist = new ArrayList<Read_columns>();
		for (Iterator i = root.elementIterator("Columns"); i.hasNext();) {
			Element element = (Element) i.next();
			Read_columns r_columns = new Read_columns();
			for (Iterator k = element.attributeIterator(); k.hasNext();) {
				Attribute attribute = (Attribute) k.next();
				String name = attribute.getName();
				String value = attribute.getValue();
				BeanUtils.setProperty(r_columns, name, changeint(name, value));
			}
			columnslist.add(r_columns);
		}

		List<Read_dataset> datasetlist = new ArrayList<Read_dataset>();
		for (Iterator i = root.elementIterator("Dataset"); i.hasNext();) {
			Element element = (Element) i.next();
			Read_dataset r_dataset = new Read_dataset();
			for (Iterator k = element.attributeIterator(); k.hasNext();) {
				Attribute attribute = (Attribute) k.next();
				String name = attribute.getName();
				String value = attribute.getValue();
				BeanUtils.setProperty(r_dataset, name, changeint(name, value));
			}
			for (Iterator v = element.elementIterator("sql"); v.hasNext();) {
				Element fooinit = (Element) v.next();
				BeanUtils.setProperty(r_dataset, "sqlstr", fooinit.getData()
						.toString());
				System.out.println(fooinit.getData().toString());
			}

			datasetlist.add(r_dataset);
		}

		List<Read_table> tablelist = new ArrayList<Read_table>();
		// table
		for (Iterator i = root.elementIterator("Table"); i.hasNext();) {
			Element element = (Element) i.next();
			Read_table r_table = new Read_table();

			for (Iterator k = element.attributeIterator(); k.hasNext();) {
				Attribute attribute = (Attribute) k.next();
				String name = attribute.getName();
				String value = attribute.getValue();
				BeanUtils.setProperty(r_table, name, changeint(name, value));
			}
			// tcol
			List<Read_tcol> tcollist = new ArrayList<Read_tcol>();
			for (Iterator j = element.elementIterator("col"); j.hasNext();) {
				Element foo = (Element) j.next();
				Read_tcol r_tcol = new Read_tcol();
				for (Iterator l = foo.attributeIterator(); l.hasNext();) {
					Attribute fooattribute = (Attribute) l.next();
					String fooname = fooattribute.getName();
					String foovalue = fooattribute.getValue();
					BeanUtils.setProperty(r_tcol, fooname, changeint(fooname,
							foovalue));
				}
				tcollist.add(r_tcol);
			}
			System.out.println(r_table.getId() + ": tcollist  "
					+ tcollist.size());
			r_table.setTcollist(tcollist);
			// tr
			List<Read_tr> trlist = new ArrayList<Read_tr>();
			for (Iterator j = element.elementIterator("tr"); j.hasNext();) {
				Element foo = (Element) j.next();
				Read_tr r_tr = new Read_tr();
				for (Iterator l = foo.attributeIterator(); l.hasNext();) {
					Attribute fooattribute = (Attribute) l.next();
					String fooname = fooattribute.getName();
					String foovalue = fooattribute.getValue();
					BeanUtils.setProperty(r_tr, fooname, changeint(fooname,
							foovalue));
				}
				trlist.add(r_tr);
			}
			System.out.println(r_table.getId() + ": trlist  " + trlist.size());
			r_table.setSqlstr("");
			for (Iterator v = element.elementIterator("sql"); v.hasNext();) {
				Element fooinit = (Element) v.next();
				String sqlstr = fooinit.getData().toString();
				System.out.println("sqlstr:"+sqlstr);
				r_table.setSqlstr(sqlstr);
			}
			// td
			List<Read_td> tdlist = new ArrayList<Read_td>();
			for (Iterator j = element.elementIterator("td"); j.hasNext();) {
				Element foo = (Element) j.next();
				Read_td r_td = new Read_td();
				for (Iterator l = foo.attributeIterator(); l.hasNext();) {
					Attribute fooattribute = (Attribute) l.next();
					String fooname = fooattribute.getName();
					String foovalue = fooattribute.getValue();
					BeanUtils.setProperty(r_td, fooname, changeint(fooname,
							foovalue));
				}

				// tdinit
				Read_clr r_clr = new Read_clr();
				for (Iterator v = foo.elementIterator("Label"); v.hasNext();) {
					Element fooinit = (Element) v.next();
					Read_label r_label = new Read_label();
					for (Iterator l = fooinit.attributeIterator(); l.hasNext();) {
						Attribute fooattribute = (Attribute) l.next();
						String fooname = fooattribute.getName();
						String foovalue = fooattribute.getValue();
						BeanUtils.setProperty(r_label, fooname, changeint(
								fooname, foovalue));
					}
					r_clr = r_label;
				}
				for (Iterator v = foo.elementIterator("Columns"); v.hasNext();) {
					Element fooinit = (Element) v.next();
					Read_columns r_columns = new Read_columns();
					for (Iterator l = fooinit.attributeIterator(); l.hasNext();) {
						Attribute fooattribute = (Attribute) l.next();
						String fooname = fooattribute.getName();
						String foovalue = fooattribute.getValue();
						BeanUtils.setProperty(r_columns, fooname, changeint(
								fooname, foovalue));
					}
					r_clr = r_columns;
				}
				for (Iterator v = foo.elementIterator("Dataset"); v.hasNext();) {
					Element fooinit = (Element) v.next();
					Read_dataset r_ds = new Read_dataset();

					for (Iterator l = fooinit.attributeIterator(); l.hasNext();) {
						Attribute fooattribute = (Attribute) l.next();
						String fooname = fooattribute.getName();
						String foovalue = fooattribute.getValue();
						BeanUtils.setProperty(r_ds, fooname, changeint(fooname,
								foovalue));
						System.out.println(fooinit.getData().toString());
					}
					for (Iterator p = fooinit.elementIterator("sql"); p
							.hasNext();) {
						Element fooinit2 = (Element) p.next();
						String dsqlstr = fooinit2.getData().toString();
						// 追加查询扩展条件
						dsqlstr = dsqlstr + condition;
						r_ds.setSqlstr(dsqlstr);
						List list = Read_dataset.datalist(dsqlstr);
						for (Iterator iterator = list.iterator(); iterator
								.hasNext();) {
							Map object = (Map) iterator.next();
							for (Iterator iterator2 = object.values()
									.iterator(); iterator2.hasNext();) {
								Object rs = iterator2.next();

								r_ds.setText(rs.toString());

							}

						}
					}
					r_clr = r_ds;
				}
				r_td.setTdclr(r_clr);

				Read_clr r_clr2 = r_td.getTdclr();

				System.out.println(r_clr2.getId());
				tdlist.add(r_td);
			}
			System.out.println(r_table.getId() + ": tdlist  " + tdlist.size());
			r_table.setTdlist(tdlist);
			tablelist.add(r_table);
		}

		// record
		List<Read_record> recordlist = new ArrayList<Read_record>();
		// table
		for (Iterator i = root.elementIterator("Record"); i.hasNext();) {
			Element element = (Element) i.next();
			Read_record r_record = new Read_record();

			for (Iterator k = element.attributeIterator(); k.hasNext();) {
				Attribute attribute = (Attribute) k.next();
				String name = attribute.getName();
				String value = attribute.getValue();
				BeanUtils.setProperty(r_record, name, changeint(name, value));
			}

			for (Iterator v = element.elementIterator("sql"); v.hasNext();) {
				Element fooinit = (Element) v.next();
				String sqlstr = fooinit.getData().toString();
				System.out.println(fooinit.getData().toString());
				r_record.setSqlstr(sqlstr);
			}

			// tcol
			List<Read_tcol> tcollist = new ArrayList<Read_tcol>();
			for (Iterator j = element.elementIterator("col"); j.hasNext();) {
				Element foo = (Element) j.next();
				Read_tcol r_tcol = new Read_tcol();
				for (Iterator l = foo.attributeIterator(); l.hasNext();) {
					Attribute fooattribute = (Attribute) l.next();
					String fooname = fooattribute.getName();
					String foovalue = fooattribute.getValue();
					BeanUtils.setProperty(r_tcol, fooname, changeint(fooname,
							foovalue));
				}
				tcollist.add(r_tcol);
			}
			System.out.println(r_record.getId() + ": tcollist  "
					+ tcollist.size());
			r_record.setTcollist(tcollist);
			// tr
			List<Read_tr> trlist = new ArrayList<Read_tr>();
			for (Iterator j = element.elementIterator("tr"); j.hasNext();) {
				Element foo = (Element) j.next();
				Read_tr r_tr = new Read_tr();
				for (Iterator l = foo.attributeIterator(); l.hasNext();) {
					Attribute fooattribute = (Attribute) l.next();
					String fooname = fooattribute.getName();
					String foovalue = fooattribute.getValue();
					BeanUtils.setProperty(r_tr, fooname, changeint(fooname,
							foovalue));
				}
				trlist.add(r_tr);
			}
			System.out.println(r_record.getId() + ": trlist  " + trlist.size());

			// td
			List<Read_td> tdlist = new ArrayList<Read_td>();
			for (Iterator j = element.elementIterator("td"); j.hasNext();) {
				Element foo = (Element) j.next();
				Read_td r_td = new Read_td();
				for (Iterator l = foo.attributeIterator(); l.hasNext();) {
					Attribute fooattribute = (Attribute) l.next();
					String fooname = fooattribute.getName();
					String foovalue = fooattribute.getValue();
					BeanUtils.setProperty(r_td, fooname, changeint(fooname,
							foovalue));
				}
				// tdinit
				Read_clr r_clr = new Read_clr();
				for (Iterator v = foo.elementIterator("Label"); v.hasNext();) {
					Element fooinit = (Element) v.next();
					Read_label r_label = new Read_label();
					for (Iterator l = fooinit.attributeIterator(); l.hasNext();) {
						Attribute fooattribute = (Attribute) l.next();
						String fooname = fooattribute.getName();
						String foovalue = fooattribute.getValue();
						BeanUtils.setProperty(r_label, fooname, changeint(
								fooname, foovalue));
					}
					r_clr = r_label;
				}
				for (Iterator v = foo.elementIterator("Columns"); v.hasNext();) {
					Element fooinit = (Element) v.next();
					Read_columns r_columns = new Read_columns();
					for (Iterator l = fooinit.attributeIterator(); l.hasNext();) {
						Attribute fooattribute = (Attribute) l.next();
						String fooname = fooattribute.getName();
						String foovalue = fooattribute.getValue();
						BeanUtils.setProperty(r_columns, fooname, changeint(
								fooname, foovalue));
					}
					r_clr = r_columns;
				}
				for (Iterator v = foo.elementIterator("Dataset"); v.hasNext();) {
					Element fooinit = (Element) v.next();
					Read_dataset r_ds = new Read_dataset();
					for (Iterator l = fooinit.attributeIterator(); l.hasNext();) {
						Attribute fooattribute = (Attribute) l.next();
						String fooname = fooattribute.getName();
						String foovalue = fooattribute.getValue();
						BeanUtils.setProperty(r_ds, fooname, changeint(fooname,
								foovalue));
					}
					for (Iterator p = fooinit.elementIterator("sql"); p
							.hasNext();) {
						Element fooinit2 = (Element) p.next();
						String dsqlstr = fooinit2.getData().toString();
						System.out.println(dsqlstr);
						r_ds.setSqlstr(dsqlstr);
					}
					r_clr = r_ds;
				}
				r_td.setTdclr(r_clr);

				Read_clr r_clr2 = r_td.getTdclr();

				System.out.println(r_clr2.getId());
				tdlist.add(r_td);
			}
			System.out.println(r_record.getId() + ": tdlist  " + tdlist.size());
			r_record.setTdlist(tdlist);
			recordlist.add(r_record);
		}

//		System.out.println(labellist.size() + "<-- label");
//		System.out.println(columnslist.size() + "<-- columns");
//		System.out.println(datasetlist.size() + "<--dataset");
//		System.out.println(tablelist.size() + "<--table");
//		System.out.println(recordlist.size() + "<--record");

		report_obj.setColumnslist(columnslist);
		report_obj.setDatasetlist(datasetlist);
		report_obj.setLablelist(labellist);
		report_obj.setRecordlist(recordlist);
		report_obj.setTablelist(tablelist);

		return report_obj;

	}

	public static Object changeint(String name, String cvalue) {
		if (name == "x" || name == "y" || name == "width" || name == "height"
				|| name == "fontSize" || name == "colid" || name == "rowid"
				|| name == "row" || name == "col" || name == "rowspan"
				|| name == "colspan") {
			return Integer.parseInt(cvalue);
		} else {
			return cvalue;
		}

	}

}
