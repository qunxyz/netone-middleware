package oe.netone.bi;

import java.rmi.NotBoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import oe.cav.bean.logic.column.TCsColumn;
import oe.midware.dyform.service.DyFormDesignService;
import oe.netone.dy.getpColumn;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

import org.apache.derby.tools.sysinfo;

import com.jl.common.dyform.DyEntry;
import com.jl.common.dyform.DyFormColumn;
import com.jl.common.dyform.DyFormData;
import com.mysql.jdbc.ResultSetMetaData;

public class ShowData {
	public Connection conn = null;
	public Statement stmt = null;
	public ResultSet rs = null;
	public static List dyformList;
	public static String dyname;
	public static boolean neibu=false;

	private static String dbDriver = "com.mysql.jdbc.Driver";
//	private static String dbUrl = "jdbc:mysql://localhost:3306/dyform";
//	private static String dbUser = "root";
//	private static String dbPwd = "xan625";
   
	public static Connection getConnection(String dbUrl,String dbUser,String dbPwd) {
		Connection conn = null;
		try {
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(dbUrl, dbUser,dbPwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (conn == null) {
			System.err.println("警告:数据库连接失败!");
		}
		return conn;
	}
	

	public  List doQuery(String sql,String dbUrl,String dbUser,String dbPwd) throws SQLException,Exception {
		dyname=divideStringForAdd(sql);
		String aString="select * from "+dyname;
		if(dbUrl.trim()=="jdbc:mysql://localhost:3306/dyform")
		{neibu=true;}
		try {
			conn = ShowData.getConnection(dbUrl,dbUser,dbPwd);
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(aString);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		List eList=resultSetToList(rs);
		return eList;
	}
	public static List resultSetToList(ResultSet rs) throws SQLException,Exception {        
		ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData(); //得到结果集(rs)的结构信息，比如字段数、字段名等     
	       int columnCount = md.getColumnCount(); //返回此 ResultSet 对象中的列数     
	       List list = new ArrayList();     
	      
	       
	       if (neibu==false) {
	    	   while (rs.next()) {     
	   	        Map rowData = rowData = new HashMap(columnCount);     
	   	        for (int i = 1; i <= columnCount; i++) {  
	   	                rowData.put(md.getColumnName(i), rs.getObject(i));     
	   	        }     
	   	        list.add(rowData);   
	   	       } 
		} else {
			 List dyList=getcname(dyname);
			 System.out.println(dyList.toString());
			while (rs.next()) {     
	   	        Map rowData = rowData = new HashMap(columnCount);     
	   	        for (int i = 1; i <= columnCount; i++) {  
	   	                rowData.put(dyList.get(i).toString(), rs.getObject(i));     
	   	        }     
	   	        list.add(rowData);   
	   	       } 
		}
	       System.out.println(list.toString());
	
	       return list;     
	}   
	       private static Object dyformList(int i) {
		// TODO Auto-generated method stub
		return null;
	}


		private static String divideStringForAdd(String str) {
	    	   String aString=null;
	    	   
	    	   for (int i = 0; i < str.length(); i++)   
	    	   {   
	    	    if (str.substring(i, i + 1).equals("."))   
	    	    {     
	    	     aString=str.substring(0,i).trim();   
	    	     
	    	    } }
		         return aString;
		     }
	       
	       public static List getcname(String tablename) throws Exception {
  	   	
	   		List cnameList = new ArrayList();
			ResourceRmi rs = (ResourceRmi) RmiEntry.iv("resource");
			UmsProtectedobject upox = new UmsProtectedobject();
			upox.setNaturalname("BUSSFORM.BUSSFORM%." + tablename.toUpperCase());
			Map map = new HashMap();
			map.put("naturalname", "like");
			List list = rs.queryObjectProtectedObj(upox, map, 0, 10, "");
			if (list != null && list.size() == 1) {
				String formcode = ((UmsProtectedobject) list.get(0)).getExtendattribute();
				getpColumn gc=new getpColumn();
				List cidlist=gc.getc(formcode);
				
				
				DyFormDesignService dys = null;
				try {
					dys = (DyFormDesignService) RmiEntry.iv("dydesign");
				} catch (NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				TCsColumn busForm = new TCsColumn();
				busForm.setFormcode(formcode);
			   	List listmame =dys.queryObjects(busForm);
			   	
			   	for (Iterator iterator1 = listmame.iterator(); iterator1.hasNext();) {
				    TCsColumn columnname=(TCsColumn)iterator1.next();
				   // System.out.println(columnname.getColumname());
				    cnameList.add(columnname.getColumname());
			   	}
              if (list==null) {
            	  cnameList=null;
			}

			} 
			System.out.println(cnameList.toString());
			return cnameList;
			
		}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
          ShowData sData=new ShowData();
//          List rSet= sData.getcname("DY_791317714727704");
//          if(rSet==null)
//          {
//        	  System.out.println("aa"); 
//          }else {
//        	  System.out.println("bb");
//		  }
          
        System.out.println( sData.doQuery("DY_791317714727704.aa", "jdbc:mysql://localhost:3306/dyform", "root", "xanadu625"));
	}

}
