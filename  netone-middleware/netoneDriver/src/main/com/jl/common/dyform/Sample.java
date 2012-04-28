package com.jl.common.dyform;

import java.rmi.RemoteException;
import java.util.List;

public class Sample {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		String formcode = DyFormConsoleImpl_Test.formcode;

		// -------------���ݲ����߼�--------------

		// �������
		DyFormData dydata2 = new DyFormData();
		dydata2.setParticipant("adminx[admin]");
		dydata2.setColumn1("xxxxxx");
		dydata2.setColumn2("1");
		dydata2.setColumn3("zzzzz");
		dydata2.setFormcode(formcode);
		dydata2.setFatherlsh("1");// ����fatherlshΪ1��������ӱ������ݣ���ôfatherlsh��ֵ�Ǹ��������ݵ�lsh
		DyEntry.iv().addData(formcode, dydata2);

		// ɾ������
		String lsh = "1";
		DyEntry.iv().deleteData(formcode, lsh);

		// װ�ص�������
		String lsh2 = "2";
		DyFormData dydatax = DyEntry.iv().loadData(formcode, lsh2);

		// �޸�����
		dydata2.setColumn3("qqqqqq");
		DyEntry.iv().modifyData(formcode, dydata2);

		// ��ѯ��������(����)
		DyFormData dydata = new DyFormData();
		dydata.setFormcode(formcode);
		List list = DyEntry.iv().queryData(dydata, 0, 4, "");

		// ��ѯ�����ӱ�������

		String lshFather = "3"; // ĳ��������

		DyFormData dydataSub = new DyFormData();
		dydataSub.setFormcode(formcode);// �ӱ���formcode
		dydataSub.setFatherlsh(lshFather);
		List listSub = DyEntry.iv().queryData(dydataSub, 0, 4, "");

		// -------------���ݲ����߼�--------------

		// -------------������߼�--------------

		DyForm dyform = DyEntry.iv().loadForm(formcode);
		// ��id
		String formcodex = dyform.getFormcode();
		// ��Ӧ��ҳ���е�html��չչʾ��Ϣ
		String htmlinfo = dyform.getHtmltitleinfo_();
		// ��������
		dyform.getFormname();
		// ������չʾ����չsql�ű�����
		dyform.getOrderinfo();
		// ����ʽ
		dyform.getStyleinfo_();
		// ��ҳ��
		int pagesize = dyform.getEachPageSize_();

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
			//_qc1.isMusk();
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
