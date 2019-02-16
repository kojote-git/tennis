package com.jkojote.tennis.game.objects.platform;

import com.jkojote.linear.engine.window.KeyCallback;
import com.jkojote.linear.engine.window.Window;
import org.lwjgl.glfw.GLFW;

public class PlatformController implements KeyCallback {
    private static final int
        MOVE_LEFT = 0,
        MOVE_RIGHT = 1,
        ROTATE_LEFT = 2,
        ROTATE_RIGHT = 3,
        NONE = -1;

    private Platform platform;
    private boolean[] pressed;
    private float moveSpeed;
    private float rotationSpeed;

    public PlatformController(Platform platform) {
        this.platform = platform;
        this.pressed = new boolean[4];
        this.moveSpeed = 5;
        this.rotationSpeed = 1;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    @Override
    public void perform(Window win, int key, int action, int mods) {
        int platformAction = resolveAction(key);
        if (platformAction == NONE)
            return;
        if (action == GLFW.GLFW_PRESS)
            pressed[platformAction] = true;
        else if (action == GLFW.GLFW_RELEASE)
            pressed[platformAction] = false;
    }

    public void update() {
        if (pressed[MOVE_RIGHT]) {
            platform.setTranslation(platform.getTranslation().add(moveSpeed, 0, 0));
        }
        if (pressed[MOVE_LEFT]) {
            platform.setTranslation(platform.getTranslation().add(-moveSpeed, 0, 0));
        }
        if (pressed[ROTATE_RIGHT]) {
            platform.setRotationAngle(platform.getRotationAngle() - rotationSpeed);
        }
        if (pressed[ROTATE_LEFT]) {
            platform.setRotationAngle(platform.getRotationAngle() + rotationSpeed);
        }
    }

    private int resolveAction(int key) {
        switch (key) {
            case GLFW.GLFW_KEY_RIGHT:
                return MOVE_RIGHT;
            case GLFW.GLFW_KEY_LEFT:
                return MOVE_LEFT;
            case GLFW.GLFW_KEY_A:
                return ROTATE_LEFT;
            case GLFW.GLFW_KEY_D:
                return ROTATE_RIGHT;
            default:
                return NONE;
        }
    }
}
