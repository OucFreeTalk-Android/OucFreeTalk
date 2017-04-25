package com.lovingrabbit.www.oucfreetalk.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lovingrabbit.www.oucfreetalk.R;

/**
 * Created by 17922 on 2017/4/25.
 */

public class AFragment extends Fragment
{
    private static final String ARG_C = "content";

    public static AFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString(ARG_C,content);
        AFragment fragment = new AFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.from(getContext()).inflate(R.layout.activity_main,container,false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.main_recyview);
//        String content = getArguments().getString(ARG_C);
//        TextView textView = new TextView(getContext());
//        textView.setTextSize(30);
//        textView.setGravity(Gravity.CENTER);
//        textView.setText("Test\n\n" +content);
//        textView.setBackgroundColor(0xFFececec);
        return recyclerView;
    }
}
