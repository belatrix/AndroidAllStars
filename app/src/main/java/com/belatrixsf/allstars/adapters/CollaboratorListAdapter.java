package com.belatrixsf.allstars.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.allstars.R;
import com.belatrixsf.allstars.entities.Collaborator;
import com.belatrixsf.allstars.utils.media.ImageFactory;
import com.belatrixsf.allstars.utils.media.loaders.ImageLoader;

import java.util.List;

/**
 * Created by icerrate on 10/06/2016.
 */
public class CollaboratorListAdapter extends RecyclerView.Adapter<CollaboratorListAdapter.ViewHolder> {

    private List<Collaborator> items;

    public CollaboratorListAdapter(List<Collaborator> items) {
        this.items = items;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collaborator, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        Collaborator collaborator = items.get(position);
        holder.fullname.setText(collaborator.getFullName());
        ImageFactory.getLoader().loadFromRes(collaborator.getDrawable(), holder.photo, ImageLoader.ImageTransformation.BORDERED_CIRCLE);
        holder.itemView.setTag(collaborator);
    }

    @Override public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView photo;
        public TextView fullname;

        public ViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.photo);
            fullname = (TextView) itemView.findViewById(R.id.full_name);
        }
    }
}