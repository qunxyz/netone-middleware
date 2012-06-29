package com.wfp.overlay;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.wfp.activity.R;

public class MyOverlay extends ItemizedOverlay {
	private Context context;
	private Location location;
	private String lable = "";
	private int radius = 22;
	private double geoLat, geoLong;
	private GeoPoint gp;
	private int textColor;
	private Resources r;
	private int pointType;	//	指针类型
	private ArrayList<OverlayItem> mapOverlays = new ArrayList<OverlayItem>();
	
	public MyOverlay(Drawable defaultMarker) {
		super(defaultMarker);
		// TODO Auto-generated constructor stub
	}
	
	public MyOverlay(Context context, Location location, String lable,
			int textColor, int pointType) {
		super(null);
		this.context = context;
		this.location = location;
		this.lable = lable;
		this.geoLat = this.location.getLatitude() * 1E6;
		this.geoLong = this.location.getLongitude() * 1E6;
		this.textColor = textColor;
		this.pointType = pointType;
	}

	public MyOverlay(Context context, double geoLat, double geoLong, String lable,
			int textColor,Resources r, int pointType) {
		super(null);
		this.geoLat = geoLat * 1E6;
		this.geoLong = geoLong * 1E6;
		this.lable = lable;
		this.textColor = textColor;
		this.r = r;
		this.pointType = pointType;
	}

	public MyOverlay(Context context, GeoPoint gp, String lable, int textColor,
			Resources r, int pointType) {
		super(null);
		this.gp = gp;
		this.geoLat = gp.getLatitudeE6() / 1E6;
		this.geoLong = gp.getLongitudeE6() / 1E6;
		this.lable = lable;
		this.textColor = textColor;
		this.r = r;
		this.pointType = pointType;
	}
	

	@Override
	public boolean draw(Canvas canvas, MapView mapView, boolean shadow,
			long when) {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		paint.setColor(this.textColor);
		//设置抗锯齿
		paint.setAntiAlias(true);
		//设置粗体
		paint.setFakeBoldText(true);
		Point screenPoint = new Point();

		// 将"实际地址"在地图上的位置转换成屏幕的实际坐标
		mapView.getProjection().toPixels(gp, screenPoint);
//		RectF oval = new RectF(screenPoint.x - radius, screenPoint.y - radius,
//				screenPoint.x + radius, screenPoint.y + radius);
//		canvas.drawOval(oval, paint);
		Bitmap marker = null;
		if(pointType == 1){
			marker = BitmapFactory.decodeResource(r, R.drawable.mark);
			// 在地图上绘制文字
			canvas.drawText(lable, screenPoint.x +15, screenPoint.y - 15,
							paint);
		}else if(pointType == 2){
			marker = BitmapFactory.decodeResource(r, R.drawable.market);
			// 在地图上绘制文字
			canvas.drawText(lable, screenPoint.x -6, screenPoint.y +8,
							paint);
		}
		canvas.drawBitmap(marker, screenPoint.x-15 ,screenPoint.y-30, null);
		
//		Resources r = 
		
		//投影
//		Projection projection=mapView.getProjection(); 
//        Point point=new Point(); 
//		projection.toPixels(gp, point); 
//        Paint paint = new Paint();// 设置样式和颜色的对象 
//        paint.setARGB(250, 255, 255, 255);// 设置颜色 
//        paint.setColor(textColor);
//        canvas.drawText("我在这里！", point.x, point.y, paint);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), 
//                R.drawable.flag); 
//        canvas.drawBitmap(bitmap, point.x – bitmap.getWidth() , point.y
//                – bitmap.getHeight() , null);
		
//		Projection projection = mapView.getProjection();
//		Point point = new Point();
//		projection.toPixels(gp, point);
//
//		// background
//		Paint background = new Paint();
//		background.setColor(Color.WHITE);
//		RectF rect = new RectF();
//		rect.set(point.x + 2 * radius, point.y - 4 * radius,
//				point.x + 90, point.y + 12);
// 
//		// text "My Location"
//		Paint text = new Paint();
//		text.setAntiAlias(true);
//		text.setColor(Color.BLUE);
//		text.setTextSize(12);
//		text.setTypeface(Typeface.MONOSPACE);
// 
//		// the circle to mark the spot
//		Paint circle = new Paint();
//		circle.setColor(Color.BLUE);
//		circle.setAntiAlias(true);
// 
//		canvas.drawRoundRect(rect, 2, 2, background);
//		canvas.drawCircle(point.x, point.y, radius, circle);
//		canvas.drawText("My Location", point.x + 3 * radius, point.y + 3
//				* radius, text);
		return super.draw(canvas, mapView, shadow, when);
	}
	
	@Override
	protected OverlayItem createItem(int i) {
		return mapOverlays.get(i);
	}

	@Override
	public int size() {
		return mapOverlays.size();
	}
	
	@Override
	  protected boolean onTap(int index) {
		  OverlayItem item = mapOverlays.get(index);
		  AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		  dialog.setTitle(item.getTitle());
		  dialog.setMessage(item.getSnippet());
		  dialog.show();
		  return true;
	  }

	
//	@Override
//	public boolean onTap(GeoPoint arg0, MapView mv) {
//		// TODO Auto-generated method stub
//		  Log.i("onTap", arg0.toString());
//		  Toast.makeText(context, "ontap", Toast.LENGTH_SHORT).show();
//		  GeoPoint p = mv.getProjection().fromPixels(   
//	                (int) event.getX(),   
//	                (int) event.getY());   
//
//	            Geocoder geoCoder = new Geocoder(   
//	                getBaseContext(), Locale.getDefault());   
//	            try {   
//	                List<Address> addresses = geoCoder.getFromLocation(   
//	                    p.getLatitudeE6()  / 1E6,    
//	                    p.getLongitudeE6() / 1E6, 1);   
//
//	                String add = "";   
//	                if (addresses.size() > 0)    
//	                {   
//	                    for (int i=0; i<addresses.get(0).getMaxAddressLineIndex();    
//	                         i++)   
//	                       add += addresses.get(0).getAddressLine(i) + "\n";   
//	                }   
//
//	                Toast.makeText(MyOverlay.this, add, Toast.LENGTH_SHORT).show();   
//	            }   
//	            catch (IOException e) {                   
//	                e.printStackTrace();   
//	            }      
//		return true;
//	}

	@Override
	public boolean onTouchEvent(MotionEvent event, MapView mapView) {
		// TODO Auto-generated method stub
		
		if (event.getAction() == 1) {
			Log.i("2onTouch", "get touch event");
        	GeoPoint p = mapView.getProjection().fromPixels((int) event.getX(), (int) event.getY());
        	MapController mc = mapView.getController();
        	//Toast.makeText(context, "指针点击", Toast.LENGTH_SHORT).show();
//   		mc.animateTo(p);
// 
//   		MyOverlay mapOverlay = new MyOverlay(p,"1",Color.RED);
//   		List<Overlay> listOfOverlays = mapView.getOverlays();
//	      listOfOverlays.clear();
//	      listOfOverlays.add(mapOverlay);
 
                return true;            
            } else return false;
//		return super.onTouchEvent(e, mapView);
	}



}
