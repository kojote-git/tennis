package com.jkojote.tennis.game.ui;

import com.jkojote.linear.engine.graphics2d.Renderable;
import com.jkojote.linear.engine.graphics2d.text.FontMap;
import com.jkojote.linear.engine.graphics2d.text.ModifiableText;
import com.jkojote.linear.engine.math.Mat4f;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.shared.Transformable;

public class TextBlock implements Transformable, UiElement {
    private ModifiableText text;
    private static final float RGB_TO_VECTOR_CONVERT_RATIO = 1 / 255f;
    public TextBlock(FontMap font) {
        text = new ModifiableText(font);
    }

    public CharSequence getText() {
        return text.getSequence();
    }

    public void setFont(FontMap font) {
        ModifiableText temp = text;
        text = new ModifiableText(font);
        text.append(temp.getSequence());
    }

    public void setText(CharSequence text) {
        this.text.delete(0, this.text.length());
        this.text.append(text);
    }

    public TextBlock append(CharSequence text) {
        this.text.append(text);
        return this;
    }

    public void setRGBColor(int red, int green, int blue) {
        checkRGB(red, green, blue);
        text.setColor(convertRGB(red, green, blue));
    }

    private Vec3f convertRGB(int red, int green, int blue) {
        float r = red * RGB_TO_VECTOR_CONVERT_RATIO;
        float g = green * RGB_TO_VECTOR_CONVERT_RATIO;
        float b = blue * RGB_TO_VECTOR_CONVERT_RATIO;
        return new Vec3f(r, g, b);
    }

    private void checkRGB(int red, int green, int blue) {
        if (red < 0 || green < 0 || blue < 0)
            throw new IllegalArgumentException("rgb value must be positive");
    }

    @Override
    public Renderable getGraphicRepresentation() {
        return text;
    }

    @Override
    public void setTranslation(Vec3f vec3f) {
        text.setTranslation(vec3f);
    }

    @Override
    public Vec3f getTranslation() {
        return text.getTranslation();
    }

    @Override
    public void setScaleFactor(float scaleFactor) {
        text.setScaleFactor(scaleFactor);
    }

    @Override
    public float getScaleFactor() {
        return text.getScaleFactor();
    }

    @Override
    public void setRotationAngle(float rotationAngle) {
        text.setRotationAngle(rotationAngle);
    }

    @Override
    public float getRotationAngle() {
        return text.getRotationAngle();
    }

    @Override
    public Mat4f transformationMatrix() {
        return text.transformationMatrix();
    }

}
