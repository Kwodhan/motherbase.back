package com.motherbase.apirest.model.motherbase;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.mission.MissionInProgress;
import com.motherbase.apirest.model.mission.strategyReward.RewardMission;
import com.motherbase.apirest.model.motherbase.department.*;
import com.motherbase.apirest.model.resource.Resource;
import com.motherbase.apirest.model.staff.Fighter;
import com.motherbase.apirest.model.staff.Staff;
import com.motherbase.apirest.model.staff.vehicule.Vehicle;

import javax.persistence.*;
import java.util.*;


@Entity
public class MotherBase {

    private Long id;
    private String pseudo;
    private Map<Resource, Integer> resources;

    private WaitingRoom waitingRoom;
    private Barrack barrack;
    private RandD randD;
    private Development development;
    private Infirmary infirmary;

    private Garage garage;

    private Set<MissionInProgress> missionInProgress;

    private Integer rankHistory;


    public MotherBase() {
    }

    public MotherBase(String pseudo) {
        this.pseudo = pseudo;
        this.resources = new HashMap<>();
        for (Resource resource : Resource.values()) {
            this.resources.putIfAbsent(resource, resource.getInitialStock());
        }
        this.rankHistory = 1;
        this.waitingRoom = new WaitingRoom();
        this.barrack = new Barrack();
        this.randD = new RandD();
        this.development = new Development();
        this.infirmary = new Infirmary();
        this.garage = new Garage();

        this.garage.setMotherBase(this);
        this.randD.setMotherBase(this);
        this.waitingRoom.setMotherBase(this);
        this.barrack.setMotherBase(this);
        this.development.setMotherBase(this);
        this.infirmary.setMotherBase(this);


    }

    public void receiveRewardMission(RewardMission rewardMission) {

        // Staffs
        for (Staff staff : rewardMission.getRewardStuff()) {
            this.getWaitingRoom().addStaff(staff);
        }

        // Vehicles
        for (Vehicle vehicle : rewardMission.getRewardVehicle()) {
            this.getGarage().addVehicle(vehicle);
        }

        // Resource
        for (Map.Entry<Resource, Integer> entry : rewardMission.getRewardResource().entrySet()) {
            this.addResource(entry.getKey(), entry.getValue());
        }

    }
    @Transient
    public boolean canUpgrade(Department department) {
        if (department.getRank().equals(RankDepartment.values()[RankDepartment.values().length - 1])) {
            return false;
        }
        RankDepartment nextRank = RankDepartment.values()[department.getRank().ordinal() + 1];
        for (Map.Entry<Resource, Integer> cost : nextRank.getCostResource().entrySet()) {
            if (this.getResource(cost.getKey()) < cost.getValue()) {
                return false;
            }
        }
        return true;
    }

    public void beginUpgrade(Department department) {
        if (department.getRank().equals(RankDepartment.values()[RankDepartment.values().length - 1])) {
            return;
        }
        RankDepartment nextRank = RankDepartment.values()[department.getRank().ordinal() + 1];
        for (Map.Entry<Resource, Integer> cost : nextRank.getCostResource().entrySet()) {
            this.removeResource(cost.getKey(), cost.getValue());
        }
        department.setDateBeginUpgrade(new Date());
    }

    public boolean isFinishUpgrade(Department department) {
        if (department.getDateBeginUpgrade() == null) {
            return false;
        }
        Date today = new Date();
        RankDepartment nextRank = RankDepartment.values()[department.getRank().ordinal() + 1];
        Date finish = new Date(department.getDateBeginUpgrade().getTime() + nextRank.getDurationUpgrade().toMillis());

        return today.after(finish);
    }

    public void upgrade(Department department) {
        department.upgradeRank();
        department.setDateBeginUpgrade(null);
    }

    public boolean canTakeMission(Mission mission) {

        if (this.rankHistory > mission.getRankMission()) {
            return false;
        }
        // if the mission is already taken by the user
        for (MissionInProgress missionInProgress : this.getMissionInProgress()) {
            if (missionInProgress.getMission().getId().equals(mission.getId())) {
                return false;
            }
        }
        return true;
    }


