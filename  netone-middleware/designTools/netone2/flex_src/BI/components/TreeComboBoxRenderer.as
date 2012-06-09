package BI.components
{
import mx.controls.Tree;
import mx.events.ListEvent;

public class TreeComboBoxRenderer extends Tree
{
	// -------------------------------------------------------------------------
	//
	// Properties 
	//			
	// -------------------------------------------------------------------------
  
	[Bindable]
	public var outerDocument:TreeComboBox;
	
	// -------------------------------------------------------------------------
	//
	// Constructor 
	//			
	// -------------------------------------------------------------------------
	
	public function TreeComboBoxRenderer()
	{
		super();
	
	     
		this.addEventListener(ListEvent.CHANGE, onSelectionChanged);
	}

	// -------------------------------------------------------------------------
	//
	// Handlers 
	//			
	// -------------------------------------------------------------------------


	private function onSelectionChanged(event:ListEvent):void
	{ 
		outerDocument.updateLabel(event.currentTarget.selectedItem);
	}

	// -------------------------------------------------------------------------
	//
	// Other methods 
	//			
	// -------------------------------------------------------------------------
	
	public function expandParents(node:Object):void
	{
		if (node && !isItemOpen(node))
		{
			expandItem(node, true);
         //  expandParents(node.parent());
        }		
	}
	
	public function selectNode(node:Object):void
	{
		selectedItem = node;
		var idx:int = getItemIndex(selectedItem);
    	scrollToIndex(idx);
	}
}
}