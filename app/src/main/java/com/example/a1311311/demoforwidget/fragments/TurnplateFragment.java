package com.example.a1311311.demoforwidget.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1311311.demoforwidget.R;


/**
 * Created by 1311311 on 2017/4/27.╰(￣▽￣)╮
 */

public class TurnplateFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_turnplate, container, false);
        return inflate;
    }
}
