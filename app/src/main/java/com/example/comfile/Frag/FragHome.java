package com.example.comfile.Frag;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comfile.Cart.MainAdapter;
import com.example.comfile.Cart.MainData;
import com.example.comfile.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragHome extends Fragment {

    private View view;
    private Toolbar toolbar;
    private String TAG = "프래그먼트";
    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Button search;
    private FirebaseDatabase database;
    private DatabaseReference dbRef;
    private RecyclerView.Adapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        view = inflater.inflate(R.layout.frag_home, container, false);

        recyclerView = view.findViewById(R.id.RecyclerViewHome);
        search= view.findViewById(R.id.Search);

        EditText editText = view.findViewById(R.id.editsearch);

        linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        mainAdapter = new MainAdapter(arrayList, getContext());
        recyclerView.setAdapter(mainAdapter);

       database = FirebaseDatabase.getInstance();

       dbRef = database.getReference("Com");

       dbRef.addListenerForSingleValueEvent (new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               arrayList.clear();
               for(DataSnapshot snapshot1 : snapshot.getChildren()){
                   MainData mainData = snapshot1.getValue(MainData.class);
                   arrayList.add(mainData);
               }
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Log.e("MainActivity", String.valueOf(error.toException()));
           }
       });

       search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String s = editText.getText().toString().toUpperCase();
               Query query = dbRef.orderByChild("name").equalTo(s);

               query.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       arrayList.clear();
                       for(DataSnapshot snapshot1 : snapshot.getChildren()){
                           MainData mainData = snapshot1.getValue(MainData.class);
                           arrayList.add(mainData);
                       }
                       adapter.notifyDataSetChanged();
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {
                       Log.e("MainActivity", String.valueOf(error.toException()));
                   }
               });

           }
       });

       adapter = new MainAdapter(arrayList,getContext());
       recyclerView.setAdapter(adapter);

        return view;
    }
}