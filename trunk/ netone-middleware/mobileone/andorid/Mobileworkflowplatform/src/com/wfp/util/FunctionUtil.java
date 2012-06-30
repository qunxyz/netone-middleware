package com.wfp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.wfp.activity.R;

public class FunctionUtil {
	
	public static AlertDialog menuDialog; // menu菜单Dialog
	public static View menuView;
	public static GridView menuGrid;
	private static String SERVICE_INFO = "SERVICEINFO";		//shared中服务端信息key
	
	public static Bitmap returnBitMap(String url) {
		URL myFileUrl = null;
		Bitmap bitmap = null;
		try {
			myFileUrl = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		try {
			HttpURLConnection conn = (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * 创建自定义网格菜单
	 * @param context 界面实例
	 * @param menuView 菜单容器
	 * @param menuGrid 菜单网格视图
	 * @return
	 */
	public static AlertDialog initMenu(Context context, View menuView, GridView menuGrid, String[] textArray, int[] topImageArray){


		// 创建AlertDialog
		menuDialog = new AlertDialog.Builder(context).create();
		menuDialog.setView(menuView);

		menuGrid.setAdapter(getMenuAdapter(context, textArray, topImageArray));
		return menuDialog;
		
	}
	
	/**
	 * 构造菜单Adapter
	 * @param context activity实例
	 * @param menuNameArray 菜单项名称
	 * @param imageResourceArray 菜单项图片
	 *            
	 * @return SimpleAdapter
	 */
	private static SimpleAdapter getMenuAdapter( Context context, String[] menuNameArray,
			int[] imageResourceArray) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < menuNameArray.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("menuItemImage", imageResourceArray[i]);
			map.put("menuItemText", menuNameArray[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(context, data,
				R.layout.menu_griditem, new String[] { "menuItemImage", "menuItemText" },
				new int[] { R.id.icon, R.id.text });
		return simperAdapter;
	}
	
	
	/**
     * 在给定的图片的右上角加上联系人数量。数量用红色表示
     * @param context activity实例
     * @param icon 给定的图片
     * @return 带联系人数量的图片
     */
    public static Bitmap generatorContactCountIcon(Context context, Bitmap icon){
    	//初始化画布
    	int iconSize=(int)context.getResources().getDimension(android.R.dimen.app_icon_size);
    	Log.d("iconsize", "the icon size is "+iconSize);
    	Bitmap contactIcon=Bitmap.createBitmap(iconSize, iconSize, Config.ARGB_8888);
    	Canvas canvas=new Canvas(contactIcon);
    	
    	//拷贝图片
    	Paint iconPaint=new Paint();
    	iconPaint.setDither(true);//防抖动
    	iconPaint.setFilterBitmap(true);//用来对Bitmap进行滤波处理，当选择Drawable时，会有抗锯齿的效果
    	Rect src=new Rect(0, 0, icon.getWidth(), icon.getHeight());
    	Rect dst=new Rect(0, 0, icon.getWidth(), icon.getHeight());
    	canvas.drawBitmap(icon, src, dst, iconPaint);
    	
    	//在图片上创建一个覆盖的数字
    	int contacyCount=23;
    	//启用抗锯齿和使用设备的文本字距
    	Paint countPaint=new Paint(Paint.ANTI_ALIAS_FLAG|Paint.DEV_KERN_TEXT_FLAG);
    	countPaint.setColor(Color.RED);
    	countPaint.setTextSize(16f);
    	countPaint.setTypeface(Typeface.DEFAULT_BOLD);
    	canvas.drawText(String.valueOf(contacyCount), icon.getWidth()-5, 20, countPaint);
    	return contactIcon;
    }

	/**
     * 根据id获取一个图片
     * @param res
     * @param resId
     * @return
     */
    public static Bitmap getResIcon(Resources res,int resId){
    	Drawable icon=res.getDrawable(resId);
    	if(icon instanceof BitmapDrawable){
    		BitmapDrawable bd=(BitmapDrawable)icon;
    		return bd.getBitmap();
    	}else{
    		return null;
    	}
    }
    
    /**
     * 获取SD卡路径
     * @return sd卡路径
     */
	public static String getSDPath() {
		File sdDir = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdDir.toString();

	}

	/**
	 * 保存网络文件到SD卡
	 * @param context 界面实例
	 * @param urlstr 网络路径
	 * @return
	 * @throws Exception 
	 */
	public static boolean saveWebFileToSDCard(Context context, String urlstr) throws Exception{
		
		try {
			//获取服务端域名和端口
			SharedPreferences serviceInfo = context.getSharedPreferences(SERVICE_INFO,0);
			String serviceip = serviceInfo.getString("serviceip", "http://112.5.5.114:84");
			//文件网络路径
			URL url = new URL(serviceip+urlstr);
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setDoInput(true);
			urlConn.connect();
			// 请求成功
			if (urlConn.getResponseCode() == 200) {
				Log.i("filelength", urlConn.getHeaderField( "Content-Length"));
				Log.i("filelength2", String.valueOf(urlConn.getContentLength()));
				InputStream inputStream = urlConn.getInputStream();
				//获取SD卡的路径
				String SDPath = getSDPath();
				if(SDPath != null) {
					//创建要使用的文件夹
					String appPath = SDPath + "/mobileworkflow";
					File dir = new File(appPath);
					if(!dir.exists())
						dir.mkdirs();
					//创建输出文件
					File file = new File(appPath + "/" + urlstr.split("/")[urlstr.split("/").length-1]);
					Log.i("webfilename", urlstr.split("/")[urlstr.split("/").length-1]);
					if(file.exists())
						file.delete();
					file.createNewFile();
					FileOutputStream output = new FileOutputStream(file);
					byte buffer [] = new byte[2];
					while((inputStream.read(buffer)) != -1){
						output.write(buffer);
					}
					output.flush();
					return true;
				} else {
					//Toast.makeText(context, context.getResources().getString(R.string.noSDCard), Toast.LENGTH_LONG).show();
					return false;
				}
			}else{
				//Toast.makeText(context, context.getResources().getString(R.string.errorcode)+urlConn.getResponseCode(), Toast.LENGTH_LONG).show();
				return false;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(context, context.getResources().getString(R.string.errorcode)+e.getMessage(), Toast.LENGTH_LONG).show();
			return false;
		} catch (Exception ex){
			ex.printStackTrace();
			//Log.e("sdcardException", ex.getMessage());
			System.out.println(ex.getMessage());
			throw new Exception(context.getResources().getString(R.string.serviceImaUrlError));

		}
	}
	/**
	 * 遍历制定目录下文件，获取路径和文件名称
	 * @param path 路径
	 * @return 列表集合（绝对路径、文件名称）
	 */
	
	public static ArrayList findFilesList(String path,int showType) {
		ArrayList list = new ArrayList();
		File file = new File(path);
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			HashMap map = new HashMap();
			File subFile = files[i];
			if(showType == 1){	//图片目录展示
				if (subFile.isFile()) {
					map.put("filepath", subFile.getAbsolutePath());
					map.put("filename", subFile.getName());
					list.add(map);
				} else if (subFile.isDirectory()) {
					map.put("filepath", R.drawable.sdcard_dirimg );
					map.put("dirpath", subFile.getAbsolutePath());
					map.put("filename", subFile.getName());
					list.add(map);
				}
			}else if(showType == 2){	//普通文件目录展示
				if (subFile.isFile()) {
					map.put("fileimg", R.drawable.sdcard_file);
					map.put("filepath", subFile.getAbsolutePath());
					map.put("filename", subFile.getName());
					list.add(map);
				} else if (subFile.isDirectory()) {
					map.put("fileimg", R.drawable.sdcard_dirimg );
					map.put("filepath", subFile.getAbsolutePath());
					map.put("filename", subFile.getName());
					list.add(map);
				}
			}
		}
		return list;
	}
	
	/**
	 * 获取功能访问服务端url
	 * @param jsonObj  扩展功能json对象
	 * @param funcId 功能编号
	 * @return 功能url
	 * @throws JSONException
	 */
	public static String getFuncUrl(JSONObject jsonObj, String funcId) throws JSONException{
		String funObj2 = jsonObj.getString(funcId);
		final JSONObject jsonObj2 = new JSONObject(funObj2);
		String loadFormUrl = "";
		for (Iterator iterator = jsonObj2.keys(); iterator.hasNext();) {
			String fieldName = (String) iterator.next();
			if(fieldName.equals("url")){
				loadFormUrl = jsonObj2.getString(fieldName);
			}
		}
		return loadFormUrl;
	}
	/*
	 * @param context 实例
	 * @return lonLatArray 当前经纬度值数组
	 */
	public static double[] getLonlatValue(Context context){
		double[] lonLatArray = new double[2];
		//获取当前经纬度
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		if (locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Location location = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null) {
				lonLatArray[1] = location.getLatitude();
				lonLatArray[0] = location.getLongitude();
				//Toast.makeText(FormListActivity.this, " 经度："+location.getLatitude()+"维度："+location.getLongitude(), Toast.LENGTH_LONG).show();

			}
		} else {
			LocationListener locationListener = new LocationListener() {

				// Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
				@Override
				public void onStatusChanged(String provider,
						int status, Bundle extras) {

				}

				// Provider被enable时触发此函数，比如GPS被打开
				@Override
				public void onProviderEnabled(String provider) {

				}

				// Provider被disable时触发此函数，比如GPS被关闭
				@Override
				public void onProviderDisabled(String provider) {

				}

				// 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
				@Override
				public void onLocationChanged(Location location) {
					if (location != null) {
						Log.e("Map", "Location changed : Lat: "
								+ location.getLatitude() + " Lng: "
								+ location.getLongitude());
						//Toast.makeText(FormListActivity.this, "改变的： 经度："+location.getLatitude()+"维度："+location.getLongitude(), Toast.LENGTH_LONG).show();
					}
				}
			};
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 1000, 0,
					locationListener);
			Location location = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (location != null) {
				lonLatArray[1] = location.getLatitude(); 	// 纬度
				lonLatArray[0] = location.getLongitude(); 	// 经度
				//Toast.makeText(FormListActivity.this, " 经度："+location.getLatitude()+"维度："+location.getLongitude(), Toast.LENGTH_LONG).show();
			}

		}
		return lonLatArray;
	}
}

