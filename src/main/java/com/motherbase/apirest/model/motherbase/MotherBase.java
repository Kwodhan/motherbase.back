package com.motherbase.apirest.model.motherbase;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.mission.MissionInProgress;
import com.motherbase.apirest.model.motherbase.department.*;
import com.motherbase.apirest.model.resource.Resource;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    private void receiveRewardMission(Mission mission) {
        for (Map.Entry<Resource, Integer> reward : mission.getRewardResources().entrySet()) {
            this.addResource(reward.getKey(), reward.getValue());

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

    public void triggerUpgrade(Department department) {
        if (department.getRank().equals(RankDepartment.values()[RankDepartment.values().length - 1])) {
            return;
        }
        RankDepartment nextRank = RankDepartment.values()[department.getRank().ordinal() + 1];
        for (Map.Entry<Resource, Integer> cost : nextRank.getCostResource().entrySet()) {
            this.removeResource(cost.getKey(), cost.getValue());
        }
        department.setDateBeginUpgrade(new Date());
    }

    public Department upgrade(Department department) {
        department.upgradeRank();
        department.setDateBeginUpgrade(null);
        return department;
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
        MissionInProgress missionInProgress = this.getMissionInProgress().stream().filter(x -> x.getMission().getId().equals(mission.getId())).findFirst().orElse(null);
        if (missionInProgress == null) {
            return false;
        }
        Date finish = new Date(missionInProgress.getDateBegin().getTime() + mission.getDurationCompleted().toMillis());

        return today.after(finish);
    }

    public MotherBase finishMission(Mission mission) {

        this.receiveRewardMission(mission);
        return this;

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


    public Integer addResource(Resource resource, Integer add) {
        Integer actualRes = this.resources.get(resource);
        return this.resources.replace(resource, actualRes + add);

    }

    public Integer removeResource(Resource resource, Integer add) {
        Integer actualRes = this.resources.get(resource);
        return this.resources.replace(resource, actualRes - add);
    }

    public Integer getResource(Resource resource) {
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
    // TODO : KEY to column name
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
