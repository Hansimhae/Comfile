package com.example.comfile.Util;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.comfile.Main.MainActivity;
import com.example.comfile.R;

public class ReviewActivity extends AppCompatActivity {

    RatingBar ratingBar;
    TextView score;

    private Object dialog;
    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageview;

    private static final String TAG = "RatingBar";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

            score = findViewById(R.id.score);

        score.setText("0점");

            ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                    score.setText(rating + "점");


                }
        });

        EditText editText1 = (EditText) findViewById(R.id.titleEdit) ;

        EditText editText2 = (EditText) findViewById(R.id.reviewEdit) ;

        imageview = (ImageView)findViewById(R.id.reviewImageview);
        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent. setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);

        }

    }

    public void onBackPressed() {
        AlertDialog.Builder aDialog = new AlertDialog.Builder(ReviewActivity.this);

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        aDialog.setMessage("종료하시겠습니까?");

        aDialog.setPositiveButton("종료하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

        aDialog.setNegativeButton("메인으로", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        aDialog.setTitle("sdsds");
        aDialog.show();
    }

    public void Completed(View v) {
        AlertDialog.Builder eDialog = new AlertDialog.Builder(ReviewActivity.this);

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        eDialog.setMessage("리뷰 등록이 완료되었습니다.");

        eDialog.setPositiveButton("종료하기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                moveTaskToBack(true);
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        eDialog.setTitle("sdsds");
        eDialog.show();
    }
}

//앱 이름 : 다나와 참고 컨샙의 쇼핑몰 어플