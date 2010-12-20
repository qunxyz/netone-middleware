package oe.cav.bean.logic.tools;

public interface DyObjFromOeForm {

	/**
	 * 根据systgemid从oeForm创建DyObj对象
	 * 
	 * @param systemid
	 * @return
	 */
	DyObj[] parser(String systemid);

}
