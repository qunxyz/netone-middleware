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
		// 保存业务对象
		beansv.saveInParam(bea);

		beansv.todo(bea.getLsh());

		// 获得返回参数目前的时候中，第一个参数 为空
		SoaBean bean = beansv.loadOutParam(bea.getLsh());
		
		
		System.out.println(bean.getValues().get("types"));
	}
}
