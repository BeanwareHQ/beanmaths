package com.ezntek.beanmaths.game;

import static com.raylib.Jaylib.*;
import com.raylib.Raylib.Texture;

public class ResourceLoader {
    public static Texture loadOperationsTexture() {
        return LoadTexture("./src/main/resources/signs.png");
    }
}
