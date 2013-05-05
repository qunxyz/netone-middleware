package oe.cav.bean.logic.dy.bus;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import oe.cav.bean.logic.bus.BussDao;
import oe.cav.bean.logic.bus.FormEntry;
import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.column.TCsColumn;
import oe.cav.bean.logic.dy.bus.reference.BussDaoReference;
import oe.cav.bean.logic.form.FormDao;
import oe.cav.bean.logic.form.TCsForm;
import oe.cav.bean.logic.tools.ConditionBuilder;
import oe.cav.bean.logic.tools.DyObj;
import oe.cav.bean.logic.tools.DyObjFromXml;
import oe.cav.bean.logic.tools.SQLTools;
import oe.cav.bean.logic.tools.XmlPools;
import oe.frame.web.WebCache;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.Clerk;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 通过传入TCsBus对象来构造BussObj对象， 再根据BussObj对象来完成数据库表的增删改查操作， 同时增删改查操作都是通过预制SQL来完成
 * 
 * @author admin
 * 
 */
public class BussDaoImpl implements BussDao {

	static Log log = LogFactory.getLog(BussDaoImpl.class);

	final String _PK = "lsh";

	private DyObjFromXml dyObjFromXml;

	FormDao formDao;

	public boolean create(TCsBus bus) {

		if (bus == null) {
			log.error("输入对象TCsBus为空");
			return false;
		}
		List list = new ArrayList();
		list.add(bus);
		return creates(list);
	}

	public boolean creates(List list) {
		boolean rs = false;
		if (list == null || list.size() == 0) {
			log.error("输入对象TCsBus数组为空");
			return false;
		} else {
			TCsBus bustmp = (TCsBus) list.get(0);
			String formcode = bustmp.getFormcode();
			if (formcode == null || formcode.equals("")) {
				log.error(formcode + " 表单不存在");
				return false;
			} else {
				DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode)
						.toString());
				if (dfo == null) {
					log.error("未找到DyObj对象");
					throw new RuntimeException("未找到DyObj对象");
				}

				TCsForm tcf = dfo.getFrom();
				String tablename = tcf.getDescription();
				if (tablename == null || tablename.equals("")) {
					log.error("未找到表名");
					throw new RuntimeException("未找到表名");
				}

				String sql = BussDaoTools.createSQL(formcode, tablename, dfo
						.getColumn());

				Connection conn = null;
				PreparedStatement ps = null;
				List columnlist = dfo.getColumn();
				try {
					String systemid = dfo.getSystemid();
					conn = SQLTools.getConn(systemid);
					conn.setAutoCommit(false);
					ps = conn.prepareStatement(sql);
					for (Iterator iterx = list.iterator(); iterx.hasNext();) {
						TCsBus element = (TCsBus) iterx.next();
						// 创建BussObj对象属性
						Map columnidvalue = new LinkedHashMap();
						// // 处理Portal相关的字段
						// BussDaoCoreTools.addToResourcePortal(element, dfo
						// .getColumn(), tablename, formcode);
						// // 添加 Fck的字段
						// BussDaoCoreTools.addToResourceFck(element, dfo
						// .getColumn(), tablename, formcode);
						// 检查字段属性是否正确
						BussDaoTools.columnTypeAndValueAvail(columnlist, tcf,
								element, columnidvalue, false);

						int i = 1;
						// 配置参数对应的值
						for (Iterator iter = columnidvalue.keySet().iterator(); iter
								.hasNext();) {
							String columnid = (String) iter.next();
							log.debug("set value:" + columnid
									+ columnidvalue.get(columnid));
							ps.setObject(i, columnidvalue.get(columnid));
							i++;
						}

						// 执行添加
						ps.executeUpdate();
					}
					conn.commit();
					rs = true;

				} catch (Exception e) {
					// 如果创建出错 那么将lsh设置为空表示失败
					for (Iterator iterx = list.iterator(); iterx.hasNext();) {
						TCsBus element = (TCsBus) iterx.next();
						element.setLsh(null);
					}

					try {
						conn.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					e.printStackTrace();
					return false;
				} finally {
					try {
						conn.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

			}

		}
		return rs;
	}

	public boolean update(TCsBus bus) {

		if (bus == null) {
			log.error("输入对象TCsBus为空");
			return false;
		}
		boolean rs = false;

		TCsBus bustmp = bus;
		String formcode = bustmp.getFormcode();
		// 从TCsBus中读取数据库名和文件名作为公用属性
		DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode).toString());
		if (dfo == null) {
			log.error("未找到DyObj对象");
			throw new RuntimeException("未找到DyObj对象");
		}

