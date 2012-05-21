// ActionScript file
package ActionForm.From.CustomControl{
	/**
	 * 表单设计上的 字段按钮
	 * xuwei(2012-4-1)
	 * **/
	import mx.controls.Button;
	import mx.controls.Label;
	import mx.controls.LinkButton;
	import mx.core.*;

public class LableButton extends UIComponent {
	    public  var _LableButton:Button;
		public  var _width:Number=250;
		public  var _height:Number=30;
		public var _cross:Number;
		public var _row:Number;
		public var _expand:String;
		public var _must:String="否";
		public var _read:String="否";
		public var _conceal:String="否";
		public var _length:String="100";
	    public var _PLANB:String;
	    public var _ID:String;
	    public var _treeProvider:String;
	    public var _type:String="按钮";
	    public var _summarytype:String;
		[Bindable]
		public var  _text:String="按钮";
		
       public function LableButton()
		{
			super();
		}
	
   protected override function createChildren():void{
			super.createChildren();
	 		var lableBotton:Label=new Label();
	 		lableBotton.width=_width;
	 		lableBotton.height=_height;
	 		lableBotton.text=_text;
	 		lableBotton.x=0;
	 		lableBotton.y=0;
	 	    
	 		this.addChild(lableBotton);
	 		this.width=_width; 
	 		this.height=_height; 
	 		var i:int=Math.round(Math.random()*(2000-1000))+1000;    
	 		this.name="LableButton"+i;
	 		this.id="16";
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
		     }
		     
		  public override  function get height():Number{
		      return this._height;
		     }
		  public override  function set height(height:Number):void{
		         this._height=height;
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