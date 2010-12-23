package oe.bi.etl.obj;

import java.io.Serializable;

public class DimensionElement implements Serializable{
	/**
	 * 维度id(最小粒度)
	 * 
	 */
	private String id;

	private String name;

	/**
	 * 维度值
	 */
	private String[] choicenode;

	/**
	 * 维度值名字
	 */
	private String[] choicenodename;

	/**
	 * 对应的相关归并粒度中的字段ID
	 */
	private String levelcolumnid;

	/**
	 * 是否排序
	 */
	private boolean order;

	/**
	 * 要降序吗
	 */
	private boolean desc;

	public boolean isDesc() {
		return desc;
	}

	public void setDesc(boolean desc) {
		this.desc = desc;
	}

	public boolean isOrder() {
		return order;
	}

	public void setOrder(boolean order) {
		this.order = order;
	}

	public String[] getChoicenode() {
		return choicenode;
	}

	public void setChoicenode(String[] choicenode) {
		this.choicenode = choicenode;
	}

	public String getLevelcolumnid() {
		return levelcolumnid;
	}

	public void setLevelcolumnid(String levelcolumnid) {
		this.levelcolumnid = levelcolumnid;
	}

	public String[] getChoicenodename() {
		return choicenodename;
	}

	public void setChoicenodename(String[] choicenodename) {
		this.choicenodename = choicenodename;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
