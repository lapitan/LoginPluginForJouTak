package org.joutak.loginpluginforjoutak.commands;

import lombok.extern.slf4j.Slf4j;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.joutak.loginpluginforjoutak.logic.dto.PlayerDto;
import org.joutak.loginpluginforjoutak.logic.dto.PlayerDtos;
import org.joutak.loginpluginforjoutak.logic.dto.converter.PlayerDtoCalendarConverter;
import org.joutak.loginpluginforjoutak.logic.dto.utils.PlayerDtosUtils;
import org.joutak.loginpluginforjoutak.logic.inputoutput.JsonReaderImpl;
import org.joutak.loginpluginforjoutak.logic.inputoutput.JsonWriterImpl;
import org.joutak.loginpluginforjoutak.logic.inputoutput.Reader;
import org.joutak.loginpluginforjoutak.logic.inputoutput.Writer;
import org.joutak.loginpluginforjoutak.utils.JoutakLoginProperties;

import java.time.LocalDate;

@Slf4j
public class LoginAddAndRemovePlayerCommand extends AbstractCommand {

    public LoginAddAndRemovePlayerCommand() {
        super("joupen");
    }

    @Override
    public void execute(CommandSender commandSender, Command command, String string, String[] args) {

        if (args.length < 1) {
            TextComponent textComponent = Component.text("Wrong amount of arguments. Try /joupen help", NamedTextColor.GOLD)
                    .toBuilder().build();
            commandSender.sendMessage(textComponent);
        }

        if (args[0].equals("help")) {
            helpCommand(commandSender);
        }

        if (args[0].equals("info")) {
            infoCommand(commandSender, args);
        }

        if (args[0].equals("prolong")) {
            prolongCommand(commandSender, args);
        }

        System.out.println(args.length);

    }

    private boolean checkPermission(CommandSender commandSender, String permission) {
        if (!commandSender.hasPermission(permission)) {
            TextComponent textComponent = Component.text("Go fuck yourself. You don't have permission", NamedTextColor.RED)
                    .toBuilder().build();
            commandSender.sendMessage(textComponent);
            return true;
        }
        return false;
    }

    private void helpCommand(CommandSender commandSender) {
        TextComponent textComponent = Component.text()
                .append(Component.text("JouHodka", NamedTextColor.GOLD))
                .appendNewline()
                .append(Component.text("Вайтлист плагин для ДжоуТека", NamedTextColor.GOLD))
                .appendNewline()
                .append(Component.text("Help:", NamedTextColor.BLUE))
                .appendNewline()
                .append(Component.text("/joupen help", NamedTextColor.GREEN))
                .append(Component.text(" - показывает эту страницу", NamedTextColor.BLUE))
                .appendNewline()
                .append(Component.text("/joupen prolong <player> [amount] [d/m]", NamedTextColor.GREEN))
                .append(Component.text(" - добавляет игрока на какое-то время. Default: 1 month", NamedTextColor.BLUE))
                .appendNewline()
                .append(Component.text("/joupen info {for OP: player}", NamedTextColor.GREEN))
                .append(Component.text(" - показывает информацию о вашей проходке. Админ может смотреть всех игроков", NamedTextColor.BLUE))
                .appendNewline()
                .append(Component.text("Developed by ", NamedTextColor.GRAY))
                .append(Component.text("Lapitaniy ", NamedTextColor.DARK_AQUA))
                .append(Component.text("The ", NamedTextColor.RED))
                .append(Component.text("Гр", NamedTextColor.WHITE))
                .append(Component.text("ыб", NamedTextColor.RED))
                .append(Component.text("ни", NamedTextColor.WHITE))
                .append(Component.text("к", NamedTextColor.RED))
                .build();
        commandSender.sendMessage(textComponent);
    }

    private void infoCommand(CommandSender commandSender, String[] args) {
        if (args.length > 1) {
            if (checkPermission(commandSender, "joupen.admin")) {
                return;
            }
            PlayerDto playerDto = PlayerDtosUtils.findPlayerByName(args[1]);

            if (playerDto == null) {
                TextComponent textComponent = Component.text("Can't find player with name " + args[1], NamedTextColor.RED);
                commandSender.sendMessage(textComponent);
                return;
            }

            TextComponent textComponent = Component.text()
                    .append(Component.text("Ник Игрока:", NamedTextColor.GREEN))
                    .append(Component.text(playerDto.getName(), NamedTextColor.BLUE))
                    .appendNewline()
                    .append(Component.text("UUID Игрока:", NamedTextColor.GREEN))
                    .append(Component.text(playerDto.getUuid(), NamedTextColor.BLUE))
                    .appendNewline()
                    .append(Component.text("Последняя дата продления проходки: ", NamedTextColor.GREEN))
                    .append(Component.text(playerDto.getLastProlongDate(), NamedTextColor.BLUE))
                    .appendNewline()
                    .append(Component.text("Проходка активна до: ", NamedTextColor.GREEN))
                    .append(Component.text(playerDto.getValidUntil(), NamedTextColor.BLUE))
                    .build();

            commandSender.sendMessage(textComponent);
            return;
        }

        PlayerDto playerDto = PlayerDtosUtils.findPlayerByName(commandSender.getName());

        if (playerDto == null) {
            TextComponent textComponent = Component.text("SOMETHING WENT WRONG! " +
                    "JOUHODKA PLUGIN COULDN'T FIND INFO ABOUT YOU!" +
                    " PLEASE CONTACT THE ADMINISTRATOR (ENDERDISSA)", NamedTextColor.RED);
            commandSender.sendMessage(textComponent);
            log.error("CAN'T FIND INFO ABOUT EXISTING PLAYER " + commandSender.getName() + " !!!! CHECK IT CAREFULLY!!!!");
            return;
        }

        TextComponent textComponent = Component.text()
                .append(Component.text("Твой Ник:", NamedTextColor.GREEN))
                .append(Component.text(playerDto.getName(), NamedTextColor.BLUE))
                .appendNewline()
                .append(Component.text("Твой UUID:", NamedTextColor.GREEN))
                .append(Component.text(playerDto.getUuid(), NamedTextColor.BLUE))
                .appendNewline()
                .append(Component.text("Последняя дата продления проходки: ", NamedTextColor.GREEN))
                .append(Component.text(playerDto.getLastProlongDate(), NamedTextColor.BLUE))
                .appendNewline()
                .append(Component.text("Проходка активна до: ", NamedTextColor.GREEN))
                .append(Component.text(playerDto.getValidUntil(), NamedTextColor.BLUE))
                .build();

        commandSender.sendMessage(textComponent);
    }

