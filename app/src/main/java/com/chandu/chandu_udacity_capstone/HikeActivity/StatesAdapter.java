package com.chandu.chandu_udacity_capstone.HikeActivity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chandu.chandu_udacity_capstone.R;
import com.chandu.chandu_udacity_capstone.hike.States;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StatesAdapter extends RecyclerView.Adapter<StatesAdapter.StatesAdapterViewHolder> {
    private List<States> statesList;

    private final StatesAdapterOnClickHandler mClickHandler;

    public interface StatesAdapterOnClickHandler {
        void onClick(States statesDetails);
    }

    public StatesAdapter(StatesAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    @NonNull
    @Override
    public StatesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.states_list;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new StatesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatesAdapterViewHolder holder, int position) {
        States statesSummary = statesList.get(position);
        String capName = statesSummary.getCapName() + "," + statesSummary.getStateName();
        Picasso.get().load(statesSummary.getLandscapeImage()).into(holder.stateImage);
        holder.stateImage.setBackgroundColor(Color.WHITE);
        holder.stateCaps.setText(capName);
    }


    @Override
    public int getItemCount() {
        return (statesList == null) ? 0: statesList.size();
    }

    public class StatesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageButton stateImage;
        public final TextView stateCaps;

        public StatesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            stateImage = (ImageButton) itemView.findViewById(R.id.state_name);
            stateCaps = (TextView) itemView.findViewById(R.id.state_caps);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            States statesDetail = statesList.get(adapterPosition);
            mClickHandler.onClick(statesDetail);
        }
    }

    public void setStatesDetails(List<States> statesDetails) {
        statesList = statesDetails;
        notifyDataSetChanged();
    }
}