    public boolean isFinishMission(Mission mission) {
        Date today = new Date();
        MissionInProgress missionInProgress = this.getMissionInProgress(mission);
        if (missionInProgress == null) {
            return false;
        }
        Date finish = new Date(missionInProgress.getDateBegin().getTime() + mission.getDurationCompleted().toMillis());

        return today.after(finish);
    }

    public boolean isSuccessMission(Mission mission) {
        Random rand = new Random();
        int randomInteger = rand.nextInt(101);
        return randomInteger <= getPercentageSuccess(mission);
    }

    public double getPercentageSuccess(Mission mission) {
        MissionInProgress missionInProgress = this.getMissionInProgress(mission);

        int force = 0;
        double percentageSuccess;
        for (Fighter fighter : missionInProgress.getFighters()) {
            force += fighter.getForce();
        }
        if (force <= mission.getForce()) {
            double rapport = (double) force / (double) mission.getForce();
            percentageSuccess = rapport * 100;
        } else {
            percentageSuccess = mission.getMaxPercentageSuccess();
        }

        if (percentageSuccess > mission.getMaxPercentageSuccess()) {
            percentageSuccess = mission.getMaxPercentageSuccess();
        }

        return percentageSuccess;
    }


    /**
     * @param mission the mission
     * @return can be null if the motherBase doesn't have the mission
     */
    public MissionInProgress getMissionInProgress(Mission mission) {
        return this.getMissionInProgress()
                .stream()
                .filter(x -> x.getMission().getId().equals(mission.getId()))
                .findFirst()
                .orElse(null);
    }

    public void removeFighter(Staff staff) {
        staff.getDepartment().removeStaff(staff);
    }

    public void removeFighter(Vehicle vehicle) {
        vehicle.getGarage().removeVehicle(vehicle);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    private Integer addResource(Resource resource, Integer add) {
        Integer actualRes = this.resources.get(resource);
        return this.resources.replace(resource, actualRes + add);

    }

    private Integer removeResource(Resource resource, Integer add) {
        Integer actualRes = this.resources.get(resource);
        return this.resources.replace(resource, actualRes - add);
    }

    private Integer getResource(Resource resource) {
        return this.resources.get(resource);
    }
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "motherBase")
    public WaitingRoom getWaitingRoom() {
        return waitingRoom;
    }

    public void setWaitingRoom(WaitingRoom waitingRoom) {
        this.waitingRoom = waitingRoom;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "motherBase")
    public Barrack getBarrack() {
        return barrack;
    }

    public void setBarrack(Barrack barrack) {
        this.barrack = barrack;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "motherBase")
    public RandD getRandD() {
        return randD;
    }

    public void setRandD(RandD randD) {
        this.randD = randD;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "motherBase")
    public Development getDevelopment() {
        return development;
    }

    public void setDevelopment(Development development) {
        this.development = development;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "motherBase")
    public Infirmary getInfirmary() {
        return infirmary;
    }

    public void setInfirmary(Infirmary infirmary) {
        this.infirmary = infirmary;
    }

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "motherBase")
    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    @MapKeyClass(Resource.class)
    @MapKeyEnumerated(EnumType.ORDINAL)
    public Map<Resource, Integer> getResources() {
        return resources;
    }

    public void setResources(Map<Resource, Integer> resources) {
        this.resources = resources;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "motherBase")
    public Set<MissionInProgress> getMissionInProgress() {
        return missionInProgress;
    }

    public void setMissionInProgress(Set<MissionInProgress> missionInProgress) {
        this.missionInProgress = missionInProgress;
    }

    @JsonIgnore
    public Integer getRankHistory() {
        return rankHistory;
    }

    public void setRankHistory(Integer rankHistory) {
        this.rankHistory = rankHistory;
    }

}
