package oe.cms.runtime.timeTrigger;

import java.io.File;
import java.rmi.RemoteException;

import oe.cms.EnvEntryInfo;
import oe.cms.dao.blog.util.ZipUtil;

import org.apache.commons.lang.StringUtils;

/**
 * 备份打包程序
 * 
 * @author chen.jia.xun(robanco)
 * 
 */
public class BkInfoServer {

	/**
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		// 从服务器的环境中获得 备份文件的存放路径
		String localuri = EnvEntryInfo.env.fetchEnvValue("ziplocaluri");
		if (localuri == null || localuri.equals("")) {
			return;
		}

		String path = EnvEntryInfo.env.fetchEnvValue("cpath");
		path = StringUtils.replace(path, "%20", " ");

		// 根据备份说明,压缩备份文件
		String[] localuriArr = localuri.split(",");
		for (int i = 0; i < localuriArr.length; i++) {
			String[] info = StringUtils.split(localuriArr[i], "$");
			String zipPath = path + info[1];
			String filename = path + info[0];
			File file = new File(zipPath);
			try {
				ZipUtil.zip(filename, file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
