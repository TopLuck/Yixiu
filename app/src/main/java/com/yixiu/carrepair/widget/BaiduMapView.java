package com.yixiu.carrepair.widget;

/**
 * Created by liuchengran on 2017/8/21.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.yixiu.carrepair.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Liuchengran
 *
 */
public class BaiduMapView extends LinearLayout implements BaiduMap.OnMarkerClickListener {
    private MapView mapView = null;
    private BaiduMap baiduMap = null;
    private Context context;

    private LocationClient locClient;
    private MyLocationListener locListener = new MyLocationListener();
    private BitmapDescriptor currentMarker;

    private ArrayList<Marker> arrayMarker = new ArrayList<>();
    private View markerView;
    private TextView markerTitle;
    private String TAG = "BaiduMapView";


    private MyLocationConfiguration.LocationMode locationMode = MyLocationConfiguration.LocationMode.FOLLOWING;

    public BaiduMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaiduMapView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        SDKInitializer.initialize(context.getApplicationContext());
        View.inflate(context, R.layout.view_baidu_map, this);
        mapView = (MapView)findViewById(R.id.bmapView);

        markerView = View.inflate(context, R.layout.view_map_marker, null);
        markerTitle = (TextView)markerView.findViewById(R.id.mark_title);

        baiduMap = mapView.getMap();
        //开启定位图层
        baiduMap.setMyLocationEnabled(true);
        baiduMap.setOnMarkerClickListener(this);
        locClient = new LocationClient(context.getApplicationContext());
        locClient.registerLocationListener(locListener);


        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(13);
        baiduMap.animateMapStatus(msu);

        initLocation();
        locClient.start();

    }

    public void setLocationFollowMode(MyLocationConfiguration.LocationMode locationMode) {
        this.locationMode = locationMode;
    }

   /* public Marker addMarker(FSU fsu) {
        //构建Marker图标
        LatLng point = new LatLng(fsu.getLat(), fsu.getLng());

        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(getMarkerBitmap(fsu.getName()));
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker，并显示
        Marker marker = (Marker) baiduMap.addOverlay(option);

        Bundle bundle = new Bundle();
        bundle.putSerializable("FSU", fsu);
        marker.setExtraInfo(bundle);
        arrayMarker.add(marker);

        return marker;
    }*/

    public void removeAllMarker() {
        for (int i = 0; i < arrayMarker.size(); i++) {
            arrayMarker.get(i).remove();
        }
    }

    public void onResume() {
        mapView.onResume();
    }

    public void onPause() {
        mapView.onPause();
    }

    public void onDestroy() {
        mapView.onDestroy();

        locClient.stop();
        locClient.unRegisterLocationListener(locListener);
    }

    public void goToRegion(Region region) {
        GeoCoder coder = GeoCoder.newInstance();
        coder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {

            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {

            }

            public void onGetGeoCodeResult(GeoCodeResult arg0) {
                if (arg0.error ==  SearchResult.ERRORNO.NO_ERROR) {
                    MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(arg0.getLocation());
                    baiduMap.animateMapStatus(msu);
                } else {
                    Log.e(TAG, "检索失败");
                }

            }
        });
        coder.geocode(new GeoCodeOption().city("长沙").address("岳麓区"));
    }

    private void showCurrentPos(BDLocation location) {
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100)
                .latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        // 设置定位数据
        baiduMap.setMyLocationData(locData);
        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        if (currentMarker == null) {
            currentMarker = BitmapDescriptorFactory
                    .fromResource(R.drawable.locate);
        }

        MyLocationConfiguration config = new MyLocationConfiguration(locationMode,
                true,
                currentMarker);
        baiduMap.setMyLocationConfigeration(config);
	    /*LatLng ll = new LatLng(location.getLatitude(),
	    		location.getLongitude());
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(ll);
        baiduMap.animateMapStatus(msu);  */


       /* Message locMsg = new Message();
        locMsg.what = MainActivity.MSG_LOCATION_SUCESS;
        locMsg.obj = location;
        QApplication.getInstance().mainHandler.sendMessage(locMsg);*/
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000*60;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        locClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
                showCurrentPos(location);
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");

                showCurrentPos(location);
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
        }
    }
    /*  @param arg0
    /*  @return
     *
     */
    public boolean onMarkerClick(Marker marker) {
    /*    FSU fsu = (FSU) marker.getExtraInfo().getSerializable("FSU");
        Intent intent = new Intent(context, FSUActivity.class);
        intent.putExtra("FSU", fsu);
        context.startActivity(intent);*/
        return true;
    }

    private Bitmap getMarkerBitmap(String marker) {
        markerTitle.setText(marker);
        markerView.setDrawingCacheEnabled(true);
        markerView.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        markerView.layout(0, 0,
                markerView.getMeasuredWidth(),
                markerView.getMeasuredHeight());

        markerView.buildDrawingCache();
        Bitmap cacheBitmap = markerView.getDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);
        markerView.destroyDrawingCache();
        return bitmap;
    }
}

