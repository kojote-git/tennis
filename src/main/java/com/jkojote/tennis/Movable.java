package com.jkojote.tennis;

import com.jkojote.linear.engine.math.Vec3f;

public interface Movable {

    Vec3f getVelocity();

    void setVelocity(Vec3f velocity);

    void move();
}
