package com.jkojote.tennis;

public interface GameObject {

    long getId();

    void addEventListener(GameEventListener listener);

    void removeEventListener(GameEventListener listener);

    void notifyListeners(GameEvent event);
}
