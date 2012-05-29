package ActionForm.From
{
	public class csource
	{
		public function csource()
		{
		}
	    public var _id:String;
        public var _label:String;
        public var _data:String;
        public var _x:Number;
        public var _y:Number;
        public var _text:String;
        public var _dataProvider:String;
        public var _width:Number;
        public var _height:Number;
        public var _PLANB:String;
        public var _expand:String;
        public var _length:String;
        public var _must:String;
        public var _read:String;
        public var _conceal:String;
        public var _summarytype:String;
        
        public function  get ID():String{
         return this._id;
        }
        public function  set ID(ID:String):void{
         this._id=ID;
        } 
        public function get summarytype():String{
         return this._summarytype;
        }
        public function set summarytype(summarytype:String):void{
         this._summarytype=summarytype;
        }   
        public   function get label():String
        {
        return _label;
        }
        public   function set label(label:String):void
        {
        this._label=label;
        }
      
        public   function get data():String
        {
        return _data;
        }
        public   function set data(data:String):void
        {
        this._data=data;
        }
     
        public   function get x():Number{return _x;}
        public   function set x(x:Number):void
        {
        this._x=x;
        }
   
        public   function get y():Number
        {
        return _y;
        }
        public   function set y(y:Number):void
        {
        this._y=y;
        }
        
        public  function get text():String{
         return _text;
        } 
       public function set text(text:String):void{
          this._text=text;
        } 
        
         public function get dataProvider():String{
         return _dataProvider;
        }
        public function set dataProvider(dataProvider:String):void{
         this._dataProvider=dataProvider;
        } 
        public function get width():Number{
         return _width;
        }
        public function set width(width:Number):void {
        this._width=width;
        }
        public function get height():Number{
         return height;
        }
        public function set height(height:Number):void{
        this._height=height;
         
        }
        public function get PLANB():String{
        return this._PLANB;
        }
        public function set PLANB(PLANB:String):void{
         this._PLANB=PLANB;
        }
        public function get Expand():String{
        return this._expand;
        }
        public function set Expand(Expand:String):void{
         this._expand=Expand;
        }
        public function get Length():String{
        return this._length;
        }
        public function set Length(length:String):void{
         this._length=length;
        }
         public function get Must():String{
        return this._must;
        }
        public function set Must(must:String):void{
         this._must=must;
        }
        public function get Read():String{
        return this._read;
        }
        public function set Read(read:String):void{
         this._read=read;
        }
         public function get conceal():String{
        return this._conceal;
        }
        public function set conceal(conceal:String):void{
         this._conceal=conceal;
        }
        
	}
}