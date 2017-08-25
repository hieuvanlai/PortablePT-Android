package com.example.hihihahahehe.portablept.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.models.HotCoachesModel;
import com.example.hihihahahehe.portablept.models.HotSportsModel;
import com.example.hihihahahehe.portablept.models.PackModel;
import com.squareup.picasso.Picasso;

import java.util.List;
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
    private List<PackModel> hotPackModelList;


    @BindView(R.id.iv_cover)
    ImageView ivCover;
    @BindView(R.id.tv_cover)
    TextView tvCover;

    HotCoachesModel hotCoachesModel;
    HotSportsModel hotSportsModel;
    PackModel hotPackModel;


    public CoverPagerAdapter(Context context, List<HotCoachesModel> hotCoachesModelList,
                             List<HotSportsModel> hotSportsModelList,
                             List<PackModel> hotPackModelList) {
        this.context = context;
        this.hotCoachesModelList = hotCoachesModelList;
        this.hotSportsModelList = hotSportsModelList;
        this.hotPackModelList = hotPackModelList;
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
//        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.swipe_cover, container, false);
        ButterKnife.bind(this, view);

        if(hotCoachesModelList.size() > 0 && hotSportsModelList.size() > 0 && hotPackModelList.size() > 0){
            int randCoaches = ThreadLocalRandom.current().nextInt(0, hotCoachesModelList.size());
            int randSports = ThreadLocalRandom.current().nextInt(0, hotSportsModelList.size());
            int randPacks = ThreadLocalRandom.current().nextInt(0, hotPackModelList.size());


            hotCoachesModel = hotCoachesModelList.get(randCoaches);
            hotSportsModel = hotSportsModelList.get(randSports);
            hotPackModel = hotPackModelList.get(randPacks);

            setData(hotCoachesModel, hotSportsModel, hotPackModel, position);
        }

        ((ViewPager) container).addView(view, 0);
        return view;
    }

    private void setData(HotCoachesModel hotCoachesModel, HotSportsModel hotSportsModel, PackModel hotPackModel, int position) {
        switch (position){
            case 0: {
                ivCover.setImageResource(R.drawable.sample_avatar);
                tvCover.setText(hotCoachesModel.getName());
                break;
            }

            case 1: {
                Picasso.with(context)
                        .load(hotSportsModel.getImageURL())
                        .into(ivCover);
                tvCover.setText(hotSportsModel.getName());
                break;
            }

            case 2: {
                tvCover.setText(hotPackModel.getPackName());
                break;
            }
        }



    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
