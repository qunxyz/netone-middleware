package oe.cms.datasource;

import java.util.List;

/**
 * 数据出口
 * 
 * @author chen.jia.xun(Robanco)
 * 
 */
public interface DataAccess {
	/**
	 * 通过数据源ID，获得该数据源的真实数据
	 * 
	 * @param sourceid
	 * @return 返回结果List中元素是Map， List元素的个数至少有两个，第一个是元素是对整个List中的元素值的标注信息,它用SQLID.ColumnID.值作为KEY
	 * 
	 * 目前需要实现一个基于SQL的实现类 其中sourceid的格式是SQLID.ColumnID,需要根据这两个ID取获取SQL语句和字段
	 * 然后调用DataAccess的方法，是用基于SQL的驱动获得数据返回
	 */
	List fetchData(String sourceid);

}
