package com.ezntek.beanmaths;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.ezntek.beanmaths.config.Config;
import com.ezntek.beanmaths.config.ConfigManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

class TestConfigLoad {
    @Test
    void loadConfig() throws Exception {
        File file = new File("./sample_config.json");
        ConfigManager loader = new ConfigManager(file.getAbsolutePath());
        Config cfg = loader.load();

        System.err.println(cfg);
    }
}
