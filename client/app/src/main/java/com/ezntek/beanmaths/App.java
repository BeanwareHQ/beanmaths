package com.ezntek.beanmaths;

import static com.raylib.Jaylib.*;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;

import com.ezntek.beanmaths.components.Background;
import com.ezntek.beanmaths.components.Component;
import com.ezntek.beanmaths.config.Config;
import com.ezntek.beanmaths.config.ConfigManager;
import com.ezntek.beanmaths.navigation.NavigationController;
import com.ezntek.beanmaths.util.RequiresDeinit;
import com.ezntek.beanmaths.screens.*;

public class App {
    static int windowWidth = 1362;
    static int windowHeight = 768;
    static long globTimer = 0;
    static boolean shouldDeinit;
    static NavigationController nc = new NavigationController();
    static Config config;
    // static Color bgColor = new Jaylib.Color(0x33, 0x33, 0x30, 0xff);

    static Font font;

    static TitleScreen titleScreen;
    static Background background = new Background(windowWidth, windowHeight);

    static void init() throws Exception {
        // some preinit stuff
        ConfigManager loader = new ConfigManager();
        loader.putDefault(false);
        config = loader.load();
        titleScreen = new TitleScreen(config, nc, windowWidth, windowHeight);

        // actual init stuff
        InitWindow(windowWidth, windowHeight, "BeanMaths (java version)");
        SetTargetFPS(60);
        SetExitKey(KEY_NULL);

        // now apply the themes and such
        config.applyAllPossible();

        nc.add(titleScreen);
        shouldDeinit = WindowShouldClose();
    }

    static void deinit() {
        try {
            LinkedList<Component> components = (LinkedList<Component>) nc.getComponents().clone();
            components.forEach((cmp) -> {
                if (cmp instanceof RequiresDeinit)
                    ((RequiresDeinit) cmp).deinit();
            });
        } catch (ConcurrentModificationException exc) {
            return;
        }
        CloseWindow();
    }

    static void refresh() {
        globTimer++;

        background.update(globTimer);
        try {
            // avoid a `ConcurrentModificationException` by cloning the array ptr first
            LinkedList<Component> components = (LinkedList<Component>) nc.getComponents().clone();
            components.forEach((cmp) -> {
                cmp.update(globTimer);
            });
        } catch (ConcurrentModificationException exc) {
            // race conditions are negligible.
            // New screens _can_ be rendered 1 frame after
            // it is added in the nagivation controller
            return;
        }

        if (nc.isEmpty()) {
            shouldDeinit = true;
            return;
        }
    }

    static void render() {
        ClearBackground(RAYWHITE);
        background.render();

        try {
            LinkedList<Component> components = (LinkedList<Component>) nc.getComponents().clone();
            components.forEach((cmp) -> {
                cmp.render();
            });
        } catch (ConcurrentModificationException exc) {
            return;
        }

        DrawText("WARNING: THIS IS INCOMPLETE SOFTWARE. DO EXPECT BUGS AT THIS STAGE.", 10, windowHeight - 20, 10, RED);
    }

    public static void main(String[] args) throws Exception {
        init();
        while (!shouldDeinit) {
            refresh();
            // exit before rendering happens.
            // prevents a null pointer exception to the
            // current screen in the render function.
            if (shouldDeinit) {
                deinit();
                return;
            }

            BeginDrawing();
            render();
            EndDrawing();
            shouldDeinit = WindowShouldClose();
        }
        deinit();
    }
}
