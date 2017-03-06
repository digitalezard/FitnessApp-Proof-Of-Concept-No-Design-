package com.supinfo.fitnessapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.supinfo.fitnessapplication.DAO.LocateDao;
import com.supinfo.fitnessapplication.DAO.ParcourDao;
import com.supinfo.fitnessapplication.Models.Locate;
import com.supinfo.fitnessapplication.Models.Parcour;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UMapActivity extends AppCompatActivity {

    private Intent intent;
    private Context context;
    private String EXTRA_ID = "userId";
    boolean towrite = false;
    private int ParcourId, UserId;
    private List<Parcour> userParcours;

    private GoogleMap googleMap;
    private NumberPicker PickerParcour;
    private Button Start, Stop, SeeOther, Return;

    private LocateDao lDataSource;
    private ParcourDao pDataSource;

    private AlertDialog alertDialog;
    private Polyline line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_umap);

        context = this;
        pDataSource = new ParcourDao(this);
        lDataSource = new LocateDao(this);

        pDataSource.open();
        lDataSource.open();

        googleMap =((MapFragment) getFragmentManager().findFragmentById(
                R.id.umap)).getMap();

        alertDialog = new AlertDialog.Builder(context).create();
        Start = (Button) findViewById(R.id.ButtonStartRecord);
        Stop = (Button) findViewById(R.id.ButtonStopRecord);
        Return = (Button) findViewById(R.id.ReturnUM);

        SeeOther = (Button) findViewById(R.id.ButtonSeeOther);
        Stop.setEnabled(false);
        Start.setEnabled(true);

        PickerParcour = new NumberPicker(UMapActivity.this);
        PickerParcour.setMinValue(1);
        PickerParcour.setValue(1);



        intent = getIntent();
        if(intent != null){
            UserId = intent.getIntExtra(EXTRA_ID, 0);
        }

        try {
            // Loading map
            initializeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(UMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(UMapActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, new android.location.LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (towrite == true) {
                    lDataSource.open();
                    try {
                        long time = System.currentTimeMillis();
                        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                        cal.setTimeInMillis(time);
                        Date date = cal.getTime();

                        Locate currentLocate = new Locate();
                        currentLocate.setParcourId(ParcourId);
                        currentLocate.setLattitude(location.getLatitude());
                        currentLocate.setLongitude(location.getLongitude());
                        currentLocate.setDate(date);

                        lDataSource.createLocate(currentLocate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    try {
                        Parcour thatParcour =pDataSource.getParcour(ParcourId);
                        makePolyline(thatParcour);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {

                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {
            }

            @Override
            public void onProviderDisabled(String provider) {
            }});

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Parcour newParcour = new Parcour();
                newParcour.setUserId(UserId);
                newParcour.setName("ThatParcour");
                Parcour myParcour = pDataSource.createParcour(newParcour);
                ParcourId = myParcour.getId();
                towrite = true;
                Start.setEnabled(false);
                Stop.setEnabled(true);
            }
        });

        Stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Start.setEnabled(true);
                Stop.setEnabled(false);
                towrite=false;
            }
        });

        SeeOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.setTitle("Session");
                userParcours= pDataSource.getParcoursByUserId(UserId);
                int numberParcour = userParcours.size();
                if(numberParcour > 0) {
                    PickerParcour.setMaxValue(numberParcour);
                }else{
                    PickerParcour.setMaxValue(1);
                }
                alertDialog.setView(PickerParcour);

                alertDialog.setButton("Go To", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Parcour selectedParcour = userParcours.get(PickerParcour.getValue()-1);
                        lDataSource.open();
                        try {
                            makePolyline(selectedParcour);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                });
                alertDialog.show();
            }
        });

        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(UMapActivity.this, WelcomeActivity.class);
                myIntent.putExtra(EXTRA_ID, UserId);
                pDataSource.close();
                lDataSource.close();
                startActivity(myIntent);
            }
        });
    }



    private void initializeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.umap)).getMap();

            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        if (googleMap != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.setMyLocationEnabled(true);
        }
    }

    public void makePolyline(Parcour parcour) throws ParseException {
        if(line != null){
            line.remove();
        }
        List<Locate> locationList = lDataSource.getAllLocatesById(parcour.getId());

        PolylineOptions options = new PolylineOptions();
        options.color(Color.parseColor("#CC0000FF"));
        options.width(5);
        options.visible(true);
        for (int i=0; i < locationList.size(); i++)
        {
            options.add( new LatLng( locationList.get(i).getLattitude(),
                    locationList.get(i).getLongitude()));
        }
       line = googleMap.addPolyline(options);
    }



}
