package ActionForm.From.com.hitb.component
{
	import ActionForm.From.com.hitb.util.ImageFactory;
	
	import flash.events.MouseEvent;
	
	import mx.collections.XMLListCollection;
	import mx.containers.Canvas;
	import mx.controls.Tree;
     import mx.events.FlexEvent;   
	public class ComponentTreePanel extends Canvas
	{   
		private var tree:Tree;

		
		
		public function ComponentTreePanel()
		{  
	  
		}
       
        protected override function createChildren():void{
        	super.createChildren();
            tree=new Tree();
            tree.percentHeight=100;
            tree.percentWidth=100;
            tree.labelField="@label";
            tree.iconField="@icon";
            tree.showRoot=false;
            tree.expandChildrenOf(tree.selectedItem,true);
            tree.iconFunction=treeiconFunction;
            tree.labelFunction=tree_labelFunc;
            tree.dragEnabled=true;
            tree.dragMoveEnabled=false;
            tree.selectedIndex=0;
            addChild(tree);
            
        } 
        private function tree_labelFunc(item:XML):String { 
		 var label:String = item.@label; 
		 if (tree.dataDescriptor.hasChildren(item)) { 
		 label += " (" + tree.dataDescriptor.getChildren(item).length + ")"; 
		 } 
		 return label; 
		 } 

        private function treeiconFunction(o:Object):*{
        	return ImageFactory.getComponentImage(o.@data);
        }
        public function initTreePanel(xml:XML):void{
          var coll:XMLListCollection=new XMLListCollection(xml.children());   
           tree.dataProvider=xml;

          // 必须要先进行验证   
                tree.validateNow();                       
                // 展开所有tree上的节点   
                for each(var item:XML in tree.dataProvider)   
                {     
                    tree.expandChildrenOf(item,true);   
                }   
        }

	}
}