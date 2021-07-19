package com.anzela.myapplication1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonArray;

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

import org.json.JSONException;

import java.io.IOException;
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
    TextView fullmeetingBtn;
    TextView nearmeetingBtn;
    TextView schedulemeetingBtn;
    TextView weatherInt;
    Dialog permissionDialog;

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

        permissionDialog = new Dialog(MainActivity.this);
        permissionDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        permissionDialog.setContentView(R.layout.permissiondialog);

        // 날씨
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    HttpConnection http = new HttpConnection();
                    http.getServerSoon(1, 20210714);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setUpInfo((ArrayList<Post>) http.getPostSoon());
                            upAdapter.notifyDataSetChanged();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        },thread2= new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    HttpConnection http = new HttpConnection();
                    http.getServerAround(1, 37.49 ,126.83);

//                    http.getServer(1);
//                    http.getServerDetail(1);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("TEST>>" , "NEAR LIST SETUp : " + http.getPostAround().size());
                            NearsetUpInfo((ArrayList<Post>) http.getPostAround());
                            nearAdapter.notifyDataSetChanged();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread1.start();
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
        // 다가오는 라이딩 일정
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
        // 내 근처 라이딩
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

        if(!checkLocationServicesStatus()){ // 기기 gps 활성화 체크 , 비활성화 되어있는 경우
            showDialogForLocationServiceSetting(); // 기기 gps 활성화를 위한 Dialog
        }else{ // 기기 gps 활성화 체크 , 활성화 되어있는 경우
            checkRunTimePermission();
        }

        final TextView textview_address = (TextView)findViewById(R.id.adresstext);
        //Button ShowLocationButton = (Button) findViewById(R.id.adressbtn); // 해당 버튼 클릭 시 gps 위치 수신
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

                Toast.makeText(MainActivity.this, "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();
            }
        });
        noticeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "푸쉬알림", Toast.LENGTH_SHORT).show();
            }
        });
        userImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "내정보", Toast.LENGTH_SHORT).show();
            }
        });
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AllListActivity.class);
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
        SpannableString personcontent = new SpannableString(textview_address.getText().toString());
        personcontent.setSpan(new UnderlineSpan(), 0, personcontent.length(), 0);
        textview_address.setText(personcontent);
    }

    // 권한 획득
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grandResults);
        // 요청 코드 = PERMISSIONS_REQUEST_CODE and 퍼미션 개수만큼 수신되었다면
        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            boolean check_result = true;

            // 퍼미션(접근권한) 허용 체크
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) { // 권한 여부에 따라 PERMISSION_GRANTED 값 반환, 권한이 없는 경우
                    check_result = false; // 값을 false
                    break;
                }
            }

            if (check_result) { // check_result = true
                // 정상 작동
            } else { // check_result = false, 퍼미션 허용이 안되었을 경우
                // 사용자가 퍼미션 거부를 한 경우
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0]) // REQUIRED_PERMISSIONS[0]
                        // ActivityCompat.shouldShowRequestPermissionRationale = 사용자에게 권한에 대한 설명이 필요한지 체크하는 함수
                        // 권한 요청에서 한번 이상 거부하거나, 다시 보지 않기를 체크하지 않은 경우에만
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) { // or REQUIRED_PERMISSIONS[1]
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish(); // 강제 종료
                } else {
                    Toast.makeText(MainActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                    showDialogForDetailSetting();
                }
            }
        }
    }

    // 위치 권한 체크
    private void checkRunTimePermission() {
        // 위치 권한을 가지고 있는지 체크 = PackageManager.PERMISSION_GRANTED or PackageManager.PERMISSION_DENIED
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);

        // 위치 권한을 가지고 있는 경우
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED && hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            // 위치 값 획득 가능
        } else { // 위치 권한이 없는 경우
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, REQUIRED_PERMISSIONS[0])) { // 거부한 기록이 있는 경우
                Toast.makeText(MainActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 권한 요청
                ActivityCompat.requestPermissions(MainActivity.this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            } else { // 거부한 기록이 없는 경우
                // 권한 요청
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
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }
        Address address = addresses.get(0);
        String add = address.getAdminArea() + " " +address.getThoroughfare();
        return add;
    }

    // 거부 이후 위치 권한 획득
    public void showDialogForDetailSetting(){

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("알림");
        builder.setMessage("위치 권한 거부\n권한을 설정하세요");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            // gps 활성화를 위해 환경설정으로 이동
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            // gps 활성화를 거부하여 강제 종료
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        }).setCancelable(false);

        builder.create().show();
    }

    // 기기 gps 활성화를 위한 Dialog
    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n" + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            // gps 활성화를 위해 환경설정으로 이동
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            // gps 활성화를 거부하여 강제 종료
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //dialog.cancel();
                finish();
            }
        });
        builder.create().show();
    }

    // gps 활성화 체크
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:
                // 사용자의 gps 활성화 확인
                if (checkLocationServicesStatus()) { // 활성화 되어있다면
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                } break;
        }
    }

    // 기기 gps 활성화 체크
    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE); // 위치 권한 사용을 위해 시스템 서비스 받아오기

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) // gps 프로바이더 사용 가능 여부
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER); // 네트워크 프로바이더 사용 가능 여부
    }

    // UpRecyclerView 세팅
    private void setUpInfo(ArrayList<Post> data) {
        ArrayList<Post> postArrayList = data;

        for (int i = 0; i < postArrayList.size(); i++){

            int id = postArrayList.get(i).id;
            String titletext = postArrayList.get(i).title;
            String cruCnttext = "";
            if (postArrayList.get(i).cruCnt == -1){
                cruCnttext = "제한없음";
            } else {
                cruCnttext = "최대 " + postArrayList.get(i).cruCnt + "명";
            }
            String startDate = postArrayList.get(i).startDate;
            startDate = startDate.substring(0, startDate.indexOf(" "));

            uparrayList.add(new UpcomingData(id, titletext, cruCnttext, startDate));
        }
    }
    // NearRecyclerView 세팅
    private void NearsetUpInfo(ArrayList<Post> data) {
        ArrayList<Post> postArrayList = data;

        for (int i = 0; i < postArrayList.size(); i++){

            int id = postArrayList.get(i).id;
            String titletext = postArrayList.get(i).title;
            String cruCnttext = "";
            if (postArrayList.get(i).cruCnt == -1){
                cruCnttext = "제한없음";
            } else {
                cruCnttext = "최대 " + postArrayList.get(i).cruCnt + "명";
            }
            String startDate = postArrayList.get(i).startDate;
            startDate = startDate.substring(0, startDate.indexOf(" "));

            neararrayList.add(new NearData(id, titletext, cruCnttext, startDate));
        }
    }
}