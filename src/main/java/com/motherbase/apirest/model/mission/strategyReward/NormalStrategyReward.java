package com.motherbase.apirest.model.mission.strategyReward;

import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.mission.RewardResource;
import com.motherbase.apirest.model.mission.RewardStaff;
import com.motherbase.apirest.model.mission.RewardVehicle;
import com.motherbase.apirest.model.motherbase.MotherBase;
import com.motherbase.apirest.model.motherbase.department.WaitingRoom;
import com.motherbase.apirest.model.staff.RankStaff;
import com.motherbase.apirest.model.staff.Skill;
import com.motherbase.apirest.model.staff.Staff;
import com.motherbase.apirest.model.staff.vehicule.Vehicle;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NormalStrategyReward implements StrategyReward {


    @Override
    public void executeRewardResource(MotherBase motherBase, Mission mission) {
        for (RewardResource rewardResource : mission.getPercentRewardResources()) {
            Random rand = new Random();
            int randomInteger = rand.nextInt(101);
            if (randomInteger <= rewardResource.getPercent()) {
                motherBase.addResource(rewardResource.getResource(), rewardResource.getNumber());
            }

        }
    }

    @Override
    public void executeRewardStuff(MotherBase motherBase, Mission mission) {
        for (RewardStaff rewardStaff : mission.getRewardStaffs()) {

            for (int i = 0; i < rewardStaff.getNumberFixRankMax(); i++) {
                motherBase.getWaitingRoom().addStaff(createStaff(rewardStaff.getRankMax(), rewardStaff.getSkill(), motherBase.getWaitingRoom()));

            }
        }

    }

    @Override
    public void executeRewardVehicle(MotherBase motherBase, Mission mission) {
        for (RewardVehicle rewardVehicle : mission.getRewardVehicles()) {
            motherBase.getGarage().addVehicle(new Vehicle(rewardVehicle.getTypeVehicle()));
        }
    }

    private Staff createStaff(RankStaff rankMax, Skill skillMax, WaitingRoom waitingRoom) {

        Map<Skill, RankStaff> skillSet = new HashMap<>();
        for (Skill skill : Skill.values()) {
            Random rand = new Random();
            int indexRandomLvl = rand.nextInt(rankMax.ordinal() + 1);
            skillSet.put(skill, RankStaff.values()[indexRandomLvl]);

        }
        skillSet.replace(skillMax, rankMax);

        String name = "Fail";
        try {
            JSONParser parser = new JSONParser();
            File file = ResourceUtils.getFile("classpath:jsonToParse/name.json");
            JSONArray a = (JSONArray) parser.parse(new FileReader(file));
            Random rand = new Random();
            int indexRandomName = rand.nextInt(a.size() + 1);
            name = a.get(indexRandomName).toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Staff(name, waitingRoom, skillSet);

    }
}
