package ActionForm.From.CustomControl{
	 /* 
	 * xuwei(2012-4-17)
	 *真假radio
	 */
	import mx.controls.Label;
	import mx.controls.RadioButton;
	import mx.core.UIComponent;

	

public class LableTAndF extends UIComponent{
  		public static  var _lable:Label;	
	    public  var _checkbox:RadioButton;
	    public  var _checkbox1:RadioButton;
		public  var _width:Number=250;
		public  var _height:Number=30;
		public var _cross:Number;
		public var _row:Number;
		public var _expand:String;
		public var _must:String="否";
		public var _read:String="否";
		public var _length:String="1";
		public var _ID:String;
		public var _PLANB:String;
        public var _conceal:String="否";
		public var _type:String="真假radio";
		public var _summarytype:String;
		[Bindable]
		public var  _text:String="真假radio";
		
		
		public function LableTAndF()
		{
		 super();
		}

   protected override function createChildren():void{
		super.createChildren();
	 	var i:int=Math.round(Math.random()*(2000-1000))+1000;  
	 		      _lable=new Label();  
	 		      _lable.width=_width/4;
	 		      _lable.height=_height;
	 		      _lable.x=0;
	 		      _lable.y=8;
	 		      _lable.text=_text;
	 		      _lable.name="lable";
	 		      addChild(_lable);
	   
	 		      _checkbox=new RadioButton();
	 		      _checkbox.width=(_width-_lable.width)/2;
	 		      _checkbox.height=_height;
	 		      _checkbox.x= _lable.width;
	 		      _checkbox.y=0;
	 		      _checkbox.label="真";
	 		      addChild(_checkbox);
	 		      
	 		      _checkbox1=new RadioButton();
	 		      _checkbox1.width=_width-_lable.width-_checkbox.width;
	 		      _checkbox1.height=_height;
	 		      _checkbox1.label="假";
	 		      _checkbox1.x= _checkbox.width+_checkbox.x;
	 		      _checkbox1.y=0;
	 		       addChild(_checkbox1);
	 		       
	 		this.width=_width;
	 		this.height=_height; 
	 		  
	 		this.name="LableTAndF"+i;
	 	 	this.id="31";
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
		     _checkbox.width=_lable.width*3;
		     }
		     
		  public override  function get height():Number{
		      return this._height;
		     }
		  public override  function set height(height:Number):void{
		         this._height=height;
		         _lable.height=height;
		         _checkbox.height=height;
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
