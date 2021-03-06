//package com.anzela.myapplication1.Activity;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.drawerlayout.widget.DrawerLayout;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import android.app.Dialog;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.Settings;
//import android.text.SpannableString;
//import android.text.style.UnderlineSpan;
//import android.view.Gravity;
//import android.view.View;
//import android.view.Window;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.anzela.myapplication1.ApiExplorer;
//import com.anzela.myapplication1.GpsTracker;
//import com.anzela.myapplication1.HttpConnection;
//import com.anzela.myapplication1.NearAdapter;
//import com.anzela.myapplication1.NearData;
//import com.anzela.myapplication1.Post;
//import com.anzela.myapplication1.R;
//import com.anzela.myapplication1.UpcomingAdapter;
//import com.anzela.myapplication1.UpcomingData;
//import com.google.android.material.navigation.NavigationView;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import android.Manifest;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.content.pm.PackageManager;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.LocationManager;
//import androidx.annotation.NonNull;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import android.util.Log;
//import android.widget.Toast;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//import java.util.concurrent.Executors;
//
//public class MainActivity extends AppCompatActivity {
//
//    private GpsTracker gpsTracker;
//    private ApiExplorer apiExplorer;
//
//    private ArrayList<UpcomingData> uparrayList;
//    private UpcomingAdapter upAdapter;
//    private RecyclerView uprecyclerView;
//    private LinearLayoutManager uplinearLayoutManager;
//
//    private ArrayList<NearData> neararrayList;
//    private NearAdapter nearAdapter;
//    private RecyclerView nearrecyclerView;
//    private LinearLayoutManager nearlinearLayoutManager;
//
//    boolean around = false;
//
//    DrawerLayout drawerLayout;
//    NavigationView navigationView;
//    ImageView inButton;
//    ImageView backButton;
//    ImageView moreButton;
//    ImageView noticeButton;
//    ImageView userImg;
//    TextView fullmeetingBtn;
//    TextView nearmeetingBtn;
//    TextView schedulemeetingBtn;
//    TextView weatherInt;
//    Dialog permissionDialog;
//    RelativeLayout userBox;
//
//    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
//    private static final int PERMISSIONS_REQUEST_CODE = 100;
//    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.main);
//
//        drawerLayout = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.nav_view);
//        inButton = findViewById(R.id.open_menu);
//        backButton = findViewById(R.id.close_menu);
//        fullmeetingBtn = findViewById(R.id.full_meeting);
//        nearmeetingBtn = findViewById(R.id.near_meeting);
//        schedulemeetingBtn = findViewById(R.id.schedule_meeting);
//        moreButton = findViewById(R.id.moreBtn);
//        weatherInt = findViewById(R.id.weather_temperature);
//        noticeButton = findViewById(R.id.notice);
//        userImg = findViewById(R.id.userimg);
//        userBox = findViewById(R.id.userbox);
//
//        uprecyclerView = (RecyclerView)findViewById(R.id.comingrv);
//        uplinearLayoutManager = new LinearLayoutManager(this);
//        uprecyclerView.setLayoutManager(uplinearLayoutManager);
//        uparrayList = new ArrayList<>();
//        upAdapter = new UpcomingAdapter(uparrayList);
//        uprecyclerView.setAdapter(upAdapter);
//
//        nearrecyclerView = (RecyclerView)findViewById(R.id.nearrv);
//        nearlinearLayoutManager = new LinearLayoutManager(this);
//        nearrecyclerView.setLayoutManager(nearlinearLayoutManager);
//        neararrayList = new ArrayList<>();
//        nearAdapter = new NearAdapter(neararrayList);
//        nearrecyclerView.setAdapter(nearAdapter);
//
//        permissionDialog = new Dialog(MainActivity.this);
//        permissionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        permissionDialog.setContentView(R.layout.permissiondialog);
//
//        long now = System.currentTimeMillis();
//        Date date = new Date(now);
//        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");
//        int getDate = Integer.parseInt(simpleDate.format(date));
//
//        // ??????
//        Thread thread1 = new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    HttpConnection http = new HttpConnection();
//                    ArrayList<Post> post = http.getServerSoon(1, getDate);
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            setUpInfo(post);
//                            upAdapter.notifyDataSetChanged();
//                        }
//                    });
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        },thread2= new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    gpsTracker = new GpsTracker(MainActivity.this);
//
//                    double latitude = gpsTracker.getLatitude();
//                    double longitude = gpsTracker.getLongitude();
//                    HttpConnection http = new HttpConnection();
//                    ArrayList<Post> post2 = http.getServerAround(1, latitude ,longitude);
//
////                    http.getServer(1);
////                    http.getServerDetail(1);
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
////                            NearsetUpInfo(post2);
////                            nearAdapter.notifyDataSetChanged();
//                        }
//                    });
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        };
//        thread1.start();
//        thread2.start();
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    apiExplorer = new ApiExplorer();
//                    apiExplorer.lookupWeather();
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            weatherInt.setText(apiExplorer.getResult());
//                        }
//                    });
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//        });
//        // ???????????? ????????? ??????
////        Executors.newSingleThreadExecutor().execute(new Runnable() {
////            @Override
////            public void run() {
////                try {
////                    http = new HttpConnection();
////                    http.getServerSoon(1, 20210714);
////
////                    runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            setUpAdapter();
////                            setUpInfo((ArrayList<Post>) http.getPostSoon());
////                        }
////                    });
////                }catch (Exception e){
////                    e.printStackTrace();
////                }
////            }
////        });
//        // ??? ?????? ?????????
////        Executors.newSingleThreadExecutor().execute(new Runnable() {
////            @Override
////            public void run() {
////                try {
////                    http = new HttpConnection();
////                    http.getServerAround(1, 37.49 ,126.83);
////
//////                    http.getServer(1);
//////                    http.getServerDetail(1);
////
////                    runOnUiThread(new Runnable() {
////                        @Override
////                        public void run() {
////                            NearsetUpAdapter();
////                            NearsetUpInfo((ArrayList<Post>) http.getPostAround());
////                        }
////                    });
////                }catch (Exception e){
////                    e.printStackTrace();
////                }
////            }
////        });
//
//        if(!checkLocationServicesStatus()){ // ?????? gps ????????? ?????? , ???????????? ???????????? ??????
//            showDialogForLocationServiceSetting(); // ?????? gps ???????????? ?????? Dialog
//        }else{ // ?????? gps ????????? ?????? , ????????? ???????????? ??????
//            checkRunTimePermission();
//        }
//
//        final TextView textview_address = (TextView)findViewById(R.id.adresstext);
//        //Button ShowLocationButton = (Button) findViewById(R.id.adressbtn); // ?????? ?????? ?????? ??? gps ?????? ??????
//        TextView ShowLocationButton = (TextView) findViewById(R.id.adresstext);
//        ShowLocationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                gpsTracker = new GpsTracker(MainActivity.this);
//
//                double latitude = gpsTracker.getLatitude();
//                double longitude = gpsTracker.getLongitude();
//
//                String address = getCurrentAddress(latitude, longitude);
//                textview_address.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.brown_grey));
//                textview_address.setText(address);
//
//                Thread thread3= new Thread(){
//                    @Override
//                    public void run() {
//                        super.run();
//                        try {
//                            HttpConnection http = new HttpConnection();
//                            ArrayList<Post> post2 = http.getServerAround(1, latitude ,longitude);
//
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (!around){
//                                        NearsetUpInfo(post2);
//                                        nearAdapter.notifyDataSetChanged();
//                                        around = true;
//                                    }
//                                }
//                            });
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                thread3.start();
//
//                Toast.makeText(MainActivity.this, "???????????? \n?????? " + latitude + "\n?????? " + longitude, Toast.LENGTH_LONG).show();
//            }
//        });
//        noticeButton.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "????????????", Toast.LENGTH_SHORT).show();
//            }
//        });
//        userBox.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, "?????????", Toast.LENGTH_SHORT).show();
//            }
//        });
//        moreButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), AroundListActivity.class);
//                startActivity(intent);
//            }
//        });
//        inButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
//                    drawerLayout.openDrawer(Gravity.LEFT);
//                }
//            }
//        });
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
//                    drawerLayout.closeDrawer(Gravity.LEFT);
//                }
//            }
//        });
//        fullmeetingBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                fullmeetingBtn.setSelected(true);
//                Intent intent = new Intent(getApplicationContext(), AllListActivity.class);
//                startActivity(intent);
//            }
//
//        });
//        nearmeetingBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                nearmeetingBtn.setSelected(true);
//                Intent intent = new Intent(getApplicationContext(), AroundListActivity.class);
//                startActivity(intent);
//            }
//        });
//        schedulemeetingBtn.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                schedulemeetingBtn.setSelected(true);
//                Intent intent = new Intent(getApplicationContext(), TimeLineListActivity.class);
//                startActivity(intent);
//            }
//        });
//        if (textview_address.toString().equals("????????????")){
//            SpannableString personcontent = new SpannableString(textview_address.getText().toString());
//            personcontent.setSpan(new UnderlineSpan(), 0, personcontent.length(), 0);
//            textview_address.setText(personcontent);
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.e("onResume", "ENTER");
//
//    }
//
//    // ?????? ??????
//    @Override
//    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
//        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
//        // ?????? ?????? = PERMISSIONS_REQUEST_CODE and ????????? ???????????? ??????????????????
//        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
//
//            boolean check_result = true;
//
//            // ?????????(????????????) ?????? ??????
//            for (int result : grandResults) {
//                if (result != PackageManager.PERMISSION_GRANTED) { // ?????? ????????? ?????? PERMISSION_GRANTED ??? ??????, ????????? ?????? ??????
//                    check_result = false; // ?????? false
//                    break;
//                }
//            }
//
//            if (check_result) { // check_result = true
//                // ?????? ??????
//            } else { // check_result = false, ????????? ????????? ???????????? ??????
//                // ???????????? ????????? ????????? ??? ??????
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0]) // REQUIRED_PERMISSIONS[0]
//                        // ActivityCompat.shouldShowRequestPermissionRationale = ??????????????? ????????? ?????? ????????? ???????????? ???????????? ??????
//                        // ?????? ???????????? ?????? ?????? ???????????????, ?????? ?????? ????????? ???????????? ?????? ????????????
//                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) { // or REQUIRED_PERMISSIONS[1]
//                    Toast.makeText(MainActivity.this, "???????????? ?????????????????????. ?????? ?????? ???????????? ???????????? ??????????????????.", Toast.LENGTH_LONG).show();
//                    finish(); // ?????? ??????
//                } else {
//                    Toast.makeText(MainActivity.this, "???????????? ?????????????????????. ??????(??? ??????)?????? ???????????? ???????????? ?????????. ", Toast.LENGTH_LONG).show();
//                    showDialogForDetailSetting();
//                }
//            }
//        }
//    }
//
//    // ?????? ?????? ??????
//    private void checkRunTimePermission() {
//        // ?????? ????????? ????????? ????????? ?????? = PackageManager.PERMISSION_GRANTED or PackageManager.PERMISSION_DENIED
//        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
//        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
//
//        // ?????? ????????? ????????? ?????? ??????
//        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
//            // ?????? ??? ?????? ??????
//            gpsTracker = new GpsTracker(MainActivity.this);
//
//            double latitude = gpsTracker.getLatitude();
//            double longitude = gpsTracker.getLongitude();
//
//            TextView textview_address = (TextView)findViewById(R.id.adresstext);
//            String address = getCurrentAddress(latitude, longitude);
//            textview_address.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.brown_grey));
//            textview_address.setText(address);
//
//            Thread thread3= new Thread(){
//                @Override
//                public void run() {
//                    super.run();
//                    try {
//                        HttpConnection http = new HttpConnection();
//                        ArrayList<Post> post2 = http.getServerAround(1, latitude ,longitude);
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (!around){
//                                    NearsetUpInfo(post2);
//                                    nearAdapter.notifyDataSetChanged();
//                                    around = true;
//                                }
//                            }
//                        });
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            };
//            thread3.start();
//        } else { // ?????? ????????? ?????? ??????
//            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) { // ????????? ????????? ?????? ??????
//                Toast.makeText(MainActivity.this, "??? ?????? ??????????????? ?????? ?????? ????????? ???????????????.", Toast.LENGTH_LONG).show();
//                // ?????? ??????
//                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
//            } else { // ????????? ????????? ?????? ??????
//                // ?????? ??????
//                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
//            }
//        }
//    }
//
//    public String getCurrentAddress( double latitude, double longitude) {
//
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//
//        List<Address> addresses;
//
//        try {
//            addresses = geocoder.getFromLocation(
//                    latitude,
//                    longitude,
//                    7);
//        } catch (IOException ioException) {
//            Toast.makeText(this, "???????????? ????????? ????????????", Toast.LENGTH_LONG).show();
//            return "???????????? ????????? ????????????";
//        } catch (IllegalArgumentException illegalArgumentException) {
//            Toast.makeText(this, "????????? GPS ??????", Toast.LENGTH_LONG).show();
//            return "????????? GPS ??????";
//        }
//
//        if (addresses == null || addresses.size() == 0) {
//            Toast.makeText(this, "?????? ?????????", Toast.LENGTH_LONG).show();
//            return "?????? ?????????";
//        }
//        Address address = addresses.get(0);
//        String add = address.getAdminArea() + " " +address.getThoroughfare();
//        return add;
//    }
//
//    // ?????? ?????? ?????? ?????? ??????
//    public void showDialogForDetailSetting(){
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setTitle("??????");
//        builder.setMessage("?????? ?????? ??????\n????????? ???????????????");
//        builder.setCancelable(true);
//        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
//            // gps ???????????? ?????? ?????????????????? ??????
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
//            }
//        });
//        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
//            // gps ???????????? ???????????? ?????? ??????
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                finish();
//            }
//        }).setCancelable(false);
//
//        builder.create().show();
//    }
//
//    // ?????? gps ???????????? ?????? Dialog
//    private void showDialogForLocationServiceSetting() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//        builder.setTitle("?????? ????????? ????????????");
//        builder.setMessage("?????? ???????????? ???????????? ?????? ???????????? ???????????????.\n" + "?????? ????????? ???????????????????");
//        builder.setCancelable(true);
//        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
//            // gps ???????????? ?????? ?????????????????? ??????
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
//            }
//        });
//        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
//            // gps ???????????? ???????????? ?????? ??????
//            @Override
//            public void onClick(DialogInterface dialog, int id) {
//                //dialog.cancel();
//                finish();
//            }
//        });
//        builder.create().show();
//    }
//
//    // gps ????????? ??????
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case GPS_ENABLE_REQUEST_CODE:
//                // ???????????? gps ????????? ??????
//                if (checkLocationServicesStatus()) { // ????????? ???????????????
//                    if (checkLocationServicesStatus()) {
//                        Log.d("@@@", "onActivityResult : GPS ????????? ?????????");
//                        checkRunTimePermission();
//                        return;
//                    }
//                } break;
//        }
//    }
//
//    // ?????? gps ????????? ??????
//    public boolean checkLocationServicesStatus() {
//        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); // ?????? ?????? ????????? ?????? ????????? ????????? ????????????
//
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) // gps ??????????????? ?????? ?????? ??????
//                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER); // ???????????? ??????????????? ?????? ?????? ??????
//    }
//
//    // UpRecyclerView ??????
//    private void setUpInfo(ArrayList<Post> data) {
//        ArrayList<Post> postArrayList = data;
//
//        for (int i = 0; i < postArrayList.size(); i++){
//
//            int id = postArrayList.get(i).id;
//            String titletext = postArrayList.get(i).title;
//            String cruCnttext = "";
//            if (postArrayList.get(i).cruCnt == -1){
//                cruCnttext = "????????????";
//            } else {
//                cruCnttext = "?????? " + postArrayList.get(i).cruCnt + "???";
//            }
//            String startDate = postArrayList.get(i).startDate;
//            startDate = startDate.substring(0, startDate.indexOf(" "));
//
//            uparrayList.add(new UpcomingData(id, titletext, cruCnttext, startDate));
//        }
//    }
//    // NearRecyclerView ??????
//    private void NearsetUpInfo(ArrayList<Post> data) {
//        ArrayList<Post> postArrayList = data;
//
//        for (int i = 0; i < postArrayList.size(); i++){
//
//            int id = postArrayList.get(i).id;
//            String titletext = postArrayList.get(i).title;
//            String cruCnttext = "";
//            if (postArrayList.get(i).cruCnt == -1){
//                cruCnttext = "????????????";
//            } else {
//                cruCnttext = "?????? " + postArrayList.get(i).cruCnt + "???";
//            }
//            String startDate = postArrayList.get(i).startDate;
//            startDate = startDate.substring(0, startDate.indexOf(" "));
//
//            neararrayList.add(new NearData(id, titletext, cruCnttext, startDate));
//        }
//    }
//}
package com.anzela.myapplication1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anzela.myapplication1.ApiExplorer;
import com.anzela.myapplication1.GpsTracker;
import com.anzela.myapplication1.HttpConnection;
import com.anzela.myapplication1.NearAdapter;
import com.anzela.myapplication1.NearData;
import com.anzela.myapplication1.Post;
import com.anzela.myapplication1.R;
import com.anzela.myapplication1.UpcomingAdapter;
import com.anzela.myapplication1.UpcomingData;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private GpsTracker gpsTracker;
    private ApiExplorer apiExplorer;

    private ArrayList<UpcomingData> uparrayList;
    private UpcomingAdapter upAdapter;
    private RecyclerView uprecyclerView;
    private LinearLayoutManager uplinearLayoutManager;

    private ArrayList<NearData> neararrayList;
    private NearAdapter nearAdapter;
    private RecyclerView nearrecyclerView;
    private LinearLayoutManager nearlinearLayoutManager;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView inButton;
    ImageView backButton;
    ImageView moreButton;
    ImageView noticeButton;
    ImageView userImg;
    TextView NameText;
    TextView fullmeetingBtn;
    TextView nearmeetingBtn;
    TextView schedulemeetingBtn;
    TextView weatherInt;
    Dialog permissionDialog;
    RelativeLayout userBox;

    private long backKeyPressedTime = 0;
    private Toast toast;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        inButton = findViewById(R.id.open_menu);
        backButton = findViewById(R.id.close_menu);
        fullmeetingBtn = findViewById(R.id.full_meeting);
        nearmeetingBtn = findViewById(R.id.near_meeting);
        schedulemeetingBtn = findViewById(R.id.schedule_meeting);
        moreButton = findViewById(R.id.moreBtn);
        weatherInt = findViewById(R.id.weather_temperature);
        noticeButton = findViewById(R.id.notice);
        userImg = findViewById(R.id.userimg);
        userBox = findViewById(R.id.userbox);

        permissionDialog = new Dialog(MainActivity.this);
        permissionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        permissionDialog.setContentView(R.layout.permissiondialog);

        // ??????

        // ???????????? ????????? ??????
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    http = new HttpConnection();
//                    http.getServerSoon(1, 20210714);
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            setUpAdapter();
//                            setUpInfo((ArrayList<Post>) http.getPostSoon());
//                        }
//                    });
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });
        // ??? ?????? ?????????
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    http = new HttpConnection();
//                    http.getServerAround(1, 37.49 ,126.83);
//
////                    http.getServer(1);
////                    http.getServerDetail(1);
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            NearsetUpAdapter();
//                            NearsetUpInfo((ArrayList<Post>) http.getPostAround());
//                        }
//                    });
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        });

        if(!checkLocationServicesStatus()){ // ?????? gps ????????? ?????? , ???????????? ???????????? ??????
            showDialogForLocationServiceSetting(); // ?????? gps ???????????? ?????? Dialog
        }else{ // ?????? gps ????????? ?????? , ????????? ???????????? ??????
            checkRunTimePermission();
        }

        final TextView textview_address = (TextView)findViewById(R.id.adresstext);
        //Button ShowLocationButton = (Button) findViewById(R.id.adressbtn); // ?????? ?????? ?????? ??? gps ?????? ??????
        TextView ShowLocationButton = (TextView) findViewById(R.id.adresstext);
        ShowLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                gpsTracker = new GpsTracker(MainActivity.this);

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                String address = getCurrentAddress(latitude, longitude);
                textview_address.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.brown_grey));
                textview_address.setText(address);

