package com.ezntek.beanmaths.dialogs;

import static com.raylib.Jaylib.*;

import org.bytedeco.javacpp.BytePointer;
import com.raylib.Jaylib.Rectangle;
import com.raylib.Raylib;
import com.ezntek.beanmaths.components.ComponentState;
import com.ezntek.beanmaths.navigation.NavigationController;
import com.ezntek.beanmaths.util.Colors;

class Rects {
    // Boxes
    static Rectangle playScreenWindowBox = new Rectangle(288, 96, 752, 576); // WindowBox: playScreenWindowBox
    static Rectangle multiplayerGroupBox = new Rectangle(304, 136, 720, 440); // GroupBox: multiplayerGroupBox
    static Rectangle singleplayerGroupBox = new Rectangle(304, 592, 720, 64); // GroupBox: singleplayerGroupBox

    // Text Boxes
    static Raylib.Rectangle nickBox = new Rectangle(632, 272, 224, 32); // TextBox: nickBox
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

class DialogState {
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
    private DialogState dialogState = new DialogState();
    private int windowWidth, windowHeight;

    static String playSingleplayerText = "Singleplayer doesn't require any additional details. You can just play!";
    static Color darkenRectColor = Colors.withOpacity(Colors.DARKGRAY, 40);

    public PlayDialog(NavigationController nc, int windowWidth, int windowHeight) {
        super(nc, windowWidth, windowHeight);
    }

    @Override
    public void render() {
        if (!super.shouldRender())
            return;
        super.render();

        // darken the screen
        DrawRectangleRec(new Rectangle(0, 0, windowWidth, windowHeight), darkenRectColor);

        if (!this.dialogState.playScreenWindowBoxActive)
            return;

        this.dialogState.playScreenWindowBoxActive = !GuiWindowBox(Rects.playScreenWindowBox, "Play");

        // boxes
        GuiGroupBox(Rects.multiplayerGroupBox, "Play Multiplayer");
        GuiGroupBox(Rects.singleplayerGroupBox, "Play Singleplayer");

        // buttons
        dialogState.testConnButton = GuiButton(Rects.testConnButton, "Test Connection");
        dialogState.playMultiplayerButton = GuiButton(Rects.playMultiplayerButton, "Join Multiplayer");
        dialogState.playSingleplayerButton = GuiButton(Rects.playSingleplayerButton, "Join Singleplayer");

        // text boxes
        if (GuiTextBox(Rects.nickBox, this.dialogState.nickBoxBuf, 128,
                this.dialogState.nickBoxEditMode))
            this.dialogState.nickBoxEditMode = !this.dialogState.nickBoxEditMode;

        if (GuiTextBox(Rects.serverBox, this.dialogState.serverBoxBuf, 128, this.dialogState.serverBoxEditMode))
            this.dialogState.serverBoxEditMode = !this.dialogState.serverBoxEditMode;

        if (GuiTextBox(Rects.lobbyBox, this.dialogState.lobbyBoxBuf, 128, this.dialogState.lobbyBoxEditMode))
            this.dialogState.lobbyBoxEditMode = !this.dialogState.lobbyBoxEditMode;

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
    }
}
