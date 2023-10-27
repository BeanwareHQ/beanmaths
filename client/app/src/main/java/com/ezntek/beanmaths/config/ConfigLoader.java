package com.ezntek.beanmaths.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ConfigLoader {
    static Gson gson = new Gson();
    private String fileContents;

    public ConfigLoader() throws IOException {
        String osName = System.getProperty("os.name").toLowerCase();
        String homeDir = System.getenv("HOME");
        File cfgFile;

        if (osName.contains("mac") || osName.contains("unix")) {
            cfgFile = new File(homeDir, ".beanmaths");

            if (!cfgFile.exists())
                cfgFile.mkdir();

            cfgFile = new File(cfgFile, "config.json");

            try {
                cfgFile.createNewFile();
            } catch (IOException exc) {
                System.err.printf("Some IO error occured whilst reading the configuration: {}\n", exc.toString());
                throw exc;
            }
        } else if (osName.contains("linux") || osName.contains("bsd")) {
            String xdgConfigHome = System.getenv("XDG_CONFIG_HOME");

            if (xdgConfigHome == "" || xdgConfigHome == null)
                xdgConfigHome = ".config";

            cfgFile = Paths.get(homeDir, xdgConfigHome, "beanmaths").toFile();
            if (!cfgFile.exists())
                cfgFile.mkdir();

            cfgFile = new File(cfgFile, "config.json");
        } else {
            System.err.println(
                    "Detected a non-unix-like OS, using a config.json in the CWD");
            cfgFile = new File("./config.json");
        }

        this.fileContents = readFile(cfgFile);
    }

    public ConfigLoader(String path) throws IOException {
        File file = new File(path);

        if (!file.exists())
            file.createNewFile();

        this.fileContents = readFile(file);
    }

    public Config load() throws JsonSyntaxException {
        Config result;

        try {
            result = gson.fromJson(this.fileContents, Config.class);
        } catch (JsonSyntaxException exc) {
            System.err.printf("A syntax error in the config file was found: {}\n", exc);
            throw exc;
        }

        return result;
    }

    private static String readFile(File file) throws IOException {
        String fileContents = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            fileContents += reader.readLine(); // cursed string concatenation
        } catch (IOException exc) {
            System.err.printf("Some IO error occured whilst reading the configuration: {}\n", exc.toString());
            throw exc;
        } catch (Exception otherExc) {
            System.err.printf("Some other error occured: {}", otherExc.toString());
            throw otherExc;
        }

        return fileContents;
    }
}
