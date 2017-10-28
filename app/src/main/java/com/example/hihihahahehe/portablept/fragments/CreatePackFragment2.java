package com.example.hihihahahehe.portablept.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;

/**
 * Created by valky on 10/28/2017.
 */

public class CreatePackFragment2 extends Fragment {
    private static final String TAG = "CreatePackFragment2";

    //fragment_create_pack_3
    @BindView(R.id.tv_des_hint)
    TextView tvDesHint;
    @BindView(R.id.tv_set_type)
    TextView tvSetType;
    @BindView(R.id.bt_create_3)
    TextView btCreate3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout_fragment_create_pack_3, container, false);
        return view;
    }
}
