package workflow.my.renderer.checkbox1
{
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.core.ClassFactory;
	
	/**
	 * 全选列,该类提供一个全选的功能
	 * 
	 * @author 群号 117376837
	 */
	public class CheckBoxColumn extends DataGridColumn
	{
		
		public var selectAllFlag:Boolean;//选择状态的标记,传递给列头选择框,不传递会默认会调用item的data setter,则引发异常
		public var enableField:String;//标记是否禁用
		
		public var selectItems:Array=new Array();//保存选中的数据
		
		public function CheckBoxColumn(columnName:String=null)
		{
			super(columnName);
			this.width=50;
			this.setStyle("textAlign","center");
			
			var itemRenderer:ClassFactory=new ClassFactory(CheckBoxItem);//定义渲染行的选择框
			itemRenderer.properties={stateHost: this, enableProperty:"enableField"};//添加属性,绑定选择状态
			this.itemRenderer=itemRenderer;//添加
			
			var headRenderer:ClassFactory=new ClassFactory(CheckBoxHead);//定义渲染列的选择框
			headRenderer.properties={stateHost: this, stateProperty:"selectAllFlag"};//添加属性,绑定选择状态
			
			this.headerRenderer=headRenderer;//添加
			
		}
		
	}
	

}
