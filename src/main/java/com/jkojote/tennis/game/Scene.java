package com.jkojote.tennis.game;

import com.jkojote.tennis.Drawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scene {
    private List<Drawable> objects = new ArrayList<>();
    private List<Drawable> unmodifiable = Collections.unmodifiableList(objects);

    public List<Drawable> getObjects() {
        return unmodifiable;
    }

    public void addObject(Drawable obj) {
        objects.add(obj);
    }

    public void removeObject(Drawable obj) {
        objects.remove(obj);
    }
}
