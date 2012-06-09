package com.resistdesign.drawTable
{
 
	import com.adobe.viewsource.ViewSource;
	import com.resistdesign.ColorPicker;
	import com.resistdesign.Component;
	import com.resistdesign.DataReport;
	import com.resistdesign.EvTijiao;
	import com.resistdesign.GlobalManager;
	import com.resistdesign.Objbinstatis;
	import com.resistdesign.reportcolr.Columns;
	import com.resistdesign.reportcolr.Dataset;
	import com.resistdesign.view.reportbox;
	
	import components.reports.reportmanage;
	
	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.DisplayObject;
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.geom.Matrix;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ArrayList;
	import mx.collections.IList;
	import mx.containers.Box;
	import mx.controls.Alert;
	import mx.controls.DateField;
	import mx.controls.Image;
	import mx.controls.NumericStepper;
	import mx.core.ClassFactory;
	import mx.core.DragSource;
	import mx.core.IFlexDisplayObject;
	import mx.core.IUIComponent;
	import mx.core.UIComponent;
	import mx.core.mx_internal;
	import mx.events.DragEvent;
	import mx.events.FlexEvent;
	import mx.events.MoveEvent;
	import mx.events.ResizeEvent;
	import mx.events.ToolTipEvent;
	import mx.managers.DragManager;
	import mx.managers.PopUpManager;
	import mx.skins.spark.*;
	import mx.utils.NameUtil;
	import mx.utils.object_proxy;
	
	import spark.components.Button;
	import spark.components.CheckBox;
	import spark.components.ComboBox;
	import spark.components.DropDownList;
	import spark.components.Label;
	import spark.components.TextArea;
	import spark.components.TextInput;
	import spark.events.ListEvent;
	
	public class TableRader extends Box
	{
//		private var txip:Button;
		private var _col:UIComponent;
		public var havechild:Boolean=false;
		private var _childtyp:String="";
		[Bidanble]
		public var _value:String="";
		
		private var _gap:Number=4;
		
		public function get value():String
		{
			return _value;
		}
		
		public function set value(value:String):void
		{
			_value = value;	
		}
		
		[Bindable]
		public function get gap():Number
		{
			return _gap;
		}

		public function set gap(value:Number):void
		{
			_gap = value;
		}

		public function get childtyp():String
		{
			return _childtyp;
		}

		public function set childtyp(value:String):void
		{
			_childtyp = value;
		}

		public function get col():UIComponent
		{
			return _col;
		}
		
		public function set col(value:UIComponent):void
		{

			_col = value;

		}
		
		
		public function TableRader()
		{
			super();
			switch(DataReport.model)
			{
				
				case "table":
					setStyle("backgroundColor","#FF3366");
				 break;
				case "tablewei":
					setStyle("backgroundColor","#FFFF99");
					break;
				case "dyrecord":
					setStyle("backgroundColor","#33CCFF");
					break;
				case "record":
					setStyle("backgroundColor","#33CCFF");
					break;
			}

			setStyle("horizontalAlign","center");
			setStyle("verticalAlign","middle");
			setStyle("backgroundAlpha",0.07);
		/*	if(!DataReport.modeltype){
			var xml:XML=DataReport.tdxml
			var Labellist:XMLList=xml.child("Label"); 
			var Columnslist:XMLList=xml.child("Columns");
			var Datasetlist:XMLList=xml.child("Dataset");
			if(Labellist.length()>0){
				 
				var lab:Label =new Label();
				lab.text=Labellist[0].@text;
				lab.id=Labellist[0].@id;
				lab.width=DataReport.tdxml.@width-4;
				lab.height=DataReport.tdxml.@height-2;
				lab.setStyle("textAlign","center");
				lab.setStyle("verticalAlign","middle");
				//reportmanage.reportdesign.lbid++; 
				
				this.addChild(lab);
				lab.setStyle("textAlign","center");
				lab.setStyle("verticalAlign","middle");
				havechild=true; 
				 lab.addEventListener(MouseEvent.MOUSE_DOWN,reportmanage.reportdesign.colclick);
				 lab.addEventListener(MouseEvent.MOUSE_DOWN,dragSource);
				 reportmanage.addreportarr(lab as Object,"label",true); 
				
 
			}
			if(Columnslist.length()>0){
				var colums:Columns =new Columns();
				colums.id=Columnslist[0].@id;
				colums.name="colums";
				colums.width=DataReport.tdxml.@width-4;
				colums.height=DataReport.tdxml.@height-2;
				colums["bincolums"]=Columnslist[0].@columns;
				colums.label=Columnslist[0].@text;
				var obj:Objbinstatis=new Objbinstatis();
				if(Columnslist[0].@statistyp=="ToSum"){
				 obj.label="累计";
				 obj.value="ToSum";
				}if(Columnslist[0].@statistyp=="Toavg"){
					obj.label="平均值";
					obj.value="Toavg";
				}if(Columnslist[0].@statistyp=="-NuLL-"){
					obj.label="无";
					obj.value="-NuLL-";
				}
				var stringtyp:Objbinstatis=new Objbinstatis();
				if(Columnslist[0].@stringtyp=="-NuLL-"){
					stringtyp.label="无";
					stringtyp.value="-NuLL-";
				}if(Columnslist[0].@stringtyp=="ToNum"){
					stringtyp.label="转成数字";
					stringtyp.value="ToNum";
				}if(Columnslist[0].@stringtyp=="ToString"){
					stringtyp.label="转成字符串";
					stringtyp.value="ToString";
				}if(Columnslist[0].@stringtyp=="ToCmoney"){
					stringtyp.label="转成中文金额";
					stringtyp.value="ToCmoney";
				}
 
				colums.binstatis=obj;
				colums.binchange=stringtyp;
				colums.doubleClickEnabled=true;
				colums.addEventListener(MouseEvent.DOUBLE_CLICK,doubleclick);
				colums.setStyle("textAlign","center");
				colums.setStyle("verticalAlign","middle");
				childtyp="rptcolums";
				colums.addEventListener(MouseEvent.MOUSE_DOWN,reportmanage.reportdesign.colclick);
				colums.addEventListener(MouseEvent.MOUSE_DOWN,dragSource);
				reportmanage.addreportarr(colums as Object,"Colums",true);
				havechild=false;
				this.addChild(colums);
			}
			if(Datasetlist.length()>0){
				var dataset:Dataset =new Dataset();
				dataset.id=Datasetlist[0].@id;
				dataset.label=Datasetlist[0].@text;
				dataset.sqlstr=Datasetlist[0].sql;
				dataset.setStyle("textAlign","center");
				dataset.setStyle("verticalAlign","middle");
				dataset.width=DataReport.tdxml.@width-4;
				dataset.height=DataReport.tdxml.@height-2;
				
				this.addChild(dataset);
				childtyp="rptdataset";
				dataset.addEventListener(MouseEvent.MOUSE_DOWN,reportmanage.reportdesign.colclick);
				dataset.addEventListener(MouseEvent.MOUSE_DOWN,dragSource);
				reportmanage.addreportarr(dataset as Object,"Dataset",true);
				havechild=true;
			}
			this.width=DataReport.tdxml.@width;
			
			this.height=DataReport.tdxml.@width;
		}*/
			
//			txip=new Button();
			GlobalManager.getInstance().addEventListener(DataReport.Evnt_select,AddLable);  
			GlobalManager.getInstance().addEventListener(DataReport.Evnt_ziduan,bangdingziduan);  
            GlobalManager.getInstance().addEventListener(DataReport.Evnt_addCLICK,Addclick);
			GlobalManager.getInstance().addEventListener(DataReport.Evnt_Addclickwei,Addclickwei);
			this.addEventListener(FlexEvent.CREATION_COMPLETE,init);
			this.addEventListener(DragEvent.DRAG_ENTER,onEnter);
			this.addEventListener(DragEvent.DRAG_DROP,onDrop);
		}
		//添加数据集
		public function Addclickwei(e:Component):void{
			if(this==e.getComponent1()){
			 var com:ComboBox=new ComboBox();
			 ComboBox(com).name="com";
			 var c2arr:ArrayCollection=new ArrayCollection([  
				     {data:"label",label:"标签"},{data:"Dataset",label:"数据集"},{data:"column",label:"字段绑定"}]  
				     );
			  ComboBox(com).enabled=true;
			  ComboBox(com).width=this.width-4;
			  ComboBox(com).dataProvider =c2arr;	
		      ComboBox(com).addEventListener(Event.CHANGE,over);
			 this.addChild(com); 
			 
			}
		}
		public function over(e:Event):void{
 
		 if(ComboBox(this.getChildByName("com")).selectedItem.data=="label"){
			 this.removeAllChildren();
			 var lab:Label =new Label();
			 lab.text="Label"+reportmanage.reportdesign.lbid;
			 lab.id="Label"+reportmanage.reportdesign.lbid;
			 lab.setStyle("textAlign","center");
			 lab.setStyle("verticalAlign","middle");
			 reportmanage.reportdesign.lbid++;
			 lab.width=this.width;
			 this.addChild(lab);
			 lab.setStyle("textAlign","center");
			 lab.setStyle("verticalAlign","middle");
			 childtyp="rptlabel";
			 lab.addEventListener(MouseEvent.MOUSE_DOWN,reportmanage.reportdesign.colclick);
			 lab.addEventListener(MouseEvent.MOUSE_DOWN,dragSource);
			 reportmanage.addreportarr(lab as Object,"label",true);
			 havechild=true;
		 }if(ComboBox(this.getChildByName("com")).selectedItem.data=="Dataset"){
			 this.removeAllChildren();
			 var dataset:Dataset =new Dataset();
			 dataset.id="dataset"+reportmanage.reportdesign.dsid;
			 dataset.label="dataset"+reportmanage.reportdesign.dsid;
			 dataset.setStyle("textAlign","center");
			 dataset.setStyle("verticalAlign","middle");
			 reportmanage.reportdesign.dsid++;
			 dataset.width=this.width;
			 this.addChild(dataset);
			 dataset.setStyle("textAlign","center");
			 dataset.setStyle("verticalAlign","middle");
			 childtyp="rptdataset";
			 dataset.addEventListener(MouseEvent.MOUSE_DOWN,reportmanage.reportdesign.colclick);
			 dataset.addEventListener(MouseEvent.MOUSE_DOWN,dragSource);
			 reportmanage.addreportarr(dataset as Object,"Dataset",true);
			 havechild=true;
			 
		 }
		 if(ComboBox(this.getChildByName("com")).selectedItem.data=="column"){
			 this.removeAllChildren(); 
			 var colums:Columns =new Columns();
			 colums.id="colums"+reportmanage.reportdesign.dsid;
			 colums.name="colums"; 
			 colums.label="Columns"+reportmanage.reportdesign.dsid;
			 reportmanage.reportdesign.dsid++;
			 colums.width=this.width;
			 colums.doubleClickEnabled=true;
			 colums.addEventListener(MouseEvent.MOUSE_DOWN,doubleclick);
			 colums.setStyle("textAlign","center");
			 colums.setStyle("verticalAlign","middle");
			 childtyp="rptcolums";
			 colums.addEventListener(MouseEvent.MOUSE_DOWN,reportmanage.reportdesign.colclick);
			 colums.addEventListener(MouseEvent.MOUSE_DOWN,dragSource);
			 reportmanage.addreportarr(colums as Object,"Colums",true);
			 this.addChild(colums);
			 havechild=true;
		  }
		}
		//单击添加
		public function Addclick(e:Component):void{
 		   if(this==e.getComponent1()){
			/*var lab:Label =new Label();
			lab.text="Label"+reportmanage.reportdesign.lbid;
			lab.id="Label"+reportmanage.reportdesign.lbid;
			lab.setStyle("textAlign","center");
			lab.setStyle("verticalAlign","middle");
			reportmanage.reportdesign.lbid++;
			lab.width=this.width;
			e.getComponent1().addChild(lab);
			lab.setStyle("textAlign","center");
			lab.setStyle("verticalAlign","middle");
			childtyp="rptlabel";
			lab.addEventListener(MouseEvent.MOUSE_DOWN,reportmanage.reportdesign.colclick);
			lab.addEventListener(MouseEvent.MOUSE_DOWN,dragSource);
			reportmanage.addreportarr(lab as Object,"label",true);
			havechild=true;*/
			   var com:ComboBox=new ComboBox();
			   ComboBox(com).name="com";
			   var c2arr:ArrayCollection=new ArrayCollection([  
				   {data:"label",label:"标签"},{data:"Dataset",label:"数据集"},{data:"column",label:"字段绑定"}]  
			   );
			   ComboBox(com).enabled=true;
			   ComboBox(com).width=this.width-4;
			   ComboBox(com).dataProvider =c2arr;	
			   ComboBox(com).addEventListener(Event.CHANGE,over);
			   this.addChild(com); 
		   }
		}
 
		//拖拽事件
	   private function onEnter(e:DragEvent):void
	   {
		   var myData:Object=new Object();
		   myData=e.dragSource.dataForFormat(e.dragSource.formats[0]);
//		   if(!havechild )
//		   {
		   DragManager.acceptDragDrop(IUIComponent(e.target));
//		   DragManager.
//		   }
	   }
		//绑定字段的数据
	   public function bangdingziduan(event:EvTijiao):void{
		   var arr:Array=event.Formcode().split("//");
		   DataReport.comp["bincolums"]=arr[1];
		   Columns(DataReport.comp).label=arr[0].toString();
		  
	   }
	   
	   //双击选择完的数据显示在单元格里
	   public function AddLable(event:Component):void{
		   if(this==event.getComponent1()){
			  
		   var comp:UIComponent=event.getComponent1() as UIComponent;
		   if(event.getComponent1().name=="colums"){
		    comp["label"]=event.getvaler();
			comp["bincolums"]=event.getvaler();
		   }else{
			   TableRader(event.getComponent1()).removeAllChildren();
			   var colums:Columns =new Columns();
			  var arr:Array=event.getvaler().split("//");
			   colums.id="colums"+reportmanage.reportdesign.clid;
			   colums.name="colums";
			   colums["bincolums"]=arr[1];
			   colums.label=arr[0].toString();
			   reportmanage.reportdesign.clid++;
			   colums.width=this.width;
			   colums.doubleClickEnabled=true;
			   colums.setStyle("textAlign","center");
			   colums.setStyle("verticalAlign","middle");
			   childtyp="rptcolums";
			   colums.addEventListener(MouseEvent.MOUSE_DOWN,reportmanage.reportdesign.colclick);
			   colums.addEventListener(MouseEvent.MOUSE_DOWN,dragSource);
			   reportmanage.addreportarr(colums as Object,"Colums",true);
			   havechild=false;
			   comp.addChild(colums);
		   }
		   }
	   }

	  public function  doubleclick(event:MouseEvent):void{
		  
		  var comp:UIComponent=event.target as UIComponent; 
		  DataReport.comp=comp; 
		  if(comp.parent.parent.parent.parent.parent.name=="record"){
			  if( comp.parent.parent.parent.parent.parent["sqlstr"]=="-NULL-" || comp.parent.parent.parent.parent.parent["sqlstr"]=="" ){
				  Alert.show("SQL数据不能空");
			  }else{		
				  DataReport.type="record"; 
				  DataReport.Sqlstr=comp.parent.parent.parent.parent.parent["sqlstr"];
				  var cp:ColorPicker=new ColorPicker();
				  PopUpManager.addPopUp(cp,comp.parent.parent.parent.parent.parent.parent,true);
				  PopUpManager.centerPopUp(cp as IFlexDisplayObject);
			  }
		  }
 
		  if(comp.parent.parent.parent.parent.parent.name=="dyrecord"){
			  if( comp.parent.parent.parent.parent.parent["sqlstr"]=="-NULL-" || comp.parent.parent.parent.parent.parent["sqlstr"]=="" || comp.parent.parent.parent.parent.parent["focdstr"]=="" ){
				  Alert.show("选择表单");
			  }else{
				  DataReport.type="dyrecord"; 
				  DataReport.focdstr=comp.parent.parent.parent.parent.parent["focdstr"];
				  var cp1:ColorPicker=new ColorPicker();
				  PopUpManager.addPopUp(cp1,comp.parent.parent.parent.parent.parent.parent,true);
				  PopUpManager.centerPopUp(cp1 as IFlexDisplayObject);
			  }
		  }
		  if(comp.parent.name=="colums"){
			  if( comp.parent.parent.parent.parent.parent["sqlstr"]=="-NULL-" || comp.parent.parent.parent.parent.parent["sqlstr"]=="" || comp.parent.parent.parent.parent.parent["focdstr"]=="" ){
				  Alert.show("选择表单");
			  }else{
				  DataReport.comp=event.target as UIComponent; 
				  DataReport.type="colums1"; 
				  DataReport.focdstr=comp.parent.parent.parent.parent.parent["focdstr"];
				  var cp1:ColorPicker=new ColorPicker();
				  PopUpManager.addPopUp(cp1,comp.parent.parent.parent.parent.parent.parent,true);
				  PopUpManager.centerPopUp(cp1 as IFlexDisplayObject);
			  }
		  }
		  if(comp.name=="colums"){
			  if( comp.parent.parent.parent.parent.parent["sqlstr"]=="-NULL-" || comp.parent.parent.parent.parent.parent["sqlstr"]=="" || comp.parent.parent.parent.parent.parent["focdstr"]=="" ){
				  Alert.show("选择表单");
			  }else{
				  DataReport.comp=event.target as UIComponent; 
				  DataReport.type="colums"; 
				  DataReport.focdstr=comp.parent.parent.parent.parent.parent["focdstr"];
				  var cp1:ColorPicker=new ColorPicker();
				  PopUpManager.addPopUp(cp1,comp.parent.parent.parent.parent.parent.parent,true);
				  PopUpManager.centerPopUp(cp1 as IFlexDisplayObject);
			  }
		  }
		  
	  }
	   //在表格里面new出新的控件
	   public function creatcolr(colrtyp:String):void
	   {
		   if(TableCav(this.parent.parent.parent.parent).name=="record" || TableCav(this.parent.parent.parent.parent).name=="dyrecord"){
		   
		   }else{
		   switch (colrtyp)
		   {
			   //--报表使用————
			   case "rptlabel":
			   {
				   var lab:Label =new Label();
				   lab.text="Label"+reportmanage.reportdesign.lbid;
				   lab.id="Label"+reportmanage.reportdesign.lbid;
				   lab.setStyle("textAlign","center");
				   lab.setStyle("verticalAlign","middle");
				   reportmanage.reportdesign.lbid++;
	
				   this.addChild(lab);
				   lab.setStyle("textAlign","center");
				   lab.setStyle("verticalAlign","middle");
				   childtyp=colrtyp;
				   lab.addEventListener(MouseEvent.MOUSE_DOWN,reportmanage.reportdesign.colclick);
				   lab.addEventListener(MouseEvent.MOUSE_DOWN,dragSource);
				   reportmanage.addreportarr(lab as Object,"label",true);
				   havechild=true;
				   
				   break;
			   }
			   case "rptdataset":
			   {
				   var dataset:Dataset =new Dataset();
				   dataset.id="dataset"+reportmanage.reportdesign.dsid;
				   dataset.label="dataset"+reportmanage.reportdesign.dsid;
				   dataset.setStyle("textAlign","center");
				   dataset.setStyle("verticalAlign","middle");
				   reportmanage.reportdesign.dsid++;
				   this.addChild(dataset);
				   dataset.setStyle("textAlign","center");
				   dataset.setStyle("verticalAlign","middle");
				   childtyp=colrtyp;
				   dataset.addEventListener(MouseEvent.MOUSE_DOWN,reportmanage.reportdesign.colclick);
				   dataset.addEventListener(MouseEvent.MOUSE_DOWN,dragSource);
				   reportmanage.addreportarr(dataset as Object,"Dataset",true);
				   havechild=true;
				 
				   
				   break;
			   }
			   case "rptcolums":
			   {
				   var colums:Columns =new Columns();
				   colums.id="colums"+reportmanage.reportdesign.clid;
				   colums.label="colums"+reportmanage.reportdesign.clid;
				   reportmanage.reportdesign.clid++;
				   this.addChild(colums);
				   colums.setStyle("textAlign","center");
				   colums.setStyle("verticalAlign","middle");
				   childtyp=colrtyp;
				   colums.addEventListener(MouseEvent.MOUSE_DOWN,reportmanage.reportdesign.colclick);
				   colums.addEventListener(MouseEvent.MOUSE_DOWN,dragSource);
				   reportmanage.addreportarr(colums as Object,"Colums",true);
				   havechild=true;
				   break;
			   }
				   
			   default:
			   {
				   Alert.show("未设置");
				   break;
			   }
		   }
		   }
			
	   }
	   
	   
	   private function onDrop(e:DragEvent):void
	   {
		   var myData:Object=new Object();
		   myData=e.dragSource.dataForFormat(e.dragSource.formats[0]);
 
		   switch(myData.soure)
		   {
			   case "celldrag":
			   {
				   var drag:UIComponent=UIComponent(e.dragInitiator);
 
				   if(drag.parent==this)
				   {
					   this.havechild=true;
					   this.col=drag;
				   }
				   else
				   {
					   
					   (drag.parent as  TableRader).havechild=false;
					   (drag.parent as  TableRader).col=null; 
					   
					   drag.removeEventListener(MouseEvent.MOUSE_DOWN,(drag.parent as  TableRader).dragSource);
					   
					   this.addChild(drag);
					   drag.addEventListener(MouseEvent.MOUSE_DOWN,dragSource);
					   
					   this.childtyp=(drag.parent as  TableRader).childtyp;
					   (drag.parent as  TableRader).childtyp="";
					   this.havechild=true;
				   }
				   break;
			   }
			   case "rptdesigndrag":
			   {
				  
				   var drag:UIComponent=UIComponent(e.dragInitiator);
				   
				   
				   var cav:reportbox= (drag.parent as  reportbox);
				   
				   drag.removeEventListener(MouseEvent.MOUSE_DOWN,dragSource);
				   
				   drag.removeEventListener( MouseEvent.MOUSE_DOWN, cav.startDragChild ); 
				   drag.removeEventListener( MouseEvent.MOUSE_UP, cav.stopDragChild ); 
				   drag.removeEventListener( MoveEvent.MOVE, cav.snapChildToGrid ); 
				   drag.removeEventListener( ResizeEvent.RESIZE,cav.resizeChildToGrid ); 
				   drag.removeEventListener(MouseEvent.CLICK,cav.showHandle)
				   drag.removeEventListener( KeyboardEvent.KEY_DOWN,cav.onKeyDown);
				   
				   drag.parent.removeChild(drag);
				   
				   this.addChild(drag);
				   
				   drag.addEventListener(MouseEvent.MOUSE_DOWN,dragSource);
				   
				   this.childtyp=NameUtil.getUnqualifiedClassName(drag);
				   this.havechild=true;
				   
				   
				   for(var k:int=0;k<reportmanage.reportcolarr.length;k++)
				   {
					   if(reportmanage.reportcolarr[k].col==drag)
					   {
						   reportmanage.reportcolarr[k].istable=true;
					   }
				   }
//				   
				   
				   break;
			   }
				   
				   
				   
				   
			   default:
			   {
				   creatcolr(myData.soure);
				   break;
			   }
		   }
	   }
		
	

		public override function addChildAt(child:DisplayObject, index:int):DisplayObject 
		{ 
			if(havechild==false)
			{
			super.addChildAt(child,index); 
		    this.col=child as UIComponent;
			/*this.col.width=this.parent.width-gap;
			this.col.height=this.parent.height-gap;*/
			}
			else
			{
				Alert.show("改单元格已有控件");
			}
			return child; 
		} 
		
		
		
		private function init(e:FlexEvent):void
		{
			this.width=this.parent.width;
			this.height=this.parent.height;

			
			this.parent.addEventListener(ResizeEvent.RESIZE,fit);
			this.parent.addEventListener(MoveEvent.MOVE,fit);
		}
		private function fit(e:*):void
		{
			this.width=e.currentTarget.width;
			this.height=e.currentTarget.height;
			
			if(havechild)
			{
				this.col.width=this.parent.width-gap;
				this.col.height=this.parent.height-gap;
			}
			
		}
		public function refresh():void
		{
			if(havechild)
			{
				this.col.width=this.parent.width-gap;
				this.col.height=this.parent.height-gap;
			}
			
		}
		
		
		
		
       //单元格内拖拽操作
		//开始
		public function dragSource(event:MouseEvent):void
		{
			var dragInitiator:UIComponent=event.currentTarget as UIComponent;
			
			
			
			var bd : BitmapData = new BitmapData(dragInitiator.width,dragInitiator.height,true,0);
			var m : Matrix = new Matrix();
			bd.draw(dragInitiator, m);
			
			
			
			var dragSource:DragSource=new DragSource();
			
			
			
			dragSource.addData({"x": event.localX, "y":event.localY,"soure":"celldrag"},"celldrag");
			var dragProxy:Image=new Image();
			dragProxy.source=new Bitmap( bd );
			
			DragManager.doDrag(dragInitiator,dragSource,event,dragProxy);
			
			event.stopPropagation();
			event.stopImmediatePropagation();
		}
	}
}