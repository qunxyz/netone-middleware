// ActionScript file
package img
{
import flash.geom.Rectangle;  
import flash.text.TextFormat;     
import mx.graphics.RoundedRectangle;  
import mx.preloaders.DownloadProgressBar;     
public class MyDownProBar1 extends DownloadProgressBar  
{  
   [Embed(source="img/9bj.png")]   //进度条的背景图片  
   [Bindable]  
   private var bcgImageClass: Class;      
   public function MyDownProBar1()  
   {  
      super();            
      downloadingLabel="下载中...";    //默认为 Loading  
      initializingLabel="初始化中..."   //默认为 Initializing  
      showLabel=true;       //是否显示标签，默认为true  
      showPercentage=true;  //是否显示下载百分比，默认为true  
   }      
   override public function get backgroundImage():Object{   
      return bcgImageClass;     //背景图片  
   }  
   override public function get backgroundSize():String{  
      return "100%"; //背景的尺寸，背景默认的尺寸是充满整个舞台的  
   }  
   override public function get backgroundAlpha():Number{         
      return 0.7;   //控件相对于背景的透明度  
   }  
   //override public function get backgroundColor():uint  
   //{  
   // return 0x00FF00;      //背景颜色，设置背景颜色或图片只能选其一  
   //}  
   //进度条边沿框的矩形区域（使进度条看起来是凹进去的）   
   override protected function get barFrameRect():RoundedRectangle{  
      return new RoundedRectangle(14, 40, 154, 10);   
   }  
   //进度条的矩形区域  
   override  protected function get barRect():RoundedRectangle  {  
      return new RoundedRectangle(14, 39, 154, 12, 0);        
}  
   //外围边框的矩形区域（使进度条看起来在一个panel里）  
   override protected function get borderRect():RoundedRectangle{  
      return new RoundedRectangle(0, 0, 182, 60, 10);  
   }  
   //label 的显示格式  
   override protected function get labelFormat():TextFormat{  
      var tf:TextFormat = new TextFormat();  
      tf.color = 0x333333;  
      tf.font = "Verdana";  
      tf.size = 12;  
      return tf;  
   }  
   //显示label的矩形区域  
   override protected function get labelRect():Rectangle{  
      return new Rectangle(14, 17, 100, 18);  
   }          
   //百分比的文字格式  
   override protected function get percentFormat():TextFormat{  
      var tf:TextFormat = new TextFormat();  
      tf.align = "right";  
      tf.color  = 0x000000;  
      tf.font = "Verdana";  
      tf.size = 12;  
      return tf;  
   }  
   //显示百分比的矩形区域  
   override protected function get percentRect():Rectangle{  
      return new Rectangle(108, 4, 34, 16);  
   }  
} 
}