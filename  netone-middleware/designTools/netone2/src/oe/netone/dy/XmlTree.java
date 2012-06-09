package  oe.netone.dy;
import com.jl.common.netone.NetoneXmlTree;

public class XmlTree {
	/**
	 * @param args
	 */
    //返回名字和naturalname
	public String dataxml(String path) {
		String cname=null;
		if (path.equals("REPORTDS")) {
			cname="报表管理";
		}
		if (path.equals("BUSSFORM")) {
			cname="业务表单";
		}
		if (path.equals("DATASOURCE")) {
			cname="驱动选择";
		}
		if (path.equals("FRAMEPG")) {
			cname="页框选择";
		}
		if (path.equals("APPFRAME")) {
			cname="框架选择";
		}
		if (path.equals("DATASOURCE")) {
			cname="数据源选择";
		}
		if (path.equals("CSSFILE")) {
			cname="样式选择";
		}
		if (path.equals("BUSSWF")) {
			cname="流程选择";
		}
		if(path.equals("REPORTQ")){
			cname="报表集选择";
		}
		if(path.equals("JSPAPP")){
			cname="页选择";
		}
		if(path.equals("PORTALPG")){
			cname="portal选择";
		}
		if(path.equals("SOASCRIPT")){
			cname="SOA选择";
		}
		NetoneXmlTree dxml=new NetoneXmlTree();
		return dxml.dataxml(path, cname);
	}
	 //返回名字和formcode
	public String formdataxml(String path) {
		String cname=null; 
		if (path.equals("BUSSFORM")) {
			cname="业务表单";
		}
		if(path.toLowerCase().equals("dyview")){
			cname="视图表单";
		}
		NetoneXmlTree dxml=new NetoneXmlTree();
		return dxml.dyfromdataxml(path, cname);
	}
	//类型区分
	public String Reportds(String path,String type) {
		String cname=null; 
		if (type.equals("chart")) {
			cname="图表选择";
		}
		if (type.equals("Report")) {
			cname="报表选择";
		}
		NetoneXmlTree dxml=new NetoneXmlTree();
		return dxml.Reportdsxml(path, cname, type);
	}
	
}
