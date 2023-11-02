package com.ezntek.beanmaths.components.settings;

import static com.raylib.Jaylib.*;

import org.bytedeco.javacpp.BytePointer;

import com.ezntek.beanmaths.components.Component;
import com.ezntek.beanmaths.components.ComponentState;
import com.ezntek.beanmaths.config.Config;
import com.ezntek.beanmaths.util.RequiresDeinit;

public class MathPane extends Component implements RequiresDeinit {
    public class BasicSubpane extends Component implements RequiresDeinit {
        // ----- nested classes -----
        private class Rects {
            // Labels
            static Rectangle enableLabel = new Rectangle(544, 216, 112, 32); // Label: enableLabel
            static Rectangle imperfectDigitsLabel = new Rectangle(544, 256, 112, 32); // Label: imperfectDigitsLabel
            static Rectangle maxNumLabel = new Rectangle(544, 296, 96, 32); // Label: maxNumLabel

            // Check Boxes
            static Rectangle enableCheckBox = new Rectangle(976, 216, 32, 32);
            static Rectangle imperfectDigitsCheckBox = new Rectangle(976, 256, 32, 32); // CheckBoxEx:

            // Input Boxes
            static Rectangle maxNumBox = new Rectangle(936, 296, 72, 32); // TextBox: maxNumBox
        }

        private class State {
            boolean enableChecked = false;
            boolean imperfectDigitsChecked = false;
            boolean maxNumBoxEditMode = false;
            BytePointer maxNumBoxBuf;
        }
        // ----- nested classes end -----

        State state;
        Config cfg;

        public BasicSubpane(Config cfg) {
            super();

            this.cfg = cfg;
            this.state = new State();
            this.init(); // initializes bytepointers
        }

        @Override
        public void render() {
            GuiLabel(Rects.enableLabel, "Enable");
            GuiLabel(Rects.imperfectDigitsLabel, "Imperfect Digits");
            GuiLabel(Rects.maxNumLabel, "Maximum Number");

            this.state.enableChecked = GuiCheckBox(Rects.enableCheckBox, "", this.state.enableChecked);
            this.state.imperfectDigitsChecked = GuiCheckBox(Rects.imperfectDigitsCheckBox, "",
                    this.state.imperfectDigitsChecked);

            if (GuiTextBox(Rects.maxNumBox, this.state.maxNumBoxBuf, 128, this.state.maxNumBoxEditMode))
                this.state.maxNumBoxEditMode = !this.state.maxNumBoxEditMode;
        }

        @Override
        public void update(long gtState) {
            String bufString = this.state.maxNumBoxBuf.getString();
            if (bufString.length() > 3)
                this.state.maxNumBoxBuf.putString(bufString.substring(0, 3));
        }

        @Override
        public void deinit() {
            this.state.maxNumBoxBuf.deallocate();
        }

        @Override
        public void init() {
            this.state.imperfectDigitsChecked = this.cfg.math.basic.imperfectDigits;
            this.state.enableChecked = this.cfg.math.basic.enable;
            this.state.maxNumBoxBuf = new BytePointer(String.valueOf(this.cfg.math.basic.max));
        }
    }

    public class TimesTableSubpane extends Component implements RequiresDeinit {
        private class Rects {
            // labels
            static Rectangle enableLabel = new Rectangle(544, 216, 112, 32); // Label: basicEnableLabel
            static Rectangle maxNumLabel = new Rectangle(544, 256, 112, 32); // Label: maxNumLabel

            // box stuff
            static Rectangle enableCheckBox = new Rectangle(976, 216, 32, 32); // CheckBoxEx: basicEnableCheckBox
            static Rectangle maxNumBox = new Rectangle(936, 256, 72, 32); // TextBox: maxNumBox
        }

        private class State {
            boolean enableChecked = false;
            boolean maxNumBoxEditMode = false;
            BytePointer maxNumBoxBuf;
        }

        State state;
        Config cfg;

        public TimesTableSubpane(Config cfg) {
            super();

            this.cfg = cfg;
            this.state = new State();
            // this.init();
        }

