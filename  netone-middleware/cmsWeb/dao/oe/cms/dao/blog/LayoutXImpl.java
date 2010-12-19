package oe.cms.dao.blog;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oe.env.client.EnvService;
import oe.frame.orm.OrmerEntry;
import oe.frame.orm.util.IdServer;
import oe.rmi.client.RmiEntry;

import org.apache.commons.lang.StringUtils;

import oe.cms.cfg.CellInfo;
import oe.cms.cfg.TCmsInfocell;
import oe.cms.cfg.TCmsInfomodel;

public class LayoutXImpl implements LayoutX {

	/**
	 * 改变布局
	 */
	public boolean copyLayOut(String userid, TCmsInfomodel cim) {
		// 获得系统所有的布局

		if (cim != null) {
			List list = splitModelXml(cim);
			String infoxml = cim.getInfoxml();

			String xmlinfoNew = copyCellAndModifyModelxml(list, infoxml, userid);

			TCmsInfomodel quedim = new TCmsInfomodel();
			quedim.setParticipant(userid);
			List listModel = OrmerEntry.fetchOrmer().fetchQuerister()
					.queryObjects(quedim, null);
			if (listModel != null && listModel.size() > 0) {
				TCmsInfomodel curCim = (TCmsInfomodel) listModel.get(0);
				curCim.setInfoxml(xmlinfoNew);
				curCim.setExtendattribute(cim.getExtendattribute());
				OrmerEntry.fetchOrmer().fetchSerializer().update(curCim);
				return true;
			}
		}
		return false;

	}

	/**
	 * 将infomodel中的cellid 分离出来
	 * 
	 * @param cim
	 * @return
	 */
	private List splitModelXml(TCmsInfomodel cim) {
		String xmlinfo = cim.getInfoxml();
		String getInfo = StringUtils.substringBetween(xmlinfo, "infoCellid=\"",
				"\" xoffset=");
		List mapInfo = new ArrayList();
		while (getInfo != null) {
			mapInfo.add(getInfo);
			xmlinfo = StringUtils.substringAfter(xmlinfo, getInfo
					+ "\" xoffset=");
			getInfo = StringUtils.substringBetween(xmlinfo, "infoCellid=\"",
					"\" xoffset=");
		}
		return mapInfo;
	}

	/**
	 * 根据从infoxml中分离出来的cellid, 创建新的cell信息,内容拷贝自原的cellid, 并且将新的 cellid
	 * 替换infoxml中的cellid
	 * 
	 * @param modelkey
	 * @param userId
	 * @param infomodelxml
	 * @return
	 */
	private String copyCellAndModifyModelxml(List modelkey,
			String infomodelxml, String userId) {
		for (Iterator itr = modelkey.iterator(); itr.hasNext();) {
			String key = (String) itr.next();
			TCmsInfocell cell = (TCmsInfocell) OrmerEntry.fetchOrmer()
					.fetchQuerister().loadObject(TCmsInfocell.class, key);
			String keyAhead = "";
			if (key.length() > 3) {
				keyAhead = key.substring(0, 3);
			}
			String newid = keyAhead + IdServer.xnumID();
			TCmsInfocell cellNew = new TCmsInfocell();
			cellNew.setCellid(newid);
			cellNew.setParticipant(userId);
			cellNew.setBelongto(userId);
			cellNew.setIntime(CellInfo._IN_TIME);
			cellNew.setTypes(CellInfo._TYPE_PUBLIC);
			cellNew.setBody(cell.getBody());
			OrmerEntry.fetchOrmer().fetchSerializer().create(cellNew);

			infomodelxml = StringUtils.replace(infomodelxml, key, newid);
		}
		return infomodelxml;
	}

	public boolean initTemplate() {
		TCmsInfomodel model = new TCmsInfomodel();
		EnvService env;
		try {
			env = (EnvService) RmiEntry.iv("envinfo");
			String demouser = env.fetchEnvValue("demouser");
			model.setParticipant(demouser);
			List list = OrmerEntry.fetchOrmer().fetchQuerister().queryObjects(
					model, null);
			StringBuffer but = new StringBuffer();
			for (Iterator itr = list.iterator(); itr.hasNext();) {
				TCmsInfomodel modeTemp = (TCmsInfomodel) itr.next();
				but.append("," + modeTemp.getModelid());
			}
			env.addEnv("demoModelid", but.toString().substring(1));
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

		return true;
	}

	public boolean copyToTemplate(TCmsInfomodel cim) {

		EnvService env;
		String demouser = "";
		try {
			env = (EnvService) RmiEntry.iv("envinfo");
			demouser = env.fetchEnvValue("demouser");
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

		if (cim != null && demouser != null) {
			List list = splitModelXml(cim);
			String infoxml = cim.getInfoxml();

			String xmlinfoNew = copyCellAndModifyModelxml(list, infoxml,
					demouser);

			TCmsInfomodel curCim = new TCmsInfomodel();
			curCim.setDescription("模板描述:" + cim.getDescription());
			curCim.setModelname("模板:" + cim.getModelname());
			curCim.setInfoxml(xmlinfoNew);
			curCim.setHit(new Long(0));
			curCim.setExtendattribute(cim.getExtendattribute());
			curCim.setParticipant(demouser);
			curCim.setAccessmode(cim.getAccessmode());
			curCim.setModelid(new Long(IdServer.xnumID()));
			OrmerEntry.fetchOrmer().fetchSerializer().create(curCim);
			return true;
		}
		return false;
	}
}
