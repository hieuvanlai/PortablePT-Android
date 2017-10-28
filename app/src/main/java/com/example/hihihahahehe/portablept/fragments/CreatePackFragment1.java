package com.example.hihihahahehe.portablept.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by valky on 10/28/2017.
 */

public class CreatePackFragment1 extends Fragment{
    private static final String TAG = "CreatePackFragment1";
    //fragment_create_pack_2
    @BindView(R.id.edt_pack_name)
    EditText edtPackName;
    @BindView(R.id.tv_pack_name)
    TextView tvPackName;
    @BindView(R.id.bt_create_2)
    TextView btCreate2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout_fragment_create_pack_2, container , false);
        setupUI(view);
        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);
    }
}
