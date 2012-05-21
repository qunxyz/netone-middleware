// ActionScript file
package  BrickTake{
	import mx.core.IUIComponent;
	
    public class BTData{
    	/**
    	 *	 砖取制定  数据的传送的静态方法
    	 * 	 xuwei(2012-2-15)
    	 * 
    	 * */
      [Bindable]//资源ID 
     public  static var repidID:String="";
      [Bindable]//资源名称
      public static var repdimxpla:String="";
      [Bindable]//URl的值
      public static var URLname:String="";
      [Bindable]//砖取模式
      public static var BTModel:String="";
      [Bindable]//在多层选择图表上的ID
      public static var ChartID:String="";
       [Bindable]//在多层选择值上的ID
      public static var ChartValueID:IUIComponent=null;
       [Bindable]//在二层选择图表上的ID
      public static var TwoID:String="";
      [Bindable]//数组多层保存了图表的数据
      public static var ArrChart:Array=null;
       [Bindable]//数组配置数据
      public static var ArrCinfigt:Array=new Array();
       [Bindable]//数组保存了图表的数据
      public static var Ismultilayer:Boolean;
       [Bindable]//两层模式的父图表
      public static var TwoChartparent:String=""; 
      [Bindable]//两层模式的子图表
      public static var TwoChartsub:String="";
       [Bindable]//砖取的naturalname
      public static var BTnaturalname:String="";
       [Bindable]//砖取的Twonaturalname
      public static var Twonaturalname:String="";
      [Bindable]//判断是不是 修改
      public static var modeltype:Boolean=true;
      [Bindable]//xml 的数据传递
      public static var controlxml:XML;
   }
}