package com.lovingrabbit.www.oucfreetalk.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lovingrabbit.www.oucfreetalk.MainActivity;
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view;
//        container.removeView();
        String content = getArguments().getString(ARG_C);
        Log.v("content:",content);
        switch (content){
            case "0":
                view = inflater.from(getContext()).inflate(R.layout.mian_recycler, container, false);
                return view;
            case "1":
                view = inflater.from(getContext()).inflate(R.layout.test, container, false);
                return view;
            case "2":
                view = inflater.from(getContext()).inflate(R.layout.test, container, false);
                return view;
            default:
                view = inflater.from(getContext()).inflate(R.layout.test, container, false);
                return view;
        }
    }
}
