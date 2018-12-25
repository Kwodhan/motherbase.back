package com.motherbase.apirest.model.mission;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.motherbase.apirest.model.staff.RankStaff;
import com.motherbase.apirest.model.staff.Skill;

import javax.persistence.*;

@Entity
@Table(name = "MISSION_REWARD_STAFF")
public class RewardStaff {

    Long id;
    Skill skill;

    RankStaff rankMax;
    /**
     * number of staff
     */
    Integer numberStuff;
    /**
     * percent that the stuff have rankMax
     */
    Integer percent;
    /**
     * number of staff that it is sure that is rankMax
     */
    Integer numberFixRankMax;

    Mission mission;

    public RewardStaff(Skill skill, RankStaff rankMax, Integer numberStuff, Integer percent, Integer numberFixRankMax) {
        if (percent > 100 || percent < 0) {
            throw new IllegalArgumentException("percent must be between 0 and 100");
        }
        if (numberFixRankMax > numberStuff) {
            throw new IllegalArgumentException("numberFixRankMax doesn't be superior at numberStuff ");
        }
        this.skill = skill;
        this.rankMax = rankMax;
        this.numberStuff = numberStuff;
        this.percent = percent;
        this.numberFixRankMax = numberFixRankMax;
    }

    public RewardStaff(Skill skill, RankStaff rankMax, Integer numberStuff, Integer percent) {
        if (percent > 100 || percent < 0) {
            throw new IllegalArgumentException("percent must be between 0 and 100");
        }
        this.skill = skill;
        this.rankMax = rankMax;
        this.numberStuff = numberStuff;
        this.percent = percent;
        this.numberFixRankMax = numberStuff;
    }

    public RewardStaff(Skill skill, RankStaff rankMax, Integer numberStuff) {
        this.skill = skill;
        this.rankMax = rankMax;
        this.numberStuff = numberStuff;
        this.percent = 100;
        this.numberFixRankMax = numberStuff;
    }

    public RewardStaff() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Enumerated(EnumType.ORDINAL)
    public RankStaff getRankMax() {
        return rankMax;
    }

    public void setRankMax(RankStaff rankMax) {
        this.rankMax = rankMax;
    }

    public Integer getNumberStuff() {
        return numberStuff;
    }

    public void setNumberStuff(Integer numberStuff) {
        this.numberStuff = numberStuff;
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public Integer getNumberFixRankMax() {
        return numberFixRankMax;
    }

    public void setNumberFixRankMax(Integer numberFixRankMax) {
        this.numberFixRankMax = numberFixRankMax;
    }

    @Enumerated(EnumType.ORDINAL)
    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    @ManyToOne
    @JsonIgnore
    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }
}

