// ActionScript file
package ActionForm{
  public class DyData{
  	 [Bindable]// 创建表单
  	 public static var sum:String="8";
  	 [Bindable]//parentdir 的数据传递
     public static var Urlname:String="";
     [Bindable]//formname的数据
     public static var formname:String="";
     [Bindable]//msinfo 描述的数据
     public static var msinfo:String="";
     [Bindable]//bussData 维度数据
     public static var bussData:String="";
     [Bindable]//busstype 维度类型
     public static var busstype:String="";
     [Bindable]// showCss 样式选择
     public static var showCss:String="";
     [Bindable]//配置表单是否创建还是修改
     public static var dytype:Boolean=false;
     [Bindable]//time 时间类型
     public static var time:String="";
     [Bindable]// subform 子表单的数据
     public static var subform:String="";
     
     [Bindable]//判断是修改还新建
     public static var modletype:Boolean=true;
     [Bindable]//config的xml文件传递
     public static var  configxml:XML;
     //选择表单的tree 数据
     [Bindable]
     public static var tree:XML;  
     [Bindable]//模式选择
     public static var modelname:String="";
  }
}