package com.ezntek.beanmaths;

import static com.raylib.Jaylib.*;

import java.util.LinkedList;

import com.ezntek.beanmaths.components.Background;
import com.ezntek.beanmaths.screens.*;

public class App {
    static int windowWidth = 1360;
    static int windowHeight = 768;
    static long globTimer = 0;
    static boolean shouldDeinit;
    static LinkedList<Screen> navScreens = new LinkedList<Screen>();
    // static Color bgColor = new Jaylib.Color(0x33, 0x33, 0x30, 0xff);

    static TitleScreen titleScreen = new TitleScreen(navScreens, windowWidth, windowHeight);
    static Background background = new Background(windowWidth, windowHeight);

    static void init() {
        InitWindow(windowWidth, windowHeight, "BeanMaths (java version)");
        SetTargetFPS(60);
        GuiSetStyle(DEFAULT, TEXT_SIZE, 15);

        navScreens.addLast(titleScreen);
        shouldDeinit = WindowShouldClose();
    }

    static void deinit() {
        CloseWindow();
    }

    static void refresh() {
        globTimer++;

        if (navScreens.isEmpty()) {
            shouldDeinit = true;
            return;
        }

        background.refresh(globTimer);
        navScreens.peekLast().refresh(globTimer);
    }

    static void render() {
        ClearBackground(RAYWHITE);
        background.render();

        try {
            navScreens.peekLast().render();
        } catch (NullPointerException exc) {
            // handles the case where there are no
            // screens in the LL, and when the case
            // in refresh() does not catch this.
            shouldDeinit = true;
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
