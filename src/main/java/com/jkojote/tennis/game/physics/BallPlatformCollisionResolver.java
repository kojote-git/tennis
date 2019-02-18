package com.jkojote.tennis.game.physics;

import com.jkojote.tennis.game.objects.Ball;
import com.jkojote.tennis.game.objects.platform.Platform;

// TODO
public class BallPlatformCollisionResolver implements CollisionResolver<Ball, Platform> {

    private CollisionResolver<Ball, Surface> resolver = new BallSurfaceCollisionResolver();

    @Override
    public void resolve(Ball ball, Platform platform) {
        resolver.resolve(ball, platform);
    }
}
