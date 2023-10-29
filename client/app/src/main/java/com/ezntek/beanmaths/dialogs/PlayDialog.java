package com.ezntek.beanmaths.dialogs;

import static com.raylib.Jaylib.*;

import org.bytedeco.javacpp.BytePointer;
import com.raylib.Jaylib.Rectangle;
import com.ezntek.beanmaths.components.ComponentState;
import com.ezntek.beanmaths.config.Config;
import com.ezntek.beanmaths.navigation.NavigationController;
import com.ezntek.beanmaths.screens.GameScreen;
import com.ezntek.beanmaths.screens.Screen;
import com.ezntek.beanmaths.screens.GameScreen.GameMode;
import com.ezntek.beanmaths.util.RequiresDeinit;

public class PlayDialog extends Dialog implements RequiresDeinit {
    // avoid namespace clashing issues
    private class Rects {
        // Boxes
        static Rectangle playScreenWindowBox = new Rectangle(288, 96, 752, 576); // WindowBox: playScreenWindowBox
        static Rectangle multiplayerGroupBox = new Rectangle(304, 136, 720, 440); // GroupBox: multiplayerGroupBox
        static Rectangle singleplayerGroupBox = new Rectangle(304, 592, 720, 64); // GroupBox: singleplayerGroupBox

        // Text Boxes
        static Rectangle nickBox = new Rectangle(632, 272, 224, 32); // TextBox: nickBox
        static Rectangle serverBox = new Rectangle(632, 312, 224, 32); // TextBox: serverBox
        static Rectangle lobbyBox = new Rectangle(632, 352, 224, 32); // TextBox: lobbyBox

        // Label
        static Rectangle nickLabel = new Rectangle(528, 272, 96, 32); // Label: nickLabel
        static Rectangle serverLabel = new Rectangle(528, 312, 96, 32); // Label: serverLabel
        static Rectangle lobbyLabel = new Rectangle(528, 352, 96, 32); // Label: lobbyLabel
        static Rectangle playSingleplayerLabel = new Rectangle(320, 608, 480, 32); // Label: singleplayerLabel

        // Buttons
        static Rectangle testConnButton = new Rectangle(728, 528, 136, 32); // Button: testConnButton
        static Rectangle playSingleplayerButton = new Rectangle(872, 608, 136, 32); // Button: playSingleplayerButton
        static Rectangle playMultiplayerButton = new Rectangle(872, 528, 136, 32); // Button: playMultiplayerButton
    }

    private class State {
        boolean playScreenWindowBoxActive = true;
        boolean testConnButton = false;
        boolean playMultiplayerButton = false;
        boolean playSingleplayerButton = false;

        boolean nickBoxEditMode = false;
        boolean serverBoxEditMode = false;
        boolean lobbyBoxEditMode = false;

        // TODO: load these values from the settings loader
        BytePointer nickBoxBuf;
        BytePointer serverBoxBuf;
        BytePointer lobbyBoxBuf;

        boolean shouldClearNickBoxDefault;
        boolean shouldClearServerBoxDefault;
        boolean shouldClearLobbyBoxDefault;

        State(Config cfg) {
            this.shouldClearNickBoxDefault = true;
            this.shouldClearServerBoxDefault = true;
            this.shouldClearLobbyBoxDefault = true;

            this.nickBoxBuf = new BytePointer(cfg.general.defaultNick);
            this.serverBoxBuf = new BytePointer(cfg.general.defaultServer);
            this.lobbyBoxBuf = new BytePointer("1");
        }
    }

    // keep the dialog disabled
    public ComponentState cmpState = ComponentState.DISABLED;

    private boolean shouldClearDefaultText;
    private State state = new State(cfg);

    static String playSingleplayerText = "Singleplayer doesn't require any additional details. You can just play!";

    public PlayDialog(Config cfg, NavigationController nc, int windowWidth, int windowHeight) {
        super(cfg, nc, windowWidth, windowHeight);

        // fix config
        this.cfg = cfg;

        // fix the buffer sizes
        this.state.nickBoxBuf.capacity(64);
        this.state.lobbyBoxBuf.capacity(64);
        this.state.serverBoxBuf.capacity(64);
    }

