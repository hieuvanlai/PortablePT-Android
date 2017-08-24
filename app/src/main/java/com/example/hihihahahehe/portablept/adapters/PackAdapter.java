package com.example.hihihahahehe.portablept.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.models.PackModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hihihahahehe on 8/15/17.
 */

public class PackAdapter extends RecyclerView.Adapter<PackAdapter.PackViewHolder> {
    private List<PackModel> packModelList;
    private View.OnClickListener onClickListener;
    private Context context;

    public void setOnClick(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public PackAdapter(List<PackModel> packModelList, Context context) {
        this.packModelList = packModelList;
        this.context = context;
    }

    @Override
    public PackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_pack, null);
        PackViewHolder viewHolder = new PackViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PackViewHolder holder, int position) {
        PackModel packModel = packModelList.get(position);

     //   holder.tvNamePack.setText(packModel.getPackName());
//        holder.tvGoal.setText(packModel.getGoal());
//        holder.tvCost.setText(packModel.getCost());
//        holder.tvDuration.setText(packModel.getDuration());
    }

    @Override
    public int getItemCount() {
        return packModelList.size();
    }

    public class PackViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.tv_name)
//        TextView tvNamePack;
//        @BindView(R.id.tv_duration)
//        TextView tvDuration;
//        @BindView(R.id.tv_goal)
//        TextView tvGoal;
//        @BindView(R.id.tv_cost)
//        TextView tvCost;

        public PackViewHolder(View itemView) {
            super(itemView);

        }
    }
}
