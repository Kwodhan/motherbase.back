package com.motherbase.apirest.controller.responsecustom;

import com.motherbase.apirest.model.mission.StateMission;
import com.motherbase.apirest.model.mission.strategyReward.RewardMission;
import com.motherbase.apirest.model.staff.Fighter;

import java.util.List;

public class FinishMissionResponse extends CustomerResponse {
    List<Fighter> fightersState;
    StateMission stateMission;
    RewardMission rewardMission;


    public FinishMissionResponse(List<Fighter> fightersState, StateMission stateMission, RewardMission rewardMission) {
        this.fightersState = fightersState;
        this.stateMission = stateMission;
        this.rewardMission = rewardMission;
    }

    public List<Fighter> getFightersState() {
        return fightersState;
    }

    public void setFightersState(List<Fighter> fightersState) {
        this.fightersState = fightersState;
    }

    public RewardMission getRewardMission() {
        return rewardMission;
    }

    public void setRewardMission(RewardMission rewardMission) {
        this.rewardMission = rewardMission;
    }

    public StateMission getStateMission() {
        return stateMission;
    }

    public void setStateMission(StateMission stateMission) {
        this.stateMission = stateMission;
    }


}
