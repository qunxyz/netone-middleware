package oe.security4a.severlet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
/**
 * Õº–Œ»œ÷§¬Î
 * @author chenjx <br> mail:15860836998@139.com
 *
 */
public class ImageCodeCheck {
	public static boolean check(HttpServletRequest request) {
		String rand = (String) request.getSession().getAttribute("rand");
		String imagecode = request.getParameter("imagecode");
		request.getSession().setAttribute("rand", null);
		if (StringUtils.isEmpty(rand) && StringUtils.isEmpty(imagecode)) {
			return true;
		} else if (StringUtils.isNotEmpty(rand) && StringUtils.isNotEmpty(imagecode) && rand.equals(imagecode)) {
			return true;
		}
		return false;
	}
}
