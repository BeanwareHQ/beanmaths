package com.ezntek.beanmaths.screens;

import com.raylib.Raylib;
import static com.raylib.Jaylib.*;

import com.ezntek.beanmaths.components.Component;
import static com.ezntek.beanmaths.components.ComponentState.*;

class ButtonStates {
    boolean playButton = false;
    boolean settingsButton = false;
    boolean exitButton = false;
    boolean aboutButton = false;
}

public class TitleScreen extends Component {
    
    // rectangles
    private static Rectangle dummyRec = new Rectangle(296, 72, 776, 264);
    private static Rectangle playButton = new Rectangle(520, 608, 328, 48);
    private static Rectangle settingsButton = new Rectangle(520, 664, 80, 40);
    private static Rectangle exitButton = new Rectangle(608, 664, 152, 40);
    private static Rectangle aboutButton = new Rectangle(768, 664, 80, 40); 

    // button states
    private ButtonStates buttonStates = new ButtonStates();

    public void render() {
        // disallow rendering when in mode HIDDEN or DISABLED
        if (this.state != ENABLED) return;          
        
        Raylib.GuiDummyRec(dummyRec, "BeanMaths logo");

        // Buttons
        this.buttonStates.aboutButton = Raylib.GuiButton(aboutButton, Raylib.GuiIconText(Raylib.ICON_INFO, "About"));
        this.buttonStates.playButton = Raylib.GuiButton(playButton, Raylib.GuiIconText(Raylib.ICON_PLAYER_PLAY, "Play"));
        this.buttonStates.settingsButton = Raylib.GuiButton(settingsButton, Raylib.GuiIconText(Raylib.ICON_GEAR, "Settings"));
        this.buttonStates.exitButton = Raylib.GuiButton(exitButton, Raylib.GuiIconText(Raylib.ICON_EXIT, "Quit"));
    }

    public void refresh(int gtState) {
        // allow everything but DISABLE
        if (this.state == DISABLED) return;

        if (this.buttonStates.playButton)
            System.out.println("play button pressed");
        if (this.buttonStates.aboutButton)
            System.out.println("about button pressed");
        if (this.buttonStates.settingsButton)
            System.out.println("settings button pressed");
        if (this.buttonStates.exitButton)
            System.out.println("exit button pressed");
    }
}
