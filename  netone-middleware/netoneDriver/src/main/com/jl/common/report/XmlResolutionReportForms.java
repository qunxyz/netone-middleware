package com.jl.common.report;

import java.io.IOException;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.jl.common.report.obj.QueryColumn;
import com.jl.common.report.obj.Report;
import com.jl.common.report.obj.ReportQueryRef;
import com.jl.common.report.obj.XReportFace;
import com.sybase.jdbc3.jdbc.Convert;

public class XmlResolutionReportForms {

	public XReportFace XmlResolution(String XmlStr) {
		XReportFace x = new XReportFace();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(XmlStr));

		Document dom = null;
		try {
			dom = builder.parse(is);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 报表的字段信息

		NodeList items = dom.getElementsByTagName("repclr");// 获取repclr这个节点
		Map mapx = new HashMap();
		for (int i = 0; i < items.getLength(); i++) {
			QueryColumn qc = new QueryColumn();
			// 解析节点
			Element personNode = (Element) items.item(i);

			// 解析节点的属性
			qc.setColumnid(new String(personNode.getAttribute("id")));
			qc.setColumnname(new String(personNode.getAttribute("label")));
			qc.setColumntype(new String(personNode.getAttribute("type")));
			qc.setDefaultvalue(new String(personNode.getAttribute("data")));
			//qc.setStrtyp(new String(personNode.getAttribute("strtyp")));
			try {
				qc
						.setXoffset(Convert.objectToInt(personNode
										.getAttribute("col")));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				qc
						.setYoffset(Convert.objectToInt(personNode
								.getAttribute("row")));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String andor = personNode.getAttribute("relat");
			if (andor.equals("And")) {
				qc.setAnd(true);
			}
			if (andor.equals("Or")) {
				qc.setAnd(false);
			}
			mapx.put(new String(personNode.getAttribute("id")), qc);

		}
		x.setColumns(mapx);

		// 报表接口配置
		Map mapReports = new HashMap();

		List<ReportQueryRef> list = new ArrayList<ReportQueryRef>();
		Map mapInface = new HashMap();
		NodeList itembaobiao = dom.getElementsByTagName("repfile");// 获取repfile这个节点

		for (int i = 0; i < itembaobiao.getLength(); i++) {
			Report rp = new Report();
			// 解析repfile节点 的属性
			Element personbaobiao = (Element) itembaobiao.item(i);

			// 解析节点的属性
			rp.setRpId(new String(personbaobiao.getAttribute("naturalname")));
			rp.setRpName(new String(personbaobiao.getAttribute("name")));

			// mapReports保存rp的值
			mapReports.put(
					personbaobiao.getAttribute("naturalname").toString(), rp);

			// repfile 下一个节点 Inface节点
			NodeList Infacelist = personbaobiao.getElementsByTagName("Inface");

			for (int j = 0; j < Infacelist.getLength(); j++) {
				// 解析节点
				ReportQueryRef rqr = new ReportQueryRef();
				Element personInface = (Element) Infacelist.item(j);
				rqr.setQuerycolumnid(new String(personInface
						.getAttribute("bindclr")));
				rqr.setDescription(new String(personInface
						.getAttribute("remark")));
				rqr.setRefColumnid(new String(personInface
						.getAttribute("columns")));
				rqr.setType(new String(personInface
						.getAttribute("stringtype")));
				rqr.setBindclr(new String(personInface
						.getAttribute("bindclr")));
				rqr.setReportid(new String(personbaobiao
						.getAttribute("naturalname")));
				list.add(rqr);
			}

			mapInface.put(personbaobiao.getAttribute("naturalname").toString(),
					list);
		}

		x.setReports(mapReports);
		x.setRq(mapInface);

		return x;
	}

}
