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

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.CustomViewHolder>{

    private ArrayList<UpcomingData> arrayList;

    public UpcomingAdapter(ArrayList<UpcomingData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public UpcomingAdapter.CustomViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcominglist, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UpcomingAdapter.CustomViewHolder holder, int position) {
        holder.Title.setText(arrayList.get(position).getTitle());
        holder.Team.setText(arrayList.get(position).getTeam());
        holder.Date.setText(arrayList.get(position).getDate());

        holder.itemView.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curTitle = holder.Title.getText().toString();
                Toast.makeText(v.getContext(), curTitle, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setUpInfo(ArrayList<UpcomingData> data) {
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView Title;
        protected TextView Team;
        protected TextView Date;

        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.Title = (TextView) itemView.findViewById(R.id.rvtitle);
            this.Team = (TextView) itemView.findViewById(R.id.rvteam);
            this.Date = (TextView) itemView.findViewById(R.id.rvdate);
        }
    }
}
