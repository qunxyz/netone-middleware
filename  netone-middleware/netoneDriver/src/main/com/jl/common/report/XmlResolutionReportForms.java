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

		// ������ֶ���Ϣ

		NodeList items = dom.getElementsByTagName("repclr");// ��ȡrepclr����ڵ�
		Map mapx = new HashMap();
		for (int i = 0; i < items.getLength(); i++) {
			QueryColumn qc = new QueryColumn();
			// �����ڵ�
			Element personNode = (Element) items.item(i);

			// �����ڵ������
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

		// ����ӿ�����
		Map mapReports = new HashMap();

		List<ReportQueryRef> list = new ArrayList<ReportQueryRef>();
		Map mapInface = new HashMap();
		NodeList itembaobiao = dom.getElementsByTagName("repfile");// ��ȡrepfile����ڵ�

		for (int i = 0; i < itembaobiao.getLength(); i++) {
			Report rp = new Report();
			// ����repfile�ڵ� ������
			Element personbaobiao = (Element) itembaobiao.item(i);

			// �����ڵ������
			rp.setRpId(new String(personbaobiao.getAttribute("naturalname")));
			rp.setRpName(new String(personbaobiao.getAttribute("name")));

			// mapReports����rp��ֵ
			mapReports.put(
					personbaobiao.getAttribute("naturalname").toString(), rp);

			// repfile ��һ���ڵ� Inface�ڵ�
			NodeList Infacelist = personbaobiao.getElementsByTagName("Inface");

			for (int j = 0; j < Infacelist.getLength(); j++) {
				// �����ڵ�
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
