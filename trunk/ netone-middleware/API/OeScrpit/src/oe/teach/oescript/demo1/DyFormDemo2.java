package oe.teach.oescript.demo1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import oe.cav.bean.logic.bus.TCsBus;
import oescript.parent.OeScript;

/**
 * ��̬����̨�ű�Ӧ�� ����
 * 
 * ���°汾��̬����ͨ��ǰ̨�ű���չ�ͺ�̨�ű���չ������һ���������Ʊ�������ԡ����к�̨�ű����ֿɶ�
 * ҵ�����ݵĽ�һ���ӹ������м��ƽ̨ͨ���¼������ṩ�ű�ִ����ڣ��漰��������¼���<br>
 * 1���½�װ���¼� <br>
 * 2���½������¼� <br>
 * 3�������¼� <br>
 * 4��ɾ���¼� <br>
 * 5����ʾװ���¼� <br>
 * 6���޸�װ���¼�<br>
 * 
 * 
 * @author jiaxun chen
 * 
 */
public class DyFormDemo2  extends OeScript{
	/**
	 *����޸�ʱ��<br>
	 *����ͨ�� $(column) ����õ�ǰ�ύ�ı�������Ϣ������column��Ӧ�Ŷ�̬�����ֶ�����
	 */
	public void todo1(){
		
		//����޸�ʱ��
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		String a1=dateformat.format(new java.util.Date());
		//���ʱ��
		dy.set("$(formcode)", "$(lsh)", a1); 
		//����޸���
		dy.set("$(formcode)", "column12", "$(participant)");
	}
	/*
	 * Ĭ���ӱ���¼����ĳЩ��Ӧ�ó����У��������½�һ������ʱ�򣬸ñ���Ҫ�Զ�����N����Ĭ�����ݵ��ӱ���¼������
	 * �û���д��<br>
	 * ע���¼���Ҫ���� ����װ���¼���
	 */
	public List todo2(){
		
		TCsBus bux = new TCsBus();
		bux.setColumn3("���촴������");
		bux.setFatherlsh("$(fatherlsh)");
		bux.setFormcode("$(formcode)");
		
		TCsBus bux1 = new TCsBus();
		bux1.setColumn3("��֯��������");
		bux1.setFatherlsh("$(fatherlsh)");
		bux1.setFormcode("$(formcode)");
		
		TCsBus bux2 = new TCsBus();
		bux2.setColumn3("��������");
		bux2.setFatherlsh("$(fatherlsh)");
		bux2.setFormcode("$(formcode)");
		
		TCsBus bux3 = new TCsBus();
		bux3.setColumn3("��������");
		bux3.setFatherlsh("$(fatherlsh)");
		bux3.setFormcode("$(formcode)");
		
		List list=new ArrayList();
		list.add(bux);
		list.add(bux1);
		list.add(bux2);
		list.add(bux3);
		
		return list;
	}

}
