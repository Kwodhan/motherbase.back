package com.motherbase.apirest.model.mission;

import com.motherbase.apirest.model.staff.Staff;

import javax.persistence.*;
import java.util.HashSet;

@Entity
public class Mission {
    private Long id;
    private Integer rewardDollar;
    private Integer rewardFuel;
    private Integer rewardBiology;
    private Integer rewardOre;
    private Integer rewardGem;
    //private Set<Staff> rewardStaff;


    public Mission() {
    }

    public Mission(Integer rewardDollar, Integer rewardFuel, Integer rewardBiology, Integer rewardOre, Integer rewardGem, HashSet<Staff> rewardStaff) {
        this.rewardDollar = rewardDollar;
        this.rewardFuel = rewardFuel;
        this.rewardBiology = rewardBiology;
        this.rewardOre = rewardOre;
        this.rewardGem = rewardGem;
        //this.rewardStaff = rewardStaff;
    }

    public Integer getRewardDollar() {
        return rewardDollar;
    }

    public void setRewardDollar(Integer rewardDollar) {
        this.rewardDollar = rewardDollar;
    }

    public Integer getRewardFuel() {
        return rewardFuel;
    }

    public void setRewardFuel(Integer rewardFuel) {
        this.rewardFuel = rewardFuel;
    }

    public Integer getRewardBiology() {
        return rewardBiology;
    }

    public void setRewardBiology(Integer rewardBiology) {
        this.rewardBiology = rewardBiology;
    }

    public Integer getRewardOre() {
        return rewardOre;
    }

    public void setRewardOre(Integer rewardOre) {
        this.rewardOre = rewardOre;
    }

    public Integer getRewardGem() {
        return rewardGem;
    }

    public void setRewardGem(Integer rewardGem) {
        this.rewardGem = rewardGem;
    }

    /*
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    public Set<Staff> getRewardStaff() {
        return rewardStaff;
    }

    public void setRewardStaff(HashSet<Staff> rewardStaff) {
        this.rewardStaff = rewardStaff;
    }*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
