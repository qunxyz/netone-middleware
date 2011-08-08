package oe.midware.workflow.design;

import oe.midware.workflow.xpdl.model.workflow.WorkflowProcess;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import oe.midware.workflow.XMLException;
import oe.midware.workflow.xpdl.model.activity.Activity;
import oe.midware.workflow.xpdl.model.activity.Implementation;
import oe.midware.workflow.xpdl.model.data.DataField;
import oe.midware.workflow.xpdl.model.misc.ExtendedAttributes;
import oe.midware.workflow.xpdl.model.transition.Join;
import oe.midware.workflow.xpdl.model.transition.Split;
import oe.midware.workflow.xpdl.model.transition.Transition;
import oe.midware.workflow.xpdl.model.transition.TransitionRestriction;


public class WebWorkflowImpl implements WebWorkflow {

	int trackOff = 0;// 整个流程轨迹下移的大小

	int off = 33;

	public String fetchWorkflow(WorkflowProcess proc) {

		Map actinfo=new HashMap();
		String strAction = fetchAction(actinfo,proc);
		String strLine = fetchLine(actinfo,proc);
		String strIcon = Icon(actinfo,proc);
		String StrData = relationData(proc);
		String StrProcess = processHeadAttribute(proc);
		return strIcon + strLine + strAction + StrData + StrProcess
				+ fetchActivitySerialNumber(proc);
	}

