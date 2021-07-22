package com.anzela.myapplication1.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anzela.myapplication1.HttpConnection;
import com.anzela.myapplication1.NearAdapter;
import com.anzela.myapplication1.NearData;
import com.anzela.myapplication1.Post;
import com.anzela.myapplication1.R;

import java.util.ArrayList;

public class AllListActivity extends AppCompatActivity {

    private ArrayList<NearData> neararrayList;
    private NearAdapter nearAdapter;
    private RecyclerView nearrecyclerView;
    private LinearLayoutManager nearlinearLayoutManager;
    int pageNum = 1;
    int Tnum;

    NestedScrollView Scroll;
    ConstraintLayout NullPage;

    ImageView backButton;
    TextView writeButton;
    TextView writebigButton;
    RelativeLayout writeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alllist);

        backButton = findViewById(R.id.backpressed);
        writeButton = findViewById(R.id.write);
        writebigButton = findViewById(R.id.writebig);
        writeBtn = findViewById(R.id.writebtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        writeBtn.setOnClickListener(new View.OnClickListener() {
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

    @Override
    protected void onResume() {
        super.onResume();

        Log.e("onResume", "All check");
        pageNum = 1;
        NullPage = findViewById(R.id.nullPage);

        nearrecyclerView = (RecyclerView)findViewById(R.id.allrv);
        nearlinearLayoutManager = new LinearLayoutManager(this);
        nearrecyclerView.setLayoutManager(nearlinearLayoutManager);
        neararrayList = new ArrayList<>();
        nearAdapter = new NearAdapter(neararrayList);
        nearrecyclerView.setAdapter(nearAdapter);

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    HttpConnection http = new HttpConnection();
                    ArrayList<Post> post = http.getServer(pageNum++);
                    int total = post.get(0).id;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (total == 0){
                                nearrecyclerView.setVisibility(View.GONE);
                                NullPage.setVisibility(View.VISIBLE);
                            }else{
                                NearsetUpInfo(post);
                                nearAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread1.start();

        Scroll = findViewById(R.id.scroll);
        Scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if (scrollY > oldScrollY) {
                        if (scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) {
                            //code to fetch more data for endless scrolling
                            int totalCount = nearAdapter.getItemCount(); // 생성된 item 수

                            Log.e("num", String.valueOf(totalCount));

                            if (Tnum != totalCount){
                                Tnum = totalCount;
                                Thread thread1 = new Thread() {
                                    @Override
                                    public void run() {
                                        super.run();
                                        try {
                                            HttpConnection http = new HttpConnection();
                                            ArrayList<Post> post = http.getServer(pageNum++);
                                            int total = post.get(0).id;
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (total == 0){
                                                        nearrecyclerView.setVisibility(View.GONE);
                                                        NullPage.setVisibility(View.VISIBLE);
                                                    }else{
                                                        NearsetUpInfo(post);
                                                        nearAdapter.notifyDataSetChanged();

                                                    }
                                                }
                                            });
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                thread1.start();
                            }

                        }
                    }
                }
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