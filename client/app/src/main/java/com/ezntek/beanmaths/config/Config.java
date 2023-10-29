package com.ezntek.beanmaths.config;

import static com.raylib.Jaylib.*;

public class Config {
    public class General {
        public String defaultNick;
        public String defaultServer;
    }

    public class Appearance {
        public String theme;
        public int textSize;
    }

    public class Math {
        public class Basic {
            public boolean enable;
            public boolean imperfectDigits;
            public int max;
        }

        public class TimesTable {
            public boolean enable;
            public int max;
        }

        public class Advanced {
            public boolean enable;
            public boolean roots;
            public int maxBase;
            public int maxPower;
        }

        public Basic basic;
        public TimesTable timesTable;
        public Advanced advanced;
        public int count;
    }

    public General general;
    public Appearance appearance;
    public Math math;

    public void applyAllPossible() {
        // theme
        // TODO: theme support

        // textSize
        GuiSetStyle(DEFAULT, TEXT_SIZE, this.appearance.textSize);
    }
}
