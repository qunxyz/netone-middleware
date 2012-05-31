package oe.midware.workflow.engine.rule2.func;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oe.frame.bus.workflow.WfEntry;
import oe.frame.bus.workflow.rule.DbScriptFunction;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.CupmRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DbScriptFunctionImpl implements DbScriptFunction {

	private Log _log = LogFactory.getLog(DbScriptFunctionImpl.class);

	public void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				_log.error(e.getMessage());
			}
		}
	}

	public Connection con(String driver, String dburl, String username,
			String password) {
		try {
			Class.forName(driver).newInstance();
			return DriverManager.getConnection(dburl, username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;
	}

	public Connection con(String dsname) {
		ResourceRmi rri;
		try {
			rri = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upo = rri.loadResourceByNatural(dsname);
			String ext = upo.getExtendattribute();
			String[] info = StringUtils.split(ext, "#");
			return con(info[0], info[1], info[2], info[3]);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return null;

	}

	public int execute(Connection con, String sql) {
		if (con == null) {
			return -1;
		}
		try {

			// ���Statment���
			Statement st = con.createStatement();
			// �����ӵ�Ŀ�����ݿ���ִ��SQL
			st.execute(sql);
			_log.info(sql);
			try {
				CupmRmi cupm=(CupmRmi)RmiEntry.iv("cupm");
				cupm.log("db_insert", "", "scriptmen", "success", sql);
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			// ����SQL��ִ�н��
			return st.getUpdateCount();
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
			try {
				CupmRmi cupm=(CupmRmi)RmiEntry.iv("cupm");
				cupm.log("db_insert", "", "scriptmen", "error", sql+e.getMessage());
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;
	}

	public List queryData(Connection con, String sql) {
		if (con == null) {
			return null;
		}
		// �����ѯ�Ľ����Ϣ
		List list = new ArrayList();

		try {

			// ���Statment���
			Statement st = con.createStatement();
			// �����ӵ�Ŀ�����ݿ���ִ��SQL
			boolean sqltype = st.execute(sql);
			if (!sqltype) {// ������ǲ�ѯ�����ô���ؿ�
				return null;
			}
			// ��ò�ѯ�����
			ResultSet rs = st.getResultSet();
			// ��ò�ѯ������еı��ֶ���Ϣ
			ResultSetMetaData metaData = rs.getMetaData();

			while (rs.next()) {
				// ����һ��map����,���洢һ����¼��Ϣ
				Map preRecord = new HashMap();
				// ����������е��ֶ�,����ǰ�е����ݿ��¼����,д��map������
				for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
					// ����ֶ���
					String columnname = metaData.getColumnName(i);
					// ����ֶε�ֵ
					Object value = rs.getObject(columnname);
					// д��һ���ֶε�ֵ
					preRecord.put(columnname, value);
				}
				// ��һ����¼���붯̬����
				list.add(preRecord);
			}
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	
	public int execute_p(Connection con, String sql,List value) {
		if (con == null) {
			return -1;
		}
		boolean error=false;
		StringBuffer but=new StringBuffer();
		try {

			// ���Statment���
			PreparedStatement ps = con.prepareStatement(sql);
			
			for (int i = 0; i < value.size(); i++) {
				ps.setObject(i+1, value.get(i));
				but.append("|"+value.get(i));
			}
			return ps.executeUpdate();

		} catch (Exception e) {
			error=true;
			e.printStackTrace();
			_log.error(e.getMessage());
			try {
				CupmRmi cupm=(CupmRmi)RmiEntry.iv("cupm");
				cupm.log("db_insert", "", "scriptmen", "error", sql+but+e.getMessage());
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}finally{
			if(!error){
				_log.info(sql);
				try {
					CupmRmi cupm=(CupmRmi)RmiEntry.iv("cupm");
					cupm.log("db_insert", "", "scriptmen", "success", sql+but);
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
			}
		}
		return -1;
	}

	public List queryData_p(Connection con, String sql,List valuex) {
		if (con == null) {
			return null;
		}
		// �����ѯ�Ľ����Ϣ
		List list = new ArrayList();

		try {

			// ���Statment���
			PreparedStatement ps = con.prepareStatement(sql);
			for (int i = 0; i < valuex.size(); i++) {
				ps.setObject(i+1, valuex.get(i));
			}

			// ��ò�ѯ�����
			ResultSet rs = ps.executeQuery();
			// ��ò�ѯ������еı��ֶ���Ϣ
			ResultSetMetaData metaData = rs.getMetaData();

			while (rs.next()) {
				// ����һ��map����,���洢һ����¼��Ϣ
				Map preRecord = new HashMap();
				// ����������е��ֶ�,����ǰ�е����ݿ��¼����,д��map������
				for (int i = 1; i < metaData.getColumnCount() + 1; i++) {
					// ����ֶ���
					String columnname = metaData.getColumnName(i);
					// ����ֶε�ֵ
					Object value = rs.getObject(columnname);
					// д��һ���ֶε�ֵ
					preRecord.put(columnname, value);
				}
				// ��һ����¼���붯̬����
				list.add(preRecord);
			}
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	public int getn(List list, int row, String col) {
		Object obj = get(list, row, col);
		if (obj == null) {
			return -1;
		}
		try {
			return ((Integer) obj).intValue();
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return -1;

	}

	public long getl(List list, int row, String col) {
		Object obj = get(list, row, col);
		if (obj == null) {
			return -1l;
		}

		try {
			return ((Long) obj).longValue();
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return -1l;
	}

	public float getf(List list, int row, String col) {
		Object obj = get(list, row, col);
		if (obj == null) {
			return -1f;
		}

		try {
			return ((Float) obj).floatValue();
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return -1f;
	}

	public double getd(List list, int row, String col) {
		Object obj = get(list, row, col);
		if (obj == null) {
			return -1d;
		}

		try {
			return ((Double) obj).doubleValue();
		} catch (Exception e) {
			e.printStackTrace();
			_log.error(e.getMessage());
		}
		return -1d;
	}

	public String gets(List list, int row, String col) {
		Object obj = get(list, row, col);
		if (obj == null) {
			return null;
		}
		return String.valueOf(obj);
	}

	public Object get(List list, int row, String col) {
		if (list.size() < row + 1) {
			return null;
		}
		return ((Map) list.get(row)).get(col);
	}

}
