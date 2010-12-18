package oe.security3a.seucore.permission ;

import java.security.* ;


/**
 * 用户权限对象的基类，实现dn模式的授权和鉴权
 * @author hls
 * @version 1.0
 */
public class NmcPermission
    extends Permission {

    public final static int VISIABLE = 0x1 ;

    public final static int READ = 0x3 ;

    public final static int WRITE = 0x7 ;
    
    public final static String VISIABLE_STR = "1";
    
    public final static String READ_STR = "3";
    
    public final static String WRITE_STR = "7";
    
    protected boolean matched = false ;

    protected String dn ;

    protected String showdn ;

    protected String actions ;

    /**
     * 构造函数
     * @param dn String       权限dn串
     * @param actions String  权限值
     */
    public NmcPermission( String dn , String actions )
    {
        this( dn , actions , "" ) ;
    }

    /**
     * 构造函数
     * @param dn String        权限dn串
     * @param actions String   权限值
     * @param showdn String    dn串显示名
     */
    public NmcPermission( String dn , String actions , String showdn )
    {
        super( dn ) ;
        this.dn = dn ;
        this.actions = actions ;
        this.showdn = showdn ;
    }


    public String getDn()
    {
        return dn ;
    }


    public String getShowdn()
    {
        return showdn ;
    }


    public void setDn( String dn )
    {
        this.dn = dn ;
    }


    public void setShowdn( String showdn )
    {
        this.showdn = showdn ;
    }


    public void setActions( String actions )
    {
        this.actions = actions ;
    }


    public String getActions()
    {
        return actions ;
    }


    public boolean equals( Object obj )
    {
        if ( obj instanceof NmcPermission )
        {
            NmcPermission np = ( NmcPermission )obj ;
            return np.getDn().equals( dn ) && np.getActions().equals( actions ) ;
        }
        return false ;
    }


    public int hashCode()
    {
        String str = dn + actions ;
        return str.hashCode() ;
    }


    /**
     * 返回permission的权限
     * @param permission Permission
     * @return String 为0表示没有权限 ； 1表示可见，3表示可读，7表示读写
     */
    public int getPermissionActions( Permission permission )
    {
        if ( permission instanceof NmcPermission )
        {
            NmcPermission fp = ( NmcPermission )permission ;
            int thislength = getDnLength() ;
            int thatlength = fp.getDnLength() ;
            String thisdn = dn ;
            String thatdn = fp.getDn() ;
            int thisaction = Integer.parseInt( actions ) ;

            if ( thatlength < thislength )
            {
                //看看是否有显示的权限。
                if ( thisdn.startsWith( thatdn ) )
                {
                    return VISIABLE ;
                }
            }
            else if ( thatlength == thislength )
            {
                if ( thisdn.startsWith( thatdn ) )
                {
                    if ( thisdn.endsWith( "-" ) )
                    {
                        return VISIABLE ;
                    }
                    else
                    {
                        //末尾为“*” 或者本身
                        return thisaction ;
                    }
                }
            }
            else
            {
                if ( thisdn.endsWith( "*" ) || thisdn.endsWith( "-" ) )
                {
                    String temp = thisdn.substring( 0 , thisdn.lastIndexOf( "." ) ) ;
                    if ( thatdn.startsWith( temp ) )
                    {
                        return thisaction ;
                    }
                }
            }
        }
        return 0 ;
    }


    /**
     * 功能鉴权
     * @param permission Permission
     * @return boolean
     */
    public boolean implies( Permission permission )
    {
        if ( permission instanceof NmcPermission )
        {
            NmcPermission fp = ( NmcPermission )permission ;
            int thislength = getDnLength() ;
            int thatlength = fp.getDnLength() ;
            String thisdn = dn ;
            String thatdn = fp.getDn() ;
            int thisaction = Integer.parseInt( actions ) ;
            int thataction = Integer.parseInt( fp.getActions() ) ;

            if ( thatlength < thislength )
            {
                //看看是否有显示的权限。
                if ( thisdn.startsWith( thatdn ) )
                {
                    if ( thataction == VISIABLE )
                    {
                        return true ;
                    }
                }
            }
            else if ( thatlength == thislength )
            {
                if ( thisdn.startsWith( thatdn ) )
                {
                    if ( thisdn.endsWith( "-" ) )
                    {
                        if ( thataction == VISIABLE )
                        {
                            return true ;
                        }
                        else
                        {
                            return false ;
                        }
                    }
                    else
                    {
                        //末尾为“*” 或者本身
                    	fp.setMatched(true);
                        if ( containAction(thisaction,thataction) )
                        {
                            return true ;
                        }
                        else
                        {
                            return false ;
                        }
                    }
                }
            }
            else
            {
                if ( thisdn.endsWith( "*" ) || thisdn.endsWith( "-" ) )
                {
                    String temp = thisdn.substring( 0 , thisdn.lastIndexOf( "." ) ) ;
                    if ( thatdn.startsWith( temp ) )
                    {
                    	fp.setMatched(true);
                        if ( containAction(thisaction,thataction))
                        {
                            return true ;
                        }
                        else
                        {
                            return false ;
                        }
                    }
                }
            }
        }
        return false ;
    }

    
    public  static boolean containAction(int thisaction , int thataction){
    	char[] chthis = Integer.toBinaryString(thisaction).toCharArray();
    	char[] chthat = Integer.toBinaryString(thataction).toCharArray();
    	
    	if(chthis.length >= chthat.length){
	    	for(int m=1 ; m<=chthat.length ; m++){
				if(chthat[chthat.length-m] > chthis[chthis.length-m]){
					return false ;
				}
			}
	    	return true ;
    	}
    	else{
    		return false ;
    	}
    }
    

    public int getDnLength()
    {
        String[] str = dn.split( "\\." ) ;
        String last = str[str.length - 1] ;
        if ( last.equals( "*" ) || last.equals( "-" ) || last.equals( "" ) )
        {
            return str.length - 1 ;
        }
        else
        {
            return str.length ;
        }
    }


    /**
     * 权限信息转换为字符表示
     * @param action int
     * @return String   返回只读“READ”，读写“WRITE”和 没有权限“NONE”
     */
    public static String getStrPrivilege( int actions )
    {
        switch ( actions )
        {
            case READ:
                return "READ" ;
            case WRITE:
                return "WRITE" ;
            default:
                return "NONE" ;
        }
    }


    public static String getStrPrivilege( String actions )
    {
        return getStrPrivilege( Integer.parseInt( actions ) ) ;
    }

    
    boolean isMatched(){
    	return this.matched;
    }
    
    void setMatched(boolean b){
    	this.matched = b ;
    }
    
    
    public String toString()
    {
        return showdn + " [" + getStrPrivilege( actions ) + "]" ;
    }
    
}
