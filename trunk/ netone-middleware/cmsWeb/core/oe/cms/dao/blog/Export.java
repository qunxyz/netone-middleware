package oe.cms.dao.blog;

import javax.servlet.http.HttpServletResponse;

/**
 * 导出所有的个人资源
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface Export {
	void export(String particiapnt, HttpServletResponse response);
}
