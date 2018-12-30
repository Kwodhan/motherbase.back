package com.motherbase.apirest.model.mission.strategyReward;

import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.model.resource.Resource;

import java.util.Map;

/**
 * Define a strategy for rewards missions
 */
public interface StrategyReward {

    default void executeReward(MotherBase motherBase, Mission mission) {
        for (Map.Entry<Resource, Integer> reward : mission.getRewardResources().entrySet()) {
            motherBase.addResource(reward.getKey(), reward.getValue());

        }
        this.executeRewardResource(motherBase, mission);
        this.executeRewardStuff(motherBase, mission);
        this.executeRewardVehicle(motherBase, mission);
    }

    void executeRewardResource(MotherBase motherBase, Mission mission);

    void executeRewardStuff(MotherBase motherBase, Mission mission);

    void executeRewardVehicle(MotherBase motherBase, Mission mission);
}
