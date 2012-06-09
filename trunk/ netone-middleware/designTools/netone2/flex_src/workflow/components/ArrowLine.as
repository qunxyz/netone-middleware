package workflow.components
{
	import mx.core.UIComponent;

	public class ArrowLine extends UIComponent
	{
		private var _startX: int; //起点X坐标
		private var _startY: int;//起点Y坐标
		private var _endX: int; //终点X坐标
		private var _endY: int; //终点Y坐标
		private var _radio: int; //小圆的半径
		private var _thickness: Number; //线条粗细
		
		private var _fromIcon: String; //从..开始 名称
		private var _toIcon: String; //到..结束 名称
		
		private var _lineColor: uint;//线条颜色
		private var _circleColor: uint; //尾部小圆的颜色
		
		public function get circleColor(): uint{
			return _circleColor;
		}
		
		public function set circleColor(value: uint): void{
			this._circleColor = value;
		}
		
		public function get toIcon(): String{
			return _toIcon;
		}
		
		public function set toIcon(value: String): void{
			this._toIcon = value;
		}
		
		public function get fromIcon(): String{
			return _fromIcon;
		}
		
		public function set fromIcon(value: String): void{
			this._fromIcon = value;
		}
		
		public function get thickness(): Number{
			return _thickness;
		}
		
		public function set thickness(value: Number): void{
			this._thickness = value;
			draw();
		}
		
		public function get radio(): int{
			return _radio;
		}
		
		public function set radio(value: int): void{
			this._radio = value;
			draw();
		}
		
		public function get lineColor(): uint{
			return _lineColor;
		}
		
		public function set lineColor(value: uint): void{
			this._lineColor = value;
			draw();
		}
		
		public function get startX(): int{
			return _startX;
		}
		
		public function set startX(value: int): void{
			this._startX = value;
			draw();
		}
		
		public function get startY(): int{
			return _startY;
		}
		
		public function set startY(value: int): void{
			this._startY = value;
			draw();
		}
		
		public function get endX(): int{
			return _endX;
		}
		
		public function set endX(value: int): void{
			this._endX = value;
			draw();
		}
		
		public function get endY(): int{
			return _endY;
		}
		
		public function set endY(value: int): void{
			this._endY = value;
			draw();
		}
		
		public function ArrowLine(startX: int = 0,
			startY: int = 0,
			endX: int = 0,
			endY: int = 0,
			lineColor: uint = 0x000000,
			thickness: Number = 1,
			radio: int = 0,
			circleColor: uint = 0xFF0000)
		{
			super();
			this._endX = endX;
			this._endY = endY;
			this._startX = startX;
			this._startY = startY;
			this._lineColor = lineColor;
			this._radio = radio;
			this._thickness = thickness;
			this._circleColor = circleColor;
			
			this.draw();
		}
		
		//画直线,并在尾端画一个小圆
		public function draw(): void{
			this.graphics.clear();//清除前面画的东东
			
			//画线
			this.graphics.lineStyle(thickness, lineColor);
			this.graphics.moveTo(startX, startY);
			this.graphics.lineTo(endX, endY);
			
			//在线条尾部画小圆
			
			this.graphics.lineStyle(2, circleColor);
			this.graphics.drawCircle(endX, endY, radio);
		}
	}
}

