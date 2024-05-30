package org.joutak.loginpluginforjoutak.logic.dto.converter;

import org.joutak.loginpluginforjoutak.logic.dto.PlayerDto;
import org.joutak.loginpluginforjoutak.utils.JoutakLoginProperties;

import java.time.LocalDate;

public final class PlayerDtoCalendarConverter {
    public static LocalDate getLastProlongDate(PlayerDto playerDto) {
        return LocalDate.parse(playerDto.getLastProlongDate(), JoutakLoginProperties.dateTimeFormatter);
    }

    public static LocalDate getValidUntil(PlayerDto playerDto) {
        return LocalDate.parse(playerDto.getValidUntil(), JoutakLoginProperties.dateTimeFormatter);
    }
}
