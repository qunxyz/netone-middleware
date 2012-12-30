package oe.mid.netone.other;

import java.sql.Timestamp;


public class HealthImpl implements HealthIfc {


	@Override
	public double caloryUseTotal(Timestamp from, Timestamp to, String userName,
			String sportType, String equipmentType) {
		if(from==null||to==null||userName==null||sportType==null||equipmentType==null){
			return 0;
		}
		long time=from.getTime()+to.getTime();

		return time/10000;

	}

	@Override
	public double caloryUseTotal(Timestamp from, Timestamp to,
			String sportType, String equipmentType) {
		if(from==null||to==null||sportType==null||equipmentType==null){
			return 0;
		}
		long time=from.getTime()+to.getTime();
	
		return time;
	}

	@Override
	public double sportDistanceTotal(Timestamp from, Timestamp to,
			String userName, String sportType, String equipmentType) {
		if(from==null||to==null||userName==null||sportType==null||equipmentType==null){
			return 0;
		}
		long time=from.getTime()+to.getTime();

		return time/10000;
	}

	@Override
	public double sportDistanceTotal(Timestamp from, Timestamp to,
			String sportType, String equipmentType) {
		if(from==null||to==null||sportType==null||equipmentType==null){
			return 0;
		}
		long time=from.getTime()+to.getTime();
		return time;
	}

	@Override
	public long sportTimeTotal(Timestamp from, Timestamp to, String userName,
			String sportType, String equipmentType) {
		if(from==null||to==null||userName==null||sportType==null||equipmentType==null){
			return 0;
		}
		long time=from.getTime()+to.getTime();
		return time/10000;
	}

	@Override
	public long sportTimeTotal(Timestamp from, Timestamp to, String sportType,
			String equipmentType) {
		if(from==null||to==null||sportType==null||equipmentType==null){
			return 0;
		}
		long time=from.getTime()+to.getTime();
		return time;
	}
	
	public static void main(String[] args) {
		System.out.println(Math.abs(-10000));
		
	}

}
