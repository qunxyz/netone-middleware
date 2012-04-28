package com.jl.common.map;

import java.util.List;

import com.jl.common.map.obj.Gis;

/**
 * 装载地图业务逻辑
 * 
 * @author chenjx <br>
 *         email:oesee@139.com, tel:15860836998
 */
public interface GisIfc {
	/**
	 * 获得地图业务逻辑对象
	 * 
	 * @param url
	 *            地图业务逻辑配置文档地址
	 * @return
	 */
	Gis load(String url);

	/**
	 * 基于配置的钻取规则，钻取地图的下一层数据
	 * 
	 * @param gis
	 *            地图业务对象,定义钻取规则
	 * @param flowId
	 *            钻取流程节点
	 * @param parentstep
	 *            当前的被钻取的点的层次位置 是Step的 index值
	 * @param areapointId
	 *            当前被钻取的点的区域ID，该值不允许为空，即使用户点业务节点进入下一级，但是由于业务节点本身子带区域id，所以要连带传入
	 * @param busspointId
	 *            当前被钻取的点的业务ID，该值可以为空，当点击行政区域进入下一级时允许该值为空
	 * @param extendbussinfo
	 *            扩展业务属性，用于进一步过滤业务数据用，比如：会按照市场或者类型来过滤经销商
	 * 
	 * @return
	 */
	List treeData(Gis gis, String flowId, int parentstep, String areapointId,
			String busspointId, String extendbussinfo);

	/**
	 * 平行展示数据，不提供钻取能力，该应用的价值在于方便客户在地图上看分布情况，<br>
	 * 
	 * @param picid
	 *            图层id
	 * @param busslevel
	 *            公司的级别
	 * @param busspointId
	 *            当前被钻取的点的业务ID，该值可以为空，当点击行政区域进入下一级时允许该值为空
	 * @param extendbussinfo
	 *            扩展业务属性，用于进一步过滤业务数据用，比如：会按照市场或者类型来过滤经销商
	 * @return
	 */
	List lineData(String picid, String busslevel,String busspointId, String extendbussinfo);

}
