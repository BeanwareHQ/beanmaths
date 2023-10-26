package com.ezntek.beanmaths.dialogs;

import com.raylib.Jaylib.Rectangle;
import static com.raylib.Jaylib.*;

import java.nio.IntBuffer;

import org.bytedeco.javacpp.BytePointer;

import com.ezntek.beanmaths.components.Component;
import com.ezntek.beanmaths.components.ComponentState;
import com.ezntek.beanmaths.navigation.NavigationController;

public class SettingsDialog extends Dialog {
    // ----- Public Nested Classes -----
    public class GeneralPane extends Component {
        public ComponentState cmpState = ComponentState.DISABLED;

        private class Rects {
            // Labels
            static Rectangle defaultNickLabel = new Rectangle(432, 240, 136, 32); // Label: defaultNickLabel
            static Rectangle defaultServerLabel = new Rectangle(432, 280, 136, 32); // Label: defaultServerLabel

            // text boxes
            static Rectangle defaultNickBox = new Rectangle(576, 240, 440, 32); // TextBox: defaultNickBox
            static Rectangle defaultServerBox = new Rectangle(576, 280, 440, 32); // TextBox: defaultServerBox

            // other
            static Rectangle headingDummyRect = new Rectangle(432, 136, 584, 72); // DummyRec: headingDummy
        }

        private class State {
            boolean nickBoxEditMode = false;
            boolean serverBoxEditMode = false;

            BytePointer nickBoxBuf = new BytePointer("");
            BytePointer serverBoxBuf = new BytePointer("");

            State() {
                this.nickBoxBuf.capacity(64);
                this.serverBoxBuf.capacity(64);
            }
        }

        public State state = new State();

        @Override
        public void render() {
            if (!super.shouldRender())
                return;

            GuiDummyRec(Rects.headingDummyRect, "General (placeholder)");
            GuiLabel(Rects.defaultNickLabel, "Default Nick");
            GuiLabel(Rects.defaultServerLabel, "Default Server");

            if (GuiTextBox(Rects.defaultNickBox, this.state.nickBoxBuf, 128, this.state.nickBoxEditMode))
                this.state.nickBoxEditMode = !this.state.nickBoxEditMode;
            if (GuiTextBox(Rects.defaultServerBox, this.state.serverBoxBuf, 128, this.state.serverBoxEditMode))
                this.state.serverBoxEditMode = !this.state.serverBoxEditMode;
        }

        @Override
        public void update(long gtState) {
            if (!super.shouldUpdate())
                return;

            // truncate strings to avoid a buffer overflow
            String nickBoxBufString = this.state.nickBoxBuf.getString();
            String serverBoxBufString = this.state.serverBoxBuf.getString();

            if (nickBoxBufString.length() > 63)
                this.state.nickBoxBuf.putString(nickBoxBufString.substring(0, 63));

            if (serverBoxBufString.length() > 63)
                this.state.serverBoxBuf.putString(serverBoxBufString.substring(0, 63));
        }
    }

    public class AppearancePane extends Component {
        private class Rects {
            static Rectangle themeLabel = new Rectangle(432, 224, 120, 32); // Label: themeLabel
            static Rectangle themeDropdown = new Rectangle(840, 224, 176, 32); // DropdownBox: themeDropdown
        }

        private class State {
            boolean themeDropdownEditMode = false;
            IntBuffer themeDropdownActive = IntBuffer.allocate(16); // 16 total themes allowed
        }

        ComponentState cmpState = ComponentState.DISABLED;
        State state = new State();

        public void render() {
            if (!super.shouldRender())
                return;

            if (this.state.themeDropdownEditMode)
                GuiLock();

            GuiLabel(Rects.themeDropdown, "Theme");

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
    // ----- End Public nested classes -----

    private enum PaneType {
        GENERAL,
        APPEARANCE,
    }

    private class Panes {
        GeneralPane general;
        AppearancePane appearance;
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
        IntBuffer scrollIndex = IntBuffer.allocate(8); // could be lower
        int scrollActive = 0;

        PaneType activePane = PaneType.GENERAL;
    }

    Panes panes;
    State state = new State();
    int windowWidth, windowHeight;
    boolean darken = true;

    public SettingsDialog(NavigationController nc, int windowWidth, int windowHeight) {
        super(nc, windowWidth, windowHeight);

        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.panes = new Panes();
        this.panes.general = this.new GeneralPane();
        this.panes.appearance = this.new AppearancePane();
    }

    @Override
    public void render() {
        if (!super.shouldRender())
            return;
        super.render();

        if (!this.state.settingsWindowBoxActive)
            return;

        this.state.settingsWindowBoxActive = !GuiWindowBox(Rects.settingsWindowBox, "Settings");
        GuiListView(Rects.settingsList, "General;Appearance", this.state.scrollIndex, this.state.scrollActive);

        this.state.closeButton = GuiButton(Rects.closeButton, "Close");
        this.state.applyButton = GuiButton(Rects.applyButton, "Apply");

        switch (this.state.activePane) {
            case GENERAL:
                this.panes.general.render();
                break;

            case APPEARANCE:
                this.panes.appearance.render();
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

        switch (this.state.activePane) {
            case GENERAL:
                this.panes.general.update(gtState);
                break;

            case APPEARANCE:
                this.panes.appearance.update(gtState);
                break;
        }

        if (this.state.closeButton) {
            this.nc.pop();
            return;
        }

        if (this.state.applyButton)
            System.out.println("Apply button pressed");
    }
}