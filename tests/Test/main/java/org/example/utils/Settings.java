package org.example.utils;

import org.example.generators.DomParser;

import java.io.File;

public class Settings {

    public static File file = new File("C:\\ProjectsJava\\Tests\\Test6\\Tests6\\src\\main\\resources\\Settings.xml");
    public static String baseUrl;
    public static String login;
    public static String password;
    public static DomParser parser = new DomParser();

    public Settings() {
        if (!file.exists()) {
            throw new RuntimeException("Problem: settings file not found:" + file);
        }
    }

    public static String getBaseUrl() {
        if (baseUrl == null) {
            baseUrl = parser.getData("baseUrl", file);
        }
        return baseUrl;
    }

    public static String getLogin() {
        if (login == null) {
            login = parser.getData("login", file);
        }
        return login;
    }

    public static String getPassword() {
        if (password == null) {
            password = parser.getData("password", file);
        }
        return password;
    }

}
