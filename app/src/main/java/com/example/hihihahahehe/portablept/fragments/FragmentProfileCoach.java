package com.example.hihihahahehe.portablept.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.events.OnclickProfile;
import com.example.hihihahahehe.portablept.models.JSONModel.DataLoginJSON;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by SMC on 09/26/2017.
 */

public class FragmentProfileCoach extends Fragment {
    @BindView(R.id.iv_avata_userprofile)
    ImageView imageAvata;
    @BindView(R.id.bt_back)
    Button btBack;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_number_student)
    TextView tvNumberStudent;
    @BindView(R.id.tv_number_start)
    TextView tvNumberStart;
    @BindView(R.id.tv_number_rank)
    TextView tvNumberRank;


    public FragmentProfileCoach() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userprofile,container,false);
        setUpUI(view);

        return view;
    }

    private void setUpUI(View view) {
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
    }
    @Subscribe(sticky = true)
    public void LoadDataCoach(OnclickProfile onclickProfile){
        DataLoginJSON dataLoginJSON = onclickProfile.getDataUser();
        Picasso.with(getContext()).load(dataLoginJSON.getImgAvata()).transform(new CropCircleTransformation()).into(imageAvata);
        tvName.setText(dataLoginJSON.getName());
    }
}
