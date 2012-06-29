package com.wfp.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.wfp.overlay.MyOverlay;
import com.wfp.util.ConnectionServiceThread;
import com.wfp.util.HelpHandler;

public class MyMapActivity extends MapActivity {
	/** Called when the activity is first created. */
	private MapView mapView;
	private MapController mapController;
	private LocationManager locationManager;
	private String locationProvider = "";
	private Location myLocation = null;
	private GeoPoint currentGeoPoint = new GeoPoint((int) (26.080 * 1E6),
			(int) (119.280 * 1E6));
	private GeoPoint toGeoPoint;
	private int zoomLevel = 17;
	private LinearLayout ll_search;
	public Resources r;
	private EditText et;
	private ImageButton ibt_search;
	private String currentCity = "";
	private String serchAddress = "";
	private static int POINT_TYPE = 1;
	private ConnectionServiceThread connServiceThread;
	private ProgressDialog pDialog;
	private int selectPersonNum;
	private ArrayList upPrsonArray;
	
	private Calendar cal = Calendar.getInstance(); 
	private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
		@Override        
		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, monthOfYear);
			cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			updateDate();
			}
		}; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 全屏显示(这两行代码必须放在setContentView方法前) */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.mymap);
		setTitle("map导航");
		r = getResources();
		ll_search = (LinearLayout) findViewById(R.id.ll_sear);

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapController = mapView.getController();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		
		// 判断gps是否开启
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			creatGpsAlertDialog();
		} else {
			myLocation = getLocationProvider(locationManager);
			if (myLocation != null) {
				processLocationUpdated(myLocation);
			} else {
				Toast.makeText(this, "未找到我的位置！", Toast.LENGTH_LONG).show();
			}
			// 监听location更改时事件，更新mapview
			locationManager.requestLocationUpdates(locationProvider, 2000, 10,
					new LocationListener() {
						// location变化时
						public void onLocationChanged(Location location) {
							// TODO Auto-generated method stub
							processLocationUpdated(location);
						}

						// 离开服务范围时
						public void onProviderDisabled(String provider) {
							// TODO Auto-generated method stub

						}

						public void onProviderEnabled(String provider) {
							// TODO Auto-generated method stub

						}

						public void onStatusChanged(String provider,
								int status, Bundle extras) {
							// TODO Auto-generated method stub

						}

					});
		}
		//加载商超地理位置
		//显示等待dialog
        pDialog = new ProgressDialog(this);
        pDialog.setMessage(getResources().getString(R.string.queryMarketPosition));
        pDialog.show();
		//获取到服务端流程名称数据，并装载数据
		HelpHandler helpHandler = new HelpHandler(pDialog,
				this);
		//启动请求服务端线程，封装数据给handler
		connServiceThread = new ConnectionServiceThread(this, 27, helpHandler, "");
		connServiceThread.start();
		
//		Intent intent = getIntent();
//		if (intent != null) {
//			String searchStr = intent.getStringExtra("searchStr");
//			if (searchStr != null) {
//				serchAddress(searchStr);
//			} 
////			else {
////				Toast.makeText(Wiki_Map.this, "请输入要搜索的地名", Toast.LENGTH_LONG)
////						.show();
////			}
//		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return true;
	}

	// 重写 onCreateOptionsMenu 用以创建选项菜单
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// // MenuItem.setTitleCondensed() - 菜单的简标题，如果指定了简标题的话，菜单项上的标题将会以此简标题为准
		// // MenuItem.setAlphabeticShortcut() - 设置选中此菜单项的快捷键
		// // 注：菜单项超过 6 个的话，第 6 个菜单将会变为 More 菜单，多余的菜单会在单击 More 菜单之后显示出来
