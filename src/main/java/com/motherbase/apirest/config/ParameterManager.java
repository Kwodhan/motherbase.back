package com.motherbase.apirest.config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ParameterManager {

    public static Object getValue(String parameter) {

        try {
            File file = ResourceUtils.getFile("classpath:jsonToParse/parameters.json");
            JSONParser parser = new JSONParser();
            JSONObject a = (JSONObject) parser.parse(new FileReader(file));
            Object value = a.get(parameter);
            return value;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
