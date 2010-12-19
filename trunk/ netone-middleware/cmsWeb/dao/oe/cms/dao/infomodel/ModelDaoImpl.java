package oe.cms.dao.infomodel;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import oe.cms.cfg.TCmsInfomodel;
import oe.cms.datasource.XMLParser;
import oe.frame.orm.Ormer;
import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.DbTools;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.db.UmsProtectedobject;
import oe.security3a.sso.Security;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ModelDaoImpl implements ModelDao {

	private Log _log = LogFactory.getLog(ModelDaoImpl.class);

	/**
	 * A水平空间，排名更新时间
	 */
	private String blogLevelARichSeriTime;

	/**
	 * B水平空间，排名更新时间
	 */
	private String blogLevelBRichSeriTime;

	/**
	 * C水平空间，排名更新时间
	 */
	private String blogLevelCRichSeriTime;

	/**
	 * 进级A水平空间时间
	 */
	private String comeAlevelTime;

	/**
	 * 进级B水平空间时间
	 */
	private String comeBlevelTime;

	/**
	 * 退到B水平空间时间
	 */
	private String backBlevelTime;

	/**
	 * 退到C水平空间时间
	 */
	private String backClevelTime;

	/**
	 * 进级A水平空间的个数
	 */
	private String comeAlevelNum;

	/**
	 * 在进级A水平空间时间里,需要淘汰到B级空间的倒数空间个数
	 */
	private String backBlevelNum;

	/**
	 * 进级B水平空间的个数
	 */
	private String comeBlevelNum;

	/**
	 * 在进级B水平空间时间里,需要淘汰到C级空间的倒数空间个数
	 */
	private String backClevelNum;

	// 评审冠军的时间
	private String winnerTime;

	// 缓存查询结果
	static Map map = new HashMap();

	// 缓存查询个人名次
	static Map map2 = new HashMap();

	// 缓存查询个人支持率
	static Map map3 = new HashMap();

	// 缓村最新创建的空间
	static List newCreate = new ArrayList();

	static List newModify = new ArrayList();

	public boolean create(TCmsInfomodel model, String belongto) {
		Ormer orm = OrmerEntry.fetchOrmer();
		model.setAccessmode("1");
		model.setHit(new Long(0));
		model.setLevels(RichLevel._LEVEL_C);
		model.setExtendattribute("100.0%");
		model.setInfoxml(XMLParser._XML_HEAD + XMLParser._DEFAULT_BODY);
		model.setModifytime((new Timestamp(System.currentTimeMillis())
				.toString()));
		String orinaturalname = model.getNaturalname().toUpperCase();
		model.setNaturalname(belongto + "." + orinaturalname);
		orm.fetchSerializer().create(model); // 新增模型

		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");

			UmsProtectedobject upo = new UmsProtectedobject();
			upo.setName(model.getModelname());
			upo.setNaturalname(orinaturalname);
			upo.setObjecttype(ProtectedObjectReference._OBJ_TYPE_PAGEGROUP);
			upo.setInclusion(ProtectedObjectReference._OBJ_INCLUSTION_NO);
			upo.setExtendattribute("" + model.getModelid());

			rsrmi.addResource(upo, belongto);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public boolean update(TCmsInfomodel model) {
		Ormer orm = OrmerEntry.fetchOrmer();
		model.setModifytime((new Timestamp(System.currentTimeMillis())
				.toString()));
		return orm.fetchSerializer().update(model);
	}

	public boolean delete(String id, String participant) {
		Ormer orm = OrmerEntry.fetchOrmer();
		TCmsInfomodel obj = (TCmsInfomodel) orm.fetchQuerister().loadObject(
				TCmsInfomodel.class, new Long(id));
		obj.setParticipant(participant);
		return orm.fetchSerializer().drop(obj); // 删除
	}

	public TCmsInfomodel view(String id) {
		Ormer orm = OrmerEntry.fetchOrmer();
		return (TCmsInfomodel) orm.fetchQuerister().loadObject(
				TCmsInfomodel.class, new Long(id));
	}

	public boolean checkExist(String id) {
		Ormer orm = OrmerEntry.fetchOrmer();
		try {
			orm.fetchQuerister().loadObject(TCmsInfomodel.class, new Long(id));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 返回视图
	 * 
	 * @param level
	 * @param from
	 * @param to
	 * @return
	 */
	public List richview(String level, int from, int to) {
		if (!map.containsKey(level)) {
			initview(level);
		}
		List list = (List) map.get(level);
		if (from < 0) {
			from = 0;
			to = 20;
		}
		if (to > list.size()) {
			to = list.size();
		}
		List listRet = new ArrayList();
		for (int i = from; i < to; i++) {
			listRet.add(list.get(i));
		}

		return listRet;

	}

	public int totalRichview(String level) {
		if (!map.containsKey(level)) {
			initview(level);
		}
		List list = (List) map.get(level);
		return list.size();
	}

	/**
	 * 处于性能考虑，投票支持直接在对象中增加一个值， 并且没有马上持久化
	 */
	public void supportThis(String level, int index) {
		List list = (List) map.get(level);
		TCmsInfomodel model = (TCmsInfomodel) list.get(index);
		long newHit = model.getHit().longValue() + 1;
		model.setHit(new Long(newHit));
	}

	public void serialRich(String level) {
		List list = (List) map.get(level);
		if (list == null) {
			return;
		}
		_log.info("持久化排名信息");
		Connection con = null;
		try {
			con = OrmerEntry.fetchDS().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			TCmsInfomodel ifm = (TCmsInfomodel) itr.next();
			String sql = "update t_cms_infomodel set hit=" + ifm.getHit()
					+ ",levels='" + ifm.getLevels() + "' where modelid="
					+ ifm.getModelid();
			_log.debug(sql);
			DbTools.execute(con, sql);

		}
		_log.info("完成");
	}

	public List fetchList(String level) {
		// TODO Auto-generated method stub
		return (List) map.get(level);
	}

	private void initviewCore(String level, String condition) {
		Ormer orm = OrmerEntry.fetchOrmer();
		TCmsInfomodel tcm = new TCmsInfomodel();
		tcm.setLevels(level);
		List list = orm.fetchQuerister().queryObjects(tcm, null, condition);

		// UserDao.userViewRichx(list, "userinfo");
		map.put(level, list);

		Map levelthis = new HashMap();
		Map levelHit = new HashMap();

		int i = 0;
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			TCmsInfomodel tcmPre = (TCmsInfomodel) itr.next();
			String id = tcmPre.getModelid().toString();

			levelthis.put(id, "" + i++);
			levelHit.put(id, tcmPre.getHit() == null ? "0" : tcmPre.getHit()
					.toString());
		}
		map2.put(level, levelthis);
		map3.put(level, levelHit);

	}

	public void initview(String level) {
		initviewCore(level, " order by hit desc");
	}

	public void initview() {
		for (int i = 0; i < RichLevel._LEVEL_TOTAL.length; i++) {
			initview(RichLevel._LEVEL_TOTAL[i]);
		}
		initviewCore(RichLevel._LEVEL_WINNER, " order by wintime desc");
	}

	public String getBackBlevelNum() {
		return backBlevelNum;
	}

	public void setBackBlevelNum(String backBlevelNum) {
		this.backBlevelNum = backBlevelNum;
	}

	public String getBackClevelNum() {
		return backClevelNum;
	}

	public void setBackClevelNum(String backClevelNum) {
		this.backClevelNum = backClevelNum;
	}

	public String getBlogLevelARichSeriTime() {
		return blogLevelARichSeriTime;
	}

	public void setBlogLevelARichSeriTime(String blogLevelARichSeriTime) {
		this.blogLevelARichSeriTime = blogLevelARichSeriTime;
	}

	public String getBlogLevelBRichSeriTime() {
		return blogLevelBRichSeriTime;
	}

	public void setBlogLevelBRichSeriTime(String blogLevelBRichSeriTime) {
		this.blogLevelBRichSeriTime = blogLevelBRichSeriTime;
	}

	public String getBlogLevelCRichSeriTime() {
		return blogLevelCRichSeriTime;
	}

	public void setBlogLevelCRichSeriTime(String blogLevelCRichSeriTime) {
		this.blogLevelCRichSeriTime = blogLevelCRichSeriTime;
	}

	public String getComeAlevelNum() {
		return comeAlevelNum;
	}

	public void setComeAlevelNum(String comeAlevelNum) {
		this.comeAlevelNum = comeAlevelNum;
	}

	public String getComeAlevelTime() {
		return comeAlevelTime;
	}

	public void setComeAlevelTime(String comeAlevelTime) {
		this.comeAlevelTime = comeAlevelTime;
	}

	public String getComeBlevelNum() {
		return comeBlevelNum;
	}

	public void setComeBlevelNum(String comeBlevelNum) {
		this.comeBlevelNum = comeBlevelNum;
	}

	public String fetchblogLevelARichSeriTime() {
		// TODO Auto-generated method stub
		return blogLevelARichSeriTime;
	}

	public String fetchblogLevelBRichSeriTime() {
		// TODO Auto-generated method stub
		return blogLevelBRichSeriTime;
	}

	public String fetchblogLevelCRichSeriTime() {
		// TODO Auto-generated method stub
		return blogLevelCRichSeriTime;
	}

	public String fetchcomeAlevelTime() {
		// TODO Auto-generated method stub
		return comeAlevelTime;
	}

	public String fetchcomeAlevelNum() {
		// TODO Auto-generated method stub
		return comeAlevelNum;
	}

	public String fetchbackBlevelNum() {
		// TODO Auto-generated method stub
		return backBlevelNum;
	}

	public String fetchcomeBlevelNum() {
		// TODO Auto-generated method stub
		return comeBlevelNum;
	}

	public String fetchbackClevelNum() {
		// TODO Auto-generated method stub
		return backClevelNum;
	}

	public String getBackBlevelTime() {
		return backBlevelTime;
	}

	public void setBackBlevelTime(String backBlevelTime) {
		this.backBlevelTime = backBlevelTime;
	}

	public String getBackClevelTime() {
		return backClevelTime;
	}

	public void setBackClevelTime(String backClevelTime) {
		this.backClevelTime = backClevelTime;
	}

	public String getComeBlevelTime() {
		return comeBlevelTime;
	}

	public void setComeBlevelTime(String comeBlevelTime) {
		this.comeBlevelTime = comeBlevelTime;
	}

	public String fetchcomeBlevelTime() {
		// TODO Auto-generated method stub
		return comeBlevelTime;
	}

	public String fetchbackBlevelTime() {
		// TODO Auto-generated method stub
		return backBlevelTime;
	}

	public String fetchbackClevelTime() {
		// TODO Auto-generated method stub
		return backClevelTime;
	}

	public int fetchOrder(String level, String modelid) {
		Map order = (Map) map2.get(level);
		if (order == null) {
			this.initview(level);
		}
		String orderStr = (String) order.get(modelid);
		int ordervalue = 0;
		try {
			ordervalue = Integer.parseInt(orderStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ordervalue;
	}

	public String fetchHitNum(String level, String modelid) {
		Map map = (Map) map3.get(level);
		if (map == null) {
			this.initview(level);
		}
		if (map.containsKey(modelid)) {
			return map.get(modelid).toString();
		} else {
			for (int i = 0; i < RichLevel._LEVEL_TOTAL.length; i++) {
				Map mapPre = (Map) map3.get(RichLevel._LEVEL_TOTAL[i]);
				if (mapPre == null) {
					this.initview(RichLevel._LEVEL_TOTAL[i]);
				}
				if (mapPre.containsKey(modelid)) {
					return mapPre.get(modelid).toString();
				}
			}
		}
		return "0";
	}

	// public String fetchOrderIndex(String level, String modelid) {
	// Map map = (Map) map2.get(level);
	// if (map == null) {
	// this.initview(level);
	// map = (Map) map2.get(level);
	// }
	// if (map.containsKey(modelid)) {
	// return map.get(modelid).toString();
	// } else {
	// for (int i = 0; i < RichLevel._LEVEL_TOTAL.length; i++) {
	// Map mapPre = (Map) map2.get(RichLevel._LEVEL_TOTAL[i]);
	// if (mapPre == null) {
	// this.initview(RichLevel._LEVEL_TOTAL[i]);
	// }
	// if (mapPre.containsKey(modelid)) {
	// return mapPre.get(modelid).toString();
	// }
	// }
	// }
	// return "-1";
	// }

	public TCmsInfomodel fetchWinner() {

		List list = (List) map.get(RichLevel._LEVEL_WINNER);
		if (list == null || list.size() < 1) {
			TCmsInfomodel infomodel = (TCmsInfomodel) OrmerEntry.fetchOrmer()
					.fetchQuerister().loadObject(TCmsInfomodel.class,
							new Long(1));
			List listx = new ArrayList();
			listx.add(infomodel);
			// UserDao.userViewRichx(listx, "userinfo");
			return (TCmsInfomodel) listx.get(0);
		}
		return (TCmsInfomodel) list.get(0);

	}

	public String getWinnerTime() {
		return winnerTime;
	}

	public void setWinnerTime(String winnerTime) {
		this.winnerTime = winnerTime;
	}

	public String fetchWinnerTime() {
		return this.winnerTime;
	}

	public TCmsInfomodel viewByUser(String userid) {
		TCmsInfomodel usermodel = new TCmsInfomodel();
		usermodel.setParticipant(userid);
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
				usermodel, null);
		if (list != null && list.size() > 0) {
			return (TCmsInfomodel) list.get(0);
		}
		return null;

	}

	public List listAllModel() {
		TCmsInfomodel infomodel = new TCmsInfomodel();
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(infomodel,
				null);

	}

	public TCmsInfomodel userinfoModel(String userid, String modelx,
			HttpServletRequest request) {
		TCmsInfomodel usermodel = null;
		if ("ok".equals(modelx)) {
			usermodel = (TCmsInfomodel) OrmerEntry.fetchOrmer()
					.fetchQuerister().loadObject(TCmsInfomodel.class,
							new Long(userid));

		} else {
			if (userid != null && !userid.equals("")) {
				usermodel = viewByUser(userid);
			} else {
				usermodel = getUserInfoModel(request);
			}
		}
		String sytleinfox = usermodel.getUserid();
		if (sytleinfox != null || !sytleinfox.equals("")) {
			String styleinfo = StringUtils.substringBetween(sytleinfox, "[",
					"]");
			usermodel.setStyleinfo(styleinfo);
		}
		return usermodel;
	}

	private TCmsInfomodel getUserInfoModel(HttpServletRequest req) {
		Ormer ormer = OrmerEntry.fetchOrmer();

		TCmsInfomodel queryicm = new TCmsInfomodel();
		Security ser = new Security(req);
		queryicm.setParticipant(ser.getUserLoginName());
		List list = ormer.fetchQuerister().queryObjects(queryicm, null);
		TCmsInfomodel usermodel = null;
		if (list.size() > 0) {
			usermodel = (TCmsInfomodel) list.get(0);
		} else {
			TCmsInfomodel defmodel = (TCmsInfomodel) ormer.fetchQuerister()
					.loadObject(TCmsInfomodel.class, new Long(1));

			usermodel = new TCmsInfomodel();
			usermodel.setInfoxml(defmodel.getInfoxml());

			usermodel.setAccessmode("1");
			ormer.fetchSerializer().create(usermodel);
		}
		return usermodel;
	}

	public boolean delete(String participant) {
		TCmsInfomodel model = new TCmsInfomodel();
		model.setParticipant(participant);
		List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
				model, null);
		OrmerEntry.fetchOrmer().fetchSerializer().drop(list);
		return true;
	}

	public void initNewCreateView() {
		List list = initviewNewCore(" order by createdtime desc", 20);
		this.newCreate.addAll(list);
	}

	public void initNewModifyView() {
		List list = initviewNewCore(" order by modifytime desc", 20);
		this.newModify.addAll(list);
	}

	private List initviewNewCore(String condition, int num) {
		Ormer orm = OrmerEntry.fetchOrmer();
		TCmsInfomodel tcm = new TCmsInfomodel();
		tcm.setLevels(RichLevel._LEVEL_C);
		List list = orm.fetchQuerister().queryObjects(tcm, null, 0, num,
				condition);
		Map cacheListKey = (Map) map2.get(RichLevel._LEVEL_C);
		List cacheList = (List) map.get(RichLevel._LEVEL_C);
		List listValue = new ArrayList();
		for (Iterator itr = list.iterator(); itr.hasNext();) {
			TCmsInfomodel tcmPre = (TCmsInfomodel) itr.next();
			String modelid = tcmPre.getModelid().toString();
			String index = (String) cacheListKey.get(modelid);
			listValue.add(cacheList.get(Integer.parseInt(index)));
		}
		return listValue;
	}

	public List richViewByCreateTime() {
		// TODO Auto-generated method stub
		return this.newCreate;
	}

	public List richViewByModifyTime() {
		// TODO Auto-generated method stub
		return this.newModify;
	}

}
