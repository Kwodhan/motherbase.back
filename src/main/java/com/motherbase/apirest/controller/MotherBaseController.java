package com.motherbase.apirest.controller;

import com.motherbase.apirest.controller.requestcustom.CreateMotherBaseRequest;
import com.motherbase.apirest.controller.requestcustom.MoveStaffBaseRequest;
import com.motherbase.apirest.controller.responsecustom.MoveStaffResponse;
import com.motherbase.apirest.controller.responsecustom.UpgradeDepartmentResponse;
import com.motherbase.apirest.model.motherbase.MessagesMotherBase;
import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.model.motherbase.department.Department;
import com.motherbase.apirest.model.motherbase.department.MessagesDepartment;
import com.motherbase.apirest.model.motherbase.department.RankDepartment;
import com.motherbase.apirest.model.staff.Staff;
import com.motherbase.apirest.service.DepartmentService;
import com.motherbase.apirest.service.MissionService;
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
public class MotherBaseController {

    private static Logger log = Logger.getLogger(MotherBaseController.class.getName());
    @Autowired
    MotherBaseService motherBaseService;

    @Autowired
    StaffService staffService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    MissionService missionService;

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    ResponseEntity<MotherBase> create(@RequestBody CreateMotherBaseRequest createMotherBaseRequest) {
        if (!motherBaseService.findByPseudo(createMotherBaseRequest.getPseudo()).isEmpty()) {
            log.warn("[WARN] " + createMotherBaseRequest.getPseudo() + " already exist");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        MotherBase l1 = this.motherBaseService.create(new MotherBase(createMotherBaseRequest.getPseudo()));
        log.info("[INFO] " + l1.getId() + " create mother base");
        return new ResponseEntity<>(l1, HttpStatus.OK);
    }

    @RequestMapping(value = "/{pseudo}", method = RequestMethod.GET)
    ResponseEntity<MotherBase> getMotherBaseByPseudo(@PathVariable("pseudo") String pseudo) {

        List<MotherBase> motherBases = this.motherBaseService.findByPseudo(pseudo);

        if (motherBases.isEmpty()) {
            log.warn("[WARN] " + pseudo + " have no mother base");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("[INFO] " + pseudo + " get motherBase " + motherBases.get(0).getId());
            return new ResponseEntity<>(motherBases.get(0), HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/moveStaff", method = RequestMethod.PATCH, produces = "application/json", consumes = "application/json")
    ResponseEntity<MoveStaffResponse> moveStaff(@RequestBody MoveStaffBaseRequest moveStaffBaseRequest) {
        Staff staff = staffService.findById(moveStaffBaseRequest.getIdStaff());
        if (staff == null) {
            log.warn("[WARN] Staff " + moveStaffBaseRequest.getIdStaff() + " doesn't exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Department department = departmentService.findById(moveStaffBaseRequest.getIdDepartment());
        if (department == null) {
            log.warn("[WARN] Department " + moveStaffBaseRequest.getIdDepartment() + " doesn't exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (motherBaseService.moveStaff(staff, department)) {
            log.info("[INFO] Staff " + staff.getId() + " move to " + department.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.warn("[WARN] Staff " + staff.getId() + " can not move to " + department.getId());
            MoveStaffResponse res = new MoveStaffResponse();
            res.setMsgError(MessagesMotherBase.CAN_NOT_MOVE_STAFF.getMsg());
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/beginUpgrade/{id}", method = RequestMethod.PATCH, produces = "application/json", consumes = "application/json")
    ResponseEntity<UpgradeDepartmentResponse> beginUpgrade(@PathVariable("id") Long id) {
        Department department = departmentService.findWithMotherBaseById(id);
        if (department == null) {
            log.warn("[WARN] Department " + id + " doesn't exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (motherBaseService.beginUpgradeDepartment(department.getMotherBase(), department)) {
            log.info("[INFO] Department " + department.getId() + " under construction to rank " + RankDepartment.values()[department.getRank().ordinal() + 1]);
            RankDepartment nextRank = RankDepartment.values()[department.getRank().ordinal() + 1];
            return new ResponseEntity<>(new UpgradeDepartmentResponse(nextRank.getDurationUpgrade(), department, department.getMotherBase()), HttpStatus.OK);
        } else {
            log.warn("[WARN] Department " + department.getId() + " can not upgrade from rank " + department.getRank());
            UpgradeDepartmentResponse res = new UpgradeDepartmentResponse();
            res.setMsgError(MessagesDepartment.CAN_NOT_UPGRADE_RESOURCE_MISSING.getMsg());
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }

    }

    @RequestMapping(value = "/finishUpgrade/{id}", method = RequestMethod.PATCH, produces = "application/json", consumes = "application/json")
    ResponseEntity<Boolean> finishUpgrade(@PathVariable("id") Long id) {
        Department department = departmentService.findWithMotherBaseById(id);
        if (department == null) {
            log.warn("[WARN] Department " + id + " doesn't exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MotherBase motherBase = department.getMotherBase();
        if (this.motherBaseService.finishUpgradeDepartment(motherBase, department)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }


}
