package com.jkojote.tennis.game.physics;

import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.tennis.game.objects.Ball;

public class BallSurfaceCollisionResolver implements CollisionResolver<Ball, Surface> {

    @Override
    public void resolve(Ball ball, Surface surface) {
        Vec3f ballVelocity = ball.getVelocity().copy();
        Vec3f surfaceVector = surface.toVector().copy();
        reflectVector(ballVelocity, surfaceVector);
        ball.setVelocity(ballVelocity.copy().scalar(2.25f));
        ball.move();
        ball.setVelocity(ballVelocity);
    }

    private void reflectVector(Vec3f velocity, Vec3f surface) {
        Vec3f normal = surface.normal();
        velocity.sub(normal.scalar(2 * velocity.dot(normal)));
    }
}
