 package ActionForm{
 	import mx.collections.ArrayCollection;
 	import mx.core.UIComponent;
 	
 	
 public class vivid{
 	   [Bindable]
       public static var sname:String="text";
       [Bindable]
       public static var model:String="1";
       [Bindable]
       public  static var  sarr:ArrayCollection=null;
       [Bindable]
       public static var backstring:Array=null;
       
       [Bindable]
       public static var personnel:String="admin";
       
       [Bindable]
       public static var role:String="管理员";
       [Bindable]
       public static var SubFormName:String="";
       [Bindable]
       public static var fromcode:String=null;
        [Bindable]
        public static var savexmlstring:XML=null; 
         [Bindable]
        public static var savexmlandroid:String=null;
         [Bindable]//保存 排序 好的控件
        public static var AddCount:Array;  
         [Bindable]
        public static var savexmlarr:Array; 
         [Bindable]
        public static var savexmlcomp:Array; 
         [Bindable] //传递android的id
        public static var savexmlid:Array; 
         [Bindable] //传递android的控件类型
        public static var typeid:Array; 
        [Bindable] //设计表单名称
        public static var savename:String=null; 
       //获取表单的ID
       [Bindable]
        public static var formcode:String;
        [Bindable]
        public static var idform:String;
        
        
            [Bindable]
        public static var csscode:Array=null;
       // public static var formname:String=""+formcode+".xml";
       // 修改表单所获取的ID
        [Bindable]
        public static var treestring:String=null;
        public static var formid:String=null;
        
        [Bindable]
        public static var updateform:Boolean=false;

        
        [Bindable]
        public static var comp:UIComponent=null;
        [Bindable]
        public static var bussData:String; 
          [Bindable]//id编号
        public static var ID:int=3; 
        [Bindable] 
        public static var   busstype:Array; 
           [Bindable] //表单格式
        public static var formlayout:Boolean=false;
        [Bindable]//项目路径
        public static var Strfile:String=null;
        [Bindable]//生成文件的路径
        public static var filelist:String=null;
        [Bindable]
        public static var Pathfile:Boolean=true;
        [Bindable]
        public static var updatecode:String=null;
              [Bindable]
        public static var WDatastring:String=null; //维度类型
        [Bindable]
        public static var xdata:String=null; //X数据
        [Bindable]
        public static var timestring:String=null;//X时间
        [Bindable]
        public static var zhibiaostring:String=null;//指标值
        [Bindable]
         public static var Tablename:String=null;//表单名
         [Bindable]
        public static var timename:String=null;//表单名
          [Bindable]
         public static var backarr:ArrayCollection=null;
         [Bindable]//kv列表上p备选直的数据
         public static var kvlisting:String="";
          [Bindable]//扩展属性
         public static var Expand:String="";
         [Bindable]//面板上的组件
         public static var Control:Array=new Array();
         [Bindable]//配置数据查询字段的数组
         public static var configarr:ArrayCollection=new ArrayCollection();
          [Bindable]//简单数据 配置
         public static var bfcf:ArrayCollection=new ArrayCollection();
          [Bindable]//高级数据 配置
         public static var dncf:ArrayCollection=new ArrayCollection();
           [Bindable]//统计数据 配置
         public static var tongji:ArrayCollection=new ArrayCollection();
         [Bindable]//表单的流水号
         public static var pid:String="";
         
         [Bindable]//判断是修改还是新建
         public static var modeltype:Boolean=true;
         [Bindable]//修改的时候返回的xml 
         public static var updatexml:XML=null;
         [Bindable]//简称查询是否启用
         public static var Queryjd:Boolean=true;
         [Bindable]//高级查询是否启用
         public static var Querygj:Boolean=true;
         [Bindable]//字段列表是否启用
         public static var FullList:Boolean=true;  
         [Bindable]//统计是否启用
         public static var isTong:Boolean=true;
         [Bindable]// 控制添加字段不会出现多个
         public static var kongzhi:Boolean=true;   
 	}
 }
 