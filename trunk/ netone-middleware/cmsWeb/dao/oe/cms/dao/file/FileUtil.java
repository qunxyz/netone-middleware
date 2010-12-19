package oe.cms.dao.file;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.DbTools;
import oe.frame.web.util.Cacheobj;

public class FileUtil {

	/**
	 * ����Դ�б�
	 * 
	 * @param elementFlag
	 * @return
	 */
	public static List getFiIdList() {

		String sql = "select dirid,dirname from iis_fileupload_dirs";
		Connection con = null;
		try {
			con = OrmerEntry.fetchDS().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[][] info = DbTools.superQueryOri(con, sql);
		List coList = new ArrayList();
		for (int i = 0; i < info.length; i++) {

			Cacheobj co = new Cacheobj(info[i][1], "" + info[i][0]);
			coList.add(co);
		}
		return coList;
	}

	/**
	 * ��������Դid��ȡFI���ݼ��б�
	 * 
	 * @param sqlid
	 * @return
	 */
	public static List getFIColumnidList(String id) {

		if (id == null || "".equals(id)) {
			String sql = "select min(dirid) from iis_fileupload_dirs";
			Connection con = null;
			try {
				con = OrmerEntry.fetchDS().getConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[][] info = DbTools.superQueryOri(con, sql);
			if (info != null && info.length > 0) {
				id = info[0][0];
			}
		}

		List coList = new ArrayList();

		String sql = "select fileid,filename from iis_fileupload_files where dirid="
				+ id;
		Connection con = null;
		try {
			con = OrmerEntry.fetchDS().getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[][] info = DbTools.superQueryOri(con, sql);
		for (int i = 0; i < info.length; i++) {

			Cacheobj co = new Cacheobj(info[i][1], "" + info[i][0]);
			coList.add(co);
		}
		return coList;

	}
}
