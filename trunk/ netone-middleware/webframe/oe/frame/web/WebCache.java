package oe.frame.web;

import java.util.Arrays;
import java.util.Date;

import java.util.Locale;

import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

/**
 * 结合memcached 来改造Cache，来实现分布式的外部cache应用<br>
 * 
 * 1级缓存用于缓存页中的项
 * 
 * @author chen.jia.xun(Robanco)<br>
 *         mail:56414429@qq.com, chenjiaxun@oesee.com<br>
 *         tel:13328675083<br>
 */
public class WebCache {

	public static final String _CACHE_ITEM_WEBITEM = "$:WEBITEM";
	public static final String _CACHE_ITEM_WEBCELL = "$:WEBCELL";
	public static final String _CACHE_ITEM_WEBGROUP = "$:WEBGROUP";
	public static final String _CACHE_ITEM_DYFORM = "$:DY";
	public static final String _CACHE_ITEM_BI = "$:BI";
	public static final String _CACHE_ITEM_WF = "$:WF";
	public static final String _CACHE_ITEM_RS = "$:RS";
	public static final String _CACHE_ITEM_SECURITY = "$:SECURITY";

	static Log log = LogFactory.getLog(WebCache.class);

	private static ResourceBundle cfg = ResourceBundle.getBundle("WebCache",
			Locale.CHINESE);

	// create a static client as most installs only need
	// a single instance
	protected static MemCachedClient mcc = null;

	// set up connection pool once at class load
	static {
		initCache();
	}

	private static void initMemcache(String[] servers, Integer[] weightsValue) {
		mcc = null;
		mcc = new MemCachedClient();

		// grab an instance of our connection pool
		SockIOPool pool = SockIOPool.getInstance();

		// set the servers and the weights
		pool.setServers(servers);
		pool.setWeights(weightsValue);

		pool.setInitConn(new Integer(cfg.getString("InitConn")));
		pool.setMinConn(new Integer(cfg.getString("MinConn")));
		pool.setMaxConn(new Integer(cfg.getString("MaxConn")));
		pool.setMaxIdle((long) (1000 * 60 * 60 * new Float(cfg
				.getString("MaxIdle"))));

		pool.setMaintSleep((long) (1000 * 60 * 60 * new Float(cfg
				.getString("MaintSleep"))));

		pool.setNagle(new Boolean(cfg.getString("Nagle")));
		pool.setSocketTO(new Integer(cfg.getString("SocketTO")));
		pool.setSocketConnectTO(new Integer(cfg.getString("SocketConnectTO")));

		// initialize the connection pool
		pool.initialize();

		// lets set some compression on for the client
		// compress anything larger than 64k
		mcc.setCompressEnable(new Boolean(cfg.getString("CompressEnable")));
		mcc.setCompressThreshold(1024 * new Integer(cfg
				.getString("CompressThreshold")));

	}

	public static void initCache() {
		// server list and weights
		String serverlist = cfg.getString("CacheServerList");
		String weightlist = cfg.getString("CacheServersWeight");
		String[] servers = StringUtils.split(serverlist, ",");
		String[] weights = StringUtils.split(weightlist, ",");
		if (servers == null) {
			log
					.error("lose cache config in cmsconfig.properties! use temporary cache");

		} else {

			for (int i = 0; i < weights.length; i++) {
				log.info("cacheServer:" + servers[i]);
			}

			int length = weights.length;
			if (weights == null || weights.length < servers.length) {
				length = servers.length;
			}
			Integer[] weightsValue = new Integer[length];
			Arrays.fill(weightsValue, new Integer(1));
			for (int i = 0; i < weights.length; i++) {
				weightsValue[i] = new Integer(weights[i]);
			}
			initMemcache(servers, weightsValue);
		}

	}

	public static Object getCache(String key) {
		return mcc.get(key);
	}

	public static void setCache(String key, Object value, Date date) {
		
		if (date != null) {
			mcc.set(key, value, date);
		} else {
			mcc.set(key, value);
		}
	}

	public static void setCache(String key, Object value, int numtime) {

		mcc.set(key, value, numtime);

	}

	public static void removeCache(String key) {
		mcc.delete(key);
	}

	public static void removeallCache() {
		mcc.flushAll();
	}

	public static boolean containCache(String key) {
	
		return mcc.keyExists(key);
	}
	
	public static MemCachedClient getcoreHandle(){
		return mcc;
	}

	public static void main(String[] args) {
		
		WebCache.setCache("111", "xxx", null);
		WebCache.setCache("222", "xxx", 300000);
		
		System.out.println(WebCache.getcoreHandle().getCounter("111"));
		System.out.println(WebCache.getcoreHandle().decr("222"));
	}

}
