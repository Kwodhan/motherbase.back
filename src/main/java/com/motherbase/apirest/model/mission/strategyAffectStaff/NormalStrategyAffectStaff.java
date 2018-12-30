package com.motherbase.apirest.model.mission.strategyAffectStaff;

import com.motherbase.apirest.model.mission.MissionInProgress;
import com.motherbase.apirest.model.staff.Fighter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class NormalStrategyAffectStaff implements StrategyAffectStaff {


    @Override
    public List<Fighter> executeAffect(MissionInProgress missionInProgress, boolean successMission) {

        double multiplierDyingFailedMission = 2.0;
        double multiplierInjuringFailedMission = 2.0;
        try {
            File file = ResourceUtils.getFile("classpath:jsonToParse/parameters.json");
            JSONParser parser = new JSONParser();
            JSONObject a = (JSONObject) parser.parse(new FileReader(file));
            multiplierDyingFailedMission = (double) a.get("multiplierDyingFailedMission");
            multiplierInjuringFailedMission = (double) a.get("multiplierInjuringFailedMission");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (Fighter fighter : missionInProgress.getFighters()) {
            fighter.takeDamage(successMission, missionInProgress.getMission(), multiplierDyingFailedMission, multiplierInjuringFailedMission);

        }

        return missionInProgress.getFighters();
    }

}
