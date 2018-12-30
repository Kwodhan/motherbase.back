package com.motherbase.apirest.model.mission.strategyAffectStaff;

import com.motherbase.apirest.model.mission.MissionInProgress;
import com.motherbase.apirest.model.staff.Fighter;

import java.util.List;

/**
 * Define a strategy for rewards missions
 */
public interface StrategyAffectStaff {

    List<Fighter> executeAffect(MissionInProgress missionInProgress, boolean successMission);


}
