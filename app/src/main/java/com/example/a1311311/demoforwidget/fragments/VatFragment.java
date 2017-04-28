package com.example.a1311311.demoforwidget.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.a1311311.demoforwidget.R;
import com.example.a1311311.demoforwidget.widget.VatView;


/**
 * Created by 1311311 on 2017/4/27.╰(￣▽￣)╮
 */

public class VatFragment extends Fragment {

    private SeekBar mSeekBar;
    private VatView mVatView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_vat, container, false);
        mSeekBar = (SeekBar) inflate.findViewById(R.id.sb_seek);
        mVatView = (VatView) inflate.findViewById(R.id.vv_cat);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mVatView.setPresent(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return inflate;
    }
}