	private String fetchActivitySerialNumber(WorkflowProcess proc) {
		String indexMax = "20";// 通常情况下一个流程图中的节点个数不会超过20个,那么如果出现意外,读不到上次的节点最大数,默认让最大数为20是个安全的做法
		String indexCount = "20";
		String otherCount = "20";
		try {
			Map info = proc.getExtendedAttributes().getMap();
			indexMax = (String) info.get("activitymaxcount");
			indexMax = (String) info.get("activitymaxcount");
			indexCount = (String) info.get("activitycount");
			otherCount = (String) info.get("othercount");
		} catch (XMLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String indexMaxStr = "<input id=\"activitymaxcount\" type=\"hidden\" value=\""
				+ indexMax + "\" />";
		String indexCountStr = "<input id=\"activitycount\"  type=\"hidden\"  value=\""
				+ indexCount + "\" />";
		String otherCountStr = "<input id=\"otherCount\"  type=\"hidden\"  value=\""
				+ otherCount + "\" />";
		return indexMaxStr + indexCountStr + otherCountStr;
	}

	/**
	 * 获得节点的属性
	 * 
	 * @return
	 */
	public String fetchAction(Map actinfo,WorkflowProcess proc) {
		String actionStr = "";
		String trueName, trueId, forward, tracklink, deadline, extendAction;
		ExtendedAttributes extendAttribute;
		String forwardCondition, offX, offY, participantID, afterCondition;
		Object key;
		try {
			WorkflowProcess workflow = proc;
			Activity[] action = workflow.getActivity();
			int length = action.length;
			for (int i = 0; i < length; i++) {
				String activityEditType = "tools";
				Implementation imple = action[i].getImplementation();
				if (imple == null) {
					activityEditType = "route";
				} else {
					String subflowinfo = action[i].getExtendedAttributes().get(
							"subflowId");
					if (subflowinfo != null && !subflowinfo.equals("")) {
						activityEditType = "subflow";
					}
				}
				extendAction = "";
				trueName = "";
				trueId = "";
				forward = "";
				tracklink = "";
				deadline = "";
				forwardCondition = "";
				offX = "";
				offY = "";
				participantID = "";
				afterCondition = "";
				extendAttribute = action[i].getExtendedAttributes();
				trueName = action[i].getName();
				trueId = action[i].getId();
				if ((action[i].getLimit()) != null) {
					deadline = String
							.valueOf((action[i].getLimit()).getValue());
				}
				tracklink = fetchActionNextLink(trueId,proc);
				forward = fetchActionForwardLink(trueId,proc);
				TransitionRestriction[] tranRes = action[i]
						.getTransitionRestriction();
				if (tranRes.length != 0) {
					Join join = tranRes[0].getJoin();
					Split split = tranRes[0].getSplit();
					if (join != null) {
						forwardCondition = (join.getType()).name;
					}
					if (split != null) {
						afterCondition = (split.getType()).name;
					}
				}

				Map extend = extendAttribute.getMap();
				Set set = extend.keySet();
				Iterator ite = set.iterator();

				while (ite.hasNext()) {
					key = ite.next();
					if ("XOffset".equals(key)) {
						offX = (String) extend.get(key);
					} else if ("YOffset".equals(key)) {
						offY = (String) extend.get(key);
					} else if ("ParticipantID".equals(key)) {
						participantID = (String) extend.get(key);
					} else {
						if (extendAttribute != null) {
							List extendlist = extendAttribute.getList(key
									.toString());
							int listLength = extendlist.size();
							for (int k = 0; k < listLength; k++) {
								if (extendAction == "") {
									extendAction = key + "&"
											+ extendlist.get(k);
								} else {
									extendAction += "," + key + "&"
											+ extendlist.get(k);
								}

							}
						}
					}
				}
				actionStr += actionToString(trueId, trueName, tracklink,
						forward, forwardCondition, afterCondition,
						extendAction, offX, offY, deadline, activityEditType);
				Action act = new Action(offX, offY,
						action[i].isStartActivity(), action[i].isExitActivity());
				actinfo.put(trueId, act);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return actionStr;
	}

	/**
	 * 获得该节点下的所有节点的Id;
	 * 
	 * @param actionId
	 *            该节点的ID
	 * @return
	 */
	public String fetchActionNextLink(String actionId,WorkflowProcess proc) {
		String tracklink = "";
		String fromId = "";
		String toId = "";
		String lineType = "";
		String type = "";
		Transition[] transition = proc.getTransition();
		int length = transition.length;
		if (length != 0) {
			for (int i = 0; i < length; i++) {
				fromId = transition[i].getFrom();
				ExtendedAttributes extend = transition[i]
						.getExtendedAttributes();
				try {
					type = (String) (extend.getMap()).get("RoutingType");
					if ("SIMPLEROUTING".equals(type)) {
						lineType = "ZLine";
					}
					if ("NOROUTING".equals(type)) {
						lineType = "beenLine";
					}
				} catch (Exception e) {
					System.out.println(e + "6");
				}
				if (actionId.equals(fromId)) {
					tracklink += transition[i].getTo() + "&" + lineType + ",";
				}
			}
		}

		if (tracklink == "") {
			tracklink = "end_" + actionId + "&" + iconLineType(actionId, "end",proc)
					+ ",";
		}
		return tracklink;
	}

	/**
	 * 获得该节点上的所有节点的Id;
	 * 
	 * @param actionId
	 *            该节点的ID
	 * @return
	 */
	public String fetchActionForwardLink(String actionId,WorkflowProcess proc) {
		String forward = "";
		String fromId = "";
		String toId = "";
		String lineType = "";
		String type = "";
		Transition[] transition = proc.getTransition();
		int length = transition.length;
		if (length != 0) {
			for (int i = 0; i < length; i++) {
				toId = transition[i].getTo();
				ExtendedAttributes extend = transition[i]
						.getExtendedAttributes();
				try {
					type = (String) (extend.getMap()).get("RoutingType");
					lineType = lineType(type);
				} catch (Exception e) {
					System.out.println(e + "5");
				}
				if (actionId.equals(toId)) {
					forward += transition[i].getFrom() + "&" + lineType + ",";
				}
			}
		}
		if (forward == "") {
			forward = "start_" + actionId + "&"
					+ iconLineType(actionId, "start",proc) + ",";
		}
		return forward;
	}

	public String actionToString(String id, String name, String tracklink,
			String forward, String forwardCondition, String afterCondition,
			String extendAttribute, String offX, String offY, String deadline,
			String eidttype) {
		String actionstr = "";
		int trackTop = Integer.parseInt(offY) + trackOff;
		String classChoice = "class='activity' />\n";
		if ("route".equals(eidttype)) {
			classChoice = "class='route'/>\n";
		} else if ("subflow".equals(eidttype)) {
			classChoice = "class='subflow'/>\n";
		}
		actionstr = "<div id='trackAction' name='"
				+ id
				+ "'isLink='false'\n"
				+ "forward='"
				+ forward
				+ "' tracklink='"
				+ tracklink
				+ "' actionType='"
				+ eidttype
				+ "' deadline='"
				+ deadline
				+ "'\n"
				+ "actionTrueId='"
				+ id
				+ "' actionExtendAttribute ='"
				+ extendAttribute
				+ "'  forwardCondition='"
				+ forwardCondition
				+ "'\n"
				+ "afterCondition ='"
				+ afterCondition
				+ "' style='position:absolute;visibility: visible;\n"
				+ "padding:2px; height:30px; width:70px;\n"
				+ "left:"
				+ offX
				+ "px; top:"
				+ trackTop
				+ "px;zIndex:10'><table id = 'table2'\n"
				+ "cellspacing=0 cellpadding=0 width=100% height='100%'\n bor"
				+ "style='cursor:hand;font-size:12px;zIndex:10' oncontextmenu='return false' >\n"
				+ "<tr><td id ='ping' name = 'track' align=center >\n"
				+ "<input id='work" + id
				+ "' name='textarea' type='button' value='" + name + "' "
				+ classChoice + "</td></tr></table></div>\n";
		return actionstr;
	}

	/**
	 * 获得路径的属性
	 * 
	 */
	public String fetchLine(Map actinfo,WorkflowProcess proc) {
		Transition[] transition = proc.getTransition();
		int length = transition.length;
		String lineString = "";
		ExtendedAttributes extend;
		String id = "";
		String name = "";
		String falseName = "";
		String extendContent = "";
		String depict = "";
		String condition = "";
		String fx = "";
		String fy = "";
		String tx = "";
		String ty = "";
		String lineTy = "";
		Object lineKey;
		Map lineExtend;
		Set lineSet;
		Iterator lineIte;
		List lineList;
		for (int i = 0; i < length; i++) {
			extendContent = "";
			id = "";
			name = "";
			fx = "";
			fy = "";
			tx = "";
			ty = "";
			lineTy = "";
			lineTy = "";
			lineKey = "";
			lineExtend = null;
			lineSet = null;
			id = transition[i].getId();
			name = transition[i].getName();
			falseName = transition[i].getFrom() + "->" + transition[i].getTo();

			if (transition[i].getDescription() != null) {
				depict = transition[i].getDescription();
			} else {
				depict = "";
			}
			if (transition[i].getCondition() != null) {
				condition = (transition[i].getCondition()).getValue();
			} else {
				condition = "";
			}
			extend = transition[i].getExtendedAttributes();
			fx = ((Action) actinfo.get(transition[i].getFrom())).getOffX();
			fy = ((Action) actinfo.get(transition[i].getFrom())).getOffY();
			tx = ((Action) actinfo.get(transition[i].getTo())).getOffX();
			ty = ((Action) actinfo.get(transition[i].getTo())).getOffY();
			try {
				lineExtend = extend.getMap();
				lineSet = lineExtend.keySet();
				lineIte = lineSet.iterator();
				while (lineIte.hasNext()) {
					lineKey = lineIte.next();
					if ("RoutingType".equals(lineKey)) {
						lineTy = lineType((String) lineExtend.get(lineKey));
					} else {
						if (extend != null) {
							lineList = extend.getList((String) lineKey);
							for (int k = 0; k < lineList.size(); k++) {
								if (extendContent == "") {
									extendContent = lineKey + "&"
											+ lineList.get(k);
								} else {
									extendContent += "," + lineKey + "&"
											+ lineList.get(k);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e + "4");
			}
			if ("beenLine".equals(lineTy)) {
				lineString += fetchbeenLine(id, name, falseName, extendContent,
						depict, fx, fy, tx, ty, condition);
			} else {
				lineString += fetchZLine(id, name, falseName, extendContent,
						depict, fx, fy, tx, ty, condition);
			}
		}
		return lineString;
	}

	/**
	 * 画直线
	 * 
	 * @param id
	 *            轨迹Id
	 * @param name
	 *            轨迹名称
	 * @param falseName
	 *            画图片需要的名称
	 * @param extendContent
	 *            轨迹的扩展属性
	 * @param depict
	 *            轨迹的描述
	 * @param fx
	 *            轨迹开始点的X坐标
	 * @param fy
	 *            轨迹开始点的Y坐标
	 * @param tx
	 *            轨迹结束点的X坐标
	 * @param ty
	 *            轨迹结束点的Y坐标
	 * @return
	 */
	public String fetchbeenLine(String id, String name, String falseName,
			String extendContent, String depict, String fx, String fy,
			String tx, String ty, String condition) {
		String lineStr = "";

		int[] linex = adjustXOffset(fx, tx, fy, ty);
		lineStr = "<div id='lineId' start='null' end='null' name='"
				+ falseName
				+ "' trueName ='"
				+ name
				+ "' extendContent='"
				+ extendContent
				+ "' lineTrueId='"
				+ id
				+ "' depict='"
				+ depict
				+ "' condition='"
				+ condition
				+ "' type='beenLine'>"
				+ "<v:line id='work"
				+ falseName
				+ "' style='Z-INDEX:0;POSITION:absolute;cursor:hand;' from='"
				+ (linex[0])
				+ "px,"
				+ linex[2]
				+ "px' to='"
				+ (linex[1])
				+ "px,"
				+ linex[3]
				+ "px' strokecolor='blue'><v:Stroke endarrow='classic'/></v:line>"
				+ "</div>\n";
		return lineStr;
	}

	private int[] adjustXOffset(String fx, String tx, String fy, String ty) {
		int realfx = Integer.parseInt(fx);
		int realtx = Integer.parseInt(tx);
		int realfy = Integer.parseInt(fy) - off;
		int realty = Integer.parseInt(ty) - off;

		int len = Math.abs(realtx - realfx);
		if (len < 80) {
			if (realfy < realty) {
				realty -= 25;
			}else{
				realty+= 35;
			}
			realfx += 35;
			realtx += 35;

		} else {

			if (realfx < realtx) {

				realfx += 35;
				realtx -= 10;

			} else {
				realfx += 35;
				realtx += 85;

			}
		}

		return new int[] { realfx, realtx, realfy, realty };

	}

	/**
	 * 画折线
	 * 
	 * @param id
	 *            轨迹Id
	 * @param name
	 *            轨迹名称
	 * @param falseName
	 *            画图片需要的名称
	 * @param extendContent
	 *            轨迹的扩展属性
	 * @param depict
	 *            轨迹的描述
	 * @param fx
	 *            轨迹开始点的X坐标
	 * @param fy
	 *            轨迹开始点的Y坐标
	 * @param tx
	 *            轨迹结束点的X坐标
	 * @param ty
	 *            轨迹结束点的Y坐标
	 * @return
	 */
	public String fetchZLine(String id, String name, String falseName,
			String extendContent, String depict, String fx, String fy,
			String tx, String ty, String condition) {
		String lineStr = "";
		int lineFY = Integer.parseInt(fy) + trackOff;
		int lineTY = Integer.parseInt(ty) + trackOff;
		lineStr = "<div id='lineId' start='null' end='null' name='"
				+ falseName
				+ "' trueName ='"
				+ name
				+ "' extendContent='"
				+ extendContent
				+ "' lineTrueId='"
				+ id
				+ "'; depict='"
				+ depict
				+ "'; condition='"
				+ condition
				+ "' type='ZLine'>"
				+ "<v:line id='work"
				+ falseName
				+ "' style='Z-INDEX:0;POSITION:absolute;cursor:hand;' from='"
				+ (Integer.parseInt(fx) + 55)
				+ "px,"
				+ (lineFY - off)
				+ "px' to='"
				+ (Integer.parseInt(fx) + 85)
				+ "px,"
				+ (lineFY - off)
				+ "px' strokecolor='red'></v:line>"
				+ "<v:line style=\"Z-INDEX:0;POSITION:absolute;cursor:hand;\" from=\""
				+ (Integer.parseInt(fx) + 85)
				+ "px,"
				+ (lineFY - off)
				+ "px\" to=\""
				+ (Integer.parseInt(fx) + 85)
				+ "px,"
				+ (lineTY - off)
				+ "px\" strokecolor=\"red\"></v:line>"
				+ "<v:line style=\"Z-INDEX:0;POSITION:absolute;cursor:hand;\" from=\""
				+ (Integer.parseInt(fx) + 85)
				+ "px,"
				+ (lineTY - off)
				+ "px\" to=\""
				+ (Integer.parseInt(tx) - 10)
				+ "px,"
				+ (lineTY - off)
				+ "px\" strokecolor=\"blue\"><v:Stroke endarrow=\'classic\'/></v:line>"
				+ "</div>\n";
		return lineStr;
	}

	/**
	 * 获得轨迹的类型
	 * 
	 * @param type
	 * @return
	 */
	public String lineType(String type) {
		String lineType = "";
		if ("SIMPLEROUTING".equals(type)) {
			lineType = "ZLine";
		}
		if ("NOROUTING".equals(type)) {
			lineType = "beenLine";
		}
		return lineType;
	}

	/**
	 * 获得开始图片，结束图片，还有与之相连的线；
	 * 
	 * @return
	 */
	public String Icon(Map actinfo,WorkflowProcess proc) {
		String extendStr = "";
		String IconLine = "";
		String name = "";
		String fx;
		String fy;
		String tx;
		String ty;
		Object extendKey;
		ExtendedAttributes extend = proc.getExtendedAttributes();
		try {
			Map extendMap = extend.getMap();
			Set extendSet = extendMap.keySet();
			Iterator ierExtend = extendSet.iterator();
			while (ierExtend.hasNext()) {
				name = "";
				fx = "";
				fy = "";
				tx = "";
				ty = "";
				extendKey = ierExtend.next();
				if ("StartOfWorkflow".equals(extendKey)) {
					if (extend != null) {
						List startlist = extend.getList("StartOfWorkflow");
						int startlength = startlist.size();
						for (int i = 0; i < startlength; i++) {
							String[] startIcon = ((String) startlist.get(i))
									.split(";");
							extendStr += startIcon(startIcon[1], startIcon[2],
									startIcon[3], startIcon[4]);
							fx = startIcon[2];
							fy = startIcon[3];
							tx = ((Action) actinfo.get(startIcon[1])).getOffX();
							ty = ((Action) actinfo.get(startIcon[1])).getOffY();
							name = "start_" + startIcon[1] + "->"
									+ startIcon[1];
							if ("NOROUTING".equals(startIcon[4])) {
								IconLine += fetchbeenLine("", "", name, "", "",
										fx, fy, tx, ty, "");
							} else if ("SIMPLEROUTING".equals(startIcon[4])) {
								IconLine += fetchZLine("", "", name, "", "",
										fx, fy, tx, ty, "");
							}
						}
					}

				} else if ("EndOfWorkflow".equals(extendKey)) {
					if (extend != null) {
						List endlist = extend.getList("EndOfWorkflow");
						for (int i = 0; i < endlist.size(); i++) {
							String[] endIcon = ((String) endlist.get(i))
									.split(";");
							extendStr += endIcon(endIcon[1], endIcon[2],
									endIcon[3], endIcon[4]);
							fx = ((Action) actinfo.get(endIcon[1])).getOffX();
							fy = ((Action) actinfo.get(endIcon[1])).getOffY();
							tx = endIcon[2];
							ty = endIcon[3];

							name = endIcon[1] + "->" + "end_" + endIcon[1];
							if ("NOROUTING".equals(endIcon[4])) {
								IconLine += fetchbeenLine("", "", name, "", "",
										fx, fy, tx, ty, "");
							} else if ("SIMPLEROUTING".equals(endIcon[4])) {
								IconLine += fetchZLine("", "", name, "", "",
										fx, fy, tx, ty, "");
							}
						}
					}

				}
			}

		} catch (Exception e) {
			System.out.println(e + "3");
		}
		return IconLine + extendStr;
	}

	/**
	 * 开始图片
	 * 
	 * @param actionId
	 * @param fx
	 * @param fy
	 * @param lineType
	 * @return
	 */
	public String startIcon(String actionId, String fx, String fy,
			String lineType) {
		String startIconStr = "";
		int iconTop = Integer.parseInt(fy) + trackOff;
		String tracklink = actionId + "&" + lineType(lineType) + ",";
		startIconStr = "<v:image id=\"start\" name=\"start_"
				+ actionId
				+ "\"  isLink=\"true\"  forward=\"null\" tracklink=\""
				+ tracklink
				+ "\" style=\"POSITION:absolute;Z-INDEX:1;LEFT:"
				+ fx
				+ "px;TOP:"
				+ iconTop
				+ "px;width:85;height:55;\" fillcolor=\"#007FFF\">"
				+ "<v:Textbox name =\"textbox\" class=startIcon print=\"t\" inset=\"1pt,1pt,1pt,1pt\"></v:Textbox>"
				+ "<input id=\"work2start_" + actionId + "\" type=\"hidden\">"
				+ "</v:image>\n";
		return startIconStr;
	}

	/**
	 * 结束图片
	 * 
	 * @param actionId
	 * @param tx
	 * @param ty
	 * @param lineType
	 * @return
	 */
	public String endIcon(String actionId, String tx, String ty, String lineType) {
		String endIconStr = "";
		int iconTop = Integer.parseInt(ty) + trackOff;
		String forward = actionId + "&" + lineType(lineType) + ",";
		endIconStr = "<v:image id=\"end\" name=\"end_"
				+ actionId
				+ "\"  isLink=\"true\"  forward=\""
				+ forward
				+ "\" tracklink=\"null\" style=\"POSITION:absolute;Z-INDEX:1;LEFT:"
				+ tx
				+ "px;TOP:"
				+ iconTop
				+ "px;width:85;height:55;\"fillcolor=\"#FFFF55\">"
				+ "<v:Textbox name =\"textbox\" class=endIcon print=\"t\" inset=\"1pt,1pt,1pt,1pt\"></v:Textbox>"
				+ "<input id=\"work2end_" + actionId + "\" type=\"hidden\">"
				+ "</v:image>\n";
		return endIconStr;
	}

	/***************************************************************************
	 * 与图片相连的线
	 * 
	 * @param actionId
	 * @param type
	 * @return
	 */
	public String iconLineType(String actionId, String type,WorkflowProcess proc) {
		String iconLineStr = "";
		ExtendedAttributes extend = proc.getExtendedAttributes();
		try {
			Map extendMap = extend.getMap();
			Set extendSet = extendMap.keySet();
			Iterator ierExtend = extendSet.iterator();
			Object extendKey;
			while (ierExtend.hasNext()) {
				extendKey = ierExtend.next();
				if (("StartOfWorkflow".equals(extendKey))
						&& ("start".equals(type))) {
					if (extend != null) {
						List startOfWorkFlow = extend
								.getList("StartOfWorkflow");
						for (int i = 0; i < startOfWorkFlow.size(); i++) {
							String[] startIcon = ((String) startOfWorkFlow
									.get(i)).split(";");
							String activityId = startIcon[1];
							if (activityId.equals(actionId)) {
								iconLineStr = lineType(startIcon[4]);
								break;
							}
						}
					}
				}
				if (("EndOfWorkflow".equals(extendKey)) && ("end".equals(type))) {
					if (extend != null) {
						List activityList = extend.getList("EndOfWorkflow");
						for (int i = 0; i < activityList.size(); i++) {
							String[] endIcon = ((String) activityList.get(i))
									.split(";");
							String activityId = endIcon[1];
							if (activityId.equals(actionId)) {
								iconLineStr = lineType(endIcon[4]);
								break;
							}

						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e + "2");
		}
		return iconLineStr;
	}

	/**
	 * 获得流程相关数据
	 * 
	 * @return
	 */
	public String relationData(WorkflowProcess proc) {
		String datafile = "";
		String id, name, initialValue, extend = "", description;
		int datalength;
		DataField[] relationData = proc.getDataField();
		int length = relationData.length;
		for (int i = 0; i < length; i++) {
			extend = "";
			id = relationData[i].getId();
			name = relationData[i].getName();
			initialValue = relationData[i].getInitialValue();
			datalength = relationData[i].getLength();
			description = relationData[i].getDescription();
			ExtendedAttributes dataExtend = relationData[i]
					.getExtendedAttributes();
			if (dataExtend != null) {
				try {
					Map dataMap = dataExtend.getMap();
					Set dataSet = dataMap.keySet();
					Iterator iter = dataSet.iterator();
					String key;
					List list;
					while (iter.hasNext()) {
						key = (String) iter.next();
						list = dataExtend.getList(key);
						for (int k = 0; k < list.size(); k++) {
							extend += key + "&" + list.get(k) + "|";
						}
					}
				} catch (Exception e) {
					System.out.println(e + "1");
				}
			}
			datafile += id + "," + name + "," + initialValue + "," + datalength
					+ "," + description + "," + extend + ";";
		}

		return "<input id=\"xpdlRelationData\" type=\"hidden\" value=\""
				+ datafile + "\">";
	}

	/**
	 * 获得流程的基本信息
	 * 
	 * @return
	 */
	public String processHeadAttribute(WorkflowProcess proc) {
		String value = "";
		String extend = "";
		String processName = "";
		String processId = "";
		String processCreateDate = "";
		processId = proc.getId();
		processName = proc.getName();
		Date createDate = proc.getProcessHeader().getCreated();
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		processCreateDate = dateFormat.format(createDate);
		ExtendedAttributes workflowExtend = proc.getExtendedAttributes();
		if (workflowExtend != null) {
			try {
				Map workflowMap = workflowExtend.getMap();

				Set workflowSet = workflowMap.keySet();
				Iterator workflowTter = workflowSet.iterator();
				String name;

				while (workflowTter.hasNext()) {

					name = (String) workflowTter.next();
					if (!name.equals("StartOfWorkflow")
							&& !name.equals("EndOfWorkflow")
							&& !name.equals("ParticipantVisualOrder")) {
						List workflowList = workflowExtend.getList(name);
						int listLength = workflowList.size();
						for (int k = 0; k < listLength; k++) {
							extend += name + "&" + workflowList.get(k) + "|";
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		value = processId + "," + processName + "," + extend + ","
				+ processCreateDate;
		return "<input id=\"updataProcess\" name=\"updataProcessAttribute\" type=\"hidden\" value=\""
				+ value + "\">";
	}

}