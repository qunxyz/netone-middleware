package components.reportset
{
	/**
	 *  报表查询  解析xml数据对象 
	 *  xuwei（2012-3-22）
	 * 
	 */
	public class repclr
	{
		public function repclr()
		{
			
		}
		private var _id:String;
		private var _x:int;
		private var _y:int;
		private var _width:int;
		private var _height:int;
		private var _col:int;
		private var _row:int;
		private var _label:String;
		private var _relat:String;
		private var _strtyp:String;
		private var _type:String;
		private var _data:String;
		
		public function get data():String{
		return this._data;
	    }
		public function set data(data:String):void{
		this._data=data;
		}
		public function get id():String{
       		return this._id;
		}
		public function set id(id:String):void{
		 this._id=id;
		}
		public function get x():int{
			return this._x;
		}
		public function set x(x:int):void{
			this._x=x;
		}
		public function get y():int{
			return this._y;
		}
		public function set y(y:int):void{
			this._y=y;
		}
		public function get width():int{
			return this._width;
		}
		public function set width(width:int):void{
			this._width=width;
		}
		public function get height():int{
			return this._height;
		}
		public function set height(height:int):void{
			this._height=height;
		}
		public function get col():int{
			return this._col;
		}
		public function set col(col:int):void{
			this._col=col;
		}
		public function get row():int{
			return this._row;
		}
		public function set row(row:int):void{
			this._row=row;
		}
		public function get label():String{
			return this._label;
		}
		public function set label(label:String):void{
			this._label=label;
		}
		public function get relat():String{
			return this._relat;
		}
		public function set relat(relat:String):void{
			this._relat=relat;
		}
		public function get strtyp():String{
			return this._strtyp;
		}
		public function set strtyp(strtyp:String):void{
			this._strtyp=strtyp;
		}
		public function get type():String{
			return this._type;
		}
		public function set type(type:String):void{
			this._type=type;
		}
	}	
}