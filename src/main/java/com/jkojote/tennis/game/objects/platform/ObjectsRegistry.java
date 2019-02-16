package com.jkojote.tennis.game.objects.platform;

import java.util.concurrent.atomic.AtomicLong;

public final class ObjectsRegistry {

    private static AtomicLong LAST_ID = new AtomicLong(0);

    private ObjectsRegistry() { }

    public static long getUniqueId() {
        return LAST_ID.incrementAndGet();
    }
}
