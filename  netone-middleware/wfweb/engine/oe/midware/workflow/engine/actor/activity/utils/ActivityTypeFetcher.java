package oe.midware.workflow.engine.actor.activity.utils;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.frame.bus.workflow.ActivityExtendAttribute;
import oe.frame.bus.workflow.RuntimeInfo;
import oe.midware.workflow.XMLException;
import oe.midware.workflow.runtime.ActivityRef;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.activity.Implementation;
import oe.midware.workflow.xpdl.model.activity.SubFlow;
/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com<br>
 *         support by http://www.oesee.com
 * 
 */
public class ActivityTypeFetcher {
	private static Log _log = LogFactory.getLog(ActivityTypeFetcher.class);

	public static String fetchType(Activity act) {

		Implementation imp = act.getImplementation();
		if (imp == null) {
			return ActivityRef.ACT_ROUTE_KEY[0];
		} else {
			String types = imp.getType().toString();
			if (ActivityRef.ACT_NORMAL.equalsIgnoreCase(types)
					|| ActivityRef.ACT_TOOL.equalsIgnoreCase(types)) {
				try {
					Map extendMap = act.getExtendedAttributes().getMap();
					if (extendMap
							.containsKey(ActivityExtendAttribute._SUBFLOW_ID)) {

						String syncMode = (String) extendMap
								.get(ActivityExtendAttribute._SUBFLOW_SYNCMODE);
						if (ActivityExtendAttribute._VALUE_SUBFLOW_SYNCMODE_SYNC
								.equals(syncMode)) {
							return ActivityRef.ACT_SUBFLOW_SYNC_KEY[0];
						}
						if (ActivityExtendAttribute._VALUE_SUBFLOW_SYNCMODE_DISYNC
								.equals(syncMode)) {
							return ActivityRef.ACT_SUBFLOW_KEY[0];
						}
					}
				} catch (XMLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return ActivityRef.ACT_NORMAL_KEY[0];
			} else if (ActivityRef.ACT_SUBFLOW.equalsIgnoreCase(types)) {
				// 主要为了兼容Jawe而存在的
				SubFlow sub = (SubFlow) imp;
				String exeType = sub.getExecution().toString();
				if (ActivityRef.FLOW_SYNCHR.equalsIgnoreCase(exeType)) {
					return ActivityRef.ACT_SUBFLOW_SYNC_KEY[0];
				}
				return ActivityRef.ACT_SUBFLOW_KEY[0];
			}
			_log.error(RuntimeInfo.OE_WF_DEF_ERR_011 + ":" + types);
			throw new RuntimeException(RuntimeInfo.OE_WF_DEF_ERR_011 + ":"
					+ types);
		}
	}
}
