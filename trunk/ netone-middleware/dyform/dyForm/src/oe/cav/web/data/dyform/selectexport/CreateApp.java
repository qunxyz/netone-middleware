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
 * ����̬�������ݵ���Ϊ��Դģ�͵���Ϣ����֧��ѡ��Ӧ��
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
	 * �ⲿʹ������������б���app����
	 * 
	 * @param formcode
	 */
	public static void todo(String formcode) {
		log.debug("ԭʼ����fromcode��" + formcode);
		createApp(formcode);
		addCopyFormResource(formcode);
		addDataFormDyform(formcode);
	}

	/**
	 * �Ѷ�Ӧ�ı����ݿ������´�����app��
	 * 
	 * @param formcode
	 */
	public static void addDataFormDyform(String formcode) {
		DyFormService dy;
		ApplicationRmi app;
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");

			// Ҫ��ӵ��ط�
			UmsProtectedobject aimobj = rsrmi.loadResourceByNatural(formcode
					+ "." + formcode);
			TCsBus tcsbus = new TCsBus();
			tcsbus.setFormcode(formcode);
			app = (ApplicationRmi) RmiEntry.iv("application");
			dy = (DyFormService) RmiEntry.iv("dyhandle");

			// ע�⣬������Ҫָ����Ҫ����������ȥ����Ŀ���ַ��ʲô��Ŀ�������´���������app��
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
				log.debug("ÿһ��Ҫ�ƶ�����Ŀ���ַ��" + parentdir);

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
	 * �Ѷ�Ӧ�����ݿ���������������Ŀ¼,����ֻ�ǿ�����app������Ӧ�ı����ݲ�û�п���
	 * 
	 * @param formcode
	 * @return
	 */
	public static void addCopyFormResource(String formcode) {
		DyFormService dy;
		ApplicationRmi app;
		try {
			ResourceRmi rsrmi = (ResourceRmi) RmiEntry.iv("resource");
			// ��һ������������app��Դ
			UmsProtectedobject upo = rsrmi.loadResourceByNatural(formcode + "."
					+ formcode);
			log.debug("��һ������������app��Դ:idΪ" + upo.getId());
			log.debug("��һ������������app��Դ:nameΪ" + upo.getName());
			log.debug("��һ������������app��Դ:ouΪ" + upo.getOu());

			app = (ApplicationRmi) RmiEntry.iv("application");
			dy = (DyFormService) RmiEntry.iv("dyhandle");
			TCsForm tCsForm = dy.loadForm(formcode);

			String naturalname = StringUtils.substringBetween(tCsForm
					.getBelongx(), "[", "]");
			String orinaturalname = naturalname + "." + naturalname;

			// ԭʼ��Դ
			UmsProtectedobject oriupo = rsrmi
					.loadResourceByNatural(orinaturalname);
			String oriupo_id = oriupo.getId();
			log.debug("ԭʼ��Դ��id:" + oriupo_id);
			log.debug("ԭʼ���ݵ�naturalname:" + orinaturalname);

			// ���ԭʼ��Դ����������Դ����Ϊ����������
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
	 * ��������fromcode,����app
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
			log.debug("ԭʼ����belongx:" + tCsForm.getBelongx());
			log.debug("ԭʼ�������ƣ�" + tCsForm.getFormname());
			String naturalname = StringUtils.substringBetween(tCsForm
					.getBelongx(), "[", "]");
			String name = tCsForm.getFormname();
			log.debug("����ԭʼ������app,��������Ϊ" + name + ",naturalnameΪ"
					+ naturalname);

			UmsApplication umsapp = new UmsApplication();
			umsapp.setName(name);// ��������
			umsapp.setNaturalname(formcode);// ����
			umsapp.setDescription(naturalname);// ����
			umsapp.setPassword("1");
			umsapp.setApptype("DYFORM");// ����
			umsapp.setCreated(new Date(System.currentTimeMillis()));
			umsapp.setExtendattribute("");// ��չ����
			umsapp.setActive("1");// ��Ч
			appid = app.create(umsapp);// ����

			if (appid == null) {
				log.info("�����Ѿ�����");
			} else {
				log.info("�����ɹ�,����������app��idΪ" + appid);
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
