package oe.teach.mid.bussmap;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import oe.frame.orm.OrmerEntry;
import oe.teach.mid.buss.OecStuDao;
import oe.teach.mid.buss.OecStudent;

public class OecStuDaoImpl implements OecStuDao {
	

	public void create(OecStudent tobj) {
		OrmerEntry.fetchOrmer().fetchSerializer().create(tobj);
	}

	public void creates(List<OecStudent> tobj) {
		OrmerEntry.fetchOrmer().fetchSerializer().creates(tobj);

	}

	public void delete(String tobjid) {
		OecStudent tobj = (OecStudent) OrmerEntry
				.fetchOrmer().fetchQuerister().loadObject(
						OecStudent.class, new Long(tobjid));
		OrmerEntry.fetchOrmer().fetchSerializer().drop(tobj);

	}

	public void delete(String[] tobjId) {
		List list = new ArrayList();
		for (int i = 0; i < tobjId.length; i++) {
			if (tobjId[i] != null && !tobjId[i].equals("")) {
				OecStudent tobjTmp = (OecStudent) OrmerEntry.fetchOrmer()
						.fetchQuerister().loadObject(OecStudent.class,
								new Long(tobjId[i]));
				list.add(tobjTmp);
			}
		}
		OrmerEntry.fetchOrmer().fetchSerializer().drops(list);

	}

	public OecStudent load(String id) {
		return (OecStudent) OrmerEntry.fetchOrmer().fetchQuerister()
				.loadObject(OecStudent.class, new Long(id));
	}

	public List query(String queryinfo, int from, int to) {
		OecStudent tobjQ = new OecStudent();
		return OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(tobjQ,
				null, from, to, queryinfo);
	}

	public int totalNum(String sql) {
		OecStudent tobjQ = new OecStudent();
		return (int) OrmerEntry.fetchOrmer().fetchQuerister()
				.queryObjectsNumber(tobjQ, null, sql);
	}

	public void update(OecStudent tobj) {
		OrmerEntry.fetchOrmer().fetchSerializer().update(tobj);

	}

	public void update(List<OecStudent> tobj) {
		OrmerEntry.fetchOrmer().fetchSerializer().updates(tobj);

	}

	public String listCondition(HttpServletRequest request) {
		String stuname = request.getParameter("stuname");
		request.setAttribute("stuname", stuname);
		String condition = "";
		if (stuname != null) {
			condition = " and stuname like '%" + stuname + "%'";
		}
		return condition;
	}

}
