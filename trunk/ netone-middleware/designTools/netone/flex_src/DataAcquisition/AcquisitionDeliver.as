package DataAcquisition{

 public class  AcquisitionDeliver{
 	[Bindable]//xml的数据存储
 	public static var xml:String="";
 	[Bindable]
 	public static var Alias:Boolean=false;
 	[Bindable]
 	public static var Alias1:Boolean=false;
 	[Bindable]//url 上的存储资源
 	public static var URLname:String="";
 	[Bindable]
 	public static  var ss1:String="";
 	[Bindable]
 	public static var ss2:String=""; 
 	[Bindable]
 	public static var ss3:String="";
 	[Bindable]
 	public static var ss4:String=""; 
 	[Bindable]
 	public static  var s1:String="";
 	[Bindable]
 	public static var s2:String="";
 	[Bindable]
 	public static var s3:String="";
 	[Bindable]
 	public static var s4:String="";
 	[Bindable]//表单 code
 	public static var  formcode:String="";
 	[Bindable]//选择的目标表
 	public static var tablename:String="";
 	[Bindable]//选择目标表的中文名字 
 	public static var tablezhongname:String="";
 	[Bindable]//Sql的数据
 	public static var sqltext:String="";
 	[Bindable]//repidID资源
 	public static var repidID:String="";
 	[Bindable]//repdimxplay资源
 	public static var repdimxplay:String="";
 	[Bindable]//startScript资源
 	public static var startScript:String="";
 	[Bindable]//endScript资源
 	public static var endScript:String="";
 	[Bindable]//必须字段的XML
 	public static var xmlData:XML;
 	
 	[Bindable]//判断是否是修改
 	public static var modeltype:Boolean=true;
 	[Bindable]//xml的数据传递
 	public static var controlxml:XML;
 	[Bindable]//减去必须的字段现在的配置的字段
 	public static var pzziduan:Array=new Array();
 }

}