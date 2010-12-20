package oe.cav.bean.logic.tools;

import java.util.Iterator;
import java.util.List;

import oe.cav.bean.logic.column.ColumnExtendInfo;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.reference.XMLReference;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.CDATA;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * ��DyObj��ض�����д����д��Ҫ���ɵ�XML�ĵ�
 * 
 * @author admin
 * 
 */
public class DyObjToXMLImpl implements DyObjToXML {

	static Log log = LogFactory.getLog(DyObjToXMLImpl.class);

	public Document parser(DyObj dyf) {
		if (dyf == null || dyf.equals("")) {
			log.error("DyObjΪ��");
			return null;
		} else {
			Document xml = DocumentHelper.createDocument();

			// ��ø�Ԫ��
			Element root = xml.addElement(XMLReference.PARAMETERDEFINE);
			// ��һ��
			TCsForm tcf = dyf.getFrom();
			if (tcf == null) {
				log.error("TCsFormΪ��");
				return null;
			}
			String butinfo = tcf.getButinfo();
			// String created = tcf.getCreated();
			String description = tcf.getDescription();
			String designer = tcf.getDesigner();
			// String extendattribute = tcf.getExtendattribute();
			String formcode = tcf.getFormcode();
			String formname = tcf.getFormname();
			String listinfo = tcf.getListinfo();
			String orderinfo = tcf.getOrderinfo();
			// String participant = tcf.getParticipant();
			// String statusinfo = tcf.getStatusinfo();
			String subform = tcf.getSubform();
			subform = subform == null ? "" : subform;
			String systemid = tcf.getSystemid();
			String typeinfo = tcf.getTypeinfo();
			String viewbutinfo = tcf.getViewbutinfo();

			String timelevel = tcf.getTimelevel();
			String dimlevel = tcf.getDimlevel();
			Element root1 = root.addElement(XMLReference.PARAMETER)
					.addAttribute(XMLReference.LEVELCOLUMN,
							XMLReference.DIMETION_BELONGX).addAttribute(
							XMLReference.TIMECOLUMN,
							XMLReference.DIMETION_TIMEX).addAttribute(
							XMLReference.DS, systemid).addAttribute(
							XMLReference.TABLE, description).addAttribute(
							XMLReference.NAME, formname).addAttribute(
							XMLReference.UUID, formcode).addAttribute(
							XMLReference.DATASRC, typeinfo).addAttribute(
							XMLReference.LEVEL, dimlevel).addAttribute(
							XMLReference.USEABLE,
							String.valueOf(XMLReference.TRUE)).addAttribute(
							XMLReference.PERIOD, timelevel).addAttribute(
							XMLReference.OTHER,
							"[subform]" + subform + "#[listinfo]" + listinfo
									+ "#[orderinfo]" + orderinfo + "#[butinfo]"
									+ butinfo + "#[viewbutinfo]" + viewbutinfo
									+ "#[styleinfo]" + tcf.getStyleinfo());

			root1.addElement(XMLReference.SQLINFO).addCDATA(tcf.getSqlinfo());

			// �ڶ���
			List formcolumn = dyf.getColumn();
			if (formcolumn == null) {
				log.error("TCsColumn�б�Ϊ��");
				return null;
			}
			for (Iterator iterator = formcolumn.iterator(); iterator.hasNext();) {
				TCsColumn tcc = (TCsColumn) iterator.next();
				String checktype = tcc.getChecktype();
				String columname = tcc.getColumname();
				String columncode = tcc.getColumncode();
				String columnid = tcc.getColumnid();
				String extendattribute1 = tcc.getExtendattribute();
				String htmltype = tcc.getHtmltype();

				// Long indexvalue = tcc.getIndexvalue();
				String musk = tcc.getMusk();
				String opemode = tcc.getOpemode();
				// String participant1 = tcc.getParticipant();
				// String statusinfo1 = tcc.getStatusinfo();
				String valuelist = tcc.getValuelist();
				String viewtype = tcc.getViewtype();
				boolean useable = tcc.isUseable();
				String attributeType = "";
				// �ж����ݿ��ֶ�������ȷ��XML�ֶ�����
				if (ColumnExtendInfo._HTML_TYPE_BUT.equals(htmltype)
						|| ColumnExtendInfo._HTML_TYPE_IMAGE.equals(htmltype)
						|| ColumnExtendInfo._HTML_TYPE_FILE.equals(htmltype)) {
					attributeType = "file";
				} else {
					if (ColumnExtendInfo._CHECK_TYPE_NUMBER.equals(checktype)
							|| "DECIMAL".equals(htmltype)||"BIGINT".equals(htmltype)) {
						attributeType = "number";
						checktype = "number";// ����DECIMAL����,��ôǿ��Ҫ��������Ϊnumber
						// �����ڱ�Ӧ���п��Լ���ֶκϷ���
					} else {
						if (ColumnExtendInfo._HTML_TYPE_DATE.equals(htmltype)
								|| ColumnExtendInfo._HTML_TYPE_DATETIME
										.equals(htmltype)
								|| ColumnExtendInfo._HTML_TYPE_TIME
										.equals(htmltype)) {
							attributeType = "date";
						} else {
							attributeType = "string";
						}
					}
				}
				root1.addElement(XMLReference.FIELDMAPPING).addAttribute(
						XMLReference.NAME, columname).addAttribute(
						XMLReference.UUID, columncode).addAttribute(
						XMLReference.VIRTUAL, valuelist).addAttribute(
						XMLReference.VIEWTYPE, viewtype).addAttribute(
						XMLReference.ATTRIBUERNAME, columncode).addAttribute(
						XMLReference.FIELDNAME, columnid).addAttribute(
						XMLReference.ATTRIBUTETYPE, attributeType)
						.addAttribute(XMLReference.UNIT, "").addAttribute(
								XMLReference.USEABLE, String.valueOf(useable))
						.addAttribute(XMLReference.PERIOD, "1h").addAttribute(
								XMLReference.OTHER,
								"[htmltype]" + htmltype + "#[checktype]"
										+ checktype + "#[musk]" + musk
										+ "#[opemode]" + opemode + "#[extend]"
										+ extendattribute1);
			}
			return xml;
		}
	}
}
