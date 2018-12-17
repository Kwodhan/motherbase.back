package com.motherbase.apirest.model.motherbase;

import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.motherbase.department.*;
import com.motherbase.apirest.model.ressource.Ressources;

import javax.persistence.*;

@Entity
public class MotherBase {
    private Long id;
    private String pseudo;
    private Integer nbDollar;
    private Integer nbFuel;
    private Integer nbBiology;
    private Integer nbOre;
    private Integer nbGem;
    private WaitingRoom waitingRoom;
    private Barrack barrack;
    private RandD randD;
    private Development development;
    private Infirmary infirmary;


    public MotherBase() {
    }

    public MotherBase(String pseudo) {
        this.pseudo = pseudo;
        this.nbDollar = Ressources.Dollar.getInitialStock();
        this.nbFuel = Ressources.Fuel.getInitialStock();
        this.nbBiology = Ressources.Biology.getInitialStock();
        this.nbOre = Ressources.Ore.getInitialStock();
        this.nbGem = Ressources.Gem.getInitialStock();
        this.waitingRoom = new WaitingRoom();
        this.barrack = new Barrack();
        this.randD = new RandD();
        this.development = new Development();
        this.infirmary = new Infirmary();
        this.randD.setMotherBase(this);
        this.waitingRoom.setMotherBase(this);
        this.barrack.setMotherBase(this);
        this.development.setMotherBase(this);
        this.infirmary.setMotherBase(this);

    }

    public void receiveRewardMission(Mission mission) {
        this.addNbDollar(mission.getRewardDollar());
        this.addNbBiology(mission.getRewardBiology());
        this.addNbFuel(mission.getRewardFuel());
        this.addNbGem(mission.getRewardGem());
        this.addNbOre(mission.getRewardOre());
       /* for(Staff staff : mission.getRewardStaff() ){
            this.waitingRoom.addStaff(staff);
        }*/

    }
    @Transient
    public boolean canUpgrade(Department department) {
        if (department.getRank().equals(RankDepartment.values()[RankDepartment.values().length - 1])) {
            return false;
        }
        RankDepartment nextRank = RankDepartment.values()[department.getRank().ordinal() + 1];
        if (this.getNbFuel() < nextRank.getCostFuel()) {
            return false;
        }
        if (this.getNbBiology() < nextRank.getCostBiology()) {
            return false;
        }
        if (this.getNbDollar() < nextRank.getCostDollar()) {
            return false;
        }
        if (this.getNbGem() < nextRank.getCostGem()) {
            return false;
        }
        return this.getNbOre() >= nextRank.getCostOre();
    }

    @Transient
    public void upgrade(Department department) {
        if (department.getRank().equals(RankDepartment.values()[RankDepartment.values().length - 1])) {
            return;
        }
        RankDepartment nextRank = RankDepartment.values()[department.getRank().ordinal() + 1];
        this.removeNbDollar(nextRank.getCostDollar());
        this.removeNbFuel(nextRank.getCostFuel());
        this.removeNbBiology(nextRank.getCostBiology());
        this.removeNbGem(nextRank.getCostGem());
        this.removeNbOre(nextRank.getCostOre());
        department.upgradeRank();
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


    @Transient
    public Integer addNbDollar(Integer dollar) {
        return this.nbDollar += dollar;
    }

    @Transient
    public Integer addNbFuel(Integer fuel) {
        return this.nbFuel += fuel;
    }

    @Transient
    public Integer addNbBiology(Integer biology) {
        return this.nbBiology += biology;
    }

    @Transient
    public Integer addNbOre(Integer ore) {
        return this.nbOre += ore;
    }

    @Transient
    public Integer addNbGem(Integer gem) {
        return this.nbGem += gem;
    }

    @Transient
    public Integer removeNbDollar(Integer dollar) {
        return this.nbDollar -= dollar;
    }

    @Transient
    public Integer removeNbFuel(Integer fuel) {
        return this.nbFuel -= fuel;
    }

    @Transient
    public Integer removeNbBiology(Integer biology) {
        return this.nbBiology -= biology;
    }

    @Transient
    public Integer removeNbOre(Integer ore) {
        return this.nbOre -= ore;
    }

    @Transient
    public Integer removeNbGem(Integer gem) {
        return this.nbGem -= gem;
    }
    public Integer getNbDollar() {
        return nbDollar;
    }

    public void setNbDollar(Integer nbDollar) {
        this.nbDollar = nbDollar;
    }

    public Integer getNbFuel() {
        return nbFuel;
    }

    public void setNbFuel(Integer nbFuel) {
        this.nbFuel = nbFuel;
    }

    public Integer getNbBiology() {
        return nbBiology;
    }

    public void setNbBiology(Integer nbBiology) {
        this.nbBiology = nbBiology;
    }

    public Integer getNbOre() {
        return nbOre;
    }

    public void setNbOre(Integer nbOre) {
        this.nbOre = nbOre;
    }

    public Integer getNbGem() {
        return nbGem;
    }

    public void setNbGem(Integer nbGem) {
        this.nbGem = nbGem;
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

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
}
