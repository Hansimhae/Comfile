package com.example.comfile.User;

import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.comfile.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;     // 파이버베이스 인증
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private EditText editEmail, editPwd, editName, editPhoneNum, editAddress;
    private Button btn_register, btn_register_complete, btn_checkEmail, btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        mFirebaseAuth.useAppLanguage();

        editEmail = findViewById(R.id.et_email);
        editPwd = findViewById(R.id.et_pwd);
        editName = findViewById(R.id.edit_name);
        editPhoneNum = findViewById(R.id.edit_PhoneNum);
        editAddress = findViewById(R.id.edit_Address);
        btn_register = findViewById(R.id.btn_register);
        btn_register_complete = findViewById(R.id.btn_register_complete);
        //btn_checkEmail = findViewById(R.id.btn_checkEmail);
        btn_close = findViewById(R.id.btn_close);

        // 회원가입 버튼 활성화 및 보이기 & 회원가입 버튼 비활성화 및 숨기기
        btn_register.setVisibility(View.VISIBLE);
        btn_register.setEnabled(true);
        btn_register_complete.setVisibility(View.INVISIBLE);
        btn_register_complete.setEnabled(false);

        editPhoneNum.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 회원가입 시작
                String strEmail = editEmail.getText().toString();
                String strPwd = editPwd.getText().toString();
                String strName = editName.getText().toString();
                String strPhoneNum = editPhoneNum.getText().toString();
                String strAddress = editAddress.getText().toString();
                String strProfile = editName.getText().toString();;

//                if(strEmail.trim().equals("") || strPwd.trim().equals("") || strName.trim().equals("") ||
//                        strPhoneNum.trim().equals("") || strAddress.trim().equals("")) {
//                    Toast.makeText(RegisterActivity.this, "정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
//                }
                // Firebase Auth
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);
                            account.setName(strName);
                            account.setPhoneNum(strPhoneNum);
                            account.setAddress(strAddress);
                            account.setProfileMSG(strProfile);

                            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {                         //해당 이메일에 확인메일을 보냄
                                        Toast.makeText(RegisterActivity.this,
                                                firebaseUser.getEmail() + " 로 인증메일을 보냈습니다.",
                                                Toast.LENGTH_SHORT).show();
                                    } else {                                             //메일 보내기 실패
                                        Toast.makeText(RegisterActivity.this,
                                                "메일 보내기 실패",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            // setValue : Database에 추가
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
                            Toast.makeText(RegisterActivity.this, "환영합니다!", Toast.LENGTH_SHORT).show();

//                            AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
//                            dialog.setTitle("회원가입"); //제목
//                            dialog.setMessage("이메일 인증 후 회원가입을 완료해주세요."); // 메시지
//                            //dlg.setIcon(R.drawable.deum); // 아이콘 설정
//                            //버튼 클릭시 동작
//                            dialog.setPositiveButton("확인",new DialogInterface.OnClickListener(){
//                                public void onClick(DialogInterface dialog, int which) {
//                                    //토스트 메시지
//                                    //Toast.makeText(RegisterActivity.this,"메세지",Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                            dialog.show();

//                            //회원가입 버튼 비활성화 & 숨기기
//                            btn_register.setVisibility(View.INVISIBLE);
//                            btn_register.setEnabled(false);
//                            //회원가입완료 버튼 활성화 & 보이기
//                            btn_register_complete.setVisibility(View.VISIBLE);
//                            btn_register_complete.setEnabled(true);
//                            btn_register_complete.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    // setValue : Database에 추가
//                                    mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);
//                                    Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
//                                    // 화면 닫기
//                                    finish();
//                                }
//                            });
                        } else {
                            Toast.makeText(RegisterActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}