    @Override
    public void render() {
        if (!super.shouldRender())
            return;
        super.render();

        if (!this.state.playScreenWindowBoxActive)
            return;

        this.state.playScreenWindowBoxActive = !GuiWindowBox(Rects.playScreenWindowBox, "Play");

        // boxes
        GuiGroupBox(Rects.multiplayerGroupBox, "Play Multiplayer");
        GuiGroupBox(Rects.singleplayerGroupBox, "Play Singleplayer");

        // buttons
        this.state.testConnButton = GuiButton(Rects.testConnButton, "Test Connection");
        this.state.playMultiplayerButton = GuiButton(Rects.playMultiplayerButton, "Join Multiplayer");
        this.state.playSingleplayerButton = GuiButton(Rects.playSingleplayerButton, "Join Singleplayer");

        // text boxes
        if (GuiTextBox(Rects.nickBox, this.state.nickBoxBuf, 128,
                this.state.nickBoxEditMode))
            this.state.nickBoxEditMode = !this.state.nickBoxEditMode;

        if (GuiTextBox(Rects.serverBox, this.state.serverBoxBuf, 128, this.state.serverBoxEditMode))
            this.state.serverBoxEditMode = !this.state.serverBoxEditMode;

        if (GuiTextBox(Rects.lobbyBox, this.state.lobbyBoxBuf, 128, this.state.lobbyBoxEditMode))
            this.state.lobbyBoxEditMode = !this.state.lobbyBoxEditMode;

        // labels
        GuiLabel(Rects.nickLabel, "Nick");
        GuiLabel(Rects.serverLabel, "Server");
        GuiLabel(Rects.lobbyLabel, "Lobby");
        GuiLabel(Rects.playSingleplayerLabel, playSingleplayerText);
    }

    @Override
    public void update(long gtState) {
        if (!super.shouldUpdate())
            return;

        if (!this.state.playScreenWindowBoxActive) {
            nc.pop();
            this.state.playScreenWindowBoxActive = true; // set it to true for the next use
            return;
        }

        // chop strings to make sure that buffer overflows don't happen
        String lobbyBoxBufString = this.state.lobbyBoxBuf.getString();
        String serverBoxBufString = this.state.serverBoxBuf.getString();
        String nickBoxBufString = this.state.nickBoxBuf.getString();

        if (lobbyBoxBufString.length() > 63)
            this.state.lobbyBoxBuf.putString(lobbyBoxBufString.substring(0, 63));

        if (serverBoxBufString.length() > 63)
            this.state.serverBoxBuf.putString(serverBoxBufString.substring(0, 63));

        if (nickBoxBufString.length() > 63)
            this.state.nickBoxBuf.putString(nickBoxBufString.substring(0, 63));

        // lobby box text clearing
        if (this.state.shouldClearLobbyBoxDefault && this.state.lobbyBoxEditMode) {
            this.state.lobbyBoxBuf.putString("");
            this.state.shouldClearLobbyBoxDefault = false;
        }

        if (this.state.shouldClearServerBoxDefault && this.state.serverBoxEditMode) {
            this.state.serverBoxBuf.putString("");
            this.state.shouldClearServerBoxDefault = false;
        }

        if (this.state.shouldClearNickBoxDefault && this.state.nickBoxEditMode) {
            this.state.nickBoxBuf.putString("");
            this.state.shouldClearNickBoxDefault = false;
        }

        // button events
        if (this.state.playMultiplayerButton) {
            Screen gameScreen = new GameScreen(this.cfg, this.nc, this.windowWidth, this.windowHeight,
                    GameMode.MULTIPLAYER);
            this.nc.pop();
            this.state.playScreenWindowBoxActive = true; // set it to true for the next use

            this.nc.add(gameScreen);
            return;
        }

        if (this.state.playSingleplayerButton) {
            Screen gameScreen = new GameScreen(this.cfg, this.nc, this.windowWidth, this.windowHeight,
                    GameMode.SINGLEPLAYER);
            this.nc.pop();
            this.state.playScreenWindowBoxActive = true; // set it to true for the next use

            this.nc.add(gameScreen);
            return;
        }
    }

    @Override
    public void deinit() {
        // free the byte pointers
        this.state.nickBoxBuf.deallocate();
        this.state.serverBoxBuf.deallocate();
        this.state.lobbyBoxBuf.deallocate();
    }

    @Override
    public void init() {
        this.state = new State(this.cfg);
    }
}
