package com.motherbase.apirest.model.motherbase;

import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.motherbase.department.*;
import com.motherbase.apirest.model.ressource.Ressources;
import com.motherbase.apirest.model.staff.Staff;

import javax.persistence.*;

@Entity
public class MotherBase {
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
    private Long id;


    public MotherBase() {
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
    public boolean changeStaffDepartment(Staff staff, Department department) throws Exception {
        // search the staff in departments
        Department departmentOut = null;
        if (waitingRoom.isInDepartment(staff)) {
            departmentOut = waitingRoom;
        } else if (barrack.isInDepartment(staff)) {
            departmentOut = barrack;
        } else if (randD.isInDepartment(staff)) {
            departmentOut = randD;
        } else if (development.isInDepartment(staff)) {
            departmentOut = development;
        } else if (infirmary.isInDepartment(staff)) {
            departmentOut = infirmary;
        } else {
            throw new Exception("The staff " + staff.getName() + " is ghost");
        }

        if (department.isPossibleToAddStuff()) {
            department.addStaff(staff);
            departmentOut.removeStaff(staff);
            return true;
        }
        return false;


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

    @OneToOne
    public WaitingRoom getWaitingRoom() {
        return waitingRoom;
    }

    public void setWaitingRoom(WaitingRoom waitingRoom) {
        this.waitingRoom = waitingRoom;
    }

    @OneToOne
    public Barrack getBarrack() {
        return barrack;
    }

    public void setBarrack(Barrack barrack) {
        this.barrack = barrack;
    }

    @OneToOne
    public RandD getRandD() {
        return randD;
    }

    public void setRandD(RandD randD) {
        this.randD = randD;
    }

    @OneToOne
    public Development getDevelopment() {
        return development;
    }

    public void setDevelopment(Development development) {
        this.development = development;
    }

    @OneToOne
    public Infirmary getInfirmary() {
        return infirmary;
    }

    public void setInfirmary(Infirmary infirmary) {
        this.infirmary = infirmary;
    }
}
