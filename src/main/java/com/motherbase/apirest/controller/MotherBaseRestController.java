package com.motherbase.apirest.controller;

import com.motherbase.apirest.controller.requestcustom.CreateMotherBaseRequest;
import com.motherbase.apirest.controller.requestcustom.MoveStaffBaseResquest;
import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.model.motherbase.department.Department;
import com.motherbase.apirest.model.staff.Staff;
import com.motherbase.apirest.service.DepartmentService;
import com.motherbase.apirest.service.MotherBaseService;
import com.motherbase.apirest.service.StaffService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/motherbase")
public class MotherBaseRestController {

    static Logger log = Logger.getLogger(MotherBaseRestController.class.getName());
    @Autowired
    MotherBaseService motherBaseService;

    @Autowired
    StaffService staffService;

    @Autowired
    DepartmentService departmentService;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    ResponseEntity<MotherBase> create(@RequestBody CreateMotherBaseRequest createMotherBaseRequest) {
        if (!motherBaseService.findByPseudo(createMotherBaseRequest.getPseudo()).isEmpty()) {
            log.info("[WARN] " + createMotherBaseRequest.getPseudo() + " already exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        MotherBase l1 = this.motherBaseService.create(new MotherBase(createMotherBaseRequest.getPseudo()));
        log.info("[INFO] " + l1.getId() + " join Diamond Dogs");
        return new ResponseEntity<>(l1, HttpStatus.OK);
    }

    @RequestMapping(value = "/{pseudo}", method = RequestMethod.GET)
    ResponseEntity<MotherBase> getMotherBaseByPseudo(@PathVariable("pseudo") String pseudo) {

        List<MotherBase> motherBases = this.motherBaseService.findByPseudo(pseudo);

        if (motherBases.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(motherBases.get(0), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/moveStaff", method = RequestMethod.PATCH, produces = "application/json", consumes = "application/json")
    ResponseEntity<MotherBase> moveStaff(@RequestBody MoveStaffBaseResquest moveStaffBaseResquest) {
        Staff staff = staffService.findById(moveStaffBaseResquest.getIdStaff());
        Department department = departmentService.findById(moveStaffBaseResquest.getIdDepartment());
        if (staff == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (motherBaseService.moveStaff(staff, department)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @RequestMapping(value = "/upgrade/{id}", method = RequestMethod.PATCH, produces = "application/json", consumes = "application/json")
    ResponseEntity<MotherBase> upgrade(@PathVariable("id") Long id) {
        Department department = departmentService.findWithMotherBaseById(id);
        if (motherBaseService.upgrade(department.getMotherBase(), department)) {
            return new ResponseEntity<>(department.getMotherBase(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }

    }


}
