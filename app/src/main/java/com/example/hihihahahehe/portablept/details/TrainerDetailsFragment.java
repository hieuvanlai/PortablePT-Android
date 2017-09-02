package com.example.hihihahahehe.portablept.details;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.models.FaceBookModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainerDetailsFragment extends Fragment {
    private FaceBookModel faceBookModel;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_age_hint)
    TextView tvAge;
    @BindView(R.id.tv_exp_hint)
    TextView tvExp;
    @BindView(R.id.tv_address_hint)
    TextView tvAddress;
    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;

    public TrainerDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trainer_details, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);

        loadData();
    }

    private void loadData() {
//        if (RealmHandle.getData() != null) {
//            faceBookModel = RealmHandle.getData();
//            Picasso.with(getContext()).load(faceBookModel.getImg()).transform(new CropCircleTransformation()).into(ivAvatar);
//            String firstName;
//            String lastName;
//            if (faceBookModel.getFirst_Name() == null) {
//                firstName = "";
//            } else firstName = faceBookModel.getFirst_Name();
//
//            if (faceBookModel.getLast_Name() == null) {
//                lastName = "";
//            } else lastName = faceBookModel.getLast_Name();
//            tvName.setText(lastName + " " + firstName);
//        }
//    }
}}
