package oe.midware.workflow.engine;

import java.util.Iterator;
import java.util.List;

import oe.frame.bus.workflow.ProcessAuto;
import oe.frame.bus.workflow.actor.AutoMen;
import oe.frame.orm.OrmerEntry;
import oe.midware.workflow.runtime.ActivityRef;
import oe.midware.workflow.runtime.RuntimeWorklistRef;
import oe.midware.workflow.runtime.ormobj.TWfWorklist;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 同步自动化活动操作
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class ProcessAutoTmpImp implements ProcessAuto {
	private Log _log = LogFactory.getLog(ProcessAutoTmpImp.class);

	private AutoMen autoMen;

	public void autoexecute() {
		List list = checkHaveAuto();
		if (_log.isDebugEnabled()) {
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				TWfWorklist worklist = (TWfWorklist) itr.next();
				_log.debug("autoExecute worklist:" + worklist.getActivityid());
			}
		}
		while (list != null && list.size() > 0) {
			autoMen.execute(list);
			list = checkHaveAuto();
		}
	}

	/**
	 * 寻找所有
	 * 
	 * @return
	 */
	private List checkHaveAuto() {
		TWfWorklist worklist = new TWfWorklist();
		// 临时为了比赛而设计的
		String condition1 = "and executestatus='"
				+ RuntimeWorklistRef.STATUS_RUNNING[0] + "' AND  types='"
				+ ActivityRef.ACT_ROUTE_KEY[0] + "' or executestatus='"
				+ RuntimeWorklistRef.STATUS_SUBFLOW_WAITING[0] + "'";

		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(worklist,
				null,condition1);

	}

	public AutoMen getAutoMen() {
		return autoMen;
	}

	public void setAutoMen(AutoMen autoMen) {
		this.autoMen = autoMen;
	}

}
