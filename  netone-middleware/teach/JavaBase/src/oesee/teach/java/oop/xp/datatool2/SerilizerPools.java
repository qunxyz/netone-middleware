package oesee.teach.java.oop.xp.datatool2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * ����־û�����
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class SerilizerPools {


	/**
	 * �־û�����
	 * 
	 * @param obj
	 */
	public static void toSerilizer(String table,Map obj) {
		OutputStream out;
		try {
			out = new FileOutputStream(table);
			ObjectOutputStream outObj = new ObjectOutputStream(out);
			outObj.writeObject(obj);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void init(String table) {
		File file = new File(table);
		file.deleteOnExit();
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * �ӳ־û��ļ��� ��ԭ����
	 * 
	 * @return
	 */
	public static Map fetchObj(String table) {

		Map maphis = null;
		ObjectInputStream in = null;
		try {
			File file = new File(table);
			if(!file.exists()){
				file.createNewFile();
				maphis=new HashMap();
				toSerilizer(table,maphis);
			}
			in = new ObjectInputStream(new FileInputStream(table));
			Object obj =  in.readObject();
			if(obj!=null){
				maphis=(Map)obj;
			}else{
				maphis=new HashMap();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return maphis;
	}
}
