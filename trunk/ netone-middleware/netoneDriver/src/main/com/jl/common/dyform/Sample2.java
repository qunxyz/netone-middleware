package com.jl.common.dyform;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

public class Sample2 {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String formcode = "674baf6cbc0011e084b38197d5f4b310_";

		// -------------���ݲ����߼�--------------

		// �������-1
		DyFormData dydata2 = new DyFormData();
		dydata2.setParticipant("adminx[admin]");
		dydata2.setColumn3("a");
		dydata2.setColumn4("b");
		dydata2.setColumn5("44");
		dydata2.setFatherlsh("1");// ����fatherlshΪ1��������ӱ������ݣ���ôfatherlsh��ֵ�Ǹ��������ݵ�lsh
		String lsh1 = DyEntry.iv().addData(formcode, dydata2);
		// ��ӵڶ�������
		String lsh2 = DyEntry.iv().addData(formcode, dydata2);
		
		
		// װ�ص�������
		DyFormData dydatax = DyEntry.iv().loadData(formcode, lsh2);
		// �޸�����
		dydatax.setColumn3("qqqqqq");
		boolean x1=DyEntry.iv().modifyData(dydatax.getFormcode(), dydatax);
		
		dydatax.setColumn3("qqqqqq2222");
		boolean x2=DyEntry.iv().modifyData(dydatax.getFormcode(), dydatax);

		// ��ѯ��������(����)
		DyFormData dydata = new DyFormData();
		dydata.setFormcode(formcode);
		List list = DyEntry.iv().queryData(dydata, 0, 100, "");
		
		//װ�ر�
		DyForm dyform = DyEntry.iv().loadForm(formcode);
		DyForm []subform=dyform.getSubform_();
		if(subform.length>0){
			//���lsh1����������2���ӱ�����
			// �������-1
			DyFormData dydata3 = new DyFormData();
			dydata3.setParticipant("adminx[admin]");
			dydata3.setColumn3("a");
			dydata3.setColumn4("b");
			dydata3.setColumn5("c");
			dydata3.setFatherlsh(lsh1);// ����fatherlshΪ1��������ӱ������ݣ���ôfatherlsh��ֵ�Ǹ��������ݵ�lsh
			String lsh3 = DyEntry.iv().addData(subform[0].getFormcode(), dydata3);
			// ��ӵڶ�������
			String lsh4 = DyEntry.iv().addData(subform[0].getFormcode(), dydata3);

			// ��ѯ�����ӱ�������
			String lshFather = lsh1; // ĳ��������

			DyFormData dydataSub = new DyFormData();
			dydataSub.setFormcode(subform[0].getFormcode());// �ӱ���formcode
			dydataSub.setFatherlsh(lshFather);
			List listSub = DyEntry.iv().queryData(dydataSub, 0, 4, "");
		}

		// -------------���ݲ����߼�--------------

		// -------------������߼�--------------

		
		// ��id
		String formcodex = dyform.getFormcode();
		// ��Ӧ��ҳ���е�html��չչʾ��Ϣ
		String htmlinfo = dyform.getHtmltitleinfo_();
		// ��������
		dyform.getFormname();
		// ������չʾ����չsql�ű�����
		dyform.getOrderinfo();
		// ����ʽ
		String style=dyform.getStyleinfo_();
		String styleurl=dyform.getStyleinfourl_();
		// ��ҳ��
		int pagesize = dyform.getEachPageSize_();
		
		//չʾ�������ֶ�
		DyFormColumn _all[] = dyform.getAllColumn_();
		// չʾ���ֶ�-��Թ����б��еĲ�ѯ�ֶ�
		DyFormColumn _qc[] = dyform.getQueryColumn_();
		for (int i = 0; i < _qc.length; i++) {
			DyFormColumn _qc1 = _qc[i];
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
			String columnid = _qc1.getColumnid();
			// �ֶ��������ģ�
			String columncode = _qc1.getColumname();

			// �ֶε�html����
			String htmltype = _qc1.getHtmltype();

			// �ֶ�Ĭ��ֵ
			String valuelist = _qc1.getValuelist();

		}

		// չʾ���ֶ�-��Ա��е�����ֶ�
		DyFormColumn _formx[] = dyform.getListColumn_();
		for (int i = 0; i < _qc.length; i++) {
			DyFormColumn _qc1 = _qc[i];
			// �ֶ�ID ����Ĭ���ֶ��⣬���е�����ֶζ�Ϊ columnN��ģʽ
			String columnid = _qc1.getColumnid();
			// �ֶ��������ģ�
			String columnname = _qc1.getColumname();
			// ��չ�ű� �ֶλ�ý�����¼�ʱ���ȵĽű�
			String focusScript = _qc1.getFocusScript();

			// �ֶε�html����
			String htmltype = _qc1.getHtmltype();
			// ��չ�ű� �ֶ�װ��ʱ�ĳ�ʼ���Ľű�
			String initScript = _qc1.getInitScript();
			// ��չ�ű� �ֶ�ʧȥ������¼�ʱ���ȵĽű�
			String losefocusScript = _qc1.getLoseFocusScript();

			// �ֶ�Ĭ��ֵ
			String valuelist = _qc1.getValuelist();
			// �ֶ�չ���е�x����
			double xoffset = _qc1.getXoffset();
			// �ֶ�չ���е�y����
			double yoffset = _qc1.getYoffset();

			// �Ƿ�����
			boolean hidden = _qc1.isHidden();
			// �Ƿ�ǿ�
			// _qc1.isMusk();
			// �Ƿ�ֻ��
			_qc1.isReadonly();
		}

		// ����ӱ�
		DyForm _subDy[] = dyform.getSubform_();
		for (int i = 0; i < _subDy.length; i++) {
			DyForm dyformSub = _subDy[i];
		}

		// -------------������߼�--------------

	}
}
