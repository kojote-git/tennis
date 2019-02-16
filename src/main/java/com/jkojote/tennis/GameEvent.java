package com.jkojote.tennis;

public abstract class GameEvent<T extends GameObject> {

    private String message;

    private T target;

    protected GameEvent(T target, String message) {
        this.target = target;
        this.message = message;
    }

    protected GameEvent(T target) {
        this(target, null);
    }

    public String getMessage() {
        return message;
    }

    public T getTarget() {
        return target;
    }
}
