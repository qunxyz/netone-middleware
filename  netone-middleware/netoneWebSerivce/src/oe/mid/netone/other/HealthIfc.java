package oe.mid.netone.other;

import java.sql.Timestamp;

/**
 * 健康数据接口
 * 
 * @author jiaxun.chen (Robanco)<br> 
 *    email:oesee@139.com <br>
 *    tel:86+18060473012<br>
 *    my blog:http://blog.sina.com.cn/robanco
 *
 */

public interface HealthIfc {
	/**
	 * 运动时间累计
	 * @param from 开始时间(用于查询的时间起始点)
	 * @param to 结束时间(用于查询的时间结束点)
	 * @param userName 用户名(用户查询是谁的运动时间
	 * @param sportType 运动类型( 01 是走路,02 是登高 03 是跑步)
	 * @param equipmentType 运动时使用的设备类型 (01计步表 02 登高表 03 轨迹表)
	 * @return 时间累计值（单位是秒）
	 */
	long  sportTimeTotal(Timestamp from,Timestamp to,String userName,String sportType,String equipmentType);
	/**
	 * 运动距离累计
	 * @param from 开始时间(用于查询的时间起始点)
	 * @param to 结束时间(用于查询的时间结束点)
	 * @param userName 用户名(用户查询是谁的运动距离
	 * @param sportType 运动类型( 01 是走路,02 是登高 03 是跑步)
	 * @param equipmentType 运动时使用的设备类型 (01计步表 02 登高表 03 轨迹表)
	 * @return 运动距离累计值（单位是公里）
	 */
	double  sportDistanceTotal(Timestamp from,Timestamp to,String userName,String sportType,String equipmentType);
	/**
	 * 运动卡洛里消耗累计
	 * @param from 开始时间(用于查询的时间起始点)
	 * @param to 结束时间(用于查询的时间结束点)
	 * @param userName 用户名(用户查询是谁的运动距离
	 * @param sportType 运动类型( 01 是走路,02 是登高 03 是跑步)
	 * @param equipmentType 运动时使用的设备类型 (01计步表 02 登高表 03 轨迹表)
	 * @return 卡洛里消耗累计值（单位是千卡）
	 */	
	double  caloryUseTotal(Timestamp from,Timestamp to,String userName,String sportType,String equipmentType);
	
	/**
	 * 所有人运动时间累计
	 * @param from 开始时间(用于查询的时间起始点)
	 * @param to 结束时间(用于查询的时间结束点)
	 * @param sportType 运动类型( 01 是走路,02 是登高 03 是跑步)
	 * @param equipmentType 运动时使用的设备类型 (01计步表 02 登高表 03 轨迹表)
	 * @return 时间累计值（单位是秒）
	 */
	long  sportTimeTotal(Timestamp from,Timestamp to,String sportType,String equipmentType);
	/**
	 * 所有人运动距离累计
	 * @param from 开始时间(用于查询的时间起始点)
	 * @param to 结束时间(用于查询的时间结束点)
	 * @param sportType 运动类型( 01 是走路,02 是登高 03 是跑步)
	 * @param equipmentType 运动时使用的设备类型 (01计步表 02 登高表 03 轨迹表)
	 * @return 运动距离累计值（单位是公里）
	 */
	double  sportDistanceTotal(Timestamp from,Timestamp to,String sportType,String equipmentType);
	/**
	 * 所有人运动卡洛里消耗累计
	 * @param from 开始时间(用于查询的时间起始点)
	 * @param to 结束时间(用于查询的时间结束点)
	 * @param sportType 运动类型( 01 是走路,02 是登高 03 是跑步)
	 * @param equipmentType 运动时使用的设备类型 (01计步表 02 登高表 03 轨迹表)
	 * @return 卡洛里消耗累计值（单位是千卡）
	 */	
	double  caloryUseTotal(Timestamp from,Timestamp to,String sportType,String equipmentType);
}
