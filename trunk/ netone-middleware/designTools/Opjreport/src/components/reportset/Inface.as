package components.reportset
{
	/**
	 * 
	 */
	public class Inface
	{
		public function Inface()
		{
			
		}
		private var _name:String;
		private var _displayname:String;
		private var _columns:String;
		private var _bindclr:String;
		private var _text:String;
		private var _remark:String;
		private var _stringtype:String;
		
		private var _rname:String;
		private var _type:String;
		private var _naturalname:String;
		
		public function get rname():String{
			return this._rname;		
		}
		public function set rname(rname:String):void{
			this._rname=rname;
		}
		public function get type():String{
			return this._type;		
		}
		public function set type(type:String):void{
			this._type=type;
		}
		public function get naturalname():String{
			return this._naturalname;		
		}
		public function set naturalname(naturalname:String):void{
			this._naturalname=naturalname;
		}
		
		
		public function get name():String{
         return this._name;		
		}
		public function set name(name:String):void{
		 this._name=name;
		}
		public function get displayname():String{
         return this._displayname;		
 		}
		public function set displayname(displayname:String):void{
		  this._displayname=displayname;
		}
		public function get columns():String{
			return this._columns;		
		}
		public function set columns(columns:String):void{
			this._columns=columns;
		}
		public function get bindclr():String{
			return this._bindclr;		
		}
		public function set bindclr(bindclr:String):void{
			this._bindclr=bindclr;
		}
		public function get text():String{
			return this._text;		
		}
		public function set text(text:String):void{
			this._text=text;
		}
		public function get remark():String{
			return this._remark;		
		}
		public function set remark(remark:String):void{
			this._remark=remark;
		}
		public function get stringtype():String{
			return this._stringtype;		
		}
		public function set stringtype(stringtype:String):void{
			this._stringtype=stringtype;
		}
	}	
}