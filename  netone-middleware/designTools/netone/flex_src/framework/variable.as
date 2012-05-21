 package framework{
 	import mx.collections.ArrayCollection;
 
 public class variable{
 
	    [Bindable] //新建应用
	   public static var xjyy:String=null ;
       [Bindable] //获取Querywork返回的值
	   public static var Dataarr:ArrayCollection=new ArrayCollection();
	   [Bindable]//流程表单
	   public static var liucheng:String;
	   
	   [Bindable]//资源路径
	   public static var zhiyuan:String=null;
	   [Bindable]
	   public static var zhiyuanname:String=null;
	   
	    [Bindable]//代办任务资源路径
	   public static var daiban:String=null;
	   [Bindable]
	   public static var daibanname:String=null;
	   [Bindable]//返回的類型
	   public static var Objecttype:String=null;
	     
	   
	   [Bindable] //
	   public static var treearr:String;
	   [Bindable]//表名
	   public static var rname:String=null;
	   [Bindable]
	   public static var rcode:String=null;
	   [Bindable]//流程名称
	   public static var  liuchenglujing:String=null;
	   
	   
	   [Bindable]//formname
	   public static var formendtitle:String="";
	    [Bindable]//formtitle
	   public static var formtitle:String="";
	      [Bindable]//worklistsize
	   public static var worklistsize:String="";
	   
	    [Bindable]//worklistsize
	   public static var huodongming:Boolean=false;
	   [Bindable]//tijiaoshijian
	   public static var tijiaoshijian:Boolean=false;
	   [Bindable]//tijiaozhe
	   public static var tijiaozhe:Boolean=false;
	   
	    [Bindable]//urlname
	   public static var  URLname:String="";
	    [Bindable]//业务框架ID
	   public static var  repidID:String="";
	    [Bindable]//业务框架的名称
	   public static var  repdimxpla:String="";
	     [Bindable]//工作流的ID
		 public static var folwID:String="";
		 [Bindable]//表单fcode
		 public static var fcode:String="";
		    [Bindable] //框架的记录id
	   public static var _idcreated1:String="";
	   [Bindable]//标示是否是修改
	   public static var Updatakuabgjian:Boolean=true;
	    	   [Bindable]  //变量传表单数据
	   public static var _formchoose:String="";
	     [Bindable]//变量传流程数据
	   public static var _woekchoose:String="";
	   [Bindable]//代办提示显示配置
	  public static var   worklistDefaultColumn:String="";
	  [Bindable]//选择目录的模式
	  public static var modelname:String="";
  }
 }
