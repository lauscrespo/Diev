package com.diev.aplicacion.diev.listener;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class GeoLocationListener implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Context context;
    private GenericListener<Location> onLocationReady;

    private boolean getLocationUpdates;
    private LocationRequest mLocationRequest;

    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 5000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    public GeoLocationListener(Context context, GenericListener onLocationReady) {
        this.context = context;
        this.onLocationReady = onLocationReady;
        getLocationUpdates = false;
        buildGoogleApiClient();
    }

    public GeoLocationListener(Context context, GenericListener onLocationReady, boolean getLocationUpdates) {
        this.context = context;
        this.onLocationReady = onLocationReady;
        this.getLocationUpdates = getLocationUpdates;
        buildGoogleApiClient();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        if(getLocationUpdates)
            createLocationRequest();

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i("com.diev.aplication", "Connected");

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if(mLastLocation != null && !getLocationUpdates && onLocationReady != null) {

            onLocationReady.action(mLastLocation);
        }

        if(getLocationUpdates){
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("com.diev.aplication", "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    public void start() {
        mGoogleApiClient.connect();
    }

    public void stop() {
        mGoogleApiClient.disconnect();
    }


    public void checkGpsStatus(){
        LocationManager service = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(
                    "The GPS is off, you have ti activate it")
                    .setCancelable(false)
                    .setPositiveButton("Activate GPS",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    Intent gpsOptionsIntent = new Intent(
                                            android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    context.startActivity(gpsOptionsIntent);
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }



    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();

        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {

        if(location != null && onLocationReady != null) {
            mLastLocation = location;
            onLocationReady.action(mLastLocation);
        }
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    public void resume() {
        if (mGoogleApiClient.isConnected() && getLocationUpdates) {
            startLocationUpdates();
        }
    }

    public void pause(){
        if (getLocationUpdates && mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    public Location getLastLocation(){
        return mLastLocation;
    }
}
