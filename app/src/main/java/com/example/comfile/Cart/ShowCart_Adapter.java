package com.example.comfile.Cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comfile.R;

import java.util.List;

public class ShowCart_Adapter extends RecyclerView.Adapter<ShowCart_Adapter.ViewHolder> {

    private Context context;
    private List<ShowCart_Model> showCart_models;

    public ShowCart_Adapter(Context context, List<ShowCart_Model> showCart_models){
        this.context = context;
        this.showCart_models = showCart_models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_counter,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ShowCart_Model showCart_model = showCart_models.get(position);
        holder.name.setText(showCart_model.getProduct_Name());
        holder.price.setText(showCart_model.getPrice());

    }


    @Override
    public int getItemCount() {
        return showCart_models.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name;
        private TextView price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.pName);
            price = itemView.findViewById(R.id.pPrice);
        }
    }
}
