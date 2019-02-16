package com.jkojote.tennis;

import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.tennis.game.objects.Ball;
import com.jkojote.tennis.game.objects.platform.Platform;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class CollisionTest {

    @Test
    public void testCollision() {
        Ball ball = new Ball(15);
        Platform platform = new Platform(85, 5);

        assertTrue(ball.collides(platform));
        assertTrue(platform.collides(ball));

        moveUp(ball, 16);

        assertFalse(ball.collides(platform));
        assertFalse(platform.collides(ball));

        moveDown(ball, 2 * 16);

        assertFalse(ball.collides(platform));
        assertFalse(platform.collides(ball));

        moveUp(ball, 16);
        moveRight(platform, 60);

        assertFalse(ball.collides(platform));
        assertFalse(platform.collides(ball));

        moveLeft(platform, 2 * 60);

        assertFalse(ball.collides(platform));
        assertFalse(platform.collides(ball));
    }

    private void moveAndStop(Movable obj, Vec3f velocity) {
        obj.setVelocity(velocity);
        obj.move();
        obj.setVelocity(new Vec3f());
    }

    private void moveUp(Movable obj, float distance) {
        moveAndStop(obj, new Vec3f(0, distance, 0));
    }

    private void moveDown(Movable obj, float distance) {
        moveAndStop(obj, new Vec3f(0, -distance, 0));
    }

    private void moveLeft(Movable obj, float distance) {
        moveAndStop(obj, new Vec3f(-distance, 0, 0));
    }

    private void moveRight(Movable obj, float distance) {
        moveAndStop(obj, new Vec3f(distance, 0, 0));
    }

}
