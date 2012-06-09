package com.resistdesign.opjtooltip
{
	
	import mx.containers.Panel;
	import mx.core.IToolTip;
	
	import spark.components.Button;
	import spark.components.Panel;
	import spark.effects.Animate;
	import spark.effects.animation.MotionPath;
	import spark.effects.animation.SimpleMotionPath;
	
	public class MyToolTip extends   spark.components.Panel implements IToolTip
	{
		public function MyToolTip()
		{
			//			var fader:Animate=new Animate();
			//			fader.duration=500;
			//			fader.repeatBehavior="reverse"
			//			fader.repeatCount=0;
			//			fader.target=this;
			//			
			//			
			//			var motion:SimpleMotionPath=new SimpleMotionPath();
			//			motion.property="alpha";
			//			motion.valueFrom="1";
			//			motion.valueTo="0";
			//			
			//			var vector:Vector.<MotionPath>=new Vector.<MotionPath>;
			//			vector[0]=motion;
			//			
			//			fader.motionPaths=vector;
			//			
			//			fader.play();
		}
		
		public function get text():String
		{
			return null;
		}
		
		public function set text(value:String):void
		{
		}
	}
}