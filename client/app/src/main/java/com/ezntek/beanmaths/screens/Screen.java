package com.ezntek.beanmaths.screens;

import java.util.LinkedList;
import com.ezntek.beanmaths.components.*;
import com.ezntek.beanmaths.config.Config;
import com.ezntek.beanmaths.navigation.NavigationController;

import static com.ezntek.beanmaths.components.ComponentState.*;

public abstract class Screen extends Component {
    protected Config cfg;
    protected NavigationController nc;
    protected int windowWidth;
    protected int windowHeight;

    public Screen(Config cfg, NavigationController nc, int windowWidth, int windowHeight) {
        this.cfg = cfg;
        this.nc = nc;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }
}
