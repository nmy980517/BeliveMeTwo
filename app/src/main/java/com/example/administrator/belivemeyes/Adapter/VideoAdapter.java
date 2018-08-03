package com.example.administrator.belivemeyes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.belivemeyes.Bean.JavaBean;
import com.example.administrator.belivemeyes.PlayActivity;
import com.example.administrator.belivemeyes.R;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private Context context;
    private List<JavaBean.MessageBean.AnchorsBean> lists;

    public VideoAdapter(Context context, List<JavaBean.MessageBean.AnchorsBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.video_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JavaBean.MessageBean.AnchorsBean anchorsBean = lists.get(position);

        String name = anchorsBean.getName();
        String pic51 = anchorsBean.getPic51();
        final String roomid = anchorsBean.getRoomid();

        Picasso.with(context).load(pic51).into(holder.mIv);
        holder.mTv.setText(name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PlayActivity.class);//跳转到直播界面
                intent.putExtra("roomid",roomid);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mIv;
        private TextView mTv;
        public ViewHolder(View itemView) {
            super(itemView);
            mIv = itemView.findViewById(R.id.video_log);
            mTv = itemView.findViewById(R.id.videoTitle);
        }
    }
}
