 package  yingyongruk{
 
 public class DateApp{
 	 [Bindable]// 总的步骤 sum
	 public static var sum:String="3";
	 [Bindable]// url的传递
	 public static var URLname:String="";
     [Bindable]// repidID ID
     public static var repidID:String="";
     [Bindable]// repdimxplay  资源的中文名 
     public static var repdimxplay:String="";
     
     [Bindable] // modletype判断是修改还是新建
     public static var modeltype:Boolean=true;
     [Bindable]// 判断选择是页框还协调应用
     public static var selectmodel:String="";
     
      [Bindable]//页框的选择
     public static var yekuangstr:String="";
     [Bindable]//协同应用
     public static var xieton:String="";
 
 
 }
 
 }