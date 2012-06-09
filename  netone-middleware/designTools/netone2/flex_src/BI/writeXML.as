package BI
{
	public class writeXML
	{
		[Bindable]
		public var sc:XML;
	    [Bindable]
	    public var  pxml:XML;
	
		public function writeXML()
		{
	
		}
		  public function Setpreview(event:saveXmlEvent):void{ 
		     for(var i:int=0; i<this.num;i++)
             {
             	 if(sc==null)
         	     {
         	    sc=<dataTrans></dataTrans>
                 }
              pxml=<source></source>;
              pxml.@id=getQualifiedClassName(this.getChildAt(i)).replace("::",".");
              pxml.@url=this.getChildAt(i).x;
              pxml.@password=this.getChildAt(i).y;
              pxml.@username=this.getChildAt(i).width;
              pxml.@driver=this.getChildAt(i).width;
              
		    }
            sc.appendChild(pxml);
	 }
	}
}