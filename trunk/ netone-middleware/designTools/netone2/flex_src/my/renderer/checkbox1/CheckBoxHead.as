package my.renderer.checkbox1
{
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.text.TextField;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ListCollectionView;
	import mx.controls.CheckBox;
	import mx.controls.DataGrid;
	
	/**
	 * 渲染列头的复选框
	 * 
	 * @author 群号 117376837
	 */
	public class CheckBoxHead extends CheckBox
	{
		public var stateHost:Object;//传递状态的组件
		public var stateProperty:String;//选择状态
		
		public function CheckBoxHead()
		{
			super();
			this.toolTip="全选";
		}
		
		/**
		 * 重写data setter,把选择状态和列中字段绑定,方便控制数据源来改变选择状态
		 * @param value 数据源 其实就是自定义列
		 */
		public override function set data(value:Object):void
		{
			this.selected=stateHost[stateProperty];
		}
		
		/**
		 * 重写单击事件,单击后,修改数据源,达到设置行选择框状态的目的
		 */
		protected override function clickHandler(event:MouseEvent):void
		{
			super.clickHandler(event);
			stateHost[stateProperty]=this.selected;
			
			
			var grid:DataGrid=DataGrid(listData.owner); //获取DataGrid对象  
			var column:CheckBoxColumn=grid.columns[listData.columnIndex]; //获取列对象  
			
			var dgData:ArrayCollection=grid.dataProvider as ArrayCollection;//得到数据源
			var dataField:String=column.dataField;//得到绑定字段
			var enableField:String=column.enableField;//获取禁用字段
			
			
			
			if (dgData!=null)
			{
				if (dgData.length>0)
				{
					column.selectItems=new Array();
					for (var i:int=0;i< dgData.length;i++)
					{
						var obj:Object=dgData[i];
						if(obj[enableField])//可用
						{
							obj[dataField]=this.selected; //绑定全选的状态
							if(this.selected)
							{
								column.selectItems.push(obj);//获取选中的数据
							}
						}
					}
				}
				dgData.refresh(); //将排序和滤镜应用到视图
			}
		}
		
		
	}
	
}
