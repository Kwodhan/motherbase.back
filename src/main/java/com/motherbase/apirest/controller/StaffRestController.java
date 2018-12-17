package com.motherbase.apirest.controller;

import com.motherbase.apirest.model.staff.Staff;
import com.motherbase.apirest.service.StaffService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/staff")
public class StaffRestController {

    static Logger log = Logger.getLogger(StaffRestController.class.getName());
    @Autowired
    StaffService staffService;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    ResponseEntity<Staff> create(@RequestBody Staff staff) {
        Staff l1 = this.staffService.create(staff);
        System.out.println("Going to create a motherbase");
        log.info("Going to create a motherbase");
        return new ResponseEntity<>(l1, HttpStatus.OK);
    }
}
