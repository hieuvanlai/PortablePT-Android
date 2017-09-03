package com.example.hihihahahehe.portablept.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.models.HotCoachesModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by valky on 8/17/2017.
 */

public class HotCoachesAdapter extends RecyclerView.Adapter<HotCoachesAdapter.HotCoachesViewHolder> {
    private List<HotCoachesModel> hotCoachesModelList = new ArrayList<>();
    private Context context;
    private View.OnClickListener onClickListener;

    public void setOnItemClick(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public HotCoachesAdapter(List<HotCoachesModel> hotCoachesModelList, Context context) {
        this.hotCoachesModelList = hotCoachesModelList;
        this.context = context;
    }

    @Override
    public HotCoachesAdapter.HotCoachesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_hot_coach, parent, false);
        view.setOnClickListener(onClickListener);
        return new HotCoachesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HotCoachesAdapter.HotCoachesViewHolder holder, int position) {
        holder.setData(hotCoachesModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return hotCoachesModelList.size();
    }


    public class HotCoachesViewHolder extends RecyclerView.ViewHolder {
        TextView tvCoachName;
        ImageView ivCoachImage;

        View view;

        public HotCoachesViewHolder(View itemView) {
            super(itemView);
            tvCoachName = (TextView) itemView.findViewById(R.id.tv_coach_name);
            ivCoachImage = (ImageView) itemView.findViewById(R.id.iv_coach_image);

            view = itemView;
        }

        public void setData(HotCoachesModel hotCoachesModel){
            if(hotCoachesModel != null){
                tvCoachName.setText(hotCoachesModel.getName());
                Picasso.with(context).load(hotCoachesModel.getAvata())
                        .transform(new CropCircleTransformation())
                        .into(ivCoachImage);
            }
        }
    }
}