//                Thread thread3= new Thread(){
//                    @Override
//                    public void run() {
//                        super.run();
//                        try {
//                            HttpConnection http = new HttpConnection();
//                            ArrayList<Post> post2 = http.getServerAround(1, latitude ,longitude);
//
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    if (!around){
//                                        NearsetUpInfo(post2);
//                                        nearAdapter.notifyDataSetChanged();
//                                        around = true;
//                                    }
//                                }
//                            });
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                thread3.start();

                Toast.makeText(MainActivity.this, "???????????? \n?????? " + latitude + "\n?????? " + longitude, Toast.LENGTH_LONG).show();
            }
        });
        noticeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "????????????", Toast.LENGTH_SHORT).show();
            }
        });
        userBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "?????????", Toast.LENGTH_SHORT).show();
            }
        });
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AroundListActivity.class);
                startActivity(intent);
            }
        });
        inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }
            }
        });
        fullmeetingBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                fullmeetingBtn.setSelected(true);
                Intent intent = new Intent(getApplicationContext(), AllListActivity.class);
                startActivity(intent);
            }

        });
        nearmeetingBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                nearmeetingBtn.setSelected(true);
                Intent intent = new Intent(getApplicationContext(), AroundListActivity.class);
                startActivity(intent);
            }
        });
        schedulemeetingBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                schedulemeetingBtn.setSelected(true);
                Intent intent = new Intent(getApplicationContext(), TimeLineListActivity.class);
                startActivity(intent);
            }
        });
        if (textview_address.toString().equals("????????????")){
            SpannableString personcontent = new SpannableString(textview_address.getText().toString());
            personcontent.setSpan(new UnderlineSpan(), 0, personcontent.length(), 0);
            textview_address.setText(personcontent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume", "ENTER");
        uprecyclerView = (RecyclerView)findViewById(R.id.comingrv);
        uplinearLayoutManager = new LinearLayoutManager(this);
        uprecyclerView.setLayoutManager(uplinearLayoutManager);
        uparrayList = new ArrayList<>();
        upAdapter = new UpcomingAdapter(uparrayList);
        uprecyclerView.setAdapter(upAdapter);


        nearrecyclerView = (RecyclerView)findViewById(R.id.nearrv);
        nearlinearLayoutManager = new LinearLayoutManager(this);
        nearrecyclerView.setLayoutManager(nearlinearLayoutManager);
        neararrayList = new ArrayList<>();
        nearAdapter = new NearAdapter(neararrayList);
        nearrecyclerView.setAdapter(nearAdapter);

        NameText = findViewById(R.id.nameText);
        NameText.setText("[user]" + "???, ?????????\n????????????????");
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMdd");
        int getDate = Integer.parseInt(simpleDate.format(date));

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    HttpConnection http = new HttpConnection();
                    ArrayList<Post> post = http.getServerSoon(1, getDate);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setUpInfo(post);
                            upAdapter.notifyDataSetChanged();

                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        Thread thread2= new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    gpsTracker = new GpsTracker(MainActivity.this);

                    double latitude = gpsTracker.getLatitude();
                    double longitude = gpsTracker.getLongitude();
                    HttpConnection http = new HttpConnection();
                    ArrayList<Post> post2 = http.getServerAround(1, latitude ,longitude);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            NearsetUpInfo(post2);
                            nearAdapter.notifyDataSetChanged();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread2.start();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    apiExplorer = new ApiExplorer();
                    apiExplorer.lookupWeather();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            weatherInt.setText(apiExplorer.getResult());
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        thread1.start();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'??????\' ????????? ?????? ??? ???????????? ???????????????.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            finish();
            toast.cancel();
        }
    }

    // ?????? ??????
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        // ?????? ?????? = PERMISSIONS_REQUEST_CODE and ????????? ???????????? ??????????????????
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            boolean check_result = true;

            // ?????????(????????????) ?????? ??????
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) { // ?????? ????????? ?????? PERMISSION_GRANTED ??? ??????, ????????? ?????? ??????
                    check_result = false; // ?????? false
                    break;
                }
            }

            if (check_result) { // check_result = true
                // ?????? ??????
            } else { // check_result = false, ????????? ????????? ???????????? ??????
                // ???????????? ????????? ????????? ??? ??????
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0]) // REQUIRED_PERMISSIONS[0]
                        // ActivityCompat.shouldShowRequestPermissionRationale = ??????????????? ????????? ?????? ????????? ???????????? ???????????? ??????
                        // ?????? ???????????? ?????? ?????? ???????????????, ?????? ?????? ????????? ???????????? ?????? ????????????
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) { // or REQUIRED_PERMISSIONS[1]
                    Toast.makeText(MainActivity.this, "???????????? ?????????????????????. ?????? ?????? ???????????? ???????????? ??????????????????.", Toast.LENGTH_LONG).show();
                    finish(); // ?????? ??????
                } else {
                    Toast.makeText(MainActivity.this, "???????????? ?????????????????????. ??????(??? ??????)?????? ???????????? ???????????? ?????????. ", Toast.LENGTH_LONG).show();
                    showDialogForDetailSetting();
                }
            }
        }
    }

    // ?????? ?????? ??????
    private void checkRunTimePermission() {
        // ?????? ????????? ????????? ????????? ?????? = PackageManager.PERMISSION_GRANTED or PackageManager.PERMISSION_DENIED
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);

        // ?????? ????????? ????????? ?????? ??????
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            // ?????? ??? ?????? ??????
            gpsTracker = new GpsTracker(MainActivity.this);

            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            TextView textview_address = (TextView)findViewById(R.id.adresstext);
            String address = getCurrentAddress(latitude, longitude);
            textview_address.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.brown_grey));
            textview_address.setText(address);

        } else { // ?????? ????????? ?????? ??????
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) { // ????????? ????????? ?????? ??????
                Toast.makeText(MainActivity.this, "??? ?????? ??????????????? ?????? ?????? ????????? ???????????????.", Toast.LENGTH_LONG).show();
                // ?????? ??????
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            } else { // ????????? ????????? ?????? ??????
                // ?????? ??????
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    public String getCurrentAddress( double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            Toast.makeText(this, "???????????? ????????? ????????????", Toast.LENGTH_LONG).show();
            return "???????????? ????????? ????????????";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "????????? GPS ??????", Toast.LENGTH_LONG).show();
            return "????????? GPS ??????";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "?????? ?????????", Toast.LENGTH_LONG).show();
            return "?????? ?????????";
        }
        Address address = addresses.get(0);
        String add = address.getAdminArea() + " " +address.getThoroughfare();
        return add;
    }

    // ?????? ?????? ?????? ?????? ??????
    public void showDialogForDetailSetting(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("??????");
        builder.setMessage("?????? ?????? ??????\n????????? ???????????????");
        builder.setCancelable(true);
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            // gps ???????????? ?????? ?????????????????? ??????
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            // gps ???????????? ???????????? ?????? ??????
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        }).setCancelable(false);

        builder.create().show();
    }

    // ?????? gps ???????????? ?????? Dialog
    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("?????? ????????? ????????????");
        builder.setMessage("?????? ???????????? ???????????? ?????? ???????????? ???????????????.\n" + "?????? ????????? ???????????????????");
        builder.setCancelable(true);
        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            // gps ???????????? ?????? ?????????????????? ??????
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            // gps ???????????? ???????????? ?????? ??????
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //dialog.cancel();
                finish();
            }
        });
        builder.create().show();
    }

    // gps ????????? ??????
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:
                // ???????????? gps ????????? ??????
                if (checkLocationServicesStatus()) { // ????????? ???????????????
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS ????????? ?????????");
                        checkRunTimePermission();
                        return;
                    }
                } break;
        }
    }

    // ?????? gps ????????? ??????
    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); // ?????? ?????? ????????? ?????? ????????? ????????? ????????????

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) // gps ??????????????? ?????? ?????? ??????
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER); // ???????????? ??????????????? ?????? ?????? ??????
    }

    // UpRecyclerView ??????
    private void setUpInfo(ArrayList<Post> data) {
        ArrayList<Post> postArrayList = data;

        for (int i = 0; i < postArrayList.size(); i++){

            int id = postArrayList.get(i).id;
            String titletext = postArrayList.get(i).title;
            String cruCnttext = "";
            if (postArrayList.get(i).cruCnt == -1){
                cruCnttext = "????????????";
            } else {
                cruCnttext = "?????? " + postArrayList.get(i).cruCnt + "???";
            }
            String startDate = postArrayList.get(i).startDate;
            startDate = startDate.substring(0, startDate.indexOf(" "));

            uparrayList.add(new UpcomingData(id, titletext, cruCnttext, startDate));
        }
    }
    // NearRecyclerView ??????
    private void NearsetUpInfo(ArrayList<Post> data) {
        ArrayList<Post> postArrayList = data;

        for (int i = 0; i < postArrayList.size(); i++){

            int id = postArrayList.get(i).id;
            String titletext = postArrayList.get(i).title;
            String cruCnttext = "";
            if (postArrayList.get(i).cruCnt == -1){
                cruCnttext = "????????????";
            } else {
                cruCnttext = "?????? " + postArrayList.get(i).cruCnt + "???";
            }
            String startDate = postArrayList.get(i).startDate;
            startDate = startDate.substring(0, startDate.indexOf(" "));

            neararrayList.add(new NearData(id, titletext, cruCnttext, startDate));
        }
    }
}
