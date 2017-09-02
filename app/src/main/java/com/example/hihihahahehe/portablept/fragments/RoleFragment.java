package com.example.hihihahahehe.portablept.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.databases.RealmHandleAccount;
import com.example.hihihahahehe.portablept.models.JSONModel.MassegeResponseJSON;
import com.example.hihihahahehe.portablept.models.JSONModel.RoleJSON;
import com.example.hihihahahehe.portablept.networks.RetrofitFactory;
import com.example.hihihahahehe.portablept.networks.services.UpdateRole;
import com.example.hihihahahehe.portablept.utils.ScreenManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by valky on 8/7/2017.
 */

public class RoleFragment extends Fragment {
    private FragmentManager fm;

    @BindView(R.id.tv_trainer)
    TextView tvTrainer;

    @BindView(R.id.tv_trainee)
    TextView tvTrainee;

    public RoleFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roles, container, false);

        setupUI(view);
        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        onClick();
    }

    private void onClick() {
        fm = getActivity().getSupportFragmentManager();

        tvTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoleJSON roleJSON = new RoleJSON();
                roleJSON.setId(RealmHandleAccount.getAccount().getData().getId());
                roleJSON.setRole("HV");
                updateRole(roleJSON);
            }
        });

        tvTrainee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoleJSON roleJSON = new RoleJSON();
                roleJSON.setId(RealmHandleAccount.getAccount().getData().getId());
                roleJSON.setRole("HLV");
                updateRole(roleJSON);
            }
        });

    }
    private void updateRole(final RoleJSON roleJSON){
        UpdateRole updateRole = RetrofitFactory.getInstance().create(UpdateRole.class);
        updateRole.updateRole(roleJSON).enqueue(new Callback<MassegeResponseJSON>() {
            @Override
            public void onResponse(Call<MassegeResponseJSON> call, Response<MassegeResponseJSON> response) {
                MassegeResponseJSON massegeResponseJSON =response.body();
                if (massegeResponseJSON.getMessage().equals("Update OK")){
                    RealmHandleAccount.UpdateAccount(roleJSON.getRole());
                    fm = getActivity().getSupportFragmentManager();
                    ScreenManager.replaceFragment(fm,
                            new FirstScreenFragment(), R.id.layout_container_main, false);
                }
            }

            @Override
            public void onFailure(Call<MassegeResponseJSON> call, Throwable t) {

            }
        });
    }
}
