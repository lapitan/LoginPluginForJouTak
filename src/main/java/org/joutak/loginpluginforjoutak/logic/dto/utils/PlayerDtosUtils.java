package org.joutak.loginpluginforjoutak.logic.dto.utils;

import org.joutak.loginpluginforjoutak.logic.dto.PlayerDto;
import org.joutak.loginpluginforjoutak.logic.inputoutput.JsonReaderImpl;
import org.joutak.loginpluginforjoutak.logic.inputoutput.Reader;
import org.joutak.loginpluginforjoutak.utils.JoutakLoginProperties;

public final class PlayerDtosUtils {
    public static PlayerDto findPlayerByName(String name) {

        Reader reader = new JsonReaderImpl(JoutakLoginProperties.saveFilepath);

        return reader.read().getPlayerDtoList().stream()
                .filter(it -> it.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

}
