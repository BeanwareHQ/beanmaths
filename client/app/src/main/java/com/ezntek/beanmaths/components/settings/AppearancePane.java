package com.ezntek.beanmaths.components.settings;

import java.util.HashMap;
import java.util.Map;

import org.bytedeco.javacpp.BytePointer;

import com.raylib.Jaylib.Rectangle;
import static com.raylib.Jaylib.*;

import com.ezntek.beanmaths.components.Component;
import com.ezntek.beanmaths.components.ComponentState;
import com.ezntek.beanmaths.config.Config;
import com.ezntek.beanmaths.util.RequiresDeinit;

public class AppearancePane extends Component implements RequiresDeinit {
    // Easy String -> Int and Int -> String conversions
    private static Map<String, Integer> themesMap;
    private static String[] themeStrings = { "light", "dark" };

    private class Rects {
        static Rectangle defaultTextSizeLabel = new Rectangle(432, 248, 120, 32);
        static Rectangle defaultTextSizeBox = new Rectangle(840, 248, 176, 32);
        static Rectangle themeLabel = new Rectangle(432, 208, 120, 32); // Label: themeLabel
        static Rectangle themeDropdown = new Rectangle(840, 208, 176, 32); // DropdownBox: themeDropdown
    }

    private class State {
        boolean defaultTextSizeBoxEditMode = false;
        boolean themeDropdownEditMode = false;
        int[] themeDropdownActive;

        BytePointer defaultTextSizeBoxBuf;

        State(Config cfg) {
            this.themeDropdownActive = new int[] { themesMap.get(cfg.appearance.theme.toLowerCase()) };
        }
    }

    Config cfg;
    ComponentState cmpState = ComponentState.DISABLED;
    State state;

    public AppearancePane(Config cfg) {
        themesMap = new HashMap<String, Integer>();
        themesMap.put("light", 0);
        themesMap.put("dark", 1);

        this.cfg = cfg;
        this.state = new State(this.cfg);
        this.init();
    }

    public void apply() {
        this.cfg.appearance.textSize = Integer.parseInt(this.state.defaultTextSizeBoxBuf.getString());
        this.cfg.appearance.theme = themeStrings[this.state.themeDropdownActive[0]];
    }

    @Override
    public void deinit() {
        this.state.defaultTextSizeBoxBuf.deallocate();
    }

    @Override
    public void init() {
        this.state.defaultTextSizeBoxBuf = new BytePointer(String.valueOf(this.cfg.appearance.textSize));
        this.state.defaultTextSizeBoxBuf.capacity(4);
    }

    @Override
    public void render() {
        if (!super.shouldRender())
            return;

        if (this.state.themeDropdownEditMode)
            GuiLock();

        GuiLabel(Rects.themeLabel, "Theme");
        GuiLabel(Rects.defaultTextSizeLabel, "Default Text Size");

        DrawText("Appearance", 432, 148, 40, DARKGRAY);

        if (GuiDropdownBox(Rects.themeDropdown, "Light;Dark", this.state.themeDropdownActive,
                this.state.themeDropdownEditMode))
            this.state.themeDropdownEditMode = !this.state.themeDropdownEditMode;

        if (GuiTextBox(Rects.defaultTextSizeBox, this.state.defaultTextSizeBoxBuf, 128,
                this.state.defaultTextSizeBoxEditMode))
            this.state.defaultTextSizeBoxEditMode = !this.state.defaultTextSizeBoxEditMode;

        GuiUnlock();
    }

    @Override
    public void update(long gtState) {
        if (!super.shouldUpdate())
            return;
    }
}
