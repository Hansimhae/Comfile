package com.example.comfile.Frag;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comfile.R;
import com.example.comfile.Cart.ShowCart_Adapter;
import com.example.comfile.Cart.ShowCart_Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragShop extends Fragment {
    private View view;
    private RecyclerView rec;
    private ArrayList<ShowCart_Model> showCart_models = new ArrayList<>();
    private Button buy;
    private ShowCart_Adapter adepter;
    private String TAG = "프래그먼트";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        view = inflater.inflate(R.layout.frag_shop, container, false);

        rec=view.findViewById(R.id.res);
        rec.setLayoutManager(new LinearLayoutManager(getContext()));
        buy=view.findViewById(R.id.jangDelete);

        DatabaseReference show_info = FirebaseDatabase.getInstance().getReference("AddToCart");

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_info.removeValue();
                Toast.makeText(getContext(), "장바구니가 비워젔습니다", Toast.LENGTH_SHORT).show();
            }

        });

        show_info.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    showCart_models.clear();
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        ShowCart_Model showCart_model = snapshot1.getValue(ShowCart_Model.class);
                        showCart_models.add(showCart_model);
                    }
                    rec.setAdapter(new ShowCart_Adapter(getContext(),showCart_models));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        return view;
    }

}
