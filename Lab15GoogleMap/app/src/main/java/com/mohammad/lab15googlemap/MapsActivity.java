package com.mohammad.lab15googlemap;

import android.content.Context;


import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mohammad.lab15googlemap.database.LocationCursorWraper;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener {

    private static final String TAG = "WhereAmI";
    public Marker whereAmI;
    private LocationManager locationManager;
    private Manager manager;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Button btnHistory, btnAddLocation, btnSavedLocations, btnTrack;
    private HistoryActivity historyActivity;
    private boolean track = true;
    private Location location;
    private boolean fromOtherPage = true;
    private boolean isClicked = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);






        btnHistory = (Button) findViewById(R.id.btnHistory);
        btnSavedLocations = (Button) findViewById(R.id.btnSaved);
        btnAddLocation = (Button) findViewById(R.id.btnAdd);
        btnTrack = (Button) findViewById(R.id.btnTrack);

        setUpMapIfNeeded();
        mMap.setOnMyLocationButtonClickListener(this);
        manager = Manager.getManager(this);
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(serviceName);


        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);

        String provider = locationManager.getBestProvider(criteria, false);
        location = getLastKnownLocation();

        LatLng latLng = fromLocationToLatLng(location);



        //ZOOM IN
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));




        locationManager.requestLocationUpdates(provider, 1000, 10, locationListener);

        String lt = this.getIntent().getStringExtra("latTag");
        String ln = this.getIntent().getStringExtra("lngTag");
        String add = this.getIntent().getStringExtra("addTag");

        track = this.getIntent().getBooleanExtra("tracker",false);
        fromOtherPage = this.getIntent().getBooleanExtra("yes",true);




        if(fromOtherPage == false)
        {
            LatLng ltlg = new LatLng(Double.parseDouble(lt), Double.parseDouble(ln));
            whereAmI = mMap.addMarker
                    (new MarkerOptions().position(ltlg)
                            .icon(BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title(add));
            Log.d("taggggggg", "-------------------------------------greeen--------------------------");

            if (track == true) {

                btnTrack.setText("tracker on");





            } else {




                btnTrack.setText("tracker off");


            }
        }
        else
        {

            updateWithNewLocation(location);
        }


        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MapsActivity.this, HistoryActivity.class);
                i.putExtra("tracker", track);
                startActivity(i);


            }
        });
     btnSavedLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MapsActivity.this, SavedActivity.class);

                i.putExtra("tracker", track);
                startActivity(i);


            }
        });


        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = updateWithNewLocation(location);
                OurLocation ol = new OurLocation(location.getLatitude() + "", location.getLongitude() + ""
                        , address);

                manager.addOurLocation(ol);
                Toast.makeText(MapsActivity.this, "User Added" + address, Toast.LENGTH_SHORT).show();
            }
        });

        btnTrack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if (track == true) {

                track = false;

                btnTrack.setText("tracker off");



        } else {


            track = true;

            btnTrack.setText("tracker on");


        }
    }
});



    }

    private Location getLastKnownLocation() {
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location location = locationManager.getLastKnownLocation(provider);

            if (location == null) {

                continue;
            }
            if (bestLocation == null || location.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = location;
            }
        }
        if (bestLocation == null) {
            return null;
        }
        return bestLocation;
    }
    private LatLng fromLocationToLatLng(Location location) {

        return new LatLng(location.getLatitude(), location.getLongitude());
    }


    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            updateWithNewLocation(location);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };


    private String updateWithNewLocation(Location location) {



        String latLongString = "No location found";
        String addressString = "no address foud";

        if (location != null) {
            LatLng latLng = fromLocationToLatLng(location);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
            if (whereAmI != null) {
                whereAmI.remove();

            }



                whereAmI = mMap.addMarker
                        (new MarkerOptions().position(latLng)
                                .icon(BitmapDescriptorFactory
                                        .defaultMarker(BitmapDescriptorFactory.HUE_CYAN)).title("You Are Here"));
                Log.d("taggggggg", "-------------------------------------bluuuuuuuuuee--------------------------");








            latLongString = "Lat:" + location.getLatitude() + "\n" + "Long:" + location.getLongitude();

            double latitude = location.getLatitude();
            double longTude = location.getLongitude();

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            if (!Geocoder.isPresent()) {
                addressString = "No geocoder availabe";
            } else {
                try {

                    List<Address> addresses = geocoder.getFromLocation(latitude, longTude, 1);

                    StringBuilder sb = new StringBuilder();
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)).append("\n");
                        }
                        sb.append(address.getLocality()).append("\n");
                        sb.append(address.getPostalCode()).append("\n");
                        sb.append(address.getCountryName());
                    }
                    addressString = sb.toString();

                    if (track == true) {


                            Toast.makeText(MapsActivity.this, "Location Tracked", Toast.LENGTH_SHORT).show();

                            OurLocation ol = new OurLocation(latitude + "", longTude + "", addressString, new Date());
                            manager.addHistory(ol);


                    }






                } catch (IOException ioe) {
                    Log.d(TAG, "IO EXEPTION", ioe);
                }
            }

        }




        return addressString;
    }



    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMyLocationEnabled(true);
    }


    @Override
    public boolean onMyLocationButtonClick() {


        Log.d("myloc", "--------------------------------------------------------**********************************************");

        if (whereAmI != null) {
            whereAmI.remove();

        }
        fromOtherPage = true;

        return false;
    }



}



