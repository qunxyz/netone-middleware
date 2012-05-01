package oe.teach.mid.bussmap;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.frame.web.db.DbTools;
import oe.frame.web.util.WebStr;
import oe.teach.mid.buss.MyAccount;
import oe.teach.mid.buss.MyAccountDao;

public class MyAccuntDaoImpl implements MyAccountDao {

	public void create(MyAccount tobj) {
		String sql = "insert into myaccount (lsh,name,types,oriprice,curprice,description)values('"
				+ tobj.getLsh()
				+ "','"
				+ tobj.getName()
				+ "','"
				+ tobj.getTypes()
				+ "',"
				+ tobj.getOriprice()
				+ ","
				+ tobj.getCurprice() + ",'" + tobj.getDescription() + "')";

		DbTools.execute(sql);

	}

	public void delete(String tobj) {
		String sql = "delete from myaccount where lsh='" + tobj + "'";
		DbTools.execute(sql);
	}

	public void delete(String[] tobj) {
		if (tobj == null || tobj.length < 1) {
			return;
		}
		StringBuffer but = new StringBuffer();
		for (int i = 0; i < tobj.length; i++) {
			if (tobj[i] != null && !tobj[i].equals("")) {
				but.append(",'" + tobj[i] + "'");
			}

		}
		String sql = "delete from myaccount where lsh in (" + but.substring(1)
				+ ")";
		DbTools.execute(sql);

	}

	public MyAccount load(String id) {
		String sql = "select * from myaccount where lsh ='" + id + "'";
		List value = DbTools.queryData(sql,MyAccount.class);

		
		if (value != null && value.size() == 1) {
			return (MyAccount) value.get(0);
		}
		return null;
	}

	public String makeCondition(HttpServletRequest request) {
		String name = request.getParameter("name");
		name = WebStr.encode(request, name);
		String types = request.getParameter("types");
		String oriprice = request.getParameter("oriprice");
		String curprice = request.getParameter("curprice");

		StringBuffer but = new StringBuffer();
		if (name != null && !name.equals("")) {
			but.append(" and name like '%" + name + "%'");
		}
		if (types != null && !types.equals("")) {
			but.append(" and types like '%" + name + "%'");
		}
		if (oriprice != null && !name.equals("")) {
			but.append(" and oriprice =" + oriprice);
		}
		if (curprice != null && !curprice.equals("")) {
			but.append(" and curprice =" + curprice);
		}
		return but.toString();

	}

	public List query(String queryinfo, int from, int size) {
		System.out.println(from + "," + size);
		if (size < 0 || from < 0) {
			return null;
		}
		String sql = "select * from myaccount where 1=1 " + queryinfo
				+ " limit " + from + "," + size;

		return DbTools.queryData(sql,MyAccount.class);

	
	}

	public int totalNum(String queryinfo) {
		String sql = "select lsh from myaccount where 1=1 " + queryinfo;
		List list = DbTools.queryData(sql);
		return list.size();
	}

	public void update(MyAccount tobj) {
		if (tobj == null || tobj.getLsh() == null) {
			return;
		}
		String sql = "update myaccount set name='" + tobj.getName()
				+ "',types='" + tobj.getTypes() + "',oriprice="
				+ tobj.getOriprice() + ",curprice=" + tobj.getCurprice()
				+ ",description='" + tobj.getDescription() + "' where lsh='"
				+ tobj.getLsh() + "'";
		DbTools.execute(sql);

	}
}
