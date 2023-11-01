package com.ezntek.beanmaths.dialogs;

import com.ezntek.beanmaths.config.Config;
import com.ezntek.beanmaths.navigation.NavigationController;
import com.ezntek.beanmaths.screens.Screen;

import com.raylib.Jaylib.Color;
import com.raylib.Jaylib.Rectangle;
import com.raylib.Jaylib;

public abstract class Dialog extends Screen {
    protected Rectangle darkeningRect;
    public boolean darken;

    Color darkeningColor;

    public Dialog(Config cfg, NavigationController nc, int windowWidth, int windowHeight) {
        super(cfg, nc, windowWidth, windowHeight);

        this.darkeningRect = new Rectangle(0, 0, windowWidth, windowHeight);
        this.darkeningColor = new Jaylib.Color(80, 80, 80, 80);
    }

    @Override
    public void render() {
        if (!super.shouldRender())
            return;

        if (this.darken) {
            Jaylib.DrawRectangleRec(darkeningRect, darkeningColor);
        }
    }
}
