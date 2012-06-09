package components.reportset
{
	import com.resistdesign.reportsetcolr.replists;
	
	import components.reportset.colstatic.pubset;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.core.IUIComponent;
	import mx.core.UIComponent;
	import mx.utils.NameUtil;

	public class repsetmanage
	{
		private var _selectobj:Object=null;
		private var _setarr:ArrayCollection=new ArrayCollection();
		private var _pubqua:pubset=null;
		public function repsetmanage()
		{
		}
		
		public function get pubqua():pubset
		{
			return _pubqua;
		}

		public function set pubqua(value:pubset):void
		{
			_pubqua = value;
		}

		[Bindable]
		public function get setarr():ArrayCollection
		{
			return _setarr;
		}

		public function set setarr(value:ArrayCollection):void
		{
			_setarr = value;
		}

		public function get selectobj():Object
		{
			return _selectobj;
		}

		public function set selectobj(value:Object):void
		{
			_selectobj = value;
		}
		
		public function setarrs(idx:int,col:Object,coltyp:String,relat:String="And"):void
		{
			setarr.addItem({idx:idx,col:col,coltyp:coltyp,relat:relat});
		}
		public function getclrxml():XML
		{
			var xmllis:XML=<clrlist></clrlist>;
			for (var i:int = 0; i < setarr.length; i++) 
			{
				xmllis.appendChild(xmltest(setarr[i].coltyp,setarr[i].col,setarr[i].relat));
			}
			return xmllis;
		}
		public function xmltest(typ:String,obj:Object,relat:String):XML
		{
			var xmllis:XML=new XML();
			switch(typ)
			{
			
				case "replistss":
				{
					xmllis=<repclr></repclr>
					xmllis.@id=obj.id;
					xmllis.@x=obj.x;
					xmllis.@y=obj.y;	
					xmllis.@width=obj.width;
					xmllis.@height=obj.height;
					var item:Object=setrow_col(obj as IUIComponent)	;
					xmllis.@col=item["col"];
					xmllis.@row=item["row"];
					xmllis.@label=obj.text;
					xmllis.@relat=relat;
					xmllis.@strtyp="1"
					switch(obj.rstype)
					{
						case "replists":
						{
							xmllis.@type="14";
							break;
						}
						case "replistss":
						{
							xmllis.@type="15";
							break;
						}
						case "reppers":
						{
							xmllis.@type="12";
							break;
						}
						case "repperss":
						{
							xmllis.@type="13";
							break;
						}
						case "reprs":
						{
							xmllis.@type="16";
							break;
						}
						case "reprss":
						{
							xmllis.@type="17";
							break;
						}
					}

					xmllis.@data=obj.binrs;
					break;
				}
				
		
					
				case "repnum":
				{
					xmllis=<repclr></repclr>
					xmllis.@id=obj.id;
					xmllis.@x=obj.x;
					xmllis.@y=obj.y;	
					xmllis.@width=obj.width;
					xmllis.@height=obj.height;
					var item:Object=setrow_col(obj as IUIComponent)	;
					xmllis.@col=item["col"];
					xmllis.@row=item["row"];
					xmllis.@label=obj.text;
					xmllis.@relat=relat;
					xmllis.@type="05";
					xmllis.@strtyp="0"
					break;
				}
				case "repnumc":
				{
					xmllis=<repclr></repclr>
					xmllis.@id=obj.id;
					xmllis.@x=obj.x;
					xmllis.@y=obj.y;	
					xmllis.@width=obj.width;
					xmllis.@height=obj.height;
					var item:Object=setrow_col(obj as IUIComponent)	;
					xmllis.@col=item["col"];
					xmllis.@row=item["row"];
					xmllis.@label=obj.text;
					xmllis.@relat=relat;
					xmllis.@type="11";
					xmllis.@strtyp="0"
					break;
				}
				case "repnumss":
				{
					xmllis=<repclr></repclr>
					xmllis.@id=obj.id;
					xmllis.@x=obj.x;
					xmllis.@y=obj.y;	
					xmllis.@width=obj.width;
					xmllis.@height=obj.height;
					var item:Object=setrow_col(obj as IUIComponent)	;
					xmllis.@col=item["col"];
					xmllis.@row=item["row"];
					xmllis.@label=obj.text;
					xmllis.@relat=relat;
					xmllis.@type="09";
					xmllis.@strtyp="0"
					break;
				}
				case "repselect":
				{
					xmllis=<repclr></repclr>
					xmllis.@id=obj.id;
					xmllis.@x=obj.x;
					xmllis.@y=obj.y;	
					xmllis.@width=obj.width;
					xmllis.@height=obj.height;
					var item:Object=setrow_col(obj as IUIComponent)	;
					xmllis.@col=item["col"];
					xmllis.@row=item["row"];
					xmllis.@label=obj.text;
					xmllis.@relat=relat;
					xmllis.@type="18";
					xmllis.@strtyp="1"
					var datastr:String="";
					
					var arr2:ArrayCollection=obj.arr as ArrayCollection;
					try
					{
					datastr+=arr2.getItemAt(0).label+"-"+arr2.getItemAt(0).value;
					}
					catch(e:*)
					{
						
					}
					for (var i:int = 1; i < arr2.length; i++) 
					{
						datastr+=","+arr2.getItemAt(i).label+"-"+arr2.getItemAt(i).value;
					}
					
					if(datastr==null || datastr==""){
					 xmllis.@data=obj.Alternative;
					}else{
					 xmllis.@data=datastr;
					}
					break;
				}
				case "repstr":
				{
					xmllis=<repclr></repclr>
						
					
					xmllis.@id=obj.id;
					xmllis.@x=obj.x;
					xmllis.@y=obj.y;	
					xmllis.@width=obj.width;
					xmllis.@height=obj.height;
					var item:Object=setrow_col(obj as IUIComponent)	;
					xmllis.@col=item["col"];
					xmllis.@row=item["row"];
					xmllis.@label=obj.text;
					xmllis.@relat=relat;
					xmllis.@type="04";
					xmllis.@strtyp="1"
					break;
				}
			  case "repstrcompare":
			  {
				  xmllis=<repclr></repclr>
				  
				  
				  xmllis.@id=obj.id;
				  xmllis.@x=obj.x;
				  xmllis.@y=obj.y;	
				  xmllis.@width=obj.width;
				  xmllis.@height=obj.height;
				  var item:Object=setrow_col(obj as IUIComponent)	;
				  xmllis.@col=item["col"];
				  xmllis.@row=item["row"];
				  xmllis.@label=obj.text;
				  xmllis.@relat=relat;
				  xmllis.@type="21";
				  xmllis.@strtyp="1"
				  break;
			  }
			  case "repstrdim":
			  {
				  xmllis=<repclr></repclr>
				  xmllis.@id=obj.id;
				  xmllis.@x=obj.x;
				  xmllis.@y=obj.y;	
				  xmllis.@width=obj.width;
				  xmllis.@height=obj.height;
				  var item:Object=setrow_col(obj as IUIComponent)	;
				  xmllis.@col=item["col"];
				  xmllis.@row=item["row"];
				  xmllis.@label=obj.text;
				  xmllis.@relat=relat;
				  xmllis.@type="22";
				  xmllis.@strtyp="1"
				  break;
			  }			  
				  
				case "reptime":
				{
					xmllis=<repclr></repclr>
					xmllis.@id=obj.id;
					xmllis.@x=obj.x;
					xmllis.@y=obj.y;	
					xmllis.@width=obj.width;
					xmllis.@height=obj.height;
					var item:Object=setrow_col(obj as IUIComponent)	;
					xmllis.@col=item["col"];
					xmllis.@row=item["row"];
					xmllis.@label=obj.text;
					xmllis.@relat=relat;
					xmllis.@strtyp="1"
					var ctyp:String;
					
					switch(obj.datetype)
					{
						case "time":
						{
							ctyp="01"
							break;
						}
						case "day":
						{
							ctyp="02";
							break;
						}
						case "daytime":
						{
							ctyp="03";
							break;
						}
					}
					xmllis.@type=ctyp;
					
					break;
				}
				case "reptimec":
				{
					xmllis=<repclr></repclr>
					xmllis.@id=obj.id;
					xmllis.@x=obj.x;
					xmllis.@y=obj.y;	
					xmllis.@width=obj.width;
					xmllis.@height=obj.height;
					var item:Object=setrow_col(obj as IUIComponent)	;
					xmllis.@col=item["col"];
					xmllis.@row=item["row"];
					xmllis.@label=obj.text;
					xmllis.@relat=relat;
					var ctyp:String;
					xmllis.@strtyp="1"
					switch(obj.datetype)
					{
						case "time":
						{
							ctyp="10"
							break;
						}
						case "day":
						{
							ctyp="19";
							break;
						}
						case "daytime":
						{
							ctyp="20";
							break;
						}
					}
					xmllis.@type=ctyp;
					
					
					
					break;
				}
				case "reptimes":
				{
					xmllis=<repclr></repclr>
					xmllis.@id=obj.id;
					xmllis.@x=obj.x;
					xmllis.@y=obj.y;	
					xmllis.@width=obj.width;
					xmllis.@height=obj.height;
					var item:Object=setrow_col(obj as IUIComponent)	;
					xmllis.@col=item["col"];
					xmllis.@row=item["row"];
					xmllis.@label=obj.text;
					xmllis.@relat=relat;
					var ctyp:String;
					xmllis.@strtyp="1"
					switch(obj.datetype)
					{
						case "time":
						{
							ctyp="06"
							break;
						}
						case "day":
						{
							ctyp="07";
							break;
						}
						case "daytime":
						{
							ctyp="08";
							break;
						}
					}
					xmllis.@type=ctyp;
					break;
				}
			}
			return xmllis;
		}
		[Bindable]
		public var crow:int=0;
		[Bindable]
		private var comarr:Array=new Array();
		[Bindable]
		public  var arr:Array=new Array();
		[Bindable]
		public var ccolumn:int=0;
		public function setrow_col(comrule:IUIComponent):Object{  
			comarr=new Array();
			arr=new Array();
			if(comrule!=null)
			{   
				var diff:int=((comrule.y+comrule.height)/50);
				
				if(((comrule.y+comrule.height)%50)!=0)
				{ 
					diff=diff+1;
					
					var comcolumn:int= diff*50 ; 
					var gap:int =comcolumn%(comrule.y+comrule.height);    	       
					comrule.y=comrule.y+gap; 
				}  
				crow=diff;
				var s:int=0;
				for(var index:int=0;index<setarr.length;index++){
					comarr.push(setarr[index].col as IUIComponent); 
					
				}
				for(var j:int=0; j<comarr.length;j++)
				{
					if(comarr[j].name==comrule.name){
						arr[s]=	UIComponent(comarr[j]);      
						s++;  
					}else{
						var di:int=((UIComponent(comarr[j]).y+UIComponent(comarr[j]).height)/50);
						
						if(diff==di)
						{   
							/* Alert.show(diff+"------"+di+"+++++++"+UIComponent(comarr[j])["text"]); */
							arr[s]=	UIComponent(comarr[j]);      
							s++;              
						}
					}
				}
				
				for(var i:int=0;i<arr.length;i++)
				{    
					for(var k:int=0; k<arr.length;k++)
					{        		
						if(i<arr.length){
							
							if(UIComponent(arr[i]).x<UIComponent(arr[k]).x)
							{  
								var hui:UIComponent;
								hui=arr[i];
								arr[i]=arr[k];
								arr[k]=hui;
							} 
						}
					}
					
				}
				//Alert.show(arr.length.toString());
				for(var index:int=0;index<arr.length;index++)
				{
					
					if(UIComponent(arr[index]).name==comrule.name){
						ccolumn=index+1;
						
						if(index==0){
							comrule.x=0;
						}else{ 
							comrule.x=UIComponent(arr[index-1]).x+UIComponent(arr[index-1]).width;       
						} 
					}   
				}
				arr =new Array(); 
			}   
			
			var item:Object=new Object();
			item["col"]=ccolumn;
			item["row"]=crow;
			return item; 
			
		}
		

	}
}