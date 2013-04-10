package com.flutesa.gpsforpapa;

import android.app.Activity;
import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


public class Gps extends Activity implements LocationListener {

	private TextView current_coordinates, goal_coordinates, distance, bearing, altitude, accuracy;
	private String[] c_latitude, c_longitude, g_latitude, g_longitude;
	private String c_lat, c_lon, g_lat, g_lon;
	private float bear;
	private double dist, lat, lon;
	private Location destin;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_gps);
		
		current_coordinates = (TextView) findViewById(R.id.current_coordinates);
		goal_coordinates = (TextView) findViewById(R.id.goal_coordinates);
		altitude = (TextView) findViewById(R.id.altitude);
		accuracy = (TextView) findViewById(R.id.accuracy);
		distance = (TextView) findViewById(R.id.distance);
		bearing = (TextView) findViewById(R.id.bearing);

		final LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
	}
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}
		            
	@Override
	public void onProviderEnabled(String provider) {}
		            
	@Override
	public void onProviderDisabled(String provider) {}
		            
	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			g_lat = "55:37:21";
			g_lon = "55:37:21,00";
					
			c_latitude = location.convert(location.getLatitude(), 2).split(","); //FORMAT_SECONDS
			c_longitude = location.convert(location.getLongitude(), 2).split(",");
			g_latitude = g_lat.split(",");
			g_longitude = g_lat.split(",");
			
			current_coordinates.setText(c_latitude[0] + " Ш\n" + c_longitude[0] + " Д"); //текущие координаты
			goal_coordinates.setText(g_latitude[0] + "\n" + g_longitude[0]); //координаты цели
			altitude.setText(String.valueOf((int)location.getAltitude()) + " м"); //текущая высота над уровнем моря
			accuracy.setText(String.valueOf((int)location.getAccuracy()) + " м"); //точность локации
			
			
			destin = new Location("destin");
			lat = 60.0;
			lon = 58.0;
			destin.setLatitude(lat);
			destin.setLongitude(lon);
			dist = location.distanceTo(destin)/1e3;
			
			distance.setText(String.valueOf(dist) + " км"); //расстояние до цели
			
			bear = location.bearingTo(destin);
			bearing.setText(String.valueOf((int)bear) + " *"); //расстояние до цели
					
		} else {
			 current_coordinates.setText("Координаты не найдены!"); //Toast.makeText(this, "Coordinates is not found", Toast.LENGTH_SHORT).show();
			   }
		    }


}

//lat = g_lat * 1000000;
//lon = g_lon * 1000000;


//di = location.distanceBetween(c_latitude, c_longitude, g_latitude, g_longitude, results[1]);

