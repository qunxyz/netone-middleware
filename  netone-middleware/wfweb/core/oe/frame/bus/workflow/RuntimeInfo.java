package oe.frame.bus.workflow;

/**
 * �����ڳ���
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public interface RuntimeInfo {

	// /////////////���̶��������Ϣ///////////////////
	String OE_WF_DEF_ERR_001 = "OE_WF_DEF_ERR_001 [���̶�����ȱ���׻]";

	String OE_WF_DEF_ERR_002 = "OE_WF_DEF_ERR_002 [���̶�����ȱ��β�]";

	String OE_WF_DEF_ERR_003 = "OE_WF_DEF_ERR_003 [�����̶������]";

	String OE_WF_DEF_ERR_004 = "OE_WF_DEF_ERR_004 [ȱ�پ�̬�����]";

	String OE_WF_DEF_ERR_005 = "OE_WF_DEF_ERR_005 [ȱ�پ�̬���̶���]";

	String OE_WF_DEF_ERR_006 = "OE_WF_DEF_ERR_006 [������ȱ�ٻ����]";

	String OE_WF_DEF_ERR_007 = "OE_WF_DEF_ERR_007 [��Ч������]";

	String OE_WF_DEF_ERR_008 = "OE_WF_DEF_ERR_008 [�������ڴ������϶��幫ʽ����]";

	String OE_WF_DEF_ERR_009 = "OE_WF_DEF_ERR_009 [���̶����ļ�������XPDL�淶]";

	String OE_WF_DEF_ERR_010 = "OE_WF_DEF_ERR_010 [���̰�Ϊ��]";

	String OE_WF_DEF_ERR_011 = "OE_WF_DEF_ERR_011 [��Ч�����]";

	String OE_WF_DEF_ERR_012 = "OE_WF_DEF_ERR_012 [���̲�����]";

	String OE_WF_DEF_ERR_013 = "OE_WF_DEF_ERR_013 [���е����̶��岻Ψһ]";

	String OE_WF_DEF_ERR_014 = "OE_WF_DEF_ERR_014 [����δע��]";

	// ////////////�������д�����Ϣ////////////////
	String OE_WF_RMT_ERR_001 = "OE_WF_RMT_ERR_001 [ȱ���������ж���]";

	String OE_WF_RMT_ERR_002 = "OE_WF_RMT_ERR_002 [�����������ʵ��ʧ��]";

	String OE_WF_RMT_ERR_003 = "OE_WF_RMT_ERR_003 [ȱ�ٻ���ж���]";

	String OE_WF_RMT_ERR_004 = "OE_WF_RMT_ERR_004 [�������д��ڷ�׼��״̬���޷�����]";

	String OE_WF_RMT_ERR_005 = "OE_WF_RMT_ERR_005 [������״̬�µĶ�̬����޷��ύ]";

	String OE_WF_RMT_ERR_006 = "OE_WF_RMT_ERR_006 [�������ж����ڷ�����״̬�У��޷�����]";

	String OE_WF_RMT_ERR_007 = "OE_WF_RMT_ERR_007 [���������쳣�ж�]";

	String OE_WF_RMT_ERR_008 = "OE_WF_RMT_ERR_008 [��ͬ���ȴ��]";

	String OE_WF_RMT_ERR_009 = "OE_WF_RMT_ERR_009 [ͬ����ȴ�]";

	String OE_WF_RMT_WAR_009 = "OE_WF_RMT_ERR_010 [�Զ��ύ���Զ�״̬�Ļ]";

	// /////////////////����������Ϣ///////////////////

	String OE_WF_RMT_INFO_001 = "OE_WF_RMT_INFO_001 [����Ŀǰ���ھ���ģʽ�У���ǰ���·��Ҫ������]";

	String OE_WF_RMT_INFO_002 = "OE_WF_RMT_INFO_002 [����ִ�н���]";

	String OE_WF_RMT_INFO_003 = "OE_WF_RMT_INFO_003 [�ȴ�ͬ��]";

	String OE_WF_RMT_INFO_004 = "OE_WF_RMT_INFO_004 [�ջ�Զ��ύ]";

	String OE_WF_RMT_INFO_005 = "OE_WF_RMT_INFO_005 [����������]";

	String OE_WF_RMT_INFO_006 = "OE_WF_RMT_INFO_006 [ͬ��������ִ�н���������]";

	// ///////////////���������쳣/////////////////
	String OE_WF_RULE_001 = "OE_WF_RULE_001[����û�и�ֵ]";

	String OE_WF_RULE_002 = "OE_WF_RULE_002[�﷨����]";

	String OE_WF_RULE_003 = "OE_WF_RULE_003[�����±����]";

	String OE_WF_RULE_004 = "OE_WF_RULE_004[��ر���ʵ����ʧ]";

	String OE_WF_RULE_005 = "OE_WF_RULE_005[��ʽ�������]";

	String OE_WF_RULE_006 = "OE_WF_RULE_006[ȱ�ٱ�������]";

	String OE_WF_RULE_007 = "OE_WF_RULE_007[��ȡ����ֵ����]";

	// ////////������ݱ���Ϣ///////////
	String _OE_WF_REV_EXT_COLUMN_NAME = "name";// ��չ�����е��ֶ���
	String _OE_WF_REV_EXT_COLUMN_VALUEMODE = "valuemode";// ��չ�����е��ֶ���
	String _OE_WF_REV_EXT_COLUMN_VALUE = "value";// ��չ�����е��ֶ���

	String _OE_WF_REV_EXT_COLUMN_HIDDEN = "hidden";// ��չ�����е��ֶ���,��־����1Ϊ��

	String _OE_WF_REV_EXT_COLUMN_READONLY = "readonly";// ��չ�����е��ֶ�������־ֻ�� 1Ϊ��

	String _OE_WF_REV_FORM_LIST = "list";
	String _OE_WF_REV_FORM_NUMBER = "number";
	String _OE_WF_REV_FORM_STRING = "string";
	String _OE_WF_REV_FORM_DYFORM = "dyform";
	String _OE_WF_REV_FORM_URL = "url";

}
