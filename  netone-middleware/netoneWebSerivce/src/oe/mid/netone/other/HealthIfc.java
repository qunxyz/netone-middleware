package oe.mid.netone.other;

import java.sql.Timestamp;
import java.util.List;

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
	 * @param from 开始时间(用于查询的时间起始点) 如果时间为空返回所有数据
	 * @param to 结束时间(用于查询的时间结束点)如果时间为空返回所有数据
	 * @param userName 用户名(用户查询是谁的运动时间，如果username为空表示所有用户
	 * @return 时间累计值（单位是秒）
	 */
	long  sportTimeTotal(Timestamp from,Timestamp to,String userName);
	/**
	 * 运动卡洛里消耗累计
	 * @param from 开始时间(用于查询的时间起始点)如果时间为空返回所有数据
	 * @param to 结束时间(用于查询的时间结束点)如果时间为空返回所有数据
	 * @param userName 用户名(用户查询是谁的运动距离，如果username为空表示所有用户

	 * @return 卡洛里消耗累计值（单位是千卡）
	 */	
	double  caloryUseTotal(Timestamp from,Timestamp to,String userName);
	
	/**
	 * 登高的高度累计
	 * @param from  开始时间(用于查询的时间起始点)如果时间为空返回所有数据
	 * @param to 结束时间(用于查询的时间结束点)如果时间为空返回所有数据
	 * @param userName，如果username为空表示所有用户
	 * @return
	 */
	List<HeightInfo> heightTotal(Timestamp from,Timestamp to,String userName);
	
	/**
	 * 轨迹的累积
	 * @param from开始时间(用于查询的时间起始点)如果时间为空返回所有数据
	 * @param to结束时间(用于查询的时间结束点)如果时间为空返回所有数据
	 * @param userName，如果username为空表示所有用户
	 * @return
	 */
	List<TrackInfo> trackTotal(Timestamp from,Timestamp to,String userName);
	
	
	/**
	 * 计步的累积
	 * @param from 开始时间(用于查询的时间起始点)如果时间为空返回所有数据
	 * @param to 结束时间(用于查询的时间结束点)如果时间为空返回所有数据
	 * @param userName，如果username为空表示所有用户
	 * @return
	 */
	long stepTotal(Timestamp from,Timestamp to,String userName);
	
	/**
	 * 运动排名
	 * @param userName ，如果username为空表示所有用户
	 * @param toptype 01 卡洛里消耗排名,02与我运动数据相近的人 
	 * @return
	 */
	List<SportInfo> sportTop(String userName,String toptype);
	
	/**
	 * 添加用户 
	 * @param userid
	 * @return
	 */
	boolean syncAddUser(String userid);
	/**
	 * 禁用用户
	 * @param userid
	 * @return
	 */
	boolean syncFobitUser(String userid);


}
