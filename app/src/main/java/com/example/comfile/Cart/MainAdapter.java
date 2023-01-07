package com.example.comfile.Cart;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.comfile.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder>{
    private ArrayList<MainData> arrayList;
    private Context context;

    public MainAdapter(ArrayList<MainData> arrayList, Context context) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_productcontainer, parent, false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {

        Glide.with(holder.itemView)
                .load(arrayList.get(position).getprofile())
                .into(holder.profile);

        holder.name.setText(arrayList.get(position).getName());
        holder.price.setText(arrayList.get(position).getPrice());
        holder.filter.setText(arrayList.get(position).getFilter());
        }


    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void remove(int position) {
        try {
            arrayList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView profile;
        protected TextView name, price, filter;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.profile = itemView.findViewById(R.id.iv_img);
            this.name = itemView.findViewById(R.id.name);
            this.price = itemView.findViewById(R.id.price);
            this.filter = itemView.findViewById(R.id.filter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currentPos = getAdapterPosition();
                    MainData md = arrayList.get(currentPos);

                    Intent intent = new Intent(context, Pactivity.class);
                    intent.putExtra("image",arrayList.get(currentPos).getprofile());
                    intent.putExtra("name",arrayList.get(currentPos).getName());
                    intent.putExtra("price",arrayList.get(currentPos).getPrice());
                    intent.putExtra("URL",arrayList.get(currentPos).getURL());
                    context.startActivity(intent);
            }
        });
    }

    }

}


