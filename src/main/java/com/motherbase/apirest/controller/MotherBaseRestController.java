package com.motherbase.apirest.controller;

import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.service.MotherBaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/motherbase")
public class MotherBaseRestController {

    static Logger log = Logger.getLogger(MotherBaseRestController.class.getName());
    @Autowired
    MotherBaseService motherBaseService;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    ResponseEntity<MotherBase> create() {
        MotherBase l1 = this.motherBaseService.create(new MotherBase());
        System.out.println("Going to create a motherbase");
        log.info("Going to create a motherbase");
        return new ResponseEntity<>(l1, HttpStatus.OK);
    }
}
