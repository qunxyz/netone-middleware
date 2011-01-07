package oe.web.tag.mail;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import oe.env.client.EnvService;
import oe.rmi.client.RmiEntry;

public class GetMailLinkUrlTag extends SimpleTagSupport {

	public void doTag() throws JspException {
		// HttpServletRequest request = (HttpServletRequest) this.pageContext
		// .getRequest();
		// this.pageContext.setAttribute("totalcount",
		// mail.getString("mail.url"));
		try {
			EnvService env = (EnvService) RmiEntry.iv("envinfo");
			String url = env.fetchEnvValue("mail.url");
			getJspContext().getOut().print(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
