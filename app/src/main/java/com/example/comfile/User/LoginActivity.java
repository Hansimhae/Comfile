package com.example.comfile.User;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.comfile.Main.MainActivity;
import com.example.comfile.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;     // 파이버베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText editEmail, editPwd;
    private Button btn_login;

    private CheckBox cb_save;
    private Context mContext;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("TestFirebase");

        editEmail = findViewById(R.id.et_email);
        editPwd = findViewById(R.id.et_pwd);
        btn_login = findViewById(R.id.btn_login);

        cb_save = (CheckBox) findViewById(R.id.cb_save);

        boolean boo = PreferenceManager.getBoolean(mContext, "check");
        //로그인 정보 기억하기 체크 유무 확인
        if (boo) {
            // 체크가 되어있다면 아래 코드를 수행
            // 저장된 아이디와 암호를 가져와 셋팅한다.
            editEmail.setText(PreferenceManager.getString(mContext, "id"));
            editPwd.setText(PreferenceManager.getString(mContext, "pw"));
            cb_save.setChecked(true); //체크박스는 여전히 체크 표시 하도록 셋팅

//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            // 저장한 키 값으로 저장된 아이디와 암호를 불러와 String 값에 저장
//            String checkId = PreferenceManager.getString(mContext, "id");
//            String checkPw = PreferenceManager.getString(mContext, "pw"); //아이디와 암호가 비어있는 경우를 체크
//            if (TextUtils.isEmpty(checkId) || TextUtils.isEmpty(checkPw)) { //아이디나 암호 둘 중 하나가 비어있으면 토스트메시지를 띄운다
//                Toast.makeText(LoginActivity.this, "아이디 또는 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
//            } else { //둘 다 충족하면 다음 동작을 구현해놓음
//                intent.putExtra("id", checkId);
//                intent.putExtra("pw", checkPw);
//                startActivity(intent);
//            }
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //로그인 버튼 눌렀을 때 동작 //아이디 암호 입력창에서 텍스트를 가져와 PreferenceManager에 저장함
                PreferenceManager.setString(mContext, "id", editEmail.getText().toString()); //id라는 키값으로 저장
                PreferenceManager.setString(mContext, "pw", editPwd.getText().toString()); //pw라는 키값으로 저장
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                // 저장한 키 값으로 저장된 아이디와 암호를 불러와 String 값에 저장
                String checkId = PreferenceManager.getString(mContext, "id");
                String checkPw = PreferenceManager.getString(mContext, "pw"); //아이디와 암호가 비어있는 경우를 체크
                if (TextUtils.isEmpty(checkId) || TextUtils.isEmpty(checkPw)) { //아이디나 암호 둘 중 하나가 비어있으면 토스트메시지를 띄운다
                    Toast.makeText(LoginActivity.this, "아이디 또는 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else { //둘 다 충족하면 다음 동작을 구현해놓음
                    intent.putExtra("id", checkId);
                    intent.putExtra("pw", checkPw);
                    //startActivity(intent);
                }

                String strEmail = editEmail.getText().toString();
                String strPwd = editPwd.getText().toString();
                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            //
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                            startActivity(intent);
                            finish(); // 로그인 액티비티 제거
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //로그인 기억하기 체크박스 유무에 따른 동작 구현
        cb_save.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) { // 체크박스 체크 되어 있으면 //editText에서 아이디와 암호 가져와 PreferenceManager에 저장한다.
                    PreferenceManager.setString(mContext, "id", editEmail.getText().toString()); //id 키값으로 저장
                    PreferenceManager.setString(mContext, "pw", editPwd.getText().toString()); //pw 키값으로 저장
                    PreferenceManager.setBoolean(mContext, "check", cb_save.isChecked()); //현재 체크박스 상태 값 저장
                } else { //체크박스가 해제되어있으면
                    PreferenceManager.setBoolean(mContext, "check", cb_save.isChecked()); //현재 체크박스 상태 값 저장
                    PreferenceManager.clear(mContext); //로그인 정보를 모두 초기화
                }
            }
        });


        // 로그인 버튼
//        btn_login = findViewById(R.id.btn_login);
//        btn_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String strEmail = editEmail.getText().toString();
//                String strPwd = editPwd.getText().toString();
//
//                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // 로그인 성공
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(intent);
//                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
//                            finish(); // 로그인 액티비티 제거
//                        }
//                        else {
//                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });

        // 비밀번호 찾기 버튼
        Button btn_findPW = findViewById(R.id.btn_findPW);
        btn_findPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), findPasswordActivity.class);
                startActivity(intent);
            }
        });

        // 회원가입 버튼
        Button btn_go_register = findViewById(R.id.btn_go_register);
        btn_go_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}