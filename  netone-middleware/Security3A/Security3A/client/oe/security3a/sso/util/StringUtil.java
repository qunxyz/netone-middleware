package oe.security3a.sso.util ;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * �ַ��������Ĺ�����
 *
 * @author not attributable
 * @version 1.0
 */
public class StringUtil {

    private StringUtil()
    {
    }

    /**
     * ȡ�ò�Ϊ�յ��ִ�
     * @param Obj Object
     * @return String
     */
    public static String getNotNullStr( Object Obj )
    {
        if ( Obj == null )
        {
            Obj = "" ;
        }
        return Obj.toString() ;
    }


    /**
     * ��������ת�����Զ��ŷָ�����ݣ���Ҫ����sql����in����
     * @param list ArrayList
     * @return String
     */
    public static String list2string( List list )
    {
        StringBuffer sb = new StringBuffer() ;
        if ( list != null )
        {
            int length = list.size() ;
            if ( length != 0 )
            {
                sb.append( list.get( 0 ).toString() ) ;
                for ( int i = 1 ; i < length ; i++ )
                {
                    sb.append( "," ) ;
                    sb.append( list.get( i ) ) ;
                }
            }
        }
        return sb.toString() ;
    }

    /**
     * ��֤�ַ����Ƿ�Ϊ����
     * @param str String
     * @return boolean
     */
    public static boolean checkInt( String str )
    {
        if ( str == null || str.equals( "" ) )
        {
            return false ;
        }
        char[] ch = str.toCharArray() ;
        for ( int i = 0 ; i < ch.length ; i++ )
        {
            if ( !Character.isDigit( ch[i] ) )
            {
                return false ;
            }
        }
        return true ;
    }


    /**
     * 8859ת��GBK����
     * @param str String
     * @return String
     */
    public static String iso8859toGBK(String str)
    {
        if(str != null)
        {
            try
            {
                String newstr = new String( str.getBytes( "iso-8859-1" ) , "GBK" ) ;
                return newstr ;
            }
            catch ( UnsupportedEncodingException ex )
            {
                ex.printStackTrace();
            }
        }
        return null ;
    }

    /**
     * ��ȡϵͳ���ļ��ָ���
     * @return String
     */
    public static String getFileSeparator()
    {
        return System.getProperty( "file.separator" ) ;
    }
 
    
    public static String getResourceBundleValue(ResourceBundle messages , String key){
    	String str = messages.getString(key);
		if(str != null){
			return iso8859toGBK(str);
		}
		return null ;
    }
    
    
    public static Map loadMapString(String str ,String split){
    	Map map = new HashMap();
    	if(str != null){
			String[] items = str.split(split); 
			for(int i=0 ; i<items.length ; i++){
				int index = items[i].indexOf("=");
				map.put(items[i].substring(0,index).trim(), items[i].substring(index+1).trim());
			}
    	}
    	return map ;
    }
    
}
