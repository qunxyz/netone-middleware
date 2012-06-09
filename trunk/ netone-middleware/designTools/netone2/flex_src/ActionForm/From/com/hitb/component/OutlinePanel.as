package ActionForm.From.com.hitb.component
{
	import ActionForm.From.com.hitb.event.Component;
	import ActionForm.From.com.hitb.event.EventTypeConstants;
	import ActionForm.From.com.hitb.event.GlobalEvent;
	import ActionForm.From.com.hitb.event.removeChildren;
	import ActionForm.From.com.hitb.event.saveXmlEvent;
	import ActionForm.From.com.hitb.util.GlobalManager;
	import ActionForm.From.com.hitb.util.ImageFactory;
	
	import flash.utils.getQualifiedClassName;
	
	import mx.collections.XMLListCollection;
	import mx.containers.Canvas;
	import mx.controls.Alert;
	import mx.controls.DataGrid;
	import mx.controls.Tree;
	import mx.core.UIComponent;
	import mx.events.ListEvent;
	public class OutlinePanel  extends Canvas
	{     
	public   var comarr:Array=new Array();
	public   var comname:Array=new Array();

		private var tree:Tree;
		[Bindable]
		public var coll:XMLListCollection;
          [Bindable]
		 public var selectedNode:XML;
		 [Bindable]
		public var xml:XML;
		
		public var pXML:XML;
		public function OutlinePanel()
		{ 
		    GlobalManager.getInstance().addEventListener(EventTypeConstants.Event_deleteall,DeleteAll);
		  	GlobalManager.getInstance().addEventListener(EventTypeConstants.Event_ADD,onAddHandler);
		    GlobalManager.getInstance().addEventListener(EventTypeConstants.Event_removeUI,deleteChild);
		    GlobalManager.getInstance().addEventListener(Data.Event_revamp,RevampData);
		}

			public function DeleteAll(event:saveXmlEvent):void{	
					 if(pXML!=null){	
					  delete pXML.control;    
					 }
			}
        public function deleteChild(event:removeChildren):void    {
        	 
         var sx:XMLList= new XMLList(coll.getItemAt(0)).children();
		      for ( var i:Number = 0 ; i < sx.length() ; i++ ) 
		     {   
		      if ( sx[i].@ID.toXMLString()==event.getComponent1()["_ID"].toString())  
		       { 
		                delete sx[i];    
		                break;    
		     
		        }
		     }
        	}
        	 public function treeChanged(event:Event):void {
             
                selectedNode=Tree(event.target).selectedItem as XML;

              }
     
	   private function Treeitemclick(event:ListEvent):void
         {
          for(var i:int=0;i<comarr.length;i++){ 
            if(XML(event.itemRenderer.data).@label.toString()==comname[i])
	          {
	           GlobalManager.getInstance().dispatchEvent(new GlobalEvent(Data.Event_data,this,UIComponent(comarr[i]),0,0)); 
	          }
          }
         }

       
        protected override function createChildren():void{
        	super.createChildren();
        	tree=new Tree();
        	 var  s:DataGrid
        	tree.labelField="@text";
        	tree.iconField="@icon"; 
        	tree.name="tree";
        	tree.iconFunction=treeiconFunction;
        	 xml=<control></control>;
        	coll=new XMLListCollection(xml.children());
        	tree.percentHeight=100;
        	tree.percentWidth=100;
        	tree.dataProvider=coll;
            tree.showRoot=false; 
            tree.doubleClickEnabled=true;
            tree.addEventListener(ListEvent.ITEM_CLICK,Treeitemclick);

            tree.labelFunction=tree_labelFunc;
            tree.iconFunction=treeiconFunction;
            tree.expandChildrenOf(tree.selectedItem,true);
            tree.doubleClickEnabled=true;
        	addChild(tree);
        	
        }
        
         public function tree_labelFunc(item:XML):String { 
		 var label:String = item.@text; 
		 if (tree.dataDescriptor.hasChildren(item)) { 
		 label += " (" + tree.dataDescriptor.getChildren(item).length + ")"; 
		 } 
		 return label; 
		 } 

        public function treeiconFunction(o:XML):*{
        	return ImageFactory.getComponentImage(o.@classname);
        }
        
               //修改XML文件
	public function RevampData(event:Component):void{
      var comp:UIComponent=(event.getComponent1());
       var coll1:XMLList=new XMLList(pXML.children());
      for(var i:int=0; i<coll1.length();i++)
		    {  
		    	if(pXML.control[i].@ID==comp["_ID"]){
		    	pXML.control[i].@text=comp["text"];
		    	}
        	}
	 
	}
        private function onAddHandler(event:GlobalEvent):void{
        	 for(var i:int=0;i<coll.length;i++){
        		if(coll.getItemAt(i).@uid==event.getComponent1().uid){
        			pXML=coll.getItemAt(i) as XML;
        			break;
        		}
        	}
        	coll.disableAutoUpdate();
        
            var xml:XML=<control  label="控件"></control>;
        	initXMLAttr(xml,event.getComponent2(),event.getComponent2()["text"], event.getComponent2()["_ID"]);
        	if(pXML==null){
        		pXML=<control  text="控件列表"></control>;
        	  //  initXMLAttr(pXML,event.getComponent2(),event.getComponent2()["text"], event.getComponent2()["_ID"]);
        		coll.addItem(pXML);
        	}
        	pXML.appendChild(xml);
        	coll.enableAutoUpdate();
        	
        	   // 必须要先进行验证   
                tree.validateNow();                       
                // 展开所有tree上的节点   
                for each(var item:XML in tree.dataProvider)   
                {      
                    tree.expandChildrenOf(item,true);   
                } 
        } 
        
        private function initXMLAttr(xml:XML,comp:UIComponent,text:String,id:String):void{
        	comarr.push(comp);
        	comname.push(comp.name);
        	xml.@text=text;
        	xml.@uid=comp.uid;
        	xml.@label=comp.name;
        	xml.@classname=getQualifiedClassName(comp).replace("::","."); 
        	xml.@ID=id;
        }
      
	}
}