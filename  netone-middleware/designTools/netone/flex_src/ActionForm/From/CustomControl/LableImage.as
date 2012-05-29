// ActionScript file
package ActionForm.From.CustomControl{

 	/**
	 * 表单设计上的 图片字段
	 * xuwei(2012-4-5)
	 * **/
	import mx.controls.Image;
	import mx.controls.Label;
	import mx.controls.TextInput;
	import mx.core.*;

public class LableImage extends UIComponent {
 
        public static  var _lable:Label;	
	    public  var _textinput:TextInput;
		public  var _width:Number=250;
		public  var _height:Number=30;
		public var _cross:Number;
		public var _row:Number;
		public var _expand:String;
		public var _must:String="否";
		public var _read:String="否";
		public var _conceal:String="否"; 
		public var _length:String="1000";
		public var _PLANB:String;
		public var _ID:String;
		public var _type:String="图片";
		[Bindable]
		public var  _text:String="图片";
		public var  _text1:String="";
		public var _summarytype:String;
		public var _widthint:String="";
		
		public function LableImage()
		{
			super();
		}
		 
	
		
   protected override function createChildren():void{
		super.createChildren();
	 	
	 		      _lable=new Label();
	 		      _lable.width=_width/4;
	 		      _lable.height=_height;
	 		      _lable.x=0;
	 		      _lable.y=0;
	 		      _lable.text=_text;
	 		      _lable.name="lable";
	 		      addChild(_lable);
	   
	 		      _textinput=new   TextInput();
	 		      _textinput.width=_lable.width*3;
	 		      _textinput.height=_height;
	 		      _textinput.x= _lable.width;
	 		      _textinput.y=0;
	 		      _textinput.name="textinput";
	 		       addChild(_textinput);
	 		this.width=_lable.width+_textinput.width;
	 		this.height=_height; 
	 		var i:int=Math.round(Math.random()*(2000-1000))+1000;    
	 		this.name="LableImage"+i;
	 		this.id="15";
		}
		     public function get text():String {
		       return _text;
		     }
		     public function set text(text:String):void{
		       this._text=text;
		     }
		   public override  function get width():Number{
		      return this._width;
		     }
		  public override  function set width(width:Number):void{
		      this._width=width;
		     _lable.width=width/4;
		     _textinput.width=_lable.width*3;
		     }
		     
		  public override  function get height():Number{
		      return this._height;
		     }
		  public override  function set height(height:Number):void{
		         this._height=height;
		         _lable.height=height;
		         _textinput.height=height;
		         
		     }
		      public function get cross():Number{
		      return this._cross;
		     }
		     public function  set cross(cross:Number):void{
		     this._cross=cross;
		     }
		     public function get row():Number{
		     
		      return this._row;
		     }
		     public function  set row(row:Number):void{
		      this._row=row;
		     }
		        public function get expand():String{
		     	return this._expand;
		     }
		     public function set expand(expand:String):void{
		       this._expand=expand;
		     }
		     public function get must():String{
		      return this._must;
		     }
		   public function set must(must:String):void{
		      this._must=must;
		     }
	    public function get read():String{
		     return this._read;
		     }
       public function set read(read:String):void{
		       this._read=read;
		     }
			     public function get length():String{
		      return this._length;
		     }
          public function set length(length:String):void{
		      this._length=length;
		     }
		       public function get PLANB():String{
		        return this._PLANB;
		     }
		     public function set PLANB(PLANB:String):void{
		      this._PLANB=PLANB;
		     }
		public function get conceal():String{
		        return this._conceal;
		     }
		 public function set conceal(conceal:String):void{
		      this._conceal=conceal;
		     }     
         public function get summarytype():String{
		     return this._summarytype;
	      }
		 public function set summarytype(summarytype:String):void{
		   this._summarytype=summarytype;
		  } 
 }

}