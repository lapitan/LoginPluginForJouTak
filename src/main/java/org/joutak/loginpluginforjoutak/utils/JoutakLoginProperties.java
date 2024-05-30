package org.joutak.loginpluginforjoutak.utils;

import java.time.format.DateTimeFormatter;

public final class JoutakLoginProperties {

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String saveFilepath = "loginPluginRes/players.json";

    public static Boolean enabled = true;

}
