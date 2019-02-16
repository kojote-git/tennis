package com.jkojote.tennis.game.physics;

import com.jkojote.tennis.Collidable;

public interface CollisionResolver<T1 extends Collidable, T2 extends Collidable> {

    void resolve(T1 firstCollidable, T2 secondCollidable);
}
