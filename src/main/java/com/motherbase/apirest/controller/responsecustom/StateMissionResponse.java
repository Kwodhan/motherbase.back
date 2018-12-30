package com.motherbase.apirest.controller.responsecustom;

import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.staff.Fighter;

import java.util.Date;
import java.util.List;

public class StateMissionResponse extends CustomerResponse {
    private Mission mission;
    private Date DateBegin;
    private Double percentageSuccess;
    private List<Fighter> fighterList;


    public StateMissionResponse(Mission mission, Date dateBegin, Double percentageSuccess, List<Fighter> fighterList) {
        this.mission = mission;
        this.DateBegin = dateBegin;
        this.percentageSuccess = percentageSuccess;
        this.fighterList = fighterList;
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

    public List<Fighter> getFighterList() {
        return fighterList;
    }

    public void setFighterList(List<Fighter> fighterList) {
        this.fighterList = fighterList;
    }
}
