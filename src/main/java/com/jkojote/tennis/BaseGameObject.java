package com.jkojote.tennis;

import com.jkojote.linear.engine.shared.BaseTransformable;
import com.jkojote.tennis.game.objects.platform.ObjectsRegistry;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class BaseGameObject implements GameObject{
    private long id;
    private Set<GameEventListener> listeners;

    protected BaseGameObject(long id) {
        this.id = id;
        this.listeners = new HashSet<>();
    }

    protected BaseGameObject() {
        this.id = ObjectsRegistry.getUniqueId();
        this.listeners = new HashSet<>();
    }

   @Override
    public long getId() {
        return 0;
    }

    @Override
    public void addEventListener(GameEventListener listener) {
        checkNotNull(listener);
        listeners.add(listener);
    }

    @Override
    public void removeEventListener(GameEventListener listener) {
        checkNotNull(listener);
        listeners.remove(listener);
    }

    @Override
    public <T extends GameObject> void notifyListeners(GameEvent<T> event) {
        for (GameEventListener listener: listeners) {
            listener.perform(event);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj instanceof GameObject) {
            GameObject gameObject = (GameObject) obj;
            return id == gameObject.getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }
}
