package com.jkojote.tennis.game.objects.platform;

import com.jkojote.linear.engine.graphics2d.Renderable;
import com.jkojote.linear.engine.graphics2d.primitives.solid.SolidRectangle;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.MathUtils;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.physics.CollisionBox;
import com.jkojote.linear.engine.shared.Transformable;
import com.jkojote.tennis.BaseGameObject;
import com.jkojote.tennis.Collidable;
import com.jkojote.tennis.Drawable;
import com.jkojote.tennis.Movable;
import com.jkojote.tennis.game.physics.Surface;

import static com.google.common.base.Preconditions.checkNotNull;

public class Platform extends BaseGameObject implements Collidable, Movable, Transformable, Drawable, Surface {
    private CollisionBox collisionBox;
    private Vec3f velocity;
    private SolidRectangle graphicRepresentation;
    private Vec3f position;
    private float rotationAngle;
    private int width, height;

    public Platform(int width, int height) {
        this.width = width;
        this.height = height;
        this.graphicRepresentation = new SolidRectangle(new Vec3f(), width, height);
        this.collisionBox = new CollisionBox(
            this.graphicRepresentation.vertices().toArray(new Vec3f[0])
        );
        velocity = new Vec3f();
        position = new Vec3f();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
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
    public Renderable getGraphicRepresentation() {
        return graphicRepresentation;
    }

    @Override
    public void setTranslation(Vec3f translation) {
        checkNotNull(translation);
        position = translation;
        graphicRepresentation.setTranslation(translation);
        collisionBox.setTranslation(translation);
    }

    @Override
    public Vec3f getTranslation() {
        return position;
    }

    @Override
    public void setScaleFactor(float scaleFactor) {
    }

    @Override
    public float getScaleFactor() {
        return 1.0f;
    }

    @Override
    public void setRotationAngle(float rotationAngle) {
        this.rotationAngle = rotationAngle;
        graphicRepresentation.setRotationAngle(rotationAngle);
        collisionBox.setRotationAngle(rotationAngle);
    }

    @Override
    public float getRotationAngle() {
        return rotationAngle;
    }

    @Override
    public Mat4f transformationMatrix() {
        return graphicRepresentation.transformationMatrix();
    }

    @Override
    public Vec3f toVector() {
        float rotationAngleRadians = rotationAngle * MathUtils.PI_180;
        return new Vec3f((float) Math.cos(rotationAngleRadians), (float) Math.sin(rotationAngleRadians), 0);
    }
}