        @Override
        public void render() {
            GuiLabel(Rects.enableLabel, "Enable");
            GuiLabel(Rects.maxNumLabel, "Maximum Number");

            this.state.enableChecked = GuiCheckBox(Rects.enableCheckBox, "", this.state.enableChecked);

            if (GuiTextBox(Rects.maxNumBox, this.state.maxNumBoxBuf, 128, this.state.maxNumBoxEditMode))
                this.state.maxNumBoxEditMode = !this.state.maxNumBoxEditMode;

        }

        @Override
        public void update(long gtState) {
            String bufString = this.state.maxNumBoxBuf.getString();
            if (bufString.length() > 3) {
                this.state.maxNumBoxBuf.putString(bufString.substring(0, 3));
            }
        }

        @Override
        public void deinit() {
            this.state.maxNumBoxBuf.deallocate();
        }

        @Override
        public void init() {
            this.state.enableChecked = this.cfg.math.timesTable.enable;
            this.state.maxNumBoxBuf = new BytePointer(String.valueOf(this.cfg.math.timesTable.max));
        }
    }

    public class AdvancedSubpane extends Component implements RequiresDeinit {
        private class Rects {
            static Rectangle enableCheckBox = new Rectangle(976, 216, 32, 32); // CheckBoxEx: enableCheckBox
            static Rectangle enableLabel = new Rectangle(544, 216, 112, 32); // Label: basicEnableLabel
            static Rectangle maxBaseLabel = new Rectangle(544, 296, 112, 32); // Label: maxBaseLabel
            static Rectangle maxBaseBox = new Rectangle(936, 296, 72, 32); // TextBox: maxBaseBox
            static Rectangle rootsCheckBox = new Rectangle(976, 256, 32, 32); // CheckBoxEx: rootsCheckBox
            static Rectangle rootsLabel = new Rectangle(544, 256, 152, 32); // Label: rootsLabel
            static Rectangle maxPowerBox = new Rectangle(936, 336, 72, 32); // TextBox: maxPowerBox
            static Rectangle maxPowerLabel = new Rectangle(544, 336, 112, 32); // Label: maxPowerLabel
        }

        private class State {
            boolean enableChecked = false;
            boolean rootsChecked = false;

            boolean maxBaseBoxEditMode = false;
            boolean maxPowerBoxEditMode = false;
            BytePointer maxPowerBoxBuf;
            BytePointer maxBaseBoxBuf;
        }

        Config cfg;
        State state;

        public AdvancedSubpane(Config cfg) {
            super();

            this.cfg = cfg;
            this.state = new State();
            this.init(); // hacky way to get around weird NavigationController behaviour
        }

        @Override
        public void render() {
            GuiLabel(Rects.enableLabel, "Enable");
            GuiLabel(Rects.rootsLabel, "Cube/Square Roots");
            GuiLabel(Rects.maxPowerLabel, "Maximum Power");
            GuiLabel(Rects.maxBaseLabel, "Maximum Base");

            this.state.enableChecked = GuiCheckBox(Rects.enableCheckBox, "", this.state.enableChecked);
            this.state.rootsChecked = GuiCheckBox(Rects.rootsCheckBox, "", this.state.rootsChecked);

            if (GuiTextBox(Rects.maxBaseBox, this.state.maxBaseBoxBuf, 128, this.state.maxBaseBoxEditMode))
                this.state.maxBaseBoxEditMode = !this.state.maxBaseBoxEditMode;

            if (GuiTextBox(Rects.maxPowerBox, this.state.maxPowerBoxBuf, 128, this.state.maxPowerBoxEditMode))
                this.state.maxPowerBoxEditMode = !this.state.maxPowerBoxEditMode;
        }

        @Override
        public void update(long gtState) {
            if (!super.shouldUpdate())
                return;
        }

        @Override
        public void deinit() {
            this.state.maxBaseBoxBuf.deallocate();
            this.state.maxPowerBoxBuf.deallocate();
        }

