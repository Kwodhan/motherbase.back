package com.motherbase.apirest.service;

import com.motherbase.apirest.controller.responsecustom.FinishMissionResponse;
import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.motherbase.MotherBase;

import java.util.List;
import java.util.Set;

public interface MissionService {

    Mission create(Mission mission);

    Mission update(Mission mission);

    Mission findMissionById(Long id);

    Set<Mission> findMissionsUnderEqualsRank(Integer rank);

    boolean takeMission(MotherBase motherBase, Mission mission, List<Long> fighters);

    FinishMissionResponse finishMission(MotherBase motherBase, Mission mission);
}
