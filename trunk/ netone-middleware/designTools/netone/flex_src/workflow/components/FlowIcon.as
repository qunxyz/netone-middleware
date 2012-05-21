package workflow.components
{
	import flash.events.MouseEvent;
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;
	
	import mx.controls.DataGrid;
	import mx.controls.Image;
	
	import workflow.events.IconEvent;
	[Event(name=IconEvent.ICON_MOUSE_DOWN, type="events.IconEvent")]
	[Event(name=IconEvent.ICON_MOUSE_UP, type="events.IconEvent")]
	[Event(name=IconEvent.ICON_MOVE, type="events.IconEvent")]

	public class FlowIcon extends Image
	{
		private var _text:DataGrid;
		private var _type: int; //图标类型
		private var _label: String; //图标上的文字说明
		private var _memo: String; //备注
		private var _selected: Boolean; //图标是否被选中
		private var _identity: String; //图标唯一标识符
		private var _icon: Object; //图像数据
		private var _shujuyuan:String;
		private var text: TextField = new TextField();
		
		public function get icon(): Object{
			return _icon;
		}
		
		public function set icon(value: Object): void{
			this._icon = value;
			draw();
		}
		
		public function get memo(): String{
			return _memo;
		}
		
		public function set memo(value: String): void{
			this._memo = value;
			draw();
		}
		
		public function get identity(): String{
			return _identity;
		}
		
		public function set identity(value: String): void{
			this._identity = value;
			draw();
		}
		
		public function get selected(): Boolean{
			return _selected;
		}
		
		public function set selected(value: Boolean): void{
			this._selected = value;
			draw();
		}
		
		public function get label(): String{
			return _label;
		}
		
		public function set label(value: String): void{
			this._label = value;
			draw();
		}
		
		public function get type(): int{
			return _type;
		}
		
		public function set type(value: int): void{
			this._type = value;
			draw();
		}
		
		public function get shujuyuan():String{
		  return this._shujuyuan;
		}
		public function set shujuyuan(shujuyuan:String):void{
			this._shujuyuan=shujuyuan;
		}
		
		public function FlowIcon(icon: Object, type: int = 0, label: String = "节点",
			selected : Boolean = false, name: String = null,
			memo: String = "")
		{
			super();
			this._icon = icon;
			this._label = label;
			this._identity = name;
			this._selected = selected;
			this._type = type;
			this._memo = memo;
			
			this.source = icon;
			
			text.autoSize = TextFieldAutoSize.LEFT;
			text.x = 15;
			text.y = 65;
            text.width=100;
            text.height=100;
			
			this.addChild(text);
			
			this.draw();
			this.events();
		}
		
		//画图标
		private function draw(): void{
			this.source = this._icon;
			
			text.htmlText = "<b>" + this.label + "</b>";
		}
		
		//事件绑定
		private function events(): void{
			this.addEventListener(MouseEvent.MOUSE_DOWN, onIconMouseDown);
			this.addEventListener(MouseEvent.MOUSE_UP, onIconMouseUp);
			this.addEventListener(MouseEvent.MOUSE_MOVE, onIconMove);
		}
		
		//触发鼠标按下事件
		private function onIconMouseDown(event: MouseEvent): void{
			var e: IconEvent= new IconEvent(IconEvent.ICON_MOUSE_DOWN);
			e.icon = this;
			this.dispatchEvent(e);					
		}
		
		//触发鼠标弹起事件
		private function onIconMouseUp(event: MouseEvent): void{
			var e: IconEvent= new IconEvent(IconEvent.ICON_MOUSE_UP);
			e.icon = this;
			this.dispatchEvent(e);			
		}
		
		//触发鼠标移动事件
		private function onIconMove(event: MouseEvent): void{
			var e: IconEvent= new IconEvent(IconEvent.ICON_MOVE);
			e.icon = this;
			this.dispatchEvent(e);	
		}
		
	}
}