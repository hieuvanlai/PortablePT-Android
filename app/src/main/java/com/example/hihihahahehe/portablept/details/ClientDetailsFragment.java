package com.example.hihihahahehe.portablept.details;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.databases.RealmHandleAccout;
import com.example.hihihahahehe.portablept.models.FaceBookModel;
import com.example.hihihahahehe.portablept.models.JSONModel.DataLoginJSON;
import com.example.hihihahahehe.portablept.models.JSONModel.LoginAndRegisterResponseJSON;
import com.example.hihihahahehe.portablept.models.JSONModel.MassegeResponseJSON;
import com.example.hihihahahehe.portablept.networks.RetrofitFactory;
import com.example.hihihahahehe.portablept.networks.services.UpdateRole;
import com.example.hihihahahehe.portablept.networks.services.UpdateUser;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    @BindView(R.id.tv_updateAccout)
    TextView tv_update;


    public ClientDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_profile, container, false);
        initView(view);
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtBirthDay.getText().toString().equals("")||edt_phone.getText().toString().equals("")||edtEmail.getText().toString().equals("")||edtName.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Bạn Nhập Thiếu Trường,\n Vui Lòng Nhập Lại", Toast.LENGTH_SHORT).show();
                    return;
                }
                DataLoginJSON dataLoginJSON   =  new DataLoginJSON();
                dataLoginJSON.setId(RealmHandleAccout.getAccout().getData().getId());
                dataLoginJSON.setImgAvata(RealmHandleAccout.getAccout().getData().getImgAvata());
                dataLoginJSON.setBirthday(edtBirthDay.getText().toString());
                dataLoginJSON.setPhoneNumber(edt_phone.getText().toString());
                dataLoginJSON.setEmail(edtEmail.getText().toString());
                dataLoginJSON.setName(edtName.getText().toString());
                UpdateUser updateUser = RetrofitFactory.getInstance().create(UpdateUser.class);
                updateUser.update(dataLoginJSON).enqueue(new Callback<MassegeResponseJSON>() {
                    @Override
                    public void onResponse(Call<MassegeResponseJSON> call, Response<MassegeResponseJSON> response) {

                    }

                    @Override
                    public void onFailure(Call<MassegeResponseJSON> call, Throwable t) {

                    }
                });

            }
        });
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