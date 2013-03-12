package com.jl.common.msg;

import java.util.List;

public interface MsgIfc {
	
	/**
	 * �½���Ϣ
	 * @param sender ������
	 * @param msgto ��Ϣ�����Ͷ�������ж���û���ѭ�����ø÷���
	 * @param msgbody ��Ϣ����
	 * @param comment �Ƿ��������ۣ�true��ʾ�������ۣ�false��ʾ��ֹ����
	 * @return ��Ϣlsh
	 */
	String newMsg(String sender,String msgto,String msgbody,boolean comment);
	
	/**
	 * ת����Ϣ
	 * @param sender ������
	 * @param msgto ��Ϣ������
	 * @param msgbody ��Ϣ����
	 * @param comment �Ƿ��������ۣ�true��ʾ�������ۣ�false��ʾ��ֹ����
	 * @param sourceMsgLsh ��ϢԴlsh
	 * @return ��Ϣlsh
	 */
	String forwardMsg(String sender,String msgto,String msgbody,boolean comment,String sourceMsgLsh);
	
	/**
	 * ����
	 * @param sender ������
	 * @param sourceMsgLsh �����۵���Ϣ
	 * @param msgbody ��������
	 * @return ��Ϣlsh
	 */
	String commentMsg(String sender,String sourceMsgLsh,String msgbody);
	
	/**
	 * �ҵ�Ⱥ���Ⱥ���е���Ա
	 * @param userid �û�id
	 * @return Ⱥ����Ϣ����Ա��Ϣ List��Ƕ��map������Ԫ���� groupid Ⱥ��id��groupnameȺ����,people Ⱥ���е���Ա�����ж�� �ö��Ÿ���
	 * 
	 * @�ο�Ⱥ������ Ӧ�� http://42.120.40.204:8080/scm/frame.do?method=onEditViewMain&naturalname=APPFRAME.APPFRAME.MSG.MYTEAM
	 * Ⱥ��չʾ���ݸ�ʽ http://42.120.40.204:82/netoneWebSerivce/MyGroupSvl?userid=msg1
	 */
	List myGroupAndMember(String userid);
	
	/**
	 * ��Ϣ�б�
	 * @param userid �û�id
	 * @param type ��Ϣ����Type=01 ��ʾȫ����Ϣ��02 ��������Ϣ03 ������Ϣ��04 �ᵽ�ҵ�05 ���� 06 ת�� 00ԭ�Ĵ������б�
	 * @param firsttime �ǵ�һ����¼��ʱ�� ��ʽ 2012-12-12 00:00:00����������ʾ������Ϣ
	 * @param lasttime �����һ����¼��ʱ�� ��ʽ 2012-12-12 00:00:00����������ʾ������Ϣ
	 * @param lsh ������Ϣ��lsh
	 * @return list �����Map��ʾһ����¼������ֶ�˵�����£�lsh ��Ϣid,timex ����ʱ�䡢sendercode ������code ,sendername ���������֣�myimgurl ��
��ͷ��(Ϊ��ʱ��Ĭ��ͷ��)recivercode ��Ϣ���������code,recivername ��Ϣ������������֣�context ��Ϣ���ݣ�
rpnum ת������,rtnum �ظ�����atturl ͼƬ������ַ��atturlzip ��ѹ���ĸ�����ַ��rtsourcelsh ת��֮ԭ��id��rtusername ת
��ԭ����������canrp �Ƿ��ֹ����=1 ��ʾ��ֹ��isrt �Ƿ�ת��=1 ��ʾת�������04 �ᵽ�ҵ���Ϣ����ô������Ƕ����Ϣrel ���������Ϣ����
	 */
	List msgList(String userid,String type,String firsttime,String lasttime,String lsh);


}
