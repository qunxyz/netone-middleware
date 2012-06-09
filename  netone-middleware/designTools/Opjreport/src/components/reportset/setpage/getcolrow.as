package components.reportset.setpage
{
	import mx.core.UIComponent;

	public class getcolrow
	{
		public function getcolrow()
		{
		}
		[Bindable]//第几列
		public var ccolumn:int=0;    
		[Bindable]//第几行
		public var diff:int=0;
		private  function rule(comrule:UIComponent):void
		{  
			if(comrule!=null)
			{   
				var diff:int=((comrule.y+comrule.height)/50);
				
				if(((comrule.y+comrule.height)%50)!=0)
				{ 
					diff=diff+1;
					
					var comcolumn:int= diff*50 ; 
					var gap:int =comcolumn%(comrule.y+comrule.height);    	       
					comrule.y=newY=comrule.y+gap; 
				}  
				crow=diff;
				var s:int=0;
				comarr=this.getChildren();
				for(var j:int=0; j<this.numChildren;j++)
				{
					var di:int=((UIComponent(comarr[j]).y+UIComponent(comarr[j]).height)/50);
					
					if(diff==di)
					{               	
						arr[s]=	UIComponent(comarr[j]);      
						s++;              
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
			}   
		}     
		

	}
}