package com.motherbase.apirest.model.mission;

import com.motherbase.apirest.model.resource.Resource;
import com.motherbase.apirest.model.staff.Staff;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;

@Entity
public class Mission {
    private Long id;

    private HashMap<Resource, Integer> rewardResources;
    //private Set<Staff> rewardStaff;


    public Mission() {
    }

    public Mission(HashSet<Staff> rewardStaff, Integer... rewards) {
        this.rewardResources = new HashMap<>();
        int indexResource = 0;
        for (Integer resource : rewards) {
            this.rewardResources.put(Resource.values()[indexResource], resource);
        }

    }

    public Integer getRewardResource(Resource resource) {

        if (!this.rewardResources.containsKey(resource)) {
            return 0;
        }
        return this.rewardResources.get(resource);

    }

    public HashMap<Resource, Integer> getRewardResources() {
        return rewardResources;
    }

    public void setRewardResources(HashMap<Resource, Integer> rewardResources) {
        this.rewardResources = rewardResources;
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
