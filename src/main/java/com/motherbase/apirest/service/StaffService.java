package com.motherbase.apirest.service;

import com.motherbase.apirest.model.staff.Staff;

public interface StaffService {
    Staff create(Staff staff);

    Staff update(Staff staff);

    Staff findById(Long id);
}
