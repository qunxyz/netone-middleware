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

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ͨ������TCsBus����������BussObj���� �ٸ���BussObj������������ݿ�����ɾ�Ĳ������ ͬʱ��ɾ�Ĳ��������ͨ��Ԥ��SQL�����
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
			log.error("�������TCsBusΪ��");
			return false;
		}
		List list = new ArrayList();
		list.add(bus);
		return creates(list);
	}

	public boolean creates(List list) {
		boolean rs = false;
		if (list == null || list.size() == 0) {
			log.error("�������TCsBus����Ϊ��");
			return false;
		} else {
			TCsBus bustmp = (TCsBus) list.get(0);
			String formcode = bustmp.getFormcode();
			if (formcode == null || formcode.equals("")) {
				log.error(formcode + " ��������");
				return false;
			} else {
				DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode)
						.toString());
				if (dfo == null) {
					log.error("δ�ҵ�DyObj����");
					throw new RuntimeException("δ�ҵ�DyObj����");
				}

				TCsForm tcf = dfo.getFrom();
				String tablename = tcf.getDescription();
				if (tablename == null || tablename.equals("")) {
					log.error("δ�ҵ�����");
					throw new RuntimeException("δ�ҵ�����");
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
						// ����BussObj��������
						Map columnidvalue = new LinkedHashMap();
						// // ����Portal��ص��ֶ�
						// BussDaoCoreTools.addToResourcePortal(element, dfo
						// .getColumn(), tablename, formcode);
						// // ��� Fck���ֶ�
						// BussDaoCoreTools.addToResourceFck(element, dfo
						// .getColumn(), tablename, formcode);
						// ����ֶ������Ƿ���ȷ
						BussDaoTools.columnTypeAndValueAvail(columnlist, tcf,
								element, columnidvalue, false);

						int i = 1;
						// ���ò�����Ӧ��ֵ
						for (Iterator iter = columnidvalue.keySet().iterator(); iter
								.hasNext();) {
							String columnid = (String) iter.next();
							log.debug("set value:" + columnid
									+ columnidvalue.get(columnid));
							ps.setObject(i, columnidvalue.get(columnid));
							i++;
						}

						// ִ�����
						ps.executeUpdate();
					}
					conn.commit();
					rs = true;

				} catch (Exception e) {
					// ����������� ��ô��lsh����Ϊ�ձ�ʾʧ��
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
			log.error("�������TCsBusΪ��");
			return false;
		}
		boolean rs = false;

		TCsBus bustmp = bus;
		String formcode = bustmp.getFormcode();
		// ��TCsBus�ж�ȡ���ݿ������ļ�����Ϊ��������
		DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode).toString());
		if (dfo == null) {
			log.error("δ�ҵ�DyObj����");
			throw new RuntimeException("δ�ҵ�DyObj����");
		}

		TCsForm tcf = dfo.getFrom();
		String description = tcf.getDescription();
		if (description.equals("")) {
			log.error("δ�ҵ�����");
			throw new RuntimeException("δ�ҵ�����");
		}

		List columnlist = dfo.getColumn();

		Connection conn = null;
		PreparedStatement ps = null;
		try {
			String ds = dfo.getSystemid();
			conn = SQLTools.getConn(ds);

			TCsBus element = bus;
			// ����BussObj��������
			Map columnidvalue = new LinkedHashMap();

			List availcolumnlist = BussDaoTools.columnTypeAndValueAvail(
					columnlist, tcf, element, columnidvalue, true);

			// ��Ҫ����� participant
			// �ֶ�(��Ϊ���޸��в������޸ĸ��ֶΣ�������޸Ļᵼ��ԭʼ�����߶�ʧ���⽫�ᵼ�°�ȫʶ���������)
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

			// ����SQL
			String sql = BussDaoTools.updateSql(bustmp.getFormcode(),
					description, availcolumnlist, _PK);
			ps = conn.prepareStatement(sql);
			log.debug("update:" + sql);

			int index = 1;
			// ���÷������ֶζ�Ӧ��ֵ
			for (Iterator iter = columnidvalue.keySet().iterator(); iter
					.hasNext();) {
				String columnid = (String) iter.next();
				log.debug("update_column:" + columnid + " index:" + index);

				ps.setObject(index, columnidvalue.get(columnid));
				index++;

			}
			ps.setObject(index, columnidvalue.get(_PK));
			// ִ���޸�
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
			log.error("�������TCsBus����Ϊ��");
			return false;
		} else {
			TCsBus bustmp = (TCsBus) list.get(0);
			String formcode = bustmp.getFormcode();
			// ��TCsBus�ж�ȡ���ݿ������ļ�����Ϊ��������
			DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode)
					.toString());
			if (dfo == null) {
				log.error("δ�ҵ�DyObj����");
				throw new RuntimeException("δ�ҵ�DyObj����");
			}

			TCsForm tcf = dfo.getFrom();
			String description = tcf.getDescription();
			if (description.equals("")) {
				log.error("δ�ҵ�����");
				throw new RuntimeException("δ�ҵ�����");
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
					// ����BussObj��������
					Map columnidvalue = new LinkedHashMap();

					BussDaoTools.columnTypeAndValueAvail(columnlist, tcf,
							element, columnidvalue, false);

					// ��Ҫ����� participant
					// �ֶ�(��Ϊ���޸��в������޸ĸ��ֶΣ�������޸Ļᵼ��ԭʼ�����߶�ʧ���⽫�ᵼ�°�ȫʶ���������)
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
					// ���÷������ֶζ�Ӧ��ֵ
					for (Iterator iter = columnidvalue.keySet().iterator(); iter
							.hasNext();) {
						String columnid = (String) iter.next();
						log.debug("update_column:" + columnid + " index:"
								+ index);

						ps.setObject(index, columnidvalue.get(columnid));
						index++;

					}
					ps.setObject(index, columnidvalue.get(_PK));
					// ִ���޸�
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
			log.error("�������TCsBusΪ��");
			return false;
		}
		List list = new ArrayList();
		list.add(bus);
		return drops(list);
	}

	public boolean drops(List list) {
		boolean rs = false;
		if (list == null || list.size() == 0) {
			log.error("�������TCsBusΪ��");
			return false;
		} else {
			TCsBus busTmp = (TCsBus) list.get(0);
			// ��TCsBus�ж�ȡ���ݿ������ļ�����Ϊ��������
			String formcode = busTmp.getFormcode();
			// ��TCsBus�ж�ȡ���ݿ������ļ�����Ϊ��������
			DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode)
					.toString());
			if (dfo == null) {
				log.error("δ�ҵ�DyObj����");
				throw new RuntimeException("δ�ҵ�DyObj����");
			}

			TCsForm tcf = dfo.getFrom();
			String description = tcf.getDescription();
			if (description.equals("")) {
				log.error("δ�ҵ�����");
				throw new RuntimeException("δ�ҵ�����");
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
					// ����BussObj��������
					Map columnidvalue = new HashMap();

					// ����ֶ������Ƿ���ȷ
					BussDaoTools.columnTypeAndValueAvail(columnlist, tcf,
							element, columnidvalue, false);

					ps.setObject(1, columnidvalue.get(_PK));
					// ִ��ɾ��
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

	/* keyΪ��������ֵ�������ֶ�Ϊ���ʱ������ */
	public TCsBus loadObject(String formcode, Serializable key) {
		if (formcode == null || formcode.equals("")) {
			log.error("δ�ҵ��ļ���");
			return null;
		} else {

			// �������ݿ�����ȡDyObj��������
			FormDao formdao = (FormDao) FormEntry.fetchBean("formDao");
			TCsForm form = formdao.loadObject(formcode);

			String description = form.getDescription();
			if (description == null || description.equals("")) {
				log.error("δ�ҵ�����");
				return null;
			}

			DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode)
					.toString());
			if (dfo == null) {
				log.error("δ�ҵ�DyObj����");
				return null;
			}

			// ��ʼ��ѯ

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

				// ��װTCsBus����
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

	public List queryObjects(TCsBus obj, int from, int to, String conditionPre) {
		if (conditionPre == null) {
			conditionPre = "";
		}
		if (obj == null || obj.equals("")) {
			log.error("�������TCsBusΪ��");
			return null;
		}
		String formcode = obj.getFormcode();
		// �������ݿ�����ȡDyObj��������
		DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode).toString());

		List columnlist = dfo.getColumn();

		// ��ѯ�������
		String finalSQL = BussDaoReference._SELECT
				+ ConditionBuilder.build(obj, dfo, columnlist) + conditionPre;

		List list = new ArrayList();

		Connection conn = null;
		ResultSet rs = null;
		Statement st = null;
		try {
			String ds = dfo.getSystemid();
			conn = SQLTools.getConn(ds);
			finalSQL = BussDaoCoreTools.reBuildSql(finalSQL, conn.getMetaData()
					.getDatabaseProductName(), from, to);
			log.debug(finalSQL);
			st = conn.createStatement();

			// ִ�в�ѯ
			rs = st.executeQuery(finalSQL);
			while (rs.next()) {
				// ��װTCsBus����
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
				bus.setLsh(rs.getObject("LSH").toString());
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
			log.error("�������TCsBusΪ��");
			return 0;
		}
		String formcode = obj.getFormcode();
		// �������ݿ�����ȡDyObj��������
		DyObj dfo = dyObjFromXml.parser(XmlPools.fetchXML(formcode).toString());

		List columnlist = dfo.getColumn();

		// ��ѯ�������
		String finalSQL = BussDaoReference._SELECTCOUNT
				+ ConditionBuilder.build(obj, dfo, columnlist) + conditionPre;

		log.debug(finalSQL);

		Connection conn = null;
		ResultSet rs = null;
		Statement st = null;
		try {
			String ds = dfo.getSystemid();
			conn = SQLTools.getConn(ds);

			st = conn.createStatement();

			// ִ�в�ѯ
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

}
