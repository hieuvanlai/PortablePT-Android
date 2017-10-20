package com.example.hihihahahehe.portablept.fragments.typeofpacks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.databases.RealmHandleAccount;
import com.example.hihihahahehe.portablept.fragments.FragmentProfileCoach;
import com.example.hihihahahehe.portablept.models.JSONModel.MassegeResponseJSON;
import com.example.hihihahahehe.portablept.models.JSONModel.RegisterPackJSON;
import com.example.hihihahahehe.portablept.models.PackModel;
import com.example.hihihahahehe.portablept.networks.RetrofitFactory;
import com.example.hihihahahehe.portablept.networks.services.RegisterPack;
import com.example.hihihahahehe.portablept.utils.ScreenManager;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hieuit on 10/18/17.
 */

public class DetailFragmentTest extends Fragment {
    @BindView(R.id.tv_pack_name)
    TextView tv_pack_name;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.iv_banner)
    ImageView iv_banner;
    @BindView(R.id.iv_avatar)
    ImageView iv_avatar;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.bt_register_pack)
    TextView bt_register_pack;
    @BindView(R.id.rb_pack_rating)
    RatingBar rbPackRating;
    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_coach)
    TextView tv_coach;
    @BindView(R.id.tv_duration)
    TextView tv_duration;
    PackModel packModel;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_test, container,false);
        setupUI(view);
        return view;
    }
    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        setOnClickItem();
        if (RealmHandleAccount.getAccount()!=null){
            if (RealmHandleAccount.getAccount().getData().getRole().equals("HLV")){
                bt_register_pack.setVisibility(View.INVISIBLE);
            }
        }
        bt_register_pack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bt_register_pack.getText().toString().equals("Đăng Ký")){
                    RegisterPackJSON registerPackJSON = new RegisterPackJSON();
                    registerPackJSON.setUser(RealmHandleAccount.getAccount().getData().getId());
                    registerPackJSON.setPack(packModel.getId());
                    RegisterPack registerPack = RetrofitFactory.getInstance().create(RegisterPack.class);
                    registerPack.register(registerPackJSON).enqueue(new Callback<MassegeResponseJSON>() {
                        @Override
                        public void onResponse(Call<MassegeResponseJSON> call, Response<MassegeResponseJSON> response) {
                            MassegeResponseJSON massegeResponseJSON = response.body();
                            Log.d("TEST",massegeResponseJSON.getMessage()+" "+RealmHandleAccount.getAccount().getData().getId()+" "+packModel.getId());
                            if (massegeResponseJSON.getMessage().equals("Register Data OK")){
                                Toast.makeText(getActivity(), "Đã Đăng Ký Gói Tập,Chờ Phản Hồi Của HLV", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MassegeResponseJSON> call, Throwable t) {
                            Toast.makeText(getActivity(), "Lỗi Mạng", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
    }
    private void setOnClickItem() {
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });
        iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                OnclickProfile data = new OnclickProfile();
//                data.setDataUser(packModel.getCoach());
//                EventBus.getDefault().postSticky(data);
              ScreenManager.openFragment(getActivity().getSupportFragmentManager(),new FragmentProfileCoach(),R.id.layout_container,true);



            }
        });
    }

    @Subscribe(sticky = true)
    public  void onRecivedFacebook (PackModel packModel){
        this.packModel = packModel;
        Picasso.with(getActivity()).load(this.packModel.getImg()).into(iv_banner);
        Picasso.with(getActivity())
                .load(this.packModel.getCoach().getImgAvata()).transform( new CropCircleTransformation())
                .into(iv_avatar);
        tv_content.setText(this.packModel.getContent());
        tv_coach.setText(this.packModel.getCoach().getName());
        tv_pack_name.setText(this.packModel.getPackName());
        tv_type.setText(this.packModel.getType());
        tv_price.setText(this.packModel.getCost());
        rbPackRating.setProgress(packModel.getStars());
        tv_duration.setText(packModel.getDuration());

    }
}
