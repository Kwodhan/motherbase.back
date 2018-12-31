package com.motherbase.apirest.model.mission.strategyReward;

import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.resource.Resource;
import com.motherbase.apirest.model.staff.Staff;
import com.motherbase.apirest.model.staff.vehicule.Vehicle;

import java.util.List;
import java.util.Map;

/**
 * Define a strategy for rewards missions
 */
public interface StrategyReward {

    default RewardMission getReward(Mission mission) {
        return new RewardMission(executeRewardResource(mission), executeRewardResourcePercentage(mission), executeRewardStuff(mission), executeRewardVehicle(mission));
    }


    /**
     * 100% sure of rewards resource
     *
     * @return resource with integer
     */
    Map<Resource, Integer> executeRewardResource(Mission mission);

    /**
     * not sure of rewards resource
     *
     * @return resource with integer
     */
    Map<Resource, Integer> executeRewardResourcePercentage(Mission mission);

    List<Staff> executeRewardStuff(Mission mission);

    List<Vehicle> executeRewardVehicle(Mission mission);
}
