package com.motherbase.apirest.service;

import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.mission.MissionInProgress;
import com.motherbase.apirest.model.mission.MissionInProgressID;
import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.model.motherbase.department.Department;
import com.motherbase.apirest.model.staff.Fighter;
import com.motherbase.apirest.model.staff.Staff;
import com.motherbase.apirest.repository.MissionInProgressRepository;
import com.motherbase.apirest.repository.MotherBaseRepository;
import com.motherbase.apirest.repository.StaffRepository;
import com.motherbase.apirest.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MotherBaseServiceImpl implements MotherBaseService {

    @Autowired
    private MotherBaseRepository motherBaseRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private MissionInProgressRepository missionInProgressRepository;


    public MotherBaseServiceImpl() {
    }

    @Override
    @Transactional
    public MotherBase create(MotherBase motherBase) {

        return motherBaseRepository.save(motherBase);
    }

    @Override
    public MotherBase findById(Long id) {
        return motherBaseRepository.findById(id).orElse(null);
    }

    @Override
    public List<MotherBase> findByPseudo(String pseudo) {
        return motherBaseRepository.findMotherBaseByPseudo(pseudo);
    }


    @Override
    @Transactional
    public boolean moveStaff(Staff staff, Department department) {
        if (!department.isPossibleToAddStuff()) {
            return false;
        }
        staff.getDepartment().removeStaff(staff);
        department.addStaff(staff);
        return true;

    }

    @Override
    @Transactional
    public boolean triggerUpgradeDepartment(MotherBase motherBase, Department department) {
        if (motherBase.canUpgrade(department)) {
            motherBase.triggerUpgrade(department);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Department upgradeDepartment(MotherBase motherBase, Department department) {

        return motherBase.upgrade(department);
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
            if (!fighter.isDown() && !fighter.isInMission()) {
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
    public boolean finishMission(MotherBase motherBase, Mission mission) {
        if (motherBase.isFinishMission(mission)) {
            motherBase.finishMission(mission);
            MissionInProgress toDelete = missionInProgressRepository.findById(new MissionInProgressID(mission.getId(), motherBase.getId())).orElse(null);
            for (Fighter fighter : toDelete.getFighters()) {
                fighter.setMissionInProgress(null);
            }
            missionInProgressRepository.delete(toDelete);
            return true;
        }
        return false;

    }

}
