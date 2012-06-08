// ActionScript file
package DyformView{
	import mx.collections.ArrayCollection;
	

public class DateView{
	 [Bindable]// 总的步骤 sum
	 public static var sum:String="3";
	 [Bindable]// url的传递
	 public static var Urlname:String="";
     [Bindable]//parentdir 的数据传递
     public static var parentdir:String="";
     [Bindable]//formname的数据
     public static var formname:String="";
     [Bindable] // modletype判断是修改还是新建
     public static var modletype:Boolean=true;
     [Bindable]//parentdir1 路径
     public static var parentdir1:String="";
	 [Bindable] // 创建表单的sql语句
     public static var sqlstr:String="";
     [Bindable]//tablename 表单名字
     public static var tablename:String="";
     [Bindable]// 字段名字配置
     public static var fieldname:String="";
     [Bindable] // 表单的Formcode
     public static var Formcode:String="";
     [Bindable] // 表单的Formcode1
     public static var Formcode1:String="";
     [Bindable] // 表单字段
    public static var zidaun:ArrayCollection=new ArrayCollection();   
}

}
