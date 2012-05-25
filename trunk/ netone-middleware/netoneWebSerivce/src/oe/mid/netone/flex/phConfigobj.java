package oe.mid.netone.flex;

/**
 * 资源
 * 
 * @version 1.0.0
 * @history
 */
public final class phConfigobj {

	/** 资源编码 */
	private String naturalname;
	/** 资源名称 */
	private String resourcename;
	/** 资源类型 */
	private String types;
	/**文件与文件夹的区别**/
	private String distinguish;
 
	private String extendattribute;
	private String imagename;

 
	public String getExtendattribute() {
		return extendattribute;
	}

	public void setExtendattribute(String extendattribute) {
		this.extendattribute = extendattribute;
	}
	public String getNaturalname() {
		return naturalname;
	}

	public void setNaturalname(String naturalname) {
		this.naturalname = naturalname;
	}

	public String getResourcename() {
		return resourcename;
	}

	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}

	public String getTypes() {
		return types;
	}

	public void setTypes(String types) {
		this.types = types;
	}
	public String getDistinguish() {
		return distinguish;
	}

	public void setDistinguish(String distinguish) {
		this.distinguish = distinguish;
	}

	public String getImagename() {
		return imagename;
	}

	public void setImagename(String imagename) {
		this.imagename = imagename;
	}

}
