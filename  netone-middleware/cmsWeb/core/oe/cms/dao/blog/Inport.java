package oe.cms.dao.blog;

import java.io.InputStream;

/**
 * 导入个人资源
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface Inport {
	void inport(InputStream input, String participant);
}
