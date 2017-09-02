package com.example.hihihahahehe.portablept.details;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.databases.RealmHandleAccout;
import com.example.hihihahahehe.portablept.models.FaceBookModel;
import com.example.hihihahahehe.portablept.models.JSONModel.LoginAndRegisterResponseJSON;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientDetailsFragment extends Fragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_city)
    EditText edtCity;
    @BindView(R.id.edt_birth)
    EditText edtBirthDay;
    @BindView(R.id.edt_gen)
    EditText edtGen;
    @BindView(R.id.edt_phone)
    EditText edt_phone;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;


    public ClientDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_profile, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ButterKnife.bind(this, view);
        setOnClickItem();
        loadInfo();
    }

    public void loadInfo() {
        if(RealmHandleAccout.getAccout() != null){
            LoginAndRegisterResponseJSON loginAndRegisterResponseJSON = RealmHandleAccout.getAccout();
            Picasso.with(getContext()).load(loginAndRegisterResponseJSON.getData().getImgAvata()).transform(new CropCircleTransformation()).into(ivAvatar);
            edt_phone.setText(loginAndRegisterResponseJSON.getData().getPhoneNumber());
            edtName.setText(loginAndRegisterResponseJSON.getData().getName());
            edtEmail.setText(loginAndRegisterResponseJSON.getData().getEmail());
            edtBirthDay.setText(loginAndRegisterResponseJSON.getData().getBirthday());

        }
    }

    private void setOnClickItem() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });
    }
}