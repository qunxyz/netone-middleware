package components
{
	/**
	 * 报表查询  数据传输的方法
	 * xuwei（2012-3-22）
	 * 
	 * 
	 * 
	 */
	public class DataRP
	{
		public function DataRP()
		{
			
		}
		[Bindable] //报表查询ID
		public static var repidID:String="";
		[Bindable]//报表查询的中文名字
		public static var repdimxpla:String="";
		[Bindable]//在url 上取消资源名称
		public static var URLname:String="";
		
		[Bindable]//配置都是界面上是新建还是修改
		public static var modeltype:Boolean=true;
		
		[Bindable]//修改时候在数据库中返回的xml数据
		public static var controlxml:XML;
 
	}	
}