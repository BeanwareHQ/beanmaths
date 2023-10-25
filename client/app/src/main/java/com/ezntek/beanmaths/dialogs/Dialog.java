package com.ezntek.beanmaths.dialogs;

import com.ezntek.beanmaths.navigation.NavigationController;
import com.ezntek.beanmaths.screens.Screen;

import com.raylib.Jaylib.Color;
import com.raylib.Jaylib.Rectangle;
import com.raylib.Jaylib;

public abstract class Dialog extends Screen {
    protected Rectangle darkeningRect;
    public boolean darken;

    Color darkeningColor;

    public Dialog(NavigationController nc, int screenWidth, int screenHeight) {
        super(nc, screenWidth, screenHeight);
        this.darken = true;
        this.darkeningRect = new Rectangle(0, 0, screenWidth, screenHeight);
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
