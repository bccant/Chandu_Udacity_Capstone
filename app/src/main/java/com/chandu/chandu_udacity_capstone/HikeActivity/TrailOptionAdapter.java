package com.chandu.chandu_udacity_capstone.HikeActivity;

import android.content.Context;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chandu.chandu_udacity_capstone.R;
import com.chandu.chandu_udacity_capstone.hike.Hike;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailOptionAdapter extends RecyclerView.Adapter<TrailOptionAdapter.TrailOptionAdapterViewHolder> {
    private List<Hike> hikeDetails;
    private final TrailOptionAdapterOnClickHandler mClickHandler;

    public interface TrailOptionAdapterOnClickHandler {
        void onClick(Hike hikeDetails);
    }

    public TrailOptionAdapter(TrailOptionAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public TrailOptionAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.hike_options;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new TrailOptionAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailOptionAdapterViewHolder holder, int position) {
        String lengthText = "Trail length: ";
        String difficultyText = " Difficulty: ";
        Hike hikeSummary = hikeDetails.get(position);
        holder.mHikeName.setText(hikeSummary.getHikeName());
        lengthText = lengthText + hikeSummary.getHikeLength();
        holder.mHikeDist.setText(lengthText);
        difficultyText = difficultyText + hikeSummary.getHikeDifficulty();
        holder.mHikeDiff.setText(difficultyText);
    }

    @Override
    public int getItemCount() {

        return (hikeDetails == null) ? 0 : hikeDetails.size();
    }

    public class TrailOptionAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mHikeName;
        public final TextView mHikeDist;
        public final TextView mHikeDiff;
        //public final TextView mHikeDiff;

        public TrailOptionAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mHikeName = (TextView) itemView.findViewById(R.id.hike_option_name);
            mHikeDist = (TextView) itemView.findViewById(R.id.hike_option_distance);
            mHikeDiff = (TextView) itemView.findViewById(R.id.hike_option_difficulty);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Hike trailDetails = hikeDetails.get(adapterPosition);
            mClickHandler.onClick(trailDetails);
        }
    }

    public void setHikeDetails(List<Hike> mHikeDetails) {
        hikeDetails = mHikeDetails;
        notifyDataSetChanged();
    }

    public void setHikeDetailsFromDB(List<Hike> mHikeDetails) {
        hikeDetails = mHikeDetails;
        notifyDataSetChanged();
    }

    public int getImageid(String itemName) {
        if (itemName.equals(("green"))) {
            return R.drawable.easy_new;
        } else if (itemName.equals(("blue")) || itemName.equals(("greenblue"))) {
            return R.drawable.semi_difficult;
        } else {
            return R.drawable.difficult;
        }

    }
}
