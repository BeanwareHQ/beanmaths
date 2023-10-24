package com.ezntek.beanmaths.screens;

import com.raylib.Jaylib;
import com.raylib.Jaylib.Rectangle;
import static com.raylib.Jaylib.GuiIconText;

import java.util.LinkedList;

import com.ezntek.beanmaths.components.ComponentState;

import static com.ezntek.beanmaths.components.ComponentState.*;

class ButtonStates {
    boolean playButton = false;
    boolean settingsButton = false;
    boolean exitButton = false;
    boolean aboutButton = false;
}

class GuiElements {
    static Rectangle dummyRec = new Rectangle(296, 72, 776, 264);
    static Rectangle playButton = new Rectangle(480, 608, 408, 48);
    static Rectangle settingsButton = new Rectangle(480, 664, 120, 40);
    static Rectangle exitButton = new Rectangle(768, 664, 120, 40);
    static Rectangle aboutButton = new Rectangle(608, 664, 152, 40);
}

public class TitleScreen extends Screen {
    public LinkedList<Screen> screens;

    public ComponentState state = ENABLED;
    private ButtonStates buttonStates = new ButtonStates();
    int windowWidth, windowHeight;

    public TitleScreen(LinkedList<Screen> screens, int windowWidth, int windowHeight) {
        this.screens = screens;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public void render() {
        // disallow rendering when in mode HIDDEN or DISABLEd
        if (this.state != ENABLED)
            return;

        Jaylib.GuiDummyRec(GuiElements.dummyRec, "BeanMaths logo");

        // Buttons
        this.buttonStates.aboutButton = Jaylib.GuiButton(GuiElements.aboutButton,
                GuiIconText(Jaylib.ICON_INFO, "About"));
        this.buttonStates.playButton = Jaylib.GuiButton(GuiElements.playButton,
                GuiIconText(Jaylib.ICON_PLAYER_PLAY, "Play"));
        this.buttonStates.settingsButton = Jaylib.GuiButton(GuiElements.settingsButton,
                GuiIconText(Jaylib.ICON_GEAR, "Settings"));
        this.buttonStates.exitButton = Jaylib.GuiButton(GuiElements.exitButton,
                GuiIconText(Jaylib.ICON_EXIT, "Quit"));
    }

    public void refresh(long gtState) {
        // allow everything but DISABLEd
        if (this.state == DISABLED)
            return;

        if (this.buttonStates.playButton)
            System.out.println("play button pressed");
        if (this.buttonStates.aboutButton)
            System.out.println("about button pressed");
        if (this.buttonStates.settingsButton)
            System.out.println("settings button pressed");
        if (this.buttonStates.exitButton)
            this.screens.removeLast();
    }
}
