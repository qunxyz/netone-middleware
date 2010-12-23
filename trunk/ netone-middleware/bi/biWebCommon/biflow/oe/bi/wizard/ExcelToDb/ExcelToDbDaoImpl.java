package oe.bi.wizard.ExcelToDb;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import oe.security3a.seucore.obj.MultiDimData;

import oe.frame.bus.res.doc.ExcelHandler;
import oe.midware.doc.excel.ExcelHandlerImp;

public class ExcelToDbDaoImpl implements ExcelToDbDao {

	public String[] getSheetName(URL url) {
		ExcelHandler excelHandler = new ExcelHandlerImp();
		String[] sheetname = null;
		try {
			sheetname = excelHandler.readSheetName(url.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheetname;
	}

	public List getList(URL url, String sheetname) {
		List list = null;
		try {
			ExcelHandler excelHandler = new ExcelHandlerImp();
			Map meta = excelHandler.metaData(url.openStream());
			List column = (List) meta.get(sheetname);
			List valueNew = excelHandler.readExcel(url.openStream(), column,
					sheetname);
			MultiDimData newDmm = XmlObjListAndMDD.toMDD(column, valueNew);
			list = newDmm.getDatavalue();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
