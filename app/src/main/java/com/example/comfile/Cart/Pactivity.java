package com.example.comfile.Cart;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.comfile.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Pactivity extends AppCompatActivity {
    private  static  final String TAG = "GalleryActivity";
    private ImageView iv;
    private TextView head,price;
    private Button button;
    private DatabaseReference DBref;
    private MainData mainData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jungbo1);
        DBref = FirebaseDatabase.getInstance().getReference("AddToCart");

        button = findViewById(R.id.bu);

        Intent intent = getIntent();

        String getName = intent.getStringExtra("name");
        String getPrice = intent.getStringExtra("price");
        String getURL = intent.getStringExtra("URL");

        iv = findViewById(R.id.image);
        head = (TextView) findViewById(R.id.head);
        price = (TextView) findViewById(R.id.price);

        head.setText(getName);
        price.setText(getPrice);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ID = DBref.push().getKey();
                HashMap<String, String> parameters = new HashMap<>();
                parameters.put("Product_Name",getName);
                parameters.put("price",getPrice);
                DBref.child(ID).setValue(parameters);

                //Intent intent1 = new Intent(Pactivity.this,FragShop.class);
                //startActivity(intent1);
                Toast.makeText(getApplicationContext(), "장바구니에 담겼습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        //자세히 보기

        Button bt_web = findViewById(R.id.webview);

        bt_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(getURL));
                startActivity(intent1);
            }
        });

    }

}
