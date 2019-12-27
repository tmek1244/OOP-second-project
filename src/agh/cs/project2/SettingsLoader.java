package agh.cs.project2;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class SettingsLoader {
    public static int width = 50, height = 30, scale = 30, periodInterval = 100;
    public static void load()
    {
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader("src/parameters.json")) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            width =(int) (long) jsonObject.get("width");
            height = (int) (long) jsonObject.get("height");
            scale = (int) (long) jsonObject.get("scale");
            periodInterval = (int) (long) jsonObject.get("periodInterval");
        } catch (IOException | ParseException e) {
            System.out.println("Exception caught during loading settings.");
            e.printStackTrace();
        }
    }
}
