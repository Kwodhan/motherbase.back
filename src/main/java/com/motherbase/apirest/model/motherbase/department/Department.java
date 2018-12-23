package com.motherbase.apirest.model.motherbase.department;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.model.staff.Staff;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public abstract class Department {
    protected MotherBase motherBase;
    protected RankDepartment rank;
    protected Set<Staff> listStaff;
    protected Long id;
    protected Date dateBeginUpgrade;


    public Department() {
        this.listStaff = new HashSet<>();
        this.dateBeginUpgrade = null;
    }

    public void upgradeRank() {
        if (this.rank != RankDepartment.Level4) {
            this.rank = RankDepartment.values()[this.rank.ordinal() + 1];
        }

    }

    @Transient
    public boolean isPossibleToAddStuff() {
        return this.getSizeStaff() < this.getMaxStaff();
    }


    public void addStaff(Staff staff) {
        this.listStaff.add(staff);
        staff.setDepartment(this);
    }

    public void removeStaff(Staff staff) {
        this.listStaff.remove(staff);
        staff.setDepartment(null);
    }

    @Transient
    public Integer getMaxStaff() {
        return this.rank.getMaxStuff();
    }

    @Transient
    public Integer getSizeStaff() {
        return this.listStaff.size();
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

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Staff> getListStaff() {
        return listStaff;
    }

    public void setListStaff(Set<Staff> listStaff) {
        this.listStaff = listStaff;
    }

    @Enumerated(EnumType.ORDINAL)
    public RankDepartment getRank() {
        return rank;
    }

    public void setRank(RankDepartment rank) {
        this.rank = rank;
    }


    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public MotherBase getMotherBase() {
        return motherBase;
    }

    public void setMotherBase(MotherBase motherBase) {
        this.motherBase = motherBase;
    }

    public Date getDateBeginUpgrade() {
        return dateBeginUpgrade;
    }

    public void setDateBeginUpgrade(Date dateBeginUpgrade) {
        this.dateBeginUpgrade = dateBeginUpgrade;
    }
}
