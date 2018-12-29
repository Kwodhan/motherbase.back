package com.motherbase.apirest.repository;

import com.motherbase.apirest.model.mission.MissionInProgress;
import com.motherbase.apirest.model.mission.MissionInProgressID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionInProgressRepository extends JpaRepository<MissionInProgress, MissionInProgressID> {
}
