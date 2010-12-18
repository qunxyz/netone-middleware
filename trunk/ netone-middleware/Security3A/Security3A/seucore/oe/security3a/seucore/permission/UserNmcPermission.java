package oe.security3a.seucore.permission ;

import java.security.Permission ;
import java.util.Iterator ;
import java.util.ArrayList ;

/**
 * ���NmcPermissionCollection�ļ��ϣ�Ȩ���ж�ȡȨ�����ֵ
 * @author hls
 * @version 1.0
 */
public abstract class UserNmcPermission implements java.io.Serializable{

    ArrayList rnplist = new ArrayList() ;

    public UserNmcPermission()
    {
    }


    public void add( NmcPermissionCollection rnp )
    {
        rnplist.add( rnp ) ;
    }


    /**
     * ����Ȩ��ֵ
     * @param permission Permission
     * @return int Ϊ0��ʾû��Ȩ�ޣ�1��ʾ�ɼ���3��ʾ�ɶ���7��ʾ��д
     */
    public int getPermissionActions( Permission permission )
    {
        int privilege = 0 ;
        if ( permission instanceof NmcPermission )
        {
            Iterator rnpiter = rnplist.iterator() ;
            while ( rnpiter.hasNext() )
            {
                NmcPermissionCollection rnp = ( NmcPermissionCollection )rnpiter.next() ;
                int i = rnp.getPermissionActions( permission ) ;
                if ( i >= NmcPermission.VISIABLE )
                {
                    privilege = Math.max( i , privilege ) ;
                }
                if ( privilege == NmcPermission.WRITE )
                {
                    return privilege ;
                }
            }
        }
        return privilege ;
    }


    /**
     * Ȩ���ж�
     * @param permission Permission
     * @return boolean
     */
    public boolean implies( Permission permission )
    {
        if ( permission instanceof NmcPermission )
        {
        	NmcPermission np = (NmcPermission)permission;
            boolean success = false ;
            Iterator rnpiter = rnplist.iterator() ;
            while ( rnpiter.hasNext() )
            {
            	np.setMatched(false);
                int actions = 0 ;
                NmcPermissionCollection rnp = ( NmcPermissionCollection )rnpiter.next() ;
                if ( rnp.implies( np ) )
                {
                    success = true ;
                    return true ;
                    
                    //hls 0724
//                    int ac = Integer.parseInt( np.getActions() ) ;
//                    if ( ac > actions )
//                    {
//                        actions = ac ;
//                        if ( ac == NmcPermission.WRITE )
//                        {
//                            return true ;
//                        }
//                    }
                }
            }
            if ( success )
            {
                return true ;
            }
        }
        return false ;
    }

}
