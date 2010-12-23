package oe.bi.web.datasource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.bi.BiEntry;
import oe.bi.datasource.bus.ExtractDataset;
import oe.bi.datasource.dao.DataSourceDao;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;




public class XmlDataSourceSvl extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public XmlDataSourceSvl() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 返回的xml结构:
	 * <dslist>
	 *     <ds id="" name="">
	 *     		<dataset id="" name="">
	 *     			<column id="" name=""/>
	 *     		</dataset>
	 *     </ds>
	 * </dslist>
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		response.setContentType("text/xml; charset=GBK");
//		response.setHeader("Pragma", "no-cache");
//		response.setHeader("Cache-Control", "no-cache");
//		response.setHeader("Expires", "0");
//		
//		
//		DataSourceDao dsdao = null ;
//		ExtractDataset dataset = null ;
//		
//	   	try {
//	   		dsdao = (DataSourceDao)BiEntry.fetchBi("dataSourceDaoImpl");
//	   		dataset = (ExtractDataset)BiEntry.fetchBi("extractDataset");
//		} catch (Exception e1) {
//			throw new ServletException("实例化DataSourceDao或ExtractDataset出错!");
//		}
//		
//        //创建返回客户端的xml文件；
//		Document redoc = DocumentHelper.createDocument();
//		Element root = redoc.addElement("dslist");
//		
//		
//	 	List dslist = dsdao.query("");
//	 	Iterator dslistiter = dslist.iterator();
//	 	while(dslistiter.hasNext()){
//	 		DatasourceObj dslistobj = (DatasourceObj)dslistiter.next();
//	 		Element eds = root.addElement("ds");
//	 		eds.addAttribute("id",dslistobj.getDsid());
//	 		eds.addAttribute("name",dslistobj.getDsname());
//	 		
//	 	 	List dssetlist = dataset.fetchDataSet(dslistobj);
//	 	 	Iterator dssetlistiter = dssetlist.iterator();
//	 	 	while(dssetlistiter.hasNext()){
//	 	 		DataSetObj dssetobj = (DataSetObj) dssetlistiter.next();
//	 	 		Element edsset = eds.addElement("dataset");
//	 	 		edsset.addAttribute("id",dssetobj.getDatasetid());
//	 	 		edsset.addAttribute("name",dssetobj.getDatasetname());
//	 	 		List collist = dssetobj.getDataColumnObjs();
//	 	 		Iterator collistiter = collist.iterator();
//	 	 		while(collistiter.hasNext()){
//	 	 			DataColumnObj colobj = (DataColumnObj) collistiter.next();
//	 	 			Element ecol = edsset.addElement("column");
//	 	 			ecol.addAttribute("id",colobj.getColumnid());
//	 	 			ecol.addAttribute("name",colobj.getColumnname());
//	 	 		}
//	 	 	}
//	 	}
//		
//
//		// log.debug(redoc.asXML());
//
//		PrintWriter out = response.getWriter();
//		OutputFormat outf = OutputFormat.createPrettyPrint();
//		outf.setEncoding("GBK");
//		XMLWriter xmlw = new XMLWriter(out, outf);
//		xmlw.write(redoc);
//		xmlw.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
