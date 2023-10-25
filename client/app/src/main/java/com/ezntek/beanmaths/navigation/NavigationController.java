package com.ezntek.beanmaths.navigation;

import java.util.LinkedList;

import com.ezntek.beanmaths.components.Component;
import com.ezntek.beanmaths.components.ComponentState;
import com.ezntek.beanmaths.dialogs.Dialog;
import com.ezntek.beanmaths.screens.Screen;

public class NavigationController {
    private LinkedList<Component> ll;

    public NavigationController() {
        this.ll = new LinkedList<Component>();
    }

    public <S extends Screen> NavigationController(S screen, int sWidth, int sHeight) {
        this.ll = new LinkedList<Component>();
        this.ll.addLast(screen);
    }

    public <S extends Screen> void add(S screen) {
        try {
            this.ll.getLast().state = ComponentState.DISABLED;
        } catch (Exception exc) {
            if (!this.ll.isEmpty() && !(exc instanceof NullPointerException)) {
                throw exc;
            }
        }

        screen.state = ComponentState.ENABLED;
        this.ll.addLast(screen);
    }

    public <D extends Dialog> void add(D dialog) {
        // Supress LSP complaint
        Component prev = Component.makeComponent();
        try {
            prev = this.ll.getLast(); // get value before pushing
        } catch (Exception exc) {
            if (!this.ll.isEmpty() && !(exc instanceof NullPointerException)) {
                throw exc;
            }
        }

        if (prev instanceof Dialog) {
            dialog.darken = false;
            // Fully disable the prevous dialog.
            prev.state = ComponentState.DISABLED;
        } else {
            // if the top element is a dialog, allow the background
            // to be displayed, but not updated.
            //
            // Makes usage a bit more natural.
            prev.state = ComponentState.NOUPDATE;
        }

        dialog.state = ComponentState.ENABLED;
        this.ll.addLast(dialog);
    }

    public Screen getTop() {
        return (Screen) this.ll.getLast();
    }

    public Screen pop() {
        return (Screen) this.ll.pop();
    }

    public LinkedList<Component> getComponents() {
        return this.ll;
    }

    public boolean isEmpty() {
        return this.ll.isEmpty();
    }
}
