package oe.security3a.sso.util ;

import java.util.Properties ;
import org.apache.commons.logging.Log ;
import org.apache.commons.logging.LogFactory ;
import java.util.Enumeration ;
import java.net.URL;

/**
 * ��ȡ�����ļ��Ĺ�����
 *
 * @author not attributable
 * @version 1.0
 */
public class PropertiesConf {

    static Log log = LogFactory.getLog( PropertiesConf.class ) ;

    private ClassLoader loader ;

    private String filename ;

    private Properties prop ;

    /**
     *
     * @param filename String �ļ������ļ�Ҫ����classpath��
     */
    public PropertiesConf( String filename )
    {
        this.filename = filename ;
        load() ;
    }

    public PropertiesConf( String filename ,ClassLoader cl)
    {
        this.filename = filename ;
        this.loader = cl;
        load() ;
    }


    private void load()
    {
        ClassLoader[] cls = new ClassLoader[3];
        cls[0] = loader ;
        cls[1] = Thread.currentThread().getContextClassLoader() ;
        cls[2] = PropertiesConf.class.getClassLoader();

        boolean b = false ;
        for(int i = 0 ; i<cls.length ; i++){
            if(cls[i]!=null){
                try{
                    URL url = cls[i].getResource( filename ) ;
                    prop = new Properties() ;
                    prop.load( url.openStream() ) ;
                    log.debug("�����ļ�:"+filename+"��·��Ϊ:"+url);
                    b = true ;
                    break ;
                }
                catch(Exception ex){
                }
            }
        }
        if(!b){
            log.error("�����ļ���" + filename + "δ�ҵ���");
        }
    }

    /**
     * ��ȡ����ֵ��֧�����������ļ�
     * @param key String
     * @return String
     */
    public String getProperty( String key )
    {
        String value = prop.getProperty( key ) ;
        if(value != null)
        {
            return StringUtil.iso8859toGBK( value ) ;
        }
        return null ;
    }


    /**
     * ��ȡ���е�������
     * @return Enumeration
     */
    public Enumeration propertyNames()
    {
        return prop.propertyNames() ;
    }

}
