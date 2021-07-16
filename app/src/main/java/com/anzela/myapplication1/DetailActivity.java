package com.anzela.myapplication1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public double Sla = 0;
    public double Slo = 0;
    public double Ela = 0;
    public double Elo = 0;
    private BoardDetail boardDetail;

    ImageView backButton;
    EditText writeCommend;
    View commentButton;
    ImageView commentImg;


    TextView Title;
    CircleImageView UserImg;
    TextView UserName;
    TextView WriteDate;
    TextView DueData;
    TextView MaxPerson;
    TextView StartDetail;
    TextView EndDetail;
    TextView DetailText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        backButton = findViewById(R.id.backpressed);
        writeCommend = findViewById(R.id.writecommend);
        commentButton = findViewById(R.id.commentBtn);
        commentImg = findViewById(R.id.commentimg);


        Title = findViewById(R.id.detailtitle);
        UserImg = findViewById(R.id.userimg);
        UserName = findViewById(R.id.username);
        WriteDate = findViewById(R.id.writedate);
        DueData = findViewById(R.id.duedate);
        MaxPerson = findViewById(R.id.detailmaxperson);
        StartDetail = findViewById(R.id.startdetail);
        EndDetail = findViewById(R.id.enddetail);
        DetailText = findViewById(R.id.detailtext);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    int id = getIntent().getIntExtra("idnum", 0);
                    HttpConnection http = new HttpConnection();
                    http.getServerDetail(id);
                    Log.e("Detail-text", "bbb");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setting((BoardDetail) http.getBoardDetail());
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        writeCommend.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean bFocus) {
                commentButton.setBackgroundDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.circle_aqua));
                commentImg.setBackgroundDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_comment_white));
                if( !bFocus){
                    commentButton.setBackgroundDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.circlr_black_two));
                    commentImg.setBackgroundDrawable(ContextCompat.getDrawable(DetailActivity.this, R.drawable.ic_comment_mint));
                }
            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        LatLng camera;
//        LatLng startPoitn = new LatLng(37.49492908756368, 126.83953848543452);
        LatLng startPoitn = new LatLng(Sla, Slo);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(startPoitn);
        markerOptions.title("출발지");
        markerOptions.snippet("");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_start_marker));
        mMap.addMarker(markerOptions);

        if (Ela == 0 && Elo == 0){
            camera = new LatLng(Sla, Slo);
        }else{
            LatLng endPoint = new LatLng(Ela, Elo);
            MarkerOptions markerOptions2 = new MarkerOptions();
            markerOptions2.position(endPoint);
            markerOptions2.title("도착지");
            markerOptions2.snippet("");
            markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_end_marker));
            mMap.addMarker(markerOptions2);

            camera = new LatLng((Sla + Ela)/2, (Slo + Elo)/2);
        }


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(camera, 10));
    }

    // 받은 data로 화면 구성
    public void setting(BoardDetail data){
        boardDetail = data;
        Title.setText(boardDetail.getTitle());
        String imageUrl = boardDetail.user.profileUrl;
        Glide.with(this).load(imageUrl).into(UserImg);

        UserName.setText(boardDetail.user.getUid());
        WriteDate.setText(boardDetail.getRegDate().substring(0, boardDetail.getRegDate().indexOf(" ")) + " 작성");

        String Sdate = boardDetail.getStartDate(); //"2021-07-15 04:00:00";
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date Ddate = new Date();
        try {
            Ddate = transFormat.parse(Sdate);
        }catch (Exception e){
            e.printStackTrace();
        }

        SimpleDateFormat transFormat2 = new SimpleDateFormat("yyyy년 M월 d일 E요일 a h시");

        String newSDate = transFormat2.format(Ddate);

        DueData.setText(newSDate);

        Log.e("newSDate-text", newSDate);

        if (boardDetail.getCruCnt() == -1){
            MaxPerson.setText("제한없음");
        }else{
            MaxPerson.setText("최대 " + boardDetail.getCruCnt() + "명");
        }

        StartDetail.setText(boardDetail.getStartPoint());

        if (boardDetail.getEndPoint() == "text on no value"){
            EndDetail.setText("정해지지 않았습니다.");
            EndDetail.setTextColor(ContextCompat.getColor(DetailActivity.this, R.color.very_light_pink));
        }else{
            EndDetail.setText(boardDetail.getEndPoint());
        }

        DetailText.setText(boardDetail.getContent());

        Sla = Double.parseDouble(boardDetail.getStartLat());
        Slo = Double.parseDouble(boardDetail.getStartLng());
        if (!boardDetail.getEndPoint().equals("text on no value")){
            Ela = Double.parseDouble(boardDetail.getEndLat());
            Elo = Double.parseDouble(boardDetail.getEndLng());
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
}