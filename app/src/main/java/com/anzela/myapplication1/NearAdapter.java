package com.anzela.myapplication1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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
        holder.NearTitle.setText(NeararrayList.get(position).getNearTitle());
        holder.NearTeam.setText(NeararrayList.get(position).getNearTeam());
        holder.NearDate.setText(NeararrayList.get(position).getNearDate());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curTitle = holder.NearTitle.getText().toString();
                Toast.makeText(v.getContext(), curTitle, Toast.LENGTH_SHORT).show();

                // id 값으로 모임 상세 페이지 호출
            }
        });
    }

    @Override
    public int getItemCount() {
        return NeararrayList.size();
    }

    public void NearsetUpInfo(ArrayList<NearData> data) {
        NeararrayList = data;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

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
