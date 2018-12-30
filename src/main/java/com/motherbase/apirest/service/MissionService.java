package com.motherbase.apirest.service;

import com.motherbase.apirest.model.mission.Mission;

import java.util.Set;

public interface MissionService {

    Mission create(Mission mission);

    Mission update(Mission mission);

    Mission findMissionById(Long id);

    Set<Mission> findMissionsUnderEqualsRank(Integer rank);
}
