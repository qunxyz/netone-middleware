/**
 * 
 */
package com.report.flashchart.ifc;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.report.flashchart.entity.ChartCompVar;
import com.report.flashchart.entity.ChartDataObj;

/**
 * @author Don(cai.you.dun)
 * @version 1.0.0 2012-1-30 ÏÂÎç04:07:08
 * @history
 */
public class ChartServiceImpl implements ChartService {

	public String entry(String chartCompVar, ChartDataObj data) {
		JSONObject json = new JSONObject();
		return entry(json, chartCompVar, data);
	}

	public String entry(JSONObject paramjson, String chartCompVar,
			ChartDataObj data) {
		JSONObject json = paramjson;
		json = json.fromObject(data);
		json.put($COMP$, chartCompVar);
		return json.toString();
	}

	public void output(HttpServletRequest request, String output, String title) {
		request.setAttribute("jsonParam", output);
		request.setAttribute("title", title);
	}

	@Deprecated
	public String loadComp() {
		JSONArray arr = new JSONArray();

		JSONObject json = new JSONObject();
		json.put("COMP_PIE_CHART", ChartCompVar.COMP_PIE_CHART);
		json.put("COMP_PIE_2L_CHART", ChartCompVar.COMP_PIE_2L_CHART);
		json.put("COMP_PIE_LINK_CHART", ChartCompVar.COMP_PIE_LINK_CHART);

		arr.add(json);
		return arr.toString();
	}

}
