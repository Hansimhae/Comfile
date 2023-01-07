package com.example.comfile.Frag;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.comfile.R;

public class FragShare extends Fragment {
    private View view;

    private String TAG = "프래그먼트";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        view = inflater.inflate(R.layout.frag_share, container, false);

        Intent sendIntent = new Intent(Intent.ACTION_SEND);

        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.addCategory(Intent.CATEGORY_DEFAULT);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "ComFile 상품 공유"); //getString 써서 상품명 받아오기 하기
        sendIntent.setType("text/plain");

        startActivity(Intent.createChooser(sendIntent, "앱 선택"));

        return view;
    }
}
