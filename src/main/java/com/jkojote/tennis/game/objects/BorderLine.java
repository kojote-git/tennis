package com.jkojote.tennis.game.objects;

import com.jkojote.linear.engine.graphics2d.Renderable;
import com.jkojote.linear.engine.graphics2d.primitives.Line;
import com.jkojote.linear.engine.math.MathUtils;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.physics.CollisionBox;
import com.jkojote.linear.engine.shared.BaseTransformable;
import com.jkojote.tennis.Drawable;
import com.jkojote.tennis.game.physics.Surface;

public class BorderLine extends BaseTransformable implements Surface, Drawable {

    private CollisionBox collisionBox;
    private Line line;

    public BorderLine(int width, Vec3f position) {
        collisionBox = new CollisionBox(createVertices(width));
        collisionBox.setTranslation(position);
        line = new Line(width);
        line.setTranslation(position);
    }

    private Vec3f[] createVertices(int width) {
        Vec3f begin = new Vec3f(-width / 2f, 0, 0);
        Vec3f end = new Vec3f(width / 2f, 0, 0);
        return new Vec3f[] { begin, end };
    }

    @Override
    public CollisionBox getCollisionBox() {
        return collisionBox;
    }

    @Override
    public Vec3f toVector() {
        float rotationAngleRadians = getRotationAngle() * MathUtils.PI_180;
        return new Vec3f((float) Math.cos(rotationAngleRadians), (float) Math.sin(rotationAngleRadians), 0);
    }

    @Override
    public void setTranslation(Vec3f translation) {
        super.setTranslation(translation);
        line.setTranslation(translation);
        collisionBox.setTranslation(translation);
    }

    @Override
    public void setRotationAngle(float rotationAngle) {
        super.setRotationAngle(rotationAngle);
        line.setRotationAngle(rotationAngle);
        collisionBox.setRotationAngle(rotationAngle);
    }

    @Override
    public void setScaleFactor(float scaleFactor) {
    }

    @Override
    public Renderable getGraphicRepresentation() {
        return line;
    }
}
