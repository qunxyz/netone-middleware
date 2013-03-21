package oe.mid.netone.other;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;


public class HealthImpl implements HealthIfc {

	public double caloryUseTotal(Timestamp from, Timestamp to, String userName) {

		if(from==null){
			from=new Timestamp(System.currentTimeMillis());
		}
		if(to==null){
			to=new Timestamp(System.currentTimeMillis());
		}
		double time=from.getTime();
		if(StringUtils.isEmpty(userName)){
			time=time*99;
		}
		return time/1000000;
	}

	public long sportTimeTotal(Timestamp from, Timestamp to, String userName) {

		if(from==null){
			from=new Timestamp(System.currentTimeMillis());
		}
		if(to==null){
			to=new Timestamp(System.currentTimeMillis());
		}
		double time=(to.getTime()-from.getTime())*0.6;
		if(StringUtils.isEmpty(userName)){
			time=time*99;
		}
		return (long)time/(1000*3600);
	}

	public List<HeightInfo> heightTotal(Timestamp from, Timestamp to,
			String userName) {

		int heightstep=100;
		List list=new ArrayList();
		
		long time1=from.getTime();
		long time2=to.getTime();
		long timestep=(long)((time2-time1)*0.8);
		long startheight=(long)Math.random()*1000;
		
		for(int i=0;i<heightstep;i++){
			HeightInfo he=new HeightInfo();
			double height=startheight+Math.random()*10;
			he.setHeight(height);
			long time=time1+timestep*i;
			he.setTime(new Date(time));
			list.add(he);
		}
		
		return list;
	}

	public List<SportInfo> sportTop(String userName, String toptype) {
		
		String []userpool={"mike","jim","lucy","³ÂÃ÷","carol","robanco","cicix","cobowa","ÀÙÒò","Jkui"};
		long cal=System.currentTimeMillis();
		List list=new ArrayList();
		for(int i=0;i<10;i++){
			SportInfo sop=new SportInfo();
			sop.setUsername(userpool[(int)(Math.random()*10)]);
			int step=i*(int)(Math.random()*100);
			sop.setCaloris(step+cal/3230);
			sop.setDistance(step+cal/1000000);
			sop.setHeight(step+cal/90000000);
			sop.setStep(step+cal/5000000);
			sop.setTimeuse(step+cal/3500000);
			list.add(sop);
		}
		return list;
	}

	public long stepTotal(Timestamp from, Timestamp to, String userName) {
		// TODO Auto-generated method stub
		return sportTimeTotal(from,to,userName)/100;
	}

	@Override
	public List<TrackInfo> trackTotal(Timestamp from, Timestamp to,
			String userName) {

		int heightstep=100;
		List list=new ArrayList();
		
		long time1=from.getTime();
		long time2=to.getTime();
		long timestep=(long)((time2-time1)*0.8);
		long startheight=(long)Math.random()*100;
		
		for(int i=0;i<heightstep;i++){
			TrackInfo he=new TrackInfo();
			double x=startheight+Math.random()*10;
			double y=startheight+Math.random()*10;
			he.setXoffset(x);
			he.setYoffset(y);
			long time=time1+timestep*i;
			he.setTimepoint(new Date(time));
			list.add(he);
		}
		return list;
	}
	


	@Override
	public boolean syncAddUser(String userid) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean syncFobitUser(String userid) {
		// TODO Auto-generated method stub
		return true;
	}

}
