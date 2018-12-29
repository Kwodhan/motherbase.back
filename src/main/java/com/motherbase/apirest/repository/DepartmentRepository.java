package com.motherbase.apirest.repository;

import com.motherbase.apirest.model.motherbase.department.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {


    @Query("select a from Department a left join fetch a.motherBase m where a.id = :id")
    Department findDepartmentWithMotherBaseById(@Param("id") Long id);


}
