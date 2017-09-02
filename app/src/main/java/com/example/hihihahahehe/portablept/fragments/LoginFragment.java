package com.example.hihihahahehe.portablept.fragments;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.databases.RealmHandleAccout;
import com.example.hihihahahehe.portablept.models.FaceBookModel;
import com.example.hihihahahehe.portablept.models.JSONModel.LoginAndRegisterResponseJSON;
import com.example.hihihahahehe.portablept.models.JSONModel.LoginJSON;
import com.example.hihihahahehe.portablept.networks.RetrofitFactory;
import com.example.hihihahahehe.portablept.networks.services.Login;
import com.example.hihihahahehe.portablept.utils.ScreenManager;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = LoginFragment.class.toString();
    @BindView(R.id.login_button)
    LoginButton loginButton;
    @BindView(R.id.tv_register)
    TextView tv_register;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.bt_login)
    Button bt_login;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private FaceBookModel faceBookModel;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        setupUI(view);

        // Add code to print out the key hash
        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo(
                    "com.example.hihihahahehe.portablept",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        if (RealmHandleAccout.getAccout()!=null){
            LoginAndRegisterResponseJSON loginAndRegisterResponseJSON = RealmHandleAccout.getAccout();

            if (loginAndRegisterResponseJSON.getData().getRole().equals("HLV")||loginAndRegisterResponseJSON.getData().getRole().equals("HV")){
                Log.d("ROLE",RealmHandleAccout.getAccout().getData().getRole());
                ScreenManager.replaceFragment(getActivity().getSupportFragmentManager(), new FirstScreenFragment(), R.id.layout_container_main, false);

            }
            else {
                ScreenManager.replaceFragment(getActivity().getSupportFragmentManager(), new RoleFragment(), R.id.layout_container_main, false);

            }

        }


        accessToken = AccessToken.getCurrentAccessToken();
        callbackManager = CallbackManager.Factory.create();

        loginButton.setReadPermissions("public_profile","email");
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization

        // Callback registration, check if not login before


            loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                    getInfoFacebook(loginResult);

                    ScreenManager.replaceFragment(getActivity().getSupportFragmentManager(), new RegisterFragment(), R.id.layout_container_main, false);
                }

                @Override
                public void onCancel() {
                    // App code
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                    Toast.makeText(getContext(), "There is no connection !", Toast.LENGTH_SHORT).show();
                }
            });

        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        tv_register.setOnClickListener(this);
        bt_login.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getInfoFacebook(LoginResult loginResult) {
        AccessToken accessToken = loginResult.getAccessToken();
        GraphRequest graphRequest = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                faceBookModel = new FaceBookModel();
                try {

                    faceBookModel.setId(object.getString("id"));
                    faceBookModel.setFirst_Name(object.getString("first_name"));
                    faceBookModel.setLast_Name(object.getString("last_name"));
                    faceBookModel.setGender(object.getString("gender"));
                    faceBookModel.setEmail(object.getString("email"));
                    if (object.has("birthday"))
                        faceBookModel.setBirthday(object.getString("birthday"));
                    faceBookModel.setImg(object.getJSONObject("picture").getJSONObject("data").getString("url"));
                    Log.d("test",faceBookModel.getFirst_Name()+faceBookModel.getBirthday()+faceBookModel.getLast_Name()+faceBookModel.getEmail());
                    EventBus.getDefault().postSticky(faceBookModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,gender,email,birthday,picture.type(normal)");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_register:
                ScreenManager.replaceFragment(getActivity().getSupportFragmentManager(), new RegisterFragment(), R.id.layout_container_main, false);
                break;
            case R.id.bt_login:
                login();
                break;
        }
    }

    private void login() {
        //TestTamDFix Sau
        if (et_password.getText().toString().equals("")||et_username.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Vui Lòng Nhập User Name và Password", Toast.LENGTH_SHORT).show();
        }
        else{
            LoginJSON  loginJSON = new LoginJSON();
            loginJSON.setUsername(et_username.getText().toString());
            loginJSON.setPassword(et_password.getText().toString());
            final Login login = RetrofitFactory.getInstance().create(Login.class);
            login.loginAccout(loginJSON).enqueue(new Callback<LoginAndRegisterResponseJSON>() {
                @Override
                public void onResponse(Call<LoginAndRegisterResponseJSON> call, Response<LoginAndRegisterResponseJSON> response) {
                    LoginAndRegisterResponseJSON loginAndRegisterResponseJSON = response.body();
                    if (loginAndRegisterResponseJSON==null){
                        Toast.makeText(getActivity(), "Could not parse body", Toast.LENGTH_SHORT).show();
                    }
                    if (loginAndRegisterResponseJSON.getMessage().equals("User not found")){
                        Toast.makeText(getActivity(), "Tài Khoản Không Tồn Tại", Toast.LENGTH_SHORT).show();
                    }
                    if (loginAndRegisterResponseJSON.getMessage().equals("Invalid password")){
                        Toast.makeText(getActivity(), "Bạn Nhập Sai Mật Khẩu", Toast.LENGTH_SHORT).show();
                    }
                    if (loginAndRegisterResponseJSON.getMessage().equals("Login OK")){
                        RealmHandleAccout.deleteAccout();
                        RealmHandleAccout.addAccout(loginAndRegisterResponseJSON);
                        if (loginAndRegisterResponseJSON.getData().getRole()!="HLV"||loginAndRegisterResponseJSON.getData().getRole()!="HV"){

                            ScreenManager.openFragment(getActivity().getSupportFragmentManager(), new RoleFragment(),R.id.layout_container_main, false);
                        }
                        ScreenManager.openFragment(getActivity().getSupportFragmentManager(), new FirstScreenFragment(),R.id.layout_container_main, false);
                    }
                }
                @Override
                public void onFailure(Call<LoginAndRegisterResponseJSON> call, Throwable t) {
                    Toast.makeText(getActivity(), "Lỗi Mạng Vui Lòng Thử Lại", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
