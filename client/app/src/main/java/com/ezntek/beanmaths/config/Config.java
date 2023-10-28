package com.ezntek.beanmaths.config;

public class Config {
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
