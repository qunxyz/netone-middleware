package oe.cms.dao.infomodel;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import oe.cms.CmsEntry;
import oe.cms.cfg.TCmsInfomodel;

public class RichLevelImpl implements RichLevel {
	private Log _log = LogFactory.getLog(RichLevelImpl.class);

	public void doAutoTask() {
		ModelDao dao = (ModelDao) CmsEntry.fetchDao("modelDao");

		String blogLevelRichSeriTime = dao.fetchblogLevelARichSeriTime();
		schdule(blogLevelRichSeriTime, "blogLevelRichSeriTime",
				RichSeriJob.class);

		// // A级空间排名
		// String blogLevelARichSeriTime = dao.fetchblogLevelARichSeriTime();
		// schdule(blogLevelARichSeriTime, "blogLevelARichSeriTime",
		// ARichSeriJob.class);
		// // B级空间排名
		// String blogLevelBRichSeriTime = dao.fetchblogLevelBRichSeriTime();
		// schdule(blogLevelBRichSeriTime, "blogLevelBRichSeriTime",
		// BRichSeriJob.class);
		// // C级空间排名
		// String blogLevelCRichSeriTime = dao.fetchblogLevelCRichSeriTime();
		// schdule(blogLevelCRichSeriTime, "blogLevelCRichSeriTime",
		// CRichSeriJob.class);
		// // 进级A空间
		// String comeAlevelTime = dao.fetchcomeAlevelTime();
		// schdule(comeAlevelTime, "comeAlevelTime", AComeRichSeriJob.class);
		// // 进级B空间
		// String comeBlevelTime = dao.fetchbackBlevelTime();
		// schdule(comeBlevelTime, "comeBlevelTime", BComeRichSeriJob.class);
		// // 从A退入B空间
		// String backBlevelTime = dao.fetchbackBlevelTime();
		// schdule(backBlevelTime, "backBlevelTime", BBackRichSeriJob.class);
		// // 从B退入C空间
		// String backClevelTime = dao.fetchbackClevelTime();
		// schdule(backClevelTime, "backClevelTime", CBackRichSeriJob.class);
		//		
		// // 从评审冠军的时间
		// String winnerTime = dao.fetchWinnerTime();
		// schdule(winnerTime, "winnerTime", WinnerComeRichSeriJob.class);
	}

	private void schdule(String serailTime, String name, Class classes) {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched;
		try {
			sched = sf.getScheduler();
			JobDetail job2 = new JobDetail("job" + name, "group" + name,
					classes);
			CronTrigger trigger2 = new CronTrigger("trigger" + name, "group"
					+ name, "job" + name, "group" + name, serailTime);
			sched.addJob(job2, true);

			sched.scheduleJob(trigger2);
			System.out.println("Oesee space auto order:" + name
					+ " START [Pre:" + serailTime + "auto manager]");
			sched.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated cat
		}
	}

	public List queryByNewCreate() {
		ModelDao dao = (ModelDao) CmsEntry.fetchDao("modelDao");
		return dao.richViewByCreateTime();
	}

	public List queryByNewModify() {
		ModelDao dao = (ModelDao) CmsEntry.fetchDao("modelDao");
		return dao.richViewByModifyTime();
	}

	public List queryByName(String name) {
		ModelDao dao = (ModelDao) CmsEntry.fetchDao("modelDao");
		List list = dao.fetchList(_LEVEL_C);
		String nameLike = ".*" + name.toUpperCase() + ".*";
		Pattern pat = Pattern.compile(nameLike);
		List returnInfo = new ArrayList();
		int i = 0;
		for (Iterator itr = list.iterator(); itr.hasNext();) {

			TCmsInfomodel model = (TCmsInfomodel) itr.next();
			String nameReal = model.getModelname().toUpperCase();
			_log.debug(nameReal);// 先查询空间名
			if (pat.matcher(model.getModelname()).find()) {
				i++;
				if (i < _ANY_QUERY_LIMIT) {
					returnInfo.add(model);
				}
			}
		}
		return returnInfo;
	}

	public List queryLimit(List list, int form, int to) {
		if (list == null || list.size() < form) {
			return list;
		}
		if (list.size() < to) {
			to = list.size();
		}
		List listSub = new ArrayList();
		for (int i = form; i < to; i++) {
			listSub.add(list.get(i));
		}
		return listSub;
	}


}