//		menu.add(0, 0, 0, "更多地点").setIcon(R.drawable.search);
//		menu.add(0, 1, 1, "查找路径").setIcon(R.drawable.root);
//		menu.add(0, 2, 2, "地图视图").setIcon(R.drawable.layers);
//		menu.add(0, 3, 3, "我的位置").setIcon(R.drawable.mylocation);
//		menu.add(0, 4, 4, "我的周边").setIcon(R.drawable.near);
//		menu.add(0, 5, 5, "退出").setIcon(R.drawable.exit);
		menu.add(0, 5, 5, "上传图片位置");
		return true;
	}

	// 重写 onOptionsItemSelected 用以响应选项菜单
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		// 搜索	
		if (item.getItemId() == 0) {
			ll_search.setVisibility(View.VISIBLE);
			et = (EditText) findViewById(R.id.et_serch);
			ibt_search = (ImageButton) findViewById(R.id.imBt_search);
			ibt_search.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!"".equals(et.getText().toString())){
						serchAddress(et.getText().toString());
						ll_search.setVisibility(View.GONE);
					}else{
						Toast.makeText(MyMapActivity.this, "请输入要搜索的地名", Toast.LENGTH_LONG).show();
					}
				}
			});
		}
		// 路线
		if (item.getItemId() == 1) {
			try {
				if (!"".equals(et.getText().toString())) {
					toGeoPoint = getGeoByAddress(serchAddress);
					if (toGeoPoint != null) {
						final CharSequence[] items = { "公交", "步行", "自驾" };
						AlertDialog.Builder builder = new AlertDialog.Builder(
								MyMapActivity.this);
						builder.setTitle("选择交通方式");
						builder.setItems(items,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int item) {
										StringBuilder params = new StringBuilder()
												.append("&dirflg=");
										switch (item) {
										case 0:
											params.append("r");
											break;
										case 1:
											params.append("w");
											break;
										case 2:
											params.append("d");
											break;
										default:
											break;
										}
										getMap(currentGeoPoint, toGeoPoint,
												params.toString());
									}
								});

						AlertDialog alert = builder.create();
						alert.show();
					}else{
						Toast.makeText(MyMapActivity.this, "请先选择目的地",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast
							.makeText(MyMapActivity.this, "请先选择目的地",
									Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(MyMapActivity.this, "请先选择目的地", Toast.LENGTH_SHORT)
						.show();
			}
		}
		// 图层
		if (item.getItemId() == 2) {
			final CharSequence[] items = { "街景视图", "实时路况", "卫星视图" };
			AlertDialog.Builder builder = new AlertDialog.Builder(MyMapActivity.this);
			builder.setTitle("选择地图视图");
			builder.setItems(items, new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int item) {
					// TODO Auto-generated method stub
					switch (item) {
					case 0:
						mapView.setTraffic(false);
						mapView.setSatellite(false);
						mapView.setStreetView(true);
						break;
					case 1:
						mapView.setTraffic(true);
						mapView.setSatellite(false);
						mapView.setStreetView(false);
						break;
					case 2:
						mapView.setTraffic(false);
						mapView.setSatellite(true);
						mapView.setStreetView(false);
						break;

					default:
						break;
					}
				}
			});
			AlertDialog alert = builder.create();
			alert.show();
		}
		// 我的位置
		if (item.getItemId() == 3) {
			try {
				if (!"".equals(currentGeoPoint)) {
					mapController.animateTo(currentGeoPoint);
				} else {
					creatGpsAlertDialog();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 附近	
		if (item.getItemId() == 4) {
			currentCity = getLocalityByGeoPoint(currentGeoPoint);
			if(currentCity.equals("福州市")){
				CharSequence[] items = { "餐厅", "旅馆", "景点" };
				AlertDialog.Builder builder = new AlertDialog.Builder(MyMapActivity.this);
				builder.setTitle("请选择：");
				builder.setItems(items, new DialogInterface.OnClickListener() {
	
					public void onClick(DialogInterface dialog, int item) {
						// TODO Auto-generated method stub
						List<Overlay> overlays = mapView.getOverlays();
						overlays.clear();
						switch (item) {
						case 0:
							final String[] food = { "盛世经典牛排", "斗牛世家牛排", "经典世家牛排",
									"凤凰假日酒店", "阿波罗大酒店", "西湖大酒店", "美海大酒店",
									"海联商务大酒店", "富黎华大酒店", "前景天下大酒店", "山水大酒店",
									"福师粤海酒店", "融侨水乡酒店", "好运来大酒店", "香格里拉大酒店",
									"最佳西方财富酒店","晋都戴斯大酒店","大饭店","美仑华美达大饭店",
									"君越大酒店","闽都大酒店","华威大饭店","闽江饭店","保罗假日大酒店",
									"汉庭快捷酒店","同欣酒店","好运快捷酒店","四季如春旅馆","沐思城市酒店",
									"西湖宾馆","舒馨商务酒店","于山宾馆","凯悦大酒店","天福大酒店",
									"如家快捷酒店","江滨假日大酒店","广达商务酒店"};

							AlertDialog.Builder builder1 = new AlertDialog.Builder(MyMapActivity.this);
							builder1.setTitle("餐厅");
							builder1.setPositiveButton("在地图上全部显示", new DialogInterface.OnClickListener(){

								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									for (int i = 0; i < food.length; i++) {
										getAddressBySerch(mapView, zoomLevel,
												getLocalityByGeoPoint(currentGeoPoint)
														+ food[i], food[i]);
									}
									mapController.animateTo(currentGeoPoint);
								}
								
							});
							builder1.setItems(food, new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog, int foodItem) {
									// TODO Auto-generated method stub
									getAddressBySerch(mapView, zoomLevel,
											getLocalityByGeoPoint(currentGeoPoint)
													+ food[foodItem], food[foodItem]);
								}
							});
							AlertDialog foodDialog = builder1.create();
							foodDialog.show();
							break;
						case 1:
							final String[] hotels = { "凤凰假日酒店", "阿波罗大酒店", "西湖大酒店",
									"美海大酒店", "海联商务大酒店", "富黎华大酒店", "前景天下大酒店",
									"山水大酒店", "福师粤海酒店", "融侨水乡酒店", "好运来大酒店",
									"香格里拉大酒店","最佳西方财富酒店","晋都戴斯大酒店","大饭店","美仑华美达大饭店",
									"君越大酒店","闽都大酒店","华威大饭店","闽江饭店","保罗假日大酒店",
									"汉庭快捷酒店","同欣酒店","好运快捷酒店","四季如春旅馆","沐思城市酒店",
									"西湖宾馆","舒馨商务酒店","于山宾馆","凯悦大酒店","天福大酒店",
									"如家快捷酒店","江滨假日大酒店","广达商务酒店"};
							AlertDialog.Builder builder2 = new AlertDialog.Builder(MyMapActivity.this);
							builder2.setTitle("旅馆");
							builder2.setPositiveButton("在地图上全部显示", new DialogInterface.OnClickListener(){

								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									for (int i = 0; i < hotels.length; i++) {
										getAddressBySerch(mapView, zoomLevel,
												getLocalityByGeoPoint(currentGeoPoint)
														+ hotels[i], hotels[i]);
									}
									mapController.animateTo(currentGeoPoint);
								}
								
							});
							builder2.setItems(hotels, new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog, int hotelItem) {
									// TODO Auto-generated method stub
									getAddressBySerch(mapView, zoomLevel,
											getLocalityByGeoPoint(currentGeoPoint)
													+ hotels[hotelItem], hotels[hotelItem]);
								}
							});
							AlertDialog hotelDialog = builder2.create();
							hotelDialog.show();
							
							break;
						case 2:
							final String[] views = { "西湖公园", "金牛山公园", "左海公园", "国家森林公园",
									"金鸡山公园", "鼓山", "罗星塔", "华林寺", "林则徐纪念馆",
									"旗山国家森林公园", "青云山", "十八重溪", "平潭岛", "永泰天门山",
									"千江月休闲农场","五一广场","三坊七巷","南后街","三叠井","白水洋",
									"鼓山涌泉寺","江滨公园","金山展览城","鼓山十八景公园","永泰天门山",
									"黄楮林温泉","大明谷温泉村","金汤国际温泉","望龙台公园","四乐轩古屋",
									"福清弥勒岩","石竹山"};
							AlertDialog.Builder builder3 = new AlertDialog.Builder(MyMapActivity.this);
							builder3.setTitle("景点");
							builder3.setPositiveButton("在地图上全部显示", new DialogInterface.OnClickListener(){

								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									for (int i = 0; i < views.length; i++) {
										getAddressBySerch(mapView, zoomLevel,
												getLocalityByGeoPoint(currentGeoPoint)
														+ views[i], views[i]);
									}
									mapController.animateTo(currentGeoPoint);
								}
								
							});
							builder3.setItems(views, new DialogInterface.OnClickListener() {
								
								public void onClick(DialogInterface dialog, int viewItem) {
									// TODO Auto-generated method stub
									getAddressBySerch(mapView, zoomLevel,
											getLocalityByGeoPoint(currentGeoPoint)
													+ views[viewItem], views[viewItem]);
								}
							});
							AlertDialog viewDialog = builder3.create();
							viewDialog.show();
							break;
						}
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}else{
				Toast.makeText(MyMapActivity.this, "暂不支持该地区搜索", Toast.LENGTH_LONG).show();
			}
		}
		// 查询有上传图片的地理位置
		if (item.getItemId() == 5) {
//			finish();
			//将list解析为指定数组
			upPrsonArray = connServiceThread.getResultList();
			StringBuffer upPrsonStr = new StringBuffer();
			upPrsonStr.append(getResources().getString(R.string.allPerson));
			upPrsonStr.append(";");
			for (int i = 0; i < upPrsonArray.size(); i++) {
				HashMap map = (HashMap) upPrsonArray.get(i);
				upPrsonStr.append(map.get("name"));
				upPrsonStr.append(";");
			}
			final String[] uppPrsonArray = upPrsonStr.toString().split(";");

			AlertDialog.Builder builder = new AlertDialog.Builder(
					MyMapActivity.this);
			builder.setTitle(getResources().getString(R.string.personChoose));
			builder.setItems(uppPrsonArray,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int item) {
							new DatePickerDialog(MyMapActivity.this,listener,
									cal.get(Calendar.YEAR),
									cal.get(Calendar.MONTH),
									cal.get(Calendar.DAY_OF_MONTH)
									).show(); 
							selectPersonNum = item;
						}
					});
			AlertDialog alert = builder.create();
			alert.show();
		}

		return false;
	}

	private Location getLocationProvider(LocationManager lm) {
		// TODO Auto-generated method stub
		Location retLocation = null;
		try {
			Criteria criteria = new Criteria();
			// 获得最好的定位效果
			criteria.setAccuracy(Criteria.ACCURACY_FINE);// 经纬度是否精确提供
			criteria.setAltitudeRequired(false);// 是否提供高度信息
			criteria.setBearingRequired(false); // 是否提供航向信息
			criteria.setCostAllowed(true);// 费用
			// 使用省电模式
			criteria.setPowerRequirement(Criteria.POWER_LOW);
			locationProvider = lm.getBestProvider(criteria, true);
			retLocation = lm.getLastKnownLocation(locationProvider);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retLocation;
	}

	private void processLocationUpdated(Location location) {
		// TODO Auto-generated method stub
		currentGeoPoint = getGeoPointByLocation(location);
		refreshMapViewByGeoPoint(currentGeoPoint, mapView, zoomLevel, false);
		setMyLocationPoint();
	}

	private GeoPoint getGeoPointByLocation(Location location) {
		GeoPoint gp = null;
		try {
			if (location != null) {
				double geoLat = location.getLatitude() * 1E6;
				double geoLong = location.getLongitude() * 1E6;
				gp = new GeoPoint((int) geoLat, (int) geoLong);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gp;
	}

	private void refreshMapViewByGeoPoint(GeoPoint gp, MapView mv,
			int zoomLevel2, boolean ifSatellite) {
		// TODO Auto-generated method stub
		try {
			mv.displayZoomControls(true);
			// 取得mapview的MapController
			MapController mc = mv.getController();
			// 移至改地理坐标
			mc.animateTo(gp);
			mc.setZoom(zoomLevel2);
			if (ifSatellite) {
				mv.setSatellite(true);
				mv.setStreetView(true);
			} else {
				mv.setSatellite(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getAddressByGeoPoint(GeoPoint gp) {
		String returnAddress = "";
		try {
			if (gp != null) {
				Geocoder gc = new Geocoder(this, Locale.getDefault());
				double geoLat = (int) gp.getLatitudeE6() / 1E6;
				double geoLong = (int) gp.getLongitudeE6() / 1E6;
				List<Address> addresses = gc
						.getFromLocation(geoLat, geoLong, 1);
				StringBuilder sb = new StringBuilder();
				if (addresses.size() > 0) {
					Address adsLocation = addresses.get(0);
					for (int i = 0; i < adsLocation.getMaxAddressLineIndex(); i++) {
						sb.append(adsLocation.getAddressLine(i));
					}
					returnAddress = sb.toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnAddress;
	}

	public String getLocalityByGeoPoint(GeoPoint gp) {
		String retCity = "";
		try {
			if (gp != null) {
				Geocoder gc = new Geocoder(MyMapActivity.this, Locale.getDefault());
				double geoLat = (int) gp.getLatitudeE6() / 1E6;
				double geoLong = (int) gp.getLongitudeE6() / 1E6;
				List<Address> addresses = gc
						.getFromLocation(geoLat, geoLong, 1);
				StringBuilder sb = new StringBuilder();
				if (addresses.size() > 0) {
					Address adsLocation = addresses.get(0);
					sb.append(adsLocation.getLocality());
					// adsLocation.getFeatureName();门牌号
					// adsLocation.getLocality();城市
					retCity = adsLocation.getLocality();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retCity;
	}

	public String getProvinceByGeoPoint(GeoPoint gp) {
		String retProvince = "";
		try {
			if (gp != null) {
				Geocoder gc = new Geocoder(MyMapActivity.this, Locale.getDefault());
				double geoLat = (int) gp.getLatitudeE6() / 1E6;
				double geoLong = (int) gp.getLongitudeE6() / 1E6;
				List<Address> addresses = gc
						.getFromLocation(geoLat, geoLong, 1);
				if (addresses.size() > 0) {
					Address adsLocation = addresses.get(0);
					// sb.append(adsLocation.getAdminArea());

					// adsLocation.getFeatureName();门牌号
					// adsLocation.getLocality();城市
					retProvince = adsLocation.getAdminArea();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retProvince;
	}

	private void setMyLocationPoint() {
		List<Overlay> overlays = mapView.getOverlays();
		MyOverlay myOverlay = new MyOverlay(this, currentGeoPoint, "我的位置",
				Color.BLUE, r, POINT_TYPE);
		overlays.add(myOverlay);

		// List<Overlay> overlays = mapView.getOverlays();
		// MyLocationOverlay myLocationOverlay = new MyLocationOverlay(
		// Wiki_Map.this, mapView);
		// myLocationOverlay.enableCompass();
		// myLocationOverlay.enableMyLocation();
		//
		// overlays.add(myLocationOverlay);

	}

	private boolean getAddressBySerch(MapView mv, int zoomLevel2,
			String serchAddress1, String lable) {
		String msg = "";
		GeoPoint gp = null;
		Geocoder gc = new Geocoder(this);
		// 将地图设为Traffic模式
		mapView.setTraffic(true);
		try {
			// 查询指定地点的地址
			List<Address> addresses = gc.getFromLocationName(serchAddress1, 10);
			List<Overlay> overlays = mapView.getOverlays();
			// overlays.clear();
			setMyLocationPoint();
			// 根据经纬度创建GeoPoint对象
			if (addresses.size() > 0) {
//				msg = "";
				for (int i = 0; i < addresses.size(); i++) {
					gp = new GeoPoint(
							(int) (addresses.get(i).getLatitude() * 1E6),
							(int) (addresses.get(i).getLongitude() * 1E6));
					// 创建MyOverlay对象，用于在地图上绘制图形
					MyOverlay myOverlay = new MyOverlay(this, gp, lable, Color.RED, r, POINT_TYPE);
					// 将MyOverlay对象添加到MapView组件中
					overlays.add(myOverlay);
//					msg += addresses.get(i).getLocality();
//					msg += addresses.get(i).getFeatureName();
//					msg += "\n";
				}
				// 设置地图的初始大小，范围在1和21之间。1：最小尺寸，21：最大尺寸
				MapController mc = mv.getController();
				mc.setZoom(zoomLevel2);
				if (addresses.size() == 1) {
					// 以动画方式进行定位
					mc.animateTo(gp);
				}
				if (addresses.size() > 1) {
					zoomLevel = 13;
					mapController.setZoom(zoomLevel);
					mc.animateTo(gp);
				}

			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	protected GeoPoint getGeoByAddress(String serAddress) {
		// TODO Auto-generated method stub
		GeoPoint gp = null;
		try {
			if (!"".equals(serAddress)) {
				Geocoder geocoder = new Geocoder(this, Locale.getDefault());
				List<Address> address = geocoder.getFromLocationName(
						serAddress, 1);
				if (!address.isEmpty()) {
					Address adsLocation = address.get(0);
					gp = new GeoPoint((int) (adsLocation.getLatitude() * 1E6),
							(int) (adsLocation.getLongitude() * 1E6));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gp;
	}

	protected void getMap(GeoPoint fromgp, GeoPoint togp, String params) {
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setData(Uri.parse("http://maps.google.com/maps?f=d&saddr="
				+ GeoPointToString(fromgp) + "&daddr=" + GeoPointToString(togp)
				+ "+" + params + ""));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				& Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		intent.setClassName("com.google.android.apps.maps",
				"com.google.android.maps.MapsActivity");
		startActivity(intent);
	}

	protected String GeoPointToString(GeoPoint gp) {
		// TODO Auto-generated method stub
		String strReturn = "";
		try {
			if (gp != null) {
				double geoLat = (int) gp.getLatitudeE6() / 1E6;
				double geoLong = (int) gp.getLongitudeE6() / 1E6;
				strReturn = String.valueOf(geoLat) + ","
						+ String.valueOf(geoLong);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strReturn;
	}

	private void creatGpsAlertDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.prompt);
		builder.setMessage(R.string.noOpenGPS);
		builder.setPositiveButton(R.string.GPSsetting,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						startActivity(new Intent(
								"android.settings.LOCATION_SOURCE_SETTINGS"));
					}
				});
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void serchAddress(String serchAddr){
		serchAddress = serchAddr;	
		if (!"".equals(serchAddress)) {
			List<Overlay> overlays = mapView.getOverlays();
			overlays.clear();
			setMyLocationPoint();
			
			String serchAddress1 = "";
			int count = 0;	
			String[] countries = {
					"Andorra","安道尔 ",
					"United Arab Emirates","阿联酋 ",
					"Afghanistan","阿富汗 ",
					"Antigua and Barbuda","安提瓜和巴布达",
					"Anguilla","安格拉",
					"Albania","阿尔巴尼亚",
					"Armenia","亚美尼亚",
					"Netherlands Antilles","荷兰属地",
					"Angola","安哥拉",
					"Argentina","阿根廷",
					"American Samoa","东萨摩亚", 
					"Austria","奥地利",
					"Australia","澳大利亚",
					"Aruba","阿鲁巴",
					"Azerbaijan","阿塞拜疆", 
					"Bosnia Hercegovina","波黑", 
					"Barbados","巴巴多斯", 
					"Bangladesh","孟加拉国", 
					"Belgium","比利时", 
					"Burkina Faso","布基纳法索", 
					"Bulgaria","保加利亚", 
					"Bahrain","巴林",
					"Burundi","布隆迪",
					"Benin","贝宁",
					"Brunei Darussalam","文莱达鲁萨兰国",
					"Bolivia","玻利维亚", 
					"Brazil","巴西",
					"Bahamas","巴哈马",
					"Bhutan","不丹",
					"Botswana","伯兹瓦纳",
					"Belarus","白俄罗斯", 
					"Belize","伯利兹",
					"Canada","加拿大",
					"Cocos Islands","科科斯群岛",
					"Central African Republic","中非共和国", 
					"Congo","刚果", 
					"Switzerland","瑞士",
					"Ivory Coast","象牙海岸",
					"Cook Islands","库克群岛", 
					"Chile"," 智利",
					"Cameroon","喀麦隆",
					"China","中国",
					"Colombia","哥伦比亚",
					"Equatorial Guinea","赤道几内亚", 
					"Costa Rica","哥斯达黎加",
					"Cuba","古巴",
					"Christmas Island","圣诞岛", 
					"Cyprus","塞浦路斯",
					"Czech Republic","捷克共和国 ",
					"Germany","德国",
					"Djibouti","吉布提", 
					"Denmark","丹麦",
					"Dominica","多米尼加联邦", 
					"Dominican Republic","多米尼加共和国", 
					"Algeria","阿尔及利亚",
					"Ecuador","厄瓜多尔", 
					"Estonia","爱沙尼亚",
					"Egypt","埃及", 
					"Western Sahara","西萨摩亚 ",
					"Spain","西班牙", 
					"Ethiopia","埃塞俄比亚",
					"El Salvador","萨尔瓦多", 
					"Finland","芬兰", 
					"Fiji","斐济",
					"Falkland Islands","福克兰群岛",
					"Micronesia","密克罗尼西亚", 
					"Faroe Islands","法罗群岛", 
					"France","法国", 
					"Gobon","加蓬",
					"Great Britain","大不列颠联合王国","UK","英国",
					"Grenada","格林纳达", 
					"Georgia","格鲁吉亚", 
					"French Guiana","法属圭亚那", 
					"Ghana","加纳",
					"Gibraltar","直布罗陀", 
					"Greenland","格陵兰群岛",
					"Gambia","冈比亚", 
					"Guynea","几内亚", 
					"Guadeloupe","瓜德罗普岛", 
					"Greece","希腊", 
					"Guatemala","危地马拉", 
					"Guam","关岛",
					"Guinea-Bissau","几内亚比绍",
					"Guyana","圭亚那", 
					"Honduras","洪都拉斯", 
					"Croatia","克罗蒂亚", 
					"Haiti","海地",
					"Hungary","匈牙利",
					"Indonesia","印度尼西亚", 
					"Ireland","爱尔兰共和国",
					"Israel","以色列", 
					"India","印度",
					"British Indian Ocean Territory","英属印度洋领地", 
					"Iraq","伊拉克", 
					"Iran","伊朗", 
					"Iceland","冰岛", 
					"Italy","意大利",
					"Jamaica","牙买加",
					"Jordan","约旦", 
					"Japan","日本", 
					"Kenya","肯尼亚", 
					"Kyrgyzstan","吉尔吉斯斯坦", 
					"Cambodia","柬埔塞 ",
					"Kiribati","基里巴斯",
					"Comoros","科摩罗", 
					"Korea-North","北朝鲜", 
					"Korea-South","南朝鲜", 
					"Kuwait","科威特",
					"Cayman Islands","开曼群岛", 
					"Kazakhstan","哈萨克斯坦 ",
					"Lao People's Republic","老挝人民共和国", 
					"Lebanon","黎巴嫩",
					"St. Lucia","圣露西亚岛", 
					"Liechtenstein","列支敦士登",
					"Sri Lanka","斯里兰卡", 
					"Liberia","利比里亚",
					"Lesotho","莱索托", 
					"Lithuania","立陶宛",
					"Luxembourg","卢森堡",
					"Latvia","拉脱维亚",
					"Libya","利比亚", 
					"Morocco","摩洛哥", 
					"Monaco","摩纳哥", 
					"Moldova","摩尔多瓦", 
					"Madagascar","马达加斯加", 
					"Marshall Islands","马绍尔群岛",
					"Mali","马里",
					"Myanmar","缅甸",
					"Mongolia","蒙古",
					"Martinique","马提尼克岛", 
					"Mauritania","毛里塔尼亚", 
					"Montserrat","蒙塞拉特岛", 
					"Malta","马尔他", 
					"Maldives","马尔代夫",
					"Malawi","马拉维",
					"Mexico","墨西哥",
					"Malaysia","马来西亚",
					"Mozambique","莫桑比克",
					"Namibia","纳米比亚",
					"New Caledonia","新喀里多尼亚",
					"Niger","尼日尔",
					"Norfolk Island","诺福克岛", 
					"Nigeria","尼日利亚",
					"Nicaragua","尼加拉瓜",
					"Netherlands","荷兰",
					"Norway","挪威",
					"Nepal","尼泊尔", 
					"Nauru","瑙鲁",
					"Niue","纽埃",
					"New Zealand","新西兰",
					"Oman","阿曼", 
					"Panama","巴拿马",
					"Peru","秘鲁", 
					"French Polynesia","法属玻利尼西亚", 
					"Papua New Guinea","巴布亚新几内亚", 
					"Philippines","菲律宾",
					"Pakistan","巴基斯坦",
					"Poland","波兰",
					"Pitcairn Island","皮特克恩岛",
					"Puerto Rico","波多黎各", 
					"Portugal","葡萄牙", 
					"Palau","帕劳",
					"Paraguay","巴拉圭", 
					"Qatar","卡塔尔", 
					"Reunion Island","留尼汪岛", 
					"Romania","罗马尼亚", 
					"Russian Federation","俄罗斯联邦",
					"Rwanda","卢旺达",
					"Saudi Arabia","沙特阿拉伯",
					"Solomon Islands","所罗门群岛", 
					"Seychelles","塞舌尔", 
					"Sudan","苏旦",
					"Sweden","瑞典",
					"Singapore","新加坡",
					"St. Helena","海伦娜", 
					"Slovakia","斯洛伐克", 
					"Sierra Leone","塞拉利昂", 
					"San Marino","圣马力诺", 
					"Senegal","塞内加尔",
					"Somalia","索马里", 
					"Suriname","苏里南",
					"Syrian Arab Republic","叙利亚",
					"Swaziland","斯威士兰",
					"Chad","乍得", 
					"French Southern Territories","法属南半球领地", 
					"Togo","多哥", 
					"Thailand","泰国",
					"Tajikistan","塔吉克斯坦",
					"tokelau","托克劳群岛", 
					"Turkmenistan","土库曼斯坦",
					"Tunisia","突尼斯", 
					"Tonga","汤加", 
					"East Timor","东帝汶", 
					"Turkey","土耳其", 
					"Tuvalu","图瓦鲁", 
					"Tanzania","坦桑尼亚", 
					"Ukrainian SSR","乌克兰",
					"Uganda","乌干达", 
					"United Kingdom","英国", 
					"United States","美国", 
					"Uruguay","乌拉圭", 
					"Vatican City State","梵地冈",
					"Venezuela","委内瑞拉", 
					"Virgin Islands","维京群岛", 
					"Vietnam","越南", 
					"Vanuatu","瓦努阿图", 
					"Yemen","也门", 
					"Yugoslavia","南斯拉夫",
					"South Africa","南非",
					"Zambia","赞比亚", 
					"Zaire","扎伊尔",
					"Zimbabwe","津巴布韦",
			};
			for(int i=0;i<countries.length;i++){
				if (countries[i].equals(serchAddress)) {
					count = 1;
					break;
				}
			}
			if(count == 1){
				serchAddress1 = serchAddress;
				getAddressBySerch(mapView, 5, serchAddress1,
						serchAddress);
			}else{
			String[] citys = {"北京",
					"天津",
					"石家庄","唐山","秦皇岛","邯郸","邢台","保定","张家口","承德","沧州","廊坊","衡水",
					"太原","阳泉","长治","晋城","朔州",
					"呼和浩特","包头","乌海","赤峰","通辽",
					"沈阳","大连","鞍山","抚顺","本溪","丹东","锦州","营口","阜新","辽阳","盘锦","铁岭","朝阳","葫芦岛",
					"长春","吉林","四平","辽源","通化","白山","松原","白城",
					"哈尔滨","齐齐哈尔","鸡西","鹤岗","双鸭山","大庆","伊春","佳木斯","七台河","牡丹江","黑河",
					"上海",
					"南京","无锡","徐州","常州","苏州","南通","连云港","省淮阴","盐城","扬州","镇江","泰州","省宿",
					"杭州","宁波","温州","嘉兴","湖州","绍兴","金华","衢州","舟山","台州",
					"合肥","芜湖","蚌埠","淮南","马鞍山","淮北","铜陵","安庆","黄山","滁州","阜阳","宿州","巢湖","六安",
					"福州","厦门","莆田","三明","泉州","漳州","南平","龙岩",
					"南昌","景德镇","萍乡","九江","新余","鹰潭","赣州" ,
					"济南","青岛","淄博","枣庄","东营","烟台","潍坊","济宁","泰安","威海","日照","莱芜","临沂","德州","聊城" ,
					"郑州","开封","洛阳","平顶山","安阳","鹤壁","新乡","焦作","濮阳","许昌","漯河","三门峡","南阳","商丘","信阳" ,
					"武汉","黄石","十堰","宜昌","襄樊","鄂州","荆门","孝感","荆州","黄冈","咸宁" ,
					"长沙","株洲","湘潭","衡阳","邵阳","岳阳","常德","张家界","益阳","郴州","永州","怀化","娄底" ,
					"广州","韶关","深圳","珠海","汕头","佛山","江门","湛江","茂名","肇庆","惠州","梅州","汕尾","河源","阳江","清远","东莞","中山","潮州","揭阳","云浮", 
					"南宁","柳州","桂林","梧州","北海","防城港","钦州","贵港","玉林", 
					"海口","三亚", 
					"重庆",
					"成都","自贡","攀枝花","泸州","德阳","绵阳","广元","遂宁","内江","乐山","南充","宜宾","广安","达州" ,
					"贵阳","六盘水","遵义" ,
					"昆明","曲靖","玉溪" ,
					"西安","铜川","宝鸡","咸阳","渭南","延安","汉中","榆林" ,
					"兰州","嘉峪关","金昌","白银","天水" ,
					"西宁",
					"银川","石嘴山","吴忠" ,
					"乌鲁木齐","克拉玛依"
					};
				for (int i = 0; i < citys.length; i++) {
					if (citys[i].equals(serchAddress)) {
						count = 1;
						break;
					}
					if (serchAddress.equals(citys[i] + "市")) {
						count = 1;
						break;

					}
				}
				try {
					if (count == 1) {
						serchAddress1 = serchAddress;
						getAddressBySerch(mapView, zoomLevel, serchAddress1,
								serchAddress);
					} else {
						serchAddress1 = getProvinceByGeoPoint(currentGeoPoint)
								+ getLocalityByGeoPoint(currentGeoPoint)
								+ serchAddress;
						if (!getAddressBySerch(mapView, zoomLevel,
								serchAddress1, serchAddress)) {
							serchAddress1 = getProvinceByGeoPoint(currentGeoPoint)
									+ serchAddress;
							if (!getAddressBySerch(mapView, zoomLevel,
									serchAddress1, serchAddress)) {
								serchAddress1 = et.getText().toString();
								if (!getAddressBySerch(mapView, zoomLevel,
										serchAddress1, serchAddress)) {
									Toast.makeText(MyMapActivity.this, "找不到该地方",
											Toast.LENGTH_LONG).show();
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(MyMapActivity.this, "找不到该地方", Toast.LENGTH_LONG)
							.show();
				}

			}
		}
	}
	
    @Override
    public void onPause(){
    	super.onStart();
    	Log.i("funconPause", "funconPause");
    	pDialog.dismiss();
    }
	
	//datepicker设置时间
	private void updateDate(){         
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");         
		String setDate = simpleDateFormat.format(cal.getTime());
		Toast.makeText(MyMapActivity.this, setDate, Toast.LENGTH_LONG)
							.show();
		String params = "";
		if(selectPersonNum != 0){
			HashMap map = (HashMap) upPrsonArray.get(selectPersonNum);
			String selectUserid = (String) map.get("usercode");
			params = setDate + ";" + selectUserid;
		}else {
			params = setDate;
		}
		
		//显示等待dialog
		pDialog = ProgressDialog.show(
				this, 
				getResources().getString(R.string.nowloading), 
				getResources().getString(R.string.pleasewait), 
				true, true);	
		
		//获取到服务端流程名称数据，并装载数据
		HelpHandler helpHandler = new HelpHandler(pDialog,
				this);
		//启动请求服务端线程，封装数据给handler
		ConnectionServiceThread connServiceThread = new ConnectionServiceThread(this, 14, helpHandler, params);
		connServiceThread.start();
	} 
}