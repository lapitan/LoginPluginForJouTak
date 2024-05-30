package org.joutak.loginpluginforjoutak.logic.inputoutput;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.joutak.loginpluginforjoutak.logic.dto.PlayerDto;
import org.joutak.loginpluginforjoutak.logic.dto.PlayerDtos;
import org.joutak.loginpluginforjoutak.utils.JoutakLoginProperties;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class JsonWriterImpl implements Writer {

    String filepath;

    public JsonWriterImpl(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public void write(PlayerDtos playerDtos) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(playerDtos);
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(filepath));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            log.error("can't write to file: {}", filepath);
        }
        log.info("Json save was completed successfully");
    }

    @Override
    public void addNew(PlayerDto playerDto) {
        Reader reader = new JsonReaderImpl(JoutakLoginProperties.saveFilepath);

        PlayerDtos playerDtos = reader.read();
        playerDtos.getPlayerDtoList().add(playerDto);

        write(playerDtos);

    }
}
