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
        return missionRepository.findMissionById(id);
    }

    @Override
    public Set<Mission> findMissionsUnderRank(Integer rank) {
        return missionRepository.findMissionsUnderRank(rank);
    }

    @Transactional
    public Mission create(Mission mission) {

        return missionRepository.save(mission);
    }
}
