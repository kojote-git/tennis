package com.jkojote.tennis.game;

import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.tennis.GameObject;
import com.jkojote.tennis.game.objects.Ball;
import com.jkojote.tennis.game.objects.BorderLine;
import com.jkojote.tennis.game.objects.platform.Platform;
import com.jkojote.tennis.game.physics.CollisionResolver;
import com.jkojote.tennis.game.physics.Surface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GameField {
    private float width, height;
    private BorderLine topBorder;
    private BorderLine bottomBorder;
    private BorderLine leftBorder;
    private BorderLine rightBorder;
    private Ball ball;
    private Platform platform;
    private Collection<GameObject> gameObjects;
    private Collection<GameObject> unmodifiable;

    public GameField(float width, float height) {
        this.width = width;
        this.height = height;
        initBorders();
        initBall();
        initPlatform();
        gameObjects = new ArrayList<>();
        unmodifiable = Collections.unmodifiableCollection(gameObjects);
    }

    private void initBorders() {
        topBorder = new BorderLine(width, new Vec3f(0, height / 2, 0));
        bottomBorder = new BorderLine(width, new Vec3f(0, -height / 2, 0));
        leftBorder = new BorderLine(height, new Vec3f(-width / 2, 0, 0));
        rightBorder = new BorderLine(height, new Vec3f(width / 2, 0, 0));
        leftBorder.setRotationAngle(90);
        rightBorder.setRotationAngle(90);
    }

    private void initBall() {
        ball = new Ball(5);
        float yOffset = height / 2f - ball.getRadius() * 2;
        float xVelocity = (float) Math.random() * 2 - 1;
        ball.setVelocity(new Vec3f(xVelocity, -3, 0));
        ball.setTranslation(new Vec3f(0, yOffset, 0));
    }

    private void initPlatform() {
        platform = new Platform((int) width / 4, 5);
        float yOffset = -height / 2f + platform.getWidth() / 2f;
        platform.setTranslation(new Vec3f(0, yOffset, 0));
    }

    public void checkBallIsWithinBorders() {
        float ballRadius = ball.getRadius();
        float leftBorderX = leftBorder.getTranslation().x();
        float rightBorderX = rightBorder.getTranslation().x();
        float topBorderY = topBorder.getTranslation().y();
        float bottomBorderY = bottomBorder.getTranslation().y();

        if (ball.getPosition().x() < leftBorderX) {
            ball.setTranslation(new Vec3f(leftBorderX + ballRadius, ball.getPosition().y(), 0));
        }
        if (ball.getPosition().x() > rightBorderX) {
            ball.setTranslation(new Vec3f(rightBorderX - ballRadius, ball.getPosition().y(), 0));
        }
        if (ball.getPosition().y() > topBorderY) {
            ball.setTranslation(new Vec3f(ball.getPosition().x(), topBorderY - ballRadius, 0));
        }
        if (ball.getPosition().y() < bottomBorderY) {
            ball.setTranslation(new Vec3f(ball.getPosition().x(), bottomBorderY + ballRadius, 0));
        }
    }

    public void checkPlatformIsWithinBorders() {
        float halfWidth = platform.getWidth() / 2f;
        float platformY = platform.getTranslation().y();
        float rightBorderX = rightBorder.getTranslation().x();
        float leftBorderX = leftBorder.getTranslation().x();
        if (platform.getPosition().x() + halfWidth > rightBorderX) {
            platform.setTranslation(new Vec3f(rightBorderX - halfWidth, platformY, 0));
        }
        if (platform.getPosition().x() - halfWidth < leftBorderX) {
            platform.setTranslation(new Vec3f(leftBorderX + halfWidth, platformY, 0));
        }
    }

    public void checkAndResolveBorderCollision(CollisionResolver<Ball, ? super Surface> collisionResolver) {
        if (ball.collides(topBorder)) {
            collisionResolver.resolve(ball, topBorder);
        }
        if (ball.collides(leftBorder)) {
            collisionResolver.resolve(ball, leftBorder);
        }
        if (ball.collides(rightBorder)) {
            collisionResolver.resolve(ball, rightBorder);
        }
    }

    public Collection<GameObject> getGameObjects() {
        return unmodifiable;
    }

    public boolean addGameObject(GameObject obj) {
        return gameObjects.add(obj);
    }

    public boolean removeGameObject(GameObject obj) {
        return gameObjects.remove(obj);
    }

    public BorderLine getTopBorder() {
        return topBorder;
    }

    public BorderLine getBottomBorder() {
        return bottomBorder;
    }

    public BorderLine getLeftBorder() {
        return leftBorder;
    }

    public BorderLine getRightBorder() {
        return rightBorder;
    }

    public Ball getBall() {
        return ball;
    }

    public Platform getPlatform() {
        return platform;
    }
}
