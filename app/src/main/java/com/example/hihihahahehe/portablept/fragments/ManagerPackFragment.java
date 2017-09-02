package com.example.hihihahahehe.portablept.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.databases.RealmHandleAccount;
import com.example.hihihahahehe.portablept.models.FaceBookModel;
import com.example.hihihahahehe.portablept.models.PackModel;
import com.example.hihihahahehe.portablept.utils.ScreenManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManagerPackFragment extends Fragment {
    private FragmentManager fm;
    private List<PackModel> packModelList = new ArrayList<>();
    private FaceBookModel faceBookModel;
//    private PackAdapter packAdapter;

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.fab_add_pack)
    FloatingActionButton fabAddPack;
    @BindView(R.id.rv_packs)
    RecyclerView rvPacks;

    public ManagerPackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manager_pack, container, false);

        setupUI(view);
//        loadData();
        return view;
    }




    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        if (RealmHandleAccount.getAccount().getData().getRole().equals("HV")){
            fabAddPack.setVisibility(View.INVISIBLE);
        }
        setOnClickItem();
    }

    private void setOnClickItem() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        fabAddPack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManager.openFragment(getActivity().getSupportFragmentManager(), new CreatePackFragment(), R.id.layout_container, true);
            }
        });
    }

}
