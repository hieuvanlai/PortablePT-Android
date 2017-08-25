package com.example.hihihahahehe.portablept.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.models.FaceBookModel;
import com.example.hihihahahehe.portablept.models.PackModel;
import com.example.hihihahahehe.portablept.utils.RealmHandle;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by hihihahahehe on 8/15/17.
 */

public class PackAdapter extends RecyclerView.Adapter<PackAdapter.PackViewHolder> {
    private static final String TAG = PackAdapter.class.toString();
    private List<PackModel> packModelList;
    private View.OnClickListener onClickListener;
    private Context context;
    private FaceBookModel faceBookModel;

    public void setOnClick(View.OnClickListener onClickListener) {
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
        faceBookModel = RealmHandle.getData();
        holder.tvNamePack.setText(packModel.getPackName());
        Picasso.with(context).load(faceBookModel.getImg()).transform(new CropCircleTransformation()).into(holder.ivTrainer);
        holder.tvPrice.setText(packModel.getPrice());
        holder.tvType.setText(packModel.getType());
        Picasso.with(context).load(packModel.getImageUrl()).into(holder.ivPack);
    }

    @Override
    public int getItemCount() {
        return packModelList.size();
    }

    public class PackViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvNamePack;
        @BindView(R.id.iv_trainer)
        ImageView ivTrainer;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.iv_pack)
        ImageView ivPack;

        public PackViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
