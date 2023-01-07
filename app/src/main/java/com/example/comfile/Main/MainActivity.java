package com.example.comfile.Main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.comfile.Frag.FragHome;
import com.example.comfile.Frag.FragMypage;
import com.example.comfile.Frag.FragSetting;
import com.example.comfile.Frag.FragShare;
import com.example.comfile.Frag.FragShop;
import com.example.comfile.R;
import com.example.comfile.Util.ReviewActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private String TAG = "메인";

    //프래그먼트 변수
    FragHome fragment_home;
    FragShare fragment_share;
    FragSetting fragment_setting;
    FragShop fragment_shop;
    FragMypage fragment_mypage;

    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment_home = new FragHome();
        fragment_share = new FragShare();
        fragment_setting = new FragSetting();
        fragment_mypage = new FragMypage();
        fragment_shop = new FragShop();

        // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // 초기 플래그먼트 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_home).commitAllowingStateLoss();

        // 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i(TAG, "바텀 네비게이션 클릭");

                switch (item.getItemId()){
                    case R.id.home:
                        Log.i(TAG, "home 들어옴");
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment_home).commitAllowingStateLoss();
                        return true;
                    case R.id.share:
                        Log.i(TAG, "share 들어옴");
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment_share).commitAllowingStateLoss();
                        return true;
                    case R.id.setting:
                        Log.i(TAG, "setting 들어옴");
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment_setting).commitAllowingStateLoss();
                        return true;

                    case R.id.shopping:
                        Log.i(TAG, "shop 들어옴");
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment_shop).commitAllowingStateLoss();
                        return true;

                    case R.id.mypage:
                        Log.i(TAG, "my 들어옴");
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment_mypage).commitAllowingStateLoss();
                        return true;
                }
                return true;
            }
        });

    }

//    동현씨 코드, 리뷰

    private Object dialog;

    public void onBackPressed() {
        AlertDialog.Builder aDialog = new AlertDialog.Builder(MainActivity.this);

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        aDialog.setMessage("앱을 평가해주세요!");

        aDialog.setPositiveButton("다음에", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
            }
        });

        aDialog.setNegativeButton("평가하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), ReviewActivity.class);
                startActivity(intent);
            }
        });

        aDialog.setTitle("컴파일(Comfile)");
        aDialog.show();
    }
}