package beanmaths;

import com.raylib.Raylib;
import static com.raylib.Jaylib.*;
import beanmaths.components.*; // FIXME: glob import

public class App {
    static int windowWidth = 1360;
    static int windowHeight = 768;
    static int globTimer = 0;
    static Background background = new Background();

    static void initStyles() {
        Raylib.GuiSetStyle(DEFAULT, TEXT_SIZE, 15);
    }

    static void refresh() {
        globTimer++;
        background.refresh(globTimer);
    }

    static void render() {
        Raylib.ClearBackground(RAYWHITE);
        background.render();
    }

    public static void main(String[] args) {
        Raylib.InitWindow(windowWidth, windowHeight, "BeanMaths (java version)");
        Raylib.SetTargetFPS(60);

        initStyles();

        while (!Raylib.WindowShouldClose()) {
            refresh();
            Raylib.BeginDrawing();
            render();
            Raylib.EndDrawing();
        }
    }
}
