package com.anzela.myapplication1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TimeLineListActivity extends AppCompatActivity {

    private ArrayList<UpcomingData> uparrayList;
    private UpcomingAdapter upAdapter;
    private RecyclerView uprecyclerView;
    private LinearLayoutManager uplinearLayoutManager;

    ImageView backButton;
    TextView writeButton;
    //TextView writebigButton;

    /*ConstraintLayout Item1;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timelinelist);

        backButton = findViewById(R.id.backpressed);
        writeButton = findViewById(R.id.write);

        /*Item1 = findViewById(R.id.item1);*/
        //writebigButton = findViewById(R.id.writebig);

/*        uprecyclerView = (RecyclerView)findViewById(R.id.comingrv);
        uplinearLayoutManager = new LinearLayoutManager(this);
        uprecyclerView.setLayoutManager(uplinearLayoutManager);

        uparrayList = new ArrayList<>();
        upAdapter = new UpcomingAdapter(uparrayList);
        uprecyclerView.setAdapter(upAdapter);

        setUpInfo();
        setUpAdapter();*/

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
        /*Item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item1.setSelected(true);
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                startActivity(intent);
            }
        });*/
/*        writebigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writebigButton.setSelected(true);
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                startActivity(intent);
            }
        });*/
    }
/*    private void setUpAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        uprecyclerView.setLayoutManager(layoutManager);
        uprecyclerView.setItemAnimator(new DefaultItemAnimator());
        uprecyclerView.setAdapter(upAdapter);
    }
    private void setUpInfo() {
        uparrayList.add(new UpcomingData("목감천 라이딩해요", "최대 4명", "2021.01.31"));
        uparrayList.add(new UpcomingData("동양공전 근처에서 만나요", "최대 4명", "2021.01.31"));

        upAdapter.setUpInfo(uparrayList);
    }*/
}