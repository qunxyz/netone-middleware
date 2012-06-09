// ActionScript file
package app.ssoconfig
{
	/**
	 * @author hp
	 */
	public class DateSSO
	{
		[Bindable]
		public static var sum:String="3";
		[Bindable]//全部就步
		public static var index:String="";
		[Bindable]
		public static var repidID:String="";
		[Bindable]
		public static var repdimxpla:String="";
		[Bindable]
		public static var URLname:String="";
		[Bindable]
		public static var ChartID:String="";
 
		[Bindable]//判断跳转下一个是什么界面
		public static var verdict:Boolean=true;
		
		[Bindable]//首页模式
		public static  var pagemodel:String="";
		[Bindable]//判断是修改还是新建
		public static var modletype:Boolean=true;
		[Bindable]//newtextArea0
		public static var newtextArea0:String="";
		[Bindable]//newtextArea1
		public static var newtextArea1:String="";
		[Bindable]//newtextArea2
		public static var newtextArea2:String="";
		[Bindable]//newtextArea3
		public static var newtextArea3:String="";
		[Bindable]//xml数据配置
		public static var Ssoxml:XML;
		
	}	
}