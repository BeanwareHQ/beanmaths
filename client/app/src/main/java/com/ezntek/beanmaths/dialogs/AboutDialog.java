package com.ezntek.beanmaths.dialogs;

import static com.raylib.Jaylib.*;

import com.ezntek.beanmaths.components.ComponentState;
import com.ezntek.beanmaths.config.Config;
import com.ezntek.beanmaths.navigation.NavigationController;
import com.ezntek.beanmaths.util.Colors;

public class AboutDialog extends Dialog {
    // to avoid visibility clashes
    class Rects {
        static Rectangle aboutWindowBox = new Rectangle(288, 96, 752, 576);
    }

    class State {
        boolean aboutWindowBoxActive = true;
    }

    public ComponentState cmpState = ComponentState.DISABLED;
    private State state = new State();

    // Description bound rectangle
    static int wBoxX = (int) Rects.aboutWindowBox.x();
    static int wBoxY = (int) Rects.aboutWindowBox.y();
    static int fontHeight = GetFontDefault().baseSize() * 4; // title font size (40) / 10

    static Rectangle descBoundRect = new Rectangle(wBoxX + 30, wBoxY + 55 + fontHeight, 692, 531 - fontHeight - 35);

    public AboutDialog(Config cfg, NavigationController nc, int windowWidth, int windowHeight) {
        super(cfg, nc, windowWidth, windowHeight);
    }

    @Override
    public void render() {
        if (!super.shouldRender())
            return;

        super.render();

        if (!this.state.aboutWindowBoxActive)
            return;

        this.state.aboutWindowBoxActive = !GuiWindowBox(Rects.aboutWindowBox, "About");

        // Title text
        DrawText("About", wBoxX + 30, wBoxY + 45, 40, Colors.DARKGRAY);

        // description
        GuiLabel(descBoundRect, "Copyright (C) Eason Qin 2023. Licensed under the GNU General Public License v3.");
    }

    @Override
    public void update(long gtState) {
        if (!super.shouldUpdate())
            return;

        if (!this.state.aboutWindowBoxActive) {
            nc.pop();
            this.state.aboutWindowBoxActive = true; // set it for the next use
            return;
        }
    }
}
