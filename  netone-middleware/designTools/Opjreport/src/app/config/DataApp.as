package  app.config
{
	import mx.collections.ArrayCollection;

	/**
	 * @author hp
	 */
	public class DataApp
	{
		public function DataApp()
		{
			
		}
		[Bindable]
		public static var sum:String="4";
		[Bindable]//全部就步
		public static var index:String="";
		[Bidnable]// 当前是第几步
		 public static var identifying:String="";
		[Bindable]
		public static var repidID:String="";
		[Bindable]
		public static var repdimxpla:String="";
		[Bindable]
		public static var URLname:String="";
		[Bindable]
		public static var ChartID:String="";
	    [Bindable]//行数据
		public static var row:String;
		[Bindable]//列数据
		public static var crow:String;
		[Bindable]//是否配置查询功能
		public static var radio1:String;
	/*	[Bindable]//判断跳转下一个是什么界面
		public static var verdict:Boolean=true;*/
		
		[Bindable]//首页模式
		public static  var pagemodel:String="";
		
		[Bindable]//判断是什么模式
		public  static  var type:String="";
		[Bindable]//保存的xml数据
		public  static var url:String="";
		[Bindable]//是否启用
		public static var active:String="1";
		[Bindable]//上传图片服务地址
		public static var WEBSER_WebSerivce:String="";
		[Bindable]//用户名
		public static  var userid:String="adminx";
		
		
	}	
}