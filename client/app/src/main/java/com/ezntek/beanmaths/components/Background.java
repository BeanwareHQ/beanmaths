package com.ezntek.beanmaths.components;

import com.raylib.Jaylib;
import static com.raylib.Jaylib.*;

import com.ezntek.beanmaths.util.Colors;
import static com.ezntek.beanmaths.components.ComponentState.*;

public class Background extends Component {
    Jaylib.Rectangle rect;
    Jaylib.Rectangle overlayRect;
    Color overlayColor = Colors.RAYWHITE;

    // TODO: make all this configurable
    static Color rectColor = Colors.BLUE;
    static int totalFrames = 60 * 12; // FPS * seconds for one cycle
    static float opacityDelta = totalFrames / 255;

    public Background(int screenWidth, int screenHeight) {
        this.rect = new Jaylib.Rectangle(0, 0, screenWidth, screenHeight);
        this.overlayRect = new Jaylib.Rectangle(0, 0, screenWidth, screenHeight);
        this.overlayColor = new Jaylib.Color(245, 245, 245, 255);
        this.state = DISABLED;
    }

    @Override
    public void render() {
        if (!super.shouldRender())
            return;

        Jaylib.DrawRectangleRec(this.rect, rectColor);
        Jaylib.DrawRectangleRec(this.overlayRect, this.overlayColor);
    }

    @Override
    public void update(long gtState) {
        if (!super.shouldUpdate())
            return;

        int animationProgress = (int) ((gtState + totalFrames) % totalFrames);
        int halfTotalFrames = totalFrames / 2;

        if (animationProgress < halfTotalFrames) {
            overlayColor.a((byte) (overlayColor.a() - opacityDelta));
        } else {
            overlayColor.a((byte) (overlayColor.a() + opacityDelta));
        }
    }
}
