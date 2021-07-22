package com.anzela.myapplication1;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anzela.myapplication1.Activity.DetailActivity;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class NearAdapter extends RecyclerView.Adapter<NearAdapter.CustomViewHolder>{

    private ArrayList<NearData> NeararrayList;

    public NearAdapter(ArrayList<NearData> arrayList) {
        this.NeararrayList = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public NearAdapter.CustomViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.neardatalist, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NearAdapter.CustomViewHolder holder, int position) {

        holder.id = NeararrayList.get(position).getId();
        holder.NearTitle.setText(NeararrayList.get(position).getNearTitle());
        holder.NearTeam.setText(NeararrayList.get(position).getNearTeam());
        holder.NearDate.setText(NeararrayList.get(position).getNearDate());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idnum = holder.id;

                // id 값으로 모임 상세 페이지 호출
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("idnum", idnum);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return NeararrayList.size();
    }

//    public void NearsetUpInfo(ArrayList<NearData> data) {
//        NeararrayList = data;
//        notifyDataSetChanged();
//    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected int id;
        protected TextView NearTitle;
        protected TextView NearTeam;
        protected TextView NearDate;

        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.NearTitle = (TextView) itemView.findViewById(R.id.nearrvtitle);
            this.NearTeam = (TextView) itemView.findViewById(R.id.nearrvteam);
            this.NearDate = (TextView) itemView.findViewById(R.id.nearrvdate);
        }
    }
}
