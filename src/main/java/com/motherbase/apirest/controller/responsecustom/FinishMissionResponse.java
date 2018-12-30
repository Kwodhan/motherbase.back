package com.motherbase.apirest.controller.responsecustom;

import com.motherbase.apirest.model.mission.StateMission;
import com.motherbase.apirest.model.staff.Fighter;

import java.util.List;

public class FinishMissionResponse extends CustomerResponse {
    List<Fighter> fighters;
    StateMission stateMission;

    public FinishMissionResponse(List<Fighter> fighters, StateMission stateMission) {
        this.fighters = fighters;
        this.stateMission = stateMission;
    }

    public List<Fighter> getFighters() {
        return fighters;
    }

    public void setFighters(List<Fighter> fighters) {
        this.fighters = fighters;
    }

    public StateMission getStateMission() {
        return stateMission;
    }

    public void setStateMission(StateMission stateMission) {
        this.stateMission = stateMission;
    }
}
