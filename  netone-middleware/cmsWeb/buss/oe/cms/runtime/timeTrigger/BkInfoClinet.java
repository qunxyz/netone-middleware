package oe.cms.runtime.timeTrigger;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import oe.cms.EnvEntryInfo;
import oe.cms.dao.blog.util.ReadNetInfo2;



/**
 * 备份工具包 客户端程序
 * 
 * @author chen.jia.xun(robanco)
 * 
 */
public class BkInfoClinet {

	public static void bkClient() throws RemoteException {
		// 从环境变量中,获得本地地址, 用于存放从服务器中下载的资源
		String localuri = EnvEntryInfo.env.fetchEnvValue("localuri");
		// 从环境变量中,获得服务器中备份资源的地址
		String remoteuriTmp = EnvEntryInfo.env.fetchEnvValue("remoteuri");

		if (remoteuriTmp == null || remoteuriTmp.equals("")) {
			return;
		}
		// 创建下载到本地文件的名字
		String[] remoteuri = remoteuriTmp.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		String dateinfo = sdf.format(new Date());
		String nameAhead = localuri + dateinfo + "_";

		for (int i = 0; i < remoteuri.length; i++) {
			// 取得ZIP文件名称
			String fileName = remoteuri[i].substring(remoteuri[i]
					.lastIndexOf("/") + 1);
			// 下载到本地的文件名称
			String localuriReal = nameAhead + fileName;
			// 调用下载程序,将服务器中的资源下载到本地
			ReadNetInfo2.netParser(remoteuri[i], localuriReal);
		}

	}

	/**
	 * @param args
	 * @throws RemoteException 
	 */
	public static void main(String[] args) throws RemoteException {
		bkClient();
	}

}
