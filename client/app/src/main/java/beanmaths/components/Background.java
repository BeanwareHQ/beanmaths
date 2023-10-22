package beanmaths.components;

import com.raylib.Jaylib;
import com.raylib.Raylib;
import static com.raylib.Jaylib.*;

import java.awt.Rectangle;

import javax.lang.model.type.NullType;

public class Background extends Component<NullType> {
    Raylib.Rectangle rect;
    Raylib.Rectangle overlayRect;
    Color overlayColor = Raylib.GetColor(0xf5f5f5ff);

    // TODO: make all this configurable
    static Color rectColor = Raylib.GetColor(0xffeef3ff);
    static int totalFrames = 60 * 6; // FPS * seconds for one cycle
    static float opacityDelta = totalFrames / 255;

    public Background() {
    }

    @Override
    public void render() {
        Raylib.DrawRectangleRec(rect, rectColor);
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
