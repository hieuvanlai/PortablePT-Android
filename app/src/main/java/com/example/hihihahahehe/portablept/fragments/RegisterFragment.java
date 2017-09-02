package com.example.hihihahahehe.portablept.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.databases.RealmHandleAccount;
import com.example.hihihahahehe.portablept.models.FaceBookModel;
import com.example.hihihahahehe.portablept.models.JSONModel.LoginAndRegisterResponseJSON;
import com.example.hihihahahehe.portablept.models.JSONModel.UserJSONModel;
import com.example.hihihahahehe.portablept.networks.RetrofitFactory;
import com.example.hihihahahehe.portablept.networks.services.RegisterAccout;
import com.example.hihihahahehe.portablept.utils.ScreenManager;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hieuv on 8/9/2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = RegisterFragment.class.toString();
    @BindView(R.id.et_full_name)
    EditText et_full_name;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_user)
    EditText et_user;
    @BindView(R.id.et_pass)
    EditText et_pass;
    @BindView(R.id.et_re_pass)
    EditText et_re_pass;
    @BindView(R.id.et_mobile)
    EditText et_mobile;
    @BindView(R.id.et_birth_day)
    EditText et_birth_day;
    @BindView(R.id.tv_dont_register)
    TextView tv_dont_register;
    @BindView(R.id.bt_sign_up)
    Button bt_sign_up;
    @BindView((R.id.iv_avatar))
    ImageView iv_avata;
    private String avata="http://lutz.donnerhacke.de/extension/ezdemo/design/ezdemo/images/user.gif";
    public RegisterFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_account, container, false);
        setUpUI(view);
        return view;
    }

    public void setUpUI(View view){
        EventBus.getDefault().register(this);
        ButterKnife.bind(this,view);
        Picasso.with(getContext())
                .load(avata)
                .into(iv_avata);
        bt_sign_up.setOnClickListener(this);
        tv_dont_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.tv_dont_register:
                ScreenManager.replaceFragment(getActivity().getSupportFragmentManager(),new LoginFragment(),R.id.layout_container_main,false);
                break;
            case R.id.bt_sign_up:
                creatAccout();
                break;

        }
    }

    public void creatAccout() {

        if (    et_birth_day.getText().toString().equals("")
                ||et_email.getText().toString().equals("")
                ||et_mobile.getText().toString().equals("")
                ||et_full_name.getText().toString().equals("")
                ||et_pass.getText().toString().equals("")
                ||et_re_pass.getText().toString().equals("")
                ||et_user.getText().toString().equals("")
                )
        {
            Toast.makeText(getActivity(), "Bạn Nhập Thiếu Trường,Vui Lòng Nhập Lại", Toast.LENGTH_SHORT).show();
            return;
        };

        if (et_pass.getText().toString().length()<8||et_re_pass.getText().toString().length()<8){
            Toast.makeText(getActivity(), "Mật Khẩu Tối Thiểu 8 Ký Tự,Vui Lòng Nhập Lại", Toast.LENGTH_SHORT).show();
            return;
        }

        if (et_pass.getText().toString().equals(et_re_pass.getText().toString())){

        }else{
            Toast.makeText(getActivity(), "Nhập Lại Mật Khẩu Không Đúng,Vui Lòng Nhập Lại", Toast.LENGTH_SHORT).show();
            return;
        }

        UserJSONModel userModel = new UserJSONModel();
        userModel.setBirthday(et_birth_day.getText().toString());
        userModel.setEmail(et_email.getText().toString());
        userModel.setName(et_full_name.getText().toString());
        userModel.setPassword(et_pass.getText().toString());
        userModel.setPhoneNumber(et_mobile.getText().toString());
        userModel.setUsername(et_user.getText().toString());
        userModel.setImgAvata(avata);
        final RegisterAccout  registerAccout = RetrofitFactory.getInstance().create(RegisterAccout.class);
        registerAccout.creatAccout(userModel).enqueue(new Callback<LoginAndRegisterResponseJSON>() {
            @Override
            public void onResponse(Call<LoginAndRegisterResponseJSON> call, Response<LoginAndRegisterResponseJSON> response) {
                LoginAndRegisterResponseJSON registerResponseJSON = response.body();
                if (registerResponseJSON==null){
                    Toast.makeText(getActivity(), "Could not parse body", Toast.LENGTH_SHORT).show();
                }else {

                    if (registerResponseJSON.getMessage().equals("Register failed, duplicate user")){
                        Toast.makeText(getActivity(),"Tài Khoản Đã Tồn Tại ", Toast.LENGTH_SHORT).show();
                    }else{
                        RealmHandleAccount.deleteAccount();
                        RealmHandleAccount.addAccount(registerResponseJSON);
                        ScreenManager.openFragment(getActivity().getSupportFragmentManager(), new RoleFragment(),R.id.layout_container_main, false);
                    }


                }


            }

            @Override
            public void onFailure(Call<LoginAndRegisterResponseJSON> call, Throwable t) {
                Toast.makeText(getActivity(), String.format("Đăng Nhập Thất Bại /n %s", t), Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Subscribe(sticky = true)
    public  void onRecivedFacebook (FaceBookModel faceBookModel){
        avata=faceBookModel.getImg();
        Picasso.with(getContext())
                .load(faceBookModel.getImg())
                .into(iv_avata);

        et_full_name.setText(faceBookModel.getLast_Name()+ " "+ faceBookModel.getFirst_Name());
        et_email.setText(faceBookModel.getEmail());
        et_birth_day.setText(faceBookModel.getBirthday());
    }
}
