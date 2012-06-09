package com.resistdesign.view
{
	import com.resistdesign.view.Marker;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.ui.Keyboard;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Canvas;
	import mx.controls.Alert;
	import mx.core.UIComponent;
	import mx.effects.Sequence;
	import mx.events.MoveEvent;
	import mx.events.ResizeEvent;

	public class DockBox extends Canvas 
	{ 
		public var ColArray:Array=[];
		public var selectionCol:Array=[];
		public var clickobj:Array=[];
		public var inheritGridSize:Boolean = false; 
		public var dargX:Number;
		public var dargY:Number;
		
		
		public var autoUpdateGrid:Boolean = false; 
		
		private var _isselection:Boolean=false;
		
		private var sizingGrid:Boolean = false; 
		
		public function get isselection():Boolean
		{
			return _isselection;
		}

		public function set isselection(value:Boolean):void
		{
			_isselection = value;
		}

		public function set gridSize( value:int ):void 
		{ 
			sizingGrid = true; 
			
			handle.visible = false; 
			
			if( value <= 0 ){ 
				value = gridSize; 
			} 
			
			if( _gridSize == int(undefined) ){ 
				_gridSize = value; 
				sizingGrid = false; 
				return; 
			} 
			
			var lastGridSize:int = _gridSize; 
			
			_gridSize = value; 
			
			if( autoUpdateGrid ){ 
				
				for each( var child:DisplayObject in getChildren() ){ 
					
					if( child is UIComponent ){ 
						
						if( !UIComponent( child ).includeInLayout ){ 
							continue; 
						} 
						
					} 
					
					child.x = ( child.x/lastGridSize ) * _gridSize; 
					child.y = ( child.y/lastGridSize ) * _gridSize; 
					child.width = ( child.width/lastGridSize ) * _gridSize; 
					child.height = ( child.height/lastGridSize ) * _gridSize; 
					
					if( child is DockBox ){ 
						
						var dockBoxChild:DockBox = DockBox( child ); 
						
						if( dockBoxChild.inheritGridSize ){ 
							
							dockBoxChild.gridSize = value; 
							
						} 
						
					} 
					
				} 
				
			} 
			
			callLater( setSizingGridFalse ); 
			
		} 
		
		private function setSizingGridFalse():void 
		{ 
			sizingGrid = false; 
		} 
		
		private var defaultGridSize:int = 10; 
		public var _gridSize:int; 
		
		public function get gridSize():int 
		{ 
			if( _gridSize == int(undefined) ){ 
				return defaultGridSize; 
			} 
			
			return _gridSize; 
		} 
		
		
		
		public var resizeMargin:Number = 12; 
		
		private var resizingChild:UIComponent; 
		
		private var handle:Canvas = new Canvas(); 
//		private var selection:Canvas = new Canvas();
		public function DockBox() 
		{ 
			super(); 
			
//			selection.visible = true; 
//			selection.setStyle("backgroundColor",0xcccccc);
//			selection.setStyle("backgroundAlpha",0.1);
			//create resize-handle 
			handle.includeInLayout = false; 
			handle.visible = false; 
			handle.width = handle.height = resizeMargin; 
			
			handle.graphics.lineStyle(1,0x000000); 
			handle.graphics.beginFill( 0x00AADD,0.6 ); 
		 
			handle.graphics.moveTo( handle.width/4, handle.height ); 
			handle.graphics.lineTo( handle.width, handle.height/4 ); 
			handle.graphics.lineTo( handle.width, handle.height ); 
			handle.graphics.lineTo( handle.width/4, handle.height ); 
			
			handle.graphics.moveTo( handle.width/1.7, handle.height ); 
			handle.graphics.lineTo( handle.width, handle.height/1.7 ); 
			handle.graphics.lineTo( handle.width, handle.height ); 
			handle.graphics.lineTo( handle.width/1.7, handle.height ); 
			
			handle.graphics.endFill(); 
			
			handle.addEventListener( MouseEvent.MOUSE_DOWN, startDragChild ); 
			
			this.addChild( handle ); 
//			this.addChild(selection); 
//			selection.removeEventListener(MouseEvent.MOUSE_DOWN, startDragChild ); 
//			selection.removeEventListener(MouseEvent.MOUSE_UP, stopDragChild ); 
//			selection.removeEventListener(MoveEvent.MOVE, snapChildToGrid ); 
//			selection.removeEventListener(ResizeEvent.RESIZE, resizeChildToGrid ); 
//			selection.removeEventListener(MouseEvent.CLICK,showHandle)
//			selection.removeEventListener( KeyboardEvent.KEY_DOWN, onKeyDown );
//			
			this.addEventListener( Event.ADDED_TO_STAGE, initListeners ); 
			
		} 
		
		private function initListeners( event:Event ):void 
		{ 
			this.stage.addEventListener( MouseEvent.MOUSE_UP, clearSizingListener ); 
		} 
		
		public override function addChildAt(child:DisplayObject, index:int):DisplayObject 
		{ 
			sizingGrid = true; 
			
			super.addChildAt( child, index ); 
			
			if( child is UIComponent ){ 
				if( UIComponent( child ).includeInLayout ){ 
					
					child.addEventListener( MouseEvent.MOUSE_DOWN, startDragChild ); 
					child.addEventListener( MouseEvent.MOUSE_UP, stopDragChild ); 
					child.addEventListener( MoveEvent.MOVE, snapChildToGrid ); 
					child.addEventListener( ResizeEvent.RESIZE, resizeChildToGrid ); 
					child.addEventListener(MouseEvent.CLICK,showHandle)
					child.addEventListener( KeyboardEvent.KEY_DOWN, onKeyDown );
					var cavnas:Marker = new Marker();
					this.addChild(cavnas);
					this.ColArray.push({obj:child,cav:cavnas});
					
//					if( child.x < 0 ){ 
//						child.x = 0; 
//					} 
//					
//					if( child.y < 0 ){ 
//						child.y = 0; 
//					} 
//					if( (child.x+child.width) > this.width ){ 
//						child.x =this.width-child.width; 
//					} 
//					if( (child.y+child.height) > this.height ){ 
//						child.y = this.height-child.height; 
//					} 
					
					
				} 
			} 
			
			callLater( setSizingGridFalse ); 
			
			return child; 
		} 
		
		var inat:Boolean=false;
		
		
		
		
		 public function showHandle(e:MouseEvent):void
		{
		
			e.currentTarget.setFocus();
			var child:UIComponent = UIComponent( e.currentTarget ); 
			
			if( !userInteracting ){ 
				resizingChild = child; 
			} 
			
			placeHandle(handle,child ); 
			bringHandleToFront(); 
			handle.visible = true; 
			
			
			for(var i:int=0;i<ColArray.length;i++){
				if(e.currentTarget==ColArray[i].obj)
				{
					
					for(var s:int=0;s<this.selectionCol.length;s++){
						try{
						if(e.currentTarget==selectionCol[s].obj)
						{
							inat=true;
						}
						}
						catch(e:Error)
						{}
					}
					if(inat)
					{
						this.dargX=this.dargX-e.currentTarget.x;
  				        this.dargY=this.dargY-e.currentTarget.y;
						
						for(var q:int=0;q<this.selectionCol.length;q++){
							if(e.currentTarget==selectionCol[q].obj)
							{
								selectionCol[q].cav.x=e.currentTarget.x;
								selectionCol[q].cav.y=e.currentTarget.y;
								setChildIndex( selectionCol[q].cav, numChildren-1 );
							}
							else
							{
						
							selectionCol[q].obj.x=selectionCol[q].obj.x-this.dargX;
							selectionCol[q].obj.y=selectionCol[q].obj.y-this.dargY;
							
							
							selectionCol[q].cav.x=selectionCol[q].obj.x;
							selectionCol[q].cav.y=selectionCol[q].obj.y;
							setChildIndex( selectionCol[q].cav, numChildren-1 ); 
							
							}
						}
							
							
					}
					else
					{
						
						for(var k:int=0;k<this.selectionCol.length;k++){
							selectionCol[k].cav.visible = false;
						}
						this.selectionCol=[];
						try
						{
							
							clickobj[0].cav.visible = false;
							
						}
						catch(e:Error)
						{}
						clickobj=[];
						clickobj.push({obj:ColArray[i].obj,cav:ColArray[i].cav});
						
						this.clickobj[0].cav.x=this.clickobj[0].obj.x-1;
						this.clickobj[0].cav.y=this.clickobj[0].obj.y-1;
						setChildIndex( this.clickobj[0].cav, numChildren-1 ); 
						
						clickobj[0].cav.visible = true;
					}

				}
				
			}
			inat=false;
			

			
		}
		private function onKeyDown(e:KeyboardEvent):void
		{
			
           
			try
			{
				
				switch(e.keyCode )
				{
					case Keyboard.UP : 
					{e.stopPropagation();e.currentTarget.y= e.currentTarget.y-gridSize;
						
						for(var q:int=0;q<this.selectionCol.length;q++){
							if(e.currentTarget==selectionCol[q].obj)
							{
								
							}
							else
							{
						    selectionCol[q].obj.y=selectionCol[q].obj.y-gridSize;
							selectionCol[q].cav.y=selectionCol[q].obj.y;						
							}
						}
						
						
					}
						break;
					case Keyboard.DOWN :
					{e.stopPropagation();e.currentTarget.y= e.currentTarget.y+gridSize; 
						for(var q:int=0;q<this.selectionCol.length;q++){
							if(e.currentTarget==selectionCol[q].obj)
							{
								
							}
							else
							{
								selectionCol[q].obj.y=selectionCol[q].obj.y+gridSize;
								selectionCol[q].cav.y=selectionCol[q].obj.y;						
							}
						}
					}
						break;
					case Keyboard.RIGHT :
					{e.stopPropagation();e.currentTarget.x= e.currentTarget.x+gridSize;
						for(var q:int=0;q<this.selectionCol.length;q++){
							if(e.currentTarget==selectionCol[q].obj)
							{
								
							}
							else
							{
								selectionCol[q].obj.x=selectionCol[q].obj.x+gridSize;
								selectionCol[q].cav.x=selectionCol[q].obj.x;						
							}
						}
					}
						break;
					case Keyboard.LEFT :
					{e.stopPropagation();e.currentTarget.x= e.currentTarget.x-gridSize; 
						for(var q:int=0;q<this.selectionCol.length;q++){
							if(e.currentTarget==selectionCol[q].obj)
							{
								
							}
							else
							{
								selectionCol[q].obj.x=selectionCol[q].obj.x-gridSize;
								selectionCol[q].cav.x=selectionCol[q].obj.x;						
							}
						}
					}break;             
					default:return; 
				}
				
				
			}catch(e:Error){}
		}
		
		private function showChildHandle( event:MouseEvent ):void 
		{ 
			
			var child:UIComponent = UIComponent( event.currentTarget ); 
			
			if( !userInteracting ){ 
				resizingChild = child; 
			} 
			
			placeHandle(handle,child ); 
			bringHandleToFront(); 
			handle.visible = true; 
		} 
		
		private function bringHandleToFront():void 
		{ 
			setChildIndex( handle, numChildren-1 ); 
		} 
		
		private function placeHandle(biaoj:Object, child:UIComponent ):void 
		{ 
			
				
			biaoj.x = child.x + child.width - biaoj.width; 
			biaoj.y = child.y + child.height - biaoj.height; 
			
			
		} 
		
		private function selectplaceHandle(child2:UIComponent ):void 
		{ 
			var child:Object = Object(child2);
			for(var i:int=0;i<ColArray.length;i++){
				if(child==ColArray[i].obj)
				{
					
					for(var s:int=0;s<this.selectionCol.length;s++){
						try{
							if(child==selectionCol[s].obj)
							{
								inat=true;
							}
						}
						catch(e:Error)
						{}
					}
					if(inat)
					{
						
						for(var q:int=0;q<this.selectionCol.length;q++){
							if(child==selectionCol[q].obj)
							{
								selectionCol[q].cav.x=child.x;
								selectionCol[q].cav.y=child.y;
							}
							else
							{
							
							}
						}
						
						
						
					}
					else
					{
						
						for(var k:int=0;k<this.selectionCol.length;k++){
							selectionCol[k].cav.visible = false;
						}
						this.selectionCol=[];
						try
						{
							
							clickobj[0].cav.visible = false;
							
						}
						catch(e:Error)
						{}
						clickobj=[];
						clickobj.push({obj:ColArray[i].obj,cav:ColArray[i].cav});

						this.clickobj[0].cav.x=this.clickobj[0].obj.x-1;
						this.clickobj[0].cav.y=this.clickobj[0].obj.y-1;
						
						
						clickobj[0].cav.visible = true;
					}

				}
				
			}
			inat=false;
			
			
			
		} 
		

		private function hideChildHandle( event:MouseEvent ):void 
		{ 
			if( !userInteracting && !UIComponent( event.currentTarget ).getBounds( this ).contains( mouseX, mouseY ) && !handle.getBounds( this ).contains( mouseX, mouseY ) ){ 
//				handle.visible = false; 
			} 
		} 
		
		public function startDragChild( event:MouseEvent ):void 
		{ 
			
//			if(this.selectionCol.length>0 && this.isselection==true && event.currentTarget!=selection )
//			{
//				event.stopImmediatePropagation();
//                for(var i:int=0;i<selectionCol.length;i++)
//				{
//					selection.addChild((selectionCol[i].obj as DisplayObject));
//					selection.addChild((selectionCol[i].cav as DisplayObject));
//				}
//		        selection.startDrag(false);
//			}
//			else
//			{
			
	 
			this.dargX=event.currentTarget.x;
			this.dargY=event.currentTarget.y;
			event.stopImmediatePropagation();
			var child:UIComponent = UIComponent( event.currentTarget ); 
			setChildIndex( child, numChildren-1 ); 
			if( event.target == handle ){ 
				child = resizingChild; 
			}else{ 
				resizingChild = child; 
			} 
			
			userInteracting = true; 
			
			if( (event.localX > child.width - resizeMargin && event.localY > child.height - resizeMargin) || event.target == handle ){ 
				this.addEventListener( MouseEvent.MOUSE_MOVE, sizeChild, false, 0, true ); 
			}else{ 
				
				if( !(event.target is DockBox) && event.currentTarget is DockBox){ 
					return; 
				} 
				
				if( event.shiftKey ){ 
					return; 
				} 
				
				
				
				child.startDrag( false ); 
				handle.visible = true; 
				
				
			} 
//			}
//			super.setChildIndex( child, this.numChildren-1 ); 
			
			drawGrid(); 
		} 
		
		public function stopDragChild( event:MouseEvent ):void 
		{ 
		
//			if(this.selectionCol.length>0 && this.isselection==true)
//			{
//				for(var i:int=0;i<this.selectionCol.length ;i++)
//				{
//					selectionCol[i].obj.x=selectionCol[i].obj.x+this.selection.x;
//					selectionCol[i].obj.y=selectionCol[i].obj.y+this.selection.y;
//					selectionCol[i].cav.x=selectionCol[i].obj.x;
//					selectionCol[i].cav.y=selectionCol[i].obj.y;	
//					this.addChild((selectionCol[i].obj as DisplayObject));
//					this.addChild((selectionCol[i].cav as DisplayObject));
//				}
//
//				this.selection.stopDrag();
//				
//			}
//			else
//			{
			UIComponent( event.currentTarget ).stopDrag(); 
//			}
//			this.selection.x=0;
//			this.selection.y=0;
		} 
		
		private function snapChildToGrid( event:MoveEvent ):void 
		{ 
			 
	/*		 if( sizingGrid ){ 
				return; 
			} 
			
			var child:UIComponent = UIComponent( event.currentTarget ); 
			
			if( child.x%gridSize < gridSize/2 ){ 
				child.x = child.x - child.x%gridSize; 
			}else{ 
				child.x = child.x - (child.x%gridSize) + gridSize; 
			} 
			
			if( child.y%gridSize < gridSize/2 ){ 
				child.y = child.y - child.y%gridSize; 
			}else{ 
				child.y = child.y - (child.y%gridSize) + gridSize; 
			} 
			
			if( child.x < 0 ){ 
				child.x = 0; 
			} 
			
			if( child.y < 0 ){ 
				child.y = 0; 
			} 
		 	if( (child.x+child.width) > this.width ){ 
				child.x =this.width-child.width; 
			} 
			if( (child.y+child.height) > this.height ){ 
				child.y = this.height-child.height; 
			}  */
			
			
		/*	
			placeHandle(handle,child ); 
			selectplaceHandle(child);*/
			 
		} 
		
		public function resizeChildToGrid( event:ResizeEvent ):void 
		{ 
			if( sizingGrid ){ 
				return; 
			} 
			
			var child:UIComponent = UIComponent( event.currentTarget ); 
			child.width = child.width - child.width%gridSize; 
			child.height = child.height - child.height%gridSize; 
			
			if( child.width < gridSize ){ 
				child.width = gridSize; 
			} 
			
			if( child.height < gridSize ){ 
				child.height = gridSize; 
			} 
			
			placeHandle(handle,child ); 
			
		} 
		
		private var userInteracting:Boolean = false; 
		
		private function clearSizingListener( event:MouseEvent ):void 
		{ 
			if( resizingChild ){ 
				resizingChild.stopDrag(); 
			} 
			userInteracting = false; 
			handle.visible = false; 
//			clickobj[0].cav.visible=false;
			drawBG(); 
			this.removeEventListener( MouseEvent.MOUSE_MOVE, sizeChild ); 
		} 
		
		private function sizeChild( event:MouseEvent ):void 
		{ 
			resizingChild.width = mouseX + horizontalScrollPosition - resizingChild.x; 
			resizingChild.height = mouseY + verticalScrollPosition - resizingChild.y; 
		} 
		
		private var backgroundAlphaLevel:Number; 
		
		private function drawBG():void 
		{ 
			var bgColor:uint = this.getStyle( "backgroundColor" ) as uint; 
			var bgAlpha:Number = this.getStyle( "backgroundAlpha" ) as Number; 
			
			if( bgAlpha == Number( undefined ) ){ 
				bgAlpha = 1; 
			} 
			
			if( this.backgroundAlphaLevel == Number( undefined ) ){ 
				this.backgroundAlphaLevel = bgAlpha; 
				this.setStyle( "backgroundAlpha", 0 ); 
			}else{ 
				bgColor = this.backgroundAlphaLevel; 
			} 
			
			if( bgColor == uint( undefined ) ){ 
				bgColor = 0x000000; 
				bgAlpha = 0; 
			} 
			
			
			
			this.graphics.clear(); 
			this.graphics.lineStyle(0,0,0); 
			this.graphics.beginFill( bgColor, bgAlpha ); 
			this.graphics.drawRect( 0, 0, this.width, this.height );
			this.graphics.endFill(); 
		} 
		
		
		private function drawGrid():void 
		{ 
			drawBG(); 
			
			//draw grid line 
			var rows:int = Math.round(this.height / gridSize); 
			var cols:int = Math.round(this.width / gridSize); 
			
			this.graphics.lineStyle(0.7, 0x00AADD); 
			this.graphics.beginFill( 0, 0 ); 
			
			for( var c:int = 0; c < cols; c++ ) 
			{ 
				for( var r:int = 0; r < rows; r++ ) 
				{ 
					var toX:Number = c * gridSize - ( horizontalScrollPosition%gridSize ); 
					var toY:Number = r * gridSize - ( verticalScrollPosition%gridSize ); 
					if( toX + gridSize <= this.width && toY + gridSize <= this.height ){ 
						if( toX >= 0 && toY >= 0 ){ 
							this.graphics.drawRect(toX, toY, gridSize, gridSize); 
						} 
					} 
				} 
			} 
			
			this.graphics.endFill(); 
			
		} 
		
		
	} 
}