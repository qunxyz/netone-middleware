package oe.mid.doc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.frame.bus.res.doc.ExcelHandler;
import oe.frame.bus.res.doc.PdfHandler;
import oe.frame.bus.res.doc.TxtHandler;
import oe.frame.bus.res.doc.WordHandler;
import oe.frame.bus.res.doc.common.XmlObjListAndMDD;
import oe.frame.bus.res.doc.common.obj.MultiDimData;
import oe.midware.doc.excel.ExcelHandlerImp;

public class DocDemo {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		excelReadX();
		//MultiDimData mdd = TestMultiDimData.data1();
//		List xmlobjlist = XmlObjListAndMDD.toXmlObjList(mdd);
//		List value = mdd.getDatavalue();
//		System.out.print(value.size());
//
//		docWrite(value, "testinstance0");
		
		//docWrite(value,"abc");

	}

	private static void pdfWriter(List value, List xmlobjlist) {
		// Ð´pdf
		OutputStream outpdf = null;
		try {
			outpdf = new FileOutputStream("c:/aaa.pdf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PdfHandler pdfHandler = (PdfHandler) DocEntry.fetchBi("pdfHandler");

		pdfHandler.writePdf(value, xmlobjlist, null, outpdf);

	}

	private static void txtWriter(List xmlobjlist, List value) {
		// Ð´txt
		OutputStream outtxt = null;
		try {
			outtxt = new FileOutputStream("c:/aaa.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TxtHandler txtHandler = (TxtHandler) DocEntry.fetchBi("txtHandler");

		txtHandler.writeTxt(value, xmlobjlist, outtxt);

	}

	private static void txtReader(List value) throws FileNotFoundException {
		TxtHandler txtHandler = (TxtHandler) DocEntry.fetchBi("txtHandler");
		// ¶Átxt
		File filetxt = new File("c:/aaa.txt");
		InputStream inputtxt = new FileInputStream(filetxt);
		List list = txtHandler.readTxt(inputtxt);
		List columx = txtHandler.metaData(list);
		MultiDimData mddNew2 = XmlObjListAndMDD.toMDD(columx, list);

	}

	private static void excelWriter(List xmlobjlist, List value)
			throws Exception {

		// Ð´Excel
		OutputStream outxls = null;
		try {
			outxls = new FileOutputStream("c:/aaa.xls");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExcelHandler excelHandler = (ExcelHandler) DocEntry
				.fetchBi("excelHandler");

		excelHandler.writeExcel(value, xmlobjlist, null, "sample", outxls);
		
		outxls.close();

	}

	private static void excelRead() throws Exception {
		ExcelHandler excelHandler = new ExcelHandlerImp();
		File file = new File("d:/x1.xls");
		InputStream input = new FileInputStream(file);

		Map meta = excelHandler.metaData(input);
		input = new FileInputStream(file);
		
		for (Iterator iterator = meta.keySet().iterator(); iterator.hasNext();) {
			String type = (String) iterator.next();
			List column = (List) meta.get(type);
			if(column.size()!=0){
				List valueNew = excelHandler.readExcel(input, column, type);
				MultiDimData newDmm = XmlObjListAndMDD.toMDD(column, valueNew);
			}		
		}
		input.close();
	}
	
	private static void excelReadX() throws Exception {
		
		File file = new File("D:/bbbx.xls");
		InputStream input = new FileInputStream(file);

		ExcelHandler excelHandler = new ExcelHandlerImp();

		Map mapx=excelHandler.readExcelAllSheet(input);
		for (Iterator iterator = mapx.keySet().iterator(); iterator.hasNext();) {
			String object = (String) iterator.next();
			MultiDimData kpp=(MultiDimData)mapx.get(object);
			System.out.println(object+","+kpp.getDataColumnName().size());
		}
	}

	private static void docWrite(List value, String filename) {
		OutputStream outdoc = null;
		try {
			outdoc = new FileOutputStream("c:/" + filename + ".zip");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WordHandler wordHandler = (WordHandler) DocEntry.fetchBi("wordHandler");
		wordHandler.generateXmlReference(filename);
		List columnDoc = wordHandler.fetchColumnInfo(filename);
		wordHandler.writeDocUseMultiBussObj(value, filename, outdoc);
	}

}