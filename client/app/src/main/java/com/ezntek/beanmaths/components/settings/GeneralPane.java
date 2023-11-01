package com.ezntek.beanmaths.components.settings;

import org.bytedeco.javacpp.BytePointer;

import com.raylib.Jaylib.Rectangle;
import static com.raylib.Jaylib.*;

import com.ezntek.beanmaths.components.Component;
import com.ezntek.beanmaths.components.ComponentState;
import com.ezntek.beanmaths.config.Config;
import com.ezntek.beanmaths.util.RequiresDeinit;

public class GeneralPane extends Component implements RequiresDeinit {
    public ComponentState cmpState = ComponentState.DISABLED;

    private class Rects {
        // Labels
        static Rectangle defaultNickLabel = new Rectangle(432, 208, 136, 32); // Label: defaultNickLabel
        static Rectangle defaultServerLabel = new Rectangle(432, 248, 136, 32); // Label: defaultServerLabel

        // text boxes
        static Rectangle defaultNickBox = new Rectangle(576, 208, 440, 32); // TextBox: defaultNickBox
        static Rectangle defaultServerBox = new Rectangle(576, 248, 440, 32); // TextBox: defaultServerBox

    }

    private class State {
        boolean nickBoxEditMode = false;
        boolean serverBoxEditMode = false;

        BytePointer nickBoxBuf;
        BytePointer serverBoxBuf;
    }

    private Config cfg;
    public State state;

    public GeneralPane(Config cfg) {
        super();
        this.cfg = cfg;

        this.state = new State();
        this.state.nickBoxBuf = new BytePointer(this.cfg.general.defaultNick);
        this.state.serverBoxBuf = new BytePointer(this.cfg.general.defaultServer);
        this.state.serverBoxBuf.capacity(64);
        this.state.nickBoxBuf.capacity(64);
    }

    public void apply() {
        this.cfg.general.defaultNick = this.state.nickBoxBuf.getString();
        this.cfg.general.defaultServer = this.state.serverBoxBuf.getString();
    }

    @Override
    public void render() {
        if (!super.shouldRender())
            return;

        GuiLabel(Rects.defaultNickLabel, "Default Nick");
        GuiLabel(Rects.defaultServerLabel, "Default Server");

        DrawText("General", 432, 148, 40, DARKGRAY);

        if (GuiTextBox(Rects.defaultNickBox, this.state.nickBoxBuf, 128, this.state.nickBoxEditMode))
            this.state.nickBoxEditMode = !this.state.nickBoxEditMode;
        if (GuiTextBox(Rects.defaultServerBox, this.state.serverBoxBuf, 128, this.state.serverBoxEditMode))
            this.state.serverBoxEditMode = !this.state.serverBoxEditMode;
    }

    @Override
    public void update(long gtState) {
        if (!super.shouldUpdate())
            return;
    }

    @Override
    public void deinit() {
        this.state.nickBoxBuf.deallocate();
        this.state.serverBoxBuf.deallocate();
    }

    @Override
    public void init() {
        this.state.nickBoxBuf = new BytePointer(this.cfg.general.defaultNick);
        this.state.serverBoxBuf = new BytePointer(this.cfg.general.defaultServer);

        this.state.nickBoxBuf.capacity(64);
        this.state.serverBoxBuf.capacity(64);
    }
}
