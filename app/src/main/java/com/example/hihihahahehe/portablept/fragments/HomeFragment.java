package com.example.hihihahahehe.portablept.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.adapters.HotCoachesAdapter;
import com.example.hihihahahehe.portablept.adapters.HotSportsAdapter;
import com.example.hihihahahehe.portablept.adapters.PackAdapter;
import com.example.hihihahahehe.portablept.models.HotCoachesModel;
import com.example.hihihahahehe.portablept.models.HotSportsModel;
import com.example.hihihahahehe.portablept.models.JSONModel.DataLoginJSON;
import com.example.hihihahahehe.portablept.models.JSONModel.GetPackJSONModel;
import com.example.hihihahahehe.portablept.models.JSONModel.SportsJSONModel;
import com.example.hihihahahehe.portablept.models.PackModel;
import com.example.hihihahahehe.portablept.networks.RetrofitFactory;
import com.example.hihihahahehe.portablept.networks.services.GetAllPacks;
import com.example.hihihahahehe.portablept.networks.services.GetCoach;
import com.example.hihihahahehe.portablept.networks.services.GetSports;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

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
public class HomeFragment extends Fragment {
    private RecyclerView rvHotCoaches;
    private RecyclerView rvHotSports;
    private RecyclerView rvHotPacks;
    private static final String TAG = "HomeFragment";

    CarouselView carouselView;
    int[] sampleImages = {R.drawable.sample_avatar, R.drawable.sample_sports};

    @BindView(R.id.tv_hot_sports)
    TextView tvHotSports;
    @BindView(R.id.tv_hot_packs)
    TextView tvHotPacks;

    private List<HotCoachesModel> hotCoachesModelList = new ArrayList<>();
    private List<HotSportsModel> hotSportsModelList = new ArrayList<>();
    private List<PackModel> hotPackModelList = new ArrayList<>();

    private HotCoachesAdapter hotCoachesAdapter;
    private HotSportsAdapter hotSportsAdapter;
    private PackAdapter hotPackAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvHotCoaches = (RecyclerView) view.findViewById(R.id.rv_hot_coaches);
        rvHotSports = (RecyclerView) view.findViewById(R.id.rv_hot_sports);
        rvHotPacks = (RecyclerView) view.findViewById(R.id.rv_hot_packs);

        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        };

        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);

        loadData();
        setupUI(view);

        return view;
    }

    private void loadData() {
        final GetAllPacks getAllPacks = RetrofitFactory.getInstance().create(GetAllPacks.class);
        getAllPacks.getAllPacks().enqueue(new Callback<List<GetPackJSONModel>>() {
            @Override
            public void onResponse(Call<List<GetPackJSONModel>> call, Response<List<GetPackJSONModel>> response) {

                for(GetPackJSONModel packJSONModel : response.body()){
                    PackModel hotPackModel = new PackModel();
                    hotPackModel.setCoachName(packJSONModel.getCoach().getName());
                    hotPackModel.setPrice(packJSONModel.getPrice());
                    hotPackModel.setDuration(packJSONModel.getDuration());
                    hotPackModel.setPackName(packJSONModel.getPackName());
                    hotPackModel.setType(packJSONModel.getPurpose());
                    hotPackModel.setImg(packJSONModel.getPackImgUrl());

                    if(packJSONModel.getTotalStars() != null && packJSONModel.getVotedStars() != null){
                        hotPackModel.setStars((int)(packJSONModel.getTotalStars().intValue()/packJSONModel.getVotedStars().intValue()));
                    }
                    Log.d("TEST",packJSONModel.getPrice());
                    hotPackModelList.add(hotPackModel);
                }

                hotPackAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<GetPackJSONModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load hot coaches into HomeFragment", Toast.LENGTH_SHORT);
            }
        });
        GetCoach getCoach = RetrofitFactory.getInstance().create(GetCoach.class);
        getCoach.getCoach().enqueue(new Callback<List<DataLoginJSON>>() {
            @Override
            public void onResponse(Call<List<DataLoginJSON>> call, Response<List<DataLoginJSON>> response) {
                for(DataLoginJSON coachJSONModel : response.body()){
                    HotCoachesModel hotCoachesModel = new HotCoachesModel();
                    hotCoachesModel.setName(coachJSONModel.getName());
                    hotCoachesModel.setAvata(coachJSONModel.getImgAvata());
                    hotCoachesModelList.add(hotCoachesModel);
                }
                hotCoachesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<DataLoginJSON>> call, Throwable t) {

            }
        });

        final GetSports getSports = RetrofitFactory.getInstance().create(GetSports.class);
        getSports.getSports().enqueue(new Callback<List<SportsJSONModel>>() {
            @Override
            public void onResponse(Call<List<SportsJSONModel>> call, Response<List<SportsJSONModel>> response) {
                for(SportsJSONModel sportsJSONModel : response.body()){
                    HotSportsModel hotSportsModel = new HotSportsModel();
                    hotSportsModel.setName(sportsJSONModel.getSportsName());
                    hotSportsModel.setImageURL(sportsJSONModel.getImageURL());
                    hotSportsModelList.add(hotSportsModel);
                }

                EventBus.getDefault().postSticky(hotSportsModelList);

                hotSportsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<SportsJSONModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to load hot sports into HomeFragment", Toast.LENGTH_SHORT);
            }
        });
    }


    private void setupUI(View view) {
        ButterKnife.bind(this, view);


        hotCoachesAdapter = new HotCoachesAdapter(hotCoachesModelList, getContext());
        rvHotCoaches.setAdapter(hotCoachesAdapter);

        hotSportsAdapter = new HotSportsAdapter(hotSportsModelList, getContext());
        rvHotSports.setAdapter(hotSportsAdapter);

        hotPackAdapter = new PackAdapter(hotPackModelList, getContext(), view);
        rvHotPacks.setAdapter(hotPackAdapter);

        final GridLayoutManager gridLayoutManagerCoaches = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        final GridLayoutManager gridLayoutManagerSports = new GridLayoutManager(getContext(), 2 , GridLayoutManager.HORIZONTAL, false);
        final LinearLayoutManager linearLayoutManagerPacks = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        rvHotCoaches.setLayoutManager(gridLayoutManagerCoaches);
        rvHotSports.setLayoutManager(gridLayoutManagerSports);
        rvHotPacks.setLayoutManager(linearLayoutManagerPacks);
    }
}
