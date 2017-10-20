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
import com.example.hihihahahehe.portablept.events.PushDaTaCoaches;
import com.example.hihihahahehe.portablept.events.PushDataPacks;
import com.example.hihihahahehe.portablept.events.PushDataSports;
import com.example.hihihahahehe.portablept.fragments.typeofpacks.DetailFragmentTest;
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
import com.example.hihihahahehe.portablept.utils.ScreenManager;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
        setupUI(view);

        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        carouselView = (CarouselView) view.findViewById(R.id.carouselView);
        rvHotCoaches = (RecyclerView) view.findViewById(R.id.rv_hot_coaches);
        rvHotSports = (RecyclerView) view.findViewById(R.id.rv_hot_sports);
        rvHotPacks = (RecyclerView) view.findViewById(R.id.rv_hot_packs);
        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        };



        carouselView.setPageCount(sampleImages.length);

        carouselView.setImageListener(imageListener);
        hotCoachesAdapter = new HotCoachesAdapter(hotCoachesModelList, getContext());
        rvHotCoaches.setAdapter(hotCoachesAdapter);

        hotSportsAdapter = new HotSportsAdapter(hotSportsModelList, getContext());
        rvHotSports.setAdapter(hotSportsAdapter);

        hotPackAdapter = new PackAdapter(hotPackModelList, getContext(), view);
        rvHotPacks.setAdapter(hotPackAdapter);

        hotPackAdapter.setOnclikListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PackModel  packModel = (PackModel) view.getTag();
                EventBus.getDefault().postSticky(packModel);
                ScreenManager.replaceFragment(getActivity().getSupportFragmentManager(), new DetailFragmentTest(), R.id.layout_container, true);
            }
        });

        final GridLayoutManager gridLayoutManagerCoaches = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        final GridLayoutManager gridLayoutManagerSports = new GridLayoutManager(getContext(), 2 , GridLayoutManager.HORIZONTAL, false);
        final LinearLayoutManager linearLayoutManagerPacks = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        rvHotCoaches.setLayoutManager(gridLayoutManagerCoaches);
        rvHotSports.setLayoutManager(gridLayoutManagerSports);
        rvHotPacks.setLayoutManager(linearLayoutManagerPacks);
    }
    @Subscribe(sticky = true)
    public void  loadPacks(PushDataPacks pushDataPacks){
        hotPackModelList= pushDataPacks.hotPackModelList;
        hotPackAdapter.notifyDataSetChanged();

    }
    @Subscribe(sticky = true)
    public void  loadCoach(PushDaTaCoaches pushDaTaCoaches){
        hotCoachesModelList= pushDaTaCoaches.hotCoachesModelList;

        hotCoachesAdapter.notifyDataSetChanged();

    }
    @Subscribe(sticky = true)
    public void  loadSports(PushDataSports pushDataSports){
        hotSportsModelList= pushDataSports.hotSportsModelList;
        hotSportsAdapter.notifyDataSetChanged();

    }




}
