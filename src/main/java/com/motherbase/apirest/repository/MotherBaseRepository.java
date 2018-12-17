package com.motherbase.apirest.repository;

import com.motherbase.apirest.model.motherbase.MotherBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MotherBaseRepository extends JpaRepository<MotherBase, Long> {

    @Query("select a from MotherBase a where a.id = :id")
    MotherBase findMotherBaseById(@Param("id") Long id);
}
