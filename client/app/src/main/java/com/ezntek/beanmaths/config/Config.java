package com.ezntek.beanmaths.config;

public class Config {
    public class Basic {
        boolean enable;
        boolean imperfectDigits;
        int max;
    }

    public class TimesTable {
        boolean enable;
        int max;
    }

    public class Advanced {
        boolean enable;
        boolean roots;
        int maxBase;
        int maxPower;
    }

    Basic basic;
    TimesTable timesTable;
    Advanced advanced;
    int count;
}
