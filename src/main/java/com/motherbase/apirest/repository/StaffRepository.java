package com.motherbase.apirest.repository;

import com.motherbase.apirest.model.staff.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    @Query("select a from Staff a where a.id = :id")
    Staff findStaffById(@Param("id") Long id);


}
