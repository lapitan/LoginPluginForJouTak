package org.joutak.loginpluginforjoutak.logic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerDto {

    @NotNull
    String name;

    String uuid;

    String lastProlongDate;

    String validUntil;

}
