package ActionForm.From.com.hitb.component
{
	
	
	import ActionForm.From.CustomControl.*;
	
	import mx.controls.*;
	import mx.core.UIComponent;
	public class ComponentFactory
	{   
		private static var tableDP:Array=new Array(
		   {data1:"column1.1",data2:"column1.2",data3:"column1.3"},
		   {data1:"column2.1",data2:"column2.2",data3:"column2.3"},
		   {data1:"column3.1",data2:"column3.2",data3:"column3.3"}
		);
		private static var treeDP:XML=<Root> 
		                                <E label='column1'>
		                                   <E label='column1.1'>
		                                       <E label='column1.1.1'></E>
		                                       <E label='column1.1.2'></E>
		                                   </E>
		                                   <E label='column1.2'></E>
		                                 </E>
		                                  <E label='column2'></E>
		                                   <E label='column3'></E>
		                             </Root>;
		                     
		public function ComponentFactory()
		{
		}
        
        public static function getComponent(identify:String):UIComponent{
        	var comp:UIComponent;
        	if(identify=="ActionForm.From.CustomControl.LableText"){
              comp=new LableText();
        	}
        	else
        	if(identify=="ActionForm.From.CustomControl.LableComboBox"){
              comp=new LableComboBox();        
        	}
        	else
        	if(identify=="ActionForm.From.CustomControl.LableColourfulea"){
        	 comp=new LableColourfulea();
        	}else
        	if(identify=="ActionForm.From.CustomControl.LableCheckBox"){ 
        		comp=new LableCheckBox();
        	}
        	else if(identify=="ActionForm.From.CustomControlLableEmail"){
        	  comp=new LableEmail();
        	} else if(identify=="ActionForm.From.CustomControl.LableTextInput"){
        	 comp =new LableTextInput();
        	}else if(identify=="ActionForm.From.CustomControl.LableTextInputIP"){
        	 comp=new LableTextInputIP();
        	} else if(identify=="ActionForm.From.CustomControl.leLableButtonMultip"){
        	 comp=new leLableButtonMultip();
        	}else if(identify=="ActionForm.From.CustomControl.LableTextpersonnel"){
        	 comp=new LableTextpersonnel();
        	}else if(identify=="ActionForm.From.CustomControl.LableTextrole"){
        	 comp=new LableTextrole();
        	}else if(identify=="ActionForm.From.CustomControl.LableEmail"){
        		comp=new  LableEmail();
        	}else
        	if(identify=="ActionForm.From.CustomControl.LableDateField"){
        		comp=new  LableDateField();
        	}else if(identify=="ActionForm.From.CustomControl.LableTimeField"){
        	 comp=new LableTimeField();
        	} else if(identify=="ActionForm.From.CustomControl.LableTime"){
        	  comp=new LableTime();
        	} else if(identify=="ActionForm.From.CustomControl.LableComboBoxKV"){
        	 comp=new LableComboBoxKV();
        	}else if(identify=="ActionForm.From.CustomControl.LableTextLarge"){
        	 comp=new LableTextLarge();
        	}else if(identify=="ActionForm.From.CustomControl.LableTextButton"){
        	 comp=new LableTextButton();
        	}else if(identify=="ActionForm.From.CustomControl.LableTextButtonzhuzhi"){
        	comp=new LableTextButtonzhuzhi();
        	}else if(identify=="ActionForm.From.CustomControl.LableTextButtonzhuzhiduo"){
        	comp=new LableTextButtonzhuzhiduo();
        	}else if(identify=="ActionForm.From.CustomControl.LablePORTAL"){
        	 comp=new LablePORTAL();
        	}
        	 else if(identify=="ActionForm.From.CustomControl.Lablezhuzhijigou")
        	{
        		comp=new Lablezhuzhijigou();
        	}
        	else if(identify=="ActionForm.From.CustomControl.Lablezhuzhijiguoduo")
           {
           	   comp=new Lablezhuzhijiguoduo();
           }
           else if(identify=="ActionForm.From.CustomControl.LableMulCheckBox")
           {
           	   comp=new LableMulCheckBox();
           }
           else if(identify=="ActionForm.From.CustomControl.LableURL")
           {     
           	   comp=new LableURL();
           }else if(identify=="ActionForm.From.CustomControl.LableButton")
           {    
           	   comp=new LableButton();
           }else if(identify=="ActionForm.From.CustomControl.LableImage"){
              comp=new LableImage();
           }else if(identify=="ActionForm.From.CustomControl.LableRadioGroup"){
             comp=new LableRadioGroup();
           }else if(identify=="ActionForm.From.CustomControl.LableFile"){
             comp=new LableFile();
           }else if(identify=="ActionForm.From.CustomControl.LableTAndF"){
             comp=new  LableTAndF();
           }
        	return comp;
        }
	}
}