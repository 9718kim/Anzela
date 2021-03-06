package com.anzela.myapplication1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.anzela.myapplication1.BoardDetail;
import com.anzela.myapplication1.CommentAdapter;
import com.anzela.myapplication1.Comments;
import com.anzela.myapplication1.HttpConnection;
import com.anzela.myapplication1.R;
import com.anzela.myapplication1.ScrollableMapFragment;
import com.anzela.myapplication1.User;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Executors;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private ArrayList<Comments> boardDetailArrayList;
    private CommentAdapter comAdapter;
    private RecyclerView comRecyclerView;
    private LinearLayoutManager comlinearLayoutManager;

//    private NestedScrollView Scroll;
    protected View Map;

    private GoogleMap mMap;
    public double Sla = 0;
    public double Slo = 0;
    public double Ela = 0;
    public double Elo = 0;
    private BoardDetail boardDetail;
    private ArrayList<Comments> commentList;

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

    TextView Modify;
    TextView DeleteBtn;
    TextView NoCommend;
    TextView commentAll;


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

        Modify = findViewById(R.id.modify);
        DeleteBtn = findViewById(R.id.deletebtn);
        NoCommend = findViewById(R.id.nocommend);
        commentAll = findViewById(R.id.commentall);

        comRecyclerView = (RecyclerView)findViewById(R.id.commentrv);
        comlinearLayoutManager = new LinearLayoutManager(this);
        comRecyclerView.setLayoutManager(comlinearLayoutManager);
        boardDetailArrayList = new ArrayList<>();
        comAdapter = new CommentAdapter(boardDetailArrayList);
        comRecyclerView.setAdapter(comAdapter);

//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    int id = getIntent().getIntExtra("idnum", 0);
//                    HttpConnection http = new HttpConnection();
//                    http.getServerDetail(id);
//                    boardDetail = (BoardDetail) http.getBoardDetail();
//                    Log.e("Detail-text", "bbb");
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            setting(boardDetail);
//                            if (boardDetail.getComments().size() >= 1){
//                                commentAll.setText("?????? " + boardDetail.cmtCnt + "???");
//                                NoCommend.setVisibility(View.GONE);
//                                comRecyclerView.setVisibility(View.VISIBLE);
//                                commentList = boardDetail.Comments;
//                                setcomment(commentList);
//                            }
//                        }
//                    });
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//
//            }
//        });

        NestedScrollView Scroll = findViewById(R.id.scroll);

        ScrollableMapFragment mapFragment = (ScrollableMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.setListener(new ScrollableMapFragment.OnTouchListener() {
            @Override
            public void onActionDown() {
                Scroll.requestDisallowInterceptTouchEvent(true);
            }
            @Override
            public void onActionUp() {
                Scroll.requestDisallowInterceptTouchEvent(false);
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

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeCommend.clearFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(writeCommend.getWindowToken(), 0);
                if(!(writeCommend.getText().toString().equals("") || writeCommend.getText() == null)){
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                HttpConnection http = new HttpConnection();
                                int id = getIntent().getIntExtra("idnum", 0);
                                http.getComment(id, writeCommend.getText().toString());
                                http.getServerDetail(id);
                                boardDetail = (BoardDetail) http.getBoardDetail();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        commentAll.setText("?????? " + boardDetail.cmtCnt + "???");
                                        writeCommend.setText("");
                                        commentList = boardDetail.Comments;
                                        comAdapter.setUpInfo(commentList);

                                        if (boardDetail.getComments().size() >= 1){
                                            NoCommend.setVisibility(View.GONE);
                                            comRecyclerView.setVisibility(View.VISIBLE);
                                        }
                                    }
                                });
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }
        });

        Modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = getIntent().getIntExtra("idnum", 0);
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                intent.putExtra("id", id);
//                intent.putExtra("data", boardDetail);
                startActivity(intent);
            }
        });

        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HttpConnection http = new HttpConnection();
                            int id = getIntent().getIntExtra("idnum", 0);
                            int code = http.getServerDelete(id);
                            Log.e("CODE", String.valueOf(code));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    onBackPressed();
                                }
                            });
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    int id = getIntent().getIntExtra("idnum", 0);
                    HttpConnection http = new HttpConnection();
                    http.getServerDetail(id);
                    boardDetail = (BoardDetail) http.getBoardDetail();
                    Log.e("Detail-text", "bbb");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setting(boardDetail);
                            if (boardDetail.getComments().size() >= 1){
                                commentAll.setText("?????? " + boardDetail.cmtCnt + "???");
                                NoCommend.setVisibility(View.GONE);
                                comRecyclerView.setVisibility(View.VISIBLE);
                                commentList = boardDetail.Comments;
                                setcomment(commentList);
                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        LatLng camera;
        LatLng startPoitn = new LatLng(Sla, Slo);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(startPoitn);
        markerOptions.title("?????????");
        markerOptions.snippet("");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_start_marker));
        mMap.addMarker(markerOptions);

        if (Ela == 0 && Elo == 0){
            camera = new LatLng(Sla, Slo);
        }else{
            LatLng endPoint = new LatLng(Ela, Elo);
            MarkerOptions markerOptions2 = new MarkerOptions();
            markerOptions2.position(endPoint);
            markerOptions2.title("?????????");
            markerOptions2.snippet("");
            markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_end_marker));
            mMap.addMarker(markerOptions2);

            camera = new LatLng((Sla + Ela)/2, (Slo + Elo)/2);
        }


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(camera, 10));
    }

    // ?????? data??? ?????? ??????
    public void setting(BoardDetail data){
        boardDetail = data;
        Title.setText(boardDetail.getTitle());
        String imageUrl = boardDetail.user.profileUrl;
        Glide.with(this).load(imageUrl).into(UserImg);

        UserName.setText(boardDetail.user.getUid());
        WriteDate.setText(boardDetail.getRegDate().substring(0, boardDetail.getRegDate().indexOf(" ")) + " ??????");

        String Sdate = boardDetail.getStartDate(); //"2021-07-15 04:00:00";
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date Ddate = new Date();
        try {
            Ddate = transFormat.parse(Sdate);
        }catch (Exception e){
            e.printStackTrace();
        }

        SimpleDateFormat transFormat2 = new SimpleDateFormat("yyyy??? M??? d??? E?????? a h???");

        String newSDate = transFormat2.format(Ddate);

        DueData.setText(newSDate);

        Log.e("newSDate-text", newSDate);

        if (boardDetail.getCruCnt() == -1){
            MaxPerson.setText("????????????");
        }else{
            MaxPerson.setText("?????? " + boardDetail.getCruCnt() + "???");
        }

        StartDetail.setText(boardDetail.getStartPoint());

        Log.e("TEST", boardDetail.getEndPoint());
        if (boardDetail.getEndPoint() == "text on no value"){
            EndDetail.setText("???????????? ???????????????.");
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
    public void setcomment(ArrayList<Comments> data){
        for (int i = 0; i < data.size(); i++){

            int id = data.get(i).id;
            String content = data.get(i).content;
            int depth = data.get(i).depth;
            String regDate = data.get(i).regDate;

            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy. MM. dd");

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            regDate = simpleDate.format(cal.getTime());

            String uid = data.get(i).user.uid;
            String profileUrl = data.get(i).user.profileUrl;

            boardDetailArrayList.add(new Comments(id, content, depth, regDate, (new User(uid, profileUrl))));
        }
    }

}