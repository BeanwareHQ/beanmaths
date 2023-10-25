package com.ezntek.beanmaths.screens;

import java.util.LinkedList;
import com.ezntek.beanmaths.components.*;
import com.ezntek.beanmaths.navigation.NavigationController;

import static com.ezntek.beanmaths.components.ComponentState.*;

public abstract class Screen extends Component {
    protected NavigationController nc;
    protected int screenWidth;
    protected int screenHeight;

    public Screen(NavigationController nc, int screenWidth, int screenHeight) {
        this.nc = nc;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }
}
