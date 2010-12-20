package oe.cav.bean.logic.dy.bus;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.cav.bean.logic.bus.BussDaoCore;
import oe.cav.bean.logic.bus.BussObj;
import oe.cav.bean.logic.dy.bus.reference.BussDaoReference;
import oe.cav.bean.logic.tools.SQLTools;
import oe.frame.orm.QueryInfo;
import oe.frame.orm.util.DbTools;

/**
 * ���ݴ����BussObj������������ݿ�����ɾ�Ĳ������ ͬʱ��ɾ�Ĳ��������ͨ��Ԥ��SQL����ɣ�
 * ������ɾ�Ĳ����û���SQL��ͨ��formcode��ds����λSQL�� ��ѯ������ʵ����BussDaoCoreImpl��ʵ����ͬ
 * 
 * @author admin
 * 
 */
public class BussDaoCoreImpl2 implements BussDaoCore {

	static Log log = LogFactory.getLog(BussDaoCoreImpl2.class);

	private static Map createmap = new HashMap();

	private static Map updatemap = new HashMap();

	private static Map dropmap = new HashMap();

	public boolean create(BussObj bus) {
		if (bus == null || bus.equals("")) {
			log.error("�������BussObjΪ��");
			return false;
		} else {
			String ds = bus.getSystemid();
			String tablename = bus.getFormcode();
			Map columnidvalue = bus.getColumnIdValue();
			Map columnidtype = bus.getColumnIdType();
			String[] pk = (String[]) bus.getPrimarkeys();
			if (tablename == null || tablename.equals("")) {
				log.error("δ�ҵ�����");
				return false;
			} else {
				if (pk != null && pk.length > 0) {
					if (columnidvalue.size() == columnidtype.size()
							&& columnidvalue.size() > 0) {
						// ��������ֶε�ֵ�Ƿ�Ϊ��
						for (Iterator iter = columnidvalue.keySet().iterator(); iter
								.hasNext();) {
							String keys = (String) iter.next();
							for (int i = 0; i < pk.length; i++) {
								if (pk[i].equals(keys)) {
									if (columnidvalue.get(keys) == null
											|| columnidvalue.get(keys).equals(
													"")) {
										log.error("����ֵ����Ϊ��");
										return false;
									}
								}
							}
						}
						String key = ds + tablename;
						StringBuffer sb = null;
						if (!createmap.containsKey(key)) {
							sb = new StringBuffer();
							sb.append(BussDaoReference._INSERT + tablename
									+ "(");
							// �ж��еĸ����ı�־����Ϊ0����Ϊfalse,��Ϊ1����������Ϊtrue
							boolean flag = false;
							for (Iterator iter = columnidvalue.keySet()
									.iterator(); iter.hasNext();) {
								String columnid = (String) iter.next();
								if (flag == false) {
									sb.append(columnid);
									flag = true;
								} else {
									sb.append("," + columnid);
								}
							}
							sb.append(") values (");
							// �ж��еĸ����ı�־����Ϊ0����Ϊfalse,��Ϊ1����������Ϊtrue
							boolean sign = false;
							for (Iterator iter = columnidvalue.keySet()
									.iterator(); iter.hasNext(); iter.next()) {
								if (sign == false) {
									sb.append(BussDaoReference._FlAG);
									sign = true;
								} else {
									sb.append("," + BussDaoReference._FlAG);
								}
							}
							sb.append(")");
							createmap.put(key, sb);
							
						} else {
							sb = (StringBuffer) createmap.get(key);
						}
						Connection conn = null;
						PreparedStatement ps = null;
						try {
							conn = SQLTools.getConn(ds);
							ps = conn.prepareStatement(sb.toString());
							log.info(sb.toString());
							int i = 1;
							// ���ò�����Ӧ��ֵ
							for (Iterator iter = columnidvalue.keySet()
									.iterator(); iter.hasNext();) {
								String columnid = (String) iter.next();
								ps.setObject(i, columnidvalue.get(columnid));
								i++;
							}
							// ִ�����
							int result = ps.executeUpdate();
							if (result > 0) {
								log.error("���ִ�гɹ�");
								return true;
							} else {
								log.error("���ִ��ʧ��");
								return false;
							}
						} catch (Exception e) {
							e.printStackTrace();
							return false;
						} finally {
							try {
								conn.close();
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					} else {
						log.error("���м�ֵ���������Ͳ���Ӧ");
						return false;
					}
				} else {
					log.error("δ�ҵ�����");
					return false;
				}
			}
		}
	}

	public boolean creates(List list) {
		if (list == null) {
			log.error("���������");
			return false;
		} else {
			boolean b = true;
			for (Iterator iteror = list.iterator(); iteror.hasNext();) {
				BussObj bus = (BussObj) iteror.next();
				if (bus == null) {
					b = false;
				} else {
					b = b && create(bus);
				}
			}
			return b;
		}
	}

	public boolean update(BussObj bus) {
		if (bus == null || bus.equals("")) {
			log.error("�������BussObjΪ��");
			return false;
		} else {
			String ds = bus.getSystemid();
			String tablename = bus.getFormcode();
			Map columnidvalue = bus.getColumnIdValue();
			Map columnidtype = bus.getColumnIdType();
			String[] pk = (String[]) bus.getPrimarkeys();
			if (tablename == null || tablename.equals("")) {
				log.error("δ�ҵ�����");
				return false;
			} else {
				if (pk != null && pk.length > 0) {
					if (columnidvalue.size() == columnidtype.size()
							&& columnidvalue.size() > 0) {
						// ��������ֶε�ֵ�Ƿ�Ϊ��
						for (Iterator iter = columnidvalue.keySet().iterator(); iter
								.hasNext();) {
							String keys = (String) iter.next();
							for (int i = 0; i < pk.length; i++) {
								if (pk[i].equals(keys)) {
									if (columnidvalue.get(keys) == null
											|| columnidvalue.get(keys).equals(
													"")) {
										log.error("����ֵ����Ϊ��");
										return false;
									}
								}
							}
						}
						String key = ds + tablename;
						StringBuffer sb = null;
						if (!updatemap.containsKey(key)) {
							sb = new StringBuffer();
							sb.append(BussDaoReference._UPDATE + tablename
									+ BussDaoReference._SET);
							// �жϷ������еĸ����ı�־����Ϊ0����Ϊfalse,��Ϊ1����������Ϊtrue
							boolean flag = false;
							for (Iterator iter = columnidvalue.keySet()
									.iterator(); iter.hasNext();) {
								String columnid = (String) iter.next();
								// �ж��Ƿ�����������������Ϊfalse��������Ϊtrue
								boolean ifpk = false;
								for (int i = 0; i < pk.length; i++) {
									if (columnid.equals(pk[i])) {
										ifpk = true;
										break;
									}
								}
								if (ifpk == false) {
									if (flag == false) {
										sb.append(columnid + " = "
												+ BussDaoReference._FlAG);
										flag = true;
									} else {
										sb.append("," + columnid + " = "
												+ BussDaoReference._FlAG);
									}
								}
							}
							sb.append(BussDaoReference._WHERE
									+ BussDaoReference._PRE_CONDITION);
							for (int i = 0; i < pk.length; i++) {
								sb.append(BussDaoReference._AND + pk[i] + " = "
										+ BussDaoReference._FlAG);
							}
							updatemap.put(key, sb);
							log.info(sb.toString());
						} else {
							sb = (StringBuffer) updatemap.get(key);
						}
						Connection conn = null;
						PreparedStatement ps = null;
						try {
							conn = SQLTools.getConn(ds);
							ps = conn.prepareStatement(sb.toString());
							int index = 1;
							// ���÷������ֶζ�Ӧ��ֵ
							for (Iterator iter = columnidvalue.keySet()
									.iterator(); iter.hasNext();) {
								String columnid = (String) iter.next();
								// �ж��Ƿ�����������������Ϊfalse��������Ϊtrue
								boolean ifpk = false;
								for (int i = 0; i < pk.length; i++) {
									if (columnid.equals(pk[i])) {
										ifpk = true;
										break;
									}
								}
								if (ifpk == false) {
									ps.setObject(index, columnidvalue
											.get(columnid));
									index++;
								}
							}
							// ���������ֶζ�Ӧ��ֵ
							for (int i = 0; i < pk.length; i++) {
								ps.setObject(index, columnidvalue.get(pk[i]));
								index++;
							}
							// ִ���޸�
							int result = ps.executeUpdate();
							if (result > 0) {
								log.error("�޸�ִ�гɹ�");
								return true;
							} else {
								log.error("�޸�ִ��ʧ��");
								return false;
							}
						} catch (Exception e) {
							e.printStackTrace();
							return false;
						} finally {
							try {
								conn.close();
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					} else {
						log.error("���м�ֵ���������Ͳ���Ӧ");
						return false;
					}
				} else {
					log.error("δ�ҵ�����");
					return false;
				}
			}
		}
	}

	public boolean updates(List bus) {
		if (bus == null) {
			log.error("���������");
			return false;
		} else {
			boolean b = true;
			for (Iterator iteror = bus.iterator(); iteror.hasNext();) {
				BussObj obj = (BussObj) iteror.next();
				if (obj == null) {
					b = false;
				} else {
					b = b && update(obj);
				}
			}
			return b;
		}
	}

	public boolean drop(BussObj bus) {
		if (bus == null || bus.equals("")) {
			log.error("�������BussObjΪ��");
			return false;
		} else {
			String ds = bus.getSystemid();
			String tablename = bus.getFormcode();
			Map columnidvalue = bus.getColumnIdValue();
			Map columnidtype = bus.getColumnIdType();
			String[] pk = (String[]) bus.getPrimarkeys();
			if (tablename == null || tablename.equals("")) {
				log.error("δ�ҵ�����");
				return false;
			} else {
				if (pk != null && pk.length > 0) {
					if (columnidvalue.size() == columnidtype.size()
							&& columnidvalue.size() > 0) {
						// ��������ֶε�ֵ�Ƿ�Ϊ��
						for (Iterator iter = columnidvalue.keySet().iterator(); iter
								.hasNext();) {
							String keys = (String) iter.next();
							for (int i = 0; i < pk.length; i++) {
								if (pk[i].equals(keys)) {
									if (columnidvalue.get(keys) == null
											|| columnidvalue.get(keys).equals(
													"")) {
										log.error("����ֵ����Ϊ��");
										return false;
									}
								}
							}
						}
						String key = ds + tablename;
						StringBuffer sb = null;
						if (!dropmap.containsKey(key)) {
							sb = new StringBuffer();
							sb.append(BussDaoReference._DELETE
									+ BussDaoReference._FROM + tablename);
							sb.append(BussDaoReference._WHERE
									+ BussDaoReference._PRE_CONDITION);
							for (int i = 0; i < pk.length; i++) {
								sb.append(BussDaoReference._AND + pk[i] + "="
										+ BussDaoReference._FlAG);
							}
							dropmap.put(key, sb);
							log.info(sb.toString());
						} else {
							sb = (StringBuffer) dropmap.get(key);
						}

						Connection conn = null;
						PreparedStatement ps = null;
						try {
							conn = SQLTools.getConn(ds);
							ps = conn.prepareStatement(sb.toString());
							// ���ò�����Ӧ��ֵ
							for (int i = 0; i < pk.length; i++) {
								ps.setObject(i + 1, columnidvalue.get(pk[i]));
							}
							// ִ��ɾ��
							int result = ps.executeUpdate();
							if (result > 0) {
								log.error("ɾ��ִ�гɹ�");
								return true;
							} else {
								log.error("ɾ��ִ��ʧ��");
								return false;
							}
						} catch (Exception e) {
							e.printStackTrace();
							return false;
						} finally {
							try {
								conn.close();
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					} else {
						log.error("���м�ֵ���������Ͳ���Ӧ");
						return false;
					}
				} else {
					log.error("δ�ҵ�����");
					return false;
				}
			}
		}
	}

	public boolean drops(List list) {
		if (list == null) {
			log.error("���������");
			return false;
		} else {
			boolean b = true;
			for (Iterator iteror = list.iterator(); iteror.hasNext();) {
				BussObj obj = (BussObj) iteror.next();
				if (obj == null) {
					b = false;
				} else {
					b = b && drop(obj);
				}
			}
			return b;
		}
	}

	/* keyΪ��������ֵ�������ֶ�Ϊ���ʱ������ */
	public Object loadObject(BussObj obj, Serializable key) {
		if (obj == null || key == null || key.equals("")) {
			log.error("�������BussObjΪ��");
			return null;
		} else {
			String ds = obj.getSystemid();
			String tablename = obj.getFormcode();
			Map columnidvalue = obj.getColumnIdValue();
			Map columnidtype = obj.getColumnIdType();
			Map extendattribute = obj.getExtendattribute();
			String[] pk = (String[]) obj.getPrimarkeys();
			if (tablename == null || tablename.equals("")) {
				log.error("δ�ҵ�����");
				return null;
			} else {
				if (pk != null && pk.length > 0) {
					if (columnidvalue.size() == columnidtype.size()
							&& columnidvalue.size() > 0) {
						// ��������ֶε�ֵ�Ƿ�Ϊ��
						for (Iterator iter = columnidvalue.keySet().iterator(); iter
								.hasNext();) {
							String keys = (String) iter.next();
							for (int i = 0; i < pk.length; i++) {
								if (pk[i].equals(keys)) {
									if (columnidvalue.get(keys) == null
											|| columnidvalue.get(keys).equals(
													"")) {
										log.error("����ֵ����Ϊ��");
										return null;
									}
								}
							}
						}
						StringBuffer sb = new StringBuffer();
						sb.append(BussDaoReference._SELECT
								+ BussDaoReference._FROM + tablename
								+ BussDaoReference._WHERE + pk[0] + "=" + key);
						log.debug(sb.toString());

						List list = new ArrayList();
						try {
							Connection con = SQLTools.getConn(ds);
							list = DbTools.queryData(con, sb.toString());
						} catch (Exception e) {
							e.printStackTrace();
						}

						Map columnidvaluemap = new HashMap();
						Map columnidtypemap = new HashMap();
						for (Iterator iter = list.iterator(); iter.hasNext();) {
							Map map = (Map) iter.next();
							for (Iterator iterator = map.keySet().iterator(); iterator
									.hasNext();) {
								String columnid = (String) iterator.next();
								Object columnvalue = map.get(columnid);
								columnidvaluemap.put(columnid, columnvalue);
								columnidtypemap.put(columnid, columnidtype.get(
										columnid).toString());
							}
						}
						// ��װBussObj����
						BussObj bo = new BussObj();
						bo.setSystemid(ds);
						bo.setFormcode(tablename);
						bo.setColumnIdValue(columnidvaluemap);
						bo.setColumnIdType(columnidtypemap);
						bo.setPrimarkeys(pk);
						bo.setExtendattribute(extendattribute);
						return bo;
					} else {
						log.error("���м�ֵ���������Ͳ���Ӧ");
						return null;
					}
				} else {
					log.error("δ�ҵ�����");
					return null;
				}
			}
		}
	}

	public boolean export(BussObj bus, String condition, String type,
			HttpServletResponse res) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean move(String formcode, String lsh, boolean up,
			String participant) {
		// TODO Auto-generated method stub
		return false;
	}

	public List queryObjects(BussObj obj, int from, int to, String condtion) {
		if (obj == null || obj.equals("")) {
			log.error("�������BussObjΪ��");
			return null;
		} else {
			String tablename = obj.getFormcode();
			Map columnidvalue = obj.getColumnIdValue();
			Map columnidtype = obj.getColumnIdType();

			String[] pk = (String[]) obj.getPrimarkeys();
			if (tablename == null || tablename.equals("")) {
				log.error("δ�ҵ�����");
				return new ArrayList();
			} else {
				if (pk != null && pk.length > 0) {
					if (columnidvalue.size() == columnidtype.size()
							&& columnidvalue.size() > 0) {
						List valuelist = BussDaoCoreTools.sqlAndParam(obj,
								condtion, false);
						String finalSQL = (String) valuelist.get(0);
						valuelist.remove(0);
						if (valuelist.size() == 0) {
							log.error("��Ч��ѯ����");
							return new ArrayList();
						} else {
							return BussDaoCoreTools.fetchData(finalSQL,
									valuelist, obj, from, to);
						}
					} else {
						log.error("���м�ֵ���������Ͳ���Ӧ");
						return new ArrayList();
					}
				} else {
					log.error("δ�ҵ�����");
					return new ArrayList();
				}
			}
		}
	}

	public long queryObjectsNumber(BussObj obj, String condtion) {
		if (obj == null || obj.equals("")) {
			log.error("�������BussObjΪ��");
			return -1;
		} else {
			String tablename = obj.getFormcode();
			Map columnidvalue = obj.getColumnIdValue();
			Map columnidtype = obj.getColumnIdType();

			String[] pk = (String[]) obj.getPrimarkeys();
			if (tablename == null || tablename.equals("")) {
				log.error("δ�ҵ�����");
				return -1;
			} else {
				if (pk != null && pk.length > 0) {
					if (columnidvalue.size() == columnidtype.size()
							&& columnidvalue.size() > 0) {
						List valuelist = BussDaoCoreTools.sqlAndParam(obj,
								condtion, true);
						String finalSQL = (String) valuelist.get(0);
						valuelist.remove(0);
						if (valuelist.size() == 0) {
							log.error("��Ч��ѯ����");
							return -1;
						} else {
							return BussDaoCoreTools.fetchDataNum(finalSQL,
									valuelist, obj);
						}
					} else {
						log.error("���м�ֵ���������Ͳ���Ӧ");
						return -1;
					}
				} else {
					log.error("δ�ҵ�����");
					return -1;
				}
			}
		}
	}

	public long queryObjectsNumber(BussObj obj) {
		return queryObjectsNumber(obj, "");
	}

	public List queryObjects(BussObj obj, int from, int to) {
		// TODO Auto-generated method stub
		return queryObjects(obj, from, to, "");
	}

	public List queryObjects(BussObj obj, String conditionPre) {
		// TODO Auto-generated method stub
		return queryObjects(obj, -1, -1, conditionPre);
	}

	public List queryObjects(BussObj obj) {
		return queryObjects(obj, -1, -1, "");
	}
}
