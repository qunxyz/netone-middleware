package oe.mid.soa.bean;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Iterator;

import oe.rmi.client.RmiEntry;

public class Test {

	public static void main(String[] args) throws MalformedURLException,
			RemoteException, NotBoundException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		BeanService beansv = (BeanService) RmiEntry.iv("beanhandle");
		SoaBean bea = beansv.inParamDescription("beansample1");

		for (Iterator iter = bea.getClassproperty().keySet().iterator(); iter
				.hasNext();) {
			String columnid = (String) iter.next();
			System.out.println(columnid);
		}
		bea.getValues().put("age", new Integer(60));
		bea.getValues().put("sex", new Boolean(true));
		// ����ҵ�����
		beansv.saveInParam(bea);

		beansv.todo(bea.getLsh());

		// ��÷��ز���Ŀǰ��ʱ���У���һ������ Ϊ��
		SoaBean bean = beansv.loadOutParam(bea.getLsh());
		
		
		System.out.println(bean.getValues().get("types"));
	}
}
