package oe.bi.etl.obj;

import java.io.Serializable;

public class DimensionElement implements Serializable{
	/**
	 * ά��id(��С����)
	 * 
	 */
	private String id;

	private String name;

	/**
	 * ά��ֵ
	 */
	private String[] choicenode;

	/**
	 * ά��ֵ����
	 */
	private String[] choicenodename;

	/**
	 * ��Ӧ����ع鲢�����е��ֶ�ID
	 */
	private String levelcolumnid;

	/**
	 * �Ƿ�����
	 */
	private boolean order;

	/**
	 * Ҫ������
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
