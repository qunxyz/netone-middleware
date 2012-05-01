import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import oesee.teach.java.oop.sample5.MenInfo;

/**
 * IOTest ≤‚ ‘¿‡
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class IOTest {

	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		//			MenInfo men=new MenInfo();
//		
//			Map map=BeanUtils.describe(men);
//			
//			for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
//				String  column = (String ) iterator.next();
//				if("classes".equals(column)){
//					continue;
//				}
//				BeanUtils.setProperty(men, column, "1");
//			}
//			
			
			String timeinfo="2008-12-12 00:00:00.000";
			long current=System.currentTimeMillis();
			long oldtime=Timestamp.valueOf(timeinfo).getTime();
			System.out.println(oldtime);
			System.out.println(current);
		
	}

}
