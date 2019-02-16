package com.jkojote.tennis;

public interface GameObject {

    long getId();

    void addEventListener(GameEventListener listener);

    void removeEventListener(GameEventListener listener);

    <T extends GameObject> void notifyListeners(GameEvent<T> event);
}
