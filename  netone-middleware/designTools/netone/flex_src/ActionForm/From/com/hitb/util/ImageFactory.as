package ActionForm.From.com.hitb.util
{
	import ActionForm.From.CustomControl.Lablezhuzhijiguoduo;
	
	public class ImageFactory
	{   
		[Embed(source="ActionForm/From/assets/icon/LableText.png")]
		public  static var ICON_LABLETEXT:Class;
		
		[Embed(source="ActionForm/From/assets/icon/TextInput.png")]
		public  static var ICON_TEXTINPUT:Class;
		
		[Embed(source="ActionForm/From/assets/icon/Button.png")]
		public  static var ICON_BUTTON:Class;
		
		[Embed(source="ActionForm/From/assets/icon/ComboBox.png")]
		public  static var ICON_LABLECOMBOBOX:Class;
		
		[Embed(source="ActionForm/From/assets/icon/CheckBox.png")]
		public  static var ICON_LABLECHECKBOX:Class;
		
		[Embed(source="ActionForm/From/assets/icon/Image.png")]
		public  static var ICON_IMAGE:Class;
		
		[Embed(source="ActionForm/From/assets/icon/DateChooser.png")]
		public  static var ICON_DATECHOOSER:Class;
		
		[Embed(source="ActionForm/From/assets/icon/DateField.png")]
		public  static var ICON_DATEFIELD:Class;
		
		[Embed(source="ActionForm/From/assets/icon/Tree.png")]
		public  static var ICON_TREE:Class;
		
		[Embed(source="ActionForm/From/assets/icon/DataGrid.png")]
		public  static var ICON_DATAGRID:Class;
		
		[Embed(source="ActionForm/From/assets/icon/Canvas.png")]
		public  static var ICON_CANVAS:Class;
		
		[Embed(source="ActionForm/From/assets/icon/LableComboBox.png")]
		public  static var ICON_LableComboBox:Class;
		
		[Embed(source="ActionForm/From/assets/icon/LableTimeField.png")]
		public static var ICON_LABLETIMEFIELD:Class;
		
		[Embed(source="ActionForm/From/assets/icon/LableTime.png")]
		public static var ICON_LABLETIME:Class;
			
		[Embed(source="ActionForm/From/assets/icon/LableTextInput.png")]
		public static var ICON_LABLETEXTINPUT:Class; 
		
		[Embed(source="ActionForm/From/assets/icon/LableTextInputIP.png")]
		public static var ICON_LABLETEXTINPUTIP:Class; 
		
		
		[Embed(source="ActionForm/From/assets/icon/LableEmail.png")]
		public static var ICON_LABLEEMALIL:Class; 
		
		[Embed(source="ActionForm/From/assets/icon/LableTextLarge.png")]
		public static var ICON_LABLETEXTLARGE:Class; 
		
		
		[Embed(source="ActionForm/From/assets/icon/LableTextButton.png")]
		public static var ICON_LableTextButton:Class; 
		
		[Embed(source="ActionForm/From/assets/icon/leLableButtonMultip.png")]
		public static var ICON_LEOLABLEBUTTONMULTIP:Class; 
		
		[Embed(source="ActionForm/From/assets/icon/LableTextpersonnel.png")]
		public static var ICON_LABLETEXTPERSONNEL:Class; 
		
		[Embed(source="ActionForm/From/assets/icon/LableTextrole.png")]
		public static var ICON_LABLETEXTROLE:Class; 
		
		[Embed(source="ActionForm/From/assets/icon/LableTextButtonzhuzhi.png")]
		public static var ICON_LABLETEXTBUTTONZHUZHI:Class; 
		
		[Embed(source="ActionForm/From/assets/icon/LableTextButtonzhuzhiduo.png")]
		public static var 	ICON_LABLETEXTBUTTONZHUZHIDUO:Class; 
	
	    [Embed(source="ActionForm/From/assets/icon/LablePORTAL.png")]
		public static var ICON_LABLEPORTAL:Class; 
		
		[Embed(source="ActionForm/From/assets/icon/Colourfulea.png")]
		public static var ICON_LABLECOLOURFULEA:Class; 
		[Embed(source="ActionForm/From/assets/icon/LABLEZHUZHIJIGOU.png")]
		public static var  ICON_LABLEZHUZHIJIGOU:Class;
		[Embed(source="ActionForm/From/assets/icon/Lablezhuzhijiguoduo.png")]
		public static var ICON_LABLEZHUZHIJIGUODUO:Class;
		
		[Embed(source="ActionForm/From/assets/icon/LableMulCheckBox.png")]
		public static var ICON_LableMulCheckBox:Class;
		
		[Embed(source="ActionForm/From/assets/icon/LableURL.png")]
		public static var ICON_LableURL:Class;
		
		[Embed(source="ActionForm/From/assets/icon/Button.png")]
		public static var ICON_Button:Class;
		
		[Embed(source="ActionForm/From/assets/icon/Image.png")]
		public static var ICON_Image:Class;
		[Embed(source="ActionForm/From/assets/icon/LableTextButton.png")]
		public static var ICON_RadioGroup:Class;
		
		[Embed(source="ActionForm/From/assets/icon/Text.png")]
		public static var ICON_LableFile:Class;
		
		[Embed(source="ActionForm/From/assets/icon/leLableButtonMultip.png")]
		public static var ICON_TAndF:Class;
		
		public function ImageFactory()
		{
		}
         
        public static function  getComponentImage(className:String):*{
            if(className=="ActionForm.From.CustomControl.LableText"){
        		return ICON_LABLETEXT;
        	}else if(className=="ActionForm.From.CustomControl.LableComboBox"){
        		return ICON_LableComboBox;
        	}else if(className=="ActionForm.From.CustomControl.LableCheckBox"){
        		return ICON_LABLECHECKBOX;
        	}else  if(className=="ActionForm.From.CustomControl.LableDateField"){
        		return ICON_DATEFIELD;
        	}else if(className=="ActionForm.From.CustomControl.LableTimeField"){
        	   return ICON_LABLETIMEFIELD;
        	}else if(className=="ActionForm.From.CustomControl.LableTime"){
        	   return ICON_LABLETIME;
        	}else if(className=="ActionForm.From.CustomControl.LableEmail"){
        	  return ICON_LABLEEMALIL;
        	}else if(className =="ActionForm.From.CustomControl.LableTextInput"){
        	 return ICON_LABLETEXTINPUT;
        	}else if(className=="ActionForm.From.CustomControl.LableComboBoxKV"){
        	 return ICON_LABLECOMBOBOX;
        	}else if(className=="ActionForm.From.CustomControl.LableTextInputIP"){
        	 return ICON_LABLETEXTINPUTIP;
        	}else if(className=="ActionForm.From.CustomControl.LableTextLarge"){
        	 return ICON_LABLETEXTLARGE;
        	}else if(className=="ActionForm.From.CustomControl.LableTextButton"){
        	 return ICON_LableTextButton;
        	}else if(className=="ActionForm.From.CustomControl.leLableButtonMultip"){
        	 return ICON_LEOLABLEBUTTONMULTIP;
        	}else if(className=="ActionForm.From.CustomControl.LableTextpersonnel"){
        	 return ICON_LABLETEXTPERSONNEL;
        	}else if(className=="ActionForm.From.CustomControl.LableTextrole"){
        	return ICON_LABLETEXTROLE;
        	}else if(className=="ActionForm.From.CustomControl.LableTextButtonzhuzhi"){
        	return ICON_LABLETEXTBUTTONZHUZHI;
        	}else if(className=="ActionForm.From.CustomControl.LableTextButtonzhuzhiduo"){
        	 return ICON_LABLETEXTBUTTONZHUZHIDUO;
        	}else if(className=="ActionForm.From.CustomControl.LablePORTAL"){
        	 return ICON_LABLEPORTAL;
        	}else if(className=="ActionForm.From.CustomControl.LableColourfulea"){
        	 return ICON_LABLECOLOURFULEA;
        	}else if(className=="ActionForm.From.CustomControl.Lablezhuzhijigou")
             return ICON_LABLEZHUZHIJIGOU;
             else if(className=="ActionForm.From.CustomControl.Lablezhuzhijiguoduo")
             return ICON_LABLEZHUZHIJIGUODUO;
        	  else if(className=="ActionForm.From.CustomControl.LableMulCheckBox")
             return ICON_LableMulCheckBox;
              else if(className=="ActionForm.From.CustomControl.LableURL")
              return ICON_LableURL;
              else if(className=="ActionForm.From.CustomControl.LableButton"){
              return ICON_Button;
              } else if(className=="ActionForm.From.CustomControl.LableImage"){
              return ICON_Image;
              }else if(className=="ActionForm.From.CustomControl.LableRadioGroup"){
              return ICON_RadioGroup;
              }else if(className=="ActionForm.From.CustomControl.LableFile"){
               return ICON_LableFile;
              }else if(className=="ActionForm.From.CustomControl.LableTAndF"){
               return ICON_TAndF;
              }
             else {
        		return null;
        	}
        }
	}
}