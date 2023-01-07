package com.example.comfile.Frag;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.comfile.R;
import com.example.comfile.User.LoginActivity;
import com.example.comfile.User.UpdateAccount;
import com.example.comfile.User.UserAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

public class FragMypage extends Fragment {

    private TextView updateProfileMSG;
    private static final int RESULT_OK = 0 ;
    private View view;

    private String TAG = "프래그먼트";
    private FirebaseAuth mFirebaseAuth;

    FragShop fragshop;
    FragMypage fragMypage;

    //프사
    private final int GALLERY_CODE = 10;
    FirebaseStorage storage = FirebaseStorage.getInstance();

    //상태메세지
    private FirebaseAuth mFirebaseAuthMSG;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseRef;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup view =(ViewGroup) inflater.inflate(R.layout.frag_mypage, container, false);

        fragshop = new FragShop();

        //프로필 사진

        ImageView profile_image = view.findViewById(R.id.profile_imageview);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        StorageReference pathReference = storageReference.child("photo");

        storageReference.child("photo/1.png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getActivity()).load(uri).into(profile_image);
                Log.d("됏냐?", "Log");
            }
        });

//        if(pathReference == null){
//            Toast.makeText(getActivity(), "저장소에 사진이 없음", Toast.LENGTH_SHORT).show();
//        }
//        else{
//            StorageReference submitProfile = storageReference.child("photo/1.jpg");
//            submitProfile.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//                    Glide.with(getActivity()).load(uri).into(profile_image);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//
//                }
//            });
//        }

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert_photo = new AlertDialog.Builder(getActivity());
                alert_photo.setTitle("업로드할 이미지를 선택하세요");

                alert_photo.setNeutralButton("앨범 선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (v.getId()){
                            case R.id.profile_imageview:
                                loadAlbum();
                                break;
                        }
                    }
                });

                alert_photo.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "취소", Toast.LENGTH_SHORT).show();
                    }
                });
                alert_photo.show();
            }
        });


        // 프로필 상태 메시지
        updateProfileMSG = (TextView) view.findViewById(R.id.sm_View);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = database.getReference("UserAccount");
        mDatabaseRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount userAccount = snapshot.getValue(UserAccount.class);
                String currentEmail = userAccount.getEmailId();
                String currentPwd = userAccount.getPassword();
                String currentName = userAccount.getName();
                String currentPhoneNum = userAccount.getPhoneNum();
                String currentAddress = userAccount.getAddress();
                String currentProfileMSG = userAccount.getProfileMSG();

                updateProfileMSG.setText(currentProfileMSG);

                Button smButton =(Button) view.findViewById((R.id.button_sm));
                smButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mFirebaseAuth.updateCurrentUser(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());

                                ad.setMessage("상태메세지를 입력해주세요");
                                ad.setTitle("상태메세지 입력");

                                final EditText et = new EditText(getContext());
                                ad.setView(et);

                                ad.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick (DialogInterface dialog, int which) {
                                        String result = et.getText().toString();
                                        if (task.isSuccessful()) {
                                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                                            UserAccount account = new UserAccount();

                                            account.setIdToken(firebaseUser.getUid());
                                            account.setEmailId(firebaseUser.getEmail());
                                            account.setPassword(currentPwd);
                                            account.setName(currentName);
                                            account.setPhoneNum(currentPhoneNum);
                                            account.setAddress(currentAddress);
                                            account.setProfileMSG(result);

                                            mDatabaseRef.child(firebaseUser.getUid()).setValue(account);
                                        }
                                        updateProfileMSG.setText(result);
                                        dialog.dismiss();
                                    }
                                });

                                ad.setNegativeButton("취소", new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick (DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                ad.show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //개인정보수정
        Button bt_edit = (Button) view.findViewById(R.id.bt_edit_myinfo);
        bt_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateAccount.class);
                startActivity(intent);

            }
        });


        //리뷰
        Button bt_review = (Button) view.findViewById(R.id.bt_리뷰);
        bt_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "아직 지원하지 않는 기능입니다", Toast.LENGTH_SHORT).show();
            }
        });


        //로그아웃
        mFirebaseAuth = FirebaseAuth.getInstance();

        Button btn_logout = (Button) view.findViewById(R.id.bt_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getActivity());
                alert_confirm.setTitle("확인").setMessage("로그아웃하시겠습니까?");

                alert_confirm.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mFirebaseAuth.signOut();
                        Toast.makeText(getActivity(),"로그아웃", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }
                });

                alert_confirm.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "취소", Toast.LENGTH_SHORT).show();
                    }
                });
                alert_confirm.show();

            }
        });
        return view;
    }

    private void loadAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, GALLERY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_CODE){
            Uri file = data.getData();
            StorageReference storageRef = storage.getReference();

            StorageReference riversRef = storageRef.child("photo/1.png");
            UploadTask uploadTask = riversRef.putFile(file);
            try{
                InputStream in = getActivity().getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                ImageView profile_image = (ImageView) view.findViewById(R.id.profile_imageview);
                profile_image.setImageBitmap(img);
            }catch (Exception e){
                e.printStackTrace();
            }

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "사진이 정상적으로 업로드 되지 않음", Toast.LENGTH_SHORT).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //Glide.with(getActivity()).load(taskSnapshot).into(profile_image);
                    Toast.makeText(getActivity(), "정상적으로 업로드 됨", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}