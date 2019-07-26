package com.motherbase.apirest.model.mission.strategyReward;

import com.motherbase.apirest.model.resource.Resource;
import com.motherbase.apirest.model.staff.Staff;
import com.motherbase.apirest.model.staff.vehicule.Vehicle;

import java.util.List;
import java.util.Map;

/**
 * Get All rewards from mission
 */
public class RewardMission {

    private Map<Resource, Integer> rewardResource;

    private List<Staff> rewardStuff;

    private List<Vehicle> rewardVehicle;

    protected RewardMission(Map<Resource, Integer> rewardResource, List<Staff> rewardStuff, List<Vehicle> rewardVehicle) {
        this.rewardResource = rewardResource;
        this.rewardStuff = rewardStuff;
        this.rewardVehicle = rewardVehicle;
    }

    public Map<Resource, Integer> getRewardResource() {
        return rewardResource;
    }

    public void setRewardResource(Map<Resource, Integer> rewardResource) {
        this.rewardResource = rewardResource;
    }


    public List<Staff> getRewardStuff() {
        return rewardStuff;
    }

    public void setRewardStuff(List<Staff> rewardStuff) {
        this.rewardStuff = rewardStuff;
    }

    public List<Vehicle> getRewardVehicle() {
        return rewardVehicle;
    }

    public void setRewardVehicle(List<Vehicle> rewardVehicle) {
        this.rewardVehicle = rewardVehicle;
    }
}
