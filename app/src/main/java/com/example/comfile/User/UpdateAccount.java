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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class UpdateAccount extends AppCompatActivity {

    private EditText update_email, update_pwd, update_name, update_PhoneNum, update_Address;
    private Button btn_updateAccount;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseRef;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    HashMap<String, Object> childUpdates = null;
    Map<String, Object> userValue = null;
    UserAccount userInfo = null;

    //
    String newEmail;
    String newPassword;
    String newName;
    String newPhoneNum;
    String newAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        update_email = findViewById(R.id.update_email);
        update_pwd = findViewById(R.id.update_pwd);
        update_name = findViewById(R.id.update_name);
        update_PhoneNum = findViewById(R.id.update_PhoneNum);
        update_Address = findViewById(R.id.update_Address);

        update_PhoneNum.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        // ?????? ?????? ????????????
        if (user != null) {
            // ????????????????????? ??????
            mFirebaseAuth = FirebaseAuth.getInstance();
            mDatabaseRef = database.getReference("UserAccount");
            mDatabaseRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    // DB?????? ???????????? ???????????? ????????? ????????????.
                    UserAccount userAccount = snapshot.getValue(UserAccount.class);
                    String currentEmail = userAccount.getEmailId();
                    String currentPwd = userAccount.getPassword();
                    String currentName = userAccount.getName();
                    String currentPhoneNum = userAccount.getPhoneNum();
                    String currentAddress = userAccount.getAddress();

                    // DB?????? ????????? ???????????? EditText??? ????????????.
                    update_email.setText(currentEmail);
                    update_pwd.setText(currentPwd);
                    update_name.setText(currentName);
                    update_PhoneNum.setText(currentPhoneNum);
                    update_Address.setText(currentAddress);

                    // ?????????????????? ??????
                    btn_updateAccount = findViewById(R.id.btn_updateAccount);
                    btn_updateAccount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            mFirebaseAuth.updateCurrentUser(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    // ????????? ????????? ????????????.
                                    newEmail = update_email.getText().toString();
                                    newPassword = update_pwd.getText().toString();
                                    newName = update_name.getText().toString();
                                    newPhoneNum = update_PhoneNum.getText().toString();
                                    newAddress = update_Address.getText().toString();

                                    if (task.isSuccessful()) {
                                        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                        UserAccount account = new UserAccount();
                                        account.setIdToken(firebaseUser.getUid());
                                        account.setEmailId(firebaseUser.getEmail());
                                        account.setPassword(newPassword);
                                        account.setName(newName);
                                        account.setPhoneNum(newPhoneNum);
                                        account.setAddress(newAddress);
                                        
                                        // setValue : Database??? ??????
                                        mDatabaseRef.child(firebaseUser.getUid()).setValue(account);
                                        Toast.makeText(UpdateAccount.this, "?????????????????? ??????!!", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                            finish();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void updateUserInfo(String email, String password, String name, String phoneNum, String address) {
        //String key = mDatabase.child("UserAccount").push().getKey();
        String key = mDatabase.child("UserAccount").child(user.getUid()).push().getKey();
        UserAccount userInfo = new UserAccount(email, password, name, phoneNum, address);
        Map<String, Object> userValues = userInfo.toMap();

        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("/UserAccount/" + key, userValues);
        userUpdate.put("/UserAccount/" + email + "/" + key, userValues);
//        userUpdate.put("/UserAccount/" + key, userValues);
//        userUpdate.put("/UserAccount/" + key, userValues);
    }
}