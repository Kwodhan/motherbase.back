package com.motherbase.apirest.model.mission.strategyReward;

import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.mission.RewardResource;
import com.motherbase.apirest.model.mission.RewardStaff;
import com.motherbase.apirest.model.mission.RewardVehicle;
import com.motherbase.apirest.model.resource.Resource;
import com.motherbase.apirest.model.staff.RankStaff;
import com.motherbase.apirest.model.staff.Skill;
import com.motherbase.apirest.model.staff.Staff;
import com.motherbase.apirest.model.staff.vehicule.Vehicle;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NormalStrategyReward implements StrategyReward {


    @Override
    public Map<Resource, Integer> executeRewardResource(Mission mission) {
        Map<Resource, Integer> rewardResources = new HashMap<>();

        for (RewardResource rewardResource : mission.getRewardResources()) {
            if (rewardResource.getPercent() == 100) {
                rewardResources.put(rewardResource.getResource(), rewardResource.getNumber());
                continue;
            }
            Random rand = new Random();
            int randomInteger = rand.nextInt(101);
            if (randomInteger <= rewardResource.getPercent()) {
                rewardResources.put(rewardResource.getResource(), rewardResource.getNumber());
            }
        }
        return rewardResources;
    }

    @Override
    public List<Staff> executeRewardStuff(Mission mission) {
        List<Staff> staffRewards = new ArrayList<>();
        for (RewardStaff rewardStaff : mission.getRewardStaffs()) {

            for (int i = 0; i < rewardStaff.getNumberFixRankMax(); i++) {
                staffRewards.add(createStaff(rewardStaff.getRankMax(), rewardStaff.getSkill()));
            }
        }
        return staffRewards;

    }

    @Override
    public List<Vehicle> executeRewardVehicle(Mission mission) {
        List<Vehicle> vehicleRewards = new ArrayList<>();
        for (RewardVehicle rewardVehicle : mission.getRewardVehicles()) {
            vehicleRewards.add(new Vehicle(rewardVehicle.getTypeVehicle()));
        }
        return vehicleRewards;
    }

    private Staff createStaff(RankStaff rankMax, Skill skillMax) {

        Map<Skill, RankStaff> skillSet = new HashMap<>();
        for (Skill skill : Skill.values()) {
            Random rand = new Random();
            int indexRandomLvl = rand.nextInt(rankMax.ordinal() + 1);
            skillSet.put(skill, RankStaff.values()[indexRandomLvl]);

        }
        skillSet.replace(skillMax, rankMax);

        String name = "Fail Name";
        try {
            JSONParser parser = new JSONParser();
            File file = ResourceUtils.getFile("classpath:jsonToParse/name.json");
            JSONArray a = (JSONArray) parser.parse(new FileReader(file));
            Random rand = new Random();
            int indexRandomName = rand.nextInt(a.size() + 1);
            name = a.get(indexRandomName).toString();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

        return new Staff(name, skillSet);

    }
}
