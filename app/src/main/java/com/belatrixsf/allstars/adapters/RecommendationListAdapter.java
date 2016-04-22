package com.belatrixsf.allstars.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Recommendation;

import java.lang.ref.WeakReference;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by icerrate on 15/04/2016.
 */
public class RecommendationListAdapter extends RecyclerView.Adapter<RecommendationListAdapter.RecommendationViewHolder> {

    private List<Recommendation> recommendationList;
    private WeakReference<Context> context;

    public RecommendationListAdapter(Context context, List<Recommendation> recommendationList) {
        this.recommendationList = recommendationList;
        this.context = new WeakReference<>(context);
    }

    @Override
    public RecommendationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommendation, parent, false);
        RecommendationViewHolder rcv = new RecommendationViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecommendationViewHolder holder, int position) {
        Recommendation recommendation = recommendationList.get(position);

        holder.userId.setText(recommendation.getSenderUserId());
        holder.message.setText(recommendation.getMessage());
    }

    @Override
    public int getItemCount() {
        return this.recommendationList.size();
    }

    static class RecommendationViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.user_id) public TextView userId;
        @Bind(R.id.message) public TextView message;

        public RecommendationViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
