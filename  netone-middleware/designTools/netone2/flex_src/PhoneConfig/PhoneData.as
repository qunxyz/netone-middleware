// ActionScript file
package  PhoneConfig{
	import mx.core.IUIComponent;
	
    public class PhoneData{
	    	/**
	    	 *	使用端的配置 数据的传送的静态方法
	    	 * 	 xuwei(2012-2-20)
	    	 * 
	    	 * */
      [Bindable]//资源ID 
     public  static var repidID:String="";
      [Bindable]//资源名称
      public static var repdimxpla:String="";
      [Bindable]//URl的值
      public static var URLname:String="";
      [Bindable]//组件的的ID
      public static var  ChartID:String="";
      [Bindable]//配置模型
      public static var model:String="";
      [Bindable]//九宫图的行键值
      public static var rowkey:String="";
      [Bindable]//九宫图的列键值
      public static var crowkey:String="";
      [Bindable]//选择页的时候
      public static var page:String="";
       [Bindable]//选择页的 id
      public static var pagID:String="";

       [Bindable]//配置使用端的选择
      public static var Clientend:String="";
   }
}