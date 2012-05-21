// ActionScript file
package BIAnalysis{
    public class BIData{
    [Bindable]//展示的类型
    public static var tubiaotype:String="";
    [Bindable]//formcode的
   public static var formcode:String="";
	[Bindable]//切片维度
   public static var qiepianweidu:String="";
    [Bindable]//切片维度值
   public static var qiepianweiduzhi:String="";
    [Bindable]//扩张条件
   public static var kuozhantiaojian:String="";
    [Bindable]//展开x轴的维度
   public static var zhankaiX:String="";
    [Bindable]//指标选择 
   public static var xuanzhezhibiao:String="";
    [Bindable]//预测开始时间
    public static var predictionbegan:String="";
    [Bindable]//预测结束时间
    public static var predictionend:String="";
    [Bindable]//预测类型
    public static var Predictiontype:String="";
    
    [Bindable]//repidID资源的传递
    public static var repidID:String="";
     [Bindable]//repdimxplay资源的传递
    public static var repdimxplay:String="";
    [Bindable]//URLname
    public static var URLname:String="";
     [Bindable]//XMLstr
    public static var XMLstr:String="";
    
    [Bindable]//判断是新建还是修改
    public static var modeltype:Boolean=true;
    [Bindable]//修改状态时的xml数据传输
    public static var controlxml:XML;
    [Bindable]//表单的中文名字
    public static var namestr:String;
    [Bindable]//标志是否判断
    public static var index:int=0;
   }
}