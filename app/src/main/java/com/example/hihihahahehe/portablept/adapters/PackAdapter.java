package com.example.hihihahahehe.portablept.adapters;

import android.animation.ValueAnimator;
import android.content.Context;
import android.media.Rating;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.hihihahahehe.portablept.R;
import com.example.hihihahahehe.portablept.models.FaceBookModel;
import com.example.hihihahahehe.portablept.models.PackModel;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hihihahahehe on 8/15/17.
 */

public class PackAdapter extends RecyclerView.Adapter<PackAdapter.PackViewHolder> {
    private static final String TAG = PackAdapter.class.toString();
    private List<PackModel> packModelList;
    private Context context;
    private FaceBookModel faceBookModel;
    private int minHeight;

    public PackAdapter(List<PackModel> packModelList, Context context, View view) {
        this.packModelList = packModelList;
        this.context = context;
    }

    public PackAdapter(List<PackModel> packModelList, Context context) {
        this.packModelList = packModelList;
        this.context = context;
    }

    @Override
    public PackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_pack_hot, null);
        PackViewHolder viewHolder = new PackViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PackViewHolder holder, int position) {
        PackModel packModel = packModelList.get(position);

        holder.tvName.setText(packModel.getPackName());
        holder.tvCost.setText(packModel.getCost());
        holder.tvGoal.setText(packModel.getType());
        holder.tvPackCoach.setText(packModel.getCoachName());
        holder.rbPackRating.setProgress(packModel.getStars());
        Picasso.with(context).load(packModel.getImg()).into(holder.ivPackBackground);
        holder.ivPackBackground.setAlpha(70);
        holder.ivPackBackground.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    @Override
    public int getItemCount() {
        return packModelList.size();
    }

    public class PackViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_pack_coach)
        TextView tvPackCoach;
        @BindView(R.id.tv_goal)
        TextView tvGoal;
        @BindView(R.id.tv_cost)
        TextView tvCost;
        @BindView(R.id.iv_pack_background)
        ImageView ivPackBackground;

        @BindView(R.id.cv_pack)
        CardView cvPack;
        @BindView(R.id.rb_pack_rating)
        AppCompatRatingBar rbPackRating;

        public PackViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dimension = new DisplayMetrics();
            windowmanager.getDefaultDisplay().getMetrics(dimension);
            final int height = 400;

            cvPack.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    cvPack.getViewTreeObserver().removeOnPreDrawListener(this);
                    minHeight = cvPack.getHeight();
                    ViewGroup.LayoutParams layoutParams = cvPack.getLayoutParams();
                    layoutParams.height = minHeight;
                    cvPack.setLayoutParams(layoutParams);
                    return true;
                }
            });

//            cvPack.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    toggleCardViewHeight(height);
//                }
//            });
        }
    }
//        private void toggleCardViewHeight(int height) {
//
//            if (cvPack.getHeight() == minHeight) {
//                expandView(height);
//            } else {
//                collapseView();
//            }
//        }
//
//        public void collapseView() {
//
//            ValueAnimator anim = ValueAnimator.ofInt(cvPack.getMeasuredHeightAndState(),
//                    minHeight);
//            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                    int val = (Integer) valueAnimator.getAnimatedValue();
//                    ViewGroup.LayoutParams layoutParams = cvPack.getLayoutParams();
//                    layoutParams.height = val;
//                    cvPack.setLayoutParams(layoutParams);
//
//                }
//            });
//            anim.start();
//        }
//
//        public void expandView(int height) {
//
//            ValueAnimator anim = ValueAnimator.ofInt(cvPack.getMeasuredHeightAndState(),
//                    height);
//            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                    int val = (Integer) valueAnimator.getAnimatedValue();
//                    ViewGroup.LayoutParams layoutParams = cvPack.getLayoutParams();
//                    layoutParams.height = val;
//                    cvPack.setLayoutParams(layoutParams);
//                }
//            });
//            anim.start();
//        }
//    }
}

