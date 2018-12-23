package com.motherbase.apirest.controller;

import com.motherbase.apirest.model.motherbase.department.Department;
import com.motherbase.apirest.service.DepartmentService;
import com.motherbase.apirest.service.StaffService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/department")
public class DepartmentController {

    static Logger log = Logger.getLogger(DepartmentController.class.getName());

    @Autowired
    StaffService staffService;

    @Autowired
    DepartmentService departmentService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    ResponseEntity<Department> getDepartmentById(@PathVariable("id") Long id) {

        Department department = this.departmentService.findById(id);

        if (department == null) {
            log.warn("[WARN] Department" + id + " doesn't exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("[INFO] Department" + id + " get");
            return new ResponseEntity<>(department, HttpStatus.OK);
        }
    }

}
