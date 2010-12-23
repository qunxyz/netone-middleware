package oe.bi.common.chart;

public class CachedChart {
	
	private byte[] img ;
	private long cacheTime ;
	

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
		cacheTime = System.currentTimeMillis();
	}

	public long getCacheTime() {
		return cacheTime;
	}
	
}
