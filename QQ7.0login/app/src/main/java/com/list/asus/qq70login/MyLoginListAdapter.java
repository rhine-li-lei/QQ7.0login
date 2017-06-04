package com.list.asus.qq70login;

/*
 * Created by ASUS on 2017/6/4.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.list.asus.qq70login.bean.UserInfo;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.list.asus.qq70login.MainActivity.currentSelectedPosition;

public class MyLoginListAdapter extends RecyclerView.Adapter<MyLoginListAdapter.ViewHolder>{

    private ArrayList<UserInfo> list = new ArrayList<>();

    public MyLoginListAdapter(ArrayList<UserInfo> arrayList){
        list = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loginlist, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                list.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.qqNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentSelectedPosition = holder.getAdapterPosition();
                int position = holder.getAdapterPosition();
                UserInfo userInfo = list.get(position);
                Toast.makeText(v.getContext(), "你点击了"+userInfo.getQqNum(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserInfo userInfo = list.get(position);
        holder.avatar.setImageResource(userInfo.getAvatar());
        holder.qqNum.setText(userInfo.getQqNum());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        View userView;
        CircleImageView avatar;
        TextView qqNum;
        ImageButton delete;
        public ViewHolder(View itemView) {
            super(itemView);
            userView = itemView;
            avatar = (CircleImageView) itemView.findViewById(R.id.login_userPhoto);
            qqNum = (TextView) itemView.findViewById(R.id.login_userQQ);
            delete = (ImageButton) itemView.findViewById(R.id.login_deleteButton);
        }
    }

}
