package com.motherbase.apirest.service;

import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.repository.MissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class MissionServiceImpl implements MissionService {

    @Autowired
    private MissionRepository missionRepository;

    public MissionServiceImpl() {
    }

    @Override
    public Mission findMissionById(Long id) {
        return missionRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Mission> findMissionsUnderRank(Integer rank) {
        return missionRepository.findMissionsUnderRank(rank);
    }

    @Transactional
    public Mission create(Mission mission) {

        return missionRepository.save(mission);
    }

    @Transactional
    public Mission update(Mission mission) {
        Mission updatedMission = missionRepository.findById(mission.getId()).orElse(null);

        updatedMission.setDescription(mission.getDescription());
        updatedMission.setDurationCompleted(mission.getDurationCompleted());
        updatedMission.setForce(mission.getForce());
        updatedMission.setPercentRewardResources(mission.getPercentRewardResources());
        updatedMission.setRankMission(mission.getRankMission());
        updatedMission.setRewardStaffs(mission.getRewardStaffs());
        updatedMission.setRewardResources(mission.getRewardResources());
        updatedMission.setMissionInProgresses(mission.getMissionInProgresses());
        return updatedMission;
    }
}
