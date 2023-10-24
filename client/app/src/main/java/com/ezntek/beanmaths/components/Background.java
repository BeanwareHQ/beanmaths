package com.ezntek.beanmaths.components;

import com.raylib.Jaylib;
import static com.raylib.Jaylib.*;

import com.ezntek.beanmaths.util.Colors;

public class Background extends Component {
    Jaylib.Rectangle rect;
    Jaylib.Rectangle overlayRect;
    Color overlayColor = Colors.RAYWHITE;

    // TODO: make all this configurable
    static Color rectColor = Colors.BLUE;
    static int totalFrames = 60 * 15; // FPS * seconds for one cycle
    static float opacityDelta = totalFrames / 255;

    public Background(int screenWidth, int screenHeight) {
        this.rect = new Jaylib.Rectangle(0, 0, screenWidth, screenHeight);
        this.overlayRect = new Jaylib.Rectangle(0, 0, screenWidth, screenHeight);
        this.overlayColor = new Jaylib.Color(245, 245, 245, 255);
    }
    
    @Override
    public void render() {
        Jaylib.DrawRectangleRec(this.rect, rectColor);
        Jaylib.DrawRectangleRec(this.overlayRect, this.overlayColor);
    }

    @Override
    public void refresh(int gtState) {
        int animationProgress = (gtState + totalFrames) % totalFrames;
        int halfTotalFrames = totalFrames / 2;

        if (animationProgress < halfTotalFrames) {
            overlayColor.a((byte)(overlayColor.a() - opacityDelta));
        } else {
            overlayColor.a((byte)(overlayColor.a() + opacityDelta));
        }
    }
}
