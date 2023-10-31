package com.ezntek.beanmaths.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ConfigManager {
    static Gson gson = new Gson();
    private String fileContents;
    private String filePath;

    final static String defaultConfig = """
            {
                "general": {
                    "defaultNick": "",
                    "defaultServer": ""
                },
                "appearance": {
                    "theme": "Light",
                    "textSize": 10
                },
                "math": {
                    "basic": {
                        "enable": true,
                        "imperfectDigits": true,
                        "max": 60
                    },
                    "timesTable": {
                        "enable": true,
                        "max": 10
                    },
                    "advanced": {
                        "enable": true,
                        "roots": true,
                        "maxBase": 10,
                        "maxPower": 3
                    },
                    "count": 5
                }
            }
            """;

    public ConfigManager() throws IOException {
        String osName = System.getProperty("os.name").toLowerCase();
        String homeDir = System.getenv("HOME");
        File cfgFile;

        if (osName.contains("mac") || osName.contains("unix")) {
            cfgFile = new File(homeDir, ".beanmaths");

            if (!cfgFile.exists())
                cfgFile.mkdir();

            cfgFile = new File(cfgFile, "config.json");
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

        this.filePath = cfgFile.toString();

        if (!cfgFile.exists())
            this.putDefault(true);

        this.fileContents = readFile(cfgFile);
    }

    public ConfigManager(String path) throws IOException {
        this.filePath = path;
        File file = new File(this.filePath);

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

    public void save(Config config) throws IOException {
        String json = gson.toJson(config, config.getClass());

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.filePath))) {
            bw.write(json);
            bw.flush();
        } catch (IOException exc) {
            System.err.printf("Some IO error occured whilst writing the configuration: {}\n", exc.toString());
            throw exc;
        }
    }

    public void putDefault(boolean force) throws IOException {
        File file = new File(this.filePath);

        if (!force)
            return;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(defaultConfig);
            bw.flush();
        } catch (IOException exc) {
            System.err.printf("Some IO error occured whilst writing the configuration: {}\n", exc.toString());
            throw exc;
        }
    }

    private static String readFile(File file) throws IOException {
        String fileContents = "";
        String line = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
                fileContents += line;
                line += '\n';
            }
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
