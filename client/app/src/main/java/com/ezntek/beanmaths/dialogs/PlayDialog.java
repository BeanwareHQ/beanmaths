package com.ezntek.beanmaths.dialogs;

import static com.raylib.Jaylib.*;

import org.bytedeco.javacpp.BytePointer;
import com.raylib.Jaylib.Rectangle;
import com.ezntek.beanmaths.components.ComponentState;
import com.ezntek.beanmaths.navigation.NavigationController;

class PlayDlgRects {
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

class PlayDlgState {
    boolean playScreenWindowBoxActive = true;
    boolean testConnButton = false;
    boolean playMultiplayerButton = false;
    boolean playSingleplayerButton = false;

    boolean nickBoxEditMode = false;
    boolean serverBoxEditMode = false;
    boolean lobbyBoxEditMode = false;

    BytePointer nickBoxBuf = new BytePointer("Joe Jimmy John");
    BytePointer serverBoxBuf = new BytePointer("10.0.3.273:2257");
    BytePointer lobbyBoxBuf = new BytePointer("12");
}

public class PlayDialog extends Dialog {
    // keep the dialog disabled
    public ComponentState state = ComponentState.DISABLED;
    private PlayDlgState dlgState = new PlayDlgState();

    static String playSingleplayerText = "Singleplayer doesn't require any additional details. You can just play!";

    public PlayDialog(NavigationController nc, int windowWidth, int windowHeight) {
        super(nc, windowWidth, windowHeight);
    }

    @Override
    public void render() {
        if (!super.shouldRender())
            return;
        super.render();

        if (!this.dlgState.playScreenWindowBoxActive)
            return;

        this.dlgState.playScreenWindowBoxActive = !GuiWindowBox(PlayDlgRects.playScreenWindowBox, "Play");

        // boxes
        GuiGroupBox(PlayDlgRects.multiplayerGroupBox, "Play Multiplayer");
        GuiGroupBox(PlayDlgRects.singleplayerGroupBox, "Play Singleplayer");

        // buttons
        dlgState.testConnButton = GuiButton(PlayDlgRects.testConnButton, "Test Connection");
        dlgState.playMultiplayerButton = GuiButton(PlayDlgRects.playMultiplayerButton, "Join Multiplayer");
        dlgState.playSingleplayerButton = GuiButton(PlayDlgRects.playSingleplayerButton, "Join Singleplayer");

        // text boxes
        if (GuiTextBox(PlayDlgRects.nickBox, this.dlgState.nickBoxBuf, 128,
                this.dlgState.nickBoxEditMode))
            this.dlgState.nickBoxEditMode = !this.dlgState.nickBoxEditMode;

        if (GuiTextBox(PlayDlgRects.serverBox, this.dlgState.serverBoxBuf, 128, this.dlgState.serverBoxEditMode))
            this.dlgState.serverBoxEditMode = !this.dlgState.serverBoxEditMode;

        if (GuiTextBox(PlayDlgRects.lobbyBox, this.dlgState.lobbyBoxBuf, 128, this.dlgState.lobbyBoxEditMode))
            this.dlgState.lobbyBoxEditMode = !this.dlgState.lobbyBoxEditMode;

        // labels
        GuiLabel(PlayDlgRects.nickLabel, "Nick");
        GuiLabel(PlayDlgRects.serverLabel, "Server");
        GuiLabel(PlayDlgRects.lobbyLabel, "Lobby");
        GuiLabel(PlayDlgRects.playSingleplayerLabel, playSingleplayerText);
    }

    @Override
    public void update(long gtState) {
        if (!super.shouldUpdate())
            return;

        if (!this.dlgState.playScreenWindowBoxActive) {
            nc.pop();
            this.dlgState.playScreenWindowBoxActive = true; // set it to true for the next use
            return;
        }
    }
}
