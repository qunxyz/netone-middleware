package oe.cav.web.data.dyform.selectexport;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import oe.cav.bean.logic.bus.TCsBus;
import oe.cav.bean.logic.form.TCsForm;
import oe.midware.dyform.service.DyFormService;
import oe.rmi.client.RmiEntry;
import oe.security3a.client.rmi.ApplicationRmi;
import oe.security3a.client.rmi.ResourceRmi;
import oe.security3a.seucore.obj.ProtectedObjectReference;
import oe.security3a.seucore.obj.db.UmsApplication;
import oe.security3a.seucore.obj.db.UmsProtectedobject;

/**
 * 将动态表单的数据导入为资源模型的信息，以支持选择应用
 * 
 * @author wu.shang.zhan
 * 
 */
public class CreateApp {

	static Log log = LogFactory.getLog(CreateApp.class);

	// public static void main(String[] args) {
	// todo("3471085bc43011dd9bf0e951f4a818b4_");
	// }
	/**
	 * 外部使用这个方法进行表单和app关联
	 * 
	 * @param formcode
	 */
	public static void todo(String formcode) {
		log.debug("原始表单的fromcode是" + formcode);
		createApp(formcode);
		addCopyFormResource(formcode);
		addDataFormDyform(formcode);
	}

	/**
	 * 把对应的表单数据拷贝到新创建的app中
	 * 
	 * @param formcode
	 */
	public static void addDataFormDyform(String formcode) {
		DyFormService dy;
		ApplicationRmi app;
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");

			// 要添加到地方
			UmsProtectedobject aimobj = rsrmi.loadResourceByNatural(formcode
					+ "." + formcode);
			TCsBus tcsbus = new TCsBus();
			tcsbus.setFormcode(formcode);
			app = (ApplicationRmi) RmiEntry.iv("application");
			dy = (DyFormService) RmiEntry.iv("dyhandle");

			// 注意，这里需要指定是要拷贝到哪里去，即目标地址是什么，目标是在新创建出来的app中
			List list = dy.queryData(tcsbus, 0, 10000, "");
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				TCsBus tCsBus1 = (TCsBus) iterator.next();
				log.debug(tCsBus1.getBelongx() + "===" + tCsBus1.getColumn3());
				String belongx = tCsBus1.getBelongx();
				String parentdir = StringUtils.substringBetween(belongx, "[",
						"]");

				parentdir = StringUtils.substringAfter(parentdir, ".");
				parentdir = StringUtils.substringAfter(parentdir, ".");
				parentdir = formcode + "." + formcode + "." + parentdir;
				log.debug("每一次要移动到的目标地址是" + parentdir);

				UmsProtectedobject upo = new UmsProtectedobject();

				upo.setNaturalname(tCsBus1.getColumn3());
				upo.setName(tCsBus1.getColumn3());
				upo.setActionurl("");
				upo.setExtendattribute("");
				upo.setObjecttype("DYFORM");
				upo.setDescription("");
				upo.setInclusion(ProtectedObjectReference._OBJ_INCLUSTION_YES);
				upo.setActive("1");
				Serializable serializable = rsrmi.addResource(upo, parentdir);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 把对应的数据拷贝，不拷贝顶层目录,这里只是拷贝了app，而对应的表单数据并没有拷贝
	 * 
	 * @param formcode
	 * @return
	 */
	public static void addCopyFormResource(String formcode) {
		DyFormService dy;
		ApplicationRmi app;
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			// 上一步创建出来的app资源
			UmsProtectedobject upo = rsrmi.loadResourceByNatural(formcode + "."
					+ formcode);
			log.debug("上一步创建出来的app资源:id为" + upo.getId());
			log.debug("上一步创建出来的app资源:name为" + upo.getName());
			log.debug("上一步创建出来的app资源:ou为" + upo.getOu());

			app = (ApplicationRmi) RmiEntry.iv("application");
			dy = (DyFormService) RmiEntry.iv("dyhandle");
			TCsForm tCsForm = dy.loadForm(formcode);

			String naturalname = StringUtils.substringBetween(tCsForm
					.getBelongx(), "[", "]");
			String orinaturalname = naturalname + "." + naturalname;

			// 原始资源
			UmsProtectedobject oriupo = rsrmi
					.loadResourceByNatural(orinaturalname);
			String oriupo_id = oriupo.getId();
			log.debug("原始资源的id:" + oriupo_id);
			log.debug("原始数据的naturalname:" + orinaturalname);

			// 获得原始资源的所有子资源，作为拷贝的数据
			List subList = rsrmi.subResource(oriupo_id);

			String[] naturalnames = new String[subList.size()];
			int i = 0;
			for (Iterator iterator = subList.iterator(); iterator.hasNext();) {
				UmsProtectedobject object = (UmsProtectedobject) iterator
						.next();
				naturalnames[i] = object.getNaturalname();
				i++;
			}
			rsrmi.addFormCopyResource(upo.getId(), naturalnames, 1000);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 给定表单的fromcode,创建app
	 * 
	 * @param formcode
	 * @return
	 */
	public static String createApp(String formcode) {
		String appid = "";
		DyFormService dy;
		ApplicationRmi app;
		try {
			app = (ApplicationRmi) RmiEntry.iv("application");
			dy = (DyFormService) RmiEntry.iv("dyhandle");
			TCsForm tCsForm = dy.loadForm(formcode);
			log.debug("原始表单的belongx:" + tCsForm.getBelongx());
			log.debug("原始表单的名称：" + tCsForm.getFormname());
			String naturalname = StringUtils.substringBetween(tCsForm
					.getBelongx(), "[", "]");
			String name = tCsForm.getFormname();
			log.debug("利用原始表单创建app,中文名称为" + name + ",naturalname为"
					+ naturalname);

			UmsApplication umsapp = new UmsApplication();
			umsapp.setName(name);// 中文名称
			umsapp.setNaturalname(formcode);// 名称
			umsapp.setDescription(naturalname);// 描述
			umsapp.setPassword("1");
			umsapp.setApptype("DYFORM");// 类型
			umsapp.setCreated(new Date(System.currentTimeMillis()));
			umsapp.setExtendattribute("");// 扩展属性
			umsapp.setActive("1");// 有效
			appid = app.create(umsapp);// 创建

			if (appid == null) {
				log.info("名称已经存在");
			} else {
				log.info("创建成功,创建产生的app的id为" + appid);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		return appid;
	}
}
