/**
	* @(#)HashMap.as
		*
* @author soda E-mail:junaisy@163.com
	* @version 1.0
* <br>Copyright (C), 2007 soda.C
	* <br>This program is protected by copyright laws.
		* <br>Program Name:FlashSyncServer
* <br>Date:2006.6
*/

package utils
{
/**
 * <p>将键映射到值的对象。一个映射不能包含重复的键；每个键最多只能映射一个值</p>
 * <p>它的实现类有以下的类：</p>
 * @author soda(弃天笑)
 */
import utils.Map;
public class HashMap implements Map
{
	private var obj:Object;
	private var len:Number;
	public function HashMap()
	{
		obj = new Object();
		len = 0;
	}
	/**
	 * 将指定的值与此映射中的指定键相关联.
	 * @param key 与指定值相关联的键.
	 * @param value 与指定键相关联的值.
	 */
	public function put(key:String,value:Object):void
	{
		var temp = obj[key];
		if(temp == undefined)
			if(temp == undefined)
			{
				obj[key] = value;
				len++;
			}
	}
	/**
	 * 返回此映射中映射到指定键的值.
	 * @param key 与指定值相关联的键.
	 * @return 此映射中映射到指定值的键，如果此映射不包含该键的映射关系，则返回 null.
	 */
	public function get(key:String):Object
	{
		return obj[key];
	}
	/**
	 * 从此映射中移除所有映射关系
	 */
	public function clear():void
	{
		for(var temp:String in obj)
		{
			delete obj[temp];
		}
		len = 0;
	}
	/**
	 * 如果存在此键的映射关系，则将其从映射中移除
	 * @param key 从映射中移除其映射关系的键
	 * @return 以前与指定键相关联的值，如果没有该键的映射关系，则返回 null
	 */
	public function remove(key:String):Object
	{
		var temp = obj[key];
		if(temp != undefined)
		{
			delete obj[key];
			len--;
		}
		return temp;
	}
	/**
	 * 返回此映射中的键-值映射关系数.
	 * @return 此映射中的键-值映射关系的个数.
	 */
	public function size():Number
	{
		return len;
	}
	/**
	 * 如果此映射未包含键-值映射关系，则返回 true.
	 * @return 如果此映射未包含键-值映射关系，则返回 true.
	 */
	public function isEmpty():Boolean
	{
		if(len != 0)
		{
			return true;
		}
		return false;
	}
	/**
	 * 如果此映射包含指定键的映射关系，则返回 true.
	 * @param key 测试在此映射中是否存在的键.
	 * @return 如果此映射包含指定键的映射关系，则返回 true.
	 */
	public function containsKey(key:String):Boolean
	{
		var temp = obj[key];
		if(temp != undefined)
		{
			return true;
		}
		return false;
	}
	/**
	 * 如果该映射将一个或多个键映射到指定值，则返回 true.
	 * @param key 测试在该映射中是否存在的值
	 * @return 如果该映射将一个或多个键映射到指定值，则返回 true.
	 */
	public function containsValue(value:Object):Boolean
	{
		for(var temp:String in obj)
		{
			if(obj[temp] == value)
			{
				return true;
			}
		}
		return false;
	}
	/**
	 * 返回此映射中包含的所有值.
	 * @param key 从映射中移除其映射关系的键
	 * @return 以前与指定键相关联的值，如果没有该键的映射关系，则返回 null
	 */
	public function values():Array
	{
		var ary:Array = new Array;
		for(var temp:String in obj)
		{
			ary.push(obj[temp]);
		}
		return ary;
	}
}
}