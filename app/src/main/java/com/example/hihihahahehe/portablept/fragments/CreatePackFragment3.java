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

/**
 * Created by valky on 10/28/2017.
 */

public class CreatePackFragment3 extends Fragment {
    //fragment_create_pack_4
    @BindView(R.id.tv_address_hint)
    TextView tvAddressHint;
    @BindView(R.id.edt_address)
    EditText edtAddress;

    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.edt_content)
    TextView edt_content;

    @BindView(R.id.bt_create_4)
    TextView btCreate4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_pack_3, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
