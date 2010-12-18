package oe.security3a.seucore.auditingser ;

import javax.servlet.http.HttpServletRequest ;
import javax.swing.JApplet ;

import oe.security3a.seucore.obj.OptrLogInfo;

public interface OptrLogUtil {

    public OptrLogInfo createOptrLogInfo( String ssosessionid , String optrid , String result , String remark );

    public OptrLogInfo createOptrLogInfo( HttpServletRequest request , String optrid , String result , String remark );
   
    public OptrLogInfo createOptrLogInfo( JApplet applet , String optrid , String result , String remark );
    
}
