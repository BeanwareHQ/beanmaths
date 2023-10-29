package com.ezntek.beanmaths.components.settings;

import java.util.HashMap;
import java.util.Map;

import com.raylib.Jaylib.Rectangle;
import static com.raylib.Jaylib.*;

import com.ezntek.beanmaths.components.Component;
import com.ezntek.beanmaths.components.ComponentState;
import com.ezntek.beanmaths.config.Config;

public class AppearancePane extends Component {
    private static Map<String, Integer> themes;

    private class Rects {
        static Rectangle themeLabel = new Rectangle(432, 208, 120, 32); // Label: themeLabel
        static Rectangle themeDropdown = new Rectangle(840, 208, 176, 32); // DropdownBox: themeDropdown
    }

    private class State {
        boolean themeDropdownEditMode = false;
        int[] themeDropdownActive;

        State(Config cfg) {
            this.themeDropdownActive = new int[] { themes.get(cfg.appearance.theme.toLowerCase()) };
        }
    }

    ComponentState cmpState = ComponentState.DISABLED;
    State state;

    public AppearancePane(Config cfg) {
        themes = new HashMap<String, Integer>();
        themes.put("light", 0);
        themes.put("dark", 1);

        this.state = new State(cfg);
    }

    public void render() {
        if (!super.shouldRender())
            return;

        if (this.state.themeDropdownEditMode)
            GuiLock();

        GuiLabel(Rects.themeLabel, "Theme");
        DrawText("Appearance", 432, 148, 40, DARKGRAY);

        if (GuiDropdownBox(Rects.themeDropdown, "Light;Dark", this.state.themeDropdownActive,
                this.state.themeDropdownEditMode))
            this.state.themeDropdownEditMode = !this.state.themeDropdownEditMode;

        GuiUnlock();
    }

    public void update(long gtState) {
        if (!super.shouldUpdate())
            return;
    }
}
