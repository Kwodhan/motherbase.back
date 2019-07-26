package com.motherbase.apirest.service;

import com.motherbase.apirest.config.ParameterManager;
import com.motherbase.apirest.controller.responsecustom.FinishMissionResponse;
import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.mission.MissionInProgress;
import com.motherbase.apirest.model.mission.MissionInProgressID;
import com.motherbase.apirest.model.mission.StateMission;
import com.motherbase.apirest.model.mission.strategyAffectStaff.StrategyAffectStaff;
import com.motherbase.apirest.model.mission.strategyAffectStaff.StrategyAffectStaffEnum;
import com.motherbase.apirest.model.mission.strategyReward.RewardMission;
import com.motherbase.apirest.model.mission.strategyReward.StrategyReward;
import com.motherbase.apirest.model.mission.strategyReward.StrategyRewardEnum;
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
        updatedMission.setRankMission(mission.getRankMission());
        updatedMission.setRewardStaffs(mission.getRewardStaffs());
        updatedMission.setRewardResources(mission.getRewardResources());
        updatedMission.setRewardVehicles(mission.getRewardVehicles());
        updatedMission.setMissionInProgresses(mission.getMissionInProgresses());
        return updatedMission;
    }

    @Override
    @Transactional
    public boolean takeMission(MotherBase motherBase, Mission mission, List<Long> fightersId) {

        if (!motherBase.canTakeMission(mission)) {
            return false;
        }

        // Search Fighters in 3 tables (Staff, Vehicle, Metal gear) Beurk!
        // TODO : c'est moche
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

        RewardMission rewardMission = null;
        if (!motherBase.isFinishMission(mission)) {
            return new FinishMissionResponse(null, StateMission.NotFinish, rewardMission);
        }
        Object strategyStaffConf = ParameterManager.getValue("strategyAffectStaff");
        if (strategyStaffConf == null) {
            throw new IllegalStateException();
        }
        StrategyAffectStaff strategyAffectStaff = StrategyAffectStaffEnum.valueOf(strategyStaffConf.toString()).getStrategyAffectStaff();

        Object strategyRewardConf = ParameterManager.getValue("strategyReward");
        if (strategyRewardConf == null) {
            throw new IllegalStateException();
        }
        StrategyReward strategyReward = StrategyRewardEnum.valueOf(strategyRewardConf.toString()).getStrategyReward();

        StateMission stateMission;

        MissionInProgress missionInProgress = missionInProgressRepository.findById(new MissionInProgressID(mission.getId(), motherBase.getId())).orElse(null);

        if (missionInProgress == null) {
            return new FinishMissionResponse(null, StateMission.Failed, rewardMission);
        }

        if (motherBase.isSuccessMission(mission)) {
            rewardMission = strategyReward.getReward(mission);
            motherBase.receiveRewardMission(rewardMission);
            stateMission = StateMission.Success;

        } else {
            stateMission = StateMission.Failed;
        }

        // Injured Staff and Destroy Vehicle

        List<Fighter> fighters = strategyAffectStaff.executeAffect(missionInProgress, (stateMission.equals(StateMission.Success)));

        List<Fighter> copy = new ArrayList<>(fighters);

        for (Fighter fighter : missionInProgress.getFighters()) {
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

        missionInProgressRepository.delete(missionInProgress);

        return new FinishMissionResponse(copy, stateMission, rewardMission);

    }
}
