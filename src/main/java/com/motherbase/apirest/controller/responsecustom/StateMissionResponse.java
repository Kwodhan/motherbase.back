package com.motherbase.apirest.controller.responsecustom;

import com.motherbase.apirest.model.mission.Mission;

import java.util.Date;

public class StateMissionResponse extends CustomerResponse {
    private Mission mission;
    private Date DateBegin;
    private Double percentageSuccess;

    public StateMissionResponse(Mission mission, Date dateBegin, Double percentageSuccess) {
        this.mission = mission;
        DateBegin = dateBegin;
        this.percentageSuccess = percentageSuccess;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Date getDateBegin() {
        return DateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        DateBegin = dateBegin;
    }

    public Double getPercentageSuccess() {
        return percentageSuccess;
    }

    public void setPercentageSuccess(Double percentageSuccess) {
        this.percentageSuccess = percentageSuccess;
    }
}
