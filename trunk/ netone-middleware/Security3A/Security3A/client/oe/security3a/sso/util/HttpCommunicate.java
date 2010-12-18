package oe.security3a.sso.util ;

import java.net.URL ;
import java.io.* ;
import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;
import java.net.URLConnection ;


/**
 * HTTP�Ŀͻ���
 *
 * @author hls
 * @version 1.0
 */
public class HttpCommunicate {

    static Log log = LogFactory.getLog( HttpCommunicate.class ) ;

    private HttpCommunicate()
    {
    }

    /**
     * ����http����
     * @param urlstr String  Ŀ��URL��ַ
     * @param sessionid String  ��Ŀ��server��sessionid
     * @return String        HttpResponse
     * @throws Exception
     */
    public static String httpRequest( String urlstr , String sessionid ) throws IOException
    {
    	int i = urlstr.indexOf("?");
        if(i != -1){
        	urlstr = urlstr.substring(0,i)+";jsessionid=" + sessionid+urlstr.substring(i);
        }
        else{
        	urlstr = urlstr + ";jsessionid=" + sessionid;
        }
        URL url = new URL( urlstr ) ;
        try
        {
            URLConnection urlconn = url.openConnection() ;
            InputStream is = urlconn.getInputStream() ;
            BufferedReader r = new BufferedReader( new InputStreamReader( is ) ) ;
            String line ;
            StringBuffer sb = new StringBuffer() ;
            while ( ( line = r.readLine() ) != null )
            {
                sb.append( line ) ;
            }
            r.close();
            return sb.toString();
        }
        catch ( IOException ex )
        {
            log.error( "����http����ʧ�ܣ�url:" + urlstr ) ;
            throw ex ;
        }
    }


    public static String httpRequest( String urlstr ) throws IOException
   {
       URL url = new URL( urlstr ) ;
       try
       {
           URLConnection urlconn = url.openConnection() ;
           InputStream is = urlconn.getInputStream() ;
           BufferedReader r = new BufferedReader( new InputStreamReader( is ) ) ;
           String line ;
           StringBuffer sb = new StringBuffer() ;
           while ( ( line = r.readLine() ) != null )
           {
               sb.append( line ) ;
           }
           r.close();
           return sb.toString();
       }
       catch ( IOException ex )
       {
           log.error( "����http����ʧ�ܣ�url:" + urlstr ) ;
           throw ex ;
       }
   }


}
