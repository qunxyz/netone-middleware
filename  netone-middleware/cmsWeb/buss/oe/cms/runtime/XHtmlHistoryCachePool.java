package oe.cms.runtime;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import oe.cms.RootPath;
import oe.cms.cfg.TCmsInfocell;

public class XHtmlHistoryCachePool {

	/**
	 * �־û���������
	 * 
	 */
	public static void serialHistory() {
		if(true){
			throw new RuntimeException("���汣��ȡ��");
		}
		
		System.out.print("�־û���ʷ��Ϣ.......");
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String serilaFile = RootPath.getHistroyCachePath() + df.format(date);
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream(serilaFile));
			// out.writeObject(CmsCache.getCache());
			System.out.print("done");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * �����ʷ��Ϣ
	 * 
	 * @param cellid
	 * @param day
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static String fetchHistory(TCmsInfocell info, String day) {
		Map maphis = null;
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream(RootPath
					.getHistroyCachePath()
					+ day));
			maphis = (Map) in.readObject();
		} catch (Exception e) {
			System.err.println("û��" + day + "����ʷ�ļ�");
			return "";
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
		if (maphis != null) {
			return (String) maphis.get(info.getCellid());
		} else {
			return "û�� " + day + "����ʷ��Ϣ��";
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		serialHistory();
		// System.out.println(fetchHistory());
	}

}
