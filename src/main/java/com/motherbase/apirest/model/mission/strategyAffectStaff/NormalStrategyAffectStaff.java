package com.motherbase.apirest.model.mission.strategyAffectStaff;

import com.motherbase.apirest.config.ParameterManager;
import com.motherbase.apirest.model.mission.MissionInProgress;
import com.motherbase.apirest.model.staff.Fighter;

import java.util.List;

public class NormalStrategyAffectStaff implements StrategyAffectStaff {


    @Override
    public List<Fighter> executeAffect(MissionInProgress missionInProgress, boolean successMission) {

        double multiplierDyingFailedMission = 2.0;
        double multiplierInjuringFailedMission = 2.0;

        multiplierDyingFailedMission = (double) ParameterManager.getValue("multiplierDyingFailedMission");
        multiplierInjuringFailedMission = (double) ParameterManager.getValue("multiplierInjuringFailedMission");

        for (Fighter fighter : missionInProgress.getFighters()) {
            fighter.takeDamage(successMission, missionInProgress.getMission(), multiplierDyingFailedMission, multiplierInjuringFailedMission);

        }

        return missionInProgress.getFighters();
    }

}
