package oe.bi.datasource.bus;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import oe.bi.BiEntry;
import oe.bi.common.db.DBHandler;
import oe.bi.datasource.bus.ExtractDataset;
import oe.bi.datasource.obj.DataColumn;
import oe.bi.datasource.obj.DataSet;
import oe.bi.datasource.obj.Datasource;


/**
 * 
 * @author wang-ting-jie
 * 
 */
public class ExtractDatasetImpl implements ExtractDataset {

	/**
	 * 检查连接
	 * 
	 * @param dsObj
	 * @return
	 */
	public boolean checkConnection(Datasource dsObj) {
		// TODO Auto-generated method stub

		DBHandler dbImpl = null;

		try {
			dbImpl = (DBHandler) BiEntry.fetchBi("dBHandler");

			dbImpl.fetchHanderStatement(dsObj);

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 获得数据集
	 * 
	 * @param dsObj
	 * @return List 中保存的为DataSetObj
	 */
	public List fetchDataSet(Datasource dsObj) {
		// TODO Auto-generated method stub
		DBHandler dbImpl = null;
		try {
			dbImpl = (DBHandler) BiEntry.fetchBi("dBHandler"); // 获取dbHandlerImpl对象

			// 根据数据源获取DatabaseMetaData对象
			DatabaseMetaData dbMeta = dbImpl.fetchDatabaseMetaData(dsObj);

			String table = dsObj.getDsflit(); // 查找表的条件
			String types[] = { "TABLE" };
			ResultSet rs = dbMeta.getTables(null, null, table, types);

			List dsObjList = new ArrayList();

			while (rs.next()) {

				DataSet dObj = new DataSet();
				dObj.setDatasetid(rs.getString("TABLE_NAME"));
				dObj.setDescription(rs.getString("REMARKS"));

				List colList = new ArrayList();

				ResultSet colRs = dbMeta.getColumns(null, "%", rs
						.getString("TABLE_NAME"), "%");
				while (colRs.next()) {
					DataColumn dcObj = new DataColumn();
					dcObj.setColumnid(colRs.getString("COLUMN_NAME"));
					dcObj.setTypes(colRs.getString("TYPE_NAME"));
					dcObj.setDescription(colRs.getString("REMARKS"));
					colList.add(dcObj);
				}

				dObj.setDataColumns(colList);

				dsObjList.add(dObj);
			}

			return dsObjList;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

	}
	
	public String[] fetchDataSetKey(Datasource tBiDatasource) {
		DBHandler dbImpl = null;
		PreparedStatement  psmt=null;
		ResultSet rs=null;
		try {
			dbImpl = (DBHandler) BiEntry.fetchBi("dBHandler"); // 获取dbHandlerImpl对象
			
            String sql="";int count=0;
            
            String filt=tBiDatasource.getDsflit()==null?"":tBiDatasource.getDsflit();
             
			sql="select count(*)  from user_tables where TABLE_NAME like'%"+filt+"%'";
			
			psmt=dbImpl.fetchHanderPreparedStatement(tBiDatasource,sql);
			
			 rs=psmt.executeQuery();
			
			if(rs.next())count=rs.getInt(1);
			
			String []talbeName=new String[count];
			
			sql="select TABLE_NAME  from user_tables where TABLE_NAME like'%"+filt+"%'";
			
			psmt=dbImpl.fetchHanderPreparedStatement(tBiDatasource,sql);
			
			rs=psmt.executeQuery();
			int i=0;
			while(rs.next()){
				talbeName[i]=rs.getString(1);
				i++;
			}
			
			return talbeName;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}finally{
			try {
				rs.close();
				psmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
				
			}		
		}
	}

	
	public String[] fetchDataSetColumnKey(Datasource tBiDatasource, String datasetKey) {
		DBHandler dbImpl = null;
		PreparedStatement  psmt=null;
		ResultSet rs=null;
		try {
			dbImpl = (DBHandler) BiEntry.fetchBi("dBHandler"); // 获取dbHandlerImpl对象
			
            String sql="";int count=0;
            
            
			sql="select count(*) from user_tab_columns where TABLE_NAME=?";
			
			psmt=dbImpl.fetchHanderPreparedStatement(tBiDatasource,sql);
			
			psmt.setString(1,datasetKey);
			
			 rs=psmt.executeQuery();
			
			if(rs.next())count=rs.getInt(1);
			
			String []columnName=new String[count];
			
			sql="select COLUMN_NAME from user_tab_columns where TABLE_NAME=?";
			
			psmt=dbImpl.fetchHanderPreparedStatement(tBiDatasource,sql);
			
			psmt.setString(1,datasetKey);
			
			
			
			rs=psmt.executeQuery();
			int i=0;
			while(rs.next()){
				columnName[i]=rs.getString(1);
				i++;
			}
			
			return columnName;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}finally{
			try {
				rs.close();
				psmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
				
			}		
		}

	}

	public static void main(String[] args) {
		ExtractDatasetImpl edImpl = new ExtractDatasetImpl();
		Datasource dsObj = new Datasource();
		dsObj.setDsdriver("oracle.jdbc.driver.OracleDriver");
		dsObj.setDsurl("jdbc:oracle:thin:@10.1.5.153:1521:pnmc");
		dsObj.setUsername("nxgnmc");
		dsObj.setPassword("nxgnmc");
		//dsObj.setDsflit("CHECK");
		System.out.println("conn=========" + edImpl.checkConnection(dsObj));
		String tableStr[]=edImpl.fetchDataSetKey(dsObj);
		for(int i=0;i<tableStr.length;i++){
			System.out.println("table==="+tableStr[i]);
		}
		
//		String colStr[]=edImpl.fetchDataSetColumnKey(dsObj,"CHECK_NP_CONF");
//		for(int i=0;i<colStr.length;i++){
//			System.out.println("col==="+colStr[i]);
//		}
	}

	

}
