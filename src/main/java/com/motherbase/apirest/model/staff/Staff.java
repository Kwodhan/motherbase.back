package com.motherbase.apirest.model.staff;

import com.motherbase.apirest.model.motherbase.department.Department;

import javax.persistence.*;

@Entity
public class Staff {
    private Department department;
    private String name;
    private RankStaff rankCombat;
    private RankStaff rankRandD;
    private RankStaff rankDevelopment;
    private RankStaff rankInfirmary;
    private Long id;

    public Staff() {
    }

    public Staff(String name, RankStaff rankCombat, RankStaff rankRandD, RankStaff rankDevelopment, RankStaff rankInfirmary) {
        this.name = name;
        this.rankCombat = rankCombat;
        this.rankRandD = rankRandD;
        this.rankDevelopment = rankDevelopment;
        this.rankInfirmary = rankInfirmary;
    }

    public void upgradeCombat() {
        if (this.rankCombat != RankStaff.S) {
            this.rankCombat = RankStaff.values()[this.rankCombat.ordinal() + 1];
        }
    }

    public void upgradeRandD() {
        if (this.rankRandD != RankStaff.S) {
            this.rankRandD = RankStaff.values()[this.rankRandD.ordinal() + 1];
        }
    }

    public void upgradeDevelopment() {
        if (this.rankDevelopment != RankStaff.S) {
            this.rankDevelopment = RankStaff.values()[this.rankDevelopment.ordinal() + 1];
        }
    }

    public void upgradeInfirmary() {
        if (this.rankInfirmary != RankStaff.S) {
            this.rankInfirmary = RankStaff.values()[this.rankInfirmary.ordinal() + 1];
        }
    }

    public void downgradCombat() {
        if (this.rankCombat != RankStaff.E) {
            this.rankCombat = RankStaff.values()[this.rankCombat.ordinal() - 1];
        }
    }

    public void downgradRandD() {
        if (this.rankRandD != RankStaff.E) {
            this.rankRandD = RankStaff.values()[this.rankRandD.ordinal() - 1];
        }
    }

    public void downgradDevelopment() {
        if (this.rankDevelopment != RankStaff.E) {
            this.rankDevelopment = RankStaff.values()[this.rankDevelopment.ordinal() - 1];
        }
    }

    public void downgradInfirmary() {
        if (this.rankInfirmary != RankStaff.E) {
            this.rankInfirmary = RankStaff.values()[this.rankInfirmary.ordinal() - 1];
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RankStaff getRankCombat() {
        return rankCombat;
    }

    public void setRankCombat(RankStaff rankCombat) {
        this.rankCombat = rankCombat;
    }

    public RankStaff getRankRandD() {
        return rankRandD;
    }

    public void setRankRandD(RankStaff rankRandD) {
        this.rankRandD = rankRandD;
    }

    public RankStaff getRankDevelopment() {
        return rankDevelopment;
    }

    public void setRankDevelopment(RankStaff rankDevelopment) {
        this.rankDevelopment = rankDevelopment;
    }

    public RankStaff getRankInfirmary() {
        return rankInfirmary;
    }

    public void setRankInfirmary(RankStaff rankInfirmary) {
        this.rankInfirmary = rankInfirmary;
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

    @ManyToOne
    @JoinColumn(name = "department", referencedColumnName = "ID")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
