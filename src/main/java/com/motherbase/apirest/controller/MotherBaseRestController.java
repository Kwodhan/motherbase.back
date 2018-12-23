package com.motherbase.apirest.controller;

import com.motherbase.apirest.controller.requestcustom.CreateMotherBaseRequest;
import com.motherbase.apirest.controller.requestcustom.MoveStaffBaseResquest;
import com.motherbase.apirest.controller.responsecustom.MoveStaffResponse;
import com.motherbase.apirest.controller.responsecustom.UpgradeDepartmentResponse;
import com.motherbase.apirest.model.motherbase.MessagesMotherBase;
import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.model.motherbase.department.Department;
import com.motherbase.apirest.model.motherbase.department.MessagesDepartment;
import com.motherbase.apirest.model.motherbase.department.RankDepartment;
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
    ResponseEntity<MoveStaffResponse> moveStaff(@RequestBody MoveStaffBaseResquest moveStaffBaseResquest) {
        Staff staff = staffService.findById(moveStaffBaseResquest.getIdStaff());
        Department department = departmentService.findById(moveStaffBaseResquest.getIdDepartment());
        if (staff == null) {
            log.warn("[WARN] Staff " + moveStaffBaseResquest.getIdStaff() + " doesn't exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (department == null) {
            log.warn("[WARN] Department " + moveStaffBaseResquest.getIdDepartment() + " doesn't exist");
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

    @RequestMapping(value = "/triggerUpgrade/{id}", method = RequestMethod.PATCH, produces = "application/json", consumes = "application/json")
    ResponseEntity<UpgradeDepartmentResponse> triggerUpgrade(@PathVariable("id") Long id) {
        Department department = departmentService.findWithMotherBaseById(id);

        if (motherBaseService.triggerUpgradeDepartment(department.getMotherBase(), department)) {
            log.info("[INFO] Department " + department.getId() + " under construction to rank " + RankDepartment.values()[department.getRank().ordinal() + 1]);
            RankDepartment nextRank = RankDepartment.values()[department.getRank().ordinal() + 1];
            return new ResponseEntity<>(new UpgradeDepartmentResponse(nextRank.getDurationUpgrade(), department, department.getMotherBase()), HttpStatus.OK);
        } else {
            log.warn("[WARN] Department " + department.getId() + " can not upgrade from rank " + department.getRank());
            UpgradeDepartmentResponse res = new UpgradeDepartmentResponse();
            res.setMsgError(MessagesDepartment.CAN_NOT_UPGRADE_RESOURCE_MISSING.getMsg());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

    }

    @RequestMapping(value = "/upgrade/{id}", method = RequestMethod.PATCH, produces = "application/json", consumes = "application/json")
    ResponseEntity<Department> upgrade(@PathVariable("id") Long id) {
        Department department = departmentService.findWithMotherBaseById(id);
        log.info("[INFO] Department " + department.getId() + " finish construction to rank " + RankDepartment.values()[department.getRank().ordinal() + 1]);
        return new ResponseEntity<>(motherBaseService.upgradeDepartment(department.getMotherBase(), department), HttpStatus.OK);

    }

}