    private void prolongCommand(CommandSender commandSender, String[] args) {
        if (checkPermission(commandSender, "joupen.admin")) {
            return;
        }
        if (args.length < 2) {
            TextComponent textComponent = Component.text("Wrong amount of arguments." +
                    " Try /jouhodka help", NamedTextColor.RED);

            commandSender.sendMessage(textComponent);
        }
        Writer writer = new JsonWriterImpl(JoutakLoginProperties.saveFilepath);
        PlayerDto playerDto = PlayerDtosUtils.findPlayerByName(args[1]);
        Integer daysAmount = 30;
        if (args.length >= 3) {
            if (args.length >= 4) {
                if (args[3].equals("d")) {
                    daysAmount = Integer.parseInt(args[2]);
                } else {
                    daysAmount = 30 * Integer.parseInt(args[2]);
                }
            } else {
                daysAmount = 30 * Integer.parseInt(args[2]);
            }
        }

        if (playerDto == null) {
            playerDto = new PlayerDto();
            playerDto.setName(args[1]);
            LocalDate localDate = LocalDate.now();
            playerDto.setLastProlongDate(localDate.format(JoutakLoginProperties.dateTimeFormatter));
            LocalDate validUntil = localDate.plusDays(daysAmount);
            playerDto.setValidUntil(validUntil.format(JoutakLoginProperties.dateTimeFormatter));
            playerDto.setUuid("-1");
            writer.addNew(playerDto);
            TextComponent textComponent = Component.text()
                    .append(Component.text("Новый Игрок ", NamedTextColor.AQUA))
                    .append(Component.text(args[1], NamedTextColor.YELLOW))
                    .append(Component.text("впервые оплатил проходку! Ура!", NamedTextColor.AQUA))
                    .build();
            Bukkit.broadcast(textComponent);
            textComponent = Component.text("Added new player to the whitelist: " + args[1], NamedTextColor.RED);
            commandSender.sendMessage(textComponent);
            log.warn("Added new player to the whitelist: {}", args[1]);
            return;
        }

        if (PlayerDtoCalendarConverter.getLastProlongDate(playerDto).isBefore(LocalDate.now())) {
            LocalDate localDate = LocalDate.now();
            playerDto.setLastProlongDate(localDate.format(JoutakLoginProperties.dateTimeFormatter));
            playerDto.setValidUntil(localDate.format(JoutakLoginProperties.dateTimeFormatter));
        }

        LocalDate localDate = PlayerDtoCalendarConverter.getValidUntil(playerDto);
        localDate = localDate.plusDays(daysAmount);
        playerDto.setValidUntil(localDate.format(JoutakLoginProperties.dateTimeFormatter));

        Reader reader = new JsonReaderImpl(JoutakLoginProperties.saveFilepath);
        PlayerDtos playerDtos = reader.read();
        PlayerDto currPlayerDto = PlayerDtosUtils.findPlayerByName(playerDto.getName());

        playerDtos.getPlayerDtoList().remove(currPlayerDto);
        playerDtos.getPlayerDtoList().add(playerDto);

        writer.write(playerDtos);

        TextComponent textComponent = Component.text()
                .append(Component.text("Игрок ", NamedTextColor.AQUA))
                .append(Component.text(args[1], NamedTextColor.YELLOW))
                .append(Component.text(" продлил проходку на еще ", NamedTextColor.AQUA))
                .append(Component.text(daysAmount, NamedTextColor.YELLOW))
                .append(Component.text(" дней. Ура!"))
                .build();

        Bukkit.broadcast(textComponent);
        textComponent = Component.text("Added new player to the whitelist: " + args[1], NamedTextColor.RED);
        commandSender.sendMessage(textComponent);
        log.warn("Added new player to the whitelist: {}", args[1]);
    }

}
