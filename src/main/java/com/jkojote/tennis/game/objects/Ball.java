package com.jkojote.tennis.game.objects;

import com.jkojote.linear.engine.graphics2d.Renderable;
import com.jkojote.linear.engine.graphics2d.primitives.solid.SolidEllipse;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.physics.CollisionBox;
import com.jkojote.linear.engine.shared.Transformable;
import com.jkojote.tennis.BaseGameObject;
import com.jkojote.tennis.Collidable;
import com.jkojote.tennis.Drawable;
import com.jkojote.tennis.Movable;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.jkojote.linear.engine.math.MathUtils.PI_180;

public class Ball extends BaseGameObject implements Transformable, Movable, Collidable, Drawable {
    private CollisionBox collisionBox;
    private SolidEllipse graphicRepresentation;
    private Vec3f velocity;
    private Vec3f position;
    private float scaleFactor;
    private float rotationAngle;
    private float radius;

    public Ball(float radius) {
        this.radius = radius;
        this.scaleFactor = 1.0f;
        graphicRepresentation = new SolidEllipse(new Vec3f(), radius, radius);
        collisionBox = new CollisionBox(getCollisionBoxVertices(radius));
        velocity = new Vec3f();
        position = new Vec3f();
    }

    private Vec3f[] getCollisionBoxVertices(float radius) {
        float x = radius * (float) Math.cos(PI_180 * 60);
        float y = radius * (float) Math.sin(PI_180 * 60);
        // collision box is represented by octagon
        return new Vec3f[] {
            new Vec3f(x, y, 0),
            new Vec3f(-x, y, 0),
            new Vec3f(-y, x, 0),
            new Vec3f(-y, -x, 0),
            new Vec3f(-x, -y, 0),
            new Vec3f(x, -y, 0),
            new Vec3f(y, -x, 0),
            new Vec3f(y, x, 0)
        };
    }

    public float getRadius() {
        return radius;
    }

    public void increaseVelocity(float increment) {
        float x = velocity.x() < 0 ? -increment : increment;
        float y = velocity.y() < 0 ? -increment: increment;
        setVelocity(velocity.add(x, y, 0));
    }

    @Override
    public Renderable getGraphicRepresentation() {
        return graphicRepresentation;
    }

    @Override
    public Vec3f getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(Vec3f velocity) {
        checkNotNull(velocity);
        this.velocity = velocity;
    }

    @Override
    public void move() {
        setTranslation(position.add(velocity));
    }

    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    @Override
    public void setTranslation(Vec3f translation) {
        checkNotNull(translation);
        position = translation;
        collisionBox.setTranslation(translation);
        graphicRepresentation.setTranslation(translation);
    }

    @Override
    public Vec3f getTranslation() {
        return position;
    }

    @Override
    public void setScaleFactor(float v) {
    }

    @Override
    public float getScaleFactor() {
        return scaleFactor;
    }

    @Override
    public void setRotationAngle(float rotationAngle) {
        this.rotationAngle = rotationAngle;
        collisionBox.setRotationAngle(rotationAngle);
        graphicRepresentation.setRotationAngle(rotationAngle);
    }

    @Override
    public float getRotationAngle() {
        return rotationAngle;
    }

    @Override
    public Mat4f transformationMatrix() {
        return graphicRepresentation.transformationMatrix();
    }
}
