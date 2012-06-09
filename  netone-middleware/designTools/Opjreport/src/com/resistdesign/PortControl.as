package com.resistdesign
{
	import com.resistdesign.drawTable.TableCav;
	
	import components.reports.reportmanage;
	
	import flash.events.MouseEvent;
	import flash.utils.flash_proxy;
	
	import mx.containers.Canvas;
	import mx.controls.Alert;
	import mx.core.UIComponent;
	import mx.events.ResizeEvent;
	import mx.utils.NameUtil;
 
	public class PortControl extends Canvas
	{   
		public function PortControl() 
		{ 
			super(); 
			  var arr:Array=new Array();
	           GlobalManager.getInstance().addEventListener("addtable",tableheng);
			   GlobalManager.getInstance().addEventListener("daltable",Dalheng);
			   if(DataReport.modeltype){
				var table:TableCav =new TableCav();  
				table.id="table" 
				table.name="table";
				table.setStyle("Color","#FF3366");
				table._strname="table";
				table.createTable(1,4,360,28);
				table.addEventListener(MouseEvent.CLICK,colclick);
				this.addChild(table);
				reportmanage.addreportarr(table as Object,"table",false);
				
				table.x = this.x;
				table.y =this.y+10;
		        table.width=360;
				table.height=56;
				arr.push(table);
				var record1:TableCav =new TableCav();
				record1.isrecord=true;
				record1.id="dyrecord";
				record1.name="dyrecord";
				record1.ishead="dyrecord";
				record1.setStyle("Color","#33CCFF");
				record1._strname="dyrecord";
				record1.createTable(1,4,360,28);
				arr.push(record1);
				record1.x = table.x;
				record1.y = table.y+table.height;
				record1.width=360;
				record1.height=56;
				record1.addEventListener(MouseEvent.CLICK,colclick);
				this.addChild(record1);
				reportmanage.addreportarr(record1 as Object,"record",false); 
				
				
				var table1:TableCav =new TableCav();  
			
				table1.id="tablewei" 
				table1.name="tablewei";
				table1.x = record1.x;
				table1.y = record1.y+record1.height;
				table1.width=360;
				table1.height=56;
				table1.setStyle("Color","#FFFF99");
				table1.ishead="wei"
				table1._strname="tablewei";
				table1.createTable(2,4,360,56);
				table1.addEventListener(MouseEvent.CLICK,colclick);
				this.addChild(table1);
				reportmanage.addreportarr(table1 as Object,"table",false);
				arr.push(table1);
	            this.width=table1.width+70;
				this.height=table1.y+table1.height+30;
			   }else{
					   var layoutxml:XML=DataReport.controlxml;
				       var rdxml:XMLList=new XMLList(layoutxml.child("Record"));
					   var Tablexml:XMLList=new XMLList(layoutxml.child("Table"));  
					  
						   var tabletou:TableCav =new TableCav();  
						   tabletou.id="table" 
						   tabletou.name="table";
						   tabletou.ishead="tou";
						   tabletou.setStyle("Color","#FF3366");
						   tabletou._strname="table";
						   tabletou.x = 10;
						   tabletou.y = 10;
						   tabletou.sqlstr=Tablexml[0].sql;
						   tabletou.width=Number(Tablexml[0].@itemwidth);
						   tabletou.height=Number(Tablexml[0].@itemheight)+28;
						   tabletou.UpDatecreateTable(Number(Tablexml[0].@rows),Number(Tablexml[0].@cols),Number(Tablexml[0].@itemwidth),Number(Tablexml[0].@itemheight),Tablexml[0] as XML);
						 /*  tabletou.createTable(Number(Tablexml[0].@rows),Number(Tablexml[0].@cols),Number(Tablexml[0].@itemwidth),Number(Tablexml[0].@itemheight));*/
						   tabletou.addEventListener(MouseEvent.CLICK,colclick);
						   this.addChild(tabletou);
						   reportmanage.addreportarr(tabletou as Object,"table",false);
						   arr.push(tabletou);
						   //Record 的数据解析
						   
						   var uprecord:TableCav =new TableCav();	   
						   uprecord.isrecord=true;   
						   
						   if(rdxml[0].@ishead=="record"){
							   uprecord.drive=rdxml[0].@Drive;
							   uprecord.id="record";
							   uprecord.name="record";
							   uprecord.ishead=rdxml[0].@ishead;
							   uprecord.setStyle("Color","#33CCFF");
							   uprecord._strname="record";
							   uprecord.sqlstr=rdxml[0].sql;
						   }else{
							   uprecord.id="dyrecord";
							   uprecord.name="dyrecord";
							   uprecord.ishead=rdxml[0].@ishead;
							   uprecord.setStyle("Color","#33CCFF");
							   uprecord._strname="dyrecord";
							   uprecord.focdstr=rdxml[0].@focdstr;
							   uprecord.sqlstr=rdxml[0].@sqlstr;
						   }
						  // uprecord.createTable(Number(rdxml[0].@rows),Number(rdxml[0].@cols),Number(rdxml[0].@itemwidth),Number(rdxml[0].@itemheight));  
						   uprecord.UpDatecreateTable(Number(rdxml[0].@rows),Number(rdxml[0].@cols),Number(rdxml[0].@itemwidth),Number(rdxml[0].@itemheight),rdxml[0] as XML);  
						   uprecord.x = tabletou.x;
						   uprecord.y = tabletou.y+tabletou.height;
						   uprecord.width=Number(rdxml[0].@itemwidth);
						   uprecord.height=Number(rdxml[0].@itemheight)+28;
						   uprecord.addEventListener(MouseEvent.CLICK,colclick);
						   this.addChild(uprecord);
						   reportmanage.addreportarr(uprecord as Object,"record",false); 
						   arr.push(uprecord);
						   
							   var tablewei:TableCav =new TableCav();  
							   
							   tablewei.id="tablewei" 
							   tablewei.name="tablewei";
							   tablewei.x = uprecord.x;
							   tablewei.y = uprecord.y+uprecord.height;
							   tablewei.width=Number(Tablexml[1].@itemwidth);
							   tablewei.height=Number(Tablexml[1].@itemheight)+28;
							   tablewei.setStyle("Color","#FFFF99");
							   tablewei.ishead="wei"
							   tablewei._strname="tablewei";
							   tablewei.sqlstr=Tablexml[1].sql;
							  // tablewei.createTable(Number(Tablexml[1].@rows),Number(Tablexml[1].@cols),Number(Tablexml[1].@itemwidth),Number(Tablexml[1].@itemheight));
							   tablewei.UpDatecreateTable(Number(Tablexml[1].@rows),Number(Tablexml[1].@cols),Number(Tablexml[1].@itemwidth),Number(Tablexml[1].@itemheight),Tablexml[1] as XML);
							   tablewei.addEventListener(MouseEvent.CLICK,colclick);
							   this.addChild(tablewei);
							   reportmanage.addreportarr(tablewei as Object,"table",false);
							   arr.push(tablewei);

							   this.width=tablewei.width+70;
							   this.height=tablewei.y+tablewei.height+30;
				
					 
					 /*  DataReport.arrReport.push(rd);
					   var trxml:XMLList=new XMLList(rdxml[i].child("tr"));
					   var tdxml:XMLList=new XMLList(rdxml[i].child("td"));
					   
					   
					   for(var t:int=0;t<trxml.length();t++){
					   var tr1:tr=new tr();
					   tr1.height=trxml[t].@height;
					   tr1.rowid=trxml[t].@rowid;
					   tr1.tableid=trxml[t].@tableid;
					   DataReport.arrReport.push(tr1);
					   }
					   for(var t1:int=0;t1<tdxml.length();t1++){
					   var td1:td=new td();
					   td1.col=tdxml[t1].@col;
					   td1.colspan=tdxml[t1].@colspan;
					   td1.height=tdxml[t1].@height;
					   td1.row=tdxml[t1].@row;
					   td1.rowspan=tdxml[t1].@rowspan;
					   td1.tableid=tdxml[t1].@tableid;
					   td1.width=tdxml[t1].@width;
					   DataReport.arrReport.push(td1);
					   
					   var Cs1:Columns1=new Columns1();
					   Cs1.color=tdxml[t1].Columns.@color;
					   Cs1.columns=tdxml[t1].Columns.@columns;
					   Cs1.fontFamily=tdxml[t1].Columns.@fontFamily;
					   Cs1.fontSize=tdxml[t1].Columns.@fontSize;
					   Cs1.fontStyle=tdxml[t1].Columns.@fontStyle;
					   Cs1.fontWeight=tdxml[t1].Columns.@fontWeight;
					   Cs1.id=tdxml[t1].Columns.@id;
					   Cs1.statistyp=tdxml[t1].Columns.@statistyp;
					   Cs1.stringtyp=tdxml[t1].Columns.@stringtyp;
					   Cs1.text=tdxml[t1].Columns.@text;
					   Cs1.textDecoration=tdxml[t1].Columns.@textDecoration;
					   DataReport.arrReport.push(Cs1);*/
					 /*  }	
					   }   
					   //Table的数据解析
					   for(var tx1:int=0;tx1<Tablexml.length();tx1++){
					   var table:Table1=new Table1();
					   table.cols=Tablexml[tx1].@cols;
					   table.CSS=Tablexml*/
					   
					//   }
				  
			   }
			   DataReport.arrcontro=arr;
		}
		public function colclick(e:MouseEvent):void
		{
		 GlobalManager.getInstance().dispatchEvent(new Component(DataReport.Evnt_CLICK,e.currentTarget as UIComponent,""));	 
		}
		//添加时方法
		public function tableheng(event:EvTijiao):void{
			if(event.Formcode()=="table"){
			this.height =this.height+40;
			this.getChildByName("dyrecord").y=this.getChildByName("dyrecord").y+30;
			this.getChildByName("tablewei").y=this.getChildByName("tablewei").y+30;
			}else{
			 this.height =this.height+40;
			}
		}
		//s删除时的方法
		public function Dalheng(event:EvTijiao):void{
			if(event.Formcode()=="table"){
				this.height =this.height-40;
				this.getChildByName("dyrecord").y=this.getChildByName("dyrecord").y-30;
				this.getChildByName("tablewei").y=this.getChildByName("tablewei").y-30;
			}else{
				this.height =this.height-40;
			}
		}
		
	}
	
}