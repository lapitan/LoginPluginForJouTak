package org.joutak.loginpluginforjoutak.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

@Slf4j
public final class ResourcesJsonReader {

    private static final String filepath = "plugins/loginPluginRes/properties.json";

    public static void getResources() {
        String json = null;

        try {
            json = Files.readString(Paths.get(filepath));
        } catch (IOException e) {
            log.error("can't read a properties file with filepath: {}", filepath);
        }
        if (json == null) {
            log.error("properties file is empty. WTF?");
            return;
        }

        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        String saveFilepath = getPropertyAsString(jsonObject, "saveFilepath");
        if (!saveFilepath.isEmpty()) {
            JoutakLoginProperties.saveFilepath = saveFilepath;
        } else {
            log.info("can't define property {}, now using a defaultValue: {}", "saveFilepath", JoutakLoginProperties.saveFilepath);
        }

        String dateTimeFormat = getPropertyAsString(jsonObject, "dateTimeFormatter");
        if (!dateTimeFormat.isEmpty()) {
            JoutakLoginProperties.dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
        } else {
            log.info("can't define property {}, now using a defaultValue: {}", "dateTimeFormatter", JoutakLoginProperties.dateTimeFormatter.toString());
        }

        String enabled = getPropertyAsString(jsonObject, "enabled");
        if (!dateTimeFormat.isEmpty()) {
            JoutakLoginProperties.enabled = Boolean.valueOf(enabled);
        } else {
            log.info("can't define property {}, now using a defaultValue: {}", "enabled", JoutakLoginProperties.enabled);
        }

        log.info("resources was loaded successfully");
    }

    private static String getPropertyAsString(JsonObject jsonObject, String propertyName) {
        try {
            return jsonObject.get(propertyName).toString().replace("\"", "");
        } catch (Exception e) {
            return "";
        }
    }
}
