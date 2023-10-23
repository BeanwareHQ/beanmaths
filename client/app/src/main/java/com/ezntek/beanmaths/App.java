package com.ezntek.beanmaths;

import com.raylib.Jaylib;
import static com.raylib.Jaylib.*;

import com.ezntek.beanmaths.components.*;

public class App {
    static int windowWidth = 1360;
    static int windowHeight = 768;
    static int globTimer = 0;
    static Background background = new Background(windowWidth, windowHeight); 

    static void initStyles() {
        Jaylib.GuiSetStyle(DEFAULT, TEXT_SIZE, 15);
    }

    static void refresh() {
        globTimer++;
        background.refresh(globTimer);
    }

    static void render() {
        Jaylib.ClearBackground(RAYWHITE);
        background.render();
    }

    public static void main(String[] args) {
        Jaylib.InitWindow(windowWidth, windowHeight, "BeanMaths (java version)");
        Jaylib.SetTargetFPS(60);

        initStyles();

        while (!Jaylib.WindowShouldClose()) {
            refresh();
            Jaylib.BeginDrawing();
            render();
            Jaylib.EndDrawing();
        }
    }
}
