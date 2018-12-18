package com.motherbase.apirest.repository;

import com.motherbase.apirest.model.mission.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query("select a from Mission a where a.id = :id")
    Mission findMissionById(@Param("id") Long id);


}
