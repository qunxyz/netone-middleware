package components.reports
{
	import com.resistdesign.PortDesigner;
	import com.resistdesign.drawTable.TableRader;
	import com.resistdesign.view.reportbox;
	
	import mx.collections.ArrayCollection;

	public class reportmanage
	{
		public static var reportdesign:PortDesigner;
		public static var tablerader:TableRader;
		public static var reportboxs:reportbox;
		public static var selectobj:Object;
		public static var reportcolarr:ArrayCollection=new ArrayCollection();
		public function reportmanage()
		{
		}
		public static function  addreportarr(col:Object,coltyp:String,istable:Boolean):void
		{
			reportcolarr.addItem({col:col,coltyp:coltyp,istable:istable});
		}
	}
}