package oe.mid.web.rspage.pagelist.StartSvl;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import oe.midware.workflow.service.WorkflowConsole;
import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 任务A
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com <br>
 *         tel: 13328675083
 * 
 */

public class TaskA implements Job {
	public TaskA() {
	}
	static Map map=new HashMap();
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		Date date=new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strdate = formatter.format(date);
		String name = context.getJobDetail().getJobDataMap().getString("name");
		String chkid = context.getJobDetail().getJobDataMap().getString("chkid");
		System.out.println("计划任务" + name + "启动");
		System.out.println("执行时间："+strdate);
		List list=new ArrayList();
		if(map.get(chkid)==null){
			list.add(System.currentTimeMillis());
			map.put(chkid, list);
		}else{
		    list=(List)map.get(chkid);
			list.add(System.currentTimeMillis());
			map.put(chkid, list);
		}
		System.out.println("结束");
		
		// 流程控制台操作句柄
		String runtimeid = null;
		String script = context.getJobDetail().getJobDataMap().getString("script");
		script=StringUtils	.replaceChars(script, "'", "\"");
		try {
			WorkflowConsole console = (WorkflowConsole) RmiEntry.iv("wfhandle");
			console.exeScript(script, runtimeid);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}