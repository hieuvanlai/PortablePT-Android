package com.example.hihihahahehe.portablept.fragments.typeofpacks;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.adapters.PackAdapter;
import com.example.hihihahahehe.portablept.models.FaceBookModel;
import com.example.hihihahahehe.portablept.models.JSONModel.PackJSONModel;
import com.example.hihihahahehe.portablept.models.PackModel;
import com.example.hihihahahehe.portablept.networks.RetrofitFactory;
import com.example.hihihahahehe.portablept.networks.services.GetPacks;
import com.example.hihihahahehe.portablept.utils.RealmHandle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstTypeFragment extends Fragment {
    private List<PackModel> packModelList = new ArrayList<>();
    private FaceBookModel faceBookModel;
    private PackAdapter packAdapter;
    @BindView(R.id.rv_packs)
    RecyclerView rvPacks;

    public FirstTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_type, container, false);
        setupUI(view);
        loadData();
        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        packAdapter = new PackAdapter(packModelList, getContext());
        rvPacks.setAdapter(packAdapter);

        rvPacks.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void loadData() {
        final GetPacks getPacks = RetrofitFactory.getInstance().create(GetPacks.class);
        faceBookModel = RealmHandle.getData();
        getPacks.getPacks().enqueue(new Callback<List<PackJSONModel>>() {
            @Override
            public void onResponse(Call<List<PackJSONModel>> call, Response<List<PackJSONModel>> response) {
                if (response.body() != null) {
                    for (PackJSONModel packJSONModel : response.body()) {
                        PackModel packModel = new PackModel();
                        packModel.setPackName(packJSONModel.getPackName());
                        packModel.setCoachName(faceBookModel.getLast_Name() + " " + faceBookModel.getFirst_Name());
                        packModel.setType(packJSONModel.getPurpose());
                        packModel.setPrice(packJSONModel.getPrice());
                        packModel.setDuration(packJSONModel.getDuration());
                        packModel.setImageUrl(packJSONModel.getPackImgUrl());

                        packModelList.add(packModel);
                    }
                    packAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<PackJSONModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Không thể kết nối đến server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
