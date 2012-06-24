package com.resistdesign
{
	import mx.collections.ArrayCollection;
	import mx.core.UIComponent;

	/**
	 * @author hp
	 */
	public class DataReport
	{   //自定义发送事件的类型
		public static var Evnt_select:String="shuanjixuanzhe";
		public static var Evnt_ziduan:String="ziduanxuanzhe";
		public static var Evnt_select1:String="shuanjixuanzhe1";
		public static var Evnt_form:String="FormSelect";
		public static var Evnt_drive:String="DriveSelect";
		public static var Evnt_Data:String="datareturn";
		public static var Evnt_CLICK:String="CLICK";
		public static var Evnt_addCLICK:String="addCLICK";
		public static var Evnt_Addclickwei:String="Addclickwei";
		
		public static var naturelname:String="";
		public static var Fcod:String="";
		public static var type:String="";
		[Bindable]//驱动的地址
		public static var urlString:String="";
		[Bindable]//驱动名称
		public static var username:String="";
		[Bindable]//驱动密码
		public static var pwd:String="";
		[Bindable]//驱动
		public static var driver:String="";
		[Bindable]//sql
		public static var Sqlstr:String="";
		[Bindable]//focd
		public static var focdstr:String="";
		[Bindable]//UIComponent
		public static var comp:UIComponent;
		[Bindable]//驱动显示
		public static var Drivar:String="";
		[Bindable]//控件arr
		public static var arrcontro:Array;
        [Bindable]//index
		public static var index:String="";
		[Bindable]//index
		public static var zhongshu:int=0;
		[Bindable]//type
		public static var model:String="";
		[Bindable]//arrxml数据
		public static var tree:XML;
		[Bindable]//控件的xml数据解析
		public static var controlxml:XML;	
		[Bindable]//tr与td的xml
		public static var trortd:XML;
		[Bindable]//td的xml
		public static var  tdxml:XML;
		[Bindable]//arrReport
		public static var arrReport:Array=new Array();
		[Bindable]//URLname
		public static var URLname:String="";
		[Bindable]//repidID的定义
		public static var repidID:String="";
		[Bindable]//repdimxpla的定义
		public static var repdimxpla:String="";
		[Bindable]//判断是新建还是修改
		public static var modeltype:Boolean=true;
	}	
}