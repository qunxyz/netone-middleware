package oe.cms.dao.blog;

import javax.servlet.http.HttpServletResponse;

/**
 * �������еĸ�����Դ
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface Export {
	void export(String particiapnt, HttpServletResponse response);
}
