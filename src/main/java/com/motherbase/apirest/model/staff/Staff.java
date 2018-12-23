package com.motherbase.apirest.model.staff;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motherbase.apirest.model.motherbase.department.Department;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Staff {
    private Department department;
    private String name;
    private Map<Skill, RankStaff> skillSet;

    private Long id;

    public Staff() {
    }

    public Staff(String name, Department department, RankStaff... rankSkills) {
        if (rankSkills.length != Skill.values().length) {
            throw new IllegalArgumentException("There are not the same number between rankSkill arguments and number of skills in Skill enum ");
        }
        this.name = name;
        this.skillSet = new HashMap<>();
        int indexRank = 0;
        for (Skill skill : Skill.values()) {
            this.skillSet.put(skill, rankSkills[indexRank]);
            indexRank++;
        }
        this.department = department;
    }

    public void upgradeSkill(Skill skill) {
        if (this.skillSet.get(skill) != RankStaff.values()[RankStaff.values().length - 1]) {
            this.skillSet.replace(skill, RankStaff.values()[this.skillSet.get(skill).ordinal() + 1]);
        }
    }


    public void downgradSkill(Skill skill) {
        if (this.skillSet.get(skill) != RankStaff.values()[0]) {
            this.skillSet.replace(skill, RankStaff.values()[this.skillSet.get(skill).ordinal() - 1]);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    @MapKeyClass(Skill.class)
    @MapKeyEnumerated(EnumType.ORDINAL)
    // TODO : KEY to column name
    public Map<Skill, RankStaff> getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(Map<Skill, RankStaff> skillSet) {
        this.skillSet = skillSet;
    }
}
