package com.motherbase.apirest.dbinitializer;

import com.motherbase.apirest.model.mission.Mission;
import com.motherbase.apirest.model.mission.RewardResource;
import com.motherbase.apirest.model.mission.RewardStaff;
import com.motherbase.apirest.model.resource.Resource;
import com.motherbase.apirest.model.staff.RankStaff;
import com.motherbase.apirest.model.staff.Skill;
import com.motherbase.apirest.service.MissionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Component
@ConditionalOnProperty(name = "app.db-init.mission", havingValue = "true")
public class DbInitializerMission implements CommandLineRunner {

    MissionService missionService;

    public DbInitializerMission(MissionService missionService) {
        this.missionService = missionService;
    }

    /*
        public Mission(Integer force,Integer rankMission,Set<RewardResource> percentRewardResources ,Set<RewardStaff> rewardStaff, Integer... rewards) {
        this.force = force;
        this.rankMission = rankMission;
        this.percentRewardResources = percentRewardResources;
        this.rewardStaffs = rewardStaff;
        this.rewardResources = new HashMap<>();
        int indexResource = 0;
        for (Integer resource : rewards) {
            this.rewardResources.put(Resource.values()[indexResource], resource);
        }
    }


    public RewardStaff( Skill skill, RankStaff rankMax, Integer numberStuff, Integer percent, Integer numberFixRankMax) {
        this.skill = skill;
        this.rankMax = rankMax;
        this.numberStuff = numberStuff;
        this.percent = percent;
        this.numberFixRankMax = numberFixRankMax;
    }
        public RewardResource( Resource resource, Integer number, Integer percent) {
        this.resource = resource;
        this.number = number;
        this.percent = percent;
    }

     */
    @Override
    public void run(String... strings) throws Exception {
        Set<RewardResource> percentRewardResources = new HashSet<>();
        Set<RewardStaff> rewardStaff = new HashSet<>();
        rewardStaff.add(new RewardStaff(Skill.Combat, RankStaff.E, 2));
        percentRewardResources.add(new RewardResource(Resource.Fuel, 100, 60));
        Mission m1 = new Mission(20, "Fuel", 1, Duration.ofMinutes(1), rewardStaff, percentRewardResources, 110, 20, 0, 0, 0);
        this.missionService.create(m1);

        percentRewardResources = new HashSet<>();
        rewardStaff = new HashSet<>();
        rewardStaff.add(new RewardStaff(Skill.Doctor, RankStaff.E, 2));
        percentRewardResources.add(new RewardResource(Resource.Biology, 100, 60));
        Mission m2 = new Mission(20, "Biology", 1, Duration.ofMinutes(1), rewardStaff, percentRewardResources, 110, 0, 20, 0, 0);
        this.missionService.create(m2);

        percentRewardResources = new HashSet<>();
        rewardStaff = new HashSet<>();
        rewardStaff.add(new RewardStaff(Skill.Engineer, RankStaff.E, 2));
        percentRewardResources.add(new RewardResource(Resource.Ore, 100, 60));
        Mission m3 = new Mission(20, "Ore", 1, Duration.ofMinutes(1), rewardStaff, percentRewardResources, 110, 0, 0, 20, 0);
        this.missionService.create(m3);

        percentRewardResources = new HashSet<>();
        rewardStaff = new HashSet<>();
        rewardStaff.add(new RewardStaff(Skill.Science, RankStaff.E, 2));
        percentRewardResources.add(new RewardResource(Resource.Gem, 100, 60));
        Mission m4 = new Mission(20, "Gem", 1, Duration.ofMinutes(1), rewardStaff, percentRewardResources, 110, 0, 0, 0, 5);
        this.missionService.create(m4);

        percentRewardResources = new HashSet<>();
        rewardStaff = new HashSet<>();
        rewardStaff.add(new RewardStaff(Skill.Combat, RankStaff.E, 4));
        rewardStaff.add(new RewardStaff(Skill.Engineer, RankStaff.D, 4, 30, 2));
        percentRewardResources.add(new RewardResource(Resource.Dollar, 1000, 80));
        percentRewardResources.add(new RewardResource(Resource.Fuel, 100, 60));
        Mission m5 = new Mission(20, "Gem", 1, Duration.ofMinutes(5), rewardStaff, percentRewardResources, 110, 0, 0, 0, 5);
        this.missionService.create(m5);
    }

}