package com.motherbase.apirest.service;

import com.motherbase.apirest.controller.responsecustom.FinishMissionResponse;
import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.mission.MissionInProgress;
import com.motherbase.apirest.model.mission.MissionInProgressID;
import com.motherbase.apirest.model.mission.StateMission;
import com.motherbase.apirest.model.mission.strategyAffectStaff.NormalStrategyAffectStaff;
import com.motherbase.apirest.model.mission.strategyAffectStaff.StrategyAffectStaff;
import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.model.staff.Fighter;
import com.motherbase.apirest.model.staff.Staff;
import com.motherbase.apirest.model.staff.vehicule.Vehicle;
import com.motherbase.apirest.repository.MissionInProgressRepository;
import com.motherbase.apirest.repository.MissionRepository;
import com.motherbase.apirest.repository.StaffRepository;
import com.motherbase.apirest.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class MissionServiceImpl implements MissionService {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private MissionInProgressRepository missionInProgressRepository;

    public MissionServiceImpl() {
    }

    @Override
    public Mission findMissionById(Long id) {
        return missionRepository.findById(id).orElse(null);
    }

    @Override
    public Set<Mission> findMissionsUnderEqualsRank(Integer rank) {
        return missionRepository.findMissionsUnderEqualsRank(rank);
    }

    @Transactional
    public Mission create(Mission mission) {

        return missionRepository.save(mission);
    }

    @Transactional
    public Mission update(Mission mission) {
        Mission updatedMission = missionRepository.findById(mission.getId()).orElse(null);

        updatedMission.setDescription(mission.getDescription());
        updatedMission.setDurationCompleted(mission.getDurationCompleted());
        updatedMission.setForce(mission.getForce());
        updatedMission.setPercentRewardResources(mission.getPercentRewardResources());
        updatedMission.setRankMission(mission.getRankMission());
        updatedMission.setRewardStaffs(mission.getRewardStaffs());
        updatedMission.setRewardResources(mission.getRewardResources());
        updatedMission.setMissionInProgresses(mission.getMissionInProgresses());
        return updatedMission;
    }

    @Override
    @Transactional
    public boolean takeMission(MotherBase motherBase, Mission mission, List<Long> fightersId) {

        if (!motherBase.canTakeMission(mission)) {
            return false;

        }

        // Search Fighters in 3 tables (Staff, Vehicle, Metal gear)
        List<Fighter> fighters = new ArrayList<>();
        for (Long id : fightersId) {
            Fighter fighter = staffRepository.findById(id).orElse(null);
            if (fighter == null) {
                fighter = vehicleRepository.findById(id).orElse(null);
            }
            if (fighter == null) {
                // TODO : Metal gear!!
                //fighter = MetalGearRepository.findById(id).orElse(null);
            }
            if (fighter == null) {
                return false;
            }
            if (fighter.canGoToMission() && !fighter.isInMission()) {
                fighters.add(fighter);
            } else {
                return false;
            }

        }

        MissionInProgress missionInProgress = new MissionInProgress(mission, new Date(), motherBase, fighters);

        missionInProgress = missionInProgressRepository.save(missionInProgress);
        for (Fighter fighter : fighters) {
            fighter.setMissionInProgress(missionInProgress);
        }


        return true;

    }


    @Override
    @Transactional
    public FinishMissionResponse finishMission(MotherBase motherBase, Mission mission) {
        if (motherBase.isFinishMission(mission)) {
            StateMission stateMission;
            if (motherBase.isSuccessMission(mission)) {
                motherBase.receiveRewardMission(mission);
                stateMission = StateMission.Success;
            } else {
                stateMission = StateMission.Failed;
            }
            MissionInProgress toDelete = missionInProgressRepository.findById(new MissionInProgressID(mission.getId(), motherBase.getId())).orElse(null);

            // Injured Staff and Destroy Vehicle
            StrategyAffectStaff strategyAffectStaff = new NormalStrategyAffectStaff();
            List<Fighter> fighters = strategyAffectStaff.executeAffect(toDelete, (stateMission.equals(StateMission.Success)));

            List<Fighter> copy = new ArrayList<>(fighters);

            for (Fighter fighter : toDelete.getFighters()) {
                fighter.setMissionInProgress(null);
                if (fighter.isDead()) {
                    // TODO : It's ugly !!
                    fighter.dead();
                    Staff deadStaff = staffRepository.findById(fighter.getId()).orElse(null);
                    if (deadStaff != null) {
                        this.staffRepository.delete(deadStaff);
                    } else {
                        Vehicle deadVehicle = vehicleRepository.findById(fighter.getId()).orElse(null);
                        this.vehicleRepository.delete(deadVehicle);
                    }


                }
            }

            missionInProgressRepository.delete(toDelete);


            return new FinishMissionResponse(copy, stateMission);
        }
        return new FinishMissionResponse(null, StateMission.NotFinish);

    }
}
