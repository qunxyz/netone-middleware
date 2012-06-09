package BI.components
{
import mx.controls.ComboBox;
import mx.core.ClassFactory;
import mx.events.DropdownEvent;

public class TreeComboBox extends ComboBox
{
	// -------------------------------------------------------------------------
	//
	// Properties 
	//			
	// -------------------------------------------------------------------------

	// ----------------------------
	// ddFactory
	// ----------------------------

	private var _ddFactory:ClassFactory;
	
	private function get ddFactory():ClassFactory
	{
		if (_ddFactory == null)
		{
			_ddFactory = new ClassFactory();
			_ddFactory.generator = TreeComboBoxRenderer;
			_ddFactory.properties = {
					width:this.width, 
					height:this._treeHeight,
					outerDocument:this
			};
		}
		return _ddFactory;		
	}	

	// ----------------------------
	// treeHeight
	// ----------------------------

	private var _treeHeight:Number;
	
	public function get treeHeight():Number
	{
		return _treeHeight;
	}
	
	public function set treeHeight(value:Number):void
	{
		this._treeHeight = value;
		ddFactory.properties["height"] = this._treeHeight;
	}

	// ----------------------------
	// treeSelectedItem
	// ----------------------------
	
	public var treeSelectedItem:Object;

	// -------------------------------------------------------------------------
	//
	// Constructor 
	//			
	// -------------------------------------------------------------------------
	
	public function TreeComboBox()
	{
		super();
		
		this.dropdownFactory = ddFactory;
		this.addEventListener("getSelectValue",setselectvalue);
		this.addEventListener(DropdownEvent.OPEN, onComboOpen);
	}
	
	// -------------------------------------------------------------------------
	//
	// Handlers 
	//			
	// -------------------------------------------------------------------------
	public function setselectvalue(event:TreeComboBoxEvent):void
	{
		text=event.uiobject.toString();
	
	}
	private function onComboOpen(event:DropdownEvent):void
	{
		var tree:TreeComboBoxRenderer = dropdown as TreeComboBoxRenderer;
		if (treeSelectedItem)
		{
			tree.expandParents(treeSelectedItem);
			tree.selectNode(treeSelectedItem);
		}
		else
		{
			tree.expandItem(dataProvider.getItemAt(0), true);
		}
	}
	
	// -------------------------------------------------------------------------
	//
	// Overridden methods 
	//			
	// -------------------------------------------------------------------------
	
	/**
	 * Ovverride to avoid root node label being displayed as combo text when 
	 * closing the combo box. 
	 */
	override protected function updateDisplayList(unscaledWidth:Number, 
	                                              unscaledHeight:Number):void 
	{ 
		super.updateDisplayList(unscaledWidth, unscaledHeight);   

		if(dropdown && treeSelectedItem && treeSelectedItem[labelField] != null)
		{   
        	text = treeSelectedItem[labelField]; 
       
   		} 
	} 

	// -------------------------------------------------------------------------
	//
	// Other functions 
	//			
	// -------------------------------------------------------------------------
	
	public function updateLabel(selectedItem:Object):void
	{
		if (selectedItem)
		{    dispatchEvent(new TreeComboBoxEvent("changevalue",selectedItem));
			treeSelectedItem = selectedItem;
			text = treeSelectedItem[labelField];
		}
	}

}
}