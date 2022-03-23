package com.hos.managastore;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.hos.managastore.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    mylocationlistener myloc;
    LocationManager locationManager;
    TextView location;
    TextView totalPrice;
    TextView prodName;
    Button btn;
    DB database;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        myloc=new mylocationlistener(getApplicationContext());
        database=new DB(getApplicationContext());
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try {
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,6000,0,myloc);
        }catch (SecurityException ex)
        {
            Toast.makeText(getApplicationContext(),"not allowed access current location",Toast.LENGTH_SHORT).show();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960,31.235711600),8));
        btn=(Button)findViewById(R.id.map_btn);
        location=(TextView)findViewById(R.id.location_by_map);
        prodName=(TextView)findViewById(R.id.nameProduct);
        totalPrice=(TextView)findViewById(R.id.totalPrice);


        Intent intent=getIntent();
        String customerID=intent.getStringExtra("customerID");
        String prodID=intent.getStringExtra("ProdID");
        String nameOfProd=intent.getStringExtra("nameOfProd");
        int quantity=intent.getIntExtra("quantity",0);
        int Totalquantity=intent.getIntExtra("Totalquantity",0);
        int total=intent.getIntExtra("total",0);

        prodName.setText("Product Name: "+nameOfProd);
        totalPrice.setText("Total Price :"+total+"$");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                Geocoder geocoder=new Geocoder(getApplicationContext());
                List<Address> addressList;
                Location loc=null;
                try {
                    loc=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
                catch (SecurityException ex)
                {
                    Toast.makeText(getApplicationContext(),"Not allowed access current location, " +
                            "Edit application permissions",Toast.LENGTH_SHORT).show();
                }
                if(loc!=null)
                {
                    LatLng myposition=new LatLng(loc.getLatitude(),loc.getLongitude());
                    try
                    {
                        addressList=geocoder.getFromLocation(myposition.latitude,myposition.longitude,1);
                        if(!addressList.isEmpty())
                        {
                            String address="";
                            for(int i=0;i<=addressList.get(0).getMaxAddressLineIndex();i++)
                            {
                                address+=addressList.get(0).getAddressLine(i)+",";
                                mMap.addMarker(new MarkerOptions().position(myposition).title("My location").snippet(address)).setDraggable(true);
                                location.setText(address);
                            }
                            database.insertOrder(address,Integer.valueOf(customerID));
                            Cursor cursor=database.getOrderId(address,customerID);
                            if(cursor!=null){
                                while (!cursor.isAfterLast()){
                                    cursor.moveToNext();
                                }
                                cursor.moveToPrevious();
                                int orderId=cursor.getInt(0);
                                database.insertOrderDetils(Integer.valueOf(prodID),String.valueOf(quantity),String.valueOf(total),orderId);
                                database.updateQuantity(prodID,Totalquantity);
                                Toast.makeText(getApplicationContext(),"Order added Succesfully",Toast.LENGTH_SHORT).show();
                                Intent intent1=new Intent(MapsActivity.this,CategoryView.class);
                                intent1.putExtra("CusID",customerID);
                                startActivity(intent1);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Faild",Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        //mMap.addMarker(new MarkerOptions().position(myposition).title("My location"));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myposition,15));
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please wait until your position is determine",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener()
        {
            @Override
            public void onMarkerDrag(@NonNull Marker marker) {
                location=(TextView)findViewById(R.id.location_by_map);
                Geocoder coder=new Geocoder(getApplicationContext());
                List<Address>addressList;
                try
                {
                    addressList=coder.getFromLocation(marker.getPosition().latitude,marker.getPosition().longitude,1);
                    if(!addressList.isEmpty())
                    {
                        String address="";
                        for(int i=0;i<=addressList.get(0).getMaxAddressLineIndex();i++)
                        {
                            address+=addressList.get(0).getAddressLine(i)+",";
                            location.setText(address);
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"No address for this location",Toast.LENGTH_SHORT).show();
                        location.getText();
                    }
                }
                catch (IOException ex)
                {
                    Toast.makeText(getApplicationContext(),"can't get your address,check your network",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMarkerDragEnd(@NonNull Marker marker) {

            }

            @Override
            public void onMarkerDragStart(@NonNull Marker marker) {

            }
        });



    }
}