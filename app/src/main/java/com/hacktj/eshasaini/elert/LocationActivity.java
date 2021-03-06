/**
 * Copyright 2014 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hacktj.eshasaini.elert;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import android.support.v4.content.ContextCompat;
import android.Manifest;
import android.view.Menu;
import android.view.View;
import android.content.Intent;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.widget.Button;
/**t
 * Location sample.
 *
 * Demonstrates use of the Location API to retrieve the last known location for a device.
 * This sample uses Google Play services (GoogleApiClient) but does not need to authenticate a user.
 * See https://github.com/googlesamples/android-google-accounts/tree/master/QuickStart if you are
 * also using APIs that need authentication.
 */
public class LocationActivity extends AppCompatActivity implements
        ConnectionCallbacks, OnConnectionFailedListener {


    protected static final String TAG = "MainActivity";

    /**
     * Provides the entry point to Google Play services.
     */
    protected GoogleApiClient mGoogleApiClient;

    /**
     * Represents a geographical location.
     */
    protected Location mLastLocation;

    protected String mLatitudeLabel;
    protected String mLongitudeLabel;
    protected TextView mLatitudeText;
    protected TextView mLongitudeText;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mLatitudeLabel = getResources().getString(R.string.latitude_label);
        mLongitudeLabel = getResources().getString(R.string.longitude_label);
        mLatitudeText = (TextView) findViewById((R.id.latitude_text));
        mLongitudeText = (TextView) findViewById((R.id.longitude_text));
//        Intent intent = getIntent();

        buildGoogleApiClient();
        Button John_button = (Button) findViewById(R.id.JohnS);
        Button Ivon_button = (Button) findViewById(R.id.IvonG);
        Button Jim_button = (Button) findViewById(R.id.JimL);
        Button Kia_button = (Button) findViewById(R.id.KiaB);
        final Button Status_button = (Button) findViewById(R.id.status);
        final TextView You_text = (TextView) findViewById(R.id.You);

        John_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(LocationActivity.this, SecondActivity.class);
                String[] person1 = {"John Smith", "40.713° N, 74.006° W"}; //New York
                myIntent.putExtra("1", person1);
                myIntent.putExtra("2", -1);
                startActivity(myIntent);
            }
        });
        Ivon_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent myIntent2 = new Intent(LocationActivity.this, SecondActivity.class);
                String[] person2 = {"Ivon Glosar", "35.683° N, 139.683° E"}; //Tokyo
                myIntent2.putExtra("1", person2);
                myIntent2.putExtra("2", 1);
                startActivity(myIntent2);
            }
        });
        Jim_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent myIntent3 = new Intent(LocationActivity.this, SecondActivity.class);
                String[] person3 = {"Jim Lochear", "32.715° N, 117.163° W"}; // San Diego
                myIntent3.putExtra("1", person3);
                myIntent3.putExtra("2", 1);
                startActivity(myIntent3);
            }
        });
        Kia_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent myIntent4 = new Intent(LocationActivity.this, SecondActivity.class);
                String[] person4 = {"Kia Boërd", "34.050° N, 118.250° W"};
                myIntent4.putExtra("1", person4);
                myIntent4.putExtra("2", 0);
                startActivity(myIntent4);
            }
        });
        Status_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(Status_button.getText().equals("SOS")) {
                    Status_button.setText("SAFE");
                    You_text.setBackgroundColor(Color.RED);
                }
                else if(Status_button.getText().equals("SAFE")) {
                    Status_button.setText("SOS");
                    You_text.setBackgroundColor(Color.GREEN);
                }
            }
        });
     //   Intent intent = getIntent();

    }



    /**
     * Builds a GoogleApiClient. Uses the addApi() method to request the LocationServices API.
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        int permission= ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION);
       if(permission == PackageManager.PERMISSION_DENIED){
           throw new RuntimeException("need permission");
       }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText.setText(String.format("%s: %f", mLatitudeLabel,
                    mLastLocation.getLatitude()));
            mLongitudeText.setText(String.format("%s: %f", mLongitudeLabel,
                    mLastLocation.getLongitude()));
        } else {
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }


    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }


}