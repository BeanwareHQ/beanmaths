package com.ezntek.beanmaths.dialogs;

import java.nio.IntBuffer;

import com.raylib.Jaylib.Rectangle;
import static com.raylib.Jaylib.*;

import com.ezntek.beanmaths.config.Config;
import com.ezntek.beanmaths.config.ConfigManager;
import com.ezntek.beanmaths.navigation.NavigationController;
import com.ezntek.beanmaths.util.RequiresDeinit;
import com.ezntek.beanmaths.components.settings.*;

public class SettingsDialog extends Dialog implements RequiresDeinit {
    private enum PaneType {
        GENERAL,
        APPEARANCE,
        MATH,
    }

    private class Panes {
        GeneralPane general;
        AppearancePane appearance;
        MathPane math;
    }

    private class Rects {
        // buttons
        static Rectangle closeButton = new Rectangle(896, 632, 120, 24); // Button: closeButton
        static Rectangle applyButton = new Rectangle(768, 632, 120, 24); // Button: applyButton

        // Containers
        static Rectangle settingsWindowBox = new Rectangle(288, 96, 752, 576); // WindowBox: settingsWindowBox
        static Rectangle settingsList = new Rectangle(304, 136, 112, 520); // ListView: SettingsList
    }

    private class State {
        boolean closeButton = false;
        boolean applyButton = false;

        boolean settingsWindowBoxActive = true;
        IntBuffer scrollIndex;
        int indexActive;

        PaneType activePane = PaneType.GENERAL;

        State() {
            this.scrollIndex = IntBuffer.allocate(8);
        }
    }

    Panes panes;
    State state = new State();
    int windowWidth, windowHeight;
    int active;
    boolean darken = true;

    public SettingsDialog(Config cfg, NavigationController nc, int windowWidth, int windowHeight) {
        super(cfg, nc, windowWidth, windowHeight);

        this.cfg = cfg;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.panes = new Panes();
        this.panes.general = new GeneralPane(this.cfg);
        this.panes.appearance = new AppearancePane(this.cfg);
        this.panes.math = new MathPane(this.cfg);
    }

    @Override
    public void render() {
        if (!super.shouldRender())
            return;
        super.render();

        if (!this.state.settingsWindowBoxActive)
            return;

        this.state.settingsWindowBoxActive = !GuiWindowBox(Rects.settingsWindowBox, "Settings");
        this.state.indexActive = GuiListView(Rects.settingsList, "General;Appearance;Math", this.state.scrollIndex,
                this.state.indexActive);

        this.state.closeButton = GuiButton(Rects.closeButton, "Close");
        this.state.applyButton = GuiButton(Rects.applyButton, "Apply");

        switch (this.state.activePane) {
            case GENERAL:
                this.panes.general.render();
                break;

            case APPEARANCE:
                this.panes.appearance.render();
                break;

            case MATH:
                this.panes.math.render();
                break;
        }
    }

    @Override
    public void update(long gtState) {
        if (!super.shouldUpdate())
            return;

        if (!this.state.settingsWindowBoxActive) {
            nc.pop();
            this.state.settingsWindowBoxActive = true; // for next use
            return;
        }

        // screen switching
        switch (this.state.indexActive) {
            case 0:
                this.state.activePane = PaneType.GENERAL;
                break;
            case 1:
                this.state.activePane = PaneType.APPEARANCE;
                break;
            case 2:
                this.state.activePane = PaneType.MATH;
                break;
            default:
                this.state.activePane = PaneType.GENERAL;
        }

        switch (this.state.activePane) {
            case GENERAL:
                this.panes.general.update(gtState);
                break;

            case APPEARANCE:
                this.panes.appearance.update(gtState);
                break;

            case MATH:
                this.panes.math.update(gtState);
                break;
        }

        // button event handling
        if (this.state.closeButton) {
            this.nc.pop();
            return;
        }

        if (this.state.applyButton) {
            this.panes.appearance.apply();
            this.panes.general.apply();
            this.panes.math.apply();

            try {
                ConfigManager cfgm = new ConfigManager();
                cfgm.save(this.cfg);
                cfg.applyAllPossible();
            } catch (Exception exc) {
                System.err.println("Some error occured when applying the configuration.");
                return;
            }
        }
    }

    @Override
    public void deinit() {
        this.panes.general.deinit();
    }

    @Override
    public void init() {
        this.panes.general.init();
    }
}
