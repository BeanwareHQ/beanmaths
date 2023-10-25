package com.ezntek.beanmaths;

import static com.raylib.Jaylib.*;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;

import com.ezntek.beanmaths.components.Background;
import com.ezntek.beanmaths.components.Component;
import com.ezntek.beanmaths.navigation.NavigationController;
import com.ezntek.beanmaths.screens.*;

public class App {
    static int windowWidth = 1362;
    static int windowHeight = 768;
    static long globTimer = 0;
    static boolean shouldDeinit;
    static NavigationController nc = new NavigationController();
    // static Color bgColor = new Jaylib.Color(0x33, 0x33, 0x30, 0xff);

    static TitleScreen titleScreen = new TitleScreen(nc, windowWidth, windowHeight);
    static Background background = new Background(windowWidth, windowHeight);

    static void init() {
        InitWindow(windowWidth, windowHeight, "BeanMaths (java version)");
        SetTargetFPS(60);
        GuiSetStyle(DEFAULT, TEXT_SIZE, 10);

        nc.add(titleScreen);
        shouldDeinit = WindowShouldClose();
    }

    static void deinit() {
        CloseWindow();
    }

    static void refresh() {
        globTimer++;

        if (nc.isEmpty()) {
            shouldDeinit = true;
            return;
        }

        background.update(globTimer);
        try {
            nc.getComponents().forEach((cmp) -> {
                cmp.update(globTimer);
            });
        } catch (ConcurrentModificationException exc) {
            return;
        }

        // if (globTimer % 45 == 0)
        // System.out.println(nc.getComponents());
    }

    static void render() {
        ClearBackground(RAYWHITE);
        background.render();

        try {
            nc.getComponents().forEach((cmp) -> {
                cmp.render();
            });
        } catch (ConcurrentModificationException exc) {
            return;
        }
    }

    public static void main(String[] args) {
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
