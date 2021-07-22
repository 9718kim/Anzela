package com.anzela.myapplication1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CustomViewHolder>{

    private ArrayList<Comments> Comments;

    public CommentAdapter(ArrayList<Comments> arrayList) {
        this.Comments = arrayList;
    }

    @NonNull
    @NotNull
    @Override
    public CommentAdapter.CustomViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CommentAdapter.CustomViewHolder holder, int position) {

        holder.comId = Comments.get(position).getId();
        holder.content.setText(Comments.get(position).getContent());
        holder.depth = Comments.get(position).depth;
        holder.regDate.setText(Comments.get(position).getRegDate());
        holder.userId.setText(Comments.get(position).getUser().getUid());

        String imageUrl = Comments.get(position).getUser().profileUrl;
        Glide.with(holder.itemView.getContext()).load(imageUrl).into(holder.Image);

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return Comments.size();
    }

    public void setUpInfo(ArrayList<Comments> data) {
        Comments = data;
        notifyDataSetChanged();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected int comId;
        protected TextView content;
        protected int depth;
        protected TextView regDate;
        protected CircleImageView Image;
        protected TextView userId;

        public CustomViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            this.content = (TextView) itemView.findViewById(R.id.commenttext);
            this.regDate = (TextView) itemView.findViewById(R.id.commentdate);
            this.Image = (CircleImageView) itemView.findViewById(R.id.commentimg);
            this.userId = (TextView) itemView.findViewById(R.id.commentid);
        }
    }
}