		TCsForm tcf = dfo.getFrom();
		String description = tcf.getDescription();
		if (description.equals("")) {
			log.error("未找到表名");
			throw new RuntimeException("未找到表名");
		}

		List columnlist = dfo.getColumn();

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String ds = dfo.getSystemid();
			conn = SQLTools.getConn(ds);

			TCsBus element = bus;
			// 创建BussObj对象属性
			Map columnidvalue = new LinkedHashMap();

			List availcolumnlist = BussDaoTools.columnTypeAndValueAvail(
					columnlist, tcf, element, columnidvalue, true);

			// 需要处理掉 participant
			// 字段(因为表单修改中不可以修改该字段，如果被修改会导致原始创建者丢失，这将会导致安全识别出现问题)
			TCsColumn objectRemove = null;
			for (Iterator iterator = availcolumnlist.iterator(); iterator
					.hasNext();) {
				TCsColumn object = (TCsColumn) iterator.next();
				if (object.getColumnid().equalsIgnoreCase("participant")) {
					objectRemove = object;
					break;
				}
			}
			if (objectRemove != null) {
				availcolumnlist.remove(objectRemove);
				columnidvalue.remove(objectRemove.getColumnid().toLowerCase());
			}
			// ////////////////////////////////////////

			// 创建SQL
			String sql = BussDaoTools.updateSql(bustmp.getFormcode(),
					description, availcolumnlist, _PK);
			ps = conn.prepareStatement(sql);
			log.debug("update:" + sql);