        @Override
        public void init() {
            this.state.enableChecked = this.cfg.math.advanced.enable;
            this.state.rootsChecked = this.cfg.math.advanced.roots;

            this.state.maxPowerBoxBuf = new BytePointer(String.valueOf(this.cfg.math.advanced.maxPower));
            this.state.maxBaseBoxBuf = new BytePointer(String.valueOf(this.cfg.math.advanced.maxBase));
        }
    }

    private class Subpanes {
        BasicSubpane basic;
        TimesTableSubpane tt;
        AdvancedSubpane adv;
    }

    private enum SubpaneType {
        BASIC,
        TIMESTABLE,
        ADVANCED
    }

    private class Rects {
        static Rectangle modesListView = new Rectangle(432, 208, 96, 448); // ListView: modesListView
    }

    private class State {
        int[] scrollIndex = { 0 };
        int indexActive;
        SubpaneType activeSubpane = SubpaneType.BASIC;
    }

    ComponentState cmpState = ComponentState.DISABLED;
    Config cfg;
    State state;
    Subpanes subpanes;

    public MathPane(Config cfg) {
        super();

        this.cfg = cfg;
        this.state = new State();
        this.subpanes = new Subpanes();
        this.subpanes.basic = new BasicSubpane(this.cfg);
        this.subpanes.adv = new AdvancedSubpane(this.cfg);
        this.subpanes.tt = new TimesTableSubpane(this.cfg);
        this.init(); // get past navigationcontroller behaviour
    }

    public void apply() {
        // TODO: fix hacky code
        this.cfg.math.basic.enable = this.subpanes.basic.state.enableChecked;
        this.cfg.math.basic.imperfectDigits = this.subpanes.basic.state.imperfectDigitsChecked;
        this.cfg.math.basic.max = Integer.parseInt(this.subpanes.basic.state.maxNumBoxBuf.getString());

        this.cfg.math.timesTable.enable = this.subpanes.tt.state.enableChecked;
        this.cfg.math.timesTable.max = Integer.parseInt(this.subpanes.tt.state.maxNumBoxBuf.getString());

        this.cfg.math.advanced.enable = this.subpanes.adv.state.enableChecked;
        this.cfg.math.advanced.roots = this.subpanes.adv.state.rootsChecked;
        this.cfg.math.advanced.maxBase = Integer.parseInt(this.subpanes.adv.state.maxBaseBoxBuf.getString());
        this.cfg.math.advanced.maxPower = Integer.parseInt(this.subpanes.adv.state.maxPowerBoxBuf.getString());
    }

    @Override
    public void render() {
        this.state.indexActive = GuiListView(Rects.modesListView, "Basic;Times Table;Advanced", this.state.scrollIndex,
                this.state.indexActive);

        DrawText("Math", 432, 148, 40, DARKGRAY);
        switch (this.state.activeSubpane) {
            case BASIC:
                this.subpanes.basic.render();
                break;

            case TIMESTABLE:
                this.subpanes.tt.render();
                break;

            case ADVANCED:
                this.subpanes.adv.render();

            default:
                break;
        }
    }

    @Override
    public void update(long gtState) {
        switch (this.state.indexActive) {
            case 0:
                this.state.activeSubpane = SubpaneType.BASIC;
                break;
            case 1:
                this.state.activeSubpane = SubpaneType.TIMESTABLE;
                break;
            case 2:
                this.state.activeSubpane = SubpaneType.ADVANCED;
                break;
        }

        switch (this.state.activeSubpane) {
            case BASIC:
                this.subpanes.basic.update(gtState);
                break;

            case TIMESTABLE:
                this.subpanes.tt.update(gtState);
                break;

            case ADVANCED:
                this.subpanes.adv.update(gtState);

            default:
                break;
        }
    }

    @Override
    public void deinit() {
        this.subpanes.basic.deinit();
        this.subpanes.adv.deinit();
        this.subpanes.tt.deinit();
    }

    @Override
    public void init() {
        this.subpanes.basic.init();
        this.subpanes.adv.init();
        this.subpanes.tt.init();
    }
}
