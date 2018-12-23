package com.motherbase.apirest.model.mission;

import com.motherbase.apirest.model.resource.Resource;
import com.motherbase.apirest.model.staff.Staff;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
public class Mission {
    private Long id;

    private Map<Resource, Integer> rewardResources;
    private Set<RewardStaff> rewardStaffs;
    /**
     * reward resource not sure
     */
    private Set<RewardResource> percentRewardResources;


    private Integer force;


    public Mission() {
    }

    public Mission(HashSet<Staff> rewardStaff, Integer... rewards) {
        this.rewardResources = new HashMap<>();
        int indexResource = 0;
        for (Integer resource : rewards) {
            this.rewardResources.put(Resource.values()[indexResource], resource);
        }
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable
    @MapKeyClass(Resource.class)
    @MapKeyEnumerated(EnumType.ORDINAL)
    public Map<Resource, Integer> getRewardResources() {
        return rewardResources;
    }

    public void setRewardResources(Map<Resource, Integer> rewardResources) {
        this.rewardResources = rewardResources;
    }

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<RewardResource> getPercentRewardResources() {
        return percentRewardResources;
    }

    public void setPercentRewardResources(Set<RewardResource> percentRewardResources) {
        this.percentRewardResources = percentRewardResources;
    }

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<RewardStaff> getRewardStaffs() {
        return rewardStaffs;
    }

    public void setRewardStaffs(Set<RewardStaff> rewardStaffs) {
        this.rewardStaffs = rewardStaffs;
    }

    public Integer getForce() {
        return force;
    }

    public void setForce(Integer force) {
        this.force = force;
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


}
