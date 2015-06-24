package com.example.moshel.wheatherforecast;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moshe L on 6/24/2015.
 */
public class ForecastAdapter  extends RecyclerView.Adapter<ForecastAdapter.ForecastPredictionViewHolder> {

    protected static class ForecastPredictionViewHolder extends  RecyclerView.ViewHolder{

        public TextView textViewCondition, textViewDay_of_week  ,textViewHigh,textViewLow;;
        public ForecastPredictionViewHolder (View itemView){
            super(itemView);
            textViewCondition = (TextView)itemView.findViewById(R.id.textViewCondition);
            textViewDay_of_week = (TextView)itemView.findViewById(R.id.textViewDay_of_week);
            textViewHigh = (TextView)itemView.findViewById(R.id.textViewHigh);
            textViewLow = (TextView)itemView.findViewById(R.id.textViewLow);
        }
    }
    List<ForecastPrediction> ForecastPredictionList = new ArrayList<>();

    public void addForecastPrediction(ForecastPrediction newForecastPrediction){
        ForecastPredictionList.add(newForecastPrediction);
        notifyDataSetChanged();
    }
    public void swapLookup(List<ForecastPrediction> newLookupList){
        ForecastPredictionList = newLookupList;
    }

    @Override
    public ForecastPredictionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.forecast_layout, viewGroup, false);
        return new ForecastPredictionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastPredictionViewHolder ForecastPredictionViewHolder, int position) {
        ForecastPrediction ForecastPrediction = ForecastPredictionList.get(position);

        ForecastPredictionViewHolder.textViewCondition.setText(ForecastPrediction.getCondition());
        ForecastPredictionViewHolder.textViewDay_of_week.setText(ForecastPrediction.getDay_of_week());
        ForecastPredictionViewHolder.textViewHigh.setText(ForecastPrediction.getHigh());
        ForecastPredictionViewHolder.textViewLow.setText(ForecastPrediction.getLow());
    }

    @Override
    public int getItemCount() {
        return ForecastPredictionList.size();
    }


}
