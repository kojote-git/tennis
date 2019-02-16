package com.jkojote.tennis;

import com.jkojote.linear.engine.physics.CollisionBox;

public interface Collidable {

    default boolean collides(Collidable c) {
        return getCollisionBox().checkCollides(c.getCollisionBox());
    }

    CollisionBox getCollisionBox();
}
