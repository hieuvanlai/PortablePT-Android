package com.example.hihihahahehe.portablept.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.models.HotCoachesModel;
import com.example.hihihahahehe.portablept.models.HotSportsModel;
import com.example.hihihahahehe.portablept.models.PackModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by valky on 8/24/2017.
 */

public class CoverPagerAdapter extends PagerAdapter{
    Context context;
    private LayoutInflater layoutInflater;

    private List<HotCoachesModel> hotCoachesModelList;
    private List<HotSportsModel> hotSportsModelList;


    @BindView(R.id.iv_coach_cover)
    ImageView ivCoachCover;
    @BindView(R.id.tv_coach_cover)
    TextView tvCoachCover;
    HotCoachesModel hotCoachesModel;

    @BindView(R.id.iv_sport_cover)
    ImageView ivSportCover;
    @BindView(R.id.tv_sport_cover)
    TextView tvSportCover;
    HotSportsModel hotSportsModel;


    public CoverPagerAdapter(Context context, List<HotCoachesModel> hotCoachesModelList, List<HotSportsModel> hotSportsModelList) {
        this.context = context;
        this.hotCoachesModelList = hotCoachesModelList;
        this.hotSportsModelList = hotSportsModelList;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(hotCoachesModelList.size() > 0 && hotSportsModelList.size() > 0){
            int randCoaches = ThreadLocalRandom.current().nextInt(0, hotCoachesModelList.size());
            int randSports = ThreadLocalRandom.current().nextInt(0, hotSportsModelList.size());

            hotCoachesModel = hotCoachesModelList.get(randCoaches);
            hotSportsModel = hotSportsModelList.get(randSports);

            setData(hotCoachesModel, hotSportsModel);
        }

        int resId = -1;
        switch (position) {
            case 0:
                resId = R.layout.swipe_coach_cover;
                break;
            case 1:
                resId = R.layout.swipe_sport_cover;
                break;
            case 2:
                resId = R.layout.item_list_pack;
                break;
        }

        View view = layoutInflater.inflate(resId, container, false);
        ((ViewPager) container).addView(view, 0);
        return view;
    }

    private void setData(HotCoachesModel hotCoachesModel, HotSportsModel hotSportsModel) {
        ivCoachCover.setImageResource(R.drawable.sample_avatar);
        tvCoachCover.setText(hotCoachesModel.getName());

        Picasso.with(context)
                .load(hotSportsModel.getImageURL())
                .into(ivSportCover);
        tvSportCover.setText(hotSportsModel.getName());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
