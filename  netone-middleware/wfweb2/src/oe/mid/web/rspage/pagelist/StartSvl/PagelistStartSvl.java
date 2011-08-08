package oe.mid.web.rspage.pagelist.StartSvl;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oe.frame.web.util.WebTip;
import oe.midware.workflow.client.SoaObj;
import oe.midware.workflow.client.TaskObject;
import oe.midware.workflow.service.soatools.ActiveBindDao;
import oe.midware.workflow.service.soatools.ActiveBindDaoImpl;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;


public class PagelistStartSvl extends HttpServlet {

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String name = request.getParameter("name");
		String chkid = request.getParameter("actionurl");
		try {
			ResourceRmi resourceRmi = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = resourceRmi.loadResourceById(chkid);
			String extendattribute = upo.getExtendattribute();
			ActiveBindDao xmldao = new ActiveBindDaoImpl();
			SoaObj soa = xmldao.fromXml(extendattribute);
			if (soa != null) {
				TaskObject taskobj = soa.getTask();
				if (taskobj != null) {
					String starttime = taskobj.getStarttime();
					String endtime = taskobj.getEndtime();
					String choicemode = taskobj.getChoicemode();
					String loopmode = taskobj.getLoopmode();
					String actionurl = starttime + "@" + choicemode + "@"
							+ loopmode + "@" + endtime;

					String[] attributeArray = actionurl.split("@");
					String startTimeStr = attributeArray[0];

					// 开始时间类型转换
					SimpleDateFormat f = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss");
					Date startTime = null;
					Date endTime = null;
					try {
						startTime = f.parse(startTimeStr);
						endTime = f.parse(endtime);
					} catch (ParseException e) {
						e.printStackTrace();
					}					
					
					// cron赋值
					String cronStr = loopmode;

					// 任务启动
					SchedulerFactory schedulerFactory = new StdSchedulerFactory();
					Scheduler scheduler = schedulerFactory.getScheduler();
					
					if(scheduler.getJobDetail("job", chkid)==null){
						
						JobDetail jobDetail = new JobDetail("job", chkid,TaskA.class);
						jobDetail.getJobDataMap().put("name", name);
						jobDetail.getJobDataMap().put("chkid", chkid);
						jobDetail.getJobDataMap().put("script",soa.getScript().getCdata());
						if(scheduler.getTrigger("trigger", chkid)==null){
							CronTrigger cronTrigger = new CronTrigger("trigger", chkid);
							cronTrigger.setStartTime(startTime);
							cronTrigger.setEndTime(endTime);
							CronExpression cexp = new CronExpression(cronStr);
							cronTrigger.setCronExpression(cexp);
							scheduler.scheduleJob(jobDetail, cronTrigger);
						}else{
							CronTrigger cronTrigger=(CronTrigger)scheduler.getTrigger("trigger", chkid);
							cronTrigger.setStartTime(startTime);
							cronTrigger.setEndTime(endTime);
							CronExpression cexp = new CronExpression(cronStr);
							cronTrigger.setCronExpression(cexp);
							scheduler.scheduleJob(jobDetail, cronTrigger);
						}
						scheduler.start();
						
					}else{
						WebTip.htmlInfoOri("<script>alert('任务不能重复启动，请使用休眠或唤醒功能')</script>",
								response);
					}

					WebTip.htmlInfoOri("<script>window.close();opener.search()</script>",
							response);
				} else {
					WebTip.htmlInfoOri("<script>alert('启动失败!日期数据不存在!')</script>",
									response);
				}
			} else {
				WebTip.htmlInfoOri("<script>alert('启动失败!日期数据不存在!')</script>",
						response);
			}
		} catch (NotBoundException e1) {
			e1.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
