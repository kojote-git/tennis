package com.jkojote.tennis.game;

import com.jkojote.linear.engine.graphics2d.cameras.StaticCamera;
import com.jkojote.linear.engine.graphics2d.engine.GraphicsEngine;
import com.jkojote.linear.engine.graphics2d.engine.PrimitiveGraphicsEngineImpl;
import com.jkojote.linear.engine.graphics2d.text.FontMap;
import com.jkojote.linear.engine.math.Vec3f;
import com.jkojote.linear.engine.shared.Releasable;
import com.jkojote.linear.engine.window.Window;
import com.jkojote.tennis.game.objects.Ball;
import com.jkojote.tennis.game.objects.BorderLine;
import com.jkojote.tennis.game.objects.platform.Platform;
import com.jkojote.tennis.game.objects.platform.PlatformController;
import com.jkojote.tennis.game.physics.BallSurfaceCollisionResolver;
import com.jkojote.tennis.game.physics.CollisionResolver;
import com.jkojote.tennis.game.physics.Surface;
import com.jkojote.tennis.game.ui.TextBlock;
import com.jkojote.tennis.game.ui.UiElement;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Game implements Runnable {
    private GameLoop gameLoop;
    private Window window;
    private GraphicsEngine graphicsEngine;
    private Ball ball;
    private Platform platform;
    private PlatformController platformController;
    private CollisionResolver<Ball, Surface> ballSurfaceCollisionResolver;
    private Set<BorderLine> borders;
    private Surface bottomBorder;
    private List<Releasable> releasableResources;
    private Map<String, UiElement> ui;
    private FontMap font;
    private int score = 0;

    Game(int width, int height) {
        this.window = new Window("Game", width, height, false, false, true);
        this.gameLoop = new GameLoop(window);
        this.graphicsEngine = new PrimitiveGraphicsEngineImpl();
        this.ballSurfaceCollisionResolver = new BallSurfaceCollisionResolver();
        this.borders  = new HashSet<>();
        this.ui = new TreeMap<>();
        this.releasableResources = new ArrayList<>();
        initUi(width, height);
        initGameObjects(width, height);
        setGameLoopRenderCallback();
        setGameLoopUpdateCallback();
        graphicsEngine.setCamera(new StaticCamera(window));
        releasableResources.add(window);
        releasableResources.add(graphicsEngine);
        window.setKeyCallback(platformController);
        window.setSamplePoints(4);
    }

    private void initFont() {
        try {
            font = FontMap.FontMapBuilder.aFont()
                    .fromFile("src/main/resources/lunchds.ttf")
                    .withFontFormat(Font.TRUETYPE_FONT)
                    .withSize(32)
                    .withAntialiasingEnabled()
                    .build();
            releasableResources.add(font);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
    }

    private void initUi(int windowWidth, int windowHeight) {
        initFont();
        TextBlock score = new TextBlock(font);
        score.setRGBColor(206, 173, 70);
        score.setText("score: 0");
        score.setTranslation(new Vec3f(0, windowHeight / 2f, 0));
        ui.put("score", score);
    }

    private void initGameObjects(int windowWidth, int windowHeight) {
        ball = new Ball(5);
        ball.setVelocity(new Vec3f(0, -4, 0));
        platform = new Platform(85, 3);
        Margin borderMargins = getBordersMargin();
        borders = createBorders(windowWidth, windowHeight, borderMargins);
        ball.setTranslation(new Vec3f(0, windowHeight / 2f - 48, 0));
        platform.setTranslation(new Vec3f(0, -windowHeight / 2f + 48, 0));
        platformController = new PlatformController(platform);
    }

    private Margin getBordersMargin() {
        Margin borderMargins = new Margin();
        borderMargins.top = 32;
        borderMargins.bottom = 25;
        borderMargins.left = borderMargins.right = 15;
        return borderMargins;
    }

    private Set<BorderLine> createBorders(int fieldWidth, int fieldHeight, Margin margin) {
        Set<BorderLine> borders = new HashSet<>();
        BorderLine top = new BorderLine(fieldWidth, new Vec3f(0, fieldHeight / 2f - margin.top, 0));
        BorderLine bottom = new BorderLine(fieldWidth, new Vec3f(0, -fieldHeight/ 2f + margin.bottom, 0));
        BorderLine left = new BorderLine(fieldHeight, new Vec3f(-fieldWidth / 2f + margin.left, 0, 0));
        BorderLine right = new BorderLine(fieldHeight, new Vec3f(fieldWidth / 2f - margin.right, 0, 0));
        left.setRotationAngle(90);
        right.setRotationAngle(90);
        borders.add(top);
        borders.add(bottom);
        borders.add(left);
        borders.add(right);
        bottomBorder = bottom;
        return borders;
    }

    private class Margin {
        float top, bottom, right, left;
    }

    private void setGameLoopRenderCallback() {
        gameLoop.setRenderCallback(() -> {
            graphicsEngine.render(ball.getGraphicRepresentation());
            graphicsEngine.render(platform.getGraphicRepresentation());
            for (UiElement uiElement : ui.values()) {
                graphicsEngine.render(uiElement.getGraphicRepresentation());
            }
            for (BorderLine borderLine : borders) {
                graphicsEngine.render(borderLine.getGraphicRepresentation());
            }
        });
    }

    private void setGameLoopUpdateCallback() {
        gameLoop.setUpdateCallback(() -> {
            for (BorderLine border : borders) {
                if (ball.collides(border)) {
                    if (border == bottomBorder) {
                        endGame();
                    }
                    ballSurfaceCollisionResolver.resolve(ball, border);
                }
                if (ball.collides(platform)) {
                    ballSurfaceCollisionResolver.resolve(ball, platform);
                    updateScore();
                    ball.increaseVelocity(0.025f);
                }
            }
            ball.move();
            platformController.update();
        });
    }

    private void endGame() {
        throw new RuntimeException("game end");
    }

    private void updateScore() {
        score++;
        TextBlock scoreBlock = (TextBlock) ui.get("score");
        scoreBlock.setText("score: " + score);
    }

    @Override
    public void run() {
        try {
            window.init();
            graphicsEngine.init();
            font.init();
            gameLoop.run();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            releaseResources(releasableResources);
        }
    }

    private void releaseResources(Collection<Releasable> releasableResources) {
        for (Releasable r : releasableResources)
            tryRelease(r);
    }

    private void tryRelease(Releasable releasable) {
        try {
            if (releasable == null)
                return;
            releasable.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
