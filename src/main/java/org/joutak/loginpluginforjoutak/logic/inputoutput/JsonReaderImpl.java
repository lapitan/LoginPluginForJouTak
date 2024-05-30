package org.joutak.loginpluginforjoutak.logic.inputoutput;

import com.google.gson.Gson;
import org.joutak.loginpluginforjoutak.logic.dto.PlayerDtos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonReaderImpl implements Reader {

    String filepath;

    public JsonReaderImpl(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public PlayerDtos read() {
        String json;
        try {
            json = Files.readString(Paths.get(filepath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PlayerDtos list = new Gson().fromJson(json, PlayerDtos.class);
        return list;
    }
}
