package com.openj.main.maininfo
{
	import mx.effects.Fade;
	import mx.effects.Parallel;

	{   
		import flash.display.DisplayObject;   
		
		import mx.core.IFlexDisplayObject;   
		import mx.effects.Blur;   
		import mx.events.TweenEvent;   
		import mx.managers.PopUpManager;   
		import mx.effects.Zoom;   
		
		public class PopUpEffect   
		{   
			
			public function PopUpEffect()   
			{   
			}   
			
			public static function Show(control:IFlexDisplayObject, parent:DisplayObject, modal:Boolean=false):void   
			{   
				//             var mShowEffect:Blur=new Blur();   
				//             mShowEffect.blurXFrom=255;   
				//             mShowEffect.blurYFrom=255;   
				//             mShowEffect.blurXTo=0;   
				//             mShowEffect.blurYTo=0;   
				//             mShowEffect.target= control;   
				//             mShowEffect.duration=300;   
				var mHideEffect:Blur=new Blur();   
				mHideEffect.blurXFrom=255;   
				mHideEffect.blurYFrom=255;   
				mHideEffect.blurXTo=0;   
				mHideEffect.blurYTo=0; 
				mHideEffect.duration=560;   
				mHideEffect.target=control;  
				
				var mShowEffect2:Fade=new Fade();   
				mShowEffect2.alphaFrom=0;   
				mShowEffect2.alphaTo=1;     
				mShowEffect2.target=control;   
				mShowEffect2.duration=560;   
				
				
				PopUpManager.addPopUp(control,parent,modal);   
				PopUpManager.centerPopUp(control);   
			
				var sss:Parallel =new Parallel();
				sss.addChild(mHideEffect);
				sss.addChild(mShowEffect2);
				
				sss.play();   
				
			}   
			
			public static function Hide(control:IFlexDisplayObject):void   
			{   
				
				
				var mHideEffect:Blur=new Blur();   
				mHideEffect.blurXFrom=0;   
				mHideEffect.blurYFrom=0;   
				mHideEffect.blurXTo=255;   
				mHideEffect.blurYTo=255;   
				mHideEffect.addEventListener(TweenEvent.TWEEN_END, function()   
				{   
					PopUpManager.removePopUp(control);   
				});   
				mHideEffect.duration=560;   
				mHideEffect.target=control;  
				
				
				var mShowEffect2:Fade=new Fade();   
				mShowEffect2.alphaFrom=1;   
				mShowEffect2.alphaTo=0;     
				mShowEffect2.target=control;   
				mShowEffect2.duration=560;  
				
				var sss:Parallel =new Parallel();
				sss.addChild(mHideEffect);
				sss.addChild(mShowEffect2);
				
				sss.play();   
			}   
		}   
		}
	}
