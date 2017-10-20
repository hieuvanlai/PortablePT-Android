package com.example.hihihahahehe.portablept;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hihihahahehe.portablept.databases.RealmHandleAccount;
import com.example.hihihahahehe.portablept.events.PushDaTaCoaches;
import com.example.hihihahahehe.portablept.events.PushDataPacks;
import com.example.hihihahahehe.portablept.events.PushDataSports;
import com.example.hihihahahehe.portablept.fragments.FirstScreenFragment;
import com.example.hihihahahehe.portablept.fragments.LoginFragment;
import com.example.hihihahahehe.portablept.fragments.RoleFragment;
import com.example.hihihahahehe.portablept.models.HotCoachesModel;
import com.example.hihihahahehe.portablept.models.HotSportsModel;
import com.example.hihihahahehe.portablept.models.JSONModel.DataLoginJSON;
import com.example.hihihahahehe.portablept.models.JSONModel.GetPackJSONModel;
import com.example.hihihahahehe.portablept.models.JSONModel.LoginAndRegisterResponseJSON;
import com.example.hihihahahehe.portablept.models.JSONModel.SportsJSONModel;
import com.example.hihihahahehe.portablept.models.PackModel;
import com.example.hihihahahehe.portablept.networks.RetrofitFactory;
import com.example.hihihahahehe.portablept.networks.services.GetAllPacks;
import com.example.hihihahahehe.portablept.networks.services.GetCoach;
import com.example.hihihahahehe.portablept.networks.services.GetSports;
import com.example.hihihahahehe.portablept.utils.ScreenManager;
import com.facebook.FacebookSdk;
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.toString();
    private List<HotCoachesModel> hotCoachesModelList = new ArrayList<>();
    private List<HotSportsModel> hotSportsModelList = new ArrayList<>();
    private List<PackModel> hotPackModelList= new ArrayList<>();
    boolean isLoadCoaches;
    boolean isLoadPacks;
    boolean isLoadSports;
    boolean isMoveFragment;
    private ImageView gifImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        gifImageView= (ImageView) findViewById(R.id.giv_loaddata);
        Glide.with(MainActivity.this).load(R.drawable.checkdata).asGif().placeholder(R.drawable.checkdata).crossFade().into(gifImageView);
        ReactiveNetwork.observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override public void accept(Boolean isConnectedToInternet) {
                        if (isConnectedToInternet){
                            Toast.makeText(MainActivity.this, "Internet Ok", Toast.LENGTH_SHORT).show();
                                loadData();

                        }else {
                            Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                                checkAccout();
                        }
                        // do something with isConnectedToInternet value
                    }
                });

    }
    private void checkAccout(){
        if (isMoveFragment==false){
            if (RealmHandleAccount.getAccount()!=null){
                LoginAndRegisterResponseJSON loginAndRegisterResponseJSON = RealmHandleAccount.getAccount();
                if (loginAndRegisterResponseJSON.getData().getRole().equals("HLV")||loginAndRegisterResponseJSON.getData().getRole().equals("HV")){
                    Log.d("ROLE", RealmHandleAccount.getAccount().getData().getRole());
                    ScreenManager.replaceFragment(getSupportFragmentManager(), new FirstScreenFragment(), R.id.layout_container_main, false);
                    isMoveFragment=true;
                }
                else {
                    ScreenManager.replaceFragment(getSupportFragmentManager(), new RoleFragment(), R.id.layout_container_main, false);
                    isMoveFragment=true;
                }

            }else {
                ScreenManager.replaceFragment(getSupportFragmentManager(), new LoginFragment(), R.id.layout_container_main, false);
                isMoveFragment=true;
            }
        }

    }
    private void check(){
        if (isLoadSports==true && isLoadCoaches==true && isLoadPacks==true){
            checkAccout();
        }
    }

    private void loadData() {
        //load pack
        if(isLoadPacks==false){
            final GetAllPacks getAllPacks = RetrofitFactory.getInstance().create(GetAllPacks.class);
            getAllPacks.getAllPacks().enqueue(new Callback<List<GetPackJSONModel>>() {
                @Override
                public void onResponse(Call<List<GetPackJSONModel>> call, Response<List<GetPackJSONModel>> response) {
                    hotPackModelList.clear();


                    for(GetPackJSONModel packJSONModel : response.body()){
                        PackModel hotPackModel = new PackModel();
                        hotPackModel.setCoachName(packJSONModel.getCoach().getName());
                        hotPackModel.setCost(packJSONModel.getPrice());
                        hotPackModel.setDuration(packJSONModel.getDuration());
                        hotPackModel.setPackName(packJSONModel.getPackName());
                        hotPackModel.setGoal(packJSONModel.getPurpose());
                        hotPackModel.setImg(packJSONModel.getPackImgUrl());
                        hotPackModel.setCoach(packJSONModel.getCoach());
                        hotPackModel.setContent(packJSONModel.getContent());
                        hotPackModel.setId(packJSONModel.getId());
                        hotPackModel.setType(packJSONModel.getType());
                        hotPackModel.setContent(packJSONModel.getContent());

                        if(packJSONModel.getTotalStars() != null && packJSONModel.getVotedStars() != null){
                            hotPackModel.setStars((int)(packJSONModel.getTotalStars().intValue()/packJSONModel.getVotedStars().intValue()));
                        }
                        hotPackModelList.add(hotPackModel);

                    }
                    Toast.makeText(MainActivity.this, "Load Pack Ok", Toast.LENGTH_SHORT);
                    PushDataPacks pushDataPacks = new PushDataPacks();
                    pushDataPacks.hotPackModelList= hotPackModelList;
                    EventBus.getDefault().postSticky(pushDataPacks);
                    isLoadPacks=true;
                    check();



                }

                @Override
                public void onFailure(Call<List<GetPackJSONModel>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed to load hot coaches into HomeFragment", Toast.LENGTH_SHORT);
                }
            });
        }


        //load coach
        if (isLoadCoaches==false){
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
                    Toast.makeText(MainActivity.this, "Load Coach Ok", Toast.LENGTH_SHORT);
                    PushDaTaCoaches pushDaTaCoaches = new PushDaTaCoaches();
                    pushDaTaCoaches.hotCoachesModelList=hotCoachesModelList;
                    EventBus.getDefault().postSticky(pushDaTaCoaches);
                    isLoadCoaches = true;
                    check();

                }

                @Override
                public void onFailure(Call<List<DataLoginJSON>> call, Throwable t) {

                }
            });
        }


        //load sport
        if (isLoadSports==false){
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
                    Toast.makeText(MainActivity.this, "Load Sport Ok", Toast.LENGTH_SHORT);
                    PushDataSports pushDataSports = new PushDataSports();
                    pushDataSports.hotSportsModelList=hotSportsModelList;
                    EventBus.getDefault().postSticky(pushDataSports);
                    isLoadSports = true;
                    check();
                }

                @Override
                public void onFailure(Call<List<SportsJSONModel>> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Failed to load hot sports into HomeFragment", Toast.LENGTH_SHORT);
                }
            });

        }

    }
}