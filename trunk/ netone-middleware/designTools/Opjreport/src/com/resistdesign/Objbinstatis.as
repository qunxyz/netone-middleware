package com.resistdesign
{
	/**
	 * @author hp
	 */
	public class Objbinstatis extends Object
	{
		public function Objbinstatis()
		{
			
		}
		private var _label:String;
		private var _value:String;
	    
 		public function get label():String{
		return this._label;
		}
		public function set label(lable:String):void{
		this._label=lable;
		} 
		
		public function get value():String{
		  return this._value;
		}
		public function set value(value:String):void{
		this._value=value;
		}
		
	}	
}