package oe.bi;

/**
 * 
 * @author chen.jia.xun
 *
 */
public class RootPath {

	private String datamodelpath;

	private String datasourcepath;
	
	private String contextPath=RootPath.class.getClassLoader().getResource("").getPath().replaceAll("%20"," ");

	public String getDatamodelpath() {
		//return contextPath+datamodelpath;
		return datamodelpath;
	}

	public void setDatamodelpath(String datamodelpath) {
		this.datamodelpath = datamodelpath;
	}

	public String getDatasourcepath() {
		return contextPath+datasourcepath;
	}

	public void setDatasourcepath(String datasourcepath) {
		this.datasourcepath = datasourcepath;
	}

}
