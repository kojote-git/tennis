package com.jkojote.tennis.game.physics;

import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.tennis.Collidable;

public interface Surface extends Collidable {
    Vec3f toVector();
}
