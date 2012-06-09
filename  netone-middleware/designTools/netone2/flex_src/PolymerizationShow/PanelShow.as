package PolymerizationShow
{
	import flash.external.ExternalInterface;
	
	import framework.IFrame;
	
	import mx.containers.Panel;
	import mx.core.*;
	public class PanelShow extends Panel
	{
 
		public  var _width:Number=0;
		public  var _height:Number=0;
		[Bindable]//url
		public var _url:String;
		[Bindable]//title
		public var _title:String;
        [Bindable]
        public var iframe:IFrame;
       
        [Bindable]//_id
		public var _ID:String;
		public function PanelShow()
		{
			super();
		}

     protected override function createChildren():void{
		         super.createChildren();
   				  iframe=new IFrame();
   				  iframe.x=0;
   				  iframe.y=0;
   				  iframe.id=_ID;
   				  iframe.source=_url;
   				  iframe.percentHeight=100;
   				  iframe.percentWidth=100;
                 
                  
   				  this.addChild(iframe);
			 	  this.width=_width;
			 	  this.height=_height; 
	 		      this.name=_url;
	 		      this.id=_ID;
	 		      this.title=_title;
	 		      this.titleTextField.name=_url;
		}
		 
		    public function get Url():String {
		       return _url;
		     }
		     public function set Url(url:String):void{
		       this._url=url;
		       this.title=url;
		       iframe.source=url; 
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
		         
	}
}
