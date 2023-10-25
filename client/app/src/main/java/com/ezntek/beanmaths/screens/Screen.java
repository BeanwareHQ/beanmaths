package com.ezntek.beanmaths.screens;

import java.util.LinkedList;
import com.ezntek.beanmaths.components.*;
import com.ezntek.beanmaths.navigation.NavigationController;

import static com.ezntek.beanmaths.components.ComponentState.*;

public abstract class Screen extends Component {
    protected NavigationController nc;
    protected int windowWidth;
    protected int windowHeight;

    public Screen(NavigationController nc, int windowWidth, int windowHeight) {
        this.nc = nc;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }
}
