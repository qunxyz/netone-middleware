package oe.security3a.seupublic.authentication;

import oe.security3a.seupublic.authentication.obj.RequestCtx;
import oe.security3a.seupublic.authentication.obj.ResponseCtx;



/**
 * ����ִ�е��������
 * @author chen.jia.xun
 * 
 */
public interface PEPConsole {

	/**
	 * ���ʿ�������
	 * 
	 * @param request
	 *            �ĵ���ʽ�ɲο�Resource/sample/xacml/requst�е�xml�ĵ�
	 * @return response �ĵ���ʽ�ɲο�Resource/sample/xacml/response�е��ĵ�
	 * 
	 * �ýӿ��ǿ���̨����,������policy_config.xml�е�������Ϣ,��������ͬ�ķ��� ���Ʋ��ò�ͬ������ʵ��,
	 * ����Щ��ĿǰĬ���Ǵ�Spring�����л�� seupub_cfg.xml.
	 * 
	 * �ÿ���̨Ҫ���Ч��,����ʹ���ϻ���
	 * 
	 */
	ResponseCtx evaluate(String code, RequestCtx request) throws Exception;

}
