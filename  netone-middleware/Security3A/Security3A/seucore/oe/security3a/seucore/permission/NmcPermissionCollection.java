package oe.security3a.seucore.permission ;

import java.security.PermissionCollection;
import java.security.Permission ;
import java.util.Enumeration ;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.Collections;
import java.util.Iterator;

/**
 * Ȩ�޵ļ�����,ʵ��Ȩ���жϰ�����ӽ���ԭ���ж�
 *
 * @author hls
 * @version 1.0
 */
public class NmcPermissionCollection extends PermissionCollection {


    private TreeSet npset = null ;


    public NmcPermissionCollection()
    {
        this.npset = new TreeSet( new NmcpComparer() ) ;
    }


    public void clear()
    {
        npset.clear() ;
    }



    public void add( Permission permission )
    {
        if(permission instanceof NmcPermission)
        {
            npset.add(permission);
        }
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
            if ( npset.size() > 0 )
            {
                NmcPermission np = ( NmcPermission )permission ;
                Iterator setiter = npset.iterator() ;
                while ( setiter.hasNext() )
                {
                    NmcPermission np1 = ( NmcPermission )setiter.next() ;
                    int i = np1.getPermissionActions(np);
                    if(i >= NmcPermission.VISIABLE)
                    {
                        privilege = i ;
                    }
                    if(privilege>=NmcPermission.READ)
                    {
                        return privilege ;
                    }
                }
            }
        }
        return privilege ;
    }

    /**
     * �ж��Ƿ���Ȩ��
     * @param permission Permission
     * @return boolean
     */
    public boolean implies( Permission permission )
    {
        if ( permission instanceof NmcPermission )
        {
            if ( npset.size() > 0 )
            {
                NmcPermission np = ( NmcPermission )permission ;
                Iterator setiter = npset.iterator() ;
                while ( setiter.hasNext() )
                {
                    NmcPermission np1 = ( NmcPermission )setiter.next() ;
                    boolean b = np1.implies( np ) ;
                    if ( b )
                    {
                        return true ;
                    }
                    else{
                    	//������û��ƥ�䵽
                    	if(np.isMatched()){
                    		return b ;
                    	}
                    }
                }
            }
        }
        return false ;
    }


    public Enumeration elements()
    {
        return Collections.enumeration(npset) ;
    }





//    public static void main( String[] args )
//    {
//        TreeSet hs = new TreeSet(new NmcpComparer());
//        FuncPermission fp1 = new FuncPermission("0.11.*","1","0.11.*");
//        FuncPermission fp2 = new FuncPermission("0.15.*","1","0.15.*");
//        FuncPermission fp3 = new FuncPermission("0.12.1201.*","1","0.12.1201.*");
//        FuncPermission fp4 = new FuncPermission("0.*","1","0.*");
//        hs.add(fp1);
//        hs.add(fp2);
//        hs.add(fp3);
//        hs.add(fp4);
//        System.out.println( "hs:"+hs ) ;
//    }

}
class NmcpComparer
   implements Comparator,java.io.Serializable {
   public int compare( Object o1 , Object o2 )
   {
       if ( o1 instanceof NmcPermission && o2 instanceof NmcPermission )
       {
           NmcPermission np1 = ( NmcPermission )o1 ;
           NmcPermission np2 = ( NmcPermission )o2 ;
           int length1 = np1.getDnLength() ;
           int length2 = np2.getDnLength() ;
//           String dn1 = np1.getDn();
//           String dn2 = np2.getDn();
           if ( length1 < length2 )
           {
               return 1 ;
           }
           else
           {
               return -1 ;
           }
       }
       return -1 ;
   }


   public boolean equals( Object obj )
   {
       return false ;
   }

}

