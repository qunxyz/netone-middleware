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
		System.out.println("�ӱ����ݹ����Ĳ���:FOMapping�Ľ��");
		System.out.println(formx.getName());
		System.out.println(formx.getAge());
		System.out.println(formx.getDescription());

		System.out.println("׼��������������:OFMapping����");
		formx.setName(formx.getName() + "_update");
		formx.setAge(formx.getAge() + 1);
		formx.setDescription(formx.getDescription() + "_update");
		// ��ת��Ŀ��ҳ��,��Struts-config-simple�����õ�,���ʱ���㽫�ᷢ��
		// Ŀ��ҳ�����ر��ֶ��е���ֵ���������õ���һ�µ�,�������OFMapping�Ľ��
		return mapping.getInputForward();

	}
}
