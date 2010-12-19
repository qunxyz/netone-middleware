package oe.cms.runtime.timeTrigger;

import javax.servlet.http.HttpServletRequest;

import org.quartz.Scheduler;

public interface InTimeTrigger {

	String DEFAULT_TRIGER = "0 0 6 * * ?";

	Scheduler initdo() throws Exception;

	Scheduler serildo() throws Exception;

	Scheduler zipdo() throws Exception;

	void initStart(HttpServletRequest requ);

}
