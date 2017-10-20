package com.example.hihihahahehe.portablept.fragments;


import android.media.CamcorderProfile;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.adapters.PackAdapter;
import com.example.hihihahahehe.portablept.databases.RealmHandleAccount;
import com.example.hihihahahehe.portablept.fragments.typeofpacks.DetailFragmentTest;
import com.example.hihihahahehe.portablept.models.FaceBookModel;
import com.example.hihihahahehe.portablept.models.JSONModel.GetPackJSONModel;
import com.example.hihihahahehe.portablept.models.JSONModel.MyPackJSONModel;
import com.example.hihihahahehe.portablept.models.PackModel;
import com.example.hihihahahehe.portablept.networks.RetrofitFactory;
import com.example.hihihahahehe.portablept.networks.services.GetMyPack;
import com.example.hihihahahehe.portablept.networks.services.GetMyPackHLV;
import com.example.hihihahahehe.portablept.utils.ScreenManager;

import org.greenrobot.eventbus.EventBus;

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
public class ManagerPackFragment extends Fragment {
    private FragmentManager fm;

    private List<PackModel> hotPackModelList = new ArrayList<>();
    private PackAdapter hotPackAdapter;

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
        loadData();
        return view;
    }




    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        if (RealmHandleAccount.getAccount().getData().getRole().equals("HV")){
            fabAddPack.setVisibility(View.INVISIBLE);
        }
        hotPackAdapter = new PackAdapter(hotPackModelList, getContext(), view);
        final LinearLayoutManager linearLayoutManagerPacks = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvPacks.setLayoutManager(linearLayoutManagerPacks);

        hotPackAdapter = new PackAdapter(hotPackModelList, getContext(), view);
        rvPacks.setAdapter(hotPackAdapter);
        setOnClickItem();
        hotPackAdapter.setOnclikListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackModel  packModel = (PackModel) view.getTag();
                EventBus.getDefault().postSticky(packModel);
                ScreenManager.replaceFragment(getActivity().getSupportFragmentManager(), new DetailFragmentTest(), R.id.layout_container, true);
            }
        });
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
    private void  loadData(){
        if (RealmHandleAccount.getAccount().getData().getRole().equals("HV")) {

            GetMyPack getMyPack = RetrofitFactory.getInstance().create(GetMyPack.class);
            getMyPack.getPacks(RealmHandleAccount.getAccount().getData().getId()).enqueue(new Callback<List<MyPackJSONModel>>() {
                @Override
                public void onResponse(Call<List<MyPackJSONModel>> call, Response<List<MyPackJSONModel>> response) {
                    hotPackModelList.clear();
                    if (response.body()!=null){
                        for (MyPackJSONModel myPackJSONModel : response.body()){
                            PackModel hotPackModel = new PackModel();
                            hotPackModel.setCost(myPackJSONModel.getPack().getPrice());
                            hotPackModel.setDuration(myPackJSONModel.getPack().getDuration());
                            hotPackModel.setPackName(myPackJSONModel.getPack().getPackName());
                            hotPackModel.setGoal(myPackJSONModel.getPack().getPurpose());
                            hotPackModel.setImg(myPackJSONModel.getPack().getPackImgUrl());
                            hotPackModel.setCoach(myPackJSONModel.getPack().getCoach());
                            hotPackModel.setContent(myPackJSONModel.getPack().getContent());
                            hotPackModel.setId(myPackJSONModel.getPack().getId());
                            hotPackModel.setType(myPackJSONModel.getPack().getType());
                            hotPackModel.setContent(myPackJSONModel.getPack().getContent());

                            if(myPackJSONModel.getPack().getTotalStars() != null && myPackJSONModel.getPack().getVotedStars() != null){
                                hotPackModel.setStars((int)(myPackJSONModel.getPack().getTotalStars().intValue()/ myPackJSONModel.getPack().getVotedStars().intValue()));
                            }

                            hotPackModelList.add(hotPackModel);
                        }
                        hotPackAdapter.notifyDataSetChanged();

                    }
                }


                @Override
                public void onFailure(Call<List<MyPackJSONModel>> call, Throwable t) {


                }
            });
        }
        if (RealmHandleAccount.getAccount().getData().getRole().equals("HLV")){

            GetMyPackHLV getMyPackHLV = RetrofitFactory.getInstance().create(GetMyPackHLV.class);
            getMyPackHLV.getPacks(RealmHandleAccount.getAccount().getData().getId()).enqueue(new Callback<List<GetPackJSONModel>>() {
                @Override
                public void onResponse(Call<List<GetPackJSONModel>> call, Response<List<GetPackJSONModel>> response) {
                    hotPackModelList.clear();
                    if (response.body()!=null){

                        for(GetPackJSONModel packJSONModel : response.body()){
                            PackModel hotPackModel = new PackModel();

                            hotPackModel.setCoachName(packJSONModel.getCoach().getName());

                            hotPackModel.setCost(packJSONModel.getPrice());
                            hotPackModel.setDuration(packJSONModel.getDuration());
                            hotPackModel.setPackName(packJSONModel.getPackName());
                            hotPackModel.setGoal(packJSONModel.getPurpose());
                            hotPackModel.setImg(packJSONModel.getPackImgUrl());
                            hotPackModel.setCoach(packJSONModel.getCoach());
                            hotPackModel.setType(packJSONModel.getType());
                            hotPackModel.setContent(packJSONModel.getContent());
                            hotPackModel.setContent(packJSONModel.getContent());
                            hotPackModel.setId(packJSONModel.getId());
                            if(packJSONModel.getTotalStars() != null && packJSONModel.getVotedStars() != null){
                                hotPackModel.setStars((int)(packJSONModel.getTotalStars().intValue()/packJSONModel.getVotedStars().intValue()));
                            }
                            hotPackModelList.add(hotPackModel);
                        }
                        hotPackAdapter.notifyDataSetChanged();

                    }
                }

                @Override
                public void onFailure(Call<List<GetPackJSONModel>> call, Throwable t) {
                    Log.d("Test",t.toString());


                }
            });

        }


    }

}
