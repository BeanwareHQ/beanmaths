package com.ezntek.beanmaths.dialogs;

import static com.raylib.Jaylib.*;

import com.ezntek.beanmaths.components.ComponentState;
import com.ezntek.beanmaths.navigation.NavigationController;

class AboutDlgRects {
    static Rectangle aboutWindowBox = new Rectangle(30, 30, 200, 165);
}

class AboutDlgState {
    boolean aboutWindowBoxActive = true;
}

public class AboutDialog extends Dialog {
    public ComponentState state = ComponentState.DISABLED;
    private AboutDlgState dlgState = new AboutDlgState();

    public AboutDialog(NavigationController nc, int windowWidth, int windowHeight) {
        super(nc, windowWidth, windowHeight);
    }

    @Override
    public void render() {
        if (!super.shouldRender())
            return;

        super.render();

        if (!this.dlgState.aboutWindowBoxActive)
            return;

        this.dlgState.aboutWindowBoxActive = !GuiWindowBox(AboutDlgRects.aboutWindowBox, "About");
    }

    @Override
    public void update(long gtState) {
        if (!super.shouldUpdate())
            return;
    }
}
