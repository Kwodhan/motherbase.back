package com.motherbase.apirest.model.motherbase.department;

import com.fasterxml.jackson.annotation.JsonValue;
import com.motherbase.apirest.model.resource.Resource;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * The cost to spend to upgrade the level
 */
public enum RankDepartment {
    Level0(Duration.ZERO, 0, 0, 0, 0, 0, 0),
    Level1(Duration.ofHours(1), 20, 30, 20, 20, 20, 20),
    Level2(Duration.ofHours(4), 30, 30, 20, 20, 20, 20),
    Level3(Duration.ofHours(12), 40, 30, 20, 20, 20, 20),
    Level4(Duration.ofHours(24), 50, 30, 20, 20, 20, 20);


    private Duration durationUpgrade;
    private Map<Resource, Integer> costResource;
    private Integer maxStuff;

    RankDepartment(Duration durationUpgrade, Integer maxStuff, Integer... tabResources) {

        this.durationUpgrade = durationUpgrade;
        this.maxStuff = maxStuff;
        this.costResource = new HashMap<>();
        int indexResource = 0;

        for (Integer resource : tabResources) {
            this.costResource.put(Resource.values()[indexResource], resource);
        }

    }

    @JsonValue
    public int toValue() {
        return ordinal();
    }

    public Integer getCostResource(Resource resource) {
        if (!this.costResource.containsKey(resource)) {
            return 0;
        }
        return this.costResource.get(resource);
    }

    public Duration getDurationUpgrade() {
        return durationUpgrade;
    }

    public Map<Resource, Integer> getCostResource() {
        return costResource;
    }

    public Integer getMaxStuff() {
        return maxStuff;
    }}
