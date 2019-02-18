package com.jkojote.tennis.game;

import com.jkojote.linear.engine.window.Window;

public class GameLoop implements Runnable{
    private Window window;
    private UpdateCallback updateCallback;
    private RenderCallback renderCallback;
    private boolean running;

    public GameLoop(Window window) {
        this.window = window;
    }

    public void setUpdateCallback(UpdateCallback updateCallback) {
        this.updateCallback = updateCallback;
    }

    public void setRenderCallback(RenderCallback renderCallback) {
        this.renderCallback = renderCallback;
    }

    @Override
    public void run() {
        running = true;
        double amountOfTicks = 75.0;
        double lastTime = System.nanoTime();
        double ns = 1000000000 / amountOfTicks;
        double elapsed = 0;
        while (!window.isTerminated() && running) {
            long now = System.nanoTime();
            elapsed += (now - lastTime) / ns;
            lastTime = now;
            while (elapsed >= 1) {
                elapsed--;
                window.pollEvents();
                if (updateCallback != null) {
                    updateCallback.perform();
                }
            }
            window.clear();
            if (renderCallback != null) {
                renderCallback.perform();
            }
            window.update();
        }
    }

    public void stop() {
        running = false;
    }
}
