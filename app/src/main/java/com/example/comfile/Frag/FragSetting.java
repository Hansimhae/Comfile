package com.example.comfile.Frag;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.comfile.User.LoginActivity;
import com.example.comfile.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class FragSetting extends Fragment {
    private View view;

    private String TAG = "프래그먼트";

    FirebaseAuth mFirebaseAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        view = inflater.inflate(R.layout.frag_setting, container, false);

        //공지사항
        Button bt_notice = (Button) view.findViewById(R.id.bt_공지사항);
        bt_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"^ㅡ^ 개발 중인 서비스입니다 ", Toast.LENGTH_SHORT).show();
            }
        });

        //고객센터
        Button bt_ccs = (Button) view.findViewById(R.id.bt_고객센터);
        bt_ccs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "개발 중인 서비스입니다 ",Toast.LENGTH_SHORT).show();
            }
        });

        //버전정보
        Button bt_ver = (Button) view.findViewById(R.id.bt_버전정보);
        bt_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "최신 버전입니다",Toast.LENGTH_SHORT).show();
            }
        });

        //개발자 정보
        Button bt_developInfo = (Button) view.findViewById(R.id.bt_개발자);
        bt_developInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "ComFile은 4인 개발 쇼핑몰 앱입니다.",Toast.LENGTH_SHORT).show();
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
                        Log.i(TAG,"로그아웃");
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


        //회원탈퇴

        Button bt_bye = (Button) view.findViewById(R.id.bt_회원탈퇴);
        bt_bye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getContext());
                alert_confirm.setTitle("확인").setMessage("정말 계정을 삭제하시겠습니까?");

                alert_confirm.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mFirebaseAuth.getCurrentUser().delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(), "성공",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "실패",Toast.LENGTH_SHORT).show();
                            }
                        });

                        Toast.makeText(getActivity(),"삭제되었습니다", Toast.LENGTH_SHORT).show();
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
}
