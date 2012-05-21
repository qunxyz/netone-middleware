package my.renderer.checkbox1
{
	import flash.events.MouseEvent;
	
	import mx.collections.ArrayCollection;
	import mx.controls.CheckBox;
	import mx.controls.DataGrid;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.controls.dataGridClasses.DataGridListData;
	
	/**
	 * 渲染行的复选框
	 * 
	 * @author 群号 117376837
	 */
	public class CheckBoxItem extends CheckBox
	{
		private var dataField:String;//保存该列绑定的字段,标记是否选中
		
		public var stateHost:Object;//传递状态的组件
		public var enableProperty:String;//选择状态
		private var isDefaultSelected:Boolean=true;//获取默认选中的项
		
		
		/**
		 * 重写data setter,把选择状态和字段的值绑定,方便控制数据源来改变选择状态
		 * @param value 该行对应的数据源的值
		 */
		public override function set data(value:Object):void
		{
			var enableField:String=stateHost[enableProperty];//获取禁用字段
			
			dataField=DataGridListData(listData).dataField;//获取选择字段
			
			if(!value.hasOwnProperty(enableField))//不存在绑定字段
			{
				value[enableField]=true;//默认可用
			}
			this.enabled=value[enableField];
			
			
			if(!value.hasOwnProperty(dataField))//不存在绑定字段
			{
				value[dataField]=false;//不存在默认不选中
			}
			this.selected=value[dataField];//设置选择状态为绑定字段的值
			
			if(isDefaultSelected)
			{
				isDefaultSelected=false;
				if(this.selected)
				{
					var grid:DataGrid=DataGrid(listData.owner); //获取DataGrid对象  
					var column:CheckBoxColumn=grid.columns[listData.columnIndex]; //获取列对象 
					column.selectItems.push(value);
				}
			}
			
			super.data=value;//保存数据
		}
		
		/**
		 * 重写单击事件,单击后,把选择状态的值赋给绑定的字段,并改变选中的值
		 */
		protected override function clickHandler(event:MouseEvent):void
		{
			super.clickHandler(event);
			data[dataField]= this.selected;
			
			
			var grid:DataGrid=DataGrid(listData.owner); //获取DataGrid对象  
			var column:CheckBoxColumn=grid.columns[listData.columnIndex]; //获取列对象  
			
			
			//改变选中的值
			if (this.selected)
			{
				column.selectItems.push(data);//添加值
			}
			else
			{
				for(var i:int=0;i<column.selectItems.length;i++)
				{
					if (column.selectItems[i]==data)
					{
						column.selectItems.splice(i,1);//删除相等的值
					}
				}
			}
		}
	}
	

}
