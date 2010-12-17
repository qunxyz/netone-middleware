package oe.teach.web.base.simplestruts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * 
 * @author chen.jia.xun(Robanco) <br>
 *         mail:56414429@qq.com,chenjiaxun@oesee.com <br>
 */
public class SimpleAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		SimpleForm formx = (SimpleForm) form;
		System.out.println("从表单传递过来的参数:FOMapping的结果");
		System.out.println(formx.getName());
		System.out.println(formx.getAge());
		System.out.println(formx.getDescription());

		System.out.println("准备往表单传递数据:OFMapping操作");
		formx.setName(formx.getName() + "_update");
		formx.setAge(formx.getAge() + 1);
		formx.setDescription(formx.getDescription() + "_update");
		// 跳转到目标页面,在Struts-config-simple中配置的,这个时候你将会发现
		// 目标页面的相关表单字段中的数值与这里设置的是一致的,这个就是OFMapping的结果
		return mapping.getInputForward();

	}
}
