package oesee.teach.java.oop.xp.datatool1;

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
 * 对象持久化工具
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class SerilizerPools {

	static final String DATA_FILE = "commdata";

	/**
	 * 持久化对象
	 * 
	 * @param obj
	 */
	public static void toSerilizer(Map obj) {
		OutputStream out;
		try {
			out = new FileOutputStream(DATA_FILE);
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

	public static void init() {
		File file = new File(DATA_FILE);
		file.deleteOnExit();
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 从持久化文件中 还原对象
	 * 
	 * @return
	 */
	public static Map fetchObj() {

		Map maphis = null;
		ObjectInputStream in = null;
		try {
			File file = new File(DATA_FILE);
			if(!file.exists()){
				file.createNewFile();
				maphis=new HashMap();
				toSerilizer(maphis);
			}
			in = new ObjectInputStream(new FileInputStream(DATA_FILE));
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