			int index = 1;
			// 配置非主键字段对应的值
			for (Iterator iter = columnidvalue.keySet().iterator(); iter
					.hasNext();) {
				String columnid = (String) iter.next();
				log.debug("update_column:" + columnid + " index:" + index);

				ps.setObject(index, columnidvalue.get(columnid));
				index++;

			}
			ps.setObject(index, columnidvalue.get(_PK));
			// 执行修改
			ps.executeUpdate();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return false;

	}

	public boolean updates(List list) {
		boolean rs = false;
		if (list == null || list.size() == 0) {
			log.error("输入对象TCsBus数组为空");
			return false;
		} else {
			TCsBus bustmp = (TCsBus) list.get(0);
			String formcode = bustmp.getFormcode();
			// 从TCsBus中读取数据库名和文件名作为公用属性
			DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode)
					.toString());
			if (dfo == null) {
				log.error("未找到DyObj对象");
				throw new RuntimeException("未找到DyObj对象");
			}

			TCsForm tcf = dfo.getFrom();
			String description = tcf.getDescription();
			if (description.equals("")) {
				log.error("未找到表名");
				throw new RuntimeException("未找到表名");
			}

			String sql = BussDaoTools.updateSql(bustmp.getFormcode(),
					description, dfo.getColumn(), _PK);

			Connection conn = null;
			PreparedStatement ps = null;
			List columnlist = dfo.getColumn();
			try {
				String ds = dfo.getSystemid();
				conn = SQLTools.getConn(ds);
				conn.setAutoCommit(false);
				ps = conn.prepareStatement(sql);
				log.debug("update:" + sql);
				for (Iterator iterx = list.iterator(); iterx.hasNext();) {
					TCsBus element = (TCsBus) iterx.next();
					// 创建BussObj对象属性
					Map columnidvalue = new LinkedHashMap();

					BussDaoTools.columnTypeAndValueAvail(columnlist, tcf,
							element, columnidvalue, false);

					// 需要处理掉 participant
					// 字段(因为表单修改中不可以修改该字段，如果被修改会导致原始创建者丢失，这将会导致安全识别出现问题)
					int participantIndex = 0;
					for (Iterator iterator = columnlist.iterator(); iterator
							.hasNext();) {
						TCsColumn object = (TCsColumn) iterator.next();
						if (object.getColumnid().equals("participant")) {
							break;
						}
						participantIndex++;
					}
					columnlist.remove(participantIndex);
					// ////////////////////////////////////////

					int index = 1;
					// 配置非主键字段对应的值
					for (Iterator iter = columnidvalue.keySet().iterator(); iter
							.hasNext();) {
						String columnid = (String) iter.next();
						log.debug("update_column:" + columnid + " index:"
								+ index);

						ps.setObject(index, columnidvalue.get(columnid));
						index++;

					}
					ps.setObject(index, columnidvalue.get(_PK));
					// 执行修改
					ps.executeUpdate();

				}
				conn.commit();
				rs = true;

			} catch (Exception e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return false;
			} finally {
				try {
					conn.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return rs;
	}

	public boolean drop(TCsBus bus) {
		if (bus == null) {
			log.error("输入对象TCsBus为空");
			return false;
		}
		List list = new ArrayList();
		list.add(bus);
		return drops(list);
	}

	public boolean drops(List list) {
		boolean rs = false;
		if (list == null || list.size() == 0) {
			log.error("输入对象TCsBus为空");
			return false;
		} else {
			TCsBus busTmp = (TCsBus) list.get(0);
			// 从TCsBus中读取数据库名和文件名作为公用属性
			String formcode = busTmp.getFormcode();
			// 从TCsBus中读取数据库名和文件名作为公用属性
			DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode)
					.toString());
			if (dfo == null) {
				log.error("未找到DyObj对象");
				throw new RuntimeException("未找到DyObj对象");
			}

			TCsForm tcf = dfo.getFrom();
			String description = tcf.getDescription();
			if (description.equals("")) {
				log.error("未找到表名");
				throw new RuntimeException("未找到表名");
			}

			String sql = BussDaoTools.dropSql(description, formcode, _PK);

			Connection conn = null;
			PreparedStatement ps = null;
			List columnlist = dfo.getColumn();
			try {
				String ds = dfo.getSystemid();
				conn = SQLTools.getConn(ds);
				conn.setAutoCommit(false);
				ps = conn.prepareStatement(sql);
				for (Iterator iterx = list.iterator(); iterx.hasNext();) {
					TCsBus element = (TCsBus) iterx.next();
					// 创建BussObj对象属性
					Map columnidvalue = new HashMap();

					// 检查字段属性是否正确
					BussDaoTools.columnTypeAndValueAvail(columnlist, tcf,
							element, columnidvalue, false);

					ps.setObject(1, columnidvalue.get(_PK));
					// 执行删除
					ps.executeUpdate();

				}
				conn.commit();
				rs = true;

			} catch (Exception e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return false;
			} finally {
				try {
					conn.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return rs;
	}

	/* key为单主键的值，主键字段为多个时不处理 */
	public TCsBus loadObject(String formcode, Serializable key) {
		if (formcode == null || formcode.equals("")) {
			log.error("未找到文件名");
			return null;
		} else {

			// 根据数据库名读取DyObj对象属性
			FormDao formdao = (FormDao) FormEntry.fetchBean("formDao");
			TCsForm form = formdao.loadObject(formcode);

			String description = form.getDescription();
			if (description == null || description.equals("")) {
				log.error("未找到表名");
				return null;
			}

			DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode)
					.toString());
			if (dfo == null) {
				log.error("未找到DyObj对象");
				return null;
			}

			// 开始查询

			StringBuffer sb = new StringBuffer();
			sb.append(BussDaoReference._SELECT + BussDaoReference._FROM
					+ description + BussDaoReference._WHERE + _PK + "= '" + key
					+ "'");
			log.debug(sb.toString());

			Connection conn = null;
			ResultSet rs = null;
			Statement st = null;
			try {
				String ds = dfo.getSystemid();
				conn = SQLTools.getConn(ds);
				st = conn.createStatement();
				rs = st.executeQuery(sb.toString());

				// 封装TCsBus对象
				TCsBus bus = new TCsBus();
				bus.setFormcode(formcode);
				while (rs.next()) {
					List columnlist = dfo.getColumn();
					for (Iterator iterator = columnlist.iterator(); iterator
							.hasNext();) {
						TCsColumn tcc = (TCsColumn) iterator.next();
						String columnid = tcc.getColumnid();
						String columncode = tcc.getColumncode();
						log.debug(columnid + "," + columncode);

						try {
							Object value = rs.getObject(columnid);
							if (value == null) {
								value = "";
							}
							columncode = columncode.toLowerCase();
							BeanUtils.setProperty(bus, columncode, value);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
							return null;
						} catch (InvocationTargetException e) {
							e.printStackTrace();
							return null;
						}
					}
				}
				return bus;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			} finally {
				try {
					conn.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public boolean export(TCsBus bus, String condition, String type,
			HttpServletResponse res) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean move(String formcode, String lsh, boolean up,
			String participant) {
		// TODO Auto-generated method stub
		return false;
	}

	public List queryObjects(TCsBus obj) {
		return queryObjects(obj, -1, -1, "");
	}

	private void permission(TCsBus obj) {
		String participant = obj.getParticipant();
		if (StringUtils.isEmpty(participant)) {
			return;
		}
		if("adminx".equals(participant)){
			return ;
		}
		try {
			ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
			CupmRmi cupm = (CupmRmi) RmiEntry.iv("cupm");
			
			UmsProtectedobject upo2=new UmsProtectedobject();
			upo2.setNaturalname("BUSSFORM.BUSSFORM.%");
			upo2.setExtendattribute(obj.getFormcode());
			Map map = new HashMap();
			map.put("naturalname", "like");

			List formlist = rs.fetchResource(upo2, map);
			if(formlist.size()==1){
				upo2=((UmsProtectedobject)formlist.get(0));
			}
			//检查是否有表单的管理权限
			boolean isAdmin=cupm.checkUserPermission("0000", participant, upo2.getNaturalname(), "15");
			System.out.println("is admin:"+isAdmin);
			if(isAdmin){
				//管理授权意味着该用户可以看到所有数据
				obj.setParticipant(null);
				return;
			}else{
				if("1".equals(upo2.getActive())){
					//如果表单的资源配置为 不共享模式activei=1，那么只有用户自己只能看到自己的数据,否则可以通过授权
					// 获取许可的数据
					obj.setParticipant("'"+obj.getParticipant()+"'");
					return;
				}
			}
			
			//需要所有的群组
			boolean alluserTeam = cupm.checkUserPermission("0000", participant,
					"SYSTEAM.SYSTEAM", "3");	
			// 需要所有的部门
			boolean alluserDept=cupm.checkUserPermission("0000", participant,
					"DEPT.DEPT", "3");	
			if(alluserTeam||alluserDept||participant.equals("adminx")){
				//如果授权SYSTEM.SYSTEAM给某个用户，那么意味着这个用户可以看所有用户的数据
				obj.setParticipant(null);
			}else{
				StringBuffer but = new StringBuffer("'" + participant + "'");
				//根据群组授权
				fetchTeamUser(but,rs,cupm,participant);
				//根据部门授权
				fetchDeptUser(but,rs,cupm,participant);
				// 根据所有所属的部门进行动态授权
				fetchDynamticDeptUser(but,rs,cupm,participant);
				obj.setParticipant(but.toString());
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void fetchTeamUser(StringBuffer but,ResourceRmi rs,CupmRmi cupm,String participant)throws Exception{
		List okTeam = new ArrayList();
		List sub = rs.subResourceByNaturalname("SYSTEAM.SYSTEAM");
		for (Iterator iterator = sub.iterator(); iterator.hasNext();) {
			UmsProtectedobject object = (UmsProtectedobject) iterator
					.next();
			String name = object.getNaturalname();
			boolean ok = cupm.checkUserPermission("0000", participant,
					name, "3");
			if (ok) {
				okTeam.add(name);
			}
		}
		

		for (Iterator iterator = okTeam.iterator(); iterator.hasNext();) {
			String object = (String) iterator.next();
			Clerk c1 = new Clerk();
			c1.setProvince("%" + object + "@%");
			Map map = new HashMap();
			map.put("major", "like");
			//后期queryObjectsClerk方法中加入cache来缓存 读取用户的性能
			List list = rs.queryObjectsClerk("0000", c1, map, 0, 1000);
			for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
				Clerk object2 = (Clerk) iterator2.next();
				but.append(",'" + object2.getDescription() + "'");
			}
		}
	}
	
	private void fetchDeptUser(StringBuffer but,ResourceRmi rs,CupmRmi cupm,String participant)throws Exception{
		List okTeam = new ArrayList();
		List sub = rs.subResourceByNaturalname("DEPT");
		List dept=new ArrayList();
		for (Iterator iterator = sub.iterator(); iterator.hasNext();) {
			UmsProtectedobject object = (UmsProtectedobject) iterator
					.next();
			String name = object.getNaturalname();
			boolean ok = cupm.checkUserPermission("0000", participant,
					name, "3");
			if (ok) {
				dept.add(object.getId());
			}
		}
		if(dept.size()>0){
			for (Iterator iterator = dept.iterator(); iterator.hasNext();) {
				String  object = (String ) iterator.next();
				Clerk c1 = new Clerk();
				c1.setDeptment(object);
				List list = rs.queryObjectsClerk("0000", c1, null, 0,1000);
				for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
					Clerk object2 = (Clerk) iterator2.next();
					but.append(",'" + object2.getDescription() + "'");
				}
			}



		}

	}

	private void fetchDynamticDeptUser(StringBuffer but,ResourceRmi rs,CupmRmi cupm,String participant)throws Exception{
		String deptid=rs.loadClerk("0000", participant).getDeptment();
		boolean rs1=cupm.checkUserPermission("0000", participant, "BUSSENV.BUSSENV.SECURITY.ROLE.FLOWDEPT", "3");
		boolean rs2=cupm.checkUserPermission("0000", participant, "BUSSENV.BUSSENV.SECURITY.ROLE.FLOWDEPT", "7");


			List dept=new ArrayList();
			if(rs1){// 有权限看自己所在部门的所有人员数据
				dept.add(deptid);
			}
			if(rs2){
				String name=rs.loadResourceById(deptid).getNaturalname();
				List listx=rs.subResourceByNaturalname(name);
				
				for (Iterator iterator = listx.iterator(); iterator.hasNext();) {
					UmsProtectedobject object = (UmsProtectedobject) iterator.next();
					dept.add(object.getId());
					
				}
			}	
			if(dept.size()>0){
				for (Iterator iterator = dept.iterator(); iterator.hasNext();) {
					String object = (String) iterator.next();
					Clerk c1 = new Clerk();
					c1.setDeptment(object);
					List list = rs.queryObjectsClerk("0000", c1, null, 0,1000);
					for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
						Clerk object2 = (Clerk) iterator2.next();
						but.append(",'" + object2.getDescription() + "'");
					}
				}
			}
	}
	public List queryObjects(TCsBus obj, int from, int to, String conditionPre) {
		if (conditionPre == null) {
			conditionPre = "";
		}
		if (obj == null || obj.equals("")) {
			log.error("输入对象TCsBus为空");
			return null;
		}
		String formcode = obj.getFormcode();
		// 根据数据库名读取DyObj对象属性
		DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode).toString());
		if(dfo==null){
			return new ArrayList();
		}
		List columnlist = dfo.getColumn();
		permission(obj);
		// 查询语句生成
		String finalSQL = BussDaoReference._SELECT
				+ ConditionBuilder.build(obj, dfo, columnlist) + conditionPre;
		List list = new ArrayList();

		Connection conn = null;
		ResultSet rs = null;
		Statement st = null;
		try {
			String ds = dfo.getSystemid();
			conn = SQLTools.getConn(ds);
			finalSQL = this.reBuildSql(finalSQL, conn.getMetaData()
					.getDatabaseProductName(), from, to);
			log.debug(finalSQL);
			st = conn.createStatement();
			System.out.println(finalSQL);
			// 执行查询
			rs = st.executeQuery(finalSQL);
			while (rs.next()) {
				// 封装TCsBus对象
				TCsBus bus = new TCsBus();

				bus.setFormcode(obj.getFormcode());

				for (Iterator iterator = columnlist.iterator(); iterator
						.hasNext();) {
					TCsColumn tcc = (TCsColumn) iterator.next();
					String columnid = tcc.getColumnid();
					String columncode = tcc.getColumncode();
					try {
						Object value = rs.getObject(columnid);
						BeanUtils.setProperty(bus, columncode.toLowerCase(),
								value);
						log.debug("columnId:" + columncode.toLowerCase()
								+ ",columnValue:" + value);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						return null;
					} catch (InvocationTargetException e) {
						e.printStackTrace();
						return null;
					}
				}
				try {
					bus.setLsh(rs.getObject("LSH").toString());
				} catch (Exception e) {
					System.err.println("视图表单无LSH");
					e.printStackTrace();
				}
				list.add(bus);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public long queryObjectsNumber(TCsBus obj) {
		return queryObjectsNumber(obj, "");
	}

	public long queryObjectsNumber(TCsBus obj, String conditionPre) {
		if (conditionPre == null) {
			conditionPre = "";
		}
		if (obj == null || obj.equals("")) {
			log.error("输入对象TCsBus为空");
			return 0;
		}

		String formcode = obj.getFormcode();
		// 根据数据库名读取DyObj对象属性
		DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode).toString());

		List columnlist = dfo.getColumn();
		
		permission(obj);

		// 查询语句生成
		String finalSQL = BussDaoReference._SELECTCOUNT
				+ ConditionBuilder.build(obj, dfo, columnlist) + conditionPre;

		Connection conn = null;
		ResultSet rs = null;
		Statement st = null;
		try {
			String ds = dfo.getSystemid();
			conn = SQLTools.getConn(ds);

			st = conn.createStatement();

			// 执行查询
			rs = st.executeQuery(finalSQL);
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public List queryObjects(TCsBus obj, int from, int to) {
		// TODO Auto-generated method stub
		return queryObjects(obj, from, to, "");
	}

	public List queryObjects(TCsBus obj, String conditionPre) {
		// TODO Auto-generated method stub
		return queryObjects(obj, -1, -1, conditionPre);
	}

	public DyObjFromXml getDyObjFromXml() {
		return dyObjFromXml;
	}

	public void setDyObjFromXml(DyObjFromXml dyObjFromXml) {
		this.dyObjFromXml = dyObjFromXml;
	}

	public FormDao getFormDao() {
		return formDao;
	}

	public void setFormDao(FormDao formDao) {
		this.formDao = formDao;
	}
	
	public  String reBuildSql(String sql, String db, int from, int to) {
		if (from == -1 || to == -1) {
			return sql;
		}
		if ("MYSQL".equalsIgnoreCase(db)) {
			sql = sql + " limit " + from + "," + to;
		} else if ("ORACLE".equalsIgnoreCase(db)) {
			sql = sql + " rows>= " + from + " and rows<" + to;
		}
		return sql;
	}

}
