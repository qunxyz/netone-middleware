package oe.netone.bi.flashchart;

import java.util.ArrayList;
import java.util.List;

import oe.netone.bi.ExpendTime;
import oe.netone.bi.ManageBI;
import oe.netone.bi.PAnalysis;
import oe.netone.bi.xyData;

import com.report.chart.entity.PieChartObj;
import flex.messaging.io.ArrayCollection;

/**
 *  饼图的  预测方法
 *   xuwei（2012-2-15）
 *   
 *   
 * */
public class PiePredictive {
    
	public static List predictiveList(String predictionbegan,String predictionend,String Predictiontype
			  ,String xdata,String []zbList,String limitcolumn,String limit,String tablename,int j,String Formcode){
		  
	 List Pielist = new ArrayList();
	    List<PAnalysis> fsdata=null;
		 
		
		String [] predictionarr=null;
	List<xyData> predictionData=null;
 
		if(predictionbegan!="" ||  predictionbegan!=null || predictionend!="" || predictionend!=null){
		predictionarr=ExpendTime.getExpendTime(predictionbegan,predictionend);
		ArrayCollection farr=new ArrayCollection();
 	    for(int i=0;i<predictionarr.length;i++){
			   farr.add(predictionarr[i]);
		 }
     	switch (j) {
			case 2:
				try {
	        		fsdata= ManageBI.ExpendXydata(xdata,zbList[0],limitcolumn,limit,tablename);
	        		predictionData=ForecastMethod.ExpendValue(fsdata,farr,Predictiontype);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
 	
			
	 
		if(predictionData!=null){
		 for (int i = 0; i < predictionData.size(); i++) {
			if (j ==2) {
			double shuju= Double.valueOf(predictionData.get(i).getYdata()).doubleValue();   
			PieChartObj Pieoyu = new PieChartObj(); 
			Pieoyu.setValue(shuju);
			Pieoyu.setKey(predictionData.get(i).xdata);
		    Pielist.add(Pieoyu);
			} 
		  }
			}
			}
		return  Pielist;
}
}
