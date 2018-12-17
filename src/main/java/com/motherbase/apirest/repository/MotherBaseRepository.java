package com.motherbase.apirest.repository;

import com.motherbase.apirest.model.motherbase.MotherBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotherBaseRepository extends JpaRepository<MotherBase, Long> {

    @Query("select a from MotherBase a where a.id = :id")
    MotherBase findMotherBaseById(@Param("id") Long id);

    @Query("select a from MotherBase a where a.pseudo = :pseudo")
    List<MotherBase> findMotherBaseByPseudo(@Param("pseudo") String pseudo);
}
