package BI
{
	import BIAnalysis.BIData;
	
	import flash.events.Event;
	
	import mx.controls.Alert;
	import mx.controls.CheckBox;
	import mx.controls.listClasses.ListBase;
	import mx.utils.ArrayUtil;
	
	public class CheckBoxItemRenderer extends CheckBox{
		
		/**存储当前列数据对象**/
		private var currData:Object; 
		
		public function CheckBoxItemRenderer(){
			super();
			this.addEventListener(Event.CHANGE,onClickCheckBox);
		}
		override public function set data(value:Object):void{
		     if(value!=null){
			this.selected =value.selected;
			this.currData = value; 
			this.label=value.columname;
		     }
		     if(!BIData.modeltype){
		     	
		     	      var xml:XML=BIData.controlxml;
		              var xmllist:XMLList=new XMLList(xml.child("Graphic"));
		              var arr:Array=String(xmllist[0].@xuanzhezhibiao).split(",");
		              arr.pop();
		              
		     if(BIData.index<arr.length+2) {   
		              for(var i:int=0;i<arr.length;i++){
			                if(value.columnid==arr[i]){
			                this.selected =true;
							this.currData = value; 
							this.label=value.columname;
							var listBase:ListBase = ListBase(listData.owner);
							var selectedItems:Array = listBase.selectedItems;
							currData.selected = this.selected;
							if(this.selected){
								selectedItems.push(currData);
							}
							else{
								selectedItems.splice(ArrayUtil.getItemIndex(currData,selectedItems),1);
							}
							listBase.selectedItems=selectedItems;
			                }
			           } 
			     BIData.index++;
			      }  
	             }  
		}
		override public function set enabled(value:Boolean):void{
			if(currData){
				value=currData.enabled==false?false:true;
			}
			super.enabled=value;
		}
		private function onClickCheckBox(e:Event):void{	
			var listBase:ListBase = ListBase(listData.owner);
			var selectedItems:Array = listBase.selectedItems;
			currData.selected = this.selected;
			if(this.selected){
				selectedItems.push(currData);
			}
			else{
				selectedItems.splice(ArrayUtil.getItemIndex(currData,selectedItems),1);
			}
			listBase.selectedItems=selectedItems;
		}
	}
}