package com.ezntek.beanmaths.screens;

import java.util.NoSuchElementException;

import com.raylib.Jaylib;
import com.raylib.Jaylib.Rectangle;
import static com.raylib.Jaylib.GuiIconText;

import com.ezntek.beanmaths.components.ComponentState;
import com.ezntek.beanmaths.dialogs.*;
import com.ezntek.beanmaths.navigation.NavigationController;

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
    public ComponentState state = ENABLED;
    private ButtonStates buttonStates = new ButtonStates();
    int windowWidth, windowHeight;

    public TitleScreen(NavigationController nc, int windowWidth, int windowHeight) {
        super(nc, windowWidth, windowHeight);

        // for dialogs
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    @Override
    public void render() {
        if (!super.shouldRender())
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

    @Override
    public void update(long gtState) {
        if (!super.shouldUpdate())
            return;

        if (this.buttonStates.playButton) {
            Dialog dialog = new PlayDialog(this.nc, this.windowWidth, this.windowHeight);
            this.nc.add(dialog);

            return;
        }

        if (this.buttonStates.aboutButton) {
            Dialog dialog = new AboutDialog(this.nc, this.windowWidth, this.windowHeight);
            this.nc.add(dialog);

            return;
        }

        if (this.buttonStates.settingsButton)
            System.out.println("not implemented");
        if (this.buttonStates.exitButton) {
            try {
                this.nc.pop();
            } catch (NoSuchElementException exc) {
                // absence of elements will be detected
                // on the same cycle after the root screen
                // gets removed, so propagating and recatching
                // is unnecessary.
                return;
            }
        }
    }
}
