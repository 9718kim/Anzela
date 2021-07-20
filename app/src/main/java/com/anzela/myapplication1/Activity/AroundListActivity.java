package com.anzela.myapplication1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anzela.myapplication1.GpsTracker;
import com.anzela.myapplication1.HttpConnection;
import com.anzela.myapplication1.NearAdapter;
import com.anzela.myapplication1.NearData;
import com.anzela.myapplication1.Post;
import com.anzela.myapplication1.R;

import java.util.ArrayList;

public class AroundListActivity extends AppCompatActivity {

    private GpsTracker gpsTracker;

    private ArrayList<NearData> neararrayList;
    private NearAdapter nearAdapter;
    private RecyclerView nearrecyclerView;
    private LinearLayoutManager nearlinearLayoutManager;

    ImageView backButton;
    TextView writeButton;
    TextView writebigButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aroundlist);

        backButton = findViewById(R.id.backpressed);
        writeButton = findViewById(R.id.write);
        writebigButton = findViewById(R.id.writebig);

        nearrecyclerView = (RecyclerView)findViewById(R.id.nearrv);
        nearlinearLayoutManager = new LinearLayoutManager(this);
        nearrecyclerView.setLayoutManager(nearlinearLayoutManager);
        neararrayList = new ArrayList<>();
        nearAdapter = new NearAdapter(neararrayList);
        nearrecyclerView.setAdapter(nearAdapter);

        gpsTracker = new GpsTracker(AroundListActivity.this);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    HttpConnection http = new HttpConnection();
                    ArrayList<Post> post = http.getServerAround(1, latitude ,longitude);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            NearsetUpInfo(post);
                            nearAdapter.notifyDataSetChanged();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread1.start();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeButton.setSelected(true);
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(intent);
            }
        });
        writebigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writebigButton.setSelected(true);
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(intent);
            }
        });
    }
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