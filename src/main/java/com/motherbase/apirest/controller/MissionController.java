package com.motherbase.apirest.controller;

import com.motherbase.apirest.controller.requestcustom.BeginMissionRequest;
import com.motherbase.apirest.controller.requestcustom.FinishMissionRequest;
import com.motherbase.apirest.controller.requestcustom.StateMissionRequest;
import com.motherbase.apirest.controller.responsecustom.BeginMissionResponse;
import com.motherbase.apirest.controller.responsecustom.FinishMissionResponse;
import com.motherbase.apirest.controller.responsecustom.StateMissionResponse;
import com.motherbase.apirest.model.mission.MessagesMission;
import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.mission.MissionInProgress;
import com.motherbase.apirest.model.mission.StateMission;
import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.service.DepartmentService;
import com.motherbase.apirest.service.MissionService;
import com.motherbase.apirest.service.MotherBaseService;
import com.motherbase.apirest.service.StaffService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/mission")
public class MissionController {


    private static Logger log = Logger.getLogger(MissionController.class.getName());
    @Autowired
    MotherBaseService motherBaseService;

    @Autowired
    StaffService staffService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    MissionService missionService;

    @RequestMapping(value = "/beginMission", method = RequestMethod.PATCH, produces = "application/json", consumes = "application/json")
    ResponseEntity<BeginMissionResponse> beginMission(@RequestBody BeginMissionRequest beginMissionRequest) {
        Mission mission = this.missionService.findMissionById(beginMissionRequest.getIdMission());
        MotherBase motherBase = this.motherBaseService.findById(beginMissionRequest.getIdMotherBase());

        if (!this.isMotherBaseAndMissionExist(motherBase, mission, beginMissionRequest.getIdMotherBase(), beginMissionRequest.getIdMission())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (missionService.takeMission(motherBase, mission, beginMissionRequest.getFighterList())) {
            log.info("[INFO] MotherBase " + motherBase.getId() + " take Mission " + mission.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            log.warn("[WARN] MotherBase " + motherBase.getId() + " can not take Mission " + mission.getId());
            BeginMissionResponse res = new BeginMissionResponse();
            res.setMsgError(MessagesMission.CAN_NOT_TAKE_MISSION.getMsg());
            return new ResponseEntity<>(res, HttpStatus.CONFLICT);
        }

    }

    @RequestMapping(value = "/finishMission", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    ResponseEntity<FinishMissionResponse> finishMission(@RequestBody FinishMissionRequest finishMissionRequest) {
        Mission mission = this.missionService.findMissionById(finishMissionRequest.getIdMission());
        MotherBase motherBase = this.motherBaseService.findById(finishMissionRequest.getIdMotherBase());

        if (!this.isMotherBaseAndMissionExist(motherBase, mission, finishMissionRequest.getIdMotherBase(), finishMissionRequest.getIdMission())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean haveMission = motherBase.getMissionInProgress().stream().filter(x -> x.getMission().getId().equals(mission.getId())).findFirst().isPresent();
        if (!haveMission) {
            log.warn("[WARN] MotherBase " + motherBase.getId() + " doesn't have mission " + mission.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        FinishMissionResponse finishMissionResponse = this.missionService.finishMission(motherBase, mission);
        StateMission stateMission = finishMissionResponse.getStateMission();
        if (stateMission.equals(StateMission.Success)) {
            return new ResponseEntity<>(finishMissionResponse, HttpStatus.OK);
        } else if (stateMission.equals(StateMission.Failed)) {
            return new ResponseEntity<>(finishMissionResponse, HttpStatus.OK);
        } else if (stateMission.equals(StateMission.NotFinish)) {
            return new ResponseEntity<>(finishMissionResponse, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(finishMissionResponse, HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/stateMission", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    ResponseEntity<StateMissionResponse> stateMission(@RequestBody StateMissionRequest stateMissionRequest) {
        Mission mission = this.missionService.findMissionById(stateMissionRequest.getIdMission());
        MotherBase motherBase = this.motherBaseService.findById(stateMissionRequest.getIdMotherBase());

        if (!this.isMotherBaseAndMissionExist(motherBase, mission, stateMissionRequest.getIdMotherBase(), stateMissionRequest.getIdMission())) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        boolean haveMission = motherBase.getMissionInProgress().stream().filter(x -> x.getMission().getId().equals(mission.getId())).findFirst().isPresent();
        if (!haveMission) {
            log.warn("[WARN] MotherBase " + motherBase.getId() + " doesn't have mission " + mission.getId());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        MissionInProgress missionInProgress = motherBase.getMissionInProgress(mission);
        return new ResponseEntity<>(new StateMissionResponse(mission, missionInProgress.getDateBegin(), motherBase.getPercentageSuccess(mission), missionInProgress.getFighters()), HttpStatus.OK);

    }

    @RequestMapping(value = "/stateMissions/{idMotherBase}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    ResponseEntity<Set<StateMissionResponse>> stateMissions(@PathVariable("idMotherBase") Long idMotherBase) {
        MotherBase motherBase = this.motherBaseService.findById(idMotherBase);
        if (motherBase == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<StateMissionResponse> stateMissionResponses = new HashSet<>();
        Set<MissionInProgress> missionInProgressSet = motherBase.getMissionInProgress();

        for (MissionInProgress missionInProgress : missionInProgressSet) {
            stateMissionResponses.add(
                    new StateMissionResponse(missionInProgress.getMission(), missionInProgress.getDateBegin(), motherBase.getPercentageSuccess(missionInProgress.getMission()), missionInProgress.getFighters())
            );
        }
        if (stateMissionResponses.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(stateMissionResponses, HttpStatus.OK);
    }

    @RequestMapping(value = "/availableMissions/{idMotherBase}", method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    ResponseEntity<Set<Mission>> availableMissions(@PathVariable("idMotherBase") Long idMotherBase) {
        MotherBase motherBase = this.motherBaseService.findById(idMotherBase);
        if (motherBase == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Set<Mission> missions = this.missionService.findMissionsUnderEqualsRank(motherBase.getRankHistory());
        Set<MissionInProgress> missionInProgressSet = motherBase.getMissionInProgress();
        for (MissionInProgress missionInProgress : missionInProgressSet) {
            missions.remove(missionInProgress.getMission());
        }
        return new ResponseEntity<>(missions, HttpStatus.OK);
    }

    private boolean isMotherBaseAndMissionExist(MotherBase motherBase, Mission mission, Long wantedIdMotherbase, Long wantedIdMission) {

        if (mission == null) {
            log.warn("[WARN] Mission " + wantedIdMission + " doesn't exist");
            return false;
        }

        if (motherBase == null) {
            log.warn("[WARN] MotherBase " + wantedIdMotherbase + " doesn't exist");
            return false;
        }
        return true;
    }


}
