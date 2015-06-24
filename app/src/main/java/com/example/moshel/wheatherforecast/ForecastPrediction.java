package com.example.moshel.wheatherforecast;

/**
 * Created by Moshe L on 6/23/2015.
 */
public class ForecastPrediction {
    private String  condition ,day_of_week,high,low;
    public ForecastPrediction(String condition, String day_of_week ,String high, String low){
        this.condition = condition;
        this.day_of_week = day_of_week;
        this.high = high;
        this.low = low;
    }

    @Override
    public String toString() {
        return "The condition is: " + condition ;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDay_of_week() {
        return day_of_week;
    }

    public void setDay_of_week(String day_of_week) {
        this.day_of_week = day_of_week;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }


}
